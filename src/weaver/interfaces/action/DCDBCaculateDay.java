package weaver.interfaces.action;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weaver.conn.RecordSet;
import weaver.general.Util;

/**
 * 发起督查督办流程界面根据到期日和收文日期计算办理时限
 * 
 * @author jiangyanlong
 *
 */
public class DCDBCaculateDay extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String result = "";
		String dqr = Util.null2String(req.getParameter("dqr"));
		String swrq = Util.null2String(req.getParameter("swrq"));

		if ("".equals(dqr)) {
			result = "{\"success\":\"1\",\"message\":\"到期日期不能为空\"}";
			resp.setContentType("application/json; charset=utf-8");
			resp.getWriter().print(result.toString());
			return;
		}
		if ("".equals(swrq)) {
			result = "{\"success\":\"1\",\"message\":\"收文日期不能为空\"}";
			resp.setContentType("application/json; charset=utf-8");
			resp.getWriter().print(result.toString());
			return;
		}
		if (getDateWithStr(dqr, "yyyy-MM-dd").before(getDateWithStr(swrq, "yyyy-MM-dd"))) {
			result = "{\"success\":\"1\",\"message\":\"到期日期不能在收文日期之前\"}";
			resp.setContentType("application/json; charset=utf-8");
			resp.getWriter().print(result.toString());
			return;
		}
		int resultCount = 0;
		int dayNum = getDayNum(swrq, dqr);
		boolean isTrue = false;
		RecordSet rs = new RecordSet();
		String sql1 = "select * from HrmPubHoliday where holidaydate = '" + dqr + "'";
		rs.execute(sql1);
		if (rs.next()) {
			isTrue = true;
		}
		String num1 = getWeekOfDateNum(dqr);
		if (Integer.parseInt(num1) == 6 || Integer.parseInt(num1) == 0) {
			isTrue = true;
		}
		int count = 0;
		while (getDateWithStr(swrq, "yyyy-MM-dd").before(getDateWithStr(dqr, "yyyy-MM-dd"))) {
			String sql = "select * from HrmPubHoliday where holidaydate = '" + swrq + "'";
			rs.execute(sql);
			if (rs.next()) {
				count++;
				swrq = getIncomeDate3(getDateWithStr(swrq, "yyyy-MM-dd"), 1);
				continue;
			}
			String num = getWeekOfDateNum(swrq);
			if (Integer.parseInt(num) == 6 || Integer.parseInt(num) == 0) {
				count++;
				swrq = getIncomeDate3(getDateWithStr(swrq, "yyyy-MM-dd"), 1);
				continue;
			}
			swrq = getIncomeDate3(getDateWithStr(swrq, "yyyy-MM-dd"), 1);
		}
		if (isTrue) {
			resultCount = dayNum - count;
		} else {
			resultCount = dayNum - count;
		}
		result = "{\"success\":\"0\",\"message\":\"\",\"count\":\"" + resultCount + "\"}";
		resp.setContentType("application/json; charset=utf-8");
		resp.getWriter().print(result.toString());
	}

	/**
	 * 根据日期获得星期
	 * 
	 * @param date
	 * @return
	 */
	public static String getWeekOfDateNum(String date) {
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		String[] weekDaysCode = {"0", "1", "2", "3", "4", "5", "6" };
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(s.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int intWeek = calendar.get(Calendar.DAY_OF_WEEK);
		return weekDaysCode[intWeek-1];
	}

	public static void main(String[] args) {
		System.out.println(getDayNum("2018-01-16", "2018-01-19"));
	}

	public static int getDayNum(String paramString1, String paramString2) {
		int i = 0;
		if ((paramString1.equals("")) || (paramString1 == null) || (paramString1.length() < 10)
				|| (paramString2.equals("")) || (paramString2 == null) || (paramString2.length() < 10)) {
		}

		String str1 = "";
		String str2 = "";
		int j = 1;

		if (paramString1.compareTo(paramString2) <= 0) {
			str1 = paramString1;
			str2 = paramString2;
		} else {
			str1 = paramString2;
			str2 = paramString1;
			j = -1;
		}

		int k = getIntValue(str1.substring(0, 4));
		int l = getIntValue(str1.substring(5, 7));
		int i1 = getIntValue(str1.substring(8, 10));

		Calendar localCalendar = Calendar.getInstance();

		localCalendar.set(k, l - 1, i1);

		while (str1.compareTo(str2) <= 0) {
			++i;
			localCalendar.add(5, 1);
			str1 = new StringBuilder().append(add0(localCalendar.get(1), 4)).append("-")
					.append(add0(localCalendar.get(2) + 1, 2)).append("-").append(add0(localCalendar.get(5), 2))
					.toString();
		}

		return ((j * i) - 1);
	}

	public static int getIntValue(String paramString) {
		return getIntValue(paramString, -1);
	}

	public static int getIntValue(String paramString, int paramInt) {
		try {
			return Integer.parseInt(paramString);
		} catch (Exception localException) {
		}
		return paramInt;
	}

	public static String add0(int paramInt1, int paramInt2) {
		long l = (long) Math.pow(10.0D, paramInt2);
		return String.valueOf(l + paramInt1).substring(1);
	}

	/**
	 * 把字符串时间转换成日期
	 * 
	 * @param date
	 * @return
	 */
	public Date getDateWithStr(String date, String flag) {
		SimpleDateFormat s = new SimpleDateFormat(flag);
		Date d = null;
		try {
			d = s.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}

	/**
	 * 获取后一天时间
	 * 
	 * @param date
	 * @param flag
	 * @return
	 * @throws NullPointerException
	 */
	public static String getIncomeDate3(Date date, int flag) throws NullPointerException {
		if (null == date) {
			throw new NullPointerException("the date is null or empty!");
		}

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);

		calendar.add(Calendar.DAY_OF_MONTH, +flag);

		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		return s.format(calendar.getTime());
	}
}
