package weaver.interfaces.service.doc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import weaver.conn.RecordSet;
import weaver.general.Util;

public class DocDServiceImpl implements DocDService {

	@Override
	public String searchIDBySubject(String json) {
		// {"subject":"aa","typeid":"111"}
		JSONObject jsonstr = JSON.parseObject(json);
		String subject = (String) jsonstr.get("subject");
		String typeid = (String) jsonstr.get("typeid");
		RecordSet rs = new RecordSet();
		// 一周工作安排 182
		// 值班表 181
		if ("gzap".equals(typeid)) {
			String sql = "select id from docdetail where seccategory = '182' and DOCSUBJECT = '" + subject + "'";
			rs.execute(sql);
			rs.next();
			String id = Util.null2String(rs.getString("id"));
			return "{\"docid\":\"" + id + "\"}";
		}
		if ("zbb".equals(typeid)) {
			String sql = "select id from docdetail where seccategory = '181' and DOCSUBJECT = '" + subject + "'";
			rs.execute(sql);
			rs.next();
			String id = Util.null2String(rs.getString("id"));
			return "{\"docid\":\"" + id + "\"}";
		}
		return "{\"docid\":\"\"}";
	}
}
