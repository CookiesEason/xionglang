<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/private/easyui-private/easyUIForm.jsp"%>
<script type="text/javascript">
$(function(){
	dataGrid.datagrid({
		columns:[[
			{field:'importId',title:'导数方案代号',width:100},
			{field:'importName',title:'导数方案名称',width:100},
			{field:'importDataInfos',title:'关联导数配置',width:250,sortable:true,formatter:function(value,row,index){
 				var info="";
 				$.each(value, function(i, n){
 					if(i!=0)
 						info +=",";
 					info +=n.importInfoName;
 				});
 				return info;
			}},
 			{field:'smrytx',title:'摘要',width:150},
 			{field:'type',title:'导数方案状态',width:80,sortable:true,formatter:function(value,row,index){
 				var info="";
 				if(value=='START')
 					info='<span style="color : green">启用</span>';
 				if(value=='STOP')
 					info='<span style="color : red">停止</span>';
 				return info;
			}},
 			{field:'createTime',title:'创建时间',width:150,sortable:true,formatter:function(value,row,index){
				var newTime = new Date(value);
				newTime.setDate(newTime.getDate());
				return (newTime.getYear()+1900)+"-"+(newTime.getMonth()+1)+"-"+newTime.getDate()+"&nbsp;"+newTime.getHours()+":"+newTime.getMinutes()+":"+newTime.getSeconds();
			}},
			{title:'更多操作',width:100,field:'caozuo', 					
				formatter : function(value, row,index) {
 					var str = '';	
 					str += sy.formatString('<img class="iconImg ext-icon-note_add" title="查看${operateObject}详情" onclick="viewObject(\'{0}\',\'{1}\',\'{2}\');"/>',row.id,row.name,'${view}');
 					str += sy.formatString('<img class="iconImg ext-icon-note_edit" title="修改${operateObject}信息" onclick="updateValidataObject(\'{0}\',\'{1}\',\'{2}\',\'{3}\',\'{4}\',\'{5}\');"/>', row.id,row.name,'${update}','${formId}','POST','JSON');
 					str += sy.formatString('<img class="iconImg ext-icon-note_delete" title="删除${operateObject}信息" onclick="deleteObject(\'{0}\',\'{1}\',\'{2}\',\'{3}\',\'{4}\');"/>', row.id,row.name,'${delete}','POST','JSON');
 					return str;
 				}
 			}
		]]
	});
});	
</script>