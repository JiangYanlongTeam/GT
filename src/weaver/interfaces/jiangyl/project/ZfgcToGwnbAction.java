package weaver.interfaces.jiangyl.project;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.hrm.User;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
/**
 * * @author yangw at 20180123
 * 公文拟报表  	formtable_main_35   workflow 59
 * 公文拟报会签表 	formtable_main_36
 * * 拟报会签提交的时候，需要将政法规的意见和字段同步到子流程
 * 字段：政法规处意见，政法处签名，政法规处签名日期
 *
 */
public class ZfgcToGwnbAction extends BaseBean implements Action {
	public String execute(RequestInfo requestinfo) {
		writeLog("进入公文拟报节点后操作开始 requestid ="+requestinfo.getRequestid());
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		RecordSet rs2 = new RecordSet(); 
		
		String requestid= requestinfo.getRequestid();//当前的请求ID
		String zcfgcqm="";
		String zcfgcqmrq="";
		String hfxsc="";
		String requestidBM="";
		String sql = "select *  from formtable_main_36 where requestid="+requestid;
		rs.execute(sql);
		if(rs.next()){
			zcfgcqm=rs.getString("zcfgcqm");
			zcfgcqmrq=rs.getString("zcfgcqmrq");
			hfxsc=rs.getString("hfxsc");
		}
		String sql1="select * from WORKFLOW_REQUESTBASE  where requestid ="+requestid +"  and  workflowid=60";
		writeLog("更新主流程流程的语句为："+sql1);
		rs1.execute(sql1);
		if(rs1.next()){
			requestidBM=rs1.getString("mainRequestId");
			if(requestidBM==null ||requestidBM.equals("")){
					return SUCCESS;
				}
				String sql2 = "update formtable_main_35 set  zcfgcqm='"+zcfgcqm+"', zcfgcqmrq='"+zcfgcqmrq+"', hfxsc='"+hfxsc+"' where requestid= "+requestidBM;//
				writeLog("更新拟报会签流程的语句为："+sql2);
				rs2.execute(sql2);
		}
		return SUCCESS;
	}	

}
