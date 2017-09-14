<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/private/easyui-private/easyUIForm.jsp"%>
<script type="text/javascript">
$(function(){
	dataGrid.datagrid({
		columns:[[
			{field:'exportInfoId',title:'卸数配置代号',width:120},
 			{field:'exportInfoName',title:'卸数配置名称',width:260},
 			{field:'systid.name',title:'文件系统来源',width:100},
 			{field:'path',title:'文件路径',width:150},
 			{field:'splitChar',title:'文件分隔符',width:80},
 			{field:'chartSet',title:'文件编码',width:80},
 			{field:'sqlstr',title:'卸数SQL',width:80},
 			{field:'filesz',title:'文件最大',width:80},
 			{field:'fileex',title:'文件后缀名',width:80},
 			{field:'smrytx',title:'摘要',width:150},
 			{field:'insrtg',title:'是否增量',width:40,sortable:true,formatter:function(value,row,index){
 				var info="";
 				if(value==1)
 					info='<span style="color : green">是</span>';
 				if(value==0)
 					info='<span style="color : red">否</span>';
 				return info;
			}},
 			{field:'type',title:'导数配置状态',width:40,sortable:true,formatter:function(value,row,index){
 				var info="";
 				if(value=='START')
 					info='<span style="color : green">启用</span>';
 				if(value=='STOP')
 					info='<span style="color : red">停止</span>';
 				return info;
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