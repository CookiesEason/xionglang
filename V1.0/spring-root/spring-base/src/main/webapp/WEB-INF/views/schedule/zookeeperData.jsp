<%@page import="java.io.StringWriter"%>
<%@page import="com.taobao.pamirs.schedule.ConsoleManager"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/schedule/shedule.jsp"%>
<%
	StringWriter writer = new StringWriter();
	ConsoleManager.getScheduleStrategyManager().printTree(ConsoleManager.getScheduleStrategyManager().getRootPath(), writer, "<br/>");
%>
<pre>
<%=writer.getBuffer().toString()%>
</pre>
