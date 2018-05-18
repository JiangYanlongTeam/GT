package weaver.interfaces.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.hrm.HrmUserVarify;
import weaver.hrm.User;
import weaver.hrm.company.DepartmentComInfo;

public class HtmlToDoc extends BaseBean {

	public static void main(String[] args) throws Exception {
		htmlToWord2();
	}

	public void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		RecordSet rs = new RecordSet();
		String bm = Util.null2String(req.getParameter("bm"));
		String sfcq = Util.null2String(req.getParameter("sfcq"));
		String userid = Util.null2String(req.getParameter("userid"));
		String shijian = Util.null2String(req.getParameter("shijian"));
		String shijianz = Util.null2String(req.getParameter("shijianz"));
		String mainrequestid = Util.null2String(req.getParameter("mainrequestid"));

		boolean isadmin = true;
		int usid = Integer.parseInt(userid);
		User user = new User(usid);
		if (!HrmUserVarify.checkUserRight("DCDBY:View", user)) {
			isadmin = false;
		}
		String backfields = "";
		if (isadmin) {
			backfields = " a.field3 wh,a.xbbm,a.nbyj,a.blqk,a.gwnbhf,a.field10 hffs,(select departmentid from hrmresource where id = b.creater) createdepid,a.cyld,a.ysld,a.requestid, b.requestname, a.field1 swrq,a.zbbm,a.cbr,a.field7 dqr,a.sfcq,b.creater,a.field2 lwdw,(select requestname from workflow_requestbase where requestid = b.mainrequestid) gwswrequestname ";
		} else {
			backfields = " a.field3 wh,a.xbbm,a.nbyj,a.blqk,a.gwnbhf,a.field10 hffs,(select departmentid from hrmresource where id = b.creater) createdepid,a.cyld,a.ysld,a.requestid, b.requestname, a.field1 swrq,a.zbbm,a.cbr,a.field7 dqr,a.sfcq,b.creater,a.field2 lwdw,(select requestname from workflow_requestbase where requestid = b.mainrequestid) gwswrequestname ";
		}

		String sqlform = " formtable_main_39 a, workflow_requestbase b ";
		String sqlwhere = " a.requestid = b.requestid ";
		if (!isadmin) {
			String userDepartid = "";
			try {
				userDepartid = new weaver.hrm.resource.ResourceComInfo().getDepartmentID(Util.null2String(usid));
			} catch (Exception e) {
				e.printStackTrace();
			}
			String _sql = "select id from hrmresource where departmentid = '" + userDepartid + "'";
			rs.execute(_sql);
			StringBuffer _sb = new StringBuffer(",");
			while (rs.next()) {
				String _id = Util.null2String(rs.getString("id"));
				_sb.append(_id + ",");
			}
			String _SS = _sb.toString();
			_SS = _SS.substring(1, _SS.length() - 1);
			sqlwhere += " and b.creater in (" + _SS + ") ";
		}

		if (!mainrequestid.equals("")) {
			sqlwhere += " and b.mainrequestid = '" + mainrequestid + "'";
		}
		if (!"".equals(bm)) {
			sqlwhere += " and a.zbbm like '%" + bm + "%' ";
		}
		if (!"".equals(shijian) && null != shijian) {
			sqlwhere += " and a.field7 >= '" + shijian + "'";
		}
		if (!"".equals(shijianz) && null != shijianz) {
			sqlwhere += " and a.field7 <= '" + shijianz + "'";
		}

		if (!"".equals(sfcq) && null != sfcq) {
			if (!"2".equals(sfcq)) {
				sqlwhere += " and a.sfcq = '" + sfcq + "'";
			}
		}

		String searchSQL = " select " + backfields + " from " + sqlform + " where " + sqlwhere
				+ " order by field7 asc ";
		writeLog("督查督办导出word SQL：" + searchSQL);
		rs.execute(searchSQL);
		DepartmentComInfo d = null;
		try {
			d = new DepartmentComInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		StringBuffer sb = new StringBuffer();
		int count = rs.getCounts();
		int c = 0;
		while (rs.next()) {
			String dqr = Util.null2String(rs.getString("dqr"));
			String wh = Util.null2String(rs.getString("wh"));
			String cyld = Util.null2String(rs.getString("cyld"));
			String ysld = Util.null2String(rs.getString("ysld"));
			String swrq = Util.null2String(rs.getString("swrq"));
			String zbbm = Util.null2String(rs.getString("zbbm"));
			String xbbm = Util.null2String(rs.getString("xbbm"));
			String lwdw = Util.null2String(rs.getString("lwdw"));

			String hffs = Util.null2String(rs.getString("hffs"));
			String nbyj = Util.null2String(rs.getString("nbyj"));
			String blqk = Util.null2String(rs.getString("blqk"));
			String gwnbhf = Util.null2String(rs.getString("gwnbhf"));
			String requestid = Util.null2String(rs.getString("requestid"));

			String createdepid = Util.null2String(rs.getString("createdepid"));
			String dqrChinese = getChineseDate(dqr);
			String yldChinese = getCW(cyld);
			String ysldChinese = getCW(ysld);
			String swrqChinese = getChineseDate(swrq);
			String zbbmChinese = d.getDepartmentNames(zbbm);
			String xbbmChinese = getXBBM(xbbm);
			String gwswrequestname = Util.null2String(rs.getString("gwswrequestname"));
			String createdeparmentname = d.getDepartmentname(createdepid);

			String selectname = getLabelName("8282", hffs);
			String requname = "";
			String reqId = "";
			if ("0".equals(hffs)) { // 公文拟报
				requname = getRequestname(gwnbhf);
				reqId = getRequestID(gwnbhf);
			} else {
				requname = blqk;
			}
			
			String operateDate = getOperateDate(requestid);
			String hh = "";
			sb.append("<h2 style=\"text-align:center;font-family:微软雅黑;margin:0 auto\">" + dqrChinese + "</h2>" + hh);
//			sb.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
//			sb.append("<tr>");
			String sq = "<p style=\"font-size:16px; font-family:微软雅黑;margin:0 auto\">" + swrqChinese + "收到的" + lwdw+ "";
			if(!"".equals(wh)) {
				sq += "的"+wh+" “" + gwswrequestname + "”。</p>";
			} else {
				sq += "的“" + gwswrequestname + "”。</p>";
			}
			sb.append(sq);
//			sb.append("</tr>");
//			sb.append("<tr>");
			String s = "<p style=\"font-size:16px; font-family:微软雅黑;margin:0 auto\">局办意见：请" + ysldChinese + " 阅示。";
			if(!yldChinese.equals("")) {
				s += "呈"+yldChinese+" 阅。</p>";
			} else {
				s += "</p>";
			}
			sb.append(s+hh);
			String s1 = "<p style=\"font-size:16px; font-family:微软雅黑;margin:0 auto\">请" + zbbmChinese;
			if(!"".equals(xbbmChinese)) {
				s1 += " 会 " + xbbmChinese + "：" + nbyj + "</p>";
			} else {
				s1 += "</p>";
			}
			sb.append(s1);
//			sb.append("</tr>");
			
			String jz = getJZ(requestid);
//			sb.append("<tr>");
			if(!"".equals(jz)) {
				sb.append("<p style=\"font-size:16px; font-family:微软雅黑;margin:0 auto\">局长拟办意见："+jz+"</p>"+hh);
			}
//			sb.append("</tr>");
			
//			sb.append("<tr>");
			sb.append("<p style=\"font-size:16px; font-family:微软雅黑;margin:0 auto\">承办部门为："+createdeparmentname+"，到期日为"+dqrChinese+"。</p>"+hh);
//			sb.append("</tr>");
//			sb.append("<tr>");
			if("公文拟报".equals(selectname)) {
				sb.append("<p style=\"font-size:16px; font-family:微软雅黑;margin:0 auto\">办理结果："+createdeparmentname+"，"+operateDate+" 拟文："+reqId+", "+requname+"。</p>");
			} else {
				sb.append("<p style=\"font-size:16px; font-family:微软雅黑;margin:0 auto\">办理结果："+createdeparmentname+"，"+operateDate+" 回复意见："+requname+"。</p>");
			}
			
//			sb.append("</tr>");
//			sb.append("</table>");
			if (c < count-1) {
				sb.append("<br clear=all  style='mso-special-character:page-break;page-break-before:always'>");
			}
			c++;
		}
		String content = "<html xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\""
				+ "xmlns:w=\"urn:schemas-microsoft-com:office:word\" xmlns:m=\"http://schemas.microsoft.com/office/2004/12/omml\""
				+ "xmlns=\"http://www.w3.org/TR/REC-html40\"><head><!--[if gte mso 9]><xml><w:WordDocument><w:View>Print</w:View><w:TrackMoves>false</w:TrackMoves><w:TrackFormatting/><w:ValidateAgainstSchemas/><w:SaveIfXMLInvalid>false</w:SaveIfXMLInvalid><w:IgnoreMixedContent>false</w:IgnoreMixedContent><w:AlwaysShowPlaceholderText>false</w:AlwaysShowPlaceholderText><w:DoNotPromoteQF/><w:LidThemeOther>EN-US</w:LidThemeOther><w:LidThemeAsian>ZH-CN</w:LidThemeAsian><w:LidThemeComplexScript>X-NONE</w:LidThemeComplexScript><w:Compatibility><w:BreakWrappedTables/><w:SnapToGridInCell/><w:WrapTextWithPunct/><w:UseAsianBreakRules/><w:DontGrowAutofit/><w:SplitPgBreakAndParaMark/><w:DontVertAlignCellWithSp/><w:DontBreakConstrainedForcedTables/><w:DontVertAlignInTxbx/><w:Word11KerningPairs/><w:CachedColBalance/><w:UseFELayout/></w:Compatibility><w:BrowserLevel>MicrosoftInternetExplorer4</w:BrowserLevel><m:mathPr><m:mathFont m:val=\"Cambria Math\"/><m:brkBin m:val=\"before\"/><m:brkBinSub m:val=\"--\"/><m:smallFrac m:val=\"off\"/><m:dispDef/><m:lMargin m:val=\"0\"/> <m:rMargin m:val=\"0\"/><m:defJc m:val=\"centerGroup\"/><m:wrapIndent m:val=\"1440\"/><m:intLim m:val=\"subSup\"/><m:naryLim m:val=\"undOvr\"/></m:mathPr></w:WordDocument></xml><![endif]--><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"></head><body>"
				+ sb.toString() + "</body></html>";
		InputStream is = new ByteArrayInputStream(content.getBytes("UTF-8"));
		OutputStream os = new FileOutputStream("D:\\WEAVER\\ecology\\interface\\jiangyl\\word\\temp.doc");
		inputStreamToWord(is, os);
		download("D:\\WEAVER\\ecology\\interface\\jiangyl\\word\\temp.doc", response);
	}

	public String getXBBM(String depid) {
		RecordSet rs = new RecordSet();
		rs.execute("select zm from uf_xbbmfz where xh in ("+depid+")");
		StringBuffer sb = new StringBuffer(",");
		while(rs.next()) {
			String cw = Util.null2String(rs.getString("zm"));
			sb.append(cw);
			sb.append(",");
		}
		String sbs = sb.toString();
		String result = "";
		if(!",".equals(sbs)) {
			result = sbs.substring(1,sbs.length()-1);
		}
		return result;
	}
	
	public String getCW(String hrmids) {
		RecordSet rs = new RecordSet();
		rs.execute("select jobactivitydesc from hrmresource where id in ("+hrmids+")");
		StringBuffer sb = new StringBuffer(",");
		while(rs.next()) {
			String cw = Util.null2String(rs.getString("jobactivitydesc"));
			sb.append(cw);
			sb.append(",");
		}
		String sbs = sb.toString();
		String result = "";
		if(!",".equals(sbs)) {
			result = sbs.substring(1,sbs.length()-1);
		}
		return result;
	}
	
	public String getJZ(String requestid) {
		RecordSet rs = new RecordSet();
		rs.execute("select * from workflow_requestbase where requestid = '"+requestid+"'");
		rs.next();
		String mainrequestid = Util.null2String(rs.getString("mainrequestid"));
		String result = "";
		for(int i = 1; i < 10; i++) {
			result += getYJ(mainrequestid, i);
		}
		return result;
	}
	
	public String getYJ(String requestid, int number) {
		RecordSet rs = new RecordSet();
		String result = "";
		String back = "";
		if(number == 1) {
			back = "jzxcs,jzxsydw,jzxfj";
		} else {
			back = "jzxbm" + number;
		}
		rs.execute("select jldrq"+number+",jzyj"+number+", "+back+" from formtable_main_37 where requestid = '"+requestid+"' and jzyj"+number+" is not null ");
		while(rs.next()) {
			String jldrq = Util.null2String(rs.getString("jldrq"+number));
			String jzyj = Util.null2String(rs.getString("jzyj"+number));
			if(number == 1) {
				String jzxcs = Util.null2String(rs.getString("jzxcs"));
				String jzxsydw = Util.null2String(rs.getString("jzxsydw"));
				String jzxfj = Util.null2String(rs.getString("jzxfj"));
				String message = "许明局长";
				if(!"".equals(jldrq)) {
					message += "于"+jldrq;
				}
				message += "批示:";
				String bumen = "";
				DepartmentComInfo department = new DepartmentComInfo();
				if(!"".equals(jzxcs)) {
					bumen = department.getDepartmentNames(jzxcs);
				}
				if(!"".equals(jzxsydw)) {
					if(!"".equals(bumen)) {
						bumen += "," + department.getDepartmentNames(jzxsydw);
					} else {
						bumen = department.getDepartmentNames(jzxsydw);
					}
				}
				if(!"".equals(jzxfj)) {
					if(!"".equals(bumen)) {
						bumen += "," + department.getDepartmentNames(jzxsydw);
					} else {
						bumen = department.getDepartmentNames(jzxfj);
					}
				}
				if(!"".equals(bumen)) {
					message += "\"请"+bumen+" " + jzyj + "\";<br>";
				} else {
					message += "\"" + jzyj + "\";<br>";
				}
				result = message;
			} else {
				String jzxbm = Util.null2String(rs.getString("jzxbm"+number));
				String message = getCW(number);
				if(!"".equals(jldrq)) {
					message += "于"+jldrq;
				}
				message += "批示:";
				String bumen = "";
				DepartmentComInfo department = new DepartmentComInfo();
				if(!"".equals(jzxbm)) {
					bumen = department.getDepartmentNames(jzxbm);
				}
				if(!"".equals(bumen)) {
					message += "\"请"+bumen+" " + jzyj + "\";<br>";
				} else {
					message += "\"" + jzyj + "\";<br>";
				}
				result = message;
			}
		}
		return result;
	}
	
	public String getCW(int number) {
		if(number == 1) {
			return "许明局长";
		}
		if(number == 2) {
			return "王东副书记";
		}
		if(number == 3) {
			return "丁和庚副局长";
		}
		if(number == 4) {
			return "冯雪渔副局长";
		}
		if(number == 5) {
			return "宋雅建副局长";
		}
		if(number == 6) {
			return "潘文辉副局长";
		}
		if(number == 7) {
			return "乔兵组长";
		}
		if(number == 8) {
			return "王晓庆副巡视员";
		}
		if(number == 9) {
			return "闻金苗副巡视员";
		}
		return "";
	}
	
	public static void htmlToWord2() throws Exception {
		String hh = "";
		String body = "<h2 style=\"text-align:center;font-family:微软雅黑;\">2017年12月18日</h2>" + hh;
		String bodyTable = ""
				+ "<p style=\"font-size:16px; font-family:微软雅黑;margin:0 auto\">2017年12月14日收到的收文test007的 “收文test007”。</p>"+hh
				+ "<p style=\"font-size:16px; font-family:微软雅黑;margin:0 auto\">局办拟办意见：市局领导阅。请秦淮分局、各处长（局长、主任）阅知。</p>"+hh
				+ "<p style=\"font-size:16px; font-family:微软雅黑;margin:0 auto\">承办部门为：阿撒撒打算打算的阿撒的阿撒的阿撒的阿撒打算打算多少啊阿撒的阿撒的阿撒的阿撒打算打算的</p>"+hh
				+ "<p style=\"font-size:16px; font-family:微软雅黑;margin:0 auto\">承办部门为：阿撒撒打算打算的阿撒的阿撒的阿撒的阿撒打算打算多少啊阿撒的阿撒的阿撒的阿撒打算打算的</p>" +hh
				+ "<p style=\"font-size:16px; font-family:微软雅黑;margin:0 auto\">承办部门为：阿撒撒打算打算的阿撒的阿撒的阿撒的阿撒打算打算多少啊阿撒的阿撒的阿撒的阿撒打算打算的</p>" +hh
				+ "<p style=\"font-size:16px; font-family:微软雅黑;margin:0 auto\">承办部门为：阿撒撒打算打算的阿撒的阿撒的阿撒的阿撒打算打算多少啊阿撒的阿撒的阿撒的阿撒打算打算的</p>" +hh
				+ "<p style=\"font-size:16px; font-family:微软雅黑;margin:0 auto\">承办部门为：阿撒撒打算打算的阿撒的阿撒的阿撒的阿撒打算打算多少啊阿撒的阿撒的阿撒的阿撒打算打算的</p>"+hh
				+ "<p style=\"font-size:16px; font-family:微软雅黑;margin:0 auto\">承办部门为：阿撒撒打算打算的阿撒的阿撒的阿撒的阿撒打算打算多少啊阿撒的阿撒的阿撒的阿撒打算打算的</p>"+hh
				+ "<br clear=all  style='mso-special-character:page-break;page-break-before:always'>";
		// 拼一个标准的HTML格式文档
		String content = "<html xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\""
				+ "xmlns:w=\"urn:schemas-microsoft-com:office:word\" xmlns:m=\"http://schemas.microsoft.com/office/2004/12/omml\""
				+ "xmlns=\"http://www.w3.org/TR/REC-html40\"><head><!--[if gte mso 9]><xml><w:WordDocument><w:View>Print</w:View><w:TrackMoves>false</w:TrackMoves><w:TrackFormatting/><w:ValidateAgainstSchemas/><w:SaveIfXMLInvalid>false</w:SaveIfXMLInvalid><w:IgnoreMixedContent>false</w:IgnoreMixedContent><w:AlwaysShowPlaceholderText>false</w:AlwaysShowPlaceholderText><w:DoNotPromoteQF/><w:LidThemeOther>EN-US</w:LidThemeOther><w:LidThemeAsian>ZH-CN</w:LidThemeAsian><w:LidThemeComplexScript>X-NONE</w:LidThemeComplexScript><w:Compatibility><w:BreakWrappedTables/><w:SnapToGridInCell/><w:WrapTextWithPunct/><w:UseAsianBreakRules/><w:DontGrowAutofit/><w:SplitPgBreakAndParaMark/><w:DontVertAlignCellWithSp/><w:DontBreakConstrainedForcedTables/><w:DontVertAlignInTxbx/><w:Word11KerningPairs/><w:CachedColBalance/><w:UseFELayout/></w:Compatibility><w:BrowserLevel>MicrosoftInternetExplorer4</w:BrowserLevel><m:mathPr><m:mathFont m:val=\"Cambria Math\"/><m:brkBin m:val=\"before\"/><m:brkBinSub m:val=\"--\"/><m:smallFrac m:val=\"off\"/><m:dispDef/><m:lMargin m:val=\"0\"/> <m:rMargin m:val=\"0\"/><m:defJc m:val=\"centerGroup\"/><m:wrapIndent m:val=\"1440\"/><m:intLim m:val=\"subSup\"/><m:naryLim m:val=\"undOvr\"/></m:mathPr></w:WordDocument></xml><![endif]--><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"></head><body>"
				+ body + bodyTable + "</body></html>";
		InputStream is = new ByteArrayInputStream(content.getBytes("UTF-8"));
		OutputStream os = new FileOutputStream("/Users/wangshanshan/Desktop/temp.doc");
		inputStreamToWord(is, os);
		System.out.println("生成好了");
	}

	public String getChineseDate(String date) {
		if("".equals(date)) {
			return "";
		}
		String[] dates = date.split("-");
		return dates[0] + "年" + dates[1] + "月" + dates[2] + "日";
	}

	/**
	 * 把is写入到对应的word输出流os中 不考虑异常的捕获，直接抛出
	 * 
	 * @param is
	 * @param os
	 * @throws IOException
	 */
	private static void inputStreamToWord(InputStream is, OutputStream os) throws IOException {
		POIFSFileSystem fs = new POIFSFileSystem();
		// 对应于org.apache.poi.hdf.extractor.WordDocument
		fs.createDocument(is, "WordDocument");
		fs.writeFilesystem(os);
		os.close();
		is.close();
	}

	/**
	 * 下载Excel文件
	 * 
	 * @param path
	 * @param response
	 */
	public void download(String path, HttpServletResponse response) {
		SimpleDateFormat simpledate = new SimpleDateFormat("yyyyMMddHHmmss");
		OutputStream toClient = null;
		try {
			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String((simpledate.format(new Date()) + ".doc").getBytes("GB2312"), "ISO8859-1"));
			// 设定输出文件头
			response.setContentType("application/msword");
			toClient = new BufferedOutputStream(response.getOutputStream());
			toClient.write(buffer);
			toClient.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				toClient.close();
				toClient = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String getLabelName(String labelid, String selectvalue) {
		RecordSet rs = new RecordSet();
		String swlxsql = "select selectname,SELECTVALUE from WORKFLOW_SELECTITEM where fieldid = '" + labelid
				+ "' and selectvalue = '" + selectvalue + "'";
		rs.execute(swlxsql);
		rs.next();
		String selectname = Util.null2String(rs.getString("selectname"));
		return selectname;
	}

	public String getRequestname(String gwnb) {
		RecordSet rs = new RecordSet();
		String swlxsql = "select requestname from workflow_requestbase where requestid = '" + gwnb + "' ";
		rs.execute(swlxsql);
		rs.next();
		String selectname = Util.null2String(rs.getString("requestname"));
		return selectname;
	}
	
	public String getRequestID(String gwnb) {
		RecordSet rs = new RecordSet();
		String swlxsql = "select requestid from workflow_requestbase where requestid = '" + gwnb + "' ";
		rs.execute(swlxsql);
		rs.next();
		String requestid = Util.null2String(rs.getString("requestid"));
		return requestid;
	}
	
	public String getOperateDate(String requestid) {
		RecordSet rs = new RecordSet();
		String swlxsql = "select operatedate from WORKFLOW_CURRENTOPERATOR where requestid = '"+requestid+"' and nodeid = 296 ";
		rs.execute(swlxsql);
		rs.next();
		String selectname = Util.null2String(rs.getString("operatedate"));
		return selectname;
	}
}
