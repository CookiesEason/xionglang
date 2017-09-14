<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/private/easyui-private/easyUIForm.jsp"%>
<script type="text/javascript">
$(function(){
	dataGrid.datagrid({
		columns:[[
			{field:'coluna',title:'字段名',width:120},
 			{field:'defava',title:'默认值',width:260},
 			{field:'commet',title:'字段说明',width:250},
			{title:'更多操作',width:100,field:'caozuo', 					
				formatter : function(value, row,index) {
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