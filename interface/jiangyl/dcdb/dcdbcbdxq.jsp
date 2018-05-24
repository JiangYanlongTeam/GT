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
String requestid = Util.null2String(request.getParameter("requestid"));
String swlx = "8280";
String GWSWBL_TABLENAME = BaseBean.getPropValue("GWSWBL", "DCDBFORMTABLE");
String CBLCTABLENAME = BaseBean.getPropValue("DCDB", "BMBL_TABELNAME");
String sql = "select a.requestid,b.requestname,a.field3 wh,a.field1 swrq,a.field2 lwdw,a.zbbm,a.nbyj,a.field5,a.blsx,a.field7 dqr,a.cbr  from " + GWSWBL_TABLENAME
		+ " a , workflow_requestbase b " + "where a.requestid = b.requestid and a.requestid = '"+requestid+"'";
RecordSet.execute(sql);
String wh = "";
String swrq = "";
String lwdw = "";
String gwmc = "";
String zbbm = "";
String nbyj = "";
String field5 = "";
String field5_name = "";
String blsx = "";
String zbbmwb = "";
String dqr = "";
String cbr = "";
while(RecordSet.next()){
	wh = Util.null2String(RecordSet.getString("wh"));
	swrq = Util.null2String(RecordSet.getString("swrq"));
	lwdw = Util.null2String(RecordSet.getString("lwdw"));
	zbbm = Util.null2String(RecordSet.getString("zbbm"));
	nbyj = Util.null2String(RecordSet.getString("nbyj"));
	field5 = Util.null2String(RecordSet.getString("field5"));
	blsx = Util.null2String(RecordSet.getString("blsx"));
	dqr = Util.null2String(RecordSet.getString("dqr"));
	cbr = Util.null2String(RecordSet.getString("cbr"));
	gwmc = Util.null2String(RecordSet.getString("requestname"));
}
zbbmwb = new weaver.hrm.company.DepartmentComInfo().getDepartmentNames(zbbm);
String cbrname = new weaver.hrm.resource.ResourceComInfo().getLastnames(cbr);
String swlxsql = "select selectname,SELECTVALUE from WORKFLOW_SELECTITEM where fieldid = '"+swlx+"' and selectvalue = '"+field5+"'";
RecordSet.execute(swlxsql);
while(RecordSet.next()) {
	field5_name = Util.null2String(RecordSet.getString("selectname"));
}
String sqlcount = "select count(*) count from "+CBLCTABLENAME+" where dblc = '"+requestid+"'";
RecordSet.execute(sqlcount);
RecordSet.next();
String cbcs = Util.null2String(RecordSet.getString("count"));
%>
<body scroll="no">
<jsp:include page="/systeminfo/commonTabHead.jsp">
   <jsp:param name="mouldID" value="blog"/>
   <jsp:param name="navName" value="督查督办催办单详情" />
</jsp:include>
<wea:layout type="fourCol">
     <wea:group context="">
      <wea:item>收文日期</wea:item>
      <wea:item>
        <input type="hidden" name="swrq" id="swrq" value="<%=swrq%>"/>
		<%=swrq%>
		<input type="hidden" id="jdbw_old" name="jdbw_old" value="<%=zbbm%>"/>
      </wea:item>
      <wea:item>来文单位</wea:item>
      <wea:item>
        <input type="hidden" name="lwdw" id="lwdw" value="<%=lwdw%>"/>
		<%=lwdw%>
      </wea:item>
      <wea:item>文号</wea:item>
      <wea:item>
        <input type="hidden" name="wh" id="wh" value="<%=wh%>"/>
		<%=wh%>
      </wea:item>
      <wea:item>文件</wea:item>
      <wea:item>
        <input type="hidden" name="bt" id="bt" value="<%=requestid%>"/>
		<%=gwmc%>
      </wea:item>
      <wea:item>局办拟办意见</wea:item>
      <wea:item attributes="{\"colspan\":\"3\"}">
        <%=nbyj%>
      </wea:item>
      <wea:item>收文类型</wea:item>
      <wea:item>
        <%=field5_name%>
      </wea:item>
      <wea:item>交办单位</wea:item>
      <wea:item>
		<%=zbbmwb%>
      </wea:item>
      <wea:item>办理期限</wea:item>
      <wea:item>
        <%=blsx%>工作日
      </wea:item>
      <wea:item>到期日</wea:item>
      <wea:item>
		<%=dqr%>
      </wea:item>
      <wea:item>承办人</wea:item>
      <wea:item>
		<%=cbrname%>
      </wea:item>
      <wea:item>催办次数</wea:item>
      <wea:item>
		<%=cbcs%>
      </wea:item>
     </wea:group>
</wea:layout>
<!-- 添加弹出窗口底部的操作按钮，样式和ID必须固定 -->
<div id="zDialog_div_bottom" class="zDialog_div_bottom">
    <table width="100%">
        <tr><td style="text-align:center;">
            <input type="button" value="确定" id="zd_btn_submit" class="zd_btn_submit" onclick="javascript:doSave(this)">
            <span class="e8_sep_line">|</span>
            <input type="button" value="<%=SystemEnv.getHtmlLabelName(309,user.getLanguage())%>" id="zd_btn_cancle"  class="zd_btn_cancle" onclick="clostWin();">
        </td></tr>
    </table>
</div>

<!-- 定义和显示右上角的菜单，id和样式固定不能随便修改。td里面的内容为显示的操作按钮 -->
<table id="topTitle" cellpadding="0" cellspacing="0">
    <tr>
        <td class="rightSearchSpan" style="text-align:right;" >
            <input type="button" value="确定" class="e8_btn_top" onclick="javascript:doSave(this)">
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
	pdialog.close();
}
</script>
 </body>
</html>