package com.sunxl.base.generic.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.sun.mail.iap.ConnectionException;
import com.sunxl.base.entity.ImportDataColm;
import com.sunxl.base.util.Sql;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn
 * @Time：2017年7月7日 @此类作用：增删改未放入共有servcie类中
 */
public interface GenericService<T> {
	void setGenericDao();

	T create(T entity, Object... params);

	void delete(T entity, Object... params);

	void delete(GenericService<T> genericService, Object id, Object... params);

	T update(T entity, Object... params);

	T find(GenericService<T> genericService, Object id);

	T find(GenericService<T> genericService, Map<String, Object> map);

	T find(GenericService<T> genericService, List<Map<String, Object>> list);

	T find(GenericService<T> genericService, Sql sql);

	long count(GenericService<T> genericService, Sql sql);

	List<T> search(GenericService<T> genericService, Sql sql);

	long count(GenericService<T> genericService, Map<String, Object> params);

	List<T> search(GenericService<T> genericService, Map<String, Object> params, int pageStart, int pageSize);

	long count(GenericService<T> genericService, StringBuilder queryString);

	List<T> search(GenericService<T> genericService, StringBuilder queryString, int pageStart, int pageSize);

	long countByNativeSql(GenericService<T> genericService, String sql);

	List<T> searchByNativeSql(GenericService<T> genericService, String sql, int pageStart, int pageSize);

	int getIntByNativeSQL(GenericService<T> genericService, String sql);

	Long getLongByNativeSQL(GenericService<T> genericService, String sql);

	BigDecimal getBigDecimalByNativeSql(GenericService<T> genericService, String sql);

	String getStringByNativeSql(GenericService<T> genericService, String stringSql);
	
	int updateByNativeHql(GenericService<T> genericService, Sql sql);
	
	int deleteByNativeHql(GenericService<T> genericService, Sql sql);

	int updateByNativeSql(GenericService<T> genericService, Sql sql);
	
	int deleteByNativeSql(GenericService<T> genericService, Sql sql);
	
	int updateByNativeSql(GenericService<T> genericService,  String stringSql);
	
	int deleteByNativeSql(GenericService<T> genericService,  String stringSql);

	boolean delete(String sql) throws ConnectionException, SQLException;

	int deleteBatch(GenericService<T> genericService, Sql sql);

	boolean update(String sql) throws ConnectionException, SQLException;

	int updateBatch(GenericService<T> genericService, Sql sql);

	boolean add(String sql) throws ConnectionException, SQLException;

	boolean insertBatch(String tableName, List<List<String>> values, List<ImportDataColm> colmnList) throws SQLException, ConnectionException;
}
