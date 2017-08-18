/**
 * 
 */
$(function() {
	IE7brower();
});

function IE7brower() {
	var browser = navigator.appName;
	var b_version = navigator.appVersion;
	var version = b_version.split(";");
	var trim_Version = version[0].replace(/[ ]/g, "");
	if (browser == "Microsoft Internet Explorer" && trim_Version == "MSIE7.0") {
		$(".golden_logo").css({
			textIndent : "0"
		});
		$(".menu").css({
			marginTop : "-84px"
		});
		$(".golden_logo").html("");
		$(".content").css({
			float : "none"
		});
		$(".row").css({
			margin : "0 auto"
		});
		$(".col-md-4").css({
			width : "29.33%",
			float : "left"
		});
		$(".col-sm-push-3").css({
			float : "none",
			left : "33.33%"
		});

		$(".ft_links").css({
			position : "relative"
		});
		$(".reg_log").css({
			marginTop : "-34px"
		});
		$(".help").css({
			marginTop : "-34px"
		});
		var s = window.screen.width;
		var w = (s - 1000) / 3;
		var x = w + 50;
		var vals = $("#yincang_panduan").val();
		if (vals != 1) {
			$(".ft_links").css({
				left : "" + w + ""
			});
			$(".ft_wrapper .hotline").css({
				height : "150px",
				textAlign : "right",
				marginRight : "" + w + ""
			});
			$(".focus_us").css({
				position : "relative",
				float : "right",
				right : "" + x + "",
				top : "-50px"
			});
			$(".row-fluid>div").css({
				width : "50%"
			});
		} else {
			$(".span8").css({
				width : "600px"
			});
			$(".mh-menu li").css({
				marginRight : "450px"
			});
		}
		// var fw = $(".form-group").width();
		// $(".col-lg-offset-3").css({
		// width : "" + fw + "px"
		// });

	} else if (browser == "Microsoft Internet Explorer"
			&& trim_Version == "MSIE6.0") {
		$(".ft_links").css({
			position : "relative"
		});
		var s = window.screen.width;
		var w = (s - 1000) / 3;
		$(".ft_links").css({
			left : "" + w + ""
		});
	}
}