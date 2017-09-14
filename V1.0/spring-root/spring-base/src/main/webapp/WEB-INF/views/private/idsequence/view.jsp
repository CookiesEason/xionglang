<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page session="true"%>
<jsp:include page="/WEB-INF/views/common/private/inc.jsp" />
<legend>索引基本信息</legend>
<table class="table" id="normortable">
	<tr>
		<th>表名</th>
		<td>${idSequence.tableName}"</td>
	</tr>
	<tr>
		<th>类路径</th>
		<td>${idSequence.entityKey}</td>
	</tr>
	<tr>
		<th>主键值</th>
		<td>${idSequence.entityValue}</td>
	</tr>
	<tr>
		<th>表用途</th>
		<td>${idSequence.smrytx}</td>
	</tr>
	<tr>
		<th>表创立时间</th>
		<td>${idSequence.createTime}</td>
	</tr>
</table>