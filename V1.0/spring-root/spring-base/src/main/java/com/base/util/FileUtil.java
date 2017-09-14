package com.base.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.base.interceptor.InitDataInterceptor;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn @Time：2017年7月25日 @此类作用：
 */
public class FileUtil {
	private static String version = null;
	private static String basePath = null;
	private static String realProjectPath = null;
	private static String homePath = null;
	private static String webPath = null;
	private static String contextPath = null;
	private static String downloadPath = null;

	public static String getWebPath() {
		return webPath;
	}

	public static void setWebPath(String webPath) {
		FileUtil.webPath = webPath;
	}

	public static void createDir(String dir, boolean ignoreIfExitst) throws IOException {
		File file = new File(dir);
		if ((ignoreIfExitst) && (file.exists())) {
			return;
		}
		if (!(file.mkdir()))
			throw new IOException("Cannot create the directory = " + dir);
	}

	public static void createDirs(String dir, boolean ignoreIfExitst) throws IOException {
		File file = new File(dir);
		if ((ignoreIfExitst) && (file.exists())) {
			return;
		}
		if (!(file.mkdirs()))
			throw new IOException("Cannot create directories = " + dir);
	}

	public static void deleteFile(String filename) throws IOException {
		File file = new File(filename);
		if (file.isDirectory())
			throw new IOException("IOException -> BadInputException: not a file.");
		if (!(file.exists()))
			throw new IOException("IOException -> BadInputException: file is not exist.");
		if (!(file.delete()))
			throw new IOException("Cannot delete file. filename = " + filename);
	}

	public static void deleteDir(File dir) throws IOException {
		if (dir.isFile())
			throw new IOException("IOException -> BadInputException: not a directory.");
		File[] files = dir.listFiles();
		if (files != null)
			for (int i = 0; i < files.length; ++i) {
				File file = files[i];
				if (file.isFile())
					file.delete();
				else
					deleteDir(file);
			}

		dir.delete();
	}

	public static long getDirLength(File dir) throws IOException {
		if (dir.isFile())
			throw new IOException("BadInputException: not a directory.");
		long size = 0L;
		File[] files = dir.listFiles();
		if (files != null)
			for (int i = 0; i < files.length; ++i) {
				File file = files[i];
				long length = 0L;
				if (file.isFile())
					length = file.length();
				else
					length = getDirLength(file);
				size += length;
			}
		return size;
	}

	public static long getDirLength_onDisk(File dir) throws IOException {
		if (dir.isFile())
			throw new IOException("BadInputException: not a directory.");
		long size = 0L;
		File[] files = dir.listFiles();
		if (files != null)
			for (int i = 0; i < files.length; ++i) {
				File file = files[i];
				long length = 0L;
				if (file.isFile())
					length = file.length();
				else
					length = getDirLength_onDisk(file);
				double mod = Math.ceil(length / 512.0D);
				if (mod == 0D)
					mod = 1D;
				length = (long) mod * 512L;
				size += length;
			}
		return size;
	}

	public static byte[] getBytes(InputStream inputStream) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
		byte[] block = new byte[512];
		while (true) {
			int readLength = inputStream.read(block);
			if (readLength == -1)
				break;
			byteArrayOutputStream.write(block, 0, readLength);
		}
		byte[] retValue = byteArrayOutputStream.toByteArray();
		byteArrayOutputStream.close();
		return retValue;
	}

	public static String getFileName(String fullFilePath) {
		if (fullFilePath == null)
			return "";
		int index1 = fullFilePath.lastIndexOf(47);
		int index2 = fullFilePath.lastIndexOf(92);
		int index = (index1 > index2) ? index1 : index2;
		if (index == -1) {
			return fullFilePath;
		}
		String fileName = fullFilePath.substring(index + 1);
		return fileName;
	}

	public static String getContextRealPath() {
		if (contextPath != null)
			return contextPath;
		File f = new File(getHomePath());
		if ((!(f.exists())) || (!(f.isDirectory()))) {
			contextPath = "D:\\item\\changchun\\bimis\\web";
			return contextPath;
		}
		contextPath = f.getParentFile().getParent();
		return contextPath;
	}

	public static String getVersion() {
		if (version == null || "".equals(version)) {
			version = ((SunXlProperties) InitDataInterceptor.map.get("META-INF/system.properties")).getProperty("system.project.version");
		}
		return version;
	}

	/**
	 * @return
	 * @此方法的作用：项目路径
	 */
	public static String getHomePath() {
		if (homePath == null || "".equals(homePath)) {
			if (getBasePath().endsWith(File.separator))
				homePath = getBasePath().substring(0, getBasePath().length() - 1);
			homePath = homePath.substring(homePath.lastIndexOf(File.separator), homePath.length());
			homePath = homePath.replace(File.separator, "/");
		}
		return homePath;
	}

	/**
	 * @return
	 * @此方法的作用：当前项目的真实路径
	 */
	public static String getRealProjectPath() {
		if (realProjectPath == null || "".equals(realProjectPath)) {
			realProjectPath = getBasePath().substring(0, getBasePath().indexOf(File.separator + ".")) + getHomePath();
			realProjectPath = realProjectPath.replace(File.separator, "/");
		}
		return realProjectPath;
	}

	/**
	 * @return
	 * @此方法的作用：服务器启动获取的跟路径
	 */
	public static String getBasePath() {
		if (basePath == null || "".equals(basePath))
			basePath = System.getProperty("tansungWeb.root");
		return basePath;
	}

	public static String getPropertyValue(String propertiesPath, String key) {
		FileInputStream in;
		try {
			in = new FileInputStream(propertiesPath);
			Properties prop = new Properties();
			prop.load(in);
			in.close();
			String value = (String) prop.get(key);
			if (!(StringUtil.isObjNullOrEmpty(value)))
				return value;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String getDownloadPath() {
		if (downloadPath == null)
			downloadPath = getHomePath() + "data" + File.separator;
		return downloadPath;
	}

	public static String realPath(String classPath) {
		if (classPath.toLowerCase().startsWith("classpath:") || classPath.toLowerCase().startsWith("classpath: ")) {
			return classPath.substring(classPath.indexOf("/"), classPath.length());
		}
		return classPath;
	}

	public static void main(String[] args) {

	}

}
