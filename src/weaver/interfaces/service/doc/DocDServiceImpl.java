package weaver.interfaces.service.doc;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;

public class DocDServiceImpl extends BaseBean implements DocDService {

	@Override
	public String searchIDBySubject(String json) {
		String createdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String createtime = new SimpleDateFormat("HH:mm").format(new Date());
		String modedatacreatetime = new SimpleDateFormat("HH:mm:ss").format(new Date());
		//{"subject":"aa","typeid":"aa"}
		JSONObject jsonstr = JSON.parseObject(json);
		String subject = (String) jsonstr.get("subject");
		String typeid = (String) jsonstr.get("typeid");
		RecordSet rs = new RecordSet();
		
		// 一周工作安排 182
		// 值班表 181
		String str="";
		if ("gzap".equals(typeid)) {
			String sql = "select id from docdetail where seccategory = '182' and DOCSUBJECT = '" + subject + "'";
			rs.execute(sql);
			rs.next();
			String id = Util.null2String(rs.getString("id"));
			str="{\"docid\":\"" + id + "\"}";
			try {
				String tableNameSQL = "select w.tablename,w.id from modeinfo m,workflow_bill w where w.id=m.formid and m.id=382";
				rs.execute(tableNameSQL);
				rs.next();
				String tableName = Util.null2String(rs.getString("tablename"));
				String tablesql = "insert into " + tableName + " (createdate,createtime,receivemessage,returnmessage,formmodeid,"
						+ "modedatacreater,modedatacreatertype,modedatacreatedate,modedatacreatetime) values ('" + createdate
						+ "','" + createtime + "','" + json + "'" + ",'" + str + "','382','1','0','" + createdate
						+ "','" + modedatacreatetime + "')";

				rs.execute(tablesql);
			} catch (Exception e) {
				writeLog("searchIDBySubject异常:"+e.getMessage());
			}
			
			return str;
		}
		if ("zbb".equals(typeid)) {
			String sql = "select id from docdetail where seccategory = '181' and DOCSUBJECT = '" + subject + "'";
			rs.execute(sql);
			rs.next();
			String id = Util.null2String(rs.getString("id"));
			str="{\"docid\":\"" + id + "\"}";
			try {
				String tableNameSQL = "select w.tablename,w.id from modeinfo m,workflow_bill w where w.id=m.formid and m.id=382";
				rs.execute(tableNameSQL);
				rs.next();
				String tableName = Util.null2String(rs.getString("tablename"));
				String tablesql = "insert into " + tableName + " (createdate,createtime,receivemessage,returnmessage,formmodeid,"
						+ "modedatacreater,modedatacreatertype,modedatacreatedate,modedatacreatetime) values ('" + createdate
						+ "','" + createtime + "','" + json + "'" + ",'" + str + "','382','1','0','" + createdate
						+ "','" + modedatacreatetime + "')";

				rs.execute(tablesql);
			} catch (Exception e) {
				writeLog("searchIDBySubject异常:"+e.getMessage());
			}
			
			return str;
		}
		str="{\"docid\":\"\"}";
		try {
			String tableNameSQL = "select w.tablename,w.id from modeinfo m,workflow_bill w where w.id=m.formid and m.id=382";
			rs.execute(tableNameSQL);
			rs.next();
			String tableName = Util.null2String(rs.getString("tablename"));
			String tablesql = "insert into " + tableName + " (createdate,createtime,receivemessage,returnmessage,formmodeid,"
					+ "modedatacreater,modedatacreatertype,modedatacreatedate,modedatacreatetime) values ('" + createdate
					+ "','" + createtime + "','" + json + "'" + ",'" + str + "','382','1','0','" + createdate
					+ "','" + modedatacreatetime + "')";

			rs.execute(tablesql);
		} catch (Exception e) {
			writeLog("searchIDBySubject异常:"+e.getMessage());
		}
		return str;
		
		
	}
}
