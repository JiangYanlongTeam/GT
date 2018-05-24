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
		writeLog("sql:"+sql);
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
			String name = "<a onclick=\"openFlow('"+requestid+"')\" title='" + gwmc
					+ "' style='cursor:pointer'>" + gwmc + "</a>";
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

			boolean orNot = onclickOrNot2(requestid,user.getUID());
			if(orNot) {
				map.put("sfdj", "是");
			} else {
				map.put("sfdj", "否");
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


	/**
	 * 督查督办列表（带发起督办功能）
	 *
	 * @param user
	 * @param otherparams
	 * @param request
	 * @param response
	 * @return
	 */
	public List<Map<String, String>> getDTData2(User user, Map<String, String> otherparams, HttpServletRequest request,
												HttpServletResponse response) {
		String type = Util.null2String(otherparams.get("type"));
		String requestname = Util.null2String(otherparams.get("requestname"));
		String id = Util.null2String(otherparams.get("reqid"));
		RecordSet rs = new RecordSet();
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();


		boolean isExist = existInRole(user.getUID()+"");
		if(!isExist) {
			return data;
		}

		String sql = "select * from VIEW_JLDPSYJ a where 1=1 ";
		if(!"".equals(type)) {
			sql += " and a.type like '%"+type+"%' ";
		}
		if(!"".equals(requestname)) {
			sql += " and a.requestname like '%"+requestname+"%' ";
		}
		if(!"".equals(id)) {
			sql += " and a.id = '"+id+"' ";
		}
		sql += " order by a.maxdate desc ";
		rs.execute(sql);
		while (rs.next()) {
			String _type = Util.null2String(rs.getString("type"));
			String _workflowid = Util.null2String(rs.getString("workflowid"));
			String _id = Util.null2String(rs.getString("id"));
			String _requestname = Util.null2String(rs.getString("requestname"));
			String _xmyj = Util.null2String(rs.getString("xmyj"));
			String _wdyj = Util.null2String(rs.getString("wdyj"));
			String _dhgyj = Util.null2String(rs.getString("dhgyj"));
			String _fxyyj = Util.null2String(rs.getString("fxyyj"));
			String _syjyj = Util.null2String(rs.getString("syjyj"));
			String _pwhyj = Util.null2String(rs.getString("pwhyj"));
			String _qbyj = Util.null2String(rs.getString("qbyj"));
			String _wxqyj = Util.null2String(rs.getString("wxqyj"));
			String _wjmyj = Util.null2String(rs.getString("wjmyj"));

			Map<String, String> map = new HashMap<String, String>();
			String name = "<a onclick=\"openFlow('"+_id+"')\" title='" + _requestname
					+ "' style='cursor:pointer'>" + _requestname + "</a>";

			boolean orNot = onclickOrNot(_id,user.getUID());
			if(orNot) {
				map.put("sfdj", "是");
			} else {
				map.put("sfdj", "否");
			}

			map.put("typename", _type);
			map.put("requestname", name);
			map.put("xmyj", _xmyj);
			map.put("wdyj", _wdyj);
			map.put("dhgyj", _dhgyj);
			map.put("fxyyj", _fxyyj);
			map.put("syjyj", _syjyj);
			map.put("pwhyj", _pwhyj);
			map.put("qbyj", _qbyj);
			map.put("wxqyj", _wxqyj);
			map.put("wjmyj", _wjmyj);

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

	/**
	 * 查询是否点击
	 *
	 * @param iid
	 * @return
	 */
	public boolean onclickOrNot(String iid,int userid) {
		RecordSet rs = new RecordSet();
		rs.execute("select * from uf_ldpsck where iid = '"+iid+"' and hrmid = '"+userid+"'");
		while(rs.next()) {
			return true;
		}
		return false;
	}
	/**
	 * 查询是否点击
	 *
	 * @param iid
	 * @return
	 */
	public boolean onclickOrNot2(String iid,int userid) {
		RecordSet rs = new RecordSet();
		rs.execute("select * from UF_DCDBCXBJ where iid = '"+iid+"' and hrmid = '"+userid+"'");
		while(rs.next()) {
			return true;
		}
		return false;
	}


	/**
	 * 判断角色中是否存在传入人员id
	 * @param id
	 * @return
	 */
	public boolean existInRole(String id) {
		RecordSet rs = new RecordSet();
		rs.execute("select * from HRMROLEMEMBERS where ROLEID = 541 and resourceid = '"+id+"'");
		while(rs.next()) {
			return true;
		}
		return false;
	}
}
