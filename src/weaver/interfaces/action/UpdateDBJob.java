package weaver.interfaces.action;

import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.interfaces.schedule.BaseCronJob;

public class UpdateDBJob extends BaseCronJob {

	@Override
	public void execute() {
		String sql = "select listagg(requestid,',') WITHIN GROUP (order by requestid) reqids from " + "	("
				+ "		select distinct a.requestid, a.field7, b.operatedate,b.ISLASTTIMES,(select nodename from WORKFLOW_NODEBASE where id = c.CURRENTNODEID) from formtable_main_39 a,"
				+ "      workflow_currentoperator b,WORKFLOW_REQUESTBASE c where a.requestid = b.requestid and a.REQUESTID = c.REQUESTID "
				+ "      and c.CURRENTNODEID in (292,293,294) "
				+ "      and b.ISLASTTIMES = 1 and c.CURRENTNODETYPE != '3' and a.FIELD7 is not null and a.SFCQ = '0' "
				+ "	) where to_char(sysdate,'yyyy-mm-dd') > field7 ";
		RecordSet rs = new RecordSet();
		rs.execute(sql);
		rs.next();
		String reqids = Util.null2String(rs.getString("reqids"));
		String[] strs = reqids.split(",");
		String updateSql = "";
		if (strs.length > 0) {
			updateSql = "update formtable_main_39 set SFCQ = '1' where ";
		}
		for (int i = 0; i < strs.length; i++) {
			updateSql += " requestid = '" + strs[i] + "' ";
			if (i != strs.length - 1) {
				updateSql += " or ";
			}
		}
		rs.execute(updateSql);
	}
}
