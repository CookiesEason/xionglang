<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page session="true"%>
<%@ include file="/WEB-INF/views/common/private/inc.jsp"%>
<form method="post" class="form" id="idSequenceForm">
	<fieldset>
		<legend>添加索引索引</legend>
		<table class="table" id="normortable">
			<tr>
				<th>表名</th>
				<td><input name="tableName" data-options="required:true"
					class="easyui-validatebox" /></td>
			</tr>
			<tr>
				<th>类路径</th>
				<td><input name="entityKey" data-options="required:true"
					class="easyui-validatebox" /></td>
			</tr>
			<tr>
				<th>主键值</th>
				<td><input name="entityValue" data-options="required:true"
					class="easyui-validatebox" /></td>
			</tr>
			<tr>
				<th>表用途</th>
				<td><textarea name="smrytx" class="easyui-validatebox"
						style="height: 100px; width: 90%;" data-options="required:true">${act.content }</textarea></td>
			</tr>
			<tr>
				<th>表创立时间</th>
				<td><input name="createTime" class="Wdate easyui-validatebox"
					onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					readonly="readonly" style="width: 186px;"
					data-options="required:true" /></td>
			</tr>
		</table>
	</fieldset>
</form>