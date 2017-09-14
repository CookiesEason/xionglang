<%@page import="com.taobao.pamirs.schedule.ConsoleManager"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/schedule/shedule.jsp"%>
<%
	boolean isRefreshParent = false;
	String result="";
	String action = request.getParameter("action");
	String uuid = request.getParameter("uuid");
	try {
 		if (action.equalsIgnoreCase("startManagerFactory")) {
 			ConsoleManager.getScheduleStrategyManager().updateManagerFactoryInfo(uuid,true);
			isRefreshParent = true;
		} else if (action.equalsIgnoreCase("stopManagerFactory")) {
			ConsoleManager.getScheduleStrategyManager().updateManagerFactoryInfo(uuid,false);
			isRefreshParent = true;
		}else{
			throw new Exception("不支持的操作：" + action);
		}
	} catch (Throwable e) {
		e.printStackTrace();
		result ="ERROR:" + e.getMessage(); 
		isRefreshParent = false;
	}
%>
<%=result%>
<% if(isRefreshParent == true){ %>
<script>
 location.reload();
</script>
<%}%>