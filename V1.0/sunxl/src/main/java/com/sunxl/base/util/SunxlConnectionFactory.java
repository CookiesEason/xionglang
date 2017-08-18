package com.sunxl.base.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.mail.iap.ConnectionException;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn @Time：2017年8月10日 @此类作用：
 */
public class SunxlConnectionFactory extends DBUtil {
	private static final Logger logger = LoggerFactory.getLogger(SunxlConnectionFactory.class);
	private static Map<Thread, Connection> connectionMap = new HashMap<Thread, Connection>();

	/**
	 * @throws ConnectionException
	 * @auto：熊浪 @Time：2017年8月10日 @此方法的作用：获取当前线程的连接(此处有点问题，一般服务器提供的JNDI连接池性能更好)
	 */
	private static Connection getThreadCurrentConnection() throws ConnectionException {
		Connection conn = null;
		try {
			conn = getUserConnection();// 获取自定义连接
		} catch (ConnectionException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			try {
				conn = getConnection();// 获取服务器提供的JNDI数据源
			} catch (NamingException e1) {
				logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			}
		}
		if (conn != null)
			connectionMap.put(Thread.currentThread(), conn);
		else {
			throw new ConnectionException("获取连接池连接异常");
		}
		return conn;
	}

	/**
	 * @throws ConnectionException
	 * @auto：熊浪
	 * @Time：2017年8月10日 @此方法的作用：获取当前线程的连接
	 */
	public static Connection getCurrentConnection() throws ConnectionException {
		if (connectionMap.get(Thread.currentThread()) == null)
			return getThreadCurrentConnection();
		return connectionMap.get(Thread.currentThread());
	}

	/**
	 * 当前线程结束，需要把Connection放回数据源
	 * 
	 * @auto：熊浪
	 * @Time：2017年8月10日 @此方法的作用：关闭之前需要先移除connectionMap中的Connection
	 */
	public static void closeUserConnection() {
		if (connectionMap.get(Thread.currentThread()) != null) {
			Connection conn = connectionMap.remove(Thread.currentThread());
			if (conn != null)
				closeUserConnection(conn);
		}
	}

	/**
	 * 把连接放回连接池
	 * 
	 * @param Statement
	 * @param ResultSet
	 * @auto：熊浪 @Time：2017年8月1日
	 * @此方法的作用：自动把当先线程的连接放回连接池
	 */
	public static void closeUserConnection(Statement pst, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			rs = null;
			if (pst != null)
				pst.close();
			pst = null;
			if (getCurrentConnection().getAutoCommit())
				closeUserConnection();
		} catch (SQLException | ConnectionException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
		}
	}

	/**
	 * 把连接放回连接池
	 * 
	 * @param Statement
	 * @auto：熊浪 @Time：2017年8月1日
	 * @此方法的作用：自动把当先线程的连接放回连接池
	 */
	public static void closeUserConnection(Statement pst) {
		try {
			if (pst != null)
				pst.close();
			if (getCurrentConnection().getAutoCommit())
				closeUserConnection();
		} catch (SQLException | ConnectionException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
		}
	}

	/**
	 * 把连接放回连接池
	 * 
	 * @param Statement
	 * @auto：熊浪 @Time：2017年8月1日
	 * @此方法的作用：自动把当先线程的连接放回连接池
	 */
	public static void closeUserConnection(ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			if (getCurrentConnection().getAutoCommit())
				closeUserConnection();
		} catch (SQLException | ConnectionException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
		}
	}

	/**
	 * 把用完的连接放回自建连接池最后的一个位置
	 * 
	 * @param conn
	 * @auto：熊浪 @Time：2017年7月30日 @此方法的作用：
	 */
	private static void closeUserConnection(Connection conn) {
		conns.addLast(conn);
	}

	/**
	 * @return
	 * @throws ConnectionException
	 * @auto：熊浪 @Time：2017年7月30日
	 * @此方法的作用：LinkedList是链状表结构，先进先 出。取到第一个连接，在移除第一个连接，此连接只能通过getCurrentConnection获取
	 */
	private static Connection getUserConnection() throws ConnectionException {
		if (db == null)
			db = new DBUtil();
		if (conns.isEmpty()) {// 当连接池为空的时候,不为空直接取
			if (modCount != 0 && modCount < maxConnect) {// 判断真正创建的连接数是否已达最大值，没有到就扩充连接池
				for (int i = 0, len = maxConnect - initConnect; i < len; i++) {
					try {
						conns.add(DriverManager.getConnection(jdbcurl, user, password));
					} catch (SQLException e) {
						logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
					}
				}
				modCount += maxConnect - initConnect;
			} else {
				if (modCount == 0)
					throw new ConnectionException("未在dataSource.properties里面配置user.sunxl.driverClass连接池信息");
				else
					throw new ConnectionException("连接池创建的连接已达最大值");
			}
		}
		conn = conns.get(0);// 获取链表的第一个连接
		conns.removeFirst();// 移除链表的第一个连接
		return conn;
	}

	/**
	 * @throws ConnectionException
	 * @auto：熊浪
	 * @Time：2017年8月10日 @此方法的作用：开启自定义连接池事物
	 */
	public static void beginTransaction() throws ConnectionException {
		try {
			getCurrentConnection().setAutoCommit(false);
		} catch (SQLException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
		}
	}

	/**
	 * @throws ConnectionException
	 * @auto：熊浪
	 * @Time：2017年8月10日 @此方法的作用：手动提交
	 */
	public static void commitTransaction() throws ConnectionException {
		try {
			if (!getCurrentConnection().getAutoCommit())
				getCurrentConnection().commit();
			closeUserConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
		}
	}

	/**
	 * @throws ConnectionException
	 * @auto：熊浪
	 * @Time：2017年8月10日 @此方法的作用：回滚
	 */
	public static void rollBack() throws ConnectionException {
		try {
			if (!getCurrentConnection().getAutoCommit())
				getCurrentConnection().rollback();
			closeUserConnection();
		} catch (SQLException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
		}
	}

	/**
	 * @auto：熊浪
	 * @Time：2017年8月10日 @此方法的作用：通过服务器创建的JNDI的connection不用关闭，服务器自动回收
	 */
	private static Connection getConnection() throws NamingException {
		Connection conn = null;
		DataSource ds = null;
		Context ct = new InitialContext();
		try {
			ds = (DataSource) ct.lookup(comp);
			conn = ds.getConnection();
		} catch (SQLException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
		} finally {
			ct.close();
		}
		return conn;
	}

	/**
	 * @param conn
	 * @param st
	 * @param rs
	 * @auto：熊浪 @Time：2017年7月30日
	 * @此方法的作用：关闭Connection、Statement、ResultSet此方法只适用于JDBC连接
	 */
	public static void close(Connection conn, Statement st, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
		}
	}

	/**
	 * @auto：熊浪 @Time：2017年8月10日
	 * @此方法的作用：初始化数据源配置信息 初始化自建数据源 获取数据源，用于main方法测试使用连接
	 */
	public static Connection getJDBCConnection() {
		Connection conn = null;
		if (driverClass == null) {
			try {
				SunXlProperties sunXlProperties = new SunXlProperties("META-INF/dataSource.properties");
				try {
					Class.forName(sunXlProperties.getProperty("user.sunxl.driverClass"));
					try {
						conn = DriverManager.getConnection(sunXlProperties.getProperty("user.sunxl.jdbcurl"), sunXlProperties.getProperty("user.sunxl.user"), sunXlProperties.getProperty("user.sunxl.password"));
					} catch (SQLException e) {
						logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
					}
				} catch (ClassNotFoundException e) {
					logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
				}
			} catch (IOException e) {
				logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			}
		}
		return conn;
	}
}
