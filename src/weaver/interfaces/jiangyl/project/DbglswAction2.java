package weaver.interfaces.jiangyl.project;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
public class DbglswAction2 extends BaseBean implements Action{
	/**
	 * * @author yangw at 20180105
	 * 1、在局领导签发节点和其他局领导核稿节点添加节点后附加操作
	 * 2、选择类型为办文回复以后，必填关联收文流程，记录下收文流程的requestid值
	 * 这里关联收文流程需要注意，关联的是督办流程所在的主流程
	 * 3、 然后然后根据主流程找到督查督办流程，
	 * 4、找到关联拟文的字段，update一个值即可。
	 * -- 公文拟报workflowid  121   --督查督办 workflowid  103  --收文workflowid  101
	 * --公文拟报 formtable_main_35 --督察督办 formtable_main_34  --收文formtable_main_29
	 
	  * -- 公文拟报workflowid  59   --督查督办 workflowid  63  --收文workflowid  61
	 * --公文拟报 formtable_main_35 --督察督办 formtable_main_39  --收文formtable_main_37
	 */
		public String execute(RequestInfo requestinfo) {
			RecordSet rs1 = new RecordSet();
			RecordSet rs2 = new RecordSet();
			RecordSet rs3 = new RecordSet();
			RecordSet rs4 = new RecordSet();
			RecordSet rs5 = new RecordSet();
			String GLSW = "";
			String DBID = "";
			String NGBM="";
			String SQDBBM="";
			String CREATER = "";
			String CBR = "";
			writeLog("进入节点后操作进入节点后操作DbglswAction2 requestid ="+requestinfo.getRequestid());
			String requestid= requestinfo.getRequestid();//当前的请求ID
			String sfqf= "已签发";//签发状态
			//当前流程信息
			//String workflowid= requestinfo.getWorkflowid();//流程的workflowid
			//String tablename = requestinfo.getRequestManager().getBillTableName();//表单名称
			String requestname = requestinfo.getRequestManager().getRequestname();//请求标题
			//先找到拟报关联的收文id
			rs1.execute("SELECT glswnew,ngbm  from 	formtable_main_35 where REQUESTID = "+requestid);
			if(rs1.next()){
					GLSW = rs1.getString("glswnew");
				    NGBM = rs1.getString("ngbm");
					if(GLSW==null ||GLSW.equals("")){
					return SUCCESS;
				}	
				//根据收文id找到督察督办id ,63是督察督办的id	
				rs2.execute("select REQUESTID from WORKFLOW_REQUESTBASE  where mainRequestId ="+GLSW +" and workflowid = 63");
				while(rs2.next()){
					DBID = rs2.getString("REQUESTID");
				//根据督办ID找到承办人
					rs5.execute("select sqdbbm  from formtable_main_39 where REQUESTID="+DBID);
					if(rs5.next()){
						SQDBBM = rs5.getString("sqdbbm");
						}
					if(SQDBBM.equals(NGBM)){
						//更新督查督办表,将公文拟报的流程标题赋值,
						writeLog("update formtable_main_39 set sfjldqf = '" +sfqf+"',gwnbhf='"+requestid+"'  where REQUESTID="+DBID);
						rs3.execute("update formtable_main_39 set sfjldqf = '" +sfqf+"',gwnbhf='"+requestid+"'  where REQUESTID="+DBID);
					}
				}
				
			}
				else{
					return SUCCESS;
		}
			
			
			return SUCCESS;
	}
	

}
