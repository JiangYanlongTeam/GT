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
String bmblWorkflowid = "102";
String jld1 = Util.null2String(request.getParameter("jld1"));
String requestid = Util.null2String(request.getParameter("requestid"));

String sql = "select * from workflow_requestbase where mainrequestid = '"+requestid+"' and workflowid = '"+bmblWorkflowid+"'";
bb.writeLog("更新主流程requestid"+requestid+"获取部门办理子流程SQL："+sql);
rs.execute(sql);
while(rs.next()) {
	String mx_requestid = Util.null2String(rs.getString("requestid"));
	bb.writeLog("部门办理子流程requestid："+mx_requestid);
	String tableName = getFormTable(mx_requestid);
	bb.writeLog("部门办理子流程requestid："+mx_requestid + "对应表名："+tableName);
	String update_sql = "update " + tableName + " set jld1 = '"+jld1+"' where requestid = '"+mx_requestid+"'";
	bb.writeLog("更新部门办理子流程SQL:"+update_sql);
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
