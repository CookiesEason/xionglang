<%@page import="java.util.Properties"%>
<%@page import="com.taobao.pamirs.schedule.zk.ZKManager"%>
<%@page import="com.taobao.pamirs.schedule.ConsoleManager"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/schedule/shedule.jsp"%>
<%
	Properties p = new Properties();
	p.setProperty(ZKManager.keys.zkConnectString.toString(),request.getParameter(ZKManager.keys.zkConnectString.toString()));
	p.setProperty(ZKManager.keys.rootPath.toString(), request.getParameter(ZKManager.keys.rootPath.toString()));
	p.setProperty(ZKManager.keys.userName.toString(), request.getParameter(ZKManager.keys.userName.toString()));
	p.setProperty(ZKManager.keys.password.toString(), request.getParameter(ZKManager.keys.password.toString()));
	p.setProperty(ZKManager.keys.zkSessionTimeout.toString(),request.getParameter(ZKManager.keys.zkSessionTimeout.toString()));
	try {
		ConsoleManager.saveConfigInfo(p);
		ConsoleManager.initial();
		ConsoleManager.getScheduleDataManager();
	} catch (Exception e) {
		e.printStackTrace();
		response.sendRedirect(request.getContextPath()+"/private/schedule/path?path=private.schedule.config&error=" + e.getMessage());
	}
%>
<script>
	if (parent != null) {
		location.href = syschedule.contextPath+"/private/schedule/path?path=private.schedule.goToSchedule&manager=true";
	} else {
		location.href = syschedule.contextPath+"/private/schedule/path?path=private.schedule.goToSchedule&manager=true";
	}
</script>
