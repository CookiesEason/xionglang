//var int = self.setInterval("clock()", 1000);
//var s = 60;
//function clock() {
//	if (s >= 0) {
//		document.getElementById("fasongduanxin").value = s--;
//	} else {
//		window.clearInterval(int);
//
//	}
//};
function fasongduanxin() {
	var tele = $("#telephoneNumber").val();
	var telReg = !!tele
			.match(/^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/);
	if (telReg == false) {
		$("#erroruser").html("<img src=\'"+sy.contextPath+"\'/resources/images/cha.jpg' 'title=请输入正确的手机号码>'");
		return false;
	}
	$.get(sy.contextPath+"/code/buildCode/0?type_tele=1&telephone=" + tele, function(
			data) {
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