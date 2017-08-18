package com.sunxl.base.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月12日
 * @此类作用：读取配置文件
 */
public abstract class SystemProperties {
	private static final Logger logger = LoggerFactory.getLogger(SystemProperties.class);
	private Properties properties;
	private String path = "META-INF/system.properties";

	/**
	 * 读取默认文件META-INF/system.properties
	 * 
	 * @throws IOException
	 */
	public SystemProperties() throws IOException {
		properties = new Properties();
		classLoaderData(this.path);
	}

	/**
	 * 相对路径读取文件src
	 * 
	 * @param path
	 * @throws IOException
	 */
	public SystemProperties(String path) throws IOException {
		properties = new Properties();
		this.path = path;
		classLoaderData(this.path);
	}

	/**
	 * 读取绝对路径文件
	 * @param projectPath
	 * @param path
	 * @throws IOException
	 */
	public SystemProperties(String projectPath, String path) throws IOException {
		properties = new Properties();
		this.path = projectPath + path;
		refresh(this.path);
	}

	/**
	 * 使用绝对路径
	 * @param path
	 * @throws IOException
	 * @auto：熊浪
	 * @Time：2017年7月25日
	 * @此方法的作用：
	 */
	private void refresh(String path) throws IOException {
		FileInputStream in = null;
		try {
			in = new FileInputStream(path);
			properties.load(in);
		} catch (IOException e) {
			logger.error("错误行号:【"+new Throwable().getStackTrace()[0].getLineNumber()+"】"+"配置文件读取异常！！！" + e.getMessage());
		} finally {
			if (in != null)
				in.close();
		}
	}

	/**
	 * 路径写法src下面META-INF/system.properties, 默认使用java的class文件的相对路径，前面没有/
	 * 
	 * @param path
	 * @throws IOException
	 * @auto：熊浪 @Time：2017年7月25日 @此方法的作用：
	 */
	private void classLoaderData(String path) throws IOException {
		ClassLoader loader = this.getClass().getClassLoader();
		InputStream is = null;
		InputStreamReader in = null;
		try {
			is = loader.getResourceAsStream(path);
			in = new InputStreamReader(is);
			properties.load(in);
		} catch (IOException e) {
			logger.error("错误行号:【"+new Throwable().getStackTrace()[0].getLineNumber()+"】"+"配置文件读取异常！！！" + e.getMessage());
		} finally {
			if (in != null)
				in.close();
		}
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getProperty(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}
}
