<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page session="true"%>
<%@ include file="/WEB-INF/views/common/private/inc.jsp"%>
<form method="post" class="form" id="idExportDataForm">
	<fieldset>
		<legend style="font-size: 14px; font-family: 'Microsoft YaHei';">索引基本信息</legend>
		<table class="table"
			style="width: 100%; font-family: 'Microsoft YaHei'; font-size: 14px; font-weight: bold;">
			<tr>
				<th>
					表名
					<input type="hidden" value="${idSequence.id}" name="id" />
				</th>
				<td>
					<input value="${idSequence.tableName}" name="tableName" data-options="required:true"
						class="easyui-validatebox" />
				</td>
			</tr>
			<tr>
				<th>类路径</th>
				<td>
					<input value="${idSequence.entityKey}" name="entityKey" data-options="required:true"
						class="easyui-validatebox" />
				</td>
			</tr>
			<tr>
				<th>主键值</th>
				<td>
					<input value="${idSequence.entityValue}" name="entityValue" data-options="required:true"
						class="easyui-validatebox" />
				</td>
			</tr>
			<tr>
				<th>表用途</th>
				<td>
					<textarea name="smrytx" class="easyui-validatebox" style="height: 100px; width: 90%;"
						data-options="required:true">${idSequence.smrytx}</textarea>
				</td>
			</tr>
			<tr>
				<th>表创立时间</th>
				<td>
					<input name="createTime" value="${idSequence.createTime}" class="Wdate easyui-validatebox"
						onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly"
						style="width: 120px;" data-options="required:true" />
				</td>
			</tr>
		</table>
	</fieldset>
</form>