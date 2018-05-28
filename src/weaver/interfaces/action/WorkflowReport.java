package weaver.interfaces.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weaver.general.BaseBean;

import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.hrm.User;
import weaver.hrm.resource.ResourceComInfo;
/**
 *by yangw at 20180523
 *主要是修改如果type是固定资产且，人员是魏雪清和吴晓海时调用查询方法。
 */
public class WorkflowReport extends BaseBean{

    public List<Map<String, String>> report(User user, Map<String, String> otherparams, HttpServletRequest request,
                                            HttpServletResponse response) {

        int userid = user.getUID();
        String type = Util.null2String(otherparams.get("type"));
        String workflowids = Util.null2String(otherparams.get("workflowids"));
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        boolean isExist = existInRole(user.getUID()+"");

        if ("db".equals(type)) {
            return db_report(workflowids, userid);
        }
        if ("yb".equals(type)) {
            return yb_report(workflowids, userid);
        }
        if ("bj".equals(type)) {
            return bj_report(workflowids, userid);
        }
        if ("allgdzc".equals(type)&&isExist) {
            return all_reportGdzc(workflowids);
        }
        return list;
    }

    private List<Map<String, String>> db_report(String workflowids, int userid) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        String sql = "select distinct t2.viewtype,t4.nodename,t3.workflowname,t1.createdate,t1.createtime,t1.creater,t1.currentnodeid,t1.currentnodetype, "
                + "  t1.lastoperator,t1.creatertype,t1.lastoperatortype,t1.lastoperatedate, "
                + "  t1.lastoperatetime,t1.requestid,t1.requestname,t1.requestlevel,t1.workflowid,t2.receivedate,t2.receivetime "
                + "  from workflow_requestbase t1,workflow_currentoperator t2, workflow_base t3,workflow_nodebase t4 where t4.id = t1.currentnodeid and t1.workflowid = t3.id and t1.requestid=t2.requestid "
                + "and t2.usertype = 0 and t2.userid = " + userid + " and t1.workflowid in (" + workflowids
                + ") and t2.isremark in( '0','1','5','7') and t2.islasttimes=1 order by t2.receivedate desc,t2.receivetime desc ";
        RecordSet rs = new RecordSet();
        rs.execute(sql);
        while (rs.next()) {
            Map<String, String> map = new HashMap<String, String>();
            String requestid = Util.null2String(rs.getString("requestid"));
            String requestname = Util.null2String(rs.getString("requestname"));
            String workflowname = Util.null2String(rs.getString("workflowname"));
            String receivedate = Util.null2String(rs.getString("receivedate"));
            String receivetime = Util.null2String(rs.getString("receivetime"));
            String beforeUser = getBeforeUser(requestid);
            String nodename = Util.null2String(rs.getString("nodename"));
            String viewtype = Util.null2String(rs.getString("viewtype"));
            String requestnameString = "";
            if("0".equals(viewtype)) {
                requestnameString = "<a href=\"javaScript:openFullWindowHaveBarForWF('/workflow/request/ViewRequest.jsp?requestid="+requestid+"&isovertime=0','"+requestid+"')\">"
                        + "<span class=\"reqname\" style=\"font-weight:bold\">"+requestname+"</span>"
                        + "<img id=\"wflist_"+requestid+"img\" title=\"新到达流程\" class=\"wfremindimg\" src=\"/images/ecology8/statusicon/BDNew_wev8.png\" align=\"absbottom\">"
                        + "</a>";
            } else {
                requestnameString = "<a href=\"javaScript:openFullWindowHaveBarForWF('/workflow/request/ViewRequest.jsp?requestid="+requestid+"&isovertime=0','"+requestid+"')\">"
                        + "<span class=\"reqname\">"+requestname+"</span>"
                        + "<img id=\"wflist_"+requestid+"img\" title=\"新到达流程\" class=\"wfremindimg\" src=\"/images/ecology8/statusicon/BDNew_wev8.png\" align=\"absbottom\" style=\"display: none;\">"
                        + "</a>";
            }
            map.put("requestid", requestid);
            map.put("requestname", requestnameString);
            map.put("workflowname", workflowname);
            map.put("receivedatetime", receivedate + " " + receivetime);
            map.put("beforeuser", beforeUser);
            map.put("nodename", nodename);
            list.add(map);
        }
        return list;
    }

    private List<Map<String, String>> yb_report(String workflowids, int userid) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        String sql = "select distinct t2.viewtype,t4.nodename,t3.workflowname,t1.createdate,t1.createtime,t1.creater,t1.currentnodeid,t1.currentnodetype,t1.lastoperator,t1.creatertype,t1.lastoperatortype,t1.lastoperatedate,t1.lastoperatetime,t1.requestid,t1.requestname,t1.requestlevel,t1.workflowid,t2.receivedate,t2.receivetime "
                + "from workflow_requestbase t1,workflow_currentoperator t2, workflow_base t3, workflow_nodebase t4 "
                + "where t3.id = t1.workflowid and t1.CURRENTNODEID = t4.id and t1.workflowid in (" + workflowids
                + ") and t1.requestid=t2.requestid and t2.usertype = 0 and t2.userid = 86 and t1.creater = "+userid+" and t1.creatertype = 0 and (t1.deleted=0 or t1.deleted is null) and t2.islasttimes=1 order by t2.RECEIVEDATE desc, t2.RECEIVETIME desc";
        RecordSet rs = new RecordSet();
        rs.execute(sql);
        while (rs.next()) {
            Map<String, String> map = new HashMap<String, String>();
            String requestid = Util.null2String(rs.getString("requestid"));
            String requestname = Util.null2String(rs.getString("requestname"));
            String workflowname = Util.null2String(rs.getString("workflowname"));
            String receivedate = Util.null2String(rs.getString("receivedate"));
            String receivetime = Util.null2String(rs.getString("receivetime"));
            String beforeUser = getBeforeUser(requestid);
            String nodename = Util.null2String(rs.getString("nodename"));
            String viewtype = Util.null2String(rs.getString("viewtype"));
            String requestnameString = "";
            if("0".equals(viewtype)) {
                requestnameString = "<a href=\"javaScript:openFullWindowHaveBarForWF('/workflow/request/ViewRequest.jsp?requestid="+requestid+"&isovertime=0','"+requestid+"')\">"
                        + "<span class=\"reqname\" style=\"font-weight:bold\">"+requestname+"</span>"
                        + "<img id=\"wflist_"+requestid+"img\" title=\"新到达流程\" class=\"wfremindimg\" src=\"/images/ecology8/statusicon/BDNew_wev8.png\" align=\"absbottom\">"
                        + "</a>";
            } else {
                requestnameString = "<a href=\"javaScript:openFullWindowHaveBarForWF('/workflow/request/ViewRequest.jsp?requestid="+requestid+"&isovertime=0','"+requestid+"')\">"
                        + "<span class=\"reqname\">"+requestname+"</span>"
                        + "<img id=\"wflist_"+requestid+"img\" title=\"新到达流程\" class=\"wfremindimg\" src=\"/images/ecology8/statusicon/BDNew_wev8.png\" align=\"absbottom\" style=\"display: none;\">"
                        + "</a>";
            }
            map.put("requestid", requestid);
            map.put("requestname", requestnameString);
            map.put("workflowname", workflowname);
            map.put("receivedatetime", receivedate + " " + receivetime);
            map.put("beforeuser", beforeUser);
            map.put("nodename", nodename);
            list.add(map);
        }
        return list;

    }

    private List<Map<String, String>> bj_report(String workflowids, int userid) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        String sql = "select distinct t2.viewtype,t4.nodename,t3.workflowname,t1.createdate,t1.createtime,t1.creater,t1.currentnodeid,t1.currentnodetype,t1.lastoperator,t1.creatertype,t1.lastoperatortype,t1.lastoperatedate,t1.lastoperatetime,t1.requestid,t1.requestname,t1.requestlevel,t1.workflowid,t2.receivedate,t2.receivetime "
                + "from workflow_requestbase t1,workflow_currentoperator t2, workflow_base t3, workflow_nodebase t4 "
                + "where t3.id = t1.workflowid and t1.CURRENTNODEID = t4.id and t1.requestid=t2.requestid and t2.usertype = 0 and t2.userid = "
                + userid + " and t1.workflowid in (" + workflowids
                + ") and t2.isremark in('2','4') and t1.currentnodetype = '3' and iscomplete=1 and islasttimes=1 order by t2.RECEIVEDATE desc, t2.RECEIVETIME desc";
        RecordSet rs = new RecordSet();
        rs.execute(sql);
        while (rs.next()) {
            Map<String, String> map = new HashMap<String, String>();
            String requestid = Util.null2String(rs.getString("requestid"));
            String requestname = Util.null2String(rs.getString("requestname"));
            String workflowname = Util.null2String(rs.getString("workflowname"));
            String receivedate = Util.null2String(rs.getString("receivedate"));
            String receivetime = Util.null2String(rs.getString("receivetime"));
            String beforeUser = getBeforeUser(requestid);
            String nodename = Util.null2String(rs.getString("nodename"));
            String viewtype = Util.null2String(rs.getString("viewtype"));
            String requestnameString = "";
            if("0".equals(viewtype)) {
                requestnameString = "<a href=\"javaScript:openFullWindowHaveBarForWF('/workflow/request/ViewRequest.jsp?requestid="+requestid+"&isovertime=0','"+requestid+"')\">"
                        + "<span class=\"reqname\" style=\"font-weight:bold\">"+requestname+"</span>"
                        + "<img id=\"wflist_"+requestid+"img\" title=\"新到达流程\" class=\"wfremindimg\" src=\"/images/ecology8/statusicon/BDNew_wev8.png\" align=\"absbottom\">"
                        + "</a>";
            } else {
                requestnameString = "<a href=\"javaScript:openFullWindowHaveBarForWF('/workflow/request/ViewRequest.jsp?requestid="+requestid+"&isovertime=0','"+requestid+"')\">"
                        + "<span class=\"reqname\">"+requestname+"</span>"
                        + "<img id=\"wflist_"+requestid+"img\" title=\"新到达流程\" class=\"wfremindimg\" src=\"/images/ecology8/statusicon/BDNew_wev8.png\" align=\"absbottom\" style=\"display: none;\">"
                        + "</a>";
            }
            map.put("requestid", requestid);
            map.put("requestname", requestnameString);
            map.put("workflowname", workflowname);
            map.put("receivedatetime", receivedate + " " + receivetime);
            map.put("beforeuser", beforeUser);
            map.put("nodename", nodename);
            list.add(map);
        }
        return list;
    }

    private List<Map<String, String>> all_reportGdzc(String workflowids) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        String sql ="select t1.requestid ,t1.requestname,t1.creater,t2.nodename,t3.workflowname "
                + " from WORKFLOW_REQUESTBASE t1,workflow_nodebase t2,workflow_base t3  where t1.WORKFLOWID in ("+workflowids+") " +
                "and t1.CURRENTNODEID = t2.id and t1.workflowid=t3.id ";
        RecordSet rs = new RecordSet();
        rs.execute(sql);
        writeLog("我的查询：" + sql);
        while (rs.next()) {
            Map<String, String> map = new HashMap<String, String>();
            String requestid = Util.null2String(rs.getString("requestid"));
            String requestname = Util.null2String(rs.getString("requestname"));
            String workflowname = Util.null2String(rs.getString("workflowname"));
            //String receivedate = Util.null2String(rs.getString("receivedate"));
            //String receivetime = Util.null2String(rs.getString("receivetime"));
            String beforeUser = getBeforeUser(requestid);
            String nodename = Util.null2String(rs.getString("nodename"));
            String requestnameString = "<a href=\"javaScript:openFullWindowHaveBarForWF('/proj/RequestView.jsp?requestid="+requestid+"','"+requestid+"')\">"
                    + "<span class=\"reqname\">"+requestname+"</span>"
                    + "<img id=\"wflist_"+requestid+"img\" title=\"新到达流程\" class=\"wfremindimg\" src=\"/images/ecology8/statusicon/BDNew_wev8.png\" align=\"absbottom\" style=\"display: none;\">"
                    + "</a>";
            map.put("requestid", requestid);
            map.put("requestname", requestnameString);
            map.put("workflowname", workflowname);
            map.put("receivedatetime", "");
            map.put("beforeuser", beforeUser);
            map.put("nodename", nodename);
            list.add(map);
        }
        return list;
    }

    private String getBeforeUser(String requestid) {
        RecordSet rs = new RecordSet();
        String sql = "select a.userid,b.CURRENTNODETYPE from WORKFLOW_CURRENTOPERATOR a, WORKFLOW_REQUESTBASE b "
                + "where a.requestid = b.requestid and a.requestid = " + requestid
                + " and a.nodeid = b.lastnodeid order by OPERATEDATE desc,OPERATEDATE desc ";
        rs.execute(sql);
        rs.next();
        String userid = Util.null2String(rs.getString("userid"));
        String CURRENTNODETYPE = Util.null2String(rs.getString("CURRENTNODETYPE"));
        if ("0".equals(CURRENTNODETYPE)) {
            return "";
        }
        if ("1".equals(userid)) {
            return "系统管理员";
        } else {
            ResourceComInfo r = null;
            try {
                r = new ResourceComInfo();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return r.getLastname(userid);
        }
    }
    /**
     * 判断角色中是否存在传入人员id1
     * @param id
     * @return
     */
    public boolean existInRole(String id) {
        RecordSet rs = new RecordSet();
        rs.execute("select * from HRMROLEMEMBERS where ROLEID = 562 and resourceid = '"+id+"'");
        while(rs.next()) {
            return true;
        }
        return false;
    }

}
