package weaver.interfaces.jiangyl.project;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.hrm.User;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.webservices.WorkflowRequestInfo;
import weaver.workflow.webservices.WorkflowService;
import weaver.workflow.webservices.WorkflowServiceImpl;
/**
 * * @author yangw at 20180105
 * 1、在督办的处室处长节点调用此接口，将主流程收文的转发给处长选择的承办人
 * 2、被转发的流程会出现在承办人的待办列表中，需要再执行一个自动提交的接口
 * 3、这里需要将主流程当前节点的操作人传过来，这里指定的是“部门文件传阅”
 */
public class WorkflowForward extends BaseBean implements Action {

	public String execute(RequestInfo requestinfo) {
		writeLog("进入节点后操作流程转办开始 requestid ="+requestinfo.getRequestid());
		User usr = requestinfo.getRequestManager().getUser();//获取当前操作用户对象
		//int uid= usr.getUID();
		RecordSet rs1 = new RecordSet();
		RecordSet rs2 = new RecordSet();  
		int uid= 1606;//主流程虚拟节点的操作人，部门文件传阅的ID
		String requestid= requestinfo.getRequestid();//当前的请求ID
		String cbr="";
		int zlcid=0;
		String sql1 = " select * from  formtable_main_39 where requestid= "+requestid;//督查督办
		rs1.execute(sql1);
		if(rs1.next()){
			cbr = rs1.getString("cbr") ;
			zlcid=rs1.getInt("zlcid");
		}
		writeLog("将收文流程"+zlcid+"转发给"+cbr+"当前操作人为"+uid);
		WorkflowService service = new WorkflowServiceImpl();
		String returnval = service.forwardWorkflowRequest(zlcid, cbr,"转发给"+cbr,uid,"127.0.0.1");
		writeLog("转发结果为:"+returnval);
		//将转办的流程自动提交
		writeLog("开始将转办的流程自动提交");
		String[] list = cbr.split(",");
		//String[] list = {"362","363"};
		for(int i=0;i<list.length;i++){
			String getcbr = list[i];
			writeLog("当前承办人1为"+getcbr);
			int currentCbr=Integer.parseInt(getcbr);
			writeLog("当前承办人为"+currentCbr);
			WorkflowRequestInfo workflowRequestInfo = service.getWorkflowRequest(zlcid, currentCbr,0);
			String str =service.submitWorkflowRequest(workflowRequestInfo, zlcid, currentCbr, "submit", "");
			writeLog("流程提交结果"+str);
		}
	   
		
		return SUCCESS;
	}


	

}
