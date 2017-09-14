package com.base.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年8月24日 @此类的作用：
 */
public class SunxlRuntimeException extends RuntimeException {
	private static final Logger log = LoggerFactory.getLogger(SunxlRuntimeException.class);
	private static final long serialVersionUID = 866412930L;
	private String errno;
	private String errmsg;
	private String exceptionname;
	private String path;

	protected void finalize() {
	}

	public String getErrmsg() {
		return this.errmsg;
	}

	public String getErrno() {
		return this.errno;
	}

	public void setErrno(String newErrno) {
		this.errno = newErrno;
	}

	public void setErrmsg(String newErrmsg) {
		this.errmsg = newErrmsg;
	}

	public String getExceptionname() {
		return this.exceptionname;
	}

	public void setExceptionname(String newExceptionname) {
		this.exceptionname = newExceptionname;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String newPath) {
		this.path = newPath;
	}

	public String toString() {
		return this.errmsg;
	}

	public SunxlRuntimeException(String message) {
		this("error", message, "");

	}

	public SunxlRuntimeException(String errNo, String errMsg) {
		this(errNo, errMsg, "");
	}

	public SunxlRuntimeException(String errNo, String errMsg, String func) {
		super(errMsg);

		this.path = null;

		this.errno = errNo;
		this.errmsg = errMsg;
		if (func != null) {
			SunxlRuntimeException tmp32_31 = this;
			tmp32_31.path = tmp32_31.path + ">" + func;
		}
	}

	public SunxlRuntimeException(String errNo, String errMsg, String func, Exception exception) {
		this(errNo, errMsg, func);
		if (!(exception instanceof SunxlRuntimeException)) {
			log.error(errMsg, exception);
		}
	}

	public SunxlRuntimeException(String func, Exception exception) {
		this("0000", "系统程序出错!" + exception, func, exception);
	}

	public SunxlRuntimeException(String errNo, String errMsg, Exception e) {
		this(errNo, errMsg, null, e);
	}

	public void addPath(String func) {
		if (this.path != null)
			this.path = func + ">" + this.path;
		else
			this.path = func;
	}
}
