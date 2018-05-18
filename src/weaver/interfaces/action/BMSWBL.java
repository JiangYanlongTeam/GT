package weaver.interfaces.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.hrm.User;
import weaver.interfaces.jiangyl.sfw.SFWUtil;

/**
 * 部门收文办理右侧
 */
public class BMSWBL extends BaseBean {

	/**
	 * 公文收文办理主流程获取子流程所有的触发情况
	 * 
	 * @param user
	 * @param otherparams
	 * @param request
	 * @param response
	 * @return
	 */
	public List<Map<String, String>> getData(User user, Map<String, String> otherparams, HttpServletRequest request,
			HttpServletResponse response) {

		String BMBLFORMTABLE = getPropValue("GWSWBL", "BMBLFORMTABLE");
		String BMBLWORKFLOWID = getPropValue("GWSWBL", "BMBLWORKFLOWID");

		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		String requestid = Util.null2String(otherparams.get("requestid"));
		String zhuban = Util.null2String(otherparams.get("zhuban"));
		String xieban = Util.null2String(otherparams.get("xieban"));
		String ismobile = Util.null2String(otherparams.get("ismobile"));
		RecordSet rs = new RecordSet();
		SFWUtil sf = new SFWUtil();
		if ("".equals(requestid)) {
			writeLog("获取requestid：" + requestid + "失败");
			return data;
		}

		String sql = "select formid from workflow_base where id = (select workflowid from workflow_requestbase where requestid = "
				+ requestid + ")";
		rs.execute(sql);
		rs.next();
		String formid = Util.null2String(rs.getString("formid"));
		String tableName = "formtable_main_" + Math.abs(Integer.parseInt(formid));
		String sql2 = "select " + zhuban + ", " + xieban + " from " + tableName + " where requestid = " + requestid
				+ "";
		rs.execute(sql2);
		rs.next();
		String zhubanren = Util.null2String(rs.getString(zhuban));
		String xiebanren = Util.null2String(rs.getString(xieban));
		if ("".equals(xiebanren)) {
			if (!"".equals(zhubanren)) {
				String sql5 = "select a.requestid,b.requestname,b.currentnodeid,'查看详情' status, c.nodename,f.departmentname,f.id from "
						+ BMBLFORMTABLE + " "
						+ "a,WORKFLOW_REQUESTBASE b,WORKFLOW_NODEBASE c,hrmresource d, hrmdepartment f where b.mainrequestid in (select mainrequestid from workflow_requestbase where requestid = '"
						+ requestid + "') and b.workflowid = '" + BMBLWORKFLOWID + "' and f.id in (" + zhubanren
						+ ") and a.requestid = b.requestid and c.id = b.currentnodeid and d.id = b.creater and d.departmentid = f.id order by f.showorder asc";
				rs.execute(sql5);
				while (rs.next()) {
					String reqid = Util.null2String(rs.getString("requestid"));
					String nodename = Util.null2String(rs.getString("nodename"));
					String requestname = Util.null2String(rs.getString("requestname"));
					String currentnodeid = Util.null2String(rs.getString("currentnodeid"));
					String status = "";
					if ("1".equals(ismobile)) {
						status = "<a href='/client.do?method=getpage&detailid=" + reqid + "' title='" + requestname
								+ "' target='_blank'>查看详情</a>";

					} else {
						status = "<a href='/proj/RequestView.jsp?requestid=" + reqid + "' title='" + requestname
								+ "' target='_blank'>查看详情</a>";
					}
					Map<String, String> jo = new HashMap<String, String>();
					String creater = sf.getLastNameByNodeIDAndRequestID(reqid, currentnodeid);
					String depname = sf.getDepartmentNameByNodeIDAndRequestID(reqid, currentnodeid);
					jo.put("czyj", status);
					jo.put("nodename", nodename);
					jo.put("creater", creater);
					jo.put("department", depname);
					data.add(jo);
				}
			}
		} else {
			if (!"".equals(zhubanren)) {
				String sql5_ = "select a.requestid,b.requestname,b.currentnodeid,'查看详情' status, c.nodename,f.departmentname,f.id from "
						+ BMBLFORMTABLE + " "
						+ "a,WORKFLOW_REQUESTBASE b,WORKFLOW_NODEBASE c,hrmresource d, hrmdepartment f where b.mainrequestid in (select mainrequestid from workflow_requestbase where requestid = '"
						+ requestid + "') and b.workflowid = '" + BMBLWORKFLOWID + "' and f.id in (" + zhubanren
						+ ") and a.requestid = b.requestid and c.id = b.currentnodeid and d.id = b.creater and d.departmentid = f.id order by f.showorder asc";
				rs.execute(sql5_);
				while (rs.next()) {
					String reqid = Util.null2String(rs.getString("requestid"));
					String nodename = Util.null2String(rs.getString("nodename"));
					String requestname = Util.null2String(rs.getString("requestname"));
					String currentnodeid = Util.null2String(rs.getString("currentnodeid"));
					String status = "";
					if ("1".equals(ismobile)) {
						status = "<a href='/client.do?method=getpage&detailid=" + reqid + "' title='" + requestname
								+ "' target='_blank'>查看详情</a>";

					} else {
						status = "<a href='/proj/RequestView.jsp?requestid=" + reqid + "' title='" + requestname
								+ "' target='_blank'>查看详情</a>";
					}
					Map<String, String> jo = new HashMap<String, String>();
					String creater = sf.getLastNameByNodeIDAndRequestID(reqid, currentnodeid);
					String depname = sf.getDepartmentNameByNodeIDAndRequestID(reqid, currentnodeid);
					jo.put("czyj", status);
					jo.put("nodename", nodename);
					jo.put("creater", creater);
					jo.put("department", depname);
					data.add(jo);
				}
				
				String sql5 = "select a.requestid,b.requestname,b.currentnodeid,'查看详情' status, c.nodename,f.departmentname,f.id from "
						+ BMBLFORMTABLE + " "
						+ "a,WORKFLOW_REQUESTBASE b,WORKFLOW_NODEBASE c,hrmresource d, hrmdepartment f where b.mainrequestid in (select mainrequestid from workflow_requestbase where requestid = '"
						+ requestid + "') and b.workflowid = '" + BMBLWORKFLOWID + "' and f.id not in (" + zhubanren
						+ ") and a.requestid = b.requestid and c.id = b.currentnodeid and d.id = b.creater and d.departmentid = f.id order by f.showorder asc";
				rs.execute(sql5);
				while (rs.next()) {
					String reqid = Util.null2String(rs.getString("requestid"));
					String nodename = Util.null2String(rs.getString("nodename"));
					String requestname = Util.null2String(rs.getString("requestname"));
					String currentnodeid = Util.null2String(rs.getString("currentnodeid"));
					String status = "";
					if ("1".equals(ismobile)) {
						status = "<a href='/client.do?method=getpage&detailid=" + reqid + "' title='" + requestname
								+ "' target='_blank'>查看详情</a>";

					} else {
						status = "<a href='/proj/RequestView.jsp?requestid=" + reqid + "' title='" + requestname
								+ "' target='_blank'>查看详情</a>";
					}
					Map<String, String> jo = new HashMap<String, String>();
					String creater = sf.getLastNameByNodeIDAndRequestID(reqid, currentnodeid);
					String depname = sf.getDepartmentNameByNodeIDAndRequestID(reqid, currentnodeid);
					jo.put("czyj", status);
					jo.put("nodename", nodename);
					jo.put("creater", creater);
					jo.put("department", depname);
					data.add(jo);
				}
			} else {
				String sql5 = "select a.requestid,b.requestname,b.currentnodeid,'查看详情' status, c.nodename,f.departmentname,f.id from "
						+ BMBLFORMTABLE + " "
						+ "a,WORKFLOW_REQUESTBASE b,WORKFLOW_NODEBASE c,hrmresource d, hrmdepartment f where b.mainrequestid in (select mainrequestid from workflow_requestbase where requestid = '"
						+ requestid + "') and b.workflowid = '" + BMBLWORKFLOWID + "' and a.requestid = b.requestid and c.id = b.currentnodeid and d.id = b.creater and d.departmentid = f.id order by f.showorder asc";
				rs.execute(sql5);
				while (rs.next()) {
					String reqid = Util.null2String(rs.getString("requestid"));
					String nodename = Util.null2String(rs.getString("nodename"));
					String requestname = Util.null2String(rs.getString("requestname"));
					String currentnodeid = Util.null2String(rs.getString("currentnodeid"));
					String status = "";
					if ("1".equals(ismobile)) {
						status = "<a href='/client.do?method=getpage&detailid=" + reqid + "' title='" + requestname
								+ "' target='_blank'>查看详情</a>";

					} else {
						status = "<a href='/proj/RequestView.jsp?requestid=" + reqid + "' title='" + requestname
								+ "' target='_blank'>查看详情</a>";
					}
					Map<String, String> jo = new HashMap<String, String>();
					String creater = sf.getLastNameByNodeIDAndRequestID(reqid, currentnodeid);
					String depname = sf.getDepartmentNameByNodeIDAndRequestID(reqid, currentnodeid);
					jo.put("czyj", status);
					jo.put("nodename", nodename);
					jo.put("creater", creater);
					jo.put("department", depname);
					data.add(jo);
				}
			}
		}
		return data;
	}
	
	/**
	 * 公文收文办理主流程获取子流程所有的触发情况
	 * 
	 * @param user
	 * @param otherparams
	 * @param request
	 * @param response
	 * @return
	 */
	public List<Map<String, String>> getData2(User user, Map<String, String> otherparams, HttpServletRequest request,
			HttpServletResponse response) {

		String BMBLFORMTABLE = getPropValue("GWSWBL", "BMBLFORMTABLE");
		String BMBLWORKFLOWID = getPropValue("GWSWBL", "BMBLWORKFLOWID");

		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		String requestid = Util.null2String(otherparams.get("requestid"));
		String zhuban = Util.null2String(otherparams.get("zhuban"));
		String xieban = Util.null2String(otherparams.get("xieban"));
		String ismobile = Util.null2String(otherparams.get("ismobile"));
		RecordSet rs = new RecordSet();
		SFWUtil sf = new SFWUtil();
		if ("".equals(requestid)) {
			writeLog("获取requestid：" + requestid + "失败");
			return data;
		}

		String sql = "select formid from workflow_base where id = (select workflowid from workflow_requestbase where requestid = "
				+ requestid + ")";
		rs.execute(sql);
		rs.next();
		String formid = Util.null2String(rs.getString("formid"));
		String tableName = "formtable_main_" + Math.abs(Integer.parseInt(formid));
		String sql2 = "select " + zhuban + ", " + xieban + " from " + tableName + " where requestid = " + requestid
				+ "";
		rs.execute(sql2);
		rs.next();
		String zhubanren = Util.null2String(rs.getString(zhuban));
		String xiebanren = Util.null2String(rs.getString(xieban));
		if ("".equals(xiebanren)) {
			if (!"".equals(zhubanren)) {
				String sql6 = "select a.requestid,b.requestname,b.currentnodeid,'查看详情' status, c.nodename,f.departmentname,f.id from formtable_main_39 "
						+ "a,WORKFLOW_REQUESTBASE b,WORKFLOW_NODEBASE c,hrmresource d, hrmdepartment f where b.mainrequestid in (select mainrequestid from workflow_requestbase where requestid = '"
						+ requestid + "') and b.workflowid = '63' and f.id in (" + zhubanren
						+ ") and a.requestid = b.requestid and c.id = b.currentnodeid and d.id = b.creater and d.departmentid = f.id order by f.showorder asc";
				rs.execute(sql6);
				while (rs.next()) {
					String reqid = Util.null2String(rs.getString("requestid"));
					String nodename = Util.null2String(rs.getString("nodename"));
					String requestname = Util.null2String(rs.getString("requestname"));
					String currentnodeid = Util.null2String(rs.getString("currentnodeid"));
					String status = "";
					if ("1".equals(ismobile)) {
						status = "<a href='/client.do?method=getpage&detailid=" + reqid + "' title='" + requestname
								+ "' target='_blank' style='color:red'>查看详情</a>";

					} else {
						status = "<a href='/proj/RequestView.jsp?requestid=" + reqid + "' title='" + requestname
								+ "' target='_blank' style='color:red'>查看详情</a>";
					}
					Map<String, String> jo = new HashMap<String, String>();
					String creater = sf.getLastNameByNodeIDAndRequestID(reqid, currentnodeid);
					String depname = sf.getDepartmentNameByNodeIDAndRequestID(reqid, currentnodeid);
					jo.put("czyj", status);
					jo.put("nodename", nodename);
					jo.put("creater", creater);
					jo.put("department", depname);
					data.add(jo);
				}
				
				
				
				String sql5 = "select a.requestid,b.requestname,b.currentnodeid,'查看详情' status, c.nodename,f.departmentname,f.id from "
						+ BMBLFORMTABLE + " "
						+ "a,WORKFLOW_REQUESTBASE b,WORKFLOW_NODEBASE c,hrmresource d, hrmdepartment f where b.mainrequestid in (select mainrequestid from workflow_requestbase where requestid = '"
						+ requestid + "') and b.workflowid = '" + BMBLWORKFLOWID + "' and f.id in (" + zhubanren
						+ ") and a.requestid = b.requestid and c.id = b.currentnodeid and d.id = b.creater and d.departmentid = f.id order by f.showorder asc";
				rs.execute(sql5);
				while (rs.next()) {
					String reqid = Util.null2String(rs.getString("requestid"));
					String nodename = Util.null2String(rs.getString("nodename"));
					String requestname = Util.null2String(rs.getString("requestname"));
					String currentnodeid = Util.null2String(rs.getString("currentnodeid"));
					String status = "";
					if ("1".equals(ismobile)) {
						status = "<a href='/client.do?method=getpage&detailid=" + reqid + "' title='" + requestname
								+ "' target='_blank'>查看详情</a>";

					} else {
						status = "<a href='/proj/RequestView.jsp?requestid=" + reqid + "' title='" + requestname
								+ "' target='_blank'>查看详情</a>";
					}
					Map<String, String> jo = new HashMap<String, String>();
					String creater = sf.getLastNameByNodeIDAndRequestID(reqid, currentnodeid);
					String depname = sf.getDepartmentNameByNodeIDAndRequestID(reqid, currentnodeid);
					jo.put("czyj", status);
					jo.put("nodename", nodename);
					jo.put("creater", creater);
					jo.put("department", depname);
					data.add(jo);
				}
			}
		} else {
			if (!"".equals(zhubanren)) {
				String sql6 = "select a.requestid,b.requestname,b.currentnodeid,'查看详情' status, c.nodename,f.departmentname,f.id from "
						+ BMBLFORMTABLE + " "
						+ "a,WORKFLOW_REQUESTBASE b,WORKFLOW_NODEBASE c,hrmresource d, hrmdepartment f where b.mainrequestid in (select mainrequestid from workflow_requestbase where requestid = '"
						+ requestid + "') and b.workflowid = '63' and f.id in (" + zhubanren
						+ ") and a.requestid = b.requestid and c.id = b.currentnodeid and d.id = b.creater and d.departmentid = f.id order by f.showorder asc";
				rs.execute(sql6);
				while (rs.next()) {
					String reqid = Util.null2String(rs.getString("requestid"));
					String nodename = Util.null2String(rs.getString("nodename"));
					String requestname = Util.null2String(rs.getString("requestname"));
					String currentnodeid = Util.null2String(rs.getString("currentnodeid"));
					String status = "";
					if ("1".equals(ismobile)) {
						status = "<a href='/client.do?method=getpage&detailid=" + reqid + "' title='" + requestname
								+ "' target='_blank' style='color:red'>查看详情</a>";

					} else {
						status = "<a href='/proj/RequestView.jsp?requestid=" + reqid + "' title='" + requestname
								+ "' target='_blank' style='color:red'>查看详情</a>";
					}
					Map<String, String> jo = new HashMap<String, String>();
					String creater = sf.getLastNameByNodeIDAndRequestID(reqid, currentnodeid);
					String depname = sf.getDepartmentNameByNodeIDAndRequestID(reqid, currentnodeid);
					jo.put("czyj", status);
					jo.put("nodename", nodename);
					jo.put("creater", creater);
					jo.put("department", depname);
					data.add(jo);
				}
				
				String sql5_ = "select a.requestid,b.requestname,b.currentnodeid,'查看详情' status, c.nodename,f.departmentname,f.id from "
						+ BMBLFORMTABLE + " "
						+ "a,WORKFLOW_REQUESTBASE b,WORKFLOW_NODEBASE c,hrmresource d, hrmdepartment f where b.mainrequestid in (select mainrequestid from workflow_requestbase where requestid = '"
						+ requestid + "') and b.workflowid = '" + BMBLWORKFLOWID + "' and f.id in (" + zhubanren
						+ ") and a.requestid = b.requestid and c.id = b.currentnodeid and d.id = b.creater and d.departmentid = f.id order by f.showorder asc";
				rs.execute(sql5_);
				while (rs.next()) {
					String reqid = Util.null2String(rs.getString("requestid"));
					String nodename = Util.null2String(rs.getString("nodename"));
					String requestname = Util.null2String(rs.getString("requestname"));
					String currentnodeid = Util.null2String(rs.getString("currentnodeid"));
					String status = "";
					if ("1".equals(ismobile)) {
						status = "<a href='/client.do?method=getpage&detailid=" + reqid + "' title='" + requestname
								+ "' target='_blank'>查看详情</a>";

					} else {
						status = "<a href='/proj/RequestView.jsp?requestid=" + reqid + "' title='" + requestname
								+ "' target='_blank'>查看详情</a>";
					}
					Map<String, String> jo = new HashMap<String, String>();
					String creater = sf.getLastNameByNodeIDAndRequestID(reqid, currentnodeid);
					String depname = sf.getDepartmentNameByNodeIDAndRequestID(reqid, currentnodeid);
					jo.put("czyj", status);
					jo.put("nodename", nodename);
					jo.put("creater", creater);
					jo.put("department", depname);
					data.add(jo);
				}
				
				String sql5 = "select a.requestid,b.requestname,b.currentnodeid,'查看详情' status, c.nodename,f.departmentname,f.id from "
						+ BMBLFORMTABLE + " "
						+ "a,WORKFLOW_REQUESTBASE b,WORKFLOW_NODEBASE c,hrmresource d, hrmdepartment f where b.mainrequestid in (select mainrequestid from workflow_requestbase where requestid = '"
						+ requestid + "') and b.workflowid = '" + BMBLWORKFLOWID + "' and f.id not in (" + zhubanren
						+ ") and a.requestid = b.requestid and c.id = b.currentnodeid and d.id = b.creater and d.departmentid = f.id order by f.showorder asc";
				rs.execute(sql5);
				while (rs.next()) {
					String reqid = Util.null2String(rs.getString("requestid"));
					String nodename = Util.null2String(rs.getString("nodename"));
					String requestname = Util.null2String(rs.getString("requestname"));
					String currentnodeid = Util.null2String(rs.getString("currentnodeid"));
					String status = "";
					if ("1".equals(ismobile)) {
						status = "<a href='/client.do?method=getpage&detailid=" + reqid + "' title='" + requestname
								+ "' target='_blank'>查看详情</a>";

					} else {
						status = "<a href='/proj/RequestView.jsp?requestid=" + reqid + "' title='" + requestname
								+ "' target='_blank'>查看详情</a>";
					}
					Map<String, String> jo = new HashMap<String, String>();
					String creater = sf.getLastNameByNodeIDAndRequestID(reqid, currentnodeid);
					String depname = sf.getDepartmentNameByNodeIDAndRequestID(reqid, currentnodeid);
					jo.put("czyj", status);
					jo.put("nodename", nodename);
					jo.put("creater", creater);
					jo.put("department", depname);
					data.add(jo);
				}
			} else {
				String sql5 = "select a.requestid,b.requestname,b.currentnodeid,'查看详情' status, c.nodename,f.departmentname,f.id from "
						+ BMBLFORMTABLE + " "
						+ "a,WORKFLOW_REQUESTBASE b,WORKFLOW_NODEBASE c,hrmresource d, hrmdepartment f where b.mainrequestid in (select mainrequestid from workflow_requestbase where requestid = '"
						+ requestid + "') and b.workflowid = '" + BMBLWORKFLOWID + "' and a.requestid = b.requestid and c.id = b.currentnodeid and d.id = b.creater and d.departmentid = f.id order by f.showorder asc";
				rs.execute(sql5);
				while (rs.next()) {
					String reqid = Util.null2String(rs.getString("requestid"));
					String nodename = Util.null2String(rs.getString("nodename"));
					String requestname = Util.null2String(rs.getString("requestname"));
					String currentnodeid = Util.null2String(rs.getString("currentnodeid"));
					String status = "";
					if ("1".equals(ismobile)) {
						status = "<a href='/client.do?method=getpage&detailid=" + reqid + "' title='" + requestname
								+ "' target='_blank'>查看详情</a>";

					} else {
						status = "<a href='/proj/RequestView.jsp?requestid=" + reqid + "' title='" + requestname
								+ "' target='_blank'>查看详情</a>";
					}
					Map<String, String> jo = new HashMap<String, String>();
					String creater = sf.getLastNameByNodeIDAndRequestID(reqid, currentnodeid);
					String depname = sf.getDepartmentNameByNodeIDAndRequestID(reqid, currentnodeid);
					jo.put("czyj", status);
					jo.put("nodename", nodename);
					jo.put("creater", creater);
					jo.put("department", depname);
					data.add(jo);
				}
			}
		}
		return data;
	}

	public List<String> getHrmID(String zbbm) {
		RecordSet rs = new RecordSet();
		List<String> list = new ArrayList<String>();
		if ("".equals(zbbm)) {
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
