<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/systeminfo/init_wev8.jsp" %>
<html>
	<head>
		<title>ERP数据门户</title>
		
<style type="text/css">
	html,body {
		margin:0px;
	}
	* {
		font-family: 微软雅黑;
		font-size:11px;
	}
	table {
		width:100%;
		height:100%;
		display: table;
		border-collapse: collapse;
		border-spacing: 1px;
		border-color: gray;
	}
	td {
		
		width:25%;
		text-align:center;
		margin:0px;
	}
	img{
		cursor:pointer;
		position:relative;
	}
	div {
		color: rgb(123, 122, 122) !important;
		font-size: 13px;
		position:relative;
		margin-top:0px;
	}
</style>
	</head>
<body style="overflow:hidden;">
		<table>
			<tbody>
				<tr>
					<td style="border-bottom:1px solid #fff;" >
							<img src="/portal2018/images/1.png" _target="/workflow/request/AddRequest.jsp?workflowid=161&isagent=0&beagenter=0&f_weaver_belongto_userid="/><br />
							<div>用车申请</div>						
					</td>
					<td style="border-bottom:1px solid #fff;">
							<img src="/portal2018/images/1.png"  _target="/interface/jiangyl/dcdb/clddcx.jsp"/><br />
							<div>车辆调度</div>						
					</td>
					
					<td style="border-bottom:1px solid #fff;" >
							<img src="/portal2018/images/1.png" _target="/formmode/search/CustomSearchBySimple.jsp?customid=44"/><br />
							<div>车辆信息</div>						
					</td>
					<td style="border-bottom:1px solid #fff;" >
							<img src="/portal2018/images/1.png" _target="/formmode/search/CustomSearchBySimple.jsp?customid=43"/><br />
							<div>驾驶员信息</div>						
					</td>
					</tr>
			</tbody>
		</table>
</body>
	<script type="text/javascript">
		$(document).ready(function(){
			$("img").bind("click",function(){
				openFullWindowHaveBar($(this).attr('_target'));
			}).bind("mouseover",function(){
				$(this).css("opacity",0.6);
			}).bind("mouseout",function(){
				$(this).css("opacity",1.0);
			});
		});
	</script>
</html>

