<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="weaver.general.Util" %>
<%@ page import="java.util.*,java.text.*" %>
<%@ page import="weaver.hrm.*" %>
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="bb" class="weaver.general.BaseBean"></jsp:useBean>
<jsp:useBean id="ResourceComInfo" class="weaver.hrm.resource.ResourceComInfo" scope="page" />
<jsp:useBean id="DepartmentComInfo" class="weaver.hrm.company.DepartmentComInfo" scope="page"/>
<jsp:useBean id="SubCompanyComInfo" class="weaver.hrm.company.SubCompanyComInfo" scope="page" />
<script language="javascript" src="/wui/theme/ecology8/jquery/js/zDialog_wev8.js"></script>

<%
	User user = HrmUserVarify.getUser (request , response) ;
    int nodeid = Util.getIntValue(request.getParameter("nodeid"),0);
    int workflowid = Util.getIntValue(request.getParameter("workflowid"),0);
	int reqid = Util.getIntValue(request.getParameter("requestid"),0);
	String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	String formidsql = "select formid formid from workflow_base where id = "+workflowid+"";
	rs.execute(formidsql);
	rs.next();
	String formid_convert = Util.null2String(rs.getString("formid"));
	weaver.interfaces.swfa.BillFieldUtil fieldUtil = new weaver.interfaces.swfa.BillFieldUtil();
 
	//将主流程里的局领导意见回写到子流程
	String bgshgid =fieldUtil.getlabelId("bgshg",Integer.parseInt(formid_convert),"0","0");//办公室核稿意见
	String bgsqmid  =fieldUtil.getlabelId("bgsqm",Integer.parseInt(formid_convert),"0","0");//办公室签名
	String bgsqmrqid  =fieldUtil.getlabelId("bgsqmrq",Integer.parseInt(formid_convert),"0","0");//办公室签名日期
	String jzyjid = fieldUtil.getlabelId("jzyj",Integer.parseInt(formid_convert),"0","0");//局长意见
	String jzqmid = fieldUtil.getlabelId("jzqm",Integer.parseInt(formid_convert),"0","0");//局长签名
	String jzqmrqid =fieldUtil.getlabelId("jzqmrq",Integer.parseInt(formid_convert),"0","0");//局长签名日期
	String fgjldyj1id  =fieldUtil.getlabelId("fgjldyj1",Integer.parseInt(formid_convert),"0","0");//分管局领导意见1
	String fgjldyj2id = fieldUtil.getlabelId("fgjldyj2",Integer.parseInt(formid_convert),"0","0");//分管局领导意见2
	String fgjldyj3id = fieldUtil.getlabelId("fgjldyj3",Integer.parseInt(formid_convert),"0","0");//分管局领导意见3
	String fgjldyj4id = fieldUtil.getlabelId("fgjldyj4",Integer.parseInt(formid_convert),"0","0");//分管局领导意见4
	String fgjldqm1id  =fieldUtil.getlabelId("fgjldqm1",Integer.parseInt(formid_convert),"0","0");//分管局领导
	String fgjldqm2id = fieldUtil.getlabelId("fgjldqm2",Integer.parseInt(formid_convert),"0","0");//分管局领导
	String fgjldqm3id = fieldUtil.getlabelId("fgjldqm3",Integer.parseInt(formid_convert),"0","0");//分管局领导
	String fgjldqm4id = fieldUtil.getlabelId("fgjldqm4",Integer.parseInt(formid_convert),"0","0");//分管局领导
	String fgjldqmrq1id  =fieldUtil.getlabelId("fgjldqmrq1",Integer.parseInt(formid_convert),"0","0");//分管局领导签名日期
	String fgjldqmrq2id = fieldUtil.getlabelId("fgjldqmrq2",Integer.parseInt(formid_convert),"0","0");//分管局领导签名日期
	String fgjldqmrq3id = fieldUtil.getlabelId("fgjldqmrq3",Integer.parseInt(formid_convert),"0","0");//分管局领导签名日期
	String fgjldqmrq4id = fieldUtil.getlabelId("fgjldqmrq4",Integer.parseInt(formid_convert),"0","0");//分管局领导签名日期

	//收文局领导意见签名回写
	String jld1id = fieldUtil.getlabelId("jld1",Integer.parseInt(formid_convert),"0","0");
	String jld2id = fieldUtil.getlabelId("jjld2",Integer.parseInt(formid_convert),"0","0");
	String jld3id = fieldUtil.getlabelId("jld3",Integer.parseInt(formid_convert),"0","0");
	String jld4id = fieldUtil.getlabelId("jld4",Integer.parseInt(formid_convert),"0","0");
	String jld5id = fieldUtil.getlabelId("jld5",Integer.parseInt(formid_convert),"0","0");
	String jld6id = fieldUtil.getlabelId("jld6",Integer.parseInt(formid_convert),"0","0");
	String jld7id = fieldUtil.getlabelId("jld7",Integer.parseInt(formid_convert),"0","0");
	String jld8id = fieldUtil.getlabelId("jld8",Integer.parseInt(formid_convert),"0","0");
	String jld9id = fieldUtil.getlabelId("jld9",Integer.parseInt(formid_convert),"0","0");
	String jld10id = fieldUtil.getlabelId("jld10",Integer.parseInt(formid_convert),"0","0");

	String jldrq1id = fieldUtil.getlabelId("jldrq1",Integer.parseInt(formid_convert),"0","0");
	String jldrq2id = fieldUtil.getlabelId("jldrq2",Integer.parseInt(formid_convert),"0","0");
	String jldrq3id = fieldUtil.getlabelId("jldrq3",Integer.parseInt(formid_convert),"0","0");
	String jldrq4id = fieldUtil.getlabelId("jldrq4",Integer.parseInt(formid_convert),"0","0");
	String jldrq5id = fieldUtil.getlabelId("jldrq5",Integer.parseInt(formid_convert),"0","0");
	String jldrq6id = fieldUtil.getlabelId("jldrq6",Integer.parseInt(formid_convert),"0","0");
	String jldrq7id = fieldUtil.getlabelId("jldrq7",Integer.parseInt(formid_convert),"0","0");
	String jldrq8id = fieldUtil.getlabelId("jldrq8",Integer.parseInt(formid_convert),"0","0");
	String jldrq9id = fieldUtil.getlabelId("jldrq9",Integer.parseInt(formid_convert),"0","0");
	String jldrq10id = fieldUtil.getlabelId("jldrq10",Integer.parseInt(formid_convert),"0","0");

	String jzyj1id = fieldUtil.getlabelId("jzyj1",Integer.parseInt(formid_convert),"0","0");
	String jzyj2id = fieldUtil.getlabelId("jzyj2",Integer.parseInt(formid_convert),"0","0");
	String jzyj3id = fieldUtil.getlabelId("jzyj3",Integer.parseInt(formid_convert),"0","0");
	String jzyj4id = fieldUtil.getlabelId("jzyj4",Integer.parseInt(formid_convert),"0","0");
	String jzyj5id = fieldUtil.getlabelId("jzyj5",Integer.parseInt(formid_convert),"0","0");
	String jzyj6id = fieldUtil.getlabelId("jzyj6",Integer.parseInt(formid_convert),"0","0");
	String jzyj7id = fieldUtil.getlabelId("jzyj7",Integer.parseInt(formid_convert),"0","0");
	String jzyj8id = fieldUtil.getlabelId("jzyj8",Integer.parseInt(formid_convert),"0","0");
	String jzyj9id = fieldUtil.getlabelId("jzyj9",Integer.parseInt(formid_convert),"0","0");
	String jzyj10id = fieldUtil.getlabelId("jzyj10",Integer.parseInt(formid_convert),"0","0");

    String jzxrid = fieldUtil.getlabelId("jzxr",Integer.parseInt(formid_convert),"0","0");
	String jzxr2id = fieldUtil.getlabelId("jzxr2",Integer.parseInt(formid_convert),"0","0");
	String jzxr3id = fieldUtil.getlabelId("jzxr3",Integer.parseInt(formid_convert),"0","0");
	String jzxr4id = fieldUtil.getlabelId("jzxr4",Integer.parseInt(formid_convert),"0","0");
	String jzxr5id = fieldUtil.getlabelId("jzxr5",Integer.parseInt(formid_convert),"0","0");
	String jzxr6id = fieldUtil.getlabelId("jzxr6",Integer.parseInt(formid_convert),"0","0");
	String jzxr7id = fieldUtil.getlabelId("jzxr7",Integer.parseInt(formid_convert),"0","0");
	String jzxr8id = fieldUtil.getlabelId("jzxr8",Integer.parseInt(formid_convert),"0","0");
	String jzxr9id = fieldUtil.getlabelId("jzxr9",Integer.parseInt(formid_convert),"0","0");
	
	String jzxbm1id = fieldUtil.getlabelId("jzxbm",Integer.parseInt(formid_convert),"0","0");
	String jzxcsid = fieldUtil.getlabelId("jzxcs",Integer.parseInt(formid_convert),"0","0");
	String jzxsydwid = fieldUtil.getlabelId("jzxsydw",Integer.parseInt(formid_convert),"0","0");
	String jzxfjid = fieldUtil.getlabelId("jzxfj",Integer.parseInt(formid_convert),"0","0");
	
	String jzxbm2id = fieldUtil.getlabelId("jzxbm2",Integer.parseInt(formid_convert),"0","0");
	String jzxbm3id = fieldUtil.getlabelId("jzxbm3",Integer.parseInt(formid_convert),"0","0");
	String jzxbm4id = fieldUtil.getlabelId("jzxbm4",Integer.parseInt(formid_convert),"0","0");
	String jzxbm5id = fieldUtil.getlabelId("jzxbm5",Integer.parseInt(formid_convert),"0","0");
	String jzxbm6id= fieldUtil.getlabelId("jzxbm6",Integer.parseInt(formid_convert),"0","0");
	String jzxbm7id = fieldUtil.getlabelId("jzxbm7",Integer.parseInt(formid_convert),"0","0");
	String jzxbm8id= fieldUtil.getlabelId("jzxbm8",Integer.parseInt(formid_convert),"0","0");
	String jzxbm9id= fieldUtil.getlabelId("jzxbm9",Integer.parseInt(formid_convert),"0","0");
	String jzxbm10id = fieldUtil.getlabelId("jzxbm10",Integer.parseInt(formid_convert),"0","0");
	int userid = user.getUID();
	rs.execute("select * from uf_boss_sign a,uf_boss_sign_dt1 b where a.id = b.mainid and a.workflowid = '"+workflowid+"' and b.nodeid = '"+nodeid+"' and sfgdr = '0'");
	while(rs.next()){
		String fieldid = Util.null2String(rs.getString("fieldid"));
		String rqid = Util.null2String(rs.getString("rqid"));
		String jld_qz = Util.null2String(rs.getString("qzfieldid"));
		String hrmid = Util.null2String(rs.getString("hrmid"));
		if(userid == Integer.parseInt(hrmid)) {
			%>
			<script>
				if(jQuery("#field<%=fieldid%>").val() != "") {
					jQuery("#field<%=fieldid%>").parent().after(jQuery("<img src='/weaver/weaver.file.ImgFileDownload?userid=<%=hrmid%>' width='80px'>"));
				}
				jQuery("#field<%=jld_qz%>span").html("<input type=\"button\" onclick=\"sign('<%=hrmid%>','<%=fieldid%>','<%=date%>','<%=rqid%>')\"class=\"e8_btn_top_first\" _disabled=\"true\" value=\"签名\" title=\"签名\" style=\"max-width: 100px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">");
			</script>
			<%
		} else {
			%>
			<script>
				if(jQuery("#field<%=fieldid%>").val() != "") {
					var _fval = jQuery("#field<%=fieldid%>").val();
					jQuery("#field<%=fieldid%>").parent().after(jQuery("<img src='/weaver/weaver.file.ImgFileDownload?userid="+_fval+"' width='80px'>"));
					jQuery("#field<%=rqid%>browser").attr("disabled","disabled");
				}
			</script>
			<%
		}
	}
	
	rs.execute("select * from uf_boss_sign a,uf_boss_sign_dt1 b where a.id = b.mainid and a.workflowid = '"+workflowid+"' and b.nodeid = '"+nodeid+"' and sfgdr = '1'");
	while(rs.next()){
		String fieldid = Util.null2String(rs.getString("fieldid"));
		String rqid = Util.null2String(rs.getString("rqid"));
		String jld_qz = Util.null2String(rs.getString("qzfieldid"));
		%>
		<script>
			if(jQuery("#field<%=fieldid%>").val() != "") {
				var _fval = jQuery("#field<%=fieldid%>").val();
				jQuery("#field<%=fieldid%>").parent().after(jQuery("<img src='/weaver/weaver.file.ImgFileDownload?userid="+_fval+"' width='80px'>"));
			}
			jQuery("#field<%=jld_qz%>span").html("<input type=\"button\" onclick=\"sign('<%=userid%>','<%=fieldid%>','<%=date%>','<%=rqid%>')\"class=\"e8_btn_top_first\" _disabled=\"true\" value=\"签名\" title=\"签名\" style=\"max-width: 100px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">");
		</script>
		<%
	}
	
	
	
%>
<script language="javascript">
    jQuery(function(){
		// 1. 获取该流程 该节点的所有人 节点 按钮位置
		
		// 2. 定位位置把非自己的已经有的值转换为img，签名按钮不可点击，日期字段按钮不可点击
		
		// 3. 定位当前人所在位置，把签名字段转换为按钮，并附加操作，把日期赋值到日期字段上
	})
	function sign(obj,obj2,obj3,obj4){
		jQuery("#field"+obj2).parent().parent().find('img').remove();
		jQuery("#field"+obj2).parent().after(jQuery("<img src='/weaver/weaver.file.ImgFileDownload?userid="+obj+"' width='80px'>"));
		jQuery("#field"+obj2).val(obj);
		jQuery("#field"+obj4).val(obj3);
		jQuery("#field"+obj4+"span").text(obj3);
		//Dialog.alert("签名成功");
	}
	function dowrite(gdtxr,ssbm, ssbm1, ssbm2, _reQID, _reQNAME, _qzyj){
	  jQuery.ajax({
		 url : '/interface/jiangyl/tip/ldpsyj.jsp',
		 data:{
			 gdtxr : gdtxr,
			 ssbm : ssbm,
			 ssbm1 : ssbm1,
			 ssbm2 : ssbm2,
			 requestid: _reQID,
			 reqName:_reQNAME,
			 qzyj:_qzyj
		 },
		 success:function(data){
		 }
	 })
	}
	checkCustomize = function(){
		var flag = false;
		var jldflag = false;
		if("<%=workflowid%>" == "61" && ("<%=nodeid%>" == "282")) {
			jldflag = true;
			var jld1value = jQuery("#field<%=jld1id%>").val();
			var jld2value = jQuery("#field<%=jld2id%>").val();
			var jld3value = jQuery("#field<%=jld3id%>").val();
			var jld4value = jQuery("#field<%=jld4id%>").val();
			var jld5value = jQuery("#field<%=jld5id%>").val();
			var jld6value = jQuery("#field<%=jld6id%>").val();
			var jld7value = jQuery("#field<%=jld7id%>").val();
			var jld8value = jQuery("#field<%=jld8id%>").val();
			var jld9value = jQuery("#field<%=jld9id%>").val();
			var jld10value = jQuery("#field<%=jld10id%>").val();

			var jldrq1value = jQuery("#field<%=jldrq1id%>").val();
			var jldrq2value = jQuery("#field<%=jldrq2id%>").val();
			var jldrq3value = jQuery("#field<%=jldrq3id%>").val();
			var jldrq4value = jQuery("#field<%=jldrq4id%>").val();
			var jldrq5value = jQuery("#field<%=jldrq5id%>").val();
			var jldrq6value = jQuery("#field<%=jldrq6id%>").val();
			var jldrq7value = jQuery("#field<%=jldrq7id%>").val();
			var jldrq8value = jQuery("#field<%=jldrq8id%>").val();
			var jldrq9value = jQuery("#field<%=jldrq9id%>").val();
			var jldrq10value = jQuery("#field<%=jldrq10id%>").val();

			var jzyj1value = jQuery("#field<%=jzyj1id%>").val();
			var jzyj2value = jQuery("#field<%=jzyj2id%>").val();
			var jzyj3value = jQuery("#field<%=jzyj3id%>").val();
			var jzyj4value = jQuery("#field<%=jzyj4id%>").val();
			var jzyj5value = jQuery("#field<%=jzyj5id%>").val();
			var jzyj6value = jQuery("#field<%=jzyj6id%>").val();
			var jzyj7value = jQuery("#field<%=jzyj7id%>").val();
			var jzyj8value = jQuery("#field<%=jzyj8id%>").val();
			var jzyj9value = jQuery("#field<%=jzyj9id%>").val();
			var jzyj10value = jQuery("#field<%=jzyj10id%>").val();

			var jzxrvalue = jQuery("#field<%=jzxrid%>").val();
			var jzxr2value = jQuery("#field<%=jzxr2id%>").val();
			var jzxr3value = jQuery("#field<%=jzxr3id%>").val();
			var jzxr4value = jQuery("#field<%=jzxr4id%>").val();
			var jzxr5value = jQuery("#field<%=jzxr5id%>").val();
			var jzxr6value = jQuery("#field<%=jzxr6id%>").val();
			var jzxr7value = jQuery("#field<%=jzxr7id%>").val();
			var jzxr8value = jQuery("#field<%=jzxr8id%>").val();
			var jzxr9value = jQuery("#field<%=jzxr9id%>").val();
			
			var jzxcsvalue = jQuery("#field<%=jzxcsid%>").val();
			var jzxsydwvalue = jQuery("#field<%=jzxsydwid%>").val();
			var jzxfjvalue = jQuery("#field<%=jzxfjid%>").val();
			
			var jzxbm1value = jQuery("#field<%=jzxbm1id%>").val();
			var jzxbm2value = jQuery("#field<%=jzxbm2id%>").val();
			var jzxbm3value = jQuery("#field<%=jzxbm3id%>").val();
			var jzxbm4value = jQuery("#field<%=jzxbm4id%>").val();
			var jzxbm5value = jQuery("#field<%=jzxbm5id%>").val();
			var jzxbm6value = jQuery("#field<%=jzxbm6id%>").val();
			var jzxbm7value = jQuery("#field<%=jzxbm7id%>").val();
			var jzxbm8value = jQuery("#field<%=jzxbm8id%>").val();
			var jzxbm9value = jQuery("#field<%=jzxbm9id%>").val();
			var jzxbm10value = jQuery("#field<%=jzxbm10id%>").val();

			var requestid = jQuery("#requestid").val();
			jQuery.ajax({
				url:'/interface/jiangyl/tip/updatejld.jsp',
				data : {
					jld1 : jld1value,
					jld2 : jld2value,
					jld3 : jld3value,
					jld4 : jld4value,
					jld5 : jld5value,
					jld6 : jld6value,
					jld7 : jld7value,
					jld8 : jld8value,
					jld9 : jld9value,
					jld10 : jld10value,

					jldrq1 : jldrq1value,
					jldrq2 : jldrq2value,
					jldrq3 : jldrq3value,
					jldrq4 : jldrq4value,
					jldrq5 : jldrq5value,
					jldrq6 : jldrq6value,
					jldrq7 : jldrq7value,
					jldrq8 : jldrq8value,
					jldrq9 : jldrq9value,
					jldrq10 : jldrq10value,
					
					jzyj1 : jzyj1value,
					jzyj2 : jzyj2value,
					jzyj3 : jzyj3value,
					jzyj4 : jzyj4value,
					jzyj5 : jzyj5value,
					jzyj6 : jzyj6value,
					jzyj7 : jzyj7value,
					jzyj8 : jzyj8value,
					jzyj9 : jzyj9value,
					jzyj10 : jzyj10value,

					jzxr : jzxrvalue,
					jzxr2 : jzxr2value,
					jzxr3 : jzxr3value,
					jzxr4 : jzxr4value,
					jzxr5 : jzxr5value,
					jzxr6 : jzxr6value,
					jzxr7 : jzxr7value,
					jzxr8 : jzxr8value,
					jzxr9 : jzxr9value,
					
					jzxcs : jzxcsvalue,
					jzxsydw : jzxsydwvalue,
					jzxfj : jzxfjvalue,
					
					jzxbm1 : jzxbm1value,
					jzxbm2 : jzxbm2value,
					jzxbm3 : jzxbm3value,
					jzxbm4 : jzxbm4value,
					jzxbm5 : jzxbm5value,
					jzxbm6 : jzxbm6value,
					jzxbm7 : jzxbm7value,
					jzxbm8 : jzxbm8value,
					jzxbm9 : jzxbm9value,
					jzxbm10 : jzxbm10value,
					requestid : requestid
				},     
				type:'post',
				async: false,
				dataType:'json',
				cache:'false',
				success:function(data) {
					flag = true;
				}
			});
		}
		if("<%=workflowid%>" == "61" && ("<%=nodeid%>" == "282")) {
			 var gdtxr = "";
			 var ssbm = "";
			 var ssbm1= "";
			 var ssbm2= "";
			 var _qzyj = "";
			 var _reQID = jQuery("#requestid").val();
			 var _reQNAME = jQuery("input[name=requestname]").val();
			 if(window.__userid == "531") {
				 gdtxr = jQuery("#field8101").val();
				 ssbm = jQuery("#field10801").val();
				 ssbm1 = jQuery("#field10802").val();
				 ssbm2 = jQuery("#field10803").val();
				 _qzyj = jQuery("#field8065").val();
			 }
			 if(window.__userid == "533") {
				 gdtxr = jQuery("#field10804").val();
				 ssbm = jQuery("#field8103").val();
				 _qzyj = jQuery("#field8066").val();
			 }
			 if(window.__userid == "535") {
				 gdtxr = jQuery("#field10805").val();
				 ssbm = jQuery("#field8104").val();
				 _qzyj = jQuery("#field8067").val();
			 }
			 if(window.__userid == "536") {
				 gdtxr = jQuery("#field10806").val();
				 ssbm = jQuery("#field8105").val();
				 _qzyj = jQuery("#field8068").val();
			 }
			 if(window.__userid == "537") {
				 gdtxr = jQuery("#field10807").val();
				 ssbm = jQuery("#field8106").val();
				 _qzyj = jQuery("#field8069").val();
			 }
			 if(window.__userid == "532") {
				 gdtxr = jQuery("#field10808").val();
				 ssbm = jQuery("#field8107").val();
				 _qzyj = jQuery("#field8070").val();
			 }
			 if(window.__userid == "540") {
				 gdtxr = jQuery("#field10809").val();
				 ssbm = jQuery("#field8108").val();
				 _qzyj = jQuery("#field8071").val();
			 }
			 if(window.__userid == "534") {
				 gdtxr = jQuery("#field10810").val();
				 ssbm = jQuery("#field8109").val();
				 _qzyj = jQuery("#field8072").val();
			 }
			 if(window.__userid == "539") {
				 gdtxr = jQuery("#field10811").val();
				 ssbm = jQuery("#field8110").val();
				 _qzyj = jQuery("#field8073").val();
			 }
			 dowrite(gdtxr, ssbm, ssbm1, ssbm2, _reQID, _reQNAME, _qzyj);
			 flag = true;
		}
		if("<%=workflowid%>" == "201" && ("<%=nodeid%>" == "424")) {
			 var gdtxr = "";
			 var ssbm = "";
			 var ssbm1 = "";
			 var ssbm2 = "";
			 var _qzyj = "";
			 var _reQID = jQuery("#requestid").val();
			 var _reQNAME = jQuery("input[name=requestname]").val();
			 if(window.__userid == "531") {
				 gdtxr = jQuery("#field9154").val();
				 ssbm = jQuery("#field10832").val();
				 ssbm1 = jQuery("#field10833").val();
				 ssbm2 = jQuery("#field10834").val();
				 _qzyj = jQuery("#field9118").val();
			 }
			 if(window.__userid == "533") {
				 gdtxr = jQuery("#field10835").val();
				 ssbm = jQuery("#field9156").val();
				 _qzyj = jQuery("#field9119").val();
			 }
			 if(window.__userid == "535") {
				 gdtxr = jQuery("#field10836").val();
				 ssbm = jQuery("#field9157").val();
				 _qzyj = jQuery("#field9120").val();
			 }
			 if(window.__userid == "536") {
				 gdtxr = jQuery("#field10837").val();
				 ssbm = jQuery("#field9158").val();
				 _qzyj = jQuery("#field9121").val();
			 }
			 if(window.__userid == "537") {
				 gdtxr = jQuery("#field10838").val();
				 ssbm = jQuery("#field9159").val();
				 _qzyj = jQuery("#field9122").val();
			 }
			 if(window.__userid == "532") {
				 gdtxr = jQuery("#field10839").val();
				 ssbm = jQuery("#field9160").val();
				 _qzyj = jQuery("#field9123").val();
			 }
			 if(window.__userid == "540") {
				 gdtxr = jQuery("#field10840").val();
				 ssbm = jQuery("#field9161").val();
				 _qzyj = jQuery("#field9124").val();
			 }
			 if(window.__userid == "534") {
				 gdtxr = jQuery("#field10841").val();
				 ssbm = jQuery("#field9162").val();
				 _qzyj = jQuery("#field9125").val();
			 }
			 if(window.__userid == "539") {
				 gdtxr = jQuery("#field10842").val();
				 ssbm = jQuery("#field9163").val();
				 _qzyj = jQuery("#field9126").val();
			 }
			 dowrite(gdtxr, ssbm, ssbm1, ssbm2, _reQID, _reQNAME, _qzyj);
			 flag = true;
		}
		if("<%=workflowid%>" == "201" && ("<%=nodeid%>" == "424")) {
			jldflag = true;
			var jld1value = jQuery("#field<%=jld1id%>").val();
			var jld2value = jQuery("#field<%=jld2id%>").val();
			var jld3value = jQuery("#field<%=jld3id%>").val();
			var jld4value = jQuery("#field<%=jld4id%>").val();
			var jld5value = jQuery("#field<%=jld5id%>").val();
			var jld6value = jQuery("#field<%=jld6id%>").val();
			var jld7value = jQuery("#field<%=jld7id%>").val();
			var jld8value = jQuery("#field<%=jld8id%>").val();
			var jld9value = jQuery("#field<%=jld9id%>").val();
			var jld10value = jQuery("#field<%=jld10id%>").val();

			var jldrq1value = jQuery("#field<%=jldrq1id%>").val();
			var jldrq2value = jQuery("#field<%=jldrq2id%>").val();
			var jldrq3value = jQuery("#field<%=jldrq3id%>").val();
			var jldrq4value = jQuery("#field<%=jldrq4id%>").val();
			var jldrq5value = jQuery("#field<%=jldrq5id%>").val();
			var jldrq6value = jQuery("#field<%=jldrq6id%>").val();
			var jldrq7value = jQuery("#field<%=jldrq7id%>").val();
			var jldrq8value = jQuery("#field<%=jldrq8id%>").val();
			var jldrq9value = jQuery("#field<%=jldrq9id%>").val();
			var jldrq10value = jQuery("#field<%=jldrq10id%>").val();

			var jzyj1value = jQuery("#field<%=jzyj1id%>").val();
			var jzyj2value = jQuery("#field<%=jzyj2id%>").val();
			var jzyj3value = jQuery("#field<%=jzyj3id%>").val();
			var jzyj4value = jQuery("#field<%=jzyj4id%>").val();
			var jzyj5value = jQuery("#field<%=jzyj5id%>").val();
			var jzyj6value = jQuery("#field<%=jzyj6id%>").val();
			var jzyj7value = jQuery("#field<%=jzyj7id%>").val();
			var jzyj8value = jQuery("#field<%=jzyj8id%>").val();
			var jzyj9value = jQuery("#field<%=jzyj9id%>").val();
			var jzyj10value = jQuery("#field<%=jzyj10id%>").val();

			var jzxrvalue = jQuery("#field<%=jzxrid%>").val();
			var jzxr2value = jQuery("#field<%=jzxr2id%>").val();
			var jzxr3value = jQuery("#field<%=jzxr3id%>").val();
			var jzxr4value = jQuery("#field<%=jzxr4id%>").val();
			var jzxr5value = jQuery("#field<%=jzxr5id%>").val();
			var jzxr6value = jQuery("#field<%=jzxr6id%>").val();
			var jzxr7value = jQuery("#field<%=jzxr7id%>").val();
			var jzxr8value = jQuery("#field<%=jzxr8id%>").val();
			var jzxr9value = jQuery("#field<%=jzxr9id%>").val();
			
			var jzxcsvalue = jQuery("#field<%=jzxcsid%>").val();
			var jzxsydwvalue = jQuery("#field<%=jzxsydwid%>").val();
			var jzxfjvalue = jQuery("#field<%=jzxfjid%>").val();
			
			var jzxbm1value = jQuery("#field<%=jzxbm1id%>").val();
			var jzxbm2value = jQuery("#field<%=jzxbm2id%>").val();
			var jzxbm3value = jQuery("#field<%=jzxbm3id%>").val();
			var jzxbm4value = jQuery("#field<%=jzxbm4id%>").val();
			var jzxbm5value = jQuery("#field<%=jzxbm5id%>").val();
			var jzxbm6value = jQuery("#field<%=jzxbm6id%>").val();
			var jzxbm7value = jQuery("#field<%=jzxbm7id%>").val();
			var jzxbm8value = jQuery("#field<%=jzxbm8id%>").val();
			var jzxbm9value = jQuery("#field<%=jzxbm9id%>").val();
			var jzxbm10value = jQuery("#field<%=jzxbm10id%>").val();

			var requestid = jQuery("#requestid").val();
			jQuery.ajax({
				url:'/interface/jiangyl/tip/update_hytzjld.jsp',
				data : {
					jld1 : jld1value,
					jld2 : jld2value,
					jld3 : jld3value,
					jld4 : jld4value,
					jld5 : jld5value,
					jld6 : jld6value,
					jld7 : jld7value,
					jld8 : jld8value,
					jld9 : jld9value,
					jld10 : jld10value,

					jldrq1 : jldrq1value,
					jldrq2 : jldrq2value,
					jldrq3 : jldrq3value,
					jldrq4 : jldrq4value,
					jldrq5 : jldrq5value,
					jldrq6 : jldrq6value,
					jldrq7 : jldrq7value,
					jldrq8 : jldrq8value,
					jldrq9 : jldrq9value,
					jldrq10 : jldrq10value,
					
					jzyj1 : jzyj1value,
					jzyj2 : jzyj2value,
					jzyj3 : jzyj3value,
					jzyj4 : jzyj4value,
					jzyj5 : jzyj5value,
					jzyj6 : jzyj6value,
					jzyj7 : jzyj7value,
					jzyj8 : jzyj8value,
					jzyj9 : jzyj9value,
					jzyj10 : jzyj10value,

					jzxr : jzxrvalue,
					jzxr2 : jzxr2value,
					jzxr3 : jzxr3value,
					jzxr4 : jzxr4value,
					jzxr5 : jzxr5value,
					jzxr6 : jzxr6value,
					jzxr7 : jzxr7value,
					jzxr8 : jzxr8value,
					jzxr9 : jzxr9value,
					
					jzxbm1 : jzxbm1value,
					jzxcs : jzxcsvalue,
					jzxsydw : jzxsydwvalue,
					jzxfj : jzxfjvalue,
					
					jzxbm2 : jzxbm2value,
					jzxbm3 : jzxbm3value,
					jzxbm4 : jzxbm4value,
					jzxbm5 : jzxbm5value,
					jzxbm6 : jzxbm6value,
					jzxbm7 : jzxbm7value,
					jzxbm8 : jzxbm8value,
					jzxbm9 : jzxbm9value,
					jzxbm10 : jzxbm10value,
					requestid : requestid
				},     
				type:'post',
				async: false,
				dataType:'json',
				cache:'false',
				success:function(data) {
					flag = true;
				}
			});
		} 
		

		if(jldflag) {
			if(flag) {
				if ("<%=workflowid%>" == "61"){
					if (confirm("是否确认提交机要室整理？")){
						return true;
					} else {
						return false;
					}
				}

				if ("<%=workflowid%>" == "201"){
					if (confirm("是否确认提交机要室整理？")){
						return true;
					} else {
						return false;
					}
				}
			} else {
				return false;
			}
		}
		return true;
	}
</script>
