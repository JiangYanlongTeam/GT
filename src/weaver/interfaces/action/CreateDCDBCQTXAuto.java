package weaver.interfaces.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import weaver.conn.RecordSet;
import weaver.formmode.setup.ModeRightInfo;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.hrm.resource.ResourceComInfo;
import weaver.interfaces.schedule.BaseCronJob;
import weaver.workflow.webservices.WorkflowBaseInfo;
import weaver.workflow.webservices.WorkflowMainTableInfo;
import weaver.workflow.webservices.WorkflowRequestInfo;
import weaver.workflow.webservices.WorkflowRequestTableField;
import weaver.workflow.webservices.WorkflowRequestTableRecord;
import weaver.workflow.webservices.WorkflowService;
import weaver.workflow.webservices.WorkflowServiceImpl;

public class CreateDCDBCQTXAuto extends BaseCronJob{
	
	private static final String DCDBCQTXWORKFLOWID = "104";
	
	@Override
	public void execute() {
		ResourceComInfo resource = null;
		try {
			resource = new ResourceComInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String IP = getPropValue("SYSIP", "SYSIP");
		RecordSet rs = new RecordSet();
		RecordSet rs2 = new RecordSet();
		String cbrq = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String currentTime = new SimpleDateFormat("HH:mm").format(new Date());
		while (isHolidayOrWeekend(currentDate)) {
			currentDate = getIncomeDate3(getDateWithStr(currentDate, "yyyy-MM-dd"), 1);
		}
		String date1 = getDQR(currentDate, 3);
		String date2 = getDQR(currentDate, 5);
		String tableName = getPropValue("GWSWBL", "DCDBFORMTABLE");
		String sql1 = "select a.zbbm,a.cbrry,b.creater,(select departmentid from hrmresource where id = b.creater) departmentid,"
				+ "a.requestid,(select requestname from workflow_requestbase where requestid = a.requestid) requestname,a.blsx,a.field7 "
				+ "from "+tableName+" a,WORKFLOW_REQUESTBASE b where a.requestid = b.requestid and a.blsx <= 15 and a.field7 <= '"+date1+"' and b.currentnodetype != '3'";
		String sql2 = "select a.zbbm,a.cbrry,b.creater,(select departmentid from hrmresource where id = b.creater) departmentid,"
				+ "a.requestid,(select requestname from workflow_requestbase where requestid = a.requestid) requestname,a.blsx,a.field7 "
				+ "from "+tableName+" a,WORKFLOW_REQUESTBASE b where a.requestid = b.requestid and a.blsx > 15 and a.field7 <= '"+date2+"' and b.currentnodetype != '3'";
		rs.execute(sql1);
		while(rs.next()) {
			String zbbm = Util.null2String(Util.null2String(rs.getString("zbbm")));
			String currentBm = Util.null2String(Util.null2String(rs.getString("departmentid")));
			String cbrry = Util.null2String(Util.null2String(rs.getString("cbrry")));
			String requestid = Util.null2String(Util.null2String(rs.getString("requestid")));
			List<String> cbrrList = new ArrayList<String>();
			if(!"".equals(cbrry)) {
				String[] strs = cbrry.split(",");
				for(int i = 0; i < strs.length; i++) {
					cbrrList.add(strs[i]);
				}
			}
			//List<String> hrmList = getHrmID(zbbm);
			//List<String> nqList = getNQHrmID(zbbm);
			List<String> hrmList = getHrmID(currentBm);
			List<String> nqList = getNQHrmID(currentBm);
			Map<String,String> hrmMap = new HashMap<String,String>();
			StringBuffer sb = new StringBuffer(",");
			for(String hrm : hrmList) {
				hrmMap.put(hrm, hrm);
			}
			for(String hrm : nqList) {
				hrmMap.put(hrm, hrm);
			}
			for(String hrm : cbrrList) {
				hrmMap.put(hrm, hrm);
			}
			for(Entry<String,String> entry : hrmMap.entrySet()) {
				String key = entry.getKey();
				sb.append(key + ",");
			}
			
			String sbStri = sb.toString();
			sbStri = sbStri.substring(1, sbStri.length()-1);
			
			//您的公文公文收文测试20180122已超期，请尽快办理。
			String dxnr = Util.null2String(Util.null2String(rs.getString("requestname")));
			String dxnrs = "您的公文" + dxnr + "即将超期，请尽快办理。";
			String userid = "1";
			Map<String, String> map = new HashMap<String, String>();
			map.put("cbr", userid);
			map.put("cbrq", cbrq);
			map.put("cbnr", dxnrs);
			map.put("bcbr", sbStri);
			map.put("dblc", requestid);
			String result = executes(map);
			writeLog("requestid:"+requestid + "发起催办流程，返回结果："+result);
			
			String sql = "select * from uf_sendantu where iid = '"+requestid+"'";
			rs2.execute(sql);
			if(!rs2.next()) {
				String[] sjrs = sbStri.split(",");
				for(int i = 0; i < sjrs.length; i++) {
					String loginName = resource.getLoginID(sjrs[i]);
					String addr = ""+IP+"/interface/portal/portal.jsp?typeid=flow-" + requestid + "";
					String requestname = getRequestName(requestid);
					String typeid = "2";
					String flag = "1";
					insertRecordToLogTable(loginName, requestid, addr, requestname, typeid, flag, currentDate, currentTime);
				}
			}
		}
		rs.execute(sql2);
		while(rs.next()) {
			String zbbm = Util.null2String(Util.null2String(rs.getString("zbbm")));
			String currentBm = Util.null2String(Util.null2String(rs.getString("departmentid")));
			String cbrry = Util.null2String(Util.null2String(rs.getString("cbrry")));
			String requestid = Util.null2String(Util.null2String(rs.getString("requestid")));
			List<String> cbrrList = new ArrayList<String>();
			if(!"".equals(cbrry)) {
				String[] strs = cbrry.split(",");
				for(int i = 0; i < strs.length; i++) {
					cbrrList.add(strs[i]);
				}
			}
			//List<String> hrmList = getHrmID(zbbm);
			//List<String> nqList = getNQHrmID(zbbm);
			List<String> hrmList = getHrmID(currentBm);
			List<String> nqList = getNQHrmID(currentBm);
			Map<String,String> hrmMap = new HashMap<String,String>();
			StringBuffer sb = new StringBuffer(",");
			for(String hrm : hrmList) {
				hrmMap.put(hrm, hrm);
			}
			for(String hrm : nqList) {
				hrmMap.put(hrm, hrm);
			}
			for(String hrm : cbrrList) {
				hrmMap.put(hrm, hrm);
			}
			for(Entry<String,String> entry : hrmMap.entrySet()) {
				String key = entry.getKey();
				sb.append(key + ",");
			}
			
			String sbStri = sb.toString();
			sbStri = sbStri.substring(1, sbStri.length()-1);
			
			//您的公文公文收文测试20180122已超期，请尽快办理。
			String dxnr = Util.null2String(Util.null2String(rs.getString("requestname")));
			String dxnrs = "您的公文" + dxnr + "即将超期，请尽快办理。";
			String userid = "1";
			Map<String, String> map = new HashMap<String, String>();
			map.put("cbr", userid);
			map.put("cbrq", cbrq);
			map.put("cbnr", dxnrs);
			map.put("bcbr", sbStri);
			map.put("dblc", requestid);
			String result = executes(map);
			writeLog("requestid:"+requestid + "发起催办流程，返回结果："+result);
			
			String sql = "select * from uf_sendantu where iid = '"+requestid+"'";
			rs2.execute(sql);
			if(!rs2.next()) {
				String[] sjrs = sbStri.split(",");
				for(int i = 0; i < sjrs.length; i++) {
					String loginName = resource.getLoginID(sjrs[i]);
					String addr = ""+IP+"/interface/portal/portal.jsp?typeid=flow-" + requestid + "";
					String requestname = getRequestName(requestid);
					String typeid = "2";
					String flag = "1";
					insertRecordToLogTable(loginName, requestid, addr, requestname, typeid, flag, currentDate, currentTime);
				}
			}
		}
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
				+ "values ('" + loginname + "','" + iid + "','" + addrid + "','" + requestname + "','"
				+ typeid + "','"+flag+"','126','1','0','" + currentDate + "','" + currentTime + "')";
		writeLog("插入纪录表SQL：" + insertSQL);
		rs.execute(insertSQL);
		String selectMaxIdSQL = "select max(id) id from uf_sendantu";
		rs.execute(selectMaxIdSQL);
		rs.next();
		String id = rs.getString("id");
		ModeRightInfo ModeRightInfo = new ModeRightInfo();
		ModeRightInfo.editModeDataShare(1, 126, Integer.parseInt(id));
	}
	
	public String getRequestName(String requestid) {
		String sql = "select requestname from workflow_requestbase where requestid = '"+requestid+"'";
		RecordSet rs = new RecordSet();
		rs.execute(sql);
		rs.next();
		return Util.null2String(rs.getString("requestname"));
	}
	
	public String executes(Map<String, String> map) {
		try {
			String dcdbRequestid = createRequest(map);
			if (null != dcdbRequestid && Integer.parseInt(dcdbRequestid) > 0) {
				return "{\"message\":\"触发成功\"}";
			} else {
				return "{\"message\":\"触发失败，请联系管理员【状态码：" + dcdbRequestid + "】\"}";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"message\":\"触发失败，请联系管理员\"}";
		}
	}

	public String createRequest(Map<String, String> map) throws Exception {
		WorkflowRequestTableField[] wrti = new WorkflowRequestTableField[map.size()]; // 字段信息
		int count = 0;
		for (Entry<String, String> entry : map.entrySet()) {
			String key = entry.getKey();
			String val = entry.getValue();
			wrti[count] = new WorkflowRequestTableField();
			wrti[count].setFieldName(key);//
			wrti[count].setFieldValue(val);//
			wrti[count].setView(true);// 字段是否可见
			wrti[count].setEdit(true);// 字段是否可编辑
			count++;
		}

		WorkflowRequestTableRecord[] wrtri = new WorkflowRequestTableRecord[1];// 主字段只有一行数据
		wrtri[0] = new WorkflowRequestTableRecord();
		wrtri[0].setWorkflowRequestTableFields(wrti);

		WorkflowMainTableInfo wmi = new WorkflowMainTableInfo();
		wmi.setRequestRecords(wrtri);

		// 添加工作流id
		WorkflowBaseInfo wbi = new WorkflowBaseInfo();
		wbi.setWorkflowId(DCDBCQTXWORKFLOWID);// workflowid 流程接口演示流程2016==38

		WorkflowRequestInfo wri = new WorkflowRequestInfo();// 流程基本信息
		wri.setCreatorId(Util.null2String(map.get("cbr")));// 创建人id
		wri.setRequestLevel("0");// 0 正常，1重要，2紧急
		wri.setRequestName(Util.null2String(map.get("cbnr")));
		wri.setWorkflowMainTableInfo(wmi);// 添加主字段数据
		wri.setWorkflowBaseInfo(wbi);

		WorkflowService WorkflowServicePortTypeProxy = new WorkflowServiceImpl();
		String requestid = WorkflowServicePortTypeProxy.doCreateWorkflowRequest(wri,
				Integer.parseInt(Util.null2String(map.get("cbr"))));
		return requestid;
	}

	public void writeLog(Object object) {
		BaseBean bean = new BaseBean();
		bean.writeLog(object);
	}

	public String getPropValue(String s1, String s2) {
		BaseBean bean = new BaseBean();
		return bean.getPropValue(s1, s2);
	}

	public String getDQR(String currentDateTime, int days) {
		while (isHolidayOrWeekend(currentDateTime)) {
			currentDateTime = getIncomeDate3(getDateWithStr(currentDateTime, "yyyy-MM-dd"), 1);
		}
		for (int i = 0; i < days; i++) {
			if (i < days - 1) {
				currentDateTime = getIncomeDate3(getDateWithStr(currentDateTime, "yyyy-MM-dd"), 1);
			}
			while (isHolidayOrWeekend(currentDateTime)) {
				currentDateTime = getIncomeDate3(getDateWithStr(currentDateTime, "yyyy-MM-dd"), 1);
			}
		}
		currentDateTime = getIncomeDate3(getDateWithStr(currentDateTime, "yyyy-MM-dd"), 1);
		return currentDateTime;
	}

	

	public boolean isHolidayOrWeekend(String currentDateTime) {
		String week = getWeekOfDateNum(currentDateTime);
		int weekVal = Integer.valueOf(week);
		if (weekVal == 0 || weekVal == 6) {
			return true;
		}
		RecordSet rs = new RecordSet();
		rs.execute("select * from HrmPubHoliday where holidaydate = '" + currentDateTime + "'");
		rs.next();
		if (rs.next()) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		CreateDCDBCQTXAuto s = new CreateDCDBCQTXAuto();
		System.out.println(s.getDQR("2018-04-24", 3));
		System.out.println(s.getDQR("2018-04-24", 5));
	}
	
	/**
	 * 根据日期获得星期
	 * 
	 * @param date
	 * @return
	 */
	public static String getWeekOfDateNum(String date) {
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" };
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(s.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int intWeek = calendar.get(Calendar.DAY_OF_WEEK);
		return weekDaysCode[intWeek - 1];
	}

	/**
	 * 把字符串时间转换成日期
	 * 
	 * @param date
	 * @return
	 */
	public Date getDateWithStr(String date, String flag) {
		SimpleDateFormat s = new SimpleDateFormat(flag);
		Date d = null;
		try {
			d = s.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
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

		calendar.add(Calendar.DAY_OF_MONTH, +flag);

		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		return s.format(calendar.getTime());
	}
	
	public List<String> getHrmID(String zbbm) {
		RecordSet rs = new RecordSet();
		List<String> list = new ArrayList<String>();
		if("".equals(zbbm)) {
			return list;
		}
		String sql = "select id from hrmresource where id in (select m.cz from Matrixtable_4 m,HrmDepartment h where h.id=m.cs and h.id in("
				+ zbbm + "))";
		writeLog("根据主办部门：" + zbbm + "获取矩阵中对应人员：" + sql);
		rs.execute(sql);
		while (rs.next()) {
			String id = Util.null2String(rs.getString("id"));
			list.add(id);
		}
		return list;
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
}
