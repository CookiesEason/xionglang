$(function() {
	$("#isAutoLogin").click(function() {
		var isAutoLogin = $("input[name='isAutoLogin']").val();
		if (isAutoLogin === "no") {
			$("input[name='isAutoLogin']").val("yes");
		} else {
			$("input[name='isAutoLogin']").val("no");
		}
	});
	var checklog = function() {

	}
	$("#login_btn").click(function() {
		isRightCode();
	});
});
function refresh() {
	$("#captcha1").attr("src", "/captcha?" + Math.random());
};
function over(obj) {
	obj.style.textDecoration = 'underline';
};
function out(obj) {
	obj.style.textDecoration = 'none';
};