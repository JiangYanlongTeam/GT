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
						<td style="background-color:#2786CA;border:1px solid #fff;border:1px solid #fff;" onclick="open4()">
								<img src="/portal2016/images/guide1.png"/><br />
								<div>人力资源系统</div>
						</td>	
						<td style="background-color:#08d845;border:1px solid #fff;" onclick="open5()">
								<img src="/portal2016/images/guide3.png"/><br />
								<div>NC财务系统</div>						
						</td>
									
				</tr>
			</tr>
				<tr>
					<td style="background-color:#2786CA;border:1px solid #fff;border:1px solid #fff;" onclick="open6()">
							<img src="/portal2016/images/guide1.png"/><br />
							<div>报表及预算填报系统</div>
					</td>
					<td style="background-color:#08d845;border:1px solid #fff;" onclick="open7()">
							<img src="/portal2016/images/guide3.png"/><br />
							<div>费用报销系统</div>						
					</td>	
			</tr>
			<tr>
				<td style="background-color:#2786CA;border:1px solid #fff;border:1px solid #fff;" onclick="open8()">
						<img src="/portal2016/images/guide1.png"/><br />
						<div>九恒星系统</div>
				</td>
				<td style="background-color:#08d845;border:1px solid #fff;" onclick="open9()">
						<img src="/portal2016/images/guide3.png"/><br />
						<div>ERP主数据管理平台</div>						
				</td>	
			</tr>	
			<tr>
				<td style="background-color:#2786CA;border:1px solid #fff;border:1px solid #fff;" onclick="open10()">
						<img src="/portal2016/images/guide1.png"/><br />
						<div>投资及资产管理系统</div>
				</td>
				<td style="background-color:#08d845;border:1px solid #fff;" onclick="open11()">
						<img src="/portal2016/images/guide3.png"/><br />
						<div>ERP商务智能系统</div>						
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
	
	<script>
		function open4(){
		window.open("http://192.167.0.126:7888/eassso/login","_blank");
		}
		function open5(){
		window.open("https://nc.jsgx.net/","_blank");
		}
		function open6(){
		window.open("/interface/Entrance.jsp?id=jxbb","_blank");
		}
		function open7(){
		window.open("/interface/xh/sso_bx2.jsp","_blank");
		}
		function open8(){
		window.open("https://app.jsgxfc.com/portal/main.do","_blank");
		}
		function open9(){
		window.open("/homepage/Homepage.jsp?hpid=1041&subCompanyId=21&isfromportal=1&isfromhp=0&e71476705126857=","_blank");
		}
		function open10(){
		window.open("/interface/xh/sso_bx3.jsp","_blank");
		}
		function open11(){
		window.open("/interface/Entrance.jsp?id=sap","_blank");
		}
	</script>
</html>

