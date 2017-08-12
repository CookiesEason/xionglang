<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/common/private/inc.jsp"%>
<c:url value="/private/update/role" var="private_role" />
<c:url value="/private/menutree/home" var="private_login" />
<c:url value="/public/menutree/home" var="public_login" />
<c:if test='${roleId==1}'>
	<c:forEach items="${adminUser.roles}" var="role">
		<div style="float: left;padding: 10px 10px 0 10px;">
			<a href='javascript:;' onclick="changeRoleAjax('${roleId}','${role.id}','${private_role}','POST','JSON','${private_login}')">
				<img alt="" width="90" height="90" src="<c:url value='/resources/image/icon1.png' />"> <br /><span style="padding: 5px 0 20px 20px">${role.name}</span>
			</a>
		</div>
	</c:forEach>
	<c:if test="${adminUser.roles.size()==0}">
		<div style="text-align: center;margin: 30px auto;"><span style="color: red;font-weight: bold;">您暂无可以切换的</span></div>
	</c:if>
</c:if>
<c:if test='${roleId==2}'>
	<c:forEach items="${user.roles}" var="role">
		<div style="float: left;padding: 10px 10px 0 10px;">
			<a href='javascript:;' onclick="changeRoleAjax('${roleId}','${role.id}','${private_role}','POST','JSON',${public_login}')">
				<img alt="" width="90" height="90" src="<c:url value='/resources/image/icon1.png' />"> <br /><span style="padding: 5px 0 20px 20px">${role.name}</span>
			</a>
		</div>
	</c:forEach>
	<c:if test="${user.roles.size()==0}">
		<div style="text-align: center;margin: 30px auto;"><span style="color: red;font-weight: bold;">您暂无可以切换的</span></div>
	</c:if>
</c:if>
