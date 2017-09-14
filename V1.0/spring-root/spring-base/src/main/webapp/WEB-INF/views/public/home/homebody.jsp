<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page session="false"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="com.base.util.CookieUtils"%>
<%@include file="/WEB-INF/views/common/public/inc.jsp"%>
<%
	Cookie userName = CookieUtils.getCookieByName(request, "account");
	if (userName != null) {
		request.setAttribute("account", URLDecoder.decode(userName.getValue(), "UTF-8"));
	}
%>
<script src="<%=contextPath%>/resources/js/body.js"
	type="text/javascript"></script>
<style type="text/css">
a {
	color: #222;
}

.index_bx_big {
	height: 429px;
}

.two_five_con .span_1, .span_3 {
	margin: 0px;
}

.dl1 dd a {
	color: #c81623;
	font-size: 14px;
	font-family: '微软雅黑';
}

.bttext2 {
	font-size: 14px;
	font-family: inherit;
}

.span_1 {
	width: 104px;
	display: inline-block;
	text-align: right;
}

.ui-widget-header {
	height: 61px;
	background-image: url("<c:url value ="/ resources/ images/ zai_1.png "/>");
	background-repeat: no-repeat;
	color: #888;
	font-size: 18px;
	font-family: '微软雅黑';
	line-height: 44px;
}
</style>
</head>

<body>
	<!-- 轮播登陆开始 -->
	<jsp:include page="/WEB-INF/views/public/home/lunbo.jsp" />
	<!-- 轮播登陆结束 -->
	<!-- 专题开始-->
	<jsp:include page="/WEB-INF/views/public/home/subject.jsp" />
	<!-- 专题结束-->
	<!-- 主菜单栏开始 -->
	<jsp:include page="/WEB-INF/views/public/home/bodyMain.jsp" />
	<!-- 主菜单栏结束 -->
</body>
