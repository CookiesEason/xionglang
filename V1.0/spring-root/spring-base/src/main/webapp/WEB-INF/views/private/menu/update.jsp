<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page session="true"%>
<%@ include file="/WEB-INF/views/common/private/inc.jsp"%>
<form method="post" class="form" id="idMenuForm">
	<fieldset>
		<legend style="font-size: 14px; font-family: 'Microsoft YaHei';">索引基本信息</legend>
		<table class="table"
			style="width: 100%; font-family: 'Microsoft YaHei'; font-size: 14px; font-weight: bold;">
			<tr>
				<th>菜单名 <input type="hidden" value="${menu.id}" name="id" /> <input
					type="hidden" value="${menu.version}" name="version" />
				</th>
				<td><input value="${menu.name}" name="name"
					data-options="required:true" class="easyui-validatebox" /></td>
			</tr>
			<tr>
				<th>菜单路径</th>
				<td><input value="${menu.url}" name="url"
					data-options="required:true" /></td>
			</tr>
			<tr>
				<th>菜单对应的方法</th>
				<td><input value="${menu.function}" name="function"
					data-options="required:true" /></td>
			</tr>
			<tr>
				<th>父级菜单</th>
				<td><select id="fatherMenu" name="father.id"
					class="easyui-combobox">
						<c:if test="${menu.father.id==null}">
							<option value="-1" selected="selected">请选择父级菜单</option>
						</c:if>
						<c:forEach items="${menus}" var="m" varStatus="i">
							<option value="${m.id}"
								<c:if test='${m.id==menu.father.id}'>selected="selected"</c:if>>${m.name}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<th>所属角色</th>
				<td><c:forEach items="${roles}" var="role" varStatus="i">
						<label> <input type="checkbox"
							<c:forEach items="${menu.roles}" var="r" varStatus="j"><c:if test="${role.id==r.id}">checked="checked"</c:if></c:forEach>
							name="rolesId" value="${role.id}" /> <span>${role.name}</span>
						</label>
					</c:forEach></td>
			</tr>
			<tr>
				<th>所属板块</th>
				<td><label> <input type="radio" name="type"
						<c:if test="${menu.type==0}">checked="checked"</c:if> value="0">
						<span>前台</span>
				</label> <label> <input type="radio" name="type"
						<c:if test="${menu.type==1}">checked="checked" </c:if> value="1">
						<span>前台会员</span>
				</label> <label> <input type="radio" name="type"
						<c:if test="${menu.type==2}">checked="checked" </c:if> value="1">
						<span>后台</span>
				</label></td>
			</tr>
		</table>
	</fieldset>
</form>