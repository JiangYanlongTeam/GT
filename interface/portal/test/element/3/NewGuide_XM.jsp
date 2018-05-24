<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/systeminfo/init_wev8.jsp" %>
<html>
	<head>
		<title>项目部门门户</title>
		
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
					<td style="background-color:#6FB63A;border-right:1px solid #fff;border-bottom:1px solid #fff;" _target="/docs/docs/DocDsp.jsp?id=407155">
							<img src="images/guide1.png"/><br />
							<div>入职手续指引</div>
					</td>
					<td style="background-color:#4979AE;border-bottom:1px solid #fff;" _target="/formmode/apps/ktree/index.jsp?cover=0">
							<img src="images/guide2.png"/><br />
							<div>产品学习指引</div>						
					</td>
				</tr>
				<tr>
					<td style="background-color:#77D4FB;border-right:1px solid #fff;" _target="/docs/docs/DocDsp.jsp?id=926751">
							<img src="images/guide3.png"/><br />
							<div>公司制度指引</div>				
					</td>
					<td style="background-color:#FDBF00;" _target="/knowledgetool/data/MainInfo.jsp?mapId=2">
							<img src="images/guide4.png"/>
							<div>岗位学习指引</div>				
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

