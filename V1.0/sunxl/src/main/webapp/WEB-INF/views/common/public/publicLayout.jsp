<!DOCTYPE c:url PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
<head>
<title><tiles:insertAttribute name="title" /></title>
<!-- 获取标题名称 -->
</head>
<body>
	<div>
		<tiles:insertAttribute name="header" />
		<!-- 头部 -->
	</div>
	<div>
		<div>
			<div>
				<tiles:insertAttribute name="menu" />
				<!-- 左侧菜单 -->
			</div>
			<div>
				<tiles:insertAttribute name="body" />
				<!-- 主题 -->
			</div>
		</div>
	</div>
	<div>
		<tiles:insertAttribute name="footer" />
		<!-- 尾部 -->
	</div>
</body>
<script type="text/javascript"
	src="<c:url value="/resources/js/html5shiv.min.js"/>"></script>
</html>
