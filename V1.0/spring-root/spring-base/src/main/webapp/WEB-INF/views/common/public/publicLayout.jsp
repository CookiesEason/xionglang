<!DOCTYPE c:url PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
<head>
<jsp:include page="/WEB-INF/views/common/public/tags.jsp" />
</head>
<body>
	<div>
		<tiles:insertAttribute name="header" />
		<!-- 头部 -->
	</div>
	<div>
		<div>
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
</html>
