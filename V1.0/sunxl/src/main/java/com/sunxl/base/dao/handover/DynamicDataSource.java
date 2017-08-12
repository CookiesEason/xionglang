package com.sunxl.base.dao.handover;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月26日
 * @此类作用：
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		// 从自定义的位置获取数据源标识
		return DynamicDataSourceHolder.getDataSource();
	}

}
