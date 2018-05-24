<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="weaver.hrm.User"%>
<%@ page import="weaver.hrm.HrmUserVarify"%>
<%@ page import="weaver.general.Util"%>
<%@ page import="weaver.conn.RecordSet"%>
<%@ taglib uri="/WEB-INF/weaver.tld" prefix="wea"%>
<jsp:useBean id="dc" class="weaver.docs.docs.DocComInfo" scope="page"/>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	/*用户验证*/
	User user = HrmUserVarify.getUser(request, response);
	if (user == null) {
		response.sendRedirect("/login/Login.jsp");
		return;
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script type="text/javascript">
function openDoc(url) {
	window.open(url);
}
</script>
<style type="text/css">
body {
	margin: 0;
	padding: 0;
}

.container {
	width: 100%;
	height: 100%;
	margin-top: 10px;
}

.rz {
	float: left;
	width: 24%;
	text-align: center;
}

.zd {
	float: left;
	width: 24%;
	text-align: center;
}

.cp {
	float: left;
	width: 24%;
	text-align: center;
}

.gw {
	float: left;
	width: 24%;
	text-align: center;
}

.p_rz {
	width: 70%;
	height: 112px;
	position: relative;
	cursor: pointer;
	padding-top: 12px;
	margin: auto;
}

.p_zd {
	width: 70%;
	height: 112px;
	position: relative;
	cursor: pointer;
	padding-top: 12px;
	margin: auto;
}

.p_cp {
	width: 70%;
	height: 112px;
	position: relative;
	cursor: pointer;
	padding-top: 12px;
	margin: auto;
}

.p_gw {
	width: 70%;
	height: 112px;
	position: relative;
	cursor: pointer;
	padding-top: 12px;
	margin: auto;
}

.c_rz {
	width: 53px;
	height: 58px;
	margin: 0 auto;
	background: url('img/ruzhi_h.png') no-repeat;
	margin-bottom: 14px;
}

.t_rz {
	color: #59c7f3;
	font-family: 微软雅黑;
	font-size: 14px
}

.c_zd {
	width: 53px;
	height: 58px;
	margin: 0 auto;
	background: url('img/shez.png') no-repeat;
	margin-bottom: 14px;
}

.t_zd {
	color: #93aafe;
	font-family: 微软雅黑;
	font-size: 14px
}

.c_cp {
	width: 60px;
	height: 60px;
	margin: 0 auto;
	background: url('img/dev.png') no-repeat;
	margin-bottom: 14px;
}

.t_cp {
	color: #ffa66a;
	font-family: 微软雅黑;
	font-size: 14px
}

.c_gw {
	width: 60px;
	height: 60px;
	margin: 0 auto;
	background: url('img/set.png') no-repeat;
	margin-bottom: 14px;
}

.t_gw {
	color: #97de62;
	font-family: 微软雅黑;
	font-size: 14px
}

	.t_test {
	color: #59c6f4;
	font-family: 微软雅黑;
	font-size: 14px
}


.c_test {
	width: 53px;
	height: 58px;
	margin: 0 auto;
	background: url('img/test.png') no-repeat;
	margin-bottom: 14px;
}




.p_rz:hover .c_rz {
	background: url('img/ruzhi.png') no-repeat;
}

.p_rz:hover .t_rz {
	color: #fff;
}

.p_rz:hover {
	background-color: #59c7f3;
}

.p_zd:hover .c_zd {
	background: url('img/shez_h.png') no-repeat;
}

.p_zd:hover .t_zd {
	color: #fff;
}

.p_zd:hover {
	background-color: #59c7f3;
}

.p_cp:hover .c_cp {
	background: url('img/dev_h.png') no-repeat;
}

.p_cp:hover .t_cp {
	color: #fff;
}

.p_cp:hover {
	background-color: #59c7f3;
}

.p_gw:hover .c_gw {
	background: url('img/set_h.png') no-repeat;
}

.p_gw:hover .c_test {
	background: url('img/test_h.png') no-repeat;
}

.p_gw:hover .t_gw {
	color: #fff;
}

.p_gw:hover .t_test {
	color: #fff;
}

.p_gw:hover {
	background-color: #59c7f3;
}

.line {
	float: left;
	border-right: 1px solid #ececed;
	height: 48px;
	margin-top: 38px;
}
</style>
</head>
<body>
	<div class="container">
		<div class="rz">
			<div class="p_rz" onclick="openDoc('/docs/docs/DocDsp.jsp?id=1941251')">
				<div class="c_rz"></div>
				<span class="t_rz">入职手续指引</span>
			</div>
		</div>
		<div class="line"></div>
		<div class="cp">
			<div class="p_cp" onclick="openDoc('/docs/docs/DocDsp.jsp?id=1943207')">
				<div class="c_cp"></div>
				<span class="t_cp">开发岗位指引</span>
			</div>
		</div>
		<div class="line"></div>
		<div class="gw">
			<div class="p_gw" onclick="openDoc('/docs/docs/DocDsp.jsp?id=191138')">
				<div class="c_gw"></div>
				<span class="t_gw">测试岗位指引</span>
			</div>
		</div>
		<div class="line"></div>
		<div class="gw">
			<div class="p_gw" onclick="openDoc('/docs/docs/DocDsp.jsp?id=1084939')">
				<div class="c_test"></div>
				<span class="t_test">配置岗位指引</span>
			</div>
		</div>
	</div>
</body>
</html>