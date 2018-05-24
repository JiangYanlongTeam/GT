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
	tr {
		height:65px;
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
		width:180px;
		height:43px;
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
						<td style="border:1px solid #fff;border:1px solid #fff;" onclick="open4()">
								<img src="/portal2016/images/OA1_03.png" align="right"/>
						</td>	
						<td style="border:1px solid #fff;" onclick="open5()">
								<img src="/portal2016/images/OA1_05.png" align="left"/>						
						</td>
									
				</tr>
			</tr>
				<tr >
					<td style="border:1px solid #fff;border:1px solid #fff;" onclick="open6()">
							<img src="/portal2016/images/OA1_09.png" align="right"/>
					</td>
					<td style="border:1px solid #fff;" onclick="open7()">
							<img src="/portal2016/images/OA1_10.png" align="left"/>				
					</td>	
			</tr>
			<tr >
				<td style="border:1px solid #fff;border:1px solid #fff;" onclick="open8()">
						<img src="/portal2016/images/OA1_13.png" align="right"/>
				</td>
				<td style="border:1px solid #fff;" onclick="open9()">
						<img src="/portal2016/images/OA1_14.png" align="left"/>				
				</td>	
			</tr>	
			<tr >
				<td style="border:1px solid #fff;border:1px solid #fff;" onclick="open10()">
						<img src="/portal2016/images/OA1_17.png" align="right"/>
				</td>
				<td style="border:1px solid #fff;" onclick="open11()">
						<img src="/portal2016/images/OA1_18.png" align="left"/>				
				</td>	
			</tr>
			<tr >
				<td style="border:1px solid #fff;border:1px solid #fff;" onclick="open12()">
						<img src="/portal2016/images/OA1_19.png" align="right"/>
				</td>
				<td>
					
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
		window.open("http://10.254.44.28:7888/easportal","_blank");
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
		function open12(){
		window.open("/interface/Entrance.jsp?id=docsys","_blank");
		}
	</script>
</html>

