<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/systeminfo/init_wev8.jsp" %>
<html>
	<head>
		<title>图片菜单</title>
		
<style type="text/css">
	html,body {
		margin: 0px;
		overflow:hidden;
	}
	* {
		font-family: 微软雅黑;
		font-size:12px;
	}
	.level1 {
		float:left;
		width:33.3%;
		height:100%;
		background-color:#fff;
	}
	.level2 {
		float:left;
		width:100%;
		height:49.8%;
	}
	.level3 {
		float:left;
		width:49.9%;
		height:100%;
	}
	div {
		color:#fff;
		font-size:16px;
		font-weight:bold;
		text-align:center;
		line-height:900%;
	}
	.item {
		cursor: pointer;
	}
</style>
	</head>
	<body>
		<div style="width:100%;height:200px;">
			<div class="level1" style="margin-right:0.05%;">
				<div class="level2 item"  _target="/knowledgeshare/data/DataFrame.jsp?issearch=1&searchtype=0"  style="background:#30A3FF url('/portal2015/images/icon1.png') no-repeat 50% 30%;">新员工学习库</div>
				<div class="level2" style="margin-top:0.4%;">
					<div class="level3 item"  _target="/knowledgeshare/data/DataFrame.jsp?issearch=1&searchtype=2&searchtype2=2" style="margin-right:0.2%;background:#AEB6CF url('/portal2015/images/icon5.png') no-repeat 50% 30%;">行业方案库</div>
					<div class="level3 item"  _target="/knowledgeshare/data/DataFrame.jsp?issearch=1&searchtype=13" style="background:#0059B2 url('/portal2015/images/icon6.png') no-repeat 50% 30%;">竞争对手库</div>
				</div>
			</div>
			<div class="level1" style="margin-right:0.05%;">
				<div class="level2" style="margin-bottom:0.4%;">
					<div class="level3 item" _target="/knowledgeshare/data/DataFrame.jsp?issearch=1&searchtype=1" style="margin-right:0.2%;background:#68CFFB url('/portal2015/images/icon2.png') no-repeat 50% 30%;">销售工具库</div>
					<div class="level3 item"  _target="/knowledgeshare/data/DataFrame.jsp?issearch=1&searchtype=14" style="background:#535195 url('/portal2015/images/icon3.png') no-repeat 50% 30%;">产品技术库</div>
				</div>
				<div class="level2 item"   _target="/knowledgeshare/data/DataFrame.jsp?issearch=1&searchtype=3" style="background:#2BB181 url('/portal2015/images/icon7.png') no-repeat 50% 30%;">客户案例库</div>
			</div>
			<div class="level1">
				<div class="level2 item" _target="/knowledgeshare/data/DataFrame.jsp?issearch=1&searchtype=2&searchtype2=1" style="background:#9ECDED url('/portal2015/images/icon4.png') no-repeat 50% 30%;">专题方案库</div>
				<div class="level2" style="margin-top:0.4%;">
					<div class="level3 item" _target="/knowledgeshare/data/DataFrame.jsp?issearch=1&searchtype=12" style="margin-right:0.2%;background:#5674FA url('/portal2015/images/icon8.png') no-repeat 50% 30%;">UI设计库</div>
					<div class="level3 item" _target="/mypage/demomanage/sso/demoList.jsp" style="background:#73B9FF url('/portal2015/images/icon9.png') no-repeat 50% 30%;">DEMO库</div>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript">
		$(document).ready(function(){
			$(".item").bind("click",function(){
				openFullWindowHaveBar($(this).attr('_target'));
			}).bind("mouseover",function(){
				$(this).css("opacity",0.6);
			}).bind("mouseout",function(){
				$(this).css("opacity",1);
			});
		});
	</script>
</html>

