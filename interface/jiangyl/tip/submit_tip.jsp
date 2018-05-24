<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="weaver.general.Util" %>
<%@ page import="java.util.*" %>
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="bb" class="weaver.general.BaseBean"></jsp:useBean>
<jsp:useBean id="ResourceComInfo" class="weaver.hrm.resource.ResourceComInfo" scope="page" />
<jsp:useBean id="DepartmentComInfo" class="weaver.hrm.company.DepartmentComInfo" scope="page"/>
<jsp:useBean id="SubCompanyComInfo" class="weaver.hrm.company.SubCompanyComInfo" scope="page" />
<script language="javascript" src="/wui/theme/ecology8/jquery/js/zDialog_wev8.js"></script>

<script language="javascript">
    function getdata() {
        var dlg = new window.top.Dialog(); //定义Dialog对象
        dlg.currentWindow = window;
        dlg.Model = false;
        dlg.Width = 300; //定义长度
        dlg.Height = 30;
        dlg.URL = '/interface/jiangyl/tip/cyd_tip.jsp';
        dlg.Title = "提交提示" ;
        dlg.maxiumnable = true;
        dlg.show();
        window.dialog = dlg;
    }
    checkCustomize = function () {
		getdata();
        return false;
    };
</script>
