<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="weaver.general.Util" %>
<%@ page import="weaver.file.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.alibaba.fastjson.*" %>
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="rs1" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="rs2" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="mysmt" class="weaver.conn.RecordSet"/>
<jsp:useBean id="bb" class="weaver.general.BaseBean"></jsp:useBean>
<jsp:useBean id="ResourceComInfo" class="weaver.hrm.resource.ResourceComInfo" scope="page" />
<jsp:useBean id="DepartmentComInfo" class="weaver.hrm.company.DepartmentComInfo" scope="page"/>
<jsp:useBean id="SubCompanyComInfo" class="weaver.hrm.company.SubCompanyComInfo" scope="page" />
<%
FileUpload fu = new FileUpload(request);
String iid = Util.null2String(fu.getParameter("iid"));
String wiid = Util.null2String(fu.getParameter("wiid"));
org.tempuri.NetOfficeServiceSoapProxy proxy = new org.tempuri.NetOfficeServiceSoapProxy();
String json = proxy.jsonSubmitUser(iid,wiid);
JSONObject jsonObject = JSON.parseObject(json);
bb.writeLog("result:" + jsonObject.toString());
JSONArray FlowList = jsonObject.getJSONArray("FlowList");
StringBuffer userids = new StringBuffer();
StringBuffer usernames = new StringBuffer();
StringBuffer usersendcout = new StringBuffer();
StringBuffer flowids = new StringBuffer();
StringBuffer loginnames = new StringBuffer();
for (int i = 0; i < FlowList.size(); i++) {
	JSONObject object = (JSONObject) FlowList.get(i);
	JSONArray UserList = object.getJSONArray("UserList");
	String FlowId = (String)object.get("FlowId");
	Integer UserSendCount = (Integer)object.get("UserSendCount");
	if("岗位内提交".equals(FlowId)) {
		continue;
	}
	for (int j = 0; j < UserList.size(); j++) {
		JSONObject user = (JSONObject) UserList.get(j);
		String UserId = (String)(user.get("UserId"));
		String UserName = (String)(user.get("UserName"));
		String LoginName = (String)(user.get("LoginName"));
		userids.append(UserId);
		usernames.append(UserName);
		loginnames.append(LoginName);
		if (j != UserList.size() - 1) {
			userids.append(";");
			usernames.append(";");
			loginnames.append(";");
		}
	}
	flowids.append(FlowId);
	usersendcout.append(UserSendCount);
	break;
}
String uids = userids.toString();
String unames = usernames.toString();
String flowidss = flowids.toString();
String usercount = usersendcout.toString();
String loginnamess = loginnames.toString();
bb.writeLog("uids:"+uids);
bb.writeLog("unames:"+unames);
out.print("{\"userids\":\""+uids+"\",\"usernames\":\""+unames+"\",\"flowids\":\""+flowidss+"\",\"usercount\":\""+usercount+"\",\"loginnames\":\""+loginnamess+"\"}");
%>