package weaver.interfaces.ipad.workflow;

import java.rmi.RemoteException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.workflow.webservices.WorkflowBaseInfo;
import weaver.workflow.webservices.WorkflowMainTableInfo;
import weaver.workflow.webservices.WorkflowRequestInfo;
import weaver.workflow.webservices.WorkflowRequestTableField;
import weaver.workflow.webservices.WorkflowRequestTableRecord;
import weaver.workflow.webservices.WorkflowService;
import weaver.workflow.webservices.WorkflowServiceImpl;

public class WFServiceImpl extends BaseBean implements WFService {

	@Override
	public String WFService(String WFXML) {
		JSONObject jsonObject = JSON.parseObject(WFXML);
		String iid = Util.null2String(jsonObject.get("iid")); // 安图的业务编号
		String wiid = Util.null2String(jsonObject.get("wiid")); // 安图的流程类型ID
		String lastwiid = Util.null2String(jsonObject.get("lastwiid")); // 安图的流程类型ID
		String wname = Util.null2String(jsonObject.get("wname")); // 安图的流程类型ID
		String submituser = Util.null2String(jsonObject.get("submituser")); // 安图的流程类型ID
		JSONArray jsonArray = jsonObject.getJSONArray("field"); // 泛微表单字段
		String creator = Util.null2String(jsonObject.get("creator")); // 泛微流程创建者
		String workflowid = Util.null2String(jsonObject.get("workflowid")); // 泛微流程类型ID
		String requestname = Util.null2String(jsonObject.get("requestname")); // 泛微流程标题
		String requestlevel = Util.null2String(jsonObject.get("requestlevel")); // 泛微流程紧急程度

		
		String hrmid = getHrmID(creator);
		if ("".equals(hrmid)) {
			return sendMessage("创建者" + creator + "在泛微OA中不存在", "");
		}
		if ("".equals(workflowid)) {
			return sendMessage("JSON中字段[workflowid]字段不能为空", "");
		}
		if ("".equals(requestname)) {
			return sendMessage("JSON中字段[requestname]字段不能为空", "");
		}
		if ("".equals(iid)) {
			return sendMessage("JSON中字段[iid]字段不能为空", "");
		}
		if ("".equals(wiid)) {
			return sendMessage("JSON中字段[wiid]字段不能为空", "");
		}
		if ("".equals(requestlevel)) {
			return sendMessage("JSON中字段[requestlevel]字段不能为空，0:正常，1:重要，2:紧急", "");
		}
		if ("".equals(lastwiid)) {
			return sendMessage("JSON中字段[lastwiid]字段不能为空", "");
		}
		if ("".equals(wname)) {
			return sendMessage("JSON中字段[wname]字段不能为空", "");
		}
		if ("".equals(submituser)) {
			return sendMessage("JSON中字段[submituser]字段不能为空", "");
		}
		
		
		// 通过wname 查询建模数据对应的泛微workflowid，查出来对应的业务审批表
		String sql = "select fw_workflowid from uf_ywsp_relation where at_workflowid = '"+wname+"'";
		RecordSet rs = new RecordSet();
		rs.execute(sql);
		rs.next();
		String fw_workflowid = Util.null2String(rs.getString("fw_workflowid"));
		rs.execute("select formid from workflow_base where id = '"+fw_workflowid+"'");
		rs.next();
		String formid = Util.null2String(rs.getString("formid"));
		String tableName = "formtable_main_" + Math.abs(Integer.parseInt(formid));
		// 通过iid和lastwiid查询业务表中是否存在 已经创建过的数据，如果存在则提交下去，提交人为submituser
		rs.execute("select * from " + tableName + " where iid = '"+iid+"' and wiid = '"+lastwiid+"'");
		boolean isSubmit = false;
		String reqid = "";
		String submituserid = "";
		if(rs.next()) {
			reqid = Util.null2String(rs.getString("requestid"));
			submituserid = getHrmID(submituser);
			try {
				isSubmit = submit(Integer.parseInt(reqid), Integer.parseInt(submituserid));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} else {
			isSubmit = true;
		}
		writeLog("提交："+isSubmit);
		if(!isSubmit) {
			return sendMessage("流程提交失败：业务编号为" + reqid + ",提交人登录账号为"+submituser+",提交人ID为"+submituserid+"", "");
		}
		WorkflowRequestTableField[] wrti = new WorkflowRequestTableField[jsonArray.size()]; // 字段信息
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject object = (JSONObject) jsonArray.get(i);
			String name = Util.null2String(object.get("name"));
			String value = Util.null2String(object.get("value"));
			wrti[i] = new WorkflowRequestTableField();
			wrti[i].setFieldName(name);//
			wrti[i].setFieldValue(value);//
			wrti[i].setView(true);// 字段是否可见
			wrti[i].setEdit(true);// 字段是否可编辑
		}

		WorkflowRequestTableRecord[] wrtri = new WorkflowRequestTableRecord[1];// 主字段只有一行数据
		wrtri[0] = new WorkflowRequestTableRecord();
		wrtri[0].setWorkflowRequestTableFields(wrti);
		WorkflowMainTableInfo wmi = new WorkflowMainTableInfo();
		wmi.setRequestRecords(wrtri);
		// 添加工作流id
		WorkflowBaseInfo wbi = new WorkflowBaseInfo();
		wbi.setWorkflowId(workflowid);// workflowid 流程接口演示流程2016==38
		WorkflowRequestInfo wri = new WorkflowRequestInfo();// 流程基本信息
		wri.setCreatorId(hrmid);// 创建人id
		wri.setRequestLevel(requestlevel);// 0 正常，1重要，2紧急
		wri.setRequestName(requestname);
		wri.setWorkflowMainTableInfo(wmi);// 添加主字段数据
		wri.setWorkflowBaseInfo(wbi);
		WorkflowService WorkflowServicePortTypeProxy = new WorkflowServiceImpl();
		String requestid = WorkflowServicePortTypeProxy.doCreateWorkflowRequest(wri, Integer.parseInt(hrmid));
		if (Integer.parseInt(requestid) < 0) {
			return sendMessage("创建流程失败，返回错误码" + requestid + "", "");
		}
		// {"workflowid":"261","creator":"LIM","requestlevel":"0","requestname":"测试","iid","111","wiid","111","field":[{"name":"xmmc","value":"项目名称"}]}
		return sendMessage("创建流程成功，返回流程编号" + requestid + "", requestid);
	}

	private String getHrmID(String loginid) {
		String sql = "select id from hrmresource where loginid = '" + loginid + "'";
		RecordSet rs = new RecordSet();
		rs.execute(sql);
		rs.next();
		return Util.null2String(rs.getString("id"));
	}

	private String sendMessage(String message, String requestid) {
		Response response = new Response();
		response.setRequestid(requestid);
		response.setMessage(message);
		return JSON.toJSON(response).toString();
	}
	
	private boolean submit(int requestid, int userid) throws RemoteException {
		WorkflowRequestInfo WorkflowRequestInfo = getRequestInfo(requestid, userid);
		WorkflowService WorkflowServicePortTypeProxy = new WorkflowServiceImpl();
		String str = WorkflowServicePortTypeProxy.submitWorkflowRequest(WorkflowRequestInfo, requestid, userid,
				"submit", "");
		return "success".equals(str);
	}
	
	/**
	 * 获得流程的详细信息
	 * 
	 * @param requestid
	 * @return
	 * @throws RemoteException
	 */
	public static WorkflowRequestInfo getRequestInfo(int requestid, int userid) throws RemoteException {
		WorkflowService WorkflowServicePortTypeProxy = new WorkflowServiceImpl();
		WorkflowRequestInfo WorkflowRequestInfo = WorkflowServicePortTypeProxy.getWorkflowRequest(requestid, userid, 0);// 调用接口获取对应requestid的数据
		return WorkflowRequestInfo;
	}
}
