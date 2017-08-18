<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page session="true"%>
<%@ include file="/WEB-INF/views/common/private/inc.jsp"%>
<legend>菜单基本信息</legend>
<table class="table" id="normortable">
	<tr>
		<th>菜单名</th>
		<td>${menu.name}"</td>
	</tr>
	<tr>
		<th>菜单路径</th>
		<td>${menu.url}</td>
	</tr>
	<tr>
		<th>菜单方法</th>
		<td>${menu.function}</td>
	</tr>
	<tr>
		<th>父级菜单名</th>
		<td>
			<c:if test="${menu.father!=null && menu.father!='' }" var="menuflag">${menu.father.name}</c:if>
			<c:if test="${!menuflag}">根菜单</c:if>
		</td>
	</tr>
	<tr>
		<th>所属角色</th>
		<td>
			<c:forEach items="${menu.roles}" var="role">
				<span>${role.name} &nbsp;&nbsp;</span>
			</c:forEach>
		</td>
	</tr>
	<tr>
		<th>菜单创立时间</th>
		<td>${menu.createTime}</td>
	</tr>
</table>
