<%@ page language="java" contentType="text/html; charset=UTF-8" %> 
<%@ page import="weaver.general.Util" %>
<%@ page import="java.util.*" %>
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="rs1" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="rs2" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="mysmt" class="weaver.conn.RecordSet"/>
<jsp:useBean id="bb" class="weaver.general.BaseBean"></jsp:useBean>
<jsp:useBean id="ResourceComInfo" class="weaver.hrm.resource.ResourceComInfo" scope="page" />
<jsp:useBean id="DepartmentComInfo" class="weaver.hrm.company.DepartmentComInfo" scope="page"/>
<jsp:useBean id="SubCompanyComInfo" class="weaver.hrm.company.SubCompanyComInfo" scope="page" />

<!-- script代码，如果需要引用js文件，请使用与HTML中相同的方式。 -->
<script type="text/javascript">
jQuery(document).ready(function(){
	ChangeValue();
	SetValue();
	jQuery(".addbtn_p").attr("onclick","addRow0(0);dostr();return false;")
	
});
function dostr(){
	var indexnum = parseInt(jQuery("#indexnum0").val());
	//alert(indexnum);
	jQuery("#field8649_"+indexnum).bindPropertyChange(function(e){
		//var zw = jQuery("#field8649_"+indexnum).val();
		alert(indexnum);
	})
}
function ChangeValue(){
	var indexnum = parseInt(jQuery("#indexnum0").val());
	if(indexnum > 0){
		var numtotal = indexnum - parseInt("1");
		for(var j = 0; j <= numtotal; j++){
			jQuery("#field8649_" + "_" + j + "").bind("change",{key:j},function(event){
				var thisindex = event.data.key;
				var zw = jQuery("#field8649_"+thisindex).val();
				alert(zw);
            })
		}
	}
}	
function SetValue(){
	//alert(11);
}
</script>
