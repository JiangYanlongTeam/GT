package weaver.interfaces.jiangyl.project;

import java.text.SimpleDateFormat;
import java.util.*;
import weaver.conn.RecordSet;
import weaver.general.*;
import weaver.interfaces.workflow.action.Action;

import weaver.soa.workflow.request.RequestInfo;
import weaver.soa.workflow.request.Cell;
import weaver.soa.workflow.request.DetailTable;
import weaver.soa.workflow.request.DetailTableInfo;
import weaver.soa.workflow.request.MainTableInfo;
import weaver.soa.workflow.request.Row;
public class WorkflowSharedScope extends BaseBean implements Action{
  private static long n = 1;

  public String execute(RequestInfo request)
  {
    try{
    	writeLog("流程权限接口执行开始");
		String requestid = Util.null2String(request.getRequestid());
		String wfid = "";
		String currentnodeid = "";
		String creater = "";		
		RecordSet rsmain = new RecordSet();    
		String sqlmain = " select * from  formtable_main_39 where requestid= "+requestid;//督查督办
		rsmain.execute(sqlmain);
		if(rsmain.next()){
			String uid = Util.null2String(rsmain.getString("cbr"));//cbr
			String mainrequestid = Util.null2String(rsmain.getString("zlcid"));//主流程ID
			String[] list = uid.split(",");
				for(int i=0;i<list.length;i++){
					String getcbr = list[i];
					writeLog("cbr:"+getcbr);
					RecordSet rs = new RecordSet();
					rs.execute("select creater,workflowid,currentnodeid from workflow_requestbase where requestid="+mainrequestid);					
					if(rs.next()){
						creater = rs.getString(1);
						wfid = rs.getString(2);
						currentnodeid = rs.getString(3);
					}
					rs.execute("select 1 from workflow_currentoperator where requestid="+mainrequestid+" and userid ="+getcbr);		
					if(!rs.next()){
						String sql = "select wfid,userid from Workflow_SharedScope where wfid ="+wfid +" and requestid = "+mainrequestid+" and iscanread = 1 and operator = '"+creater+"' and currentnodeid = "+currentnodeid+" and userid ="+getcbr;
						rs.execute(sql);
						if(!rs.next()){
							sql = " insert into Workflow_SharedScope (wfid,requestid,permissiontype,userid,iscanread,operator,currentnodeid) values ("+wfid+","+mainrequestid+",5,"+getcbr+",1,"+creater+","+currentnodeid+")"   ;
							rs.execute(sql);
							rs.execute("update workflow_base set isshared = 1 where id="+wfid);
						}
					}
					
				}
		}
		/*
		writeLog("1111111111"+cbr);
		RecordSet rs = new RecordSet();
		rs.executeSql("select creater,workflowid,currentnodeid from workflow_requestbase where requestid="+mainrequestid);
		if(rs.next()){
			creater = rs.getString(1);
			wfid = rs.getString(2);
			currentnodeid = rs.getString(3);
		}
		writeLog("select 1 from workflow_currentoperator where requestid="+requestid+" and userid in ("+uid+")");
		rs.executeSql("select 1 from workflow_currentoperator where requestid="+mainrequestid+" and userid in ("+uid+")");		
		if(!rs.next()){
			String sql = "select wfid,userid from Workflow_SharedScope where wfid ="+wfid +" and requestid = "+mainrequestid+" and iscanread = 1 and operator = '"+creater+"' and currentnodeid = "+currentnodeid+" and userid in"+uid+")" ;
			rs.executeSql(sql)   ;
			if(!rs.next()){
				sql = " insert into Workflow_SharedScope (wfid,requestid,permissiontype,userid,iscanread,operator,currentnodeid) values ("+wfid+","+mainrequestid+",5,"+uid+",1,"+creater+","+currentnodeid+")"   ;
				rs.executeSql(sql);
				rs.executeSql("update workflow_base set isshared = 1 where id="+wfid);
			}
		}
		*/

    }
    catch (Exception e)
    {
		writeLog("=========");
    }
    return Action.SUCCESS;
  }

}