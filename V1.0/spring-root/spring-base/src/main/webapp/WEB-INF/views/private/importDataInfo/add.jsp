<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page session="true"%>
<%@ include file="/WEB-INF/views/common/private/inc.jsp"%>
<style>
.form input, .form select, .form textarea {
	width: 120px;
}
</style>
<form method="post" class="form" id="idImportDataInfoForm">
	<fieldset>
		<legend>添加导数配置</legend>
		<table class="table" id="normortable">
			<tr>
				<th>文件名前缀</th>
				<td><input name="importInfoId" data-options="required:true"
					class="easyui-validatebox" /></td>
				<th>导数配置名称</th>
				<td><input name="importInfoName" data-options="required:true"
					class="easyui-validatebox" /></td>
			</tr>
			<tr>
				<th>文件系统来源</th>
				<td><select name="systid.id" class="easyui-combobox">
						<c:forEach items="${sysTidDatas}" var="sysTidData" varStatus="i">
							<option value="${sysTidData.id}"
								<c:if test='${i.index==0}'>selected="selected"</c:if>>${sysTidData.name}</option>
						</c:forEach>
				</select></td>
				<th>导数配置状态</th>
				<td><select name="type" class="easyui-combobox">
						<c:forEach items="${sunxl_type}" var="sysData" varStatus="i">
							<option value="${sysData.value}"
								<c:if test='${sysData.intData==0}'>selected="selected"</c:if>>${sysData.paraOne}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<th>文件编码</th>
				<td><select name="chartSet" class="easyui-combobox">
						<option value="GBK" selected="selected">GBK</option>
						<option value="GBK">UTF-8</option>
						<option value="GBK2312">GBK2312</option>
				</select></td>
				<th>分隔符</th>
				<td><input name="splitChar" placeholder="只支持,和|组合"
					data-options="required:true" class="easyui-validatebox" /></td>
			</tr>
			<tr>
				<th>表名</th>
				<td><input name="tableName" data-options="required:true"
					class="easyui-validatebox" /></td>
				<th>指令</th>
				<td><input name="certty" data-options="required:false"
					class="easyui-validatebox" /></td>
			</tr>
			<tr>
				<th>导数文件路径</th>
				<td colspan="3"><input name="path" style="width: 90%"
					data-options="required:true" class="easyui-validatebox" /></td>
			</tr>
			<tr>
				<th>摘要</th>
				<td colspan="3"><textarea name="smrytx"
						class="easyui-validatebox" style="height: 100px; width: 90%;"
						data-options="required:true"></textarea></td>
			</tr>
		</table>
	</fieldset>
</form>