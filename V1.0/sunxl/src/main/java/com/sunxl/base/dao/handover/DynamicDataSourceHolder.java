package com.sunxl.base.dao.handover;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月26日
 * @此类作用：
 */
public class DynamicDataSourceHolder {
	/**
	 * 注意：数据源标识保存在线程变量中，避免多线程操作数据源时互相干扰
	 */
	private static final ThreadLocal<String> THREAD_DATA_SOURCE = new ThreadLocal<String>();

	public static String getDataSource() {
		return THREAD_DATA_SOURCE.get();
	}

	public static void setDataSource(String dataSource) {
		THREAD_DATA_SOURCE.set(dataSource);
	}

	public static void clearDataSource() {
		THREAD_DATA_SOURCE.remove();
	}
}
