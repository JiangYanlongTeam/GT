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
				<table _target="mainFileUploadField" class="annexblocktblclass" cols="3" id="field6859_tab" style="border-collapse:collapse;border:0px;width:390px;">
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
							while(RecordSet.next()){
								String _imagefileid = Util.null2String(RecordSet.getString("imagefileid"));
								String _imagefilename = Util.null2String(RecordSet.getString("imagefilename"));
								String _docid = Util.null2String(RecordSet.getString("docid"));
								sb.append("<tr onmouseover=\"changecancleon(this)\" onmouseout=\"changecancleout(this)\" style=\"border-bottom:1px solid #e6e6e6;height: 40px;\">");
								sb.append("<td class=\"fieldvalueClass\" valign=\"middle\" colspan=\"2\" style=\"word-break:normal;word-wrap:normal;\">");
								sb.append("<div style=\"float: left; height: 40px; line-height: 40px; width: 225px; background-color: rgb(255, 255, 255);\" class=\"fieldClassChange\">");
								sb.append("<div style=\"float:left;width:20px;height:40px; line-height:40px;\">");
								sb.append("<span style=\"display:inline-block;vertical-align: middle;padding-top:6px;\">");
								sb.append("<img border=\"0\" src=\"/images/filetypeicons/rar_wev8.png\" width=\"20\" height=\"20\">");
								sb.append("</span>");
								sb.append("</div>");
								sb.append("<div style=\"float:left;\">");
								sb.append("<span style=\"display:inline-block;width:200px;height:30px;padding-bottom:10px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;vertical-align: middle;\">");
								sb.append("<a style=\"cursor:pointer;color:#8b8b8b!important;\" onmouseover=\"changefileaon(this)\" onmouseout=\"changefileaout(this)\" onclick=\"parent.addDocReadTag('"+_docid+"');parent.openAccessory('"+_imagefileid+"')\" title=\""+_imagefilename+"\">"+_imagefilename+"</a>&nbsp;");
								sb.append("</span>");
								sb.append("</div>");
								sb.append("</div>");
								sb.append("<div style=\"float: left; height: 40px; line-height: 40px; width: 30px; padding-left: 2px; background-color: rgb(255, 255, 255);\" class=\"fieldClassChange\">");
								sb.append("<span id=\"selectDownload\">");
								sb.append("<a style=\"display:inline-block;cursor:pointer;vertical-align:middle;width:20px;height:20px;margin-bottom:5px;background-image:url('/images/ecology8/workflow/fileupload/upload_wev8.png');\" onclick=\"parent.addDocReadTag('"+_docid+"');downloads('"+_imagefileid+"')\" title=\"下载\"></a>");
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
								<span class="e8_grouptitle" style="height: 30px; vertical-align: middle; white-space: nowrap; text-overflow: ellipsis; word-break: keep-all; overflow: hidden;">公文收文办理局领导批办意见</span>
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
				<table _target="mainFileUploadField" class="annexblocktblclass" cols="3" id="field6859_tab" style="border-collapse:collapse;border:0px;width:390px;">
				<colgroup>
					<col width="70%">
					<col width="17%">
				</colgroup>
				<tbody>
					<%
					if("".equals(_requestid) || "undefined".equals(_requestid)){
						out.print("接受参数reqid为空，该流程为未保存状态");
					} else {
						//String sql = "select requestid,bt from "+_tablename+" where requestid != '"+_requestid+"' and bt = (select bt from "+_tablename+" where requestid = '"+_requestid+"')";
						String sql ="select requestid,requestname bt from workflow_requestbase where requestid =( select mainRequestId from workflow_requestbase where requestid = '"+_requestid+"')";
						RecordSet.execute(sql);
						while(RecordSet.next()) {
							String reqid = Util.null2String(RecordSet.getString("requestid"));
							String bt = Util.null2String(RecordSet.getString("bt"));
							sb1.append("<tr onmouseover=\"changecancleon(this)\" onmouseout=\"changecancleout(this)\" style=\"border-bottom:1px solid #e6e6e6;height: 40px;\">");
							sb1.append("<td class=\"fieldvalueClass\" valign=\"middle\" colspan=\"2\" style=\"word-break:normal;word-wrap:normal;\">");
							sb1.append("<div style=\"float: left; height: 40px; line-height: 40px; width: 225px; background-color: rgb(255, 255, 255);\" class=\"fieldClassChange\">");
							sb1.append("<div style=\"float:left;\">");
							sb1.append("<span style=\"display:inline-block;width:200px;height:30px;padding-bottom:10px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;vertical-align: middle;\">");
							sb1.append("<a style=\"cursor:pointer;color:#8b8b8b!important;\" onmouseover=\"changefileaon(this)\" onmouseout=\"changefileaout(this)\" onclick=\"openWf('"+reqid+"')\" title=\""+bt+"\">"+bt+"</a>&nbsp;");
							sb1.append("</span>");
							sb1.append("</div>");
							sb1.append("</div>");
							sb1.append("<div style=\"float: left; height: 40px; line-height: 40px; width: 30px; padding-left: 2px; background-color: rgb(255, 255, 255);\" class=\"fieldClassChange\">");
							sb1.append("<span id=\"selectDownload\">");
							sb1.append("</span>");
							sb1.append("</div>");
							sb1.append("</td>");
							sb1.append("</tr>");
						}
						out.print(sb1.toString());
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
				<table _target="mainFileUploadField" class="annexblocktblclass" cols="3" id="field6859_tab" style="border-collapse:collapse;border:0px;width:390px;">
				<colgroup>
					<col width="70%">
					<col width="17%">
				</colgroup>
				<tbody>
					<%
					if("".equals(_requestid) || "undefined".equals(_requestid)){
						out.print("接受参数reqid为空，该流程为未保存状态");
					} else {
						//String sql = "select requestid,bt from "+_tablename+" where requestid != '"+_requestid+"' and bt = (select bt from "+_tablename+" where requestid = '"+_requestid+"')";
						//String sql ="select requestid,requestname bt from workflow_requestbase where requestid = ( select mainRequestId from workflow_requestbase where requestid = '"+_requestid+"')";
						String sql2 = "select a.mainRequestId,(select formid from workflow_base where id = (select workflowid from workflow_requestbase where requestid = a.mainrequestid)) formid "+
						"from workflow_requestbase a where a.requestid = '"+_requestid+"'";
						RecordSet.execute(sql2);
						RecordSet.next();
						String name = Util.null2String(RecordSet.getString("formid"));
						String mainRequestId = Util.null2String(RecordSet.getString("mainRequestId"));
						String tableName = "formtable_main_" + Math.abs(Integer.parseInt(name));
						String sql = "select requestid,bt from " + tableName + " where bt = (select bt from "+tableName+" where requestid = '"+mainRequestId+"') and requestid != '"+mainRequestId+"'";
						//String sql = "select requestid,bt from " + tableName + " where bt = (select requestname from workflow_requestbase where requestid = '"+_requestid+"') and requestid != '"+mainRequestId+"'";
						new weaver.general.BaseBean().writeLog("_sql："+sql);
						RecordSet.execute(sql);
						while(RecordSet.next()) {
							String reqid1 = Util.null2String(RecordSet.getString("requestid"));
							String bt1 = Util.null2String(RecordSet.getString("bt"));
							sb2.append("<tr onmouseover=\"changecancleon(this)\" onmouseout=\"changecancleout(this)\" style=\"border-bottom:1px solid #e6e6e6;height: 40px;\">");
							sb2.append("<td class=\"fieldvalueClass\" valign=\"middle\" colspan=\"2\" style=\"word-break:normal;word-wrap:normal;\">");
							sb2.append("<div style=\"float: left; height: 40px; line-height: 40px; width: 225px; background-color: rgb(255, 255, 255);\" class=\"fieldClassChange\">");
							sb2.append("<div style=\"float:left;\">");
							sb2.append("<span style=\"display:inline-block;width:200px;height:30px;padding-bottom:10px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;vertical-align: middle;\">");
							sb2.append("<a style=\"cursor:pointer;color:#8b8b8b!important;\" onmouseover=\"changefileaon(this)\" onmouseout=\"changefileaout(this)\" onclick=\"openWf('"+reqid1+"')\" title=\""+bt1+"\">"+bt1+"</a>&nbsp;");
							sb2.append("</span>");
							sb2.append("</div>");
							sb2.append("</div>");
							sb2.append("<div style=\"float: left; height: 40px; line-height: 40px; width: 30px; padding-left: 2px; background-color: rgb(255, 255, 255);\" class=\"fieldClassChange\">");
							sb2.append("<span id=\"selectDownload\">");
							sb2.append("</span>");
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
		<%
			String userlang = ""+user.getLanguage();
			String para4=userlang+"+"+user.getUID()+"+"+user.getUID();
			String orderBy = "id";
			String primarykey = "id";
			//String backfields=" a.cbr,(select departmentid from hrmresource where id = a.cbr) depid,(case when b.CURRENTNODETYPE = '3' then '已办结' else '正在办理' end) status,(select nodename from WORKFLOW_NODEBASE where id = b.currentnodeid) nodename ";
			//String backfields=" a.requestid,b.currentnodeid,'操作' caozuo,(case when b.CURRENTNODETYPE = '3' then '已办结' else '正在办理' end) status,(select nodename from WORKFLOW_NODEBASE where id = b.currentnodeid) nodename ";
			String backfields=" a.requestid,b.currentnodeid,'查看详情' caozuo,a.czyj,(select nodename from WORKFLOW_NODEBASE where id = b.currentnodeid) nodename ";
			String sqlform=" formtable_main_38 a,workflow_requestbase b ";
			String sqlwhere=" a.requestId in(select requestid from workflow_requestbase  where mainRequestId= (select mainRequestId from workflow_requestbase where requestid="+_requestid+") and workflowid=62) and a.requestId=b.requestid";
			String sqlgroupby="group by id";

			int pagesize=15;


			String colString="";
				   

				   colString +="<col width=\"20%\" text=\"办理人\" column=\"requestid\" otherpara=\"column:currentnodeid\" transmethod=\"weaver.interfaces.jiangyl.sfw.SFWUtil.getLastNameByNodeIDAndRequestID\"/>"+
				 "<col width=\"25%\" text=\"部门\" column=\"requestid\" otherpara=\"column:currentnodeid\" transmethod=\"weaver.interfaces.jiangyl.sfw.SFWUtil.getDepartmentNameByNodeIDAndRequestID\"/>"+
				 "<col width=\"25%\" text=\"岗位\" column=\"nodename\"/>"+
				  "<col width=\"30%\" text=\"查看详情\" column=\"caozuo\" href=\"/proj/RequestView.jsp\" linkkey=\"requestid\" linkvaluecolumn=\"requestid\"/>";


			String tableString="<table  pagesize=\""+pagesize+"\" tabletype=\"none\">";
				   tableString+="<sql backfields=\""+backfields+"\" sqlform=\""+sqlform+"\" sqlwhere=\""+Util.toHtmlForSplitPage(sqlwhere)+"\" sqlorderby=\"\" sqlsortway=\"DESC\"  sqlprimarykey=\"a.requestid\"  sqldistinct=\"true\" />";
				   tableString+="<head>"+colString+"</head>";
				   tableString+="</table>";
		%>
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
				<wea:SplitPageTag tableString="<%=tableString%>"  mode="run"/>
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