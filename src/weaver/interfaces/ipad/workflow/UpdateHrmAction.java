package weaver.interfaces.ipad.workflow;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.Property;
import weaver.soa.workflow.request.RequestInfo;

public class UpdateHrmAction extends BaseBean implements Action {

	@Override
	public String execute(RequestInfo request) {
		String requestid = request.getRequestid();
		// 项目编号的值
		String xmb_value = "";
		// 项目编号字段
		String xmb_column = "receiveloginid";

		Property[] properties = request.getMainTableInfo().getProperty();// 获取主表单字段信息
		for (int i = 0; i < properties.length; i++) {
			String name = properties[i].getName();// 主字段名称
			String value = Util.null2String(properties[i].getValue());// 主字段对应的值
			if (name.equals(xmb_column)) {// 判断是不是主表单的项目的编号
				xmb_value = value;// 如果是就把获得的值传给xmb_value
			}
		}

		RecordSet rs = new RecordSet();
		StringBuffer sb = new StringBuffer(",");
		writeLog("传入登录账号:"+xmb_value);
		if (!"".equals(Util.null2String(xmb_value))) {
			String[] strs = xmb_value.split(",");
			for (int i = 0; i < strs.length; i++) {
				rs.execute("select id from hrmresource where loginid = '" + strs[i] + "'");
				rs.next();
				String id = Util.null2String(rs.getString("id"));
				if(!"".equals(id)) {
					sb.append(id);
					sb.append(",");
				}
			}
		}
		String sbs = sb.toString();
		if (!",".equals(sbs)) {
			sbs = sbs.substring(1, sbs.length() - 1);
			writeLog("获取人员ID:"+sbs);
			rs.execute(
					"select formid from workflow_base where id = (select workflowid from workflow_requestbase where REQUESTID = '"
							+ requestid + "')");
			rs.next();
			String formid = Util.null2String(rs.getString("formid"));
			String tableName = "formtable_main_" + Math.abs(Integer.parseInt(formid));
			String sql = "update " + tableName + " set xyjdjsr = '" + sbs + "' where requestid = '" + requestid + "'";
			writeLog("更新下一节点操作人员:"+sql);
			rs.execute(sql);
		}
		return SUCCESS;
	}

}
