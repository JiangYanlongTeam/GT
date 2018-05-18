package weaver.interfaces.ipad.workflow;

import java.rmi.RemoteException;

import org.tempuri.NetOfficeServiceSoapProxy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import weaver.general.BaseBean;
import weaver.general.Util;

public class WFSubmitInfo extends BaseBean {

	public WFNextStepInfo getNextStepInfo(String iid, String wiid) {
		WFNextStepInfo info = new WFNextStepInfo();
		StringBuffer flows = new StringBuffer();
		StringBuffer userid = new StringBuffer();
		NetOfficeServiceSoapProxy proxy = new NetOfficeServiceSoapProxy();
		try {
			String json = proxy.jsonSubmitUser(iid, wiid);
			JSONObject jsonObject = JSON.parseObject(json);
			writeLog("根据iid:" + iid + ",wiid:" + wiid + " 获取流向信息返回信息:" + jsonObject.toString());
			JSONArray FlowList = jsonObject.getJSONArray("FlowList");
			for (int i = 0; i < FlowList.size(); i++) {
				JSONObject object = (JSONObject) FlowList.get(i);
				String FlowId = Util.null2String(object.get("FlowId"));
				flows.append(FlowId);
				if (i != FlowList.size() - 1) {
					flows.append("|");
				}
				StringBuffer userids = new StringBuffer();
				JSONArray UserList = object.getJSONArray("UserList");
				for (int j = 0; j < UserList.size(); j++) {
					JSONObject user = (JSONObject) UserList.get(j);
					String UserId = Util.null2String(user.get("UserId"));
					userids.append(UserId);
					if (j != UserList.size() - 1) {
						userids.append(";");
					}
				}
				userid.append(userids.toString());
				if (i != FlowList.size() - 1) {
					userid.append("|");
				}
			}
			info.setFlows(flows.toString());
			info.setUSERS(userid.toString());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return info;
	}
	
	public String submit(String iid, String wiid, String users, String flows) {
		NetOfficeServiceSoapProxy proxy = new NetOfficeServiceSoapProxy();
		String xml = "";
		try {
			xml = proxy.sumbit(iid, wiid, flows, users);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return xml;
	}

}
