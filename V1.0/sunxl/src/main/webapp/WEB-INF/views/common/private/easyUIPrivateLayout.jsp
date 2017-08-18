<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.sunxl.base.util.WebKeys"%>
<%@page import="com.sunxl.base.entity.AdminUser"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/views/common/private/inc.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head></head>
<body id="mainLayout" class="easyui-layout" style="font-size: 14px;">
	<div data-options="region:'north'" style="height: 99px; overflow: hidden;" class="logo">
		<tiles:insertAttribute name="header" />
	</div>
	<div data-options="region:'west',href:'',split:true" title="导航"
		style="width: 200px; font-family: 'Microsoft YaHei'; font-size: 14px;">
		<div id="aa" class="easyui-accordion">
			<c:forEach items="${menuTree }" var="mTree">
				<div title="${mTree.name }" data-options="iconCls:'ext-icon-computer'" style="overflow: auto;">
					<ul style="list-style-type: none;">
						<c:forEach items="${mTree.subMenus }" var="subTree">
							<li>
								<a
									href="javascript:switchTab('${subTree.name }','<%=contextPath%>${subTree.url }','ext-icon-group');"
									id="a_corpor" style="text-decoration: none; color: black;">
									<span class="l-btn-text ext-icon-group l-btn-icon-left">&nbsp;&nbsp;&nbsp;&nbsp; </span>
									<span>${subTree.name }</span>
								</a>
							</li>
						</c:forEach>
					</ul>
				</div>
			</c:forEach>
		</div>
	</div>
	<div data-options="region:'center'" style="overflow: hidden;">
		<div class="easyui-tabs" id="div_tabs">
			<div title="欢迎您！" style="padding: 20px;">
				<%
					AdminUser adminUser = (AdminUser) session.getAttribute(WebKeys.ADMINUSER_KEY);
					if (adminUser != null && adminUser.getRole() != null) {
				%>
				<h3 style="color: #0099FF;">欢迎您进入XXX后台管理中心</h3>
				<div style="background: #fafafa; padding: 5px; width: 200px; border: 1px solid #ccc">
					<a href="#" class="easyui-menubutton" menu="#mm1" iconCls="ext-icon-arrow_switch">快捷通道</a>
				</div>
				<div id="mm1" style="width: 150px;">
					<div iconCls="ext-icon-group"
						onclick="javascript:switchTab('企业管理','${goCorPorList}','ext-icon-group');">企业管理</div>
					<div class="menu-sep"></div>
					<div iconCls="ext-icon-group"
						onclick="javascript:switchTab('银行管理','<c:url value="/private/finance/bankView"/>','ext-icon-group')">银行管理</div>
					<div iconCls="ext-icon-group"
						onclick="javascript:switchTab('投资机构管理','<c:url value="/private/investment/goInvestment"/>','ext-icon-group')">投资机构管理</div>
					<div class="menu-sep"></div>
					<div iconCls="ext-icon-group"
						onclick="javascript:switchTab('中介管理','${goAgency}','ext-icon-group');">中介机构管理</div>
				</div>
				<%
					}
				%>
			</div>
			<tiles:insertAttribute name="body" />
		</div>
	</div>
	<div data-options="region:'south',border:false" style="height: 30px; overflow: hidden;">
		<tiles:insertAttribute name="footer" />
	</div>
</body>
</html>