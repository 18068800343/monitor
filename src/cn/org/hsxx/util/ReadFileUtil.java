package cn.org.hsxx.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.org.hsxx.bean.BrgCardAdminId;
import cn.org.hsxx.dao.BrgCardDao;
import cn.org.hsxx.dao.HealthCheckDateDao;
import cn.org.hsxx.server.ShortMessageServiceV2;
import cn.org.hsxx.dao.BrgHealthStatisticDao;
import cn.org.hsxx.dao.BrgMonitorDao;

import java.text.Format;  
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;  
import java.util.Date;  

public class ReadFileUtil {
	/** 
	* 判断时间是否在时间段内 
	* @param date 
	*            当前时间 yyyy-MM-dd HH:mm:ss 
	* @param strDateBegin 
	*            开始时间 09:00:00 
	* @param strDateEnd 
	*            结束时间 18:00:00 
	*/  
	public static boolean isInDate(Date date) {
		//开始时间=09:00:00
		//结束时间=18:00:00 
		date=new Date();
		String strDateBegin = "09:00:00";
		String strDateEnd = "17:40:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
		String strDate = sdf.format(date); // 2016-12-16 11:53:54
		// 截取当前时间时分秒 转成整型
		int tempDate = Integer
				.parseInt(strDate.substring(11, 13) + strDate.substring(14, 16) + strDate.substring(17, 19));
		// 截取开始时间时分秒 转成整型
		int tempDateBegin = Integer
				.parseInt(strDateBegin.substring(0, 2) + strDateBegin.substring(3, 5) + strDateBegin.substring(6, 8));
		// 截取结束时间时分秒 转成整型
		int tempDateEnd = Integer
				.parseInt(strDateEnd.substring(0, 2) + strDateEnd.substring(3, 5) + strDateEnd.substring(6, 8));

		if ((tempDate >= tempDateBegin && tempDate <= tempDateEnd)) {
			return true;
		} else {
			return false;
		}
	}

	public static String getTxtValue(String file_path, String system_name) {
		String path = ReadFileUtil.class.getResource(file_path).getPath(); 
		StringBuilder result = new StringBuilder();
		File file = new File(path);
		String value = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));// 构造一个BufferedReader类来读取文件
			String s = null;
			while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
				if (s.contains(system_name)) {
					String[] strings = s.split("=");
					if (strings.length > 1) {
						value = strings[1];
					}

				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
    public static Date getTomorrow(Date today) {  
        Format f = new SimpleDateFormat("yyyy-MM-dd");  
   
        System.out.println("今天是:" + f.format(today));  
   
        Calendar c = Calendar.getInstance();  
        c.setTime(today);  
        c.add(Calendar.DAY_OF_MONTH, 1);// 今天+1天  
   
        Date tomorrow = c.getTime();  
        
        return tomorrow;  
    }  
    public static String  getTomorrowFmt(Date today) {  
        Format f = new SimpleDateFormat("yyyy-MM-dd");  
   
        System.out.println("今天是:" + f.format(today));  
   
        Calendar c = Calendar.getInstance();  
        c.setTime(today);  
        c.add(Calendar.DAY_OF_MONTH, 1);// 今天+1天  
   
        Date tomorrow = c.getTime();  
        return  f.format(tomorrow);  
    }  
	public static void main(String[] args) {
		/*boolean bool = ReadFileUtil.isInDate(new Date());
	    System.out.println(bool);
	    System.out.println(ReadFileUtil.getTxtValue("/messagePhoneConfig.txt", "GPS系统")); */
		//System.out.println(ReadFileUtil.getTomorrowFmt(new Date()));
		System.out.println(24*60*60);
		Date nowDate = new Date();
		long waitTime = 21600;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		String nowDateStr = simpleDateFormat.format(nowDate).substring(0, 10);
		String tommorow = ReadFileUtil.getTomorrowFmt(nowDate);
		System.out.println(nowDateStr);
			try {
				Date date = simpleDateFormat.parse(tommorow+" 17:30:00");
				waitTime = (date.getTime()-nowDate.getTime())/1000;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	//E:\abcd\G15320902L0020\2017-06-14 04-43-14\dynadisp.txt
	//[E:,abcd,G15320902L0020S,2017-11-02 11-50-57,dynadisp.txt]
	//[E:, weight_data, 新沭河大桥, 2017-08-19 16-02-30, 2017-08-19 16-02-30.txt]
	//E:\weight_data\永安河大桥\2017-10-25 13-03-05\ratio_ovlo_com.txt
	//E:\health_data\绣针河大桥\G15320721L0010\2017-04-08 15-25-08\dynadisp.txt
	/**
	 * 
	 * strainc 混凝土应变 8 strains 钢应变 9
       dynadisp 动位移 4 temp 温度  3 calberforce 索力 10
	 */
	public static String DateToDB(File file,String[] arr,String fileName) {
		
		String value = "";
		InputStreamReader read;
		BufferedReader br = null;
		try {
			     read = new InputStreamReader(new FileInputStream(file),"gbk");       
				 br = new BufferedReader(read);// 构造一个BufferedReader类来读取文件
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				String s = null;
				int i = 0;
				String brgNo = arr[2].replace("S","");
				String brg_id = "";
				String brg_name = "";
				BrgCardAdminId brgCardAdminId=BrgCardDao.getInstance().getBrgCardAdminIdData(brgNo);
				if(null!=brgCardAdminId){
					brg_id=brgCardAdminId.getBridge_id();
					brg_name=brgCardAdminId.getBridge_name();
				}
				Map<String,List<String>> dynadispMsg_unusual = new HashMap();
				Map<String,List<String>> dynadispMsg_warn = new HashMap();
				Map<String,List<String>> straincMsg_unusual = new HashMap();
				Map<String,List<String>> straincMsg_warn = new HashMap();
				Map<String,List<String>> strainsMsg_unusual = new HashMap();
				Map<String,List<String>> strainsMsg_warn = new HashMap();
				Map<String,List<String>> tempMsg_unusual = new HashMap();
				Map<String,List<String>> tempMsg_warn = new HashMap();
				Map<String,List<String>> cableforceMsg_unusual = new HashMap();
				Map<String,List<String>> cableforceMsg_warn = new HashMap();
				Map<String,List<String>> staticdispMsg_unusual = new HashMap();
				Map<String,List<String>> staticdispMsg_warn = new HashMap();
				List<String> list_dynadispMsg_unusual = new ArrayList<>();
				List<String> list_dynadispMsg_warnR = new ArrayList<>();
				List<String> list_dynadispMsg_warnY = new ArrayList<>();
				List<String> list_straincMsg_unusual = new ArrayList<>();
				List<String> list_straincMsg_warnR = new ArrayList<>();
				List<String> list_straincMsg_warnY = new ArrayList<>();
				List<String> list_strainsMsg_unusual = new ArrayList<>();
				List<String> list_strainsMsg_warnR = new ArrayList<>();
				List<String> list_strainsMsg_warnY = new ArrayList<>();
				List<String> list_tempMsg_unusual = new ArrayList<>();
				List<String> list_tempMsg_warnR = new ArrayList<>();
				List<String> list_tempMsg_warnY = new ArrayList<>();
				List<String> list_cableforceMsg_unusual = new ArrayList<>();
				List<String> list_cableforceMsg_warnR = new ArrayList<>();
				List<String> list_cableforceMsg_warnY = new ArrayList<>();
				List<String> list_staticdispMsg_unusual = new ArrayList<>();
				List<String> list_staticdispMsg_warnR = new ArrayList<>();
				List<String> list_staticdispMsg_warnY = new ArrayList<>();
				while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
					++i;
					String[] arrLine = s.split(",");
					if(fileName.contains("dynadisp")){
						
						int item = 4;//动位移
						String chanelNum= BrgCardDao.getInstance().getChanelNum(brg_id, item+"");
						String[] chanelNums = chanelNum.split(",");
							String realChanelNum= chanelNums[i-1];
						String bugMsg = getMsg(arrLine);
						if(bugMsg.equals("异常")){
							list_dynadispMsg_unusual.add(realChanelNum);
							dynadispMsg_unusual.put(bugMsg, list_dynadispMsg_unusual);
						}else if(bugMsg.equals("红色告警")){
							list_dynadispMsg_warnR.add(realChanelNum);
							dynadispMsg_warn.put(bugMsg, list_dynadispMsg_warnR);
						}else if(bugMsg.equals("黄色告警")){
							list_dynadispMsg_warnY.add(realChanelNum);
							dynadispMsg_warn.put(bugMsg, list_dynadispMsg_warnY);
						}
						String[] emptyArr = new String[9]; 
						String[] newArr = getNewStringArr(emptyArr,arrLine);
						newArr[0]=arr[2].replace("S","");
						newArr[1]=arr[3];
						newArr[2]="hour";
						newArr[8]=i+"";
						HealthCheckDateDao.getInstance().addDynadisp(newArr);
						System.out.println(fileName+": 成功");
					}else if(fileName.contains("strainc")){
						int item = 8;//混凝土应变 8
						String chanelNum= BrgCardDao.getInstance().getChanelNum(brg_id, item+"");
						String[] chanelNums = chanelNum.split(",");
						String realChanelNum= chanelNums[i-1];
						String bugMsg = getMsg(arrLine);
						if(bugMsg.equals("异常")){
							list_straincMsg_unusual.add(realChanelNum);
							straincMsg_unusual.put(bugMsg, list_straincMsg_unusual);
						}else if(bugMsg.equals("红色告警")){
							list_straincMsg_warnR.add(realChanelNum);
							straincMsg_warn.put(bugMsg, list_straincMsg_warnR);
						}else if(bugMsg.equals("黄色告警")){
							list_straincMsg_warnY.add(realChanelNum);
							straincMsg_warn.put(bugMsg, list_straincMsg_warnY);
						}
						String[] emptyArr = new String[11]; 
						String[] newArr = getNewStringArr(emptyArr,arrLine);
						newArr[0]=arr[2].replace("S","");
						newArr[1]=arr[3];
						newArr[2]="hour";
						newArr[10]=i+"";
						HealthCheckDateDao.getInstance().addStrainc(newArr);
						System.out.println(fileName+": 成功");
					}else if(fileName.contains("strains")){
						int item = 9;//钢应变
						String chanelNum= BrgCardDao.getInstance().getChanelNum(brg_id, item+"");
						String[] chanelNums = chanelNum.split(",");
						String realChanelNum= chanelNums[i-1];
						String bugMsg = getMsg(arrLine);
						if(bugMsg.equals("异常")){
							list_strainsMsg_unusual.add(realChanelNum);
							strainsMsg_unusual.put(bugMsg, list_strainsMsg_unusual);
						}else if(bugMsg.equals("红色告警")){
							list_strainsMsg_warnR.add(realChanelNum);
							strainsMsg_warn.put(bugMsg, list_strainsMsg_warnR);
						}else if(bugMsg.equals("黄色告警")){
							list_strainsMsg_warnY.add(realChanelNum);
							strainsMsg_warn.put(bugMsg, list_strainsMsg_warnY);
						}
						String[] emptyArr = new String[11]; 
						String[] newArr = getNewStringArr(emptyArr,arrLine);
						newArr[0]=arr[2].replace("S","");
						newArr[1]=arr[3];
						newArr[2]="hour";
						newArr[10]=i+"";
						HealthCheckDateDao.getInstance().addStrains(newArr);
						System.out.println(fileName+": 成功");
					}else if(fileName.contains("temp")){
						int item = 3;// 温度  
						String chanelNum= BrgCardDao.getInstance().getChanelNum(brg_id, item+"");
						String[] chanelNums = chanelNum.split(",");
						String realChanelNum= chanelNums[i-1];
						String bugMsg = getMsg(arrLine);
						if(bugMsg.equals("异常")){
							list_tempMsg_unusual.add(realChanelNum);
							tempMsg_unusual.put(bugMsg, list_tempMsg_unusual);
						}else if(bugMsg.equals("红色告警")){
							list_tempMsg_warnR.add(realChanelNum);
							tempMsg_warn.put(bugMsg, list_tempMsg_warnR);
						}else if(bugMsg.equals("黄色告警")){
							list_tempMsg_warnY.add(realChanelNum);
							tempMsg_warn.put(bugMsg, list_tempMsg_warnY);
						}
						String[] emptyArr = new String[6]; 
						String[] newArr = getNewStringArr(emptyArr,arrLine);
						newArr[0]=arr[2].replace("S","");
						newArr[1]=arr[3];
						newArr[2]="hour";
						newArr[5]=i+"";
						HealthCheckDateDao.getInstance().addTemp(newArr);
						System.out.println(fileName+": 成功");
					}else if(fileName.contains("cableforce")){
						int item = 10;//索力 
						String chanelNum= BrgCardDao.getInstance().getChanelNum(brg_id, item+"");
						String[] chanelNums = chanelNum.split(",");
						String realChanelNum= chanelNums[i-1];
						String bugMsg = getMsg(arrLine);
						if(bugMsg.equals("异常")){
							list_cableforceMsg_unusual.add(realChanelNum);
							cableforceMsg_unusual.put(bugMsg, list_cableforceMsg_unusual);
						}else if(bugMsg.equals("红色告警")){
							list_cableforceMsg_warnR.add(realChanelNum);
							cableforceMsg_warn.put(bugMsg, list_cableforceMsg_warnR);
						}else if(bugMsg.equals("黄色告警")){
							list_cableforceMsg_warnY.add(realChanelNum);
							cableforceMsg_warn.put(bugMsg, list_cableforceMsg_warnY);
						}
						String[] emptyArr = new String[6]; 
						String[] newArr = getNewStringArr(emptyArr,arrLine);
						newArr[0]=arr[2].replace("S","");
						newArr[1]=arr[3];
						newArr[2]="hour";
						newArr[5]=i+"";
						HealthCheckDateDao.getInstance().addCableforce(newArr);
						System.out.println(fileName+": 成功");
					}else if(fileName.contains("staticdisp")){
						int item = 11;//
						String chanelNum= BrgCardDao.getInstance().getChanelNum(brg_id, item+"");
						String[] chanelNums = chanelNum.split(",");
						String realChanelNum= chanelNums[i-1];
						String bugMsg = getMsg(arrLine);
						if(bugMsg.equals("异常")){
							list_staticdispMsg_unusual.add(realChanelNum);
							staticdispMsg_unusual.put(bugMsg, list_staticdispMsg_unusual);
						}else if(bugMsg.equals("红色告警")){
							list_staticdispMsg_warnR.add(realChanelNum);
							staticdispMsg_warn.put(bugMsg, list_staticdispMsg_warnR);
						}else if(bugMsg.equals("黄色告警")){
							list_staticdispMsg_warnY.add(realChanelNum);
							staticdispMsg_warn.put(bugMsg, list_staticdispMsg_warnY);
						}
						String[] emptyArr = new String[7]; 
						String[] newArr = getNewStringArr(emptyArr,arrLine);
						newArr[0]=arr[2].replace("S","");
						newArr[1]=arr[3];
						newArr[2]="hour";
						newArr[6]=i+"";
						HealthCheckDateDao.getInstance().addStaticdisp(newArr);
						System.out.println(fileName+": 成功");
					}
				}
				br.close();
				SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
				String nowDay=arr[3].split(" ")[0];
				long nowTime=sdf1.parse(arr[3]).getTime();
				long nextTime=sdf1.parse(nowDay+" 09-00-00").getTime();
				long lastTime=sdf1.parse(nowDay+" 18-00-00").getTime();
				String errorTime = arr[3].substring(11, 13);
				if(!errorTime.contains("00")&&!errorTime.contains("23")&&!errorTime.contains("24")&&!errorTime.contains("12")){
				
				if(null!=dynadispMsg_unusual.get("异常")&&dynadispMsg_unusual.get("异常").size()>0){
					Date date = new Date();
					boolean isInDate =ReadFileUtil.isInDate(date);
					String dynadispU = dynadispMsg_unusual.get("异常").toString();//动位移异常 4 
					String phone=BrgMonitorDao.getIntance().selectByItem(brgNo, "数据异常", "动位移");
					boolean ifSend = BrgHealthStatisticDao.getInstance().selectIfSend(brgNo, sdf.format(new Date()), dynadispU, "数据异常","4");
					List<String> newTimeList=BrgHealthStatisticDao.getInstance().selectBrgLastTimeXN(brgNo, "数据异常", "4");
					
					String code = "";
					if(!ifSend){
						if(isInDate){
							if(newTimeList.size()>0){
								long newTime=sdf1.parse(newTimeList.get(0)+" 09-00-00").getTime();
								if((nowTime-newTime)>302400000){
									code =	ShortMessageServiceV2.getInstance().SendMessage(""+phone+"","{system:'"+brg_name+"健康监测系统',serialnum:'"+dynadispU+"',param:'整体响应-动位移'}", "SMS_105420026");
								}
							}else{
								code =	ShortMessageServiceV2.getInstance().SendMessage(""+phone+"","{system:'"+brg_name+"健康监测系统',serialnum:'"+dynadispU+"',param:'整体响应-动位移'}", "SMS_105420026");
							}
								
						}
					}
						if(code!=null&&"OK".equals(code)) {
							if(nowTime>nextTime&&nowTime<lastTime){
						       BrgHealthStatisticDao.getInstance().addBugMessageMonitor(brgNo,sdf.format(new Date()),dynadispU,"数据异常","4");
							 }
				    }
				}
				if(null!=dynadispMsg_warn.get("红色告警")&&dynadispMsg_warn.get("红色告警").size()>0){
					Date date = new Date();
					boolean isInDate =ReadFileUtil.isInDate(date);
					String dynadispNR = dynadispMsg_warn.get("红色告警").toString();//动位移红色告警通道list 4
					String phone=BrgMonitorDao.getIntance().selectByItem(brgNo, "红色告警", "动位移");
					boolean ifSend = BrgHealthStatisticDao.getInstance().selectIfSend(brgNo, sdf.format(new Date()), dynadispNR, "红色告警","4");
					List<String> newTimeList=BrgHealthStatisticDao.getInstance().selectBrgLastTimeXN(brgNo, "红色告警", "4");
					String code = "";
					if(!ifSend){
						if(isInDate){
							if(newTimeList.size()>0){
								long newTime=sdf1.parse(newTimeList.get(0)+" 09-00-00").getTime();
									if((nowTime-newTime)>302400000){
										code =  ShortMessageServiceV2.getInstance().SendMessage(""+phone+"","{bridge:'"+brg_name+"健康监测系统',alarm:'红色',serialnum:'"+dynadispNR+"',param:'整体响应-动位移'}", "SMS_105420034");
									}
							}else{
								code =  ShortMessageServiceV2.getInstance().SendMessage(""+phone+"","{bridge:'"+brg_name+"健康监测系统',alarm:'红色',serialnum:'"+dynadispNR+"',param:'整体响应-动位移'}", "SMS_105420034");
							}
							
						}
					}
					 if(code!=null&&"OK".equals(code)) {
						 if(nowTime>nextTime&&nowTime<lastTime){
							BrgHealthStatisticDao.getInstance().addBugMessageMonitor(brgNo,sdf.format(new Date()),dynadispNR,"红色告警","4");
						 }
						}
				}
				if(null!=dynadispMsg_warn.get("黄色告警")&&dynadispMsg_warn.get("黄色告警").size()>0){
					String dynadispNY = dynadispMsg_warn.get("黄色告警").toString();//动位移黄色告警通道list 4
					String phone=BrgMonitorDao.getIntance().selectByItem(brgNo, "黄色告警", "动位移");
					boolean ifSend = BrgHealthStatisticDao.getInstance().selectIfSend(brgNo, sdf.format(new Date()), dynadispNY, "黄色告警","4");
					Date date = new Date();
					boolean isInDate =ReadFileUtil.isInDate(date);
					List<String> newTimeList=BrgHealthStatisticDao.getInstance().selectBrgLastTimeXN(brgNo, "黄色告警", "4");
					String code = "";
					if(!ifSend){
						if(isInDate){
							if(newTimeList.size()>0){
								long newTime=sdf1.parse(newTimeList.get(0)+" 09-00-00").getTime();
								if((nowTime-newTime)>302400000){
									code =  ShortMessageServiceV2.getInstance().SendMessage(""+phone+"","{bridge:'"+brg_name+"健康监测系统',alarm:'黄色',serialnum:'"+dynadispNY+"',param:'整体响应-动位移'}", "SMS_105420034");
								}
							}else{
								code =  ShortMessageServiceV2.getInstance().SendMessage(""+phone+"","{bridge:'"+brg_name+"健康监测系统',alarm:'黄色',serialnum:'"+dynadispNY+"',param:'整体响应-动位移'}", "SMS_105420034");
							}
						}
					}
					 if(code!=null&&"OK".equals(code)) {
						 if(nowTime>nextTime&&nowTime<lastTime){
							BrgHealthStatisticDao.getInstance().addBugMessageMonitor(brgNo,sdf.format(new Date()),dynadispNY,"黄色告警","4");
						 }
						}
				}
				if(null!=straincMsg_unusual.get("异常")&&straincMsg_unusual.get("异常").size()>0){
					String straincU =  straincMsg_unusual.get("异常").toString();//混凝土应变异常 8
					String phone=BrgMonitorDao.getIntance().selectByItem(brgNo, "数据异常", "混凝土应变");
					boolean ifSend = BrgHealthStatisticDao.getInstance().selectIfSend(brgNo, sdf.format(new Date()), straincU,"数据异常","8");
					String code = "";
					Date date = new Date();
					boolean isInDate =ReadFileUtil.isInDate(date);
					List<String> newTimeList=BrgHealthStatisticDao.getInstance().selectBrgLastTimeXN(brgNo, "数据异常", "8");
					if(!ifSend){
						if(isInDate){
							if(newTimeList.size()>0){
								long newTime=sdf1.parse(newTimeList.get(0)+" 09-00-00").getTime();
								if((nowTime-newTime)>302400000){
								 code =  ShortMessageServiceV2.getInstance().SendMessage(""+phone+"","{system:'"+brg_name+"健康监测系统',serialnum:'"+straincU+"',param:'局部响应-混凝土应变'}", "SMS_105420026");
								}
							}else{
								code =  ShortMessageServiceV2.getInstance().SendMessage(""+phone+"","{system:'"+brg_name+"健康监测系统',serialnum:'"+straincU+"',param:'局部响应-混凝土应变'}", "SMS_105420026");
							}
						}
					}
					 if(code!=null&&"OK".equals(code)) {
						 if(nowTime>nextTime&&nowTime<lastTime){
							BrgHealthStatisticDao.getInstance().addBugMessageMonitor(brgNo,sdf.format(new Date()),straincU,"数据异常","8");
						 }
						}
				}
				if(null!=straincMsg_warn.get("红色告警")&&straincMsg_warn.get("红色告警").size()>0){
					String straincMsgR =  straincMsg_warn.get("红色告警").toString();//混凝土红色告警 8
					String phone=BrgMonitorDao.getIntance().selectByItem(brgNo, "红色告警", "混凝土应变");
					Date date = new Date();
					boolean isInDate =ReadFileUtil.isInDate(date);
					boolean ifSend = BrgHealthStatisticDao.getInstance().selectIfSend(brgNo, sdf.format(new Date()), straincMsgR,"红色告警","8");
					String code = "";
					if(!ifSend){
						if(isInDate){
							List<String> newTimeList=BrgHealthStatisticDao.getInstance().selectBrgLastTimeXN(brgNo, "红色告警", "8");
							if(newTimeList.size()>0){
								long newTime=sdf1.parse(newTimeList.get(0)+" 09-00-00").getTime();
								if((nowTime-newTime)>302400000){
									code =  ShortMessageServiceV2.getInstance().SendMessage(""+phone+"","{bridge:'"+brg_name+"健康监测系统',alarm:'红色',serialnum:'"+straincMsgR+"',param:'局部响应-混凝土应变'}", "SMS_105420034");
								}
							}else{
								code =  ShortMessageServiceV2.getInstance().SendMessage(""+phone+"","{bridge:'"+brg_name+"健康监测系统',alarm:'红色',serialnum:'"+straincMsgR+"',param:'局部响应-混凝土应变'}", "SMS_105420034");
							}
						}
					}
					 if(code!=null&&"OK".equals(code)) {
						 if(nowTime>nextTime&&nowTime<lastTime){
							BrgHealthStatisticDao.getInstance().addBugMessageMonitor(brgNo,sdf.format(new Date()),straincMsgR,"红色告警","8");
						 }
						}
				}
				if(null!=straincMsg_warn.get("黄色告警")&&straincMsg_warn.get("黄色告警").size()>0){
					String straincMsgY =straincMsg_warn.get("黄色告警").toString();//混凝土黄色告警 8
					String phone=BrgMonitorDao.getIntance().selectByItem(brgNo, "黄色告警", "混凝土应变");
					boolean ifSend = BrgHealthStatisticDao.getInstance().selectIfSend(brgNo, sdf.format(new Date()), straincMsgY,"黄色告警","8");
					String code = "";
					Date date = new Date();
					boolean isInDate =ReadFileUtil.isInDate(date);
					if (!ifSend) {
						if(isInDate){
							List<String> newTimeList=BrgHealthStatisticDao.getInstance().selectBrgLastTimeXN(brgNo, "黄色告警", "8");
							if(newTimeList.size()>0){
								long newTime=sdf1.parse(newTimeList.get(0)+" 09-00-00").getTime();
								if((nowTime-newTime)>302400000){
									code =  ShortMessageServiceV2.getInstance().SendMessage(""+phone+"","{bridge:'"+brg_name+"健康监测系统',alarm:'黄色',serialnum:'"+straincMsgY+"',param:'局部响应-混凝土应变'}", "SMS_105420034");
								}

							}else{
								code =  ShortMessageServiceV2.getInstance().SendMessage(""+phone+"","{bridge:'"+brg_name+"健康监测系统',alarm:'黄色',serialnum:'"+straincMsgY+"',param:'局部响应-混凝土应变'}", "SMS_105420034");
							}
						}
					}
					 if(code!=null&&"OK".equals(code)) {
						 if(nowTime>nextTime&&nowTime<lastTime){
							BrgHealthStatisticDao.getInstance().addBugMessageMonitor(brgNo,sdf.format(new Date()),straincMsgY,"黄色告警","8");
						 }
						}
				}
				if(null!=strainsMsg_unusual.get("异常")&&strainsMsg_unusual.get("异常").size()>0){
					String strainsMsgU =  strainsMsg_unusual.get("异常").toString();//钢应变异常 9
					String phone=BrgMonitorDao.getIntance().selectByItem(brgNo, "数据异常", "钢应变");
					boolean ifSend = BrgHealthStatisticDao.getInstance().selectIfSend(brgNo, sdf.format(new Date()), strainsMsgU,"数据异常","9");
					String code = "";
					Date date = new Date();
					boolean isInDate =ReadFileUtil.isInDate(date);
					if (!ifSend) {
						if(isInDate){
							List<String> newTimeList=BrgHealthStatisticDao.getInstance().selectBrgLastTimeXN(brgNo, "数据异常", "9");
                            if(newTimeList.size()>0){
                            	long newTime=sdf1.parse(newTimeList.get(0)+" 09-00-00").getTime();
								if((nowTime-newTime)>302400000){
									code =  ShortMessageServiceV2.getInstance().SendMessage(""+phone+"","{system:'"+brg_name+"健康监测系统',serialnum:'"+strainsMsgU+"',param:'局部响应-钢应变'}", "SMS_105420026");
								}
                            }else{
                            	code =  ShortMessageServiceV2.getInstance().SendMessage(""+phone+"","{system:'"+brg_name+"健康监测系统',serialnum:'"+strainsMsgU+"',param:'局部响应-钢应变'}", "SMS_105420026");
                            }
						}
					}
					 if(code!=null&&"OK".equals(code)) {
						 if(nowTime>nextTime&&nowTime<lastTime){
							BrgHealthStatisticDao.getInstance().addBugMessageMonitor(brgNo,sdf.format(new Date()),strainsMsgU,"数据异常","9");
						 }
						}
				}
				 if(null!=strainsMsg_warn.get("红色告警")&&strainsMsg_warn.get("红色告警").size()>0){
					 String strainsMsgR =   strainsMsg_warn.get("红色告警").toString();//钢应变红色告警 9
					 String phone=BrgMonitorDao.getIntance().selectByItem(brgNo, "红色告警", "钢应变");
					 boolean ifSend = BrgHealthStatisticDao.getInstance().selectIfSend(brgNo, sdf.format(new Date()),strainsMsgR,"红色告警","9");
					 String code = "";
						Date date = new Date();
						boolean isInDate =ReadFileUtil.isInDate(date);
					 if (!ifSend) {
						 if(isInDate){
								List<String> newTimeList=BrgHealthStatisticDao.getInstance().selectBrgLastTimeXN(brgNo, "红色告警", "9");
								if(newTimeList.size()>0){
									long newTime=sdf1.parse(newTimeList.get(0)+" 09-00-00").getTime();
									if((nowTime-newTime)>302400000){
										code = ShortMessageServiceV2.getInstance().SendMessage(""+phone+"","{bridge:'"+brg_name+"健康监测系统',alarm:'红色',serialnum:'"+strainsMsgR+"',param:'局部响应-钢应变'}", "SMS_105420034");
									}
								}else{
									code = ShortMessageServiceV2.getInstance().SendMessage(""+phone+"","{bridge:'"+brg_name+"健康监测系统',alarm:'红色',serialnum:'"+strainsMsgR+"',param:'局部响应-钢应变'}", "SMS_105420034");
								}
						 }
					}
					 if(code!=null&&"OK".equals(code)) {
						 if(nowTime>nextTime&&nowTime<lastTime){
							BrgHealthStatisticDao.getInstance().addBugMessageMonitor(brgNo,sdf.format(new Date()),strainsMsgR,"红色告警","9");
						 }
						}
				 }
				 if(null!=strainsMsg_warn.get("黄色告警")&&strainsMsg_warn.get("黄色告警").size()>0){
					 String  strainsMsgY = strainsMsg_warn.get("黄色告警").toString();//钢应变黄色告警 9
					 String phone=BrgMonitorDao.getIntance().selectByItem(brgNo, "黄色告警", "钢应变");
					 boolean ifSend = BrgHealthStatisticDao.getInstance().selectIfSend(brgNo, sdf.format(new Date()),strainsMsgY,"黄色告警","9");
					 String code = "";
					 Date date = new Date();
					 boolean isInDate =ReadFileUtil.isInDate(date);
					 if (!ifSend) {
						 if(isInDate){
							 List<String> newTimeList=BrgHealthStatisticDao.getInstance().selectBrgLastTimeXN(brgNo, "黄色告警", "9");
								if(newTimeList.size()>0){
									long newTime=sdf1.parse(newTimeList.get(0)+" 09-00-00").getTime();
									if((nowTime-newTime)>302400000){
										code = ShortMessageServiceV2.getInstance().SendMessage(""+phone+"","{bridge:'"+brg_name+"健康监测系统',alarm:'黄色',serialnum:'"+strainsMsgY+"',param:'局部响应-钢应变'}", "SMS_105420034");
									}
								}else{
									code = ShortMessageServiceV2.getInstance().SendMessage(""+phone+"","{bridge:'"+brg_name+"健康监测系统',alarm:'黄色',serialnum:'"+strainsMsgY+"',param:'局部响应-钢应变'}", "SMS_105420034");
								}
							}
					 }
					 if(code!=null&&"OK".equals(code)) {
						 if(nowTime>nextTime&&nowTime<lastTime){
							BrgHealthStatisticDao.getInstance().addBugMessageMonitor(brgNo,sdf.format(new Date()),strainsMsgY,"黄色告警","9");
						 }
						}
				 }
				if(null!=tempMsg_unusual.get("异常")&&tempMsg_unusual.get("异常").size()>0){
					String tempMsgU =  tempMsg_unusual.get("异常").toString();//温度异常 3
					 String phone=BrgMonitorDao.getIntance().selectByItem(brgNo, "数据异常", "温度");
					 boolean ifSend = BrgHealthStatisticDao.getInstance().selectIfSend(brgNo, sdf.format(new Date()),tempMsgU,"数据异常","3");
					 String code = "";
					 if (!ifSend) {
						 List<String> newTimeList=BrgHealthStatisticDao.getInstance().selectBrgLastTimeXN(brgNo, "数据异常","3");
							if(newTimeList.size()>0){
								long newTime=sdf1.parse(newTimeList.get(0)+" 09-00-00").getTime();
								if((nowTime-newTime)>302400000){
									code = ShortMessageServiceV2.getInstance().SendMessage(""+phone+"","{system:'"+brg_name+"健康监测系统',serialnum:'"+tempMsgU+"',param:'荷载与环境-温度'}", "SMS_105420026");
								}
							}else{
								code = ShortMessageServiceV2.getInstance().SendMessage(""+phone+"","{system:'"+brg_name+"健康监测系统',serialnum:'"+tempMsgU+"',param:'荷载与环境-温度'}", "SMS_105420026");
							}
					}
					 if(code!=null&&"OK".equals(code)) {
						 if(nowTime>nextTime&&nowTime<lastTime){
							BrgHealthStatisticDao.getInstance().addBugMessageMonitor(brgNo,sdf.format(new Date()),tempMsgU,"数据异常","3");
						 }
						}
				}
				 if(null!=tempMsg_warn.get("红色告警")&&tempMsg_warn.get("红色告警").size()>0){
					 String tempMsgR =  tempMsg_warn.get("红色告警").toString();//温度红色告警 3
					 String phone=BrgMonitorDao.getIntance().selectByItem(brgNo, "红色告警", "温度");
					 boolean ifSend = BrgHealthStatisticDao.getInstance().selectIfSend(brgNo, sdf.format(new Date()),tempMsgR,"红色告警","3");
					 String code = "";
					 Date date = new Date();
					 boolean isInDate =ReadFileUtil.isInDate(date);
					 if (!ifSend) {
						 if(isInDate){
							 List<String> newTimeList=BrgHealthStatisticDao.getInstance().selectBrgLastTimeXN(brgNo, "红色告警","3");
								if(newTimeList.size()>0){
									long newTime=sdf1.parse(newTimeList.get(0)+" 09-00-00").getTime();
									if((nowTime-newTime)>302400000){
										code = ShortMessageServiceV2.getInstance().SendMessage(""+phone+"","{bridge:'"+brg_name+"健康监测系统',alarm:'红色',serialnum:'"+tempMsgR+"',param:'荷载与环境-温度'}", "SMS_105420034");
									}
								}else{
									code = ShortMessageServiceV2.getInstance().SendMessage(""+phone+"","{bridge:'"+brg_name+"健康监测系统',alarm:'红色',serialnum:'"+tempMsgR+"',param:'荷载与环境-温度'}", "SMS_105420034");
								}
						 }
					}
					 if(code!=null&&"OK".equals(code)) {
						 if(nowTime>nextTime&&nowTime<lastTime){
							BrgHealthStatisticDao.getInstance().addBugMessageMonitor(brgNo,sdf.format(new Date()),tempMsgR,"红色告警","3");
						 }
						}
				 }
				 if(null!=tempMsg_warn.get("黄色告警")&&tempMsg_warn.get("黄色告警").size()>0){
					 String tempMsgY =  tempMsg_warn.get("黄色告警").toString();//温度黄色告警 3
					 String phone=BrgMonitorDao.getIntance().selectByItem(brgNo, "黄色告警", "温度");
					 boolean ifSend = BrgHealthStatisticDao.getInstance().selectIfSend(brgNo, sdf.format(new Date()),tempMsgY,"黄色告警","3");
					 String code = "";
					 Date date = new Date();
					 boolean isInDate =ReadFileUtil.isInDate(date);
					 if (!ifSend) {
						 if(isInDate){
							 List<String> newTimeList=BrgHealthStatisticDao.getInstance().selectBrgLastTimeXN(brgNo, "黄色告警","3");
								if(newTimeList.size()>0){
									long newTime=sdf1.parse(newTimeList.get(0)+" 09-00-00").getTime();
									if((nowTime-newTime)>302400000){
										code =  ShortMessageServiceV2.getInstance().SendMessage(""+phone+"","{bridge:'"+brg_name+"健康监测系统',alarm:'黄色',serialnum:'"+tempMsgY+"',param:'荷载与环境-温度'}", "SMS_105420034");
									}
								}else{
									code =  ShortMessageServiceV2.getInstance().SendMessage(""+phone+"","{bridge:'"+brg_name+"健康监测系统',alarm:'黄色',serialnum:'"+tempMsgY+"',param:'荷载与环境-温度'}", "SMS_105420034");
								}
						 }
					}
					 if(code!=null&&"OK".equals(code)) {
						 if(nowTime>nextTime&&nowTime<lastTime){
							BrgHealthStatisticDao.getInstance().addBugMessageMonitor(brgNo,sdf.format(new Date()),tempMsgY,"黄色告警","3");
						 }
						}
				 }
				if(null!=cableforceMsg_unusual.get("异常")&&cableforceMsg_unusual.get("异常").size()>0){
					String cableforceMsgU =  cableforceMsg_unusual.get("异常").toString();//索力异常 10
					 String phone=BrgMonitorDao.getIntance().selectByItem(brgNo, "数据异常", "索力");
					 boolean ifSend = BrgHealthStatisticDao.getInstance().selectIfSend(brgNo, sdf.format(new Date()),cableforceMsgU,"数据异常","10");
					 String code = "";
					 Date date = new Date();
					 boolean isInDate =ReadFileUtil.isInDate(date);
					 if (!ifSend) {
						 if(isInDate){
							 List<String> newTimeList=BrgHealthStatisticDao.getInstance().selectBrgLastTimeXN(brgNo, "数据异常","10");
								if(newTimeList.size()>0){
									long newTime=sdf1.parse(newTimeList.get(0)+" 09-00-00").getTime();
									if((nowTime-newTime)>302400000){
										code=  ShortMessageServiceV2.getInstance().SendMessage(""+phone+"","{system:'"+brg_name+"健康监测系统',serialnum:'"+cableforceMsgU+"',param:'局部响应-索力'}", "SMS_105420026");
									}
								}else{
									code=  ShortMessageServiceV2.getInstance().SendMessage(""+phone+"","{system:'"+brg_name+"健康监测系统',serialnum:'"+cableforceMsgU+"',param:'局部响应-索力'}", "SMS_105420026");
								}
						 }
					}
					 if(code!=null&&"OK".equals(code)) {
						 if(nowTime>nextTime&&nowTime<lastTime){
							BrgHealthStatisticDao.getInstance().addBugMessageMonitor(brgNo,sdf.format(new Date()),cableforceMsgU,"数据异常","10");
						 }
						}
				}
				if(null!=cableforceMsg_warn.get("红色告警")&&cableforceMsg_warn.get("红色告警").size()>0){
					String cableforceMsgR =  cableforceMsg_warn.get("红色告警").toString();//索力红色告警 10
					String phone=BrgMonitorDao.getIntance().selectByItem(brgNo, "红色告警", "索力");
					Date date = new Date();
					boolean isInDate =ReadFileUtil.isInDate(date);
					boolean ifSend = BrgHealthStatisticDao.getInstance().selectIfSend(brgNo, sdf.format(new Date()),cableforceMsgR,"红色告警","10");
					 String code = "";
					 if (!ifSend) {
						 if(isInDate){
							 List<String> newTimeList=BrgHealthStatisticDao.getInstance().selectBrgLastTimeXN(brgNo, "红色告警","10");
								if(newTimeList.size()>0){
									long newTime=sdf1.parse(newTimeList.get(0)+" 09-00-00").getTime();
									if((nowTime-newTime)>302400000){
										code = ShortMessageServiceV2.getInstance().SendMessage(""+phone+"","{bridge:'"+brg_name+"健康监测系统',alarm:'红色',serialnum:'"+cableforceMsgR+"',param:'局部响应-索力'}", "SMS_105420034");
									}
								}else{
									code = ShortMessageServiceV2.getInstance().SendMessage(""+phone+"","{bridge:'"+brg_name+"健康监测系统',alarm:'红色',serialnum:'"+cableforceMsgR+"',param:'局部响应-索力'}", "SMS_105420034");
								}
							}
					}
					 if(code!=null&&"OK".equals(code)) {
						 if(nowTime>nextTime&&nowTime<lastTime){
							BrgHealthStatisticDao.getInstance().addBugMessageMonitor(brgNo,sdf.format(new Date()),cableforceMsgR,"红色告警","10");
						 }
						}
				}
				 if(null!=cableforceMsg_warn.get("黄色告警")&&cableforceMsg_warn.get("黄色告警").size()>0){
					 String cableforceMsgY =  cableforceMsg_warn.get("黄色告警").toString();//索力黄色告警 10
					 String phone=BrgMonitorDao.getIntance().selectByItem(brgNo, "黄色告警", "索力");
					 boolean ifSend = BrgHealthStatisticDao.getInstance().selectIfSend(brgNo, sdf.format(new Date()),cableforceMsgY,"黄色告警","10");
					 String code = "";
					 Date date = new Date();
					 boolean isInDate =ReadFileUtil.isInDate(date);
					 if (!ifSend) {
						 if(isInDate){
							 List<String> newTimeList=BrgHealthStatisticDao.getInstance().selectBrgLastTimeXN(brgNo, "黄色告警","10");
								if(newTimeList.size()>0){
									long newTime=sdf1.parse(newTimeList.get(0)+" 09-00-00").getTime();
									if((nowTime-newTime)>302400000){
										code = ShortMessageServiceV2.getInstance().SendMessage(""+phone+"","{bridge:'"+brg_name+"健康监测系统',alarm:'黄色',serialnum:'"+cableforceMsgY+"',param:'局部响应-索力'}", "SMS_105420034");
									}
								}else{
									code = ShortMessageServiceV2.getInstance().SendMessage(""+phone+"","{bridge:'"+brg_name+"健康监测系统',alarm:'黄色',serialnum:'"+cableforceMsgY+"',param:'局部响应-索力'}", "SMS_105420034");
								}
						 }
					}
					 if(code!=null&&"OK".equals(code)) {
						 if(nowTime>nextTime&&nowTime<lastTime){
							BrgHealthStatisticDao.getInstance().addBugMessageMonitor(brgNo,sdf.format(new Date()),cableforceMsgY,"黄色告警","10");
						 }
						}
				 }
				 if(null!=staticdispMsg_unusual.get("异常")&&staticdispMsg_unusual.get("异常").size()>0){
						String staticdispMsgU =  staticdispMsg_unusual.get("异常").toString();//索力异常 10
						 String phone=BrgMonitorDao.getIntance().selectByItem(brgNo, "数据异常", "静位移");
						 boolean ifSend = BrgHealthStatisticDao.getInstance().selectIfSend(brgNo, sdf.format(new Date()),staticdispMsgU,"数据异常","11");
						 String code = "";
						 Date date = new Date();
						 boolean isInDate =ReadFileUtil.isInDate(date);
						 if (!ifSend) {
							 if(isInDate){
								 List<String> newTimeList=BrgHealthStatisticDao.getInstance().selectBrgLastTimeXN(brgNo, "数据异常","11");
									if(newTimeList.size()>0){
										long newTime=sdf1.parse(newTimeList.get(0)+" 09-00-00").getTime();
										if((nowTime-newTime)>302400000){
											code=  ShortMessageServiceV2.getInstance().SendMessage(""+phone+"","{system:'"+brg_name+"健康监测系统',serialnum:'"+staticdispMsgU+"',param:'局部响应-索力'}", "SMS_105420026");
										}
									}else{
										code=  ShortMessageServiceV2.getInstance().SendMessage(""+phone+"","{system:'"+brg_name+"健康监测系统',serialnum:'"+staticdispMsgU+"',param:'局部响应-索力'}", "SMS_105420026");
									}
							 }
						}
						 if(code!=null&&"OK".equals(code)) {
							 if(nowTime>nextTime&&nowTime<lastTime){
								BrgHealthStatisticDao.getInstance().addBugMessageMonitor(brgNo,sdf.format(new Date()),staticdispMsgU,"数据异常","11");
							 }
							}
					}
					if(null!=staticdispMsg_warn.get("红色告警")&&staticdispMsg_warn.get("红色告警").size()>0){
						String staticdispMsgR =  staticdispMsg_warn.get("红色告警").toString();//索力红色告警 10
						String phone=BrgMonitorDao.getIntance().selectByItem(brgNo, "红色告警", "静位移");
						 boolean ifSend = BrgHealthStatisticDao.getInstance().selectIfSend(brgNo, sdf.format(new Date()),staticdispMsgR,"红色告警","11");
						 Date date = new Date();
						 boolean isInDate =ReadFileUtil.isInDate(date);
						 String code = "";
						 if (!ifSend) {
							 if(isInDate){
								 List<String> newTimeList=BrgHealthStatisticDao.getInstance().selectBrgLastTimeXN(brgNo, "红色告警","11");
									if(newTimeList.size()>0){
										long newTime=sdf1.parse(newTimeList.get(0)+" 09-00-00").getTime();
										if((nowTime-newTime)>302400000){
											code = ShortMessageServiceV2.getInstance().SendMessage(""+phone+"","{bridge:'"+brg_name+"健康监测系统',alarm:'红色',serialnum:'"+staticdispMsgR+"',param:'局部响应-索力'}", "SMS_105420034");
										}
									}else{
										code = ShortMessageServiceV2.getInstance().SendMessage(""+phone+"","{bridge:'"+brg_name+"健康监测系统',alarm:'红色',serialnum:'"+staticdispMsgR+"',param:'局部响应-索力'}", "SMS_105420034");
									}
							 }
						}
						 
						 if(code!=null&&"OK".equals(code)) {
							 if(nowTime>nextTime&&nowTime<lastTime){
								BrgHealthStatisticDao.getInstance().addBugMessageMonitor(brgNo,sdf.format(new Date()),staticdispMsgR,"红色告警","11");
							 }
							}
					}
					 if(null!=staticdispMsg_warn.get("黄色告警")&&staticdispMsg_warn.get("黄色告警").size()>0){
						 String staticdispMsgY =  staticdispMsg_warn.get("黄色告警").toString();//索力黄色告警 10
						 String phone=BrgMonitorDao.getIntance().selectByItem(brgNo, "黄色告警", "静位移");
						 boolean ifSend = BrgHealthStatisticDao.getInstance().selectIfSend(brgNo, sdf.format(new Date()),staticdispMsgY,"黄色告警","11");
						 String code = "";
						 Date date = new Date();
						 boolean isInDate =ReadFileUtil.isInDate(date);
						 if (!ifSend) {
							 if(isInDate){
								 List<String> newTimeList=BrgHealthStatisticDao.getInstance().selectBrgLastTimeXN(brgNo, "黄色告警","11");
									if(newTimeList.size()>0){
										long newTime=sdf1.parse(newTimeList.get(0)+" 09-00-00").getTime();
										if((nowTime-newTime)>302400000){
											code = ShortMessageServiceV2.getInstance().SendMessage(""+phone+"","{bridge:'"+brg_name+"健康监测系统',alarm:'黄色',serialnum:'"+staticdispMsgY+"',param:'整体响应-静位移'}", "SMS_105420034");
										}
									}else{
										code = ShortMessageServiceV2.getInstance().SendMessage(""+phone+"","{bridge:'"+brg_name+"健康监测系统',alarm:'黄色',serialnum:'"+staticdispMsgY+"',param:'整体响应-静位移'}", "SMS_105420034");
									}
							 }
						}
						 if(code!=null&&"OK".equals(code)) {
							 if(nowTime>nextTime&&nowTime<lastTime){
								BrgHealthStatisticDao.getInstance().addBugMessageMonitor(brgNo,sdf.format(new Date()),staticdispMsgY,"黄色告警","11");
							 }
							}
					 }
				}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				br.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return value;
	}
	
	
	private static String[] getNewStringArr(String[] newArr,String[] oldArr_B){
		for(int i=0;i<oldArr_B.length;i++){
			switch(oldArr_B[oldArr_B.length-1])
			{
			case "0":oldArr_B[oldArr_B.length-1]="异常";
					break;
			case "1":oldArr_B[oldArr_B.length-1]="正常";
			        break;
			case "2":oldArr_B[oldArr_B.length-1]="黄色告警";
				    break;
			case "3":oldArr_B[oldArr_B.length-1]="红色告警";
			        break;
			}
			newArr[i+3]=oldArr_B[i];
		}
		return newArr;
	}
	
	private static String getMsg(String[] oldArr_B){
			switch(oldArr_B[oldArr_B.length-1])
			{
			case "0":oldArr_B[oldArr_B.length-1]="异常";
					break;
			case "1":oldArr_B[oldArr_B.length-1]="正常";
			        break;
			case "2":oldArr_B[oldArr_B.length-1]="黄色告警";
				    break;
			case "3":oldArr_B[oldArr_B.length-1]="红色告警";
			        break;
			}
		return oldArr_B[oldArr_B.length-1];
	}
	
}
