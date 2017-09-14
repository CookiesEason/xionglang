package com.base.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.base.interceptor.InitDataInterceptor;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月13日
 * @此类作用：数据源信息，对象的生成加载顺序 父类静态属性、父类静态代码块> 
 * 子类静态属性、 子类静态代码块> 父类普通属性、父类普通代码块>
 * 子类普通属性、子类普通代码块> 父类构造函数>子类构造函数
 * 静态代码块和静态对象都属于类的信息，只在加载类的时候加载（一般为初次创建对象的时候加载），
 * 之后无论创建多少次对象都不会继续执行，放于方法去（1.8之前的永久带，1.8之后的matespace）。
 * 普通代码块和普通属性属于对象，所以每次创建对象多会执行一次，存放于堆中。
 * 单例对象只会在对象中创建一次，它可以达到让普通代码块和普通属性只执行一次。
 */
public class DBUtil {
	private static final Logger logger = LoggerFactory.getLogger(DBUtil.class);
	protected Map<String, DBInfo> mapdb = new HashMap<String, DBInfo>();// 所有配置的JNDI信息
	protected Connection conn;// 获取的连接
	protected String comp = "sunxl.database";// 默认的服务器JDNI
	protected String defaultJndiName;
	{
		if (SunxlConnectionFactory.getInstance() == null) {// 当SunxlConnectionFactory单例未创建，则执行初始化,SunxlConnectionFactory单例在服务器启动时就会创建
			try {
				SunXlProperties p = (SunXlProperties) InitDataInterceptor.map.get("META-INF/dataSource.properties");
				if (p == null)
					p = new SunXlProperties("META-INF/dataSource.properties");
				String[] name = p.getProperty("sunxl.base.jndi.name").split("\\|");
				String[] user = p.getProperty("sunxl.base.jndi.user").split("\\|");
				String[] password = p.getProperty("sunxl.base.jndi.password").split("\\|");
				String[] jdbcurl = p.getProperty("sunxl.base.jndi.jdbcurl").split("\\|");
				String[] driverClass = p.getProperty("sunxl.base.jndi.driverClass").split("\\|");
				String[] initConnect = p.getProperty("sunxl.base.jndi.init.size").split("\\|");
				String[] everyAdd = p.getProperty("sunxl.base.jndi.every.add").split("\\|");
				String[] maxConnect = p.getProperty("sunxl.base.jndi.max.size").split("\\|");
				defaultJndiName = name[0];// 如果不传入连接池名称，这是用默认jndi连接池名称，其为第一个连接池名称
				DBInfo db = null;
				for (int i = 0, len = name.length; i < len; i++) {
					db = new DBInfo(user[i], password[i], jdbcurl[i], driverClass[i], Integer.parseInt(initConnect[i]), Integer.parseInt(everyAdd[i]), Integer.parseInt(maxConnect[i]));
					mapdb.put(name[i], db);
				}
				if (p.getProperty("java.comp") != null)// 如果没有配置服务器数据源，就使用默认的sunxl.database数据源
					comp = "java:comp/env/" + p.getProperty("java.comp");
				else
					comp = "java:comp/env/" + "sunxl.database";
			} catch (Exception e) {
				logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			}
		}
	}

	class DBInfo {
		private String user;
		private String password;
		private String jdbcurl;
		private String driverClass;
		private int initSize;
		private int everyAdd;
		private int maxSize;
		private int modCount;// 已创建的连接
		private LinkedList<Connection> linkedList;// 可用连接

		public String getUser() {
			return user;
		}

		public String getPassword() {
			return password;
		}

		public String getJdbcurl() {
			return jdbcurl;
		}

		public String getDriverClass() {
			return driverClass;
		}

		public int getInitSize() {
			return initSize;
		}

		public int getEveryAdd() {
			return everyAdd;
		}

		public int getMaxSize() {
			return maxSize;
		}

		public int getModCount() {
			return modCount;
		}

		public void setModCount(int modCount) {
			this.modCount = modCount;
		}

		public LinkedList<Connection> getLinkedList() {
			return linkedList;
		}

		public void setLinkedList(LinkedList<Connection> linkedList) {
			this.linkedList = linkedList;
		}

		private DBInfo(String user, String password, String jdbcurl, String driverClass, int initSize, int everyAdd, int maxSize) {
			this.user = user;
			this.password = password;
			this.jdbcurl = jdbcurl;
			this.driverClass = driverClass;
			this.initSize = initSize;
			this.everyAdd = everyAdd;
			this.maxSize = maxSize;
			this.linkedList = new LinkedList<Connection>();
			try {
				Class.forName(driverClass);
				for (int i = 0; i < initSize; i++) {
					this.linkedList.add(DriverManager.getConnection(jdbcurl, user, password));
				}
				this.modCount = initSize;
			} catch (ClassNotFoundException | SQLException e) {
				logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage() + "，请检查META-INF/dataSource.properties配置的sunxl.base.jndi信息是否正确");
			}
		}

	}

	/**
	 * @param name
	 * @此方法的作用：添加连接
	 */
	public void addConnect(String name) {
		DBInfo db = mapdb.get(name);
		LinkedList<Connection> list = db.getLinkedList();
		int maxSize = db.getMaxSize();
		// 如果连接已空
		if (list.isEmpty()) {
			// 如果连接已达最大连接数
			if (db.getModCount() <= maxSize) {
				for (int i = 0, len = db.getEveryAdd(); i < len; i++) {
					if (db.getModCount() <= maxSize) {
						try {
							list.add(DriverManager.getConnection(db.getJdbcurl(), db.getUser(), db.getPassword()));
							db.setLinkedList(list);
							db.setModCount(db.getModCount() + 1);
						} catch (SQLException e) {
							logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
						}
					} else// 如果已达连接最大值，则跳出添加连接
						break;
				}
			} else {
				logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】已达最大连接数" + maxSize);
			}
		} else {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】连接池还存在" + list.size() + "个连接");
		}
	}
}