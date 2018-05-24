<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/weaver.tld" prefix="wea"%>
<%@ taglib uri="/WEB-INF/weaver.tld" prefix="wea1"%>
<%@ include file="/systeminfo/init_wev8.jsp" %>
<%@ page import="weaver.general.Util"%>
<%@ page import="java.lang.*"%>
<jsp:useBean id="RecordSet" class="weaver.conn.RecordSet" scope="page" />
<!DOCTYPE html>
<HTML><HEAD>
<LINK href="/mobile/plugin/rightframe/css/bootstrap.css" type="text/css" rel="STYLESHEET">
<LINK href="/css/Weaver_wev8.css" type=text/css rel=STYLESHEET>
<style>
*{
	font-size:12pt !important;
	font-family: 微软雅黑 !important;
}
</style>
</HEAD>
<body style="margin:0 auto">
<%
	String requestid = Util.null2String(request.getParameter("requestid"));
	weaver.interfaces.action.WorkflowSign GWSWBL = new weaver.interfaces.action.WorkflowSign();
	String htmlSign = GWSWBL.getWorkflowSignHtml(requestid);
	out.print(htmlSign);
%>
 </body>
</html>