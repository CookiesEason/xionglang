<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<input type="hidden" name="pageNow" value="${pageSupport.pageNow }"
	id="PAGEUTIL-VALUE-PAGENOW" />

<script type="text/javascript">
function pageTurning(pageNum){
		var formId = '${pageSupport.formId}';
		jQuery('#PAGEUTIL-VALUE-PAGENOW').val(pageNum);
		formId = jQuery.trim(formId);
		var form;
		if(formId.length>0){
		form = document.getElementById(formId);
		}else{
		  var $form  = jQuery('#PAGEUTIL-VALUE-PAGENOW').parents('form');
			form = $form[0];
		}
		var flag;
		try{
			flag = form.onsubmit();
		}
		catch(e){
			flag=true;
		}
		if(flag){
			form.submit();
		}
}


function selectTurning(obj){
	var value = obj.value;
	pageTurning(value);
};
function turningText(){
   var value = document.getElementById('TEXT-PLUGIN').value;
    pageTurning(value);
};
</script>
<table class="page_turn">

	<tr>
		<td>共计&nbsp;<font class="page_count">${pageSupport.rowCount }</font>&nbsp;条记录
		</td>
		<c:if test="${pageSupport.pageEnd > 1 }">
			<td>&nbsp;</td>
			<td><c:if test="${pageSupport.pageNow != 1}">
					<a class="next_page" href='javascript:;' onclick="pageTurning('1')">首页</a>
				</c:if></td>
			<td>&nbsp;</td>
			<c:if test="${pageSupport.pageNow > 1}">
				<td><a class="next_page" href='javascript:;'
					onclick="pageTurning('${pageSupport.pageNow-1}')">上一页</a></td>
			</c:if>
			<td>&nbsp;</td>
			<c:if test="${pageSupport.headOmit}">
				<td>..</td>
			</c:if>
			<c:forEach items="${pageSupport.displayPages}" var="v_paper">
				<td><c:if test="${v_paper eq pageSupport.pageNow}"
						var="v_current">
						<a style="font-weight: bold;">${v_paper}</a>&nbsp;</c:if> <c:if
						test="${!v_current}">
						<a href='javascript:;' onclick="pageTurning('${v_paper }')">${v_paper }</a>&nbsp;</c:if>
				</td>
			</c:forEach>
			<c:if test="${pageSupport.tailOmit}">
				<td>..</td>
			</c:if>

			<c:if test="${pageSupport.pageNow < pageSupport.pageEnd}">
				<td><a class="next_page" href='javascript:;'
					onclick="pageTurning('${pageSupport.pageNow+1}')">下一页</a></td>
			</c:if>
			<c:if test="${pageSupport.pageNow != pageSupport.pageEnd}">
				<td><a class="next_page" href='javascript:;'
					onclick="pageTurning('${pageSupport.pageEnd}')">&nbsp;末页</a></td>
			</c:if>

			<c:if test="${pageSupport.selectPluginStarted}">
				<td>&nbsp;</td>
				<td>跳至<select onchange="selectTurning(this)">
						<c:forEach items="${pageSupport.pageArray}" var="v_page">
							<option value="${v_page }"
								<c:if test="${v_page eq pageSupport.pageNow}">
			   				selected="selected"
			   			</c:if>>${v_page }</option>
						</c:forEach>
				</select>
				</td>
			</c:if>

			<c:if test="${pageSupport.textPluginStarted}">
				<td>&nbsp;</td>
				<td>前往<input id="TEXT-PLUGIN" size='3'
					value="${pageSupport.pageNow }"
					onkeyup="value=value.replace(/[^\\d]/g,'')" /><input type="button"
					value="GO>>" onclick="turningText()">
				</td>
			</c:if>

		</c:if>
	</tr>
</table>

