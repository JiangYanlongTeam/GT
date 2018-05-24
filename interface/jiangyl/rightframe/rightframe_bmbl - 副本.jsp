<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/weaver.tld" prefix="wea"%>
<%@ include file="/systeminfo/init_wev8.jsp" %>
<%@ page import="weaver.general.Util"%>
<%@ page import="java.lang.*"%>
<jsp:useBean id="RecordSet" class="weaver.conn.RecordSet" scope="page" />
<!DOCTYPE html>
<HTML><HEAD>
<LINK href="/mobile/plugin/rightframe/css/bootstrap.css" type="text/css" rel="STYLESHEET">
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
								<span class="groupbg" style=""></span>
								<span class="e8_grouptitle" style="height: 30px; vertical-align: middle; white-space: nowrap; text-overflow: ellipsis; word-break: keep-all; overflow: hidden;">相关附件</span>
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
			<td class="Line" colspan="2"></td>
		</tr>
		<tr class="items intervalTR" style="display:">
			<td colspan="2">
				<div style="height:200px;overflow:auto;">
				<table _target="mainFileUploadField" class="annexblocktblclass" cols="3" id="field6859_tab" style="border-collapse:collapse;border:0px;width:100%;">
				<colgroup>
					<col width="70%">
					<col width="17%">
				</colgroup>
				<tbody>
					<%
					//接受requestid，获取表名，获取对应的相关附件字段，根据附件的id获取多个附件的id
					String _requestid = Util.null2String(request.getParameter("reqid"));
					String _tablename = Util.null2String(request.getParameter("tablename"));
					String _column = Util.null2String(request.getParameter("column"));
					StringBuffer sb = new StringBuffer();
					StringBuffer sb1 = new StringBuffer();
					StringBuffer sb2 = new StringBuffer();
					if("".equals(_requestid) || "undefined".equals(_requestid)){
						out.print("接受参数reqid为空，该流程为未保存状态");
					} else if("".equals(_tablename)){
						out.print("接受参数tablename为空");
					}else if("".equals(_column)){
						out.print("接受参数column为空");
					} else {
						String sql = "select " + _column + " from " + _tablename + " where requestid = "+_requestid+"";
						new weaver.general.BaseBean().writeLog("sql:"+sql);
						RecordSet.execute(sql);
						RecordSet.next();
						String xgfj = Util.null2String(RecordSet.getString(_column));
						if("".equals(xgfj)) {
							
						} else {
							String _sql = "select docid,imagefileid,imagefilename from DOCIMAGEFILE where docid in ("+xgfj+")";
							new weaver.general.BaseBean().writeLog("sql:"+_sql);
							RecordSet.execute(_sql);
							java.util.Map<String,String> map = new java.util.HashMap<String,String>();
							map.put("doc", "doc_wev8.png");
							map.put("docx", "doc_wev8.png");
							map.put("xls", "xls_wev8.png");
							map.put("pdf", "pdf_wev8.png");
							map.put("txt", "txt_wev8.png");
							//shell32_dll_icon_1_wev8.gif
							map.put("jpg", "jpg_wev8.png");
							map.put("jpeg", "jpg_wev8.png");
							map.put("png", "jpg_wev8.png");
							map.put("gif", "jpg_wev8.png");
							while(RecordSet.next()){
								String _imagefileid = Util.null2String(RecordSet.getString("imagefileid"));
								String _imagefilename = Util.null2String(RecordSet.getString("imagefilename"));
								String _docid = Util.null2String(RecordSet.getString("docid"));
								String _imagefilenametype = _imagefilename.substring(_imagefilename.lastIndexOf(".")+1,_imagefilename.length());
								String imgtype = "shell32_dll_icon_1_wev8.gif";
								if(map.containsKey(_imagefilenametype)) {
									imgtype = (String) map.get(_imagefilenametype);
								}
								sb.append("<tr style=\"border-bottom:1px solid #e6e6e6;height: 40px;\">");
								sb.append("<td class=\"fieldvalueClass\" valign=\"middle\" colspan=\"2\" style=\"word-break:normal;word-wrap:normal;\">");
								sb.append("<div style=\"float: left; height: 40px; line-height: 40px; width: 90%; background-color: rgb(255, 255, 255);\" class=\"fieldClassChange\">");
								sb.append("<div style=\"float:left;width:20px;height:40px; line-height:40px;\">");
								sb.append("<span style=\"display:inline-block;vertical-align: middle;padding-top:6px;\">");
								sb.append("<img border=\"0\" src=\"/images/filetypeicons/"+imgtype+"\" width=\"20\" height=\"20\">");
								sb.append("</span>");
								sb.append("</div>");
								sb.append("<div style=\"float:left;\">");
								sb.append("<span style=\"display:inline-block;width:180px;height:30px;padding-bottom:10px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;vertical-align: middle;\">");
								sb.append("<a style=\"cursor:pointer;font-family:微软雅黑 !important;font-weight:bold;\" onclick=\"parent.addDocReadTag('"+_docid+"');parent.openDocExt('"+_docid+"','','"+_imagefileid+"',0)\" title=\""+_imagefilename+"\">"+_imagefilename+"</a>&nbsp;");
								sb.append("</span>");
								sb.append("</div>");
								sb.append("</div>");
								sb.append("<div style=\"float: left; height: 40px; line-height: 40px; width: 10%; background-color: rgb(255, 255, 255);\" class=\"fieldClassChange\">");
								sb.append("<span id=\"selectDownload\">");
								sb.append("<a style=\"display:inline-block;cursor:pointer;vertical-align:middle;width:20px;height:20px;margin-bottom:5px;background-image:url('/images/ecology8/workflow/fileupload/upload_wev8.png');\" onclick=\"parent.addDocReadTag('"+_docid+"');parent.downloads('"+_imagefileid+"')\" title=\"下载\"></a>");
								sb.append("</span>");
								sb.append("</div>");
								sb.append("</td>");
								sb.append("</tr>");
							}
						}
						out.print(sb.toString());
					}
					%>
				</table>
				</div>
			</td>
		</tr>
		<tr class="Spacing" style="height:1px;display:">
			<td class="Line" colspan="2"></td>
		</tr>
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
								<span class="e8_grouptitle" style="height: 30px; vertical-align: middle; white-space: nowrap; text-overflow: ellipsis; word-break: keep-all; overflow: hidden;">历史收文单</span>
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
			<td class="Line" colspan="2"></td>
		</tr>
		<tr class="items intervalTR" style="display:">
			<td colspan="2">
				<div style="height:120px;overflow:auto;">
				<table _target="mainFileUploadField" class="annexblocktblclass" cols="3" id="field6859_tab" style="border-collapse:collapse;border:0px;width:100%;">
				<colgroup>
					<col width="70%">
					<col width="17%">
				</colgroup>
				<tbody>
					<%
					if("".equals(_requestid) || "undefined".equals(_requestid)){
						out.print("接受参数reqid为空，该流程为未保存状态");
					} else {
						//String sql = "select requestid,requestname from workflow_requestbase where requestid != '"+_requestid+"' and workflowid= '61' and requestname = (select //requestname from workflow_requestbase where requestid = '"+_requestid+"')";
						//String sql ="select requestid,requestname  from workflow_requestbase where requestid !=( select mainRequestId from workflow_requestbase where requestid = //'"+_requestid+"') and workflowid= '61' and requestname = (select requestname from workflow_requestbase where requestid = ( select mainRequestId from //workflow_requestbase where requestid = '"+_requestid+"'))";
						String sql ="select requestid,swrq from formtable_main_37 where requestid in ("+"select requestid from workflow_requestbase where requestid !=( select mainRequestId from workflow_requestbase where requestid = '"+_requestid+"') and workflowid= '61' and requestname = (select requestname from workflow_requestbase where requestid = ( select mainRequestId from workflow_requestbase where requestid = '"+_requestid+"')))";
						new weaver.general.BaseBean().writeLog("sql我的:"+sql);						
						RecordSet.execute(sql);
						while(RecordSet.next()) {
							String reqid = Util.null2String(RecordSet.getString("requestid"));
							String bt = Util.null2String(RecordSet.getString("swrq"));
							
							sb2.append("<tr style=\"border-bottom:1px solid #e6e6e6;height: 40px;\">");
							sb2.append("<td class=\"fieldvalueClass\" valign=\"middle\" colspan=\"2\" style=\"word-break:normal;word-wrap:normal;\">");
							sb2.append("<div style=\"float: left; height: 40px; line-height: 40px; width: 100%; background-color: rgb(255, 255, 255);\" class=\"fieldClassChange\">");
							sb2.append("<div style=\"float:left;margin-left:5px;\">");
							sb2.append("<span style=\"display:inline-block;width:100%;height:30px;padding-bottom:10px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;vertical-align: middle;\">");
							sb2.append("<a style=\"cursor:pointer;font-family:微软雅黑 !important;font-weight:bold;\" onclick=\"openWf('"+reqid+"')\" title=\""+bt+"\">"+bt+"</a>&nbsp;");
							sb2.append("</span>");
							sb2.append("</div>");
							sb2.append("</div>");
							sb2.append("</td>");
							sb2.append("</tr>");
						}
						out.print(sb2.toString());
					}
					%>
				</table>
				</div>
			</td>
		</tr>
		<tr class="Spacing" style="height:1px;display:">
			<td class="Line" colspan="2"></td>
		</tr>
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
								<span class="e8_grouptitle" style="height: 30px; vertical-align: middle; white-space: nowrap; text-overflow: ellipsis; word-break: keep-all; overflow: hidden;">部门办理情况</span>
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
		<tr class="intervalTR" _samepair="showgroup" style="display:">
			<td colspan="2">
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
						weaver.interfaces.action.BMSWBL BMSWBL = new weaver.interfaces.action.BMSWBL();
						java.util.Map<String,String> utMap = new java.util.HashMap<String,String>();
						new weaver.general.BaseBean().writeLog("_requestid:"+_requestid);
						utMap.put("requestid",_requestid);
						utMap.put("zhuban","zbbm");
						utMap.put("xieban","xbbm");
						utMap.put("ismobile","0");
						java.util.List<java.util.Map<String, String>> listMap = BMSWBL.getData(user,utMap,request,response);
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