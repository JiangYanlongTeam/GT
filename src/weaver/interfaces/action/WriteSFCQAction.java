package weaver.interfaces.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * 判断当前节点提交是否超过到期日，如果超过把字段sfcq改写为1 （是）
 * 
 * @author jiangyanlong
 *
 */
public class WriteSFCQAction extends BaseBean implements Action {

	public String execute(RequestInfo request) {
		RecordSet rs = new RecordSet();
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		String reqid = Util.null2String(request.getRequestid());
		String tbname = Util.null2String(request.getRequestManager().getBillTableName());
		String sql = "select field7 from " + tbname + " where requestid = '" + reqid + "'";
		rs.execute(sql);
		rs.next();
		String dqr = Util.null2String(rs.getString("field7"));
		if (getDateWithStr(sim.format(new Date()), "yyyy-MM-dd").after(getDateWithStr(dqr, "yyyy-MM-dd"))) {
			String sql2 = "update " + tbname + " set sfcq = '1' where requestid = '" + reqid + "'";
			rs.execute(sql2);
		}
		return SUCCESS;
	}

	/**
	 * 把字符串时间转换成日期
	 * 
	 * @param date
	 * @return
	 */
	public Date getDateWithStr(String date, String flag) {
		SimpleDateFormat s = new SimpleDateFormat(flag);
		Date d = null;
		try {
			d = s.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}
}
