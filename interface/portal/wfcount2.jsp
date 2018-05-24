<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="weaver.general.*"%>
<%@ page import="weaver.hrm.*" %>
<%
	User user = HrmUserVarify.getUser (request , response) ;
	if (null == user) { 
        response.sendRedirect("/login/Login.jsp");
        return;
    }
	int userid = user.getUID();
	String wfid = Util.null2String(request.getParameter("workflowid"));
	String[] workflowids = wfid.split("\\|");
	String wfids = "";
	String at_wfid = "";
	if(workflowids.length > 1) {
		wfids = workflowids[0];
		at_wfid = workflowids[1];
	} else {
		wfids = wfid;
	}
	
	weaver.interfaces.action.WorkflowUtil workflowUtil = new weaver.interfaces.action.WorkflowUtil();
	int count = workflowUtil.getWorkflowCountByWorkflowidAndUserId(wfids,userid);
	int _count = 0;
	if(!"".equals(at_wfid)) {
		String sql = "select count(*) count from OFS_TODO_DATA a, OFS_WORKFLOW b where a.WORKFLOWNAME = b.WORKFLOWNAME and a.userid = '"+userid+"' and b.WORKFLOWID in ("+at_wfid+")";
		weaver.conn.RecordSet rs = new weaver.conn.RecordSet();
		rs.execute(sql);
		rs.next();
		String at_count = new weaver.general.Util().null2String(rs.getString("count"));
		if(!"".equals(at_count)) {
			_count = Integer.parseInt(at_count);
		}
	}
	int total = count + _count;
	
	out.println("{\"count\":\""+total+"\"}");
%>