package hs.bm.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.authz.annotation.RequiresGuest;

import hs.bm.bean.BrgCardAdminId;
import hs.bm.bean.BrgGpsStatistic;
import hs.bm.bean.BrgHealthStatistic;
import hs.bm.bean.BrgStaticStatistic;
import hs.bm.bean.BrgSystem;
import hs.bm.bean.BrgWeightLoadRatio;
import hs.bm.bean.BrgWeightStatistic;
import hs.bm.bean.BrgWindStatistic;
import hs.bm.bean.Connect;
import hs.bm.bean.MaxGw;
import hs.bm.bean.MaxGw8;
import hs.bm.bean.MaxGwInfo;
import hs.bm.bean.MaxGwInfo8;
import hs.bm.bean.NumOvlo;
import hs.bm.bean.NumOvlo8;
import hs.bm.bean.NumVehi;
import hs.bm.bean.NumVehi8;
import hs.bm.bean.ProbOvlo;
import hs.bm.bean.ProbOvlo8;
import hs.bm.bean.RatioOvlo;
import hs.bm.bean.RatioOvlo8;
import hs.bm.util.ToJSONStr;
import hs.bm.vo.FileTimeSize;
import hs.bm.vo.ManageSectionBridge;

public class AppDao {
	public static boolean flag=false;
	private static AppDao getFileSizeDao;
	public static AppDao getInstance(){
		if(getFileSizeDao==null){
			getFileSizeDao=new AppDao();
		}
		return getFileSizeDao;
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
				i=mdo.executeUpdate(sql1, new String[]{UUID.randomUUID().toString(),bridge_id,bridge_id,mode,sdf.format(new Date()),"閫氶亾"+(j+1)});
			}
		}
		mdo.close();
		return i;
    }
    
    public List<Connect> initWeightTree(){
    	List<Connect> lc1=new ArrayList<Connect>();
    	Connect con=new Connect();
    	con.setName("鍔ㄦ�佺О閲�");
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
    	con.setName("鍔ㄦ�佺О閲�");
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
    	con.setName("鍋ュ悍鐩戞祴");
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
    			"			b.bridge_name\n" +
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
    	time=time-3600000;
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
				co.setBrg_type(getBrgTopStructType(rs.getString("bridge_id")));
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
    			"			b.longitude,\n" +
    			"			b.latitude,\n" +
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
				co.setBridge_id(rs.getString("bridge_id"));
				co.setBridge_name(rs.getString("bridge_name"));
				String str_start_time = rs.getString("end_time");
				Date end_time = new Date();
				SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date start_time = sdf.parse(str_start_time);
				Long long1 = (end_time.getTime()-start_time.getTime())/1000/60/30;
				co.setType("bridge");
			    Long long2 = Long.parseLong(rs.getString("count"));
			    co.setCount(getBaifenbi(long1, long2)+"%");
				//co.setCd_count(getCdStateCount(rs.getString("bridge_id")));
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
    public static String getBaifenbi(Long long1,Long long2){

        float num1 = long1;
 
        float num2 = long2;
 
		// 鍒涘缓涓�涓暟鍊兼牸寮忓寲瀵硅薄
 
		NumberFormat numberFormat = NumberFormat.getInstance();
 
		// 璁剧疆绮剧‘鍒板皬鏁扮偣鍚�2浣�
 
		numberFormat.setMaximumFractionDigits(2);
		String result =numberFormat.format((float)num2/(float)num1 * 100);
		return result; 
    }
    
    
    public List<Connect> initHealthTree2(String orgid){
    	List<Connect> lc1=new ArrayList<Connect>();
    	Connect con=new Connect();
    	con.setName("鍋ュ悍鐩戞祴");
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
    	con.setName("椋庨�熼鍚�");
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
    	con.setName("椋庨�熼鍚�");
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
    	con.setName("闈欐�佺洃娴�");
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
    	con.setName("闈欐�佺洃娴�");
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
				   brg_type ="姊佹ˉ";
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
	public static void main(String[] args) {
		System.out.println(ToJSONStr.getJSON(GetFileSizeDao.getInstance().GetWeightData("G15320981K0030"), null));
	}
	
	
	public List<NumVehi>selectNumVehi(String brg_id,String brg_mode,String time,String lastTime){
		List<NumVehi>list=new ArrayList<>();
		String sql=null;
		ResultSet rs=null;
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		if(lastTime==""||lastTime==null){
			sql="SELECT * from brg_weight_num_vehi where brg_mode=? and brg_id=? and brg_startime like ? ORDER BY brg_startime desc  LIMIT 1";
			rs =dataOperation.executeQuery(sql,new String[]{brg_mode,brg_id,time+"%"});
		}else{
			sql="SELECT * from brg_weight_num_vehi where brg_mode=? and brg_id=? and brg_startime > ? and brg_startime < ? ORDER BY brg_startime desc LIMIT 1";
			rs =dataOperation.executeQuery(sql,new String[]{brg_mode,brg_id,lastTime,time});
		}
		
		if (rs == null) {
			list = null;
		}
		try {
			while (rs.next()) {
				NumVehi nv=new NumVehi();
				nv.setBrgId(rs.getString(1));
				nv.setBrgMode(rs.getString(2));
				nv.setBrgStartime(rs.getString(3));
				nv.setNumVehi1_1(rs.getString(4));
				nv.setNumVehi1_2(rs.getString(5));
				nv.setNumVehi1_3(rs.getString(6));
				nv.setNumVehi1_4(rs.getString(7));
				nv.setNumVehi1_5(rs.getString(8));
				nv.setNumVehi1_6(rs.getString(9));
				nv.setNumVehiLane1(rs.getString(10));
				nv.setNumVehi2_1(rs.getString(11));
				nv.setNumVehi2_2(rs.getString(12));
				nv.setNumVehi2_3(rs.getString(13));
				nv.setNumVehi2_4(rs.getString(14));
				nv.setNumVehi2_5(rs.getString(15));
				nv.setNumVehi2_6(rs.getString(16));
				nv.setNumVehiLane2(rs.getString(17));
				nv.setNumVehi3_1(rs.getString(18));
				nv.setNumVehi3_2(rs.getString(19));
				nv.setNumVehi3_3(rs.getString(20));
				nv.setNumVehi3_4(rs.getString(21));
				nv.setNumVehi3_5(rs.getString(22));
				nv.setNumVehi3_6(rs.getString(23));
				nv.setNumVehiLane3(rs.getString(24));
				nv.setNumVehiHax1_1(rs.getString(25));
				nv.setNumVehiHax1_2(rs.getString(26));
				nv.setNumVehiHax1_3(rs.getString(27));
				nv.setNumVehiHax1_4(rs.getString(28));
				nv.setNumVehiHax1_5(rs.getString(29));
				nv.setNumVehiHax1_6(rs.getString(30));
				nv.setNumVehiHalf1(rs.getString(31));
				nv.setNumVehi4_1(rs.getString(32));
				nv.setNumVehi4_2(rs.getString(33));
				nv.setNumVehi4_3(rs.getString(34));
				nv.setNumVehi4_4(rs.getString(35));
				nv.setNumVehi4_5(rs.getString(36));
				nv.setNumVehi4_6(rs.getString(37));
				nv.setNumVehiLane4(rs.getString(38));
				nv.setNumVehi5_1(rs.getString(39));
				nv.setNumVehi5_2(rs.getString(40));
				nv.setNumVehi5_3(rs.getString(41));
				nv.setNumVehi5_4(rs.getString(42));
				nv.setNumVehi5_5(rs.getString(43));
				nv.setNumVehi5_6(rs.getString(44));
				nv.setNumVehiLane5(rs.getString(45));
				nv.setNumVehi6_1(rs.getString(46));
				nv.setNumVehi6_2(rs.getString(47));
				nv.setNumVehi6_3(rs.getString(48));
				nv.setNumVehi6_4(rs.getString(49));
				nv.setNumVehi6_5(rs.getString(50));
				nv.setNumVehi6_6(rs.getString(51));
				nv.setNumVehiLane6(rs.getString(52));
				nv.setNumVehiHax2_1(rs.getString(53));
				nv.setNumVehiHax2_2(rs.getString(54));
				nv.setNumVehiHax2_3(rs.getString(55));
				nv.setNumVehiHax2_4(rs.getString(56));
				nv.setNumVehiHax2_5(rs.getString(57));
				nv.setNumVehiHax2_6(rs.getString(58));
				nv.setNumVehiHalf2(rs.getString(59));
				nv.setNumVehiAxle1(rs.getString(60));
				nv.setNumVehiAxle2(rs.getString(61));
				nv.setNumVehiAxle3(rs.getString(62));
				nv.setNumVehiAxle4(rs.getString(63));
				nv.setNumVehiAxle5(rs.getString(64));
				nv.setNumVehiAxle6(rs.getString(65));
				nv.setNumVehiAll(rs.getString(66));
				list.add(nv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}
	
	public List<NumVehi8>selectNumVehi8(String brg_id,String brg_mode,String time,String lastTime){
		List<NumVehi8>list=new ArrayList<>();
		String sql=null;
		ResultSet rs=null;
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		if(lastTime==""||lastTime==null){
			sql="SELECT * from brg_weight_num_vehi_8 where brg_mode=? and brg_id=? and brg_startime like ? ORDER BY brg_startime desc  LIMIT 1";
			rs =dataOperation.executeQuery(sql,new String[]{brg_mode,brg_id,time+"%"});
		}else{
			sql="SELECT * from brg_weight_num_vehi_8 where brg_mode=? and brg_id=? and brg_startime > ? and brg_startime < ? ORDER BY brg_startime desc  LIMIT 1";
			rs =dataOperation.executeQuery(sql,new String[]{brg_mode,brg_id,lastTime,time});
		}
		
		if (rs == null) {
			list = null;
		}
		try {
			while (rs.next()) {
				NumVehi8 nv=new NumVehi8();
				nv.setBrgId(rs.getString(1));
				nv.setBrgMode(rs.getString(2));
				nv.setBrgStartime(rs.getString(3));
				nv.setNumVehi1_1(rs.getString(4));
				nv.setNumVehi1_2(rs.getString(5));
				nv.setNumVehi1_3(rs.getString(6));
				nv.setNumVehi1_4(rs.getString(7));
				nv.setNumVehi1_5(rs.getString(8));
				nv.setNumVehi1_6(rs.getString(9));
				nv.setNumVehiLane1(rs.getString(10));
				nv.setNumVehi2_1(rs.getString(11));
				nv.setNumVehi2_2(rs.getString(12));
				nv.setNumVehi2_3(rs.getString(13));
				nv.setNumVehi2_4(rs.getString(14));
				nv.setNumVehi2_5(rs.getString(15));
				nv.setNumVehi2_6(rs.getString(16));
				nv.setNumVehiLane2(rs.getString(17));
				nv.setNumVehi3_1(rs.getString(18));
				nv.setNumVehi3_2(rs.getString(19));
				nv.setNumVehi3_3(rs.getString(20));
				nv.setNumVehi3_4(rs.getString(21));
				nv.setNumVehi3_5(rs.getString(22));
				nv.setNumVehi3_6(rs.getString(23));
				nv.setNumVehiLane3(rs.getString(24));
				nv.setNumVehi4_1(rs.getString(25));
				nv.setNumVehi4_2(rs.getString(26));
				nv.setNumVehi4_3(rs.getString(27));
				nv.setNumVehi4_4(rs.getString(28));
				nv.setNumVehi4_5(rs.getString(29));
				nv.setNumVehi4_6(rs.getString(30));
				nv.setNumVehiLane4(rs.getString(31));
				nv.setNumVehiHax1_1(rs.getString(32));
				nv.setNumVehiHax1_2(rs.getString(33));
				nv.setNumVehiHax1_3(rs.getString(34));
				nv.setNumVehiHax1_4(rs.getString(35));
				nv.setNumVehiHax1_5(rs.getString(36));
				nv.setNumVehiHax1_6(rs.getString(37));
				nv.setNumVehiHalf1(rs.getString(38));
				nv.setNumVehi5_1(rs.getString(39));
				nv.setNumVehi5_2(rs.getString(40));
				nv.setNumVehi5_3(rs.getString(41));
				nv.setNumVehi5_4(rs.getString(42));
				nv.setNumVehi5_5(rs.getString(43));
				nv.setNumVehi5_6(rs.getString(44));
				nv.setNumVehiLane5(rs.getString(45));
				nv.setNumVehi6_1(rs.getString(46));
				nv.setNumVehi6_2(rs.getString(47));
				nv.setNumVehi6_3(rs.getString(48));
				nv.setNumVehi6_4(rs.getString(49));
				nv.setNumVehi6_5(rs.getString(50));
				nv.setNumVehi6_6(rs.getString(51));
				nv.setNumVehiLane6(rs.getString(52));
				nv.setNumVehi7_1(rs.getString(53));
				nv.setNumVehi7_2(rs.getString(54));
				nv.setNumVehi7_3(rs.getString(55));
				nv.setNumVehi7_4(rs.getString(56));
				nv.setNumVehi7_5(rs.getString(57));
				nv.setNumVehi7_6(rs.getString(58));
				nv.setNumVehiLane7(rs.getString(59));
				nv.setNumVehi8_1(rs.getString(60));
				nv.setNumVehi8_2(rs.getString(61));
				nv.setNumVehi8_3(rs.getString(62));
				nv.setNumVehi8_4(rs.getString(63));
				nv.setNumVehi8_5(rs.getString(64));
				nv.setNumVehi8_6(rs.getString(65));
				nv.setNumVehiLane8(rs.getString(66));
				nv.setNumVehiHax2_1(rs.getString(67));
				nv.setNumVehiHax2_2(rs.getString(68));
				nv.setNumVehiHax2_3(rs.getString(69));
				nv.setNumVehiHax2_4(rs.getString(70));
				nv.setNumVehiHax2_5(rs.getString(71));
				nv.setNumVehiHax2_6(rs.getString(72));
				nv.setNumVehiHalf2(rs.getString(73));
				nv.setNumVehiAxle1(rs.getString(74));
				nv.setNumVehiAxle2(rs.getString(75));
				nv.setNumVehiAxle3(rs.getString(76));
				nv.setNumVehiAxle4(rs.getString(77));
				nv.setNumVehiAxle5(rs.getString(80));
				nv.setNumVehiAxle6(rs.getString(79));
				nv.setNumVehiAll(rs.getString(80));
				list.add(nv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}
	
	public List<MaxGw>selectMaxGw(String brg_id,String brg_mode,String time,String lastTime){
		List<MaxGw>list=new ArrayList<>();
		String sql=null;
		ResultSet rs=null;
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		if(lastTime==""||lastTime==null){
			sql="SELECT * from brg_weight_max_gw where brg_mode=? and brg_id=? and brg_startime like ? ORDER BY brg_startime desc  LIMIT 1";
			rs =dataOperation.executeQuery(sql,new String[]{brg_mode,brg_id,time+"%"});
		}else{
			sql="SELECT * from brg_weight_max_gw where brg_mode=? and brg_id=? and brg_startime > ? and brg_startime < ? ORDER BY brg_startime desc LIMIT 1";
			rs =dataOperation.executeQuery(sql,new String[]{brg_mode,brg_id,lastTime,time});
		}
		
		if (rs == null) {
			list = null;
		}
		try {
			while (rs.next()) {
				MaxGw mg=new MaxGw();
				mg.setBrgId(rs.getString(1));
				mg.setBrgMode(rs.getString(2));
				mg.setBrgStartime(rs.getString(3));
				mg.setMaxGw1_1(rs.getString(4));
				mg.setMaxGw1_2(rs.getString(5));
				mg.setMaxGw1_3(rs.getString(6));
				mg.setMaxGw1_4(rs.getString(7));
				mg.setMaxGw1_5(rs.getString(8));
				mg.setMaxGw1_6(rs.getString(9));
				mg.setMaxGwLane1(rs.getString(10));
				mg.setMaxGw2_1(rs.getString(11));
				mg.setMaxGw2_2(rs.getString(12));
				mg.setMaxGw2_3(rs.getString(13));
				mg.setMaxGw2_4(rs.getString(14));
				mg.setMaxGw2_5(rs.getString(15));
				mg.setMaxGw2_6(rs.getString(16));
				mg.setMaxGwLane2(rs.getString(17));
				mg.setMaxGw3_1(rs.getString(18));
				mg.setMaxGw3_2(rs.getString(19));
				mg.setMaxGw3_3(rs.getString(20));
				mg.setMaxGw3_4(rs.getString(21));
				mg.setMaxGw3_5(rs.getString(22));
				mg.setMaxGw3_6(rs.getString(23));
				mg.setMaxGwLane3(rs.getString(24));
				mg.setMaxGwHax1_1(rs.getString(25));
				mg.setMaxGwHax1_2(rs.getString(26));
				mg.setMaxGwHax1_3(rs.getString(27));
				mg.setMaxGwHax1_4(rs.getString(28));
				mg.setMaxGwHax1_5(rs.getString(29));
				mg.setMaxGwHax1_6(rs.getString(30));
				mg.setMaxGwHalf1(rs.getString(31));
				mg.setMaxGw4_1(rs.getString(32));
				mg.setMaxGw4_2(rs.getString(33));
				mg.setMaxGw4_3(rs.getString(34));
				mg.setMaxGw4_4(rs.getString(35));
				mg.setMaxGw4_5(rs.getString(36));
				mg.setMaxGw4_6(rs.getString(37));
				mg.setMaxGwLane4(rs.getString(38));
				mg.setMaxGw5_1(rs.getString(39));
				mg.setMaxGw5_2(rs.getString(40));
				mg.setMaxGw5_3(rs.getString(41));
				mg.setMaxGw5_4(rs.getString(42));
				mg.setMaxGw5_5(rs.getString(43));
				mg.setMaxGw5_6(rs.getString(44));
				mg.setMaxGwLane5(rs.getString(45));
				mg.setMaxGw6_1(rs.getString(46));
				mg.setMaxGw6_2(rs.getString(47));
				mg.setMaxGw6_3(rs.getString(48));
				mg.setMaxGw6_4(rs.getString(49));
				mg.setMaxGw6_5(rs.getString(50));
				mg.setMaxGw6_6(rs.getString(51));
				mg.setMaxGwLane6(rs.getString(52));
				mg.setMaxGwHax2_1(rs.getString(53));
				mg.setMaxGwHax2_2(rs.getString(54));
				mg.setMaxGwHax2_3(rs.getString(55));
				mg.setMaxGwHax2_4(rs.getString(56));
				mg.setMaxGwHax2_5(rs.getString(57));
				mg.setMaxGwHax2_6(rs.getString(58));
				mg.setMaxGwHalf2(rs.getString(59));
				mg.setMaxGwAxle1(rs.getString(60));
				mg.setMaxGwAxle2(rs.getString(61));
				mg.setMaxGwAxle3(rs.getString(62));
				mg.setMaxGwAxle4(rs.getString(63));
				mg.setMaxGwAxle5(rs.getString(64));
				mg.setMaxGwAxle6(rs.getString(65));
				mg.setMaxGwAll(rs.getString(66));
				list.add(mg);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}

	public List<MaxGw8>selectMaxGw8(String brg_id,String brg_mode,String time,String lastTime){
		List<MaxGw8>list=new ArrayList<MaxGw8>();
		String sql=null;
		ResultSet rs=null;
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		if(lastTime==""||lastTime==null){
			sql="SELECT * from brg_weight_max_gw_8 where brg_mode=? and brg_id=? and brg_startime like ? ORDER BY brg_startime desc  LIMIT 1";
			rs =dataOperation.executeQuery(sql,new String[]{brg_mode,brg_id,time+"%"});
		}else{
			sql="SELECT * from brg_weight_max_gw_8 where brg_mode=? and brg_id=? and brg_startime > ? and brg_startime < ? ORDER BY brg_startime desc LIMIT 1";
			rs =dataOperation.executeQuery(sql,new String[]{brg_mode,brg_id,lastTime,time});
		}
		
		if (rs == null) {
			list = null;
		}
		try {
			while (rs.next()) {
				MaxGw8 mg=new MaxGw8();
				mg.setBrgId(rs.getString(1));
				mg.setBrgMode(rs.getString(2));
				mg.setBrgStartime(rs.getString(3));
				mg.setMaxGw1_1(rs.getString(4));
				mg.setMaxGw1_2(rs.getString(5));
				mg.setMaxGw1_3(rs.getString(6));
				mg.setMaxGw1_4(rs.getString(7));
				mg.setMaxGw1_5(rs.getString(8));
				mg.setMaxGw1_6(rs.getString(9));
				mg.setMaxGwLane1(rs.getString(10));
				mg.setMaxGw2_1(rs.getString(11));
				mg.setMaxGw2_2(rs.getString(12));
				mg.setMaxGw2_3(rs.getString(13));
				mg.setMaxGw2_4(rs.getString(14));
				mg.setMaxGw2_5(rs.getString(15));
				mg.setMaxGw2_6(rs.getString(16));
				mg.setMaxGwLane2(rs.getString(17));
				mg.setMaxGw3_1(rs.getString(18));
				mg.setMaxGw3_2(rs.getString(19));
				mg.setMaxGw3_3(rs.getString(20));
				mg.setMaxGw3_4(rs.getString(21));
				mg.setMaxGw3_5(rs.getString(22));
				mg.setMaxGw3_6(rs.getString(23));
				mg.setMaxGwLane3(rs.getString(24));
				mg.setMaxGw4_1(rs.getString(25));
				mg.setMaxGw4_2(rs.getString(26));
				mg.setMaxGw4_3(rs.getString(27));
				mg.setMaxGw4_4(rs.getString(28));
				mg.setMaxGw4_5(rs.getString(29));
				mg.setMaxGw4_6(rs.getString(30));
				mg.setMaxGwLane4(rs.getString(31));
				mg.setMaxGwHax1_1(rs.getString(32));
				mg.setMaxGwHax1_2(rs.getString(33));
				mg.setMaxGwHax1_3(rs.getString(34));
				mg.setMaxGwHax1_4(rs.getString(35));
				mg.setMaxGwHax1_5(rs.getString(36));
				mg.setMaxGwHax1_6(rs.getString(37));
				mg.setMaxGwHalf1(rs.getString(38));
				mg.setMaxGw5_1(rs.getString(39));
				mg.setMaxGw5_2(rs.getString(40));
				mg.setMaxGw5_3(rs.getString(41));
				mg.setMaxGw5_4(rs.getString(42));
				mg.setMaxGw5_5(rs.getString(43));
				mg.setMaxGw5_6(rs.getString(44));
				mg.setMaxGwLane5(rs.getString(45));
				mg.setMaxGw6_1(rs.getString(46));
				mg.setMaxGw6_2(rs.getString(47));
				mg.setMaxGw6_3(rs.getString(48));
				mg.setMaxGw6_4(rs.getString(49));
				mg.setMaxGw6_5(rs.getString(50));
				mg.setMaxGw6_6(rs.getString(51));
				mg.setMaxGwLane6(rs.getString(52));
				mg.setMaxGw7_1(rs.getString(53));
				mg.setMaxGw7_2(rs.getString(54));
				mg.setMaxGw7_3(rs.getString(55));
				mg.setMaxGw7_4(rs.getString(56));
				mg.setMaxGw7_5(rs.getString(57));
				mg.setMaxGw7_6(rs.getString(58));
				mg.setMaxGwLane7(rs.getString(59));
				mg.setMaxGw8_1(rs.getString(60));
				mg.setMaxGw8_2(rs.getString(61));
				mg.setMaxGw8_3(rs.getString(62));
				mg.setMaxGw8_4(rs.getString(63));
				mg.setMaxGw8_5(rs.getString(64));
				mg.setMaxGw8_6(rs.getString(65));
				mg.setMaxGwLane8(rs.getString(66));
				mg.setMaxGwHax2_1(rs.getString(67));
				mg.setMaxGwHax2_2(rs.getString(68));
				mg.setMaxGwHax2_3(rs.getString(69));
				mg.setMaxGwHax2_4(rs.getString(70));
				mg.setMaxGwHax2_5(rs.getString(71));
				mg.setMaxGwHax2_6(rs.getString(72));
				mg.setMaxGwHalf2(rs.getString(73));
				mg.setMaxGwAxle1(rs.getString(74));
				mg.setMaxGwAxle2(rs.getString(75));
				mg.setMaxGwAxle3(rs.getString(76));
				mg.setMaxGwAxle4(rs.getString(77));
				mg.setMaxGwAxle5(rs.getString(78));
				mg.setMaxGwAxle6(rs.getString(79));
				mg.setMaxGwAll(rs.getString(80));
				list.add(mg);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}
	public List<MaxGwInfo>selectMaxGwInfo(String brg_id,String brg_mode,String time,String lastTime){
		List<MaxGwInfo>list=new ArrayList<>();
		String sql=null;
		ResultSet rs=null;
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		if(lastTime==""||lastTime==null){
			sql="SELECT * from brg_weight_max_gw_info where brg_mode=? and brg_id=? and brg_startime like ? ORDER BY brg_startime desc  LIMIT 1";
			rs =dataOperation.executeQuery(sql,new String[]{brg_mode,brg_id,time+"%"});
		}else{
			sql="SELECT * from brg_weight_max_gw_info where brg_mode=? and brg_id=? and brg_startime > ? and brg_startime < ? ORDER BY brg_startime desc LIMIT 1";
			rs =dataOperation.executeQuery(sql,new String[]{brg_mode,brg_id,lastTime,time});
		}
		
		try {
			while (rs.next()) {
				MaxGwInfo mgi=new MaxGwInfo();
				mgi.setBrgId(rs.getString(1));
				mgi.setBrgMode(rs.getString(2));
				mgi.setBrgStartime(rs.getString(3));
				mgi.setMaxGw1_1(rs.getString(4));
				mgi.setMaxGw1_2(rs.getString(5));
				mgi.setMaxGw1_3(rs.getString(6));
				mgi.setMaxGw1_4(rs.getString(7));
				mgi.setMaxGw1_5(rs.getString(8));
				mgi.setMaxGw1_6(rs.getString(9));
				mgi.setMaxGwLane1(rs.getString(10));
				mgi.setMaxGw2_1(rs.getString(11));
				mgi.setMaxGw2_2(rs.getString(12));
				mgi.setMaxGw2_3(rs.getString(13));
				mgi.setMaxGw2_4(rs.getString(14));
				mgi.setMaxGw2_5(rs.getString(15));
				mgi.setMaxGw2_6(rs.getString(16));
				mgi.setMaxGwLane2(rs.getString(17));
				mgi.setMaxGw3_1(rs.getString(18));
				mgi.setMaxGw3_2(rs.getString(19));
				mgi.setMaxGw3_3(rs.getString(20));
				mgi.setMaxGw3_4(rs.getString(21));
				mgi.setMaxGw3_5(rs.getString(22));
				mgi.setMaxGw3_6(rs.getString(23));
				mgi.setMaxGwLane3(rs.getString(24));
				mgi.setMaxGwHax1_1(rs.getString(25));
				mgi.setMaxGwHax1_2(rs.getString(26));
				mgi.setMaxGwHax1_3(rs.getString(27));
				mgi.setMaxGwHax1_4(rs.getString(28));
				mgi.setMaxGwHax1_5(rs.getString(29));
				mgi.setMaxGwHax1_6(rs.getString(30));
				mgi.setMaxGwHalf1(rs.getString(31));
				mgi.setMaxGw4_1(rs.getString(32));
				mgi.setMaxGw4_2(rs.getString(33));
				mgi.setMaxGw4_3(rs.getString(34));
				mgi.setMaxGw4_4(rs.getString(35));
				mgi.setMaxGw4_5(rs.getString(36));
				mgi.setMaxGw4_6(rs.getString(37));
				mgi.setMaxGwLane4(rs.getString(38));
				mgi.setMaxGw5_1(rs.getString(39));
				mgi.setMaxGw5_2(rs.getString(40));
				mgi.setMaxGw5_3(rs.getString(41));
				mgi.setMaxGw5_4(rs.getString(42));
				mgi.setMaxGw5_5(rs.getString(43));
				mgi.setMaxGw5_6(rs.getString(44));
				mgi.setMaxGwLane5(rs.getString(45));
				mgi.setMaxGw6_1(rs.getString(46));
				mgi.setMaxGw6_2(rs.getString(47));
				mgi.setMaxGw6_3(rs.getString(48));
				mgi.setMaxGw6_4(rs.getString(49));
				mgi.setMaxGw6_5(rs.getString(50));
				mgi.setMaxGw6_6(rs.getString(51));
				mgi.setMaxGwLane6(rs.getString(52));
				mgi.setMaxGwHax2_1(rs.getString(53));
				mgi.setMaxGwHax2_2(rs.getString(54));
				mgi.setMaxGwHax2_3(rs.getString(55));
				mgi.setMaxGwHax2_4(rs.getString(56));
				mgi.setMaxGwHax2_5(rs.getString(57));
				mgi.setMaxGwHax2_6(rs.getString(58));
				mgi.setMaxGwHalf2(rs.getString(59));
				mgi.setMaxGwAxle1(rs.getString(60));
				mgi.setMaxGwAxle2(rs.getString(61));
				mgi.setMaxGwAxle3(rs.getString(62));
				mgi.setMaxGwAxle4(rs.getString(63));
				mgi.setMaxGwAxle5(rs.getString(64));
				mgi.setMaxGwAxle6(rs.getString(65));
				mgi.setMaxGwAll(rs.getString(66));
				list.add(mgi);				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}
	
	public List<MaxGwInfo8>selectMaxGwInfo8(String brg_id,String brg_mode,String time,String lastTime){
		List<MaxGwInfo8>list=new ArrayList<MaxGwInfo8>();
		String sql=null;
		ResultSet rs=null;
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		if(lastTime==""||lastTime==null){
			sql="SELECT * from brg_weight_max_gw_info_8 where brg_mode=? and brg_id=? and brg_startime like ? ORDER BY brg_startime desc  LIMIT 1";
			rs =dataOperation.executeQuery(sql,new String[]{brg_mode,brg_id,time+"%"});
		}else{
			sql="SELECT * from brg_weight_max_gw_info_8 where brg_mode=? and brg_id=? and brg_startime > ? and brg_startime < ? ORDER BY brg_startime desc LIMIT 1";
			rs =dataOperation.executeQuery(sql,new String[]{brg_mode,brg_id,lastTime,time});
		}
		
		if (rs == null) {
			list = null;
		}
		try {
			while (rs.next()) {
				MaxGwInfo8 mg=new MaxGwInfo8();
				mg.setBrgId(rs.getString(1));
				mg.setBrgMode(rs.getString(2));
				mg.setBrgStartime(rs.getString(3));
				mg.setMaxGw1_1(rs.getString(4));
				mg.setMaxGw1_2(rs.getString(5));
				mg.setMaxGw1_3(rs.getString(6));
				mg.setMaxGw1_4(rs.getString(7));
				mg.setMaxGw1_5(rs.getString(8));
				mg.setMaxGw1_6(rs.getString(9));
				mg.setMaxGwLane1(rs.getString(10));
				mg.setMaxGw2_1(rs.getString(11));
				mg.setMaxGw2_2(rs.getString(12));
				mg.setMaxGw2_3(rs.getString(13));
				mg.setMaxGw2_4(rs.getString(14));
				mg.setMaxGw2_5(rs.getString(15));
				mg.setMaxGw2_6(rs.getString(16));
				mg.setMaxGwLane2(rs.getString(17));
				mg.setMaxGw3_1(rs.getString(18));
				mg.setMaxGw3_2(rs.getString(19));
				mg.setMaxGw3_3(rs.getString(20));
				mg.setMaxGw3_4(rs.getString(21));
				mg.setMaxGw3_5(rs.getString(22));
				mg.setMaxGw3_6(rs.getString(23));
				mg.setMaxGwLane3(rs.getString(24));
				mg.setMaxGw4_1(rs.getString(25));
				mg.setMaxGw4_2(rs.getString(26));
				mg.setMaxGw4_3(rs.getString(27));
				mg.setMaxGw4_4(rs.getString(28));
				mg.setMaxGw4_5(rs.getString(29));
				mg.setMaxGw4_6(rs.getString(30));
				mg.setMaxGwLane4(rs.getString(31));
				mg.setMaxGwHax1_1(rs.getString(32));
				mg.setMaxGwHax1_2(rs.getString(33));
				mg.setMaxGwHax1_3(rs.getString(34));
				mg.setMaxGwHax1_4(rs.getString(35));
				mg.setMaxGwHax1_5(rs.getString(36));
				mg.setMaxGwHax1_6(rs.getString(37));
				mg.setMaxGwHalf1(rs.getString(38));
				mg.setMaxGw5_1(rs.getString(39));
				mg.setMaxGw5_2(rs.getString(40));
				mg.setMaxGw5_3(rs.getString(41));
				mg.setMaxGw5_4(rs.getString(42));
				mg.setMaxGw5_5(rs.getString(43));
				mg.setMaxGw5_6(rs.getString(44));
				mg.setMaxGwLane5(rs.getString(45));
				mg.setMaxGw6_1(rs.getString(46));
				mg.setMaxGw6_2(rs.getString(47));
				mg.setMaxGw6_3(rs.getString(48));
				mg.setMaxGw6_4(rs.getString(49));
				mg.setMaxGw6_5(rs.getString(50));
				mg.setMaxGw6_6(rs.getString(51));
				mg.setMaxGwLane6(rs.getString(52));
				mg.setMaxGw7_1(rs.getString(53));
				mg.setMaxGw7_2(rs.getString(54));
				mg.setMaxGw7_3(rs.getString(55));
				mg.setMaxGw7_4(rs.getString(56));
				mg.setMaxGw7_5(rs.getString(57));
				mg.setMaxGw7_6(rs.getString(58));
				mg.setMaxGwLane7(rs.getString(59));
				mg.setMaxGw8_1(rs.getString(60));
				mg.setMaxGw8_2(rs.getString(61));
				mg.setMaxGw8_3(rs.getString(62));
				mg.setMaxGw8_4(rs.getString(63));
				mg.setMaxGw8_5(rs.getString(64));
				mg.setMaxGw8_6(rs.getString(65));
				mg.setMaxGwLane8(rs.getString(66));
				mg.setMaxGwHax2_1(rs.getString(67));
				mg.setMaxGwHax2_2(rs.getString(68));
				mg.setMaxGwHax2_3(rs.getString(69));
				mg.setMaxGwHax2_4(rs.getString(70));
				mg.setMaxGwHax2_5(rs.getString(71));
				mg.setMaxGwHax2_6(rs.getString(72));
				mg.setMaxGwHalf2(rs.getString(73));
				mg.setMaxGwAxle1(rs.getString(74));
				mg.setMaxGwAxle2(rs.getString(75));
				mg.setMaxGwAxle3(rs.getString(76));
				mg.setMaxGwAxle4(rs.getString(77));
				mg.setMaxGwAxle5(rs.getString(78));
				mg.setMaxGwAxle6(rs.getString(79));
				mg.setMaxGwAll(rs.getString(80));
				list.add(mg);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}
	
	public List<NumOvlo>selectNumOvlo(String brg_id,String brg_mode,String time,String lastTime){
		List<NumOvlo>list=new ArrayList<>();
		String sql=null;
		ResultSet rs=null;
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		if(lastTime==""||lastTime==null){
			sql="SELECT * from brg_weight_num_ovlo where brg_mode=? and brg_id=? and brg_startime like ? ORDER BY brg_startime desc  LIMIT 1";
			rs =dataOperation.executeQuery(sql,new String[]{brg_mode,brg_id,time+"%"});
		}else{
			sql="SELECT * from brg_weight_num_ovlo where brg_mode=? and brg_id=? and brg_startime > ? and brg_startime < ? ORDER BY brg_startime desc LIMIT 1";
			rs =dataOperation.executeQuery(sql,new String[]{brg_mode,brg_id,lastTime,time});
		}
		
		if (rs == null) {
			list = null;
		}
		
		try {
			while (rs.next()) {
				NumOvlo no=new NumOvlo();
				no.setBrgId(rs.getString(1));
				no.setBrgMode(rs.getString(2));
				no.setBrgStartime(rs.getString(3));
				no.setNumOvlo1_1(rs.getString(4));
				no.setNumOvlo1_2(rs.getString(5));
				no.setNumOvlo1_3(rs.getString(6));
				no.setNumOvlo1_4(rs.getString(7));
				no.setNumOvlo1_5(rs.getString(8));
				no.setNumOvlo1_6(rs.getString(9));
				no.setNumOvloLane1(rs.getString(10));
				no.setNumOvlo2_1(rs.getString(11));
				no.setNumOvlo2_2(rs.getString(12));
				no.setNumOvlo2_3(rs.getString(13));
				no.setNumOvlo2_4(rs.getString(14));
				no.setNumOvlo2_5(rs.getString(15));
				no.setNumOvlo2_6(rs.getString(16));
				no.setNumOvloLane2(rs.getString(17));
				no.setNumOvlo3_1(rs.getString(18));
				no.setNumOvlo3_2(rs.getString(19));
				no.setNumOvlo3_3(rs.getString(20));
				no.setNumOvlo3_4(rs.getString(21));
				no.setNumOvlo3_5(rs.getString(22));
				no.setNumOvlo3_6(rs.getString(23));
				no.setNumOvloLane3(rs.getString(24));
				no.setNumOvloHax1_1(rs.getString(25));
				no.setNumOvloHax1_2(rs.getString(26));
				no.setNumOvloHax1_3(rs.getString(27));
				no.setNumOvloHax1_4(rs.getString(28));
				no.setNumOvloHax1_5(rs.getString(29));
				no.setNumOvloHax1_6(rs.getString(30));
				no.setNumOvloHalf1(rs.getString(31));
				no.setNumOvlo4_1(rs.getString(32));
				no.setNumOvlo4_2(rs.getString(33));
				no.setNumOvlo4_3(rs.getString(34));
				no.setNumOvlo4_4(rs.getString(35));
				no.setNumOvlo4_5(rs.getString(36));
				no.setNumOvlo4_6(rs.getString(37));
				no.setNumOvloLane4(rs.getString(38));
				no.setNumOvlo5_1(rs.getString(39));
				no.setNumOvlo5_2(rs.getString(40));
				no.setNumOvlo5_3(rs.getString(41));
				no.setNumOvlo5_4(rs.getString(42));
				no.setNumOvlo5_5(rs.getString(43));
				no.setNumOvlo5_6(rs.getString(44));
				no.setNumOvloLane5(rs.getString(45));
				no.setNumOvlo6_1(rs.getString(46));
				no.setNumOvlo6_2(rs.getString(47));
				no.setNumOvlo6_3(rs.getString(48));
				no.setNumOvlo6_4(rs.getString(49));
				no.setNumOvlo6_5(rs.getString(50));
				no.setNumOvlo6_6(rs.getString(51));
				no.setNumOvloLane6(rs.getString(52));
				no.setNumOvloHax2_1(rs.getString(53));
				no.setNumOvloHax2_2(rs.getString(54));
				no.setNumOvloHax2_3(rs.getString(55));
				no.setNumOvloHax2_4(rs.getString(56));
				no.setNumOvloHax2_5(rs.getString(57));
				no.setNumOvloHax2_6(rs.getString(58));
				no.setNumOvloHalf2(rs.getString(59));
				no.setNumOvloAxle1(rs.getString(60));
				no.setNumOvloAxle2(rs.getString(61));
				no.setNumOvloAxle3(rs.getString(62));
				no.setNumOvloAxle4(rs.getString(63));
				no.setNumOvloAxle5(rs.getString(64));
				no.setNumOvloAxle6(rs.getString(65));
				no.setNumOvloAll(rs.getString(66));
				list.add(no);				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;		
	}

	public List<NumOvlo8>selectNumOvlo8(String brg_id,String brg_mode,String time,String lastTime){
		List<NumOvlo8>list=new ArrayList<NumOvlo8>();
		String sql=null;
		ResultSet rs=null;
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		if(lastTime==""||lastTime==null){
			sql="SELECT * from brg_weight_num_ovlo_8 where brg_mode=? and brg_id=? and brg_startime like ? ORDER BY brg_startime desc  LIMIT 1";
			rs =dataOperation.executeQuery(sql,new String[]{brg_mode,brg_id,time+"%"});
		}else{
			sql="SELECT * from brg_weight_num_ovlo_8 where brg_mode=? and brg_id=? and brg_startime > ? and brg_startime < ? ORDER BY brg_startime desc LIMIT 1";
			rs =dataOperation.executeQuery(sql,new String[]{brg_mode,brg_id,lastTime,time});
		}
		
		try {
			while (rs.next()) {
				NumOvlo8 no=new NumOvlo8();
				no.setBrgId(rs.getString(1));
				no.setBrgMode(rs.getString(2));
				no.setBrgStartime(rs.getString(3));
				no.setNumOvlo1_1(rs.getString(4));
				no.setNumOvlo1_2(rs.getString(5));
				no.setNumOvlo1_3(rs.getString(6));
				no.setNumOvlo1_4(rs.getString(7));
				no.setNumOvlo1_5(rs.getString(8));
				no.setNumOvlo1_6(rs.getString(9));
				no.setNumOvloLane1(rs.getString(10));
				no.setNumOvlo2_1(rs.getString(11));
				no.setNumOvlo2_2(rs.getString(12));
				no.setNumOvlo2_3(rs.getString(13));
				no.setNumOvlo2_4(rs.getString(14));
				no.setNumOvlo2_5(rs.getString(15));
				no.setNumOvlo2_6(rs.getString(16));
				no.setNumOvloLane2(rs.getString(17));
				no.setNumOvlo3_1(rs.getString(18));
				no.setNumOvlo3_2(rs.getString(19));
				no.setNumOvlo3_3(rs.getString(20));
				no.setNumOvlo3_4(rs.getString(21));
				no.setNumOvlo3_5(rs.getString(22));
				no.setNumOvlo3_6(rs.getString(23));
				no.setNumOvloLane3(rs.getString(24));
				no.setNumOvlo4_1(rs.getString(25));
				no.setNumOvlo4_2(rs.getString(26));
				no.setNumOvlo4_3(rs.getString(27));
				no.setNumOvlo4_4(rs.getString(28));
				no.setNumOvlo4_5(rs.getString(29));
				no.setNumOvlo4_6(rs.getString(30));
				no.setNumOvloLane4(rs.getString(31));
				no.setNumOvloHax1_1(rs.getString(32));
				no.setNumOvloHax1_2(rs.getString(33));
				no.setNumOvloHax1_3(rs.getString(34));
				no.setNumOvloHax1_4(rs.getString(35));
				no.setNumOvloHax1_5(rs.getString(36));
				no.setNumOvloHax1_6(rs.getString(37));
				no.setNumOvloHalf1(rs.getString(38));
				no.setNumOvlo5_1(rs.getString(39));
				no.setNumOvlo5_2(rs.getString(40));
				no.setNumOvlo5_3(rs.getString(41));
				no.setNumOvlo5_4(rs.getString(42));
				no.setNumOvlo5_5(rs.getString(43));
				no.setNumOvlo5_6(rs.getString(44));
				no.setNumOvloLane5(rs.getString(45));
				no.setNumOvlo6_1(rs.getString(46));
				no.setNumOvlo6_2(rs.getString(47));
				no.setNumOvlo6_3(rs.getString(48));
				no.setNumOvlo6_4(rs.getString(49));
				no.setNumOvlo6_5(rs.getString(50));
				no.setNumOvlo6_6(rs.getString(51));
				no.setNumOvloLane6(rs.getString(52));
				no.setNumOvlo7_1(rs.getString(53));
				no.setNumOvlo7_2(rs.getString(54));
				no.setNumOvlo7_3(rs.getString(55));
				no.setNumOvlo7_4(rs.getString(56));
				no.setNumOvlo7_5(rs.getString(57));
				no.setNumOvlo7_6(rs.getString(58));
				no.setNumOvloLane7(rs.getString(59));
				no.setNumOvlo8_1(rs.getString(60));
				no.setNumOvlo8_2(rs.getString(61));
				no.setNumOvlo8_3(rs.getString(62));
				no.setNumOvlo8_4(rs.getString(63));
				no.setNumOvlo8_5(rs.getString(64));
				no.setNumOvlo8_6(rs.getString(65));
				no.setNumOvloLane8(rs.getString(66));
				no.setNumOvloHax2_1(rs.getString(67));
				no.setNumOvloHax2_2(rs.getString(68));
				no.setNumOvloHax2_3(rs.getString(69));
				no.setNumOvloHax2_4(rs.getString(70));
				no.setNumOvloHax2_5(rs.getString(71));
				no.setNumOvloHax2_6(rs.getString(72));
				no.setNumOvloHalf2(rs.getString(73));
				no.setNumOvloAxle1(rs.getString(74));
				no.setNumOvloAxle2(rs.getString(75));
				no.setNumOvloAxle3(rs.getString(76));
				no.setNumOvloAxle4(rs.getString(77));
				no.setNumOvloAxle5(rs.getString(78));
				no.setNumOvloAxle6(rs.getString(79));
				no.setNumOvloAll(rs.getString(80));
				list.add(no);				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;		
	}
	public List<RatioOvlo>selectRatioOvlo(String brg_id,String brg_mode,String time,String lastTime){
		List<RatioOvlo>list=new ArrayList<>();
		String sql=null;
		ResultSet rs=null;
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		if(lastTime==""||lastTime==null){
			sql="SELECT * from brg_weight_ratio_ovlo where brg_mode=? and brg_id=? and brg_startime like ? ORDER BY brg_startime desc  LIMIT 1";
			rs =dataOperation.executeQuery(sql,new String[]{brg_mode,brg_id,time+"%"});
		}else{
			sql="SELECT * from brg_weight_ratio_ovlo where brg_mode=? and brg_id=? and brg_startime > ? and brg_startime < ? ORDER BY brg_startime desc LIMIT 1";
			rs =dataOperation.executeQuery(sql,new String[]{brg_mode,brg_id,lastTime,time});
		}
		
		if (rs == null) {
			list = null;
		}
		
		try {
			while (rs.next()) {
				RatioOvlo ro=new RatioOvlo();
				ro.setBrgId(rs.getString(1));
				ro.setBrgMode(rs.getString(2));
				ro.setBrgStartime(rs.getString(3));
				ro.setRatioOvlo1_1(rs.getString(4));
				ro.setRatioOvlo1_2(rs.getString(5));
				ro.setRatioOvlo1_3(rs.getString(6));
				ro.setRatioOvlo1_4(rs.getString(7));
				ro.setRatioOvlo1_5(rs.getString(8));
				ro.setRatioOvlo1_6(rs.getString(9));
				ro.setRatioOvloLane1(rs.getString(10));
				ro.setRatioOvlo2_1(rs.getString(11));
				ro.setRatioOvlo2_2(rs.getString(12));
				ro.setRatioOvlo2_3(rs.getString(13));
				ro.setRatioOvlo2_4(rs.getString(14));
				ro.setRatioOvlo2_5(rs.getString(15));
				ro.setRatioOvlo2_6(rs.getString(16));
				ro.setRatioOvloLane2(rs.getString(17));
				ro.setRatioOvlo3_1(rs.getString(18));
				ro.setRatioOvlo3_2(rs.getString(19));
				ro.setRatioOvlo3_3(rs.getString(20));
				ro.setRatioOvlo3_4(rs.getString(21));
				ro.setRatioOvlo3_5(rs.getString(22));
				ro.setRatioOvlo3_6(rs.getString(23));
				ro.setRatioOvloLane3(rs.getString(24));
				ro.setRatioOvloHax1_1(rs.getString(25));
				ro.setRatioOvloHax1_2(rs.getString(26));
				ro.setRatioOvloHax1_3(rs.getString(27));
				ro.setRatioOvloHax1_4(rs.getString(28));
				ro.setRatioOvloHax1_5(rs.getString(29));
				ro.setRatioOvloHax1_6(rs.getString(30));
				ro.setRatioOvloHalf1(rs.getString(31));
				ro.setRatioOvlo4_1(rs.getString(32));
				ro.setRatioOvlo4_2(rs.getString(33));
				ro.setRatioOvlo4_3(rs.getString(34));
				ro.setRatioOvlo4_4(rs.getString(35));
				ro.setRatioOvlo4_5(rs.getString(36));
				ro.setRatioOvlo4_6(rs.getString(37));
				ro.setRatioOvloLane4(rs.getString(38));
				ro.setRatioOvlo5_1(rs.getString(39));
				ro.setRatioOvlo5_2(rs.getString(40));
				ro.setRatioOvlo5_3(rs.getString(41));
				ro.setRatioOvlo5_4(rs.getString(42));
				ro.setRatioOvlo5_5(rs.getString(43));
				ro.setRatioOvlo5_6(rs.getString(44));
				ro.setRatioOvloLane5(rs.getString(45));
				ro.setRatioOvlo6_1(rs.getString(46));
				ro.setRatioOvlo6_2(rs.getString(47));
				ro.setRatioOvlo6_3(rs.getString(48));
				ro.setRatioOvlo6_4(rs.getString(49));
				ro.setRatioOvlo6_5(rs.getString(50));
				ro.setRatioOvlo6_6(rs.getString(51));
				ro.setRatioOvloLane6(rs.getString(52));
				ro.setRatioOvloHax2_1(rs.getString(53));
				ro.setRatioOvloHax2_2(rs.getString(54));
				ro.setRatioOvloHax2_3(rs.getString(55));
				ro.setRatioOvloHax2_4(rs.getString(56));
				ro.setRatioOvloHax2_5(rs.getString(57));
				ro.setRatioOvloHax2_6(rs.getString(58));
				ro.setRatioOvloHalf2(rs.getString(59));
				ro.setRatioOvloAxle1(rs.getString(60));
				ro.setRatioOvloAxle2(rs.getString(61));
				ro.setRatioOvloAxle3(rs.getString(62));
				ro.setRatioOvloAxle4(rs.getString(63));
				ro.setRatioOvloAxle5(rs.getString(64));
				ro.setRatioOvloAxle6(rs.getString(65));
				ro.setRatioOvloAll(rs.getString(66));
				list.add(ro);				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
		
	}	
	
	public List<RatioOvlo8>selectRatioOvlo8(String brg_id,String brg_mode,String time,String lastTime){
		List<RatioOvlo8>list=new ArrayList<>();
		String sql=null;
		ResultSet rs=null;
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		if(lastTime==""||lastTime==null){
			sql="SELECT * from brg_weight_ratio_ovlo_8 where brg_mode=? and brg_id=? and brg_startime like ? ORDER BY brg_startime desc  LIMIT 1";
			rs =dataOperation.executeQuery(sql,new String[]{brg_mode,brg_id,time+"%"});
		}else{
			sql="SELECT * from brg_weight_ratio_ovlo_8 where brg_mode=? and brg_id=? and brg_startime > ? and brg_startime < ? ORDER BY brg_startime desc LIMIT 1";
			rs =dataOperation.executeQuery(sql,new String[]{brg_mode,brg_id,lastTime,time});
		}
		
		if (rs == null) {
			list = null;
		}
		
		try {
			while (rs.next()) {
				RatioOvlo8 ro=new RatioOvlo8();
				ro.setBrgId(rs.getString(1));
				ro.setBrgMode(rs.getString(2));
				ro.setBrgStartime(rs.getString(3));
				ro.setRatioOvlo1_1(rs.getString(4));
				ro.setRatioOvlo1_2(rs.getString(5));
				ro.setRatioOvlo1_3(rs.getString(6));
				ro.setRatioOvlo1_4(rs.getString(7));
				ro.setRatioOvlo1_5(rs.getString(8));
				ro.setRatioOvlo1_6(rs.getString(9));
				ro.setRatioOvloLane1(rs.getString(10));
				ro.setRatioOvlo2_1(rs.getString(11));
				ro.setRatioOvlo2_2(rs.getString(12));
				ro.setRatioOvlo2_3(rs.getString(13));
				ro.setRatioOvlo2_4(rs.getString(14));
				ro.setRatioOvlo2_5(rs.getString(15));
				ro.setRatioOvlo2_6(rs.getString(16));
				ro.setRatioOvloLane2(rs.getString(17));
				ro.setRatioOvlo3_1(rs.getString(18));
				ro.setRatioOvlo3_2(rs.getString(19));
				ro.setRatioOvlo3_3(rs.getString(20));
				ro.setRatioOvlo3_4(rs.getString(21));
				ro.setRatioOvlo3_5(rs.getString(22));
				ro.setRatioOvlo3_6(rs.getString(23));
				ro.setRatioOvloLane3(rs.getString(24));
				ro.setRatioOvlo4_1(rs.getString(25));
				ro.setRatioOvlo4_2(rs.getString(26));
				ro.setRatioOvlo4_3(rs.getString(27));
				ro.setRatioOvlo4_4(rs.getString(28));
				ro.setRatioOvlo4_5(rs.getString(29));
				ro.setRatioOvlo4_6(rs.getString(30));
				ro.setRatioOvloLane4(rs.getString(31));
				ro.setRatioOvloHax1_1(rs.getString(32));
				ro.setRatioOvloHax1_2(rs.getString(33));
				ro.setRatioOvloHax1_3(rs.getString(34));
				ro.setRatioOvloHax1_4(rs.getString(35));
				ro.setRatioOvloHax1_5(rs.getString(36));
				ro.setRatioOvloHax1_6(rs.getString(37));
				ro.setRatioOvloHalf1(rs.getString(38));
				ro.setRatioOvlo5_1(rs.getString(39));
				ro.setRatioOvlo5_2(rs.getString(40));
				ro.setRatioOvlo5_3(rs.getString(41));
				ro.setRatioOvlo5_4(rs.getString(42));
				ro.setRatioOvlo5_5(rs.getString(43));
				ro.setRatioOvlo5_6(rs.getString(44));
				ro.setRatioOvloLane5(rs.getString(45));
				ro.setRatioOvlo6_1(rs.getString(46));
				ro.setRatioOvlo6_2(rs.getString(47));
				ro.setRatioOvlo6_3(rs.getString(48));
				ro.setRatioOvlo6_4(rs.getString(49));
				ro.setRatioOvlo6_5(rs.getString(50));
				ro.setRatioOvlo6_6(rs.getString(51));
				ro.setRatioOvloLane6(rs.getString(52));
				ro.setRatioOvlo7_1(rs.getString(53));
				ro.setRatioOvlo7_2(rs.getString(54));
				ro.setRatioOvlo7_3(rs.getString(55));
				ro.setRatioOvlo7_4(rs.getString(56));
				ro.setRatioOvlo7_5(rs.getString(57));
				ro.setRatioOvlo7_6(rs.getString(58));
				ro.setRatioOvloLane7(rs.getString(59));
				ro.setRatioOvlo8_1(rs.getString(60));
				ro.setRatioOvlo8_2(rs.getString(61));
				ro.setRatioOvlo8_3(rs.getString(62));
				ro.setRatioOvlo8_4(rs.getString(63));
				ro.setRatioOvlo8_5(rs.getString(64));
				ro.setRatioOvlo8_6(rs.getString(65));
				ro.setRatioOvloLane8(rs.getString(66));
				ro.setRatioOvloHax2_1(rs.getString(67));
				ro.setRatioOvloHax2_2(rs.getString(68));
				ro.setRatioOvloHax2_3(rs.getString(69));
				ro.setRatioOvloHax2_4(rs.getString(70));
				ro.setRatioOvloHax2_5(rs.getString(71));
				ro.setRatioOvloHax2_6(rs.getString(72));
				ro.setRatioOvloHalf2(rs.getString(73));
				ro.setRatioOvloAxle1(rs.getString(74));
				ro.setRatioOvloAxle2(rs.getString(75));
				ro.setRatioOvloAxle3(rs.getString(76));
				ro.setRatioOvloAxle4(rs.getString(77));
				ro.setRatioOvloAxle5(rs.getString(78));
				ro.setRatioOvloAxle6(rs.getString(79));
				ro.setRatioOvloAll(rs.getString(80));
				list.add(ro);				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
		
	}	
	public List<BrgWeightLoadRatio> getOneByID(String brg_id,String brg_mode,String time,String lastTime){
		List<BrgWeightLoadRatio>list=new ArrayList<>();
		String sql=null;
		ResultSet rs=null;
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		if(lastTime==""||lastTime==null){
			sql="SELECT * from brg_weight_load_radio where brg_mode=? and brg_id=? and brg_startime like ? ORDER BY brg_startime desc  LIMIT 1";
			rs =dataOperation.executeQuery(sql,new String[]{brg_mode,brg_id,time+"%"});
		}else{
			sql="SELECT * from brg_weight_load_radio where brg_mode=? and brg_id=? and brg_startime > ? and brg_startime < ? ORDER BY brg_startime desc LIMIT 1";
			rs =dataOperation.executeQuery(sql,new String[]{brg_mode,brg_id,lastTime,time});
		}
		
		try {
			while (rs.next()) {
				BrgWeightLoadRatio entity = new BrgWeightLoadRatio();
				entity.setBridge_id(rs.getString("brg_id"));
				entity.setBrg_mode(rs.getString("brg_mode"));
				entity.setStart_time(rs.getString("brg_startime"));
				entity.setLoad_radio1(rs.getString("load_radio1"));
				entity.setLoad_radio2(rs.getString("load_radio2"));
				entity.setLoad_radio3(rs.getString("load_radio3"));
				entity.setLoad_radio4(rs.getString("load_radio4"));
				entity.setLoad_radio5(rs.getString("load_radio5"));
				entity.setLoad_radio6(rs.getString("load_radio6"));
				entity.setLoad_radio7(rs.getString("load_radio7"));
				entity.setLoad_radio8(rs.getString("load_radio8"));
				entity.setLoad_radio9(rs.getString("load_radio9"));
				entity.setLoad_radio10(rs.getString("load_radio10"));
				entity.setLoad_radio11(rs.getString("load_radio11"));
				entity.setLoad_radio12(rs.getString("load_radio12"));
				entity.setLoad_radio13(rs.getString("load_radio13"));
				entity.setLoad_radio14(rs.getString("load_radio14"));
				entity.setLoad_radio15(rs.getString("load_radio15"));
				entity.setLoad_radio16(rs.getString("load_radio16"));
				list.add(entity);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
		
	}
	
	
	public List<ProbOvlo>selectProbOvlo(String brg_id,String brg_mode,String time,String lastTime){
		List<ProbOvlo>list=new ArrayList<>();
		String sql=null;
		ResultSet rs=null;
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		if(lastTime==""||lastTime==null){
			sql="SELECT * from brg_weight_prob_ovlo where brg_mode=? and brg_id=? and brg_startime like ? ORDER BY brg_startime desc  LIMIT 1";
			rs =dataOperation.executeQuery(sql,new String[]{brg_mode,brg_id,time+"%"});
		}else{
			sql="SELECT * from brg_weight_prob_ovlo where brg_mode=? and brg_id=? and brg_startime > ? and brg_startime < ? ORDER BY brg_startime desc LIMIT 1";
			rs =dataOperation.executeQuery(sql,new String[]{brg_mode,brg_id,lastTime,time});
		}
		
		if (rs == null) {
			list = null;
		}
		
		try {
			while (rs.next()) {
				ProbOvlo po=new ProbOvlo();
				po.setBrgId(rs.getString(1));
				po.setBrgMode(rs.getString(2));
				po.setBrgStartime(rs.getString(3));
				po.setProbOvlo1_1(rs.getString(4));
				po.setProbOvlo1_2(rs.getString(5));
				po.setProbOvlo1_3(rs.getString(6));
				po.setProbOvlo1_4(rs.getString(7));
				po.setProbOvlo1_5(rs.getString(8));
				po.setProbOvlo1_6(rs.getString(9));
				po.setProbOvloLane1(rs.getString(10));
				po.setProbOvlo2_1(rs.getString(11));
				po.setProbOvlo2_2(rs.getString(12));
				po.setProbOvlo2_3(rs.getString(13));
				po.setProbOvlo2_4(rs.getString(14));
				po.setProbOvlo2_5(rs.getString(15));
				po.setProbOvlo2_6(rs.getString(16));
				po.setProbOvloLane2(rs.getString(17));
				po.setProbOvlo3_1(rs.getString(18));
				po.setProbOvlo3_2(rs.getString(19));
				po.setProbOvlo3_3(rs.getString(20));
				po.setProbOvlo3_4(rs.getString(21));
				po.setProbOvlo3_5(rs.getString(22));
				po.setProbOvlo3_6(rs.getString(23));
				po.setProbOvloLane3(rs.getString(24));
				po.setProbOvloHax1_1(rs.getString(25));
				po.setProbOvloHax1_2(rs.getString(26));
				po.setProbOvloHax1_3(rs.getString(27));
				po.setProbOvloHax1_4(rs.getString(28));
				po.setProbOvloHax1_5(rs.getString(29));
				po.setProbOvloHax1_6(rs.getString(30));
				po.setProbOvloHalf1(rs.getString(31));
				po.setProbOvlo4_1(rs.getString(32));
				po.setProbOvlo4_2(rs.getString(33));
				po.setProbOvlo4_3(rs.getString(34));
				po.setProbOvlo4_4(rs.getString(35));
				po.setProbOvlo4_5(rs.getString(36));
				po.setProbOvlo4_6(rs.getString(37));
				po.setProbOvloLane4(rs.getString(38));
				po.setProbOvlo5_1(rs.getString(39));
				po.setProbOvlo5_2(rs.getString(40));
				po.setProbOvlo5_3(rs.getString(41));
				po.setProbOvlo5_4(rs.getString(42));
				po.setProbOvlo5_5(rs.getString(43));
				po.setProbOvlo5_6(rs.getString(44));
				po.setProbOvloLane5(rs.getString(45));
				po.setProbOvlo6_1(rs.getString(46));
				po.setProbOvlo6_2(rs.getString(47));
				po.setProbOvlo6_3(rs.getString(48));
				po.setProbOvlo6_4(rs.getString(49));
				po.setProbOvlo6_5(rs.getString(50));
				po.setProbOvlo6_6(rs.getString(51));
				po.setProbOvloLane6(rs.getString(52));
				po.setProbOvloHax2_1(rs.getString(53));
				po.setProbOvloHax2_2(rs.getString(54));
				po.setProbOvloHax2_3(rs.getString(55));
				po.setProbOvloHax2_4(rs.getString(56));
				po.setProbOvloHax2_5(rs.getString(57));
				po.setProbOvloHax2_6(rs.getString(58));
				po.setProbOvloHalf2(rs.getString(59));
				po.setProbOvloAxle1(rs.getString(60));
				po.setProbOvloAxle2(rs.getString(61));
				po.setProbOvloAxle3(rs.getString(62));
				po.setProbOvloAxle4(rs.getString(63));
				po.setProbOvloAxle5(rs.getString(64));
				po.setProbOvloAxle6(rs.getString(65));
				po.setProbOvloAll(rs.getString(66));
				list.add(po);				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;		
	}
	
	public List<ProbOvlo8>selectProbOvlo8(String brg_id,String brg_mode,String time,String lastTime){
		List<ProbOvlo8>list=new ArrayList<>();
		String sql=null;
		ResultSet rs=null;
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		if(lastTime==""||lastTime==null){
			sql="SELECT * from brg_weight_prob_ovlo_8 where brg_mode=? and brg_id=? and brg_startime like ? ORDER BY brg_startime desc  LIMIT 1";
			rs =dataOperation.executeQuery(sql,new String[]{brg_mode,brg_id,time+"%"});
		}else{
			sql="SELECT * from brg_weight_prob_ovlo_8 where brg_mode=? and brg_id=? and brg_startime > ? and brg_startime < ? ORDER BY brg_startime desc LIMIT 1";
			rs =dataOperation.executeQuery(sql,new String[]{brg_mode,brg_id,lastTime,time});
		}
		
		if (rs == null) {
			list = null;
		}
		
		try {
			while (rs.next()) {
				ProbOvlo8 po=new ProbOvlo8();
				po.setBrgId(rs.getString(1));
				po.setBrgMode(rs.getString(2));
				po.setBrgStartime(rs.getString(3));
				po.setProbOvlo1_1(rs.getString(4));
				po.setProbOvlo1_2(rs.getString(5));
				po.setProbOvlo1_3(rs.getString(6));
				po.setProbOvlo1_4(rs.getString(7));
				po.setProbOvlo1_5(rs.getString(8));
				po.setProbOvlo1_6(rs.getString(9));
				po.setProbOvloLane1(rs.getString(10));
				po.setProbOvlo2_1(rs.getString(11));
				po.setProbOvlo2_2(rs.getString(12));
				po.setProbOvlo2_3(rs.getString(13));
				po.setProbOvlo2_4(rs.getString(14));
				po.setProbOvlo2_5(rs.getString(15));
				po.setProbOvlo2_6(rs.getString(16));
				po.setProbOvloLane2(rs.getString(17));
				po.setProbOvlo3_1(rs.getString(18));
				po.setProbOvlo3_2(rs.getString(19));
				po.setProbOvlo3_3(rs.getString(20));
				po.setProbOvlo3_4(rs.getString(21));
				po.setProbOvlo3_5(rs.getString(22));
				po.setProbOvlo3_6(rs.getString(23));
				po.setProbOvloLane3(rs.getString(24));
				po.setProbOvlo4_1(rs.getString(25));
				po.setProbOvlo4_2(rs.getString(26));
				po.setProbOvlo4_3(rs.getString(27));
				po.setProbOvlo4_4(rs.getString(28));
				po.setProbOvlo4_5(rs.getString(29));
				po.setProbOvlo4_6(rs.getString(30));
				po.setProbOvloLane4(rs.getString(31));
				po.setProbOvloHax1_1(rs.getString(32));
				po.setProbOvloHax1_2(rs.getString(33));
				po.setProbOvloHax1_3(rs.getString(34));
				po.setProbOvloHax1_4(rs.getString(35));
				po.setProbOvloHax1_5(rs.getString(36));
				po.setProbOvloHax1_6(rs.getString(37));
				po.setProbOvloHalf1(rs.getString(38));
				po.setProbOvlo5_1(rs.getString(39));
				po.setProbOvlo5_2(rs.getString(40));
				po.setProbOvlo5_3(rs.getString(41));
				po.setProbOvlo5_4(rs.getString(42));
				po.setProbOvlo5_5(rs.getString(43));
				po.setProbOvlo5_6(rs.getString(44));
				po.setProbOvloLane5(rs.getString(45));
				po.setProbOvlo6_1(rs.getString(46));
				po.setProbOvlo6_2(rs.getString(47));
				po.setProbOvlo6_3(rs.getString(48));
				po.setProbOvlo6_4(rs.getString(49));
				po.setProbOvlo6_5(rs.getString(50));
				po.setProbOvlo6_6(rs.getString(51));
				po.setProbOvloLane6(rs.getString(52));
				po.setProbOvlo7_1(rs.getString(53));
				po.setProbOvlo7_2(rs.getString(54));
				po.setProbOvlo7_3(rs.getString(55));
				po.setProbOvlo7_4(rs.getString(56));
				po.setProbOvlo7_5(rs.getString(57));
				po.setProbOvlo7_6(rs.getString(58));
				po.setProbOvloLane7(rs.getString(59));
				po.setProbOvlo8_1(rs.getString(60));
				po.setProbOvlo8_2(rs.getString(61));
				po.setProbOvlo8_3(rs.getString(62));
				po.setProbOvlo8_4(rs.getString(63));
				po.setProbOvlo8_5(rs.getString(64));
				po.setProbOvlo8_6(rs.getString(65));
				po.setProbOvloLane8(rs.getString(66));
				po.setProbOvloHax2_1(rs.getString(67));
				po.setProbOvloHax2_2(rs.getString(68));
				po.setProbOvloHax2_3(rs.getString(69));
				po.setProbOvloHax2_4(rs.getString(70));
				po.setProbOvloHax2_5(rs.getString(71));
				po.setProbOvloHax2_6(rs.getString(72));
				po.setProbOvloHalf2(rs.getString(73));
				po.setProbOvloAxle1(rs.getString(74));
				po.setProbOvloAxle2(rs.getString(75));
				po.setProbOvloAxle3(rs.getString(76));
				po.setProbOvloAxle4(rs.getString(77));
				po.setProbOvloAxle5(rs.getString(78));
				po.setProbOvloAxle6(rs.getString(79));
				po.setProbOvloAll(rs.getString(80));
				list.add(po);				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;		
	}
	public List<Connect> initDataManageTableNews(String mode,String zone_id,String brg_id){
    	List<Connect> lc=new ArrayList<Connect>();
    	Connection conn=MyDataSource.getInstance().getConnection();
    	MyDataOperation mdo=new MyDataOperation(conn,false);
    	String sql=
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
    			"	 AND a.bridge_id=?";
    	ResultSet rs=mdo.executeQuery(sql,new Object[]{zone_id,brg_id});
    	try {
			while (rs.next()) {
				Connect co=new Connect();
				co.setBridge_id(rs.getString("bridge_id"));
				co.setBridge_name(rs.getString("bridge_name"));
				String str_start_time = "0";
				co.setType("bridge");
//				String countTableName = getTableNameByMode(mode, "day");
//				String count = getLastDayCountByTableName(countTableName, brg_id);
//				if(null==count){
//					count="0";
//				}
				co.setCount(0+"%");
//				String countTableName1 = getNewTableNameByMode(mode, "month");
//				String monthcount = getNewLastMonthCountByTableName(countTableName1, brg_id);
//				if(null==monthcount){
//					monthcount="0";
//				}
				co.setDay_count(0+"%");
//				String countTableName2 = getNewTableNameByMode(mode, "year");
//				String yearcount = getNewLastYearCountByTableName(countTableName2, brg_id);
//				if(null==yearcount){
//					yearcount="0";
//				}
				co.setMonth_count(0+"%");
				
				if(rs.getString("bridge_id")==null||str_start_time==null){
					co.setBgco("0");
				}else{
					co.setBgco("1");
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
}
