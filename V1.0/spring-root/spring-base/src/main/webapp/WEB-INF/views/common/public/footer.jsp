<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page session="false"%>
<style>
.clear {
	clear: both;
}

.indexbottom a {
	color: #9f9e9e;
	font-size: 12px;
	font-family: '微软雅黑';
}

.jrzj_copyrights a {
	color: #9f9e9e;
	font-size: 12px;
	font-family: '微软雅黑';
}
</style>
<script type="text/javascript">
	function ifClick(id) {
		var $arr = $('#' + id).parent().next();
		var $a = $arr.find('a');
		var path = $($a[0]).attr('href');
		location = path;
	}
</script>
<!--copyright-start-->
<div class="indexbottom">
	<div class="jrzj_newwidth">
		<!--合作机构开始-->
		<div class="index_hz">
			<div class="hzline">
				<span> <a href="javascript:void(0)" target="_self"
					class="foocolor">合作机构</a> <a target="_self"
					href="javascript:void(0)">友情链接</a>
				</span>
			</div>
			<div class="hzpic">
				<div class="hzpicbig">
					<p style="padding-left: 30px;">
						<a href="http://www.kjzx.com.cn/"> <img alt=""
							src="<c:url value="/resources/images/company_1.png"/>">
						</a> <a href="http://www.quandashi.com/"> <img alt=""
							src="<c:url value="/resources/images/company_2.png"/>">
						</a> <a href="http://www.vgtech.com.cn/"> <img alt=""
							src="<c:url value="/resources/images/company_3.png"/>">
						</a> <a href="http://www.splf.com.cn/"> <img alt=""
							src="<c:url value="/resources/images/company_4.png"/>">
						</a> <a href="http://www.zhilinlaw.com/"> <img alt=""
							src="<c:url value="/resources/images/company_5.png"/>">
						</a>
					</p>
					<p style="padding-left: 30px;">
						<a href="http://www.tushuomeiwu.com/index.html"> <img alt=""
							src="<c:url value="/resources/images/company_6.png"/>">
						</a> <a href="http://www.quandashi.com/"> <img alt=""
							src="<c:url value="/resources/images/company_7.png"/>">
						</a> <a href="http://www.vgtech.com.cn/"> <img alt=""
							src="<c:url value="/resources/images/company_8.png" />">
						</a> <a href="http://www.splf.com.cn/"> <img alt=""
							src="<c:url value="/resources/images/company_9.png" />">
						</a> <a href="http://www.zhilinlaw.com/"> <img alt=""
							src="<c:url value="/resources/images/company_10.png" />">
						</a>
					</p>
				</div>

				<div class="hzpicbig yqlj" style="display: none;">
					<p style="padding-left: 30px;">
						<a href="http://www.tushuomeiwu.com/index.html"> <img alt=""
							src="<c:url value="/resources/images/company_6.png"/>">
						</a> <a href="http://www.quandashi.com/"> <img alt=""
							src="<c:url value="/resources/images/company_7.png"/>">
						</a> <a href="http://www.vgtech.com.cn/"> <img alt=""
							src="<c:url value="/resources/images/company_8.png" />">
						</a> <a href="http://www.splf.com.cn/"> <img alt=""
							src="<c:url value="/resources/images/company_9.png" />">
						</a> <a href="http://www.zhilinlaw.com/"> <img alt=""
							src="<c:url value="/resources/images/company_10.png" />">
						</a>
					</p>
					<p style="padding-left: 30px;">
						<a href="http://www.kjzx.com.cn/"> <img alt=""
							src="<c:url value="/resources/images/company_1.png"/>">
						</a> <a href="http://www.quandashi.com/"> <img alt=""
							src="<c:url value="/resources/images/company_2.png"/>">
						</a> <a href="http://www.vgtech.com.cn/"> <img alt=""
							src="<c:url value="/resources/images/company_3.png"/>">
						</a> <a href="http://www.splf.com.cn/"> <img alt=""
							src="<c:url value="/resources/images/company_4.png"/>">
						</a> <a href="http://www.zhilinlaw.com/"> <img alt=""
							src="<c:url value="/resources/images/company_5.png"/>">
						</a>
					</p>
				</div>
			</div>
			<!--合作机构结束 -->



			<!-- 监督机构开始 -->
			<div class="jd">
				<div class="hzline">
					<span>监督机构</span>
				</div>
				<div class="jdji" style="display: block;">
					<p>
						<em> <a href="http://www.qianzhan.com/indynews/">前瞻财经</a> <font>|</font>
						</em> <em> <a href="http://www.wefax.cn/">钱端</a> <font>|</font>
						</em> <em> <a href="http://www.mingin.cn">鸣金网</a> <font>|</font>
						</em> <em> <a href="http://www.news007.cn/">每日电讯网</a> <font>|</font>
						</em> <em> <a href="http://www.tophold.com">社交投资</a> <font>|</font>
						</em> <em> <a href="http://www.wjtr.com">无界投融</a> <font>|</font>
						</em> <em> <a href="http://www.zongls.cn">棕榈树</a> <font>|</font>
						</em> <em> <a href="http://www.axbxw.com">保险网站</a> <font>|</font>
						</em> <em> <a href="http://www.bi22.com">外汇论坛</a> <font>|</font>
						</em> <em> <a href="http://www.myfund.com">展恒基金网 </a> <font>|</font>
						</em> <em> <a href="http://www.1bd1.com/">贵金属论坛</a> <font>|</font>
						</em> <em> <a href="http://waihuiren.cn ">外汇新闻</a> <font>|</font>
						</em> <em> <a href="http://www.xincaijie.com">新财界</a> <font>|</font>
						</em> <em> <a href="http://news.cnfol.com/">中金在线</a> <font>|</font>
						</em>
					</p>
				</div>
			</div>
		</div>
		<!--监督机构结束-->
	</div>
</div>
<!--copyright-end-->
<!--版权结束-->
<div class="jrzj_copyrights">
	<div class="jrzj_newwidth">
		<!--left-start-->
		<div class="copyright_text" style="width: 600px;">
			<div class="bz">
				<a href="<c:url value="/public/section/section7.html"/>"
					target="_blank">关于金科</a> | <a
					href="<c:url value='/public/section/section14.html'/>"
					target="_blank">联系我们</a> | <a
					href="http://www.stffocus.com/public/commissioner/commissionerInfo?type=1"
					target="_blank">金科专员</a> | <a href="http://www.stffocus.com/"
					target="_blank">服务网点</a> | <a
					href="<c:url value="/public/section/section6.html"/>"
					target="_blank"> 法律保障</a> | <a
					href="<c:url value="/public/section/section13.html"/>"
					target="_blank">帮助中心</a> | <a
					href="<c:url value="/public/section/section309.html"/>"
					target="_blank">科技大数据</a>
			</div>
			<p>
				<a> Copyright © 2015 stffocus.com Inc. All Rights Reserved.<br />
					京ICP备15007315号
				</a>
			</p>
			<p>
				<!-- 				<img src="http://img.jrzj.com/img/index/ba.png" border="0" -->
				<!-- 					usemap="#Map" /> -->
				<map name="Map" id="Map">
					<area shape="rect" coords="4,-1,90,29" href="#" target="_blank" />
					<area shape="rect" coords="97,2,180,29" href="#" />
					<area shape="rect" coords="180,26,182,34" href="#" target="_blank" />
					<area shape="rect" coords="194,0,276,35" href="#" target="_blank" />
					<area shape="rect" coords="291,1,371,39" href="#" target="_blank" />
				</map>
			</p>
		</div>
		<!--left-end-->
		<div
			style="float: left; margin-right: 40px; text-align: center; color: #dedede">
			<img src="<c:url value="/resources/images/ewm.jpg"/>"
				style="display: block; padding-bottom: 10px; margin-left: 16px;" />
			<span style="color: white;">金科汇微信二维码</span>
		</div>
		<div
			style="float: left; margin-right: 40px; text-align: center; color: #dedede">
			<img src="<c:url value="/resources/images/ewms.jpg"/>"
				style="display: block; padding-bottom: 10px; margin-left: 16px;" />
			<span style="color: white;">促进会微信二维码</span>
		</div>
		<div class="copyright_right">
			<p
				style="font-size: 16px; font-family: '微软雅黑'; color: #FFF; line-height: 24px;">客服热线</p>
			<p
				style="font-size: 28px; font-family: Arial, Helvetica, sans-serif; color: #FFF; line-height: 36px">010-64853151</p>
			<p
				style="font-size: 14px; font-family: Arial, Helvetica, sans-serif; color: #FFF; line-height: 10px">服务时间：09:00-17:00</p>
		</div>
		<!--right-end-->
	</div>
</div>