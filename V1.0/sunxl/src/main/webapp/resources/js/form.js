/*新建后台的datagrid，列需要在对应的列中创建，
 * 每页显示的条数要求可能不同，所以需要设置一个
 * basePage.pageSize每页显示的条数，
 * 如果确定条数可以写死*/
$(function() {
	var tab = $('#div_tabs');
	tab.tabs({
		fit : true,
		border : false,
		tools : [ {
			text : '刷新',
			iconCls : 'ext-icon-arrow_refresh',
			handler : function() {
				var panel = tab.tabs('getSelected').panel('panel');
				var frame = panel.find('iframe');
				try {
					if (frame.length > 0) {
						for (var i = 0; i < frame.length; i++) {
							frame[i].contentWindow.document.write('');
							frame[i].contentWindow.close();
							frame[i].src = frame[i].src;
						}
						if (navigator.userAgent.indexOf("MSIE") > 0) {// IE特有回收内存方法
							try {
								CollectGarbage();
							} catch (e) {
							}
						}
					}
				} catch (e) {
				}
			}
		}, {
			text : '关闭',
			iconCls : 'ext-icon-cross',
			handler : function() {
				var index = tab.tabs('getTabIndex', tab.tabs('getSelected'));
				var tab1 = tab.tabs('getTab', index);
				if (tab1.panel('options').closable) {
					tab.tabs('close', index);
				} else {
					$.messager.alert('提示', '[' + tab1.panel('options').title + ']不可以被关闭！', 'error');
				}
			}
		} ]
	});
});

function switchTab(title, url, icon) {
	var tab = $('#div_tabs');
	if (tab.tabs('exists', title)) {
		tab.tabs('select', title);
	} else {
		tab.tabs('add', {
			title : title,
			iconCls : icon,
			content : sy.formatString('<iframe src="{0}" allowTransparency="true" style="border:0;width:100%;height:99%;" frameBorder="0"></iframe>', url),
			closable : true
		});
	}
}

/*
 * 不用easyui验证输入框的值， 返回值和提交类型可以扩展, 可以添加data扩展参数 $dialog开启的window对象 $grid父级窗口
 * $pjq最上级窗口 url路径 formId提交的form表单id type访问类型，一般post datatype返回类型，一般json
 * obj指的是操作的对象名称 id指的是操作的对象id 具体情况具体调整 具体验证可以价格beforeSubmit:function()
 * 就是在提交之前执行一个方法 layer.msg('title提示信息','time提示框存在的时间','img提示的图片','fn回调函数')
 * datagrid('load');重新加载;datagrid('reload');重新加载当页dialog('destroy');销毁窗口
 * $pjq.messager.alert('提示', result.msg, 'error');没有success flag表示是否需要返回函数
 */
/* 需要验证输入框的值 */
var submitValidataForm = function($dialog, $grid, $pjq, url, formId, type, datatype) {
	if ($(formId).form('validate')) {
		var option = {
			type : type,
			url : url,
			dataType : datatype,
			success : function(result) {
				if (result.success) {
					layer.msg(result.msg, 1, 1, function() {
						$grid.datagrid('reload');
						$dialog.dialog('destroy');
					});
				} else
					layer.msg(result.msg, 2, 8);
			}
		};
		$(formId).ajaxSubmit(option);
	}
};
var submitForm = function($dialog, $grid, $pjq, url, formId, type, datatype) {
	var option = {
		type : type,
		url : url,
		dataType : datatype,
		success : function(result) {
			if (result.success) {
				layer.msg(result.msg, 1, 1, function() {
					$grid.datagrid('reload');
					$dialog.dialog('destroy');
				});
			} else
				layer.msg(result.msg, 2, 8);
		}
	};
	$(formId).ajaxSubmit(option);
};
/* 本窗口进行ajax提交 */
var submitTwoForm = function(url, formId, type, datatype, flag) {
	var option = {
		type : type,
		url : url,
		dataType : datatype,
		success : function(result) {
			if (result.success) {
				if (flag) {
					layer.msg(result.msg, 2, 1, function() {
						fn();
					});
				} else
					layer.msg(result.msg, 2, 1);
			} else {
				if (flag) {
					layer.msg(result.msg, 2, 8, function() {
						fn();
					});
				} else
					layer.msg(result.msg, 2, 8);
			}
		}
	};
	$(formId).ajaxSubmit(option);
}

/* 添加对象submitValidateForm就是指的上面那个需要验证表单的方法 */
var addValidataObject = function(obj, url, formId, type, datatype) {
	var dialog = parent.sy.modalDialog({
		title : '添加' + obj,
		url : url,
		buttons : [ {
			text : '添加',
			handler : function() {
				dialog.find('iframe').get(0).contentWindow.submitValidataForm(dialog, dataGrid, parent.$, url, formId, type, datatype);
			}
		} ]
	});
};
var addObject = function(obj, url, formId, type, datatype) {
	var dialog = parent.sy.modalDialog({
		title : '添加' + obj,
		url : url,
		buttons : [ {
			text : '添加',
			handler : function() {
				dialog.find('iframe').get(0).contentWindow.submitForm(dialog, dataGrid, parent.$, url, formId, type, datatype);
			}
		} ]
	});
};
/* 修改对象 */
var updateValidataObject = function(id, obj, url, formId, type, datatype) {
	var dialog = parent.sy.modalDialog({
		title : '修改' + obj,
		url : url + '/' + id,
		buttons : [ {
			text : '修改',
			handler : function() {
				dialog.find('iframe').get(0).contentWindow.submitValidataForm(dialog, dataGrid, parent.$, url, formId, type, datatype);
			}
		} ]
	});
};
var updateObject = function(id, obj, url, formId, type, datatype) {
	var dialog = parent.sy.modalDialog({
		title : '修改' + obj,
		url : url + '/' + id,
		buttons : [ {
			text : '修改',
			handler : function() {
				dialog.find('iframe').get(0).contentWindow.submitForm(dialog, dataGrid, parent.$, url, formId, type, datatype);
			}
		} ]
	});
};
/* 查询对象 */
var viewObject = function(id, obj, url) {
	parent.sy.modalDialog({
		title : '查看' + obj,
		width : 900,
		height : $(window).height() - 100,
		url : url + '/' + id
	});
};
/* 删除对象 */
function deleteObject(id, obj, url, type, datatype) {
	layer.confirm('确定要删除' + obj + '的数据吗？删除后将无法恢复', function(index) {
		$.ajax({
			type : type,
			url : url + "/" + id,
			dataType : datatype,
			success : function(result) {
				if (result.success) {
					layer.msg(result.msg, 1, 1, function() {
						dataGrid.datagrid('reload');
					});
				} else
					layer.msg(result.msg, 2, 8);
			}
		});
	}, "提示");
};
/*
 * 整理数据,此方法不是所有的对象都需要使用， 整理数据使用的事连接池技术，并非数据源技术， 所以只能整理服务器设置的连接池数据
 * （可以通过跳转路径设置是使用数据源还是连接池）
 */
function operateObject(obj, url, type, datatype) {
	var info="";
	if(obj=='索引'){// 此处控制按钮是否需要提示信息
		info='确定要整理所有的' + obj + '数据吗？整理后将无法还原';
		layer.confirm(info, function(index) {
			ajaxOperateObject(url, type, datatype);
		}, "提示");
	}else{
		ajaxOperateObject(url, type, datatype);
	}
};

function ajaxOperateObject(url, type, datatype){
	$.ajax({
		type : type,
		url : sy.contextPath+url,
		dataType : datatype,
		success : function(result) {
			if (result.success) {
				layer.msg(result.msg, 1, 1, function() {
					dataGrid.datagrid('reload');
				});
			} else
				layer.msg(result.msg, 2, 8);
		}
	});
}
/**
 * 验证两次密码是否一致
 */
$.extend($.fn.validatebox.defaults.rules, {
	depCh : {
		validator : function(value, param) {
			return $('#' + param[0]).val() == value;
		},
		message : '两次输入密码不匹配'
	}
});
/**
 * 修改密码
 * 
 * @param $dialog
 * @param $jq
 * @param formId
 * @param url
 * @returns
 */
function saveNewPasword($dialog, $jq, id, formId, url, type, datatype) {
	if ($(formId).form('validate')) {
		var option = {
			type : type,
			url : url + "/" + id,
			dataType : datatype,
			success : function(result) {
				if (result.success) {
					layer.msg(result.msg, 1, 1, function() {
						$dialog.dialog('destroy');
					});
				} else
					layer.msg(result.msg, 2, 8);
			}
		};
		$(formId).ajaxSubmit(option);
	}
}
/**
 * 弹出修改窗口
 * 
 * @param info
 * @param url
 * @returns
 */
function changePassword(info, url, id, formId, updateUrl, type, datatype) {
	var dialog = sy.modalDialog({
		title : info,
		width : 600,
		height : 400,
		url : url + "/" + id,
		buttons : [ {
			text : '保存',
			handler : function() {
				dialog.find('iframe').get(0).contentWindow.saveNewPasword(dialog, $, id, formId, updateUrl, type, datatype);
			}
		} ]
	});
}
var changeRole = function(info, url) {
	var dialog = sy.modalDialog({
		title : info,
		width : 600,
		height : 300,
		url : url
	});
}

function changeRoleAjax(roleId, id, url, type, datatype, loginUrl) {
	$.ajax({
		type : type,
		url : url + "/" + roleId,
		data : {
			"id" : id
		},
		dataType : datatype,
		success : function(result) {
			if (result.success)
				parent.location.href = loginUrl;
			else
				layer.msg(result.msg, 2, 8);
		}
	});
}

/* 加密 */
decode = function(code) {
	var c = String.fromCharCode(code.charCodeAt(0) + code.length);
	for (var i = 1; i < code.length; i++) {
		c += String.fromCharCode(code.charCodeAt(i) + code.charCodeAt(i - 1));
	}
	return escape(c);
}
/* 解密 */
undecode = function(code) {
	code = unescape(code);
	var c = String.fromCharCode(code.charCodeAt(0) - code.length);
	for (var i = 1; i < code.length; i++) {
		c += String.fromCharCode(code.charCodeAt(i) - c.charCodeAt(i - 1));
	}
	return eval(c);
}

/* 显示遮罩层 */
function showOverlay(id) {
    $(id).height(pageHeight());
    $(id).width(pageWidth());
    // fadeTo第一个参数为速度，第二个为透明度
    // 多重方式控制透明度，保证兼容性，但也带来修改麻烦的问题
    $(id).fadeTo(200, 0.5);
}

/* 隐藏覆盖层 */
function hideOverlay() {
    $(id).fadeOut(200);
}

/* 当前页面高度 */
function pageHeight() {
    return document.body.scrollHeight;
}

/* 当前页面宽度 */
function pageWidth() {
    return document.body.scrollWidth;
}

/* 定位到页面中心 */
function adjust(id) {
    var w = $(id).width();
    var h = $(id).height();
    var t = scrollY() + (windowHeight()/2) - (h/2);
    if(t < 0) t = 0;
    var l = scrollX() + (windowWidth()/2) - (w/2);
    if(l < 0) l = 0;
    $(id).css({left: l+'px', top: t+'px'});
}

// 浏览器视口的高度
function windowHeight() {
    var de = document.documentElement;
    return self.innerHeight || (de && de.clientHeight) || document.body.clientHeight;
}

// 浏览器视口的宽度
function windowWidth() {
    var de = document.documentElement;
    return self.innerWidth || (de && de.clientWidth) || document.body.clientWidth
}

/* 浏览器垂直滚动位置 */
function scrollY() {
    var de = document.documentElement;
    return self.pageYOffset || (de && de.scrollTop) || document.body.scrollTop;
}

/* 浏览器水平滚动位置 */
function scrollX() {
    var de = document.documentElement;
    return self.pageXOffset || (de && de.scrollLeft) || document.body.scrollLeft;
}

var lockWindowFun = function(id,url,divId) {
	$.post(sy.contextPath + url, function(result) {
		if (result.success) {
			$(id).dialog('open');
			showOverlay(divId);
		}
	}, 'json');
};
var logoutFun = function(url,replacUrl) {
	$.post(sy.contextPath+url, function(result) {
		if (result.success) {
			location.replace(sy.contextPath+replacUrl);
		}
	}, 'json');
};
var showMyInfoFun = function(url,info) {
	var dialog = parent.sy.modalDialog({
		title : info,
		url : sy.contextPath+url
	});
};

var modifyStaff=function(id,url,formId,info){
	var dialog = parent.sy.modalDialog({
		title : info,
		url : url+"/"+id,
		buttons : [ {
			text : '保存',
			handler : function() {
				dialog.find('iframe').get(0).contentWindow.submitForm(dialog, dataGrid, parent.$, url+"/"+id, formId , 'POST', 'JSON');
			}
		} ]
	});
}