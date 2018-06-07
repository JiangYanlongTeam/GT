package weaver.interfaces.jiangyl.project;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.hrm.User;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
/**
 * * @author yangw at 20180123
 * 信息拟报表  	formtable_main_64   workflow 181
 * 信息拟报会签表 	formtable_main_74
 * * 信息拟报主流程提交的时候，需要将主流程上的意见和字段同步到子流程
 * 字段：主送zs，抄报cb，抄送cs ，打印人签名dyrqm，打印人签名日期dyrqmrq，校对人签名jdrqm，校对人签名日期jdrqmrq，打印份数dyfs ，
 * 局办公室主任签名，分管局长签名，局长签名，信息拟报员意见
 *
 */
public class XxnbToBmAction extends BaseBean implements Action {
	public String execute(RequestInfo requestinfo) {
		writeLog("进入信息拟报节点后操作开始 requestid ="+requestinfo.getRequestid());
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		RecordSet rs2 = new RecordSet(); 
		RecordSet rs3 = new RecordSet();  
		RecordSet rs4 = new RecordSet();
		RecordSet rs5 = new RecordSet();
		RecordSet rs6 = new RecordSet();
		String requestid= requestinfo.getRequestid();//当前的请求ID
		String bgsqm="";
		String bgsqmrq="";
		String bgshg="";
		String jzqm="";
		String jzqmrq="";
		String jzyj="";
		String xxybjyj ="";
		String fgjldyj1="";
		String fgjldyj2="";
		String fgjldyj3="";
		String fgjldyj4="";
		String fgjldqm1="";
		String fgjldqm2="";
		String fgjldqm3="";
		String fgjldqm4="";
		String fgjldqmrq1="";
		String fgjldqmrq2="";
		String fgjldqmrq3="";
		String fgjldqmrq4="";
		String zs="";
		String cb="";
		String cs="";
		String dyrqm="";
		String dyrqmrq="";
		String jdrqm="";
		String jdrqmrq="";
		String dyfs="";
		String requestidBM="";
		String sql = "select *  from formtable_main_64 where requestid="+requestid;
		rs.execute(sql);
		if(rs.next()){
			zs=rs.getString("zs");
			cb=rs.getString("cb");
			cs=rs.getString("cs");
			dyrqm=rs.getString("dyrqm");
			dyrqmrq=rs.getString("dyrqmrq");
			jdrqm=rs.getString("jdrqm");
			jdrqmrq=rs.getString("jdrqmrq");
			dyfs=rs.getString("dyfs");
			bgsqm=rs.getString("bgsqm");
			bgsqmrq=rs.getString("bgsqmrq");
			bgshg=rs.getString("bgshg");
			jzqm=rs.getString("jzqm");
			jzqmrq=rs.getString("jzqmrq");
			jzyj=rs.getString("jzyj");
			xxybjyj=rs.getString("xxybjyj");
			fgjldyj1=rs.getString("fgjldyj1");
			fgjldyj2=rs.getString("fgjldyj2");
			fgjldyj3=rs.getString("fgjldyj3");
			fgjldyj4=rs.getString("fgjldyj4");
			fgjldqm1=rs.getString("fgjldqm1");
			fgjldqm2=rs.getString("fgjldqm2");
			fgjldqm3=rs.getString("fgjldqm3");
			fgjldqm4=rs.getString("fgjldqm4");
			fgjldqmrq1=rs.getString("fgjldqmrq1");
			fgjldqmrq2=rs.getString("fgjldqmrq2");
			fgjldqmrq3=rs.getString("fgjldqmrq3");
			fgjldqmrq4=rs.getString("fgjldqmrq4");
		}
		writeLog("需要的基本信息为："+zs+cb+cs+dyrqm+dyrqmrq+jdrqm+jdrqmrq+dyfs);
		int currentNodeid = requestinfo.getRequestManager().getNodeid();//当前流程的节点ID
		writeLog("当前流程节点为："+currentNodeid);
		String sql1="select REQUESTID from WORKFLOW_REQUESTBASE  where mainRequestId ="+requestid +"  and  workflowid=221";
		writeLog("更新子流程流程的语句为："+sql1);
		rs1.execute(sql1);
		while(rs1.next()){
			requestidBM=rs1.getString("REQUESTID");
			writeLog("当前子流程为："+requestidBM);
			if(requestidBM==null ||requestidBM.equals("")){
					return SUCCESS;
				}
			if(currentNodeid==411){//局办或监察室信息员编辑
				String sql2 = "update formtable_main_74 set  xxybjyj='"+xxybjyj+"' where requestid= "+requestidBM;//
				writeLog("更新拟报会签流程的语句为："+sql2);
				rs2.execute(sql2);
			}
			if(currentNodeid==412){//局办公室主任，更新办公室主任签名，签名日期
				String sql4 = "update formtable_main_74 set  bgsqm='"+bgsqm+"',bgsqmrq='"+bgsqmrq+"',bgshg='"+bgshg+"' where requestid= "+requestidBM;//
				writeLog("更新拟报会签流程的语句为："+sql4);
				rs4.execute(sql4);
			}
			if(currentNodeid==413||currentNodeid==414){//纪检组长和分管局领导签名
				String sql6 = "update formtable_main_74 set  fgjldyj1='"+fgjldyj1+"',fgjldyj2='"+fgjldyj2+"',fgjldyj3='"+fgjldyj3+
						"',fgjldyj4='"+fgjldyj4+"',fgjldqm1='"+fgjldqm1+"',fgjldqm2='"+fgjldqm2+"',fgjldqm3='"+fgjldqm3+
						"',fgjldqm4='"+fgjldqm4+"',fgjldqmrq1='"+fgjldqmrq1+"',fgjldqmrq2='"+fgjldqmrq2+"',fgjldqmrq3='"+fgjldqmrq3+
						"',fgjldqmrq4='"+fgjldqmrq4+"' where requestid= "+requestidBM;//
				writeLog("更新拟报会签流程的语句为："+sql6);
				rs6.execute(sql6);
			}
			if(currentNodeid==415){//局长签名，更新局长签名，签名日期
				String sql5 = "update formtable_main_74 set  jzqm='"+jzqm+"',jzqmrq='"+jzqmrq+"',jzyj='"+jzyj+"' where requestid= "+requestidBM;//
				writeLog("更新拟报会签流程的语句为："+sql5);
				rs5.execute(sql5);
			}
			if(currentNodeid==417){//处室校对，更新打印人签名，签名日期，打印份数
				String sql3 = "update formtable_main_74 set  jdrqm='"+jdrqm+"',jdrqmrq='"+jdrqmrq+"',dyfs='"+dyfs+
						"',zs='"+zs+"',cb='"+cb+"',cs='"+cs+"' where requestid= "+requestidBM;//
				writeLog("更新拟报会签流程的语句为："+sql3);
				rs3.execute(sql3);
			}	
			
		}
		return SUCCESS;
	}


	

}
