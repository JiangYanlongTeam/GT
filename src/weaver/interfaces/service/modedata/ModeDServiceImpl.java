package weaver.interfaces.service.modedata;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import weaver.conn.RecordSet;
import weaver.formmode.webservices.ModeDataServiceImpl;
import weaver.formmode.webservices.ModeDateService;
import weaver.general.BaseBean;
import weaver.general.Util;

public class ModeDServiceImpl extends BaseBean implements ModeDService {

	@Override//插入来件查阅
	public String insert(String data) {
		String createdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String createtime = new SimpleDateFormat("HH:mm").format(new Date());
		String modedatacreatetime = new SimpleDateFormat("HH:mm:ss").format(new Date());
		//[{"mc":"aa","dz":"aa1","fjr":"aa2","sjr","aa3","fjrq":"aa4"}],{"mc":"aa","dz":"aa1","fjr":"aa2","sjr","aa3","fjrq":"aa4"}]
		//[{"mc":"aa"},{"dz":"aa1"},{"fjr":"aa2"},{"sjr","aa3"},{"fjrq":"aa4"},{"mc":"aa"},{"dz":"aa1"},{"fjr":"aa2"},{"sjr","aa3"},{"fjrq":"aa4"}]
		JSONArray arrs = JSON.parseArray(data);
		ModeDateService service = new ModeDataServiceImpl();
		String jsonstr="";
		for (int i = 0; i < arrs.size(); i++) {
			JSONObject arr = (JSONObject) arrs.get(i);
			String mc = (String) arr.get("mc");
			String dz = (String) arr.get("dz");
			String fjr = (String) arr.get("fjr");
			String sjr = (String) arr.get("sjr");
			String fjrq = (String) arr.get("fjrq");
			String fjrid = getHrmId(fjr);
			String sjrids = getHrmIds(sjr);
			String[] strs = sjrids.split(",");
			for (String sjridss : strs) {
				if(!"".equals(fjrid)) {
					String nwdz = "";
					if(!"".equals(dz)) {
						nwdz = dz;
						nwdz = nwdz.replace("http://218.94.36.101:8888/Ftpfile/", "http://192.168.1.93/ftpserver/RES/SubData/");
					}
					StringBuilder sb = new StringBuilder();
					sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("<ROOT>").append("<header>")
							.append("<userid>" + fjrid + "</userid>")
							.append("<modeid>201</modeid><id></id></header><search><condition /><right>Y</right></search><data id=\"\"><maintable>");
					sb.append("<field>");
					sb.append("<filedname>mc</filedname>");
					sb.append("<fileddbtype>varchar(500)</fileddbtype>");
					sb.append("<filedvalue>" + mc + "</filedvalue>");
					sb.append("</field>");
					sb.append("<field>");
					sb.append("<filedname>nwdz</filedname>");
					sb.append("<fileddbtype>varchar(200)</fileddbtype>");
					sb.append("<filedvalue>" + nwdz + "</filedvalue>");
					sb.append("</field>");
					sb.append("<field>");
					sb.append("<filedname>dz</filedname>");
					sb.append("<fileddbtype>varchar(200)</fileddbtype>");
					sb.append("<filedvalue>" + dz + "</filedvalue>");
					sb.append("</field>");
					sb.append("<field>");
					sb.append("<filedname>fjr</filedname>");
					sb.append("<fileddbtype>varchar(200)</fileddbtype>");
					sb.append("<filedvalue>" + fjrid + "</filedvalue>");
					sb.append("</field>");
					sb.append("<field>");
					sb.append("<filedname>sjr</filedname>");
					sb.append("<fileddbtype>varchar(200)</fileddbtype>");
					sb.append("<filedvalue>" + sjridss + "</filedvalue>");
					sb.append("</field>");
					sb.append("<field>");
					sb.append("<filedname>fjrq</filedname>");
					sb.append("<fileddbtype>varchar(200)</fileddbtype>");
					sb.append("<filedvalue>" + fjrq + "</filedvalue>");
					sb.append("</field>");
					sb.append("<field>");
					sb.append("<filedname>viewtype</filedname>");
					sb.append("<fileddbtype>varchar(200)</fileddbtype>");
					sb.append("<filedvalue>0</filedvalue>");
					sb.append("</field>");
					sb.append("</maintable>");
					sb.append("</data>");
					sb.append("</ROOT>");
					new BaseBean().writeLog("传入参数：" + sb.toString());
					String result = service.saveModeData(sb.toString());
					Map<String, String> map = ana(result);
					if (map.isEmpty() && null == map.get("id")) {
						ModeInfo mode = new ModeInfo();
						mode.setMessage(result);
						jsonstr=JSON.toJSON(mode).toString();
						try {
							RecordSet rs=new RecordSet();
							String tableNameSQL = "select w.tablename,w.id from modeinfo m,workflow_bill w where w.id=m.formid and m.id=383";
							rs.execute(tableNameSQL);
							rs.next();
							String tableName = Util.null2String(rs.getString("tablename"));

							String sql = "insert into " + tableName + " (createdate,createtime,receivemessage,returnmessage,formmodeid,"
									+ "modedatacreater,modedatacreatertype,modedatacreatedate,modedatacreatetime) values ('" + createdate
									+ "','" + createtime + "','" + data + "'" + ",'" + jsonstr + "','383','1','0','" + createdate
									+ "','" + modedatacreatetime + "')";

							rs.execute(sql);
						} catch (Exception e) {
							writeLog("insert异常:"+e.getMessage());
						}
						
						return jsonstr;
					}
				}
			}
		}
		ModeInfo mode = new ModeInfo();
		mode.setMessage("操作成功");
		jsonstr=JSON.toJSON(mode).toString();
		try {
			RecordSet rs=new RecordSet();
			String tableNameSQL = "select w.tablename,w.id from modeinfo m,workflow_bill w where w.id=m.formid and m.id=383";
			rs.execute(tableNameSQL);
			rs.next();
			String tableName = Util.null2String(rs.getString("tablename"));

			String sql = "insert into " + tableName + " (createdate,createtime,receivemessage,returnmessage,formmodeid,"
					+ "modedatacreater,modedatacreatertype,modedatacreatedate,modedatacreatetime) values ('" + createdate
					+ "','" + createtime + "','" + data + "'" + ",'" + jsonstr + "','383','1','0','" + createdate
					+ "','" + modedatacreatetime + "')";

			rs.execute(sql);
		} catch (Exception e) {
			writeLog("insert异常:"+e.getMessage());
		}
		
		
		return jsonstr;
	}
	
	@Override
	public String insertHistoty(String data) {
		JSONArray arrs = JSON.parseArray(data);
		ModeDateService service = new ModeDataServiceImpl();
		for (int i = 0; i < arrs.size(); i++) {
			JSONObject arr = (JSONObject) arrs.get(i);
			String mc = (String) arr.get("mc");
			String dz = (String) arr.get("dz");
			String fjr = (String) arr.get("fjr");
			String sjr = (String) arr.get("sjr");
			String fjrq = (String) arr.get("fjrq");
			String viewtype = (String) arr.get("viewtype");
			String fjrid = getHrmId(fjr);
			String sjrids = getHrmIds(sjr);
			String[] strs = sjrids.split(",");
			for (String sjridss : strs) {
				if(!"".equals(fjrid)) {
					StringBuilder sb = new StringBuilder();
					sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("<ROOT>").append("<header>")
							.append("<userid>" + fjrid + "</userid>")
							.append("<modeid>201</modeid><id></id></header><search><condition /><right>Y</right></search><data id=\"\"><maintable>");
					sb.append("<field>");
					sb.append("<filedname>mc</filedname>");
					sb.append("<fileddbtype>varchar(500)</fileddbtype>");
					sb.append("<filedvalue>" + mc + "</filedvalue>");
					sb.append("</field>");
					sb.append("<field>");
					sb.append("<filedname>dz</filedname>");
					sb.append("<fileddbtype>varchar(200)</fileddbtype>");
					sb.append("<filedvalue>" + dz + "</filedvalue>");
					sb.append("</field>");
					sb.append("<field>");
					sb.append("<filedname>fjr</filedname>");
					sb.append("<fileddbtype>varchar(200)</fileddbtype>");
					sb.append("<filedvalue>" + fjrid + "</filedvalue>");
					sb.append("</field>");
					sb.append("<field>");
					sb.append("<filedname>sjr</filedname>");
					sb.append("<fileddbtype>varchar(200)</fileddbtype>");
					sb.append("<filedvalue>" + sjridss + "</filedvalue>");
					sb.append("</field>");
					sb.append("<field>");
					sb.append("<filedname>fjrq</filedname>");
					sb.append("<fileddbtype>varchar(200)</fileddbtype>");
					sb.append("<filedvalue>" + fjrq + "</filedvalue>");
					sb.append("</field>");
					sb.append("<field>");
					sb.append("<filedname>viewtype</filedname>");
					sb.append("<fileddbtype>varchar(200)</fileddbtype>");
					sb.append("<filedvalue>" + viewtype + "</filedvalue>");
					sb.append("</field>");
					sb.append("</maintable>");
					sb.append("</data>");
					sb.append("</ROOT>");
					new BaseBean().writeLog("传入参数：" + sb.toString());
					String result = service.saveModeData(sb.toString());
					Map<String, String> map = ana(result);
					if (map.isEmpty() && null == map.get("id")) {
						ModeInfo mode = new ModeInfo();
						mode.setMessage(result);
						return JSON.toJSON(mode).toString();
					}
				}
				
			}
		}
		ModeInfo mode = new ModeInfo();
		mode.setMessage("操作成功");
		return JSON.toJSON(mode).toString();
	}

	public String getHrmId(String loginid) {
		RecordSet rs = new RecordSet();
		rs.execute("select id from hrmresource where loginid = '" + loginid + "'");
		rs.next();
		return Util.null2String(rs.getString("id"));
	}

	public String getHrmIds(String loginid) {
		RecordSet rs = new RecordSet();
		String[] strs = loginid.split(",");
		StringBuffer sb = new StringBuffer(",");
		for (String s : strs) {
			sb.append("'" + s + "'");
			sb.append(",");
		}
		String sbs = sb.toString();
		sbs = sbs.substring(1, sbs.length() - 1);
		rs.execute("select id from hrmresource where loginid in (" + sbs + ")");
		StringBuffer ids = new StringBuffer(",");
		while (rs.next()) {
			ids.append(Util.null2String(rs.getString("id")));
			ids.append(",");
		}
		String idss = ids.toString();
		if(",".equals(idss)) {
			return "";
		}
		idss = idss.substring(1, idss.length() - 1);
		return idss;
	}

	/**
	 * 解析返回XML
	 * 
	 * @param xml
	 * @return
	 */
	public Map<String, String> ana(String xml) {
		Map<String, String> map = new HashMap<String, String>();
		// 创建一个新的字符串
		StringReader reader = new StringReader(xml);
		InputSource source = new InputSource(reader);
		SAXBuilder sax = new SAXBuilder();
		try {
			Document doc = sax.build(source);
			Element root = doc.getRootElement();
			List<?> node = root.getChildren();
			Element el = null;
			for (int i = 0; i < node.size(); i++) {
				el = (Element) node.get(i);
				String nodename = el.getName();
				if ("return".equals(nodename)) {
					String value = el.getChild("id").getValue();
					String returnnode = el.getChild("returnnode").getValue();
					String returnmessage = el.getChild("returnmessage").getValue();
					map.put("id", value);
					map.put("returnnode", returnnode);
					map.put("returnmessage", returnmessage);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
