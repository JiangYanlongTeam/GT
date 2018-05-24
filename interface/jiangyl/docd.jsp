<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="weaver.general.Util"%>
<%@ page import="java.util.*"%>
<%@ page import="java.lang.*"%>
<jsp:useBean id="RecordSet" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="BaseBean" class="weaver.general.BaseBean" scope="page" />
<%
weaver.general.Util util = new weaver.general.Util();
String fj = util.null2String(request.getParameter("docid"));
if("".equals(fj)) {
	out.println("输入docid参数");
	return;
}
weaver.conn.RecordSet rs = new weaver.conn.RecordSet();
weaver.conn.RecordSet rs1 = new weaver.conn.RecordSet();
weaver.share.ShareinnerInfo shareInfo = new weaver.share.ShareinnerInfo();
String sql = "select * from docdetail where SECCATEGORY = '"+fj+"'";
rs.execute(sql);
char flag = util.getSeparator();
while(rs.next()){
	int id = util.getIntValue(rs.getString("id"),0);
	String procPara = "";
	procPara = "" + id;
	procPara += flag + "1";
	procPara += flag + "0";
	procPara += flag + "0";
	procPara += flag + "1";
	procPara += flag + ("" + 1635);
	procPara += flag + "0";
	procPara += flag + "0";
	procPara += flag + "0";
	procPara += flag + "0";
	procPara += flag + ("" + 1635);// CRMID
	procPara += flag + "1";// @sharesource
	rs1.executeProc("DocShare_FromDocSecCategoryI", procPara);
	shareInfo.AddShare(id, 1, 1635, 0, 1, 1, 1635, "shareinnerdoc", 1);
}
out.println("重构完成");
%>