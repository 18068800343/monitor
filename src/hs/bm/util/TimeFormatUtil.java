package hs.bm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author xianing
 */
public class TimeFormatUtil {

	//获取规定格式时间
	public static String getYMDtime(String format,Date date){
		SimpleDateFormat dateFormater = new SimpleDateFormat(format);
		return dateFormater.format(date);
	}
	public static String getSpecifiedDayBefore(String format,String regix){ 
		//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		Calendar c = Calendar.getInstance(); 
		c.setTime(new Date()); 
		int day=c.get(Calendar.DATE); 
		c.set(Calendar.DATE,day-1); 

		String dayBefore=new SimpleDateFormat(format).format(c.getTime()); 
		String times[] = dayBefore.split(regix);
		String day1 = times[1];
		if(day1.length()==1){
			day1 = "0"+day1;
		}
		String newDayBefore = times[0]+regix+day1+regix+times[2];
		return newDayBefore; 
	} 
	public static String getSpecifiedMonthBefore(String format,String regix){ 
		SimpleDateFormat ss = new SimpleDateFormat(format);
		 Calendar c = Calendar.getInstance();
	        //过去一月
	        c.setTime(new Date());
	        c.add(Calendar.MONTH, -1);
	        Date m = c.getTime();
	        String mon = ss.format(m);
	        System.out.println("过去一个月："+mon);
		return mon; 
	} 
	public static String getSpecifiedYearBefore(String format,String regix){ 
		SimpleDateFormat ss = new SimpleDateFormat(format);
		 Calendar c = Calendar.getInstance();
	        //过去一月
	        c.setTime(new Date());
	        c.add(Calendar.YEAR, -1);
	        Date m = c.getTime();
	        String mon = ss.format(m);
	        System.out.println("过去一个月："+mon);
		return mon; 
	} 
	public static void main(String[] args) {
		getSpecifiedYearBefore("yyyy","-");
	}
}
