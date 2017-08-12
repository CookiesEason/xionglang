<%@page import="java.io.*"%>
<%@page import="com.taobao.pamirs.schedule.ConsoleManager"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/schedule/shedule.jsp"%>
<%
	String rootPath = request.getAttribute("rootPathString") != null ? request.getAttribute("rootPathString").toString() : ConsoleManager.getScheduleStrategyManager().getRootPath();;
	StringWriter errWriter = request.getAttribute("errWriterString") != null ? (StringWriter) request.getAttribute("errWriterString") : new StringWriter();
	StringWriter confWriter = request.getAttribute("confWriterString") != null ? (StringWriter) request.getAttribute("confWriterString") : new StringWriter();
%>
<form id="taskTypeForm" method="post" name="taskTypeForm"
	action="<%=request.getContextPath()%>/private/schedule/path">
	<input type="hidden" name="path" value="private.schedule.exportConfig" />
	<input type="hidden" id="type" name="type" value="1" /> 配置文件路径：<input
		type="text" name="rootPath"
		value="<%=rootPath == null ? "" : rootPath%>" style="width: 330px;" />
	<input type="button" onclick="viewConfig();" value="查看" /> <input
		type="button" onclick="saveConfig();" value="导出" />
	<pre>
<%
	if (errWriter == null || errWriter.getBuffer().length() == 0) {
%>
<%=confWriter%>
<%
	}
%>
</pre>
	<h3><%=errWriter == null ? "" : errWriter%></h3>
</form>
<script>
	// 查看配置文件 
	function viewConfig() {
		document.getElementById("type").value = "0";
		document.getElementById("taskTypeForm").submit();
	}
	// 导出配置文件
	function saveConfig() {
		document.getElementById("type").value = "1";
		document.getElementById("taskTypeForm").submit();
	}
</script>
