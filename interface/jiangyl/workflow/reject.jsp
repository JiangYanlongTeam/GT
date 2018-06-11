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
	String rejectmarkval = Util.null2String(request.getParameter("rejectmarkval"));
	String wfID = Util.null2String(request.getParameter("wfID"));
	String reqID = Util.null2String(request.getParameter("reqID"));
	String fqrID = Util.null2String(request.getParameter("fqrID"));
	String nodename = Util.null2String(request.getParameter("nodename"));
	String rejectnodeid = Util.null2String(request.getParameter("rejectnodeid"));
	String modedatacreatedate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
	String modedatacreatetime = new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date());
	String sql = "insert into uf_lcth (workflowid,reqid,fqr,fqjd,thjd,thry,thyj,formmodeid,modedatacreater,modedatacreatertype,modedatacreatedate,modedatacreatetime) values "+ 
		" ('"+wfID+"','"+reqID+"', '"+fqrID+"', '"+nodename+"', '"+rejectnodeid+"','"+fqrID+"','"+rejectmarkval+"','421','1','0','"+modedatacreatedate+"','"+modedatacreatetime+"') ";
	weaver.conn.RecordSet rs = new weaver.conn.RecordSet();
	rs.execute(sql);
	out.println("{\"success\":\"1\"}");
%>
 </body>
</html>