package weaver.interfaces.service.remind;

import com.alibaba.fastjson.JSON;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;

import java.util.ArrayList;
import java.util.List;

public class RemindServiceImpl extends BaseBean implements RemindService {

	@Override
	public String exists(String loginname, String type) {
		String loginid = Util.null2String(loginname);
		if ("".equals(loginid)) {
			return "登录账号不能为空";
		}
		RecordSet rs = new RecordSet();
		String sql = "select id from hrmresource where loginid = '" + loginname + "'";
		writeLog("根据传入参数：" + loginname + "查询人员sql：" + sql);
		rs.execute(sql);
		rs.next();
		String id = Util.null2String(rs.getString("id"));
		if ("".equals(id)) {
			return "登录账号" + loginname + "在泛微OA中不存在";
		}
		String typeid = "";
		String typeName = "";
		if ("1".equals(type)) { // 领导批示提醒
			typeid = "3";
			typeName = "领导批示提醒";
			// 获取领导批示意见
			RemindMode[] ldremindModes = getRemindWithLoginnameAndTypeID(loginname, typeid, typeName);
			int total = ldremindModes.length;
			RemindMode[] modes = new RemindMode[total];
			for (int i = 0; i < ldremindModes.length; i++) {
				modes[i] = ldremindModes[i];
			}
			String jsonstr = JSON.toJSON(modes).toString();
			return jsonstr;
		}
		if ("2".equals(type)) { // 提醒通知单
			// 获取提醒通知单
			RemindMode[] txremindModes = getTodoRemind(loginname);
			int total = txremindModes.length;
			RemindMode[] modes = new RemindMode[total];
			for (int i = 0; i < txremindModes.length; i++) {
				modes[i] = txremindModes[i];
			}
			String jsonstr = JSON.toJSON(modes).toString();
			return jsonstr;
		}
		if ("3".equals(type)) { // 归档提醒
			// 获取流程归档提醒
			typeid = "1";
			typeName = "流程归档提醒";
			// RemindMode[] gdremindModes = getRemindWithLoginnameAndTypeID(loginname,
			// typeid, typeName);
			RemindMode[] gdremindModes = getLCGDTX(loginname, typeid, typeName);
			int total = gdremindModes.length;
			RemindMode[] modes = new RemindMode[total];
			for (int i = 0; i < gdremindModes.length; i++) {
				modes[i] = gdremindModes[i];
			}
			String jsonstr = JSON.toJSON(modes).toString();
			return jsonstr;
		}
		if ("4".equals(type)) { // 获取催办通知单
			// 获取催办通知单
			typeid = "2";
			typeName = "催办通知单";
			RemindMode[] dbremindModes = getRemindWithLoginnameAndTypeID(loginname, typeid, typeName);
			int total = dbremindModes.length;
			RemindMode[] modes = new RemindMode[total];
			for (int i = 0; i < dbremindModes.length; i++) {
				modes[i] = dbremindModes[i];
			}
			String jsonstr = JSON.toJSON(modes).toString();
			return jsonstr;
		}
		return "";
	}

	@Override
	public String remind(String loginname, String type) {
		String loginid = Util.null2String(loginname);
		if ("".equals(loginid)) {
			return "登录账号不能为空";
		}
		RecordSet rs = new RecordSet();
		String sql = "select id from hrmresource where loginid = '" + loginname + "'";
		writeLog("根据传入参数：" + loginname + "查询人员sql：" + sql);
		rs.execute(sql);
		rs.next();
		String id = Util.null2String(rs.getString("id"));
		if ("".equals(id)) {
			return "登录账号" + loginname + "在泛微OA中不存在";
		}
		String typeid = "";
		String typeName = "";
		if ("1".equals(type)) { // 领导批示提醒
			typeid = "3";
			typeName = "领导批示提醒";
			// 获取领导批示意见
			RemindMode[] ldremindModes = getRemindWithLoginnameAndTypeID(loginname, typeid, typeName);
			updateLDPSYJ(loginname, typeid, typeName);
			int total = ldremindModes.length;
			RemindMode[] modes = new RemindMode[total];
			for (int i = 0; i < ldremindModes.length; i++) {
				modes[i] = ldremindModes[i];
			}
			String jsonstr = JSON.toJSON(modes).toString();
			return jsonstr;
		}
		if ("2".equals(type)) { // 提醒通知单
			// 获取提醒通知单
			RemindMode[] txremindModes = getTodoRemind(loginname);
			int total = txremindModes.length;
			RemindMode[] modes = new RemindMode[total];
			for (int i = 0; i < txremindModes.length; i++) {
				modes[i] = txremindModes[i];
			}
			String jsonstr = JSON.toJSON(modes).toString();
			return jsonstr;
		}
		if ("3".equals(type)) { // 归档提醒
			// 获取流程归档提醒
			typeid = "1";
			typeName = "流程归档提醒";
			// RemindMode[] gdremindModes = getRemindWithLoginnameAndTypeID(loginname,
			// typeid, typeName);
			RemindMode[] gdremindModes = getLCGDTX(loginname, typeid, typeName);
			int total = gdremindModes.length;
			RemindMode[] modes = new RemindMode[total];
			for (int i = 0; i < gdremindModes.length; i++) {
				modes[i] = gdremindModes[i];
			}
			String jsonstr = JSON.toJSON(modes).toString();
			return jsonstr;
		}
		if ("4".equals(type)) { // 获取催办通知单
			// 获取催办通知单
			typeid = "2";
			typeName = "催办通知单";
			RemindMode[] dbremindModes = getRemindWithLoginnameAndTypeID(loginname, typeid, typeName);
			int total = dbremindModes.length;
			RemindMode[] modes = new RemindMode[total];
			for (int i = 0; i < dbremindModes.length; i++) {
				modes[i] = dbremindModes[i];
			}
			String jsonstr = JSON.toJSON(modes).toString();
			return jsonstr;
		}
		return "";
	}

	private RemindMode[] getTodoRemind(String loginname) {
		String IP = getPropValue("SYSIP", "SYSIP");
		RecordSet rs = new RecordSet();
		String sql = "select id from hrmresource where loginid = '" + loginname + "'";
		writeLog("根据传入参数：" + loginname + "查询人员sql：" + sql);
		rs.execute(sql);
		rs.next();
		String id = Util.null2String(rs.getString("id"));
		String getTodoSQL = "select distinct t1.createdate,t1.createtime,t1.creater,t1.currentnodeid,t1.currentnodetype,"
				+ "  t1.lastoperator,t1.creatertype,t1.lastoperatortype,t1.lastoperatedate,"
				+ "  t1.lastoperatetime,t1.requestid,t1.requestname,t1.requestlevel,t1.workflowid,t2.receivedate,t2.receivetime "
				+ "  from workflow_requestbase t1,workflow_currentoperator t2 where t1.requestid=t2.requestid "
				+ "  and t2.usertype = 0 and t2.userid = " + id
				+ " and t2.isremark in( '0','1','5','7') and t2.islasttimes=1 and t1.workflowid in (201,205,222,241,59,60,202) ";
		rs.execute(getTodoSQL);
		int count = rs.getCounts();
		RemindMode[] RemindModes = new RemindMode[count];
		int c = 0;
		while (rs.next()) {
			String requestid = Util.null2String(rs.getString("requestid"));
			String requestname = Util.null2String(rs.getString("requestname"));
			String address = IP + "/interface/portal/portal.jsp?typeid=flow-" + requestid + "";
			String type = "提醒通知单";
			RemindMode remind = new RemindMode();
			remind.setIid(requestid);
			remind.setName(requestname);
			remind.setAddress(address);
			remind.setType(type);
			RemindModes[c] = remind;
			c++;
		}
		return RemindModes;
	}

	/**
	 * 获取消息
	 * 
	 * @param loginname
	 * @param typeid
	 * @param typeName
	 * @return
	 */
	private RemindMode[] getRemindWithLoginnameAndTypeID(String loginname, String typeid, String typeName) {
		RecordSet rs = new RecordSet();
		String sql = "select * from uf_sendantu where loginname = '" + loginname + "' and flag = 1 and typeid = "
				+ typeid + " ";
		rs.execute(sql);
		int count = rs.getCounts();
		RemindMode[] RemindModes = new RemindMode[count];
		int c = 0;
		while (rs.next()) {
			String requestid = Util.null2String(rs.getString("iid"));
			String requestname = Util.null2String(rs.getString("requestname"));
			String address = Util.null2String(rs.getString("address"));
			String type = typeName;
			RemindMode remind = new RemindMode();
			remind.setIid(requestid);
			remind.setName(requestname);
			remind.setAddress(address);
			remind.setType(type);
			RemindModes[c] = remind;
			c++;
		}
		return RemindModes;
	}

	/**
	 * 更新领导批示意见
	 * 
	 * @param loginname
	 * @param typeid
	 * @param typeName
	 * @return
	 */
	private void updateLDPSYJ(String loginname, String typeid, String typeName) {
		RecordSet rs = new RecordSet();
		String sql = "update uf_sendantu set flag = 0 where loginname = '" + loginname + "' and flag = 1 and typeid = "
				+ typeid + " ";
		rs.execute(sql);
	}

	private RemindMode[] getLCGDTX(String loginname, String typeid, String typeName) {
		String IP = getPropValue("SYSIP", "SYSIP");
		BaseBean bb = new BaseBean();
		String wfid = bb.getPropValue("FOW", "workflowid");
		String during = bb.getPropValue("FOW", "during");
		RecordSet rs = new RecordSet();
		String sql1 = "select id from hrmresource where loginid = '" + loginname + "'";
		writeLog("根据传入参数：" + loginname + "查询人员sql：" + sql1);
		rs.execute(sql1);
		rs.next();
		String id = Util.null2String(rs.getString("id"));

		String[] strs = wfid.split(",");
		List<RemindMode> list = new ArrayList<RemindMode>();
		for (String s : strs) {
			String tableName = getTableName(s);
			String sql = "";
			if ("59".equals(s)) {
				sql = "select distinct t1.createdate swrq,t1.createtime,t1.creater,t1.currentnodeid,t1.currentnodetype,"
						+ "  t1.lastoperator,t1.creatertype,t1.lastoperatortype,t1.lastoperatedate,"
						+ "  t1.lastoperatetime,t1.requestid,t1.requestname,t1.requestlevel,t1.workflowid,t2.receivedate,t2.receivetime "
						+ "  from workflow_requestbase t1,workflow_currentoperator t2 where t1.requestid=t2.requestid "
						+ "and t2.usertype = 0 and t2.userid = " + id
						+ " and t2.isremark in( '0','1','5','7') and t2.islasttimes=1 and t1.workflowid = " + s + " "
						+ "and to_char(add_months(to_date(t1.createdate,'yyyy-mm-dd')," + during
						+ ")-5,'yyyy-mm-dd') <= to_char(sysdate,'yyyy-mm-dd') "
						+ "and t1.CURRENTNODETYPE != 0 and t1.CURRENTNODETYPE != 3";
			} else {
				sql = "select distinct t2.userid,t3.swrq swrq,t1.createtime,t1.creater,t1.currentnodeid,t1.currentnodetype,"
						+ "  t1.lastoperator,t1.creatertype,t1.lastoperatortype,t1.lastoperatedate,"
						+ "  t1.lastoperatetime,t1.requestid,t1.requestname,t1.requestlevel,t1.workflowid,t2.receivedate,t2.receivetime "
						+ "  from workflow_requestbase t1,workflow_currentoperator t2," + tableName
						+ " t3 where t3.requestid = t1.requestid and t1.requestid=t2.requestid "
						+ "and t2.usertype = 0 and t2.userid = " + id
						+ " and t2.isremark in( '0','1','5','7') and t2.islasttimes=1 and t1.workflowid = " + s + " "
						+ "and to_char(add_months(to_date(t3.swrq,'yyyy-mm-dd')," + during
						+ ")-5,'yyyy-mm-dd') <= to_char(sysdate,'yyyy-mm-dd') "
						+ "and t1.CURRENTNODETYPE != 0 and t1.CURRENTNODETYPE != 3";
			}
			rs.execute(sql);
			while (rs.next()) {
				String reqid = Util.null2String(rs.getString("requestid"));
				String currentuserid = getCurrentUserid(reqid);
				String reqname = getReqName(reqid);
				String[] currentuserids = currentuserid.split(",");
				for (int i = 0; i < currentuserids.length; i++) {
					String addr = "" + IP + "/interface/portal/portal.jsp?typeid=flow-" + reqid + "";
					RemindMode remind = new RemindMode();
					remind.setAddress(addr);
					remind.setIid(reqid);
					remind.setName(reqname);
					remind.setType("流程归档提醒");
					list.add(remind);
				}
			}
		}
		RemindMode[] RemindModes = new RemindMode[list.size()];
		for (int i = 0; i < list.size(); i++) {
			RemindModes[i] = list.get(i);
		}
		return RemindModes;
	}

	public String getTableName(String workflowid) {
		RecordSet rs = new RecordSet();
		rs.execute("select formid from workflow_base where id = '" + workflowid + "'");
		rs.next();
		String formid = Util.null2String(rs.getString("formid"));
		return "formtable_main_" + Math.abs(Integer.parseInt(formid));
	}

	public String getCurrentUserid(String requestid) {
		RecordSet rs = new RecordSet();
		String sql = "select userid,isremark from WORKFLOW_CURRENTOPERATOR where requestid = " + requestid
				+ " order by RECEIVEDATE desc, RECEIVEDATE desc, OPERATEDATE desc, OPERATETIME desc";
		rs.execute(sql);
		StringBuffer sb = new StringBuffer(",");
		while (rs.next()) {
			String isremark = Util.null2String(rs.getString("isremark"));
			String userid = Util.null2String(rs.getString("userid"));
			if (!"2".equals(isremark)) {
				sb.append(userid + ",");
			}
		}
		String s = sb.toString();
		if (!",".equals(s)) {
			s = s.substring(1, s.length() - 1);
		}
		return s;
	}

	public String getReqName(String requestid) {
		RecordSet rs = new RecordSet();
		String sql = "select requestname from workflow_requestbase where requestid = '" + requestid + "'";
		rs.execute(sql);
		rs.next();
		String requestname = Util.null2String(rs.getString("requestname"));
		return requestname;
	}
}
