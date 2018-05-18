package weaver.interfaces.action;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.hrm.resource.ResourceComInfo;

public class PaginationReport extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		BaseBean bean = new BaseBean();
		RecordSet rs = new RecordSet();
		String xm = Util.null2String(req.getParameter("xm"));
		String lclx = Util.null2String(req.getParameter("lclx"));
		String enddate = Util.null2String(req.getParameter("enddate"));
		String pageSize = Util.null2String(req.getParameter("pageSize"));
		String begindate = Util.null2String(req.getParameter("begindate"));
		String pageNumber = Util.null2String(req.getParameter("pageNumber"));
		int limit = Integer.parseInt(pageNumber);
		int page = Integer.parseInt(pageSize);

		if (page < 1)
			page = 1;
		int i = 0;
		int j = 0;
		i = page * limit + 1;
		j = (page - 1) * limit;

		String _sql = "select * from ( select my_table.*,rownum as my_rownum from ( select tableA.*,rownum as r from ( ";
		String __sql = " ) tableA  ) my_table where rownum <" + i + " ) where my_rownum >" + j + " ";

		String sql_ = " select requestid,ccr,zwwb,ccrq,ccjsrq,ccdd,workflowname,xj from ( ";
		String sql__ = " ) order by REQUESTID, workflowname ";

		String sql1 = "select a.requestid,to_number(b.ccr) ccr,b.zwwb,a.ccrq,a.ccjsrq,a.ccdd,c.workflowname,a.xj from formtable_main_51 a, formtable_main_51_dt1 b, workflow_base c, workflow_requestbase e "
				+ "where a.id = b.mainid and e.requestid = a.requestid and e.workflowid = c.id ";
		String sql2 = "select a.requestid,to_number(b.ccr) ccr,b.zwwb,a.ccrq,a.ccjsrq,a.ccdd,c.workflowname,a.xj from formtable_main_53 a, formtable_main_53_dt1 b, workflow_base c, workflow_requestbase e "
				+ "where a.id = b.mainid and e.requestid = a.requestid and e.workflowid = c.id ";
		String sql3 = "select a.requestid,to_number(a.qjr) ccr,a.zwwb,a.ccrq,a.ccjsrq,a.qjdd ccdd,c.workflowname,a.xj from formtable_main_55 a, workflow_base c, workflow_requestbase e "
				+ "where e.requestid = a.requestid and e.workflowid = c.id ";

		String sql = "";
		String count_sql = "";

		if (!"".equals(lclx)) {
			if ("0".equals(lclx)) { // 0 formtable_main_51 公务出差审批流程
				sql = new StringBuilder().append(_sql).append(sql_).append(sql1).append(sql__).append(__sql).toString();
				count_sql = new StringBuilder().append(sql_).append(sql1).append(")").toString();
			}
			if ("1".equals(lclx)) { // 1 formtable_main_53 因公离岗外出流程
				sql = new StringBuilder().append(_sql).append(sql_).append(sql2).append(sql__).append(__sql).toString();
				count_sql = new StringBuilder().append(sql_).append(sql2).append(")").toString();
			}
			if ("3".equals(lclx)) { // 2 formtable_main_55 因私请假流程
				sql = new StringBuilder().append(_sql).append(sql_).append(sql3).append(sql__).append(__sql).toString();
				count_sql = new StringBuilder().append(sql_).append(sql3).append(")").toString();
			}
		} else {
			sql = new StringBuilder().append(_sql).append(sql_).append(sql1).append(" UNION ALL ").append(sql2)
					.append(" UNION ALL ").append(sql3).append(sql__).append(__sql).toString();
			count_sql = new StringBuilder().append(sql_).append(sql1).append(" UNION ALL ").append(sql2)
					.append(" UNION ALL ").append(sql3).append(")").toString();
		}
		if (!"".equals(xm)) {
			sql += new StringBuilder(" ccr = '" + xm + "' ");
			count_sql += new StringBuilder(" ccr = '" + xm + "' ");
		}
		if (!"".equals(begindate)) {
			sql += new StringBuilder(" ccrq >= '" + begindate + "' ");
			count_sql += new StringBuilder(" ccrq >= '" + begindate + "' ");
		}
		if (!"".equals(enddate)) {
			sql += new StringBuilder(" ccjsrq <= '" + enddate + "' ");
			count_sql += new StringBuilder(" ccjsrq <= '" + enddate + "' ");
		}

		rs.execute(count_sql);
		String count = Util.null2String(rs.getCounts());
		rs.execute(sql);
		bean.writeLog("查询SQL：" + sql);
		ResourceComInfo r = null;
		try {
			r = new ResourceComInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}

		PaginationModes mode = new PaginationModes();
		mode.setTotal(count);
		PaginationMode_Item temp = null;
		List<PaginationMode_Item> rows = new ArrayList<PaginationMode_Item>();
		while (rs.next()) {
			String xj = Util.null2String(rs.getString("xj"));
			String requestid = Util.null2String(rs.getString("requestid"));
			String ccr = Util.null2String(rs.getString("ccr"));
			if (!"".equals(ccr)) {
				ccr = r.getLastnames(ccr);
			}
			String zwwb = Util.null2String(rs.getString("zwwb"));
			String ccrq = Util.null2String(rs.getString("ccrq"));
			String ccjsrq = Util.null2String(rs.getString("ccjsrq"));
			String ccdd = Util.null2String(rs.getString("ccdd"));
			String workflowname = Util.null2String(rs.getString("workflowname"));

			if (temp != null) {

			}

			PaginationMode_Item item = new PaginationMode_Item();
			if (!"".equals(xj)) {
				item.setDqzt("回归已销假");
			} else {
				try {
					if (new Date().before(new SimpleDateFormat("yyyy-MM-dd").parse(ccrq))) {
						item.setDqzt("外出前待审批");
					} else if (new Date().after(new SimpleDateFormat("yyyy-MM-dd").parse(ccjsrq))) {
						item.setDqzt("回归未销假");
					} else {
						item.setDqzt("外出中");
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}

			item.setJsrq(ccjsrq);
			item.setKsrq(ccrq);
			item.setLx(workflowname);
			item.setName(ccrq);
			item.setRequestid(requestid);
			item.setWcdd(ccdd);
			item.setZw(zwwb);

			temp = item;

			rows.add(item);
		}
		mode.setRows(rows);
		String jsonstr = JSON.toJSON(mode).toString();
		bean.writeLog("返回结果：" + jsonstr);
		resp.setContentType("application/json; charset=utf-8");
		resp.getWriter().print(jsonstr);
	}
}
