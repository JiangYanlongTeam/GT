package weaver.interfaces.service.doc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.service.log.ServiceLog;
import weaver.interfaces.service.log.ServiceLogInfo;

public class DocDServiceImpl extends BaseBean implements DocDService {

    @Override
    public String searchIDBySubject(String json) {
        String createdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String createtime = new SimpleDateFormat("HH:mm").format(new Date());
        String modedatacreatetime = new SimpleDateFormat("HH:mm:ss").format(new Date());
        //{"subject":"aa","typeid":"aa"}
        JSONObject jsonstr = JSON.parseObject(json);
        String subject = (String) jsonstr.get("subject");
        String typeid = (String) jsonstr.get("typeid");
        RecordSet rs = new RecordSet();

        ServiceLogInfo logInfo = new ServiceLogInfo();
        logInfo.setModedatacreatedate(createdate);
        logInfo.setModedatacreatetime(modedatacreatetime);
        logInfo.setPROP_FILENAME("gt_interface_loginfo");
        logInfo.setPROP_COLUMN_KEY("DocDServiceID");

        ServiceLog serviceLog = new ServiceLog();
        // 一周工作安排 182
        // 值班表 181
        String str = "";
        if ("gzap".equals(typeid)) {
            String sql = "select id from docdetail where seccategory = '182' and DOCSUBJECT = '" + subject + "'";
            rs.execute(sql);
            rs.next();
            String id = Util.null2String(rs.getString("id"));
            str = "{\"docid\":\"" + id + "\"}";

            Map<String, String> map = new HashMap<String, String>();
            map.put("createdate", createdate);
            map.put("createtime", createtime);
            map.put("receivemessage", json);
            map.put("returnmessage", str);
            logInfo.setMap(map);

            try {
                serviceLog.insertLog(logInfo);
            } catch (Exception e) {
                writeLog("文档DocDService接口插入接口调用日志异常：" + e.getMessage());
            }
            return str;
        }
        if ("zbb".equals(typeid)) {
            String sql = "select id from docdetail where seccategory = '181' and DOCSUBJECT = '" + subject + "'";
            rs.execute(sql);
            rs.next();
            String id = Util.null2String(rs.getString("id"));
            str = "{\"docid\":\"" + id + "\"}";

            Map<String, String> map = new HashMap<String, String>();
            map.put("createdate", createdate);
            map.put("createtime", createtime);
            map.put("receivemessage", json);
            map.put("returnmessage", str);
            logInfo.setMap(map);

            try {
                serviceLog.insertLog(logInfo);
            } catch (Exception e) {
                writeLog("文档DocDService接口插入接口调用日志异常：" + e.getMessage());
            }
            return str;
        }

        Map<String, String> map = new HashMap<String, String>();
        map.put("createdate", createdate);
        map.put("createtime", createtime);
        map.put("receivemessage", json);
        map.put("returnmessage", str);
        logInfo.setMap(map);

        try {
            serviceLog.insertLog(logInfo);
        } catch (Exception e) {
            writeLog("文档DocDService接口插入接口调用日志异常：" + e.getMessage());
        }

        str = "{\"docid\":\"\"}";
        return str;
    }
}
