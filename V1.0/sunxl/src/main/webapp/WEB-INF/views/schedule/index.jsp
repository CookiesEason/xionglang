<%@page import="com.taobao.pamirs.schedule.ConsoleManager"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/schedule/shedule.jsp"%>
<%
	try {
		ConsoleManager.initial();
		ConsoleManager.getScheduleDataManager();
		String isManager = request.getParameter("manager");
%>
<iframe name="content" style="height: 100%; width: 100%; border: 0; scroll: no;"
	src="<%=request.getContextPath()%>/private/schedule/path?path=private.schedule.scheduleStrategyList&manager=<%=isManager%>"></iframe>
<script>
function linkOnclick(obj){
	taskTypeList.style.backgroundColor="";
	strategyList.style.backgroundColor="";
	managerFactoryList.style.backgroundColor="";
	serverList.style.backgroundColor="";
	config.style.backgroundColor="";
	zookeeperData.style.backgroundColor="";
	zookeeperDataExport.style.backgroundColor="";
	zookeeperDataImport.style.backgroundColor="";
	obj.style.backgroundColor="#FF0000";	
}

</script>
<%
	} catch (Exception e) {
		response.sendRedirect(request.getContextPath()+"/private/schedule/path?path=private.schedule.config&error=" + e.getMessage());
	}
%>