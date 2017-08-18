<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<c:url value='/admin/login' var="login" />
<c:url value='/register' var="register" />
<c:url value='/captcha' var="captcha" />
<script type="text/javascript">
	function refresh() {
		$("#captcha1").attr("src", "${captcha}?" + Math.random());
	}

	$(function() {
		var loginFun = function() {
			if ($('.form').form('validate')) {
				$('#loginBtn').linkbutton('disable');
				$.post('${login}', $('.form').serialize(), function(result) {
					if (result.success) {
						location.replace(sy.contextPath + '/private/menutree/home');
					} else {
						layer.msg(result.msg, 1, 8, function() {
							$('#loginBtn').linkbutton('enable');
						});
					}
				}, 'json');
			}
		};
		$('#loginDialog').show().dialog({
			modal : false,
			closable : false,
			iconCls : 'ext-icon-lock',
			buttons : [ {
				id : 'loginBtn',
				text : '登录',
				handler : function() {
					loginFun();
				}
			} ],
			onOpen : function() {
				$('form :input:first').focus();
				$('form :input').keyup(function(event) {
					if (event.keyCode == 13) {
						loginFun();
					}
				});
			}
		});
	});
</script>
<div id="loginDialog" closed="true" title="XXX后台系统登录"
	style="width: 320px; height: 180px; overflow: hidden;">
	<div title="用户输入模式" style="overflow: hidden; padding: 10px;">
		<form method="post" class="form">
			<table class="table" style="width: 100%; height: 100%;">
				<tr>
					<th width="50">登录名</th>
					<td>
						<input name="userName" class="easyui-validatebox" data-options="required:true"
							value="${adminuser }" style="width: 210px;" />
					</td>
				</tr>
				<tr>
					<th>密码</th>
					<td>
						<input name="passWord" type="password" class="easyui-validatebox" data-options="required:true"
							value="123456" style="width: 210px;" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>