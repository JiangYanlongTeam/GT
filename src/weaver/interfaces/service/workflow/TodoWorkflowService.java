package weaver.interfaces.service.workflow;

public interface TodoWorkflowService {

	/**
	 * 根据登录账号获取待办数量
	 * 
	 * @param loginname
	 * @return
	 */
	public String todoWorkflowCount(String loginname);
	
	/**
	 * 根据登录账号和分页信息获取分页列表数据
	 * 
	 * @param page
	 * @param limit
	 * @param loginname
	 * @param wname
	 * @return
	 */
	public String todoWorkflowList(int page, int limit, String loginname, String wname, String iid, String reqname);
	
	/**
	 * 根据登录账号获取待办中流程类型统计数量
	 * 
	 * @param loginname
	 * @return
	 */
	public String todoWorkflowTypeList(String loginname);
	
	/**
	 * 流程类型
	 * 
	 * @return
	 */
	public String workflowTypeList();
	
	/**
	 * 根据流程类型获取流程信息
	 * 
	 * @param workflowid
	 * @return
	 */
	public String workflowList(int page, int limit, String name, String workflowid, String status, String startDate, String endDate, String loginname);
	
	/**
	 * 来件查阅提醒
	 * 
	 * @param title
	 * @param content
	 * @param loginid
	 * @return
	 */
	public String ljcyRemind(String content);
	
	/**
	 * 业务审批提醒
	 * 
	 * @param jsondata
	 * @return
	 */
	public String ywspRemind(String jsondata);
	
	
	/**
	 * 根据登录账号获取已办/办结中流程类型统计数量
	 * 
	 * @param loginname
	 * @return
	 */
	public String finishWorkflowTypeList(String loginname);
	
	
	/**
	 * 根据登录账号获取已办/办结中流程数据
	 * 
	 * @param loginid 登录账号
	 * @param page 页数
	 * @param limit 每页条数
	 * @param requestid 业务编号
	 * @param requestname 业务名称
	 * @param wname 业务类型
	 * @return
	 */
	public String finishWorklowList(int page, int limit, String loginid, String requestid, String requestname, String wname);
}
