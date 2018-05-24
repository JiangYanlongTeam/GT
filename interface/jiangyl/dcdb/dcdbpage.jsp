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
String nodeid = BaseBean.getPropValue("DCDB", "nodeid");
String swlx = BaseBean.getPropValue("DCDB", "swlx_fieldid");
String GWSWBL_TABLENAME = BaseBean.getPropValue("DCDB", "GWSWBL_TABELNAME");
String sql = "select a.requestid,b.requestname,a.wh,a.swrq,a.lwdw,a.zbbmwb,a.zbbm,a.nbyj from " + GWSWBL_TABLENAME
		+ " a , workflow_requestbase b " + "where a.requestid = b.requestid and a.requestid = '"+requestid+"'";
RecordSet.execute(sql);
String wh = "";
String swrq = "";
String lwdw = "";
String zbbmwb = "";
String gwmc = "";
String zbbm = "";
String nbyj = "";
while(RecordSet.next()){
	wh = Util.null2String(RecordSet.getString("wh"));
	swrq = Util.null2String(RecordSet.getString("swrq"));
	lwdw = Util.null2String(RecordSet.getString("lwdw"));
	zbbm = Util.null2String(RecordSet.getString("zbbm"));
	zbbmwb = Util.null2String(RecordSet.getString("zbbmwb"));
	gwmc = Util.null2String(RecordSet.getString("requestname"));
	nbyj = Util.null2String(RecordSet.getString("nbyj"));
}
String nbyjarea = Util.toHtmltextarea(nbyj);
StringBuffer swlxsb = new StringBuffer("<select id='swlx' name='swlx'><option value></option>");
String swlxsql = "select selectname,SELECTVALUE from WORKFLOW_SELECTITEM where fieldid = '"+swlx+"'";
RecordSet.execute(swlxsql);
while(RecordSet.next()) {
	String selectname = Util.null2String(RecordSet.getString("selectname"));
	String selectvalue = Util.null2String(RecordSet.getString("SELECTVALUE"));
	swlxsb.append("<option value='"+selectvalue+"' > "+selectname+" </option>");
}
swlxsb.append("</select>");
String swlxContent = swlxsb.toString();
%>
<body scroll="no">
<jsp:include page="/systeminfo/commonTabHead.jsp">
   <jsp:param name="mouldID" value="blog"/>
   <jsp:param name="navName" value="发起督查督办" />
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
      <wea:item>标题</wea:item>
      <wea:item>
        <input type="hidden" name="bt" id="bt" value="<%=requestid%>"/>
		<%=gwmc%>
      </wea:item>
      <wea:item>文号</wea:item>
      <wea:item>
        <input type="hidden" name="wh" id="wh" value="<%=wh%>"/>
		<%=wh%>
      </wea:item>
      <wea:item>局办拟办意见</wea:item>
      <wea:item attributes="{\"colspan\":\"3\"}">
	  	<span><%=nbyj%></span>
        <textarea id="jbnbyj" name="jbnbyj" style="display:none"><%=nbyj%></textarea>
      </wea:item>
      <wea:item>收文类型</wea:item>
      <wea:item>
        <%=swlxContent%>
      </wea:item>
      <wea:item>交办单位</wea:item>
      <wea:item>
		<brow:browser name="jbdw" id="jbdw" viewType="0" hasBrowser="true" hasAdd="false"		                          browserUrl="/systeminfo/BrowserMain.jsp?url=/interface/MultiCommonBrowser.jsp?type=browser.bm"
		                            isMustInput="1" isSingle="true" hasInput="true"
		                            completeUrl=""
		                            width="300px" browserValue="<%=zbbm%>" browserSpanValue="<%=zbbmwb%>" />
      </wea:item>
      <wea:item>办理期限</wea:item>
      <wea:item>
        <input type="hidden" name="blqx" id="blqx" value=""/>
		<span id="blqxspan" name="blqxspan"></span>
      </wea:item>
      <wea:item>到期日</wea:item>
      <wea:item>
		<button class="calendar" type="button" onclick="getDate(dqrspan,dqr)"></button>
		<span id="dqrspan"></span>
		<input type="hidden" name="dqr" id="dqr" value="">
      </wea:item>
     </wea:group>
</wea:layout>
<!-- 添加弹出窗口底部的操作按钮，样式和ID必须固定 -->
<div id="zDialog_div_bottom" class="zDialog_div_bottom">
    <table width="100%">
        <tr><td style="text-align:center;">
            <input type="button" value="<%=SystemEnv.getHtmlLabelName(725,user.getLanguage()) %>" id="zd_btn_submit" class="zd_btn_submit" onclick="javascript:doSave(this)">
            <span class="e8_sep_line">|</span>
            <input type="button" value="<%=SystemEnv.getHtmlLabelName(309,user.getLanguage())%>" id="zd_btn_cancle"  class="zd_btn_cancle" onclick="clostWin();">
        </td></tr>
    </table>
</div>

<!-- 定义和显示右上角的菜单，id和样式固定不能随便修改。td里面的内容为显示的操作按钮 -->
<table id="topTitle" cellpadding="0" cellspacing="0">
    <tr>
        <td class="rightSearchSpan" style="text-align:right;" >
            <!--<input type="button" value="<%=SystemEnv.getHtmlLabelName(725,user.getLanguage()) %>" class="e8_btn_top" onclick="javascript:doSave(this)">-->
            <span title="<%=SystemEnv.getHtmlLabelName(83721,user.getLanguage())%>" class="cornerMenu" id="rightclickcornerMenu">
            </span>
        </td>
    </tr>
</table>

<script type="text/javascript">
var pdialog = parent.getDialog(window);//获取窗口对象；
var parentWin = parent.getParentWindow(window);//获取父对象；
function DateCompare(YearFrom, MonthFrom, DayFrom,YearTo, MonthTo,DayTo){
	YearFrom  = parseInt(YearFrom,10);
	MonthFrom = parseInt(MonthFrom,10);
	DayFrom = parseInt(DayFrom,10);
	YearTo	= parseInt(YearTo,10);
	MonthTo   = parseInt(MonthTo,10);
	DayTo = parseInt(DayTo,10);
	if(YearTo<YearFrom){
		return false;
	}else{
		if(YearTo==YearFrom){
			if(MonthTo<MonthFrom){
				return false;
			}else{
				if(MonthTo==MonthFrom){
					if(DayTo<DayFrom)
					return false;
					else
					return true;
				}
				else
				return true;
			}
		}else{
			return true;
		}
	}
}
function clostWin(){
    pdialog.close();
}
function doSave(){
	var dqr = jQuery("#dqr").val();
	if(dqr == "") {
		Dialog.alert("请选择到期日");
		return;
	}
	var YearFrom=document.getElementById("swrq").value.substring(0,4);
	var MonthFrom=document.getElementById("swrq").value.substring(5,7);
	var DayFrom=document.getElementById("swrq").value.substring(8,10);
	var YearTo=document.getElementById("dqr").value.substring(0,4);
	var MonthTo=document.getElementById("dqr").value.substring(5,7);
	var DayTo=document.getElementById("dqr").value.substring(8,10);
	if (!DateCompare(YearFrom, MonthFrom, DayFrom,YearTo, MonthTo,DayTo )){
		Dialog.alert("到期日不能小于收文日期");
		jQuery("#dqr").val("");
		jQuery("#dqrspan").text("");
		return;
	}
	var jbnbyj = jQuery("#jbnbyj").val();
	var swlx = jQuery("#swlx").val();
	var jbdw = jQuery("#jbdw").val();
	var blqx = jQuery("#blqx").val();
	var dqr = jQuery("#dqr").val();
	var swrq = jQuery("#swrq").val();
	var lwdw = jQuery("#lwdw").val();
	var bt = jQuery("#bt").val();
	var wh = jQuery("#wh").val();
	var zbbm = jQuery("#jdbw_old").val();
	
	if (swrq == ""){
		Dialog.alert("收文日期不能为空");
		return;
	}
	if (jbdw == ""){
		Dialog.alert("交办单位不能为空");
		return;
	}
	if (dqr == ""){
		Dialog.alert("到期日不能为空");
		return;
	}
	if (swlx == ""){
		Dialog.alert("收文类型不能为空");
		return;
	}
	var flag = false;
	//触发督查督办流程
	jQuery.ajax({
		url : '/createdcdb',
		data : {
			swrq : swrq,
			lwdw : lwdw,
			bt : bt,
			wh : wh,
			jbnbyj : jbnbyj,
			swlx : swlx,
			jbdw : jbdw,
			jbdw_old : zbbm,
			blqx : blqx,
			dqr : dqr
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

//定义一个轮询的方法，来判断浏览按钮的隐藏域值是否发生变化
//如果发生变化则执行要调用的方法
function bindchange(id, fun) {
    var old_val = jQuery(id).val();
    setInterval(function() {
        var new_val = jQuery(id).val();
        if(old_val != new_val) {
            old_val = new_val;
            fun();
        }
    }, 500);
}

jQuery(document).ready(function() {
    //调用绑定的事件方法
    bindchange("#dqr", getDetail145817);
});

function getDetail145817() {
    var dqr = jQuery("#dqr").val();
	var swrq = jQuery("#swrq").val();
	
    //执行其他操作
	
	jQuery.ajax({
		url : '/DCDBCaculateDay',
		data : {
			swrq : swrq,
			dqr : dqr
		},
		type:'post',
    	async: false,
    	dataType:'json',
    	cache:'false',
		success:function(data) {
			if(data.success == "1") {
				alert(data.message);
				jQuery("#dqr").val("");
				jQuery("#dqrspan").text("");
			} else {
				jQuery("#blqx").val(data.count);
				jQuery("#blqxspan").text(data.count);
			}
		}
	})
}

</script>
 </body>
</html>