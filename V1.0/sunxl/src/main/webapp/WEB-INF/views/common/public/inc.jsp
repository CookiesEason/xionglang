<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.sunxl.base.util.WebKeys"%>
<%@page import="com.sunxl.base.entity.User"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String version = "20170727";
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+ request.getContextPath();
	String contextPath = request.getContextPath();
	User user = (User) request.getSession().getAttribute(WebKeys.USER_KEY);
	request.setAttribute("user", user);
%>
<script type="text/javascript">
var sy = sy || {};
sy.contextPath = '<%=contextPath%>';
sy.basePath = '<%=basePath%>';
sy.version = '<%=version%>';
sy.pixel_0 = '<%=contextPath%>/style/images/pixel_0.gif';//0像素的背景，一般用于占位
</script>
<link href="<%=contextPath%>/resources/css/home/golden.min.css" rel="stylesheet" type="text/css" />
<link href="<%=contextPath%>/resources/css/default.css" rel="stylesheet" type="text/css" />
<link href="<%=contextPath%>/resources/css/footer.css" rel="stylesheet" type="text/css" />
<link href="<%=contextPath%>/resources/css/header.css" rel="stylesheet" type="text/css" />
<link href="<%=contextPath%>/resources/css/index.css" rel="stylesheet" type="text/css" />
<link href="<%=contextPath%>/resources/css/home.css" rel="stylesheet" type="text/css" />
<link href="<%=contextPath%>/resources/images/favicon.ico" rel="stylesheet" type="image/x-icon" />
<link href="<%=contextPath%>/resources/js/banner/css/common-home.css" rel="stylesheet"
	type="text/css" />
<link href="<%=contextPath%>/resources/js/banner/css/home-lend.css" rel="stylesheet" type="text/css" />
<link href="<%=contextPath%>/resources/js/banner/css/IE8-IE11.css" rel="stylesheet" type="text/css" />
<link href="<%=contextPath%>/resources/js/banner/css/main.css" rel="stylesheet" type="text/css" />
<link href="<%=contextPath%>/resources/js/banner/css/lrtk.css" rel="stylesheet" type="text/css" />
<link href="<%=contextPath%>/resources/js/bootstrap/css/bootstrap-combined.min.css" rel="stylesheet"
	type="text/css" />
<link href="<%=contextPath%>/resources/css/login-reg/mian.css" rel="stylesheet" type="text/css" />
<script src="<%=contextPath%>/resources/js/jquery.min.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/home_sunxl.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/banner/js/slide2.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/banner/js/responsiveslides.min.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/code.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/util/login/loginInfo.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/jquery.SuperSlidexigai.2.1.1.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/calendarSign2.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/layer/layer.min.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/common.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/ie7.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/jquery.lazyload.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/jquery.cookie.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/code.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/html5shiv.min.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/jquery.form.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/h.min.js" type="text/javascript"></script>