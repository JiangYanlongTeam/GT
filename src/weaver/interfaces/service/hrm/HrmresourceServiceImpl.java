package weaver.interfaces.service.hrm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.general.BaseBean;

public class HrmresourceServiceImpl extends BaseBean implements HrmresourceService {

	@Override
	public String getHrmID(String hrmLoginIDS) {
		RecordSet rs = new RecordSet();
		String createdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String createtime = new SimpleDateFormat("HH:mm").format(new Date());
		String modedatacreatetime = new SimpleDateFormat("HH:mm:ss").format(new Date());
		// 接收参数 hrmLoginIDS
		List<HrmInfo> list = new ArrayList<HrmInfo>();
		JSONArray arrs = JSON.parseArray(hrmLoginIDS);
		for (int i = 0; i < arrs.size(); i++) {
			JSONObject arr = (JSONObject) arrs.get(i);
			String loginname = (String) arr.get("loginname");
			rs.execute("select id from hrmresource where loginid = '" + loginname + "'");
			rs.next();
			String id = Util.null2String(rs.getString("id"));
			if (!"".equals(id)) {
				HrmInfo hrm = new HrmInfo();
				hrm.setId(id);
				hrm.setLoginname(loginname);
				list.add(hrm);
			}
		}
		String jsonstr = JSON.toJSON(list).toString();
		try {
			RecordSet rs1 = new RecordSet();
			String tableNameSQL = "select w.tablename,w.id from modeinfo m,workflow_bill w where w.id=m.formid and m.id=381";
			rs1.execute(tableNameSQL);
			rs1.next();
			String tableName = Util.null2String(rs1.getString("tablename"));

			String sql = "insert into " + tableName + " (createdate,createtime,receivemessage,returnmessage,formmodeid,"
					+ "modedatacreater,modedatacreatertype,modedatacreatedate,modedatacreatetime) values ('" + createdate
					+ "','" + createtime + "','" + hrmLoginIDS + "'" + ",'" + jsonstr + "','381','1','0','" + createdate
					+ "','" + modedatacreatetime + "')";

			rs1.execute(sql);
			
		} catch (Exception e) {
			writeLog("getHrmID异常:"+e.getMessage());
		}
		return jsonstr;
		
	}

	
	
	
}
