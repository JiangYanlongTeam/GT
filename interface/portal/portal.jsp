<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>OA登录</title>
</head>
<%
	String typeid=request.getParameter("typeid");
	String token=request.getParameter("token");
	String key=request.getParameter("key");
    weaver.interfaces.action.AT_SSO atso=new weaver.interfaces.action.AT_SSO();
    weaver.interfaces.action.UserInfo user=atso.sso(token,key);
	//out.println(user);
	//out.println(typeid);
	if(null != typeid && !"".equals(typeid)&&null!=user) {
		%>
		<body>
	<div style="display:none">
	<form name="form" action="http://192.168.1.206:8080/login/VerifyLogin.jsp" method="post">
		<input id="loginid" name="loginid" value="<%=user.getUsername()%>">
		<input id="userpassword" name="userpassword" type="password" value="<%=user.getPassword()%>">
		<input id="logintype" name="logintype" value="1">
		<input id="formmethod" name="formmethod" value="post">
		<input id="rnd" name="rnd" value="">
		<input id="isie" name="isie" value="false">
		<input id="serial" name="serial" value="">
		<input id="username" name="username" value="">
		<input id="islanguid" name="islanguid" value="7">
		<input id="gopage" name="gopage" value="<%=typeid%>">
		<input id="loginfile" name="loginfile" value="/wui/theme/ecology8/page/login.jsp?templateId=3&logintype=1">
		<input type="submit" value="" class="btn2"  />
	</form>
	</div>
</body>
<script>

	document.getElementById("gopage").value = "<%=typeid%>";
	document.form.submit();
</script>
		<%
	}
	else {
%>
		<script>
			alert("会话超时，请重新登陆");
			window.close();
		</script>
<%} %>
</html>