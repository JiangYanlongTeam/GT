package weaver.interfaces.action;

import weaver.conn.RecordSet;
import weaver.general.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

public class XXNBReport extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String begindatetime = Util.null2String(req.getParameter("begindate"));
		String enddatetime = Util.null2String(req.getParameter("enddate"));
		String result = xxnbReport(begindatetime, enddatetime);
		resp.setContentType("text/html; charset=utf-8");
		resp.getWriter().print(result.toString());
	}

	public String xxnbReport(String begindatetime, String enddatetime) {
		String beginND = begindatetime.split("-")[0];
		StringBuffer s = new StringBuffer();
		s.append(" <table class=\"gridtable\" width=\"100%\">");
		s.append("<tr><th rowspan=3 >单位</th>");
		s.append("<th colspan=11 >采用信息</th>");
		s.append("<th rowspan=3 >本次积分</th>");
		s.append("<th rowspan=3 >年度累计积分</th></tr>");
		s.append("<tr><th colspan=3 >市局采用</th>");
		s.append("<th rowspan=2 >市委采用</th>");
		s.append("<th rowspan=2 >市政府采用</th>");
		s.append("<th rowspan=2 >省厅采用</th>");
		s.append("<th rowspan=2 >省委、省政府、办公厅采用</th>");
		s.append("<th rowspan=2 >中办、国办、部办公厅采用</th>");
		s.append("<th rowspan=2 >加分情况</th>");
		s.append("<th rowspan=2 >投稿数</th>");
		s.append("<th rowspan=2 >规定指标</th></tr>");
		s.append("<tr><th >市局采用总数</th>");
		s.append("<th >其中市局专报、专刊、专项动态</th>");
		s.append("<th >其中廉政信息</th></tr>");

		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		// 查询部门
		LinkedHashMap<String, String> departmentMap = new LinkedHashMap<String, String>();
		String departmentSQL = "select a.id,a.DEPARTMENTNAME,b.zbsl from HRMDEPARTMENT a, uf_bmxxcyzb b where a.id = b.bm order by a.SHOWORDER asc ";
		rs.execute(departmentSQL);
		while (rs.next()) {
			String departmentID = Util.null2String(rs.getString("id"));
			String departmentName = Util.null2String(rs.getString("departmentname"));
			departmentMap.put(departmentID, departmentName);
		}
		for (Entry<String, String> entry : departmentMap.entrySet()) {
			String depID = entry.getKey();
			String depNa = entry.getValue();

			// 市局采用总数
			String sjcyzsSQL = "select sjcylx,swcylx1,swcylx2,szfcylx,stcylx,szfbcylx,swbcylx,gtbcylx,jflx from uf_xxnbgl where 1=1 and bsbm like '%"
					+ depID + "%' or qtzzbm like '%" + depID + "%' ";
			if (!"".equals(Util.null2String(begindatetime))) {
				sjcyzsSQL += " and xxybjjsrq >='" + begindatetime + "' ";
			}
			if (!"".equals(Util.null2String(enddatetime))) {
				sjcyzsSQL += " and xxybjjsrq <='" + enddatetime + "' ";
			}
			rs1.execute(sjcyzsSQL);
			List<String> sjcylxTotal = new ArrayList<String>();
			List<String> swcyTotal = new ArrayList<String>();
			List<String> szfcyTotal = new ArrayList<String>();
			List<String> stcyTotal = new ArrayList<String>();
			List<String> swszfTotal = new ArrayList<String>();
			List<String> zbgbTotal = new ArrayList<String>();
			List<String> jfTotal = new ArrayList<String>();
			int tgszs = rs1.getCounts();
			while (rs1.next()) {
				String sjcylx = Util.null2String(rs1.getString("sjcylx"));// 市局采用总数 //市局专报/专刊/专项/廉政信息
				String swcylx1 = Util.null2String(rs1.getString("swcylx1"));// 市委采用1
				String swcylx2 = Util.null2String(rs1.getString("swcylx2"));// 市委采用2
				String szfcylx = Util.null2String(rs1.getString("szfcylx"));// 市政府采用
				String stcylx = Util.null2String(rs1.getString("stcylx"));// 省厅采用
				String szfbcylx = Util.null2String(rs1.getString("szfbcylx"));// 省委/省政府
				String swbcylx = Util.null2String(rs1.getString("swbcylx"));// 省委/省政府
				String gtbcylx = Util.null2String(rs1.getString("gtbcylx"));// 中办/国办
				String jflx = Util.null2String(rs1.getString("jflx"));// 加分
				if (!"".equals(sjcylx)) {
					String[] sjcylxs = sjcylx.split(",");
					for (int i = 0; i < sjcylxs.length; i++) {
						sjcylxTotal.add(sjcylxs[i]);
					}
				}
				if (!"".equals(swcylx1)) {
					String[] swcylx1s = swcylx1.split(",");
					for (int i = 0; i < swcylx1s.length; i++) {
						swcyTotal.add(swcylx1s[i]);
					}
				}
				if (!"".equals(swcylx2)) {
					String[] swcylx2s = swcylx2.split(",");
					for (int i = 0; i < swcylx2s.length; i++) {
						swcyTotal.add(swcylx2s[i]);
					}
				}
				if (!"".equals(szfcylx)) {
					String[] szfcylxs = szfcylx.split(",");
					for (int i = 0; i < szfcylxs.length; i++) {
						szfcyTotal.add(szfcylxs[i]);
					}
				}
				if (!"".equals(stcylx)) {
					String[] stcylxs = stcylx.split(",");
					for (int i = 0; i < stcylxs.length; i++) {
						stcyTotal.add(stcylxs[i]);
					}
				}
				if (!"".equals(szfbcylx)) {
					String[] szfbcylxs = szfbcylx.split(",");
					for (int i = 0; i < szfbcylxs.length; i++) {
						swszfTotal.add(szfbcylxs[i]);
					}
				}
				if (!"".equals(swbcylx)) {
					String[] swbcylxs = swbcylx.split(",");
					for (int i = 0; i < swbcylxs.length; i++) {
						swszfTotal.add(swbcylxs[i]);
					}
				}
				if (!"".equals(gtbcylx)) {
					String[] gtbcylxs = gtbcylx.split(",");
					for (int i = 0; i < gtbcylxs.length; i++) {
						zbgbTotal.add(gtbcylxs[i]);
					}
				}
				if (!"".equals(jflx)) {
					String[] jflxs = jflx.split(",");
					for (int i = 0; i < jflxs.length; i++) {
						jfTotal.add(jflxs[i]);
					}
				}
			}
			String[] sjzbString = new String[] { "4", "5", "6" };
			String[] lzxxString = new String[] { "2" };
			String[] otherString = new String[] { "1", "3" };
			String[] swcyString = new String[] { "11", "12", "13", "14" };
			String[] szfcyString = new String[] { "8", "9", "10" };
			String[] stcyString = new String[] { "17", "18", "19" };
			String[] swszfString = new String[] { "20", "21" };
			String[] zbgbString = new String[] { "22" };
			String[] jfglString = new String[] { "16", "15" };
			new weaver.general.BaseBean().writeLog("部门ID:" + depID + ";部门名字:" + depNa);
			Map<String, String> sjzb = map(sjcylxTotal, sjzbString);
			Map<String, String> lzxx = map(sjcylxTotal, lzxxString);
			Map<String, String> other = map(sjcylxTotal, otherString);
			Map<String, String> swcy = map(swcyTotal, swcyString);
			Map<String, String> szfcy = map(szfcyTotal, szfcyString);
			Map<String, String> stcy = map(stcyTotal, stcyString);
			Map<String, String> swszf = map(swszfTotal, swszfString);
			Map<String, String> zbgb = map(zbgbTotal, zbgbString);
			Map<String, String> jfgl = map(jfTotal, jfglString);

			int sjcyzs = sjcylxTotal.size();
			int sjzbzs = getTotal(sjzbString, sjzb);
			int lzxxzs = getTotal(lzxxString, lzxx);
			int swcyzs = getTotal(swcyString, swcy);
			int szfcyzs = getTotal(szfcyString, szfcy);
			int stcyzs = getTotal(stcyString, stcy);
			int swszfzs = getTotal(swszfString, swszf);
			int zbgbzs = getTotal(zbgbString, zbgb);
			int jfgkzs = getFS(jfglString, jfgl);
			String gdzb = getZBSL(depID);
			int bczfzs = getFS(sjzbString, sjzb) + getFS(lzxxString, lzxx) + getFS(otherString, other)
					+ getFS(swcyString, swcy) + getFS(szfcyString, szfcy) + getFS(stcyString, stcy)
					+ getFS(swszfString, swszf) + getFS(zbgbString, zbgb) + getFS(jfglString, jfgl);
			int ndljzs = xxnbReport4ND(beginND + "-01-01", enddatetime, depID);

			s.append("<tr>");
			s.append("<td>" + depNa + "</td>");
			s.append("<td>" + sjcyzs + "</td>");
			s.append("<td>" + sjzbzs + "</td>");
			s.append("<td>" + lzxxzs + "</td>");
			s.append("<td>" + swcyzs + "</td>");
			s.append("<td>" + szfcyzs + "</td>");
			s.append("<td>" + stcyzs + "</td>");
			s.append("<td>" + swszfzs + "</td>");
			s.append("<td>" + zbgbzs + "</td>");
			s.append("<td>" + jfgkzs + "</td>");
			s.append("<td>" + tgszs + "</td>");
			s.append("<td>" + gdzb + "</td>");
			s.append("<td>" + bczfzs + "</td>");
			s.append("<td>" + ndljzs + "</td>");
			s.append("</tr>");
			// 市局采用总数
		}
		s.append("</table>");
		// 查询部门
		return s.toString();
	}

	public int xxnbReport4ND(String begindatetime, String enddatetime, String depID) {
		RecordSet rs1 = new RecordSet();
		// 市局采用总数
		String sjcyzsSQL = "select sjcylx,swcylx1,swcylx2,szfcylx,stcylx,szfbcylx,swbcylx,gtbcylx,jflx from uf_xxnbgl where 1=1 and bsbm like '%"
				+ depID + "%' or qtzzbm like '%" + depID + "%' ";
		if (!"".equals(Util.null2String(begindatetime))) {
			sjcyzsSQL += " and xxybjjsrq >='" + begindatetime + "' ";
		}
		if (!"".equals(Util.null2String(enddatetime))) {
			sjcyzsSQL += " and xxybjjsrq <='" + enddatetime + "' ";
		}
		rs1.execute(sjcyzsSQL);
		List<String> sjcylxTotal = new ArrayList<String>();
		List<String> swcyTotal = new ArrayList<String>();
		List<String> szfcyTotal = new ArrayList<String>();
		List<String> stcyTotal = new ArrayList<String>();
		List<String> swszfTotal = new ArrayList<String>();
		List<String> zbgbTotal = new ArrayList<String>();
		List<String> jfTotal = new ArrayList<String>();
		while (rs1.next()) {
			String sjcylx = Util.null2String(rs1.getString("sjcylx"));// 市局采用总数 //市局专报/专刊/专项/廉政信息
			String swcylx1 = Util.null2String(rs1.getString("swcylx1"));// 市委采用1
			String swcylx2 = Util.null2String(rs1.getString("swcylx2"));// 市委采用2
			String szfcylx = Util.null2String(rs1.getString("szfcylx"));// 市政府采用
			String stcylx = Util.null2String(rs1.getString("stcylx"));// 省厅采用
			String szfbcylx = Util.null2String(rs1.getString("szfbcylx"));// 省委/省政府
			String swbcylx = Util.null2String(rs1.getString("swbcylx"));// 省委/省政府
			String gtbcylx = Util.null2String(rs1.getString("gtbcylx"));// 中办/国办
			String jflx = Util.null2String(rs1.getString("jflx"));// 加分
			if (!"".equals(sjcylx)) {
				String[] sjcylxs = sjcylx.split(",");
				for (int i = 0; i < sjcylxs.length; i++) {
					sjcylxTotal.add(sjcylxs[i]);
				}
			}
			if (!"".equals(swcylx1)) {
				String[] swcylx1s = swcylx1.split(",");
				for (int i = 0; i < swcylx1s.length; i++) {
					swcyTotal.add(swcylx1s[i]);
				}
			}
			if (!"".equals(swcylx2)) {
				String[] swcylx2s = swcylx2.split(",");
				for (int i = 0; i < swcylx2s.length; i++) {
					swcyTotal.add(swcylx2s[i]);
				}
			}
			if (!"".equals(szfcylx)) {
				String[] szfcylxs = szfcylx.split(",");
				for (int i = 0; i < szfcylxs.length; i++) {
					szfcyTotal.add(szfcylxs[i]);
				}
			}
			if (!"".equals(stcylx)) {
				String[] stcylxs = stcylx.split(",");
				for (int i = 0; i < stcylxs.length; i++) {
					stcyTotal.add(stcylxs[i]);
				}
			}
			if (!"".equals(szfbcylx)) {
				String[] szfbcylxs = szfbcylx.split(",");
				for (int i = 0; i < szfbcylxs.length; i++) {
					swszfTotal.add(szfbcylxs[i]);
				}
			}
			if (!"".equals(swbcylx)) {
				String[] swbcylxs = swbcylx.split(",");
				for (int i = 0; i < swbcylxs.length; i++) {
					swszfTotal.add(swbcylxs[i]);
				}
			}
			if (!"".equals(gtbcylx)) {
				String[] gtbcylxs = gtbcylx.split(",");
				for (int i = 0; i < gtbcylxs.length; i++) {
					zbgbTotal.add(gtbcylxs[i]);
				}
			}
			if (!"".equals(jflx)) {
				String[] jflxs = jflx.split(",");
				for (int i = 0; i < jflxs.length; i++) {
					jfTotal.add(jflxs[i]);
				}
			}
		}
		String[] sjzbString = new String[] { "4", "5", "6" };
		String[] lzxxString = new String[] { "2" };
		String[] otherString = new String[] { "1", "3" };
		String[] swcyString = new String[] { "11", "12", "13", "14" };
		String[] szfcyString = new String[] { "8", "9", "10" };
		String[] stcyString = new String[] { "17", "18", "19" };
		String[] swszfString = new String[] { "20", "21" };
		String[] zbgbString = new String[] { "22" };
		String[] jfglString = new String[] { "16", "15" };
		Map<String, String> sjzb = map(sjcylxTotal, sjzbString);
		Map<String, String> lzxx = map(sjcylxTotal, lzxxString);
		Map<String, String> other = map(sjcylxTotal, otherString);
		Map<String, String> swcy = map(swcyTotal, swcyString);
		Map<String, String> szfcy = map(szfcyTotal, szfcyString);
		Map<String, String> stcy = map(stcyTotal, stcyString);
		Map<String, String> swszf = map(swszfTotal, swszfString);
		Map<String, String> zbgb = map(zbgbTotal, zbgbString);
		Map<String, String> jfgl = map(jfTotal, jfglString);

		int bczfzs = getFS(sjzbString, sjzb) + getFS(lzxxString, lzxx) + getFS(otherString, other)
				+ getFS(swcyString, swcy) + getFS(szfcyString, szfcy) + getFS(stcyString, stcy)
				+ getFS(swszfString, swszf) + getFS(zbgbString, zbgb) + getFS(jfglString, jfgl);
		return bczfzs;
	}
	
	
	public LinkedList<Object[]> xxnbReportMap(String begindatetime, String enddatetime) {
		LinkedList<Object[]> list = new LinkedList<Object[]>();
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		// 查询部门
		LinkedHashMap<String, String> departmentMap = new LinkedHashMap<String, String>();
		String departmentSQL = "select a.id,a.DEPARTMENTNAME,b.zbsl from HRMDEPARTMENT a, uf_bmxxcyzb b where a.id = b.bm order by a.SHOWORDER asc ";
		rs.execute(departmentSQL);
		while (rs.next()) {
			String departmentID = Util.null2String(rs.getString("id"));
			String departmentName = Util.null2String(rs.getString("departmentname"));
			departmentMap.put(departmentID, departmentName);
		}
		for (Entry<String, String> entry : departmentMap.entrySet()) {
			String depID = entry.getKey();
			String depNa = entry.getValue();
			Object[] strs = new Object[14];
			// 市局采用总数
			String sjcyzsSQL = "select sjcylx,swcylx1,swcylx2,szfcylx,stcylx,szfbcylx,swbcylx,gtbcylx,jflx from uf_xxnbgl where 1=1 and bsbm like '%"
					+ depID + "%' or qtzzbm like '%" + depID + "%' ";
			if (!"".equals(Util.null2String(begindatetime))) {
				sjcyzsSQL += " and xxybjjsrq >='" + begindatetime + "' ";
			}
			if (!"".equals(Util.null2String(enddatetime))) {
				sjcyzsSQL += " and xxybjjsrq <='" + enddatetime + "' ";
			}
			rs1.execute(sjcyzsSQL);
			List<String> sjcylxTotal = new ArrayList<String>();
			List<String> swcyTotal = new ArrayList<String>();
			List<String> szfcyTotal = new ArrayList<String>();
			List<String> stcyTotal = new ArrayList<String>();
			List<String> swszfTotal = new ArrayList<String>();
			List<String> zbgbTotal = new ArrayList<String>();
			List<String> jfTotal = new ArrayList<String>();
			int tgszs = rs1.getCounts();
			while (rs1.next()) {
				String sjcylx = Util.null2String(rs1.getString("sjcylx"));// 市局采用总数 //市局专报/专刊/专项/廉政信息
				String swcylx1 = Util.null2String(rs1.getString("swcylx1"));// 市委采用1
				String swcylx2 = Util.null2String(rs1.getString("swcylx2"));// 市委采用2
				String szfcylx = Util.null2String(rs1.getString("szfcylx"));// 市政府采用
				String stcylx = Util.null2String(rs1.getString("stcylx"));// 省厅采用
				String szfbcylx = Util.null2String(rs1.getString("szfbcylx"));// 省委/省政府
				String swbcylx = Util.null2String(rs1.getString("swbcylx"));// 省委/省政府
				String gtbcylx = Util.null2String(rs1.getString("gtbcylx"));// 中办/国办
				String jflx = Util.null2String(rs1.getString("jflx"));// 加分
				if (!"".equals(sjcylx)) {
					String[] sjcylxs = sjcylx.split(",");
					for (int i = 0; i < sjcylxs.length; i++) {
						sjcylxTotal.add(sjcylxs[i]);
					}
				}
				if (!"".equals(swcylx1)) {
					String[] swcylx1s = swcylx1.split(",");
					for (int i = 0; i < swcylx1s.length; i++) {
						swcyTotal.add(swcylx1s[i]);
					}
				}
				if (!"".equals(swcylx2)) {
					String[] swcylx2s = swcylx2.split(",");
					for (int i = 0; i < swcylx2s.length; i++) {
						swcyTotal.add(swcylx2s[i]);
					}
				}
				if (!"".equals(szfcylx)) {
					String[] szfcylxs = szfcylx.split(",");
					for (int i = 0; i < szfcylxs.length; i++) {
						szfcyTotal.add(szfcylxs[i]);
					}
				}
				if (!"".equals(stcylx)) {
					String[] stcylxs = stcylx.split(",");
					for (int i = 0; i < stcylxs.length; i++) {
						stcyTotal.add(stcylxs[i]);
					}
				}
				if (!"".equals(szfbcylx)) {
					String[] szfbcylxs = szfbcylx.split(",");
					for (int i = 0; i < szfbcylxs.length; i++) {
						swszfTotal.add(szfbcylxs[i]);
					}
				}
				if (!"".equals(swbcylx)) {
					String[] swbcylxs = swbcylx.split(",");
					for (int i = 0; i < swbcylxs.length; i++) {
						swszfTotal.add(swbcylxs[i]);
					}
				}
				if (!"".equals(gtbcylx)) {
					String[] gtbcylxs = gtbcylx.split(",");
					for (int i = 0; i < gtbcylxs.length; i++) {
						zbgbTotal.add(gtbcylxs[i]);
					}
				}
				if (!"".equals(jflx)) {
					String[] jflxs = jflx.split(",");
					for (int i = 0; i < jflxs.length; i++) {
						jfTotal.add(jflxs[i]);
					}
				}
			}
			String[] sjzbString = new String[] { "4", "5", "6" };
			String[] lzxxString = new String[] { "2" };
			String[] otherString = new String[] { "1", "3" };
			String[] swcyString = new String[] { "11", "12", "13", "14" };
			String[] szfcyString = new String[] { "8", "9", "10" };
			String[] stcyString = new String[] { "17", "18", "19" };
			String[] swszfString = new String[] { "20", "21" };
			String[] zbgbString = new String[] { "22" };
			String[] jfglString = new String[] { "16", "15" };
			new weaver.general.BaseBean().writeLog("部门ID:" + depID + ";部门名字:" + depNa);
			Map<String, String> sjzb = map(sjcylxTotal, sjzbString);
			Map<String, String> lzxx = map(sjcylxTotal, lzxxString);
			Map<String, String> other = map(sjcylxTotal, otherString);
			Map<String, String> swcy = map(swcyTotal, swcyString);
			Map<String, String> szfcy = map(szfcyTotal, szfcyString);
			Map<String, String> stcy = map(stcyTotal, stcyString);
			Map<String, String> swszf = map(swszfTotal, swszfString);
			Map<String, String> zbgb = map(zbgbTotal, zbgbString);
			Map<String, String> jfgl = map(jfTotal, jfglString);

			int sjcyzs = sjcylxTotal.size();
			int sjzbzs = getTotal(sjzbString, sjzb);
			int lzxxzs = getTotal(lzxxString, lzxx);
			int swcyzs = getTotal(swcyString, swcy);
			int szfcyzs = getTotal(szfcyString, szfcy);
			int stcyzs = getTotal(stcyString, stcy);
			int swszfzs = getTotal(swszfString, swszf);
			int zbgbzs = getTotal(zbgbString, zbgb);
			int jfgkzs = getFS(jfglString, jfgl);
			String gdzb = getZBSL(depID);
			int bczfzs = getFS(sjzbString, sjzb) + getFS(lzxxString, lzxx) + getFS(otherString, other)
					+ getFS(swcyString, swcy) + getFS(szfcyString, szfcy) + getFS(stcyString, stcy)
					+ getFS(swszfString, swszf) + getFS(zbgbString, zbgb) + getFS(jfglString, jfgl);
			int ndljzs = xxnbReport4NDExport(begindatetime, enddatetime, depID);
			strs[0] = depNa;
			strs[1] = sjcyzs;
			strs[2] = sjzbzs;
			strs[3] = lzxxzs;
			strs[4] = swcyzs;
			strs[5] = szfcyzs;
			strs[6] = stcyzs;
			strs[7] = swszfzs;
			strs[8] = zbgbzs;
			strs[9] = jfgkzs;
			strs[10] = tgszs;
			strs[11] = gdzb;
			strs[12] = bczfzs;
			strs[13] = ndljzs;
			list.add(strs);
		}
		// 查询部门
		return list;
	}

	public int xxnbReport4NDExport(String begindatetime, String enddatetime, String depID) {
		RecordSet rs1 = new RecordSet();
		// 市局采用总数
		String sjcyzsSQL = "select sjcylx,swcylx1,swcylx2,szfcylx,stcylx,szfbcylx,swbcylx,gtbcylx,jflx from uf_xxnbgl where 1=1 and bsbm like '%"
				+ depID + "%' or qtzzbm like '%" + depID + "%' ";
		if (!"".equals(Util.null2String(begindatetime))) {
			sjcyzsSQL += " and xxybjjsrq >='" + begindatetime + "' ";
		}
		if (!"".equals(Util.null2String(enddatetime))) {
			sjcyzsSQL += " and xxybjjsrq <='" + enddatetime + "' ";
		}
		rs1.execute(sjcyzsSQL);
		List<String> sjcylxTotal = new ArrayList<String>();
		List<String> swcyTotal = new ArrayList<String>();
		List<String> szfcyTotal = new ArrayList<String>();
		List<String> stcyTotal = new ArrayList<String>();
		List<String> swszfTotal = new ArrayList<String>();
		List<String> zbgbTotal = new ArrayList<String>();
		List<String> jfTotal = new ArrayList<String>();
		while (rs1.next()) {
			String sjcylx = Util.null2String(rs1.getString("sjcylx"));// 市局采用总数 //市局专报/专刊/专项/廉政信息
			String swcylx1 = Util.null2String(rs1.getString("swcylx1"));// 市委采用1
			String swcylx2 = Util.null2String(rs1.getString("swcylx2"));// 市委采用2
			String szfcylx = Util.null2String(rs1.getString("szfcylx"));// 市政府采用
			String stcylx = Util.null2String(rs1.getString("stcylx"));// 省厅采用
			String szfbcylx = Util.null2String(rs1.getString("szfbcylx"));// 省委/省政府
			String swbcylx = Util.null2String(rs1.getString("swbcylx"));// 省委/省政府
			String gtbcylx = Util.null2String(rs1.getString("gtbcylx"));// 中办/国办
			String jflx = Util.null2String(rs1.getString("jflx"));// 加分
			if (!"".equals(sjcylx)) {
				String[] sjcylxs = sjcylx.split(",");
				for (int i = 0; i < sjcylxs.length; i++) {
					sjcylxTotal.add(sjcylxs[i]);
				}
			}
			if (!"".equals(swcylx1)) {
				String[] swcylx1s = swcylx1.split(",");
				for (int i = 0; i < swcylx1s.length; i++) {
					swcyTotal.add(swcylx1s[i]);
				}
			}
			if (!"".equals(swcylx2)) {
				String[] swcylx2s = swcylx2.split(",");
				for (int i = 0; i < swcylx2s.length; i++) {
					swcyTotal.add(swcylx2s[i]);
				}
			}
			if (!"".equals(szfcylx)) {
				String[] szfcylxs = szfcylx.split(",");
				for (int i = 0; i < szfcylxs.length; i++) {
					szfcyTotal.add(szfcylxs[i]);
				}
			}
			if (!"".equals(stcylx)) {
				String[] stcylxs = stcylx.split(",");
				for (int i = 0; i < stcylxs.length; i++) {
					stcyTotal.add(stcylxs[i]);
				}
			}
			if (!"".equals(szfbcylx)) {
				String[] szfbcylxs = szfbcylx.split(",");
				for (int i = 0; i < szfbcylxs.length; i++) {
					swszfTotal.add(szfbcylxs[i]);
				}
			}
			if (!"".equals(swbcylx)) {
				String[] swbcylxs = swbcylx.split(",");
				for (int i = 0; i < swbcylxs.length; i++) {
					swszfTotal.add(swbcylxs[i]);
				}
			}
			if (!"".equals(gtbcylx)) {
				String[] gtbcylxs = gtbcylx.split(",");
				for (int i = 0; i < gtbcylxs.length; i++) {
					zbgbTotal.add(gtbcylxs[i]);
				}
			}
			if (!"".equals(jflx)) {
				String[] jflxs = jflx.split(",");
				for (int i = 0; i < jflxs.length; i++) {
					jfTotal.add(jflxs[i]);
				}
			}
		}
		String[] sjzbString = new String[] { "4", "5", "6" };
		String[] lzxxString = new String[] { "2" };
		String[] otherString = new String[] { "1", "3" };
		String[] swcyString = new String[] { "11", "12", "13", "14" };
		String[] szfcyString = new String[] { "8", "9", "10" };
		String[] stcyString = new String[] { "17", "18", "19" };
		String[] swszfString = new String[] { "20", "21" };
		String[] zbgbString = new String[] { "22" };
		String[] jfglString = new String[] { "16", "15" };
		Map<String, String> sjzb = map(sjcylxTotal, sjzbString);
		Map<String, String> lzxx = map(sjcylxTotal, lzxxString);
		Map<String, String> other = map(sjcylxTotal, otherString);
		Map<String, String> swcy = map(swcyTotal, swcyString);
		Map<String, String> szfcy = map(szfcyTotal, szfcyString);
		Map<String, String> stcy = map(stcyTotal, stcyString);
		Map<String, String> swszf = map(swszfTotal, swszfString);
		Map<String, String> zbgb = map(zbgbTotal, zbgbString);
		Map<String, String> jfgl = map(jfTotal, jfglString);

		int bczfzs = getFS(sjzbString, sjzb) + getFS(lzxxString, lzxx) + getFS(otherString, other)
				+ getFS(swcyString, swcy) + getFS(szfcyString, szfcy) + getFS(stcyString, stcy)
				+ getFS(swszfString, swszf) + getFS(zbgbString, zbgb) + getFS(jfglString, jfgl);
		return bczfzs;
	}

	public Map<String, String> map(List<String> list, String[] strs) {
		Map<String, String> sjzb = new HashMap<String, String>();
		if (list.size() == 0) {
			for (String s : strs) {
				sjzb.put(s, "0");
			}
		} else {
			for (String str : strs) {
				for (String s : list) {
					if (s.equals(str)) {
						if (sjzb.containsKey(s)) {
							sjzb.put(s, Util.null2String(Integer.parseInt(sjzb.get(s)) + 1));
						} else {
							sjzb.put(s, "1");
						}
					}
				}
			}
		}
		return sjzb;
	}

	public int getTotal(String[] strs, Map<String, String> map) {
		int sjzbzs = 0;
		for (String s : strs) {
			sjzbzs += Integer.parseInt(Util.null2o(map.get(s)));
		}
		return sjzbzs;
	}

	public int getFS(String[] strs, Map<String, String> map) {
		RecordSet rs = new RecordSet();
		int c = 0;
		for (String s : strs) {
			rs.execute("select fs from uf_xxnb_sjcy where id = " + s + "");
			rs.next();
			String fs = Util.null2o(rs.getString("fs"));
			int count = Integer.parseInt(Util.null2o(map.get(s)));
			c += count * (Integer.parseInt(fs));
		}
		return c;
	}
	
	public String getZBSL(String depid) {
		String sql = "select a.id,a.DEPARTMENTNAME,b.zbsl from HRMDEPARTMENT a, uf_bmxxcyzb b where a.id = b.bm and a.id = '"+depid+"'";
		RecordSet rs = new RecordSet();
		rs.execute(sql);
		rs.next();
		String zbsl = Util.null2String(rs.getString("zbsl"));
		return zbsl;
	}
}
