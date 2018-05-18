package weaver.interfaces.action;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.Property;
import weaver.soa.workflow.request.RequestInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class GWNBDistinctDepartmentIDAction extends BaseBean implements Action {

    @Override
    public String execute(RequestInfo request) {
        writeLog("公文拟报案件补发");
        String requestid = request.getRequestid();//获取主流程requestid
        RecordSet rs = new RecordSet();
        String tableNamme = request.getRequestManager().getBillTableName();

        String hqbm_value = "";
        String hqbm_column = "hqcs";

        String czyj_value = "";
        String czyj_column = "czyj";

        String hqbmnq_value = "";
        String hqbmnq_column = "hqbmnq";

        String xgfj_value = "";
        String xgfj_column = "xgfj";



        Property[] properties = request.getMainTableInfo().getProperty();// 获取表单主字段信息
        for (int i = 0; i < properties.length; i++) {
            String name = properties[i].getName();// 主字段名称
            String value = Util.null2String(properties[i].getValue());// 主字段对应的值
            if (name.equals(hqbm_column)) {
                hqbm_value = value;
            }
            if (name.equals(czyj_column)) {
                czyj_value = value;
            }
            if (name.equals(hqbmnq_column)) {
                hqbmnq_value = value;
            }
            if (name.equals(xgfj_column)) {
                xgfj_value = value;
            }
        }

        Map<String, String> hqbmnqMap = new HashMap<String, String>();
        if (!"".equals(hqbmnq_value)) {
            String[] hqbmnqs = hqbmnq_value.split(",");
            for (String hqbmnq : hqbmnqs) {
                if (!"".equals(hqbmnq)) {
                    hqbmnqMap.put(hqbmnq, hqbmnq);
                }
            }
        }
        String sql1="select  creater ,requestid  from workflow_requestbase where  mainrequestid = "+requestid;
        RecordSet rs1= new RecordSet();
        rs1.execute(sql1);
        while (rs1.next()) {
            String ccRequestid = Util.null2String(rs1.getString("requestid"));
            String creater = Util.null2String(rs1.getString("creater"));
            new RecordSet().execute("update formtable_main_36 set hqbm = '"+hqbm_value+"',jbrczyj ='"+czyj_value+"',xgfj='"+xgfj_value+"' where requestid ="+ccRequestid);
                if(hqbmnqMap.containsKey(creater)) {
                    hqbmnqMap.remove(creater);
            }
        }

        StringBuffer sb = new StringBuffer(",");
        for (Entry<String, String> entry : hqbmnqMap.entrySet()) {
            sb.append(entry.getKey());
            sb.append(",");
        }
        String sbs = sb.toString();

        if (",".equals(sbs)) {
            //sbs = sbs.substring(1, sbs.length() - 1);
            String sql = "update " + tableNamme + " set hqbmnq = '' where requestid = '" + requestid + "'";
            writeLog("执行更新SQL：" + sql);
            rs.execute(sql);
        }else{
            sbs = sbs.substring(1, sbs.length() - 1);
            String sql = "update " + tableNamme + " set hqbmnq = '" + sbs + "' where requestid = '" + requestid + "'";
            writeLog("执行更新SQL：" + sql);
            rs.execute(sql);
        }
        return SUCCESS;
    }
}
