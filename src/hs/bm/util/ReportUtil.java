package hs.bm.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/** 
* @ClassName: ReportUtil 
* @Description: 生成监测报告
* @author 马潇霄
* @date 2018年12月4日 下午5:38:24 
*  
*/
public class ReportUtil {

	public String getReportPath(String id){
		String path="";
		String op = "D:\\监测报告\\AutoReport.exe "+id;
		try {
			Process process = Runtime.getRuntime().exec(op);
			InputStream is = process.getInputStream();
		    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "GBK"));
		    String line;
		    while((line = reader.readLine())!= null){
				   System.out.println(line);
					   if(line.contains("生成报告文件：")){
						   path = line.replace("生成报告文件：", "");
						   System.out.println(path);
					   }
				  }
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
}
