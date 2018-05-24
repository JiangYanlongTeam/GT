<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="weaver.hrm.HrmUserVarify"%>
<%@ page import="weaver.hrm.User"%>
<%
    /*用户验证*/
    User user = HrmUserVarify.getUser(request, response);
    if (user == null) {
        response.sendRedirect("/login/Login.jsp");
        return;
    }
%>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<style>
			.user_div{ width: 270px;height:100px;}
			
			.user_div .user_info{ margin-left: 10px;height: 100px;display: inline-block;*display:inline;*zoom:1;vertical-align: middle; }
			
		</style>
	</head>
	<body>
		<div class="user_div" style="background:#445c74;border-radius:20px;">
			<div class="user_img" style="vertical-align: top;font-size: 14px;color:#ffffff;">
				<img src="img/person.png" style="padding-left:30px;width:30%;height:30%;float:left;" />
				<div style="padding-top:30px;padding-right:80px;float:right;">
			    欢迎您，</br></br><span style="padding-top:15px;"><%=user.getLastname()%></span>
				</div>
			</div>
		</div>

	</body>
</html>
