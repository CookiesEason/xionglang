package com.base.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.base.form.ClassTypeInfoForm;
import com.base.form.TableInfoBeanForm;
import com.base.interfaces.MethodLog;
import com.sun.mail.iap.ConnectionException;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年6月27日
 * @此类作用jpa操作的时对象，根据ID
 */
@SuppressWarnings({ "unchecked" })
public class DBSqlUtil {
	private static final Logger logger = LoggerFactory.getLogger(DBSqlUtil.class);

	private static class InnerDBSqlUtil {
		private static DBSqlUtil INSTANCE = new DBSqlUtil();
	}

	public static final DBSqlUtil getInInstance() {
		return InnerDBSqlUtil.INSTANCE;
	}

	/**
	 * @param sql预编译的sql
	 * @param params填充的参数值
	 * @throws SQLException
	 * @auto：熊浪
	 * @Time：2017年8月11日 @此方法的作用：修改
	 */
	public boolean update(ClassTypeInfoForm typeInfo) throws ConnectionException, SQLException {
		try {
			return executeObject(typeInfo);
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
	public boolean delete(ClassTypeInfoForm typeInfo) throws ConnectionException, SQLException {
		try {
			return executeObject(typeInfo);
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
	public boolean add(ClassTypeInfoForm typeInfo) throws ConnectionException, SQLException {
		try {
			return executeObject(typeInfo);
		} catch (SQLException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			throw new SQLException(e.getMessage());
		}
	}

	/**
	 * @auto：熊浪
	 * @Time：2017年8月11日 @此方法的作用：增删改执行Sql语句
	 */
	private boolean executeObject(ClassTypeInfoForm typeInfo) throws SQLException, ConnectionException {
		boolean flag = false;
		PreparedStatement pst = null;
		try {
			pst = SunxlConnectionFactory.getUserConnection(typeInfo.getJndiName()).prepareStatement(typeInfo.getSql());
			if (typeInfo.getParams() != null && typeInfo.getParams().length != 0) {
				int index = 1;
				for (Object param : typeInfo.getParams()) {
					pst.setObject(index++, param);
				}
			}
			flag = pst.execute();
		} catch (SQLException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getStackTrace());
			throw new SQLException(e.getMessage());
		} finally {
			SunxlConnectionFactory.closeUserConnection(pst, typeInfo.getJndiName());
		}
		return flag;
	}

	/**
	 * @throws ConnectionException
	 * @throws SQLException
	 * @auto：熊浪
	 * @Time：2017年8月9日 @此方法的作用：可以返回基本类型的包装类，之后强转如Integer
	 */
	public <T> T getObject(ClassTypeInfoForm typeInfo) throws ConnectionException, SQLException {
		T obj = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		try {
			rs = executeTypeSql(typeInfo);
			if (rs.next()) {
				obj = (T) resultToClass(typeInfo.getType(), rs);
			}
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		} finally {
			SunxlConnectionFactory.closeUserConnection(pst, rs, typeInfo.getJndiName());
		}
		return obj;
	}

	/**
	 * @throws ConnectionException
	 * @throws SQLException
	 * @auto：熊浪
	 * @Time：2017年8月9日 @此方法的作用：可以返回基本类型的包装类，之后强转如List<Integer>,需要提供查询的字段
	 */
	public <T> List<T> getObjectList(ClassTypeInfoForm typeInfo) throws ConnectionException, SQLException {
		List<T> list = new ArrayList<T>();
		ResultSet rs = null;
		PreparedStatement pst = null;
		try {
			rs = executeTypeSql(typeInfo);
			while (rs.next()) {
				list.add((T) resultToClass(typeInfo.getType(), rs));
			}
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		} finally {
			SunxlConnectionFactory.closeUserConnection(pst, rs, typeInfo.getJndiName());
		}
		return list;
	}

	private ResultSet executeTypeSql(ClassTypeInfoForm typeInfo) throws ConnectionException, SQLException {
		ResultSet rs = null;
		try {
			PreparedStatement pst = SunxlConnectionFactory.getUserConnection(typeInfo.getJndiName()).prepareStatement(typeInfo.getSql());
			if (typeInfo.getParams() != null && typeInfo.getParams().length != 0) {
				int index = 1;
				for (Object param : typeInfo.getParams())
					pst.setObject(index++, param);
			}
			rs = pst.executeQuery();
		} catch (SQLException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			throw new SQLException(e.getMessage());
		}
		return rs;
	}

	/**
	 * @auto：熊浪
	 * @Time：2017年8月14日 @此方法的作用：把结果转换为一个对象,字段值为基础数据类型，没有查询的值设置为null，非基础数据类型为null
	 */
	private <T> T resultToClass(Class<?> type, ResultSet rs) {
		if (type == null)
			type = String.class;
		T obj = null;
		String methodName = type.getName().substring(type.getName().trim().lastIndexOf(".") + 1, type.getName().trim().length()).toUpperCase();
		if ("STRING,INTEGER,BOOLEAN,FLOOR,DOUBLE,TIMESTAMP".contains(methodName)) {
			try {
				obj = (T) (rs.getObject(1));
			} catch (SQLException e) {
				logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			}
		} else {
			try {
				obj = (T) type.newInstance();
			} catch (InstantiationException e1) {
				logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e1.getMessage());
			} catch (IllegalAccessException e1) {
				logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e1.getMessage());
			}
			Field[] fields = type.getDeclaredFields();
			Method[] methods = type.getMethods();
			Object o = null;
			for (Method method : methods) {
				try {
					methodName = method.getName();
					if (methodName.startsWith("set")) {
						Class<?>[] types = method.getParameterTypes();
						methodName = methodName.substring(3, methodName.length());
						if (types.length == 1) {
							for (Field field : fields) {
								if (methodName.toUpperCase().equals(field.getName().toUpperCase())) {
									methodName = field.getName();
									break;
								}
							}
							if (String.class.equals(types[0]) || char.class.equals(types[0]) || Character.class.equals(types[0])) {
								o = rs.getString(methodName);
							} else if (Integer.class.equals(types[0]) || int.class.equals(types[0])) {
								o = rs.getInt(methodName);
							} else if (Date.class.equals(types[0])) {
								o = rs.getDate(methodName);
							} else if (Timestamp.class.equals(types[0])) {
								o = rs.getTimestamp(methodName);
							} else if (Boolean.class.equals(types[0]) || boolean.class.equals(types[0])) {
								o = rs.getBoolean(methodName);
							} else if (Long.class.equals(types[0]) || long.class.equals(types[0])) {
								o = rs.getLong(methodName);
							} else if (Float.class.equals(types[0]) || float.class.equals(types[0])) {
								o = rs.getFloat(methodName);
							} else if (Double.class.equals(types[0]) || double.class.equals(types[0])) {
								o = rs.getDouble(methodName);
							} else if (Short.class.equals(types[0]) || short.class.equals(types[0])) {
								o = rs.getShort(methodName);
							} else if (Byte.class.equals(types[0]) || byte.class.equals(types[0])) {
								o = rs.getByte(methodName);
							} else {
								o = rs.getObject(methodName);
							}
							method.invoke(obj, o);
						}
					}
				} catch (SQLException e) {
					logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
				} catch (IllegalAccessException e) {
					logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
				} catch (IllegalArgumentException e) {
					logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
				} catch (InvocationTargetException e) {
					logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
				}
			}
		}
		return obj;
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
	public <T> T resultSetToObject(Class<?> type, ResultSet rs) throws SQLException {
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
	 * @param tbName表名
	 * @param conn
	 *            连接
	 * @param tbInfo
	 *            已存在可以直接把它传过来，不存在可以传null
	 * @return TableInfoBeanForm
	 * @throws SQLException
	 * @throws ConnectionException
	 * @auto：熊浪 @Time：2017年8月9日
	 * @此方法的作用：获取表信息，包含表名、表字段和表字段类型
	 */
	public TableInfoBeanForm getTableColumn(String tbName, TableInfoBeanForm tbInfo, String... jndiNames) throws SQLException, ConnectionException {
		if (tbInfo == null || (tbInfo != null && !tbInfo.getTableName().equals(tbName)) || (tbInfo != null && tbInfo.getColumnList().size() <= 0)) {
			try {
				tbInfo = new TableInfoBeanForm(tbName, jndiNames);
			} catch (SQLException e) {
				logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
				throw new SQLException(e.getMessage());
			}
		}
		return tbInfo;
	}

	/**
	 * @auto：熊浪
	 * @Time：2017年8月14日 @此方法的作用：获取数据库类型
	 */
	@MethodLog(remark = "获取数据库类型")
	public String getDBType(String... jndiNames) throws ConnectionException {
		String dataBaseType = null;
		try {
			DatabaseMetaData dbmd = SunxlConnectionFactory.getUserConnection(jndiNames).getMetaData();
			dataBaseType = dbmd.getDatabaseProductName().toLowerCase(); // 获取数据库类型
		} catch (SQLException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
		}
		return dataBaseType;
	}
}
