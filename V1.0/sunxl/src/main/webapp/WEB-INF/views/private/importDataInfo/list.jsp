<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/private/easyui-private/easyUIForm.jsp"%>
<c:url value="${baseUrl}/addTableInfo" var="addTableInfo" />
<script type="text/javascript">
$(function(){
	dataGrid.datagrid({
		columns:[[
			{field:'importInfoId',title:'文件名前缀',width:100},
 			{field:'importInfoName',title:'导数配置名称',width:100},
 			{field:'systid',width:100,title:'系统来源',formatter : function(value, row,index) {
 				return value == null ? "" : value.name	
 			}},
 			{field:'path',title:'文件路径',width:150},
 			{field:'splitChar',title:'文件分隔符',width:80},
 			{field:'chartSet',title:'文件编码',width:80},
 			{field:'tableName',width:100,title:'表信息',formatter : function(value, row,index) {
 				return "<a href='#' onclick=\"viewObjectInfo('"+row.id+"','${addTableInfo}','#idImportDataColmForm','添加"+value+"表信息')\" >"+value+"</a>";	
 			}},
 			{field:'type',title:'导数配置状态',width:80,sortable:true,formatter:function(value,row,index){
 				var info="";
 				if(value=='START')
 					info='<span style="color : green">启用</span>';
 				if(value=='STOP')
 					info='<span style="color : red">不启用</span>';
 				return info;
			}},
			{title:'更多操作',width:100,field:'caozuo',formatter : function(value, row,index) {
 					var str = '';
 					str += sy.formatString('<a href=\'#\' title=\'查看${operateObject}详情\' class=\'easyui-tooltip\'><img class=\'iconImg ext-icon-note_add\' onclick=\"viewObject(\'{0}\',\'{1}\',\'{2}\');\"/>',row.id,row.name,'${view}');
 					str += sy.formatString('<a href=\'#\' title=\'修改${operateObject}详情\' class=\'easyui-tooltip\'><img class=\'iconImg ext-icon-note_edit\' onclick="updateValidataObject(\'{0}\',\'{1}\',\'{2}\',\'{3}\',\'{4}\',\'{5}\');"/>', row.id,row.name,'${update}','${formId}','POST','JSON');
 					str += sy.formatString('<a href=\'#\' title=\'删除${operateObject}详情\' class=\'easyui-tooltip\'><img class=\'iconImg ext-icon-note_delete\' onclick="deleteObject(\'{0}\',\'{1}\',\'{2}\',\'{3}\',\'{4}\');"/>', row.id,row.name,'${delete}','POST','JSON');
 					return str;
 				}
 			}
		]]
	});
});	

</script>