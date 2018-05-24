<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="weaver.general.Util" %>
<%@ page import="java.util.*" %>
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="rs1" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="bb" class="weaver.general.BaseBean"></jsp:useBean>
<jsp:useBean id="ResourceComInfo" class="weaver.hrm.resource.ResourceComInfo" scope="page" />
<jsp:useBean id="DepartmentComInfo" class="weaver.hrm.company.DepartmentComInfo" scope="page"/>
<jsp:useBean id="SubCompanyComInfo" class="weaver.hrm.company.SubCompanyComInfo" scope="page" />
<%
String bmhqWorkflowid = "60";//部门会签的workflow
String bgshg = Util.null2String(request.getParameter("bgshg"));
String bgsqm = Util.null2String(request.getParameter("bgsqm"));
String bgsqmrq = Util.null2String(request.getParameter("bgsqmrq"));
String jzyj = Util.null2String(request.getParameter("jzyj"));
String jzqm = Util.null2String(request.getParameter("jzqm"));
String jzqmrq = Util.null2String(request.getParameter("jzqmrq"));
String fgjldyj1 = Util.null2String(request.getParameter("fgjldyj1"));
String fgjldyj2 = Util.null2String(request.getParameter("fgjldyj2"));
String fgjldyj3 = Util.null2String(request.getParameter("fgjldyj3"));
String fgjldyj4 = Util.null2String(request.getParameter("fgjldyj4"));
String fgjldqm1 = Util.null2String(request.getParameter("fgjldqm1"));
String fgjldqm2 = Util.null2String(request.getParameter("fgjldqm2"));
String fgjldqm3 = Util.null2String(request.getParameter("fgjldqm3"));
String fgjldqm4 = Util.null2String(request.getParameter("fgjldqm4"));
String fgjldqmrq1 = Util.null2String(request.getParameter("fgjldqmrq1"));
String fgjldqmrq2 = Util.null2String(request.getParameter("fgjldqmrq2"));
String fgjldqmrq3 = Util.null2String(request.getParameter("fgjldqmrq3"));
String fgjldqmrq4 = Util.null2String(request.getParameter("fgjldqmrq4"));

String requestid = Util.null2String(request.getParameter("requestid"));

String sql = "select * from workflow_requestbase where mainrequestid = '"+requestid+"' and workflowid = '"+bmhqWorkflowid+"'";
bb.writeLog("更新公文拟报主流程requestid"+requestid+"获取部门会签子流程SQL："+sql);
rs.execute(sql);
while(rs.next()) {
	String mx_requestid = Util.null2String(rs.getString("requestid"));
	bb.writeLog("部门会签子流程requestid："+mx_requestid);
	String tableName = getFormTable(mx_requestid);
	bb.writeLog("部门会签子流程requestid："+mx_requestid + "对应表名："+tableName);
	String update_sql = "update " + tableName + " set bgshg = '"+bgshg+ "', bgsqm = '"+ bgsqm+"', bgsqmrq = '"+bgsqmrq+"', jzyj = '"+jzyj+
		"', jzqm = '"+jzqm+"', jzqmrq = '"+jzqmrq+"', fgjldyj1 = '"+fgjldyj1+"', fgjldyj2 = '"+fgjldyj2+"', fgjldyj3 = '"+fgjldyj3+"', fgjldyj4='"+fgjldyj4+"', fgjldqm1='"+fgjldqm1+"', fgjldqm2='"+fgjldqm2+"', fgjldqm3='"+fgjldqm3+"', fgjldqm4='"+fgjldqm4+"', fgjldqmrq1 ='"+fgjldqmrq1+"', fgjldqmrq2='"+
		fgjldqmrq2+"' ,fgjldqmrq3='"+fgjldqmrq3+"', fgjldqmrq4='"+fgjldqmrq4+"' where requestid = '"+mx_requestid+"'";
	bb.writeLog("更新部门会签子流程SQL:"+update_sql);
	rs1.execute(update_sql);
}
out.println("{\"success\":\"1\"}");
%>
<%!
public String getFormTable(String requestid){
	String sql = "select abs(formid) formid from workflow_base where id = (select workflowid from workflow_requestbase where requestid = '"+requestid+"')";
	weaver.conn.RecordSet set = new weaver.conn.RecordSet();
	set.execute(sql);
	set.next();
	String formid = Util.null2String(set.getString("formid"));
	return "formtable_main_"+formid;
}
%>
