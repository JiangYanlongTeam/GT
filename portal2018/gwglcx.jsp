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
							<img src="/portal2018/images/1.png" _target="/page/element/compatible/more.jsp?ebaseid=8&eid=344"/><br />
							<div>公文查询</div>						
					</td>
				    <td style="border-bottom:1px solid #fff;" >
							<img src="/portal2018/images/1.png" _target="/system/systemmonitor/workflow/WorkflowMonitor.jsp"/><br />
					<div>公文监察</div>						
					</td>
                   	<td style="border-bottom:1px solid #fff;" >
							<img src="/portal2018/images/1.png" _target="/system/systemmonitor/workflow/WorkflowMonitor.jsp"/><br />
					<div>虚拟</div>						
					</td>
					<td style="border-bottom:1px solid #fff;" >
							<img src="/portal2018/images/1.png" _target="/interface/jiangyl/dcdb/ldpsyjview.jsp"/><br />
					<div>局领导批示意见</div>						
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

