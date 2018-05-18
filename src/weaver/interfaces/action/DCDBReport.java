package weaver.interfaces.action;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.hrm.User;

/**
 * 督查督办统计报表
 * 
 * @author jiangyanlong
 *
 */
public class DCDBReport extends BaseBean {

	public List<Map<String, String>> getDTData(User user, Map<String, String> otherparams, HttpServletRequest request,
			HttpServletResponse response) {
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		String dqrq = Util.null2String(otherparams.get("dqrq"));
		String dqrz = Util.null2String(otherparams.get("dqrz"));
		String DCDBFORMTABLE = getPropValue("GWSWBL", "DCDBFORMTABLE");
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		String sql = "select (select id from hrmdepartment where id in (select departmentid from hrmresource where id = b.creater)) departmentid,b.creater from "
				+ DCDBFORMTABLE + " a, workflow_requestbase b where a.requestid = b.requestid ";
		if (!"".equals(dqrq)) {
			sql += " and a.field7 >= '" + dqrq + "' ";
		}
		if (!"".equals(dqrz)) {
			sql += " and a.field7 <= '" + dqrz + "' ";
		}
		Map<String, List<String>> depHrmMap = new HashMap<String, List<String>>();
		rs.execute(sql);
		while (rs.next()) {
			String creater = Util.null2String(rs.getString("creater"));
			String depid = Util.null2String(rs.getString("departmentid"));
			if (depHrmMap.containsKey(depid)) {
				depHrmMap.get(depid).add(creater);
			} else {
				List<String> list = new ArrayList<String>();
				list.add(creater);
				depHrmMap.put(depid, list);
			}
		}
		if (depHrmMap.isEmpty()) {
			return data;
		}

		StringBuffer sb = new StringBuffer(",");
		for (Entry<String, List<String>> entry : depHrmMap.entrySet()) {
			String depid = entry.getKey();
			sb.append(depid + ",");
		}

		String deps = sb.toString();
		deps = deps.substring(1, deps.length() - 1);

		String sql2 = "select id,departmentname,showorder from hrmdepartment where id in (" + deps
				+ ") order by SHOWORDER asc";
		rs.execute(sql2);
		while (rs.next()) {
			Map<String, String> map = new HashMap<String, String>();
			String id = Util.null2String(rs.getString("id"));
			String departmentname = Util.null2String(rs.getString("departmentname"));
			List<String> li = depHrmMap.get(id);
			StringBuffer sb1 = new StringBuffer(",");
			for (String s : li) {
				sb1.append(s + ",");
			}
			String s1 = sb1.toString();
			s1 = s1.substring(1, s1.length() - 1);
			String sql3 = "select count(*) count from workflow_requestbase b, " + DCDBFORMTABLE
					+ " a where a.requestid = b.requestid and b.creater in (" + s1 + ") ";
			rs1.execute(sql3);
			rs1.next();
			String count = Util.null2o(rs1.getString("count"));
			map.put("zbbm", departmentname);
			map.put("dbljzs", count);

			String sql4 = "select count(*) count from workflow_requestbase b, " + DCDBFORMTABLE
					+ " a where a.requestid = b.requestid and b.creater in (" + s1 + ") ";

			if (!"".equals(dqrq)) {
				sql4 += " and a.field7 >= '" + dqrq + "' ";
			}
			if (!"".equals(dqrz)) {
				sql4 += " and a.field7 <= '" + dqrz + "' ";
			}
			rs1.execute(sql4);
			rs1.next();
			String ybj = Util.null2o(rs1.getString("count"));
			map.put("ybjzs", ybj);

			String sql5 = "select count(*) count from workflow_requestbase b, " + DCDBFORMTABLE
					+ " a where a.requestid = b.requestid and b.creater in (" + s1
					+ ") and a.sfcq = '0' and b.currentnodeid in (296,297) ";

			if (!"".equals(dqrq)) {
				sql5 += " and a.field7 >= '" + dqrq + "' ";
			}
			if (!"".equals(dqrz)) {
				sql5 += " and a.field7 <= '" + dqrz + "' ";
			}
			rs1.execute(sql5);
			rs1.next();
			String asbj = Util.null2o(rs1.getString("count"));
			map.put("asbjs", asbj);

//			String sql6 = "select count(*) count from workflow_requestbase b, " + DCDBFORMTABLE
//					+ " a where a.requestid = b.requestid and b.creater in (" + s1 + ") and a.sfcq = '1' ";
//
//			if (!"".equals(dqrq)) {
//				sql6 += " and a.field7 >= '" + dqrq + "' ";
//			}
//			if (!"".equals(dqrz)) {
//				sql6 += " and a.field7 <= '" + dqrz + "' ";
//			}
//			rs1.execute(sql6);
//			rs1.next();
//			String cqs = Util.null2o(rs1.getString("count"));
			String cqs = Util.null2o(String.valueOf(Integer.parseInt(ybj) - Integer.parseInt(asbj)));
			map.put("cqs", cqs);

			String sql7 = "select count(*) count from workflow_requestbase b, " + DCDBFORMTABLE
					+ " a where a.requestid = b.requestid and b.creater in (" + s1 + ") and a.yqcs > 0 ";

			if (!"".equals(dqrq)) {
				sql7 += " and a.field7 >= '" + dqrq + "' ";
			}
			if (!"".equals(dqrz)) {
				sql7 += " and a.field7 <= '" + dqrz + "' ";
			}
			rs1.execute(sql7);
			rs1.next();
			String yqc = Util.null2o(rs1.getString("count"));
			map.put("yqjs", yqc);
			String asbjl = "";
			if ("0".equals(ybj)) {
				asbjl = "/";
			} else {
				asbjl = percent(Integer.parseInt(asbj), Integer.parseInt(ybj));
				map.put("asbjl", asbjl);
			}
			map.put("depid", id);
			data.add(map);
		}

		List<Map<String, String>> data1 = new ArrayList<Map<String, String>>();
		
		String sql3 = "select a.id,a.departmentname,a.showorder from hrmdepartment a, HrmDepartmentDefined b where a.id = b.deptid and b.gwblyxbm=0 and a.id != '46' order by SHOWORDER asc";
		rs.execute(sql3);
		while (rs.next()) {
			String depid = Util.null2String(rs.getString("id"));
			String depname = Util.null2String(rs.getString("departmentname"));
			boolean isTrue = false;
			for (Map<String, String> dap : data) {
				String dpid = Util.null2String(dap.get("depid"));
				if (depid.equals(dpid)) {
					data1.add(dap);
					isTrue = true;
				}
			}
			if (!isTrue) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("zbbm", depname);
				map.put("dbljzs", "0");
				map.put("ybjzs", "0");
				map.put("asbjs", "0");
				map.put("cqs", "0");
				map.put("yqjs", "0");
				map.put("asbjl", "0%");
				data1.add(map);
			}
		}
		return data1;
	}

	public String percent(int num1, int num2) {
		// 创建一个数值格式化对象
		NumberFormat numberFormat = NumberFormat.getInstance();
		// 设置精确到小数点后2位
		numberFormat.setMaximumFractionDigits(0);
		String result = numberFormat.format((float) num1 / (float) num2 * 100);
		return result + "%";
	}
}
