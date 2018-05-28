package weaver.interfaces.service.sjfw;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;

public class SJFWServiceImpl extends BaseBean implements SJFWService {

	//查询市局发文固定数量数据
	@Override
	public String sjfw(int num, String loginname) {
		String createdate1 = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String createtime = new SimpleDateFormat("HH:mm").format(new Date());
		String modedatacreatetime = new SimpleDateFormat("HH:mm:ss").format(new Date());
		String IP = getPropValue("SYSIP", "SYSIP");
		String message="[{num:"+num+",loginname:"+loginname+"}]";
		RecordSet rs = new RecordSet();
		String sql = "select id from hrmresource where loginid = '" + loginname + "'";
		rs.execute(sql);
		rs.next();
		String id = Util.null2String(rs.getString("id"));
		List<SJFWMode> list = new ArrayList<SJFWMode>();
		if (num < 0) {
			SJFWModes mode1 = new SJFWModes();
			mode1.setSjfwdata(list);
			String jsonstr = JSON.toJSON(mode1).toString();
			writeLog("传入参数num为：" + num + " 应该为正整数");
			try {
				RecordSet rs1=new RecordSet();
				String tableNameSQL = "select w.tablename,w.id from modeinfo m,workflow_bill w where w.id=m.formid and m.id=386";
				rs1.execute(tableNameSQL);
				rs1.next();
				String tableName = Util.null2String(rs1.getString("tablename"));

				String sql1 = "insert into " + tableName + " (createdate,createtime,name,num,receivemessage,returnmessage,formmodeid,"
						+ "modedatacreater,modedatacreatertype,modedatacreatedate,modedatacreatetime) values ('" + createdate1
						+ "','" + createtime + "','" + id + "','" + num + "'"  + ",'"+ message + "'" + ",'" + jsonstr + "','386','1','0','" + createdate1
						+ "','" + modedatacreatetime + "')";

				rs1.execute(sql1);
			} catch (Exception e) {
				writeLog("sjfw异常:"+e.getMessage());
			}
			return jsonstr;
		}
		
		if ("".equals(id)) {
			SJFWModes mode1 = new SJFWModes();
			mode1.setSjfwdata(list);
			String jsonstr = JSON.toJSON(mode1).toString();
			try {
				RecordSet rs1=new RecordSet();
				String tableNameSQL = "select w.tablename,w.id from modeinfo m,workflow_bill w where w.id=m.formid and m.id=386";
				rs1.execute(tableNameSQL);
				rs1.next();
				String tableName = Util.null2String(rs1.getString("tablename"));

				String sql1 = "insert into " + tableName + " (createdate,createtime,name,num,receivemessage,returnmessage,formmodeid,"
						+ "modedatacreater,modedatacreatertype,modedatacreatedate,modedatacreatetime) values ('" + createdate1
						+ "','" + createtime + "','" + id + "','" + num + "'"  + ",'"+ message + "'" + ",'" + jsonstr + "','386','1','0','" + createdate1
						+ "','" + modedatacreatetime + "')";

				rs1.execute(sql1);
			} catch (Exception e) {
				writeLog("sjfw异常:"+e.getMessage());
			}
			return jsonstr;
		}
		SJFWModes mode = new SJFWModes();
		rs.execute(
				"select * from (select b.createdate,(select departmentname from hrmdepartment where id = (select departmentid from hrmresource where id = b.creater)) departmentname,a.requestid,a.bh,"
				+ "(select selectname from WORKFLOW_SELECTITEM where SELECTVALUE = a.bhxlk and FIELDID = '9336' ) bhtext,b.workflowid,(select workflowname from workflow_base where id = b.workflowid) workflowname,b.requestname from formtable_main_68 a, workflow_requestbase b where a.requestid = b.requestid "
				+ "order by b.CREATEDATE desc, CREATETIME desc) where ROWNUM <= "+num+"");
		while (rs.next()) {
			String fwbh = Util.null2String(rs.getString("bh"));
			String bhtext = Util.null2String(rs.getString("bhtext"));
			String requestid = Util.null2String(rs.getString("requestid"));
			String createdate = Util.null2String(rs.getString("createdate"));
			String requestname = Util.null2String(rs.getString("requestname"));
			String workflowname = Util.null2String(rs.getString("workflowname"));
			String departmentname = Util.null2String(rs.getString("departmentname"));
			String date = getDate(createdate);
			SJFWMode mode1 = new SJFWMode();
			mode1.setWname(workflowname);
			mode1.setName(requestname);
			mode1.setIid(requestid);
			mode1.setCaseno(bhtext+fwbh);
			mode1.setAccepted_time(createdate);
			mode1.setTime(date);
			mode1.set发文处室(departmentname);
			mode1.setAddress(""+IP+"/interface/portal/portal.jsp?typeid=flow-" + requestid + ""); //TODO 地址需要换
			String viewType = getCurrentType(requestid, id);
			mode1.setType(viewType);
			list.add(mode1);
		}
		mode.setSjfwdata(list);
		String jsonstr = JSON.toJSON(mode).toString();
		writeLog("传入num：" + num + " 返回数据：" + jsonstr);
		try {
			RecordSet rs1=new RecordSet();
			String tableNameSQL = "select w.tablename,w.id from modeinfo m,workflow_bill w where w.id=m.formid and m.id=386";
			rs1.execute(tableNameSQL);
			rs1.next();
			String tableName = Util.null2String(rs1.getString("tablename"));

			String sql1 = "insert into " + tableName + " (createdate,createtime,name,num,receivemessage,returnmessage,formmodeid,"
					+ "modedatacreater,modedatacreatertype,modedatacreatedate,modedatacreatetime) values ('" + createdate1
					+ "','" + createtime + "','" + id + "','" + num + "'"  + ",'"+ message + "'" + ",'" + jsonstr + "','386','1','0','" + createdate1
					+ "','" + modedatacreatetime + "')";

			rs1.execute(sql1);
		} catch (Exception e) {
			writeLog("sjfw异常:"+e.getMessage());
		}
		return jsonstr;
	}

	@Override
	public String sfjwMore(int page, int limit, String handle, String type, String name, String startDate,
			String endDate, String loginname) {
		String createdate1 = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String createtime = new SimpleDateFormat("HH:mm").format(new Date());
		String modedatacreatetime = new SimpleDateFormat("HH:mm:ss").format(new Date());
		String IP = getPropValue("SYSIP", "SYSIP");
		String message="[{page:"+page+",limit:"+limit+",handle:"+handle+",type:"+type+",name:"+name+",startDate:"+startDate+",endDate:"+endDate+",loginname:"+loginname+"}]";
		SJFWMoreModes modes = new SJFWMoreModes();
		RecordSet rs = new RecordSet();
		String sql = "select id from hrmresource where loginid = '" + loginname + "'";
		rs.execute(sql);
		rs.next();
		String id = Util.null2String(rs.getString("id"));
		
		if(!"get".equals(handle)) {
			modes.setCount("0");
			modes.setMsg("传入参数handle不是get");
			String jsonstr = JSON.toJSON(modes).toString();
			writeLog("条件：[page:" + page + ",limit:" + limit + ",handle:" + handle + ",type:" + type + ",name:" + name
					+ ",startDate:" + startDate + ",endDate:" + endDate + ",loginname:" + loginname + "] 返回结果："
					+ jsonstr);
			try {
				RecordSet rs1=new RecordSet();
				String tableNameSQL = "select w.tablename,w.id from modeinfo m,workflow_bill w where w.id=m.formid and m.id=387";
				rs1.execute(tableNameSQL);
				rs1.next();
				String tableName = Util.null2String(rs1.getString("tablename"));

				String sql1 = "insert into " + tableName + " (createdate,createtime,handle,type,name,receivemessage,formmodeid,"
						+ "modedatacreater,modedatacreatertype,modedatacreatedate,modedatacreatetime) values ('" + createdate1
						+ "','" + createtime + "','" + handle + "'" + ",'" + type + "'" + ",'"  + id + "'" + ",'"+message+"','387','1','0','" + createdate1
						+ "','" + modedatacreatetime + "')";

				rs1.execute(sql1);
			} catch (Exception e) {
				writeLog("sfjwMore异常:"+e.getMessage());
			}
			
			return jsonstr;
		}
		if(!"市局发文".equals(type)) {
			modes.setCount("0");
			modes.setMsg("传入参数type不是市局发文");
			String jsonstr = JSON.toJSON(modes).toString();
			writeLog("条件：[page:" + page + ",limit:" + limit + ",handle:" + handle + ",type:" + type + ",name:" + name
					+ ",startDate:" + startDate + ",endDate:" + endDate + ",loginname:" + loginname + "] 返回结果："
					+ jsonstr);
			try {
				RecordSet rs1=new RecordSet();
				String tableNameSQL = "select w.tablename,w.id from modeinfo m,workflow_bill w where w.id=m.formid and m.id=387";
				rs1.execute(tableNameSQL);
				rs1.next();
				String tableName = Util.null2String(rs1.getString("tablename"));

				String sql1 = "insert into " + tableName + " (createdate,createtime,handle,type,name,receivemessage,formmodeid,"
						+ "modedatacreater,modedatacreatertype,modedatacreatedate,modedatacreatetime) values ('" + createdate1
						+ "','" + createtime + "','" + handle + "'" + ",'" + type + "'" + ",'"  + id + "'" + ",'"+message+"','387','1','0','" + createdate1
						+ "','" + modedatacreatetime + "')";

				rs1.execute(sql1);
			} catch (Exception e) {
				writeLog("sfjwMore异常:"+e.getMessage());
			}
			return jsonstr;
		}
		if ("".equals(Util.null2String(loginname))) {
			modes.setCount("0");
			modes.setMsg("登录账号不能为空");
			String jsonstr = JSON.toJSON(modes).toString();
			writeLog("条件：[page:" + page + ",limit:" + limit + ",handle:" + handle + ",type:" + type + ",name:" + name
					+ ",startDate:" + startDate + ",endDate:" + endDate + ",loginname:" + loginname + "] 返回结果："
					+ jsonstr);
			try {
				RecordSet rs1=new RecordSet();
				String tableNameSQL = "select w.tablename,w.id from modeinfo m,workflow_bill w where w.id=m.formid and m.id=387";
				rs1.execute(tableNameSQL);
				rs1.next();
				String tableName = Util.null2String(rs1.getString("tablename"));

				String sql1 = "insert into " + tableName + " (createdate,createtime,handle,type,name,receivemessage,formmodeid,"
						+ "modedatacreater,modedatacreatertype,modedatacreatedate,modedatacreatetime) values ('" + createdate1
						+ "','" + createtime + "','" + handle + "'" + ",'" + type + "'" + ",'"  + id + "'" + ",'"+message+"','387','1','0','" + createdate1
						+ "','" + modedatacreatetime + "')";

				rs1.execute(sql1);
			} catch (Exception e) {
				writeLog("sfjwMore异常:"+e.getMessage());
			}
			return jsonstr;
		}
		
		if ("".equals(id)) {
			modes.setCount("0");
			modes.setMsg("登录账号在范围OA中不存在");
			String jsonstr = JSON.toJSON(modes).toString();
			writeLog("条件：[page:" + page + ",limit:" + limit + ",handle:" + handle + ",type:" + type + ",name:" + name
					+ ",startDate:" + startDate + ",endDate:" + endDate + ",loginname:" + loginname + "] 返回结果："
					+ jsonstr);
			try {
				RecordSet rs1=new RecordSet();
				String tableNameSQL = "select w.tablename,w.id from modeinfo m,workflow_bill w where w.id=m.formid and m.id=387";
				rs1.execute(tableNameSQL);
				rs1.next();
				String tableName = Util.null2String(rs1.getString("tablename"));

				String sql1 = "insert into " + tableName + " (createdate,createtime,handle,type,name,receivemessage,formmodeid,"
						+ "modedatacreater,modedatacreatertype,modedatacreatedate,modedatacreatetime) values ('" + createdate1
						+ "','" + createtime + "','" + handle + "'" + ",'" + type + "'" + ",'"  + id + "'" + ",'"+message+"','387','1','0','" + createdate1
						+ "','" + modedatacreatetime + "')";

				rs1.execute(sql1);
			} catch (Exception e) {
				writeLog("sfjwMore异常:"+e.getMessage());
			}
			return jsonstr;
		}

		int start = (page - 1) * limit;
		int end = (page * limit) + 1;

		String countSQL = "select count(*) count from formtable_main_68 a, workflow_requestbase b where a.requestid = b.requestid ";
		String searchSQL = "select r.* from (     select my_table.*, rownum as my_rownum from (    select tableA.*,rownum  as oracle_rownum from ( "
				+ " select b.createdate,(select departmentname from hrmdepartment where id = "
				+ " (select departmentid from hrmresource where id = b.creater)) departmentname,a.requestid,a.bh,(select selectname from WORKFLOW_SELECTITEM where SELECTVALUE = a.bhxlk and FIELDID = '9336' ) bhtext,b.workflowid,"
				+ " (select workflowname from workflow_base where id = b.workflowid) workflowname,b.requestname from formtable_main_68 a, "
				+ " workflow_requestbase b where a.requestid = b.requestid ";
		if (!"".equals(Util.null2String(name))) {
			searchSQL += " and b.requestname like '%" + name + "%' ";
			countSQL += " and b.requestname like '%" + name + "%' ";
		}
		if (!"".equals(Util.null2String(startDate))) {
			searchSQL += " and b.createdate >= '" + startDate + "' ";
			countSQL += " and b.createdate >= '" + startDate + "' ";
		}
		if (!"".equals(Util.null2String(endDate))) {
			searchSQL += " and b.createdate <= '" + endDate + "' ";
			countSQL += " and b.createdate <= '" + startDate + "' ";
		}
		searchSQL += "	order by b.CREATEDATE desc, CREATETIME desc " + " ) tableA  ) my_table where oracle_rownum < "
				+ end + " and oracle_rownum>" + start + ") r";
		writeLog("查询总数：" + countSQL);
		rs.execute(countSQL);
		rs.next();
		String count = Util.null2String(rs.getString("count"));
		writeLog("查询分页：" + searchSQL);
		rs.execute(searchSQL);
		modes.setCount(Util.null2String(count));
		List<SJFWMoreMode> list = new ArrayList<SJFWMoreMode>();
		while (rs.next()) {
			String fwbh = Util.null2String(rs.getString("bh"));
			String bhtext = Util.null2String(rs.getString("bhtext"));
			String requestid = Util.null2String(rs.getString("requestid"));
			String createdate = Util.null2String(rs.getString("createdate"));
			String requestname = Util.null2String(rs.getString("requestname"));
			String workflowname = Util.null2String(rs.getString("workflowname"));
			String oracle_rownum = Util.null2String(rs.getString("oracle_rownum"));
			String departmentname = Util.null2String(rs.getString("departmentname"));
			String date = getDate(createdate);
			SJFWMoreMode mode1 = new SJFWMoreMode();
			mode1.setWname(workflowname);
			mode1.setName(requestname);
			mode1.setIid(requestid);
			mode1.setCaseno(bhtext+fwbh);
			mode1.setAccepted_time(createdate);
			mode1.setTime(date);
			mode1.set发文处室(departmentname);
			mode1.setAddress(""+IP+"/interface/portal/portal.jsp?typeid=flow-" + requestid + ""); //TODO 地址需要换
			mode1.setRn(oracle_rownum);
			list.add(mode1);
		}
		modes.setData(list);
		modes.setMsg("");
		String jsonstr = JSON.toJSON(modes).toString();
		writeLog("条件：[page:" + page + ",limit:" + limit + ",handle:" + handle + ",type:" + type + ",name:" + name
				+ ",startDate:" + startDate + ",endDate:" + endDate + ",loginname:" + loginname + "] 返回结果：" + jsonstr);
		try {
			RecordSet rs1=new RecordSet();
			String tableNameSQL = "select w.tablename,w.id from modeinfo m,workflow_bill w where w.id=m.formid and m.id=387";
			rs1.execute(tableNameSQL);
			rs1.next();
			String tableName = Util.null2String(rs1.getString("tablename"));

			String sql1 = "insert into " + tableName + " (createdate,createtime,handle,type,name,count,receivemessage,formmodeid,"
					+ "modedatacreater,modedatacreatertype,modedatacreatedate,modedatacreatetime) values ('" + createdate1
					+ "','" + createtime + "','" + handle + "'" + ",'" + type + "'" + ",'"  + id + "'" + ",'"+ count + "'" + ",'"+message+"','387','1','0','" + createdate1
					+ "','" + modedatacreatetime + "')";

			rs1.execute(sql1);
		} catch (Exception e) {
			writeLog("sfjwMore异常:"+e.getMessage());
		}
		return jsonstr;
	}

	public String getDate(String date) {
		if (null == date || "".equals(date)) {
			return "";
		}
		String[] strs = date.split("-");
		return strs[1] + "-" + strs[2];
	}

	public static void main(String[] args) {
		SJFWModes mode1 = new SJFWModes();
		List<SJFWMode> list = new ArrayList<SJFWMode>();
		SJFWMode mode = new SJFWMode();
		mode.setAccepted_time("1");
		mode.setCaseno("1");
		mode.set发文处室("1");
		mode.setIid("1");
		mode.setName("1");
		mode.setTime("1");
		mode.setWname("1");

		SJFWMode mode11 = new SJFWMode();
		mode11.setAccepted_time("1");
		mode11.setCaseno("1");
		mode11.set发文处室("1");
		mode11.setIid("1");
		mode11.setName("1");
		mode11.setTime("1");
		mode11.setWname("1");

		list.add(mode11);
		list.add(mode);
		mode1.setSjfwdata(list);
		String jsonstr = JSON.toJSON(mode1).toString();
		System.out.println("发送明源JSON：" + jsonstr);
	}
	

	public String getCurrentType(String requestid, String userid) {
		RecordSet rs = new RecordSet();
		String sql = "select viewtype from WORKFLOW_CURRENTOPERATOR where REQUESTID = " + requestid + " and  userid = '"
				+ userid + "' order by OPERATEDATE desc,OPERATEDATE desc, RECEIVEDATE desc, RECEIVETIME desc";
		rs.execute(sql);
		rs.next();
		String viewtype = Util.null2String(rs.getString("viewtype"));
		if ("0".equals(viewtype)) {
			return "0";
		} else {
			return "1";
		}
	}
}
