package weaver.interfaces.ipad.workflow;

import java.rmi.RemoteException;

import org.tempuri.MyAndFwServiceSoapProxy;

import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.hrm.resource.ResourceComInfo;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.Property;
import weaver.soa.workflow.request.RequestInfo;

public class SubmitWorkflow extends BaseBean implements Action {

	@Override
	public String execute(RequestInfo request) {
		// 判断是否是提交的 如过不是提交 则返回
		String src = request.getRequestManager().getSrc();
		if (!"submit".equals(src)) {
			request.getRequestManager().setMessageid("Failed");
			request.getRequestManager().setMessagecontent("不是提交操作");
			return SUCCESS;
		}

		// 获取主表单里的对应字段
		// iid 安图业务编号 wiid 安图流程类型
		// qmrq 签名日期 qzyj 签字意见
		String iid_column = "iid";
		String iid_value = "";
		String wiid_column = "wiid";
		String wiid_value = "";
		String qmrq_column = "qmrq";
		String qmrq_value = "";
		String qzyj_column = "qzyj";
		String qzyj_value = "";
		String flowids_value = "";
		String flowids_column = "flowids";
		String xzry_value = "";
		String xzry_column = "xzry";

		Property[] properties = request.getMainTableInfo().getProperty();// 获取主表单字段信息
		for (int i = 0; i < properties.length; i++) {
			String name = properties[i].getName();// 主字段名称
			String value = Util.null2String(properties[i].getValue());// 主字段对应的值

			if (name.equals(iid_column)) {// 判断是不是主表单的业务编号
				iid_value = value;// 如果是就把获得的值传给iid_value
			}
			if (name.equals(wiid_column)) {// 判断是不是主表单的业务类型
				wiid_value = value;// 如果是就把获得的值传给iid_value
			}
			if (name.equals(qmrq_column)) {// 判断是不是主表单的签名日期
				qmrq_value = value;// 如果是就把获得的值传给iid_value
			}
			if (name.equals(qzyj_column)) {// 判断是不是主表单的签字意见
				qzyj_value = value;// 如果是就把获得的值传给iid_value
			}
			if (name.equals(flowids_column)) {// 判断是不是主表单的签字意见
				flowids_value = value;// 如果是就把获得的值传给iid_value
			}
			if (name.equals(xzry_column)) {// 判断是不是主表单的签字意见
				xzry_value = value;// 如果是就把获得的值传给iid_value
			}
		}

		// 判断是否获取到对应字段的值 是否存在
		if (iid_value.isEmpty() || wiid_value.isEmpty() || qmrq_value.isEmpty() || qzyj_value.isEmpty()) {
			request.getRequestManager().setMessageid("Failed");
			request.getRequestManager().setMessagecontent("信息不完善(签名日期，签字意见不能为空)，请检查后再提交");
			return SUCCESS;
		}
		// 获得当前操作者的ID 通过id获取操作者的信息
		String requestOperator = request.getLastoperator();
		ResourceComInfo res;
		String operator = "";
		try {
			res = new ResourceComInfo();
			operator = res.getLoginID(requestOperator);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			MyAndFwServiceSoapProxy proxy1 = new MyAndFwServiceSoapProxy();
			writeLog("iid_value：" + iid_value);
			writeLog("wiid_value：" + wiid_value);
			writeLog("users：" + xzry_value);
			writeLog("flows：" + flowids_value);
			writeLog("operators：" + operator);
			writeLog("qmrq_value：" + qmrq_value);
			writeLog("qzyj_value：" + qzyj_value);
			String result1 = proxy1.examinationAndApprova(iid_value, wiid_value, xzry_value, flowids_value, operator,
					qmrq_value, qzyj_value);
			if (!"".equals(result1)) {
				request.getRequestManager().setMessageid("Failed");
				request.getRequestManager().setMessagecontent("调用安图系统失败：" + result1);
				return SUCCESS;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
