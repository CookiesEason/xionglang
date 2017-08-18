<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page session="false"%>
<%@ include file="/WEB-INF/views/common/public/inc.jsp"%>
<style type="text/css">
.content {
    width: 1200px;
    margin: 0 auto
}

p#back-to-top {
    position: fixed;
    bottom: 100px;
    left: 1400px;
}

p#back-to-top a {
    text-align: center;
    text-decoration: none;
    color: #d1d1d1;
    display: block;
    width: 80px;
    /*使用CSS3中的transition属性给跳转链接中的文字添加渐变效果*/
    -moz-transition: color 1s;
    -webkit-transition: color 1s;
    -o-transition: color 1s;
}

p#back-to-top a:hover {
    color: #979797;
}

p#back-to-top a span {
    border-radius: 6px;
    display: block;
    height: 80px;
    width: 80px;
    margin-bottom: 5px;
    /*使用CSS3中的transition属性给<span>标签背景颜色添加渐变效果*/
    -moz-transition: background 1s;
    -webkit-transition: background 1s;
    -o-transition: background 1s;
}

.black_header_right a {
    color: #c81623;
    font-size: 12px;
    font-family: '微软雅黑';
}

.meanu {
    text-align: right;
}
</style>
<script type="text/javascript">
	layer.use("extend/layer.ext.js"); //载入layer拓展模块
	var curWwwPath = window.document.location.href;
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	var localhostPaht = curWwwPath.substring(0, pos); //根目录 http://localhost:8080
	var url = "<c:url value='/code/valiCode?id=1' />";
	var shortmessage = "<c:url value='/code/buildCode/0?telephone='/>";
	var path = "<c:url value='/resources/images/cha.jpg'/>";
	function over(obj) {
		obj.style.textDecoration = 'underline';
	}
	function out(obj) {
		obj.style.textDecoration = 'none';
	}
	function pushMsg(data) {
		$("#ll").html("(" + data.count + ")");
		alert("您有新的未读消息");
	}
	$(document).ready(function() {
		$.ajax({
			type : "GET",
			url : "<c:url value='/member/instation/getCount/7' />",
			dataType : "text",
			success : function(data) {
				$("#ll").html("(" + data + ")");
			}
		});
		$("#realPath").val(window.document.location.pathname); //当前页面路径
		/**
		 * 返回顶部
		 */
		//首先将#back-to-top隐藏
		$("#back-to-top").hide();
		//当滚动条的位置处于距顶部100像素以下时，跳转链接出现，否则消失
		$(window).scroll(function() {
			if ($(window).scrollTop() > 100) {
				$("#back-to-top").fadeIn(1500);
			} else {
				$("#back-to-top").fadeOut(1500);
			}
		});
		//当点击跳转链接后，回到页面顶部位置
		$("#back-to-top").click(function() {
			$('body,html').animate({
				scrollTop : 0
			}, 500);
			return false;
		});

		/* $(".menu>li>a").hover(function(){
			$(".menu>li>a").removeClass("active");
			$(this).addClass("active");
		}); */
		var index = $("#index").val();
		if (index == null || index == undefined || index == "") {
			$(".twofive_ul li a").removeClass("twofive_hover");
			$(".twofive_ul li").eq(0).children("a").addClass("twofive_hover");
		} else {
			$(".twofive_ul li a").removeClass("twofive_hover");
			$(".twofive_ul li").eq(index).children("a").addClass("twofive_hover");
		}
	});
	/**
	 * 反序列化
	 data:序列化的串
	 formId:表单ID
	 */

	function cssClass() {

	}
	function deSerialize(data, formId) {
		var deSerialize = data;
		if (deSerialize != null && deSerialize.length > 0) {
			var s_arr = deSerialize.split("&");
			for (var i = 0; i < s_arr.length; i++) {
				if (s_arr[i].split("=").length > 1) {
					$("#" + formId).find("[name='" + s_arr[i].split("=")[0] + "']").val(s_arr[i].split("=")[1]);
				}
			}
		}
	}
</script>
<p id="back-to-top">
	<a href="#top">
		<span>
			<img src="<c:url value ="/resources/images/Top.png"/>" />
		</span>
		返回顶部
	</a>
</p>


<header>
	<div class="black_header">
		<div class="jrzj_widthheader">
			<input type="hidden" value="${index}" id="index" />
			<input type="hidden" value="${color}" id="color" />
			<!--left-start-->
			<div class="black_header_right" style="float: left; color: #">
				<ul class="black_xinxi">
					<li class="li0">
						<a href="#">微信</a>
					</li>
					<li class="li1">
						<a href="#">移动客户端</a>
					</li>
					<li class="li2">
						<a href="#">PC客户端</a>
					</li>
				</ul>
			</div>

			<div class="black_header_right">
				<ul class="black_xinxi2">
					<li class="li3" style="color: #9f9e9e">
						<c:if test="${user==null}">
							<span style="font-size: 12px;color: #9f9e9e;">
								您好,请<a href="${home}" style="color: red;">登录 </a>
							</span>

						</c:if>
						<c:if test="${user!=null}">
						您好,<a href="<c:url value="/my-center" />" style="color: red;">${user.userName}</a>&nbsp;<a href="<c:url value="/my-center" />" style="color: red;">会员中心</a>
						</c:if>
					</li>
					<li class="li3">
						<a href="<c:url value="/sign-up" />">免费注册</a>
					</li>
					<li class="li3">
						<a href="<c:url value="/quit" />" style="color: #9f9e9e;" target="_self">注销</a>
					</li>
					<li class="li3">
						<a href="<c:url value="/public/section/section14.html"/>">联系客服</a>
					</li>
				</ul>
			</div>
			<!--right-end-->
		</div>
	</div>
	<!--header-end-->
	<!--top-start-->
	<div class="index_bg">
		<div class="jrzj_widthheader navbig">
			<div class="twofive">
				<a href="/">
					<img src="<c:url value="/resources/images/logo.png"/>">
				</a>
			</div>
			<!--menu-start-->
			<div class="twofive_menu" style="float: right;">
				<ul class="twofive_ul">
					<li>
						<a href="<c:url value="/" />">首页</a>
					</li>
					<li>
						<a href="<c:url value="/public/loan/loanHome?type=1" />">债权融资</a>
					</li>
					<li>
						<a href="<c:url value="/public/loan/appreciationService?type=5" />">股权融资</a>
					</li>
					<li>
						<a href="<c:url value="/public/loan/appreciationService?type=1" />">科技服务</a>
					</li>
					<li>
						<a href="<c:url value="/public/credit/golist?type=9" />">金科数据</a>
					</li>
					<%-- 					<li><a href="<c:url value="/public/bigdata/golist" />">金科信用</a></li> --%>
					<li>
						<a href="<c:url value="/public/loan/leganInformation" />?type=1">资讯动态</a>
					</li>
					<li>
						<a href="<c:url value="/public/credit/golist?type=10" />">优惠活动</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
</header>