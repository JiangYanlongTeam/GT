<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/systeminfo/init_wev8.jsp" %>
<html>
	<head>
		<title>公司门户</title>
		
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
		margin-left:15%;
		position:relative;
		float:left;
	}
	.item{
		float:left;
		margin-left:20px;
	}
	.item1 {
		  color: #fff;
  font-size: 18px;
  position: relative;
  line-height: 30px;
  text-align: left;
	}
	.item2 {
		color: #fff;
  font-size: 12px;
  position: relative;
  line-height: 20px;
  text-align: left;
	}
</style>
	</head>
<body style="overflow:hidden;">
		<table>
			<tbody>
				<tr>
					<td style="background-color:#A8C49C;border-right:1px solid #fff;border-bottom:1px solid #fff;" _target="/knowledgetool/data/MainInfo.jsp?mapId=17">
							<img src="images/guide_GS1.png"/>
							<div class="item">
							<div class="item1">泛微OA发展历程</div>
							<div class="item2">泛微协同OA的过去、现在与未来</div>
							</div>
					</td>
				</tr>
				<tr>
					<td style="background-color:#A8C49C;border-bottom:1px solid #fff;" _target="/knowledgetool/data/MainInfo.jsp?mapId=16">
							<img src="images/guide_GS2.png"/>
							<div class="item">
							<div class="item1">泛微人才发展体系</div>						
							<div class="item2">发现人才、尊重人才、培养人才</div>
						</div>
					</td>
				</tr>
				<tr>
					<!--
					<td style="background-color:#5DCFF3;border-right:1px solid #fff;" _target="/docs/docs/DocDsp.jsp?id=926751">
							<img src="images/guide3.png"/><br />
							<div>公司制度指引</div>				
					</td>
					-->
					<td style="background-color:#A8C49C;" _target="/knowledgetool/data/MainInfo.jsp?mapId=23">
							<img src="images/guide_GS3.png"/>
							<div class="item">
							<div class="item1">泛微发展知识体系</div>
							<div class="item2">凝聚分散智慧、协同提速发展</div>		
						</div>		
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

