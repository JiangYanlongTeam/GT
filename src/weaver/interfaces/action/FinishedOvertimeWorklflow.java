package weaver.interfaces.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import weaver.conn.RecordSet;
import weaver.formmode.setup.ModeRightInfo;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.schedule.BaseCronJob;
import weaver.workflow.webservices.WorkflowBaseInfo;
import weaver.workflow.webservices.WorkflowMainTableInfo;
import weaver.workflow.webservices.WorkflowRequestInfo;
import weaver.workflow.webservices.WorkflowRequestTableField;
import weaver.workflow.webservices.WorkflowRequestTableRecord;
import weaver.workflow.webservices.WorkflowService;
import weaver.workflow.webservices.WorkflowServiceImpl;

/**
 * 强制归档超过期限的流程
 * 
 * @author jiangyanlong
 *
 */
public class FinishedOvertimeWorklflow extends BaseCronJob {

	@Override
	public void execute() {
//		String IP = getPropValue("SYSIP", "SYSIP");
//		ResourceComInfo resource = null;
//		try {
//			resource = new ResourceComInfo();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		BaseBean bb = new BaseBean();
//		String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//		String currentTime = new SimpleDateFormat("HH:mm").format(new Date());
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		String wfid = bb.getPropValue("FOW", "workflowid");
		String during = bb.getPropValue("FOW", "during");
//		String preduring = Util.null2String(Integer.parseInt(during) - 5);
		String[] strs = wfid.split(",");
		Date date = new Date();
		for (String t : strs) {
			String tableName = getTableName(t);
			String sql = "";
			if("59".equals(t)) {
				sql = "select a.requestid,b.createdate swrq,c.departmentid from " + tableName
						+ " a, workflow_requestbase b, hrmresource c where b.creater = c.id and a.requestid = b.requestid and b.currentnodetype != 3 and b.currentnodetype != 0";
			} else {
				sql = "select a.requestid,a.swrq,c.departmentid from " + tableName
						+ " a, workflow_requestbase b, hrmresource c where b.creater = c.id and a.requestid = b.requestid and b.currentnodetype != 3 and b.currentnodetype != 0 and a.swrq is not null";
			}
			rs.execute(sql);
			while (rs.next()) {
				String swrq = Util.null2String(rs.getString("swrq"));
				String reqid = Util.null2String(rs.getString("requestid"));
//				String departmentid = Util.null2String(rs.getString("departmentid"));
				try {
					String standard = getIncomeDate3(sim.parse(swrq), Integer.parseInt(during));
					if (sim.parse(standard).before(date)) {
						String currentuserid = getCurrentUserid(reqid);
						finishworkflow(reqid);
						String reqname = getReqName(reqid);
						sendtx("您的流程" + reqname + "已经超过办理期限，已经被强制归档", reqid, currentuserid);
						String sql2 = "update " + tableName + " set sfzcgd = '否，节点时间超过6个月，强制归档' where requestid = '"
								+ reqid + "'";
						rs1.execute(sql2);
					}
//					String standards = getIncomeDate3(sim.parse(swrq), Integer.parseInt(preduring));
//					if (sim.parse(standards).before(date)) {
//						String currentuserid = getCurrentUserid(reqid);
//						List<String> nquserid = getNQHrmID(departmentid);
//						StringBuffer sb = new StringBuffer();
//						if(!nquserid.isEmpty()) {
//							for(String s : nquserid) {
//								sb.append(s + ",");	
//							}
//						}
//						finishworkflow(reqid);
//						String reqname = getReqName(reqid);
//						currentuserid = currentuserid + sb.toString();
//						String[] currentuserids = currentuserid.split(",");
//						for (int i = 0; i < currentuserids.length; i++) {
//							String loginName = resource.getLoginID(currentuserids[i]);
//							String addr = "" + IP + "/interface/portal/portal.jsp?typeid=flow-" + reqid + "";
//							String typeid = "1";
//							String flag = "1";
//							insertRecordToLogTable(loginName, reqid, addr, "您的流程" + reqname + "即将超过办理期限，请尽快办理",
//									typeid, flag, currentDate, currentTime);
//						}
//					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (NullPointerException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public List<String> getNQHrmID(String zbbm) {
		RecordSet rs = new RecordSet();
		List<String> list = new ArrayList<String>();
		if("".equals(zbbm)) {
			return list;
		}
		String sql = "select id from hrmresource where id in (select m.nq from Matrixtable_3 m,HrmDepartment h where h.id=m.cs and h.id in("
				+ zbbm + "))";
		writeLog("根据主办部门：" + zbbm + "获取矩阵中对应人员：" + sql);
		rs.execute(sql);
		while (rs.next()) {
			String id = Util.null2String(rs.getString("id"));
			list.add(id);
		}
		return list;
	}
	
	/**
	 * 插入到纪录表
	 * 
	 * @param date
	 * @param time
	 * @param addrid
	 * @param responseID
	 * @param message
	 * @param jsonData
	 */
	public void insertRecordToLogTable(String loginname, String iid, String addrid, String requestname, String typeid,
			String flag, String currentDate, String currentTime) {
		RecordSet rs = new RecordSet();
		String insertSQL = "insert into uf_sendantu (loginname,iid,address,requestname,typeid,flag,formmodeid,modedatacreater,modedatacreatertype,modedatacreatedate,modedatacreatetime) "
				+ "values ('" + loginname + "','" + iid + "','" + addrid + "','" + requestname + "','" + typeid + "','"
				+ flag + "','126','1','0','" + currentDate + "','" + currentTime + "')";
		writeLog("插入纪录表SQL：" + insertSQL);
		rs.execute(insertSQL);
		String selectMaxIdSQL = "select max(id) id from uf_sendantu";
		rs.execute(selectMaxIdSQL);
		rs.next();
		String id = rs.getString("id");
		ModeRightInfo ModeRightInfo = new ModeRightInfo();
		ModeRightInfo.editModeDataShare(1, 126, Integer.parseInt(id));
	}

	public String getTableName(String workflowid) {
		RecordSet rs = new RecordSet();
		rs.execute("select formid from workflow_base where id = '" + workflowid + "'");
		rs.next();
		String formid = Util.null2String(rs.getString("formid"));
		return "formtable_main_" + Math.abs(Integer.parseInt(formid));
	}

	public void writeLog(Object object) {
		BaseBean bean = new BaseBean();
		bean.writeLog(object);
	}

	public String getPropValue(String s1, String s2) {
		BaseBean bean = new BaseBean();
		return bean.getPropValue(s1, s2);
	}

	/**
	 * 获取后一天时间
	 * 
	 * @param date
	 * @param flag
	 * @return
	 * @throws NullPointerException
	 */
	public static String getIncomeDate3(Date date, int flag) throws NullPointerException {
		if (null == date) {
			throw new NullPointerException("the date is null or empty!");
		}
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, +flag);
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		return s.format(calendar.getTime());
	}

	public void finishworkflow(String requestid) {
		ArrayList<String> list1 = new ArrayList<String>();
		list1.add(requestid);
		new WfForceOver().doForceOver(list1);
	}

	public String sendtx(String content, String requestid, String receiver) {
		// 主字段
		WorkflowRequestTableField[] wrti = new WorkflowRequestTableField[2]; // 字段信息

		wrti[0] = new WorkflowRequestTableField();
		wrti[0].setFieldName("request");//
		wrti[0].setFieldValue(requestid);//
		wrti[0].setView(true);// 字段是否可见
		wrti[0].setEdit(true);// 字段是否可编辑

		wrti[1] = new WorkflowRequestTableField();
		wrti[1].setFieldName("mutiresource");//
		wrti[1].setFieldValue(receiver);//
		wrti[1].setView(true);// 字段是否可见
		wrti[1].setEdit(true);// 字段是否可编辑

		WorkflowRequestTableRecord[] wrtri = new WorkflowRequestTableRecord[1];// 主字段只有一行数据
		wrtri[0] = new WorkflowRequestTableRecord();
		wrtri[0].setWorkflowRequestTableFields(wrti);

		WorkflowMainTableInfo wmi = new WorkflowMainTableInfo();
		wmi.setRequestRecords(wrtri);
		// mutiresource

		// 添加工作流id
		WorkflowBaseInfo wbi = new WorkflowBaseInfo();
		wbi.setWorkflowId("1");// workflowid 流程接口演示流程2016==38

		WorkflowRequestInfo wri = new WorkflowRequestInfo();// 流程基本信息
		wri.setCreatorId("1");// 创建人id
		wri.setRequestLevel("0");// 0 正常，1重要，2紧急
		wri.setWorkflowMainTableInfo(wmi);// 添加主字段数据
		wri.setWorkflowBaseInfo(wbi);
		wri.setRequestName(content);

		WorkflowService WorkflowServicePortTypeProxy = new WorkflowServiceImpl();
		String rqid = WorkflowServicePortTypeProxy.doCreateWorkflowRequest(wri, 1);
		return rqid;
	}

	public String getReqName(String requestid) {
		RecordSet rs = new RecordSet();
		String sql = "select requestname from workflow_requestbase where requestid = '" + requestid + "'";
		rs.execute(sql);
		rs.next();
		String requestname = Util.null2String(rs.getString("requestname"));
		return requestname;
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

	public static void main(String[] args) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		try {
			System.out.println(getIncomeDate3(sim.parse("2017-08-02"), 6));
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
