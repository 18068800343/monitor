
package cn.org.hsxx.run;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.org.hsxx.matlab.FtpUtil;
import cn.org.hsxx.zutil.GetTime;



public class Test {
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat sdf3=new SimpleDateFormat("yyyy");
		long distanceOfDay=0;
		long distanceOfMonth=0;
		long distanceOfYear=0;
		GetTime gt=new GetTime();
		try {
			long nextTimeOfDay=sdf1.parse(sdf.format(new Date())+" 00:30:00").getTime()+86400000;
			long nowTime=sdf1.parse(sdf1.format(new Date())).getTime();
			distanceOfDay=(nextTimeOfDay-nowTime)/1000;
			long nextTimeOfMonth=sdf1.parse(gt.getMonthDate(sdf2.format(new Date()))).getTime();
			distanceOfMonth=(nextTimeOfMonth-nowTime)/1000;
			long nextTimeOfYear=sdf1.parse(gt.getYearDate(sdf3.format(new Date()))).getTime();
			distanceOfYear=(nextTimeOfYear-nowTime)/1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		ScheduledThreadPoolExecutor schedule = new ScheduledThreadPoolExecutor(10);
		System.out.println("开始。。。");
		schedule.scheduleAtFixedRate(new FtpUtil(),1500,1800,TimeUnit.SECONDS);
	}
}
