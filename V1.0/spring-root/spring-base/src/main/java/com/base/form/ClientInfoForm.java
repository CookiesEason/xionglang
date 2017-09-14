package com.base.form;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年8月25日 @此类的作用：
 */
public class ClientInfoForm {
	private String IP;// 客户端IP
	private String clientName;// 电脑名
	private String agent;// 浏览器版本
	private String url;// 客户端的请求路径
	private String servletUrl;

	public String getIP() {
		return IP;
	}

	public String getClientName() {
		return clientName;
	}

	public String getAgent() {
		return agent;
	}

	public String getUrl() {
		return url;
	}

	public String getServletUrl() {
		return servletUrl;
	}

	public ClientInfoForm(HttpServletRequest request) {
		this.IP = request.getRemoteAddr();
		this.clientName = request.getRemoteHost();
		this.agent = request.getHeader("user-agent");
		this.url = request.getRequestURI();
		this.servletUrl = request.getServletPath();
	}
}
