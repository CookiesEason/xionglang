package com.sunxl.base.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunxl.base.interceptor.InitDataInterceptor;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月13日
 * @此类作用：数据源信息，对象的生成加载顺序 父类静态属性、父类静态代码块> 子类静态属性、子类静态代码块> 父类普通属性、父类普通代码块>
 *                       子类普通属性、子类普通代码块> 父类构造函数>子类构造函数
 */
public class DBUtil {
	private static final Logger logger = LoggerFactory.getLogger(DBUtil.class);
	protected static String driverClass;// 数据库驱动
	protected static String jdbcurl;// 数据库路径，包含数据库名
	protected static String user;// 数据库用户名
	protected static String password;// 数据库密码
	protected static int initConnect;// 数据库初始化连接数
	protected static int maxConnect;// 数据库最大连接数
	protected static LinkedList<Connection> conns = new LinkedList<Connection>();// 先进先出的连接集合
	protected static Connection conn;// 获取的连接
	protected static int modCount = 0;// 连接池已经创建的连接
	protected static String comp = "sunxl.database";// 默认的服务器数据源
	protected static DBUtil db;
	/**
	 * 加载自定义的JNDI信息
	 */
	static {
		try {
			SunXlProperties p = (SunXlProperties) InitDataInterceptor.map.get("META-INF/dataSource.properties");
			if (p == null)
				p = new SunXlProperties("META-INF/dataSource.properties");
			driverClass = p.getProperty("user.sunxl.driverClass");
			jdbcurl = p.getProperty("user.sunxl.jdbcurl");
			user = p.getProperty("user.sunxl.user");
			password = p.getProperty("user.sunxl.password");
			initConnect = Integer.valueOf(p.getProperty("user.sunxl.init.size"));
			maxConnect = Integer.valueOf(p.getProperty("user.sunxl.max.size"));
			if (p.getProperty("java.comp") != null)// 如果没有配置服务器数据源，就使用默认的sunxl.database数据源
				comp = "java:comp/env/" + p.getProperty("java.comp");
			else
				comp = "java:comp/env/" + "sunxl.database";
			Class.forName(driverClass);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("错误行号:【"+new Throwable().getStackTrace()[0].getLineNumber()+"】"+e.getMessage());
		}
	}
	/**
	 * 初始化一个自定义的JNDI连接池
	 */
	{
		try {
			for (int i = 0; i < initConnect; i++) {
				conns.add(DriverManager.getConnection(jdbcurl, user, password));
			}
			modCount += initConnect;
		} catch (SQLException e) {
			logger.error("错误行号:【"+new Throwable().getStackTrace()[0].getLineNumber()+"】"+e.getMessage());
		}
	}
}