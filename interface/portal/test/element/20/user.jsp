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
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<script type="text/javascript" src="/js/jquery/jquery-1.4.2.min_wev8.js"></script>
		<style>
			.user_div{ width: 308px;}
			.user_div .user_img{ width: 64px;height: 64px;display: inline-block;*display:inline;*zoom:1; }
			.user_div .user_info{ margin-left: 10px;height: 64px;display: inline-block;*display:inline;*zoom:1;vertical-align: top; }
			.user_div .info_top{ margin-top: 3px; }
			.user_div .info_top .user_ico{ background:url(img/02_user02.png);width: 20px;height: 18px;display: inline-block;*display:inline;*zoom:1;}
			.user_div .user_name{ vertical-align: top;font-size: 14px;color:#333333;height: 18px;line-height: 18px;display: inline-block;*display:inline;*zoom:1; }
			.info_bottom{ height: 54px;line-height: 54px; }
			.info_bottom a{ font-size: 24px;color: #ff8e20;margin: 0 5px;text-decoration:none; }
			.info_bottom > div{ font-size: 12px;color: #333;vertical-align: middle;display: inline-block;*display:inline;*zoom:1; }
		</style>
	</head>
	<body>
		<div class="user_div">
			<div class="user_img">
				<img src="img/02_user01.png"  />
			</div>
			<div class="user_info">
				<div class="info_top">
					<div class="user_ico"></div>
					<div class="user_name">欢迎您，<span><%=user.getLastname()%></span></div>
				</div>
				<div class="info_bottom">
					<div id="unfinished"><a href="/workflow/request/RequestView.jsp?e71482198758371=" target="_blank" ></a>待办</div>
					<div id="finished"><a  href="/workflow/request/RequestHandled.jsp" target="_blank"></a>已办</div>
					<div id="myrequest"><a  href="/workflow/request/MyRequestView.jsp" target="_blank"></a>请求</div>
				</div>
			</div>
		</div>

	</body>
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
		jQuery("#unfinished a").text(str[0]);
		jQuery("#finished a").text(str[1]);
		jQuery("#myrequest a").text(str[2]);
	  },
	  error: function(){
	    alert("网络异常，请检查网络！");
	  }
	});
</script>
</html>
