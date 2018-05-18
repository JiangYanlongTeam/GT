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
import weaver.hrm.company.DepartmentComInfo;

/**
 * 督查督办列表（带发起督办功能）
 */
public class DCDB extends BaseBean {

	/**
	 * 督查督办列表（带发起督办功能）
	 * 
	 * @param user
	 * @param otherparams
	 * @param request
	 * @param response
	 * @return
	 */
	public List<Map<String, String>> getDTData(User user, Map<String, String> otherparams, HttpServletRequest request,
			HttpServletResponse response) {
		String _wh = Util.null2String(otherparams.get("wh"));
		String _gwmc = Util.null2String(otherparams.get("gwmc"));
		String _swrqq = Util.null2String(otherparams.get("swrqq"));
		String _swrqz = Util.null2String(otherparams.get("swrqz"));
		RecordSet rs = new RecordSet();
		String DCDBWORKFLOWID = getPropValue("GWSWBL", "DCDBWORKFLOWID");
		String GWSWBL_TABLENAME = getPropValue("DCDB", "GWSWBL_TABELNAME");
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
//		String sql = "select a.requestid,b.requestname,a.wh,a.swrq,a.lwdw,a.zbbmwb,a.sfcfdb from " + GWSWBL_TABLENAME
//				+ " a , workflow_requestbase b " + "where a.requestid = b.requestid and b.currentnodeid = " + nodeid
//				+ " and a.zbbm is not null ";
		String sql = "select a.requestid,b.requestname,a.wh,a.swrq,a.lwdw,a.zbbm,a.sfcfdb,b.CURRENTNODEID from " + GWSWBL_TABLENAME
				+ " a , workflow_requestbase b " + "where a.requestid = b.requestid and a.zbbm is not null and b.CURRENTNODEID not in (279,541,280,281) ";
		if(!"".equals(_wh)) {
			sql += " and a.wh like '%"+_wh+"%' ";
		}
		if(!"".equals(_gwmc)) {
			sql += " and b.requestname like '%"+_gwmc+"%' ";
		}
		if(!"".equals(_swrqq)) {
			sql += " and a.swrq >= '"+_swrqq+"' ";
		}
		if(!"".equals(_swrqz)) {
			sql += " and a.swrq <= '"+_swrqz+"' ";
		}
		sql += " order by a.requestid desc ";
		rs.execute(sql);
		while (rs.next()) {
			String wh = Util.null2String(rs.getString("wh"));
			String swrq = Util.null2String(rs.getString("swrq"));
			String lwdw = Util.null2String(rs.getString("lwdw"));
			String zbbmwb = Util.null2String(rs.getString("zbbm"));
			DepartmentComInfo d = new DepartmentComInfo();
			zbbmwb = d.getDepartmentNames(zbbmwb);
			String sfcfdb = Util.null2String(rs.getString("sfcfdb"));
			String gwmc = Util.null2String(rs.getString("requestname"));
			String requestid = Util.null2String(rs.getString("requestid"));
			String CURRENTNODEID = Util.null2String(rs.getString("CURRENTNODEID"));

			Map<String, String> map = new HashMap<String, String>();
			String name = "<a href='/proj/RequestView.jsp?requestid=" + requestid + "' title='" + gwmc
					+ "' target='_blank'>" + gwmc + "</a>";
			String fqdb = "";
			if("0".equals(sfcfdb)) {
				fqdb = "<a onclick=\"viewDone('" + requestid + "','"+DCDBWORKFLOWID+"')\" title='查看已发起督办' style='cursor:pointer;color:blue'>查看已发起督办</a>";
			} else {
				if(CURRENTNODEID.equals("284") || CURRENTNODEID.equals("285")) {
					fqdb = "<span style='cursor:pointer;color:gray'>发起督办</span>";
				} else {
					fqdb = "<a onclick=\"openDCDB('" + requestid + "')\" title='发起督办' style='cursor:pointer;color:blue'>发起督办</a>";
				}
			}
			
			map.put("wh", wh);
			map.put("gwmc", name);
			map.put("swrq", swrq);
			map.put("lwdw", lwdw);
			map.put("fqdb", fqdb);
			map.put("zbbmwb", zbbmwb);
			map.put("requestid", requestid);
			data.add(map);
		}
		return data;
	}
	
	
	public String del(String requestid) {
		return "<a style=\"color:blue;cursor:pointer;\" target=\"_blank\" onclick=\"del('"+requestid+"')\">删除</a>";
	}
	
	public String cbtzd(String requestid) {
		return "<a style=\"color:blue;cursor:pointer;\" target=\"_blank\" onclick=\"cbtzd('"+requestid+"')\">催办通知单</a>";
	}
	
	public String wxtx(String requestid) {
		return "<a style=\"color:blue;cursor:pointer;\" target=\"_blank\" onclick=\"wxtx('"+requestid+"')\">微信提醒</a>";
	}
	
	public String getCBCS(String requestid) {
		String sql = "select count(*) count from formtable_main_52 where dblc = '"+requestid+"'";
		RecordSet rs = new RecordSet();
		rs.execute(sql);
		rs.next();
		String count = Util.null2o(rs.getString("count"));
		return count;
	}
}
