<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page session="true"%>
<%@ include file="/WEB-INF/views/common/private/inc.jsp"%>
<form method="post" class="form" id="idRoleForm">
	<fieldset>
		<legend>角色基本信息</legend>
		<table class="table" id="normortable">
			<tr>
				<th>角色名称</th>
				<td>
					<input name="name" data-options="required:true" class="easyui-validatebox" />
				</td>
			</tr>
			<tr>
				<th>角色级别</th>
				<td>
					<input name="url" data-options="required:true" class="easyui-validatebox" />
				</td>
			</tr>
			<tr>
				<th>角色所属方向</th>
				<td>
					<input name="function" data-options="required:true" class="easyui-validatebox" />
				</td>
			</tr>
		</table>
	</fieldset>
</form>