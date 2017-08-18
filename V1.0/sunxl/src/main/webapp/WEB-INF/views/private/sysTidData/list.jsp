<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/private/easyui-private/easyUIForm.jsp"%>
<script type="text/javascript">
$(function(){
	dataGrid.datagrid({
		columns:[[
			{field:'name',title:'系统来源',width:120},
 			{field:'url',title:'系统来源路径',width:260},
 			{field:'createTime',title:'系统添加时间',width:150,sortable:true,formatter:function(value,row,index){
				var newTime = new Date(value);
				newTime.setDate(newTime.getDate());
				return (newTime.getYear()+1900)+"-"+(newTime.getMonth()+1)+"-"+newTime.getDate()+"&nbsp;"+newTime.getHours()+":"+newTime.getMinutes()+":"+newTime.getSeconds();
			}},
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