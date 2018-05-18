package weaver.interfaces.service.workflow;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.hrm.resource.ResourceComInfo;
import weaver.interfaces.service.weixin.WXRemindServiceImpl;
import weaver.workflow.webservices.WorkflowBaseInfo;
import weaver.workflow.webservices.WorkflowMainTableInfo;
import weaver.workflow.webservices.WorkflowRequestInfo;
import weaver.workflow.webservices.WorkflowRequestTableField;
import weaver.workflow.webservices.WorkflowRequestTableRecord;
import weaver.workflow.webservices.WorkflowService;
import weaver.workflow.webservices.WorkflowServiceImpl;

public class TodoWorkflowServiceImpl2 extends BaseBean implements TodoWorkflowService {

	@Override
	public String todoWorkflowCount(String loginname) {
		String loginid = Util.null2String(loginname);
		if ("".equals(loginid)) {
			return "登录账号不能为空";
		}
		RecordSet rs = new RecordSet();
		String sql = "select id from hrmresource where loginid = '" + loginname + "'";
		rs.execute(sql);
		rs.next();
		String id = Util.null2String(rs.getString("id"));
		if ("".equals(id)) {
			return "登录账号" + loginname + "在泛微OA中不存在";
		}
		WorkflowService WorkflowServicePortTypeProxy = new WorkflowServiceImpl();
		String[] conditions = new String[1];
		conditions[0] = " t1.workflowid not in (104, 1, 101) ";
		int count = WorkflowServicePortTypeProxy.getToDoWorkflowRequestCount(Integer.parseInt(id), conditions);
		return Util.null2String(count);
	}

	public String todoWorkflowCountWithWorkflowid(String loginname, String workflowid, String reqid, String reqname) {
		RecordSet rs = new RecordSet();
		String sql = "select id from hrmresource where loginid = '" + loginname + "'";
		rs.execute(sql);
		rs.next();
		String id = Util.null2String(rs.getString("id"));
		WorkflowService WorkflowServicePortTypeProxy = new WorkflowServiceImpl();
		String[] conditions = new String[1];
		if (!"".equals(workflowid)) {
			conditions[0] = " t1.workflowid = '" + workflowid + "' ";
		} else {
			conditions[0] = " t1.workflowid not in (104, 1, 101) ";
		}
		if (!"".equals(reqid)) {
			conditions[0] += " and t1.requestid = '" + reqid + "' ";
		}
		if (!"".equals(reqname)) {
			conditions[0] += " and t1.requestname like '%" + reqname + "%' ";
		}
		int count = WorkflowServicePortTypeProxy.getToDoWorkflowRequestCount(Integer.parseInt(id), conditions);
		return Util.null2String(count);
	}

	@Override
	public String todoWorkflowList(int page, int limit, String loginname, String wname, String reqid, String reqname) {
		String IP = getPropValue("SYSIP", "SYSIP");
		TodoWorkflowModes modes = new TodoWorkflowModes();
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
		String wfId = "";
		if (!"".equals(wname)) {
			String workflowsql = "select id from workflow_base where id = '" + wname + "'";
			rs.execute(workflowsql);
			rs.next();
			wfId = Util.null2String(rs.getString("id"));
			if ("".equals(wfId)) {
				modes.setCount("0");
				modes.setMsg("wname:" + wname + " 在泛微OA中不存在");
				String jsonstr = JSON.toJSON(modes).toString();
				return jsonstr;
			}
		}

		WorkflowService WorkflowServicePortTypeProxy = new WorkflowServiceImpl();
		WorkflowRequestInfo[] workflowRequestInfo = null;
		String[] conditions = new String[1];
		if (!"".equals(wfId)) {
			conditions[0] = " t1.workflowid = '" + wfId + "' ";
		} else {
			conditions[0] = " t1.workflowid not in (104, 1, 101) ";
		}
		if (!"".equals(Util.null2String(reqid))) {
			conditions[0] += " and t1.requestid = '" + reqid + "' ";
		}
		if (!"".equals(Util.null2String(reqname))) {
			conditions[0] += " and t1.requestname like '%" + reqname + "%' ";
		}
		workflowRequestInfo = WorkflowServicePortTypeProxy.getToDoWorkflowRequestList(page, limit, 0,
				Integer.parseInt(id), conditions);
		String c = todoWorkflowCountWithWorkflowid(loginname, wfId, reqid, reqname);
		modes.setCount(c);
		List<TodoWorkflowMode> list = new ArrayList<TodoWorkflowMode>();
		for (int i = 0; i < workflowRequestInfo.length; i++) {
			TodoWorkflowMode mode = new TodoWorkflowMode();
			String iid = workflowRequestInfo[i].getRequestId();
			String wfname = workflowRequestInfo[i].getWorkflowBaseInfo().getWorkflowName();
			String name = workflowRequestInfo[i].getRequestName();
			String sj = workflowRequestInfo[i].getReceiveTime();
			String qyyh = getBeforeUser(iid);
			String status = getCurrentType(iid, id);
			mode.setIid(iid);
			mode.setName(name);
			mode.setQyyh(qyyh);
			mode.setSj(sj);
			mode.setStatus(status);
			mode.setWname(wfname);
			mode.setAddress("" + IP + "/interface/portal/portal.jsp?typeid=flow-" + iid + "");
			list.add(mode);
		}
		modes.setData(list);
		modes.setMsg("");
		String jsonstr = JSON.toJSON(modes).toString();
		return jsonstr;
	}

	@Override
	public String todoWorkflowTypeList(String loginname) {
		if ("".equals(Util.null2String(loginname))) {
			TodoWorkflowTypeMode[] modes = new TodoWorkflowTypeMode[0];
			String jsonstr = JSON.toJSON(modes).toString();
			return jsonstr;
		}
		RecordSet rs = new RecordSet();
		String sql = "select id from hrmresource where loginid = '" + loginname + "'";
		rs.execute(sql);
		rs.next();
		String id = Util.null2String(rs.getString("id"));
		if ("".equals(id)) {
			TodoWorkflowTypeMode[] modes = new TodoWorkflowTypeMode[0];
			String jsonstr = JSON.toJSON(modes).toString();
			return jsonstr;
		}
		String searchsql = "select distinct count(t1.requestid) requestcount,t1.workflowid,(select workflowname from workflow_base where id = t1.workflowid) workflowname "
				+ " from workflow_requestbase t1,workflow_currentoperator t2 where t1.requestid=t2.requestid "
				+ " and t2.usertype = 0 and t2.userid = " + id
				+ " and t2.isremark in( '0','1','5','7') and t2.islasttimes=1 and t1.workflowid not in (104, 1, 101) group by t1.WORKFLOWID";
		rs.execute(searchsql);
		int count = rs.getCounts();
		int c = 0;
		TodoWorkflowTypeMode[] modes = new TodoWorkflowTypeMode[count];
		while (rs.next()) {
			TodoWorkflowTypeMode mode = new TodoWorkflowTypeMode();
			String requestcount = Util.null2String(rs.getString("requestcount"));
			String workflowname = Util.null2String(rs.getString("workflowname"));
			String workflowid = Util.null2String(rs.getString("workflowid"));
			mode.setNum(requestcount);
			mode.setWid(workflowid);
			mode.setWname(workflowname);
			modes[c] = mode;
			c++;
		}
		String jsonstr = JSON.toJSON(modes).toString();
		return jsonstr;
	}

	@Override
	public String workflowTypeList() {
		RecordSet rs = new RecordSet();
		// String sql = "select id,workflowname from workflow_base where isvalid = 1";
		String sql = "select id,workflowname from workflow_base w where isvalid = 1 and not exists(select 1 from workflow_subwfset f where w.id=f.subworkflowid ) and id not in (104,1,101)";
		rs.execute(sql);
		int count = rs.getCounts();
		int c = 0;
		WorkflowType[] modes = new WorkflowType[count];
		while (rs.next()) {
			WorkflowType mode = new WorkflowType();
			String workflowid = Util.null2String(rs.getString("id"));
			String workflowname = Util.null2String(rs.getString("workflowname"));
			mode.setWorkflowid(workflowid);
			mode.setWorkflowname(workflowname);
			modes[c] = mode;
			c++;
		}
		String jsonstr = JSON.toJSON(modes).toString();
		return jsonstr;
	}

	@Override
	public String workflowList(int page, int limit, String name, String workflowid, String status, String startDate,
			String endDate, String loginname) {
		String IP = getPropValue("SYSIP", "SYSIP");
		RecordSet rs = new RecordSet();
		// String sql = "select
		// a.requestid,b.workflowname,a.requestname,a.currentnodetype,currentnodeid,c.userid,a.createdate,c.RECEIVEDATE
		// || ' ' || c.RECEIVETIME as receivedate,d.nodename from workflow_requestbase
		// a, //workflow_base b, "
		// + "WORKFLOW_CURRENTOPERATOR c, WORKFLOW_NODEBASE d where d.id =
		// a.CURRENTNODEID and a.workflowid = b.id and c.requestid = a.requestid and
		// c.nodeid = a.currentnodeid and not exists(select 1 from //workflow_subwfset f
		// where b.id=f.subworkflowid ) and b.id not in (104,1,101)";
		WorkflowDataMode mode = new WorkflowDataMode();
		if ("".equals(Util.null2String(loginname))) {
			mode.setCount("0");
			mode.setMsg("登录账号不能为空");
			String jsonstr = JSON.toJSON(mode).toString();
			return jsonstr;
		}
		String sql1 = "select id,departmentid from hrmresource where loginid = '" + loginname + "'";
		rs.execute(sql1);
		rs.next();
		String id = Util.null2String(rs.getString("id"));
		String departmentid = Util.null2String(rs.getString("departmentid"));
		if ("".equals(id)) {
			mode.setCount("0");
			mode.setMsg("登录账号在泛微OA中不存在");
			String jsonstr = JSON.toJSON(mode).toString();
			return jsonstr;
		}

		List<String> list = new ArrayList<String>();
		String conditions = "";
		if (!("ADMIN".equals(loginname) || "ZHOUX".equals(loginname) || "21".equals(departmentid) || "46".equals(departmentid))) {
			String sql2 = "select distinct requestid from WORKFLOW_CURRENTOPERATOR where userid = '" + id + "'";
			rs.execute(sql2);
			while (rs.next()) {
				list.add(Util.null2String(rs.getString("requestid")));
			}
		}
		
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				if (i == 0) {
					conditions += " and (";
				}
				conditions += " a.REQUESTID = '" + list.get(i) + "' ";
				if (i == list.size() - 1) {
					conditions += " ) ";
				} else {
					conditions += " or ";
				}
			}
		} else if ("ADMIN".equals(loginname) || "ZHOUX".equals(loginname) || "21".equals(departmentid) || "46".equals(departmentid)) {

		} else {
			conditions += " and 1 != 1 ";
		}

		String sql = "select a.requestid, b.workflowname,  a.requestname,  a.currentnodetype, currentnodeid,  a.createdate,  max(c.userid) as userid,  d.nodename from workflow_requestbase  a, workflow_base  b, WORKFLOW_CURRENTOPERATOR c,  WORKFLOW_NODEBASE  d where d.id = a.CURRENTNODEID  and a.workflowid = b.id  and c.requestid = a.requestid and c.nodeid = a.currentnodeid  and not exists (select 1 from workflow_subwfset f where b.id = f.subworkflowid) and b.id not in (104, 1, 101,63) ";

		if (!"".equals(Util.null2String(name))) {
			sql += " and a.requestname like '%" + name + "%' ";
		}
		if (!"".equals(Util.null2String(workflowid))) {
			sql += " and a.workflowid = '" + workflowid + "' ";
		}
		if (!"".equals(Util.null2String(status))) {
			if ("1".equals(status)) {
				sql += " and a.currentnodetype != '3' ";
			}
			if ("2".equals(status)) {
				sql += " and a.currentnodetype = '3' ";
			}
		}
		if (!"".equals(Util.null2String(startDate))) {
			sql += " and a.createdate >= '" + startDate + "' ";
		}
		if (!"".equals(Util.null2String(endDate))) {
			sql += " and a.createdate <= '" + endDate + "' ";
		}
		sql += conditions;
		sql += "  group by a.requestid,b.workflowname,a.requestname, a.currentnodetype,currentnodeid,a.createdate,d.nodename";
		sql += " order by a.requestid desc ";
		rs.execute(sql);
		int cnum = rs.getCounts();

		mode.setCode("0");
		mode.setCount(Util.null2String(cnum));
		mode.setMsg("");
		List<WorkflowDataMode_Item> data = new ArrayList<WorkflowDataMode_Item>();
		if (page < 1)
			page = 1;
		int i = 0;
		int j = 0;
		i = page * limit + 1;
		j = (page - 1) * limit;
		String str = new StringBuilder().append(
				" select * from ( select my_table.*,rownum as my_rownum from ( select tableA.*,rownum as r from ( ")
				.append(sql).append(" ) tableA  ) my_table where rownum < ").append(i).append(" ) where my_rownum > ")
				.append(j).toString();
		rs.execute(str);
		while (rs.next()) {
			WorkflowDataMode_Item item = new WorkflowDataMode_Item();
			String iid = Util.null2String(rs.getString("requestid"));
			String wname = Util.null2String(rs.getString("workflowname"));
			String names = Util.null2String(rs.getString("requestname"));
			
			String currentnodetype = Util.null2String(rs.getString("currentnodetype"));
			String statuss = "";
			if ("3".equals(currentnodetype)) {
				statuss = "办结";
			} else {
				statuss = "未办结";
			}
			String step = Util.null2String(rs.getString("nodename"));
			String userid = Util.null2String(rs.getString("userid"));
			String accepted_time = getReceiveDateTime(iid, userid);
			
			String user_name = getLoginname(userid);
			String caseno = "";
			String request_process_address = "" + IP + "/interface/portal/portal.jsp?typeid=flow-" + iid + "";
			String request_address = "" + IP + "/interface/portal/portal.jsp?typeid=flowchart-" + iid + "";

			item.setIid(iid);
			item.setWname(wname);
			item.setName(names);
			item.setAccepted_time(accepted_time);
			item.setStatus(statuss);
			item.setStep(step);
			item.setUser_name(user_name);
			item.setCaseno(caseno);
			item.setRequest_address(request_address);
			item.setRequest_process_address(request_process_address);
			data.add(item);
		}
		mode.setData(data);
		String jsonstr = JSON.toJSON(mode).toString();
		return jsonstr;
	}

	public String getReceiveDateTime(String requestid,String userid) {
		RecordSet rs = new RecordSet();
		rs.execute("SELECT * from WORKFLOW_CURRENTOPERATOR where REQUESTID = '"+requestid+"' and userid = '"+userid+"' order by RECEIVEDATE desc,RECEIVETIME desc");
		rs.next();
		String RECEIVEDATE = Util.null2String(rs.getString("RECEIVEDATE"));
		String RECEIVETIME = Util.null2String(rs.getString("RECEIVETIME"));
		return RECEIVEDATE + " " +  RECEIVETIME;
	}
	
	
	public String getBeforeUser(String requestid) {
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

	public String getCurrentType(String requestid, String userid) {
		RecordSet rs = new RecordSet();
		String sql = "select viewtype from WORKFLOW_CURRENTOPERATOR where REQUESTID = " + requestid + " and  userid = '"
				+ userid + "' order by OPERATEDATE desc,OPERATEDATE desc, RECEIVEDATE desc, RECEIVETIME desc";
		rs.execute(sql);
		rs.next();
		String viewtype = Util.null2String(rs.getString("viewtype"));
		if ("0".equals(viewtype)) {
			return "0";
		} else {
			return "1";
		}
	}

	public String getLoginname(String userid) {
		String sql = "select lastname from hrmresource where id = '" + userid + "'";
		RecordSet rs = new RecordSet();
		rs.execute(sql);
		rs.next();
		String lastname = Util.null2String(rs.getString("lastname"));
		if ("1".equals(Util.null2String(userid))) {
			return "系统管理员";
		}
		return lastname;
	}

	@Override
	public String ljcyRemind(String content) {
		JSONObject jsonObject = JSON.parseObject(content);
		String title = jsonObject.getString("title");
		String nr = jsonObject.getString("content");
		JSONArray array = jsonObject.getJSONArray("hrm");
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < array.size(); i++) {
			JSONObject object = (JSONObject) array.get(i);
			String loginid = (String) object.get("loginid");
			if ("".equals(loginid)) {
				continue;
			}
			String hrmid = getHrmid(loginid);
			if (!"".equals(hrmid)) {
				list.add(hrmid);
			}
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));
			if (i != list.size() - 1) {
				sb.append(",");
			}
		}
		String hrmids = sb.toString();
		String requestid = "";
		if (!"".equals(hrmids)) {
			requestid = createWorkflow(hrmids, title, nr);
		}
		if (!"".equals(requestid) && Integer.parseInt(requestid) > 0) {
			return "{\"message\":\"成功\",\"requestid\":\"" + requestid + "\"}";
		}
		// {"title":"","content":"","hrm":[{"loginid":""}]}

		return "{\"message\":\"失败，错误码：" + requestid + "\",\"requestid\":\"\"}";
	}

	private String createWorkflow(String hrmids, String title, String content) {
		// 主字段
		WorkflowRequestTableField[] wrti = new WorkflowRequestTableField[2]; // 字段信息

		wrti[0] = new WorkflowRequestTableField();
		wrti[0].setFieldName("mutiresource");//
		wrti[0].setFieldValue(hrmids);//
		wrti[0].setView(true);// 字段是否可见
		wrti[0].setEdit(true);// 字段是否可编辑

		wrti[1] = new WorkflowRequestTableField();
		wrti[1].setFieldName("remark");//
		wrti[1].setFieldValue(content);//
		wrti[1].setView(true);// 字段是否可见
		wrti[1].setEdit(true);// 字段是否可编辑

		WorkflowRequestTableRecord[] wrtri = new WorkflowRequestTableRecord[1];// 主字段只有一行数据
		wrtri[0] = new WorkflowRequestTableRecord();
		wrtri[0].setWorkflowRequestTableFields(wrti);

		WorkflowMainTableInfo wmi = new WorkflowMainTableInfo();
		wmi.setRequestRecords(wrtri);

		// 添加工作流id
		WorkflowBaseInfo wbi = new WorkflowBaseInfo();
		wbi.setWorkflowId("1");// workflowid 流程接口演示流程2016==38

		WorkflowRequestInfo wri = new WorkflowRequestInfo();// 流程基本信息
		wri.setCreatorId("1");// 创建人id
		wri.setRequestLevel("0");// 0 正常，1重要，2紧急
		wri.setWorkflowMainTableInfo(wmi);// 添加主字段数据
		wri.setWorkflowBaseInfo(wbi);
		wri.setRequestName(title);

		WorkflowService WorkflowServicePortTypeProxy = new WorkflowServiceImpl();
		String rqid = WorkflowServicePortTypeProxy.doCreateWorkflowRequest(wri, 1);
		return rqid;
	}

	private String getHrmid(String loginid) {
		String sql = "select id from hrmresource where loginid = '" + loginid + "'";
		RecordSet rs = new RecordSet();
		rs.execute(sql);
		rs.next();
		return Util.null2String(rs.getString("id"));
	}

	@Override
	public String finishWorkflowTypeList(String loginname) {
		String loginid = Util.null2String(loginname);
		if ("".equals(loginid)) {
			return "登录账号不能为空";
		}
		RecordSet rs = new RecordSet();
		String sql = "select id from hrmresource where loginid = '" + loginname + "'";
		rs.execute(sql);
		rs.next();
		String id = Util.null2String(rs.getString("id"));
		if ("".equals(id)) {
			return "登录账号" + loginname + "在泛微OA中不存在";
		}
		String finishsql = "select DISTINCT workflowid,workflowname from (SELECT DISTINCT t1.workflowid ,t3.workflowname "
				+ "FROM workflow_requestbase t1, workflow_currentoperator t2, workflow_base t3 WHERE "
				+ "t1.requestid = t2.requestid and t1.workflowid not in (104, 1, 101) and t1.workflowid = t3.id AND t2.usertype = 0 AND t2.userid = "
				+ id + " AND t2.isremark = '2' AND t2.iscomplete = 0 AND " + "t2.islasttimes = 1 " + "UNION ALL "
				+ "SELECT DISTINCT t1.workflowid, t3.workflowname  "
				+ "FROM workflow_requestbase t1, workflow_currentoperator t2,workflow_base t3 WHERE "
				+ "t1.requestid = t2.requestid and t1.workflowid not in (104, 1, 101) and t1.workflowid = t3.id  AND t2.usertype = 0 AND t2.userid = "
				+ id + " AND t2.isremark IN ('2', '4') AND "
				+ "t1.currentnodetype = '3' AND iscomplete = 1 AND islasttimes = 1)";

		rs.execute(finishsql);
		List<FinishedWorkflowTypeMode> list = new ArrayList<FinishedWorkflowTypeMode>();
		while (rs.next()) {
			FinishedWorkflowTypeMode mode = new FinishedWorkflowTypeMode();
			String workflowid = Util.null2String(rs.getString("workflowid"));
			String workflowname = Util.null2String(rs.getString("workflowname"));
			mode.setWid(workflowid);
			mode.setWname(workflowname);
			list.add(mode);
		}
		String jsonstr = JSON.toJSON(list).toString();
		return jsonstr;
	}

	@Override
	public String finishWorklowList(int page, int limit, String loginname, String requestid, String name,
			String workflowid) {
		String IP = getPropValue("SYSIP", "SYSIP");
		FinishedWorkflowModes modes = new FinishedWorkflowModes();
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
		String wfId = "";
		if (!"".equals(workflowid)) {
			String workflowsql = "select id from workflow_base where id = '" + workflowid + "'";
			rs.execute(workflowsql);
			rs.next();
			wfId = Util.null2String(rs.getString("id"));
			if ("".equals(wfId)) {
				modes.setCount("0");
				modes.setMsg("wname:" + workflowid + " 在泛微OA中不存在");
				String jsonstr = JSON.toJSON(modes).toString();
				return jsonstr;
			}
		}

		String finishsql = "select distinct requestid,workflowname,requestname,redate,currentnodetype,workflowid,CREATEDATE,CREATETIME from ("
				+ "  SELECT DISTINCT" + "    t3.workflowname," + " " + "    t1.createdate," + "    t1.createtime,"
				+ "    t1.creater," + "    t1.currentnodeid," + "    t1.currentnodetype," + "    t1.lastoperator,"
				+ "    t1.creatertype," + "    t1.lastoperatortype," + "    t1.lastoperatedate,"
				+ "    t1.lastoperatetime," + "    t1.requestid," + "    t1.requestname," + "    t1.requestlevel,"
				+ "    t1.workflowid," + "    t1.createdate || ' ' || t1.createtime redate"
				+ "  FROM workflow_requestbase t1, workflow_currentoperator t2, workflow_base t3" + "  WHERE"
				+ "    t1.requestid = t2.requestid AND t1.workflowid = t3.id AND t2.usertype = 0 AND t2.userid = " + id
				+ " AND t2.isremark = '2'" + "    AND t2.iscomplete = 0 AND t2.islasttimes = 1" + "  UNION ALL"
				+ "  SELECT DISTINCT" + "    t3.workflowname," + " " + "    t1.createdate," + "    t1.createtime,"
				+ "    t1.creater," + "    t1.currentnodeid," + "    t1.currentnodetype," + "    t1.lastoperator,"
				+ "    t1.creatertype," + "    t1.lastoperatortype," + "    t1.lastoperatedate,"
				+ "    t1.lastoperatetime," + "    t1.requestid," + "    t1.requestname," + "    t1.requestlevel,"
				+ "    t1.workflowid," + "    t1.createdate || ' ' || t1.createtime redate "
				+ "  FROM workflow_requestbase t1, workflow_currentoperator t2, workflow_base t3"
				+ "  WHERE t1.workflowid = t3.id AND t1.requestid = t2.requestid AND t2.usertype = 0 AND t2.userid = "
				+ id + " AND"
				+ "        t2.isremark IN ('2', '4') AND t1.currentnodetype = '3' AND iscomplete = 1 AND islasttimes = 1"
				+ ") where 1=1 ";
		if (!"".equals(Util.null2String(requestid))) {
			finishsql += " and requestid = '" + requestid + "' ";
		}
		if (!"".equals(Util.null2String(name))) {
			finishsql += " and requestname like '%" + name + "%' ";
		}
		if (!"".equals(Util.null2String(workflowid))) {
			finishsql += " and workflowid = '" + workflowid + "' ";
		}

		rs.execute(finishsql);
		int count = rs.getCounts();
		FinishedWorkflowModes mode = new FinishedWorkflowModes();
		mode.setCode("0");
		mode.setCount(Util.null2String(count));
		mode.setMsg("");
		List<FinishedWorkflowMode> data = new ArrayList<FinishedWorkflowMode>();

		if (page < 1)
			page = 1;
		int i = 0;
		int j = 0;
		i = page * limit + 1;
		j = (page - 1) * limit;
		String orderBy = " order by CREATEDATE desc, CREATETIME desc ";
		String str = new StringBuilder().append(
				" select * from ( select my_table.*,rownum as my_rownum from ( select tableA.*,rownum as r from ( ")
				.append(finishsql).append(orderBy).append(" ) tableA  ) my_table where rownum < ").append(i)
				.append(" ) where my_rownum > ").append(j).toString();
		rs.execute(str);
		while (rs.next()) {
			String currentnodetype = Util.null2String(rs.getString("currentnodetype"));
			String reqid = Util.null2String(rs.getString("requestid"));
			String workflowname = Util.null2String(rs.getString("workflowname"));
			String requestname = Util.null2String(rs.getString("requestname"));
			String redate = Util.null2String(rs.getString("redate"));
			String address = "" + IP + "/interface/portal/portal.jsp?typeid=flow-" + reqid + "";
			FinishedWorkflowMode mod = new FinishedWorkflowMode();
			mod.setIid(reqid);
			mod.setWname(workflowname);
			mod.setName(requestname);
			mod.setSj(redate);
			if ("3".equals(currentnodetype)) {
				mod.setZbr("已归档");
			} else {
				mod.setZbr(getUnopetator(reqid));
			}

			mod.setAddress(address);
			data.add(mod);
		}
		mode.setData(data);
		String jsonstr = JSON.toJSON(mode).toString();
		return jsonstr;
	}

	private String getUnopetator(String requestid) {
		RecordSet rs = new RecordSet();
		String sql = "select lastname from hrmresource where id in (" + "  SELECT DISTINCT userid"
				+ "  FROM workflow_currentoperator"
				+ "  WHERE (isremark IN ('0', '1', '5', '7', '8', '9') OR (isremark = '4' AND viewtype = 0)) AND requestid = '"
				+ requestid + "'" + "  UNION SELECT DISTINCT" + "          userid" + "        FROM ofs_todo_data"
				+ "        WHERE requestid = '" + requestid + "'" + "              AND isremark = '0'" + ")";
		rs.execute(sql);
		StringBuffer sb = new StringBuffer(",");
		while (rs.next()) {
			String lastname = Util.null2String(rs.getString("lastname"));
			sb.append(lastname);
			sb.append(",");
		}
		String sbs = sb.toString();
		if (",".equals(sbs)) {
			return "";
		}
		sbs = sbs.substring(1, sbs.length() - 1);
		return sbs;
	}

	@Override
	public String ywspRemind(String jsondata) {
		WXRemindServiceImpl imp = new WXRemindServiceImpl();
		String data = imp.sendWXRemind(jsondata);
		return data;
	}
}