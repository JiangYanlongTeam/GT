package weaver.interfaces.action;

import weaver.general.BaseBean;
import weaver.workflow.webservices.WorkflowService;
import weaver.workflow.webservices.WorkflowServiceImpl;

/**
 * 门户根据流程workflowid和当前人获取流程待办数量
 * 
 * @author jiangyanlong
 *
 */
public class WorkflowUtil extends BaseBean {

	/**
	 * 根据登录人和流程类型获取待办数量
	 * 
	 * @param workflowid
	 * @param userid
	 * @return
	 */
	public int getWorkflowCountByWorkflowidAndUserId(String workflowid, int userid) {
		WorkflowService WorkflowServicePortTypeProxy = new WorkflowServiceImpl();
        int count = WorkflowServicePortTypeProxy.getToDoWorkflowRequestCount(userid, null);
        //带查询条件查询，只能写关于这2个表的查询条件 workflow_requestbase t1,workflow_currentoperator t2
        //查询条件里面不需要写and
        String conditions[] = new String[1];
        conditions[0] = "   t1.workflowid in ( "+workflowid+" )";//创建人为24
        count = WorkflowServicePortTypeProxy.getToDoWorkflowRequestCount(userid, conditions);
        return count;
	}
}
