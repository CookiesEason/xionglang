package com.sunxl.base.generic.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.sun.mail.iap.ConnectionException;
import com.sunxl.base.entity.ImportDataColm;
import com.sunxl.base.form.TableInfoBeanForm;
import com.sunxl.base.generic.dao.GenericDao;
import com.sunxl.base.generic.service.GenericService;
import com.sunxl.base.util.DBSqlUtil;
import com.sunxl.base.util.Sql;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn
 * @Time：2017年7月7日 @此类作用：处理所有的一般查询方法， 需要特殊查询可在子类单独实现
 *                 genericService.setGenericDao(); 的作用就是为指顶的查询方法找到指定注入的Dao,
 *                 如果有特殊的查询方法，可以在指定的子类中重写父类的查询方法。
 */
public class GenericServiceImpl<T> {
	private static final Logger logger = LoggerFactory.getLogger(GenericServiceImpl.class);
	protected GenericDao<T> genericDao;

	public GenericDao<T> getGenericDao() {
		return genericDao;
	}

	@Transactional
	public void delete(GenericService<T> genericService, Object id, Object... params) {
		genericService.setGenericDao();
		genericDao.delete(id);
	};

	/**
	 * 通过ID查找对象
	 * 
	 * @param genericService
	 * @param id
	 * @return
	 * @auto：熊浪 @Time：2017年8月2日 @此方法的作用：
	 */
	public T find(GenericService<T> genericService, Object id) {
		genericService.setGenericDao();
		return genericDao.find(id);
	}

	/**
	 * 传入Mapkey为表字段，value表字段值，返回对象
	 * 
	 * @param genericService
	 * @param map
	 * @return
	 * @auto：熊浪 @Time：2017年8月2日 @此方法的作用：
	 */
	public T find(GenericService<T> genericService, Map<String, Object> map) {
		genericService.setGenericDao();
		Sql sql = new Sql();
		if (map != null) {
			Set<String> keys = map.keySet();
			Iterator<String> it = keys.iterator();
			while (it.hasNext()) {
				String name = it.next();
				sql.add(name, map.get(name));
			}
		}
		return genericDao.find(sql);
	}

	/**
	 * 传入List<Map>key为表字段，value表字段值，返回对象
	 * 
	 * @param genericService
	 * @param list
	 * @return
	 * @auto：熊浪 @Time：2017年8月2日 @此方法的作用：
	 */
	public T find(GenericService<T> genericService, List<Map<String, Object>> list) {
		genericService.setGenericDao();
		Sql sql = new Sql();
		if (list != null) {
			for (Map<String, Object> map : list) {
				Set<String> keys = map.keySet();
				Iterator<String> it = keys.iterator();
				while (it.hasNext()) {
					String name = it.next();
					sql.add(name, map.get(name));
				}
			}
		}
		return genericDao.find(sql);
	}

	/**
	 * @param genericService
	 * @param sql
	 * @return
	 * @auto：熊浪 @Time：2017年8月2日
	 * @此方法的作用：通过查询HQL找到对象
	 */
	public T find(GenericService<T> genericService, Sql sql) {
		genericService.setGenericDao();
		return genericDao.find(sql);
	}

	/**
	 * @param genericService
	 * @param sql
	 * @return
	 * @auto：熊浪 @Time：2017年8月2日
	 * @此方法的作用：通过HQL查找数据总数
	 */
	public long count(GenericService<T> genericService, Sql sql) {
		genericService.setGenericDao();
		return genericDao.count(sql);
	}

	/**
	 * @param genericService
	 * @param sql
	 * @return：list集合
	 * @auto：熊浪 @Time：2017年8月2日
	 * @此方法的作用：通过HQL查找数据
	 */
	public List<T> search(GenericService<T> genericService, Sql sql) {
		genericService.setGenericDao();
		return genericDao.search(sql);
	}

	/**
	 * @param genericService
	 * @param params
	 * @return
	 * @auto：熊浪 @Time：2017年8月2日
	 * @此方法的作用：通过传入map参数返回数据总数HQL
	 */
	public long count(GenericService<T> genericService, Map<String, Object> params) {
		genericService.setGenericDao();
		return genericDao.count(params);
	}

	/**
	 * @param genericService
	 * @param params
	 * @param pageStart
	 * @param pageSize
	 * @return:返回list集合
	 * @auto：熊浪 @Time：2017年8月2日
	 * @此方法的作用：通过传入map参数起始数据，分页每页数据HQL
	 */
	public List<T> search(GenericService<T> genericService, Map<String, Object> params, int pageStart, int pageSize) {
		genericService.setGenericDao();
		return genericDao.search(params, pageStart, pageSize);
	}

	/**
	 * @param genericService
	 * @param queryString
	 * @return 不建议自己拼装HQL
	 * @auto：熊浪 @Time：2017年8月2日
	 * @此方法的作用：通过HQL查询总数 SELECT COUNT(o) FROM clas.getSimpleName() o 加上条件
	 */
	@Deprecated
	public long count(GenericService<T> genericService, StringBuilder queryString) {
		genericService.setGenericDao();
		return genericDao.count(queryString);
	}

	/**
	 * @param genericService
	 * @param queryString
	 * @param pageStart
	 * @param pageSize
	 * @return:当返回的字段不需要全部时
	 * @auto：熊浪 @Time：2017年8月2日
	 * @此方法的作用：通过HQL参数，分页起始页，分页条数SELECT o.类属性 FROM clas.getSimpleName() o 加上条件
	 */
	public List<T> search(GenericService<T> genericService, StringBuilder queryString, int pageStart, int pageSize) {
		genericService.setGenericDao();
		return genericDao.search(queryString, pageStart, pageSize);
	}

	/**
	 * @param genericService
	 * @param sql
	 * @return
	 * @auto：熊浪 @Time：2017年8月2日 @此方法的作用：通过SQL返回条数(可以是某字段的某数字)
	 */
	public long countByNativeSql(GenericService<T> genericService, String sql) {
		genericService.setGenericDao();
		return genericDao.countByNativeSql(sql);
	}

	/**
	 * @param genericService
	 * @param sql
	 * @param pageStart
	 * @param pageSize
	 * @return:list集合
	 * @auto：熊浪 @Time：2017年8月2日
	 * @此方法的作用：通过sql，分页起始页，分页条数查询，此方法需要用到T，所以只能是对应的service方法调用
	 */
	public List<T> searchByNativeSql(GenericService<T> genericService, String sql, int pageStart, int pageSize) {
		genericService.setGenericDao();
		return genericDao.searchByNativeSql(sql, pageStart, pageSize);
	}

	/**
	 * @param genericService
	 * @param sql
	 * @return
	 * @auto：熊浪 @Time：2017年8月2日 @此方法的作用：通过传入SQL返回条数(可以是某字段的某数字)
	 */
	public int getIntByNativeSQL(GenericService<T> genericService, String sql) {
		genericService.setGenericDao();
		return genericDao.getIntByNativeSQL(sql);
	}

	/**
	 * @param genericService
	 * @param sql
	 * @return
	 * @auto：熊浪 @Time：2017年8月2日 @此方法的作用：通过传入SQL返回数据条数(可以是某字段的某数字)
	 */
	public Long getLongByNativeSQL(GenericService<T> genericService, String sql) {
		genericService.setGenericDao();
		return genericDao.getLongByNativeSQL(sql);
	}

	/**
	 * @param genericService
	 * @param sql
	 * @return
	 * @auto：熊浪 @Time：2017年8月2日 @此方法的作用：通过传入SQL返回条数(可以是某字段的某数字)
	 */
	public BigDecimal getBigDecimalByNativeSql(GenericService<T> genericService, String sql) {
		genericService.setGenericDao();
		return genericDao.getBigDecimalByNativeSql(sql);
	}

	/**
	 * @param genericService
	 * @param sql
	 * @return
	 * @auto：熊浪 @Time：2017年8月2日
	 * @此方法的作用：通过传入SQL返回某字段的某字符串
	 */
	public String getStringByNativeSql(GenericService<T> genericService, String sql) {
		genericService.setGenericDao();
		return genericDao.getStringByNativeSql(sql);
	}

	/**
	 * @param genericService
	 * @param sql
	 * @auto：熊浪 @Time：2017年8月2日 @此方法的作用： 通过传入SQL修改表，需要用到T，只能指定的Service
	 */
	@Transactional
	public int updateByNativeHql(GenericService<T> genericService, Sql sql) {
		genericService.setGenericDao();
		return genericDao.updateBatch(sql);
	}

	/**
	 * @param genericService
	 * @param sql
	 * @auto：熊浪 @Time：2017年8月2日 @此方法的作用： 通过传入SQL修改表，需要用到T，只能指定的Service
	 */
	@Transactional
	public int deleteByNativeHql(GenericService<T> genericService, Sql sql) {
		genericService.setGenericDao();
		return genericDao.deleteBatch(sql);
	}

	/**
	 * @param genericService
	 * @param sql
	 * @auto：熊浪 @Time：2017年8月2日 @此方法的作用： 通过传入SQL修改表，需要用到T，只能指定的Service
	 */
	@Transactional
	public int updateByNativeSql(GenericService<T> genericService, Sql sql) {
		genericService.setGenericDao();
		return genericDao.updateByNativeSql(sql);
	}

	/**
	 * @param genericService
	 * @param sql
	 * @auto：熊浪 @Time：2017年8月2日 @此方法的作用： 通过传入SQL修改表，需要用到T，只能指定的Service
	 */
	@Transactional
	public int deleteByNativeSql(GenericService<T> genericService, Sql sql) {
		genericService.setGenericDao();
		return genericDao.deleteByNativeSql(sql);
	}

	/**
	 * @param genericService
	 * @param sql
	 * @auto：熊浪 @Time：2017年8月2日 @此方法的作用： 通过传入SQL修改表，需要用到T，只能指定的Service
	 */
	@Transactional
	public int updateByNativeSql(GenericService<T> genericService, String stringSql) {
		genericService.setGenericDao();
		return genericDao.updateByNativeSql(stringSql);
	}

	/**
	 * @param genericService
	 * @param sql
	 * @auto：熊浪 @Time：2017年8月2日 @此方法的作用： 通过传入SQL修改表，需要用到T，只能指定的Service
	 */
	@Transactional
	public int deleteByNativeSql(GenericService<T> genericService, String stringSql) {
		genericService.setGenericDao();
		return genericDao.deleteByNativeSql(stringSql);
	}

	/**
	 * @param sql
	 * @return
	 * @throws ConnectionException
	 * @throws SQLException
	 * @auto：熊浪 @Time：2017年8月2日
	 * @此方法的作用：根据sql语句删除，需要连接池
	 */
	public boolean delete(String sql) throws ConnectionException, SQLException {
		try {
			return DBSqlUtil.delete(sql);
		} catch (SQLException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			throw new SQLException(e.getMessage());
		}
	};

	/**
	 * @param genericService
	 * @param sql
	 * @return
	 * @auto：熊浪 @Time：2017年8月2日
	 * @此方法的作用：根据HQL语句删除
	 */
	@Transactional
	public int deleteBatch(GenericService<T> genericService, Sql sql) {
		genericService.setGenericDao();
		return genericDao.deleteBatch(sql);
	};

	/**
	 * @param sql
	 * @return
	 * @throws ConnectionException
	 * @throws SQLException
	 * @auto：熊浪 @Time：2017年8月2日
	 * @此方法的作用：根据sql修改，需要连接池
	 */
	public boolean update(String sql) throws ConnectionException, SQLException {
		try {
			return DBSqlUtil.update(sql);
		} catch (SQLException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			throw new SQLException(e.getMessage());
		}
	};

	/**
	 * @param genericService
	 * @param sql
	 * @return
	 * @auto：熊浪 @Time：2017年8月2日
	 * @此方法的作用：通过HQL语句修改
	 */
	@Transactional
	public int updateBatch(GenericService<T> genericService, Sql sql) {
		genericService.setGenericDao();
		return genericDao.updateBatch(sql);
	};

	/**
	 * @param sql
	 * @return
	 * @throws ConnectionException
	 * @throws SQLException
	 * @auto：熊浪 @Time：2017年8月2日
	 * @此方法的作用：根据sql语句添加，需要连接池
	 */
	public boolean add(String sql) throws ConnectionException, SQLException {
		try {
			return DBSqlUtil.add(sql);
		} catch (SQLException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			throw new SQLException(e.getMessage());
		}
	};

	/**
	 * @param tableName
	 *            表名
	 * @param values
	 *            批添加的值
	 * @param colmnList
	 *            字段信息(通过查询字段表获得)
	 * @auto：熊浪 @Time：2017年8月14日
	 * @此方法的作用：批添加数据
	 */
	public boolean insertBatch(String tableName, List<List<String>> values, List<ImportDataColm> colmnList) throws SQLException, ConnectionException {
		try {
			TableInfoBeanForm tbInfo = new TableInfoBeanForm(tableName);
			{
				return DBSqlUtil.insertBatch(tbInfo, values, colmnList);
			}
		} catch (SQLException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			throw new SQLException(e.getMessage());
		}
	}
}
