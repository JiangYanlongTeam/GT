package weaver.interfaces.action;

import java.util.HashMap;
import java.util.Map;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;

/**
 * 流程流转意见
 * 
 * @author jiangyanlong
 *
 */
public class WorkflowSign extends BaseBean{

	public static Map<String, String> map = new HashMap<String, String>();
	
	public static Map<String, String> map1 = new HashMap<String, String>();

	public String getWorkflowSignHtml(String requestid) {
		init();
		RecordSet rs = new RecordSet();
		String sql = "select distinct a.REQUESTID,a.OPERATEDATE,a.OPERATETIME,a.NODEID ,"
				+ "(select nodename from WORKFLOW_NODEBASE where id = a.NODEID) nodename,"
				+ "(select lastname from hrmresource where id = a.OPERATOR) lastname,a.OPERATOR," + "a.LOGTYPE isremark "
				+ "from WORKFLOW_REQUESTLOG a WHERE " + "a.REQUESTID = " + requestid
				+ " order by a.OPERATEDATE asc, a.OPERATETIME asc";
		rs.execute(sql);
		writeLog("WorkflowSign.sql:"+sql);
		StringBuffer sb = new StringBuffer();
		sb.append("<div class=\"table-responsive\">");
		sb.append("<table class=\"table table-hover\">");
		sb.append("<thead>");
		sb.append("<tr>");
		sb.append("<th>节点</th>");
		sb.append("<th>操作人</th>");
		sb.append("<th>操作状态</th>");
		sb.append("<th>接收时间</th>");
		sb.append("<th>操作时间</th>");
		sb.append("</thead>");
		sb.append("<tbody>");
		while (rs.next()) {
			String nodename = Util.null2String(rs.getString("nodename"));
			String reqid = Util.null2String(rs.getString("requestid"));
			String userid = Util.null2String(rs.getString("OPERATOR"));
			String lastname = Util.null2String(rs.getString("lastname"));
			if ("".equals(lastname)) {
				lastname = "系统管理员";
			}
			String isremark = Util.null2String(rs.getString("isremark"));
			String mark = Util.null2String(map.get(isremark));
			String receivedatetime = "";
			String operatedatetime = "";

			// String RECEIVEDATE = Util.null2String(rs.getString("RECEIVEDATE"));
			// String RECEIVETIME = Util.null2String(rs.getString("RECEIVETIME"));
			String OPERATEDATE = Util.null2String(rs.getString("OPERATEDATE"));
			String OPERATETIME = Util.null2String(rs.getString("OPERATETIME"));
			TimeInfo info = getDateTime(reqid, userid, OPERATEDATE, OPERATETIME);
			String RECEIVEDATE = info.getDate();
			String RECEIVETIME = info.getTime();
			if ("".equals(RECEIVEDATE)) {
				receivedatetime = "";
			} else {
				receivedatetime = RECEIVEDATE + " " + RECEIVETIME;
			}
			if ("".equals(OPERATEDATE)) {
				operatedatetime = "";
			} else {
				operatedatetime = OPERATEDATE + " " + OPERATETIME;
			}
			sb.append("<tr>");
			sb.append("<td>" + nodename + "</td>");
			sb.append("<td>" + lastname + "</td>");
			sb.append("<td>" + mark + "</td>");
			sb.append("<td>" + receivedatetime + "</td>");
			sb.append("<td>" + operatedatetime + "</td>");
			sb.append("</tr>");
		}
		String sql2 = "select distinct b.NODENAME,a.REQUESTID,a.USERID,c.lastname,a.ISREMARK,a.RECEIVEDATE,a.RECEIVETIME,a.OPERATEDATE,a.OPERATETIME from WORKFLOW_CURRENTOPERATOR a,WORKFLOW_NODEBASE b,hrmresource c where c.id= a.USERID and a.nodeid = b.id and a.REQUESTID = "+requestid+" and a.ISREMARK = '0' order by a.RECEIVEDATE desc , a.RECEIVETIME desc";
		rs.execute(sql2);
		writeLog("WorkflowSign.sql:"+sql2);
		while (rs.next()) {
			String nodename = Util.null2String(rs.getString("nodename"));
			String lastname = Util.null2String(rs.getString("lastname"));
			if ("".equals(lastname)) {
				lastname = "系统管理员";
			}
			String isremark = Util.null2String(rs.getString("isremark"));
			String mark = Util.null2String(map1.get(isremark));
			String receivedatetime = "";
			String operatedatetime = "";

			String RECEIVEDATE = Util.null2String(rs.getString("RECEIVEDATE"));
			String RECEIVETIME = Util.null2String(rs.getString("RECEIVETIME"));
			String OPERATEDATE = Util.null2String(rs.getString("OPERATEDATE"));
			String OPERATETIME = Util.null2String(rs.getString("OPERATETIME"));
			if ("".equals(RECEIVEDATE)) {
				receivedatetime = "";
			} else {
				receivedatetime = RECEIVEDATE + " " + RECEIVETIME;
			}
			if ("".equals(OPERATEDATE)) {
				operatedatetime = "";
			} else {
				operatedatetime = OPERATEDATE + " " + OPERATETIME;
			}
			sb.append("<tr>");
			sb.append("<td>" + nodename + "</td>");
			sb.append("<td>" + lastname + "</td>");
			sb.append("<td>" + mark + "</td>");
			sb.append("<td>" + receivedatetime + "</td>");
			sb.append("<td>" + operatedatetime + "</td>");
			sb.append("</tr>");
		}

		return sb.toString();
	}

	public TimeInfo getDateTime(String reqid, String userid, String date, String time) {
		RecordSet rs = new RecordSet();
		rs.execute("select RECEIVEDATE,RECEIVETIME from WORKFLOW_CURRENTOPERATOR where requestid = '" + reqid
				+ "' and userid = '" + userid + "' and OPERATEDATE = '" + date + "' and OPERATETIME = '" + time + "'");
		rs.next();
		String RECEIVEDATE = Util.null2String(rs.getString("RECEIVEDATE"));
		String RECEIVETIME = Util.null2String(rs.getString("RECEIVETIME"));
		TimeInfo info = new TimeInfo();
		info.setDate(RECEIVEDATE);
		info.setTime(RECEIVETIME);
		return info;
	}

	class TimeInfo {
		private String date;
		private String time;

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}
	}

	public String getIsRemark(String id) {
		return Util.null2String(map.get(id));
	}

	public void init() {
		map.put("0", "批准");
		map.put("1", "保存");
		map.put("2", "提交");
		map.put("3", "退回");
		map.put("4", "重新打开");
		map.put("5", "删除");
		map.put("6", "激活");
		map.put("7", "转发");
		map.put("9", "批注");
		map.put("a", "意见征询");
		map.put("b", "意见征询回复");
		map.put("e", "强制归档");
		map.put("h", "转办");
		map.put("i", "干预");
		map.put("j", "转办反馈");
		map.put("s", "督办");
		map.put("t", "抄送");
		
		map1.put("0", "未操作");
		map1.put("1", "转发");
		map1.put("2", "已操作");
		map1.put("4", "归档");
		map1.put("5", "超时");
		map1.put("8", "抄送(不需提交)");
		map1.put("9", "抄送(需提交)");
		map1.put("a", "意见赠讯");
		map1.put("b", "回复");
		map1.put("h", "转办");
		map1.put("j", "转办提交");
	}

	public static void main(String[] args) {
		String requestid = "97";
		String sql = "select requestid,(select lastname from hrmresource where id = userid) lastname,"
				+ "workflowid,(select nodename from workflow_nodebase where id = nodeid) nodename,"
				+ "(case when VIEWTYPE = '0' then '<font color=''red''>未查看</font>' when VIEWTYPE = '-2' then '已查看' else '' end) VIEWTYPE,( case when isremark = '0' then '未提交' when isremark = '1' then '转发' when isremark = '2' then '已操作' "
				+ "when isremark = '4' then '归档' when isremark = '5' then '超时' when isremark = '8' then '抄送(不需提交)' when isremark = '9' then '抄送(需提交)' "
				+ "when isremark = 'a' then '意见征询' when isremark = 'b' then '回复' when isremark = 'h' then '转办' when isremark = 'j' then '转办提交' else '' end) isremark,"
				+ "RECEIVEDATE,RECEIVETIME,OPERATEDATE,OPERATETIME from WORKFLOW_CURRENTOPERATOR where requestid = "
				+ requestid + " order by RECEIVEDATE asc,RECEIVETIME asc,OPERATEDATE asc, OPERATETIME asc";
		System.out.println(sql);
	}
}
