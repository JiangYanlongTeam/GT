package weaver.interfaces.pdf;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.Property;
import weaver.soa.workflow.request.RequestInfo;

public class CheckCEBAction extends BaseBean implements Action {

	@Override
	public String execute(RequestInfo request) {

		String cebvalue = "";
		String cebcolumn = "xgfj";

		Property[] properties = request.getMainTableInfo().getProperty();// 获取表单主字段信息
		for (int i = 0; i < properties.length; i++) {
			String name = properties[i].getName();// 主字段名称
			String value = Util.null2String(properties[i].getValue());// 主字段对应的值
			if (name.equals(cebcolumn)) {
				cebvalue = value;
			}
		}
		RecordSet rs = new RecordSet();
		List<String> listname = new ArrayList<String>();
		if (!"".equals(cebvalue)) {
			String sql = "select a.docid,b.imagefilename,b.filerealpath,b.filesize,b.aescode from DocImageFile a,imagefile b where a.imagefileid = b.imagefileid and a.docid in ("
					+ cebvalue + ")";
			rs.execute(sql);
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
			while (rs.next()) {
				String docid = Util.null2String(rs.getString("docid"));
				String filerealpath = Util.null2String(rs.getString("filerealpath"));
				String imagefilename = Util.null2String(rs.getString("imagefilename"));
				String[] strs = imagefilename.split("\\.");
				String type = strs[strs.length - 1];
				if (imagefilename.contains(".") && type.equals("ceb")) {
					map.put(imagefilename, filerealpath);
					listname.add(docid);
				}
			}
		}
		if(!listname.isEmpty()) {
			request.getRequestManager().setMessageid("Failed");
			request.getRequestManager().setMessagecontent("不能上传ceb文件");
		}
		return SUCCESS;
	}

	/**
	 * 获取文件后缀名 如：.zip返回zip，如果没有后缀名，则返回文件全名
	 * 
	 * @param file
	 * @return
	 */
	public String getPrefix(String file) {
		String prefix = file.substring(file.lastIndexOf(".") + 1);
		return prefix;
	}

	/**
	 * 获取文件名字，不要后缀
	 * 
	 * @param s
	 * @return
	 */
	public String getFileNameWithoutPrefix(String s) {
		return s.substring(0, s.lastIndexOf("."));
	}

	/**
	 * 获取文件后缀名 如：.zip返回zip，如果没有后缀名，则返回文件全名
	 * 
	 * @param file
	 * @return
	 */
	public String getPrefix(File file) {
		String fileName = file.getName();
		String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
		return prefix;
	}

	public static void main(String[] args) {
		// org.apache.commons.logging.LogFactory
		DefaultHttpClient httpClient = new DefaultHttpClient();
		// httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
		// 20000);
		// httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
		String urls = "http://192.168.1.3:8090/convert?filename=1bc9156934fe4c19bb5b9f7989081b78.ceb%5C5e8659864c03492d944e8d2cc6176495.ceb%5C981e3a82a0ef4009924a5d77c43e4ea9.ceb%5C08534486084b44f89972991fed23c2d8.ceb%5Cff68f5a72b544e339f1b3bbb16e851ab.ceb%5Cc0900769f5cd4013a4d8462556773b12.ceb%5C9859baf1827d440eac2dcad038ee08b2.ceb%5Ce0e4b4e9f4934fdeb5e3a2a0786ee220.ceb%5Ce83fabcd681e43708ec15bdd229bb683.ceb%5C1f701c5470404440a4bdc29b41a0914b.ceb%5C7f74626582204b4886125144effa64ae.ceb%5C6095dfe7d6e94950a42f1ca066844dc6.ceb%5Cca1aa17bb8a84f6391622cbb2514d374.ceb%5Cc15c9ec2fdf84a668583962b17617ef3.ceb&operation=convert&requestid=1379&pdfdocname=1bc9156934fe4c19bb5b9f7989081b78%5C5e8659864c03492d944e8d2cc6176495%5C981e3a82a0ef4009924a5d77c43e4ea9%5C08534486084b44f89972991fed23c2d8%5Cff68f5a72b544e339f1b3bbb16e851ab%5Cc0900769f5cd4013a4d8462556773b12%5C9859baf1827d440eac2dcad038ee08b2%5Ce0e4b4e9f4934fdeb5e3a2a0786ee220%5Ce83fabcd681e43708ec15bdd229bb683%5C1f701c5470404440a4bdc29b41a0914b%5C7f74626582204b4886125144effa64ae%5C6095dfe7d6e94950a42f1ca066844dc6%5Cca1aa17bb8a84f6391622cbb2514d374%5Cc15c9ec2fdf84a668583962b17617ef3";
		URL url;
		try {
			url = new URL(urls);
			URI uri = new URI(url.getProtocol(), null, url.getHost(), url.getPort(), url.getPath(), url.getQuery(),
					null);
//			System.out.println(uri.toString());
			HttpGet get = new HttpGet(uri);
			HttpResponse response;
			try {
				response = httpClient.execute(get);
				int statusCode = response.getStatusLine().getStatusCode();
				System.out.println(statusCode);
				if (statusCode != HttpStatus.SC_OK) {
					get.abort();
					System.out.println("1");
				}
				HttpEntity ent = response.getEntity();
				// System.out.println(response.getEntity().getContentLength());
				 System.out.println(EntityUtils.toString(ent));

				StringBuffer returnMessage = new StringBuffer();
				System.out.println(response.getStatusLine());
				if (ent != null) {
					System.out.println("Response content length: " + ent.getContentLength());
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(ent.getContent()));
					String lineMessage;
					while ((lineMessage = bufferedReader.readLine()) != null) {
						returnMessage.append(lineMessage);
					}
					bufferedReader.close();
					System.out.println(returnMessage.toString());
				}

				// InputStream in = response.getEntity().getContent();
				// System.out.println(response.getEntity().getContentLength());
				// String jsonResult = getResponseString(in);
				// JSONObject j = JSON.parseObject(result);
				// String errrocode = j.getString("status");
				// System.out.println(errrocode);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 作用：将输入流转成字节数组
	 * 
	 * * @return byte[]
	 */
	public static byte[] inputStreamToByte(InputStream is) {
		ByteArrayOutputStream baos = null;
		byte[] buffer = new byte[8 * 1024];
		int c = 0;
		try {
			while ((c = is.read(buffer)) != -1) {
				baos.write(buffer, 0, c);
				baos.flush();
			}
			return baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public void writeFileID(String docid, String requestid) {
		RecordSet rs = new RecordSet();
		rs.execute(
				"select formid from workflow_base where id = (select workflowid from workflow_requestbase where REQUESTID = '"
						+ requestid + "')");
		rs.next();
		String formid = Util.null2String(rs.getString("formid"));
		String tableName = "formtable_main_" + Math.abs(Integer.parseInt(formid));
		String sql = "update " + tableName + " set pdf = '" + docid + "' where requestid = '" + requestid + "'";
		writeLog("更新表字段pdf SQL: " + sql);
		rs.execute(sql);
	}
	
	public String getTableName(String requestid) {
		RecordSet rs = new RecordSet();
		rs.execute(
				"select formid from workflow_base where id = (select workflowid from workflow_requestbase where REQUESTID = '"
						+ requestid + "')");
		rs.next();
		String formid = Util.null2String(rs.getString("formid"));
		String tableName = "formtable_main_" + Math.abs(Integer.parseInt(formid));
		return tableName;
	}

	public void writeFileIDOTHER(String docid, String requestid) {
		RecordSet rs = new RecordSet();
		rs.execute(
				"select formid from workflow_base where id = (select workflowid from workflow_requestbase where REQUESTID = '"
						+ requestid + "')");
		rs.next();
		String formid = Util.null2String(rs.getString("formid"));
		String tableName = "formtable_main_" + Math.abs(Integer.parseInt(formid));
		String sql = "update " + tableName + " set xgfj = '" + docid + "' where requestid = '" + requestid + "'";
		writeLog("更新表字段xgfj SQL: " + sql);
		rs.execute(sql);
	}
}
