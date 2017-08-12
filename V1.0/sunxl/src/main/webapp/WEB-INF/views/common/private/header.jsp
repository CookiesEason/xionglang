<%@page import="com.sunxl.base.util.WebKeys"%>
<%@page import="com.sunxl.base.entity.AdminUser"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:url value="/password" var="v_password" />
<c:url value="/private/update/password" var="private_password" />
<c:url value="/public/update/password" var="public_password" />
<c:url value="/role" var="v_role" />
<div id="sessionInfoDiv"
	style="position: absolute; right: 10px; top: 5px;">
	<%
		AdminUser adminUser = (AdminUser) session.getAttribute(WebKeys.ADMINUSER_KEY);
		StringBuffer info = new StringBuffer();
		if (adminUser.getRole() != null)
			info.append("欢迎您：" + adminUser.getRole().getName() + "，" + adminUser.getUserName());
		else
			info.append("欢迎您：" + adminUser.getUserName() + "，<span style=\"color:red;\">但您暂无角色信息，请先让管理员赋予角色</span>");
	%>
	<%=info.toString()%>
</div>
<div style="position: absolute; right: 0px; bottom: 0px;">
	<a href="javascript:void(0);" class="easyui-menubutton"
		data-options="menu:'#layout_north_pfMenu',iconCls:'ext-icon-rainbow'">更换皮肤</a>
	<a href="javascript:void(0);" class="easyui-menubutton"
		data-options="menu:'#layout_north_kzmbMenu',iconCls:'ext-icon-cog'">控制面板</a>
	<a href="javascript:void(0);" class="easyui-menubutton"
		data-options="menu:'#layout_north_zxMenu',iconCls:'ext-icon-disconnect'">注销</a>
</div>
<div id="layout_north_pfMenu" style="width: 120px; display: none;">
	<div onclick="sy.changeTheme('default');" title="default">default</div>
	<div onclick="sy.changeTheme('gray');" title="gray">gray</div>
	<div onclick="sy.changeTheme('metro');" title="metro">metro</div>
	<div onclick="sy.changeTheme('bootstrap');" title="bootstrap">bootstrap</div>
	<div onclick="sy.changeTheme('black');" title="black">black</div>
	<div class="menu-sep"></div>
	<div onclick="sy.changeTheme('metro-blue');" title="metro-blue">metro-blue</div>
	<div onclick="sy.changeTheme('metro-gray');" title="metro-gray">metro-gray</div>
	<div onclick="sy.changeTheme('metro-green');" title="metro-green">metro-green</div>
	<div onclick="sy.changeTheme('metro-orange');" title="metro-orange">metro-orange</div>
	<div onclick="sy.changeTheme('metro-red');" title="metro-red">metro-red</div>
</div>
<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
	<div data-options="iconCls:'ext-icon-user_edit'"
		onclick="changePassword('修改密码','${v_password}','1','#passwordId','${private_password}','POST','JSON');">修改密码</div>
	<%
		if (adminUser.getRole() != null && (adminUser.getRole().getName().equals("admin") || adminUser.getRole().getName().equals("开发者"))) {
	%>
	<div data-options="iconCls:'ext-icon-user_edit'"
		onclick="changePassword('管理员修改后台用户密码','${v_password}','3','#passwordId','${private_password}','POST','JSON');">管理员修改后台用户密码</div>
	<div data-options="iconCls:'ext-icon-user_edit'"
		onclick="changePassword('管理员修改前台用户密码','${v_password}','4','#passwordId','${public_password}','POST','JSON');">管理员修改前台用户密码</div>
	<%
		}
	%>
	<div data-options="iconCls:'ext-icon-user_edit'"
		onclick="changeRole('切换角色','${v_role}/1');">切换角色</div>
</div>
<div id="layout_north_zxMenu" style="width: 100px; display: none;">
	<div data-options="iconCls:'ext-icon-user_suit'"
		onclick="showMyInfoFun('/window/2','我的信息');">用户信息</div>
	<div data-options="iconCls:'ext-icon-lock'"
		onclick="lockWindowFun('#loginDialog','/window/3','#overlay');">锁定窗口</div>
	<div data-options="iconCls:'ext-icon-door_out'"
		onclick="logoutFun('/window/4','/admin/login');">退出系统</div>
</div>