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
		String requestid = request.getRequestid();
		RecordSet rs = new RecordSet();
		String tableNamme = request.getRequestManager().getBillTableName();
		String xxbm_value = "";
		String xxbm_column = "xbnq";

		String zzbm_value = "";
		String zbbm_column = "zbnq";

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

		if (!"".equals(zzbm_value)) {
			String[] zzbms = zzbm_value.split(",");
			for (String zzbm : zzbms) {
				if (!"".equals(zzbm) && xbnqMap.containsKey(zzbm)) {
					xbnqMap.remove(zzbm);
				}
			}
		}
		StringBuffer sb = new StringBuffer(",");
		for (Entry<String, String> entry : xbnqMap.entrySet()) {
			sb.append(entry.getKey());
			sb.append(",");
		}
		String sbs = sb.toString();
		if (!",".equals(sbs)) {
			sbs = sbs.substring(1, sbs.length() - 1);
			String sql = "update " + tableNamme + " set xbnq = '" + sbs + "' where requestid = '" + requestid + "'";
			writeLog("执行更新SQL：" + sql);
			rs.execute(sql);
		}
		return SUCCESS;
	}
}
