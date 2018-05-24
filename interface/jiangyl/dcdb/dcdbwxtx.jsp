<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/weaver.tld" prefix="wea"%>
<%@ page import="weaver.general.Util"%>
<%@ page import="java.util.*"%>
<%@ page import="java.lang.*"%>
<jsp:useBean id="RecordSet" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="BaseBean" class="weaver.general.BaseBean" scope="page" />
<%@ taglib uri="/WEB-INF/tld/browser.tld" prefix="brow"%>
<jsp:useBean id="SubCompanyComInfo" class="weaver.hrm.company.SubCompanyComInfo" scope="page" />
<%@ include file="/systeminfo/init_wev8.jsp" %>
<HTML><HEAD>
<LINK href="/css/Weaver_wev8.css" type=text/css rel=STYLESHEET>
<script type='text/javascript' src='/js/jquery-autocomplete/lib/jquery.bgiframe.min_wev8.js'></script>
<script type='text/javascript' src='/js/jquery-autocomplete/jquery.autocomplete_wev8.js'></script>
<script type='text/javascript' src='/js/jquery-autocomplete/browser_wev8.js'></script>
<link rel="stylesheet" type="text/css" href="/js/jquery-autocomplete/jquery.autocomplete_wev8.css" />
<link rel="stylesheet" type="text/css" href="/js/jquery-autocomplete/browser_wev8.css" />
<SCRIPT language="javascript" defer="defer" src="/js/workflow/VCEventHandle_wev8.js"></script>
<SCRIPT language="javascript" defer="defer" src="/js/datetime_wev8.js"></script>
<SCRIPT language="javascript" defer="defer" src="/js/JSDateTime/WdatePicker_wev8.js"></script>
<SCRIPT language="javascript" defer="defer" src="/js/JSDateTime/calendar_wev8.js"></script>
	<style>
		*{
			font-size:16px !important;
		}
	</style>
</HEAD>
<%
int userid = user.getUID();
String requestid = Util.null2String(request.getParameter("requestid"));
String sjr = Util.null2String(request.getParameter("sjr"));
String sjrs = new weaver.hrm.resource.ResourceComInfo().getLastnames(sjr);
String swlxsql = "select requestname from workflow_requestbase where requestid = '"+requestid+"'";
RecordSet.execute(swlxsql);
String requestname = "";
while(RecordSet.next()) {
	requestname = Util.null2String(RecordSet.getString("requestname"));
}
%>
<body scroll="no">
<jsp:include page="/systeminfo/commonTabHead.jsp">
   <jsp:param name="mouldID" value="blog"/>
   <jsp:param name="navName" value="督查督办催办" />
</jsp:include>
<wea:layout type="2Col">
     <wea:group context="">
      <wea:item>收件人</wea:item>
      <wea:item>
		<brow:browser name="sjr" id="sjr" viewType="0" hasBrowser="true" hasAdd="false"		                          browserUrl="/systeminfo/BrowserMain.jsp?url=/hrm/resource/MutiResourceBrowser.jsp"
		                            isMustInput="1" isSingle="false" hasInput="true"
		                            completeUrl="/data.jsp?type=1"
		                            width="300px" browserValue="<%=sjr%>" browserSpanValue="<%=sjrs%>" />
      </wea:item>
      <wea:item>短信内容</wea:item>
      <wea:item>
        <textarea id="dxnr" name="dxnr">您的公文<%=requestname%>已超期，请尽快办理。</textarea>
      </wea:item>
     </wea:group>
</wea:layout>
<!-- 添加弹出窗口底部的操作按钮，样式和ID必须固定 -->
<div id="zDialog_div_bottom" class="zDialog_div_bottom">
    <table width="100%">
        <tr><td style="text-align:center;">
            <input type="button" value="发送" id="zd_btn_submit" class="zd_btn_submit" onclick="javascript:doSave(this)">
            <span class="e8_sep_line">|</span>
            <input type="button" value="<%=SystemEnv.getHtmlLabelName(309,user.getLanguage())%>" id="zd_btn_cancle"  class="zd_btn_cancle" onclick="clostWin();">
        </td></tr>
    </table>
</div>

<!-- 定义和显示右上角的菜单，id和样式固定不能随便修改。td里面的内容为显示的操作按钮 -->
<table id="topTitle" cellpadding="0" cellspacing="0">
    <tr>
        <td class="rightSearchSpan" style="text-align:right;" >
            <input type="button" value="发送" class="e8_btn_top" onclick="javascript:doSave(this)">
            <span title="<%=SystemEnv.getHtmlLabelName(83721,user.getLanguage())%>" class="cornerMenu" id="rightclickcornerMenu">
            </span>
        </td>
    </tr>
</table>

<script type="text/javascript">
var pdialog = parent.getDialog(window);//获取窗口对象；
var parentWin = parent.getParentWindow(window);//获取父对象；
function clostWin(){
    pdialog.close();
}
function doSave(){
	var sjr = jQuery("#sjr").val();
	var dxnr = jQuery("#dxnr").val();
	var flag = false;
	//触发督查督办流程
	jQuery.ajax({
		url : '/createdcdbcqtx',
		data : {
			sjr : sjr,
			dxnr : dxnr,
			userid : '<%=userid%>',
			requestid: '<%=requestid%>'
		},
		type:'post',
    	async: false,
    	dataType:'json',
    	cache:'false',
		success: function (data) {
		    alert(data.message);//假设数据保存成功，刷新父窗口，关闭弹窗
		    parentWin.location.reload();
		    pdialog.close();
		}
	})
}
</script>
 </body>
</html>