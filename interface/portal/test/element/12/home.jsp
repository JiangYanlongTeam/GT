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
	.center{
		position:relative;
		bottom:-20%;
		 
	}
	#imgslider .imgnum div{width:33.3%;}
	#imgslider{
		margin-bottom:1px;
		height：20%;
	}
</style>
<link rel="stylesheet" href="css/banner.css?1" type="text/css" />
	</head>
	<body>
	
	
	
	<div class="element" id="imgslider">
		<div class="imgbox"  style="background:#000000 ">
			<ul id="banner_img">
				<li style="background-image: url('images/banner_cp.jpg');"></li>   
<!-- images/banner_cp.jpg 背景图路径，如需更改直接修改路径即可，在同目录下不必填写绝对路径-->
			</ul>

		</div>
	</div>	
	
	
		<div style="width:100%;height:300px;">
			<div class="level1" style="margin-right:0.05%;">
				<div class="level2 item"  _target="/knowledgeshare/data/DataFrame.jsp?issearch=1&searchtype=0" 
				style="background:#30A3FF url('/CS/images/icon1.png') no-repeat 50% 30%;"><div class="center">新员工学习库</div></div>
				<div class="level2" style="margin-top:0.4%;">
					<div class="level3 item"  _target="/knowledgeshare/data/DataFrame.jsp?issearch=1&searchtype=2&searchtype2=2" 
					style="margin-right:0.2%;background:#AEB6CF url('/CS/images/icon5.png') no-repeat 50% 30%;"><div class="center">行业方案库</div></div>
					<div class="level3 item"  _target="/knowledgeshare/data/DataFrame.jsp?issearch=1&searchtype=13" style="background:#0059B2 url('/CS/images/icon6.png') no-repeat 50% 30%;"><div class="center">竞争对手库</div></div>
				</div>
			</div>
			<div class="level1" style="margin-right:0.05%;">
				<div class="level2" style="margin-bottom:0.4%;">
					<div class="level3 item" _target="/knowledgeshare/data/DataFrame.jsp?issearch=1&searchtype=1" style="margin-right:0.2%;background:#68CFFB url('/CS/images/icon2.png') no-repeat 50% 30%;"><div class="center">销售工具库</div></div>
					<div class="level3 item"  _target="/knowledgeshare/data/DataFrame.jsp?issearch=1&searchtype=14" style="background:#535195 url('/CS/images/icon3.png') no-repeat 50% 30%;"><div class="center">产品技术库</div></div>
				</div>
				<div class="level2 item"   _target="/knowledgeshare/data/DataFrame.jsp?issearch=1&searchtype=3" style="background:#2BB181 url('/CS/images/icon7.png') no-repeat 50% 30%;"><div class="center">客户案例库</div></div>
			</div>
			<div class="level1">
				<div class="level2 item" _target="/knowledgeshare/data/DataFrame.jsp?issearch=1&searchtype=2&searchtype2=1" style="background:#9ECDED url('/CS/images/icon4.png') no-repeat 50% 30%;"><div class="center">专题方案库</div></div>
				<div class="level2" style="margin-top:0.4%;">
					<div class="level3 item" _target="/knowledgeshare/data/DataFrame.jsp?issearch=1&searchtype=12" style="margin-right:0.2%;background:#5674FA url('/CS/images/icon8.png') no-repeat 50% 30%;"><div class="center">UI设计库</div></div>
					<div class="level3 item" _target="/mypage/demomanage/sso/demoList.jsp" style="background:#73B9FF url('/CS/images/icon9.png') no-repeat 50% 30%;"><div class="center">DEMO库</div></div>
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

