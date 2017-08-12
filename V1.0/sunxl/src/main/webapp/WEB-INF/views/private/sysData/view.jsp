<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page session="true"%>
<%@ include file="/WEB-INF/views/common/private/inc.jsp"%>
<legend>系统基本信息</legend>
<table class="table" id="normortable">
	<tr>
		<th>菜单名</th>
		<td>${sysTidData.name}"</td>
	</tr>
	<tr>
		<th>菜单路径</th>
		<td>${sysTidData.url}</td>
	</tr>
	<tr>
		<th>系统添加时间</th>
		<td>${sysTidData.createTime}</td>
	</tr>
</table>
