package weaver.interfaces.jiangyl.sfw;

import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.hrm.company.DepartmentComInfo;
import weaver.hrm.resource.ResourceComInfo;

/**
 * 转换类
 * 
 * @author jiangyanlong
 *
 */
public class SFWUtil {

	public String getLastNameByNodeIDAndRequestID(String requestid,String nodeid) {
		String lastname = "";
		StringBuffer sb = new StringBuffer(",");
		RecordSet rs = new RecordSet();
		String sql = "select * from WORKFLOW_CURRENTOPERATOR where requestid = "+requestid+" and nodeid = "+nodeid+"";
		rs.execute(sql);
		while(rs.next()) {
			String userid = Util.null2String(rs.getString("userid"));
			sb.append(userid);
			sb.append(",");
		}
		String userids = sb.toString();
		if(userids.equals(",")) {
			return "";
		}
		userids = userids.substring(1,userids.length()-1);
		try {
			lastname = new ResourceComInfo().getLastnames(userids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lastname;
	}
	
	public String getDepartmentNameByNodeIDAndRequestID(String requestid,String nodeid) {
		String lastname = "";
		StringBuffer sb = new StringBuffer(",");
		RecordSet rs = new RecordSet();
		String sql = "select * from WORKFLOW_CURRENTOPERATOR where requestid = "+requestid+" and nodeid = "+nodeid+"";
		rs.execute(sql);
		while(rs.next()) {
			String userid = Util.null2String(rs.getString("userid"));
			sb.append(userid);
			sb.append(",");
		}
		String userids = sb.toString();
		if(userids.equals(",")) {
			return "";
		}
		userids = userids.substring(1,userids.length()-1);
		String depid = userids.split(",")[0];
		try {
			
			lastname = new DepartmentComInfo().getDepartmentname(new ResourceComInfo().getDepartmentID(depid));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lastname;
	}
}
