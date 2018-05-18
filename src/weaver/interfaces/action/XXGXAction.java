package weaver.interfaces.action;

import com.weaver.cssRenderHandler.doc.CheckboxColorRender;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.Property;
import weaver.soa.workflow.request.RequestInfo;

public class XXGXAction extends BaseBean implements Action {

	public String execute(RequestInfo request) {
		
		RecordSet rs = new RecordSet();
		// 取主表数据
		String requestid = request.getRequestid();
		Property[] properties = request.getMainTableInfo().getProperty();// 获取表单主字段信息
		String xgks = "";// 会议日期
		String reqprojectid = "";
		for (int i = 0; i < properties.length; i++) {
			String name = properties[i].getName();// 主字段名称
			String value = Util.null2String(properties[i].getValue());// 主字段对应的值
			if (name.equals("xgks")) {
				xgks = value;
			}
			if (name.equals("reqprojectid")) {
				reqprojectid = value;
			}
		}
		writeLog("xgks:"+xgks);
		writeLog("reqprojectid:"+reqprojectid);
		
		String sql = "update FnaExpenseInfo set relatedcrm = '" + xgks + "', relatedprj = '" + reqprojectid + "' where sourcerequestid = '" + requestid + "'";
		rs.executeSql(sql);
		writeLog("XXGXAction拼接的更新sql为：" + sql);
		
		return Action.SUCCESS;
	}
}
