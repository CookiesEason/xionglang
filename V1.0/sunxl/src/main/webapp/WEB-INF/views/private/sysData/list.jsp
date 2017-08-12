<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/private/easyui-private/easyUIForm.jsp"%>
<script type="text/javascript">
$(function(){
	dataGrid.datagrid({
		columns:[[
			{field:'project',title:'所属项目',width:100},
 			{field:'type',title:'所属位置',width:100},
 			{field:'value',title:'默认值大小32(String)',width:120},
 			{field:'intData',title:'int值',width:100},
 			{field:'doubleData',title:'double值',width:100},
 			{field:'paraOne',title:'参数1大小64',width:100},
 			{field:'paraTwo',title:'参数2大小128',width:100},
 			{field:'paraThree',title:'参数3大小256',width:100},
 			{field:'paraFour',title:'参数4大小512',width:100},
 			{field:'orderForData',title:'排序规则',width:80,sortable:true,formatter:function(value,row,index){
 				var info="";
 				if(value==0)
 					info="<span style='color: blue;'>不排序</span>";
 				if(value==1)
 					info="<span style='color: green;'>升序</span>";
 				if(value==-1)
 					info="<span style='color: red;'>降序</span>";
 				return info;
			}},
			{title:'更多操作',width:100,field:'caozuo', 					
				formatter : function(value, row,index) {
 					var str = '';
 					str += sy.formatString('<img class="iconImg ext-icon-note_add" title="查看${operateObject}详情" onclick="viewObject(\'{0}\',\'{1}\',\'{2}\');"/>',row.id,row.name,'${view}');
 					str += sy.formatString('<img class="iconImg ext-icon-note_edit" title="修改${operateObject}信息" onclick="updateValidataObject(\'{0}\',\'{1}\',\'{2}\',\'{3}\',\'{4}\',\'{5}\');"/>', row.id,row.name,'${update}','${formId}','POST','JSON');
 					str += sy.formatString('<img class="iconImg ext-icon-note_delete" title="删除${operateObject}信息" onclick="deleteObject(\'{0}\',\'{1}\',\'{2}\',\'{3}\',\'{4}\');"/>', row.id,row.tableName,'${delete}','POST','JSON');
 					return str;
 				}
 			}
		]]
	});
});	
</script>