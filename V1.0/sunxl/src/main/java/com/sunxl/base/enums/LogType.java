package com.sunxl.base.enums;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月12日
 * @此类作用：日志类型
 */
public enum LogType {

	LOGPUBLICTYPE(1), LOGPRIVATETYPE(2);
	private int logType;

	private LogType(int logType) {
		this.logType = logType;
	}

	public int getLogType() {
		return logType;
	}
}
