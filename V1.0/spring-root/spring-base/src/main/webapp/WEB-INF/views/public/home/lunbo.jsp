<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="twofive_banner">
	<div class="slideTxtBox">
		<div class="hd">
			<ul>
				<li tages="#435466" class="on"><a href="javascript:void(0);"
					class="flitvisited"> </a></li>
				<li tages="#eae1dc"><a href="javascript:void(0);"> </a></li>
				<li tages="#c3e2fe"><a href="javascript:void(0);"> </a></li>
			</ul>
		</div>
		<div class="bd">
			<ul>
				<li><span> <a href="#"> <img
							src="<c:url value="/resources/images/banner_one.jpg"/>"
							style="height: 400px;" />
					</a>
				</span></li>
				<li><span> <a href="#"> <img
							src="<c:url value="/resources/images/banner_two.JPG"/>"
							style="height: 400px;" />
					</a>
				</span></li>
				<li><span> <a href="#"> <img
							src="<c:url value="/resources/images/banner_three.jpg"/>"
							style="height: 400px;" />
					</a>
				</span></li>
			</ul>
		</div>
	</div>
	<c:if test="${user==null}">
		<div id="hydl" style="height: 280px; padding-top: 5px;">
			<div id="mfzc">
				<a href="<c:url value="/sign-up" />">免费注册</a>
			</div>
			<br />
			<form id="form_login" method="post" action="${login}" target="_self">
				<table id="tab1">
					<tr>
						<td style="width: 50px;">用户名: <input type="hidden"
							value="${realPath}" name="realPath" />
						</td>
						<td style="width: 200px;" class="tab1td2"><input
							class="ipt1 bji1" name="userName"
							style="width: 196px; line-height: normal;" id="username" /></td>
						<td style=""><span id="erroruser"></span></td>
					</tr>
					<tr>
						<td>密码:</td>
						<td class="tab1td2"><input type="password" class="ipt1 bji2"
							name="passWord" id="password"
							style="width: 196px; line-height: normal; color: initial; padding: 0 10px 0 35px; height: 36px; display: inline-block; border-radius: 0;" />
						</td>
						<td><span id="errorpass"></span></td>
					</tr>
					<tr>
						<td>验证码:</td>
						<td style="padding-left: 10px;"><input type="hidden"
							value="${validateCode}" name="validateCode" /> <img
							src="<c:url value="/code/buildCode" />/1" /> <input class="ipt1"
							name="code" id="code"
							style="width: 112px; line-height: normal; color: initial; padding: 0 10px 0 35px; height: 36px; display: inline-block; border-radius: 0;" />
						</td>
						<td><span id="errorcode"></span></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td class="tab1td2"><input type="hidden" name="isAutoLogin"
							value="no" /> <label style="float: left; padding-left: 10px;">
								<input type="checkbox" id="isAutoLogin" /> 下次自动登录
						</label> <label style="float: right; padding-left: 10px;"> <a
								href="<c:url value="/forgetPassword"/>" onmouseover="over(this)"
								onmouseout="out(this)" style="text-decoration: none;">忘记密码?</a>
						</label></td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td colspan="3" class="log-bt"><img
							src="<c:url value="/resources/images/ljdl.jpg"/>" id="login_btn" />
							<input type="hidden" id="infocode"></td>
					</tr>
				</table>
			</form>
		</div>
	</c:if>
</div>
<script>
	$(".slideTxtBox").slide({
		mainCell : ".bd ul",
		effect : "leftLoop",
		autoPlay : true,
		interTime : 5000
	});
</script>