package com.base.util;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;

import com.base.enums.ResourceType;
import com.base.one.entity.Resource;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn 
 * @Time：2017年7月13日 
 * @此类作用：读取文件路径
 */
@SuppressWarnings("unused")
public class FileMaker {
	public static String getImageBasicPath() throws IOException {
		SunXlProperties properties = SunXlProperties.getInstance();
		return properties.getProperty(SunXlProperties.FILE_ROOT) + File.separator + properties.getProperty(SunXlProperties.FILE_IMAGE); // Linux
	}

	public static String getExcelBasicPath() throws IOException {
		SunXlProperties properties = SunXlProperties.getInstance();
		return properties.getProperty(SunXlProperties.FILE_ROOT) + File.separator + properties.getProperty(SunXlProperties.FILE_EXCEL);
	}

	public static String getAttachmentBasicPath() throws IOException {
		SunXlProperties properties = SunXlProperties.getInstance();
		return properties.getProperty(SunXlProperties.FILE_ROOT) + File.separator + properties.getProperty(SunXlProperties.FILE_ATTACHMENT); // Linux
	}

	public static String createDatePath(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy,MM,dd");
		String[] strs = df.format(date).split(",");
		StringBuilder sb = new StringBuilder();
		for (String str : strs) {
			// sb.append(File.separator).append(str); //Windows
			sb.append(File.separator).append(str); // Linux
		}
		return sb.toString();
	}

	public static Resource defaultMethodForCreateImage(String fileName, byte[] data) throws IOException {
		return defaultCreate(fileName, data, getImageBasicPath(), ResourceType.IMAGE);
	}

	public static File getImageDirectory() throws IOException {
		Date now = new Date();
		String path = getImageBasicPath() + FileMaker.createDatePath(now);
		File directory = new File(path);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		return directory;
	}

	public static File getExcelDirectory() throws IOException {
		Date now = new Date();
		String path = getExcelBasicPath() + FileMaker.createDatePath(now);
		File directory = new File(path);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		return directory;
	}

	private static Resource defaultCreate(String fileName, byte[] data, String rootUrl, ResourceType type) throws IOException {
		Date now = new Date();
		String path = rootUrl + FileMaker.createDatePath(now);
		File directory = new File(path);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		Resource resource = new Resource();
		resource.setResourcePath(getLinuxUrl(directory, now.getTime() + fileName.length() + fileName.substring(fileName.indexOf("."), fileName.length()), data, path)); // Linux
		resource.setUploadDate(now);
		resource.setFileName(fileName);
		resource.setResourceType(type);
		return resource;
	}

	/**
	 * Linux获取路径
	 * 
	 * @param directory
	 * @param resourcePath
	 * @param data
	 * @param path
	 * @return
	 * @throws IOException
	 */
	private static String getLinuxUrl(File directory, String resourcePath, byte[] data, String path) throws IOException {
		File attachment = new File(directory, resourcePath);
		FileUtils.writeByteArrayToFile(attachment, data);
		return path + File.separator + resourcePath;
	}

	/**
	 * Windows获取路径
	 * 
	 * @param directory
	 * @param nowTime
	 * @param data
	 * @return
	 * @throws IOException
	 */
	private static String getWinUrl(File directory, String resourcePath, byte[] data) throws IOException {
		File attachment = new File(directory, resourcePath);
		FileUtils.writeByteArrayToFile(attachment, data);
		return attachment.getAbsolutePath();
	}

	public static Resource defaultMethodForCreateAttachment(String fileName, byte[] data) throws IOException {
		return defaultCreate(fileName, data, getAttachmentBasicPath(), ResourceType.ATTACHMENT);
	}
}
