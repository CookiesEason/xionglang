<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page session="false"%>
<%@include file="/WEB-INF/views/common/public/tags.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
	$(function() {
		$(".twofive_indexinfor .twofive_left_nav ul li").hover(function() {
			$(this).siblings("li").children("a").removeClass("index_nav_visited");
			$(this).children("a").addClass("index_nav_visited");
			$(this).parent().parent(".twofive_left_nav").next(".two_five_con").children("div").hide();
			$(this).parent().parent(".twofive_left_nav").next(".two_five_con").children("div").eq($(this).index()).show();
		}, function() {
		})
	})
</script>
<c:forEach items="${publicmenu}" var="publicmenu">
	<div class="twofive_indexinfor">
		<div class="bgcolor_twofive ${publicmenu.cssNames[0]}">
			<!--left-start-->
			<div class="twofive_left_nav">
				<p>
					<a href="<c:url value="${publicmenu.url}" />" style="color: white;">${publicmenu.name}</a>
				</p>
				<ul>
					<c:forEach items="${publicmenu.publicMenuView}"
						var="publicMenuView" varStatus="i">
						<li><a href="<c:url value="${publicMenuView.url}" />"
							<c:if test="${i.index==0}">class="index_nav_visited"</c:if>>${publicMenuView.name}</a>
						</li>
					</c:forEach>
				</ul>
			</div>
			<!--left-end-->
			<!-- right-start -->
			<div class="two_five_con">
				<c:forEach begin="0" end="${fn:length(publicmenu.publicMenuView)-1}"
					varStatus="i">
					<div>
						<!-- right-left-start -->
						<div class="two_five_con_left">
							<div class="two_five_con_left_top">
								<div>左上${i.index}</div>
							</div>
							<div class="two_five_con_left_down">
								<div>左下</div>
							</div>
						</div>
						<!-- right-left-end -->
						<!-- right-right-start -->
						<div class="two_five_con_right">
							<div class="two_five_con_right_top">
								<div class="two_five_con_right_top_left">
									<div></div>
								</div>
								<div class="two_five_con_right_top_right">
									<div></div>
								</div>
							</div>
							<div class="two_five_con_right_down">
								<div class="two_five_con_right_down_left">
									<div></div>
								</div>
								<div class="two_five_con_right_down_right">
									<div></div>
								</div>
							</div>
						</div>
						<!-- right-end-start -->
					</div>
					<!--con-end-->
				</c:forEach>
			</div>
			<!-- right-end -->
		</div>
	</div>
</c:forEach>