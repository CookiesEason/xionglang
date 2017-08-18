<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page session="true"%>
<%@ include file="/WEB-INF/views/common/private/inc.jsp"%>
<form method="post" class="form" id="idSysTidDataForm">
	<fieldset>
		<legend>系统基本信息</legend>
		<table class="table" id="normortable">
			<tr>
				<th>系统来源</th>
				<td>
					<input name="name" data-options="required:true" class="easyui-validatebox" />
				</td>
			</tr>
			<tr>
				<th>系统来源路径</th>
				<td>
					<input name="url" data-options="required:true" class="easyui-validatebox" />
				</td>
			</tr>
			<tr>
				<th>系统添加时间</th>
				<td>
					<input name="createTime" class="Wdate easyui-validatebox"
						onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly"
						style="width: 186px;" data-options="required:true" />
				</td>
			</tr>
		</table>
	</fieldset>
</form>