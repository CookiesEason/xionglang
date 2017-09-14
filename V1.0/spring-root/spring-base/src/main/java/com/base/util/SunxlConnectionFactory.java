package com.base.util;

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

import com.base.exception.SunxlRuntimeException;
import com.sun.mail.iap.ConnectionException;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn @Time：2017年8月10日 @此类作用：
 */
public class SunxlConnectionFactory extends DBUtil {
	private static final Logger logger = LoggerFactory.getLogger(SunxlConnectionFactory.class);
	private static Map<String, Connection> connectionMap = new HashMap<String, Connection>();
	private static final int TIME_OUT = 15;// 默认连接测试时间为15秒

	private SunxlConnectionFactory() {
		super();
	}

	private static class InnerSunxlConnectionFactory {
		private static SunxlConnectionFactory INSTANCE = new SunxlConnectionFactory();
	}

	public static final SunxlConnectionFactory getInstance() {
		return InnerSunxlConnectionFactory.INSTANCE;
	}

	public static Connection getUserConnection(String... jndiNames) throws ConnectionException {
		return getInstance().getCurrentConnection(getJndiName(jndiNames));
	}

	public static String getJndiName(String... jndiNames) {
		String jndiName = null;
		if (jndiNames.length == 0 || "".equals(jndiNames[0]))
			jndiName = getInstance().defaultJndiName;
		else
			jndiName = jndiNames[0];
		return jndiName;
	}

	/**
	 * @throws ConnectionException
	 * @auto：熊浪
	 * @Time：2017年8月10日 @此方法的作用：获取当前线程的连接
	 */
	private Connection getCurrentConnection(String jndiName) throws ConnectionException {
		try {
			if (connectionMap.get(Thread.currentThread() + jndiName) == null)
				return getThreadCurrentConnection(jndiName);
			return connectionMap.get(Thread.currentThread() + jndiName);
		} catch (Exception e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
		}
		return null;
	}

	/**
	 * @throws ConnectionException
	 * @auto：熊浪 @Time：2017年8月10日 @此方法的作用：获取当前线程的连接(此处有点问题，一般服务器提供的JNDI连接池性能更好)
	 */
	private Connection getThreadCurrentConnection(String jndiName) throws ConnectionException {
		Connection conn = null;
		conn = getJNDIConnection(jndiName, 0);// 获取自定义连接
		if (conn != null)// 获取连接池成功则把当前线程的当前连接池名称存入map中
			connectionMap.put(Thread.currentThread() + jndiName, conn);
		else {
			throw new ConnectionException("获取连接池连接异常");
		}
		return conn;
	}

	/**
	 * @return
	 * @throws ConnectionException
	 * @auto：熊浪 @Time：2017年7月30日
	 * @此方法的作用：LinkedList是链状表结构，先进先 出。取到第一个连接，在移除第一个连接，此连接只能通过getCurrentConnection获取
	 */
	private Connection getJNDIConnection(String jndiName, Integer count) throws ConnectionException {
		if (getInstance().mapdb.get(jndiName) == null)// 只有初次取连接的时候判断一次
			throw new SunxlRuntimeException("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】,传入的" + jndiName + "连接池名称出错");
		try {
			if (getInstance().mapdb.get(jndiName).getLinkedList().isEmpty())
				getInstance().addConnect(jndiName);
			Connection conn = getInstance().mapdb.get(jndiName).getLinkedList().get(0);// 获取链表的第一个连接
			getInstance().mapdb.get(jndiName).getLinkedList().removeFirst();// 移除链表的第一个连接
			if (!conn.isValid(TIME_OUT)) {// 判断连接是否有效，如果没有确认连接有效，进入if方法
				getInstance().mapdb.get(jndiName).getLinkedList().addLast(conn);// 就把它放在最后，
				if (count < 3)// 如果连续3次都不能获得有效的连接，则抛出异常
					getJNDIConnection(jndiName, count++);// 继续回调获取
				else
					throw new SunxlRuntimeException("不能获取连接池为" + jndiName + "的有效连接！！！");
			}
			return conn;
		} catch (SunxlRuntimeException | SQLException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			throw new SunxlRuntimeException(e.getMessage());
		}
	}

	/**
	 * 当前线程结束，需要把Connection放回数据源
	 * 
	 * @auto：熊浪
	 * @Time：2017年8月10日 @此方法的作用：关闭之前需要先移除connectionMap中的Connection
	 */
	private void closeUserConnection(String jndiName) {
		if (connectionMap.get(Thread.currentThread() + jndiName) != null) {
			Connection conn = connectionMap.remove(Thread.currentThread() + jndiName);
			if (conn != null) {// 如果当前连接不为空，则把当前连接放回最后的位置
				getInstance().mapdb.get(jndiName).getLinkedList().addLast(conn);
			}
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
	public static void closeUserConnection(Statement pst, ResultSet rs, String... jndiNames) {
		try {
			if (rs != null)
				rs.close();
			rs = null;
			if (pst != null)
				pst.close();
			pst = null;
			String jndiName = getJndiName(jndiNames);
			if (getInstance().getCurrentConnection(jndiName).getAutoCommit())
				getInstance().closeUserConnection(jndiName);
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
	public static void closeUserConnection(Statement pst, String... jndiNames) {
		try {
			if (pst != null)
				pst.close();
			String jndiName = getJndiName(jndiNames);
			if (getInstance().getCurrentConnection(jndiName).getAutoCommit())
				getInstance().closeUserConnection(jndiName);
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
	public static void closeUserConnection(ResultSet rs, String... jndiNames) {
		try {
			if (rs != null)
				rs.close();
			String jndiName = getJndiName(jndiNames);
			if (getInstance().getCurrentConnection(jndiName).getAutoCommit())
				getInstance().closeUserConnection(jndiName);
		} catch (SQLException | ConnectionException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
		}
	}

	/**
	 * @throws ConnectionException
	 * @auto：熊浪
	 * @Time：2017年8月10日 @此方法的作用：开启自定义连接池事物
	 */
	public static void beginTransaction(String... jndiNames) throws ConnectionException {
		try {
			String jndiName = getJndiName(jndiNames);
			getInstance().getCurrentConnection(jndiName).setAutoCommit(false);
		} catch (SQLException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
		}
	}

	/**
	 * @throws ConnectionException
	 * @auto：熊浪
	 * @Time：2017年8月10日 @此方法的作用：手动提交
	 */
	public static void commitTransaction(String... jndiNames) throws ConnectionException {
		try {
			String jndiName = getJndiName(jndiNames);
			if (!getInstance().getCurrentConnection(jndiName).getAutoCommit())
				getInstance().getCurrentConnection(jndiName).commit();
			getInstance().closeUserConnection(jndiName);
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
	public static void rollBack(String... jndiNames) throws ConnectionException {
		try {
			String jndiName = getJndiName(jndiNames);
			if (!getInstance().getCurrentConnection(jndiName).getAutoCommit())
				getInstance().getCurrentConnection(jndiName).rollback();
			getInstance().closeUserConnection(jndiName);
		} catch (SQLException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
		}
	}

	/**
	 * @auto：熊浪
	 * @Time：2017年8月10日 @此方法的作用：通过服务器创建的JNDI的connection不用关闭，服务器自动回收
	 */
	@SuppressWarnings("unused")
	private static Connection getConnection() throws NamingException {
		Connection conn = null;
		DataSource ds = null;
		Context ct = new InitialContext();
		try {
			ds = (DataSource) ct.lookup(getInstance().comp);
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
	private Connection getJDBCJndiNameConnection(String jndiName) {
		Connection conn = null;
		if (getInstance().mapdb.get(jndiName) == null)
			throw new SunxlRuntimeException("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】,初始化连接池" + jndiName + "出错");
		try {
			conn = DriverManager.getConnection(getInstance().mapdb.get(jndiName).getJdbcurl(), getInstance().mapdb.get(jndiName).getUser(), getInstance().mapdb.get(jndiName).getPassword());
		} catch (SQLException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
		}
		return conn;
	}

	private Connection getJDBCFirstConnection() {
		Connection conn = null;
		try {
			SunXlProperties p = new SunXlProperties("META-INF/dataSource.properties");
			try {
				Class.forName(p.getProperty("sunxl.base.jndi.driverClass").split("|")[0]);
				try {
					conn = DriverManager.getConnection(p.getProperty("sunxl.base.jndi.jdbcurl").split("|")[0], p.getProperty("sunxl.base.jndi.user").split("|")[0], p.getProperty("sunxl.base.jndi.password").split("|")[0]);
				} catch (SQLException e) {
					logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
				}
			} catch (ClassNotFoundException e) {
				logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			}
		} catch (IOException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
		}
		return conn;
	}

	public Connection getJDBCConnection() {
		Connection conn = null;
		if (getInstance() == null)
			throw new SunxlRuntimeException("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】,创建对象出错");
		if (getInstance().mapdb == null) {
			conn = getJDBCFirstConnection();
		} else {
			conn = getJDBCJndiNameConnection(getInstance().defaultJndiName);
		}
		return conn;
	}

	public Connection getJDBCConnection(String jndiName) {
		Connection conn = null;
		if (getInstance() == null)
			throw new SunxlRuntimeException("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】,创建对象出错");
		if (getInstance().mapdb == null) {
			conn = getJDBCFirstConnection();
		} else {
			conn = getJDBCJndiNameConnection(jndiName);
		}
		return conn;
	}

}
