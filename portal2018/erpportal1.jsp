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
		width:25%;
		text-align:center;
		margin:0px;
	}
	img{
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
					<td style="border-bottom:1px solid #fff;" _target="/workflow/request/AddRequest.jsp?workflowid=61&isagent=0&beagenter=0&f_weaver_belongto_userid=">
							<img src="/portal2018/images/1.png"/><br />
							<div>公文收文办理</div>						
					</td>
										
					<td style="border-right:1px solid #fff;border-bottom:1px solid #fff;" _target="/workflow/request/AddRequest.jsp?workflowid=201&isagent=0&beagenter=0&f_weaver_belongto_userid=">
							<img src="/portal2018/images/1.png"/><br />
							<div>会议通知</div>
					</td>
				
		<td style="border-right:1px solid #fff;border-bottom:1px solid #fff;" _target="/workflow/request/AddRequest.jsp?workflowid=203&isagent=0&beagenter=0&f_weaver_belongto_userid=">
							<img src="/portal2018/images/1.png"/><br />
							<div>公文收文传阅</div>
					</td>
					<td style="border-bottom:1px solid #fff;" _target="/workflow/request/AddRequest.jsp?workflowid=204&isagent=0&beagenter=0&f_weaver_belongto_userid=">
							<img src="/portal2018/images/1.png"/><br />
							<div>局内发文传阅</div>						
					</td>
						
					</tr>
					<tr>
					
				
						
					<td style="border-right:1px solid #fff;border-bottom:1px solid #fff;" _target="/workflow/request/AddRequest.jsp?workflowid=59&isagent=0&beagenter=0&f_weaver_belongto_userid=">
							<img src="/portal2018/images/1.png"/><br />
							<div>公文拟报</div>
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

