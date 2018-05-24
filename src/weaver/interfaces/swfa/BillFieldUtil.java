package weaver.interfaces.swfa;

import java.util.HashMap;
import java.util.Map;

import weaver.conn.RecordSet;
import weaver.general.Util;

/**
 * 字段名称工具类
 * 
 * @ClassName: BillFieldUtil
 * @Description: TODO
 * @author ocean
 * @date 2013-6-20 下午6:40:46
 * 
 */
public class BillFieldUtil {

	/**
	 * 
	 * 方法描述 : 获取字段的ID 创建者：ocean 项目名称： ecologyTest 类名： CwUtil.java 版本： v1.0 创建时间：
	 * 2013-9-14 下午3:05:53
	 * 
	 * @param workFlowId
	 *            流程id
	 * @param num
	 *            明细表
	 * @return Map
	 */
	public static Map getFieldId(int formid, String num) {

		formid = Math.abs(formid);
		String sql = "";
		if ("0".equals(num)) {
			sql = "select b.id,fieldname,detailtable from workflow_billfield b ,workflow_base a where b.billid=-"
					+ formid + " and a.formid=b.billid and (detailtable='' or detailtable is null)";
		} else {
			sql = "select b.id,fieldname,detailtable from workflow_billfield b ,workflow_base a where b.billid=-"
					+ formid
					+ " and a.formid=b.billid and detailtable='formtable_main_"
					+ formid + "_dt" + num + "'";
		}

		RecordSet rs = new RecordSet();
		rs.execute(sql);
		Map array = new HashMap();
		while (rs.next()) {
			array.put(
					Util.null2String(rs.getString("fieldname")).toLowerCase(),
					Util.null2String(rs.getString("id")));
		}
		return array;
	}

	/**
	 * 
	 * @Title: getlabelId
	 * @Description: TODO
	 * @param @param name 数据库字段名称 小写
	 * @param @param formid 表单ID
	 * @param @param ismain 是否主表 0:主表 1：明细表
	 * @param @param num 明细表序号12
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getlabelId(String name, int formid, String ismain,
			String num) {
		name = name.toLowerCase();
		String id = "";
		String sql = "";
		formid = formid * -1;

		if ("0".equals(ismain)) {
			sql = "select id,fieldname,detailtable from workflow_billfield where billid=-"
					+ formid
					+ " and (detailtable='' or detailtable is null) and lower(fieldname)='"
					+ name + "'";
		} else {
			sql = "select id,fieldname,detailtable from workflow_billfield where billid=-"
					+ formid
					+ " and detailtable='formtable_main_"
					+ formid
					+ "_dt" + num + "' and lower(fieldname)='" + name + "'";
		}
		// System.out.println(sql);
		RecordSet rs = new RecordSet();
		rs.execute(sql);
		if(rs.next()){
			id = Util.null2String(rs.getString("id"));
		}
		return id;

	}

	/**
	 * 选择框 显示名
	 * 
	 * @param val
	 * @param id
	 * @return
	 */
	public static String getselectName(String val, String id) {
		String name = "";

		if (val.equals("")) {

			return "";
		}
		RecordSet recordSet = new RecordSet();

		recordSet
				.executeSql("select selectname,fieldid from workflow_selectItem where fieldid ="
						+ Util.getIntValue(id, 0)
						+ "  and selectvalue = "
						+ Util.getIntValue(val, 0));
		if (recordSet.next()) {
			name = recordSet.getString("selectname");
		}

		return name;

	}

	/**
	 * 选择框 options
	 * 
	 * @param val
	 * @param id
	 * @return
	 */
	public static String getselectOptionStr(String val, String id) {

		String str = "<option value=''></option>";

		RecordSet recordSet = new RecordSet();
		recordSet
				.executeSql("select selectname,fieldid,selectvalue from workflow_selectItem  where fieldid = "
						+ Util.getIntValue(id, 0) + " order by selectvalue");
		while (recordSet.next()) {
			String name = Util.null2String(recordSet.getString("selectname"));
			String value = Util.null2String(recordSet.getString("selectvalue"));

			if (value.equals(val)) {
				str += "<option value='" + value + "' selected>" + name
						+ "</option>";
			} else {
				str += "<option value='" + value + "'>" + name + "</option>";
			}
		}

		return str;
	}

	/**
	 * 判断节点是否为创建节点
	 * 
	 * @param nodeid
	 * @return
	 */
	public static boolean judgeNodeStart(String nodeid) {

		boolean falg = false;
		String isstart = "0";

		RecordSet rs = new RecordSet();
		rs.executeSql("select * from workflow_nodebase where id="
				+ Util.getIntValue(nodeid, 0));
		if (rs.next()) {
			isstart = rs.getString("isstart");
		}

		if ("1".equals(isstart)) {
			falg = true;
		}

		return falg;
	}
	
	/**
	 * 获取节点名称
	 * @param nodeid
	 * @return
	 */
	public String getNodename(String nodeid){
		
		String nodename = "";
		
		RecordSet rs = new RecordSet();
		rs.executeSql("select id,nodename from workflow_nodebase where id="
				+ Util.getIntValue(nodeid, 0));
		if (rs.next()) {
			nodename = rs.getString("nodename");
		}
		
		return nodename;
		
	}

}
