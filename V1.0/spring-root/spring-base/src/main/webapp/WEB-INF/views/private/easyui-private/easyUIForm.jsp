<!-- easyUI公有部分 -->
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/common/private/inc.jsp"%>
<%@ page session="true"%>
<div class="easyui-layout">
	<div id="toolbar" style="font-size: 12px;">
		<form action="" id="searchInfoParam">
			<table>
				<tr>
					<td style="padding-right: 20px;"><input id="search" />
						<div id="searchParam">
							<c:forEach items="${searchParamList}" var="searchObject"
								varStatus="i">
								<div
									data-options="name:'${searchObject.field}'<c:if test="${i.index==0}">,iconCls:'icon-ok'</c:if>">${searchObject.name}</div>
							</c:forEach>
						</div></td>
					<c:if test="${addButton.flag}">
						<!-- 默认有添加按钮 -->
						<td style="padding-right: 20px;"><a
							href="javascript:void(0);" class="easyui-linkbutton"
							data-options="iconCls:'ext-icon-note_add',plain:true"
							onclick="addValidataObject('${addButton.valueName}','${addButton.buttonUrl}','${addButton.formId}','${addButton.type}','${addButton.dataType}');">${addButton.buttonName}</a>
						</td>
					</c:if>
					<c:forEach items="${listBtns}" var="btns" varStatus="i">
						<c:if test="${btns.flag}">
							<td style="padding-right: 20px;"><a
								href="javascript:void(0);" class="easyui-linkbutton"
								data-options="iconCls:'ext-icon-note_add',plain:true"
								onclick="operateObject('${btns.valueName}','${btns.buttonUrl}','${buttonUrl.formId}','${btns.type}','${btns.dataType}');">${btns.buttonName}</a>
							</td>
						</c:if>
					</c:forEach>
				</tr>
			</table>
		</form>
	</div>
	<table id="dataGrid" class="datagrid-view"></table>
</div>
<script type="text/javascript">
<!-- 后台数据rows为空，不能使用点子属性，如：systid.name,会报错，可以使用formatter。 -->
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			title : '',
			iconCls : '',
			autoRowHeight : false,
			singleSelect : true,
			checkOnSelect : true,
			striped : true,
			collapsible : true,
			width : function() {
				return document.body.clientWidth * 0.9
			},
			url : '${ajaxList}',
			pageSize : '${basePage.pageSize}',
			loadMsg : '',
			pageList : [ 10, 20, 30, 40, 50, 100, 200 ],
			remoteSort : false,
			idField : 'id',
			filterBtnIconCls : 'icon-filter',
			pagination : true,
			rownumbers : true,
			onBeforeLoad : function(param) {
				parent.$.messager.progress({
					text : '数据加载中....'
				});
			},
			onLoadSuccess : function(data) {
				if (data != null && data != undefined) {
					$.each(data.rows, function(index, value) {
						value = eval()
					});
				}
				parent.$.messager.progress('close');
			}
		});
		dataGrid.datagrid('getPager').pagination({
			onBeforeRefresh : function(pageNumber, pageSize) {
				$(this).pagination('loading');
				$(this).pagination('loaded');
			}
		});

		/*搜索框*/
		$('#search').searchbox({
			searcher : function(value, name) {
				alert(value + "," + name)
			},
			menu : '#searchParam',
			prompt : '请输入值'
		});
		$(".searchbox").css("width", "250px");
		$(".textbox-text").css("width", "147px");
	});
</script>