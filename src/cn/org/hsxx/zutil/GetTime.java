
package cn.org.hsxx.zutil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GetTime {

	public String getMonthDate(String date){
		int year=Integer.valueOf(date.split("-")[0]);
		int month=Integer.valueOf(date.split("-")[1]);
		String mon=null;
		if(month<12){
			month=month+1;
			if(month<10){
				mon="0"+String.valueOf(month);
			}else{
				mon=String.valueOf(month);
			}
		}else if(month==12){
			year=year+1;
			mon="01";
		}
		String time=String.valueOf(year)+"-"+mon+"-01 00:40:00";
		return time;
	}
	
	public String getYearDate(String date){
		int year=Integer.valueOf(date);
		String time=String.valueOf(year+1)+"-01-01 00:50:00";
		return time;
	}
	
	
}
