<%@ page language="java" contentType="text/html; charset=UTF-8" %> <%@ include file="/systeminfo/init_wev8.jsp" %>
<%@ page import="weaver.general.Util" %>
<%@ page import="weaver.conn.*,java.lang.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/weaver.tld" prefix="wea"%>
<%@ taglib uri="/WEB-INF/tld/browser.tld" prefix="brow"%>
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="rs1" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="SubCompanyComInfo" class="weaver.hrm.company.SubCompanyComInfo" scope="page"/>
<jsp:useBean id="DepartmentComInfo" class="weaver.hrm.company.DepartmentComInfo" scope="page"/>
<jsp:useBean id="ResourceComInfo" class="weaver.hrm.resource.ResourceComInfo" scope="page"/>
<HTML><HEAD>
<LINK href="/css/Weaver_wev8.css" type=text/css rel=STYLESHEET>
<SCRIPT language="javascript" src="/js/weaver_wev8.js"></script>
<script type="text/javascript" src="/js/ecology8/request/wfSearchResult_wev8.js"></script>
<SCRIPT language="javascript" defer="defer" src="/js/datetime_wev8.js"></script>
<SCRIPT language="javascript" defer="defer" src="/js/JSDateTime/WdatePicker_wev8.js"></script>
<SCRIPT language="javascript" defer="defer" src="/js/JSDateTime/calendar_wev8.js"></script>
	<style>
		*{
			font-size:16px !important;
		}
	</style>
	<%
	boolean isadmin = true;
	if(!HrmUserVarify.checkUserRight("DCDBCBY:View", user)){
	    	isadmin = false;
	}
	%>
	<title>
		<%
		if(isadmin) {
			%>
				督查督办超期事项
			<%
		} else {
			%>
				督查督办催办事项
			<%
		}
		%>
		</title>
</head>
<%

int usid = user.getUID();
String imagefilename = "/images/hdMaintenance_wev8.gif";
String titlename = "";
String needfav ="1";
String needhelp ="";
String userlang = ""+user.getLanguage();
String para4=userlang+"+"+user.getUID()+"+"+user.getUID();
//String sql="from workflow_signbase";
String orderBy = "id";
//primarykey
String primarykey = "id";
//pagesize
String backfields = " a.requestid, b.requestname, a.field1 swrq,a.zbbm,a.cbr,a.field7 dqr ";
String sqlform=" formtable_main_39 a, workflow_requestbase b ";
String sqlwhere = " a.requestid = b.requestid and a.sfcq = '1'";

if(!isadmin) {
	String userDepartid = new weaver.hrm.resource.ResourceComInfo().getDepartmentID(Util.null2String(usid));
	String _sql = "select id from hrmresource where departmentid = '"+userDepartid+"'";
	rs.execute(_sql);
	java.lang.StringBuffer _sb = new java.lang.StringBuffer(",");
	while(rs.next()){
		String _id = Util.null2String(rs.getString("id"));
		_sb.append(_id+",");
	}
	String _SS = _sb.toString();
	_SS = _SS.substring(1,_SS.length()-1);
	sqlwhere+=" and b.creater in ("+_SS+") ";
}
String sqlgroupby="group by id";

//out.print(sqlwhere);

String bm  = Util.null2String(request.getParameter("bm"));
String departmentnames = DepartmentComInfo.getDepartmentNames(bm);
if(!"".equals(bm)) {
	sqlwhere += " and a.zbbm like '%"+bm+"%' ";
}

int pagesize=20;


String colString="";
       

	   colString +="<col width=\"10%\" text=\"业务编号\" column=\"requestid\"/>"+	
     "<col width=\"18%\" text=\"公文名称\" column=\"requestname\" href=\"/workflow/request/ViewRequest.jsp\" linkkey=\"requestid\" linkvaluecolumn=\"requestid\" />"+
     "<col width=\"18%\" text=\"收文日期\" column=\"swrq\"/>"+
	 "<col width=\"14%\" text=\"主办处室局\" column=\"zbbm\" transmethod=\"weaver.hrm.company.DepartmentComInfo.getDepartmentNames\"/>"+
	  "<col width=\"14%\" text=\"承办人\" column=\"cbr\" transmethod=\"weaver.hrm.resource.ResourceComInfo.getLastnames\"/>"+
	  "<col width=\"14%\" text=\"到期日\" column=\"dqr\" />"+
	 "<col width=\"13%\" text=\"催办次数\" column=\"requestid\" transmethod=\"weaver.interfaces.action.DCDB.getCBCS\"/>"+
	 "<col width=\"13%\" text=\"催办通知单\" column=\"requestid\" transmethod=\"weaver.interfaces.action.DCDB.cbtzd\"/>";
	   if(isadmin) {
	   	colString += "<col width=\"13%\" text=\"微信提醒\" column=\"requestid\" transmethod=\"weaver.interfaces.action.DCDB.wxtx\"/>";
	   }
	 
	 


String tableString="<table  pagesize=\""+pagesize+"\" tabletype=\"none\">";
       tableString+="<sql backfields=\""+backfields+"\" sqlform=\""+sqlform+"\" sqlwhere=\""+Util.toHtmlForSplitPage(sqlwhere)+"\" sqlorderby=\"\" sqlsortway=\"DESC\"  sqlprimarykey=\"a.requestid\"  sqldistinct=\"true\" />";
       tableString+="<head>"+colString+"</head>";
       tableString+="</table>";


%>
<BODY>
<%@ include file="/systeminfo/TopTitle_wev8.jsp" %>
<%@ include file="/systeminfo/RightClickMenuConent_wev8.jsp" %>
<%
RCMenu += "{查询,javascript:submitData(),_self} " ;
RCMenuHeight += RCMenuHeightStep ;
%>
<%@ include file="/systeminfo/RightClickMenu_wev8.jsp" %>

<table width=100% height=100% border="0" cellspacing="0" cellpadding="0">
<colgroup>
<col width="10">
<col width="">
<col width="10">
<tr>
<td ></td>
<td valign="top">
<TABLE class=Shadow>
<tr>
<td valign="top">
<form name="frmSearch" method="post" action="">
	<table class=ViewForm style="margin-left: 0.4%;">
		<colgroup>
		<col width="20%">
		<col width="60%">
		<col width="20%">
		<tbody>
			<tr>
							<td colSpan=4 style="text-align:center;font-size:18px;">
								<%
								if(isadmin) {
									%>
										督查督办超期事项
									<%
								} else {
									%>
										督查督办催办事项
									<%
								}
								%>
							</td>
				</tr>
	<TR class=Spacing><TD class=Line colSpan=4></TD></TR>
	<tr>
					<td>主办处室</td>
					<td class=Field colspan=2>
									<brow:browser name="bm" id="bm" viewType="0" hasBrowser="true" hasAdd="false"		                          browserUrl="/systeminfo/BrowserMain.jsp?mouldID=hrm&url=/hrm/company/MutiDepartmentBrowser.jsp?selectedids="
									                            isMustInput="1" isSingle="true" hasInput="true"
									                            completeUrl="/data.jsp?type=4"
									                            width="300px" browserValue="<%=bm%>" browserSpanValue="<%=departmentnames%>" />
					</td>
		</tr>
		<TR class=Spacing><TD class=Line colSpan=4></TD></TR>
													</table>
													<TABLE class=ViewForm valign="top">
												                     <TR>
												                         <TD valign="top" colspan=4>
												                             <wea:SplitPageTag tableString="<%=tableString%>"/>
												                          </TD>
												                     </TR>
													</table>
</form>
</td>
</tr>
</TABLE>
</td>
<td></td>
</tr>
<tr>
<td height="10" colspan="3"></td>
</tr>
</table>

<div id='divshowreceivied' style='background: #FFFFFF; padding: 3px; width: 100%' valign='top'></div>
<SCRIPT language=javascript>
function submitData() {

 frmSearch.submit();

}
document.getElementById('dbutton').onclick = function(){
	frmSearch.submit();
}
document.getElementById('word').onclick = function(){
	Dialog.alert("建设中...");
}
</SCRIPT>

<SCRIPT language="javascript">
		
var showTableDiv  = $GetEle('divshowreceivied');
var oIframe = document.createElement('iframe');
function showreceiviedPopup(content){
	showTableDiv.style.display='';
	var message_Div = document.createElement("div");
	 message_Div.id="message_Div";
	 message_Div.className="xTable_message";
	 showTableDiv.appendChild(message_Div);
	 var message_Div1  = $GetEle("message_Div");
	 message_Div1.style.display="inline";
	 message_Div1.innerHTML=content;
	 var pTop= document.body.offsetHeight/2+document.body.scrollTop-50;
	 var pLeft= document.body.offsetWidth/2-50;
	 message_Div1.style.position="absolute"
	 message_Div1.style.top=pTop;
	 message_Div1.style.left=pLeft;

	 message_Div1.style.zIndex=1002;

	 oIframe.id = 'HelpFrame';
	 showTableDiv.appendChild(oIframe);
	 oIframe.frameborder = 0;
	 oIframe.style.position = 'absolute';
	 oIframe.style.top = pTop;
	 oIframe.style.left = pLeft;
	 oIframe.style.zIndex = message_Div1.style.zIndex - 1;
	 oIframe.style.width = parseInt(message_Div1.offsetWidth);
	 oIframe.style.height = parseInt(message_Div1.offsetHeight);
	 oIframe.style.display = 'block';
}


function ajaxinit(){
	var ajax=false;
	try {
		ajax = new ActiveXObject("Msxml2.XMLHTTP");
	} catch (e) {
		try {
			ajax = new ActiveXObject("Microsoft.XMLHTTP");
		} catch (E) {
			ajax = false;
		}
	}
	if (!ajax && typeof XMLHttpRequest!='undefined') {
	ajax = new XMLHttpRequest();
	}
	return ajax;
}
function showallreceived(requestid,returntdid){
	showreceiviedPopup("<%=SystemEnv.getHtmlLabelName(19205, user.getLanguage())%>");
	var ajax=ajaxinit();
	ajax.open("POST", "/workflow/search/WorkflowUnoperatorPersons.jsp", true);
	ajax.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	ajax.send("requestid="+requestid+"&returntdid="+returntdid);
	//获取执行状态

	//alert(ajax.readyState);
	//alert(ajax.status);
	ajax.onreadystatechange = function() {
		//如果执行状态成功，那么就把返回信息写到指定的层里

		if (ajax.readyState==4&&ajax.status == 200) {
			try{
				 $GetEle(returntdid).innerHTML = ajax.responseText;
				 $GetEle(returntdid).parentElement.title = ajax.responseText.replace(/[\r\n]/gm, "");
			}catch(e){}
				showTableDiv.style.display='none';
				oIframe.style.display='none';
		} 
	} 
}
//收发文单位
function onShowMReceiveUnit(spanname,inputename){
	var tmpids = $G(inputename).value;
	var id1 = window.showModalDialog("/systeminfo/BrowserMain.jsp?url=/docs/sendDoc/DocReceiveUnitBrowserMulti.jsp?receiveUnitIds="+tmpids);
	if(id1){
		if(id1.id!= ""){
		 resourceids =id1.id;
		 resourcename =id1.name;
		 recievenumber1 =id1.name;

		 retresourceids = "";
		 retresourcename = "";
		 retrecievenumber1 = ""

		 sHtml = "";

		 resourceids =resourceids.substr(1);
		 resourcename =resourcename.substr(1);
		 recievenumber1 =recievenumber1.substr(1);

		 var ids=resourceids.split(",");
		 var names=resourcename.split(",");

		 $G(spanname).innerHTML = resourcename;
		 //$G("recievenumber1").value=trimComma(retrecievenumber1);
		 $G(inputename).value= resourceids;

				 //alert(resourceids);

		}else{
			 $G(spanname).innerHTML ="";
			 $G(inputename).value="";
			 //$G("recievenumber1").value="";
		}
	 }
}
//单位
function onShowCompany(spanname,inputename){
	var tmpids = $G(inputename).value;
	var id1 = window.showModalDialog("/systeminfo/BrowserMain.jsp?mouldID=hrm&url=/hrm/company/MutiDepartmentBrowser.jsp?selectedids="+tmpids);
	if(id1){
		if(id1.id!= ""){
		 resourceids =id1.id;
		 resourcename =id1.name;
		 recievenumber1 =id1.name;

		 retresourceids = "";
		 retresourcename = "";
		 retrecievenumber1 = ""

		 //sHtml = "";

		 //resourceids =resourceids.substr(1);
		 //resourcename =resourcename.substr(1);
		// recievenumber1 =recievenumber1.substr(1);

		 //var ids=resourceids.split(",");
		 //var names=resourcename.split(",");

		 $G(spanname).innerHTML = resourcename;
		 //$G("recievenumber1").value=trimComma(retrecievenumber1);
		 $G(inputename).value= resourceids;

		}else{
			 $G(spanname).innerHTML ="";
			 $G(inputename).value="";
			 //$G("recievenumber1").value="";
		}
	 }
}
//单位
function onShowHrm(spanname,inputename){
	var tmpids = $G(inputename).value;
	var id1 = window.showModalDialog("/systeminfo/BrowserMain.jsp?url=/hrm/resource/ResourceBrowser.jsp?id="+tmpids);
	if(id1){
		if(id1.id!= ""){
		 resourceids =id1.id;
		 resourcename =id1.name;
		 recievenumber1 =id1.name;

		 retresourceids = "";
		 retresourcename = "";
		 retrecievenumber1 = ""

		 //sHtml = "";

		 //resourceids =resourceids.substr(1);
		 //resourcename =resourcename.substr(1);
		// recievenumber1 =recievenumber1.substr(1);

		 //var ids=resourceids.split(",");
		 //var names=resourcename.split(",");

		 $G(spanname).innerHTML = resourcename;
		 //$G("recievenumber1").value=trimComma(retrecievenumber1);
		 $G(inputename).value= resourceids;

				 //alert(resourceids);

		}else{
			 $G(spanname).innerHTML ="";
			 $G(inputename).value="";
			 //$G("recievenumber1").value="";
		}
	 }
}
//获取勾选的ID
function doCheck(){
	var selectedIds = _xtable_CheckedCheckboxId();
	if(selectedIds == ""){
		alert("请选择需要操作的数据!");
		return "请选择需要操作的数据!";
	}else{
		selectedIds = selectedIds.substring(0,selectedIds.length-1);
	}
	return selectedIds;
}
function del(obj) {
	if(obj) {
		if(confirm("确定删除流程？")) {
			//触发督查督办流程
			jQuery.ajax({
				url : '/interface/jiangyl/dcdb/deldcdb.jsp',
				data : {
					reqid : obj
				},
				type:'post',
		    	async: false,
		    	dataType:'json',
		    	cache:'false',
				success: function (data) {
					var dataJSON = jQuery.parseJSON(data);
				    alert(data.success);//假设数据保存成功，刷新父窗口，关闭弹窗
				    location.reload();
				}
			})
		}
	}
}

function cbtzd(obj) {
	var dlg = new window.top.Dialog(); //定义Dialog对象
    dlg.currentWindow = window;
    dlg.Model = false;　　　
    dlg.Width = 1060; //定义长度
    dlg.Height = 500;　　　
    dlg.URL = "/interface/jiangyl/dcdb/dcdbcbdxq.jsp?requestid="+obj;　　　
    dlg.Title = "督查督办催办单详情" ;
    dlg.maxiumnable = true;　　　
    dlg.show();
    window.dialog = dlg;
}
function wxtx(obj) {
	var dlg = new window.top.Dialog(); //定义Dialog对象
    dlg.currentWindow = window;
    dlg.Model = false;　　　
    dlg.Width = 1060; //定义长度
    dlg.Height = 500;　　　
    dlg.URL = "/interface/jiangyl/dcdb/dcdbwxtx.jsp?requestid="+obj;　　　
    dlg.Title = "督查督办催办" ;
    dlg.maxiumnable = true;　　　
    dlg.show();
    window.dialog = dlg;
}
</script>
 
</BODY></HTML>