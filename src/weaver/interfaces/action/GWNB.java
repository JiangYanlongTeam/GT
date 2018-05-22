package weaver.interfaces.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.hrm.User;
import weaver.interfaces.jiangyl.sfw.SFWUtil;

/**
 * 公文拟报
 */
public class GWNB extends BaseBean {

    public static void main(String[] args) {
        System.out.println("this is a test method ");
    }

    /**
     * 主流程获取子流程所有的签名内容
     *
     * @param user
     * @param otherparams
     * @param request
     * @param response
     * @return
     */
    public List<Map<String, String>> getDTData(User user, Map<String, String> otherparams, HttpServletRequest request,
                                               HttpServletResponse response) {

        String BMBLFORMTABLE = getPropValue("GWNB", "GWNB_DT_FORMTABLE");

        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        String requestid = Util.null2String(otherparams.get("requestid"));
        RecordSet rs = new RecordSet();
        if ("".equals(requestid)) {
            writeLog("获取requestid：" + requestid + "失败");
            return data;
        }
        String sql = "select cbdwyj1,cbdwqm1,cbdwqmrq1 from " + BMBLFORMTABLE
                + " where requestid in (select requestid from workflow_requestbase where mainrequestid = " + requestid
                + ")";
        rs.execute(sql);
        while (rs.next()) {
            String yijian = Util.null2String(rs.getString("cbdwyj1"));
            String qianming = Util.null2String(rs.getString("cbdwqm1"));
            String riqi = Util.null2String(rs.getString("cbdwqmrq1"));
            if ("".equals(yijian) && "".equals(qianming) && "".equals(riqi)) {
                continue;
            }
            Map<String, String> map = new HashMap<String, String>();
            map.put("riqi", riqi);
            map.put("yijian", yijian);
            map.put("qianming", "<img src='/weaver/weaver.file.ImgFileDownload?userid=" + qianming + "' width='80px'>");
            data.add(map);
        }
        return data;
    }

    /**
     * 子流程获取其他子流程的所有签字信息（包含自己）
     *
     * @param user
     * @param otherparams
     * @param request
     * @param response
     * @return
     */
    public List<Map<String, String>> getBrotherDTData(User user, Map<String, String> otherparams,
                                                      HttpServletRequest request, HttpServletResponse response) {

        String BMBLFORMTABLE = getPropValue("GWNB", "GWNB_DT_FORMTABLE");

        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        String requestid = Util.null2String(otherparams.get("requestid"));
        RecordSet rs = new RecordSet();
        if ("".equals(requestid)) {
            writeLog("获取requestid：" + requestid + "失败");
            return data;
        }
        String sql = "select cbdwyj1,cbdwqm1,cbdwqmrq1 from " + BMBLFORMTABLE
                + " where requestid in (select requestid from workflow_requestbase where mainrequestid in (select mainrequestid from workflow_requestbase where requestid = "
                + requestid + "))";
        rs.execute(sql);
        while (rs.next()) {
            String yijian = Util.null2String(rs.getString("cbdwyj1"));
            String qianming = Util.null2String(rs.getString("cbdwqm1"));
            String riqi = Util.null2String(rs.getString("cbdwqmrq1"));
            if ("".equals(yijian) && "".equals(qianming) && "".equals(riqi)) {
                continue;
            }
            Map<String, String> map = new HashMap<String, String>();
            map.put("riqi", riqi);
            map.put("yijian", yijian);
            map.put("qianming", "<img src='/weaver/weaver.file.ImgFileDownload?userid=" + qianming + "' width='80px'>");
            data.add(map);
        }
        return data;
    }

    /**
     * 公文拟报
     *
     * @param user
     * @param otherparams
     * @param request
     * @param response
     * @return
     */
    public List<Map<String, String>> getData(User user, Map<String, String> otherparams, HttpServletRequest request,
                                             HttpServletResponse response) {

        String GWNBTABLENAME = getPropValue("GWNB", "GWNB_DT_FORMTABLE");

        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        String requestid = Util.null2String(otherparams.get("requestid"));
        String ismobile = Util.null2String(otherparams.get("ismobile"));
        RecordSet rs = new RecordSet();
        SFWUtil sf = new SFWUtil();
        if ("".equals(requestid)) {
            writeLog("获取requestid：" + requestid + "失败");
            return data;
        }

//		String sql = " select a.requestid,b.requestname,b.currentnodeid,'查看详情' caozuo,(select nodename from WORKFLOW_NODEBASE where id = b.currentnodeid) nodename from "
//				+ GWNBTABLENAME + " a,WORKFLOW_REQUESTBASE b "
//				+ "where a.requestid = b.requestid and b.mainrequestid = " + requestid + "";
        String sql = "select a.requestid,b.requestname,b.currentnodeid,'查看详情' caozuo, c.nodename,f.departmentname from " + GWNBTABLENAME + " "
                + "a,WORKFLOW_REQUESTBASE b,WORKFLOW_NODEBASE c,hrmresource d, hrmdepartment f where b.mainrequestid = " + requestid + " "
                + "and a.requestid = b.requestid and c.id = b.currentnodeid and d.id = b.creater and d.departmentid = f.id order by f.showorder asc";
        rs.execute(sql);
        while (rs.next()) {
            String reqid = Util.null2String(rs.getString("requestid"));
            String nodename = Util.null2String(rs.getString("nodename"));
            String requestname = Util.null2String(rs.getString("requestname"));
            String currentnodeid = Util.null2String(rs.getString("currentnodeid"));
            Map<String, String> jo = new HashMap<String, String>();
            String creater = sf.getLastNameByNodeIDAndRequestID(reqid, currentnodeid);
            String depname = sf.getDepartmentNameByNodeIDAndRequestID(reqid, currentnodeid);
            String status = "";
            if ("1".equals(ismobile)) {
                status = "<a href='/client.do?method=getpage&detailid=" + reqid + "' title='" + requestname
                        + "' target='_blank' style='color:red'>查看详情</a>";

            } else {
                status = "<a href='/proj/RequestView.jsp?requestid=" + reqid + "' title='" + requestname
                        + "' target='_blank'>查看详情</a>";
            }
            jo.put("czyj", status);
            jo.put("nodename", nodename);
            jo.put("creater", creater);
            jo.put("department", depname);
            data.add(jo);
        }
        return data;
    }

    /**
     * 公文拟报会签
     *
     * @param user
     * @param otherparams
     * @param request
     * @param response
     * @return
     */
    public List<Map<String, String>> getHQData(User user, Map<String, String> otherparams, HttpServletRequest request,
                                               HttpServletResponse response) {

        String GWNBTABLENAME = getPropValue("GWNB", "GWNB_DT_FORMTABLE");

        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        String requestid = Util.null2String(otherparams.get("requestid"));
        String ismobile = Util.null2String(otherparams.get("ismobile"));
        RecordSet rs = new RecordSet();
        SFWUtil sf = new SFWUtil();
        if ("".equals(requestid)) {
            writeLog("获取requestid：" + requestid + "失败");
            return data;
        }

//		String sql = " select a.requestid,b.requestname,b.currentnodeid,'查看详情' caozuo,(select nodename from WORKFLOW_NODEBASE where id = b.currentnodeid) nodename from "
//				+ GWNBTABLENAME + " a,WORKFLOW_REQUESTBASE b "
//				+ "where a.requestId in(select requestid from workflow_requestbase  "
//				+ "where mainRequestId= (select mainRequestId from workflow_requestbase where requestid="+requestid+") and workflowid=60) and a.requestId=b.requestid";
        String sql = "select a.requestid,b.requestname,b.currentnodeid,'查看详情' caozuo, c.nodename,f.departmentname from " + GWNBTABLENAME + " "
                + "a,WORKFLOW_REQUESTBASE b,WORKFLOW_NODEBASE c,hrmresource d, hrmdepartment f where a.requestId in (select requestid from workflow_requestbase "
                + "where mainRequestId= (select mainRequestId from workflow_requestbase where requestid=" + requestid + ") and workflowid=60) and a.requestid = b.requestid and c.id = b.currentnodeid and d.id = b.creater and d.departmentid = f.id order by f.showorder asc";
        rs.execute(sql);
        while (rs.next()) {
            String reqid = Util.null2String(rs.getString("requestid"));
            String nodename = Util.null2String(rs.getString("nodename"));
            String requestname = Util.null2String(rs.getString("requestname"));
            String currentnodeid = Util.null2String(rs.getString("currentnodeid"));
            Map<String, String> jo = new HashMap<String, String>();
            String creater = sf.getLastNameByNodeIDAndRequestID(reqid, currentnodeid);
            String depname = sf.getDepartmentNameByNodeIDAndRequestID(reqid, currentnodeid);
            String status = "";
            if ("1".equals(ismobile)) {
                status = "<a href='/client.do?method=getpage&detailid=" + reqid + "' title='" + requestname
                        + "' target='_blank' style='color:red'>查看详情</a>";

            } else {
                status = "<a href='/proj/RequestView.jsp?requestid=" + reqid + "' title='" + requestname
                        + "' target='_blank'>查看详情</a>";
            }
            jo.put("czyj", status);
            jo.put("nodename", nodename);
            jo.put("creater", creater);
            jo.put("department", depname);
            data.add(jo);
        }
        return data;
    }
}
