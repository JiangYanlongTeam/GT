package weaver.interfaces.ipad.workflow;

import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.Property;
import weaver.soa.workflow.request.RequestInfo;

public class WFSubmitAction extends BaseBean implements Action {

	@Override
	public String execute(RequestInfo request) {
		String requestid = request.getRequestid();
		String iid_value = "";
		String iid_column = "iid";

		String wiid_value = "";
		String wiid_column = "wiid";

		String flowids_value = "";
		String flowids_column = "flowids";
		
		String xzry_value = "";
		String xzry_column = "xzry";
		
		Property[] properties = request.getMainTableInfo().getProperty();// 获取主表单字段信息
		for (int i = 0; i < properties.length; i++) {
			String name = properties[i].getName();// 主字段名称
			String value = Util.null2String(properties[i].getValue());// 主字段对应的值
			if (name.equals(flowids_column)) {
				flowids_value = value;
			}
			if (name.equals(xzry_column)) {
				xzry_value = value;
			}
			if (name.equals(iid_column)) {
				iid_value = value;
			}
			if (name.equals(wiid_column)) {
				wiid_value = value;
			}
		}

		writeLog("requestid:" + requestid + " iid:" + iid_value);
		writeLog("requestid:" + requestid + " wiid:" + wiid_value);
		writeLog("requestid:" + requestid + " flowids:" + flowids_value);
		writeLog("requestid:" + requestid + " xzry:" + xzry_value);

		WFSubmitInfo info = new WFSubmitInfo();
//		WFNextStepInfo in = info.getNextStepInfo(iid_value, wiid_value);
//		String flows = in.getFlows();
//		String users = in.getUSERS();

		String result = info.submit(iid_value, wiid_value, xzry_value, flowids_value);
		if (!"".equals(result)) {
			request.getRequestManager().setMessageid("Failed");
			request.getRequestManager().setMessagecontent("调用安图系统提交失败：" + result);
		}
		return SUCCESS;
	}
}
