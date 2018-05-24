<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/weaver.tld" prefix="wea"%>
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
<body scroll="no">


<table class="LayoutTable" id="" style="display:;">
		<colgroup>
			<col width="15%">
			<col width="35%">
			<col width="15%">
			<col width="35%">
		</colgroup>
		<tbody>		
		<tr class="intervalTR" _samepair="showgroup" style="display:">
			<td colspan="2">
				<table class="LayoutTable" style="width:100%;height:30px;">
					<colgroup>
						<col width="50%">
						<col width="50%">
					</colgroup>
					<tbody>
						<tr class="groupHeadHide" style="background-color: #F2F2F2">
							<td class="interval" style="border-left-style: solid !important;border-left-color: rgb(0, 112, 192) !important;">
								<span class="groupbg"> </span>
								<span class="e8_grouptitle" style="height: 30px; vertical-align: middle; white-space: nowrap; text-overflow: ellipsis; word-break: keep-all; overflow: hidden;">部门会签办理情况</span>
							</td>
							<td class="interval" colspan="2" style="text-align:right;">
								<span class="toolbar">	
								</span>															
								<!--  style="display:" -->
								<span _status="0" class="hideBlockDiv" style="display:">
									
								</span>
							</td>
						</tr>
					</tbody>
				</table>
			</td>
		</tr>
		<tr class="Spacing" style="height:1px;display:">
			<td class="Line" colspan="4"></td>
		</tr>
		<tr class="intervalTR" _samepair="showgroup" style="display:">
			<td colspan="4">
				<table cellspacing="0" class="ListStyle" style="table-layout: fixed;">
					<colgroup>
						<col _display="none" style="display: none;">
						<col width="20%" _itemid="1" style="width: 20%;">
						<col width="25%" _itemid="2" style="width: 25%;">
						<col width="25%" _itemid="3" style="width: 25%;">
						<col width="30%" _itemid="4" style="width: 30%;">
					</colgroup>
					<thead>
						<tr class="HeaderForXtalbe">
							<th align="left" title=""
								style="height: 30px; text-overflow: ellipsis; white-space: nowrap; word-break: keep-all; overflow: hidden; display: none;"></th>
							<th _itemid="1" align="left" title="办理人"
								style="height: 30px; text-overflow: ellipsis; white-space: nowrap; word-break: keep-all; overflow: hidden; width: 20%;">办理人</th>
							<th _itemid="2" align="left" title="部门"
								style="height: 30px; text-overflow: ellipsis; white-space: nowrap; word-break: keep-all; overflow: hidden; width: 25%;">部门</th>
							<th _itemid="3" align="left" title="岗位"
								style="height: 30px; text-overflow: ellipsis; white-space: nowrap; word-break: keep-all; overflow: hidden; width: 25%;">岗位</th>
							<th _itemid="4" align="left" title="查看详情"
								style="height: 30px; text-overflow: ellipsis; white-space: nowrap; word-break: keep-all; overflow: hidden; width: 30%;">查看详情</th>
						</tr>
					</thead>
					<tbody>
					<%
						String _requestid = Util.null2String(request.getParameter("requestid"));
						weaver.interfaces.action.XXNB XXNB = new weaver.interfaces.action.XXNB();
						java.util.Map<String,String> utMap = new java.util.HashMap<String,String>();
						new weaver.general.BaseBean().writeLog("_requestid:"+_requestid);
						utMap.put("requestid",_requestid);
						utMap.put("ismobile","0");
						java.util.List<java.util.Map<String, String>> listMap = XXNB.getData(user,utMap,request,response);
						java.lang.StringBuffer tablesb = new java.lang.StringBuffer();
						for(java.util.Map<String, String> lMap : listMap) {
							String _czyj = lMap.get("czyj");
							String _nodename = lMap.get("nodename");
							String _creater = lMap.get("creater");
							String _department = lMap.get("department");
							tablesb.append("<tr style=\"vertical-align: middle;\">");
							tablesb.append("<td style=\"height: 30px; display: none; width: 3%;\">&nbsp;<input type=\"checkbox\" style=\"display: none\" id=\"\" checkboxid=\"300\" value=\"300\"></td>");
							tablesb.append("<td spacevalue=\"300\" align=\"left\" title=\""+_creater+"\" style=\"height: 30px; vertical-align: middle; white-space: nowrap; text-overflow: ellipsis; word-break: keep-all; overflow: hidden;\">"+_creater+"</td>");
							tablesb.append("<td spacevalue=\"300\" align=\"left\" title=\""+_department+"\" style=\"height: 30px; vertical-align: middle; white-space: nowrap; text-overflow: ellipsis; word-break: keep-all; overflow: hidden;\">"+_department+"</td>");
							tablesb.append("<td spacevalue=\"300\" align=\"left\" title=\""+_nodename+"\" style=\"height: 30px; vertical-align: middle; white-space: nowrap; text-overflow: ellipsis; word-break: keep-all; overflow: hidden;\">"+_nodename+"</td>");
							tablesb.append("<td spacevalue=\"300\" align=\"left\" title=\"查看详情\" style=\"height: 30px; vertical-align: middle; white-space: nowrap; text-overflow: ellipsis; word-break: keep-all; overflow: hidden;\">"+_czyj+"</td>");
							tablesb.append("</tr>");
							tablesb.append("<tr class=\"Spacing\" style=\"height: 1px !important;\"><td colspan=\"4\" class=\"paddingLeft0Table\"><div class=\"intervalDivClass\"></div></td></tr>");
						}
						out.print(tablesb.toString());
					%>
				</tbody>
			</table>
			</td>
		</tr>
	</tbody>
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
	var width = jQuery('#xgzy', window.parent.document).css("width");
	jQuery("#LayoutTable").css("width",width-10);
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