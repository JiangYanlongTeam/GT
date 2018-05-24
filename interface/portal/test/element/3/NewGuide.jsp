<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/systeminfo/init_wev8.jsp" %>
<html>
	<head>
		<title>营销部门门户</title>
		
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
					<td style="background-color:#5DCFF3;border-right:1px solid #fff;border-bottom:1px solid #fff;" _target="./ruzhi/index.html">
							<img src="images/guide_YX1.png"/>
							<div class="item">
							<div class="item1">销售入职体系</div>
							<div class="item2">欢迎你的加入，共担当、共成长</div>
							</div>
					</td>
				</tr>
				<tr>
					<td style="background-color:#5DCFF3;border-bottom:1px solid #fff;" _target="/homepage/Homepage.jsp?hpid=76&subCompanyId=1&isfromportal=1&isfromhp=0">
							<img src="images/guide_YX2.png"/>
							<div class="item">
							<div class="item1">销售随身宝典</div>						
							<div class="item2">成为客户认可的销售顾问专家</div>
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
					<td style="background-color:#5DCFF3;" _target="/homepage/Homepage.jsp?hpid=67&subCompanyId=1&isfromportal=1&isfromhp=0">
							<img src="images/guide_YX3.png"/>
							<div class="item">
							<div class="item1">岗位培训指引</div>
							<div class="item2">快速担负起岗位的工作职责</div>		
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

