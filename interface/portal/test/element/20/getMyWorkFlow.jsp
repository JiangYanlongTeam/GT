<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="weaver.conn.RecordSet"%>
<%@ page import="weaver.workflow.webservices.WorkflowBaseInfo"%>
<%@ page import="weaver.workflow.webservices.WorkflowDetailTableInfo"%>
<%@ page import="weaver.workflow.webservices.WorkflowMainTableInfo"%>
<%@ page import="weaver.workflow.webservices.WorkflowRequestInfo"%>
<%@ page import="weaver.workflow.webservices.WorkflowRequestTableField"%>
<%@ page import="weaver.workflow.webservices.WorkflowRequestTableRecord"%>
<%@ page import="weaver.workflow.webservices.WorkflowService"%>
<%@ page import="weaver.workflow.webservices.WorkflowServiceImpl"%>

<%!
/**
	 * 获取用户待办数量
	 * 
	 * @return
	 * @throws Exception
	 */
	public int getToDoWorkflowRequestCount(String hrmId,String[] params) throws Exception {
		int userId = Integer.parseInt(hrmId); // 用户id
		String[] conditions = params; // 查询条件
	    String str1 = " select distinct ";
	    String str2 = " t1.createdate,t1.createtime,t1.creater,t1.currentnodeid,t1.currentnodetype,t1.lastoperator,t1.creatertype,t1.lastoperatortype,t1.lastoperatedate,t1.lastoperatetime,t1.requestid,t1.requestname,t1.requestlevel,t1.workflowid,t2.receivedate,t2.receivetime ";
	    String str3 = " from workflow_requestbase t1,workflow_currentoperator t2 ";
	    String str4 = " where t1.requestid=t2.requestid ";
	    str4 = new StringBuilder().append(str4).append(" and t2.usertype = 0 and t2.userid = ").append(userId).toString();
	    str4 = new StringBuilder().append(str4).append(" and t2.isremark in( '0','1','5','7') and t2.islasttimes=1 ").toString();
	    if (conditions != null) {
	      for (int i = 0; i < conditions.length; i++) {
	        String str6 = conditions[i];
	        str4 = new StringBuilder().append(str4).append((str6 != null) && (!"".equals(str6)) ? new StringBuilder().append(" and ").append(str6).toString() : "").toString();
	      }
	    }
	    String str = new StringBuilder().append(" select count(*) my_count from ( ").append(str1).append(" ").append(str2).append(" ").append(str3).append(" ").append(str4).append(" ) tableA ").toString();
	    RecordSet localRecordSet = new RecordSet();
	    int i = 0;
	    try {
	      localRecordSet.executeSql(str);
	      if (localRecordSet.next())
	        i = localRecordSet.getInt("my_count");
	    }
	    catch (Exception localException) {
	      localException.printStackTrace();
	      localRecordSet.writeLog(localException);
	    }
	    return i;
	  }


/**
	 * 获取用户已办数量
	 * 
	 * @return
	 * @throws Exception
	 */
	 
	 public int getFinishedWorkflowRequestCount(String hrmId,String[] params) throws Exception {
		int userId = Integer.parseInt(hrmId); // 用户id
		String[] conditions = params; // 查询条件
	    String str1 = " select distinct ";
	    String str2 = " t1.createdate,t1.createtime,t1.creater,t1.currentnodeid,t1.currentnodetype,t1.lastoperator,t1.creatertype,t1.lastoperatortype,t1.lastoperatedate,t1.lastoperatetime,t1.requestid,t1.requestname,t1.requestlevel,t1.workflowid,t2.receivedate,t2.receivetime ";
	    String str3 = " from workflow_requestbase t1,workflow_currentoperator t2 ";
	    String str4 = " where t1.requestid=t2.requestid ";
	    str4 = new StringBuilder().append(str4).append(" and t2.usertype = 0 and t2.userid = ").append(userId).toString();
	    str4 = new StringBuilder().append(str4).append(" and t2.isremark in( '2','4') and t2.islasttimes=1 ").toString();
	    if (conditions != null) {
	      for (int i = 0; i < conditions.length; i++) {
	        String str6 = conditions[i];
	        str4 = new StringBuilder().append(str4).append((str6 != null) && (!"".equals(str6)) ? new StringBuilder().append(" and ").append(str6).toString() : "").toString();
	      }
	    }
	    String str = new StringBuilder().append(" select count(*) my_count from ( ").append(str1).append(" ").append(str2).append(" ").append(str3).append(" ").append(str4).append(" ) tableA ").toString();
	    RecordSet localRecordSet = new RecordSet();
	    int i = 0;
	    try {
	      localRecordSet.executeSql(str);
	      if (localRecordSet.next())
	        i = localRecordSet.getInt("my_count");
	    }
	    catch (Exception localException) {
	      localException.printStackTrace();
	      localRecordSet.writeLog(localException);
	    }
	    return i;
	  }

 /**
	 * 获取用户请求数量
	 * 
	 * @return
	 * @throws Exception
	 */
	  public int getMyRequestWorkflowRequestCount(String hrmId,String[] params) throws Exception {
		int userId = Integer.parseInt(hrmId); // 用户id
		String str = "select count(*) my_count from workflow_base where userid = '"+userId+"'";
		RecordSet localRecordSet = new RecordSet();
	    int i = 0;
	    try {
	      localRecordSet.executeSql(str);
	      if (localRecordSet.next())
	        i = localRecordSet.getInt("my_count");
	    }
	    catch (Exception localException) {
	      localException.printStackTrace();
	      localRecordSet.writeLog(localException);
	    }
	    return i;
	  }
 %>
<%
String hrmId = request.getParameter("hrmId");
out.print(getToDoWorkflowRequestCount(hrmId,null)+","+getFinishedWorkflowRequestCount(hrmId,null)+","+getMyRequestWorkflowRequestCount(hrmId,null));
%>