package weaver.interfaces.service.workflow;

import com.alibaba.fastjson.JSON;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TestLay extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = Util.null2String(req.getParameter("page"));
        String number = Util.null2String(req.getParameter("limit"));
        String result = lay(Integer.parseInt(page),Integer.parseInt(number));
        resp.setHeader("Content-type", "text/html;charset=UTF-8");
        //这句话的意思，是告诉servlet用UTF-8转码，而不是用默认的ISO8859
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();
        writer.print(result);
     }

    public String lay(int page, int limit) {

        if (page < 1)
            page = 1;
        int i = 0;
        int j = 0;
        i = page * limit + 1;
        j = (page - 1) * limit;
        RecordSet recordSet = new RecordSet();
        String sql = "select a.lastname,b.departmentname,a.id hrmid,b.id depid,a.dsporder from hrmresource a, hrmdepartment b where a.departmentid = b.id order by departmentid asc";
        recordSet.execute(sql);
        int count = recordSet.getCounts();
        String str = "";
        if(recordSet.getDBType().equals("oracle")) {
            str = new StringBuilder().append(
                    " select * from ( select my_table.*,rownum as my_rownum from ( select tableA.*,rownum as r from ( ")
                    .append(sql).append(" ) tableA  ) my_table where rownum < ").append(i).append(" ) where my_rownum > ")
                    .append(j).toString();
        } else {
            str = new StringBuilder().append("select * from (select row_number()over(order by b.id)rownumber,a.lastname,a.id hrmid," +
                    "b.id,b.departmentname from hrmresource a,hrmdepartment b " +
                    "where a.departmentid = b.id ) a1 where rownumber between "+j+" and "+i+"").toString();
        }
        new BaseBean().writeLog("str-sql:"+str);
        recordSet.execute(str);
        LayInfo info = new LayInfo();
        info.setCode("");
        info.setMessage("");
        info.setCount(count);
        List<LayDetail> list = new ArrayList<LayDetail>();
        while (recordSet.next()) {
            String lastname = Util.null2String(recordSet.getString("lastname"));
            String departmentname = Util.null2String(recordSet.getString("departmentname"));
            String hrmid = Util.null2String(recordSet.getString("hrmid"));
            String depid = Util.null2String(recordSet.getString("depid"));
            LayDetail detail = new LayDetail();
            detail.setDepartmentname(departmentname);
            detail.setDepid(depid);
            detail.setHrmid(hrmid);
            detail.setLastname(lastname);
            list.add(detail);
        }
        info.setData(list);
        String jsonstr = JSON.toJSON(info).toString();
        return jsonstr;
    }
}
