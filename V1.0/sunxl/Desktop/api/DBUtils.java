package com.seecen.fanshe.fs0327;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import oracle.sql.DATE;
import oracle.sql.TIMESTAMP;

import com.seecen.fanshe.fs0327.model.Score;

/**
 * ���ݿ⹤����
 * 
 * @author hzcl_sky
 * 
 */
public class DBUtils {

	final static String className = "oracle.jdbc.OracleDriver";
	final static String url = "jdbc:oracle:thin:@192.168.1.249:1521:orcl";
	final static String user = "sc1412";
	final static String pwd = "sc1412";

	public static Connection getConnection() {
		try {
			Class.forName(className);
			return DriverManager.getConnection(url, user, pwd);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void close(Connection conn, Statement st, ResultSet clas) {
		try {
			if (clas != null) {
				clas.close();
			}
			close(conn, st);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(Connection conn, Statement st) {
		try {
			if (st != null)
				st.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static <T> T getObject(String sql, Class<?> clas, Object... params) {
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(sql);
			if (params != null) {
				int index = 1;
				for (Object param : params) {
					pst.setObject(index++, param);
				}
			}
			rs = pst.executeQuery();
			if (rs.next()) {
				return rsToClass(clas, rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pst, rs);
		}
		return null;
	}

	public static <T> List<T> getList(String sql, Class<?> clas, Object... params) {
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<T> list = new ArrayList<T>();
		try {
			pst = conn.prepareStatement(sql);
			if (params != null) {
				int index = 1;
				for (Object param : params) {
					pst.setObject(index++, param);
				}
			}
			rs = pst.executeQuery();
			while (rs.next()) {
				list.add((T) rsToClass(clas, rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pst, rs);
		}
		return list;
	}

	private static <T> T rsToClass(Class<?> type, ResultSet rs) {
		try {
			// ResultSetMetaData metaData = rs.getMetaData();
			T obj = (T) type.newInstance();
			Method[] methods = clas.getMethods();
			for (Method method : methods) {
				String methodName = method.getName();
				if (methodName.startsWith("set")) {
					Class<?>[] types = method.getParameterTypes();
					if (types.length == 1) {
						methodName = methodName.substring(3, methodName
								.length());
						if (String.class.equals(types[0])
								|| char.class.equals(types[0])
								|| Character.class.equals(types[0])) {
							String value = rs.getString(methodName);
							method.invoke(obj, value);
						} else if (boolean.class.equals(types[0])) {
							boolean value = rs.getBoolean(methodName);
							method.invoke(obj, value);
						} else if (Date.class.equals(types[0])) {
							Object value = rs.getObject(methodName);
							if (value instanceof TIMESTAMP) {
								TIMESTAMP tm = (TIMESTAMP) value;
								// Timestamp timestamp = tm.timestampValue();
								method.invoke(obj, tm.timestampValue());
							} else if (value instanceof DATE) {
								DATE tm = (DATE) value;
								// java.sql.Date date = tm.dateValue();
								method.invoke(obj, tm.dateValue());
							} else {
								method.invoke(obj, (Date) value);
							}
						} else if (Boolean.class.equals(types[0])
								|| boolean.class.equals(types[0])) {
							boolean value = rs.getBoolean(methodName);
							method.invoke(obj, value);
						} else if (Long.class.equals(types[0])
								|| long.class.equals(types[0])) {
							long value = rs.getLong(methodName);
							method.invoke(obj, value);
						} else if (Float.class.equals(types[0])
								|| float.class.equals(types[0])) {
							float value = rs.getFloat(methodName);
							method.invoke(obj, value);
						} else if (Double.class.equals(types[0])
								|| double.class.equals(types[0])) {
							double value = rs.getDouble(methodName);
							method.invoke(obj, value);
						} else if (Short.class.equals(types[0])
								|| short.class.equals(types[0])) {
							short value = rs.getShort(methodName);
							method.invoke(obj, value);
						} else if (Byte.class.equals(types[0])
								|| byte.class.equals(types[0])) {
							byte value = rs.getByte(methodName);
							method.invoke(obj, value);
						} else {
							method.invoke(obj, rs.getInt(methodName));
						}
					}
				}
			}
			return obj;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static <T> T resultSetToObject(Class<?> clas, ResultSet rs)throws SQLException {
		try {
			ResultSetMetaData metaData = rs.getMetaData();
			int count = metaData.getColumnCount();
			Object obj = clas.newInstance();
			for (int i = 1; i <= count; i++) {
				int type = metaData.getColumnType(i);
				String colName = metaData.getColumnLabel(i).toLowerCase();
				Method[] methods = clas.getMethods();
				for (Method method : methods) {
					String methodName = method.getName();
					if (methodName.startsWith("set")) {
						if (methodName.toLowerCase().endsWith(colName)) {
							switch (type) {
							case Types.DATE:
								java.sql.Date date = rs.getDate(i);
								method.invoke(obj, new Date(date.getTime()));
								break;
							case Types.TIMESTAMP:
								Timestamp timestamp = rs.getTimestamp(i);
								method.invoke(obj,
										new Date(timestamp.getTime()));
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
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String sql = "select * from (select a.*,rownum rn from (select * from score order by scoreid) a where rownum<=?) where rn>=?";
//		User user = getObject(sql, User.class, 2);
//		System.out.println(user);
		List<Score> list = getList(sql, Score.class,20,11);
		for (Score user : list) {
			System.out.println(user);
		}
	}

}
