<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="weaver.hrm.HrmUserVarify"%>
<%@ page import="weaver.hrm.User"%>
<%
    /*用户验证*/
    User user = HrmUserVarify.getUser(request, response);
	
	int userString = -1;
    if (user == null) {
        response.sendRedirect("/login/Login.jsp");
        return;
    }else{userString = user.getUID();}
%>
<html><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta charset="UTF-8">
		<title></title>
	</head>
	<body>
	 <script type="text/javascript" src="/js/jquery/jquery-1.4.2.min_wev8.js"></script>
		<style type="text/css"> 
		.dealwith{
			float: none;
			font-weight: 700;
			font-size:14px;
		}
		.panel{
			border-radius: 20px;
			width: 215px;
			height: 80px;
			background: #183e68;
			padding:5px 0  5px 10px ;
		}
		.date{
			border-radius: 10px;
			width: 80px;
			height: 70px;
			margin: 5px;
			background: white;
			float: left;
		}
		.right{
			
			height: 70px;
			margin: 5px;
			 
			float: left;
			color: white;
		}
		.count{
			border-radius: 20px;
		    display: inline-block;
			background: red;
			line-height: 24px;
			padding: 0px 12px;
			height: 24px;
			margin-bottom: 8px;
		}
		 .week{
		 	background: red;
		 	border-radius: 10px 10px 0 0;
		 	color: white;
		 	font-family: "微软雅黑";
		 	font-weight: 800;
		 	text-align: center;
		 	font-size: 12px;
		 	height: 20px
		 	;
		 	line-height: 20px;;
		 	
		 }
		 .days{
		 	text-align: center;
		 	line-height: 55px;
		 	font-size: 40px;
		 	font-family:"微软雅黑";
		 }
		 .ntime{
			font-size: 12px;
			line-height: 2;
		}
		.ltime{
			font-size: 12px;
			
		}
		 @-moz-document url-prefix() {
			.ltime{font-size: 13px;font-weight: 700;}
		}
		</style>
		
		
		<div id="panel" class="panel">
			 
			
			<div class="date">
				<div id = "week" class="week"></div>
				<div id = "days" class="days">
					
					
				</div>
					
			</div>
			<div class="right">
			
				<div class="dealwith">
					 <a href="/workflow/request/RequestView.jsp?e71480478386051=" target="_blank" style="text-decoration:none;color:#ffffff;padding-left:0px;" >待办事宜
					<span id="count" class="count"></span></a>
				</div>
				<div id="nongli" class="ntime" style=""></div>
				<div id="date" class="ltime" style=""></div>
			</div>
			
		</div>
	

</body>
<script type="text/javascript" src="js/calendar.js"></script>
<script type = "text/javascript">
		 var d = new Date();
		 var week;
		 var week2;
		 var lunar = calendar.solar2lunar();
		 switch (d.getDay()){
		 case 1: week="Monday"; week2="周一"; break;
		 case 2: week="Tuesday"; week2="周二";  break;
		 case 3: week="Wednesday"; week2="周三";  break;
		 case 4: week="Thursday"; week2="周四";  break;
		 case 5: week="Friday"; week2="周五";  break;
		 case 6: week="Saturday"; week2="周六";  break;
		 default: week="Sunday"; week2="周日"; 
		 }
		var str = "" + d.getFullYear() + "/";
	    str += (d.getMonth()+1) + "/";
	    str += d.getDate();
		 jQuery("#week").html(week);
		 jQuery("#days").html(d.getDate());
		 jQuery("#nongli").html("农历 "+lunar.IMonthCn+lunar.IDayCn);
		 jQuery("#date").html(str+' '+week2);
		 
</script>
<script type="text/javascript">
var userId = <%=userString%>;
$.ajax({
	  type: 'POST',
	  dataType: "html",
	  cache:false,
	  url: "getMyWorkFlow.jsp?id="+Math.random(),
	  data: {hrmId:userId},
	  success: function(data){
		//data = data.replaceAll("\\data+", "");
		data = data.replace(/[\r\n]/g,"");
		var str = data.split(",");
		jQuery("#count").text(str[0]);
	  },
	  error: function(){
	    alert("网络异常，请检查网络！");
	  }
	});
</script>
</html>