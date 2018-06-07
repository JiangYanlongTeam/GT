package weaver.interfaces.jiangyl.project;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
/**
 * * @author yangw at 20180123
 * 公文拟报表  	formtable_main_35   workflow 59
 * 公文拟报会签表 	formtable_main_36
 * * 公文拟报主流程提交的时候，需要将主流程上的意见和字段同步到子流程
 * 字段：主送zs，抄报cb，抄送cs ，打印人签名dyrqm，打印人签名日期dyrqmrq，校对人签名jdrqm，校对人签名日期jdrqmrq，打印份数dyfs ，更新局办公室主任签名
 *
 */
public class GwnbToBmAction extends BaseBean implements Action {
	public String execute(RequestInfo requestinfo) {
		writeLog("进入公文拟报节点后操作开始GwnbToBmAction requestid ="+requestinfo.getRequestid());
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		RecordSet rs2 = new RecordSet(); 
		RecordSet rs3 = new RecordSet();  
		RecordSet rs4 = new RecordSet();
		RecordSet rs5 = new RecordSet();
		RecordSet rs6 = new RecordSet();
		RecordSet rs7 = new RecordSet();
		RecordSet rs8 = new RecordSet();
		String requestid= requestinfo.getRequestid();//当前的请求ID
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
	
		String bgsqm="";
		String bgsqmrq="";
		String bgshg="";
		String jzqm="";
		String jzqmrq="";
		String jzyj="";
		String zs="";
		String cb="";
		String cs="";
		String dyrqm="";
		String dyrqmrq="";
		String jdrqm="";
		String jdrqmrq="";
		String dyfs="";
		String wtz="";//文头字
		String fwhnew="";//发文号
		String fwh=""; //发文编号


		String requestidBM="";
		String sql = "select *  from formtable_main_35 where requestid="+requestid;
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
			wtz=rs.getString("wtz");
			fwhnew=rs.getString("fwhnew");
			fwh=rs.getString("fwh");
		}
		writeLog("需要的基本信息为："+zs+cb+cs+dyrqm+dyrqmrq+jdrqm+jdrqmrq+dyfs);
		int currentNodeid = requestinfo.getRequestManager().getNodeid();//当前流程的节点ID
		writeLog("当前流程节点为："+currentNodeid);
		String sql1="select REQUESTID from WORKFLOW_REQUESTBASE  where mainRequestId ="+requestid +"  and  workflowid=60";
		writeLog("更新子流程流程的语句为："+sql1);
		rs1.execute(sql1);
		while(rs1.next()){
			requestidBM=rs1.getString("REQUESTID");
			writeLog("当前子流程为："+requestidBM);
			if(requestidBM==null ||requestidBM.equals("")){
					return SUCCESS;
				}
			if(currentNodeid==263){//局办公室主任，更新办公室主任签名，签名日期
				String sql4 = "update formtable_main_36 set  bgsqm='"+bgsqm+"',bgsqmrq='"+bgsqmrq+"',bgshg='"+bgshg+"' where requestid= "+requestidBM;//
				writeLog("更新拟报会签流程的语句为："+sql4);
				rs4.execute(sql4);
			}
			if(currentNodeid==264){//分管局领导核稿
				String sql6 = "update formtable_main_36 set  fgjldyj1='"+fgjldyj1+"',fgjldyj2='"+fgjldyj2+"',fgjldyj3='"+fgjldyj3+
						"',fgjldyj4='"+fgjldyj4+"',fgjldqm1='"+fgjldqm1+"',fgjldqm2='"+fgjldqm2+"',fgjldqm3='"+fgjldqm3+
						"',fgjldqm4='"+fgjldqm4+"',fgjldqmrq1='"+fgjldqmrq1+"',fgjldqmrq2='"+fgjldqmrq2+"',fgjldqmrq3='"+fgjldqmrq3+
						"',fgjldqmrq4='"+fgjldqmrq4+"' where requestid= "+requestidBM;//
				writeLog("更新拟报会签流程的语句为："+sql6);
				rs6.execute(sql6);
			}
			if(currentNodeid==265){//局领导签发
				String sql7 = "update formtable_main_36 set  fgjldyj1='"+fgjldyj1+"',fgjldyj2='"+fgjldyj2+"',fgjldyj3='"+fgjldyj3+
						"',fgjldyj4='"+fgjldyj4+"',fgjldqm1='"+fgjldqm1+"',fgjldqm2='"+fgjldqm2+"',fgjldqm3='"+fgjldqm3+
						"',fgjldqm4='"+fgjldqm4+"',fgjldqmrq1='"+fgjldqmrq1+"',fgjldqmrq2='"+fgjldqmrq2+"',fgjldqmrq3='"+fgjldqmrq3+
						"',fgjldqmrq4='"+fgjldqmrq4+"',jzyj='"+jzyj+"',jzqm='"+jzqm+"',jzqmrq='"+jzqmrq+"' where requestid= "+requestidBM;//
				writeLog("更新拟报会签流程的语句为："+sql7);
				rs7.execute(sql7);
			}
			
			if(currentNodeid==268){//其他局领导，更新其他局领导签名，签名日期
				String sql5 = "update formtable_main_36 set  jzqm='"+jzqm+"',jzqmrq='"+jzqmrq+"',jzyj='"+jzyj+"' where requestid= "+requestidBM;//
				writeLog("更新拟报会签流程的语句为："+sql5);
				rs5.execute(sql5);
			}
			if(currentNodeid==269){//局办公室编号更新编号字段
				String sql8 = "update formtable_main_36 set  wtz='"+wtz+"',fwhnew='"+fwhnew+"',fwh='"+fwh+"' where requestid= "+requestidBM;//
				writeLog("更新拟报会签流程的语句为："+sql8);
				rs8.execute(sql8);
			}
			if(currentNodeid==272){//处室校对，更新校对人签名，签名日期
				String sql2 = "update formtable_main_36 set  jdrqm='"+jdrqm+"',jdrqmrq='"+jdrqmrq+"' where requestid= "+requestidBM;//
				writeLog("更新拟报会签流程的语句为："+sql2);
				rs2.execute(sql2);
			}
			if(currentNodeid==273){//局办公室发文，更新打印人签名，签名日期，打印份数
				String sql3 = "update formtable_main_36 set  dyrqm='"+dyrqm+"',dyrqmrq='"+dyrqmrq+"',dyfs='"+dyfs+
						"',zs='"+zs+"',cb='"+cb+"',cs='"+cs+"' where requestid= "+requestidBM;//
				writeLog("更新拟报会签流程的语句为："+sql3);
				rs3.execute(sql3);
			}	
			
		}
		return SUCCESS;
	}


	

}
