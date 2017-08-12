package com.sunxl.base.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.mail.iap.ConnectionException;
import com.sunxl.base.entity.ImportDataColm;
import com.sunxl.base.form.ColumnBeanForm;
import com.sunxl.base.form.TableInfoBeanForm;

import oracle.sql.DATE;
import oracle.sql.TIMESTAMP;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年6月27日
 * @此类作用jpa操作的时对象，根据ID
 */
@SuppressWarnings({ "unchecked" })
public class DBSqlUtil {
	private static final Logger logger = LoggerFactory.getLogger(DBSqlUtil.class);

	/**
	 * @param sql预编译的sql
	 * @param params填充的参数值
	 * @throws SQLException
	 * @auto：熊浪
	 * @Time：2017年8月11日 @此方法的作用：修改
	 */
	public static boolean update(String sql, String... params) throws ConnectionException, SQLException {
		try {
			return DBSqlUtil.executeObject(sql, params);
		} catch (SQLException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			throw new SQLException(e.getMessage());
		}
	}

	/**
	 * @param sql预编译的sql
	 * @param params填充的参数值
	 * @throws SQLException
	 * @auto：熊浪
	 * @Time：2017年8月11日 @此方法的作用：删除
	 */
	public static boolean delete(String sql, String... params) throws ConnectionException, SQLException {
		try {
			return DBSqlUtil.executeObject(sql, params);
		} catch (SQLException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			throw new SQLException(e.getMessage());
		}
	}

	/**
	 * @param sql预编译的sql
	 * @param params填充的参数值
	 * @throws SQLException
	 * @auto：熊浪
	 * @Time：2017年8月11日 @此方法的作用：添加
	 */
	public static boolean add(String sql, String... params) throws ConnectionException, SQLException {
		try {
			return DBSqlUtil.executeObject(sql, params);
		} catch (SQLException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			throw new SQLException(e.getMessage());
		}
	}

	/**
	 * @auto：熊浪
	 * @Time：2017年8月11日 @此方法的作用：增删改执行Sql语句
	 */
	private static boolean executeObject(String sql, String... params) throws SQLException, ConnectionException {
		boolean flag = false;
		PreparedStatement pst = null;
		try {
			pst = SunxlConnectionFactory.getCurrentConnection().prepareStatement(sql);
			if (params != null) {
				int index = 1;
				for (Object param : params) {
					pst.setObject(index++, param);
				}
			}
			flag = pst.execute();
		} catch (SQLException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getStackTrace());
			throw new SQLException(e.getMessage());
		} finally {
			SunxlConnectionFactory.closeUserConnection(pst);
		}
		return flag;
	}

	/**
	 * @throws ConnectionException
	 * @auto：熊浪
	 * @Time：2017年8月9日 @此方法的作用：可以返回基本类型的包装类，之后强转如Integer
	 */
	public static <T> T getObject(Class<?> type, String sql, Object... params) throws ConnectionException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = SunxlConnectionFactory.getCurrentConnection().prepareStatement(sql);
			if (params != null) {
				int index = 1;
				for (Object param : params) {
					pst.setObject(index++, param);
				}
			}
			rs = pst.executeQuery();
			if (rs.next()) {
				return resultToClass(type, rs);
			}
		} catch (SQLException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
		} finally {
			SunxlConnectionFactory.closeUserConnection(pst, rs);
		}
		return null;
	}

	/**
	 * @throws ConnectionException
	 * @auto：熊浪
	 * @Time：2017年8月9日 @此方法的作用：可以返回基本类型的包装类，之后强转如List<Integer>
	 */
	public static <T> List<T> getObjectList(Class<?> type, String sql, Object... params) throws ConnectionException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<T> list = new ArrayList<T>();
		try {
			pst = SunxlConnectionFactory.getCurrentConnection().prepareStatement(sql);
			if (params != null) {
				int index = 1;
				for (Object param : params) {
					pst.setObject(index++, param);
				}
			}
			rs = pst.executeQuery();
			while (rs.next()) {
				list.add((T) resultToClass(type, rs));
			}
		} catch (SQLException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
		} finally {
			SunxlConnectionFactory.closeUserConnection(pst, rs);
		}
		return list;
	}

	/**
	 * 把结果转换为一个对象
	 * 
	 * @param rs
	 * @return
	 * @auto：熊浪
	 * @Time：2017年8月2日 @此方法的作用：没有外键干扰
	 */
	private static <T> T resultToClass(Class<?> type, ResultSet rs) {
		try {
			String methodName = type.getName().substring(type.getName().trim().lastIndexOf(".") + 1, type.getName().trim().length()).toUpperCase();
			if ("STRING,INTEGER,BOOLEAN,FLOOR,DOUBLE,TIMESTAMP".contains(methodName)) {
				return (T) (rs.getObject(1));
			} else {
				T obj = (T) type.newInstance();
				Method[] methods = type.getMethods();
				for (Method method : methods) {
					methodName = method.getName();
					if (methodName.startsWith("set")) {
						Class<?>[] types = method.getParameterTypes();
						if (types.length == 1) {
							methodName = methodName.substring(3, methodName.length());
							if (String.class.equals(types[0]) || char.class.equals(types[0]) || Character.class.equals(types[0])) {
								String value = rs.getString(methodName);
								method.invoke(obj, value);
							} else if (boolean.class.equals(types[0])) {
								boolean value = rs.getBoolean(methodName);
								method.invoke(obj, value);
							} else if (Date.class.equals(types[0])) {
								Object value = rs.getObject(methodName);
								if (value instanceof TIMESTAMP) {
									TIMESTAMP tm = (TIMESTAMP) value;
									method.invoke(obj, tm.timestampValue());
								} else if (value instanceof DATE) {
									DATE tm = (DATE) value;
									method.invoke(obj, tm.dateValue());
								} else {
									method.invoke(obj, (Date) value);
								}
							} else if (Boolean.class.equals(types[0]) || boolean.class.equals(types[0])) {
								boolean value = rs.getBoolean(methodName);
								method.invoke(obj, value);
							} else if (Long.class.equals(types[0]) || long.class.equals(types[0])) {
								long value = rs.getLong(methodName);
								method.invoke(obj, value);
							} else if (Float.class.equals(types[0]) || float.class.equals(types[0])) {
								float value = rs.getFloat(methodName);
								method.invoke(obj, value);
							} else if (Double.class.equals(types[0]) || double.class.equals(types[0])) {
								double value = rs.getDouble(methodName);
								method.invoke(obj, value);
							} else if (Short.class.equals(types[0]) || short.class.equals(types[0])) {
								short value = rs.getShort(methodName);
								method.invoke(obj, value);
							} else if (Byte.class.equals(types[0]) || byte.class.equals(types[0])) {
								byte value = rs.getByte(methodName);
								method.invoke(obj, value);
							} else {
								method.invoke(obj, rs.getInt(methodName));
							}
						}
					}
				}
				return obj;
			}
		} catch (SQLException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
		} catch (InstantiationException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
		} catch (IllegalAccessException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
		} catch (InvocationTargetException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
		}
		return null;
	}

	/**
	 * 结果转换为对象
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 * @auto：熊浪
	 * @Time：2017年8月2日 @此方法的作用：没有外键干扰
	 */
	public static <T> T resultSetToObject(Class<?> type, ResultSet rs) throws SQLException {
		try {
			ResultSetMetaData metaData = rs.getMetaData();
			int count = metaData.getColumnCount();
			Object obj = type.newInstance();
			for (int i = 1; i <= count; i++) {
				int colmtype = metaData.getColumnType(i);
				String colName = metaData.getColumnLabel(i).toLowerCase();
				Method[] methods = type.getMethods();
				for (Method method : methods) {
					String methodName = method.getName();
					if (methodName.startsWith("set")) {
						if (methodName.toLowerCase().endsWith(colName)) {
							switch (colmtype) {
							case Types.DATE:
								java.sql.Date date = rs.getDate(i);
								method.invoke(obj, new Date(date.getTime()));
								break;
							case Types.TIMESTAMP:
								Timestamp timestamp = rs.getTimestamp(i);
								method.invoke(obj, new Date(timestamp.getTime()));
								break;
							case Types.VARCHAR:
								method.invoke(obj, rs.getString(i));
								break;
							case Types.CHAR:
								method.invoke(obj, rs.getString(i));
								break;
							case Types.NUMERIC:
								method.invoke(obj, rs.getInt(i));
								break;
							case Types.INTEGER:
								method.invoke(obj, rs.getInt(i));
								break;
							default:
								method.invoke(obj, rs.getObject(i));
								break;
							}
							break;
						}
					}
				}
			}
			return (T) obj;
		} catch (InstantiationException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
		} catch (IllegalAccessException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
		} catch (InvocationTargetException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
		}
		return null;
	}

	/**
	 * 拼接pst
	 * 
	 * @param pst
	 * @param colmList
	 * @param fields
	 * @param tbInfo
	 * @return
	 * @throws SQLException
	 * @auto：熊浪 @Time：2017年8月9日 @此方法的作用：
	 */
	private static String executeAddBatchObject(PreparedStatement pst, List<ImportDataColm> colmList, List<String> fields, TableInfoBeanForm tbInfo) throws SQLException {
		String exceptionMsg = "";
		if (fields.size() != colmList.size())
			exceptionMsg = "数据内容：" + ReadInfoUtil.replaceLowOrderASCIICharacters(Arrays.toString(fields.toArray())) + ",数据序列个数[" + fields.size() + "]与导数配置字段个数[" + colmList.size() + "]不相同";
		else {
			String coluna = "字段:[";
			String value = "数据值:[";
			for (int i = 0, len = fields.size(); i < len; i++) {
				// 根据colm的类型来设置参数
				String val = fields.get(i);
				String defaval = colmList.get(i).getDefava();// 顺序必须跟给定的数据顺序一致
				if (StringUtil.isEmpty(val)) {
					if (StringUtil.isNotEmpty(defaval))
						val = defaval;
				}
				setStatement(pst, colmList.get(i).getColuna(), i + 1, val, tbInfo);
				if (i < fields.size() - 1) {
					coluna += colmList.get(i).getColuna() + ",";
					value += val + ",";
				} else {
					coluna += colmList.get(i).getColuna() + "],";
					value += val + "],";
				}
			}
			exceptionMsg = value + coluna;
			try {
				pst.addBatch();
			} catch (SQLException e) {
				logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
				throw new SQLException(pst + "addBatch出错" + e.getMessage());
			}
		}
		return exceptionMsg;
	}

	/**
	 * 根据指定的类型拼接pst
	 * 
	 * @param pst
	 * @param columnName
	 * @param idx
	 * @param val
	 * @param tbInfo
	 * @throws SQLException
	 * @auto：熊浪 @Time：2017年8月9日 @此方法的作用：
	 */
	private static void setStatement(PreparedStatement pst, String columnName, int idx, String val, TableInfoBeanForm tbInfo) throws SQLException {
		String colmType = "";
		try {
			colmType = getColumnType(columnName, tbInfo);
		} catch (SQLException e1) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e1.getMessage());
			throw new SQLException(e1.getMessage());
		}
		if (colmType.indexOf("INT") >= 0 || colmType.indexOf("INTEGER") >= 0) {
			try {
				if (val == null || "".equals(val))
					pst.setNull(idx, java.sql.Types.INTEGER);
				else
					pst.setInt(idx, Integer.parseInt(val));
			} catch (SQLException e) {
				logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			}
		} else if (colmType.indexOf("CHAR") >= 0) {
			try {
				pst.setString(idx, val);
			} catch (SQLException e) {
				logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			}
		} else if (colmType.indexOf("VARCHAR") >= 0) {
			try {
				pst.setString(idx, val);
			} catch (SQLException e) {
				logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			}
		} else if (colmType.indexOf("TIMESTAMP") >= 0) {
			long time = DatetimeUtil.changeToDate(val, DatetimeUtil.TIME_FORMAT_Y_M_D).getTime();
			try {
				pst.setTimestamp(idx, new Timestamp(time));
			} catch (SQLException e) {
				logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			}
		} else if (colmType.indexOf("CLOB") >= 0) {
			try {
				pst.setString(idx, val);
			} catch (SQLException e) {
				logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			}
		} else if (colmType.indexOf("NUMBER") >= 0) {
			try {
				if (val == null || val.equals("")) {
					pst.setNull(idx, java.sql.Types.NUMERIC);
				} else {
					BigDecimal bigDecimal = new BigDecimal(val);
					pst.setBigDecimal(idx, bigDecimal);
				}
			} catch (SQLException e) {
				logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			}
		} else if (colmType.indexOf("BOOLEAN") >= 0) {
			try {
				pst.setBoolean(idx, Boolean.valueOf(val));
			} catch (SQLException e) {
				logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			}
		} else if (colmType.indexOf("DATE") >= 0) {
			long time = DatetimeUtil.changeToDate(val, DatetimeUtil.TIME_FORMAT_Y_M_D).getTime();
			try {
				pst.setDate(idx, new java.sql.Date(time));
			} catch (SQLException e) {
				logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			}
		} else if (colmType.indexOf("DECIMAL") >= 0) {
			try {
				pst.setBigDecimal(idx, new BigDecimal(val));
			} catch (SQLException e) {
				logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			}
		} else {
			try {
				pst.setString(idx, val);
			} catch (SQLException e) {
				logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			}
		}
	}

	/**
	 * @param columnName字段名
	 * @param tbInfo初始化信息数据
	 * @param conn连接池
	 * @param tbName表名
	 * @return
	 * @throws SQLException
	 * @auto：熊浪 @Time：2017年8月8日
	 * @此方法的作用：获取对应字段的数据类型
	 */
	private static String getColumnType(String columnName, TableInfoBeanForm tbInfo) throws SQLException {
		List<ColumnBeanForm> columnList = tbInfo.getColumnList();
		for (Iterator<ColumnBeanForm> iterator = columnList.iterator(); iterator.hasNext();) {
			ColumnBeanForm columnBean = (ColumnBeanForm) iterator.next();
			if (columnBean.getColumnName().equalsIgnoreCase(columnName)) {
				return columnBean.getSqlType();
			}
		}
		return "";
	}

	/**
	 * @param tbName表名
	 * @param type需要拼接的sql为1：增、2：删、3：改中的哪一种
	 * @param values改的set字段值
	 * @param columnswhere条件和insert的添加字段
	 * @auto：熊浪
	 * @Time：2017年8月11日 @此方法的作用：根据提供的列集合创建添加Sql，最简单的增删改
	 */
	public static String getStringSql(String tbName, int type, String[] values, String... columns) {
		StringBuffer sbOne = new StringBuffer();
		String sql = null;
		if (type == 1) {
			StringBuffer sbTwo = new StringBuffer();
			sbOne.append("INSERT INTO " + tbName + " (");
			sbTwo.append(" VALUES (");
			for (int i = 0, len = columns.length; i < len; i++) {
				sbOne.append(columns[i] + ",");
				sbTwo.append("?" + ",");
			}
			sbOne.replace(sbOne.length() - 1, sbOne.length(), ")");
			sbTwo.replace(sbTwo.length() - 1, sbTwo.length(), ")");
			sql = sbOne.append(sbTwo).toString();
		}
		if (type == 2) {
			sbOne.append("DELETE FROM " + tbName);
			if (columns.length > 0)
				sbOne.append(" WHERE ");
			for (int i = 0, len = columns.length; i < len; i++) {
				if (i == len - 1)
					sbOne.append(columns[i] + " = ? ");
				else
					sbOne.append(columns[i] + " = ? AND ");
			}
			sql = sbOne.toString();
		}
		if (type == 3 && values != null && values.length > 0) {
			sbOne.append("UPDATE TABLE " + tbName + " SET ");
			for (int i = 0, len = values.length; i < len; i++) {
				if (i != len - 1)
					sbOne.append(values[i] + " = ? ");
				else
					sbOne.append(values[i] + " = ? ,");
			}
			for (int i = 0, len = columns.length; i < len; i++) {
				if (i == len - 1)
					sbOne.append(columns[i] + " = ? ");
				else
					sbOne.append(columns[i] + " = ? AND ");
			}
		}
		logger.error("拼接的SQL:" + sql);
		return sql;
	}

	/**
	 * 包含表名，连接，表字段，表字段类型
	 * 
	 * @param tbName
	 *            表名
	 * @param conn
	 *            连接
	 * @param tbInfo
	 *            已存在可以直接把它传过来，不存在可以传null
	 * @return TableInfoBeanForm
	 * @throws SQLException
	 * @throws ConnectionException
	 * @auto：熊浪 @Time：2017年8月9日 @此方法的作用：
	 */
	public static TableInfoBeanForm getTableColumn(String tbName, TableInfoBeanForm tbInfo) throws SQLException, ConnectionException {
		if (tbInfo == null || (tbInfo != null && !tbInfo.getTableName().equals(tbName)) || (tbInfo != null && tbInfo.getColumnList().size() <= 0)) {
			try {
				tbInfo = new TableInfoBeanForm(tbName);
			} catch (SQLException e) {
				logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
				throw new SQLException(e.getMessage());
			}
		}
		return tbInfo;
	}

	/**
	 * 开始创建pst
	 * 
	 * @param pst
	 * @param tbInfo
	 * @param values
	 * @param colmList
	 * @return
	 * @throws SQLException
	 * @auto：熊浪 @Time：2017年8月9日 @此方法的作用：
	 */
	public static String executePrepareStatement(PreparedStatement pst, TableInfoBeanForm tbInfo, List<String> values, List<ImportDataColm> colmList) throws SQLException {
		String info = "";
		try {
			info = executeAddBatchObject(pst, colmList, values, tbInfo);
		} catch (SQLException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			throw new SQLException("数据源获取异常" + e.getMessage());
		}
		return info;
	}

	/**
	 * 执行批处理
	 * 
	 * @param sql执行的insetSQL
	 * @param tbInfo分析的表结构，包括表字段，表名，表字段类型
	 * @param values添加的数据
	 * @param colmList表需要执行的insert的字段
	 * @return 返回执行失败还是成
	 * @throws SQLException
	 * @throws ConnectionException
	 * @auto：熊浪 @Time：2017年8月9日
	 * @此方法的作用：SQL语句的字段、colmList、values中的list三者的长度一致，顺序相同,此批处理的conn未关闭，事物结束后提交关闭
	 */
	public static boolean executeAllAddBatch(TableInfoBeanForm tbInfo, List<List<String>> values, List<ImportDataColm> colmList) throws SQLException, ConnectionException {
		boolean flag = false;
		PreparedStatement pst = null;
		if (colmList != null) {
			String[] columns = new String[colmList.size()];
			for (int i = 0, len = colmList.size(); i < len; i++)
				columns[i] = colmList.get(i).getColuna();
			pst = SunxlConnectionFactory.getCurrentConnection().prepareStatement(getStringSql(tbInfo.getTableName(), 1, null, columns));
			try {
				if (values != null) {
					for (int i = 0, len = values.size(); i < len; i++)
						executePrepareStatement(pst, tbInfo, values.get(i), colmList);
					pst.executeBatch();
				}
				flag = true;
			} catch (SQLException e) {
				logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
				throw new SQLException("批处理异常" + e.getMessage());
			} finally {
				SunxlConnectionFactory.closeUserConnection(pst);
			}
		}
		return flag;
	}

	/**
	 * 获取数据库类型方法
	 * 
	 * @throws ConnectionException
	 */
	public static String getDBType() throws ConnectionException {
		String dataBaseType = null;
		try {
			DatabaseMetaData dbmd = SunxlConnectionFactory.getCurrentConnection().getMetaData();
			dataBaseType = dbmd.getDatabaseProductName().toLowerCase(); // 获取数据库类型
		} catch (SQLException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
		}
		return dataBaseType;
	}

	public static void main(String[] args) throws ConnectionException {
		
	}
}
