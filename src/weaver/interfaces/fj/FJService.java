package weaver.interfaces.fj;

public interface FJService {

	/**
	 * 获取分局数据
	 * 
	 * @param page 页数
	 * @param limit 每页条数
	 * @param loginname 登录账号
	 * @param workflowid 流程类型
	 * @param begindate 开始日期
	 * @param enddate 结束日期
	 * @param requestname 流程标题
	 * @return
	 */
	public String getFJData(int page, int limit, String loginname, String workflowid, String begindate, String enddate, String requestname);
	
	/**
	 * 安图流程办结推送给OA中，更新requestid对应的流程中的字段信息，并强制归档当前流程
	 * 
	 * @param requestid
	 * @param message
	 * @return
	 */
	public String doForceWf(String requestid, String message);
	
	/**
	 * 通知OA流程已经在安图中创建
	 * 
	 * @param requestid
	 * @return
	 */
	public String notify(String requestid);
	
	/**
	 * 根据requestid获取附件信息
	 * 
	 * @param requestid
	 * @return
	 */
	public String getFile(String requestid);
}
