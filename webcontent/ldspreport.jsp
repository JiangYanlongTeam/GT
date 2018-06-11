<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ include file="/systeminfo/init_wev8.jsp" %>
<%@ taglib uri="/WEB-INF/weaver.tld" prefix="wea" %>
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page"/>
<jsp:useBean id="rs1" class="weaver.conn.RecordSet" scope="page"/>
<jsp:useBean id="SubCompanyComInfo" class="weaver.hrm.company.SubCompanyComInfo" scope="page"/>
<jsp:useBean id="DepartmentComInfo" class="weaver.hrm.company.DepartmentComInfo" scope="page"/>
<jsp:useBean id="ResourceComInfo" class="weaver.hrm.resource.ResourceComInfo" scope="page"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>领导审批记录</title>
    <link rel="stylesheet" href="layui/css/layui.css" media="all">
</head>
<body>
<%@ include file="/systeminfo/TopTitle_wev8.jsp" %>
<%@ include file="/systeminfo/RightClickMenuConent_wev8.jsp" %>
<%
    RCMenu += "{查询,javascript:submitData(),_self} " ;
    RCMenuHeight += RCMenuHeightStep ;
    RCMenu += "{导出,javascript:exportexcel(),_self} " ;
    RCMenuHeight += RCMenuHeightStep ;

%>
<%@ include file="/systeminfo/RightClickMenu_wev8.jsp" %>
<table class=ViewForm style="margin-left: 0.4%;">
    <colgroup>
        <col width="30%">
        <col width="70%">
        <tr>
            <td>流程标题</td>
            <td class=Field>
            </td>
        </tr>
        <TR class=Spacing><TD class=Line colSpan=4></TD></TR>
        <tr>
            <td>流程编号</td>
            <td class=Field>
            </td>
        </tr>
        <TR class=Spacing><TD class=Line colSpan=4></TD></TR>
</table>
<div id="pTable" style="width: 100%;">
    <table class="layui-table" id="layui_table_id" lay-filter="test">
    </table>
    <div id="laypage"></div>
</div>
<script type="text/javascript" src="jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="jquery.table.rowspan.js"></script>
<script src="layui/layui.js"></script>
<script>
    var limitcount = 10;
    var curnum = 1;
    function submitData(){
        productsearch(curnum,limitcount);
    }

    //列表查询方法
    function productsearch(start, limitsize) {
        layui.use(['table', 'laypage'], function () {
            var table = layui.table, laypage = layui.laypage;
            table.render({
                elem: '#layui_table_id'
                , url: '/LayUI.do'
                , loading: true
                , where: {page: start, limit: limitsize} //传参
                , cols: [[ //表头
                    {field: 'departmentname', title: '部门名称', width: '25%'}
                    , {field: 'lastname', title: '姓名', width: '25%'}
                    , {field: 'depid', title: '部门id', width: '25%'}
                    , {field: 'hrmid', title: '人员id', width: '25%'}
                ]]
                , page: false
                , height: 'full-400'
                , done: function (res, curr, count) {
                    //如果是异步请求数据方式，res即为你接口返回的信息。
                    //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
                    laypage.render({
                        elem: 'laypage'
                        , count: count
                        , curr: curnum
                        , limit: limitcount
                        , limits: [10, 15, 20, 25, 50]
                        , layout: ['prev', 'page', 'next', 'skip', 'count', 'limit']
                        , jump: function (obj, first) {
                            if (!first) {
                                curnum = obj.curr;
                                limitcount = obj.limit;
                                //console.log("curnum"+curnum);
                                //console.log("limitcount"+limitcount);
                                //layer.msg(curnum+"-"+limitcount);
                                productsearch(curnum, limitcount);
                            }
                            $(".layui-table").rowspan(0);
                        }
                    })
                }
            })
        });
    }

    productsearch(curnum, limitcount);
</script>
</body>
</html>