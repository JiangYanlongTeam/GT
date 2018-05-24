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
String bmblWorkflowid = "202";
String jld1 = Util.null2String(request.getParameter("jld1"));
String jld2 = Util.null2String(request.getParameter("jld2"));
String jld3 = Util.null2String(request.getParameter("jld3"));
String jld4 = Util.null2String(request.getParameter("jld4"));
String jld5 = Util.null2String(request.getParameter("jld5"));
String jld6 = Util.null2String(request.getParameter("jld6"));
String jld7 = Util.null2String(request.getParameter("jld7"));
String jld8 = Util.null2String(request.getParameter("jld8"));
String jld9 = Util.null2String(request.getParameter("jld9"));
String jld10 = Util.null2String(request.getParameter("jld10"));

String jldrq1 = Util.null2String(request.getParameter("jldrq1"));
String jldrq2 = Util.null2String(request.getParameter("jldrq2"));
String jldrq3 = Util.null2String(request.getParameter("jldrq3"));
String jldrq4 = Util.null2String(request.getParameter("jldrq4"));
String jldrq5 = Util.null2String(request.getParameter("jldrq5"));
String jldrq6 = Util.null2String(request.getParameter("jldrq6"));
String jldrq7 = Util.null2String(request.getParameter("jldrq7"));
String jldrq8 = Util.null2String(request.getParameter("jldrq8"));
String jldrq9 = Util.null2String(request.getParameter("jldrq9"));
String jldrq10 = Util.null2String(request.getParameter("jldrq10"));

String jzyj1 = Util.null2String(request.getParameter("jzyj1"));
String jzyj2 = Util.null2String(request.getParameter("jzyj2"));
String jzyj3 = Util.null2String(request.getParameter("jzyj3"));
String jzyj4 = Util.null2String(request.getParameter("jzyj4"));
String jzyj5 = Util.null2String(request.getParameter("jzyj5"));
String jzyj6 = Util.null2String(request.getParameter("jzyj6"));
String jzyj7 = Util.null2String(request.getParameter("jzyj7"));
String jzyj8 = Util.null2String(request.getParameter("jzyj8"));
String jzyj9 = Util.null2String(request.getParameter("jzyj9"));
String jzyj10 = Util.null2String(request.getParameter("jzyj10"));

String jzxr = Util.null2String(request.getParameter("jzxr"));
String jzxr2 = Util.null2String(request.getParameter("jzxr2"));
String jzxr3 = Util.null2String(request.getParameter("jzxr3"));
String jzxr4 = Util.null2String(request.getParameter("jzxr4"));
String jzxr5 = Util.null2String(request.getParameter("jzxr5"));
String jzxr6 = Util.null2String(request.getParameter("jzxr6"));
String jzxr7 = Util.null2String(request.getParameter("jzxr7"));
String jzxr8 = Util.null2String(request.getParameter("jzxr8"));
String jzxr9 = Util.null2String(request.getParameter("jzxr9"));

String jzxcs = Util.null2String(request.getParameter("jzxcs"));
String jzxsydw = Util.null2String(request.getParameter("jzxsydw"));
String jzxfj = Util.null2String(request.getParameter("jzxfj"));

String jzxbm1 = Util.null2String(request.getParameter("jzxbm1"));
String jzxbm2 = Util.null2String(request.getParameter("jzxbm2"));
String jzxbm3 = Util.null2String(request.getParameter("jzxbm3"));
String jzxbm4 = Util.null2String(request.getParameter("jzxbm4"));
String jzxbm5 = Util.null2String(request.getParameter("jzxbm5"));
String jzxbm6 = Util.null2String(request.getParameter("jzxbm6"));
String jzxbm7 = Util.null2String(request.getParameter("jzxbm7"));
String jzxbm8 = Util.null2String(request.getParameter("jzxbm8"));
String jzxbm9 = Util.null2String(request.getParameter("jzxbm9"));
String jzxbm10 = Util.null2String(request.getParameter("jzxbm10"));


String requestid = Util.null2String(request.getParameter("requestid"));

String sql = "select * from workflow_requestbase where mainrequestid = '"+requestid+"' and workflowid = '"+bmblWorkflowid+"'";
bb.writeLog("更新主流程requestid"+requestid+"获取部门办理子流程SQL："+sql);
rs.execute(sql);
while(rs.next()) {
	String mx_requestid = Util.null2String(rs.getString("requestid"));
	bb.writeLog("部门办理子流程requestid："+mx_requestid);
	String tableName = getFormTable(mx_requestid);
	bb.writeLog("部门办理子流程requestid："+mx_requestid + "对应表名："+tableName);
	String update_sql = "update " + tableName + " set jld1 = '"+jld1+ "', jld2 = '"+ jld2+"', jld3 = '"+jld3+"', jld4 = '"+jld4+
		"', jld5 = '"+jld5+"', jld6 = '"+jld6+"', jld7 = '"+jld7+"', jld8 = '"+jld8+"', jld9 = '"+jld9+"', jld10='"+jld10+"', jldrq1='"+jldrq1+
		"', jldrq2='"+jldrq2+"', jldrq3='"+jldrq3+"', jldrq4='"+jldrq4+"', jldrq5 ='"+jldrq5+"', jldrq6='"+jldrq6+"', jldrq7='"+jldrq7+
		"', jldrq8='"+jldrq8+"', jldrq9='"+jldrq9+"', jldrq10='"+jldrq10+"', jzyj1='"+jzyj1+"', jzyj2='"+jzyj2+"', jzyj3='"+jzyj3+
		"' ,jzyj4='"+jzyj4+",', jzyj5='"+jzyj5+"', jzyj6='"+jzyj6+"', jzyj7='"+jzyj7+"', jzyj8='"+jzyj8+"', jzyj9='"+jzyj9+"' ,jzyj10='"+jzyj10+
		"', jzxr='"+jzxr+"' ,jzxbm1='"+jzxbm1+"', jzxbm2='"+jzxbm2+"', jzxbm3='"+jzxbm3+"' ,jzxbm4='"+jzxbm4+"', jzxbm5='"+jzxbm5+
		"', jzxbm6='"+jzxbm6+"' ,jzxbm7='"+jzxbm7+"' ,jzxbm8='"+jzxbm8+"', jzxbm9='"+jzxbm9+"', jzxbm10='"+jzxbm10+"', jzxr2='"+jzxr2+
		"', jzxr3='"+jzxr3+"' ,jzxr4='"+jzxr4+"' ,jzxr5='"+jzxr5+"', jzxr6='"+jzxr6+"', jzxr7='"+jzxr7+"', jzxr8='"+jzxr8+"', jzxr9='"+jzxr9+
		"', jzxcs='"+jzxcs+"' ,jzxsydw='"+jzxsydw+"' ,jzxfj='"+jzxfj+
		"' where requestid = '"+mx_requestid+"'";
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
