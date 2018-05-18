package weaver.interfaces.action;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * 督查督办流程归档之后执行更新 推送到安图即时提醒 表中的对应流程信息为已读
 * 
 * @author jiangyanlong
 *
 */
public class UpdateCBTXZAction extends BaseBean implements Action  {

	@Override
	public String execute(RequestInfo request) {
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		// 取主表数据
		String requestid = request.getRequestid();
		String sql = "select id from uf_sendantu where iid = '"+requestid+"'";
		rs.executeSql(sql);
		while(rs.next()) {
			String id = Util.null2String(rs.getString("id"));
			String sql2 = "update uf_sendantu set flag = '0' where id = '"+id+"'";
			rs1.execute(sql2);
		}
		return Action.SUCCESS;
	}

}
