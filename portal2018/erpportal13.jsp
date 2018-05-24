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
		/*height:100%;*/
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
		width: 185px;
		height: 50px;
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
					<td colspan=2 style="border:1px solid #fff;" onclick="open1()">
							<img src="/portal2016/images/OA2_03.png" style="width:370px;"/>	
				</tr>		
				<tr>
				<td style="border:1px solid #fff;" onclick="open3()">
							<img src="/portal2016/images/OA2_06.png" align="right"/>					
					</td>
					<td style="border:1px solid #fff;border:1px solid #fff;" onclick="openDocDialog()">
							<img src="/portal2016/images/OA2_08.png" align="left"/>						
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

