<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.taobao.pamirs.schedule.ConsoleManager"%>
<%@ include file="/WEB-INF/views/common/private/inc.jsp"%>
<c:url value="/private/schedule/start" var="scheduleStart" />
<input type="hidden" value="${isManager}" id="isManagerSchedule" />
<input type="hidden" value="<%=ConsoleManager.initial()%>" id="ConsoleManager" />
<div>
	<div id="tabSchedule" class="easyui-tabs" style="height: 30px;">
		<%
			if (ConsoleManager.initial()) {
		%>
		<div title="开启调度" style="padding: 20px;"></div>
		<div title="调度策略" style="padding: 20px;"></div>
		<div title="任务管理" style="padding: 20px;"></div>
		<div title="机器管理" style="padding: 20px;"></div>
		<div title="处理线程组列表" style="padding: 20px;"></div>
		<%
			}
		%>
		<c:if test="${isManager!=null}">
			<div title="Zookeeper连接配置" style="padding: 20px;"></div>
			<%
				if (ConsoleManager.initial()) {
			%>
			<div title="Zookeeper数据" style="padding: 20px;"></div>
			<div title="Export配置数据" style="padding: 20px;"></div>
			<div title="Import配置数据" style="padding: 20px;"></div>
			<%
				}
			%>
		</c:if>
	</div>
	<div id="tabsDivJsp"></div>
</div>
<script type="text/javascript">
	/* 普通跳转 */
	function scheduleClick(type, url, dataType) {
		layer.confirm('确定需要重新启动调度吗，未重启项目或重启zookeeper，将不需要重新注入zookeeper', function() {
			$.ajax({
				type : type,
				url : url,
				dataType : dataType,
				success : function(result) {
					if (result.success)
						layer.msg(result.msg, 2, 1);
					else
						layer.msg(result.msg, 2, 8);
				}
			});
		}, "提示");
	};

	$('#tabSchedule').tabs({
		border : false,
		onSelect : function(title, index) {
			var flag = $("#isManagerSchedule").val();
			var ConsoleManager = $("#ConsoleManager").val();
			var path = "<c:url value='/private/schedule/path' />?path=private.schedule.";
			if (ConsoleManager != null) {
				if (index != 0) {
					if (index == 1)
						path = path + "scheduleStrategyList&manager=" + flag;
					if (index == 2)
						path = path + "taskTypeList&manager=" + flag;
					if (index == 3)
						path = path + "managerFactoryList&manager=" + flag;
					if (index == 4)
						path = path + "serverList&manager=" + flag;
					if (index == 5)
						path = path + "config&manager=" + flag;
					if (index == 6)
						path = path + "zookeeperData&manager=" + flag;
					if (index == 7)
						path = path + "exportConfig&manager=" + flag;
					if (index == 8)
						path = path + "importConfig&manager=" + flag;
					$("#tabsDivJsp").html("<iframe src=" + path + " style='height:100%;width:100%;overflow:auto;border: 0;'></iframe>");
				} else {
					$("#tabsDivJsp").html("<button style='width: 100px;margin: 10px 0 0 20px; border: 0; color: white; height:45px; background-color: #007BFF' type='button' class='btn btn-primary' id='sunxlStartSchedual' onclick=\"scheduleClick('POST','${scheduleStart}','JSON')\" value='开启调度' data-complete-text='Loading finished' >开启调度</button>");
				}
			} else {
				path = path + "config&manager=" + flag;
				$("#tabsDivJsp").html("<iframe src=" + path + " style='height:100%;width:100%;overflow:auto;border: 0;'></iframe>");
			}
		}
	});
</script>