package weaver.interfaces.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.hrm.User;

/**
 * 督查督办流程中查看主流程中局领导签字意见等信息
 * 
 * @author jiangyanlong
 *
 */
public class DCDB_JLDSIGN extends BaseBean {

	public List<Map<String, String>> getDTData(User user, Map<String, String> otherparams, HttpServletRequest request,
			HttpServletResponse response) {
		RecordSet rs = new RecordSet();
		String requestid = Util.null2String(otherparams.get("requestid"));
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		String mainrequestid = getMainRequestidByRequestid(requestid);
		String tablename = getTableNameByRequestid(mainrequestid);
		String sql = "select jld1,jzyj1,jldrq1,jjld2,jzyj2,jldrq2,jld3,jzyj3,jldrq3,jld4,jzyj4,jldrq4,jld5,jzyj5,jldrq5,jld6,jzyj6,jldrq6,jld7,jzyj7,jldrq7,jld8,jzyj8,jldrq8,jld9,jzyj9,jldrq9 "
				+ " from " + tablename + " where requestid = '" + mainrequestid + "'";
		rs.execute(sql);
		while (rs.next()) {
			String jld1 = Util.null2String(rs.getString("jld1"));
			String jzyj1 = Util.null2String(rs.getString("jzyj1"));
			String jldrq1 = Util.null2String(rs.getString("jldrq1"));
			String jld2 = Util.null2String(rs.getString("jjld2"));
			String jzyj2 = Util.null2String(rs.getString("jzyj2"));
			String jldrq2 = Util.null2String(rs.getString("jldrq2"));
			String jld3 = Util.null2String(rs.getString("jld3"));
			String jzyj3 = Util.null2String(rs.getString("jzyj3"));
			String jldrq3 = Util.null2String(rs.getString("jldrq3"));
			String jld4 = Util.null2String(rs.getString("jld4"));
			String jzyj4 = Util.null2String(rs.getString("jzyj4"));
			String jldrq4 = Util.null2String(rs.getString("jldrq4"));
			String jld5 = Util.null2String(rs.getString("jld5"));
			String jzyj5 = Util.null2String(rs.getString("jzyj5"));
			String jldrq5 = Util.null2String(rs.getString("jldrq5"));
			String jld6 = Util.null2String(rs.getString("jld6"));
			String jzyj6 = Util.null2String(rs.getString("jzyj6"));
			String jldrq6 = Util.null2String(rs.getString("jldrq6"));
			String jld7 = Util.null2String(rs.getString("jld7"));
			String jzyj7 = Util.null2String(rs.getString("jzyj7"));
			String jldrq7 = Util.null2String(rs.getString("jldrq7"));
			String jld8 = Util.null2String(rs.getString("jld8"));
			String jzyj8 = Util.null2String(rs.getString("jzyj8"));
			String jldrq8 = Util.null2String(rs.getString("jldrq8"));
			String jld9 = Util.null2String(rs.getString("jld9"));
			String jzyj9 = Util.null2String(rs.getString("jzyj9"));
			String jldrq9 = Util.null2String(rs.getString("jldrq9"));

			data = add(data, jld1, jzyj1, jldrq1);
			data = add(data, jld2, jzyj2, jldrq2);
			data = add(data, jld3, jzyj3, jldrq3);
			data = add(data, jld4, jzyj4, jldrq4);
			data = add(data, jld5, jzyj5, jldrq5);
			data = add(data, jld6, jzyj6, jldrq6);
			data = add(data, jld7, jzyj7, jldrq7);
			data = add(data, jld8, jzyj8, jldrq8);
			data = add(data, jld9, jzyj9, jldrq9);
		}
		return data;
	}

	public List<Map<String, String>> add(List<Map<String, String>> data, String jld, String jzyj, String jldrq) {
		if (!("".equals(jld) && "".equals(jzyj) && "".equals(jldrq))) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("sign", "<img src='/weaver/weaver.file.ImgFileDownload?userid=" + jld + "' width='80px'>");
			map.put("riqi", jldrq);
			map.put("yijian", jzyj);
			data.add(map);
		}
		return data;
	}

	public String getMainRequestidByRequestid(String requestId) {
		RecordSet rs = new RecordSet();
		rs.execute("select mainrequestid from workflow_requestbase where requestid = '" + requestId + "'");
		rs.next();
		String mainrequestid = Util.null2String(rs.getString("mainrequestid"));
		return mainrequestid;
	}

	public String getTableNameByRequestid(String requestId) {
		RecordSet rs = new RecordSet();
		rs.execute(
				"select formid from workflow_base where id = (select workflowid from workflow_requestbase where requestid = '"
						+ requestId + "')");
		rs.next();
		String formid = Util.null2String(rs.getString("formid"));
		return "formtable_main_" + Math.abs(Integer.parseInt(formid));
	}
}
