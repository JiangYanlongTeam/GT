package weaver.interfaces.service.log;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;

import java.util.Map;

public class ServiceLog extends BaseBean {

    public void insertLog(ServiceLogInfo serviceLogInfo) {
        RecordSet rs = new RecordSet();
        String prop_filename = Util.null2String(serviceLogInfo.getPROP_FILENAME());
        String prop_column_key = Util.null2String(serviceLogInfo.getPROP_COLUMN_KEY());
        String modeid = Util.null2String(getPropValue(prop_filename,prop_column_key));
        String createdate = Util.null2String(serviceLogInfo.getModedatacreatedate());
        String modedatacreatetime = Util.null2String(serviceLogInfo.getModedatacreatetime());
        String modedatacreater = Util.null2String(serviceLogInfo.getModedatacreater());
        String modedatacreatertype = Util.null2String(serviceLogInfo.getModedatacreatertype());

        StringBuilder columnStringBuffer = new StringBuilder("formmodeid,modedatacreater,modedatacreatertype,modedatacreatedate,modedatacreatetime");
        StringBuilder valueStringBuffer = new StringBuilder("'"+modeid+"','"+modedatacreater+"','"+modedatacreatertype+"','"+createdate+"','"+modedatacreatetime+"'");
        Map<String,String> columnsMap = serviceLogInfo.getMap();
        if(columnsMap.size() > 0) {
            columnStringBuffer.append(",");
            valueStringBuffer.append(",");
        }
        int count = 0;
        for(Map.Entry<String,String> entry : columnsMap.entrySet()) {
            String key = Util.null2String(entry.getKey());
            String val = Util.null2String(entry.getValue());
            columnStringBuffer.append(key);
            valueStringBuffer.append("'" + val + "'");
            if(count != columnsMap.size()-1) {
                columnStringBuffer.append(",");
                valueStringBuffer.append(",");
            }
            count++;
        }


        String tableNameSQL = "select w.tablename,w.id from modeinfo m,workflow_bill w where w.id=m.formid and m.id='"+modeid+"'";
        rs.execute(tableNameSQL);
        rs.next();
        String tableName = Util.null2String(rs.getString("tablename"));
        String tablesql = new StringBuilder().append("insert into ").append(tableName).append(" (").append(columnStringBuffer.toString()).append(" ) ").append(" values ").append(" (").append(valueStringBuffer.toString()).append(" )").toString();
        rs.execute(tablesql);
    }
}
