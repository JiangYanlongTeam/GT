<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/weaver.tld" prefix="wea"%>
<%@ include file="/systeminfo/init_wev8.jsp" %>
<%@ page import="weaver.general.Util" %>
<HTML><HEAD>
<LINK href="/css/Weaver_wev8.css" type=text/css rel=STYLESHEET>
<script src="/js/ecology8/jquery_wev8.js"></script>
<!--checkbox组件-->
<link href="/js/ecology8/jNice/jNice/jNice_wev8.css" type=text/css rel=stylesheet>
<script language=javascript src="/js/ecology8/jNice/jNice/jquery.jNice_wev8.js"></script>
<!-- 下拉框美化组件-->
<link href="/js/ecology8/selectbox/css/jquery.selectbox_wev8.css" type=text/css rel=stylesheet>
<script language=javascript src="/js/ecology8/selectbox/js/jquery.selectbox-0.2_wev8.js"></script>
<!-- 泛微可编辑表格组件-->
<link rel="stylesheet" href="/wui/theme/ecology8/weaveredittable/css/WeaverEditTable_wev8.css">
<script  src="/wui/theme/ecology8/weaveredittable/js/WeaverEditTable_wev8.js"></script>

<script language=javascript src="/js/ecology8/request/e8.browser_wev8.js"></script>

<link type="text/css" href="/js/ecology8/base/jquery-ui_wev8.css" rel=stylesheet>
<script type="text/javascript" src="/js/ecology8/base/jquery-ui_wev8.js"></script>

<script type='text/javascript' src='/js/jquery-autocomplete/lib/jquery.bgiframe.min_wev8.js'></script>
<script type='text/javascript' src='/js/jquery-autocomplete/jquery.autocomplete_wev8.js'></script>
<script type='text/javascript' src='/js/jquery-autocomplete/browser_wev8.js'></script>
<link rel="stylesheet" type="text/css" href="/js/jquery-autocomplete/jquery.autocomplete_wev8.css" />
<link rel="stylesheet" type="text/css" href="/js/jquery-autocomplete/browser_wev8.css" />
<style>
	.wfremindimg{
		vertical-align: middle;
		margin-top: -3px;
		padding-left: 0px;
		cursor: pointer;
	}
</style>
</HEAD>
<body scroll="no">
<%@ include file="/systeminfo/RightClickMenuConent_wev8.jsp" %>
<%@ include file="/systeminfo/RightClickMenu_wev8.jsp" %>
<%
    String tiptitle = "流程信息" ;
	String type = Util.null2String(request.getParameter("type"));
	String workflowids = Util.null2String(request.getParameter("workflowids"));
	int pageSize = 8;
%>
<%
    String tableString = "";  //定义表格xml数据
    //指定分页条数和初始化id以及是否有复选框 以及数据来源  datasource表示数据来源 sourceparams表示传入参数参数格式为"name:value+name1:value1"多个参数用加号连接
    tableString =   " <table instanceid=\"ds_list\" tabletype=\"none\" datasource=\"weaver.interfaces.action.WorkflowReport.report\" sourceparams=\"userid:"+user.getUID()+"+type:"+type+"+workflowids:"+workflowids+"\" pagesize=\""+pageSize+"\" >"+
                    "       <sql backfields=\"*\" sqlform=\"tmptable\" sqlwhere=\"\"  sqlorderby=\"\"  sqlprimarykey=\"id\" sqlsortway=\"asc\" sqlisdistinct=\"false\" />"+
                    "       <head>"+
                    "           <col width=\"10%\"  text=\"业务编号\" column=\"requestid\" orderkey=\"requestid\" />"+
                    "           <col width=\"50%\"  text=\"标题\" column=\"requestname\" orderkey=\"requestname\"/>"+
                    "           <col width=\"15%\"  text=\"流程类型\" column=\"workflowname\" orderkey=\"workflowname\"/>"+
                    "           <col width=\"15%\"  text=\"接收时间\" column=\"receivedatetime\"  orderkey=\"receivedatetime\" />"+
                  //  "           <col width=\"10%\"  text=\"前一用户\" column=\"beforeuser\" orderkey=\"beforeuser\" />"+
                    "           <col width=\"15%\"  text=\"当前岗位\" column=\"nodename\" orderkey=\"nodename\" />"+
                    "       </head>"+
                    " </table>";

%>
 <wea:SplitPageTag  tableString='<%=tableString%>'  mode="run" />
<script type="text/javascript">

</script>
 </body>
 <script>
	 function openFullWindowHaveBarForWF(url,requestid){
    
	     try{
	 		$("#wflist_"+requestid+"img").hide();
	 		$("#wflist_"+requestid+"img").parent('a').find('.reqname').removeAttr("style");
	 	}catch(e){}
     
	 	if (url.indexOf("?") != -1) {
	 		url += "&";
	 	} else {
	 		url += "?";
	 	}
	
	 	url += "e7" + new Date().getTime() + "=";
	 	var redirectUrl = url ;
	 	var width = screen.availWidth-10 ;
	 	var height = screen.availHeight-50 ;
	 	//if (height == 768 ) height -= 75 ;
	 	//if (height == 600 ) height -= 60 ;
	 	 var szFeatures = "top=0," ;
	 	szFeatures +="left=0," ;
	 	szFeatures +="width="+width+"," ;
	 	szFeatures +="height="+height+"," ;
	 	szFeatures +="directories=no," ;
	 	szFeatures +="status=yes,toolbar=no,location=no," ;
	 	szFeatures +="menubar=no," ;
	 	szFeatures +="scrollbars=yes," ;
	 	szFeatures +="resizable=yes" ; //channelmode
	 	window.open(redirectUrl,"",szFeatures) ;
	 }
 </script>
</html>