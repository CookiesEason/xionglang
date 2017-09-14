package com.base.generic.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.base.generic.dao.GenericDao;
import com.base.util.CacheManagerFactory;
import com.base.util.Sql;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.sun.star.uno.Exception;

/**
 * @ @author：熊浪 @Email：xiongl@sunline.cn @Time：2017年7月7日 @此类作用：提供公共执行的dao层
 *   1、分清String是通过HQL还是SQL执行的语句createQuery执行HQL，
 *   createNativeQuery执行SQL，使用Sql对象一般为HQL,尽量使用HQL,
 *   因为HQL是面向对象的，SQL是面向SQL的，面向SQL,因为多方数据库的驱动不同，
 *   对SQL的实现可能存在不同，如分页，mysql使用的是limit,别的使用的是序列，
 *   这样sql的写法可能就不同，但如果使用面向HQL,驱动会自动帮你生成对应的SQL,这样就不用你去判断了。
 *   2、所有需要使用em操作的对象都必须在scan扫描的时候能在dao层扫描到
 *   3、必须指定dao类，不能直接集成GenericDaoImpl，否则不能找到指定的数据库
 *   4、在自定义的dao重写了setEntityManagerFactory方法，就必须具体实现，否则不会加载连接
 *   5、JPA做添加操作需要开启事物，因为方法是线程安全的，同一个方法中可能使用多个em对象，
 *   所以统一在basedao判断事物是否开启，如果没有开启，就开启。开启之后统一在AOP中提交或回滚。
 *   如果AOP中事物回滚，之后默认执行错误西路信息的方法。
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class GenericDaoImpl<T> implements GenericDao<T> {
	private static final Logger logger = LoggerFactory.getLogger(GenericDaoImpl.class);
	// 当前使用的 EntityManager,属于对象使用
	protected EntityManager em;
	// 对应的Dao,属于对象使用
	protected Class<T> type;
	// 存储所有的EntityManagerFactory工厂,以便根据实际情况扩充连接，整个项目共享，设置为静态
	protected static Map<Object, EntityManagerFactory> emfs;
	// 出事化一定的EntityManager连接，通过Object确定指定的Dao,Object为type,整个项目共享，设置为静态
	protected static Map<Object, LinkedList<EntityManager>> emsMap;
	// 存储当前线程的所有连接，同一个线程对同一个type只会使用一个对象的连接
	protected static Map<Thread, LinkedList<Map<Object, EntityManager>>> emMap;
	// 存储当前线程的所有事物，同一个线程对同一个type只会使用一个对象的连接
	protected static Map<Thread, LinkedList<EntityTransaction>> txMap;
	// 线程池中所有活动的线程信息
	protected static Map<Thread, StackTraceElement[]> threads;
	// 创建连接数,默认创建1个，可以根据实际情况修改，通过具体的Dao的构造方法修改num的值设置指定的dao的连接
	protected int num = 1;
	// 默认每个对象使用的最大EntityManager数
	protected final static int MAX_CONNECTION = 20;
	// 当前session
	protected static Session session;
	// 当前sessionFactory
	protected static SessionFactory sessionFactory;
	/*
	 * 使用EntityManagerFactory创建EntityManager,它会为每一个dao创建一个EntityManager对象，
	 * 也会为每一个EntityManager 创建一个事物，当时用了@Transactional开启事物时，其实只是默认开启了一个事物，
	 * 如果你在这个方法中有另一个EntityManager对象进行修改
	 * 操作时，第二个EntityManager的事物就未开启，但JPA的CUD操作都需要开启事物，这时就报错了，
	 * 解决办法就是在所有的CUD操作时判断事物是否开
	 * 启，如果没有开启，就开启当前事物，但不要做提交和回滚操作，否则外部@Transactional失效，最后让@
	 * Transactional失效做统一提交和回滚 这样就可以了。
	 */
	// 当前线程当前EntityManager的事物
	protected EntityTransaction tx;

	static {
		emfs = new HashMap<Object, EntityManagerFactory>();
		emsMap = new HashMap<Object, LinkedList<EntityManager>>();
		emMap = new HashMap<Thread, LinkedList<Map<Object, EntityManager>>>();
		txMap = new HashMap<Thread, LinkedList<EntityTransaction>>();
	}

	/**
	 * 构造方法获取超类
	 */
	public GenericDaoImpl() {
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		type = (Class) (pt.getActualTypeArguments()[0]);
	}

	/**
	 * @return type
	 * @此方法的作用：返回具体的超类
	 */
	public Class<T> getType() {
		return type;
	}

	/**
	 * @return em
	 * @此方法的作用：获取当前线程的操作对象
	 */
	@Override
	public EntityManager getEntityManager(Thread thread, boolean... bs) throws PersistenceException {
		boolean flag = false;
		try {
			if (emMap.get(thread) != null) {
				for (int i = 0, len = emMap.get(thread).size(); i < len; i++) {
					if (emMap.get(thread).get(i).get(type) != null) {
						em = emMap.get(thread).get(i).get(type);
						flag = true;
						break;
					}
				}
			}
			if (!flag) {
				if (emsMap.get(type) != null) {// 判断servlet是否已经初始化了type的连接，如果没有，先初始化
					logger.warn(type + "的连接还有：【" + emsMap.get(type).size() + "】个");
					if (emsMap.get(type).size() != 0) {// 当前连接池不为空，获取当前连接池的第一个连接，获取当前连接的事物。
						em = emsMap.get(type).get(0);// 获取链表第一个值
						emsMap.get(type).removeFirst();// 移除链表第一个值
						Map<Object, EntityManager> ems = new HashMap<Object, EntityManager>();
						ems.put(type, em);// 一个线程同一个dao只需要一个对象，所以type不会重复
						if (emMap.get(thread) != null)
							emMap.get(thread).addLast(ems);
						else {
							LinkedList<Map<Object, EntityManager>> list = new LinkedList<Map<Object, EntityManager>>();
							list.add(ems);
							emMap.put(thread, list);
						}
						Map<Object, EntityTransaction> txs = new HashMap<Object, EntityTransaction>();
						txs.put(type, em.getTransaction());// 一个线程同一个dao只需要一个对象，所以type不会重复
					} else {
						threads = Thread.getAllStackTraces();// 获取所有的堆栈线程信息
						if (bs.length == 0 && !emMap.isEmpty()) {// 查询所有线程，回收未使用的连接
							recycleEm();
							getEntityManager(thread, true);// 回收所有连接后重新获取连接
						} else {// 回收所有未使用的连接后，还是没有连接，表示当前type已使用了最大连接，获取已使用和未使用的所有连接，扩充连接，最大扩充到5个
							int count = 0;
							count = getUseEm(count);
							if (count <= MAX_CONNECTION) {
								logger.warn(type + "已使用的的连接为" + count + ",已用完，扩充连接");
								em = emfs.get(type).createEntityManager();
								emsMap.get(type).add(em);
								getEntityManager(thread, true);
							} else {
								logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + type + "已使用的的连接为" + count + ",已达默认最大数，请等待");
								throw new PersistenceException(type + "已使用的的连接为" + count + ",已达默认最大数，请等待");
							}
						}
					}
				} else {
					throw new PersistenceException("没有正确初始化" + type + "的dao层，请查询其dao层是否存在或继承是否正确");
				}
			}
		} catch (PersistenceException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			throw new PersistenceException(e.getMessage());
		}
		return em;
	}

	/**
	 * @param thread
	 *            回收资源的线程，默认为当前线程
	 * @此方法的作用：关闭连接,资源回收,通过AOP的面向切面回收，当方法退出Servic层时，默认回调方法
	 */
	public static void closeEntityManager(Thread thread) {
		try {
			if (emMap.get(thread) != null) {// 同一个线程的list的type和EntityManager集合是否为null
				for (int i = 0, len = emMap.get(thread).size(); i < len; i++) {// 遍历当前线程的list集合
					Map<Object, EntityManager> userMap = emMap.get(thread).get(i);// 获取当前线程list集合中的一个包含type的EntityManager集合
					Set<Object> keys = userMap.keySet();// 获取所有的键
					if (keys != null) {// 键不为null
						Iterator<Object> it = keys.iterator();// 迭代便利key
						Object t = null;
						while (it.hasNext()) {
							t = it.next();
							if (emsMap.get(t) != null) // 如果EntityManager工厂存在指定type的EntityManager集合，如果原始类型不存在，抛异常。
								emsMap.get(t).addLast(userMap.get(t));// 通过key获取值，把当前值放回emsMap指定位置的工厂中
							else
								throw new PersistenceException("回收" + thread + "线程的" + t + "连接异常");
						}
					}
				}
				emMap.remove(thread);// 清空当前线程的连接
			}
		} catch (PersistenceException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			throw new PersistenceException(e.getMessage());
		}
	}

	/**
	 * @param thread
	 * @此方法的作用：提交所有事物,此处不太合理
	 */
	public static void commitTransaction(Thread thread) {
		if (txMap.get(thread) != null) {
			for (int i = txMap.get(thread).size() - 1; i >= 0; i--) {
				try {
					if (txMap.get(thread).get(i) != null && txMap.get(thread).get(i).isActive())
						txMap.get(thread).get(i).commit();
				} catch (PersistenceException e) {
					logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
				}
			}
			txMap.remove(thread);
		}

	}

	/**
	 * @param thread
	 * @此方法的作用：回滚所有事物
	 */
	public static void rollBack(Thread thread) {
		if (txMap.get(thread) != null) {
			for (int i = txMap.get(thread).size() - 1; i >= 0; i--) {
				try {
					if (txMap.get(thread).get(i) != null && txMap.get(thread).get(i).isActive())
						txMap.get(thread).get(i).rollback();
				} catch (PersistenceException e) {
					logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
					throw new PersistenceException(e.getMessage());
				}
			}
			txMap.remove(thread);
		}
	}

	/**
	 * @return
	 * @此方法的作用：存储所有的事物
	 */
	private Map<Thread, LinkedList<EntityTransaction>> getAllTransaction(EntityTransaction tx) {
		if (txMap.get(Thread.currentThread()) != null) {
			txMap.get(Thread.currentThread()).addLast(tx);
		} else {
			LinkedList<EntityTransaction> list = new LinkedList<EntityTransaction>();
			list.add(tx);
			txMap.put(Thread.currentThread(), list);
		}
		return txMap;
	}

	/**
	 * @return tx
	 * @此方法的作用：获取当前线程的事物
	 */
	public EntityTransaction getEntityTransaction() {
		return em.getTransaction();
	}

	public Session getSession() {
		return (Session) em.getDelegate();
	}

	public SessionFactory getSessionFactory() {
		return getSession().getSessionFactory();
	}

	/**
	 * @return
	 * @此方法的作用：返回工厂emf
	 */
	public EntityManagerFactory getEntityManagerFactory() {
		return emfs.get(type);
	}

	/**
	 * @此方法的作用：递归回收非活动的线程连接
	 */
	private void recycleEm() {
		boolean flag = false;
		Iterator<Thread> userThread = emMap.keySet().iterator();
		Thread thread = null;
		while (userThread.hasNext()) {
			thread = userThread.next();
			if (threads.get(thread) == null) {
				closeEntityManager(thread);// 每次迭代器数据进行删除后需要从新获取迭代器，否则next指针报错
				flag = true;
				break;
			}
		}
		if (flag) {// 存在不是活动的线程占有连接，继续回调删除
			recycleEm();
		}
	}

	/**
	 * @此方法的作用：获取获得的使用连接
	 */
	private int getUseEm(int count) {
		Iterator<Thread> it = threads.keySet().iterator();
		while (it.hasNext()) {// 获取所有已在使用的线程数
			LinkedList<Map<Object, EntityManager>> list = emMap.get(it.next());
			if (list != null) {
				for (int i = 0, len = list.size(); i < len; i++) {
					if (list.get(i).get(type) != null) {
						count++;
						break;
					}
				}
			}
		}
		return count;
	}

	/**
	 * @param entity
	 * @return
	 * @此方法的作用：添加对象
	 */
	@Override
	@TriggersRemove(cacheName = "oneEhcache,twoEhcache", removeAll = true)
	public T create(final T entity) {
		getEntityManager(Thread.currentThread());
		if (em != null) {
			if (em.getTransaction() != null && !em.getTransaction().isActive()) {
				em.getTransaction().begin();
				getAllTransaction(em.getTransaction());
			}
			this.em.persist(entity);
			return entity;
		} else {
			return null;
		}
	}

	/**
	 * @param entity
	 * @此方法的作用：删除对象
	 */
	@Override
	@TriggersRemove(cacheName = "oneEhcache,twoEhcache", removeAll = true)
	public void remove(T entity) {
		getEntityManager(Thread.currentThread());
		if (em != null) {
			if (em.getTransaction() != null && !em.getTransaction().isActive()) {
				em.getTransaction().begin();
				getAllTransaction(em.getTransaction());
			}
			this.em.remove(entity);
		}
	}

	/**
	 * @param id
	 * @此方法的作用：删除信息 通过id找到对 应的实体类再执行删除方法
	 */
	@Override
	@TriggersRemove(cacheName = "oneEhcache,twoEhcache", removeAll = true)
	public void delete(final Object id) {
		getEntityManager(Thread.currentThread());
		if (em != null) {
			if (em.getTransaction() != null && !em.getTransaction().isActive()) {
				em.getTransaction().begin();
				getAllTransaction(em.getTransaction());
			}
			this.em.remove(this.em.getReference(type, id));
		}
	}

	/**
	 * @param entity
	 * @return
	 * @此方法的作用：修改信息 覆盖上一条信息 相同的不变，不同的修改
	 */
	@Override
	@TriggersRemove(cacheName = "oneEhcache,twoEhcache", removeAll = true)
	public T update(final T entity) {
		getEntityManager(Thread.currentThread());
		if (em != null) {
			if (em.getTransaction() != null && !em.getTransaction().isActive()) {
				em.getTransaction().begin();
				getAllTransaction(em.getTransaction());
			}
			return this.em.merge(entity);
		} else {
			return null;
		}
	}

	/**
	 * @param stringSql,完整的SQL
	 * @return
	 * @此方法的作用：通过SQL语句添加数据，返回添加的条数
	 */
	@Override
	@TriggersRemove(cacheName = "oneEhcache,twoEhcache", removeAll = true)
	public int addByNativeSql(String stringSql) {
		getEntityManager(Thread.currentThread());
		if (em != null) {
			if (em.getTransaction() != null && !em.getTransaction().isActive()) {
				em.getTransaction().begin();
				getAllTransaction(em.getTransaction());
			}
			Query query = this.em.createNativeQuery(stringSql);
			return query.executeUpdate();
		} else {
			return -1;
		}
	}

	/**
	 * @param sql
	 *            通过SQL对象生成的sql
	 * @return @此方法的作用： 通过SQL语句修改数据，返回修改的条数
	 */
	@Override
	@TriggersRemove(cacheName = "oneEhcache,twoEhcache", removeAll = true)
	public int updateByNativeSql(Sql sql) {
		getEntityManager(Thread.currentThread());
		if (em != null) {
			if (em.getTransaction() != null && !em.getTransaction().isActive()) {
				em.getTransaction().begin();
				getAllTransaction(em.getTransaction());
			}
			sql.setClass(type);
			Query query = this.em.createNativeQuery(sql.getSqlUpdate());
			return query.executeUpdate();
		} else {
			return -1;
		}
	}

	/**
	 * @param stringSql,完整的SQL
	 * @return
	 * @此方法的作用：通过SQL语句修改数据，返回修改的条数
	 */
	@Override
	@TriggersRemove(cacheName = "oneEhcache,twoEhcache", removeAll = true)
	public int updateByNativeSql(String stringSql) {
		getEntityManager(Thread.currentThread());
		if (em != null) {
			if (em.getTransaction() != null && !em.getTransaction().isActive()) {
				em.getTransaction().begin();
				getAllTransaction(em.getTransaction());
			}
			Query query = this.em.createNativeQuery(stringSql);
			return query.executeUpdate();
		} else {
			return -1;
		}
	}

	/**
	 * @param sql,通过SQL对象获取HQL
	 * @return
	 * @此方法的作用：通过HQL更新数据，支持批处理
	 */
	@Override
	@TriggersRemove(cacheName = "oneEhcache,twoEhcache", removeAll = true)
	public int updateBatch(Sql sql) {
		getEntityManager(Thread.currentThread());
		if (em != null) {
			if (em.getTransaction() != null && !em.getTransaction().isActive()) {
				em.getTransaction().begin();
				getAllTransaction(em.getTransaction());
			}
			sql.setClass(type);
			Query query = this.em.createQuery(sql.getHqlUpdate());
			return query.executeUpdate();
		} else {
			return -1;
		}
	}

	/**
	 * @param stringSql,完整的SQL
	 * @return
	 * @此方法的作用：通过SQL语句删除数据，返回删除的条数
	 */
	@Override
	@TriggersRemove(cacheName = "oneEhcache,twoEhcache", removeAll = true)
	public int deleteByNativeSql(String stringSql) {
		getEntityManager(Thread.currentThread());
		if (em != null) {
			if (em.getTransaction() != null && !em.getTransaction().isActive()) {
				em.getTransaction().begin();
				getAllTransaction(em.getTransaction());
			}
			Query query = this.em.createNativeQuery(stringSql);
			return query.executeUpdate();
		} else {
			return -1;
		}
	}

	/**
	 * @param sql,通过SQL对象获取SQL
	 * @return
	 * @此方法的作用：通过SQL语句删除数据，返回删除的条数
	 */
	@Override
	@TriggersRemove(cacheName = "oneEhcache,twoEhcache", removeAll = true)
	public int deleteByNativeSql(Sql sql) {
		getEntityManager(Thread.currentThread());
		if (em != null) {
			if (em.getTransaction() != null && !em.getTransaction().isActive()) {
				em.getTransaction().begin();
				getAllTransaction(em.getTransaction());
			}
			sql.setClass(type);
			Query query = this.em.createNativeQuery(sql.getSqlDelete());
			return query.executeUpdate();
		} else {
			return -1;
		}
	}

	/**
	 * @param sql,通过SQL对象获取HQL
	 * @return
	 * @此方法的作用：批量删除 HQL语句，支持批处理
	 */
	@Override
	@TriggersRemove(cacheName = "oneEhcache,twoEhcache", removeAll = true)
	public int deleteBatch(Sql sql) {
		getEntityManager(Thread.currentThread());
		if (em != null) {
			if (em.getTransaction() != null && !em.getTransaction().isActive()) {
				em.getTransaction().begin();
				getAllTransaction(em.getTransaction());
			}
			sql.setClass(type);
			Query query = this.em.createQuery(sql.getHqlDelete());
			return query.executeUpdate();
		} else {
			return -1;
		}
	}

	/**
	 * @param id
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws PersistenceException
	 * @此方法的作用：查找信息 通过id 找到对应的实体类
	 */
	@Override
	public T find(final Object id) {
		getEntityManager(Thread.currentThread());
		if (em != null) {
			return (T) this.em.find(type, id);
		} else {
			return null;
		}
	}

	/**
	 * 刷新数据库信息
	 */
	@Override
	public void refresh(final T id) {
		getEntityManager(Thread.currentThread());
		if (em != null) {
			this.em.refresh(id);
		}
	}

	/**
	 * 使用HQL语句进行查询
	 * 
	 * @param key为实体类的属性，值为对应的值
	 * @return 返回单个对象，如果对象不存在，返回null
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws Exception
	 */
	@Override
	public T find(final Map<String, Object> params) {
		final StringBuilder queryString = new StringBuilder("SELECT o FROM ");
		queryString.append(type.getSimpleName()).append(" o ");
		this.buildQueryClauses(params, queryString);
		List<T> list = this.search(queryString, 0, 1);
		return list.size() == 0 ? null : list.get(0);
	}

	/**
	 * 查询一个数据集合 int从pageStart开始取出pageSize条数据 HQL语句
	 * 
	 * @param key为实体类的属性，值为对应的值
	 * @return list对象集合
	 * @throws Exception
	 */
	@Override
	public List<T> search(final Map<String, Object> params, int pageStart, int pageSize) {
		final StringBuilder queryString = new StringBuilder("SELECT o FROM ");
		queryString.append(type.getSimpleName()).append(" o ");
		this.buildQueryClauses(params, queryString);
		return this.search(queryString, pageStart, pageSize);
	}

	/**
	 * 查询数据的数量 HQL语句
	 * 
	 * @param key为实体类的属性，值为对应的值
	 * @return long
	 * @throws Exception
	 */
	@Override
	public long count(final Map<String, Object> params) {
		final StringBuilder queryString = new StringBuilder("SELECT COUNT(o) FROM ");
		queryString.append(type.getSimpleName()).append(" o ");
		this.buildQueryClauses(params, queryString);
		return this.count(queryString);
	}

	/**
	 * 查询一个数据集合 int从pageStart开始取出pageSize条数据 HQL语句
	 * 
	 * @param stringBulid为sql语句
	 * @return list集合
	 * @throws Exception
	 */
	@Override
	public List<T> search(final StringBuilder queryString, int pageStart, int pageSize) {
		getEntityManager(Thread.currentThread());
		if (em != null) {
			final Query query = this.em.createQuery(queryString.toString());
			this.setPage(query, pageStart, pageSize);
			return (List<T>) query.setHint("org.hibernate.cacheable", true).getResultList();
		} else {
			return new ArrayList<T>();
		}
	}

	/**
	 * 查询单一的数据 HQL语句
	 * 
	 * @param stringBulid
	 * @return 数据库返回一个数字
	 * @throws Exception
	 */
	@Override
	public long count(StringBuilder queryString) {
		getEntityManager(Thread.currentThread());
		if (em != null) {
			final Query query = this.em.createQuery(queryString.toString());
			return (Long) query.getSingleResult();
		} else {
			return -1;
		}
	}

	/**
	 * 查询数据的条数 SQL语句
	 * 
	 * @param String为sql语句
	 * @return 数据库返回表数据总数Long类型
	 * @throws Exception
	 */
	@Override
	public long countByNativeSql(String sql) {
		getEntityManager(Thread.currentThread());
		if (em != null) {
			StringBuffer newsql = new StringBuffer();
			newsql.append("select count(*) from ( ");
			newsql.append(sql);
			newsql.append(") a");
			final Query query = this.em.createNativeQuery(newsql.toString());
			BigInteger bi = (BigInteger) query.getSingleResult();
			return bi.longValue();
		} else {
			return -1;
		}
	}

	/**
	 * 查询一个数据集合 int从pageStart开始取出 pageSize条数据 SQL语句
	 * 
	 * @param type
	 * @return 返回type类型的封装list数据
	 * @throws Exception
	 */
	@Override
	public List<T> searchByNativeSql(String sql, int pageStart, int pageSize) {
		getEntityManager(Thread.currentThread());
		if (em != null) {
			Query query = this.em.createNativeQuery(sql, type);
			this.setPage(query, pageStart, pageSize);
			return query.setHint("org.hibernate.cacheable", true).getResultList();
		} else {
			return new ArrayList<T>();
		}
	}

	/**
	 * 查询单一数据的值 SQL语句
	 * 
	 * @param String为sql语句
	 * @return 返回一个int类型的数字
	 * @throws Exception
	 */
	@Override
	public int getIntByNativeSQL(String sql) {
		getEntityManager(Thread.currentThread());
		if (em != null) {
			Query query = this.em.createNativeQuery(sql);
			Object object = query.getSingleResult();
			if (object == null)
				return 0;
			String result = object.toString();
			return new BigDecimal(result).intValue();
		} else {
			return -1;
		}
	}

	/**
	 * 查询单一数据的值 SQL语句
	 * 
	 * @param String为sql语句
	 * @return 返回一个Long的值
	 * @throws Exception
	 */
	@Override
	public Long getLongByNativeSQL(String sql) {
		getEntityManager(Thread.currentThread());
		if (em != null) {
			Query query = this.em.createNativeQuery(sql);
			Object object = query.getSingleResult();
			if (object == null)
				return 0L;
			String result = object.toString();
			return new BigDecimal(result).longValue();
		} else {
			return -1L;
		}
	}

	/**
	 * 查询单一数据的值 SQL语句
	 * 
	 * @param String为sql语句
	 * @return String
	 * @throws Exception
	 */
	@Override
	public String getStringByNativeSql(String sql) {
		getEntityManager(Thread.currentThread());
		if (em != null) {
			Query query = this.em.createNativeQuery(sql);
			Object object = query.getSingleResult();
			if (object != null) {
				return object.toString();
			} else {
				return "";
			}
		} else {
			return "-1";
		}

	}

	/**
	 * 保留两位有效数字 SQL语句
	 * 
	 * @param String为sql语句
	 * @return 保留两位有效数字的值
	 * @throws Exception
	 */
	@Override
	public BigDecimal getBigDecimalByNativeSql(String sql) {
		getEntityManager(Thread.currentThread());
		if (em != null) {
			Query query = this.em.createNativeQuery(sql);
			Object obj = query.getSingleResult();
			return obj == null ? BigDecimal.ZERO : new BigDecimal(String.valueOf(obj));
		} else {
			return new BigDecimal("-1");
		}
	}

	/**
	 * 查找信息 HQL语句
	 * 
	 * @param Sql
	 * @return 对象
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws Exception
	 */
	@Override
	public T find(Sql sql) {
		getEntityManager(Thread.currentThread());
		if (em != null) {
			sql.setClass(type);
			Query query = this.em.createQuery(sql.getHqlFind());
			return (T) query.getSingleResult();
		} else {
			return null;
		}
	}

	/**
	 * 查找数据的条数 HQL语句
	 * 
	 * @param Sql
	 * @return long
	 * @throws Exception
	 */
	@Override
	public long count(Sql sql) {
		getEntityManager(Thread.currentThread());
		if (em != null) {
			sql.setClass(type);
			Query query = this.em.createQuery(sql.useCount().getStatement());
			return (Long) query.getSingleResult();
		} else {
			return -1;
		}
	}

	/**
	 * 查找信息集合 HQL语句
	 * 
	 * @param Sql
	 * @return list集合
	 * @throws Exception
	 */
	@Override
	public List<T> search(Sql sql) {
		getEntityManager(Thread.currentThread());
		if (em != null) {
			sql.setClass(type);
			CacheManagerFactory.getInstance().read("oneEhcache", sql.toString());
			Query query = this.em.createQuery(sql.getHqlFind());
			this.setPage(query, sql.getBegin(), sql.getEnd());
			return (List<T>) query.setHint("org.hibernate.cacheable", true).getResultList();
		} else {
			return new ArrayList<T>();
		}
	}

	/**
	 * 创建查询sql语句 key为表字段，value为对应的值
	 * 
	 * @param map集合为条件
	 */
	public void buildQueryClauses(final Map<String, Object> params, StringBuilder queryString) {
		boolean needWhereClause = true;
		for (String key : params.keySet()) {
			if (needWhereClause) {
				queryString.append(" WHERE o.").append(key).append("='").append(params.get(key)).append("' ");
				needWhereClause = false;
			} else {
				queryString.append(" AND o.").append(key).append("='").append(params.get(key)).append("' ");
			}
		}

	}

	/**
	 * 设置开始页和数据的条数，查询到的query集合 nt pageStart开始页， int pageSize 取出的数据条数
	 * Query对象的分页方法
	 * 
	 * @param Query
	 */
	protected void setPage(final Query query, final int pageStart, final int pageSize) {
		if (pageStart >= 0) {
			query.setFirstResult(pageStart);
		}
		if (pageSize > 0) {
			query.setMaxResults(pageSize);
		}
	}
}
