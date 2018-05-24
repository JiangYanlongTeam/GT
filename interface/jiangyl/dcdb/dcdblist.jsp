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
		*{
			font-size:16px !important;
		}
	</style>
	<title>发起督查督办</title>
</head>
<%

if(!HrmUserVarify.checkUserRight("DCDBY:View", user)){
    	response.sendRedirect("/notice/noright.jsp");
    	return;
}
String gwmc = Util.null2String(request.getParameter("gwmc"));
String wh = Util.null2String(request.getParameter("wh"));
String shijian = Util.null2String(request.getParameter("shijian"));
String shijianz = Util.null2String(request.getParameter("shijianz"));


String imagefilename = "/images/hdMaintenance_wev8.gif";
String titlename = "督查督办查询";
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
									<td colSpan=4 style="text-align:center;font-size:18px;">发起督查督办</td>
						</tr>
			<TR class=Spacing><TD class=Line colSpan=4></TD></TR>
			<tr>
							<td>公文名称</td>
							<td class=Field colspan=2>
											<input type="text" id="gwmc" name="gwmc" value="<%=gwmc%>">
							</td>
				</tr>
				<TR class=Spacing><TD class=Line colSpan=4></TD></TR>
				</tbody>
				<tr>
							<td>文号</td>
							<td class=Field colspan=2>
											<input type="text" id="wh" name="wh" value="<%=wh%>">
							</td>
				</tr>
				<TR class=Spacing><TD class=Line colSpan=4></TD></TR>
				<tr>
							<td>收文日期</td>
							<td class=Field colspan=2>
											<button class="calendar" type="button" onclick="getDate(shijianspan,shijian)"></button>
											<span id="shijianspan"><%=shijian%></span>
											<input type="hidden" name="shijian" id="shijian" value="<%=shijian%>">
															－&nbsp;&nbsp;
											<button class="calendar" type="button" onclick="getDate(shijianzspan,shijianz)"></button>
											<span id="shijianzspan"><%=shijianz%></span>
											<input type="hidden" name="shijianz" id="shijianz" value="<%=shijianz%>">
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
				tableString1 =   " <table instanceid=\"ds_list\" tabletype=\"none\" datasource=\"weaver.interfaces.action.DCDB.getDTData\" sourceparams=\"gwmc:"+gwmc+"+wh:"+wh+"+swrqq:"+shijian+"+swrqz:"+shijianz+"\" pagesize=\"15\" >"+
								" <checkboxpopedom  id=\"checkbox\" popedompara=\"column:id\" />"+//用于控制checkbox 框是否可用
								"       <sql backfields=\"*\" sqlform=\"tmptable\" sqlwhere=\"\"  sqlorderby=\"swrq\"  sqlprimarykey=\"requestid\" sqlsortway=\"desc\" sqlisdistinct=\"false\" />"+
								"       <head>"+
								"           <col width=\"30%\"  text=\"公文名称\" column=\"gwmc\"/>"+
								"           <col width=\"15%\"  text=\"文号\" column=\"wh\" />"+
								"           <col width=\"15%\"  text=\"收文日期\" column=\"swrq\"/>"+
								"           <col width=\"15%\"  text=\"来文单位\" column=\"lwdw\" />"+
								"           <col width=\"15%\"  text=\"主办处室\" column=\"zbbmwb\" />"+
								"           <col width=\"10%\"  text=\"发起督办\" column=\"fqdb\" />"+
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
		</script>
	</body>
</html>