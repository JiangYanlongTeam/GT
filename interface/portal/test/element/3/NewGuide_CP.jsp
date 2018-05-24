<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/systeminfo/init_wev8.jsp" %>
<html>
	<head>
		<title>产品门户</title>
		
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
		margin-top:-14px;
	}
</style>
	</head>
<body style="overflow:hidden;">
		<table>
			<tbody>
				<tr>
					<td style="background-color:#B7D100;border-right:1px solid #fff;border-bottom:1px solid #fff;" _target="/docs/docs/DocDsp.jsp?id=407155">
							<img src="images/guide_CP1.png"/>
							<div>入职手续指引</div>
					</td>
					<td style="background-color:#A6D6D1;border-bottom:1px solid #fff;" _target="/docs/docs/DocDsp.jsp?id=1800817">
							<img src="images/guide_CP2.png"/>
							<div>产品学习指引</div>						
					</td>
				</tr>
				<tr>
					<td colspan="2" style="background-color:#CBCBCB;border-bottom:1px solid #fff;" _target="/docs/docs/DocDspExt.jsp?id=1724183">
							<img src="images/guide_CP3.png" style="position:relative;margin-left:-50%;"/>
							<div style="position:absolute;left:40%;top:50%;">OA的历史、现在、未来</div>
					</td>
				</tr>
					<td style="background-color:#D5C72B;border-right:1px solid #fff;border-bottom:1px solid #fff;" _target="/docs/docs/DocDsp.jsp?id=926751">
						<div>
						<img src="images/guide_CP4.png"/>
						<div>公司制度指引</div>
						</div>
					</td>
					<td style="background-color:#589BED;border-bottom:1px solid #fff;" _target=" /docs/docs/DocDsp.jsp?id=1800818">
						<img src="images/guide_CP5.png"/>
						<div>岗位学习指引</div>
					</td>
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

