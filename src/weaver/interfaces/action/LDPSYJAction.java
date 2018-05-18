package weaver.interfaces.action;

import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * 领导批示意见
 * 
 * @author jiangyanlong
 *
 */
public class LDPSYJAction extends BaseBean implements Action {

	@Override
	public String execute(RequestInfo request) {
		String iid = Util.null2String(request.getRequestid());
		String loginname = "";
		String requestname = "";
		String typeid = "3";
		String flag = "1";
		
		
		
		return null;
	}
}
