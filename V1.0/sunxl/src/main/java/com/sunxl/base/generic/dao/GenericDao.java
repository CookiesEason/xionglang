package com.sunxl.base.generic.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.sunxl.base.util.Sql;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn @Time：2017年7月7日 @此类作用：
 */
public interface GenericDao<T> {
	T create(T entity);

	void delete(Object id);

	void remove(T t);

	T find(Object id);

	T find(Map<String, Object> params);

	void refresh(T id);

	T update(T entity);

	T find(Sql sql);

	long count(Sql sql);

	List<T> search(Sql sql);

	long count(Map<String, Object> params);

	List<T> search(Map<String, Object> params, int pageStart, int pageSize);

	long count(StringBuilder queryString);

	List<T> search(StringBuilder queryString, int pageStart, int pageSize);

	long countByNativeSql(String sql);

	List<T> searchByNativeSql(String sql, int pageStart, int pageSize);

	int getIntByNativeSQL(String sql);

	Long getLongByNativeSQL(String sql);

	EntityManager getEntityManager();

	BigDecimal getBigDecimalByNativeSql(String sql);

	String getStringByNativeSql(String sql);

	int addByNativeSql(String stringSql);
	
	int updateByNativeSql(String stringSql);

	int deleteByNativeSql(String stringSql);

	int updateByNativeSql(Sql sql);

	int deleteByNativeSql(Sql sql);

	int deleteBatch(Sql sql);

	int updateBatch(Sql sql);

}
