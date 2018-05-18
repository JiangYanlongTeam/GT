package weaver.interfaces.service.weixin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

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
		JSONObject jsonObject = JSON.parseObject(jsondata);
		String content = Util.null2String(jsonObject.get("content"));
		String title = Util.null2String(jsonObject.get("title"));
		String userids = Util.null2String(jsonObject.get("userids"));
		String wxid = Util.null2String(jsonObject.get("wxid"));
		if ("".equals(content)) {
			WXRemindMode mode = new WXRemindMode();
			mode.setCode("-1");
			mode.setMessage("content不能为空");
			return JSON.toJSON(mode).toString();
		}
		if ("".equals(title)) {
			WXRemindMode mode = new WXRemindMode();
			mode.setCode("-1");
			mode.setMessage("title不能为空");
			return JSON.toJSON(mode).toString();
		}
		if ("".equals(userids)) {
			WXRemindMode mode = new WXRemindMode();
			mode.setCode("-1");
			mode.setMessage("userids不能为空");
			return JSON.toJSON(mode).toString();
		}
		if ("".equals(wxid)) {
			WXRemindMode mode = new WXRemindMode();
			mode.setCode("-1");
			mode.setMessage("wxid不能为空");
			return JSON.toJSON(mode).toString();
		}

		SendWxRemind send = new SendWxRemind();
		WXRemindMode mode = send.sendWxMsg(title, content, userids, wxid);
		return JSON.toJSON(mode).toString();
	}
}
