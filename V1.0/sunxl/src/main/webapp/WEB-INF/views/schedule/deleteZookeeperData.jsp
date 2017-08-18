<%@page import="java.io.StringWriter"%>
<%@page import="com.taobao.pamirs.schedule.ConsoleManager"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/schedule/shedule.jsp"%>
<%
	String path = request.getParameter("path");
	String action = request.getParameter("action");
	if (action == null) {
		action = "query";
	}
	if (path == null) {
		path = ConsoleManager.getScheduleStrategyManager().getRootPath();
		action = "query";
	}
	StringWriter writer = new StringWriter();
	if (action.equals("delete")) {
		try {
			ConsoleManager.getScheduleStrategyManager().deleteTree(path);
			writer.write("删除目录：" + path + "成功！");
		} catch (Exception e) {
			writer.write(e.getMessage());
		}
	} else {
		ConsoleManager.getScheduleStrategyManager().printTree(path, writer, "<br/>");
	}
%>
数据路径：
<input id="path" type="text" size="80" value="<%=path%>" />
<input type="button" value="查询" onclick="queryPath()" />
<input type="checkbox" id="openDeleteButton" onclick="openDelete()">
<input type="button" id="deleteButton" disabled="disabled" value="删除" onclick="deletePath()" />
<LABEL style="color: red">
	<b>请注意,会删除改目录及子目录的所有内容，而且不可恢复！</b>
</LABEL>
<hr />
<pre>
<%=writer.getBuffer().toString()%>
</pre>

<script>
	function queryPath() {
		var url = syschedule.contextPath + "/private/schedule/path?path=private.schedule.deleteZookeeperData&action=query&path=" + document.all("path").value;
		location.href = url;
	}
	function deletePath() {
		location.href = syschedule.contextPath + "/private/schedule/path?path=private.schedule.deleteZookeeperData&action=delete&path=" + document.all("path").value;
	}
	function openDelete() {
		if (document.all("openDeleteButton").checked == false) {
			document.all("deleteButton").disabled = true;
		} else {
			document.all("deleteButton").disabled = false;
		}
	}
</script>