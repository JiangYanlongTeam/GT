<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/weaver.tld" prefix="wea"%>
<%@ taglib uri="/WEB-INF/tld/browser.tld" prefix="brow"%>
<%@ include file="/systeminfo/init_wev8.jsp" %>
<HTML><HEAD>
<LINK href="/css/Weaver_wev8.css" type=text/css rel=STYLESHEET>
<SCRIPT language="javascript" src="../../js/weaver_wev8.js"></script>	
<script type="text/javascript" src="/js/ecology8/request/wfSearchResult_wev8.js"></script>
<SCRIPT language="javascript" defer="defer" src="/js/datetime_wev8.js"></script>
<SCRIPT language="javascript" defer="defer" src="/js/JSDateTime/WdatePicker_wev8.js"></script>
<SCRIPT language="javascript" defer="defer" src="/js/JSDateTime/calendar_wev8.js"></script>	
<style type="text/css">
*{
	font-size:18px !important;
}
table.gridtable {
	font-family: 微软雅黑;
	font-size: 12px;
	color: #333333;
	border-width: 1px;
	border-color: #F5F5F5;
	border-collapse: collapse;
}
table.gridtable tr {
	text-align:center;
}
table.gridtable th {
	font-family: 微软雅黑;
	font-size: 12px;
	text-align: center;
	border-width: 1px;
	border-style: solid;
	border-color: #F5F5F5;
	background-color: #DCDCDC;
}

table.gridtable td {
	font-family: 微软雅黑;
	font-size: 12px;
	width: 120px;
	text-align: center;
	border-width: 1px;
	border-style: solid;
	border-color: #F5F5F5;
	background-color: #ffffff;
}
</style>
</HEAD>
<%
String hrmresource_id = Util.null2String(request.getParameter("id"));
weaver.conn.RecordSet rs = new weaver.conn.RecordSet();
String hrmresource_name = "";
String hrmresource_workcode = "";
String hrmresource_dsporder = "";
if(!"".equals(hrmresource_id)) {
	String sql = "select lastname,workcode,dsporder from hrmresource where id = '"+hrmresource_id+"'";
	rs.execute(sql);
	rs.next();
	hrmresource_name = Util.null2String(rs.getString("lastname"));
	hrmresource_workcode = Util.null2String(rs.getString("workcode"));
	hrmresource_dsporder = Util.null2String(rs.getString("dsporder"));
}
%>
<body scroll="no">
<%@ include file="/systeminfo/RightClickMenuConent_wev8.jsp" %>
<%
RCMenu += "{查询,javascript:onSubmit(),_self} " ;
RCMenuHeight += RCMenuHeightStep;	
RCMenu += "{导出,javascript:exp(),_self} " ;
RCMenuHeight += RCMenuHeightStep;
%>
<%@ include file="/systeminfo/RightClickMenu_wev8.jsp" %>
<wea:layout type="2Col">
     <wea:group context="查询条件" attributes="{'class':\"e8_title e8_title_1\",'samePair':'showgroup'}">
      <wea:item>开始日期</wea:item>
      <wea:item>
		<button class="calendar" type="button" id="SelectDate" onclick="getDate(begindatespan,begindate)"></button>
		<span id="begindatespan"></span>
		<input type="hidden" name="begindate" id="begindate" value="">
      </wea:item>
      <wea:item>结束日期</wea:item>
      <wea:item>
		<button type="button" class="calendar" id="SelectDate" onclick="getDate(enddatespan,enddate)"></button>
		<span id="enddatespan"></span>
		<input type="hidden" name="enddate" id="enddate" value="">
      </wea:item>
	 </wea:group>
</wea:layout>
<span id="synresult"></span>
<script type="text/javascript">
function onSubmit(){
	var begindate = jQuery("#begindate").val();
	var enddate = jQuery("#enddate").val();
	if(begindate == "") {
		Dialog.alert("开始日期必填");
		return;
	}
	if(enddate == "") {
		Dialog.alert("结束日期必填");
		return;
	}
	enableAllmenu();
	jQuery.ajax({
		url:'/XXNBReport',// 跳转到 action  
		type:'post',
	    data:{  
	    	begindate:begindate,
			enddate:enddate
	    },
		cache : false,
		beforeSend:function(){
			e8showAjaxTips("正在处理，请稍候...",true);	
		},
		complete:function(){
			e8showAjaxTips("",false);
			displayAllmenu();
		},
	    success:function(data) {
			jQuery("#synresult").empty();
			jQuery("#synresult").html(data);
	    }, 
	    error : function() {  
			Dialog.alert("请求服务器异常，请联系系统管理员");
			e8showAjaxTips("",false);
			displayAllmenu();
			return;
	    }
	});
}
function exp(){
	var begindate = jQuery("#begindate").val();
	var enddate = jQuery("#enddate").val();
	if(begindate == "") {
		Dialog.alert("开始日期必填");
		return;
	}
	if(enddate == "") {
		Dialog.alert("结束日期必填");
		return;
	}
	var form = jQuery("<form>");  
	form.attr('style', 'display:none');  
	form.attr('target', '');  
	form.attr('method', 'post');  
	form.attr('action', '/XXNBExport');  
	var input1 = jQuery('<input>');  
	input1.attr('type', 'hidden');  
	input1.attr('name', 'begindate');  
	input1.attr('value', begindate);  
	var input2 = jQuery('<input>');  
	input2.attr('type', 'hidden');  
	input2.attr('name', 'enddate');  
	input2.attr('value', enddate);  
	$('body').append(form);  
	form.append(input1);  
	form.append(input2);  
	form.submit();  
	form.remove();
}
</script>
 </body>
</html>