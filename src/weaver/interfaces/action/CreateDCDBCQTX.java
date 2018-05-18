package weaver.interfaces.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weaver.conn.RecordSet;
import weaver.formmode.setup.ModeRightInfo;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.hrm.resource.ResourceComInfo;
import weaver.workflow.webservices.WorkflowBaseInfo;
import weaver.workflow.webservices.WorkflowMainTableInfo;
import weaver.workflow.webservices.WorkflowRequestInfo;
import weaver.workflow.webservices.WorkflowRequestTableField;
import weaver.workflow.webservices.WorkflowRequestTableRecord;
import weaver.workflow.webservices.WorkflowService;
import weaver.workflow.webservices.WorkflowServiceImpl;

/**
 * 创建督查督办超期提醒流程
 * 
 * @author jiangyanlong
 *
 */
public class CreateDCDBCQTX extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String DCDBCQTXWORKFLOWID = "104";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String IP = getPropValue("SYSIP", "SYSIP");
		String sjr = Util.null2String(req.getParameter("sjr"));
		String dxnr = Util.null2String(req.getParameter("dxnr"));
		String userid = Util.null2String(req.getParameter("userid"));
		String requestid = Util.null2String(req.getParameter("requestid"));
		String cbrq = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		Map<String, String> map = new java.util.HashMap<String, String>();
		map.put("cbr", userid);
		map.put("cbrq", cbrq);
		map.put("cbnr", dxnr);
		map.put("bcbr", sjr);
		map.put("dblc", requestid);

		String result = execute(map);
		ResourceComInfo resource = null;
		try {
			resource = new ResourceComInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String currentTime = new SimpleDateFormat("HH:mm").format(new Date());
		String[] sjrs = sjr.split(",");
		for(int i = 0; i < sjrs.length; i++) {
			String loginName = resource.getLoginID(sjrs[i]);
			String addr = ""+IP+"/interface/portal/portal.jsp?typeid=flow-" + requestid + "";
			String requestname = getRequestName(requestid);
			String typeid = "2";
			String flag = "1";
			insertRecordToLogTable(loginName, requestid, addr, requestname, typeid, flag, currentDate, currentTime);
		}
		resp.setContentType("application/json; charset=utf-8");
		resp.getWriter().print(result.toString());
	}

	public String getRequestName(String requestid) {
		String sql = "select requestname from workflow_requestbase where requestid = '"+requestid+"'";
		RecordSet rs = new RecordSet();
		rs.execute(sql);
		rs.next();
		return Util.null2String(rs.getString("requestname"));
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
	
	public String execute(Map<String, String> map) {
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
}
