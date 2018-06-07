package weaver.interfaces.action;
/**
 * by yangwei at 20180524
 * 主要是收文流程如果发起督办以后再删除督办流程，发送到的安图系统的催办提醒不会被删除
 * 所以做了一个定时任务，每天晚上删除一下废弃的消息数据
 */

import weaver.conn.RecordSet;
import weaver.interfaces.schedule.BaseCronJob;

public class DeleteDBRemind extends BaseCronJob {

	@Override
	public void execute() {
		String sql = "delete from UF_SENDANTU b where b.iid not in (select  REQUESTID from WORKFLOW_REQUESTBASE a  where a.REQUESTID = b.IID)";
		RecordSet rs = new RecordSet();
		rs.execute(sql);
	}
}
