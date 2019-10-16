package hs.bm.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.authz.annotation.RequiresGuest;

import com.alibaba.druid.sql.visitor.functions.If;
import com.sun.crypto.provider.RSACipher;
import com.sun.java.swing.plaf.motif.resources.motif;

import hs.bm.bean.BrgCardAdminId;
import hs.bm.bean.BrgGpsStatistic;
import hs.bm.bean.BrgHealthStatistic;
import hs.bm.bean.BrgMonitorTemp;
import hs.bm.bean.BrgStaticStatistic;
import hs.bm.bean.BrgSystem;
import hs.bm.bean.BrgWeightStatistic;
import hs.bm.bean.BrgWindStatistic;
import hs.bm.bean.Connect;
import hs.bm.bean.DataFull;
import hs.bm.util.TimeFormatUtil;
import hs.bm.util.ToJSONStr;
import hs.bm.vo.BrgCdyc;
import hs.bm.vo.FileTimeSize;
import hs.bm.vo.ManageSectionBridge;

public class GetFileSizeDao {
	public static boolean flag=false;
	private static GetFileSizeDao getFileSizeDao;
	public static GetFileSizeDao getInstance(){
		if(getFileSizeDao==null){
			getFileSizeDao=new GetFileSizeDao();
		}
		return getFileSizeDao;
	}
	
	public String getCdDescribe(String id,String cd){
		String describe="";
		String sql="SELECT cd_describe FROM test_point where td_code=? and bridge_id=?";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection(),false);
		ResultSet rs=dataOperation.executeQuery(sql,new Object[]{cd,id});
		try {
			while(rs.next()){
				describe=rs.getString("cd_describe"); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return describe;
	}
	public Integer updateCdStatus(String status,String id,String cd){
		String sql="update test_point set yj_status = ? where td_code=? and bridge_id=?";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection(),false);
		int rs=dataOperation.executeUpdate(sql,new Object[]{status,cd,id});
		dataOperation.close();
		return rs;
	}
	public List<BrgCardAdminId> getBrgIdByMode(String mode,String manage_id){
		List<BrgCardAdminId> list=new ArrayList<>();
		String sql="SELECT DISTINCT bs.bridge_id,bcai.bridge_name,bcai.bridge_no FROM brg_system bs left join brg_card_admin_id bcai on bs.bridge_id = bcai.bridge_id where bs.mode=? and bcai.manage_id like ? ";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection(),false);
		ResultSet rs=dataOperation.executeQuery(sql,new Object[]{mode,manage_id});
		try {
			while(rs.next()){
				BrgCardAdminId brgCardAdminId = new BrgCardAdminId();
				String brgId=rs.getString("bridge_id");
				brgCardAdminId.setBridge_id(brgId);
				brgCardAdminId.setBridge_name(rs.getString("bridge_name"));
				brgCardAdminId.setBridge_no(rs.getString("bridge_no"));
				list.add(brgCardAdminId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}
	
	public String getWeightYcById(String id){
		String all="";
		String yc="%异常%";
		String sql="select * from ( "+
						" SELECT a.load_radio8,a.load_radio16 FROM brg_weight_load_radio as a "+
						" where a.brg_id=( "+
						" select bridge_no from brg_card_admin_id where bridge_id=? "+
						" ) ORDER BY a.brg_startime desc limit 1)as c where c.load_radio8 like ? or c.load_radio16 like ?";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection(),false);
		ResultSet rs=dataOperation.executeQuery(sql,new Object[]{id,yc,yc});
		try {
			while(rs.next()){
				String load_radio8=rs.getString("load_radio8");
				String load_radio16=rs.getString("load_radio16");
				all=load_radio8+";"+load_radio16;
				all=all.replace("正常", "").replace("异常", "");
				if(all.startsWith(";")){
					all=all.substring(1, all.length());
				}
				if(all.endsWith(";")){
					all=all.substring(0, all.length()-1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return all;
	}
	public List<BrgCdyc> getabnormalYcNew(String tableName,String zoneId){
		List<BrgCdyc> list = new ArrayList<>();
		String sql=" select * from "+tableName+" a LEFT JOIN brg_card_admin_id b on a.brg_id = b.bridge_id where b.zone_id like ? ";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection(),false);
		ResultSet rs=dataOperation.executeQuery(sql,new String[]{zoneId});
		try {
			while(rs.next()){
				BrgCdyc brgCdyc = new BrgCdyc();
				brgCdyc.setBrgName(rs.getString("brg_name"));
				brgCdyc.setYcAddr(rs.getString("sort"));
				brgCdyc.setYcStates("异常");
				list.add(brgCdyc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}
	public List<BrgCdyc> getalarmsumYcNew(String zoneId){
		List<BrgCdyc> list = new ArrayList<>();
		String sql=" select * from brg_monitor_alarmsum a LEFT JOIN brg_card_admin_id b on a.brg_id = b.bridge_id where b.zone_id like ? ";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection(),false);
		ResultSet rs=dataOperation.executeQuery(sql,new String[]{zoneId});
		try {
			while(rs.next()){
				BrgCdyc brgCdyc = new BrgCdyc();
				brgCdyc.setBrgName(rs.getString("brg_name"));
				brgCdyc.setYcAddr(rs.getString("sort"));
				brgCdyc.setYcStates(rs.getString("type"));
				list.add(brgCdyc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}
	public String getBrgNo(String id){
		String mid="";
		String sql=" select bridge_no from brg_card_admin_id where bridge_id=? ";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection(),false);
		ResultSet rs=dataOperation.executeQuery(sql,new Object[]{id});
		try {
			while(rs.next()){
				mid=rs.getString("bridge_no");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return mid;
	}
	
	public List<Integer> getheightSort(String tableName,String mid,String yc){
		List<Integer> list=new ArrayList<>();
		String sql="SELECT sort FROM "+tableName+" where time=(select time from "+tableName+" where monitor_id=? "+
						  "  GROUP BY time ORDER BY time desc limit 1) and type=?";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection(),false);
		ResultSet rs=dataOperation.executeQuery(sql,new Object[]{mid,yc});
		try {
			while(rs.next()){
				int sort=rs.getInt("sort");
				list.add(sort);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}
	public List<BrgMonitorTemp> getheightSortStatus(String tableName,String mid){
		List<BrgMonitorTemp> list=new ArrayList<>();
		String sql="SELECT sort,type FROM "+tableName+" where time=(select time from "+tableName+" where monitor_id=? "+
						  "  GROUP BY time ORDER BY time desc limit 1) ";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection(),false);
		ResultSet rs=dataOperation.executeQuery(sql,new Object[]{mid});
		try {
			while(rs.next()){
				BrgMonitorTemp brgMonitorTemp = new BrgMonitorTemp();
				int sort=rs.getInt("sort");
				brgMonitorTemp.setSort(sort);
				brgMonitorTemp.setType(rs.getString("type"));
				list.add(brgMonitorTemp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}
    public Map<String, List<FileTimeSize>> GetFileSize(int second){
    	Map<String, List<FileTimeSize>> map=new HashMap<String, List<FileTimeSize>>();
    	List<FileTimeSize> lf=new ArrayList<FileTimeSize>();
    	 MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection(),false);
    	String sql="SELECT file_size,file_time from brg_weight_statistic where TO_SECONDS(NOW())-TO_SECONDS(file_time)<=?";
    	ResultSet rs=dataOperation.executeQuery(sql,new Object[]{second});
    	FileTimeSize f1=new FileTimeSize();
    	FileTimeSize f2=new FileTimeSize();
    	long l1=0;
    	long l2=0;
    	try {
			while (rs.next()) {
				long l=rs.getLong("file_size");
				l1+=l;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	f1.setSys("w");
    	f1.setFilesize(l1);
    	f1.setSecond(second);
    	sql="SELECT file_size,file_time from brg_health_statistic where TO_SECONDS(NOW())-TO_SECONDS(file_time)<=?";
    	rs=dataOperation.executeQuery(sql,new Object[]{second});
    	try {
			while (rs.next()) {
				long l=rs.getLong("file_size");
				l2+=l;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	f2.setFilesize(l2);
    	f2.setSecond(second);
    	f2.setSys("h");
    	lf.add(f1);
    	lf.add(f2);
    	dataOperation.close();
    	map.put("data", lf);
    	return map;
    	
    }
    public List<BrgWeightStatistic> GetWeightData(String brg_id){
    	List<BrgWeightStatistic> lf=new ArrayList<BrgWeightStatistic>();
   	 MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection(),false);
   	String sql="SELECT a.*,b.bridge_no from brg_weight_statistic as a,brg_card_admin_id as b where a.bridge_id=? and a.bridge_id=b.bridge_id order by start_time desc";
   	ResultSet rs=dataOperation.executeQuery(sql,new Object[]{brg_id});
   	try {
		while (rs.next()) {
			BrgWeightStatistic bw=new BrgWeightStatistic();
			bw.setFile_time(rs.getString("file_time").substring(0, 19));
			bw.setFile_size(rs.getInt("file_size"));
			bw.setStart_time(rs.getString("start_time"));
			bw.setEnd_time(rs.getString("end_time"));
			bw.setBridge_id(rs.getString("bridge_id"));
			bw.setBridge_no(rs.getString("bridge_no"));
			bw.setData_file(rs.getString("data_file"));
			bw.setIs_download(rs.getInt("is_download"));
			lf.add(bw);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
   	dataOperation.close();
   	return lf;
    }
    public List<BrgHealthStatistic> GetHealthData(String brg_id){
    	List<BrgHealthStatistic> lf=new ArrayList<BrgHealthStatistic>();
   	 MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection(),false);
   	String sql="SELECT a.*,b.bridge_no from brg_health_statistic as a,brg_card_admin_id as b where a.bridge_id=? and a.bridge_id=b.bridge_id order by start_time desc";
   	ResultSet rs=dataOperation.executeQuery(sql,new Object[]{brg_id});
   	try {
		while (rs.next()) {
			BrgHealthStatistic bw=new BrgHealthStatistic();
			bw.setFile_time(rs.getString("file_time").substring(0, 19));
			bw.setFile_size(rs.getInt("file_size"));
			bw.setStart_time(rs.getString("start_time"));
			bw.setEnd_time(rs.getString("end_time"));
			bw.setBridge_id(rs.getString("bridge_id"));
			bw.setBridge_no(rs.getString("bridge_no"));
			bw.setData_file(rs.getString("data_file"));
			bw.setIs_download(rs.getInt("is_download"));
			lf.add(bw);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
   	dataOperation.close();
   	return lf;
    }
    
    public List<BrgGpsStatistic> GetGpsDataS(String brg_id){
    	List<BrgGpsStatistic> lf=new ArrayList<BrgGpsStatistic>();
   	 MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection(),false);
   	String sql="SELECT a.*,b.bridge_no from brg_gps_statistic as a,brg_card_admin_id as b where a.bridge_id=? and a.bridge_id=b.bridge_id order by start_time desc";
   	ResultSet rs=dataOperation.executeQuery(sql,new Object[]{brg_id});
   	try {
		while (rs.next()) {
			BrgGpsStatistic bw=new BrgGpsStatistic();
			bw.setFile_time(rs.getString("file_time").substring(0, 19));
			bw.setFile_size(rs.getInt("file_size"));
			bw.setStart_time(rs.getString("start_time"));
			bw.setEnd_time(rs.getString("end_time"));
			bw.setBridge_id(rs.getString("bridge_id"));
			bw.setBridge_no(rs.getString("bridge_no"));
			bw.setData_file(rs.getString("data_file"));
			bw.setIs_download(rs.getInt("is_download"));
			lf.add(bw);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
   	dataOperation.close();
   	return lf;
    }
    public List<BrgGpsStatistic> GetGpsData(String brg_id,String bridge_name){
    	MyDataOperation dataOperation1 = new MyDataOperation(MyDataSource.getInstance().getConnection(),false);
    	String sql1="SELECT brg_pass_no from brg_system  where id=? ";
    	String name = "";
    	ResultSet rs1=dataOperation1.executeQuery(sql1,new Object[]{bridge_name});
    	try {
    		while (rs1.next()) {
    			name = rs1.getString("brg_pass_no");
    		}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	dataOperation1.close();
    	String gps = null;
    	
    	switch (name) {
		case "通道一":
			gps = "GPS1";
			break;
		case "通道二":
			gps = "GPS2";
			break;
		case "通道三":
			gps = "GPS3";
			break;
		case "通道四":
			gps = "GPS4";
			break;
		case "通道五":
			gps = "GPS5";
			break;
		}
    	
    	
    	List<BrgGpsStatistic> lf=new ArrayList<BrgGpsStatistic>();
    	MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection(),false);
   	String sql="SELECT a.*,b.bridge_no from brg_gps_statistic as a,brg_card_admin_id as b where a.bridge_id=? and a.bridge_id=b.bridge_id order by start_time desc";
   	ResultSet rs=dataOperation.executeQuery(sql,new Object[]{brg_id});
   	
   	try {
		while (rs.next()) {
			String dataFile = rs.getString("data_file");
			
			if(dataFile.startsWith(gps)) {
				BrgGpsStatistic bw=new BrgGpsStatistic();
				bw.setFile_time(rs.getString("file_time").substring(0, 19));
				bw.setFile_size(rs.getInt("file_size"));
				bw.setStart_time(rs.getString("start_time"));
				bw.setEnd_time(rs.getString("end_time"));
				bw.setBridge_id(rs.getString("bridge_id"));
				bw.setBridge_no(rs.getString("bridge_no"));
				bw.setData_file(rs.getString("data_file"));
				bw.setIs_download(rs.getInt("is_download"));
				lf.add(bw);
				
			}
			
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
   	dataOperation.close();
   	return lf;
    }
    
    public List<BrgStaticStatistic> GetStaticData(String brg_id){
    	List<BrgStaticStatistic> lf=new ArrayList<BrgStaticStatistic>();
   	 MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection(),false);
   	String sql="SELECT a.*,b.bridge_no from brg_static_statistic as a,brg_card_admin_id as b where a.bridge_id=? and a.bridge_id=b.bridge_id order by start_time desc";
   	ResultSet rs=dataOperation.executeQuery(sql,new Object[]{brg_id});
   	try {
		while (rs.next()) {
			BrgStaticStatistic bw=new BrgStaticStatistic();
			bw.setFile_time(rs.getString("file_time").substring(0, 19));
			bw.setFile_size(rs.getInt("file_size"));
			bw.setStart_time(rs.getString("start_time"));
			bw.setEnd_time(rs.getString("end_time"));
			bw.setBridge_id(rs.getString("bridge_id"));
			bw.setBridge_no(rs.getString("bridge_no"));
			bw.setData_file(rs.getString("data_file"));
			bw.setIs_download(rs.getInt("is_download"));
			lf.add(bw);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
   	dataOperation.close();
   	return lf;
    }
    
    public List<BrgWindStatistic> GetWindData(String brg_id){
    	List<BrgWindStatistic> lf=new ArrayList<BrgWindStatistic>();
   	 MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection(),false);
   	String sql="SELECT a.*,b.bridge_no from brg_wind_statistic as a,brg_card_admin_id as b where a.bridge_id=? and a.bridge_id=b.bridge_id order by start_time desc";
   	ResultSet rs=dataOperation.executeQuery(sql,new Object[]{brg_id});
   	try {
		while (rs.next()) {
			BrgWindStatistic bw=new BrgWindStatistic();
			bw.setFile_time(rs.getString("file_time").substring(0, 19));
			bw.setFile_size(rs.getInt("file_size"));
			bw.setStart_time(rs.getString("start_time"));
			bw.setEnd_time(rs.getString("end_time"));
			bw.setBridge_id(rs.getString("bridge_id"));
			bw.setBridge_no(rs.getString("bridge_no"));
			bw.setData_file(rs.getString("data_file"));
			bw.setIs_download(rs.getInt("is_download"));
			lf.add(bw);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
   	dataOperation.close();
   	return lf;
    }
    
    public List<BrgSystem> getBrgSystemsById(String brg_id,String bgco){
     List<BrgSystem> lf=new ArrayList<BrgSystem>();
	 long time=new Date().getTime();
	 time=time-3600000;
	 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   	 MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection(),false);
   	 String sql="SELECT	brg_system.id,brg_system.`mode`,brg_system.bridge_id,brg_system.out_time,"
   			+ " brg_system.brg_pass_no,brg_system.monitor_starttime,brg_card_admin_id.bridge_name "
   			+ " FROM	brg_system LEFT JOIN brg_card_admin_id "
   			+ " ON brg_system.bridge_id = brg_card_admin_id.bridge_id where brg_system.bridge_id like ? and brg_system.brg_pass_no is not null and brg_system.brg_pass_no != '' order by brg_system.sort  ";
   	ResultSet rs=dataOperation.executeQuery(sql,new Object[]{brg_id});
   	try {
		while (rs.next()) {
			BrgSystem bw=new BrgSystem();
			bw.setBrg_pass_no(rs.getString("brg_pass_no"));
			bw.setId(rs.getString("id"));
			bw.setBridge_id(rs.getString("bridge_id"));
			bw.setBrg_name(rs.getString("bridge_name"));
			bw.setOut_time(bgco);
			lf.add(bw);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
   	dataOperation.close();
   	return lf;
    }
    
    public ArrayList<BrgSystem> getAllSystemBtOneBridge(String brg_id) {
    	ArrayList<BrgSystem> list = new ArrayList<BrgSystem>();
   	 MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection(),false);
   	 String sql = " SELECT DISTINCT mode FROM  brg_system WHERE bridge_id=? ";
   	ResultSet rs=dataOperation.executeQuery(sql,new Object[]{brg_id});
   	try {
		while (rs.next()) {
			BrgSystem entity = new BrgSystem();
			entity.setMode(rs.getString("mode"));
			list.add(entity);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
   		dataOperation.close();
		return list;
    }
    
    public int GetFileInTime(int s,String type){
    	if(!flag){
    		return 1;
    	}
    	String sql="select count(*) from brg_weight_statistic where file_time >? and file_time <= ?";
    	if(type.toLowerCase().contains("weight")){
    		sql="select count(*) from brg_weight_statistic where file_time >? and file_time <= ?";
    	}else{
    		sql="select count(*) from brg_health_statistic where file_time >? and file_time <= ?";
    	}
    	 MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection(),false);
    	 Date d1=new Date();
    	 Date d2=new Date();
    	 d2.setTime(d1.getTime() - s * 1000);
    	 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	 ResultSet rs=dataOperation.executeQuery(sql,new Object[]{format.format(d2),format.format(d1)});
    	 //System.out.println(format.format(d2)+"-----"+format.format(d1));
    	 int i=0;
    	   	try {
    			while (rs.next()) {
    				i=rs.getInt(1);
    			}
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	   	dataOperation.close();
    	   	return i;
    }
    
    public String getMonitor_startTime(String brg_id,String mode){
    	String str = "";
    	if ("weight".equals(mode)) {
			mode = "w";
		}else if("health".equals(mode)) {
			mode = "s";
		}else if("gps".equals(mode)) {
			mode = "g";
		}else if("gpss".equals(mode)) {
			mode = "g";
		}
    	String sql = " SELECT monitor_starttime FROM brg_system WHERE bridge_id=? AND mode =? ";
    	 MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection(),false);
    	ResultSet rs=dataOperation.executeQuery(sql, new String[]{brg_id,mode});
    	try {
			while (rs.next()) {
				str = rs.getString("monitor_starttime");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	dataOperation.close();
		return str;
    	
    }
    public List<BrgSystem> getBrgSystems(String mode){
    	List<BrgSystem> list = new ArrayList<>();
    	String sql = " select DISTINCT * from brg_system where mode=?";
    	MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection(),false);
    	ResultSet rs=dataOperation.executeQuery(sql, new String[]{mode});
    	try {
			while (rs.next()) {
				BrgSystem brgSystem  = new BrgSystem();
				brgSystem.setBridge_id(rs.getString("bridge_id"));
				brgSystem.setDir_name(rs.getString("dir_name"));
				brgSystem.setId(rs.getString("id"));
				brgSystem.setMode(rs.getString("mode"));
				brgSystem.setMonitor_starttime(rs.getString("monitor_starttime"));
				brgSystem.setOut_time(rs.getString("out_time"));
				list.add(brgSystem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	dataOperation.close();
		return list;
    }
    public int updateBrgSystemOutTime(String bridge_id,String out_time,String mode){
    	List<BrgSystem> list = new ArrayList<>();
    	String sql = " update brg_system set out_time=? where bridge_id=? and mode=? ";
    	MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection(),false);
    	int i=dataOperation.executeUpdate(sql, new String[]{out_time,bridge_id,mode});
    	dataOperation.close();
		return i;
    }
    public BrgSystem getBrgSystemByBrgId(String bridge_id){
    	List<BrgSystem> list = new ArrayList<>();
    	String sql = " select * from brg_system where bridge_id=?";
    	MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection(),false);
    	ResultSet rs=dataOperation.executeQuery(sql, new String[]{bridge_id});
    	try {
			while (rs.next()) {
				BrgSystem brgSystem  = new BrgSystem();
				brgSystem.setBridge_id(rs.getString("monitor_starttime"));
				brgSystem.setDir_name(rs.getString("dir_name"));
				brgSystem.setId(rs.getString("id"));
				brgSystem.setMode(rs.getString("mode"));
				brgSystem.setMonitor_starttime(rs.getString("monitor_starttime"));
				brgSystem.setOut_time(rs.getString("out_time"));		
				list.add(brgSystem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	dataOperation.close();
		return list.size()==0 ? null : list.get(0) ;
    }
    public int addBrgSystem(String bridge_id,String mode){
    	Connection conn=MyDataSource.getInstance().getConnection();
    	MyDataOperation mdo=new MyDataOperation(conn,false);
    	String sql="insert into brg_system(id,bridge_id,dir_name,mode) values(?,?,?,?);";
    	if(mode.toLowerCase().equals("s")){
    		sql="insert into brg_system(id,bridge_id,dir_name,mode) values(?,?,"
    				+ "(select CONCAT(bridge_no,'S') FROM brg_card_admin_id where bridge_id=?),?);";
    	}else{
    		sql="insert into brg_system(id,bridge_id,dir_name,mode) values(?,?,"
    				+ "(select CONCAT(bridge_no,'W') FROM brg_card_admin_id where bridge_id=?),?);";
    	}
    	
    	String id=UUID.randomUUID().toString();
		int i=mdo.executeUpdate(sql, new String[]{id,bridge_id,bridge_id,mode});
		mdo.close();
		return i;
    }
    
    public int addBrgSystemWind(String bridge_id,String mode){
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    	Connection conn=MyDataSource.getInstance().getConnection();
    	MyDataOperation mdo=new MyDataOperation(conn,false);
    	String sql="insert into brg_system(id,bridge_id,dir_name,mode,monitor_starttime) values(?,?,(select CONCAT(bridge_no,'F') FROM brg_card_admin_id where bridge_id=?),?,?)";
    	String id=UUID.randomUUID().toString();
		int i=mdo.executeUpdate(sql, new String[]{id,bridge_id,bridge_id,mode,sdf.format(new Date())});
		mdo.close();
		return i;
    }
    
    
    public int addBrgSystemGPS(String bridge_id,String mode){
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    	Connection conn=MyDataSource.getInstance().getConnection();
    	MyDataOperation mdo=new MyDataOperation(conn,false);
    	String sql="insert into brg_system(id,bridge_id,dir_name,mode,monitor_starttime) values(?,?,(select CONCAT(bridge_no,'G') FROM brg_card_admin_id where bridge_id=?),?,?)";
    	int i=0;
		i=mdo.executeUpdate(sql, new String[]{UUID.randomUUID().toString(),bridge_id,bridge_id,mode,sdf.format(new Date())});
		if(i>0){
			for(int j=0;j<4;j++){
				String sql1="insert into brg_system(id,bridge_id,dir_name,mode,monitor_starttime,brg_pass_no) values(?,?,(select CONCAT(bridge_no,'_0"+(j+1)+"G') FROM brg_card_admin_id where bridge_id=?),?,?,?)";
				System.out.println(sql1);
				i=mdo.executeUpdate(sql1, new String[]{UUID.randomUUID().toString(),bridge_id,bridge_id,mode,sdf.format(new Date()),"通道"+(j+1)});
			}
		}
		mdo.close();
		return i;
    }
    
    public List<Connect> initWeightTree(){
    	List<Connect> lc1=new ArrayList<Connect>();
    	Connect con=new Connect();
    	con.setName("动态称重");
    	con.setType("weight");
    	List<Connect> lc=new ArrayList<Connect>();
    	Connection conn=MyDataSource.getInstance().getConnection();
    	MyDataOperation mdo=new MyDataOperation(conn,false);
    	String sql="SELECT bs.*,bh.bridge_id as bgid,bh.end_time from "
    			+ "(select a.bridge_id,a.dir_name,b.bridge_name from "
    			+ "brg_system as a,brg_card_admin_id as b where a.bridge_id=b.bridge_id and mode='w') "
    			+ "as bs LEFT JOIN (SELECT a.bridge_id,a.end_time from "
    			+ "(SELECT * from brg_weight_statistic ORDER BY end_time DESC) "
    			+ "as a GROUP BY a.bridge_id) as bh ON bs.bridge_id=bh.bridge_id";
    	long time=new Date().getTime();
    	time=time-3600000;
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	ResultSet rs=mdo.executeQuery(sql,null);
    	try {
			while (rs.next()) {
				Connect co=new Connect();
				co.setId(rs.getString("bridge_id"));
				co.setName(rs.getString("bridge_name"));
				String end_time=rs.getString("end_time");
				if(rs.getString("bgid")==null||end_time==null){
					co.setBgco("0");
				}else{
					long et=sdf.parse(end_time).getTime();
					if(et<time){
						co.setBgco(end_time);
					}else{
						co.setBgco("1");
					}
				}
				co.setType("bridge");
				lc.add(co);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			mdo.close();
		}
    	con.setChildren(lc);
    	lc1.add(con);
    	return lc1;
    	
    }
    
    public List<Connect> initWeightTree2(String orgid){
    	List<Connect> lc1=new ArrayList<Connect>();
    	Connect con=new Connect();
    	con.setName("动态称重");
    	con.setType("weight");
    	List<Connect> lc=new ArrayList<Connect>();
    	Connection conn=MyDataSource.getInstance().getConnection();
    	MyDataOperation mdo=new MyDataOperation(conn,false);
    	String sql="SELECT\n" +
    			"	bs.*, bh.bridge_id AS bgid,\n" +
    			"	bh.end_time\n" +
    			"FROM\n" +
    			"	(\n" +
    			"		SELECT\n" +
    			"			a.bridge_id,\n" +
    			"			a.dir_name,\n" +
    			"			b.bridge_name\n" +
    			"		FROM\n" +
    			"			brg_system AS a,\n" +
    			"			brg_card_admin_id AS b\n" +
    			"		WHERE\n" +
    			"			a.bridge_id = b.bridge_id\n" +
    			"		AND b.zone_id LIKE ?\n" +
    			"		AND MODE = 'w'\n" +
    			"	) AS bs\n" +
    			"LEFT JOIN (\n" +
    			"	SELECT\n" +
    			"		a.bridge_id,\n" +
    			"		a.end_time\n" +
    			"	FROM\n" +
    			"		(\n" +
    			"			SELECT\n" +
    			"				*\n" +
    			"			FROM\n" +
    			"				brg_weight_statistic\n" +
    			"			ORDER BY\n" +
    			"				end_time DESC\n" +
    			"		) AS a\n" +
    			"	GROUP BY\n" +
    			"		a.bridge_id\n" +
    			") AS bh ON bs.bridge_id = bh.bridge_id";
    	long time=new Date().getTime();
    	time=time-3600000;
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	ResultSet rs=mdo.executeQuery(sql,new String[] {orgid});
    	try {
			while (rs.next()) {
				Connect co=new Connect();
				co.setId(rs.getString("bridge_id"));
				co.setName(rs.getString("bridge_name"));
				String end_time=rs.getString("end_time");
				if(rs.getString("bgid")==null||end_time==null){
					co.setBgco("0");
				}else{
					long et=sdf.parse(end_time).getTime();
					if(et<time){
						co.setBgco(end_time);
					}else{
						co.setBgco("1");
					}
				}
				co.setType("bridge");
				lc.add(co);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			mdo.close();
		}
    	con.setChildren(lc);
    	lc1.add(con);
    	return lc1;
    	
    }
    public List<Connect> initHealthTree(){
    	List<Connect> lc1=new ArrayList<Connect>();
    	Connect con=new Connect();
    	con.setName("健康监测");
    	con.setType("health");
    	List<Connect> lc=new ArrayList<Connect>();
    	Connection conn=MyDataSource.getInstance().getConnection();
    	MyDataOperation mdo=new MyDataOperation(conn,false);
    	String sql="SELECT\n" +
    			"	bs.*, bh.bridge_id AS bgid,\n" +
    			"	bh.end_time\n" +
    			"FROM\n" +
    			"	(\n" +
    			"		SELECT\n" +
    			"			a.bridge_id,\n" +
    			"			a.dir_name,\n" +
    			"			b.bridge_name\n" +
    			"		FROM\n" +
    			"			brg_system AS a,\n" +
    			"			brg_card_admin_id AS b\n" +
    			"		WHERE\n" +
    			"			a.bridge_id = b.bridge_id\n" +
    			"		AND MODE = 's'\n" +
    			"	) AS bs\n" +
    			"LEFT JOIN (\n" +
    			"	SELECT\n" +
    			"		a.bridge_id,\n" +
    			"		a.end_time\n" +
    			"	FROM\n" +
    			"		(\n" +
    			"			SELECT\n" +
    			"				*\n" +
    			"			FROM\n" +
    			"				brg_health_statistic\n" +
    			"			ORDER BY\n" +
    			"				end_time DESC\n" +
    			"		) AS a\n" +
    			"	GROUP BY\n" +
    			"		a.bridge_id\n" +
    			") AS bh ON bs.bridge_id = bh.bridge_id";
    	long time=new Date().getTime();
    	time=time-3600000;
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	ResultSet rs=mdo.executeQuery(sql,null);
    	try {
			while (rs.next()) {
				Connect co=new Connect();
				co.setId(rs.getString("bridge_id"));
				co.setName(rs.getString("bridge_name"));
				co.setType("bridge");
				String end_time=rs.getString("end_time");
				if(rs.getString("bgid")==null||end_time==null){
					co.setBgco("0");
				}else{
					long et=sdf.parse(end_time).getTime();
					if(et<time){
						co.setBgco(end_time);
					}else{
						co.setBgco("1");
					}
				}
				lc.add(co);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			mdo.close();
		}
    	con.setChildren(lc);
    	lc1.add(con);
    	return lc1;
    	
    }
    public List<Connect> initIndexModeMap(String mode,String tableName,String zone_id){
    	List<Connect> lc1=new ArrayList<Connect>();
    	Connect con=new Connect();
    	List<Connect> lc=new ArrayList<Connect>();
    	Connection conn=MyDataSource.getInstance().getConnection();
    	MyDataOperation mdo=new MyDataOperation(conn,false);
    	String sql="SELECT\n" +
    			"	bs.*, bh.bridge_id AS bgid,\n" +
    			"	bh.end_time\n" +
    			"FROM\n" +
    			"	(\n" +
    			"		SELECT\n" +
    			"			a.bridge_id,\n" +
    			"			a.dir_name,\n" +
    			"			b.longitude,\n" +
    			"			b.latitude,\n" +
    			"			b.bridge_name,\n" +
    			"			a.brg_pass_no\n" +
    			"		FROM\n" +
    			"			brg_system AS a\n" +
    			"			 left join brg_card_admin_id AS b\n" +
    			"		on\n" +
    			"			a.bridge_id = b.bridge_id\n" +
    			"		where b.zone_id LIKE ?\n" +
    			"		AND MODE = '"+mode+"'\n" +
    			"	) AS bs\n" +
    			"LEFT JOIN (\n" +
    			"	SELECT\n" +
    			"		a.bridge_id,\n" +
    			"		a.end_time\n" +
    			"	FROM\n" +
    			"		(\n" +
    			"			SELECT\n" +
    			"				*\n" +
    			"			FROM\n" +
    			"				"+tableName+"\n" +
    			"			ORDER BY\n" +
    			"				end_time DESC\n" +
    			"		) AS a\n" +
    			"	GROUP BY\n" +
    			"		a.bridge_id\n" +
    			") AS bh ON bs.bridge_id = bh.bridge_id";
    	long time=new Date().getTime();
    	time=time-3600000*1;
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	ResultSet rs=mdo.executeQuery(sql,new Object[]{zone_id});
    	try {
			while (rs.next()) {
				Connect co=new Connect();
				co.setBridge_id(rs.getString("bridge_id"));
				co.setBridge_name(rs.getString("bridge_name"));
				co.setType("bridge");
				co.setLongitude(rs.getString("longitude"));
				co.setLatitude(rs.getString("latitude"));
				if(mode.equals("w")){
					co.setBrg_type("动态称重");
				}else if(mode.equals("s")){
					co.setBrg_type("健康监测");
				}else if(mode.equals("g")){
					co.setBrg_type("GPS"+rs.getString("brg_pass_no"));
				}else if(mode.equals("f")){
					co.setBrg_type("风速风向");
				}
				/*co.setBrg_type(getBrgTopStructType(rs.getString("bridge_id")));*/
				String end_time=rs.getString("end_time");
				if(rs.getString("bgid")==null||end_time==null){
					co.setBgco("0");
					co.setSort(1);
				}else{
					long et=sdf.parse(end_time).getTime();
					if(et<time){
						//数据异常,已经有 end_time (单位：ms 毫秒)无数据接入
						co.setBgco(end_time);
						co.setSort(0);
					}else{
						//数据正常,有最新数据
						co.setBgco("1");
						co.setSort(2);
					}
				}
				lc.add(co);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			mdo.close();
		}
    	return lc;
    	
    }  
    
    public List<Connect> initIndexModeMapCd(String mode,String tableName,String zone_id){
    	List<Connect> lc=new ArrayList<Connect>();
    	Connection conn=MyDataSource.getInstance().getConnection();
    	MyDataOperation mdo=new MyDataOperation(conn,false);
    	String sql=  "		SELECT distinct\n" +
    			"			a.bridge_id,\n" +
    			"			b.longitude,\n" +
    			"			b.latitude,\n" +
    			"			b.bridge_name\n" +
    			"		FROM\n" +
    			"			brg_system AS a\n" +
    			"			 left join brg_card_admin_id AS b\n" +
    			"		on\n" +
    			"			a.bridge_id = b.bridge_id\n" +
    			"		where b.zone_id LIKE ?\n" +
    			"		AND MODE = '"+mode+"'";
    	long time=new Date().getTime();
    	time=time-3600000;
    	ResultSet rs=mdo.executeQuery(sql,new Object[]{zone_id});
    	try {
			while (rs.next()) {
				Connect co=new Connect();
				co.setBridge_id(rs.getString("bridge_id"));
				co.setBridge_name(rs.getString("bridge_name"));
				co.setType("bridge");
				co.setCd_count(getCdStateCount(rs.getString("bridge_id")));
				co.setBrg_type(getBrgTopStructType(rs.getString("bridge_id")));
				lc.add(co);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			mdo.close();
		}
    	return lc;
    	
    } 
    public List<Connect> initIndexModeMapDataFull(String mode,String tableName,String zone_id){
    	List<Connect> lc=new ArrayList<Connect>();
    	Connection conn=MyDataSource.getInstance().getConnection();
    	MyDataOperation mdo=new MyDataOperation(conn,false);
    	String sql="SELECT\n" +
    			"	bs.*, bh.bridge_id,bh.end_time,bh.count AS count\n" +
    			"   FROM\n" +
    			"	(\n" +
    			"		SELECT\n" +
    			"			a.bridge_id,\n" +
    			"			a.dir_name,\n" +
    			"			b.bridge_name,\n" +
    			"			a.brg_pass_no \n" +
    			"		FROM\n" +
    			"			brg_system AS a\n" +
    			"			 left join brg_card_admin_id AS b\n" +
    			"		on\n" +
    			"			a.bridge_id = b.bridge_id\n" +
    			"		where b.zone_id LIKE ?\n" +
    			"		AND MODE = '"+mode+"'\n" +
    			"	 ) AS bs\n" +
    			"    LEFT JOIN (\n" +
    			"	 SELECT\n" +
    			"		a.bridge_id,\n" +
    			"		a.count,\n" +
    			"		a.end_time\n" +
    			"	 FROM (\n" +
    			"    SELECT "+
				"    count(tt.bridge_id) as count,tt.bridge_id,tt.end_time "+
			    "    FROM  ( select c.* from "+tableName+" c  ORDER BY c.end_time  ) as tt "+
                "    where tt.end_time like  ?  GROUP BY  tt.bridge_id ) AS a  "+
    			"  ) AS bh ON bs.bridge_id = bh.bridge_id";
    	long time=new Date().getTime();
    	time=time-3600000;
	    Calendar cale = null;  
        cale = Calendar.getInstance();  
        int year = cale.get(Calendar.YEAR);
    	ResultSet rs=mdo.executeQuery(sql,new Object[]{zone_id,year+"%"});
    	try {
			while (rs.next()) {
				Connect co=new Connect();
				if(mode.equals("w")){
					co.setBrg_type("动态称重");
				}else if(mode.equals("s")){
					co.setBrg_type("健康监测");
				}else if(mode.equals("g")){
					co.setBrg_type("GPS"+rs.getString("brg_pass_no"));
				}else if(mode.equals("f")){
					co.setBrg_type("风速风向");
				}
				co.setBridge_id(rs.getString("bridge_id"));
				co.setBridge_name(rs.getString("bridge_name"));
				String str_start_time = rs.getString("end_time");
				if("".equals(str_start_time)||null==str_start_time||"null".equals(str_start_time)){
					co.setCount("0%");
				}else{
					Date end_time = new Date();
					SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					Date start_time = sdf.parse(str_start_time);
					Long long1 = (end_time.getTime()-start_time.getTime())/1000/60/30;
					co.setType("bridge");
					Long long2 =(long) 0;
					if(mode.equals("g")){
						long2=Long.parseLong(rs.getString("count"))/4;
					}else{
						long2=Long.parseLong(rs.getString("count"));
					}
					co.setCount(getBaifenbi(long1, long2)+"%");
				}
				//co.setCd_count(getCdStateCount(rs.getString("bridge_id")));
				/*co.setBrg_type(getBrgTopStructType(rs.getString("bridge_id")));*/
				
				lc.add(co);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			mdo.close();
		}
    	return lc;
    	
    } 
    public List<Connect> initDataFullNew(String tableName,String zone_id){
    	List<Connect> lc=new ArrayList<Connect>();
    	Connection conn=MyDataSource.getInstance().getConnection();
    	MyDataOperation mdo=new MyDataOperation(conn,false);
    	String sql=" select * from "+tableName+" a LEFT JOIN brg_card_admin_id b on a.brg_id = b.bridge_id where b.zone_id like ? and a.rq like ? ";
    	String lastDay = TimeFormatUtil.getSpecifiedDayBefore("yyyy-MM-dd","-");
    	ResultSet rs=mdo.executeQuery(sql,new Object[]{zone_id,lastDay});
    	try {
			while (rs.next()) {
				Connect co=new Connect();
				co.setBrg_type(getTypeByTableName(tableName));
				co.setBridge_id(rs.getString("bridge_id"));
				co.setBridge_name(rs.getString("bridge_name"));
				Float ff = rs.getFloat("rate");
				co.setCount(formatFloat(ff*100)+"%");
				lc.add(co);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			mdo.close();
		}
    	return lc;
    	
    } 
    public List<Connect> initDataFullIndex(String tableName,String zone_id){
    	List<Connect> lc=new ArrayList<Connect>();
    	Connection conn=MyDataSource.getInstance().getConnection();
    	MyDataOperation mdo=new MyDataOperation(conn,false);
    	String sql=" select * from "+tableName+" a LEFT JOIN brg_card_admin_id b on a.brg_id = b.bridge_id where b.manage_id like ?  ";
    	String lastDay = TimeFormatUtil.getSpecifiedDayBefore("yyyy-MM-dd","-");
    	ResultSet rs=mdo.executeQuery(sql,new Object[]{zone_id});
    	try {
    		while (rs.next()) {
    			Connect co=new Connect();
    			co.setBrg_type(getNewTypeByTableName(tableName));
    			co.setBridge_id(rs.getString("bridge_id"));
    			co.setBridge_name(rs.getString("bridge_name"));
    			Float ff = rs.getFloat("rate");
    			co.setCount(formatFloat(ff*100)+"%");
    			lc.add(co);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}finally {
    		mdo.close();
    	}
    	return lc;
    	
    } 
    public List<Connect> initDataManageTable(String mode,String tableName,String zone_id,String brg_id){
    	List<Connect> lc=new ArrayList<Connect>();
    	Connection conn=MyDataSource.getInstance().getConnection();
    	MyDataOperation mdo=new MyDataOperation(conn,false);
    	String sql="SELECT\n" +
    			"	bs.*, bh.bridge_id,bh.end_time,bh.count AS count\n" +
    			"   FROM\n" +
    			"	(\n" +
    			"		SELECT\n" +
    			"			a.bridge_id,\n" +
    			"			a.dir_name,\n" +
    			"			b.bridge_name\n" +
    			"		FROM\n" +
    			"			brg_system AS a\n" +
    			"			 left join brg_card_admin_id AS b\n" +
    			"		on\n" +
    			"			a.bridge_id = b.bridge_id\n" +
    			"		where b.zone_id LIKE ?\n" +
    			"		AND MODE = '"+mode+"'\n" +
    			"	 ) AS bs\n" +
    			"    LEFT JOIN (\n" +
    			"	 SELECT\n" +
    			"		a.bridge_id,\n" +
    			"		a.count,\n" +
    			"		a.end_time\n" +
    			"	 FROM (\n" +
    			"    SELECT "+
				"    count(tt.bridge_id)  as count,tt.bridge_id,tt.end_time "+
			    "    FROM  ( select c.* from "+tableName+" c where  bridge_id =?  ORDER BY c.end_time  ) as tt "+
                "    where tt.end_time like  ? ) AS a  "+
    			"  ) AS bh ON bs.bridge_id = bh.bridge_id where bs.bridge_id=?";
    	long time=new Date().getTime();
    	time=time-3600000*8;
	    Calendar cale = null;  
        cale = Calendar.getInstance();  
        int year = cale.get(Calendar.YEAR);
    	ResultSet rs=mdo.executeQuery(sql,new Object[]{zone_id,brg_id,year+"%",brg_id});
    	try {
			while (rs.next()) {
				Connect co=new Connect();
				co.setBridge_id(rs.getString("bridge_id"));
				co.setBridge_name(rs.getString("bridge_name"));
				String str_start_time = rs.getString("end_time");
				SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				if("".equals(str_start_time)||null==str_start_time||"null".equals(str_start_time)){
					co.setCount("0%");
				}else{
					Date end_time = new Date();
					Date start_time = sdf.parse(str_start_time);
					Long long1 = (end_time.getTime()-start_time.getTime())/1000/60/30;
					co.setType("bridge");
					Long long2 = Long.parseLong(rs.getString("count"));
					co.setCount(getBaifenbi(long1, long2)+"%");
				}
				co.setDay_count(getDayFullBaiFen(brg_id,tableName));
				co.setMonth_count(getYueFullBaiFen(brg_id,tableName));
				String end_time=getNearTime(tableName, brg_id);
				if(rs.getString("bridge_id")==null||end_time==null){
					co.setBgco("0");
				}else{
					long et=sdf.parse(end_time).getTime();
					if(et<time){
						co.setBgco(end_time);
					}else{
						co.setBgco("1");
					}
				}
				//co.setCd_count(getCdStateCount(rs.getString("bridge_id")));
				/*co.setBrg_type(getBrgTopStructType(rs.getString("bridge_id")));*/
				if(mode.equals("w")){
					co.setBrg_type("动态称重");
				}else if(mode.equals("s")){
					co.setBrg_type("健康监测");
				}else if(mode.equals("g")){
					co.setBrg_type("GPS");
				}else if(mode.equals("f")){
					co.setBrg_type("风速风向");
				}
				lc.add(co);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			mdo.close();
		}
    	return lc;
    	
    } 
    public List<Connect> initDataManageTableNew(String mode,String tableName,String zone_id,String brg_id){
    	List<Connect> lc=new ArrayList<Connect>();
    	Connection conn=MyDataSource.getInstance().getConnection();
    	MyDataOperation mdo=new MyDataOperation(conn,false);
    	String sql="SELECT\n" +
    			"	bs.*, bh.bridge_id,bh.end_time,bh.count AS count\n" +
    			"   FROM\n" +
    			"	(\n" +
    			"		SELECT\n" +
    			"			a.bridge_id,\n" +
    			"			a.dir_name,\n" +
    			"			b.bridge_name\n" +
    			"		FROM\n" +
    			"			brg_system AS a\n" +
    			"			 left join brg_card_admin_id AS b\n" +
    			"		on\n" +
    			"			a.bridge_id = b.bridge_id\n" +
    			"		where b.zone_id LIKE ?\n" +
    			"		AND MODE = '"+mode+"'\n" +
    			"	 ) AS bs\n" +
    			"    LEFT JOIN (\n" +
    			"	 SELECT\n" +
    			"		a.bridge_id,\n" +
    			"		a.count,\n" +
    			"		a.end_time\n" +
    			"	 FROM (\n" +
    			"    SELECT "+
				"    count(tt.bridge_id)  as count,tt.bridge_id,tt.end_time "+
			    "    FROM  ( select c.* from "+tableName+" c where  bridge_id =?  ORDER BY c.end_time desc) as tt "+
                "    where tt.end_time like  ? ) AS a  "+
    			"  ) AS bh ON bs.bridge_id = bh.bridge_id where bs.bridge_id=?";
    	long time=new Date().getTime();
    	time=time-3600000*8;
	    Calendar cale = null;  
        cale = Calendar.getInstance();  
        int year = cale.get(Calendar.YEAR);
    	ResultSet rs=mdo.executeQuery(sql,new Object[]{zone_id,brg_id,year+"%",brg_id});
    	try {
			while (rs.next()) {
				Connect co=new Connect();
				co.setBridge_id(rs.getString("bridge_id"));
				co.setBridge_name(rs.getString("bridge_name"));
				String str_start_time = rs.getString("end_time");
				SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				co.setType("bridge");
				
				String countTableName = getNewTableNameByMode(mode, "day");
				String count = getLastDayCountByTableName(countTableName, brg_id);
				if(null==count){
					count="0";
				}
				co.setCount(count+"%");
				String countTableName1 = getNewTableNameByMode(mode, "month");
				String monthcount = getNewLastMonthCountByTableName(countTableName1, brg_id);
				if(null==monthcount){
					monthcount="0";
				}
				co.setDay_count(monthcount+"%");
				String countTableName2 = getNewTableNameByMode(mode, "year");
				String yearcount = getNewLastYearCountByTableName(countTableName2, brg_id);
				if(null==yearcount){
					yearcount="0";
				}
				co.setMonth_count(yearcount+"%");
				
				if(rs.getString("bridge_id")==null||str_start_time==null){
					co.setBgco("0");
				}else{
					long et=sdf.parse(str_start_time).getTime();
					if(et<time){
						co.setBgco(str_start_time);
					}else{
						co.setBgco("1");
					}
				}
				//co.setCd_count(getCdStateCount(rs.getString("bridge_id")));
				/*co.setBrg_type(getBrgTopStructType(rs.getString("bridge_id")));*/
				if(mode.equals("w")){
					co.setBrg_type("动态称重");
				}else if(mode.equals("s")){
					co.setBrg_type("健康监测");
				}else if(mode.equals("g")){
					co.setBrg_type("GPS");
				}else if(mode.equals("f")){
					co.setBrg_type("风速风向");
				}
				lc.add(co);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			mdo.close();
		}
    	return lc;
    	
    } 
    public static  String getNearTime(String tableName,String brg_id){
    	Connection conn=MyDataSource.getInstance().getConnection();
    	MyDataOperation mdo=new MyDataOperation(conn,false);
    	String sql=" select end_time from "+tableName+" where bridge_id=? order by end_time desc limit 1";
    	ResultSet rs=mdo.executeQuery(sql,new Object[]{brg_id});
    	String string = null;
    	try {
			if (rs.next()) {
				string = rs.getString("end_time");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			mdo.close();
		}
    	return string;
    	
    } 
    
    public static  String getLastDayCountByTableName(String countTableName,String brg_id){
    	Connection conn=MyDataSource.getInstance().getConnection();
    	MyDataOperation mdo=new MyDataOperation(conn,false);
    	String sql=" select * from "+countTableName+" where brg_id=? and rq like ? order by rq desc";
    	String lastDay = TimeFormatUtil.getSpecifiedDayBefore("yyyy-MM-dd","-");
    	ResultSet rs=mdo.executeQuery(sql,new Object[]{brg_id,lastDay});
    	String string = null;
    	try {
			if (rs.next()) {
				Float ff = rs.getFloat("rate");
			    string = formatFloat(ff*100);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			mdo.close();
		}
    	return string;
    	
    }
    public static  String getLastMonthCountByTableName(String countTableName,String brg_id){
    	Connection conn=MyDataSource.getInstance().getConnection();
    	MyDataOperation mdo=new MyDataOperation(conn,false);
    	String sql=" select * from "+countTableName+" where brg_id=? and rq like ? order by rq desc";
    	String lastDay = TimeFormatUtil.getSpecifiedMonthBefore("yyyy-MM","-");
    	ResultSet rs=mdo.executeQuery(sql,new Object[]{brg_id,lastDay});
    	String string = null;
    	try {
			if (rs.next()) {
				Float ff = rs.getFloat("rate");
			    string = formatFloat(ff*100);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			mdo.close();
		}
    	return string;
    	
    }  
    public static  String getNewLastMonthCountByTableName(String countTableName,String brg_id){
    	Connection conn=MyDataSource.getInstance().getConnection();
    	MyDataOperation mdo=new MyDataOperation(conn,false);
    	String sql=" select * from "+countTableName+" where brg_id=?  order by rq desc";
    	String lastDay = TimeFormatUtil.getSpecifiedMonthBefore("yyyy-MM","-");
    	ResultSet rs=mdo.executeQuery(sql,new Object[]{brg_id});
    	String string = null;
    	try {
    		if (rs.next()) {
    			Float ff = rs.getFloat("rate");
    			string = formatFloat(ff*100);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}finally {
    		mdo.close();
    	}
    	return string;
    	
    }  
    public static  String getLastYearCountByTableName(String countTableName,String brg_id){
    	Connection conn=MyDataSource.getInstance().getConnection();
    	MyDataOperation mdo=new MyDataOperation(conn,false);
    	String sql=" select * from "+countTableName+" where brg_id=? and rq like ? order by rq desc";
    	String lastDay = TimeFormatUtil.getSpecifiedYearBefore("yyyy","-");
    	ResultSet rs=mdo.executeQuery(sql,new Object[]{brg_id,lastDay});
    	String string = null;
    	try {
			if (rs.next()) {
				Float ff = rs.getFloat("rate");
			    string = formatFloat(ff*100);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			mdo.close();
		}
    	return string;
    	
    }
    public static  String getNewLastYearCountByTableName(String countTableName,String brg_id){
    	Connection conn=MyDataSource.getInstance().getConnection();
    	MyDataOperation mdo=new MyDataOperation(conn,false);
    	String sql=" select * from "+countTableName+" where brg_id=? order by rq desc";
    	String lastDay = TimeFormatUtil.getSpecifiedYearBefore("yyyy","-");
    	ResultSet rs=mdo.executeQuery(sql,new Object[]{brg_id});
    	String string = null;
    	try {
    		if (rs.next()) {
    			Float ff = rs.getFloat("rate");
    			string = formatFloat(ff*100);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}finally {
    		mdo.close();
    	}
    	return string;
    	
    }
    public static String getDayFullBaiFen(String bridge_id,String tableName){
    	Connection conn=MyDataSource.getInstance().getConnection();
    	MyDataOperation mdo=new MyDataOperation(conn,false);
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    	String nowStr11= dateFormat1.format(new Date());
    	String sql = " SELECT count(*) as count FROM "+tableName+" c WHERE c.bridge_id = ? and c.end_time like ?  ";
    	ResultSet rs = mdo.executeQuery(sql, new Object[]{bridge_id,nowStr11+"%"});
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String nowStr = dateFormat.format(new Date());
        String starStr = nowStr.substring(0,nowStr.indexOf(" ")) + " 00:00:00";
        String bb = "";
    	try {
			if(rs.next()){
			    String count = rs.getString("count");
				Long long1 = (dateFormat.parse(nowStr).getTime() - dateFormat.parse(starStr).getTime()) / 1000/60/30;
				if(null!=count&&!"".equals(count)){
					Long long2 = Long.parseLong(rs.getString("count"));
					bb = getBaifenbi(long1, long2)+"%";
				}else{
					bb="0";
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}finally {
			mdo.close();
		}
        return bb;
    }
    
    public static String getYueFullBaiFen(String bridge_id,String tableName){
    	Connection conn=MyDataSource.getInstance().getConnection();
    	MyDataOperation mdo=new MyDataOperation(conn,false);
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM");
    	String nowStr11= dateFormat1.format(new Date());
    	String sql = " SELECT count(*) as count FROM "+tableName+" c WHERE c.bridge_id = ? and c.end_time like ?  ";
    	ResultSet rs = mdo.executeQuery(sql, new Object[]{bridge_id,nowStr11+"%"});
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM HH:mm:ss");
    	SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String nowStr = dateFormat.format(new Date());
        String starStr = nowStr.substring(0,nowStr.indexOf(" ")) + "-01 00:00:00";
        String bb = "";
    	try {
			if(rs.next()){
			    String count = rs.getString("count");
				Long long1 = (new Date().getTime() - dateFormat2.parse(starStr).getTime()) / 1000/60/30;
				if(null!=count&&!"".equals(count)){
					Long long2 = Long.parseLong(rs.getString("count"));
					bb = getBaifenbi(long1, long2)+"%";
				}else{
					bb="0";
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			mdo.close();
		}
        return bb;
    }
    public static String getBaifenbi(Long long1,Long long2){

        float num1 = long1;
 
        float num2 = long2;
 
		// 创建一个数值格式化对象
 
		NumberFormat numberFormat = NumberFormat.getInstance();
 
		// 设置精确到小数点后2位
 
		numberFormat.setMaximumFractionDigits(0);
		float f =  (float)num2/(float)num1 * 100;
		if(f>(float)100){
			f=(float) 100;
		}
		String result =numberFormat.format(f);
		
		return result; 
    }
    public static String formatFloat(Float float1){
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(0);
		String result =numberFormat.format(float1);
		return result; 
    }
    
    public List<Connect> initHealthTree2(String orgid){
    	List<Connect> lc1=new ArrayList<Connect>();
    	Connect con=new Connect();
    	con.setName("健康监测");
    	con.setType("health");
    	List<Connect> lc=new ArrayList<Connect>();
    	Connection conn=MyDataSource.getInstance().getConnection();
    	MyDataOperation mdo=new MyDataOperation(conn,false);
    	String sql="SELECT\n" +
    			"	bs.*, bh.bridge_id AS bgid,\n" +
    			"	bh.end_time\n" +
    			"FROM\n" +
    			"	(\n" +
    			"		SELECT\n" +
    			"			a.bridge_id,\n" +
    			"			a.dir_name,\n" +
    			"			b.bridge_name\n" +
    			"		FROM\n" +
    			"			brg_system AS a,\n" +
    			"			brg_card_admin_id AS b\n" +
    			"		WHERE\n" +
    			"			a.bridge_id = b.bridge_id\n" +
    			"		AND b.zone_id LIKE ?\n" +
    			"		AND MODE = 's'\n" +
    			"	) AS bs\n" +
    			"LEFT JOIN (\n" +
    			"	SELECT\n" +
    			"		a.bridge_id,\n" +
    			"		a.end_time\n" +
    			"	FROM\n" +
    			"		(\n" +
    			"			SELECT\n" +
    			"				*\n" +
    			"			FROM\n" +
    			"				brg_health_statistic\n" +
    			"			ORDER BY\n" +
    			"				end_time DESC\n" +
    			"		) AS a\n" +
    			"	GROUP BY\n" +
    			"		a.bridge_id\n" +
    			") AS bh ON bs.bridge_id = bh.bridge_id";
    	long time=new Date().getTime();
    	time=time-3600000;
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	ResultSet rs=mdo.executeQuery(sql,new String[] {orgid});
    	try {
			while (rs.next()) {
				Connect co=new Connect();
				co.setId(rs.getString("bridge_id"));
				co.setName(rs.getString("bridge_name"));
				co.setType("bridge");
				String end_time=rs.getString("end_time");
				if(rs.getString("bgid")==null||end_time==null){
					co.setBgco("0");
				}else{
					long et=sdf.parse(end_time).getTime();
					if(et<time){
						co.setBgco(end_time);
					}else{
						co.setBgco("1");
					}
				}
				lc.add(co);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			mdo.close();
		}
    	con.setChildren(lc);
    	lc1.add(con);
    	return lc1;
    	
    }
    
    public List<Connect> initWindTree(){
    	List<Connect> lc1=new ArrayList<Connect>();
    	Connect con=new Connect();
    	con.setName("风速风向");
    	con.setType("wind");
    	List<Connect> lc=new ArrayList<Connect>();
    	Connection conn=MyDataSource.getInstance().getConnection();
    	MyDataOperation mdo=new MyDataOperation(conn,false);
    	String sql="SELECT\n" +
    			"	bs.*, bh.bridge_id AS bgid,\n" +
    			"	bh.end_time\n" +
    			"FROM\n" +
    			"	(\n" +
    			"		SELECT\n" +
    			"			a.bridge_id,\n" +
    			"			a.dir_name,\n" +
    			"			b.bridge_name\n" +
    			"		FROM\n" +
    			"			brg_system AS a,\n" +
    			"			brg_card_admin_id AS b\n" +
    			"		WHERE\n" +
    			"			a.bridge_id = b.bridge_id\n" +
    			"		AND MODE = 'f'\n" +
    			"	) AS bs\n" +
    			"LEFT JOIN (\n" +
    			"	SELECT\n" +
    			"		a.bridge_id,\n" +
    			"		a.end_time\n" +
    			"	FROM\n" +
    			"		(\n" +
    			"			SELECT\n" +
    			"				*\n" +
    			"			FROM\n" +
    			"				brg_wind_statistic\n" +
    			"			ORDER BY\n" +
    			"				end_time DESC\n" +
    			"		) AS a\n" +
    			"	GROUP BY\n" +
    			"		a.bridge_id\n" +
    			") AS bh ON bs.bridge_id = bh.bridge_id";
    	long time=new Date().getTime();
    	time=time-3600000;
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	ResultSet rs=mdo.executeQuery(sql,null);
    	try {
			while (rs.next()) {
				Connect co=new Connect();
				co.setId(rs.getString("bridge_id"));
				co.setName(rs.getString("bridge_name"));
				co.setType("bridge");
				String end_time=rs.getString("end_time");
				if(rs.getString("bgid")==null||end_time==null){
					co.setBgco("0");
				}else{
					long et=sdf.parse(end_time).getTime();
					if(et<time){
						co.setBgco(end_time);
					}else{
						co.setBgco("1");
					}
				}
				lc.add(co);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			mdo.close();
		}
    	con.setChildren(lc);
    	lc1.add(con);
    	return lc1;
    	
    }
    
    public List<Connect> initWindTree2(String orgid){
    	List<Connect> lc1=new ArrayList<Connect>();
    	Connect con=new Connect();
    	con.setName("风速风向");
    	con.setType("wind");
    	List<Connect> lc=new ArrayList<Connect>();
    	Connection conn=MyDataSource.getInstance().getConnection();
    	MyDataOperation mdo=new MyDataOperation(conn,false);
    	String sql="SELECT\n" +
    			"	bs.*, bh.bridge_id AS bgid,\n" +
    			"	bh.end_time\n" +
    			"FROM\n" +
    			"	(\n" +
    			"		SELECT\n" +
    			"			a.bridge_id,\n" +
    			"			a.dir_name,\n" +
    			"			b.bridge_name\n" +
    			"		FROM\n" +
    			"			brg_system AS a,\n" +
    			"			brg_card_admin_id AS b\n" +
    			"		WHERE\n" +
    			"			a.bridge_id = b.bridge_id\n" +
    			"		AND b.zone_id LIKE ?\n" +
    			"		AND MODE = 'f'\n" +
    			"	) AS bs\n" +
    			"LEFT JOIN (\n" +
    			"	SELECT\n" +
    			"		a.bridge_id,\n" +
    			"		a.end_time\n" +
    			"	FROM\n" +
    			"		(\n" +
    			"			SELECT\n" +
    			"				*\n" +
    			"			FROM\n" +
    			"				brg_wind_statistic\n" +
    			"			ORDER BY\n" +
    			"				end_time DESC\n" +
    			"		) AS a\n" +
    			"	GROUP BY\n" +
    			"		a.bridge_id\n" +
    			") AS bh ON bs.bridge_id = bh.bridge_id";
    	long time=new Date().getTime();
    	time=time-3600000;
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	ResultSet rs=mdo.executeQuery(sql,new String[] {orgid});
    	try {
			while (rs.next()) {
				Connect co=new Connect();
				co.setId(rs.getString("bridge_id"));
				co.setName(rs.getString("bridge_name"));
				co.setType("bridge");
				String end_time=rs.getString("end_time");
				if(rs.getString("bgid")==null||end_time==null){
					co.setBgco("0");
				}else{
					long et=sdf.parse(end_time).getTime();
					if(et<time){
						co.setBgco(end_time);
					}else{
						co.setBgco("1");
					}
				}
				lc.add(co);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			mdo.close();
		}
    	con.setChildren(lc);
    	lc1.add(con);
    	return lc1;
    	
    }
    
    public List<Connect> initGPSTree(){
    	List<Connect> lc1=new ArrayList<Connect>();
    	Connect con=new Connect();
    	con.setName("GPS");
    	con.setType("gps");
    	List<Connect> lc=new ArrayList<Connect>();
    	Connection conn=MyDataSource.getInstance().getConnection();
    	MyDataOperation mdo=new MyDataOperation(conn,false);
    	String sql="SELECT\n" +
    			"	bs.*, bh.bridge_id AS bgid,\n" +
    			"	bh.end_time\n" +
    			"FROM\n" +
    			"	(\n" +
    			"		SELECT\n" +
    			"			a.bridge_id,\n" +
    			"			a.dir_name,\n" +
    			" 			a.brg_pass_no,\n" +
    			"			b.bridge_name\n" +
    			"		FROM\n" +
    			"			brg_system AS a,\n" +
    			"			brg_card_admin_id AS b\n" +
    			"		WHERE\n" +
    			"			a.bridge_id = b.bridge_id\n" +
    			"		AND MODE = 'g'\n" +
    			"	) AS bs\n" +
    			"LEFT JOIN (\n" +
    			"	SELECT\n" +
    			"		a.bridge_id,\n" +
    			"		a.end_time\n" +
    			"	FROM\n" +
    			"		(\n" +
    			"			SELECT\n" +
    			"				*\n" +
    			"			FROM\n" +
    			"				brg_gps_statistic\n" +
    			"			ORDER BY\n" +
    			"				end_time DESC\n" +
    			"		) AS a\n" +
    			"	GROUP BY\n" +
    			"		a.bridge_id\n" +
    			") AS bh ON bs.bridge_id = bh.bridge_id";
    	long time=new Date().getTime();
    	time=time-3600000;
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	ResultSet rs=mdo.executeQuery(sql,null);
    	try {
			while (rs.next()) {
				Connect co=new Connect();
				co.setId(rs.getString("bridge_id"));
				co.setName(rs.getString("bridge_name"));
				co.setType("bridge");
				co.setName_no(rs.getString("brg_pass_no"));
				String end_time=rs.getString("end_time");
				if(rs.getString("bgid")==null||end_time==null){
					co.setBgco("0");
				}else{
					long et=sdf.parse(end_time).getTime();
					if(et<time){
						co.setBgco(end_time);
					}else{
						co.setBgco("1");
					}
				}
				lc.add(co);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			mdo.close();
		}
    	con.setChildren(lc);
    	lc1.add(con);
    	return lc1;
    	
    }
    
    public List<Connect> initGPSTreeOne(){
    	List<Connect> lc1=new ArrayList<Connect>();
    	Connect con=new Connect();
    	con.setName("GPS");
    	con.setType("gps");
    	List<Connect> lc=new ArrayList<Connect>();
    	Connection conn=MyDataSource.getInstance().getConnection();
    	MyDataOperation mdo=new MyDataOperation(conn,false);
    	String sql="SELECT\n" +
    			" 	bs.*, bh.bridge_id AS bgid,\n" +
    			"	bh.end_time\n" +
    			"FROM\n" +
    			"	(\n" +
    			"		SELECT\n" +
    			"			a.bridge_id,\n" +
    			"			a.dir_name,\n" +
    			" 			a.brg_pass_no,\n" +
    			"			b.bridge_name\n" +
    			"		FROM\n" +
    			"			brg_system AS a,\n" +
    			"			brg_card_admin_id AS b\n" +
    			"		WHERE\n" +
    			"			a.bridge_id = b.bridge_id\n " +
    			"		AND MODE = 'g'\n" +
    			"	) AS bs\n" +
    			"LEFT JOIN (\n" +
    			"	SELECT\n" +
    			"		a.bridge_id,\n" +
    			"		a.end_time\n" +
    			"	FROM\n" +
    			"		(\n" +
    			"			SELECT\n" +
    			"				*\n" +
    			"			FROM\n" +
    			"				brg_gps_statistic\n" +
    			"			ORDER BY\n" +
    			"				end_time DESC\n" +
    			"		) AS a\n" +
    			"	GROUP BY\n" +
    			"		a.bridge_id\n" +
    			") AS bh ON bs.bridge_id = bh.bridge_id ";
    	long time=new Date().getTime();
    	time=time-3600000;
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	ResultSet rs=mdo.executeQuery(sql,null);
    	try {
			while (rs.next()) {
				if(rs.getString("brg_pass_no")==null||"".equals(rs.getString("brg_pass_no"))){
					Connect co=new Connect();
					co.setId(rs.getString("bridge_id"));
					co.setName(rs.getString("bridge_name"));
					co.setType("bridge");
					co.setName_no(rs.getString("brg_pass_no"));
					String end_time=rs.getString("end_time");
					if(rs.getString("bgid")==null||end_time==null){
						co.setBgco("0");
					}else{
						long et=sdf.parse(end_time).getTime();
						if(et<time){
							co.setBgco(end_time);
						}else{
							co.setBgco("1");
						}
					}
					lc.add(co);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			mdo.close();
		}
    	con.setChildren(lc);
    	
    	lc1.add(con);
    	return lc1;
    	
    }
    
    public List<Connect> initGPSTree2(String orgid){
    	List<Connect> lc1=new ArrayList<Connect>();
    	Connect con=new Connect();
    	con.setName("GPS");
    	con.setType("gps");
    	List<Connect> lc=new ArrayList<Connect>();
    	Connection conn=MyDataSource.getInstance().getConnection();
    	MyDataOperation mdo=new MyDataOperation(conn,false);
    	String sql="SELECT\n" +
    			"	bs.*, bh.bridge_id AS bgid,\n" +
    			"	bh.end_time\n" +
    			"FROM\n" +
    			"	(\n" +
    			"		SELECT\n" +
    			"			a.bridge_id,\n" +
    			"			a.dir_name,\n" +
    			" 			a.brg_pass_no,\n" +
    			"			b.bridge_name\n" +
    			"		FROM\n" +
    			"			brg_system AS a,\n" +
    			"			brg_card_admin_id AS b\n" +
    			"		WHERE\n" +
    			"			a.bridge_id = b.bridge_id\n" +
    			"		AND b.zone_id LIKE ?\n" +
    			"		AND MODE = 'g'\n" +
    			"	) AS bs\n" +
    			"LEFT JOIN (\n" +
    			"	SELECT\n" +
    			"		a.bridge_id,\n" +
    			"		a.end_time\n" +
    			"	FROM\n" +
    			"		(\n" +
    			"			SELECT\n" +
    			"				*\n" +
    			"			FROM\n" +
    			"				brg_gps_statistic\n" +
    			"			ORDER BY\n" +
    			"				end_time DESC\n" +
    			"		) AS a\n" +
    			"	GROUP BY\n" +
    			"		a.bridge_id\n" +
    			") AS bh ON bs.bridge_id = bh.bridge_id";
    	long time=new Date().getTime();
    	time=time-3600000;
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	ResultSet rs=mdo.executeQuery(sql,new String[] {orgid});
    	try {
			while (rs.next()) {
				Connect co=new Connect();
				co.setId(rs.getString("bridge_id"));
				co.setName(rs.getString("bridge_name"));
				co.setType("bridge");
				String end_time=rs.getString("end_time");
				co.setName_no(rs.getString("brg_pass_no"));
				if(rs.getString("bgid")==null||end_time==null){
					co.setBgco("0");
				}else{
					long et=sdf.parse(end_time).getTime();
					if(et<time){
						co.setBgco(end_time);
					}else{
						co.setBgco("1");
					}
				}
				lc.add(co);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			mdo.close();
		}
    	con.setChildren(lc);
    	lc1.add(con);
    	return lc1;
    	
    }
    
    public List<Connect> initStaticTree(){
    	List<Connect> lc1=new ArrayList<Connect>();
    	Connect con=new Connect();
    	con.setName("静态监测");
    	con.setType("static");
    	List<Connect> lc=new ArrayList<Connect>();
    	Connection conn=MyDataSource.getInstance().getConnection();
    	MyDataOperation mdo=new MyDataOperation(conn,false);
    	String sql="SELECT\n" +
    			"	bs.*, bh.bridge_id AS bgid,\n" +
    			"	bh.end_time\n" +
    			"FROM\n" +
    			"	(\n" +
    			"		SELECT\n" +
    			"			a.bridge_id,\n" +
    			"			a.dir_name,\n" +
    			"			b.bridge_name\n" +
    			"		FROM\n" +
    			"			brg_system AS a,\n" +
    			"			brg_card_admin_id AS b\n" +
    			"		WHERE\n" +
    			"			a.bridge_id = b.bridge_id\n" +
    			"		AND MODE = 'j'\n" +
    			"	) AS bs\n" +
    			"LEFT JOIN (\n" +
    			"	SELECT\n" +
    			"		a.bridge_id,\n" +
    			"		a.end_time\n" +
    			"	FROM\n" +
    			"		(\n" +
    			"			SELECT\n" +
    			"				*\n" +
    			"			FROM\n" +
    			"				brg_static_statistic\n" +
    			"			ORDER BY\n" +
    			"				end_time DESC\n" +
    			"		) AS a\n" +
    			"	GROUP BY\n" +
    			"		a.bridge_id\n" +
    			") AS bh ON bs.bridge_id = bh.bridge_id";
    	long time=new Date().getTime();
    	time=time-3600000;
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	ResultSet rs=mdo.executeQuery(sql,null);
    	try {
			while (rs.next()) {
				Connect co=new Connect();
				co.setId(rs.getString("bridge_id"));
				co.setName(rs.getString("bridge_name"));
				co.setType("bridge");
				String end_time=rs.getString("end_time");
				if(rs.getString("bgid")==null||end_time==null){
					co.setBgco("0");
				}else{
					long et=sdf.parse(end_time).getTime();
					if(et<time){
						co.setBgco(end_time);
					}else{
						co.setBgco("1");
					}
				}
				lc.add(co);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			mdo.close();
		}
    	con.setChildren(lc);
    	lc1.add(con);
    	return lc1;
    	
    }
    
    public List<Connect> initStaticTree2(String orgid){
    	List<Connect> lc1=new ArrayList<Connect>();
    	Connect con=new Connect();
    	con.setName("静态监测");
    	con.setType("static");
    	List<Connect> lc=new ArrayList<Connect>();
    	Connection conn=MyDataSource.getInstance().getConnection();
    	MyDataOperation mdo=new MyDataOperation(conn,false);
    	String sql="SELECT\n" +
    			"	bs.*, bh.bridge_id AS bgid,\n" +
    			"	bh.end_time\n" +
    			"FROM\n" +
    			"	(\n" +
    			"		SELECT\n" +
    			"			a.bridge_id,\n" +
    			"			a.dir_name,\n" +
    			"			b.bridge_name\n" +
    			"		FROM\n" +
    			"			brg_system AS a,\n" +
    			"			brg_card_admin_id AS b\n" +
    			"		WHERE\n" +
    			"			a.bridge_id = b.bridge_id\n" +
    			"		AND b.zone_id LIKE ?\n" +
    			"		AND MODE = 'j'\n" +
    			"	) AS bs\n" +
    			"LEFT JOIN (\n" +
    			"	SELECT\n" +
    			"		a.bridge_id,\n" +
    			"		a.end_time\n" +
    			"	FROM\n" +
    			"		(\n" +
    			"			SELECT\n" +
    			"				*\n" +
    			"			FROM\n" +
    			"				brg_static_statistic\n" +
    			"			ORDER BY\n" +
    			"				end_time DESC\n" +
    			"		) AS a\n" +
    			"	GROUP BY\n" +
    			"		a.bridge_id\n" +
    			") AS bh ON bs.bridge_id = bh.bridge_id";
    	long time=new Date().getTime();
    	time=time-3600000;
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	ResultSet rs=mdo.executeQuery(sql,new String[] {orgid});
    	try {
			while (rs.next()) {
				Connect co=new Connect();
				co.setId(rs.getString("bridge_id"));
				co.setName(rs.getString("bridge_name"));
				co.setType("bridge");
				String end_time=rs.getString("end_time");
				if(rs.getString("bgid")==null||end_time==null){
					co.setBgco("0");
				}else{
					long et=sdf.parse(end_time).getTime();
					if(et<time){
						co.setBgco(end_time);
					}else{
						co.setBgco("1");
					}
				}
				lc.add(co);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			mdo.close();
		}
    	con.setChildren(lc);
    	lc1.add(con);
    	return lc1;
    	
    }
    
    public List<BrgWeightStatistic> getWeightList(String bridge_id){
    	List<BrgWeightStatistic> lb=new ArrayList<BrgWeightStatistic>();
    	Connection conn=MyDataSource.getInstance().getConnection();
    	MyDataOperation mdo=new MyDataOperation(conn,false);
    	String sql="select * from brg_weight_statistic where brg_id=?";
    	ResultSet rs=mdo.executeQuery(sql,new String[]{bridge_id});
    	try {
			while (rs.next()) {
				BrgWeightStatistic bs=new BrgWeightStatistic();
				bs.setBridge_id(rs.getString("bridge_id"));
				bs.setData_file(rs.getString("data_file"));
				bs.setEnd_time(rs.getString("end_time"));
				bs.setFile_size(rs.getInt("file_size"));
				bs.setFile_time(rs.getString("file_time"));
				bs.setMemo(rs.getString("memo"));
				bs.setR_id(rs.getString("r_id"));
				bs.setStart_time(rs.getString("start_time"));
				lb.add(bs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			mdo.close();
		}
    	return lb;
    }
    public boolean checkDir(String bridge_id,String mode){
    	Connection conn=MyDataSource.getInstance().getConnection();
    	MyDataOperation mdo=new MyDataOperation(conn,false);
    	String sql="select * from brg_system where dir_name=(select CONCAT(bridge_no,'W') FROM brg_card_admin_id where bridge_id=?)";
    	if(mode.toLowerCase().equals("s")){
    		sql="select * from brg_system where dir_name=(select CONCAT(bridge_no,'S') FROM brg_card_admin_id where bridge_id=?)";
    	}
    	ResultSet rs=mdo.executeQuery(sql,new String[]{bridge_id});
    	try {
			if (rs.next()) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			mdo.close();
		}
    	return true;
    }
    
    public boolean checkDirWind(String bridge_id,String mode){
    	Connection conn=MyDataSource.getInstance().getConnection();
    	MyDataOperation mdo=new MyDataOperation(conn,false);
    	String sql="select * from brg_system where dir_name=(select CONCAT(bridge_no,'F') FROM brg_card_admin_id where bridge_id=?)";
    	ResultSet rs=mdo.executeQuery(sql,new String[]{bridge_id});
    	try {
			if (rs.next()) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			mdo.close();
		}
    	return true;
    }
    
    public boolean checkDirGPS(String bridge_id,String mode){
    	Connection conn=MyDataSource.getInstance().getConnection();
    	MyDataOperation mdo=new MyDataOperation(conn,false);
    	String sql="select * from brg_system where dir_name=(select CONCAT(bridge_no,'G') FROM brg_card_admin_id where bridge_id=?)";
    	ResultSet rs=mdo.executeQuery(sql,new String[]{bridge_id});
    	try {
			if (rs.next()) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			mdo.close();
		}
    	return true;
    }
    
    public List<ManageSectionBridge> initManageSection(){
    	List<ManageSectionBridge> lc=new ArrayList<>();
    	Connection conn=MyDataSource.getInstance().getConnection();
    	MyDataOperation mdo=new MyDataOperation(conn,false);
    	String sql="SELECT b.org_id,b.org_name,c.section_id,"
    			+ "c.section_name,a.bridge_id,a.bridge_name from "
    			+ "brg_card_admin_id as a,sys_org_info as b,"
    			+ "sys_section_info as c where a.manage_id=b.org_id "
    			+ "and a.section_id=c.section_id ORDER BY org_name,section_name,bridge_name";
    	ResultSet rs=mdo.executeQuery(sql,null);
    	try {
			while (rs.next()) {
				String manage_id=rs.getString("org_id");
				String manage_name=rs.getString("org_name");
				String section_id=rs.getString("section_id");
				String section_name=rs.getString("section_name");
				String bridge_id=rs.getString("bridge_id");
				String bridge_name=rs.getString("bridge_name");
				ManageSectionBridge msb=new ManageSectionBridge();
				msb.setStruct_name(bridge_name);
				msb.setStruct_id(bridge_id);
				msb.setManage_id(manage_id);
				msb.setManage_name(manage_name);
				msb.setSection_id(section_id);
				msb.setSection_name(section_name);
				lc.add(msb);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			mdo.close();
		}
    	return lc;
    }
    public static String getBrgTopStructType(String brg_id){
	    MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection(),false);
	   	String sql=" select * from brg_card_top_struct where bridge_id = ?";
	   	ResultSet rs=dataOperation.executeQuery(sql,new Object[]{brg_id});
	   	String brg_type = "";
	   	try {
			if (rs.next()) {
				
			   brg_type = rs.getString("top_struct_type");
			   if("".equals(brg_type)||null==brg_type){
				   brg_type ="梁桥";
			   }
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	   	dataOperation.close();
	   	return brg_type;
    }
    
    public static String getCdStateCount(String brg_id){
	    MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection(),false);
	   	String sql=" select count(id) as count from test_point where bridge_id = ? and if_jihuo=1";
	   	ResultSet rs=dataOperation.executeQuery(sql,new Object[]{brg_id});
	   	String brg_type = "";
	   	try {
			if (rs.next()) {
			   brg_type = rs.getString("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	   	dataOperation.close();
	   	return brg_type;
    }
    public static Map<String, List<String>> getDataFullMap(String brg_id,String data_type){
	    MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection(),false);
	   	String sql=" select * from brg_system where bridge_id =? GROUP BY `mode`;";
	   	ResultSet rs=dataOperation.executeQuery(sql,new Object[]{brg_id});
	   	String brg_mode = "";
	   	Map map = new HashMap<>();
	   	try {
			while (rs.next()) {
			  brg_mode=rs.getString("mode");
			  List<String> list = getDataFullDayByTableName(brg_id,brg_mode,data_type);
			  if(list.size()>0){
				  map.put(brg_mode,getDataFullDayByTableName(brg_id,brg_mode,data_type));
			  }
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dataOperation.close();
		}
	   	return map;
    }
    public static List<String> getDataFullDayStr(String brg_id,String mode,String data_type){
	    MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection(),false);
	   	String sql=" select * from data_full where brg_id =? and mode=? and data_type=? order by data_full_date desc limit 7 ;";
	   	ResultSet rs=dataOperation.executeQuery(sql,new Object[]{brg_id,mode,data_type});
	   	List<String> list = new ArrayList<>();
	   	try {
			while (rs.next()) {
				String dataFull = "";
				dataFull = rs.getString("data_full");
				list.add(dataFull);
			}
		Collections.reverse(list);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dataOperation.close();
		}
	   	return list;
    }
     
    public static List<String> getDataFullDayByTableName(String brg_id,String mode,String data_type){
    	String tableName = getTableNameByMode(mode, data_type);
    	List<String> list = new ArrayList<>();
    	if(!"".equals(tableName)){
    		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection(),false);
    		String sql=" select rate from "+tableName+" where brg_id = ? ORDER BY rq desc LIMIT 7 ;";
    		ResultSet rs=dataOperation.executeQuery(sql,new Object[]{brg_id});
    		try {
    			while (rs.next()) {
    				
    				Float ff = rs.getFloat("rate");
    				String string = formatFloat(ff*100);
    				list.add(string);
    			}
    			Collections.reverse(list);
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}finally {
    			dataOperation.close();
    		}
    	}
	   	return list;
    }
    
    public static List<String> getDataFullTimeByTableName(String brg_id,String mode,String data_type){
    	String tableName = getTableNameByMode(mode, data_type);
    	List<String> list = new ArrayList<>();
    	if(!"".equals(tableName)){
    		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection(),false);
    		String sql=" select distinct rq from "+tableName+" where brg_id = ? ORDER BY rq desc LIMIT 7 ;";
    		ResultSet rs=dataOperation.executeQuery(sql,new Object[]{brg_id});
    		try {
    			while (rs.next()) {
    				String dataFull = "";
    				dataFull = rs.getString("rq");
    				list.add(dataFull);
    			}
    			Collections.reverse(list);
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}finally {
    			dataOperation.close();
    		}
    	}
	   	return list;
    }
    public static String getTableNameByMode(String mode,String timeType){
    	if(mode.equals("w")&&timeType.equals("day")){
    		return "brg_weight_dayrate";
    	}else if(mode.equals("s")&&timeType.equals("day")){
    		return "brg_health_dayrate";
    	}else if(mode.equals("w")&&timeType.equals("month")){
    		return "brg_weight_monthrate";
    	}else if(mode.equals("s")&&timeType.equals("month")){
    		return "brg_health_monthrate";
    	}else if(mode.equals("w")&&timeType.equals("year")){
    		return "brg_weight_yearrate";
    	}else if(mode.equals("s")&&timeType.equals("year")){
    		return "brg_health_yearrate";
    	}else{
    		return "";
    	}
    }
    public static String getNewTableNameByMode(String mode,String timeType){
    	if(mode.equals("w")&&timeType.equals("day")){
    		return "brg_weight_dayrate";
    	}else if(mode.equals("s")&&timeType.equals("day")){
    		return "brg_health_dayrate";
    	}else if(mode.equals("w")&&timeType.equals("month")){
    		return "brg_weight_curmrate";
    	}else if(mode.equals("s")&&timeType.equals("month")){
    		return "brg_health_curmrate";
    	}else if(mode.equals("w")&&timeType.equals("year")){
    		return "brg_weight_currate";
    	}else if(mode.equals("s")&&timeType.equals("year")){
    		return "brg_health_currate";
    	}else if(mode.equals("g")&&timeType.equals("day")){
    		return "brg_gps_dayrate";
    	}else if(mode.equals("g")&&timeType.equals("month")){
    		return "brg_gps_curmrate";
    	}else if(mode.equals("g")&&timeType.equals("year")){
    		return "brg_gps_curyrate";
    	}else{
    		return "";
    	}
    }
    
    public static List<String> getDataFullDayStrTime(String brg_id,String mode,String data_type){
	    MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection(),false);
	   	String sql=" select * from data_full where brg_id =? and mode=? and data_type=? order by data_full_date desc limit 7 ;";
	   	ResultSet rs=dataOperation.executeQuery(sql,new Object[]{brg_id,mode,data_type});
	   	List<String> list = new ArrayList<>();
	   	SimpleDateFormat sdFormat=new SimpleDateFormat("yyyy-MM-dd");
	   	try {
			while (rs.next()) {
				Date date = rs.getDate("data_full_date");
				String dataFull = "";
				dataFull = sdFormat.format(date);
				list.add(dataFull);
			}
		Collections.reverse(list);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dataOperation.close();
		}
	   	return list;
    }
    public static List<DataFull> getDataFullDay(String brg_id,String mode){
	    MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection(),false);
	   	String sql=" select * from data_full where bridge_id =? and mode=? order by data_full_date desc limit 7 ;";
	   	ResultSet rs=dataOperation.executeQuery(sql,new Object[]{brg_id,mode});
	   	List<DataFull> list = new ArrayList<>();
	   	try {
			while (rs.next()) {
				DataFull dataFull = new DataFull();
				dataFull.setId(rs.getInt("id"));
				dataFull.setBrg_id(rs.getString("brg_id"));
				dataFull.setMode(rs.getString("mode"));
				dataFull.setData_full(rs.getString("data_full"));
				dataFull.setData_full_date(rs.getString("data_full_date"));
				dataFull.setData_type(rs.getString("data_type"));
				dataFull.setMode_name(rs.getString("mode_name"));
				list.add(dataFull);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dataOperation.close();
		}
	   	return list;
    }
    public static String getTypeByTableName(String tableName){
    	if(tableName.equals("brg_health_dayrate")){
    		return "健康监测";
    	}else if(tableName.equals("brg_weight_dayrate")){
    		return "动态称重";
    	}else{
			return "";
		}
    }
    public static String getNewTypeByTableName(String tableName){
    	if(tableName.contains("health")){
    		return "健康监测";
    	}else if(tableName.contains("weight")){
    		return "动态称重";
    	}else if(tableName.contains("gps")){
    		return "GPS";
    	}else{
    		return "";
    	}
    }
	public static void main(String[] args) {
		System.out.println(ToJSONStr.getJSON(GetFileSizeDao.getInstance().GetWeightData("G15320981K0030"), null));
	}

}
