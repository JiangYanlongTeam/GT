package weaver.interfaces.fj;

import com.alibaba.fastjson.JSON;
import weaver.conn.RecordSet;
import weaver.docs.webservices.DocAttachment;
import weaver.docs.webservices.DocInfo;
import weaver.docs.webservices.DocService;
import weaver.docs.webservices.DocServiceImpl;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.action.WfForceOver;
import weaver.workflow.webservices.WorkflowRequestInfo;
import weaver.workflow.webservices.WorkflowService;
import weaver.workflow.webservices.WorkflowServiceImpl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class FJServiceImpl extends BaseBean implements FJService {

    @Override
    public String getFJData(int page, int limit, String loginname, String workflowid, String begindate, String enddate,
                            String requestname) {
        List<String> fjList = fjList();
        FJModes modes = new FJModes();
        String IP = getPropValue("SYSIP", "SYSIP");
        if ("".equals(Util.null2String(loginname))) {
            modes.setCount("0");
            modes.setMsg("登录账号不能为空");
            String jsonstr = JSON.toJSON(modes).toString();
            return jsonstr;
        }
        RecordSet rs = new RecordSet();
        String sql = "select id from hrmresource where loginid = '" + loginname + "'";
        rs.execute(sql);
        rs.next();
        String id = Util.null2String(rs.getString("id"));
        if ("".equals(id)) {
            modes.setCount("0");
            modes.setMsg("登录账号在泛微OA中不存在");
            String jsonstr = JSON.toJSON(modes).toString();
            return jsonstr;
        }
        StringBuffer departmentids = new StringBuffer("");
        for (int i = 0; i < fjList.size(); i++) {
            departmentids.append(fjList.get(i));
            if (i != fjList.size() - 1) {
                departmentids.append(",");
            }
        }
        String depids = departmentids.toString();

        String sql2 = "select distinct a.REQUESTID,a.createdate,a.createtime, a.createdate || ' ' || a.createtime as createdatetime,a.creater,b.loginid, "
                + "  a.workflowid,d.WORKFLOWNAME,a.requestname,b.DEPARTMENTID,c.DEPARTMENTNAME,a.MAINREQUESTID "
                + "  from workflow_requestbase a,workflow_currentoperator t2,HRMRESOURCE b,workflow_base d,HRMDEPARTMENT c where c.id = b.DEPARTMENTID and a.WORKFLOWID = d.id and b.id = a.creater and a.requestid=t2.requestid "
                + "  and t2.usertype = 0 and t2.userid = " + id + " and b.DEPARTMENTID in (" + depids
                + ") and t2.isremark in( '0','1','5','7') and t2.islasttimes=1 and a.WORKFLOWID in (62,202) and a.CURRENTNODEID in (287,428) ";
        if (!"".equals(Util.null2String(workflowid))) {
            sql2 += " and a.WORKFLOWID = '" + workflowid + "' ";
        }
        if (!"".equals(Util.null2String(begindate))) {
            sql2 += " and a.createdate >= '" + begindate + "' ";
        }
        if (!"".equals(Util.null2String(enddate))) {
            sql2 += " and a.createdate <= '" + enddate + "' ";
        }
        if (!"".equals(Util.null2String(requestname))) {
            sql2 += " and a.requestname like '%" + requestname + "%' ";
        }

        String orderBy = " order by a.createdate desc, a.createtime desc ";
        rs.execute(sql2);
        int count = rs.getCounts();
        modes.setCount(Util.null2String(count));
        modes.setCode("0");
        modes.setMsg("");
        if (page < 1)
            page = 1;
        int i = 0;
        int j = 0;
        i = page * limit + 1;
        j = (page - 1) * limit;
        String str = new StringBuilder().append(
                " select * from ( select my_table.*,rownum as my_rownum from ( select tableA.*,rownum as r from ( ")
                .append(sql2).append(orderBy).append(" ) tableA  ) my_table where rownum < ").append(i)
                .append(" ) where my_rownum > ").append(j).toString();
        rs.execute(str);
        List<FJMode> data = new ArrayList<FJMode>();
        while (rs.next()) {
            String requestid = Util.null2String(rs.getString("requestid"));
            String createdatetime = Util.null2String(rs.getString("createdatetime"));
            String creater = Util.null2String(rs.getString("loginid"));
            String wfid = Util.null2String(rs.getString("workflowid"));
            String workflowname = Util.null2String(rs.getString("workflowname"));
            String title = Util.null2String(rs.getString("requestname"));
            String departmentid = Util.null2String(rs.getString("departmentid"));
            String departmentname = Util.null2String(rs.getString("departmentname"));
            String mainrequestid = Util.null2String(rs.getString("mainrequestid"));
            FJMode mode = new FJMode();
            mode.setRequestid(requestid);
            mode.setCreatedatetime(createdatetime);
            mode.setCreater(creater);
            mode.setWorkflowid(wfid);
            mode.setWorkflowname(workflowname);
            mode.setTitle(title);
            mode.setDepartmentid(departmentid);
            mode.setDepartmentname(departmentname);
            String address = "" + IP + "/interface/portal/portal.jsp?typeid=flow-" + mainrequestid + "";
            mode.setAddress(address);
            data.add(mode);
        }
        modes.setData(data);
        String jsonstr = JSON.toJSON(modes).toString();
        return jsonstr;
    }

    /**
     * 获取文件json对象
     *
     * @param xgfj
     * @return
     */
    public List<FileMode> getFileJSON(String xgfj) {
        List<FileMode> list = new ArrayList<FileMode>();
        if ("".equals(xgfj)) {
            return list;
        }
        String[] xgfjs = xgfj.split(",");
        for (int i = 0; i < xgfjs.length; i++) {
            if (!xgfjs[i].equals("")) {
                FileMode mode = getFileMode(xgfjs[i]);
                if (mode != null) {
                    list.add(mode);
                }
            }
        }
        return list;
    }

    /**
     * 查询webservice接口，获取对应docid的附件内容和附件名字
     *
     * @param docid
     * @return
     */
    public FileMode getFileMode(String docid) {
        FileMode mode = new FileMode();
        String username = Util.null2String(getPropValue("DocAccountUsedByAntu", "username"));
        String password = Util.null2String(getPropValue("DocAccountUsedByAntu", "password"));
        if ("".equals(username)) {
            username = "FJWH";
        }
        if ("".equals(password)) {
            password = "1";
        }
        DocService proxy = new DocServiceImpl();
        try {
            String session = proxy.login(username, password, 0, "127.0.0.1");
            DocInfo doc = proxy.getDoc(Integer.parseInt(docid), session);
            if (doc != null) {
                DocAttachment[] das = doc.getAttachments();
                DocAttachment da = das[0];
                String name = da.getFilename();
                String content = da.getFilecontent();
                // byte[] content = Base64.decode(da.getFilecontent());
                String value = content.toString();
                mode.setName(name);
                mode.setValue(value);
            }

        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e1) {
            e1.printStackTrace();
            return null;
        }
        return mode;
    }

    public List<String> fjList() {
        List<String> list = new ArrayList<String>();
        list.add("44");
        list.add("54");
        list.add("51");
        list.add("49");
        return list;
    }

    public String getTableName(String requestid) {
        RecordSet rs = new RecordSet();
        String sql = "select formid from workflow_base where id = (select workflowid from workflow_requestbase where requestid = "
                + requestid + ")";
        rs.execute(sql);
        rs.next();
        String formid = Util.null2String(rs.getString("formid"));
        String tableName = "formtable_main_" + Math.abs(Integer.parseInt(formid));
        return tableName;
    }

    public String getXGFJ(String tablename, String requestid) {
        RecordSet rs = new RecordSet();
        rs.execute("select xgfj from " + tablename + " where requestid = '" + requestid + "'");
        rs.next();
        String xgfj = Util.null2String(rs.getString("xgfj"));
        return xgfj;
    }

    @Override
    public String doForceWf(String requestid, String message) {
        // RecordSet rs = new RecordSet();
        // String tableName = getTableName(requestid);
        // 更新表单字段
        WfForceOver wf = new WfForceOver();
        ArrayList<String> array = new ArrayList<String>();
        array.add(requestid);
        wf.doForceOver(array);
        return "";
    }

    @Override
    public String notify(String requestid) {
        RecordSet rs = new RecordSet();
        String sql = "select formid from workflow_base where id = (select workflowid from workflow_requestbase where requestid = "
                + requestid + ")";
        rs.execute(sql);
        rs.next();
        String formid = Util.null2String(rs.getString("formid"));
        String tableName = "formtable_main_" + Math.abs(Integer.parseInt(formid));

        String sql1 = "select creater from workflow_requestbase where requestid = '" + requestid + "'";
        rs.execute(sql1);
        rs.next();
        String creater = Util.null2String(rs.getString("creater"));
        String sql2 = "select departmentid from hrmresource where id = '" + creater + "'";
        rs.execute(sql2);
        rs.next();
        String depid = Util.null2String(rs.getString("departmentid"));
        String sql3 = "update " + tableName + " set dqbm = '" + depid + "' where requestid = '" + requestid + "'";
        rs.execute(sql3);
        boolean isSuccess = false;
        try {
            isSuccess = SubmitRequest(Integer.parseInt(requestid), Integer.parseInt(creater));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (isSuccess) {
            return "{\"message\":\"提交成功\",\"code\":\"0\"}";
        }
        return "{\"message\":\"提交失败\",\"code\":\"-1\"}";
    }

    /**
     * 获得流程的详细信息
     *
     * @param requestid
     * @return
     * @throws RemoteException
     */
    private static WorkflowRequestInfo getRequestInfo(int requestid, int userid) throws RemoteException {
        WorkflowService WorkflowServicePortTypeProxy = new WorkflowServiceImpl();
        WorkflowRequestInfo WorkflowRequestInfo = WorkflowServicePortTypeProxy.getWorkflowRequest(requestid, userid, 0);// 调用接口获取对应requestid的数据
        return WorkflowRequestInfo;
    }

    /**
     * 提交流程
     *
     * @throws RemoteException
     */
    private boolean SubmitRequest(int requestid, int userid) throws RemoteException {
        WorkflowRequestInfo WorkflowRequestInfo = getRequestInfo(requestid, userid);
        WorkflowService WorkflowServicePortTypeProxy = new WorkflowServiceImpl();
        String str = WorkflowServicePortTypeProxy.submitWorkflowRequest(WorkflowRequestInfo, requestid, userid,
                "submit", "");
        writeLog("流程自动提交：" + str);
        return "success".equals(str);
    }

    @Override
    public String getFile(String requestid) {
        String tableName = getTableName(requestid);
        String xfgj = getXGFJ(tableName, requestid);
        List<FileMode> files = getFileJSON(xfgj);
        return JSON.toJSON(files).toString();
    }
}
