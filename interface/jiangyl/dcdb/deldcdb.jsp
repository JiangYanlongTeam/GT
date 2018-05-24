<%
	weaver.hrm.User user = new weaver.hrm.HrmUserVarify().getUser (request , response) ;
	String reqid = new weaver.general.Util().null2String(request.getParameter("reqid"));
	String sql = "select mainrequestid from workflow_requestbase where requestid = '"+reqid+"'";
	weaver.conn.RecordSet rs = new weaver.conn.RecordSet();
	rs.execute(sql);
	rs.next();
	String main_requestid = new weaver.general.Util().null2String(rs.getString("mainrequestid"));
	String sql2 = "select count(*) count from workflow_requestbase where mainrequestid = '"+main_requestid+"' and workflowid = '63'";
	rs.execute(sql2);
	rs.next();
	String _count = new weaver.general.Util().null2String(rs.getString("count"));
	if("".equals(_count)) {
		_count = "0";
	}
	new weaver.general.BaseBean().writeLog(Integer.parseInt(_count) == 1);
	if(Integer.parseInt(_count) == 1){
		String sql3 = "update formtable_main_37 set sfcfdb = '1' where requestid = '"+main_requestid+"' ";
		rs.execute(sql3);
	}
	int userid = user.getUID();
	weaver.workflow.webservices.WorkflowService service = new weaver.workflow.webservices.WorkflowServiceImpl();
	boolean issuccess = service.deleteRequest(Integer.parseInt(reqid),userid);
	if(issuccess) {
		out.println("{\"success\":\"删除成功\"}");
	} else {
		out.println("{\"success\":\"删除失败\"}");
	}
%>