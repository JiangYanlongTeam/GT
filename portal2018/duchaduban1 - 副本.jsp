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
		width:20%;
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
					<td style="border-bottom:1px solid #fff;" _target="/interface/jiangyl/dcdb/dcdblist.jsp">
							<img src="/portal2018/images/3.png"/><br />
							<div>发起督查督办</div>						
					</td>
					<td style="border-right:1px solid #fff;border-bottom:1px solid #fff;" _target="/interface/jiangyl/dcdb/viewdcdb.jsp">
							<img src="/portal2018/images/3.png"/><br />
							<div>督查督办办理</div>
					</td>

					<td style="border-bottom:1px solid #fff;" _target="/interface/jiangyl/dcdb/dcdbcqsx.jsp">
							<img src="/portal2018/images/3.png"/><br />
							<div>督查督办超期事项</div>						
					</td>
					<td style="border-right:1px solid #fff;border-bottom:1px solid #fff;" _target="/interface/jiangyl/dcdb/dcdblist.jsp">
							<img src="/portal2018/images/3.png"/><br />
							<div>督查督办催办</div>
					</td>
					<td style="border-right:1px solid #fff;border-bottom:1px solid #fff;" _target="/interface/jiangyl/dcdb/dcdbtj.jsp">
							<img src="/portal2018/images/3.png"/><br />
							<div>督查督办统计</div>
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

