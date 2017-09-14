<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page session="true"%>
<%@ include file="/WEB-INF/views/common/private/inc.jsp"%>
<form method="post" class="form" id="idMenuForm">
	<fieldset>
		<legend>添加菜单</legend>
		<table class="table" id="normortable">
			<tr>
				<th>菜单名</th>
				<td><input name="name" data-options="required:true"
					class="easyui-validatebox" /></td>
			</tr>
			<tr>
				<th>菜单路径</th>
				<td><input name="url" data-options="required:true"
					class="easyui-validatebox" /></td>
			</tr>
			<tr>
				<th>菜单方法</th>
				<td><input name="function" data-options="required:true"
					class="easyui-validatebox" /></td>
			</tr>
			<tr>
				<th>父级菜单名</th>
				<td><select id="fatherMenu" name="father.id"
					class="easyui-combobox">
						<option value="-1" selected="selected">请选择父级菜单</option>
						<c:forEach items="${menus}" var="menu" varStatus="i">
							<option value="${menu.id}">${menu.name}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<th>所属角色</th>
				<td><c:forEach items="${roles}" var="role" varStatus="i">
						<label> <input type="checkbox" name="rolesId"
							value="${role.id}"> <span>${role.name}</span>
						</label>
					</c:forEach></td>
			</tr>
			<tr>
				<th>所属板块</th>
				<td><label> <input type="radio" name="type" value="0">
						<span>前台</span>
				</label> <label> <input type="radio" name="type" value="1">
						<span>前台会员</span>
				</label> <label> <input type="radio" name="type" value="2">
						<span>后台</span>
				</label></td>
			</tr>
			<tr>
				<th>菜单创立时间</th>
				<td><input name="createTime" class="Wdate easyui-validatebox"
					onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					readonly="readonly" style="width: 186px;"
					data-options="required:true" /></td>
			</tr>
		</table>
	</fieldset>
</form>