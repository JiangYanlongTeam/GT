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
		/*管理员*/
			.user_div{ width: 320px;padding:10px; }
			.user_div .user_img{ width: 64px;height: 64px;display: inline-block;*display:inline;*zoom:1; }
			.user_div .user_info{ margin-left: 10px;height: 64px;display: inline-block;*display:inline;*zoom:1;vertical-align: top; }
			.user_div .info_top{ margin-top: 3px; }
			.user_div .info_top .user_ico{ background:url(img/02_user02.png);width: 20px;height: 18px;display: inline-block;*display:inline;*zoom:1;}
			.user_div .user_name{ vertical-align: top;font-size: 14px;color:#333333;height: 18px;line-height: 18px;display: inline-block;*display:inline;*zoom:1; }
			.info_bottom{ height: 54px;line-height: 54px; }
			.info_bottom font{ font-size: 24px;color: #ff8e20;margin: 0 5px; }
			.info_bottom > div{ font-size: 12px;color: #333;vertical-align: middle;display: inline-block;*display:inline;*zoom:1; }
		/*系统管理员*/
			.systemadm{ width: 255px;height:100px;padding:15px;background: #669ce3; }
			.systemadm .adm_top{ display: block; }
			.systemadm .adm_top .user_img{ background: url(img/04_user04.png);width:30px;height:29px;display: inline-block;*display:inline;*zoom:1; }
			.systemadm .adm_name{ font-size:20px;color: #fff;padding:0 10px;height:30px;line-height:30px;display: inline-block;*display:inline;*zoom:1;vertical-align: top; }
			.systemadm .adm_bottom{ display: block;padding-top: 20px; }
			.systemadm .adm_bottom > div{ display: inline-block; }
			.systemadm .post_ico{ height: 32px;background:url(img/04_user_wdtz.png) no-repeat center center;display: block; }
			.systemadm .note_ico{ height: 32px;background:url(img/04_user_wdwd.png) no-repeat center center;display: block; }
			.systemadm .news_ico{ height: 32px;background:url(img/04_user_wdxx.png) no-repeat center center;display: block; }
			.systemadm .node_div{ padding: :0 20px; }
			.systemadm .ico_font{ font-size: 14px;color: #FFFFFF;padding: 10px 0; }
		</style>
	</head>
	<body>
		<div class="systemadm">
			<div class="adm_top">
				<div class="user_img"></div>
				<div class="adm_name">欢迎您，<%=user.getLastname()%></div>
			</div>
			<div class="adm_bottom">
				<div>
					<div class="post_ico"></div>
					<a href="/cowork/CoworkMineFrame.jsp?e71477279973236=" target="mainFrame" style="text-decoration:none;"><div class="ico_font">我的帖子</div></a>
				</div>
				<div>
					<div class="note_ico"></div>
					<a target="mainFrame" href="/docs/search/DocMain.jsp?urlType=5&e71477280074739=" style="text-decoration:none;"><div class="ico_font node_div" style="padding: 5px 35px;">我的文档</div></a>
				</div>
				<div>
					<div class="news_ico"></div>
					<a target="mainFrame" style="text-decoration:none;" href="/wui/theme/ecology8/page/getRemindInfo.jsp?1="+ new Date().getTime()><div class="ico_font">我的消息</div></a>
				</div>
			</div>
		</div>
		<!--<div class="user_div">
			<div class="user_img">
				<img src="img/02_user01.png"  />
			</div>
			<div class="user_info">
				<div class="info_top">
					<div class="user_ico"></div>
					<div class="user_name">欢迎您，<span>系统管理员</span></div>
				</div>
				<div class="info_bottom">
					<div><font>15</font>待办</div>
					<div><font>1024</font>已办</div>
					<div><font>300</font>请求</div>
				</div>
			</div>
		</div>-->

	</body>
</html>
