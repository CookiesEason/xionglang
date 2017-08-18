<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/common/private/inc.jsp"%>
<style>
table tr {
    line-height: 39px;
}
</style>
<form id="passwordId" method="post" novalidate>
	<table style="margin: 10px auto;">
		<c:if test="${passWordId==1||passWordId==2}">
			<tr>
				<td>旧密码:</td>
				<td>
					<input name="oldPassword" type="password" class="easyui-validatebox" required="true" />
				</td>
			</tr>
		</c:if>
		<c:if test="${passWordId==3 || passWordId==4}">
			<tr>
				<td>用户ID:</td>
				<td>
					<input name="oldPassword" type="password" class="easyui-validatebox" required="true" />
				</td>
			</tr>
			<tr>
				<td>用户名:</td>
				<td>
					<input name="oldPassword" type="password" class="easyui-validatebox" required="true" />
				</td>
			</tr>
		</c:if>
		<tr>
			<td>新密码:</td>
			<td>
				<input name="newPassword" id="newPassword" type="password" class="easyui-validatebox"
					required="true" />
			</td>
		</tr>
		<tr>
			<td>确认新密码:</td>
			<td>
				<input name="checPassword" type="password" class="easyui-validatebox" required="true"
					validType="depCh['newPassword']" />
			</td>
		</tr>
	</table>
</form>