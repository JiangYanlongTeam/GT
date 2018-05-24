<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/systeminfo/init_wev8.jsp" %>
<html>
	<head>
		<title>集团门户多系统登录</title>
		
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
					<td colspan=2 style="background-color:#CDDC39;border:1px solid #fff;" onclick="open1()">
							<img src="/portal2016/images/guide3.png"/><br />
							<div>董事长信箱</div>						
				</tr>		
				<tr>
					<td style="background-color:#2786CA;border:1px solid #fff;border:1px solid #fff;" onclick="openDocDialog()">
							<img src="/portal2016/images/guide1.png"/><br />
							<div>纪检监察信访举报</div>						
					</td>
					<td style="background-color:#08d845;border:1px solid #fff;" onclick="open3()">
							<img src="/portal2016/images/guide3.png"/><br />
							<div>群众意见收集信箱</div>						
					</td>
</tr>
			</tbody>
		</table>
</body>
	<script type="text/javascript">
		$(document).ready(function(){
			$("td").bind("mouseover",function(){
				$(this).css("opacity",0.6);
			}).bind("mouseout",function(){
				$(this).css("opacity",1.0);
			});
		});
	</script>
	
	<script>	function openDocDialog(){		var dialog = new window.top.Dialog();		dialog.currentWindow = window; 		dialog.URL = "/interface/jiangyl/mhdoc/mhdoc.jsp";		dialog.Title = "举报须知";		dialog.Width = 300;		dialog.Height = 500;		dialog.Drag = true;		dialog.show();	}
		function open1(){
		window.open("/workflow/request/AddRequest.jsp?workflowid=5281&isagent=0&beagenter=0&f_weaver_belongto_userid=","_blank");
		}
		function open3(){
		window.open("/workflow/request/AddRequest.jsp?workflowid=6781&isagent=0&beagenter=0&f_weaver_belongto_userid=","_blank");
		}
	</script>
</html>

