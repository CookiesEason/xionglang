function getBasePath() {
	var curWwwPath = window.document.location.href;
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	var localhostPaht = curWwwPath.substring(0, pos);
	var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
	return (localhostPaht);
}
if (isMobile.any()) {
	window.location.href = M_DOMAIN;
}
layer.use("extend/layer.ext.js"); // 载入layer拓展模块
$(function() {
	// ajaxGetProducts("3");
	// $(".mh-menu li:nth-child(1)").eq(0).mouseover();
	// $(".mh-menu li:nth-child(1)").eq(0).trigger("mouseover");
	/*
	 * $(".mh-menu li").each(function (){ $(this).hover(function (){ $(".mh-menu
	 * li").removeClass("hover"); $(this).addClass("hover"); }); });
	 */
	$(".mh-menu li:nth-child(1)").mouseout(function() {
		$(this).find("img").removeClass("mouseover");
	});
	$(".container").one("mouseenter", function() {
		$(this).find(".mh-menu li:nth-child(1) img").addClass("mouseover");
	}).mouseover();
	$(".container").mouseenter(function() {
		$(this).find(".mh-menu li:nth-child(1) img").removeClass("mouseover");
	});
	$(".container").mouseleave(function() {
		$(this).find(".mh-menu li:nth-child(1) img").addClass("mouseover");
	});
	var speed = 50
	demo2.innerHTML = demo1.innerHTML
	function Marquee() {
		if (demo2.offsetTop - demo.scrollTop <= 0)
			demo.scrollTop -= demo1.offsetHeight
		else {
			demo.scrollTop++
		}
	}
	var MyMar = setInterval(Marquee, speed)
	demo.onmouseover = function() {
		clearInterval(MyMar)
	}
	demo.onmouseout = function() {
		MyMar = setInterval(Marquee, speed)
	}
});

function getBasePath() {
	var curWwwPath = window.document.location.href;
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	var localhostPaht = curWwwPath.substring(0, pos);
	var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
	return (localhostPaht);
}
function ajaxGetProducts(type) {
	$.ajax({
		type : "GET",
		url : "<c:url value='/public/loan/ajaxGetRecommendProducts'/>",
		data : {
			"type" : type
		},
		dataType : "JSON",
		success : function(data) {
			if (data != null) {
				$("#div_product").html("");
				$(data).each(
						function(index, d) {
							$("#div_product").append(
									"<div style='width:50%;float: left;'><ul><li style='width:357px;height:121px;'><a style='vertical-align:middle;width:100%;height: 100%;color:#ff6600;' href=\"<c:url value='/public/product/infoApply/" + d.id + "'/>\"><img style='border:0 none;vertical-align:middle;width:1px;height:100%;' src='img/baseline.png' class='baseline' /><img style='max-width: 265px;max-height: 125px;vertical-align:middle;' src=\"${showImage}" + d.bank.logoFile.id
											+ "\"></a></li><li><a style='height: initial !important;color:#ff6600;' href=\"<c:url value='/public/product/infoApply/" + d.id + "'/>\"'>" + d.productName + "</a></li></ul></div>");
						});
			}
		}
	});
}
function ifClick(id) {
	var $arr = $('#' + id).parent().next();
	var $a = $arr.find('a');
	var path = $($a[0]).attr('href');

	location = path;
}
$(".slideTxtBox").slide({
	mainCell : ".bd ul",
	effect : "leftLoop",
	autoPlay : true,
	interTime : 5000
});
function fasongduanxin() {
	var tele = $("#telephoneNumber").val();
	var telReg = !!tele.match(/^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/);
	if (telReg == false) {
		$("#erroruser").html("<img src=\'" + sy.contextPath + "\'/resources/images/cha.jpg' 'title=请输入正确的手机号码>'");
		return false;
	}
	$.get(sy.contextPath + "/code/buildCode/0?type_tele=1&telephone=" + tele, function(data) {
		alert(data);
	});
};

function changeImg(obj) {
	var imgSrc = null;
	if (obj == 1) {
		imgSrc = $("#imgObj");
	}
	if (obj == 2) {
		imgSrc = $("#imgObjs");
	}
	if (obj == 3) {
		imgSrc = $("#imgObjts");
	}
	var src = imgSrc.attr("src");
	imgSrc.attr("src", chgUrl(src));
}

// 时间戳
// 为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳
function chgUrl(url) {
	var timestamp = (new Date()).valueOf();
	urlurl = url.substring(0, 17);
	if ((url.indexOf("&") >= 0)) {
		urlurl = url + "×tamp=" + timestamp;
	} else {
		urlurl = url + "?timestamp=" + timestamp;
	}
	return urlurl;
}

function isRightCode() {
	var code = $("#code").val();
	code = "c=" + code;
	$.ajax({
		type : "POST",
		url : url,
		data : code,
		success : callback
	});
}

function callback(data) {
	var s = "";
	if (data == 0) {
		s = "验证码输入正确";
	}
	if (data == 1) {
		s = "验证码不能为空";
	}
	if (data == 2) {
		s = "验证码输入错误";
	}
	if (data == 3) {
		s = "服务器开小差，请稍后再试";
	}
	var flag = true;

	if (s != '验证码输入正确') {
		$("#errorcode").html('<img src="' + path + '" title="' + s + '">');
		$("#errorcode").focus();
		flag = false;
	}
	if ($("#username").val() === "" || $("#username").val() == "用户名") {
		$("#erroruser").html('<img src="' + path + '" title="请输入用户名">');
		$("#username").focus();
		flag = false;
	}
	if ($("#password").val() === "" || $("#password").val() == "密码") {
		$("#errorpass").html('<img src="' + path + '" title="请输入密码">');
		$("#password").focus();
		flag = false;
	}
	if (flag) {
		$("#login_btn").attr("disabled", "true").html("正在登录中...");
		$("#error").html("");
		$("#form_login").submit();
	}
}
