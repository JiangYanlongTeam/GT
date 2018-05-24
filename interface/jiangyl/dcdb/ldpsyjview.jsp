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
	<title>领导批示意见查询</title>
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
				<tbody>
			<tr>
							<td>流程类型</td>
							<td class=Field colspan=2>
											<input type="text" id="type" name="type" value="<%=type%>">
							</td>
				</tr>
				<TR class=Spacing><TD class=Line colSpan=4></TD></TR>
				</tbody>
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
				tableString1 =   " <table instanceid=\"ds_list\" tabletype=\"none\" datasource=\"weaver.interfaces.action.DCDB.getDTData2\" sourceparams=\"type:"+type+"+requestname:"+requestname+"+reqid:"+reqid+"\" pagesize=\"15\" >"+
								" <checkboxpopedom  id=\"checkbox\" popedompara=\"column:id\" />"+//用于控制checkbox 框是否可用
								"       <sql backfields=\"*\" sqlform=\"tmptable\" sqlwhere=\"\"  sqlorderby=\"\"  sqlprimarykey=\"reqid\" sqlsortway=\"\" sqlisdistinct=\"false\" />"+
								"       <head>"+
								"           <col width=\"10%\"  text=\"流程类型\" column=\"typename\"/>"+
								"           <col width=\"15%\"  text=\"流程标题\" column=\"requestname\" />"+
								"           <col width=\"5%\"  text=\"是否查看\" column=\"sfdj\" />"+
								"           <col width=\"10%\"  text=\"许明意见\" column=\"xmyj\"/>"+
								"           <col width=\"10%\"  text=\"王东意见\" column=\"wdyj\" />"+
								"           <col width=\"10%\"  text=\"丁和庚意见\" column=\"dhgyj\" />"+
								"           <col width=\"10%\"  text=\"冯雪渔意见\" column=\"fxyyj\" />"+
								"           <col width=\"10%\"  text=\"宋雅建意见\" column=\"syjyj\" />"+
								"           <col width=\"10%\"  text=\"潘文辉意见\" column=\"pwhyj\" />"+
								"           <col width=\"10%\"  text=\"乔兵意见\" column=\"qbyj\" />"+
								"           <col width=\"10%\"  text=\"王晓庆意见\" column=\"wxqyj\" />"+
								"           <col width=\"10%\"  text=\"闻金苗意见\" column=\"wjmyj\" />"+
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
				jQuery.ajax({
					url:'/interface/jiangyl/dcdb/dcdbonclick2.jsp',
					data : {
						iid : obj
					},     
					type:'post',
					async: false,
					dataType:'json',
					cache:'false',
					success:function(data) {
						window.open("/proj/RequestView.jsp?requestid="+obj);
					}
				});
			}
		</script>
	</body>
</html>