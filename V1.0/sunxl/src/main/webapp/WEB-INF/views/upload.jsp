<%@page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url value="/" var="cp" />
<script type="text/javascript" src="<c:url value="/resources/uploadify/js/ajaxfileupload.js"/>"></script>
<script type="text/javascript">
	$(function() {
		$("#uploadify").uploadify({
			'uploader' : '${cp}resources/uploadify/uploadify.swf',//浏览按钮swf
			'script' : '${cp}newUpload;jsessionid=${pageContext.session.id}',//控制层
			'scriptData' : {
				"echoMethod" : '${echoMethod}'
			},
			'fileDataName' : 'file',
			'cancelImg' : '${cp}resources/uploadify/img/cancel.png',//取消图片
			'queueID' : 'fileQueue',
			'fileDesc' : '请选择jpg、jpeg、gif、png、bmp、xls、xlsx、doc、txt',//提示信息
			'fileExt' : '*.png;*.jpeg;*.gif;*.jpg;*.bmp;*.xls;*.xlsx;*.doc;*.txt',//过滤类型-可以上传的后缀
			'auto' : false,
			'multi' : true,//是否批量
			'sizeLimit' : 1024 * 1024 * 10, //文件最大10M
			'width' : 170,
			'height' : 47,
			'onComplete' : function(event, queueID, fileObj, response, data) {//成功从后台返回的函数
				if (response > 0) {
					layer.msg("保存成功", 2, 1);
					if (imageCompleteF)
						imageCompleteF(response);
				} else 
					layer.msg("服务器繁忙", 8, 1);
			},
			'onError' : function(event, queueID, fileObj, errorObj) {//未进入后台返回的函数
				var type = errorObj.type;
				if (type == 'File Size') 
					layer.msg("图片大小不能超过10M", 3, 3);
				layer.msg("上传文件格式错误，请重新选择");
			},
			'buttonText' : '请选择图片、Excel、txt或doc'
		});
	};
	function getEchoMethod() {
		return $('#echoMethod').val();
	}
</script>



<div id="fileQueue"></div>
<input type="hidden" name="uploadFileType" id="echoMethod" value="${echoMethod}" />
<input type="file" name="uploadify" id="uploadify" />
<p>
	<a href="javascript:$('#uploadify').uploadifyUpload()" id="uplodFile">上传</a>
	|
	<a href="javascript:$('#uploadify').uploadifyClearQueue()" id="uploadFileClear">取消上传</a>
</p>


