<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page session="true"%>
<%@ include file="/WEB-INF/views/common/private/inc.jsp"%>
<form method="post" class="form" id="idRoleForm">
	<fieldset>
		<legend>索引基本信息</legend>
		<table class="table" id="normortable">
			<tr>
				<th>表名<input type="hidden" value="${role.id}" name="id" /></th>
				<td><input value="${role.tableName}" name="tableName"
					data-options="required:true" class="easyui-validatebox" /></td>
			</tr>
			<tr>
				<th>类路径</th>
				<td><input value="${role.entityKey}" name="entityKey"
					data-options="required:true" class="easyui-validatebox" /></td>
			</tr>
			<tr>
				<th>主键值</th>
				<td><input value="${role.entityValue}" name="entityValue"
					data-options="required:true" class="easyui-validatebox" /></td>
			</tr>
			<tr>
				<th>表用途</th>
				<td><textarea name="smrytx" class="easyui-validatebox"
						style="height: 100px; width: 90%;" data-options="required:true">${role.smrytx}</textarea></td>
			</tr>
			<tr>
				<th>表创立时间</th>
				<td><input name="createTime" value="${role.createTime}"
					class="Wdate easyui-validatebox"
					onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					readonly="readonly" style="width: 120px;"
					data-options="required:true" /></td>
			</tr>
		</table>
	</fieldset>
</form>