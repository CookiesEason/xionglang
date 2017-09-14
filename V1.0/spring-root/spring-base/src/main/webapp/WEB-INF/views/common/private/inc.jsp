<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.base.util.FileUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- baseUrl是后台设定的值 ，跟form.js一起使用-->
<c:url value="${baseUrl}/goViewList?method=ajaxList" var="ajaxList" />
<c:url value="${baseUrl}/view" var="view" />
<c:url value="${baseUrl}/add" var="add" />
<c:url value="${baseUrl}/delete" var="delete" />
<c:url value="${baseUrl}/update" var="update" />
<%
	String version = FileUtil.getVersion();
	String basePath = FileUtil.getRealProjectPath();
	String contextPath = FileUtil.getHomePath();
	Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
	Cookie[] cookies = request.getCookies();
	if (null != cookies) {
		for (Cookie cookie : cookies) {
			cookieMap.put(cookie.getName(), cookie);
		}
	}
	String easyuiTheme = "bootstrap";//指定如果用户未选择样式，那么初始化一个默认样式
	if (cookieMap.containsKey("easyuiTheme")) {
		Cookie cookie = (Cookie) cookieMap.get("easyuiTheme");
		easyuiTheme = cookie.getValue();
	}
%>
<script type="text/javascript">
var sy = sy || {};
sy.contextPath = '<%=contextPath%>';
sy.basePath = '<%=basePath%>';
sy.version = '<%=version%>';
sy.pixel_0 = '<%=contextPath%>/style/images/pixel_0.gif';//0像素的背景，一般用于占位
</script>
<!-- 引入jQuery -->
<%-- <%
	String User_Agent = request.getHeader("User-Agent");
	if (StringUtils.indexOfIgnoreCase(User_Agent, "MSIE") > -1 && (StringUtils.indexOfIgnoreCase(User_Agent, "MSIE 6") > -1 || StringUtils.indexOfIgnoreCase(User_Agent, "MSIE 7") > -1 || StringUtils.indexOfIgnoreCase(User_Agent, "MSIE 8") > -1)) {
		out.println("<script src='" + contextPath + "/resources/js/jquery-1.7.2.min.js' type='text/javascript' charset='UTF-8'></script>");
	} else {
		out.println("<script src='" + contextPath + "/resources/js/jquery-1.7.2.min.js' type='text/javascript' charset='UTF-8'></script>");
	}
%> --%>
<style type="text/css">
legend {
	font-size: 14px;
	padding: 10px 0 20px 0;
	text-align: center;
	font-family: 'Microsoft YaHei';
	font-weight: bold;
}

#normortable {
	width: 90%;
	margin: 0 auto;
	font-family: 'Microsoft YaHei';
	font-size: 14px;
	font-weight: bold;
}

a {
	text-decoration: none;
}

.iconImg {
	margin: 0 3px 0 3px;
}
/* 半透明的遮罩层 */
#overlay {
	background: #000;
	filter: alpha(opacity = 50); /* IE的透明度 */
	opacity: 0.5; /* 透明度 */
	display: none;
	position: absolute;
	top: 0px;
	left: 0px;
	width: 100%;
	height: 100%;
	z-index: 100; /* 此处的图层要大于页面 */
	display: none;
}
</style>
<!--[if lt IE 9]> 
<script src="http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js"></script> 
<![endif]-->
<link href="<%=contextPath%>/resources/css/media.css" rel="stylesheet"
	type="text/css" />
<!-- 引入jQuery,jquery最好第一个引入 -->
<script src="<%=contextPath%>/resources/easyUI-1.5.2/jquery.min.js"
	type="text/javascript" charset="UTF-8"></script>
<script src="<%=contextPath%>/resources/js/jquery-1.7.2.min.js"
	type="text/javascript" charset="UTF-8"></script>
<!-- 引入jquery扩展 -->
<script
	src="<%=contextPath%>/resources/jslib/syExtJquery.js?version=<%=version%>"
	type="text/javascript" charset="UTF-8"></script>
<!--引入EasyUI -->
<link rel="stylesheet"
	href="<%=contextPath%>/resources/easyUI-1.5.2/themes/<%=easyuiTheme%>/easyui.css"
	id="easyuiTheme" type="text/css">
<link rel="stylesheet"
	href="<%=contextPath%>/resources/easyUI-1.5.2/themes/icon.css"
	type="text/css">
<script
	src="<%=contextPath%>/resources/easyUI-1.5.2/jquery.easyui.min.js"
	type="text/javascript" charset="UTF-8"></script>
<%-- 引入my97日期时间控件 --%>
<script type="text/javascript"
	src="<%=contextPath%>/resources/jslib/My97DatePicker4.8Beta3/My97DatePicker/WdatePicker.js"
	charset="UTF-8"></script>
<script
	src="<%=contextPath%>/resources/easyUI-1.5.2/locale/easyui-lang-zh_CN.js"
	type="text/javascript" charset="UTF-8"></script>
<!-- 引入EasyUI Portal插件 -->
<link rel="stylesheet"
	href="<%=contextPath%>/resources/jslib/jquery-easyui-portal/portal.css"
	type="text/css">
<script
	src="<%=contextPath%>/resources/jslib/jquery-easyui-portal/jquery.portal.js"
	charset="UTF-8" type="text/javascript"></script>
<!-- 引入easyui扩展 -->
<script
	src="<%=contextPath%>/resources/jslib/syExtEasyUI.js?version=<%=version%>"
	type="text/javascript" charset="UTF-8"></script>
<!-- 引入扩展图标 -->
<link rel="stylesheet"
	href="<%=contextPath%>/resources/style/syExtIcon.css?version=<%=version%>"
	type="text/css">
<!-- 引入自定义样式 -->
<link rel="stylesheet"
	href="<%=contextPath%>/resources/style/syExtCss.css?version=<%=version%>"
	type="text/css">
<!-- 引入javascript扩展 -->
<script
	src="<%=contextPath%>/resources/jslib/syExtJavascript.js?version=<%=version%>"
	type="text/javascript" charset="UTF-8"></script>
<!-- 引入jquery.form插件 -->
<script
	src="<%=contextPath%>/resources/js/jquery.form.js?version=<%=version%>"
	type="text/javascript" charset="UTF-8"></script>
<!-- 引入自定义form.js -->
<script src="<%=contextPath%>/resources/js/form.js?version=<%=version%>"
	type="text/javascript" charset="UTF-8"></script>
<!-- 引入layer.js -->
<script
	src="<%=contextPath%>/resources/layer/layer.min.js?version=<%=version%>"
	type="text/javascript" charset="UTF-8"></script>
<!-- 引入ckeditor文件编辑器 -->
<script
	src="<%=contextPath%>/resources/ckeditor/ckeditor.js?version=<%=version%>"
	type="text/javascript" charset="UTF-8"></script>