<%@ page language="java" contentType="text/html; charset=UTF-8" %> <%@ include file="/systeminfo/init_wev8.jsp" %>
<%@ page import="weaver.general.Util" %>
<%@ page import="weaver.conn.*,java.lang.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/weaver.tld" prefix="wea"%>
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="rs1" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="SubCompanyComInfo" class="weaver.hrm.company.SubCompanyComInfo" scope="page"/>
<jsp:useBean id="DepartmentComInfo" class="weaver.hrm.company.DepartmentComInfo" scope="page"/>
<jsp:useBean id="ResourceComInfo" class="weaver.hrm.resource.ResourceComInfo" scope="page"/>
<HTML><HEAD>
<LINK href="/css/Weaver_wev8.css" type=text/css rel=STYLESHEET>
<SCRIPT language="javascript" src="../../js/weaver_wev8.js"></script>
<script type="text/javascript" src="/js/ecology8/request/wfSearchResult_wev8.js"></script>
<SCRIPT language="javascript" defer="defer" src="/js/datetime_wev8.js"></script>
<SCRIPT language="javascript" defer="defer" src="/js/JSDateTime/WdatePicker_wev8.js"></script>
<SCRIPT language="javascript" defer="defer" src="/js/JSDateTime/calendar_wev8.js"></script>
	<style>
/*		*{
			font-size:16px !important;
		}*/
	</style>
	<title>车辆调度查询</title>
</head>
<%
String type = Util.null2String(request.getParameter("type"));
String requestname = Util.null2String(request.getParameter("requestname"));
String reqid = Util.null2String(request.getParameter("reqid"));


String imagefilename = "/images/hdMaintenance_wev8.gif";
String titlename = "领导批示意见查询";
String needfav ="1";
String needhelp ="";
String userlang = ""+user.getLanguage();
String para4=userlang+"+"+user.getUID()+"+"+user.getUID();

%>
	<body style="margin:0 auto">
		
		<%@ include file="/systeminfo/TopTitle_wev8.jsp" %>
		<%@ include file="/systeminfo/RightClickMenuConent_wev8.jsp" %>
		<%
		RCMenu += "{查询,javascript:submitData(),_self} " ;
		RCMenuHeight += RCMenuHeightStep ;
		RCMenu += "{导出,javascript:exportexcel(),_self} " ;
		RCMenuHeight += RCMenuHeightStep ;

		%>
		<%@ include file="/systeminfo/RightClickMenu_wev8.jsp" %>
		
		<form name="frmSearch" method="post" action="">
			<table class=ViewForm style="margin-left: 0.4%;">
				<colgroup>
				<col width="20%">
				<col width="60%">
				<col width="20%">
				<tr>
							<td>流程标题</td>
							<td class=Field colspan=2>
											<input type="text" id="requestname" name="requestname" value="<%=requestname%>">
							</td>
				</tr>
				<TR class=Spacing><TD class=Line colSpan=4></TD></TR>
				<tr>
							<td>流程编号</td>
							<td class=Field colspan=2>
											<input type="text" id="reqid" name="reqid" value="<%=reqid%>">
							</td>
							<td class=Field>
											<!--<input type="button" id="dbutton" value="查询" class="e8_btn_top_first">-->
							</td>
				</tr>
				<TR class=Spacing><TD class=Line colSpan=4></TD></TR>
															</table>
			<%
				String tableString1 = "";  //定义表格xml数据
				//指定分页条数和初始化id以及是否有复选框 以及数据来源  datasource表示数据来源 sourceparams表示传入参数参数格式为"name:value+name1:value1"多个参数用加号连接
				tableString1 =   " <table instanceid=\"ds_list\" tabletype=\"none\" datasource=\"weaver.interfaces.action.DCDB.getCldd\" sourceparams=\"requestname:"+requestname+"+reqid:"+reqid+"\" pagesize=\"15\" >"+
								" <checkboxpopedom  id=\"checkbox\" popedompara=\"column:id\" />"+//用于控制checkbox 框是否可用
								"       <sql backfields=\"*\" sqlform=\"tmptable\" sqlwhere=\"\"  sqlorderby=\"\"  sqlprimarykey=\"reqid\" sqlsortway=\"\" sqlisdistinct=\"false\" />"+
								"       <head>"+
								"           <col width=\"10%\"  text=\"业务编号\" column=\"reqid\"/>"+
								"           <col width=\"15%\"  text=\"请求标题\" column=\"requestname\"/>"+
								"           <col width=\"5%\"  text=\"用车单位\" column=\"ycdw\" transmethod=\"weaver.hrm.company.DepartmentComInfo.getDepartmentname\"/>"+
								"           <col width=\"10%\"  text=\"申请人\" column=\"sqr\" transmethod=\"weaver.hrm.resource.ResourceComInfo.getLastname\"/>"+
								"           <col width=\"10%\"  text=\"用车时间\" column=\"ycsj\"/>"+
								"           <col width=\"10%\"  text=\"申请车型\" column=\"sqcx\" />"+
								"           <col width=\"10%\"  text=\"驾驶员姓名\" column=\"jsy\" transmethod=\"weaver.hrm.resource.ResourceComInfo.getLastname\" />"+
								"           <col width=\"10%\"  text=\"在办节点\" column=\"zbjd\" />"+
								"           <col width=\"10%\"  text=\"派车日期\" column=\"pcrq\" />"+
								"       </head>"+
								" </table>";
			%>

			<TABLE class=ViewForm valign="top">
		                     <TR>
		                         <TD valign="top" colspan=4>
		                             <wea:SplitPageTag tableString="<%=tableString1%>"/>
		                          </TD>
		                     </TR>
			</table>
		</form>
		
		<script>
			function openDCDB(obj){
				var dlg = new window.top.Dialog(); //定义Dialog对象
			    dlg.currentWindow = window;
			    dlg.Model = false;　　　
			    dlg.Width = 1060; //定义长度
			    dlg.Height = 500;　　　
			    dlg.URL = "/interface/jiangyl/dcdb/dcdbpage.jsp?requestid="+obj;　　　
			    dlg.Title = "督查督办" ;
			    dlg.maxiumnable = true;　　　
			    dlg.show();
			    window.dialog = dlg;
			}
			function viewDone(obj,obj2){
				window.open("/interface/jiangyl/dcdb/viewdcdb.jsp?mainrequestid="+obj+"&wfid="+obj2);
			}
			document.getElementById('dbutton').onclick = function(){
				frmSearch.submit();
			}
			function exportexcel(){
				window.location='/weaver/weaver.common.util.taglib.CreateExcelServer?showOrder=all';
			}
			function submitData() {

			 frmSearch.submit();

			}
			function openFlow(obj) {
						window.open("/proj/RequestView.jsp?requestid="+obj);
			}
		</script>
	</body>
</html>