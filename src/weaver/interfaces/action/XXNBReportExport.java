package weaver.interfaces.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import km.org.apache.poi.hssf.usermodel.HSSFCell;
import km.org.apache.poi.hssf.usermodel.HSSFCellStyle;
import km.org.apache.poi.hssf.usermodel.HSSFFont;
import km.org.apache.poi.hssf.usermodel.HSSFRow;
import km.org.apache.poi.hssf.usermodel.HSSFSheet;
import km.org.apache.poi.hssf.usermodel.HSSFWorkbook;
import km.org.apache.poi.hssf.util.CellRangeAddress;
import km.org.apache.poi.hssf.util.HSSFColor;
import weaver.general.Util;


public class XXNBReportExport extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String begindatetime = Util.null2String(req.getParameter("begindate"));
		String enddatetime = Util.null2String(req.getParameter("enddate"));
		OutputStream out = null;
		try {
			out = new FileOutputStream("D:\\WEAVER\\ecology\\interface\\jiangyl\\excel\\temp.xlsx");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet("信息采用成绩通报");
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 20);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.WHITE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.DEFAULT_CHARSET);
		font.setFontName("微软雅黑");
		// 把字体应用到当前的样式
		style.setFont(font);

		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setColor(HSSFColor.BLACK.index);
		font2.setFontHeightInPoints((short) 14);
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font2.setFontName("微软雅黑");
		// 把字体应用到当前的样式
		style2.setFont(font2);
		
		
		CellRangeAddress cellRange1 = new CellRangeAddress(0, 0, 1, 11);
		sheet.addMergedRegion(cellRange1);

		CellRangeAddress cellRange = new CellRangeAddress(0, 2, 0, 0);
		sheet.addMergedRegion(cellRange);
		
		CellRangeAddress cellRange2 = new CellRangeAddress(0, 2, 12, 12);
		sheet.addMergedRegion(cellRange2);
		
		CellRangeAddress cellRange3 = new CellRangeAddress(0, 2, 13, 13);
		sheet.addMergedRegion(cellRange3);
		
		CellRangeAddress cellRange4 = new CellRangeAddress(1, 1, 1, 3);
		sheet.addMergedRegion(cellRange4);
		
		for(int i = 4; i < 12; i++) {
			CellRangeAddress cellRange5 = new CellRangeAddress(1, 2, i, i);
			sheet.addMergedRegion(cellRange5);
		}
		String[] first = new String[] {"单位","采用信息","采用信息","采用信息","采用信息","采用信息","采用信息","采用信息","采用信息","采用信息","采用信息","采用信息","本次积分","年度累计积分"};
		String[] second = new String[] {"","市局采用","","","市委采用","市政府采用","省厅采用","省委、省政府、办公厅采用","中办、国办、部办公厅采用","加分情况","投稿数","规定指标","",""};
		String[] third = new String[] {"","市局采用总数","其中市局专报、专刊、专项动态","其中廉政信息","","","","","","","","","",""};
		HSSFRow row0 = sheet.createRow(0);
		for(int i = 0; i < first.length; i++) {
			HSSFCell cell0 = row0.createCell(i);
			cell0.setCellStyle(style2);
			cell0.setCellValue(first[i]);
		}
		HSSFRow row1 = sheet.createRow(1);
		for(int i = 0; i < second.length; i++) {
			HSSFCell cell0 = row1.createCell(i);
			cell0.setCellStyle(style2);
			cell0.setCellValue(second[i]);
		}
		HSSFRow row2 = sheet.createRow(2);
		for(int i = 0; i < third.length; i++) {
			HSSFCell cell0 = row2.createCell(i);
			cell0.setCellStyle(style2);
			cell0.setCellValue(third[i]);
		}
		
		XXNBReport export = new XXNBReport();
		LinkedList<Object[]> list = export.xxnbReportMap(begindatetime, enddatetime);
		for(int i = 0; i < list.size(); i++) {
			HSSFRow row3 = sheet.createRow(3 + i);
			for(int j = 0; j < list.get(i).length; j++) {
				HSSFCell cell0 = row3.createCell(j);
				cell0.setCellStyle(style);
				cell0.setCellValue(Util.null2String(list.get(i)[j]));
			}
		}
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/**
		 * 下载生成好的Excel表格
		 * 
		 **/
		try {
			download("D:\\WEAVER\\ecology\\interface\\jiangyl\\excel\\temp.xls", resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
					+ new String((simpledate.format(new Date()) + ".xls").getBytes("GB2312"), "ISO8859-1"));
			// 设定输出文件头
			response.setContentType("application/vnd.ms-excel");
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
}
