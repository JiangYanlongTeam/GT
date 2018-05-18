package weaver.interfaces.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.tempuri.ServiceSoapProxy;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.hrm.company.DepartmentComInfo;
import weaver.hrm.resource.ResourceComInfo;
import weaver.workflow.webservices.WorkflowBaseInfo;
import weaver.workflow.webservices.WorkflowMainTableInfo;
import weaver.workflow.webservices.WorkflowRequestInfo;
import weaver.workflow.webservices.WorkflowRequestTableField;
import weaver.workflow.webservices.WorkflowRequestTableRecord;
import weaver.workflow.webservices.WorkflowService;
import weaver.workflow.webservices.WorkflowServiceImpl;
import weaver.workflow.workflow.WfForceOver;

/**
 * 创建督查督办流程
 * 
 * @author jiangyanlong
 *
 */
public class CreateDCDB extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public final static String DCDBWORKFLOWID = "63";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String swrq = Util.null2String(req.getParameter("swrq"));
		String lwdw = Util.null2String(req.getParameter("lwdw"));
		String bt = Util.null2String(req.getParameter("bt"));
		String wh = Util.null2String(req.getParameter("wh"));
		String jbdw = Util.null2String(req.getParameter("jbdw"));
		String jbdw_old = Util.null2String(req.getParameter("jbdw_old"));
		String blqx = Util.null2String(req.getParameter("blqx"));
		String dqr = Util.null2String(req.getParameter("dqr"));
		
		String swlx = Util.null2String(req.getParameter("swlx"));
		String jbnbyj = Util.null2String(req.getParameter("jbnbyj"));

		RecordSet rs = new RecordSet();
		String gwswtable = getPropValue("DCDB", "GWSWBL_TABELNAME");
		String sql = "select xgfj,ysld,cyld,xbbm,bgszrqz,bgszrqzrq from " + gwswtable + " where requestid = '"+bt+"'";
		rs.execute(sql);
		rs.next();
		String xgfj = Util.null2String(rs.getString("xgfj"));
		String ysld = Util.null2String(rs.getString("ysld"));
		String yld = Util.null2String(rs.getString("cyld"));
		String xbbm = Util.null2String(rs.getString("xbbm"));
		String bgszrqm = Util.null2String(rs.getString("bgszrqz"));
		String bgszrqzrq = Util.null2String(rs.getString("bgszrqzrq"));
		
		
		
		Map<String, String> map = new java.util.HashMap<String, String>();
		map.put("field5", swlx);
		map.put("nbyj", jbnbyj);
		map.put("field1", swrq);
		map.put("field2", lwdw);
		map.put("zlcid", bt);
		map.put("field3", wh);
		map.put("zbbm", jbdw);
		map.put("blsx", blqx);
		map.put("field7", dqr);
		map.put("yqcs", "0");
		map.put("sfcq", "0");
		map.put("xgfj", xgfj);
		map.put("ysld", ysld);
		map.put("yld", yld);
		map.put("xbbm", xbbm);
		map.put("bgszrqm", bgszrqm);
		map.put("bgszrqmrq", bgszrqzrq);

		String result = execute(map, jbdw_old, jbdw, req, resp);
		resp.setContentType("application/json; charset=utf-8");
		resp.getWriter().print(result.toString());
	}

	public String execute(Map<String, String> map, String jbdw_old, String zbbm, HttpServletRequest req,
			HttpServletResponse resp) {
		List<String> fjList = fjList();
		boolean createFlag = true;
		String dcdbRequestid = "";
		String zlcid = Util.null2String(map.get("zlcid"));
		writeLog("交办部门：" + zbbm);
		writeLog("主办部门OLD：" + jbdw_old);
		writeLog("主流程REQUESTID：" + zlcid);
		if ("".equals(zbbm)) {
			writeLog("触发督查督办流程失败：交办部门不能为空");
			return "{\"message\":\"触发督查督办流程失败：交办部门不能为空\"}";
		}
		try {
			List<String> hrmids = getHrmID(zbbm);
			List<String> dblist = new ArrayList<String>();
			writeLog("主办部门对应人员：" + hrmids.toString());
			for (String id : hrmids) {
				dcdbRequestid = createRequest(map, id, zlcid);
				writeLog("创建督办流程返回REQUESTID：" + dcdbRequestid);
				if (Integer.parseInt(dcdbRequestid) > 0) {
					updateMainWorkflow(zlcid, dcdbRequestid);
					dblist.add(dcdbRequestid);
				} else {
					createFlag = false;
					break;
				}
			}
			if (hrmids.isEmpty()) {
				writeLog("触发督查督办流程失败：根据主办部门：" + zbbm + "再矩阵中没有人员");
				return "{\"message\":\"触发督查督办流程失败：根据主办部门：" + zbbm + "再矩阵中没有人员\"}";
			}
			if (createFlag) {
				StringBuffer sb = new StringBuffer(",");
				StringBuffer sb1 = new StringBuffer(",");
				for (String id : hrmids) {
					sb.append(id + ",");
				}
				String sbs = sb.toString();
				sbs = sbs.substring(1, sbs.length() - 1);
				for(String s : dblist) {
					sb1.append(s + ",");
				}
				String sbss = sb1.toString();
				sbss = sbss.substring(1, sbss.length() - 1);
				RecordSet rs = new RecordSet();
				RecordSet rs1 = new RecordSet();
				String BMBLFORMTABLE = getPropValue("GWSWBL", "BMBLFORMTABLE");
				String sqls = "select a.requestid from " + BMBLFORMTABLE
						+ " a, workflow_requestbase b where a.requestid = b.requestid and b.creater in (" + sbs
						+ ") and b.mainrequestid = '" + zlcid + "' and a.requestid not in ("+sbss+") ";
				writeLog("根据选择的交办部门找对应部门办理流程中是公文收文办理[" + zlcid + "]的子流程sql：" + sqls);
				rs.execute(sqls);
				List<String> list = new ArrayList<String>();
				while (rs.next()) {
					String requestid = Util.null2String(rs.getString("requestid"));
					ArrayList<String> list1 = new ArrayList<String>();
					list1.add(requestid);
					new WfForceOver().doForceOver(list1, req, resp);
					doForceOverSign(list1);

					String sql = "select a.bmcz,a.cbr,b.creater,c.departmentid from " + BMBLFORMTABLE
							+ " a , workflow_requestbase b,hrmresource c where c.id = b.creater and a.requestid = b.requestid and a.requestid = '"
							+ requestid + "'";

					rs1.execute(sql);
					rs1.next();
					String bmcz = Util.null2String(rs1.getString("bmcz"));
					String cbr = Util.null2String(rs1.getString("cbr"));
					String creater = Util.null2String(rs1.getString("creater"));
					String departmentid = Util.null2String(rs1.getString("departmentid"));
					if (!"".equals(bmcz)) {
						creater += "," + bmcz;
					}
					if (!"".equals(cbr)) {
						creater += "," + cbr;
					}
					createXXMR("您的部门收文办理流程已被督办，原部门收文办理流程已终结，请前往督办流程继续办理！", requestid, creater);
					if (fjList.contains(departmentid)) {
						list.add(requestid + "," + departmentid);
					}
				}
				updateZBBM(zbbm, zlcid);
				ServiceSoapProxy pro = new ServiceSoapProxy();
				if (!list.isEmpty()) {
					for (int i = 0; i < list.size(); i++) {
						String reqid = list.get(i).split(",")[0];
						String depid = list.get(i).split(",")[1];
						String str = "";
						if ("51".equals(depid)) {
							str = "LHFJ";
						}
						if ("54".equals(depid)) {
							str = "PKFJ";
						}
						if ("44".equals(depid)) {
							str = "JNFJ";
						}
						if ("49".equals(depid)) {
							str = "LSFJ";
						}
						String s = pro.DBWorkflowFj(reqid, str);
						if (!"督办成功".equals(s)) {
							writeLog("督办流程requestid:" + reqid + ",在安图流程中督办失败");
						}
					}
				}
			} else {
				writeLog("触发督查督办流程失败：" + dcdbRequestid);
				return "{\"message\":\"触发失败，请联系管理员,错误码：" + dcdbRequestid + "\"}";
			}
		} catch (Exception e) {
			e.printStackTrace();
			writeLog("触发督查督办流程失败：" + e.getMessage());
			return "{\"message\":\"触发失败，请联系管理员\"}";
		}
		return "{\"message\":\"触发成功\"}";
	}

	public String createRequest(Map<String, String> map, String creater, String mainrequestid) throws Exception {
		RecordSet rs = new RecordSet();
		String reqnamesql = "select requestname from workflow_requestbase where requestid = '" + mainrequestid + "'";
		rs.execute(reqnamesql);
		rs.next();
		String requestname = Util.null2String(rs.getString("requestname"));
		ResourceComInfo r = new ResourceComInfo();
		String dep = r.getDepartmentID(creater);
		map.put("sqdbbm", dep);
		DepartmentComInfo de = new DepartmentComInfo();
		String depname = de.getDepartmentname(dep);
		map.put("sqdbbmmc", depname);
		// 主字段
		WorkflowRequestTableField[] wrti = new WorkflowRequestTableField[map.size()]; // 字段信息
		writeLog("创建督查督办流程数据："+map.toString());
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
		wbi.setWorkflowId(DCDBWORKFLOWID);// workflowid 流程接口演示流程2016==38

		WorkflowRequestInfo wri = new WorkflowRequestInfo();// 流程基本信息
		wri.setCreatorId(creater);// 创建人id
		wri.setRequestLevel("0");// 0 正常，1重要，2紧急
		wri.setRequestName(requestname);
		wri.setWorkflowMainTableInfo(wmi);// 添加主字段数据
		wri.setWorkflowBaseInfo(wbi);
		wri.setIsnextflow("0");

		WorkflowService WorkflowServicePortTypeProxy = new WorkflowServiceImpl();
		String requestid = WorkflowServicePortTypeProxy.doCreateWorkflowRequest(wri, Integer.parseInt(creater));
		return requestid;
	}

	public List<String> getHrmID(String zbbm) {
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

	public void updateMainWorkflow(String zlcid, String requestid) {
		RecordSet rs = new RecordSet();
		String sql = "update workflow_requestbase set mainrequestid = '" + zlcid + "' where requestid = '" + requestid
				+ "'";
		writeLog("跟新子流程MAINREQUESTID字段：" + sql);
		rs.execute(sql);
		String gwswtable = getPropValue("DCDB", "GWSWBL_TABELNAME");
		String sql2 = "update " + gwswtable + " set sfdb = '1' where requestid = '"+zlcid+"'";
		writeLog("跟新主流程sfdb字段：1 " + sql2);
		rs.execute(sql2);
	}

	public void updateZBBM(String zbbm, String zlcid) {
		String gwswtable = getPropValue("DCDB", "GWSWBL_TABELNAME");
		String bmbltablename = getPropValue("DCDB", "BMBL_TABELNAME");
		RecordSet rs = new RecordSet();
		String sql = "update " + gwswtable + " set zbbm = '" + zbbm + "' where requestid = '" + zlcid + "'";
		writeLog("更新主办部门1:"+sql);
		rs.execute(sql);
		String sql1 = "update " + bmbltablename + " set zbbm = '" + zbbm
				+ "' where requestid in (select requestid from workflow_requestbase where mainrequestid = '" + zlcid
				+ "')";
		writeLog("更新主办部门2:"+sql1);
		rs.execute(sql1);

		String sql2 = "update " + gwswtable + " set sfcfdb = '0' where requestid = '" + zlcid + "'";
		writeLog("更新主办部门3:"+sql2);
		rs.execute(sql2);
	}

	public ArrayList<String> getAllDTFLOW(String requestid, String workflowid) {
		RecordSet rs = new RecordSet();
		String sql = "select requestid from workflow_requestbase where mainrequestid = '" + requestid
				+ "' and workflowid = '" + workflowid + "'";
		rs.execute(sql);
		ArrayList<String> list = new ArrayList<String>();
		while (rs.next()) {
			String reqid = Util.null2String(rs.getString("requestid"));
			list.add(reqid);
		}
		return list;
	}

	public String createXXMR(String content, String requestid, String txr) {
		// 主字段
		WorkflowRequestTableField[] wrti = new WorkflowRequestTableField[3]; // 字段信息

		wrti[0] = new WorkflowRequestTableField();
		wrti[0].setFieldName("txnr");//
		wrti[0].setFieldValue(content);//
		wrti[0].setView(true);// 字段是否可见
		wrti[0].setEdit(true);// 字段是否可编辑

		wrti[1] = new WorkflowRequestTableField();
		wrti[1].setFieldName("xglc");//
		wrti[1].setFieldValue(requestid);//
		wrti[1].setView(true);// 字段是否可见
		wrti[1].setEdit(true);// 字段是否可编辑

		wrti[2] = new WorkflowRequestTableField();
		wrti[2].setFieldName("txr");//
		wrti[2].setFieldValue(txr);//
		wrti[2].setView(true);// 字段是否可见
		wrti[2].setEdit(true);// 字段是否可编辑

		WorkflowRequestTableRecord[] wrtri = new WorkflowRequestTableRecord[1];// 主字段只有一行数据
		wrtri[0] = new WorkflowRequestTableRecord();
		wrtri[0].setWorkflowRequestTableFields(wrti);

		WorkflowMainTableInfo wmi = new WorkflowMainTableInfo();
		wmi.setRequestRecords(wrtri);

		// 添加工作流id
		WorkflowBaseInfo wbi = new WorkflowBaseInfo();
		wbi.setWorkflowId("101");// workflowid 流程接口演示流程2016==38

		WorkflowRequestInfo wri = new WorkflowRequestInfo();// 流程基本信息
		wri.setCreatorId("1");// 创建人id
		wri.setRequestLevel("0");// 0 正常，1重要，2紧急
		wri.setWorkflowMainTableInfo(wmi);// 添加主字段数据
		wri.setWorkflowBaseInfo(wbi);
		wri.setRequestName("部门收文办理流程强制归档提醒");

		WorkflowService WorkflowServicePortTypeProxy = new WorkflowServiceImpl();
		String rqid = WorkflowServicePortTypeProxy.doCreateWorkflowRequest(wri, 1);
		return rqid;
	}

	public void writeLog(Object object) {
		BaseBean bean = new BaseBean();
		bean.writeLog(object);
	}

	public String getPropValue(String s1, String s2) {
		BaseBean bean = new BaseBean();
		return bean.getPropValue(s1, s2);
	}

	public void doForceOverSign(ArrayList<String> list) {
		RecordSet rs = new RecordSet();
		String bmbltablename = getPropValue("DCDB", "BMBL_TABELNAME");
		for (String s : list) {
			String sql = "update " + bmbltablename + " set dbgdbj = '0' where requestid = '" + s + "'";
			rs.execute(sql);
		}
	}
	
	public List<String> fjList() {
		List<String> list = new ArrayList<String>();
		//list.add("44");
		list.add("54");
		list.add("51");
		//list.add("49");
		return list;
	}
}
