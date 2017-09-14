<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page session="true"%>
<%@ include file="/WEB-INF/views/common/private/inc.jsp"%>
<legend>菜单基本信息</legend>
<table class="table" id="normortable">
	<tr>
		<th>用户</th>
		<td>${adminUser.id}"</td>
		<th>用户名</th>
		<td>${adminUser.userName}</td>
	</tr>
	<tr>
		<th>用户真名</th>
		<td>${adminUser.realName}</td>
		<th>Email</th>
		<td>${adminUser.email}</td>
	</tr>
	<tr>
		<th>用户号码</th>
		<td>${adminUser.mobilePhone}</td>
		<th>用户部门</th>
		<td>${adminUser.dept.deptName}</td>
	</tr>
	<tr>
		<th>用户默认角色</th>
		<td>${adminUser.role.name}</td>
		<th>是否是管理员</th>
		<td>${adminUser.isSuperAdmin}</td>
	</tr>
	<tr>
		<th>用户状态</th>
		<td>${adminUser.type}</td>
		<th>用户创立时间</th>
		<td>${adminUser.createTime}</td>
	</tr>
	<tr>
		<th>所拥有的角色</th>
		<td colspan="3"><c:forEach items="${adminUser.roles}" var="role">
				<span>${role.name} &nbsp;&nbsp;</span>
			</c:forEach></td>
	</tr>
</table>
