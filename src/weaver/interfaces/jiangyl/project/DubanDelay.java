package weaver.interfaces.jiangyl.project;

import com.weaver.general.Util;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
/**
 * * @author yangw at 20180105
 * * 督办延期流程提交结束时，执行节点后附加操作，将到期日和办理时限更新
 * 督办表  formtable_main_39   督办延期表：formtable_main_46
 */
public class DubanDelay extends BaseBean implements Action {

	public String execute(RequestInfo requestinfo) {
		writeLog("进入节点后操作督办延期开始 requestid ="+requestinfo.getRequestid());
		RecordSet rs1 = new RecordSet();
		RecordSet rs2 = new RecordSet();  
		String requestid= requestinfo.getRequestid();//当前的请求ID
		String gldb="";//关联督办流程requestid
        String yqhdqr="";//延期后到期日
        String zblsx="";//延期后总办理时限
		String sql1 = " select * from  formtable_main_46 where requestid= "+requestid;//督办延期
		rs1.execute(sql1);
		if(rs1.next()){
			gldb = rs1.getString("gldb") ;
			yqhdqr=rs1.getString("yqhdqr");
			zblsx=rs1.getString("zblsx");
		}
		String sql2 ="update formtable_main_39 set field7 = '"+yqhdqr+"',sfcq = '0' "+",blsx = '"+zblsx+"',yqcs=yqcs+1  where requestid ='"+gldb+"'";
		writeLog("更新督办流程的语句为："+sql2);
		rs2.execute(sql2);
		return SUCCESS;
	}


	

}
