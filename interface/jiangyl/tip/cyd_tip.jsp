<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/weaver.tld" prefix="wea"%>
<%@ include file="/systeminfo/init_wev8.jsp" %>
<HTML><HEAD>
	<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />
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
<script language="javascript" src="/wui/theme/ecology8/jquery/js/zDialog_wev8.js"></script>

</HEAD>
<body scroll="no">
<%@ include file="/systeminfo/RightClickMenuConent_wev8.jsp" %>
<%
RCMenu += "{查询,javascript:dosubmit(),_self} " ;
RCMenuHeight += RCMenuHeightStep;
int uid = user.getUID();
%>
<%@ include file="/systeminfo/RightClickMenu_wev8.jsp" %>
<%
%>
<form action="" method="post" id="weaver" name="weaver">
<wea:layout type="2Col">
     <wea:group context="提交信息">
		<wea:item>签名</wea:item>
		<wea:item><img src="/weaver/weaver.file.ImgFileDownload?userid=<%=uid%>"></wea:item>
		<wea:item>日期</wea:item>
		<wea:item>
			<script>
				var rq = parent.getParentWindow(window).document.getElementById("field6821").value
				document.write(rq);
			</script>
			
		</wea:item>
     </wea:group>
</wea:layout>
	<div id="zDialog_div_bottom" class="zDialog_div_bottom">
	    <table width="100%">
	        <tr><td style="text-align:center;">
	            <input type="button" value="确定" id="zd_btn_submit" class="zd_btn_submit" onclick="javascript:doSave(this)">
	            <span class="e8_sep_line">|</span>
	            <input type="button" value="<%=SystemEnv.getHtmlLabelName(309,user.getLanguage())%>" id="zd_btn_cancle"  class="zd_btn_cancle" onclick="clostWin();">
	        </td></tr>
	    </table>
	</div>
</form>
<script type="text/javascript">
var pdialog = parent.getDialog(window);//获取窗口对象；
var parentWin = parent.getParentWindow(window);//获取父对象；
jQuery(document).ready(function () {//初始化表单查询按钮
    $("#topTitle").topMenuTitle({searchFn:onBtnSearchClick});
    $(".topMenuTitle td:eq(0)").html($("#tabDiv").html());
    $("#tabDiv").remove();
});

function onBtnSearchClick(){//点击快捷搜索的放大镜调用的方法。
    enableAllmenu();
    var name=$("input[name='flowTitle']",parent.document).val();
    jQuery("input[name='name']").val(name);
    location.href="/demo/demo_listall.jsp?temp="+Math.random()+"&name="+name;
}

function onReset() {
    jQuery('input[name="flowTitle"]', parent.document).val('');
    jQuery('input[name="name"]').val('');
}

function dosubmit(){
    weaver.submit();
}

function newDialog(){
    alert("新建数据");
}

function DeleteData(){
    alert("删除数据");
}
//获取勾选的ID
function doCheck(){
	var selectedIds = _xtable_CheckedRadioId();
	if(selectedIds == ""){
		Dialog.alert("请选择需要操作的数据!");
		return "请选择需要操作的数据!";
	}
	return selectedIds;
}
function clearValue(){
    pdialog.close();
}
function clostWin(){
    pdialog.close();
}

function doSave(){
	pdialog.close();  
}
</script>
 </body>
</html>
