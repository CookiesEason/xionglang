package com.base.form;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年9月7日
 * @此类的作用：
 */
public class FilterInfoForm {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Long startTime;//访问开始时间，用于扩展在高并发时客户连接时间过长拒绝连接使用。
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public FilterInfoForm() {
		super();
		
	}
	public FilterInfoForm(HttpServletRequest request, HttpServletResponse response, Long startTime) {
		super();
		this.request = request;
		this.response = response;
		this.startTime = startTime;
	}
}
