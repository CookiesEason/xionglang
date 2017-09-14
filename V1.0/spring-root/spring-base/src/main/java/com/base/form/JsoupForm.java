package com.base.form;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年9月2日
 * @此类的作用：
 */
public class JsoupForm {
	private String url;
	private String userAgent;
	private String method;
	private int timeOut;
	private String filePath;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public int getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
