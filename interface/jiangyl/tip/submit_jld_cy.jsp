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
</script>
