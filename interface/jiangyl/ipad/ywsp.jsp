<%@ page language="java" contentType="text/html; charset=UTF-8" %> 
<%@ page import="weaver.general.Util" %>
<%@ page import="java.util.*" %>
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="rs1" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="rs2" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="mysmt" class="weaver.conn.RecordSet"/>
<jsp:useBean id="bb" class="weaver.general.BaseBean"></jsp:useBean>
<jsp:useBean id="ResourceComInfo" class="weaver.hrm.resource.ResourceComInfo" scope="page" />
<jsp:useBean id="DepartmentComInfo" class="weaver.hrm.company.DepartmentComInfo" scope="page"/>
<jsp:useBean id="SubCompanyComInfo" class="weaver.hrm.company.SubCompanyComInfo" scope="page" />
<%
int nodeid = Util.getIntValue(request.getParameter("nodeid"),0);//流程的节点id
int nodetype = Util.getIntValue(request.getParameter("nodetype"),0);//流程的节点id
int workflowid = Util.getIntValue(request.getParameter("workflowid"),0);//流程的节点id
int _nodeid = Util.getIntValue(request.getParameter("_nodeid"),0);//流程的节点id

rs.execute("select formid from workflow_base where id = '"+workflowid+"'");
rs.next();
String formid_convert = rs.getString("formid");
weaver.interfaces.swfa.BillFieldUtil fieldUtil = new weaver.interfaces.swfa.BillFieldUtil();
String xzryid = fieldUtil.getlabelId("xzry",Integer.parseInt(formid_convert),"0","0");//选择人员
String xzrybrowserid = fieldUtil.getlabelId("xzrybrowser",Integer.parseInt(formid_convert),"0","0");//选择人员
String flowidsid = fieldUtil.getlabelId("flowids",Integer.parseInt(formid_convert),"0","0");//选择人员
String iidid = fieldUtil.getlabelId("iid",Integer.parseInt(formid_convert),"0","0");//iid
String wiidid = fieldUtil.getlabelId("wiid",Integer.parseInt(formid_convert),"0","0");//wiid


%>	
<script language="javascript">
function setValue(obj){
	jQuery("#field<%=xzryid%>").val(jQuery(obj).val());
	jQuery("#field<%=xzrybrowserid%>").val(jQuery(obj).attr("dval"));
}
function setValue2(obj){
}
//页面加载绑定事件
jQuery(document).ready(function(){
	if("<%=nodeid%>" == "<%=_nodeid%>") {
		var _loginnames = "";
		var _flowidss = "";
		var iid = jQuery("#field<%=iidid%>").val();
		var wiid = jQuery("#field<%=wiidid%>").val();
		jQuery.ajax({
		    url:'/interface/jiangyl/ipad/ywsppage.jsp',
		    data : {
		        iid : iid,
				wiid: wiid
		    },     
		    type:'post',
		    async: false,
		    dataType:'json',
		    cache:'false',
		    success:function(data) {
				var str = "";
				var userids = data.userids;
				var usernames = data.usernames;
				var loginnames = data.loginnames;
				var flowidss = data.flowids;
				var usercount = data.usercount;
				var userarr = userids.split(";");
				var usernamearr = usernames.split(";");
				var loginnamess = loginnames.split(";");
				_loginnames = loginnames;
				_flowidss = flowidss;
				for(var i = 0; i < userarr.length; i++) {
					if(userarr[i] != "") {
						if(usercount && parseInt(usercount) == 2) {
							str += "<input type=\"radio\" name=\"selecthrm\" value=\""+userarr[i]+"\" dval=\""+loginnamess[i]+"\" onclick=\"setValue(this)\">&nbsp;&nbsp;<span>"+usernamearr[i]+"</span>&nbsp;&nbsp;";
						} else {
							str += "<input type=\"checkbox\" name=\"selecthrmcheckbox\" value=\""+userarr[i]+"\" dval=\""+loginnamess[i]+"\" onclick=\"setValue2(this)\">&nbsp;&nbsp;<span>"+usernamearr[i]+"</span>&nbsp;&nbsp;";
						}
					}
				}
				if(str != "") {
					jQuery("#selecthrmspan").html(str);
				}
				if(flowidss != "") {
					jQuery("#flowname").html("<span>选择人员&nbsp;&nbsp;(流向："+flowidss+")</span>");
					jQuery("#field<%=flowidsid%>").val(flowidss);
				}
		    }
		});	

		var __systemHandleFunction = dosubmit;
		dosubmit = function (btnobj){
			if(_flowidss != "" && _loginnames == "") {
			} else {
				var xzry = jQuery("#field<%=xzryid%>").val();
				if(xzry == "") {
					alert("需要选择人员才能提交");
					return;
				}
			}
		
			__systemHandleFunction(btnobj);
		}
	}
	
});
</script>
