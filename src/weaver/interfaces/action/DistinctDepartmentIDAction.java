package weaver.interfaces.action;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.Property;
import weaver.soa.workflow.request.RequestInfo;

public class DistinctDepartmentIDAction extends BaseBean implements Action {

	@Override
	public String execute(RequestInfo request) {
		String requestid = request.getRequestid();//获取主流程requestid
		RecordSet rs = new RecordSet();
		String tableNamme = request.getRequestManager().getBillTableName();
		String xxbm_value = "";
		String xxbm_column = "xbnq";

		String zzbm_value = "";
		String zbbm_column = "zbnq";

		String ysld_value="";
		String ysld_column="ysldxz";

		String cyld_value="";
		String cyld_column="cyldxz";

		String ys_value="";
		String ys_column="ysld";

		String cy_value="";
		String cy_column="cyld";

		String xb_value = "";
		String xb_column = "xbbm";

		String zb_value = "";
		String zb_column = "zbbm";

		String nbyj_value = "";
		String nbyj_column = "nbyj";

		String xgfj_value = "";
		String xgfj_column = "xgfj";



		Property[] properties = request.getMainTableInfo().getProperty();// 获取表单主字段信息
		for (int i = 0; i < properties.length; i++) {
			String name = properties[i].getName();// 主字段名称
			String value = Util.null2String(properties[i].getValue());// 主字段对应的值
			if (name.equals(xxbm_column)) {
				xxbm_value = value;
			}
			if (name.equals(zbbm_column)) {
				zzbm_value = value;
			}
			if (name.equals(ysld_column)) {
				ysld_value = value;
			}
			if (name.equals(cyld_column)) {
				cyld_value = value;
			}
			if (name.equals(ys_column)) {
				ys_value = value;
			}
			if (name.equals(cy_column)) {
				cy_value = value;
			}
			if (name.equals(xb_column)) {
				xb_value = value;
			}
			if (name.equals(zb_column)) {
				zb_value = value;
			}
			if (name.equals(nbyj_column)) {
				nbyj_value = value;
			}
			if (name.equals(xgfj_column)) {
				xgfj_value = value;
			}
		}

		Map<String, String> ysldMap = new HashMap<String, String>();
		if (!"".equals(ysld_value)) {
			String[] yslds = ysld_value.split(",");
			for (String ysld : yslds) {
				if (!"".equals(ysld)) {
					ysldMap.put(ysld, ysld);
				}
			}
		}

		Map<String, String> cyldMap = new HashMap<String, String>();
		if (!"".equals(cyld_value)) {
			String[] cylds = cyld_value.split(",");
			for (String cyld : cylds) {
				if (!"".equals(cyld)) {
					cyldMap.put(cyld, cyld);
				}
			}
		}

		Map<String, String> xbnqMap = new HashMap<String, String>();
		if (!"".equals(xxbm_value)) {
			String[] xbnqs = xxbm_value.split(",");
			for (String xbnq : xbnqs) {
				if (!"".equals(xbnq)) {
					xbnqMap.put(xbnq, xbnq);
				}
			}
		}

		Map<String,String> zbMap = new HashMap<String,String>();
		if (!"".equals(zzbm_value)) {
			String[] zzbms = zzbm_value.split(",");
			for (String zzbm : zzbms) {
				if (!"".equals(zzbm) && xbnqMap.containsKey(zzbm)) {
					xbnqMap.remove(zzbm);
				}
				if(!"".equals(zzbm)) {
					zbMap.put(zzbm,zzbm);
				}
			}
		}

		String sql1="select  creater ,requestid ,workflowid from workflow_requestbase where  mainrequestid = "+requestid;
		RecordSet rs1= new RecordSet();
		rs1.execute(sql1);
		while (rs1.next()) {
			String ccRequestid = Util.null2String(rs1.getString("requestid"));
			String creater = Util.null2String(rs1.getString("creater"));
			String workflowid = Util.null2String(rs1.getString("workflowid"));
			if(workflowid.equals("62")){//如果是公文收文办理（部门）流程更新此表
				new RecordSet().execute("update formtable_main_38 set ysld_new = '"+ys_value+"',cyld ='"+cy_value+"',zbbm='"+zb_value+"',xbbm='"+xb_value+"',nbyj='"+nbyj_value+"',xgfj='"+xgfj_value+"' where requestid ="+ccRequestid);
			}
			if(workflowid.equals("63")){//如果是公文收文办理督办流程，更新此表
				new RecordSet().execute("update formtable_main_39 set ysld = '"+ys_value+"',cyld ='"+cy_value+"',zbbm='"+zb_value+"',xbbm='"+xb_value+"',nbyj='"+nbyj_value+"',xgfj='"+xgfj_value+"' where requestid ="+ccRequestid);
			}
			if(zbMap.containsKey(creater)) {
				zbMap.remove(creater);
			}
			if(xbnqMap.containsKey(creater)) {
				xbnqMap.remove(creater);
			}
		}
		//过滤已提交的局领导
		String sql2 ="select  OPERATORID  from workflow_requestoperatelog  where  operatetype ='submit'  and nodeid = 282 and requestid ="+requestid;
		RecordSet rs2= new RecordSet();
		rs2.execute(sql2);
		while (rs2.next()) {
			String OPERATORID = Util.null2String(rs2.getString("OPERATORID"));
			if(ysldMap.containsKey(OPERATORID)) {
				ysldMap.remove(OPERATORID);
			}
			if(cyldMap.containsKey(OPERATORID)) {
				cyldMap.remove(OPERATORID);
			}
		}

		StringBuffer sb = new StringBuffer(",");
		for (Entry<String, String> entry : xbnqMap.entrySet()) {
			sb.append(entry.getKey());
			sb.append(",");
		}
		String sbs = sb.toString(); //xiebanren

		StringBuffer sb1 = new StringBuffer(",");
		for (Entry<String, String> entry : zbMap.entrySet()) {
			sb1.append(entry.getKey());
			sb1.append(",");
		}
		String sbs1 = sb1.toString(); //zhubanren

		StringBuffer sb2 = new StringBuffer(",");
		for (Entry<String, String> entry : ysldMap.entrySet()) {
			sb2.append(entry.getKey());
			sb2.append(",");
		}
		String sbs2 = sb2.toString(); //阅示领导

		StringBuffer sb3 = new StringBuffer(",");
		for (Entry<String, String> entry : cyldMap.entrySet()) {
			sb3.append(entry.getKey());
			sb3.append(",");
		}
		String sbs3 = sb3.toString(); //呈阅领导

		if (",".equals(sbs)) {
			//sbs = sbs.substring(1, sbs.length() - 1);
			String sql = "update " + tableNamme + " set xbnq = '' where requestid = '" + requestid + "'";
			writeLog("执行更新SQL：" + sql);
			rs.execute(sql);
		}else{
			sbs = sbs.substring(1, sbs.length() - 1);
			String sql = "update " + tableNamme + " set xbnq = '" + sbs + "' where requestid = '" + requestid + "'";
			writeLog("执行更新SQL：" + sql);
			rs.execute(sql);
		}
		if (",".equals(sbs1)) {
			//sbs1 = sbs1.substring(1, sbs1.length() - 1);
			String sql = "update " + tableNamme + " set zbnq = '' where requestid = '" + requestid + "'";
			writeLog("执行更新SQL：" + sql);
			rs.execute(sql);
		}else{
			sbs1 = sbs1.substring(1, sbs1.length() - 1);
			String sql = "update " + tableNamme + " set zbnq = '"+sbs1+"' where requestid = '" + requestid + "'";
			writeLog("执行更新SQL：" + sql);
			rs.execute(sql);
		}

		if (",".equals(sbs2)) {
			String sql = "update " + tableNamme + " set ysldxz = '' where requestid = '" + requestid + "'";
			writeLog("执行更新SQL：" + sql);
			rs.execute(sql);
		}else{
			sbs2 = sbs2.substring(1, sbs2.length() - 1);
			String sql = "update " + tableNamme + " set ysldxz = '"+sbs2+"' where requestid = '" + requestid + "'";
			writeLog("执行更新SQL：" + sql);
			rs.execute(sql);
		}

		if (",".equals(sbs3)) {
			String sql = "update " + tableNamme + " set cyldxz = '' where requestid = '" + requestid + "'";
			writeLog("执行更新SQL：" + sql);
			rs.execute(sql);
		}else{
			sbs3 = sbs3.substring(1, sbs3.length() - 1);
			String sql = "update " + tableNamme + " set cyldxz = '"+sbs3+"' where requestid = '" + requestid + "'";
			writeLog("执行更新SQL：" + sql);
			rs.execute(sql);
		}

		return SUCCESS;
	}
}
