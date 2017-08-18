<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page session="true"%>
<%@ include file="/WEB-INF/views/common/private/inc.jsp"%>
<form method="post" class="form" id="idImportDataForm">
	<fieldset>
		<legend>添加导数方案</legend>
		<table class="table" id="normortable">
			<tr>
				<th>导数方案代号</th>
				<td>
					<input name="name" data-options="required:true" class="easyui-validatebox" />
				</td>
			</tr>
			<tr>
				<th>导数方案名称</th>
				<td>
					<input name="url" data-options="required:true" class="easyui-validatebox" />
				</td>
			</tr>
			<tr>
				<th>关联导数配置</th>
				<td>
					<input name="function" data-options="required:true" class="easyui-validatebox" />
				</td>
			</tr>
			<tr>
				<th>摘要</th>
				<td>
					<select id="fatherMenu" name="father.id" class="easyui-combobox">
						<c:forEach items="${menus}" var="menu" varStatus="i">
							<option value="${menu.id}" <c:if test='${i.index==0}'>selected="selected"</c:if>>${menu.name}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th>创建时间</th>
				<td>
					<c:forEach items="${roles}" var="role" varStatus="i">
						<label>
							<input type="checkbox" name="roles.id" value="${role.id}">
							<span>${role.name}</span>
						</label>
					</c:forEach>
				</td>
			</tr>
			<tr>
				<th>菜单创立时间</th>
				<td>
					<input name="createTime" class="Wdate easyui-validatebox"
						onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly"
						style="width: 186px;" data-options="required:true" />
				</td>
			</tr>
		</table>
	</fieldset>
</form>