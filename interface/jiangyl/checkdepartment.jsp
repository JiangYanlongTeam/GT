<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="weaver.general.Util" %>
<%@ page import="weaver.conn.*" %>
<%@ page import="weaver.hrm.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="weaver.general.AttachFileUtil" %>
<%@ page import="weaver.workflow.request.RevisionConstants" %>
<%@ page import="weaver.file.FileUpload" %>
<jsp:useBean id="RecordSet" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="ResourceComInfo" class="weaver.hrm.resource.ResourceComInfo" scope="page"/>
<%
User user = HrmUserVarify.getUser(request,response);
String userid = "" + user.getUID();
String departmentid = "";
try{
	departmentid = ResourceComInfo.getDepartmentID(userid);
}catch(Exception e) {
}
String hrmresourceids = Util.null2String(request.getParameter("hrmresourceids"));
if("".equals(hrmresourceids)) {
	out.println("{\"success\":\"0\",\"message\":\"\"}");
	return;
} else {
	try{
		String _departmentid = ResourceComInfo.getDepartmentID(hrmresourceids);
		if(_departmentid.equals(departmentid)) {
			out.println("{\"success\":\"0\",\"message\":\"\"}");
			return;
		} else {
			out.println("{\"success\":\"1\",\"message\":\"请选择本部门人员\"}");
			return;
		}
	}catch(Exception e) {
	}
}
%>
