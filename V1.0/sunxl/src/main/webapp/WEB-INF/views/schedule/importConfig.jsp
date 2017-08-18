<%@page import="java.io.*"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/schedule/shedule.jsp"%>
<%
	String configContent = request.getAttribute("configContentString") != null ? request.getAttribute("configContentString").toString() : "";
	boolean isUpdate = request.getAttribute("isUpdateString") != null ? Boolean.parseBoolean(request.getAttribute("errWriterString").toString()) : false;
	StringWriter writer = request.getAttribute("writerString") != null ? (StringWriter) request.getAttribute("confWriterString") : new StringWriter();
%>
<!-- encType="multipart/form-data" -->
<form id="taskTypeForm" method="POST" name="taskTypeForm"
	action="<%=request.getContextPath()%>/private/schedule/path">
	<input type="hidden" name="path" value="private.schedule.importConfig" />
	<pre style="width: 100px; float: left;">配置文本信息：</pre>
	<textarea name="configContent" style="width: 1000px; height: 150px;"><%=configContent%></textarea>
	<br /> 是否强制更新：&nbsp;&nbsp;
	<select name="isUpdate">
		<option value="true" <%if (isUpdate) {%> selected <%}%>>是</option>
		<option value="false" <%if (!isUpdate) {%> selected <%}%>>否</option>
	</select>
	<input type="button" onclick="importConfig();" value="导入配置" />
</form>
<pre>	
</pre>
<h3><%=writer.toString()%></h3>
<script>
	// 导入配置文件
	function importConfig() {
		document.getElementById("taskTypeForm").submit();
	}
	function insertTitle(tValue) {
		var t1 = tValue.lastIndexOf("\\");
		var t2 = tValue.lastIndexOf(".");
		if (t1 >= 0 && t1 < t2 && t1 < tValue.length) {
			document.getElementById("fileName").value = tValue.substring(t1 + 1);
		}
	}
</script>

