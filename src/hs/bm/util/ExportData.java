package hs.bm.util;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

import hs.bm.bean.BrgMonitorCableforce;
import hs.bm.bean.BrgMonitorDynadisp;
import hs.bm.bean.BrgMonitorStaticdisp;
import hs.bm.bean.BrgMonitorStrainc;
import hs.bm.bean.BrgMonitorStrains;
import hs.bm.bean.BrgMonitorTemp;
import hs.bm.bean.BrgWeightLoadRatio;
import hs.bm.bean.MaxGw;
import hs.bm.bean.MaxGw8;
import hs.bm.bean.NumOvlo;
import hs.bm.bean.NumOvlo8;
import hs.bm.bean.NumVehi;
import hs.bm.bean.NumVehi8;
import hs.bm.bean.ProbOvlo;
import hs.bm.bean.ProbOvlo8;
import hs.bm.bean.RatioOvlo;
import hs.bm.bean.RatioOvlo8;

public class ExportData {

	public void ExportWithResponse1(String titleName,  
            String fileName,String [] titleList,NumVehi8 nv8,HttpServletResponse response) throws Exception {  
       
            // 第一步，创建一个webbook，对应一个Excel文件  
            HSSFWorkbook wb = new HSSFWorkbook();  
            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
            HSSFSheet sheet = wb.createSheet(titleName);  
            // sheet.setDefaultColumnWidth(15); //统一设置列宽  
            for (int i = 0; i < titleList.length; i++)   
            {  
                for (int j = 0; j <= i; j++)   
                {  
                    if (i == j)   
                    {  
                        sheet.setColumnWidth(i, 20 * 256); // 单独设置每列的宽  
                    }  
                }  
            }  
            // 创建第0行 也就是标题  
            HSSFRow row1 = sheet.createRow((int) 0);  
            row1.setHeightInPoints(50);// 设备标题的高度  
            // 第三步创建标题的单元格样式style2以及字体样式headerFont1  
            HSSFCellStyle style2 = wb.createCellStyle();  
            style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
            style2.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);  
            style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
            HSSFFont headerFont1 = (HSSFFont) wb.createFont(); // 创建字体样式  
            headerFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
            headerFont1.setFontName("黑体"); // 设置字体类型  
            headerFont1.setFontHeightInPoints((short) 15); // 设置字体大小  
            style2.setFont(headerFont1); // 为标题样式设置字体样式  
  
            HSSFCell cell1 = row1.createCell(0);// 创建标题第一列  
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,  
            		titleList.length - 1)); // 合并列标题  
            cell1.setCellValue(titleName); // 设置值标题  
            cell1.setCellStyle(style2); // 设置标题样式  
  
            // 创建第1行 也就是表头  
            HSSFRow row = sheet.createRow((int) 1);  
            row.setHeightInPoints(37);// 设置表头高度  
  
            // 第四步，创建表头单元格样式 以及表头的字体样式  
            HSSFCellStyle style = wb.createCellStyle();  
            style.setWrapText(true);// 设置自动换行  
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式  
  
            style.setBottomBorderColor(HSSFColor.BLACK.index);  
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
  
            HSSFFont headerFont = (HSSFFont) wb.createFont(); // 创建字体样式  
            headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
            headerFont.setFontName("黑体"); // 设置字体类型  
            headerFont.setFontHeightInPoints((short) 10); // 设置字体大小  
            style.setFont(headerFont); // 为标题样式设置字体样式  
  
            // 第四.一步，创建表头的列  
            for (int i = 0; i < titleList.length; i++)   
            {  
                HSSFCell cell = row.createCell(i);  
                cell.setCellValue(titleList[i]);  
                cell.setCellStyle(style);  
            }  
  
            /*第五步*/
            // 为数据内容设置特点新单元格样式2 自动换行 上下居中左右也居中  
            HSSFCellStyle zidonghuanhang2 = wb.createCellStyle();  
            zidonghuanhang2.setWrapText(true);// 设置自动换行  
            zidonghuanhang2  
                    .setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个上下居中格式  
            zidonghuanhang2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中  

            // 设置边框  
            zidonghuanhang2.setBottomBorderColor(HSSFColor.BLACK.index);  
            zidonghuanhang2.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderTop(HSSFCellStyle.BORDER_THIN);  
            
            
            // 第六步，创建单元格，并设置值  
            HSSFCell datacell = null;
            sheet.addMergedRegion(new CellRangeAddress(2, 6, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(6, 6, 1, 2));
            sheet.addMergedRegion(new CellRangeAddress(7, 11, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(11, 11, 1, 2));
            sheet.addMergedRegion(new CellRangeAddress(12, 12, 1, 2));
            //第二行
            row = sheet.createRow(2);  
            datacell = row.createCell(0);  
            datacell.setCellValue("上行");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(1);  
            datacell.setCellValue("1");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("停车道");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(3);  
            datacell.setCellValue(nv8.getNumVehi1_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(nv8.getNumVehi1_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(nv8.getNumVehi1_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(nv8.getNumVehi1_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(nv8.getNumVehi1_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(nv8.getNumVehi1_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(nv8.getNumVehiLane1());  
            datacell.setCellStyle(zidonghuanhang2); 
          //第三行
            row = sheet.createRow(3); 
            datacell = row.createCell(1);  
            datacell.setCellValue("2");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道3");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(3);  
            datacell.setCellValue(nv8.getNumVehi2_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(nv8.getNumVehi2_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(nv8.getNumVehi2_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(nv8.getNumVehi2_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(nv8.getNumVehi2_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(nv8.getNumVehi2_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(nv8.getNumVehiLane2());  
            datacell.setCellStyle(zidonghuanhang2); 
          //第四行
            row = sheet.createRow(4); 
            datacell = row.createCell(1);  
            datacell.setCellValue("3");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道2");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(3);  
            datacell.setCellValue(nv8.getNumVehi3_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(nv8.getNumVehi3_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(nv8.getNumVehi3_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(nv8.getNumVehi3_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(nv8.getNumVehi3_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(nv8.getNumVehi3_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(nv8.getNumVehiLane3());  
            datacell.setCellStyle(zidonghuanhang2); 
          //第五行
            row = sheet.createRow(5); 
            datacell = row.createCell(1);  
            datacell.setCellValue("4");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道1");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(3);  
            datacell.setCellValue(nv8.getNumVehi4_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(nv8.getNumVehi4_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(nv8.getNumVehi4_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(nv8.getNumVehi4_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(nv8.getNumVehi4_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(nv8.getNumVehi4_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(nv8.getNumVehiLane4());  
            datacell.setCellStyle(zidonghuanhang2); 
            //第六行
            row = sheet.createRow(6); 
            datacell = row.createCell(1);  
            datacell.setCellValue("小计");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(nv8.getNumVehiHax1_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(nv8.getNumVehiHax1_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(nv8.getNumVehiHax1_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(nv8.getNumVehiHax1_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(nv8.getNumVehiHax1_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(nv8.getNumVehiHax1_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(nv8.getNumVehiHalf1());  
            datacell.setCellStyle(zidonghuanhang2); 
          //第七行
            row = sheet.createRow(7); 
            datacell = row.createCell(0);  
            datacell.setCellValue("下行");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(1);  
            datacell.setCellValue("5");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道1");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(nv8.getNumVehi5_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(nv8.getNumVehi5_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(nv8.getNumVehi5_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(nv8.getNumVehi5_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(nv8.getNumVehi5_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(nv8.getNumVehi5_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(nv8.getNumVehiLane5());  
            datacell.setCellStyle(zidonghuanhang2); 
          //第八行
            row = sheet.createRow(8); 
            datacell = row.createCell(1);  
            datacell.setCellValue("6");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道2");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(nv8.getNumVehi6_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(nv8.getNumVehi6_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(nv8.getNumVehi6_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(nv8.getNumVehi6_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(nv8.getNumVehi6_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(nv8.getNumVehi6_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(nv8.getNumVehiLane6());  
            datacell.setCellStyle(zidonghuanhang2); 
          //第九行
            row = sheet.createRow(9); 
            datacell = row.createCell(1);  
            datacell.setCellValue("7");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道3");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(nv8.getNumVehi7_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(nv8.getNumVehi7_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(nv8.getNumVehi7_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(nv8.getNumVehi7_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(nv8.getNumVehi7_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(nv8.getNumVehi7_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(nv8.getNumVehiLane7());  
            datacell.setCellStyle(zidonghuanhang2);
          //第十行
            row = sheet.createRow(10); 
            datacell = row.createCell(1);  
            datacell.setCellValue("8");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("停车道");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(nv8.getNumVehi8_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(nv8.getNumVehi8_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(nv8.getNumVehi8_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(nv8.getNumVehi8_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(nv8.getNumVehi8_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(nv8.getNumVehi8_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(nv8.getNumVehiLane8());  
            datacell.setCellStyle(zidonghuanhang2);
          //第十一行
            row = sheet.createRow(11); 
            datacell = row.createCell(1);  
            datacell.setCellValue("小计");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(nv8.getNumVehiHax2_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(nv8.getNumVehiHax2_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(nv8.getNumVehiHax2_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(nv8.getNumVehiHax2_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(nv8.getNumVehiHax2_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(nv8.getNumVehiHax2_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(nv8.getNumVehiHalf2());  
            datacell.setCellStyle(zidonghuanhang2);
          //第十二行
            row = sheet.createRow(12); 
            datacell = row.createCell(0);  
            datacell.setCellValue("");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(1);  
            datacell.setCellValue("合计");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(nv8.getNumVehiAxle1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(nv8.getNumVehiAxle2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(nv8.getNumVehiAxle3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(nv8.getNumVehiAxle4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(nv8.getNumVehiAxle5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(nv8.getNumVehiAxle6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(nv8.getNumVehiAll());  
            datacell.setCellStyle(zidonghuanhang2);
  
            // 第七步，将文件存到浏览器设置的下载位置  
            String filename = fileName + ".xls";  
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-disposition", "attachment; filename="+new String((filename).getBytes("gb2312"), "ISO8859-1"));  
            OutputStream out = response.getOutputStream();  
            
            try { 
            	if (wb != null) {
            		wb.write(out);// 将数据写出去  
            	}
            } catch (Exception e) {  
                e.printStackTrace();  
                String str1 = "导出" + fileName + "失败！";  
                System.out.println(str1);  
            } finally {  
                out.close();  
            }  
  
  
    }  
	
	public void ExportWithResponse2(String titleName,  
            String fileName,String [] titleList,MaxGw8 mg8,HttpServletResponse response) throws Exception {  
       
            // 第一步，创建一个webbook，对应一个Excel文件  
            HSSFWorkbook wb = new HSSFWorkbook();  
            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
            HSSFSheet sheet = wb.createSheet(titleName);  
            // sheet.setDefaultColumnWidth(15); //统一设置列宽  
            for (int i = 0; i < titleList.length; i++)   
            {  
                for (int j = 0; j <= i; j++)   
                {  
                    if (i == j)   
                    {  
                        sheet.setColumnWidth(i, 20 * 256); // 单独设置每列的宽  
                    }  
                }  
            }  
            // 创建第0行 也就是标题  
            HSSFRow row1 = sheet.createRow((int) 0);  
            row1.setHeightInPoints(50);// 设备标题的高度  
            // 第三步创建标题的单元格样式style2以及字体样式headerFont1  
            HSSFCellStyle style2 = wb.createCellStyle();  
            style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
            style2.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);  
            style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
            HSSFFont headerFont1 = (HSSFFont) wb.createFont(); // 创建字体样式  
            headerFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
            headerFont1.setFontName("黑体"); // 设置字体类型  
            headerFont1.setFontHeightInPoints((short) 15); // 设置字体大小  
            style2.setFont(headerFont1); // 为标题样式设置字体样式  
  
            HSSFCell cell1 = row1.createCell(0);// 创建标题第一列  
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,  
            		titleList.length - 1)); // 合并列标题  
            cell1.setCellValue(titleName); // 设置值标题  
            cell1.setCellStyle(style2); // 设置标题样式  
  
            // 创建第1行 也就是表头  
            HSSFRow row = sheet.createRow((int) 1);  
            row.setHeightInPoints(37);// 设置表头高度  
  
            // 第四步，创建表头单元格样式 以及表头的字体样式  
            HSSFCellStyle style = wb.createCellStyle();  
            style.setWrapText(true);// 设置自动换行  
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式  
  
            style.setBottomBorderColor(HSSFColor.BLACK.index);  
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
  
            HSSFFont headerFont = (HSSFFont) wb.createFont(); // 创建字体样式  
            headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
            headerFont.setFontName("黑体"); // 设置字体类型  
            headerFont.setFontHeightInPoints((short) 10); // 设置字体大小  
            style.setFont(headerFont); // 为标题样式设置字体样式  
  
            // 第四.一步，创建表头的列  
            for (int i = 0; i < titleList.length; i++)   
            {  
                HSSFCell cell = row.createCell(i);  
                cell.setCellValue(titleList[i]);  
                cell.setCellStyle(style);  
            }  
  
            /*第五步*/
            // 为数据内容设置特点新单元格样式2 自动换行 上下居中左右也居中  
            HSSFCellStyle zidonghuanhang2 = wb.createCellStyle();  
            zidonghuanhang2.setWrapText(true);// 设置自动换行  
            zidonghuanhang2  
                    .setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个上下居中格式  
            zidonghuanhang2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中  

            // 设置边框  
            zidonghuanhang2.setBottomBorderColor(HSSFColor.BLACK.index);  
            zidonghuanhang2.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderTop(HSSFCellStyle.BORDER_THIN);  
            
            
            // 第六步，创建单元格，并设置值  
            HSSFCell datacell = null;
            sheet.addMergedRegion(new CellRangeAddress(2, 6, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(6, 6, 1, 2));
            sheet.addMergedRegion(new CellRangeAddress(7, 11, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(11, 11, 1, 2));
            sheet.addMergedRegion(new CellRangeAddress(12, 12, 1, 2));
            //第二行
            row = sheet.createRow(2);  
            datacell = row.createCell(0);  
            datacell.setCellValue("上行");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(1);  
            datacell.setCellValue("1");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("停车道");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(3);  
            datacell.setCellValue(mg8.getMaxGw1_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(mg8.getMaxGw1_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(mg8.getMaxGw1_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(mg8.getMaxGw1_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(mg8.getMaxGw1_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(mg8.getMaxGw1_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(mg8.getMaxGwLane1());  
            datacell.setCellStyle(zidonghuanhang2); 
          //第三行
            row = sheet.createRow(3); 
            datacell = row.createCell(1);  
            datacell.setCellValue("2");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道3");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(3);  
            datacell.setCellValue(mg8.getMaxGw2_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(mg8.getMaxGw2_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(mg8.getMaxGw2_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(mg8.getMaxGw2_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(mg8.getMaxGw2_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(mg8.getMaxGw2_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(mg8.getMaxGwLane2());  
            datacell.setCellStyle(zidonghuanhang2); 
          //第四行
            row = sheet.createRow(4); 
            datacell = row.createCell(1);  
            datacell.setCellValue("3");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道2");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(3);  
            datacell.setCellValue(mg8.getMaxGw3_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(mg8.getMaxGw3_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(mg8.getMaxGw3_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(mg8.getMaxGw3_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(mg8.getMaxGw3_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(mg8.getMaxGw3_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(mg8.getMaxGwLane3());  
            datacell.setCellStyle(zidonghuanhang2); 
          //第五行
            row = sheet.createRow(5); 
            datacell = row.createCell(1);  
            datacell.setCellValue("4");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道1");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(3);  
            datacell.setCellValue(mg8.getMaxGw4_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(mg8.getMaxGw4_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(mg8.getMaxGw4_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(mg8.getMaxGw4_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(mg8.getMaxGw4_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(mg8.getMaxGw4_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(mg8.getMaxGwLane4());  
            datacell.setCellStyle(zidonghuanhang2); 
            //第六行
            row = sheet.createRow(6); 
            datacell = row.createCell(1);  
            datacell.setCellValue("小计");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(mg8.getMaxGwHax1_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(mg8.getMaxGwHax1_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(mg8.getMaxGwHax1_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(mg8.getMaxGwHax1_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(mg8.getMaxGwHax1_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(mg8.getMaxGwHax1_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(mg8.getMaxGwHalf1());  
            datacell.setCellStyle(zidonghuanhang2); 
          //第七行
            row = sheet.createRow(7); 
            datacell = row.createCell(0);  
            datacell.setCellValue("下行");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(1);  
            datacell.setCellValue("5");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道1");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(mg8.getMaxGw5_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(mg8.getMaxGw5_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(mg8.getMaxGw5_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(mg8.getMaxGw5_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(mg8.getMaxGw5_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(mg8.getMaxGw5_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(mg8.getMaxGwLane5());  
            datacell.setCellStyle(zidonghuanhang2); 
          //第八行
            row = sheet.createRow(8); 
            datacell = row.createCell(1);  
            datacell.setCellValue("6");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道2");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(mg8.getMaxGw6_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(mg8.getMaxGw6_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(mg8.getMaxGw6_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(mg8.getMaxGw6_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(mg8.getMaxGw6_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(mg8.getMaxGw6_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(mg8.getMaxGwLane6());  
            datacell.setCellStyle(zidonghuanhang2); 
          //第九行
            row = sheet.createRow(9); 
            datacell = row.createCell(1);  
            datacell.setCellValue("7");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道3");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(mg8.getMaxGw7_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(mg8.getMaxGw7_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(mg8.getMaxGw7_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(mg8.getMaxGw7_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(mg8.getMaxGw7_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(mg8.getMaxGw7_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(mg8.getMaxGwLane7());  
            datacell.setCellStyle(zidonghuanhang2);
          //第十行
            row = sheet.createRow(10); 
            datacell = row.createCell(1);  
            datacell.setCellValue("8");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("停车道");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(mg8.getMaxGw8_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(mg8.getMaxGw8_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(mg8.getMaxGw8_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(mg8.getMaxGw8_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(mg8.getMaxGw8_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(mg8.getMaxGw8_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(mg8.getMaxGwLane8());  
            datacell.setCellStyle(zidonghuanhang2);
          //第十一行
            row = sheet.createRow(11); 
            datacell = row.createCell(1);  
            datacell.setCellValue("小计");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(mg8.getMaxGw2_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(mg8.getMaxGw2_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(mg8.getMaxGw2_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(mg8.getMaxGw2_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(mg8.getMaxGw2_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(mg8.getMaxGw2_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(mg8.getMaxGwHalf2());  
            datacell.setCellStyle(zidonghuanhang2);
          //第十二行
            row = sheet.createRow(12); 
            datacell = row.createCell(0);  
            datacell.setCellValue("");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(1);  
            datacell.setCellValue("合计");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(mg8.getMaxGwAxle1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(mg8.getMaxGwAxle2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(mg8.getMaxGwAxle3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(mg8.getMaxGwAxle4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(mg8.getMaxGwAxle5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(mg8.getMaxGwAxle6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(mg8.getMaxGwAll());  
            datacell.setCellStyle(zidonghuanhang2);
  
            // 第七步，将文件存到浏览器设置的下载位置  
            String filename = fileName + ".xls";  
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-disposition", "attachment; filename="+new String((filename).getBytes("gb2312"), "ISO8859-1"));  
            OutputStream out = response.getOutputStream();  
            
            try { 
            	if (wb != null) {
            		wb.write(out);// 将数据写出去  
            	}
            } catch (Exception e) {  
                e.printStackTrace();  
                String str1 = "导出" + fileName + "失败！";  
                System.out.println(str1);  
            } finally {  
                out.close();  
            }  
  
  
    }  
	
	public void ExportWithResponse3(String titleName,  
            String fileName,String [] titleList,NumOvlo8 no8,HttpServletResponse response) throws Exception {  
       
            // 第一步，创建一个webbook，对应一个Excel文件  
            HSSFWorkbook wb = new HSSFWorkbook();  
            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
            HSSFSheet sheet = wb.createSheet(titleName);  
            // sheet.setDefaultColumnWidth(15); //统一设置列宽  
            for (int i = 0; i < titleList.length; i++)   
            {  
                for (int j = 0; j <= i; j++)   
                {  
                    if (i == j)   
                    {  
                        sheet.setColumnWidth(i, 20 * 256); // 单独设置每列的宽  
                    }  
                }  
            }  
            // 创建第0行 也就是标题  
            HSSFRow row1 = sheet.createRow((int) 0);  
            row1.setHeightInPoints(50);// 设备标题的高度  
            // 第三步创建标题的单元格样式style2以及字体样式headerFont1  
            HSSFCellStyle style2 = wb.createCellStyle();  
            style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
            style2.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);  
            style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
            HSSFFont headerFont1 = (HSSFFont) wb.createFont(); // 创建字体样式  
            headerFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
            headerFont1.setFontName("黑体"); // 设置字体类型  
            headerFont1.setFontHeightInPoints((short) 15); // 设置字体大小  
            style2.setFont(headerFont1); // 为标题样式设置字体样式  
  
            HSSFCell cell1 = row1.createCell(0);// 创建标题第一列  
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,  
            		titleList.length - 1)); // 合并列标题  
            cell1.setCellValue(titleName); // 设置值标题  
            cell1.setCellStyle(style2); // 设置标题样式  
  
            // 创建第1行 也就是表头  
            HSSFRow row = sheet.createRow((int) 1);  
            row.setHeightInPoints(37);// 设置表头高度  
  
            // 第四步，创建表头单元格样式 以及表头的字体样式  
            HSSFCellStyle style = wb.createCellStyle();  
            style.setWrapText(true);// 设置自动换行  
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式  
  
            style.setBottomBorderColor(HSSFColor.BLACK.index);  
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
  
            HSSFFont headerFont = (HSSFFont) wb.createFont(); // 创建字体样式  
            headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
            headerFont.setFontName("黑体"); // 设置字体类型  
            headerFont.setFontHeightInPoints((short) 10); // 设置字体大小  
            style.setFont(headerFont); // 为标题样式设置字体样式  
  
            // 第四.一步，创建表头的列  
            for (int i = 0; i < titleList.length; i++)   
            {  
                HSSFCell cell = row.createCell(i);  
                cell.setCellValue(titleList[i]);  
                cell.setCellStyle(style);  
            }  
  
            /*第五步*/
            // 为数据内容设置特点新单元格样式2 自动换行 上下居中左右也居中  
            HSSFCellStyle zidonghuanhang2 = wb.createCellStyle();  
            zidonghuanhang2.setWrapText(true);// 设置自动换行  
            zidonghuanhang2  
                    .setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个上下居中格式  
            zidonghuanhang2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中  

            // 设置边框  
            zidonghuanhang2.setBottomBorderColor(HSSFColor.BLACK.index);  
            zidonghuanhang2.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderTop(HSSFCellStyle.BORDER_THIN);  
            
            
            // 第六步，创建单元格，并设置值  
            HSSFCell datacell = null;
            sheet.addMergedRegion(new CellRangeAddress(2, 6, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(6, 6, 1, 2));
            sheet.addMergedRegion(new CellRangeAddress(7, 11, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(11, 11, 1, 2));
            sheet.addMergedRegion(new CellRangeAddress(12, 12, 1, 2));
            //第二行
            row = sheet.createRow(2);  
            datacell = row.createCell(0);  
            datacell.setCellValue("上行");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(1);  
            datacell.setCellValue("1");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("停车道");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(3);  
            datacell.setCellValue(no8.getNumOvlo1_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(no8.getNumOvlo1_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(no8.getNumOvlo1_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(no8.getNumOvlo1_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(no8.getNumOvlo1_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(no8.getNumOvlo1_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(no8.getNumOvloLane1());  
            datacell.setCellStyle(zidonghuanhang2); 
          //第三行
            row = sheet.createRow(3); 
            datacell = row.createCell(1);  
            datacell.setCellValue("2");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道3");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(3);  
            datacell.setCellValue(no8.getNumOvlo2_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(no8.getNumOvlo2_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(no8.getNumOvlo2_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(no8.getNumOvlo2_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(no8.getNumOvlo2_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(no8.getNumOvlo2_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(no8.getNumOvloLane2());  
            datacell.setCellStyle(zidonghuanhang2); 
          //第四行
            row = sheet.createRow(4); 
            datacell = row.createCell(1);  
            datacell.setCellValue("3");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道2");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(3);  
            datacell.setCellValue(no8.getNumOvlo3_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(no8.getNumOvlo3_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(no8.getNumOvlo3_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(no8.getNumOvlo3_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(no8.getNumOvlo3_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(no8.getNumOvlo3_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(no8.getNumOvloLane3());  
            datacell.setCellStyle(zidonghuanhang2); 
          //第五行
            row = sheet.createRow(5); 
            datacell = row.createCell(1);  
            datacell.setCellValue("4");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道1");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(3);  
            datacell.setCellValue(no8.getNumOvlo4_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(no8.getNumOvlo4_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(no8.getNumOvlo4_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(no8.getNumOvlo4_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(no8.getNumOvlo4_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(no8.getNumOvlo4_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(no8.getNumOvloLane4());  
            datacell.setCellStyle(zidonghuanhang2); 
            //第六行
            row = sheet.createRow(6); 
            datacell = row.createCell(1);  
            datacell.setCellValue("小计");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(no8.getNumOvloHax1_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(no8.getNumOvloHax1_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(no8.getNumOvloHax1_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(no8.getNumOvloHax1_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(no8.getNumOvloHax1_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(no8.getNumOvloHax1_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(no8.getNumOvloHalf1());  
            datacell.setCellStyle(zidonghuanhang2); 
          //第七行
            row = sheet.createRow(7); 
            datacell = row.createCell(0);  
            datacell.setCellValue("下行");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(1);  
            datacell.setCellValue("5");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道1");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(no8.getNumOvlo5_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(no8.getNumOvlo5_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(no8.getNumOvlo5_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(no8.getNumOvlo5_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(no8.getNumOvlo5_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(no8.getNumOvlo5_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(no8.getNumOvloLane5());  
            datacell.setCellStyle(zidonghuanhang2); 
          //第八行
            row = sheet.createRow(8); 
            datacell = row.createCell(1);  
            datacell.setCellValue("6");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道2");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(no8.getNumOvlo6_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(no8.getNumOvlo6_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(no8.getNumOvlo6_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(no8.getNumOvlo6_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(no8.getNumOvlo6_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(no8.getNumOvlo6_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(no8.getNumOvloLane6());  
            datacell.setCellStyle(zidonghuanhang2); 
          //第九行
            row = sheet.createRow(9); 
            datacell = row.createCell(1);  
            datacell.setCellValue("7");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道3");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(no8.getNumOvlo7_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(no8.getNumOvlo7_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(no8.getNumOvlo7_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(no8.getNumOvlo7_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(no8.getNumOvlo7_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(no8.getNumOvlo7_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(no8.getNumOvloLane7());  
            datacell.setCellStyle(zidonghuanhang2);
          //第十行
            row = sheet.createRow(10); 
            datacell = row.createCell(1);  
            datacell.setCellValue("8");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("停车道");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(no8.getNumOvlo8_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(no8.getNumOvlo8_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(no8.getNumOvlo8_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(no8.getNumOvlo8_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(no8.getNumOvlo8_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(no8.getNumOvlo8_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(no8.getNumOvloLane8());  
            datacell.setCellStyle(zidonghuanhang2);
          //第十一行
            row = sheet.createRow(11); 
            datacell = row.createCell(1);  
            datacell.setCellValue("小计");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(no8.getNumOvloHax2_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(no8.getNumOvloHax2_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(no8.getNumOvloHax2_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(no8.getNumOvloHax2_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(no8.getNumOvloHax2_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(no8.getNumOvloHax2_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(no8.getNumOvloHalf2());  
            datacell.setCellStyle(zidonghuanhang2);
          //第十二行
            row = sheet.createRow(12); 
            datacell = row.createCell(0);  
            datacell.setCellValue("");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(1);  
            datacell.setCellValue("合计");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(no8.getNumOvloAxle1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(no8.getNumOvloAxle2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(no8.getNumOvloAxle3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(no8.getNumOvloAxle4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(no8.getNumOvloAxle5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(no8.getNumOvloAxle6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(no8.getNumOvloAll());  
            datacell.setCellStyle(zidonghuanhang2);
  
            // 第七步，将文件存到浏览器设置的下载位置  
            String filename = fileName + ".xls";  
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-disposition", "attachment; filename="+new String((filename).getBytes("gb2312"), "ISO8859-1"));  
            OutputStream out = response.getOutputStream();  
            
            try { 
            	if (wb != null) {
            		wb.write(out);// 将数据写出去  
            	}
            } catch (Exception e) {  
                e.printStackTrace();  
                String str1 = "导出" + fileName + "失败！";  
                System.out.println(str1);  
            } finally {  
                out.close();  
            }  
  
  
    }  
	
	public void ExportWithResponse4(String titleName,  
            String fileName,String [] titleList,RatioOvlo8 ro8,HttpServletResponse response) throws Exception {  
       
            // 第一步，创建一个webbook，对应一个Excel文件  
            HSSFWorkbook wb = new HSSFWorkbook();  
            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
            HSSFSheet sheet = wb.createSheet(titleName);  
            // sheet.setDefaultColumnWidth(15); //统一设置列宽  
            for (int i = 0; i < titleList.length; i++)   
            {  
                for (int j = 0; j <= i; j++)   
                {  
                    if (i == j)   
                    {  
                        sheet.setColumnWidth(i, 20 * 256); // 单独设置每列的宽  
                    }  
                }  
            }  
            // 创建第0行 也就是标题  
            HSSFRow row1 = sheet.createRow((int) 0);  
            row1.setHeightInPoints(50);// 设备标题的高度  
            // 第三步创建标题的单元格样式style2以及字体样式headerFont1  
            HSSFCellStyle style2 = wb.createCellStyle();  
            style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
            style2.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);  
            style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
            HSSFFont headerFont1 = (HSSFFont) wb.createFont(); // 创建字体样式  
            headerFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
            headerFont1.setFontName("黑体"); // 设置字体类型  
            headerFont1.setFontHeightInPoints((short) 15); // 设置字体大小  
            style2.setFont(headerFont1); // 为标题样式设置字体样式  
  
            HSSFCell cell1 = row1.createCell(0);// 创建标题第一列  
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,  
            		titleList.length - 1)); // 合并列标题  
            cell1.setCellValue(titleName); // 设置值标题  
            cell1.setCellStyle(style2); // 设置标题样式  
  
            // 创建第1行 也就是表头  
            HSSFRow row = sheet.createRow((int) 1);  
            row.setHeightInPoints(37);// 设置表头高度  
  
            // 第四步，创建表头单元格样式 以及表头的字体样式  
            HSSFCellStyle style = wb.createCellStyle();  
            style.setWrapText(true);// 设置自动换行  
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式  
  
            style.setBottomBorderColor(HSSFColor.BLACK.index);  
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
  
            HSSFFont headerFont = (HSSFFont) wb.createFont(); // 创建字体样式  
            headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
            headerFont.setFontName("黑体"); // 设置字体类型  
            headerFont.setFontHeightInPoints((short) 10); // 设置字体大小  
            style.setFont(headerFont); // 为标题样式设置字体样式  
  
            // 第四.一步，创建表头的列  
            for (int i = 0; i < titleList.length; i++)   
            {  
                HSSFCell cell = row.createCell(i);  
                cell.setCellValue(titleList[i]);  
                cell.setCellStyle(style);  
            }  
  
            /*第五步*/
            // 为数据内容设置特点新单元格样式2 自动换行 上下居中左右也居中  
            HSSFCellStyle zidonghuanhang2 = wb.createCellStyle();  
            zidonghuanhang2.setWrapText(true);// 设置自动换行  
            zidonghuanhang2  
                    .setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个上下居中格式  
            zidonghuanhang2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中  

            // 设置边框  
            zidonghuanhang2.setBottomBorderColor(HSSFColor.BLACK.index);  
            zidonghuanhang2.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderTop(HSSFCellStyle.BORDER_THIN);  
            
            
            // 第六步，创建单元格，并设置值  
            HSSFCell datacell = null;
            sheet.addMergedRegion(new CellRangeAddress(2, 6, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(6, 6, 1, 2));
            sheet.addMergedRegion(new CellRangeAddress(7, 11, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(11, 11, 1, 2));
            sheet.addMergedRegion(new CellRangeAddress(12, 12, 1, 2));
            //第二行
            row = sheet.createRow(2);  
            datacell = row.createCell(0);  
            datacell.setCellValue("上行");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(1);  
            datacell.setCellValue("1");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("停车道");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo1_1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo1_2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo1_3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo1_4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo1_5()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo1_6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvloLane1()));  
            datacell.setCellStyle(zidonghuanhang2); 
          //第三行
            row = sheet.createRow(3); 
            datacell = row.createCell(1);  
            datacell.setCellValue("2");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道3");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo2_1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo2_2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo2_3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo2_4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo2_5()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo2_6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvloLane2()));  
            datacell.setCellStyle(zidonghuanhang2); 
          //第四行
            row = sheet.createRow(4); 
            datacell = row.createCell(1);  
            datacell.setCellValue("3");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道2");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo3_1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo3_2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo3_3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo3_4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo3_5()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo3_6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvloLane3()));  
            datacell.setCellStyle(zidonghuanhang2); 
          //第五行
            row = sheet.createRow(5); 
            datacell = row.createCell(1);  
            datacell.setCellValue("4");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道1");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo4_1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo4_2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo4_3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo4_4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo4_5()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo4_6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvloLane4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            //第六行
            row = sheet.createRow(6); 
            datacell = row.createCell(1);  
            datacell.setCellValue("小计");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvloHax1_1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvloHax1_2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvloHax1_3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvloHax1_4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvloHax1_5()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvloHax1_6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvloHalf1()));  
            datacell.setCellStyle(zidonghuanhang2); 
          //第七行
            row = sheet.createRow(7); 
            datacell = row.createCell(0);  
            datacell.setCellValue("下行");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(1);  
            datacell.setCellValue("5");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道1");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo5_1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo5_2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo5_3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo5_4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo5_5()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo5_6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvloLane5()));  
            datacell.setCellStyle(zidonghuanhang2); 
          //第八行
            row = sheet.createRow(8); 
            datacell = row.createCell(1);  
            datacell.setCellValue("6");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道2");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo6_1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo6_2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo6_3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo6_4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo6_5()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo6_6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvloLane6()));  
            datacell.setCellStyle(zidonghuanhang2); 
          //第九行
            row = sheet.createRow(9); 
            datacell = row.createCell(1);  
            datacell.setCellValue("7");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道3");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo7_1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo7_2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo7_3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo7_4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo7_5()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo7_6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvloLane7()));  
            datacell.setCellStyle(zidonghuanhang2);
          //第十行
            row = sheet.createRow(10); 
            datacell = row.createCell(1);  
            datacell.setCellValue("8");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("停车道");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo8_1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo8_2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo8_3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo8_4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo8_5()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvlo8_6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvloLane8()));  
            datacell.setCellStyle(zidonghuanhang2);
          //第十一行
            row = sheet.createRow(11); 
            datacell = row.createCell(1);  
            datacell.setCellValue("小计");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvloHax2_1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvloHax2_2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvloHax2_3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvloHax2_4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvloHax2_4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvloHax2_6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvloHalf2()));  
            datacell.setCellStyle(zidonghuanhang2);
          //第十二行
            row = sheet.createRow(12); 
            datacell = row.createCell(0);  
            datacell.setCellValue("");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(1);  
            datacell.setCellValue("合计");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvloAxle1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvloAxle2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvloAxle3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvloAxle4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvloAxle5()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvloAxle6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(ro8.getRatioOvloAll()));  
            datacell.setCellStyle(zidonghuanhang2);
  
            // 第七步，将文件存到浏览器设置的下载位置  
            String filename = fileName + ".xls";  
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-disposition", "attachment; filename="+new String((filename).getBytes("gb2312"), "ISO8859-1"));  
            OutputStream out = response.getOutputStream();  
            
            try { 
            	if (wb != null) {
            		wb.write(out);// 将数据写出去  
            	}
            } catch (Exception e) {  
                e.printStackTrace();  
                String str1 = "导出" + fileName + "失败！";  
                System.out.println(str1);  
            } finally {  
                out.close();  
            }  
  
  
    }  
	public void ExportWithResponse5(String titleName,  
            String fileName,String [] titleList,ProbOvlo8 po8,HttpServletResponse response) throws Exception {  
       
            // 第一步，创建一个webbook，对应一个Excel文件  
            HSSFWorkbook wb = new HSSFWorkbook();  
            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
            HSSFSheet sheet = wb.createSheet(titleName);  
            // sheet.setDefaultColumnWidth(15); //统一设置列宽  
            for (int i = 0; i < titleList.length; i++)   
            {  
                for (int j = 0; j <= i; j++)   
                {  
                    if (i == j)   
                    {  
                        sheet.setColumnWidth(i, 20 * 256); // 单独设置每列的宽  
                    }  
                }  
            }  
            // 创建第0行 也就是标题  
            HSSFRow row1 = sheet.createRow((int) 0);  
            row1.setHeightInPoints(50);// 设备标题的高度  
            // 第三步创建标题的单元格样式style2以及字体样式headerFont1  
            HSSFCellStyle style2 = wb.createCellStyle();  
            style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
            style2.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);  
            style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
            HSSFFont headerFont1 = (HSSFFont) wb.createFont(); // 创建字体样式  
            headerFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
            headerFont1.setFontName("黑体"); // 设置字体类型  
            headerFont1.setFontHeightInPoints((short) 15); // 设置字体大小  
            style2.setFont(headerFont1); // 为标题样式设置字体样式  
  
            HSSFCell cell1 = row1.createCell(0);// 创建标题第一列  
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,  
            		titleList.length - 1)); // 合并列标题  
            cell1.setCellValue(titleName); // 设置值标题  
            cell1.setCellStyle(style2); // 设置标题样式  
  
            // 创建第1行 也就是表头  
            HSSFRow row = sheet.createRow((int) 1);  
            row.setHeightInPoints(37);// 设置表头高度  
  
            // 第四步，创建表头单元格样式 以及表头的字体样式  
            HSSFCellStyle style = wb.createCellStyle();  
            style.setWrapText(true);// 设置自动换行  
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式  
  
            style.setBottomBorderColor(HSSFColor.BLACK.index);  
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
  
            HSSFFont headerFont = (HSSFFont) wb.createFont(); // 创建字体样式  
            headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
            headerFont.setFontName("黑体"); // 设置字体类型  
            headerFont.setFontHeightInPoints((short) 10); // 设置字体大小  
            style.setFont(headerFont); // 为标题样式设置字体样式  
  
            // 第四.一步，创建表头的列  
            for (int i = 0; i < titleList.length; i++)   
            {  
                HSSFCell cell = row.createCell(i);  
                cell.setCellValue(titleList[i]);  
                cell.setCellStyle(style);  
            }  
  
            /*第五步*/
            // 为数据内容设置特点新单元格样式2 自动换行 上下居中左右也居中  
            HSSFCellStyle zidonghuanhang2 = wb.createCellStyle();  
            zidonghuanhang2.setWrapText(true);// 设置自动换行  
            zidonghuanhang2  
                    .setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个上下居中格式  
            zidonghuanhang2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中  

            // 设置边框  
            zidonghuanhang2.setBottomBorderColor(HSSFColor.BLACK.index);  
            zidonghuanhang2.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderTop(HSSFCellStyle.BORDER_THIN);  
            
            
            // 第六步，创建单元格，并设置值  
            HSSFCell datacell = null;
            sheet.addMergedRegion(new CellRangeAddress(2, 6, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(6, 6, 1, 2));
            sheet.addMergedRegion(new CellRangeAddress(7, 11, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(11, 11, 1, 2));
            sheet.addMergedRegion(new CellRangeAddress(12, 12, 1, 2));
            //第二行
            row = sheet.createRow(2);  
            datacell = row.createCell(0);  
            datacell.setCellValue("上行");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(1);  
            datacell.setCellValue("1");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("停车道");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo1_1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo1_2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo1_3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo1_4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo1_5()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo1_6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvloLane1()));  
            datacell.setCellStyle(zidonghuanhang2); 
          //第三行
            row = sheet.createRow(3); 
            datacell = row.createCell(1);  
            datacell.setCellValue("2");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道3");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo2_1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo2_2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo2_3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo2_4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo2_5()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo2_6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvloLane2()));  
            datacell.setCellStyle(zidonghuanhang2); 
          //第四行
            row = sheet.createRow(4); 
            datacell = row.createCell(1);  
            datacell.setCellValue("3");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道2");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo3_1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo3_2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo3_3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo3_4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo3_5()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo3_6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvloLane3()));  
            datacell.setCellStyle(zidonghuanhang2); 
          //第五行
            row = sheet.createRow(5); 
            datacell = row.createCell(1);  
            datacell.setCellValue("4");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道1");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo4_1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo4_2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo4_3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo4_4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo4_5()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo4_6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvloLane4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            //第六行
            row = sheet.createRow(6); 
            datacell = row.createCell(1);  
            datacell.setCellValue("小计");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvloHax1_1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvloHax1_2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvloHax1_3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvloHax1_4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvloHax1_5()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvloHax1_6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvloHalf1()));  
            datacell.setCellStyle(zidonghuanhang2); 
          //第七行
            row = sheet.createRow(7); 
            datacell = row.createCell(0);  
            datacell.setCellValue("下行");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(1);  
            datacell.setCellValue("5");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道1");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo5_1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo5_2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo5_3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo5_4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo5_5()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo5_6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvloLane5()));  
            datacell.setCellStyle(zidonghuanhang2); 
          //第八行
            row = sheet.createRow(8); 
            datacell = row.createCell(1);  
            datacell.setCellValue("6");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道2");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo6_1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo6_2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo6_3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo6_4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo6_5()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo6_6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvloLane6()));  
            datacell.setCellStyle(zidonghuanhang2); 
          //第九行
            row = sheet.createRow(9); 
            datacell = row.createCell(1);  
            datacell.setCellValue("7");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道3");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo7_1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo7_2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo7_3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo7_4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo7_5()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo7_6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvloLane7()));  
            datacell.setCellStyle(zidonghuanhang2);
          //第十行
            row = sheet.createRow(10); 
            datacell = row.createCell(1);  
            datacell.setCellValue("8");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("停车道");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo8_1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo8_2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo8_3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo8_4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo8_5()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvlo8_6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvloLane8()));  
            datacell.setCellStyle(zidonghuanhang2);
          //第十一行
            row = sheet.createRow(11); 
            datacell = row.createCell(1);  
            datacell.setCellValue("小计");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvloHax2_1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvloHax2_2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvloHax2_3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvloHax2_4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvloHax2_5()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvloHax2_6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvloHalf2()));  
            datacell.setCellStyle(zidonghuanhang2);
          //第十二行
            row = sheet.createRow(12); 
            datacell = row.createCell(0);  
            datacell.setCellValue("");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(1);  
            datacell.setCellValue("合计");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvloAxle1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvloAxle2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvloAxle3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvloAxle4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvloAxle5()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvloAxle6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(po8.getProbOvloAll()));  
            datacell.setCellStyle(zidonghuanhang2);
  
            // 第七步，将文件存到浏览器设置的下载位置  
            String filename = fileName + ".xls";  
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-disposition", "attachment; filename="+new String((filename).getBytes("gb2312"), "ISO8859-1"));  
            OutputStream out = response.getOutputStream();  
            
            try { 
            	if (wb != null) {
            		wb.write(out);// 将数据写出去  
            	}
            } catch (Exception e) {  
                e.printStackTrace();  
                String str1 = "导出" + fileName + "失败！";  
                System.out.println(str1);  
            } finally {  
                out.close();  
            }  
  
  
    }  
	
	public void ExportWithResponse6(String titleName,  
            String fileName,String [] titleList,NumVehi nv,HttpServletResponse response) throws Exception {  
       
            // 第一步，创建一个webbook，对应一个Excel文件  
            HSSFWorkbook wb = new HSSFWorkbook();  
            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
            HSSFSheet sheet = wb.createSheet(titleName);  
            // sheet.setDefaultColumnWidth(15); //统一设置列宽  
            for (int i = 0; i < titleList.length; i++)   
            {  
                for (int j = 0; j <= i; j++)   
                {  
                    if (i == j)   
                    {  
                        sheet.setColumnWidth(i, 20 * 256); // 单独设置每列的宽  
                    }  
                }  
            }  
            // 创建第0行 也就是标题  
            HSSFRow row1 = sheet.createRow((int) 0);  
            row1.setHeightInPoints(50);// 设备标题的高度  
            // 第三步创建标题的单元格样式style2以及字体样式headerFont1  
            HSSFCellStyle style2 = wb.createCellStyle();  
            style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
            style2.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);  
            style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
            HSSFFont headerFont1 = (HSSFFont) wb.createFont(); // 创建字体样式  
            headerFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
            headerFont1.setFontName("黑体"); // 设置字体类型  
            headerFont1.setFontHeightInPoints((short) 15); // 设置字体大小  
            style2.setFont(headerFont1); // 为标题样式设置字体样式  
  
            HSSFCell cell1 = row1.createCell(0);// 创建标题第一列  
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,  
            		titleList.length - 1)); // 合并列标题  
            cell1.setCellValue(titleName); // 设置值标题  
            cell1.setCellStyle(style2); // 设置标题样式  
  
            // 创建第1行 也就是表头  
            HSSFRow row = sheet.createRow((int) 1);  
            row.setHeightInPoints(37);// 设置表头高度  
  
            // 第四步，创建表头单元格样式 以及表头的字体样式  
            HSSFCellStyle style = wb.createCellStyle();  
            style.setWrapText(true);// 设置自动换行  
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式  
  
            style.setBottomBorderColor(HSSFColor.BLACK.index);  
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
  
            HSSFFont headerFont = (HSSFFont) wb.createFont(); // 创建字体样式  
            headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
            headerFont.setFontName("黑体"); // 设置字体类型  
            headerFont.setFontHeightInPoints((short) 10); // 设置字体大小  
            style.setFont(headerFont); // 为标题样式设置字体样式  
  
            // 第四.一步，创建表头的列  
            for (int i = 0; i < titleList.length; i++)   
            {  
                HSSFCell cell = row.createCell(i);  
                cell.setCellValue(titleList[i]);  
                cell.setCellStyle(style);  
            }  
  
            /*第五步*/
            // 为数据内容设置特点新单元格样式2 自动换行 上下居中左右也居中  
            HSSFCellStyle zidonghuanhang2 = wb.createCellStyle();  
            zidonghuanhang2.setWrapText(true);// 设置自动换行  
            zidonghuanhang2  
                    .setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个上下居中格式  
            zidonghuanhang2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中  

            // 设置边框  
            zidonghuanhang2.setBottomBorderColor(HSSFColor.BLACK.index);  
            zidonghuanhang2.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderTop(HSSFCellStyle.BORDER_THIN);  
            
            
            // 第六步，创建单元格，并设置值  
            HSSFCell datacell = null;
            sheet.addMergedRegion(new CellRangeAddress(2, 5, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(5, 5, 1, 2));
            sheet.addMergedRegion(new CellRangeAddress(6, 9, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(9, 9, 1, 2));
            sheet.addMergedRegion(new CellRangeAddress(10, 10, 1, 2));
            //第二行
            row = sheet.createRow(2);  
            datacell = row.createCell(0);  
            datacell.setCellValue("上行");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(1);  
            datacell.setCellValue("1");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("停车道");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(3);  
            datacell.setCellValue(nv.getNumVehi1_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(nv.getNumVehi1_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(nv.getNumVehi1_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(nv.getNumVehi1_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(nv.getNumVehi1_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(nv.getNumVehi1_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(nv.getNumVehiLane1());  
            datacell.setCellStyle(zidonghuanhang2); 
          //第三行
            row = sheet.createRow(3); 
            datacell = row.createCell(1);  
            datacell.setCellValue("2");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道2");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(3);  
            datacell.setCellValue(nv.getNumVehi2_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(nv.getNumVehi2_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(nv.getNumVehi2_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(nv.getNumVehi2_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(nv.getNumVehi2_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(nv.getNumVehi2_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(nv.getNumVehiLane2());  
            datacell.setCellStyle(zidonghuanhang2); 
          //第四行
            row = sheet.createRow(4); 
            datacell = row.createCell(1);  
            datacell.setCellValue("3");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道1");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(3);  
            datacell.setCellValue(nv.getNumVehi3_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(nv.getNumVehi3_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(nv.getNumVehi3_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(nv.getNumVehi3_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(nv.getNumVehi3_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(nv.getNumVehi3_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(nv.getNumVehiLane3());  
            datacell.setCellStyle(zidonghuanhang2); 
          //第五行
            row = sheet.createRow(5); 
            datacell = row.createCell(1);  
            datacell.setCellValue("小计");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(nv.getNumVehiHax1_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(nv.getNumVehiHax1_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(nv.getNumVehiHax1_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(nv.getNumVehiHax1_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(nv.getNumVehiHax1_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(nv.getNumVehiHax1_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(nv.getNumVehiHalf1());  
            datacell.setCellStyle(zidonghuanhang2); 
          //第六行
            row = sheet.createRow(6); 
            datacell = row.createCell(0);  
            datacell.setCellValue("下行");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(1);  
            datacell.setCellValue("4");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道1");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(nv.getNumVehi4_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(nv.getNumVehi4_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(nv.getNumVehi4_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(nv.getNumVehi4_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(nv.getNumVehi4_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(nv.getNumVehi4_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(nv.getNumVehiLane4());  
            datacell.setCellStyle(zidonghuanhang2); 
          //第七行
            row = sheet.createRow(7); 
            datacell = row.createCell(1);  
            datacell.setCellValue("5");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道2");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(nv.getNumVehi5_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(nv.getNumVehi5_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(nv.getNumVehi5_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(nv.getNumVehi5_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(nv.getNumVehi5_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(nv.getNumVehi5_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(nv.getNumVehiLane5());  
            datacell.setCellStyle(zidonghuanhang2); 
          //第八行
            row = sheet.createRow(8); 
            datacell = row.createCell(1);  
            datacell.setCellValue("6");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("停车道");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(nv.getNumVehi6_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(nv.getNumVehi6_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(nv.getNumVehi6_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(nv.getNumVehi6_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(nv.getNumVehi6_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(nv.getNumVehi6_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(nv.getNumVehiLane6());  
            datacell.setCellStyle(zidonghuanhang2);
          //第九行
            row = sheet.createRow(9); 
            datacell = row.createCell(1);  
            datacell.setCellValue("小计");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(nv.getNumVehiHax2_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(nv.getNumVehiHax2_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(nv.getNumVehiHax2_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(nv.getNumVehiHax2_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(nv.getNumVehiHax2_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(nv.getNumVehiHax2_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(nv.getNumVehiHalf2());  
            datacell.setCellStyle(zidonghuanhang2);
          //第十行
            row = sheet.createRow(10); 
            datacell = row.createCell(0);  
            datacell.setCellValue("");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(1);  
            datacell.setCellValue("合计");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(nv.getNumVehiAxle1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(nv.getNumVehiAxle2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(nv.getNumVehiAxle3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(nv.getNumVehiAxle4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(nv.getNumVehiAxle5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(nv.getNumVehiAxle6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(nv.getNumVehiAll());  
            datacell.setCellStyle(zidonghuanhang2);
  
            // 第七步，将文件存到浏览器设置的下载位置  
            String filename = fileName + ".xls";  
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-disposition", "attachment; filename="+new String((filename).getBytes("gb2312"), "ISO8859-1"));  
            OutputStream out = response.getOutputStream();  
            
            try { 
            	if (wb != null) {
            		wb.write(out);// 将数据写出去  
            	}
            } catch (Exception e) {  
                e.printStackTrace();  
                String str1 = "导出" + fileName + "失败！";  
                System.out.println(str1);  
            } finally {  
                out.close();  
            }  
  
  
    }  
	
	public void ExportWithResponse7(String titleName,  
            String fileName,String [] titleList,MaxGw mg,HttpServletResponse response) throws Exception {  
       
            // 第一步，创建一个webbook，对应一个Excel文件  
            HSSFWorkbook wb = new HSSFWorkbook();  
            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
            HSSFSheet sheet = wb.createSheet(titleName);  
            // sheet.setDefaultColumnWidth(15); //统一设置列宽  
            for (int i = 0; i < titleList.length; i++)   
            {  
                for (int j = 0; j <= i; j++)   
                {  
                    if (i == j)   
                    {  
                        sheet.setColumnWidth(i, 20 * 256); // 单独设置每列的宽  
                    }  
                }  
            }  
            // 创建第0行 也就是标题  
            HSSFRow row1 = sheet.createRow((int) 0);  
            row1.setHeightInPoints(50);// 设备标题的高度  
            // 第三步创建标题的单元格样式style2以及字体样式headerFont1  
            HSSFCellStyle style2 = wb.createCellStyle();  
            style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
            style2.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);  
            style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
            HSSFFont headerFont1 = (HSSFFont) wb.createFont(); // 创建字体样式  
            headerFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
            headerFont1.setFontName("黑体"); // 设置字体类型  
            headerFont1.setFontHeightInPoints((short) 15); // 设置字体大小  
            style2.setFont(headerFont1); // 为标题样式设置字体样式  
  
            HSSFCell cell1 = row1.createCell(0);// 创建标题第一列  
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,  
            		titleList.length - 1)); // 合并列标题  
            cell1.setCellValue(titleName); // 设置值标题  
            cell1.setCellStyle(style2); // 设置标题样式  
  
            // 创建第1行 也就是表头  
            HSSFRow row = sheet.createRow((int) 1);  
            row.setHeightInPoints(37);// 设置表头高度  
  
            // 第四步，创建表头单元格样式 以及表头的字体样式  
            HSSFCellStyle style = wb.createCellStyle();  
            style.setWrapText(true);// 设置自动换行  
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式  
  
            style.setBottomBorderColor(HSSFColor.BLACK.index);  
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
  
            HSSFFont headerFont = (HSSFFont) wb.createFont(); // 创建字体样式  
            headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
            headerFont.setFontName("黑体"); // 设置字体类型  
            headerFont.setFontHeightInPoints((short) 10); // 设置字体大小  
            style.setFont(headerFont); // 为标题样式设置字体样式  
  
            // 第四.一步，创建表头的列  
            for (int i = 0; i < titleList.length; i++)   
            {  
                HSSFCell cell = row.createCell(i);  
                cell.setCellValue(titleList[i]);  
                cell.setCellStyle(style);  
            }  
  
            /*第五步*/
            // 为数据内容设置特点新单元格样式2 自动换行 上下居中左右也居中  
            HSSFCellStyle zidonghuanhang2 = wb.createCellStyle();  
            zidonghuanhang2.setWrapText(true);// 设置自动换行  
            zidonghuanhang2  
                    .setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个上下居中格式  
            zidonghuanhang2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中  

            // 设置边框  
            zidonghuanhang2.setBottomBorderColor(HSSFColor.BLACK.index);  
            zidonghuanhang2.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderTop(HSSFCellStyle.BORDER_THIN);  
            
            
            // 第六步，创建单元格，并设置值  
            HSSFCell datacell = null;
            sheet.addMergedRegion(new CellRangeAddress(2, 5, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(5, 5, 1, 2));
            sheet.addMergedRegion(new CellRangeAddress(6, 9, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(9, 9, 1, 2));
            sheet.addMergedRegion(new CellRangeAddress(10, 10, 1, 2));
            //第二行
            row = sheet.createRow(2);  
            datacell = row.createCell(0);  
            datacell.setCellValue("上行");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(1);  
            datacell.setCellValue("1");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("停车道");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(3);  
            datacell.setCellValue(mg.getMaxGw1_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(mg.getMaxGw1_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(mg.getMaxGw1_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(mg.getMaxGw1_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(mg.getMaxGw1_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(mg.getMaxGw1_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(mg.getMaxGwLane1());  
            datacell.setCellStyle(zidonghuanhang2); 
          //第三行
            row = sheet.createRow(3); 
            datacell = row.createCell(1);  
            datacell.setCellValue("2");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道2");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(3);  
            datacell.setCellValue(mg.getMaxGw2_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(mg.getMaxGw2_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(mg.getMaxGw2_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(mg.getMaxGw2_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(mg.getMaxGw2_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(mg.getMaxGw2_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(mg.getMaxGwLane2());  
            datacell.setCellStyle(zidonghuanhang2); 
          //第四行
            row = sheet.createRow(4); 
            datacell = row.createCell(1);  
            datacell.setCellValue("3");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道1");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(3);  
            datacell.setCellValue(mg.getMaxGw3_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(mg.getMaxGw3_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(mg.getMaxGw3_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(mg.getMaxGw3_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(mg.getMaxGw3_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(mg.getMaxGw3_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(mg.getMaxGwLane3());  
            datacell.setCellStyle(zidonghuanhang2); 
          //第五行
            row = sheet.createRow(5); 
            datacell = row.createCell(1);  
            datacell.setCellValue("小计");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(mg.getMaxGwHax1_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(mg.getMaxGwHax1_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(mg.getMaxGwHax1_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(mg.getMaxGwHax1_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(mg.getMaxGwHax1_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(mg.getMaxGwHax1_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(mg.getMaxGwHalf1());  
            datacell.setCellStyle(zidonghuanhang2); 
          //第六行
            row = sheet.createRow(6); 
            datacell = row.createCell(0);  
            datacell.setCellValue("下行");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(1);  
            datacell.setCellValue("4");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道1");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(mg.getMaxGw4_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(mg.getMaxGw4_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(mg.getMaxGw4_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(mg.getMaxGw4_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(mg.getMaxGw4_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(mg.getMaxGw4_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(mg.getMaxGwLane4());  
            datacell.setCellStyle(zidonghuanhang2); 
          //第七行
            row = sheet.createRow(7); 
            datacell = row.createCell(1);  
            datacell.setCellValue("6");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道2");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(mg.getMaxGw5_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(mg.getMaxGw5_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(mg.getMaxGw5_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(mg.getMaxGw5_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(mg.getMaxGw5_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(mg.getMaxGw5_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(mg.getMaxGwLane5());  
            datacell.setCellStyle(zidonghuanhang2); 
          //第八行
            row = sheet.createRow(8); 
            datacell = row.createCell(1);  
            datacell.setCellValue("8");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("停车道");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(mg.getMaxGw6_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(mg.getMaxGw6_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(mg.getMaxGw6_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(mg.getMaxGw6_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(mg.getMaxGw6_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(mg.getMaxGw6_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(mg.getMaxGwLane6());  
            datacell.setCellStyle(zidonghuanhang2);
          //第九行
            row = sheet.createRow(9); 
            datacell = row.createCell(1);  
            datacell.setCellValue("小计");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(mg.getMaxGw2_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(mg.getMaxGw2_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(mg.getMaxGw2_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(mg.getMaxGw2_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(mg.getMaxGw2_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(mg.getMaxGw2_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(mg.getMaxGwHalf2());  
            datacell.setCellStyle(zidonghuanhang2);
          //第十行
            row = sheet.createRow(10); 
            datacell = row.createCell(0);  
            datacell.setCellValue("");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(1);  
            datacell.setCellValue("合计");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(mg.getMaxGwAxle1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(mg.getMaxGwAxle2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(mg.getMaxGwAxle3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(mg.getMaxGwAxle4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(mg.getMaxGwAxle5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(mg.getMaxGwAxle6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(mg.getMaxGwAll());  
            datacell.setCellStyle(zidonghuanhang2);
  
            // 第七步，将文件存到浏览器设置的下载位置  
            String filename = fileName + ".xls";  
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-disposition", "attachment; filename="+new String((filename).getBytes("gb2312"), "ISO8859-1"));  
            OutputStream out = response.getOutputStream();  
            
            try { 
            	if (wb != null) {
            		wb.write(out);// 将数据写出去  
            	}
            } catch (Exception e) {  
                e.printStackTrace();  
                String str1 = "导出" + fileName + "失败！";  
                System.out.println(str1);  
            } finally {  
                out.close();  
            }  
  
  
    }  
	
	public void ExportWithResponse8(String titleName,  
            String fileName,String [] titleList,NumOvlo no,HttpServletResponse response) throws Exception {  
       
            // 第一步，创建一个webbook，对应一个Excel文件  
            HSSFWorkbook wb = new HSSFWorkbook();  
            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
            HSSFSheet sheet = wb.createSheet(titleName);  
            // sheet.setDefaultColumnWidth(15); //统一设置列宽  
            for (int i = 0; i < titleList.length; i++)   
            {  
                for (int j = 0; j <= i; j++)   
                {  
                    if (i == j)   
                    {  
                        sheet.setColumnWidth(i, 20 * 256); // 单独设置每列的宽  
                    }  
                }  
            }  
            // 创建第0行 也就是标题  
            HSSFRow row1 = sheet.createRow((int) 0);  
            row1.setHeightInPoints(50);// 设备标题的高度  
            // 第三步创建标题的单元格样式style2以及字体样式headerFont1  
            HSSFCellStyle style2 = wb.createCellStyle();  
            style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
            style2.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);  
            style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
            HSSFFont headerFont1 = (HSSFFont) wb.createFont(); // 创建字体样式  
            headerFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
            headerFont1.setFontName("黑体"); // 设置字体类型  
            headerFont1.setFontHeightInPoints((short) 15); // 设置字体大小  
            style2.setFont(headerFont1); // 为标题样式设置字体样式  
  
            HSSFCell cell1 = row1.createCell(0);// 创建标题第一列  
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,  
            		titleList.length - 1)); // 合并列标题  
            cell1.setCellValue(titleName); // 设置值标题  
            cell1.setCellStyle(style2); // 设置标题样式  
  
            // 创建第1行 也就是表头  
            HSSFRow row = sheet.createRow((int) 1);  
            row.setHeightInPoints(37);// 设置表头高度  
  
            // 第四步，创建表头单元格样式 以及表头的字体样式  
            HSSFCellStyle style = wb.createCellStyle();  
            style.setWrapText(true);// 设置自动换行  
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式  
  
            style.setBottomBorderColor(HSSFColor.BLACK.index);  
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
  
            HSSFFont headerFont = (HSSFFont) wb.createFont(); // 创建字体样式  
            headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
            headerFont.setFontName("黑体"); // 设置字体类型  
            headerFont.setFontHeightInPoints((short) 10); // 设置字体大小  
            style.setFont(headerFont); // 为标题样式设置字体样式  
  
            // 第四.一步，创建表头的列  
            for (int i = 0; i < titleList.length; i++)   
            {  
                HSSFCell cell = row.createCell(i);  
                cell.setCellValue(titleList[i]);  
                cell.setCellStyle(style);  
            }  
  
            /*第五步*/
            // 为数据内容设置特点新单元格样式2 自动换行 上下居中左右也居中  
            HSSFCellStyle zidonghuanhang2 = wb.createCellStyle();  
            zidonghuanhang2.setWrapText(true);// 设置自动换行  
            zidonghuanhang2  
                    .setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个上下居中格式  
            zidonghuanhang2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中  

            // 设置边框  
            zidonghuanhang2.setBottomBorderColor(HSSFColor.BLACK.index);  
            zidonghuanhang2.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderTop(HSSFCellStyle.BORDER_THIN);  
            
            
            // 第六步，创建单元格，并设置值  
            HSSFCell datacell = null;
            sheet.addMergedRegion(new CellRangeAddress(2, 5, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(5, 5, 1, 2));
            sheet.addMergedRegion(new CellRangeAddress(6, 9, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(9, 9, 1, 2));
            sheet.addMergedRegion(new CellRangeAddress(10, 10, 1, 2));
            //第二行
            row = sheet.createRow(2);  
            datacell = row.createCell(0);  
            datacell.setCellValue("上行");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(1);  
            datacell.setCellValue("1");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("停车道");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(3);  
            datacell.setCellValue(no.getNumOvlo1_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(no.getNumOvlo1_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(no.getNumOvlo1_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(no.getNumOvlo1_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(no.getNumOvlo1_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(no.getNumOvlo1_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(no.getNumOvloLane1());  
            datacell.setCellStyle(zidonghuanhang2); 
          //第三行
            row = sheet.createRow(3); 
            datacell = row.createCell(1);  
            datacell.setCellValue("2");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道2");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(3);  
            datacell.setCellValue(no.getNumOvlo2_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(no.getNumOvlo2_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(no.getNumOvlo2_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(no.getNumOvlo2_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(no.getNumOvlo2_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(no.getNumOvlo2_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(no.getNumOvloLane2());  
            datacell.setCellStyle(zidonghuanhang2); 
          //第四行
            row = sheet.createRow(4); 
            datacell = row.createCell(1);  
            datacell.setCellValue("3");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道1");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(3);  
            datacell.setCellValue(no.getNumOvlo3_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(no.getNumOvlo3_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(no.getNumOvlo3_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(no.getNumOvlo3_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(no.getNumOvlo3_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(no.getNumOvlo3_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(no.getNumOvloLane3());  
            datacell.setCellStyle(zidonghuanhang2); 
          //第五行
            row = sheet.createRow(5); 
            datacell = row.createCell(1);  
            datacell.setCellValue("小计");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(no.getNumOvloHax1_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(no.getNumOvloHax1_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(no.getNumOvloHax1_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(no.getNumOvloHax1_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(no.getNumOvloHax1_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(no.getNumOvloHax1_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(no.getNumOvloHalf1());  
            datacell.setCellStyle(zidonghuanhang2); 
          //第六行
            row = sheet.createRow(6); 
            datacell = row.createCell(0);  
            datacell.setCellValue("下行");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(1);  
            datacell.setCellValue("4");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道1");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(no.getNumOvlo4_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(no.getNumOvlo4_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(no.getNumOvlo4_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(no.getNumOvlo4_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(no.getNumOvlo4_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(no.getNumOvlo4_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(no.getNumOvloLane4());  
            datacell.setCellStyle(zidonghuanhang2); 
          //第七行
            row = sheet.createRow(7); 
            datacell = row.createCell(1);  
            datacell.setCellValue("5");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道2");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(no.getNumOvlo5_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(no.getNumOvlo5_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(no.getNumOvlo5_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(no.getNumOvlo5_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(no.getNumOvlo5_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(no.getNumOvlo5_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(no.getNumOvloLane5());  
            datacell.setCellStyle(zidonghuanhang2); 
          //第八行
            row = sheet.createRow(8); 
            datacell = row.createCell(1);  
            datacell.setCellValue("6");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("停车道");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(no.getNumOvlo6_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(no.getNumOvlo6_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(no.getNumOvlo6_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(no.getNumOvlo6_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(no.getNumOvlo6_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(no.getNumOvlo6_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(no.getNumOvloLane6());  
            datacell.setCellStyle(zidonghuanhang2);
          //第九行
            row = sheet.createRow(9); 
            datacell = row.createCell(1);  
            datacell.setCellValue("小计");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(no.getNumOvloHax2_1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(no.getNumOvloHax2_2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(no.getNumOvloHax2_3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(no.getNumOvloHax2_4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(no.getNumOvloHax2_5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(no.getNumOvloHax2_6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(no.getNumOvloHalf2());  
            datacell.setCellStyle(zidonghuanhang2);
          //第十行
            row = sheet.createRow(10); 
            datacell = row.createCell(0);  
            datacell.setCellValue("");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(1);  
            datacell.setCellValue("合计");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(no.getNumOvloAxle1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(no.getNumOvloAxle2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(no.getNumOvloAxle3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(no.getNumOvloAxle4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(no.getNumOvloAxle5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(no.getNumOvloAxle6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(no.getNumOvloAll());  
            datacell.setCellStyle(zidonghuanhang2);
  
            // 第七步，将文件存到浏览器设置的下载位置  
            String filename = fileName + ".xls";  
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-disposition", "attachment; filename="+new String((filename).getBytes("gb2312"), "ISO8859-1"));  
            OutputStream out = response.getOutputStream();  
            
            try { 
            	if (wb != null) {
            		wb.write(out);// 将数据写出去  
            	}
            } catch (Exception e) {  
                e.printStackTrace();  
                String str1 = "导出" + fileName + "失败！";  
                System.out.println(str1);  
            } finally {  
                out.close();  
            }  
  
  
    }  
	
	public void ExportWithResponse9(String titleName,  
            String fileName,String [] titleList,RatioOvlo ro,HttpServletResponse response) throws Exception {  
       
            // 第一步，创建一个webbook，对应一个Excel文件  
            HSSFWorkbook wb = new HSSFWorkbook();  
            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
            HSSFSheet sheet = wb.createSheet(titleName);  
            // sheet.setDefaultColumnWidth(15); //统一设置列宽  
            for (int i = 0; i < titleList.length; i++)   
            {  
                for (int j = 0; j <= i; j++)   
                {  
                    if (i == j)   
                    {  
                        sheet.setColumnWidth(i, 20 * 256); // 单独设置每列的宽  
                    }  
                }  
            }  
            // 创建第0行 也就是标题  
            HSSFRow row1 = sheet.createRow((int) 0);  
            row1.setHeightInPoints(50);// 设备标题的高度  
            // 第三步创建标题的单元格样式style2以及字体样式headerFont1  
            HSSFCellStyle style2 = wb.createCellStyle();  
            style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
            style2.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);  
            style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
            HSSFFont headerFont1 = (HSSFFont) wb.createFont(); // 创建字体样式  
            headerFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
            headerFont1.setFontName("黑体"); // 设置字体类型  
            headerFont1.setFontHeightInPoints((short) 15); // 设置字体大小  
            style2.setFont(headerFont1); // 为标题样式设置字体样式  
  
            HSSFCell cell1 = row1.createCell(0);// 创建标题第一列  
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,  
            		titleList.length - 1)); // 合并列标题  
            cell1.setCellValue(titleName); // 设置值标题  
            cell1.setCellStyle(style2); // 设置标题样式  
  
            // 创建第1行 也就是表头  
            HSSFRow row = sheet.createRow((int) 1);  
            row.setHeightInPoints(37);// 设置表头高度  
  
            // 第四步，创建表头单元格样式 以及表头的字体样式  
            HSSFCellStyle style = wb.createCellStyle();  
            style.setWrapText(true);// 设置自动换行  
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式  
  
            style.setBottomBorderColor(HSSFColor.BLACK.index);  
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
  
            HSSFFont headerFont = (HSSFFont) wb.createFont(); // 创建字体样式  
            headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
            headerFont.setFontName("黑体"); // 设置字体类型  
            headerFont.setFontHeightInPoints((short) 10); // 设置字体大小  
            style.setFont(headerFont); // 为标题样式设置字体样式  
  
            // 第四.一步，创建表头的列  
            for (int i = 0; i < titleList.length; i++)   
            {  
                HSSFCell cell = row.createCell(i);  
                cell.setCellValue(titleList[i]);  
                cell.setCellStyle(style);  
            }  
  
            /*第五步*/
            // 为数据内容设置特点新单元格样式2 自动换行 上下居中左右也居中  
            HSSFCellStyle zidonghuanhang2 = wb.createCellStyle();  
            zidonghuanhang2.setWrapText(true);// 设置自动换行  
            zidonghuanhang2  
                    .setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个上下居中格式  
            zidonghuanhang2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中  

            // 设置边框  
            zidonghuanhang2.setBottomBorderColor(HSSFColor.BLACK.index);  
            zidonghuanhang2.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderTop(HSSFCellStyle.BORDER_THIN);  
            
            
            // 第六步，创建单元格，并设置值  
            HSSFCell datacell = null;
            sheet.addMergedRegion(new CellRangeAddress(2, 5, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(5, 5, 1, 2));
            sheet.addMergedRegion(new CellRangeAddress(6, 9, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(9, 9, 1, 2));
            sheet.addMergedRegion(new CellRangeAddress(10, 10, 1, 2));
            //第二行
            row = sheet.createRow(2);  
            datacell = row.createCell(0);  
            datacell.setCellValue("上行");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(1);  
            datacell.setCellValue("1");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("停车道");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvlo1_1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvlo1_2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvlo1_3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvlo1_4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvlo1_5()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvlo1_6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvloLane1()));  
            datacell.setCellStyle(zidonghuanhang2); 
          //第三行
            row = sheet.createRow(3); 
            datacell = row.createCell(1);  
            datacell.setCellValue("2");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道2");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvlo2_1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvlo2_2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvlo2_3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvlo2_4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvlo2_5()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvlo2_6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvloLane2()));  
            datacell.setCellStyle(zidonghuanhang2); 
          //第四行
            row = sheet.createRow(4); 
            datacell = row.createCell(1);  
            datacell.setCellValue("3");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道1");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvlo3_1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvlo3_2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvlo3_3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvlo3_4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvlo3_5()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvlo3_6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvloLane3()));  
            datacell.setCellStyle(zidonghuanhang2); 
          //第五行
            row = sheet.createRow(5); 
            datacell = row.createCell(1);  
            datacell.setCellValue("小计");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvloHax1_1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvloHax1_2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvloHax1_3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvloHax1_4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvloHax1_5()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvloHax1_6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvloHalf1()));  
            datacell.setCellStyle(zidonghuanhang2); 
          //第六行
            row = sheet.createRow(6); 
            datacell = row.createCell(0);  
            datacell.setCellValue("下行");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(1);  
            datacell.setCellValue("4");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道1");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvlo4_1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvlo4_2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvlo4_3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvlo4_4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvlo4_5()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvlo4_6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvloLane4()));  
            datacell.setCellStyle(zidonghuanhang2); 
          //第七行
            row = sheet.createRow(7); 
            datacell = row.createCell(1);  
            datacell.setCellValue("5");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道2");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvlo5_1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvlo5_2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvlo5_3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvlo5_4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvlo5_5()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvlo5_6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvloLane5()));  
            datacell.setCellStyle(zidonghuanhang2); 
          //第八行
            row = sheet.createRow(8); 
            datacell = row.createCell(1);  
            datacell.setCellValue("6");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("停车道");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvlo6_1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvlo6_2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvlo6_3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvlo6_4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvlo6_5()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvlo6_6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvloLane6()));  
            datacell.setCellStyle(zidonghuanhang2);
          //第九行
            row = sheet.createRow(9); 
            datacell = row.createCell(1);  
            datacell.setCellValue("小计");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvloHax2_1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvloHax2_2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvloHax2_3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvloHax2_4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvloHax2_4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvloHax2_6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvloHalf2()));  
            datacell.setCellStyle(zidonghuanhang2);
          //第十行
            row = sheet.createRow(10); 
            datacell = row.createCell(0);  
            datacell.setCellValue("");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(1);  
            datacell.setCellValue("合计");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvloAxle1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvloAxle2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvloAxle3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvloAxle4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvloAxle5()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvloAxle6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(ro.getRatioOvloAll()));  
            datacell.setCellStyle(zidonghuanhang2);
  
            // 第七步，将文件存到浏览器设置的下载位置  
            String filename = fileName + ".xls";  
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-disposition", "attachment; filename="+new String((filename).getBytes("gb2312"), "ISO8859-1"));  
            OutputStream out = response.getOutputStream();  
            
            try { 
            	if (wb != null) {
            		wb.write(out);// 将数据写出去  
            	}
            } catch (Exception e) {  
                e.printStackTrace();  
                String str1 = "导出" + fileName + "失败！";  
                System.out.println(str1);  
            } finally {  
                out.close();  
            }  
  
  
    }  
	public void ExportWithResponse10(String titleName,  
            String fileName,String [] titleList,ProbOvlo po,HttpServletResponse response) throws Exception {  
       
            // 第一步，创建一个webbook，对应一个Excel文件  
            HSSFWorkbook wb = new HSSFWorkbook();  
            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
            HSSFSheet sheet = wb.createSheet(titleName);  
            // sheet.setDefaultColumnWidth(15); //统一设置列宽  
            for (int i = 0; i < titleList.length; i++)   
            {  
                for (int j = 0; j <= i; j++)   
                {  
                    if (i == j)   
                    {  
                        sheet.setColumnWidth(i, 20 * 256); // 单独设置每列的宽  
                    }  
                }  
            }  
            // 创建第0行 也就是标题  
            HSSFRow row1 = sheet.createRow((int) 0);  
            row1.setHeightInPoints(50);// 设备标题的高度  
            // 第三步创建标题的单元格样式style2以及字体样式headerFont1  
            HSSFCellStyle style2 = wb.createCellStyle();  
            style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
            style2.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);  
            style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
            HSSFFont headerFont1 = (HSSFFont) wb.createFont(); // 创建字体样式  
            headerFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
            headerFont1.setFontName("黑体"); // 设置字体类型  
            headerFont1.setFontHeightInPoints((short) 15); // 设置字体大小  
            style2.setFont(headerFont1); // 为标题样式设置字体样式  
  
            HSSFCell cell1 = row1.createCell(0);// 创建标题第一列  
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,  
            		titleList.length - 1)); // 合并列标题  
            cell1.setCellValue(titleName); // 设置值标题  
            cell1.setCellStyle(style2); // 设置标题样式  
  
            // 创建第1行 也就是表头  
            HSSFRow row = sheet.createRow((int) 1);  
            row.setHeightInPoints(37);// 设置表头高度  
  
            // 第四步，创建表头单元格样式 以及表头的字体样式  
            HSSFCellStyle style = wb.createCellStyle();  
            style.setWrapText(true);// 设置自动换行  
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式  
  
            style.setBottomBorderColor(HSSFColor.BLACK.index);  
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
  
            HSSFFont headerFont = (HSSFFont) wb.createFont(); // 创建字体样式  
            headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
            headerFont.setFontName("黑体"); // 设置字体类型  
            headerFont.setFontHeightInPoints((short) 10); // 设置字体大小  
            style.setFont(headerFont); // 为标题样式设置字体样式  
  
            // 第四.一步，创建表头的列  
            for (int i = 0; i < titleList.length; i++)   
            {  
                HSSFCell cell = row.createCell(i);  
                cell.setCellValue(titleList[i]);  
                cell.setCellStyle(style);  
            }  
  
            /*第五步*/
            // 为数据内容设置特点新单元格样式2 自动换行 上下居中左右也居中  
            HSSFCellStyle zidonghuanhang2 = wb.createCellStyle();  
            zidonghuanhang2.setWrapText(true);// 设置自动换行  
            zidonghuanhang2  
                    .setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个上下居中格式  
            zidonghuanhang2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中  

            // 设置边框  
            zidonghuanhang2.setBottomBorderColor(HSSFColor.BLACK.index);  
            zidonghuanhang2.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderTop(HSSFCellStyle.BORDER_THIN);  
            
            
            // 第六步，创建单元格，并设置值  
            HSSFCell datacell = null;
            sheet.addMergedRegion(new CellRangeAddress(2, 5, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(5, 5, 1, 2));
            sheet.addMergedRegion(new CellRangeAddress(6, 9, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(9, 9, 1, 2));
            sheet.addMergedRegion(new CellRangeAddress(10, 10, 1, 2));
            //第二行
            row = sheet.createRow(2);  
            datacell = row.createCell(0);  
            datacell.setCellValue("上行");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(1);  
            datacell.setCellValue("1");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("停车道");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvlo1_1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvlo1_2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvlo1_3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvlo1_4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvlo1_5()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvlo1_6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvloLane1()));  
            datacell.setCellStyle(zidonghuanhang2); 
          //第三行
            row = sheet.createRow(3); 
            datacell = row.createCell(1);  
            datacell.setCellValue("2");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道2");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvlo2_1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvlo2_2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvlo2_3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvlo2_4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvlo2_5()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvlo2_6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvloLane2()));  
            datacell.setCellStyle(zidonghuanhang2); 
          //第四行
            row = sheet.createRow(4); 
            datacell = row.createCell(1);  
            datacell.setCellValue("3");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道1");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvlo3_1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvlo3_2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvlo3_3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvlo3_4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvlo3_5()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvlo3_6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvloLane3()));  
            datacell.setCellStyle(zidonghuanhang2); 
          //第五行
            row = sheet.createRow(5); 
            datacell = row.createCell(1);  
            datacell.setCellValue("小计");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvloHax1_1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvloHax1_2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvloHax1_3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvloHax1_4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvloHax1_5()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvloHax1_6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvloHalf1()));  
            datacell.setCellStyle(zidonghuanhang2); 
          //第六行
            row = sheet.createRow(6); 
            datacell = row.createCell(0);  
            datacell.setCellValue("下行");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(1);  
            datacell.setCellValue("4");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道1");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvlo4_1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvlo4_2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvlo4_3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvlo4_4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvlo4_5()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvlo4_6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvloLane4()));  
            datacell.setCellStyle(zidonghuanhang2); 
          //第七行
            row = sheet.createRow(7); 
            datacell = row.createCell(1);  
            datacell.setCellValue("5");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("车道2");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvlo5_1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvlo5_2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvlo5_3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvlo5_4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvlo5_5()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvlo5_6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvloLane5()));  
            datacell.setCellStyle(zidonghuanhang2); 
          //第八行
            row = sheet.createRow(8); 
            datacell = row.createCell(1);  
            datacell.setCellValue("6");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(2);  
            datacell.setCellValue("停车道");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvlo6_1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvlo6_2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvlo6_3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvlo6_4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvlo6_5()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvlo6_6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvloLane6()));  
            datacell.setCellStyle(zidonghuanhang2);
          //第九行
            row = sheet.createRow(9); 
            datacell = row.createCell(1);  
            datacell.setCellValue("小计");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvloHax2_1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvloHax2_2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvloHax2_3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvloHax2_4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvloHax2_5()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvloHax2_6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvloHalf2()));  
            datacell.setCellStyle(zidonghuanhang2);
          //第十行
            row = sheet.createRow(10); 
            datacell = row.createCell(0);  
            datacell.setCellValue("");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(1);  
            datacell.setCellValue("合计");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(3);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvloAxle1()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvloAxle2()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvloAxle3()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvloAxle4()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvloAxle5()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvloAxle6()));  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(getBaiFenBi(po.getProbOvloAll()));  
            datacell.setCellStyle(zidonghuanhang2);
  
            // 第七步，将文件存到浏览器设置的下载位置  
            String filename = fileName + ".xls";  
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-disposition", "attachment; filename="+new String((filename).getBytes("gb2312"), "ISO8859-1"));  
            OutputStream out = response.getOutputStream();  
            
            try { 
            	if (wb != null) {
            		wb.write(out);// 将数据写出去  
            	}
            } catch (Exception e) {  
                e.printStackTrace();  
                String str1 = "导出" + fileName + "失败！";  
                System.out.println(str1);  
            } finally {  
                out.close();  
            }  
  
  
    }  
	
	
	public void ExportWithResponse11(String titleName,  
            String fileName,BrgWeightLoadRatio ratio,HttpServletResponse response) throws Exception {  
       
            // 第一步，创建一个webbook，对应一个Excel文件  
            HSSFWorkbook wb = new HSSFWorkbook();  
            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
            HSSFSheet sheet = wb.createSheet(titleName);  
            sheet.setDefaultColumnWidth(15); //统一设置列宽  
            // 创建第0行 也就是标题  
            HSSFRow row1 = sheet.createRow((int) 0);  
            row1.setHeightInPoints(50);// 设备标题的高度  
            // 第三步创建标题的单元格样式style2以及字体样式headerFont1  
            HSSFCellStyle style2 = wb.createCellStyle();  
            style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
            style2.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);  
            style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
            HSSFFont headerFont1 = (HSSFFont) wb.createFont(); // 创建字体样式  
            headerFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
            headerFont1.setFontName("黑体"); // 设置字体类型  
            headerFont1.setFontHeightInPoints((short) 15); // 设置字体大小  
            style2.setFont(headerFont1); // 为标题样式设置字体样式  
  
            HSSFCell cell1 = row1.createCell(0);// 创建标题第一列  
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,  
            		10)); // 合并列标题  
            cell1.setCellValue(titleName); // 设置值标题  
            cell1.setCellStyle(style2); // 设置标题样式  
  
            // 创建第1行 也就是表头  
            HSSFRow row = sheet.createRow((int) 1);  
            row.setHeightInPoints(37);// 设置表头高度  
  
            // 第四步，创建表头单元格样式 以及表头的字体样式  
            HSSFCellStyle style = wb.createCellStyle();  
            style.setWrapText(true);// 设置自动换行  
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式  
  
            style.setBottomBorderColor(HSSFColor.BLACK.index);  
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
  
            HSSFFont headerFont = (HSSFFont) wb.createFont(); // 创建字体样式  
            headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
            headerFont.setFontName("黑体"); // 设置字体类型  
            headerFont.setFontHeightInPoints((short) 10); // 设置字体大小  
            style.setFont(headerFont); // 为标题样式设置字体样式  
  
           
                
  
            /*第五步*/
            // 为数据内容设置特点新单元格样式2 自动换行 上下居中左右也居中  
            HSSFCellStyle zidonghuanhang2 = wb.createCellStyle();  
            zidonghuanhang2.setWrapText(true);// 设置自动换行  
            zidonghuanhang2  
                    .setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个上下居中格式  
            zidonghuanhang2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中  

            // 设置边框  
            zidonghuanhang2.setBottomBorderColor(HSSFColor.BLACK.index);  
            zidonghuanhang2.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderTop(HSSFCellStyle.BORDER_THIN);  
            
            
            // 第六步，创建单元格，并设置值  
            HSSFCell datacell = null;
            sheet.addMergedRegion(new CellRangeAddress(1, 2, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(1, 2, 1, 1));
            sheet.addMergedRegion(new CellRangeAddress(1, 2, 2, 2));
            sheet.addMergedRegion(new CellRangeAddress(1, 2, 9, 9));
            sheet.addMergedRegion(new CellRangeAddress(1, 2, 10, 10));
            
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 3, 5));
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 6, 8));
            
            sheet.addMergedRegion(new CellRangeAddress(3, 6, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(3, 6, 3, 3));
            sheet.addMergedRegion(new CellRangeAddress(3, 6, 4, 4));
            sheet.addMergedRegion(new CellRangeAddress(3, 6, 5, 5));
            sheet.addMergedRegion(new CellRangeAddress(3, 6, 6, 6));
            sheet.addMergedRegion(new CellRangeAddress(3, 6, 7, 7));
            sheet.addMergedRegion(new CellRangeAddress(3, 6, 8, 8));
            sheet.addMergedRegion(new CellRangeAddress(3, 6, 9, 9));
            sheet.addMergedRegion(new CellRangeAddress(3, 6, 10, 10));
            
            sheet.addMergedRegion(new CellRangeAddress(7, 10, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(7, 10, 3, 3));
            sheet.addMergedRegion(new CellRangeAddress(7, 10, 4, 4));
            sheet.addMergedRegion(new CellRangeAddress(7, 10, 5, 5));
            sheet.addMergedRegion(new CellRangeAddress(7, 10, 6, 6));
            sheet.addMergedRegion(new CellRangeAddress(7, 10, 7, 7));
            sheet.addMergedRegion(new CellRangeAddress(7, 10, 8, 8));
            sheet.addMergedRegion(new CellRangeAddress(7, 10, 9, 9));
            sheet.addMergedRegion(new CellRangeAddress(7, 10, 10, 10));
            
            
            // 第一行，创建表头的列  
                row = sheet.createRow(1);  
                datacell = row.createCell(0);  
                datacell.setCellValue("方向");  
                datacell.setCellStyle(style); 
                datacell = row.createCell(1);  
                datacell.setCellValue("车道编号");  
                datacell.setCellStyle(style);
                datacell = row.createCell(2);  
                datacell.setCellValue("车道名");  
                datacell.setCellStyle(style); 
                datacell = row.createCell(3);  
                datacell.setCellValue("实际荷载效应/设计荷载效应");  
                datacell.setCellStyle(style); 
                datacell = row.createCell(6);  
                datacell.setCellValue("实际荷载效应/规范荷载效应");  
                datacell.setCellStyle(style); 
                datacell = row.createCell(9);  
                datacell.setCellValue("荷载状态");  
                datacell.setCellStyle(style); 
                datacell = row.createCell(10);  
                datacell.setCellValue("车道状态");  
                datacell.setCellStyle(style); 
            
            //第二行
            row = sheet.createRow(2);  
            datacell = row.createCell(3);  
            datacell.setCellValue("标准值");  
            datacell.setCellStyle(style); 
            datacell = row.createCell(4);  
            datacell.setCellValue("最大值");  
            datacell.setCellStyle(style); 
            datacell = row.createCell(5);  
            datacell.setCellValue("0.95分位值");  
            datacell.setCellStyle(style); 
            datacell = row.createCell(6);  
            datacell.setCellValue("标准值");  
            datacell.setCellStyle(style); 
            datacell = row.createCell(7);  
            datacell.setCellValue("最大值");  
            datacell.setCellStyle(style); 
            datacell = row.createCell(8);  
            datacell.setCellValue("0.95分位值");  
            datacell.setCellStyle(style); 
          //第三行
            row = sheet.createRow(3);  
            datacell = row.createCell(0);  
            datacell.setCellValue("上行");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(1);  
            datacell.setCellValue("1");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(2);  
            datacell.setCellValue("停车道");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(3);  
            datacell.setCellValue(ratio.getLoad_radio1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(ratio.getLoad_radio2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(ratio.getLoad_radio3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(ratio.getLoad_radio4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(ratio.getLoad_radio5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(ratio.getLoad_radio6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(ratio.getLoad_radio7());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(10);  
            datacell.setCellValue(ratio.getLoad_radio8());  
            datacell.setCellStyle(zidonghuanhang2); 
            //第四行
            row = sheet.createRow(4);
            datacell = row.createCell(1);  
            datacell.setCellValue("2");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(2);  
            datacell.setCellValue("车道3");  
            datacell.setCellStyle(zidonghuanhang2); 
          //第五行
            row = sheet.createRow(5);
            datacell = row.createCell(1);  
            datacell.setCellValue("3");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(2);  
            datacell.setCellValue("车道2");  
            datacell.setCellStyle(zidonghuanhang2); 
          //第六行
            row = sheet.createRow(6);
            datacell = row.createCell(1);  
            datacell.setCellValue("4");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(2);  
            datacell.setCellValue("车道1");  
            datacell.setCellStyle(zidonghuanhang2); 
          //第七行
            row = sheet.createRow(7);
            datacell = row.createCell(0);  
            datacell.setCellValue("下行");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(1);  
            datacell.setCellValue("5");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(2);  
            datacell.setCellValue("车道1");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(3);  
            datacell.setCellValue(ratio.getLoad_radio9());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(ratio.getLoad_radio10());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(ratio.getLoad_radio11());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(ratio.getLoad_radio12());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(ratio.getLoad_radio13());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(ratio.getLoad_radio14());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(ratio.getLoad_radio15());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(10);  
            datacell.setCellValue(ratio.getLoad_radio16());  
            datacell.setCellStyle(zidonghuanhang2); 
          //第八行
            row = sheet.createRow(8);
            datacell = row.createCell(1);  
            datacell.setCellValue("6");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(2);  
            datacell.setCellValue("车道2");  
            datacell.setCellStyle(zidonghuanhang2);
          //第九行
            row = sheet.createRow(9);
            datacell = row.createCell(1);  
            datacell.setCellValue("7");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(2);  
            datacell.setCellValue("车道3");  
            datacell.setCellStyle(zidonghuanhang2);
          //第十行
            row = sheet.createRow(10);
            datacell = row.createCell(1);  
            datacell.setCellValue("8");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(2);  
            datacell.setCellValue("停车道");  
            datacell.setCellStyle(zidonghuanhang2);
  
            // 第七步，将文件存到浏览器设置的下载位置  
            String filename = fileName + ".xls";  
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-disposition", "attachment; filename="+new String((filename).getBytes("gb2312"), "ISO8859-1"));  
            OutputStream out = response.getOutputStream();  
            
            try { 
            	if (wb != null) {
            		wb.write(out);// 将数据写出去  
            	}
            } catch (Exception e) {  
                e.printStackTrace();  
                String str1 = "导出" + fileName + "失败！";  
                System.out.println(str1);  
            } finally {  
                out.close();  
            }  
  
  
    }  
	
	public void ExportWithResponse12(String titleName,  
            String fileName,BrgWeightLoadRatio ratio,HttpServletResponse response) throws Exception {  
       
            // 第一步，创建一个webbook，对应一个Excel文件  
            HSSFWorkbook wb = new HSSFWorkbook();  
            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
            HSSFSheet sheet = wb.createSheet(titleName);  
            sheet.setDefaultColumnWidth(15); //统一设置列宽  
            // 创建第0行 也就是标题  
            HSSFRow row1 = sheet.createRow((int) 0);  
            row1.setHeightInPoints(50);// 设备标题的高度  
            // 第三步创建标题的单元格样式style2以及字体样式headerFont1  
            HSSFCellStyle style2 = wb.createCellStyle();  
            style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
            style2.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);  
            style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
            HSSFFont headerFont1 = (HSSFFont) wb.createFont(); // 创建字体样式  
            headerFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
            headerFont1.setFontName("黑体"); // 设置字体类型  
            headerFont1.setFontHeightInPoints((short) 15); // 设置字体大小  
            style2.setFont(headerFont1); // 为标题样式设置字体样式  
  
            HSSFCell cell1 = row1.createCell(0);// 创建标题第一列  
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,  
            		10)); // 合并列标题  
            cell1.setCellValue(titleName); // 设置值标题  
            cell1.setCellStyle(style2); // 设置标题样式  
  
            // 创建第1行 也就是表头  
            HSSFRow row = sheet.createRow((int) 1);  
            row.setHeightInPoints(37);// 设置表头高度  
  
            // 第四步，创建表头单元格样式 以及表头的字体样式  
            HSSFCellStyle style = wb.createCellStyle();  
            style.setWrapText(true);// 设置自动换行  
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式  
  
            style.setBottomBorderColor(HSSFColor.BLACK.index);  
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
  
            HSSFFont headerFont = (HSSFFont) wb.createFont(); // 创建字体样式  
            headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
            headerFont.setFontName("黑体"); // 设置字体类型  
            headerFont.setFontHeightInPoints((short) 10); // 设置字体大小  
            style.setFont(headerFont); // 为标题样式设置字体样式  
  
           
                
  
            /*第五步*/
            // 为数据内容设置特点新单元格样式2 自动换行 上下居中左右也居中  
            HSSFCellStyle zidonghuanhang2 = wb.createCellStyle();  
            zidonghuanhang2.setWrapText(true);// 设置自动换行  
            zidonghuanhang2  
                    .setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个上下居中格式  
            zidonghuanhang2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中  

            // 设置边框  
            zidonghuanhang2.setBottomBorderColor(HSSFColor.BLACK.index);  
            zidonghuanhang2.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderTop(HSSFCellStyle.BORDER_THIN);  
            
            
            // 第六步，创建单元格，并设置值  
            HSSFCell datacell = null;
            sheet.addMergedRegion(new CellRangeAddress(1, 2, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(1, 2, 1, 1));
            sheet.addMergedRegion(new CellRangeAddress(1, 2, 2, 2));
            sheet.addMergedRegion(new CellRangeAddress(1, 2, 9, 9));
            sheet.addMergedRegion(new CellRangeAddress(1, 2, 10, 10));
            
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 3, 5));
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 6, 8));
            
            sheet.addMergedRegion(new CellRangeAddress(3, 5, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(3, 5, 3, 3));
            sheet.addMergedRegion(new CellRangeAddress(3, 5, 4, 4));
            sheet.addMergedRegion(new CellRangeAddress(3, 5, 5, 5));
            sheet.addMergedRegion(new CellRangeAddress(3, 5, 6, 6));
            sheet.addMergedRegion(new CellRangeAddress(3, 5, 7, 7));
            sheet.addMergedRegion(new CellRangeAddress(3, 5, 8, 8));
            sheet.addMergedRegion(new CellRangeAddress(3, 5, 9, 9));
            sheet.addMergedRegion(new CellRangeAddress(3, 5, 10, 10));
            
            sheet.addMergedRegion(new CellRangeAddress(6, 8, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(6, 8, 3, 3));
            sheet.addMergedRegion(new CellRangeAddress(6, 8, 4, 4));
            sheet.addMergedRegion(new CellRangeAddress(6, 8, 5, 5));
            sheet.addMergedRegion(new CellRangeAddress(6, 8, 6, 6));
            sheet.addMergedRegion(new CellRangeAddress(6, 8, 7, 7));
            sheet.addMergedRegion(new CellRangeAddress(6, 8, 8, 8));
            sheet.addMergedRegion(new CellRangeAddress(6, 8, 9, 9));
            sheet.addMergedRegion(new CellRangeAddress(6, 8, 10, 10));
            
            
            // 第一行，创建表头的列  
                row = sheet.createRow(1);  
                datacell = row.createCell(0);  
                datacell.setCellValue("方向");  
                datacell.setCellStyle(style); 
                datacell = row.createCell(1);  
                datacell.setCellValue("车道编号");  
                datacell.setCellStyle(style);
                datacell = row.createCell(2);  
                datacell.setCellValue("车道名");  
                datacell.setCellStyle(style); 
                datacell = row.createCell(3);  
                datacell.setCellValue("实际荷载效应/设计荷载效应");  
                datacell.setCellStyle(style); 
                datacell = row.createCell(6);  
                datacell.setCellValue("实际荷载效应/规范荷载效应");  
                datacell.setCellStyle(style); 
                datacell = row.createCell(9);  
                datacell.setCellValue("荷载状态");  
                datacell.setCellStyle(style); 
                datacell = row.createCell(10);  
                datacell.setCellValue("车道状态");  
                datacell.setCellStyle(style); 
            
            //第二行
            row = sheet.createRow(2);  
            datacell = row.createCell(3);  
            datacell.setCellValue("标准值");  
            datacell.setCellStyle(style); 
            datacell = row.createCell(4);  
            datacell.setCellValue("最大值");  
            datacell.setCellStyle(style); 
            datacell = row.createCell(5);  
            datacell.setCellValue("0.95分位值");  
            datacell.setCellStyle(style); 
            datacell = row.createCell(6);  
            datacell.setCellValue("标准值");  
            datacell.setCellStyle(style); 
            datacell = row.createCell(7);  
            datacell.setCellValue("最大值");  
            datacell.setCellStyle(style); 
            datacell = row.createCell(8);  
            datacell.setCellValue("0.95分位值");  
            datacell.setCellStyle(style); 
          //第三行
            row = sheet.createRow(3);  
            datacell = row.createCell(0);  
            datacell.setCellValue("上行");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(1);  
            datacell.setCellValue("1");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(2);  
            datacell.setCellValue("停车道");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(3);  
            datacell.setCellValue(ratio.getLoad_radio1());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(ratio.getLoad_radio2());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(ratio.getLoad_radio3());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(ratio.getLoad_radio4());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(ratio.getLoad_radio5());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(ratio.getLoad_radio6());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(ratio.getLoad_radio7());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(10);  
            datacell.setCellValue(ratio.getLoad_radio8());  
            datacell.setCellStyle(zidonghuanhang2); 
            //第四行
            row = sheet.createRow(4);
            datacell = row.createCell(1);  
            datacell.setCellValue("2");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(2);  
            datacell.setCellValue("车道2");  
            datacell.setCellStyle(zidonghuanhang2); 
          //第五行
            row = sheet.createRow(5);
            datacell = row.createCell(1);  
            datacell.setCellValue("3");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(2);  
            datacell.setCellValue("车道1");  
            datacell.setCellStyle(zidonghuanhang2); 
          //第六行
            row = sheet.createRow(6);
            datacell = row.createCell(0);  
            datacell.setCellValue("下行");  
            datacell.setCellStyle(zidonghuanhang2);
            datacell = row.createCell(1);  
            datacell.setCellValue("4");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(2);  
            datacell.setCellValue("车道1");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(3);  
            datacell.setCellValue(ratio.getLoad_radio9());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(4);  
            datacell.setCellValue(ratio.getLoad_radio10());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(5);  
            datacell.setCellValue(ratio.getLoad_radio11());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(6);  
            datacell.setCellValue(ratio.getLoad_radio12());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(7);  
            datacell.setCellValue(ratio.getLoad_radio13());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(8);  
            datacell.setCellValue(ratio.getLoad_radio14());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(9);  
            datacell.setCellValue(ratio.getLoad_radio15());  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(10);  
            datacell.setCellValue(ratio.getLoad_radio16());  
            datacell.setCellStyle(zidonghuanhang2); 
          //第七行
            row = sheet.createRow(7);
            datacell = row.createCell(1);  
            datacell.setCellValue("5");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(2);  
            datacell.setCellValue("车道2");  
            datacell.setCellStyle(zidonghuanhang2);
          //第八行
            row = sheet.createRow(8);
            datacell = row.createCell(1);  
            datacell.setCellValue("6");  
            datacell.setCellStyle(zidonghuanhang2); 
            datacell = row.createCell(2);  
            datacell.setCellValue("停车道");  
            datacell.setCellStyle(zidonghuanhang2);
  
            // 第七步，将文件存到浏览器设置的下载位置  
            String filename = fileName + ".xls";  
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-disposition", "attachment; filename="+new String((filename).getBytes("gb2312"), "ISO8859-1"));  
            OutputStream out = response.getOutputStream();  
            
            try { 
            	if (wb != null) {
            		wb.write(out);// 将数据写出去  
            	}
            } catch (Exception e) {  
                e.printStackTrace();  
                String str1 = "导出" + fileName + "失败！";  
                System.out.println(str1);  
            } finally {  
                out.close();  
            }  
  
  
    }  
	
	public void ExportHeight2(String titleName,String fileName,String [] titleList,List<BrgMonitorStrains> list,
			String[] chanelNums,String[] pointsNos,HttpServletResponse response) throws Exception {  
       
            // 第一步，创建一个webbook，对应一个Excel文件  
            HSSFWorkbook wb = new HSSFWorkbook();  
            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
            HSSFSheet sheet = wb.createSheet(titleName);  
            // sheet.setDefaultColumnWidth(15); //统一设置列宽  
            for (int i = 0; i < titleList.length; i++)   
            {  
                for (int j = 0; j <= i; j++)   
                {  
                    if (i == j)   
                    {  
                        sheet.setColumnWidth(i, 20 * 256); // 单独设置每列的宽  
                    }  
                }  
            }  
            // 创建第0行 也就是标题  
            HSSFRow row1 = sheet.createRow((int) 0);  
            row1.setHeightInPoints(50);// 设备标题的高度  
            // 第三步创建标题的单元格样式style2以及字体样式headerFont1  
            HSSFCellStyle style2 = wb.createCellStyle();  
            style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
            style2.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);  
            style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
            HSSFFont headerFont1 = (HSSFFont) wb.createFont(); // 创建字体样式  
            headerFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
            headerFont1.setFontName("黑体"); // 设置字体类型  
            headerFont1.setFontHeightInPoints((short) 15); // 设置字体大小  
            style2.setFont(headerFont1); // 为标题样式设置字体样式  
  
            HSSFCell cell1 = row1.createCell(0);// 创建标题第一列  
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,  
            		titleList.length - 1)); // 合并列标题  
            cell1.setCellValue(titleName); // 设置值标题  
            cell1.setCellStyle(style2); // 设置标题样式  
  
            // 创建第1行 也就是表头  
            HSSFRow row = sheet.createRow((int) 1);  
            row.setHeightInPoints(37);// 设置表头高度  
  
            // 第四步，创建表头单元格样式 以及表头的字体样式  
            HSSFCellStyle style = wb.createCellStyle();  
            style.setWrapText(true);// 设置自动换行  
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式  
  
            style.setBottomBorderColor(HSSFColor.BLACK.index);  
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
  
            HSSFFont headerFont = (HSSFFont) wb.createFont(); // 创建字体样式  
            headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
            headerFont.setFontName("黑体"); // 设置字体类型  
            headerFont.setFontHeightInPoints((short) 10); // 设置字体大小  
            style.setFont(headerFont); // 为标题样式设置字体样式  
  
            // 第四.一步，创建表头的列  
            for (int i = 0; i < titleList.length; i++)   
            {  
                HSSFCell cell = row.createCell(i);  
                cell.setCellValue(titleList[i]);  
                cell.setCellStyle(style);  
            }  
  
            /*第五步*/
         // 为数据内容设置特点新单元格样式1 自动换行 上下居中  
            HSSFCellStyle zidonghuanhang = wb.createCellStyle();  
            zidonghuanhang.setWrapText(true);// 设置自动换行  
            zidonghuanhang.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式  

            // 设置边框  
            zidonghuanhang.setBottomBorderColor(HSSFColor.BLACK.index);  
            zidonghuanhang.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang.setBorderTop(HSSFCellStyle.BORDER_THIN);  

            // 为数据内容设置特点新单元格样式2 自动换行 上下居中左右也居中  
            HSSFCellStyle zidonghuanhang2 = wb.createCellStyle();  
            zidonghuanhang2.setWrapText(true);// 设置自动换行  
            zidonghuanhang2  
                    .setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个上下居中格式  
            zidonghuanhang2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中  

            // 设置边框  
            zidonghuanhang2.setBottomBorderColor(HSSFColor.BLACK.index);  
            zidonghuanhang2.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderTop(HSSFCellStyle.BORDER_THIN);  
            
            
            // 第六步，创建单元格，并设置值  
            for (int i = 0; i < list.size(); i++)   {  
                row = sheet.createRow((int) i + 2);  
                HSSFCell datacell = null;  
                BrgMonitorStrains strains=list.get(i);
                if(pointsNos.length>1){
                	datacell = row.createCell(0);  
                    datacell.setCellValue(chanelNums[i]);  
                    datacell.setCellStyle(zidonghuanhang2);  
                    datacell = row.createCell(1);  
                    datacell.setCellValue(pointsNos[i]);  
                    datacell.setCellStyle(zidonghuanhang2);  
                    datacell = row.createCell(2);  
                    datacell.setCellValue(strains.getMax());  
                    datacell.setCellStyle(zidonghuanhang2); 
                    datacell = row.createCell(3);  
                    datacell.setCellValue(strains.getTantile_95());  
                    datacell.setCellStyle(zidonghuanhang2); 
                    datacell = row.createCell(4);  
                    datacell.setCellValue(strains.getMin());  
                    datacell.setCellStyle(zidonghuanhang2); 
                    datacell = row.createCell(5);  
                    datacell.setCellValue(strains.getTantile_5());  
                    datacell.setCellStyle(zidonghuanhang2); 
                    datacell = row.createCell(6);  
                    datacell.setCellValue(strains.getAvg());  
                    datacell.setCellStyle(zidonghuanhang2); 
                    datacell = row.createCell(7);  
                    datacell.setCellValue(strains.getVariance());  
                    datacell.setCellStyle(zidonghuanhang2); 
                    datacell = row.createCell(8);  
                    datacell.setCellValue(strains.getType());  
                    datacell.setCellStyle(zidonghuanhang2); 
                }else{
                	datacell = row.createCell(0);  
                    datacell.setCellValue(chanelNums[i]);  
                    datacell.setCellStyle(zidonghuanhang2);  
                    datacell = row.createCell(1);  
                    datacell.setCellValue(strains.getMax());  
                    datacell.setCellStyle(zidonghuanhang2); 
                    datacell = row.createCell(2);  
                    datacell.setCellValue(strains.getTantile_95());  
                    datacell.setCellStyle(zidonghuanhang2); 
                    datacell = row.createCell(3);  
                    datacell.setCellValue(strains.getMin());  
                    datacell.setCellStyle(zidonghuanhang2); 
                    datacell = row.createCell(4);  
                    datacell.setCellValue(strains.getTantile_5());  
                    datacell.setCellStyle(zidonghuanhang2); 
                    datacell = row.createCell(5);  
                    datacell.setCellValue(strains.getAvg());  
                    datacell.setCellStyle(zidonghuanhang2); 
                    datacell = row.createCell(6);  
                    datacell.setCellValue(strains.getVariance());  
                    datacell.setCellStyle(zidonghuanhang2); 
                    datacell = row.createCell(7);  
                    datacell.setCellValue(strains.getType());  
                    datacell.setCellStyle(zidonghuanhang2); 
                }
                
            }  
  
            // 第七步，将文件存到浏览器设置的下载位置  
            String filename = fileName + ".xls";  
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-disposition", "attachment; filename="+new String((filename).getBytes("gb2312"), "ISO8859-1"));  
            OutputStream out = response.getOutputStream();  
            
            try { 
            	if (wb != null) {
            		wb.write(out);// 将数据写出去  
            	}
            } catch (Exception e) {  
                e.printStackTrace();  
                String str1 = "导出" + fileName + "失败！";  
                System.out.println(str1);  
            } finally {  
                out.close();  
            }  
  
    }  
	
	public void ExportHeight3(String titleName,String fileName,String [] titleList,List<BrgMonitorDynadisp> list,
			String[] chanelNums,String [] pointsNos,HttpServletResponse response) throws Exception {  
       
            // 第一步，创建一个webbook，对应一个Excel文件  
            HSSFWorkbook wb = new HSSFWorkbook();  
            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
            HSSFSheet sheet = wb.createSheet(titleName);  
            // sheet.setDefaultColumnWidth(15); //统一设置列宽  
            for (int i = 0; i < titleList.length; i++)   
            {  
                for (int j = 0; j <= i; j++)   
                {  
                    if (i == j)   
                    {  
                        sheet.setColumnWidth(i, 20 * 256); // 单独设置每列的宽  
                    }  
                }  
            }  
            // 创建第0行 也就是标题  
            HSSFRow row1 = sheet.createRow((int) 0);  
            row1.setHeightInPoints(50);// 设备标题的高度  
            // 第三步创建标题的单元格样式style2以及字体样式headerFont1  
            HSSFCellStyle style2 = wb.createCellStyle();  
            style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
            style2.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);  
            style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
            HSSFFont headerFont1 = (HSSFFont) wb.createFont(); // 创建字体样式  
            headerFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
            headerFont1.setFontName("黑体"); // 设置字体类型  
            headerFont1.setFontHeightInPoints((short) 15); // 设置字体大小  
            style2.setFont(headerFont1); // 为标题样式设置字体样式  
  
            HSSFCell cell1 = row1.createCell(0);// 创建标题第一列  
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,  
            		titleList.length - 1)); // 合并列标题  
            cell1.setCellValue(titleName); // 设置值标题  
            cell1.setCellStyle(style2); // 设置标题样式  
  
            // 创建第1行 也就是表头  
            HSSFRow row = sheet.createRow((int) 1);  
            row.setHeightInPoints(37);// 设置表头高度  
  
            // 第四步，创建表头单元格样式 以及表头的字体样式  
            HSSFCellStyle style = wb.createCellStyle();  
            style.setWrapText(true);// 设置自动换行  
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式  
  
            style.setBottomBorderColor(HSSFColor.BLACK.index);  
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
  
            HSSFFont headerFont = (HSSFFont) wb.createFont(); // 创建字体样式  
            headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
            headerFont.setFontName("黑体"); // 设置字体类型  
            headerFont.setFontHeightInPoints((short) 10); // 设置字体大小  
            style.setFont(headerFont); // 为标题样式设置字体样式  
  
            // 第四.一步，创建表头的列  
            for (int i = 0; i < titleList.length; i++)   
            {  
                HSSFCell cell = row.createCell(i);  
                cell.setCellValue(titleList[i]);  
                cell.setCellStyle(style);  
            }  
  
            /*第五步*/
         // 为数据内容设置特点新单元格样式1 自动换行 上下居中  
            HSSFCellStyle zidonghuanhang = wb.createCellStyle();  
            zidonghuanhang.setWrapText(true);// 设置自动换行  
            zidonghuanhang.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式  

            // 设置边框  
            zidonghuanhang.setBottomBorderColor(HSSFColor.BLACK.index);  
            zidonghuanhang.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang.setBorderTop(HSSFCellStyle.BORDER_THIN);  

            // 为数据内容设置特点新单元格样式2 自动换行 上下居中左右也居中  
            HSSFCellStyle zidonghuanhang2 = wb.createCellStyle();  
            zidonghuanhang2.setWrapText(true);// 设置自动换行  
            zidonghuanhang2  
                    .setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个上下居中格式  
            zidonghuanhang2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中  

            // 设置边框  
            zidonghuanhang2.setBottomBorderColor(HSSFColor.BLACK.index);  
            zidonghuanhang2.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderTop(HSSFCellStyle.BORDER_THIN);  
            
            
            // 第六步，创建单元格，并设置值  
            for (int i = 0; i < list.size(); i++)   {  
                row = sheet.createRow((int) i + 2);  
                HSSFCell datacell = null;  
                BrgMonitorDynadisp Dynadisp=list.get(i);
                if(pointsNos.length>1){
                	 datacell = row.createCell(0);  
                     datacell.setCellValue(chanelNums[i]);  
                     datacell.setCellStyle(zidonghuanhang2); 
                     datacell = row.createCell(1);  
                     datacell.setCellValue(pointsNos[i]);  
                     datacell.setCellStyle(zidonghuanhang2); 
                     datacell = row.createCell(2);  
                     datacell.setCellValue(Dynadisp.getMax());  
                     datacell.setCellStyle(zidonghuanhang2); 
                     datacell = row.createCell(3);  
                     datacell.setCellValue(Dynadisp.getTantile_95());  
                     datacell.setCellStyle(zidonghuanhang2); 
                     datacell = row.createCell(4);  
                     datacell.setCellValue(Dynadisp.getAvg());  
                     datacell.setCellStyle(zidonghuanhang2); 
                     datacell = row.createCell(5);  
                     datacell.setCellValue(Dynadisp.getVariance());  
                     datacell.setCellStyle(zidonghuanhang2); 
                     datacell = row.createCell(6);  
                     datacell.setCellValue(Dynadisp.getType());  
                     datacell.setCellStyle(zidonghuanhang2); 
                }else{
                	 datacell = row.createCell(0);  
                     datacell.setCellValue(chanelNums[i]);  
                     datacell.setCellStyle(zidonghuanhang2); 
                     datacell = row.createCell(1);  
                     datacell.setCellValue(Dynadisp.getMax());  
                     datacell.setCellStyle(zidonghuanhang2); 
                     datacell = row.createCell(2);  
                     datacell.setCellValue(Dynadisp.getTantile_95());  
                     datacell.setCellStyle(zidonghuanhang2); 
                     datacell = row.createCell(3);  
                     datacell.setCellValue(Dynadisp.getAvg());  
                     datacell.setCellStyle(zidonghuanhang2); 
                     datacell = row.createCell(4);  
                     datacell.setCellValue(Dynadisp.getVariance());  
                     datacell.setCellStyle(zidonghuanhang2); 
                     datacell = row.createCell(5);  
                     datacell.setCellValue(Dynadisp.getType());  
                     datacell.setCellStyle(zidonghuanhang2); 
                }
               
            }  
  
            // 第七步，将文件存到浏览器设置的下载位置  
            String filename = fileName + ".xls";  
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-disposition", "attachment; filename="+new String((filename).getBytes("gb2312"), "ISO8859-1"));  
            OutputStream out = response.getOutputStream();  
            
            try { 
            	if (wb != null) {
            		wb.write(out);// 将数据写出去  
            	}
            } catch (Exception e) {  
                e.printStackTrace();  
                String str1 = "导出" + fileName + "失败！";  
                System.out.println(str1);  
            } finally {  
                out.close();  
            }  
  
    }  
	
	public void ExportHeight4(String titleName,String fileName,String [] titleList,List<BrgMonitorTemp> list,
			String[] chanelNums,String [] pointsNos,HttpServletResponse response) throws Exception {  
       
            // 第一步，创建一个webbook，对应一个Excel文件  
            HSSFWorkbook wb = new HSSFWorkbook();  
            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
            HSSFSheet sheet = wb.createSheet(titleName);  
            // sheet.setDefaultColumnWidth(15); //统一设置列宽  
            for (int i = 0; i < titleList.length; i++)   
            {  
                for (int j = 0; j <= i; j++)   
                {  
                    if (i == j)   
                    {  
                        sheet.setColumnWidth(i, 20 * 256); // 单独设置每列的宽  
                    }  
                }  
            }  
            // 创建第0行 也就是标题  
            HSSFRow row1 = sheet.createRow((int) 0);  
            row1.setHeightInPoints(50);// 设备标题的高度  
            // 第三步创建标题的单元格样式style2以及字体样式headerFont1  
            HSSFCellStyle style2 = wb.createCellStyle();  
            style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
            style2.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);  
            style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
            HSSFFont headerFont1 = (HSSFFont) wb.createFont(); // 创建字体样式  
            headerFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
            headerFont1.setFontName("黑体"); // 设置字体类型  
            headerFont1.setFontHeightInPoints((short) 15); // 设置字体大小  
            style2.setFont(headerFont1); // 为标题样式设置字体样式  
  
            HSSFCell cell1 = row1.createCell(0);// 创建标题第一列  
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,  
            		titleList.length - 1)); // 合并列标题  
            cell1.setCellValue(titleName); // 设置值标题  
            cell1.setCellStyle(style2); // 设置标题样式  
  
            // 创建第1行 也就是表头  
            HSSFRow row = sheet.createRow((int) 1);  
            row.setHeightInPoints(37);// 设置表头高度  
  
            // 第四步，创建表头单元格样式 以及表头的字体样式  
            HSSFCellStyle style = wb.createCellStyle();  
            style.setWrapText(true);// 设置自动换行  
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式  
  
            style.setBottomBorderColor(HSSFColor.BLACK.index);  
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
  
            HSSFFont headerFont = (HSSFFont) wb.createFont(); // 创建字体样式  
            headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
            headerFont.setFontName("黑体"); // 设置字体类型  
            headerFont.setFontHeightInPoints((short) 10); // 设置字体大小  
            style.setFont(headerFont); // 为标题样式设置字体样式  
  
            // 第四.一步，创建表头的列  
            for (int i = 0; i < titleList.length; i++)   
            {  
                HSSFCell cell = row.createCell(i);  
                cell.setCellValue(titleList[i]);  
                cell.setCellStyle(style);  
            }  
  
            /*第五步*/
         // 为数据内容设置特点新单元格样式1 自动换行 上下居中  
            HSSFCellStyle zidonghuanhang = wb.createCellStyle();  
            zidonghuanhang.setWrapText(true);// 设置自动换行  
            zidonghuanhang.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式  

            // 设置边框  
            zidonghuanhang.setBottomBorderColor(HSSFColor.BLACK.index);  
            zidonghuanhang.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang.setBorderTop(HSSFCellStyle.BORDER_THIN);  

            // 为数据内容设置特点新单元格样式2 自动换行 上下居中左右也居中  
            HSSFCellStyle zidonghuanhang2 = wb.createCellStyle();  
            zidonghuanhang2.setWrapText(true);// 设置自动换行  
            zidonghuanhang2  
                    .setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个上下居中格式  
            zidonghuanhang2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中  

            // 设置边框  
            zidonghuanhang2.setBottomBorderColor(HSSFColor.BLACK.index);  
            zidonghuanhang2.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderTop(HSSFCellStyle.BORDER_THIN);  
            
            
            // 第六步，创建单元格，并设置值  
            for (int i = 0; i < list.size(); i++)   {  
                row = sheet.createRow((int) i + 2);  
                HSSFCell datacell = null;  
                BrgMonitorTemp temp=list.get(i);
                if(pointsNos.length>1){
                	datacell = row.createCell(0);  
                    datacell.setCellValue(chanelNums[i]);  
                    datacell.setCellStyle(zidonghuanhang2);
                    datacell = row.createCell(1);  
                    datacell.setCellValue(pointsNos[i]);  
                    datacell.setCellStyle(zidonghuanhang2); 
                    datacell = row.createCell(2);
                    datacell.setCellValue(temp.getAvg());  
                    datacell.setCellStyle(zidonghuanhang2); 
                    datacell = row.createCell(3);  
                    datacell.setCellValue(temp.getType());  
                    datacell.setCellStyle(zidonghuanhang2); 
                }else{
                	datacell = row.createCell(0);  
                    datacell.setCellValue(chanelNums[i]);  
                    datacell.setCellStyle(zidonghuanhang2);
                    datacell = row.createCell(1);
                    datacell.setCellValue(temp.getAvg());  
                    datacell.setCellStyle(zidonghuanhang2); 
                    datacell = row.createCell(2);  
                    datacell.setCellValue(temp.getType());  
                    datacell.setCellStyle(zidonghuanhang2); 
                }
                
            }  
  
            // 第七步，将文件存到浏览器设置的下载位置  
            String filename = fileName + ".xls";  
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-disposition", "attachment; filename="+new String((filename).getBytes("gb2312"), "ISO8859-1"));  
            OutputStream out = response.getOutputStream();  
            
            try { 
            	if (wb != null) {
            		wb.write(out);// 将数据写出去  
            	}
            } catch (Exception e) {  
                e.printStackTrace();  
                String str1 = "导出" + fileName + "失败！";  
                System.out.println(str1);  
            } finally {  
                out.close();  
            }  
  
    }  
	
	public void ExportHeight5(String titleName,String fileName,String [] titleList,List<BrgMonitorCableforce> list,
			String[] chanelNums,String [] pointsNos,HttpServletResponse response) throws Exception {  
       
            // 第一步，创建一个webbook，对应一个Excel文件  
            HSSFWorkbook wb = new HSSFWorkbook();  
            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
            HSSFSheet sheet = wb.createSheet(titleName);  
            // sheet.setDefaultColumnWidth(15); //统一设置列宽  
            for (int i = 0; i < titleList.length; i++)   
            {  
                for (int j = 0; j <= i; j++)   
                {  
                    if (i == j)   
                    {  
                        sheet.setColumnWidth(i, 20 * 256); // 单独设置每列的宽  
                    }  
                }  
            }  
            // 创建第0行 也就是标题  
            HSSFRow row1 = sheet.createRow((int) 0);  
            row1.setHeightInPoints(50);// 设备标题的高度  
            // 第三步创建标题的单元格样式style2以及字体样式headerFont1  
            HSSFCellStyle style2 = wb.createCellStyle();  
            style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
            style2.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);  
            style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
            HSSFFont headerFont1 = (HSSFFont) wb.createFont(); // 创建字体样式  
            headerFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
            headerFont1.setFontName("黑体"); // 设置字体类型  
            headerFont1.setFontHeightInPoints((short) 15); // 设置字体大小  
            style2.setFont(headerFont1); // 为标题样式设置字体样式  
  
            HSSFCell cell1 = row1.createCell(0);// 创建标题第一列  
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,  
            		titleList.length - 1)); // 合并列标题  
            cell1.setCellValue(titleName); // 设置值标题  
            cell1.setCellStyle(style2); // 设置标题样式  
  
            // 创建第1行 也就是表头  
            HSSFRow row = sheet.createRow((int) 1);  
            row.setHeightInPoints(37);// 设置表头高度  
  
            // 第四步，创建表头单元格样式 以及表头的字体样式  
            HSSFCellStyle style = wb.createCellStyle();  
            style.setWrapText(true);// 设置自动换行  
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式  
  
            style.setBottomBorderColor(HSSFColor.BLACK.index);  
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
  
            HSSFFont headerFont = (HSSFFont) wb.createFont(); // 创建字体样式  
            headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
            headerFont.setFontName("黑体"); // 设置字体类型  
            headerFont.setFontHeightInPoints((short) 10); // 设置字体大小  
            style.setFont(headerFont); // 为标题样式设置字体样式  
  
            // 第四.一步，创建表头的列  
            for (int i = 0; i < titleList.length; i++)   
            {  
                HSSFCell cell = row.createCell(i);  
                cell.setCellValue(titleList[i]);  
                cell.setCellStyle(style);  
            }  
  
            /*第五步*/
         // 为数据内容设置特点新单元格样式1 自动换行 上下居中  
            HSSFCellStyle zidonghuanhang = wb.createCellStyle();  
            zidonghuanhang.setWrapText(true);// 设置自动换行  
            zidonghuanhang.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式  

            // 设置边框  
            zidonghuanhang.setBottomBorderColor(HSSFColor.BLACK.index);  
            zidonghuanhang.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang.setBorderTop(HSSFCellStyle.BORDER_THIN);  

            // 为数据内容设置特点新单元格样式2 自动换行 上下居中左右也居中  
            HSSFCellStyle zidonghuanhang2 = wb.createCellStyle();  
            zidonghuanhang2.setWrapText(true);// 设置自动换行  
            zidonghuanhang2  
                    .setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个上下居中格式  
            zidonghuanhang2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中  

            // 设置边框  
            zidonghuanhang2.setBottomBorderColor(HSSFColor.BLACK.index);  
            zidonghuanhang2.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderTop(HSSFCellStyle.BORDER_THIN);  
            
            
            // 第六步，创建单元格，并设置值  
            for (int i = 0; i < list.size(); i++)   {  
                row = sheet.createRow((int) i + 2);  
                HSSFCell datacell = null;  
                BrgMonitorCableforce Cableforce=list.get(i);
                if(pointsNos.length>1){
                	datacell = row.createCell(0);  
                    datacell.setCellValue(chanelNums[i]);  
                    datacell.setCellStyle(zidonghuanhang2);  
                    datacell = row.createCell(1);  
                    datacell.setCellValue(pointsNos[i]);  
                    datacell.setCellStyle(zidonghuanhang2); 
                    datacell = row.createCell(2);  
                    datacell.setCellValue(Cableforce.getAvg());  
                    datacell.setCellStyle(zidonghuanhang2); 
                    datacell = row.createCell(3);  
                    datacell.setCellValue(Cableforce.getType());  
                    datacell.setCellStyle(zidonghuanhang2); 
                }else{
                	datacell = row.createCell(0);  
                    datacell.setCellValue(chanelNums[i]);  
                    datacell.setCellStyle(zidonghuanhang2);  
                    datacell = row.createCell(1);  
                    datacell.setCellValue(Cableforce.getAvg());  
                    datacell.setCellStyle(zidonghuanhang2); 
                    datacell = row.createCell(2);  
                    datacell.setCellValue(Cableforce.getType());  
                    datacell.setCellStyle(zidonghuanhang2); 
                }
                
            }  
  
            // 第七步，将文件存到浏览器设置的下载位置  
            String filename = fileName + ".xls";  
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-disposition", "attachment; filename="+new String((filename).getBytes("gb2312"), "ISO8859-1"));  
            OutputStream out = response.getOutputStream();  
            
            try { 
            	if (wb != null) {
            		wb.write(out);// 将数据写出去  
            	}
            } catch (Exception e) {  
                e.printStackTrace();  
                String str1 = "导出" + fileName + "失败！";  
                System.out.println(str1);  
            } finally {  
                out.close();  
            }  
  
    }  
	
	public void ExportHeight1(String titleName,String fileName,String [] titleList,List<BrgMonitorStrainc> list,
			String[] chanelNums,String [] pointsNos,HttpServletResponse response) throws Exception {  
       
            // 第一步，创建一个webbook，对应一个Excel文件  
            HSSFWorkbook wb = new HSSFWorkbook();  
            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
            HSSFSheet sheet = wb.createSheet(titleName);  
            // sheet.setDefaultColumnWidth(15); //统一设置列宽  
            for (int i = 0; i < titleList.length; i++)   
            {  
                for (int j = 0; j <= i; j++)   
                {  
                    if (i == j)   
                    {  
                        sheet.setColumnWidth(i, 20 * 256); // 单独设置每列的宽  
                    }  
                }  
            }  
            // 创建第0行 也就是标题  
            HSSFRow row1 = sheet.createRow((int) 0);  
            row1.setHeightInPoints(50);// 设备标题的高度  
            // 第三步创建标题的单元格样式style2以及字体样式headerFont1  
            HSSFCellStyle style2 = wb.createCellStyle();  
            style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
            style2.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);  
            style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
            HSSFFont headerFont1 = (HSSFFont) wb.createFont(); // 创建字体样式  
            headerFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
            headerFont1.setFontName("黑体"); // 设置字体类型  
            headerFont1.setFontHeightInPoints((short) 15); // 设置字体大小  
            style2.setFont(headerFont1); // 为标题样式设置字体样式  
  
            HSSFCell cell1 = row1.createCell(0);// 创建标题第一列  
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,  
            		titleList.length - 1)); // 合并列标题  
            cell1.setCellValue(titleName); // 设置值标题  
            cell1.setCellStyle(style2); // 设置标题样式  
  
            // 创建第1行 也就是表头  
            HSSFRow row = sheet.createRow((int) 1);  
            row.setHeightInPoints(37);// 设置表头高度  
  
            // 第四步，创建表头单元格样式 以及表头的字体样式  
            HSSFCellStyle style = wb.createCellStyle();  
            style.setWrapText(true);// 设置自动换行  
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式  
  
            style.setBottomBorderColor(HSSFColor.BLACK.index);  
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
  
            HSSFFont headerFont = (HSSFFont) wb.createFont(); // 创建字体样式  
            headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
            headerFont.setFontName("黑体"); // 设置字体类型  
            headerFont.setFontHeightInPoints((short) 10); // 设置字体大小  
            style.setFont(headerFont); // 为标题样式设置字体样式  
  
            // 第四.一步，创建表头的列  
            for (int i = 0; i < titleList.length; i++)   
            {  
                HSSFCell cell = row.createCell(i);  
                cell.setCellValue(titleList[i]);  
                cell.setCellStyle(style);  
            }  
  
            /*第五步*/
         // 为数据内容设置特点新单元格样式1 自动换行 上下居中  
            HSSFCellStyle zidonghuanhang = wb.createCellStyle();  
            zidonghuanhang.setWrapText(true);// 设置自动换行  
            zidonghuanhang.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式  

            // 设置边框  
            zidonghuanhang.setBottomBorderColor(HSSFColor.BLACK.index);  
            zidonghuanhang.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang.setBorderTop(HSSFCellStyle.BORDER_THIN);  

            // 为数据内容设置特点新单元格样式2 自动换行 上下居中左右也居中  
            HSSFCellStyle zidonghuanhang2 = wb.createCellStyle();  
            zidonghuanhang2.setWrapText(true);// 设置自动换行  
            zidonghuanhang2  
                    .setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个上下居中格式  
            zidonghuanhang2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中  

            // 设置边框  
            zidonghuanhang2.setBottomBorderColor(HSSFColor.BLACK.index);  
            zidonghuanhang2.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderTop(HSSFCellStyle.BORDER_THIN);  
            
            
            // 第六步，创建单元格，并设置值  
            for (int i = 0; i < list.size(); i++)   {  
                row = sheet.createRow((int) i + 2);  
                HSSFCell datacell = null;  
            	BrgMonitorStrainc strainc=list.get(i);
            	if(pointsNos.length>1){
            		datacell = row.createCell(0);  
                    datacell.setCellValue(chanelNums[i]);  
                    datacell.setCellStyle(zidonghuanhang2);  
                    datacell = row.createCell(1);  
                    datacell.setCellValue(pointsNos[i]);  
                    datacell.setCellStyle(zidonghuanhang2);  
                    datacell = row.createCell(2);  
                    datacell.setCellValue(strainc.getMax());  
                    datacell.setCellStyle(zidonghuanhang2); 
                    datacell = row.createCell(3);  
                    datacell.setCellValue(strainc.getTantile_95());  
                    datacell.setCellStyle(zidonghuanhang2); 
                    datacell = row.createCell(4);  
                    datacell.setCellValue(strainc.getMin());  
                    datacell.setCellStyle(zidonghuanhang2); 
                    datacell = row.createCell(5);  
                    datacell.setCellValue(strainc.getTantile_5());  
                    datacell.setCellStyle(zidonghuanhang2); 
                    datacell = row.createCell(6);  
                    datacell.setCellValue(strainc.getAvg());  
                    datacell.setCellStyle(zidonghuanhang2); 
                    datacell = row.createCell(7);  
                    datacell.setCellValue(strainc.getVariance());  
                    datacell.setCellStyle(zidonghuanhang2); 
                    datacell = row.createCell(8);  
                    datacell.setCellValue(strainc.getType());  
                    datacell.setCellStyle(zidonghuanhang2); 
            	}else{
            		datacell = row.createCell(0);  
                    datacell.setCellValue(chanelNums[i]);  
                    datacell.setCellStyle(zidonghuanhang2);  
                    datacell = row.createCell(1);  
                    datacell.setCellValue(strainc.getMax());  
                    datacell.setCellStyle(zidonghuanhang2); 
                    datacell = row.createCell(2);  
                    datacell.setCellValue(strainc.getTantile_95());  
                    datacell.setCellStyle(zidonghuanhang2); 
                    datacell = row.createCell(3);  
                    datacell.setCellValue(strainc.getMin());  
                    datacell.setCellStyle(zidonghuanhang2); 
                    datacell = row.createCell(4);  
                    datacell.setCellValue(strainc.getTantile_5());  
                    datacell.setCellStyle(zidonghuanhang2); 
                    datacell = row.createCell(5);  
                    datacell.setCellValue(strainc.getAvg());  
                    datacell.setCellStyle(zidonghuanhang2); 
                    datacell = row.createCell(6);  
                    datacell.setCellValue(strainc.getVariance());  
                    datacell.setCellStyle(zidonghuanhang2); 
                    datacell = row.createCell(7);  
                    datacell.setCellValue(strainc.getType());  
                    datacell.setCellStyle(zidonghuanhang2); 
            	}
                
            }  
  
            // 第七步，将文件存到浏览器设置的下载位置  
            String filename = fileName + ".xls";  
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-disposition", "attachment; filename="+new String((filename).getBytes("gb2312"), "ISO8859-1"));  
            OutputStream out = response.getOutputStream();  
            
            try { 
            	if (wb != null) {
            		wb.write(out);// 将数据写出去  
            	}
            } catch (Exception e) {  
                e.printStackTrace();  
                String str1 = "导出" + fileName + "失败！";  
                System.out.println(str1);  
            } finally {  
                out.close();  
            }  
  
    }  
	
	public void ExportHeight6(String titleName,String fileName,String [] titleList,List<BrgMonitorStaticdisp> list,
			String[] chanelNums,String [] pointsNos,HttpServletResponse response) throws Exception {  
       
            // 第一步，创建一个webbook，对应一个Excel文件  
            HSSFWorkbook wb = new HSSFWorkbook();  
            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
            HSSFSheet sheet = wb.createSheet(titleName);  
            // sheet.setDefaultColumnWidth(15); //统一设置列宽  
            for (int i = 0; i < titleList.length; i++)   
            {  
                for (int j = 0; j <= i; j++)   
                {  
                    if (i == j)   
                    {  
                        sheet.setColumnWidth(i, 20 * 256); // 单独设置每列的宽  
                    }  
                }  
            }  
            // 创建第0行 也就是标题  
            HSSFRow row1 = sheet.createRow((int) 0);  
            row1.setHeightInPoints(50);// 设备标题的高度  
            // 第三步创建标题的单元格样式style2以及字体样式headerFont1  
            HSSFCellStyle style2 = wb.createCellStyle();  
            style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
            style2.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);  
            style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
            HSSFFont headerFont1 = (HSSFFont) wb.createFont(); // 创建字体样式  
            headerFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
            headerFont1.setFontName("黑体"); // 设置字体类型  
            headerFont1.setFontHeightInPoints((short) 15); // 设置字体大小  
            style2.setFont(headerFont1); // 为标题样式设置字体样式  
  
            HSSFCell cell1 = row1.createCell(0);// 创建标题第一列  
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,  
            		titleList.length - 1)); // 合并列标题  
            cell1.setCellValue(titleName); // 设置值标题  
            cell1.setCellStyle(style2); // 设置标题样式  
  
            // 创建第1行 也就是表头  
            HSSFRow row = sheet.createRow((int) 1);  
            row.setHeightInPoints(37);// 设置表头高度  
  
            // 第四步，创建表头单元格样式 以及表头的字体样式  
            HSSFCellStyle style = wb.createCellStyle();  
            style.setWrapText(true);// 设置自动换行  
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式  
  
            style.setBottomBorderColor(HSSFColor.BLACK.index);  
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
  
            HSSFFont headerFont = (HSSFFont) wb.createFont(); // 创建字体样式  
            headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
            headerFont.setFontName("黑体"); // 设置字体类型  
            headerFont.setFontHeightInPoints((short) 10); // 设置字体大小  
            style.setFont(headerFont); // 为标题样式设置字体样式  
  
            // 第四.一步，创建表头的列  
            for (int i = 0; i < titleList.length; i++)   
            {  
                HSSFCell cell = row.createCell(i);  
                cell.setCellValue(titleList[i]);  
                cell.setCellStyle(style);  
            }  
  
            /*第五步*/
         // 为数据内容设置特点新单元格样式1 自动换行 上下居中  
            HSSFCellStyle zidonghuanhang = wb.createCellStyle();  
            zidonghuanhang.setWrapText(true);// 设置自动换行  
            zidonghuanhang.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式  

            // 设置边框  
            zidonghuanhang.setBottomBorderColor(HSSFColor.BLACK.index);  
            zidonghuanhang.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang.setBorderTop(HSSFCellStyle.BORDER_THIN);  

            // 为数据内容设置特点新单元格样式2 自动换行 上下居中左右也居中  
            HSSFCellStyle zidonghuanhang2 = wb.createCellStyle();  
            zidonghuanhang2.setWrapText(true);// 设置自动换行  
            zidonghuanhang2  
                    .setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个上下居中格式  
            zidonghuanhang2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中  

            // 设置边框  
            zidonghuanhang2.setBottomBorderColor(HSSFColor.BLACK.index);  
            zidonghuanhang2.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            zidonghuanhang2.setBorderTop(HSSFCellStyle.BORDER_THIN);  
            
            
            // 第六步，创建单元格，并设置值  
            for (int i = 0; i < list.size(); i++)   {  
                row = sheet.createRow((int) i + 2);  
                HSSFCell datacell = null;  
                BrgMonitorStaticdisp Staticdisp=list.get(i);
                if(pointsNos.length>1){
                	datacell = row.createCell(0);  
                    datacell.setCellValue(chanelNums[i]);  
                    datacell.setCellStyle(zidonghuanhang2);  
                    datacell = row.createCell(1);  
                    datacell.setCellValue(pointsNos[i]);  
                    datacell.setCellStyle(zidonghuanhang2);  
                    datacell = row.createCell(2);  
                    datacell.setCellValue(Staticdisp.getMax());  
                    datacell.setCellStyle(zidonghuanhang2); 
                    datacell = row.createCell(3);  
                    datacell.setCellValue(Staticdisp.getMin());  
                    datacell.setCellStyle(zidonghuanhang2); 
                    datacell = row.createCell(4);  
                    datacell.setCellValue(Staticdisp.getType());  
                    datacell.setCellStyle(zidonghuanhang2); 
                }else{
                	datacell = row.createCell(0);  
                    datacell.setCellValue(chanelNums[i]);  
                    datacell.setCellStyle(zidonghuanhang2);  
                    datacell = row.createCell(1);  
                    datacell.setCellValue(Staticdisp.getMax());  
                    datacell.setCellStyle(zidonghuanhang2); 
                    datacell = row.createCell(2);  
                    datacell.setCellValue(Staticdisp.getMin());  
                    datacell.setCellStyle(zidonghuanhang2); 
                    datacell = row.createCell(3);  
                    datacell.setCellValue(Staticdisp.getType());  
                    datacell.setCellStyle(zidonghuanhang2); 
                }
                
            }  
  
            // 第七步，将文件存到浏览器设置的下载位置  
            String filename = fileName + ".xls";  
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-disposition", "attachment; filename="+new String((filename).getBytes("gb2312"), "ISO8859-1"));  
            OutputStream out = response.getOutputStream();  
            
            try { 
            	if (wb != null) {
            		wb.write(out);// 将数据写出去  
            	}
            } catch (Exception e) {  
                e.printStackTrace();  
                String str1 = "导出" + fileName + "失败！";  
                System.out.println(str1);  
            } finally {  
                out.close();  
            }  
  
    }  
	
	public String getBaiFenBi(String obj){
		String result=(int)((Float.parseFloat(obj))*100)+"%";
		return result;
	}
	
	
}
