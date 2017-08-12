package com.sunxl.base.util;

import java.io.IOException;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn
 * @Time：2017年7月13日 @此类作用：具体读取那个文件的配置信息
 */
public class SunXlProperties extends SystemProperties {

	public static final String FILE_ROOT = "system.file.config.root";

	public static final String FILE_IMAGE = "system.file.config.image";

	public static final String FILE_EXCEL = "system.file.config.xls";

	public static final String FILE_DOC = "system.file.config.doc";

	public static final String FILE_ATTACHMENT = "system.file.config.attachment";

	private static SunXlProperties properties;

	/**
	 * 使用相对路径，默认读取META-INF/system.properties配置文件
	 */
	public SunXlProperties() throws IOException {
		super();
	}

	public SunXlProperties(String path) throws IOException {
		super(path);
	}

	/**
	 * path全路径包含项目名 指定读取哪个 路径下的property文件信息 使用绝对路径
	 * 
	 * @param path
	 * @throws IOException
	 */
	public SunXlProperties(String path, String projectPath) throws IOException {
		super(FileUtil.getHomePath(), path);
	}

	public static SunXlProperties getInstance() throws IOException {
		if (properties == null) {
			return properties = new SunXlProperties();
		}
		return properties;
	}
}
