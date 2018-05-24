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
		cursor:pointer;
		width:50%;
		text-align:center;
		margin:0px;
	}
	img{
		margin-left:10px;
		position:relative;
	}
	div {
		color: #fff;
		font-size: 14px;
		position:relative;
		margin-top:-20px;
	}
</style>
	</head>
<body style="overflow:hidden;">
		<table>
			<tbody>
				<tr>
					<td style="background-color:#D13A43;border-bottom:1px solid #fff;" _target="/interface/jiangyl/yhzh/yhzhsearch.jsp">
							<img src="/portal2016/images/guide2.png"/><br />
							<div>银行账户查询</div>						
					</td>
					<td style="background-color:#2786CA;border-right:1px solid #fff;border-bottom:1px solid #fff;" _target="/workflow/request/AddRequest.jsp?workflowid=5004&isagent=0&beagenter=0&f_weaver_belongto_userid=">
							<img src="/portal2016/images/guide1.png"/><br />
							<div>银行账户创建申请</div>
					</td>
				</tr>
					<tr>
					<td colspan=2 style="background-color:#08d845;border-bottom:1px solid #fff;" _target="/workflow/request/AddRequest.jsp?workflowid=5007&isagent=0&beagenter=0&f_weaver_belongto_userid=">
							<img src="/portal2016/images/guide3.png"/><br />
							<div>银行账户修改申请</div>						
					</td>
				
				</tr>
			</tbody>
		</table>
</body>
	<script type="text/javascript">
		$(document).ready(function(){
			$("td").bind("click",function(){
				openFullWindowHaveBar($(this).attr('_target'));
			}).bind("mouseover",function(){
				$(this).css("opacity",0.6);
			}).bind("mouseout",function(){
				$(this).css("opacity",1.0);
			});
		});
	</script>
</html>

