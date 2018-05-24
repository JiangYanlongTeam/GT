<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/weaver.tld" prefix="wea"%>
<%@ taglib uri="/WEB-INF/weaver.tld" prefix="wea1"%>
<%@ include file="/systeminfo/init_wev8.jsp" %>
<%@ page import="weaver.general.Util"%>
<%@ page import="java.lang.*"%>
<jsp:useBean id="RecordSet" class="weaver.conn.RecordSet" scope="page" />
<!DOCTYPE html>
<HTML><HEAD>
<LINK href="/css/Weaver_wev8.css" type=text/css rel=STYLESHEET>
<style>
*{
	font-size:12pt !important;
	font-family: 微软雅黑 !important;
}
</style>
</HEAD>
<body style="margin:0 auto">
	<table cellspacing="0" class="ListStyle" style="table-layout: fixed;">
		<colgroup>
			<col _display="none" style="display: none;">
			<col width="20%" _itemid="1" style="width: 60%;">
			<col width="25%" _itemid="2" style="width: 20%;">
			<col width="25%" _itemid="3" style="width: 20%;">
		</colgroup>
		<thead>
			<tr class="HeaderForXtalbe">
				<th align="left" title=""
					style="height: 30px; text-overflow: ellipsis; white-space: nowrap; word-break: keep-all; overflow: hidden; display: none;"></th>
				<th _itemid="1" align="left" title="签字意见"
					style="height: 30px; text-overflow: ellipsis; white-space: nowrap; word-break: keep-all; overflow: hidden; width: 60%;font-weight:bold">签字意见</th>
				<th _itemid="2" align="left" title="签字"
					style="height: 30px; text-overflow: ellipsis; white-space: nowrap; word-break: keep-all; overflow: hidden; width: 20%;font-weight:bold">签字</th>
				<th _itemid="3" align="left" title="签字时间"
					style="height: 30px; text-overflow: ellipsis; white-space: nowrap; word-break: keep-all; overflow: hidden; width: 20%;font-weight:bold">签字时间</th>
			</tr>
		</thead>
		<tbody>
		<%
			String _requestid = Util.null2String(request.getParameter("requestid"));
			weaver.interfaces.action.XXNB XXNB = new weaver.interfaces.action.XXNB();
			java.util.Map<String,String> utMap = new java.util.HashMap<String,String>();
			new weaver.general.BaseBean().writeLog("_requestid:"+_requestid);
			utMap.put("requestid",_requestid);
			java.util.List<java.util.Map<String, String>> listMap = XXNB.getBrotherDTData(user,utMap,request,response);
			java.lang.StringBuffer tablesb = new java.lang.StringBuffer();
			for(java.util.Map<String, String> lMap : listMap) {
				String _riqi = lMap.get("riqi");
				String _yijian = lMap.get("yijian");
				String _qianming = lMap.get("qianming");
				tablesb.append("<tr style=\"vertical-align: middle;\">");
				tablesb.append("<td style=\"height: 30px; display: none; width: 3%;\">&nbsp;<input type=\"checkbox\" style=\"display: none\" id=\"\" checkboxid=\"300\" value=\"300\"></td>");
				tablesb.append("<td spacevalue=\"300\" align=\"left\" title=\""+_yijian+"\" style=\"height: 30px; vertical-align: middle; white-space: nowrap; text-overflow: ellipsis; word-break: keep-all; overflow: hidden;font-weight:bold\">"+_yijian+"</td>");
				tablesb.append("<td spacevalue=\"300\" align=\"left\" title=\""+_qianming+"\" style=\"height: 30px; vertical-align: middle; white-space: nowrap; text-overflow: ellipsis; word-break: keep-all; overflow: hidden;font-weight:bold\">"+_qianming+"</td>");
				tablesb.append("<td spacevalue=\"300\" align=\"left\" title=\""+_riqi+"\" style=\"height: 30px; vertical-align: middle; white-space: nowrap; text-overflow: ellipsis; word-break: keep-all; overflow: hidden;font-weight:bold\">"+_riqi+"</td>");
				tablesb.append("</tr>");
				tablesb.append("<tr class=\"Spacing\" style=\"height: 1px !important;\"><td colspan=\"3\" class=\"paddingLeft0Table\"><div class=\"intervalDivClass\"></div></td></tr>");
			}
			out.print(tablesb.toString());
		%>
	</tbody>
</table>
<script type="text/javascript">
function Onchange(obj){
        if(obj.value=="0"){
            showEle("showdiv","true");//显示指定属性的单元格
            showGroup("showgroup","true");//显示指定属性的group
        }else if(obj.value=="1"){
            hideEle("showdiv","true");//隐藏指定属性的单元格
        }else if(obj.value=="2"){
            hideGroup("showgroup","true");//隐藏指定属性的group
        }
}
jQuery(function(){
	jQuery("#message_table_Div").remove();
	
	
});
function downloads(id){
	document.location.href="/weaver/weaver.file.FileDownload?fileid="+id+"&download=1";
}
function changecancleon(obj){
	jQuery(obj).find(".fieldClassChange").css("background-color","#f4fcff");
	jQuery(obj).find("#fieldCancleChange").find("span").css("display","block");
}

function changecancleout(obj){
	jQuery(obj).find(".fieldClassChange").css("background-color","#ffffff");
	jQuery(obj).find("#fieldCancleChange").find("span").css("display","none");
}

function openWf(id) {
	window.open("/proj/RequestView.jsp?requestid="+id);
}
</script>
 </body>
</html>