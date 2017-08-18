<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page session="true"%>
<%@ include file="/WEB-INF/views/common/private/inc.jsp"%>
<legend>菜单基本信息</legend>
<table class="table" id="normortable">
	<tr>
		<th>菜单名</th>
		<td>${role.name}"</td>
	</tr>
	<tr>
		<th>菜单路径</th>
		<td>${role.url}</td>
	</tr>
	<tr>
		<th>菜单方法</th>
		<td>${role.function}</td>
	</tr>
	<tr>
		<th>父级菜单名</th>
		<td>
			<c:if test="${role.father!=null && menu.father!='' }" var="menuflag">${role.father.name}</c:if>
			<c:if test="${!menuflag}">根菜单</c:if>
		</td>
	</tr>
	<tr>
		<th>所属角色</th>
		<td>
			<c:forEach items="${role.roles}" var="role">
				<span>${role.name} &nbsp;&nbsp;</span>
			</c:forEach>
		</td>
	</tr>
	<tr>
		<th>菜单创立时间</th>
		<td>${role.createTime}</td>
	</tr>
</table>
