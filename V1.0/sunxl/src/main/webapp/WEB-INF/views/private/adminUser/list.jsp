<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/private/easyui-private/easyUIForm.jsp"%>
<script type="text/javascript">
$(function(){
	dataGrid.datagrid({
		columns:[[
			{field:'userName',title:'用户名',width:80},
 			{field:'realName',title:'用户真名',width:80},
 			{field:'dept.deptName',title:'部门',width:80},
 			{field:'email',title:'邮箱',width:120},
 			{field:'mobilePhone',title:'电话',width:120},
 			{field:'role',title:'默认使用角色',width:80,sortable:true,formatter:function(value,row,index){
 				return value==null?'暂未登陆':value.name;
			}},
 			{field:'isSuperAdmin',title:'是否管理员',width:80,sortable:true,formatter:function(value,row,index){
 				return value=='1'?'<span style="color : green">是</span>':'<span style="color : red">否</span>';
			}},
			{field:'type',title:'用户状态',width:80,sortable:true,formatter:function(value,row,index){
 				var info="";
 				if(value=='DREDGE')
 					info='<span style="color : blue">开通</span>';
 				if(value=='USE')
 					info='<span style="color : green">已使用</span>';
 				if(value=='FROST')
 					info='<span style="color : red">冻结</span>';
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
 					str += sy.formatString('<img class="iconImg ext-icon-note_add" title="查看${operateObject}信息" onclick="viewObject(\'{0}\',\'{1}\',\'{2}\');"/>',row.id,row.name,'${view}');
 					str += sy.formatString('<img class="iconImg ext-icon-note_edit" title="修改${operateObject}信息" onclick="updateValidataObject(\'{0}\',\'{1}\',\'{2}\',\'{3}\',\'{4}\',\'{5}\');"/>', row.id,row.name,'${update}','${formId}','POST','JSON');
 					str += sy.formatString('<img class="iconImg ext-icon-note_delete" title="删除${operateObject}信息" onclick="deleteObject(\'{0}\',\'{1}\',\'{2}\',\'{3}\',\'{4}\');"/>', row.id,row.tableName,'${delete}','POST','JSON');
 					return str;
 				}
 			}
		]]
	});
});	
</script>