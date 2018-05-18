package weaver.interfaces.action;

import java.rmi.RemoteException;

import org.json.JSONException;
import org.tempuri.UserServiceSoapProxy;

import com.alibaba.fastjson.JSON;

import weaver.general.BaseBean;
import weaver.general.Util;

/**
 * 与安图系统单点登录对接认证
 * 
 * @author jiangyanlong
 *
 */
public class AT_SSO extends BaseBean {

	public UserInfo sso(String token, String key) {
		UserServiceSoapProxy proxy = new UserServiceSoapProxy();
		String json = "";
		UserInfo user = null;
		try {
			writeLog("传入TOKEN："+token);
			writeLog("传入KEY："+key);
			json = Util.null2String(proxy.userInfo(token, key));
			writeLog("返回结果："+json);
			if ("".equals(json)) {
				return null;
			}
			user = getUserByJSON(json);
		} catch (RemoteException e) {
			e.printStackTrace();
			writeLog("调用接口异常："+e.getMessage());
			return null;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return user;
	}

	public UserInfo getUserByJSON(String json) throws JSONException {
		UserInfo user = new UserInfo();
		com.alibaba.fastjson.JSONObject ob = JSON.parseObject(json);
		String loginid = Util.null2String(ob.get("login_name"));
		String password = Util.null2String(ob.get("signaturepassword"));
		user.setUsername(loginid);
		user.setPassword(password);
		return user;
	}
	
	public String getToken(String loginname) {
		String token = "";
		UserServiceSoapProxy proxy = new UserServiceSoapProxy();
		writeLog("传入loginname："+loginname);
		writeLog("传入key：A0C5AD7EF8DF41EF83045CEF8C051215");
		try {
			token = proxy.loginOnTokenForFanWei(loginname, "A0C5AD7EF8DF41EF83045CEF8C051215");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return token;
	}
}
