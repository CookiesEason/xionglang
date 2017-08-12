<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page session="true"%>
<%@ include file="/WEB-INF/views/common/private/inc.jsp"%>
<style>
#idImportDataColmForm input[type='button'] {
	width: 40px;
	margin: 0 0 0 20px;
	border: 0;
	color: white;
	height: 25px;
	background-color: #007BFF
}

#idImportDataColmForm th {
	text-align: center;
	line-height: 30px;
}
</style>
<form method="post" class="form" id="idImportDataColmForm">
	<fieldset>
		<legend>添加表字段</legend>
		<div>
			<input style="width: 80px; height: 35px; margin: 10px 0 10px 20px;"
				type='button' onclick="addrow()" value='新增字段' />
		</div>
		<table class="table" id="normortable">
			<tr>
				<th>顺序</th>
				<th>字段名</th>
				<th>默认值</th>
				<th>注释</th>
				<th>操作</th>
				<th>删除</th>
			</tr>
		</table>
	</fieldset>
</form>

<script type="text/javascript">
	function addrow() {
		//表格有多少个数据行
		var vNum = $("#normortable tr").length;
		vNum = vNum;
		var ordeid = "<td><input type='text' id='ordeid' name='importDataColm[" + (vNum - 1) + "].ordeid' readonly='readonly' value='" + vNum + "' style='width: 30px;'/></td>";
		var coluna = "<td><input type='text' id='coluna' name='importDataColm[" + (vNum - 1) + "].coluna' onblur='checkColuna(this," + vNum + ")' style='width: 80px;'/></td>";
		var defava = "<td><input type='text' id='defava' name='importDataColm[" + (vNum - 1) + "].defava' style='width: 100px;'/></td>";
		var commet = "<td><input type='text' id='comment' name='importDataColm[" + (vNum - 1) + "].commet' style='width: 50px;'/></td>";
		var operate = "<td style='font-size: 9pt;'><a href='javascript:gliImpUpRow(" + vNum + ")' name='gliImpUpRow'>上移</a> <a href='javascript:gliImpDownRow(" + vNum + ")' name='gliImpDownRow'>下移</a></td>";
		var delBtn = "<td><input type='button' class='button' value='删除' onclick='delImpColmRow(this," + vNum + ")'/></td>";
		var innerHtml = "<tr>" + ordeid + coluna + commet + defava + operate + delBtn + "</tr>";
		$("#normortable").append(innerHtml);
		gliImpUpDownStatus();
	};

	//删除一行
	function delImpColmRow(obj) {
		var tr = $(obj).parents('tr').remove();
		sortGliImpOrdeid();
	};

	//重新排序ordeid
	function sortGliImpOrdeid() {
		var vNum = $("#normortable tr").length;
		for (var i = 1; i < vNum; i++) {
			var tr = $("#normortable tr:eq(" + i + ")");
			tr.find("input[name=ordeid]").val(i);
		}
		gliImpUpDownStatus();
	};

	//上移
	function gliImpUpRow(rowId) {
		var self = $("#normortable tr:eq(" + (rowId - 1) + ")");
		$("#normortable tr:eq(" + (rowId - 2) + ")").before(self);
		sortGliImpOrdeid();
	};

	//下移
	function gliImpDownRow(rowId) {
		var self = $("#normortable tr:eq(" + (rowId - 1) + ")");
		$("#normortable tr:eq(" + rowId + ")").after(self);
		sortGliImpOrdeid();
	};

	//把第一行的上移链接去除，把最后一行的下移链接去除
	function gliImpUpDownStatus() {
		var vNum = $("#normortable tr").length;
		for (var i = 1; i < vNum; i++) {
			var idx = i + 1;
			$("#normortable tr:eq(" + i + ")").find("a[name=gliImpUpRow]").remove();
			$("#normortable tr:eq(" + i + ")").find("a[name=gliImpDownRow]").remove();
			$("#normortable tr:eq(" + i + ") td:eq(4)").text("");
			//只有一行
			var str = "";
			if (vNum == 2) {
				str = "上移 下移 ";
				$("#normortable tr:last td:eq(4)").append(str);
			} else if (vNum > 2) {//多行
				if (i == 1) {
					//第一行
					str = "上移 <a href='javascript:gliImpDownRow(" + idx + ")' name='gliImpDownRow'>下移</a>";
					$("#normortable tr:eq(" + i + ") td:eq(4)").append(str);
				} else if (idx < vNum) {
					//中间的行
					str = "<a href='javascript:gliImpUpRow(" + idx + ")' name='gliImpUpRow'>上移</a> <a href='javascript:gliImpDownRow(" + idx + ")' name='gliImpDownRow'>下移</a>";
					$("#normortable tr:eq(" + i + ") td:eq(4)").append(str);
				} else {
					//最后一行
					str = "<a href='javascript:gliImpUpRow(" + vNum + ")' name='gliImpUpRow'>上移</a> 下移";
					$("#normortable tr:last td:eq(4)").append(str);
				}
			}
		}
	};

	$(function() {
		gliImpUpDownStatus();
	});

	function checkColuna(obj, idx) {
		var na = $(obj).val();
		if (na == null || na == 'undefined' || na == '') {
			return;
		}
		var pattern = /^[A-Za-z_]/;
		if (!pattern.test(na)) {
			alertMsg.error("字段名必须以字母或下划线开头！");
			return;
		}
		//检查是否重名
		var vNum = $("#normortable tr").length;
		var i = 0;
		for (i; i < vNum; i++) {
			var val = $("#normortable tr:eq(" + i + ") td:eq(1) input").val();
			var ordeid = $("#normortable tr:eq(" + i + ") td:eq(0) input").val();
			if (na == val && idx != ordeid) {//不是自己所在行
				alertMsg.error("存在多个[" + na + "]字段！");
				return;
			}
		}
	}
</script>
