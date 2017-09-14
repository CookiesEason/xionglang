$(function(){
	//投资、融资tab切换
	$(".titles .title").click(function(){
		var v_index = $(this).index(".titles .title");
		$(this).parent(".l").siblings(".l").children(".title").removeClass("active");
		$(this).addClass("active")
		$(".tab_detail").hide();
		$(".tab_detail").eq(v_index).show();
	});
	//模拟现实option的数据 START
	$(".dropdown").html(function(){
		return $(this).next("select").find("option:selected").text();
	});
	$("select").change(function(){
		$(this).prev(".dropdown").html($(this).find("option:selected").text());
	});
	$("option").click(function(event){
		event.stopPropagation();
		$(this).parent("select").next("s").removeClass("active");
	});
	$("select").click(function(event){ 
		event.stopPropagation();
		$(this).next("s").toggleClass("active");
	});
	$(document).click(function(){
		$("s").removeClass("active");
	});
	//模拟现实option的数据 END
	//input的聚焦事件
	$("input").focus(function(){
		$(this).addClass("focus");
	});
	$("input").blur(function(){
		$(this).removeClass("focus");
	});
	//项目分类滑动效果[暂时无用]
//	$(window).bind("load, resize",slide);
//	$(document).scroll(slide);
//	$(".side_menu .bd").delegate(".tr>a", "click", function(event){
//		event.preventDefault();
//		var v_index = $(this).index(".side_menu .bd .tr a");
//		var v_scroll_top = $(this).parents(".pro_kinds").offset().top;
//		//var v_top = $(".side_menu_content").eq(v_index).position().top;
//		//$("html,body").animate({scrollTop:v_scroll_top + v_top}, 1000);
//	});
	
	//动态信息、政策法规
	$(".info_rule .title").mouseover(function(){
		var v_index = $(this).index(".info_rule .title");
		console.log(v_index);
		$(".info_rule .title").removeClass("active");
		$(this).addClass("active");
		$(".info_rule .tab_detail_info").hide();
		$(".info_rule .tab_detail_info").eq(v_index).show();
	});
});

//距离新年还有多久[倒计时效果]
//$(function(){
//	var NY = Math.round((new Date('1/01/2015 00:00:01')).getTime()/1000);
//	$('.count_down_second').flipcountdown({
//		size:"lg",
//		tick:function(){
//			var nol = function(h){
//				return h>9?h:'0'+h;
//			}
//			var	range  	= NY-Math.round((new Date()).getTime()/1000),
//				secday = 86400, sechour = 3600,
//				days 	= parseInt(range/secday),
//				hours	= parseInt((range%secday)/sechour),
//				min		= parseInt(((range%secday)%sechour)/60),
//				sec		= ((range%secday)%sechour)%60;
//			//return nol(days)+' '+nol(hours)+' '+nol(min)+' '+nol(sec);
//			return nol(sec);
//		}
//	});
//});


//side_menu的滑动效果
//function slide(){
//	var v_top_limit = $(".side_menu").parents(".pro_kinds").offset().top;
//	var v_left_limit = $(".side_menu").parents(".pro_kinds").offset().left;
//	var v_bottom_limit = v_top_limit + $(".side_menu").parents(".pro_kinds").outerHeight(true) - $(".side_menu").outerHeight(true);
//	var v_scroll_top = $(document).scrollTop();
//	if(v_scroll_top>=v_top_limit && v_scroll_top<=v_bottom_limit){
//		$(".side_menu").css({
//			"position":"fixed",
//			"left":v_left_limit,
//			"top":0
//		});
//	}else{
//		$(".side_menu").css({
//			"position":"absolute",
//			"left":0,
//			"top":0
//		});
//	}
//}


































