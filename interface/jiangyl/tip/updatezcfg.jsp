<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="weaver.general.Util" %>
<%@ page import="java.util.*" %>
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="rs1" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="rs2" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="rs3" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="rs4" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="bb" class="weaver.general.BaseBean"></jsp:useBean>
<jsp:useBean id="ResourceComInfo" class="weaver.hrm.resource.ResourceComInfo" scope="page" />
<jsp:useBean id="DepartmentComInfo" class="weaver.hrm.company.DepartmentComInfo" scope="page"/>
<jsp:useBean id="SubCompanyComInfo" class="weaver.hrm.company.SubCompanyComInfo" scope="page" />
<%
bb.writeLog("主流程公文拟报");
String bmhqWorkflowid = "60";
String gwnbWorkflowid = "59";
String hfxsc = Util.null2String(request.getParameter("hfxsc"));
String zcfgcqm = Util.null2String(request.getParameter("zcfgcqm"));
String zcfgcqmrq = Util.null2String(request.getParameter("zcfgcqmrq"));

String requestid = Util.null2String(request.getParameter("requestid"));//当前子流程requestid
String main_requestid ="";//主流程requestid
String sql = "select * from workflow_requestbase where requestid = '"+requestid+"' and workflowid = '"+bmhqWorkflowid+"'";

rs.execute(sql);
if(rs.next()){
	main_requestid = Util.null2String(rs.getString("mainrequestid"));
	bb.writeLog("主流程公文拟报的requestid："+main_requestid);
	String tableName = getFormTable(main_requestid);
	bb.writeLog("主流程公文拟报的requestid："+main_requestid + "对应表名："+tableName);
	String update_sql1= "update " + tableName +" set hfxsc ='"+hfxsc+"', zcfgcqm='"+zcfgcqm+"',zcfgcqmrq='"+zcfgcqmrq+"' where  requestid = '"+main_requestid+"'";
	bb.writeLog("更新主流程公文拟报SQL:"+update_sql1);
	rs1.execute(update_sql1);
}

String sql2="select * from workflow_requestbase where mainrequestid = '"+main_requestid+"'";
rs2.execute(sql2);
while(rs2.next()) {
	String hq_requestid = Util.null2String(rs2.getString("requestid"));
	bb.writeLog("当前部门会签子流程requestid："+hq_requestid);
	String tableName = getFormTable(hq_requestid);
	bb.writeLog("当前部门会签子流程requestid："+hq_requestid + "对应表名："+tableName);
	String update_sql2 = "update " + tableName +" set hfxsc ='"+hfxsc+"', zcfgcqm='"+zcfgcqm+"',zcfgcqmrq='"+zcfgcqmrq+"' where  requestid = '"+hq_requestid+"'";
	bb.writeLog("更新部门会签子流程SQL:"+update_sql2);
	rs3.execute(update_sql2);
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
