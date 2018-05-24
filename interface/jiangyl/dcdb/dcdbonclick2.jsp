<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="weaver.hrm.*" %>
<%
User user = HrmUserVarify.getUser (request , response) ;
String userid = user.getUID() + "";
String iid = new weaver.general.Util().null2String(request.getParameter("iid"));
if(iid.equals("")) {
	return;
}
weaver.conn.RecordSet rs = new weaver.conn.RecordSet();
rs.execute("select * from uf_ldpsck where iid = '"+iid+"' and hrmid = '"+userid+"'");
if(rs.next()){
	return;
}else {
	rs.execute("insert into uf_ldpsck (iid,hrmid) values('"+iid+"','"+userid+"')");
	return;
}

%>