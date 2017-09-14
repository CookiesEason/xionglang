<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page session="true"%>
<%@ include file="/WEB-INF/views/common/private/inc.jsp"%>
<style>
.form input, .form select, .form textarea {
	width: 120px;
}
</style>
<form method="post" class="form" id="idSysDataForm">
	<fieldset>
		<legend>添加数据字典</legend>
		<table class="table" id="normortable">
			<tr>
				<th>所属项目</th>
				<td><input name="project" data-options="required:true"
					class="easyui-validatebox" /></td>
				<th>所属位置</th>
				<td><input name="type" data-options="required:true"
					class="easyui-validatebox" /></td>
			</tr>
			<tr>
				<th>默认值大小32</th>
				<td><input name="value" data-options="required:false"
					class="easyui-validatebox" /></td>
				<th>int值</th>
				<td><input name="intData" data-options="required:false"
					class="easyui-validatebox" /></td>
			</tr>
			<tr>
				<th>double值</th>
				<td><input name="doubleData" data-options="required:false"
					class="easyui-validatebox" /></td>
				<th>参数1大小64</th>
				<td><input name="paraOne" data-options="required:false"
					class="easyui-validatebox" /></td>
			</tr>
			<tr>
				<th>参数2大小128</th>
				<td><input name="paraTwo" data-options="required:false"
					class="easyui-validatebox" /></td>
				<th>参数3大小256</th>
				<td><input name="paraThree" data-options="required:false"
					class="easyui-validatebox" /></td>
			</tr>
			<tr>
				<th>参数4大小512</th>
				<td><input name="paraFour" data-options="required:false"
					class="easyui-validatebox" /></td>
				<th>排序规则</th>
				<td><select name="orderForData" class="easyui-combobox">
						<option value='0' selected="selected">不排序</option>
						<option value='1'>升序</option>
						<option value='0'>降序</option>
				</select></td>
			</tr>
		</table>
	</fieldset>
</form>