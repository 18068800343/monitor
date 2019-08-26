package cn.org.hsxx.matlab;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TimeUtil {

	public static long getTimeLong(String end_time) throws ParseException{
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		Date date = new Date();
		Date end_date;
		try {
			end_date = dfs.parse(end_time);
			long between=(date.getTime()-end_date.getTime())/1000;//除以1000是为了转换成秒
			long minute=between/60;
			return minute;
		} catch (ParseException e) {
			e.printStackTrace();
			throw new ParseException(end_time,0);
		}
		
	}
}
