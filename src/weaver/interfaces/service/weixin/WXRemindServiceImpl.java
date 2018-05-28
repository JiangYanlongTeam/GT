package weaver.interfaces.service.weixin;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;

public class WXRemindServiceImpl extends BaseBean {

	/**
	 * 发送微信提醒
	 * 
	 * @param jsondata
	 * @return
	 */
	public String sendWXRemind(String jsondata) {
		// {"content":"","title":"","userids":""}
		String createdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String createtime = new SimpleDateFormat("HH:mm").format(new Date());
		String modedatacreatetime = new SimpleDateFormat("HH:mm:ss").format(new Date());
		JSONObject jsonObject = JSON.parseObject(jsondata);
		String content = Util.null2String(jsonObject.get("content"));
		String title = Util.null2String(jsonObject.get("title"));
		String userids = Util.null2String(jsonObject.get("userids"));
		String wxid = Util.null2String(jsonObject.get("wxid"));
		String json="";
		if ("".equals(content)) {
			WXRemindMode mode = new WXRemindMode();
			mode.setCode("-1");
			mode.setMessage("content不能为空");
			json=JSON.toJSON(mode).toString();
			try {
				RecordSet rs=new RecordSet();
				String tableNameSQL = "select w.tablename,w.id from modeinfo m,workflow_bill w where w.id=m.formid and m.id=402";
				rs.execute(tableNameSQL);
				rs.next();
				String tableName = Util.null2String(rs.getString("tablename"));

				String sql = "insert into " + tableName + " (createdate,createtime,content,title,receivemessage,returnmessage,formmodeid,"
						+ "modedatacreater,modedatacreatertype,modedatacreatedate,modedatacreatetime) values ('" + createdate
						+ "','" + createtime + "','" + content + "','" + title + "','" + jsondata + "'" + ",'" + json + "','402','1','0','" + createdate
						+ "','" + modedatacreatetime + "')";

				rs.execute(sql);
			} catch (Exception e) {
				writeLog("sendWXRemind异常:"+e.getMessage());
			}
			
			return json;
		}
		if ("".equals(title)) {
			WXRemindMode mode = new WXRemindMode();
			mode.setCode("-1");
			mode.setMessage("title不能为空");
			json=JSON.toJSON(mode).toString();
			try {
				RecordSet rs=new RecordSet();
				String tableNameSQL = "select w.tablename,w.id from modeinfo m,workflow_bill w where w.id=m.formid and m.id=402";
				rs.execute(tableNameSQL);
				rs.next();
				String tableName = Util.null2String(rs.getString("tablename"));

				String sql = "insert into " + tableName + " (createdate,createtime,content,title,receivemessage,returnmessage,formmodeid,"
						+ "modedatacreater,modedatacreatertype,modedatacreatedate,modedatacreatetime) values ('" + createdate
						+ "','" + createtime + "','" + content + "','" + title + "','" + jsondata + "'" + ",'" + json + "','402','1','0','" + createdate
						+ "','" + modedatacreatetime + "')";

				rs.execute(sql);
			} catch (Exception e) {
				writeLog("sendWXRemind异常:"+e.getMessage());
			}
			return json;
		}
		if ("".equals(userids)) {
			WXRemindMode mode = new WXRemindMode();
			mode.setCode("-1");
			mode.setMessage("userids不能为空");
			json=JSON.toJSON(mode).toString();
			try {
				RecordSet rs=new RecordSet();
				String tableNameSQL = "select w.tablename,w.id from modeinfo m,workflow_bill w where w.id=m.formid and m.id=402";
				rs.execute(tableNameSQL);
				rs.next();
				String tableName = Util.null2String(rs.getString("tablename"));

				String sql = "insert into " + tableName + " (createdate,createtime,content,title,receivemessage,returnmessage,formmodeid,"
						+ "modedatacreater,modedatacreatertype,modedatacreatedate,modedatacreatetime) values ('" + createdate
						+ "','" + createtime + "','" + content + "','" + title + "','" + jsondata + "'" + ",'" + json + "','402','1','0','" + createdate
						+ "','" + modedatacreatetime + "')";

				rs.execute(sql);
			} catch (Exception e) {
				writeLog("sendWXRemind异常:"+e.getMessage());
			}
			return json;
		}
		if ("".equals(wxid)) {
			WXRemindMode mode = new WXRemindMode();
			mode.setCode("-1");
			mode.setMessage("wxid不能为空");
			json=JSON.toJSON(mode).toString();
			try {
				RecordSet rs=new RecordSet();
				String tableNameSQL = "select w.tablename,w.id from modeinfo m,workflow_bill w where w.id=m.formid and m.id=402";
				rs.execute(tableNameSQL);
				rs.next();
				String tableName = Util.null2String(rs.getString("tablename"));

				String sql = "insert into " + tableName + " (createdate,createtime,content,title,receivemessage,returnmessage,formmodeid,"
						+ "modedatacreater,modedatacreatertype,modedatacreatedate,modedatacreatetime) values ('" + createdate
						+ "','" + createtime + "','" + content + "','" + title + "','" + jsondata + "'" + ",'" + json + "','402','1','0','" + createdate
						+ "','" + modedatacreatetime + "')";

				rs.execute(sql);
			} catch (Exception e) {
				writeLog("sendWXRemind异常:"+e.getMessage());
			}
			return json;
		}

		SendWxRemind send = new SendWxRemind();
		WXRemindMode mode = send.sendWxMsg(title, content, userids, wxid);
		json=JSON.toJSON(mode).toString();
		try {
			RecordSet rs=new RecordSet();
			String tableNameSQL = "select w.tablename,w.id from modeinfo m,workflow_bill w where w.id=m.formid and m.id=402";
			rs.execute(tableNameSQL);
			rs.next();
			String tableName = Util.null2String(rs.getString("tablename"));

			String sql = "insert into " + tableName + " (createdate,createtime,content,title,receivemessage,returnmessage,formmodeid,"
					+ "modedatacreater,modedatacreatertype,modedatacreatedate,modedatacreatetime) values ('" + createdate
					+ "','" + createtime + "','" + content + "','" + title + "','" + jsondata + "'" + ",'" + json + "','402','1','0','" + createdate
					+ "','" + modedatacreatetime + "')";

			rs.execute(sql);
		} catch (Exception e) {
			writeLog("sendWXRemind异常:"+e.getMessage());
		}
		
		return json;
	}
}
