package com.sunxl.base.util;

import java.io.Serializable;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月13日
 * @此类作用：后台返回JSON到前台
 */
public class JSON implements Serializable {
	private static final long serialVersionUID = 1L;

	private boolean success = false;

	private String msg = "";

	private Object obj = null;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
}
