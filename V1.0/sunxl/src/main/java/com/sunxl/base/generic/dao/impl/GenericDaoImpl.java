package com.sunxl.base.generic.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunxl.base.generic.dao.GenericDao;
import com.sunxl.base.util.Sql;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn
 * @Time：2017年7月7日 @此类作用：提供公共执行的dao层
 * @注意：分清String是通过HQL还是SQL执行的语句createQuery执行HQL，createNativeQuery执行SQL，使用Sql对象一般为HQL
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class GenericDaoImpl<T> implements GenericDao<T> {
	private static final Logger logger = LoggerFactory.getLogger(GenericDaoImpl.class);

	protected EntityManagerFactory emf;

	protected EntityManager em;

	protected Class<T> type;

	/**
	 * 获取对应的class类型
	 * 
	 * @return
	 * @auto：熊浪 @Time：2017年8月1日 @此方法的作用：
	 */
	public Class<T> getType() {
		return type;
	}

	/**
	 * 获取对应的EntityManager对象
	 */
	public EntityManager getEntityManager() {
		return em;
	}

	/**
	 * 获取对应的EntityManagerFactory工厂
	 * 
	 * @return
	 * @auto：熊浪 @Time：2017年8月1日 @此方法的作用：
	 */
	public EntityManagerFactory getEntityManagerFactory() {
		return emf;
	}

	/**
	 * 获取对应的实体类
	 */
	public GenericDaoImpl() {
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		type = (Class) (pt.getActualTypeArguments()[0]);
	}

	/**
	 * 添加对象
	 * 
	 * @param 对象
	 * @return 对象
	 */
	@Override
	public T create(final T entity) {
		this.em.persist(entity);
		return entity;
	}

	/**
	 * 删除信息
	 * 
	 * @param 迭代的删除对象
	 */
	@Override
	public void remove(T t) {
		this.em.remove(t);
	}

	/**
	 * 删除信息 通过id找到对 应的实体类再执行删除方法
	 * 
	 * @param id
	 */
	@Override
	public void delete(final Object id) {
		this.em.remove(this.em.getReference(type, id));
	}

	/**
	 * 查找信息 通过id 找到对应的实体类
	 * 
	 * @param id
	 * @return 对象
	 */
	@Override
	public T find(final Object id) {
		return (T) this.em.find(type, id);
	}

	/**
	 * 查找信息（优雅的查询方法） 通过id找到对应的实体类
	 * 
	 * @param id
	 */
	public void refresh(final T id) {
		this.em.refresh(id);
	}

	/**
	 * 修改信息 覆盖上一条信息 相同的不变，不同的修改
	 * 
	 * @param 实体类
	 * @return 对应的实体类
	 */
	@Override
	public T update(final T entity) {
		return this.em.merge(entity);
	}

	/**
	 * 使用HQL语句进行查询
	 * 
	 * @param key为实体类的属性，值为对应的值
	 * @return 返回单个对象，如果对象不存在，返回null
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
	 */
	@Override
	public List<T> search(final StringBuilder queryString, int pageStart, int pageSize) {
		final Query query = this.em.createQuery(queryString.toString());
		this.setPage(query, pageStart, pageSize);
		return (List<T>) query.getResultList();
	}

	/**
	 * 查询单一的数据 HQL语句
	 * 
	 * @param stringBulid
	 * @return 数据库返回一个数字
	 */
	@Override
	public long count(StringBuilder queryString) {
		final Query query = this.em.createQuery(queryString.toString());
		return (Long) query.getSingleResult();
	}

	/**
	 * 查询数据的条数 SQL语句
	 * 
	 * @param String为sql语句
	 * @return 数据库返回表数据总数Long类型
	 */
	@Override
	public long countByNativeSql(String sql) {
		StringBuffer newsql = new StringBuffer();
		newsql.append("select count(*) from ( ");
		newsql.append(sql);
		newsql.append(") a");
		final Query query = this.em.createNativeQuery(newsql.toString());
		BigInteger bi = (BigInteger) query.getSingleResult();
		return bi.longValue();
	}

	/**
	 * 查询一个数据集合 int从pageStart开始取出 pageSize条数据 SQL语句
	 * 
	 * @param type
	 * @return 返回type类型的封装list数据
	 */
	@Override
	public List<T> searchByNativeSql(String sql, int pageStart, int pageSize) {
		Query query = this.em.createNativeQuery(sql, type);
		this.setPage(query, pageStart, pageSize);
		return query.getResultList();
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
	 * 查询单一数据的值 SQL语句
	 * 
	 * @param String为sql语句
	 * @return 返回一个int类型的数字
	 */
	@Override
	public int getIntByNativeSQL(String sql) {
		Query query = this.em.createNativeQuery(sql);
		Object object = query.getSingleResult();
		if (object == null)
			return 0;
		String result = object.toString();
		return new BigDecimal(result).intValue();
	}

	/**
	 * 查询单一数据的值 SQL语句
	 * 
	 * @param String为sql语句
	 * @return 返回一个Long的值
	 */
	@Override
	public Long getLongByNativeSQL(String sql) {
		Query query = this.em.createNativeQuery(sql);
		Object object = query.getSingleResult();
		if (object == null)
			return null;
		String result = object.toString();
		return new BigDecimal(result).longValue();
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

	/**
	 * 查询单一数据的值 SQL语句
	 * 
	 * @param String为sql语句
	 * @return String
	 */
	@Override
	public String getStringByNativeSql(String sql) {
		Query query = this.em.createNativeQuery(sql);
		try {
			Object object = query.getSingleResult();
			if (object != null) {
				return object.toString();
			} else {
				return null;
			}
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * 保留两位有效数字 SQL语句
	 * 
	 * @param String为sql语句
	 * @return 保留两位有效数字的值
	 */
	@Override
	public BigDecimal getBigDecimalByNativeSql(String sql) {
		Query query = this.em.createNativeQuery(sql);
		Object obj = query.getSingleResult();
		return obj == null ? BigDecimal.ZERO : new BigDecimal(String.valueOf(obj));
	}

	/**
	 * 查找信息 HQL语句
	 * 
	 * @param Sql
	 * @return 对象
	 */
	@Override
	public T find(Sql sql) {
		sql.setClass(type);
		Query query = this.em.createQuery(sql.getFind());
		Object obj = null;
		try {
			obj = query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return (T) obj;
	}

	/**
	 * 查找数据的条数 HQL语句
	 * 
	 * @param Sql
	 * @return long
	 */
	@Override
	public long count(Sql sql) {
		sql.setClass(type);
		Query query = this.em.createQuery(sql.useCount().getStatement());
		return (Long) query.getSingleResult();
	}

	/**
	 * 查找信息集合 HQL语句
	 * 
	 * @param Sql
	 * @return list集合
	 */
	@Override
	public List<T> search(Sql sql) {
		sql.setClass(type);
		Query query = this.em.createQuery(sql.getFind());
		this.setPage(query, sql.getBegin(), sql.getEnd());
		return (List<T>) query.getResultList();
	}

	/**
	 * 返回修改信息的条数 SQL语句
	 * 
	 * @param String为sql语句
	 * @return int
	 */
	@Override
	public int updateByNativeSql(String sql) {
		Query query = this.em.createNativeQuery(sql);
		try {
			return query.executeUpdate();
		} catch (Exception e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			return -1;
		}
	}

	/**
	 * 批量删除 HQL语句
	 */
	@Override
	public int deleteBatch(Sql sql) {
		sql.setClass(type);
		Query query = this.em.createQuery(sql.getDelete());
		return query.executeUpdate();
	}

	/**
	 * 批量更新 HQL语句
	 */
	@Override
	public int updateBatch(Sql sql) {
		sql.setClass(type);
		Query query = this.em.createQuery(sql.getUpdate());
		return query.executeUpdate();
	}
}
