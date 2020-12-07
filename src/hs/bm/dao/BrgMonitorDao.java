
package hs.bm.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresGuest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.org.hsxx.bean.BrgHealthStatistic;
import hs.bm.bean.BrgMonitor;
import hs.bm.bean.BrgMonitorCableforce;
import hs.bm.bean.BrgMonitorDynadisp;
import hs.bm.bean.BrgMonitorStaticdisp;
import hs.bm.bean.BrgMonitorStrainc;
import hs.bm.bean.BrgMonitorStrains;
import hs.bm.bean.BrgMonitorTemp;
import hs.bm.bean.BrgWeightLoadRatio;
import hs.bm.bean.DicBrgMonitoringItem;
import hs.bm.bean.SqlColumn;
import hs.bm.bean.StaffNumber;

public class BrgMonitorDao {
	private static BrgMonitorDao brgDao;
	
	public static BrgMonitorDao getIntance(){
		if(brgDao==null){
			brgDao=new BrgMonitorDao();
		}
		return brgDao;
	}
	
	public List<String> initModeType(String id){
		List<String> list=new ArrayList<>();
		String sql="SELECT DISTINCT mode FROM brg_system where bridge_id=? ORDER BY mode desc";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs =dataOperation.executeQuery(sql,new Object[]{id});
		try {
			while(rs.next()){
				String mode=rs.getString("mode");
				if("s".equals(mode)){
					mode="健康监测";
				}else if("w".equals(mode)){
					mode="动态称重";
				}else if("g".equals(mode)){
					mode="GPS";
				}else if("f".equals(mode)){
					mode="风速风向";
				}
				list.add(mode);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}
	
	public List<BrgHealthStatistic> selectMonitorByTypeAndBrg(String mType,String brgId,String startTime,String endTime,int page,int iDisplayLength){
		List<BrgHealthStatistic> list=new ArrayList<>();
		String sql="";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs =null;
		if(startTime!=null&&endTime!=null&&startTime!=""&&endTime!=""){
			if(mType.equals("动态称重")){
				sql="SELECT a.data_file,a.start_time,a.end_time,a.file_size,a.is_download,b.bridge_no FROM brg_weight_statistic as a LEFT JOIN brg_card_admin_id as b on a.bridge_id=b.bridge_id where a.bridge_id=? and a.start_time > ? and a.end_time < ? ORDER BY a.start_time desc limit ?,?";
			}else if(mType.equals("健康监测")){
				sql="SELECT a.data_file,a.start_time,a.end_time,a.file_size,a.is_download,b.bridge_no FROM brg_health_statistic as a LEFT JOIN brg_card_admin_id as b on a.bridge_id=b.bridge_id where a.bridge_id=? and a.start_time > ? and a.end_time < ? ORDER BY a.start_time desc limit ?,?";
			}else if(mType.equals("风速风向")){
				sql="SELECT a.data_file,a.start_time,a.end_time,a.file_size,a.is_download,b.bridge_no FROM brg_wind_statistic as a LEFT JOIN brg_card_admin_id as b on a.bridge_id=b.bridge_id where a.bridge_id=? and a.start_time > ? and a.end_time < ? ORDER BY a.start_time desc limit ?,?";
			}else{
				sql="SELECT a.data_file,a.start_time,a.end_time,a.file_size,a.is_download,b.bridge_no FROM brg_gps_statistic as a LEFT JOIN brg_card_admin_id as b on a.bridge_id=b.bridge_id  where a.bridge_id=? and a.start_time > ? and a.end_time < ? ORDER BY a.start_time desc limit ?,?";
			}
			rs =dataOperation.executeQuery(sql,new Object[]{brgId,startTime,endTime,page,iDisplayLength});
		}else{
			if(mType.equals("动态称重")){
				sql="SELECT a.data_file,a.start_time,a.end_time,a.file_size,a.is_download,b.bridge_no FROM brg_weight_statistic as a LEFT JOIN brg_card_admin_id as b on a.bridge_id=b.bridge_id where a.bridge_id=? ORDER BY a.start_time desc limit ?,?";
			}else if(mType.equals("健康监测")){
				sql="SELECT a.data_file,a.start_time,a.end_time,a.file_size,a.is_download,b.bridge_no FROM brg_health_statistic as a LEFT JOIN brg_card_admin_id as b on a.bridge_id=b.bridge_id where  a.bridge_id=? ORDER BY a.start_time desc limit ?,?";
			}else if(mType.equals("风速风向")){
				sql="SELECT a.data_file,a.start_time,a.end_time,a.file_size,a.is_download,b.bridge_no FROM brg_wind_statistic as a LEFT JOIN brg_card_admin_id as b on a.bridge_id=b.bridge_id where a.bridge_id=? ORDER BY a.start_time desc limit ?,?";
			}else{
				sql="SELECT a.data_file,a.start_time,a.end_time,a.file_size,a.is_download,b.bridge_no FROM brg_gps_statistic as a LEFT JOIN brg_card_admin_id as b on a.bridge_id=b.bridge_id where a.bridge_id=? ORDER BY a.start_time desc limit ?,?";
			}
			rs =dataOperation.executeQuery(sql,new Object[]{brgId,page,iDisplayLength});
		}
		try {
			while(rs.next()){
				BrgHealthStatistic  health=new BrgHealthStatistic();
				health.setBridge_no(rs.getString("bridge_no"));
				health.setStart_time(rs.getString("start_time"));
				health.setEnd_time(rs.getString("end_time"));
				health.setFile_size(rs.getInt("file_size"));
				health.setData_file(rs.getString("data_file"));
				health.setIs_download(rs.getInt("is_download"));
				list.add(health);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}
	
	public int fileCount(String mType,String brgId,String startTime,String endTime){
		int i=0;
		String sql="";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs =null;
		if(startTime!=null&&endTime!=null&&startTime!=""&&endTime!=""){
			if(mType.equals("动态称重")){
				sql="SELECT count(r_id) FROM brg_weight_statistic where bridge_id=? and start_time > ? and end_time < ?";
			}else if(mType.equals("健康监测")){
				sql="SELECT count(r_id) FROM brg_health_statistic where bridge_id=? and start_time > ? and end_time < ?";
			}else if(mType.equals("风速风向")){
				sql="SELECT count(r_id) FROM brg_wind_statistic where bridge_id=? and start_time > ? and end_time < ?";
			}else{
				sql="SELECT count(r_id) FROM brg_gps_statistic where bridge_id=? and start_time > ? and end_time < ?";
			}
			rs =dataOperation.executeQuery(sql,new String[]{brgId,startTime,endTime});
		}else{
			if(mType.equals("动态称重")){
				sql="SELECT count(r_id) FROM brg_weight_statistic where bridge_id=?";
			}else if(mType.equals("健康监测")){
				sql="SELECT count(r_id) FROM brg_health_statistic where bridge_id=?";
			}else if(mType.equals("风速风向")){
				sql="SELECT count(r_id) FROM brg_wind_statistic where bridge_id=?";
			}else{
				sql="SELECT count(r_id) FROM brg_gps_statistic where bridge_id=?";
			}
			rs =dataOperation.executeQuery(sql,new String[]{brgId});
		}
		try {
			while(rs.next()){
				i=rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return i;
	}
	
	public List<String>getItem_2(String item_first,String brg_id){
		List<String>list=new ArrayList<>();
		String sql="select item_second from dic_brg_monitoring_item where item_first=? and id in(SELECT item_id from brg_monitor where brg_id=?)";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs =dataOperation.executeQuery(sql,new String[]{item_first,brg_id});
		try {
			while(rs.next()){
				list.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}
	
	public List<String>getItem_second(String brg_id){
		List<String>list=new ArrayList<>();
		String sql="select item_second from dic_brg_monitoring_item where id in(SELECT item_id from brg_monitor where brg_id=?)";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs =dataOperation.executeQuery(sql,new String[]{brg_id});
		try {
			while(rs.next()){
				list.add(rs.getString("item_second"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}
	public List<DicBrgMonitoringItem>getItem_secondMode(String brg_id){
		List<DicBrgMonitoringItem>list=new ArrayList<>();
		String sql="select id,item_second,tableName from dic_brg_monitoring_item where id in(SELECT item_id from brg_monitor where brg_id=?) and id!='1'";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs =dataOperation.executeQuery(sql,new String[]{brg_id});
		try {
			while(rs.next()){
				DicBrgMonitoringItem dbMonitoringItem = new DicBrgMonitoringItem();
				dbMonitoringItem.setId(rs.getInt("id")+"");
				dbMonitoringItem.setItem_second(rs.getString("item_second"));
				dbMonitoringItem.setTableName(rs.getString("tableName"));
				list.add(dbMonitoringItem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}
	public List<SqlColumn>getTableColumn(String tableName){
 		List<SqlColumn >list=new ArrayList<>();
		String sql="select COLUMN_NAME from information_schema.COLUMNS where table_name = ? and table_schema = 'htbmssecond' ";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs =dataOperation.executeQuery(sql,new String[]{tableName});
		try {
			while(rs.next()){
				SqlColumn sqlColumn = new SqlColumn();
				String column = rs.getString("COLUMN_NAME");
				if("max,avg,tantile_95,variance,tantile_5".contains(column)){
					sqlColumn.setColumn(column);
					sqlColumn.setColumnName(getColumnNameByColumn(column));
					list.add(sqlColumn);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}
	public static BrgMonitor getMonitorIdByBrgIdAndItmId(String brg_id,String item_id){
		BrgMonitor brgMonitor = new BrgMonitor();
		String sql="select monitor_id,ChanelNum from brg_monitor where brg_id= ? and item_id= ? ";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs =dataOperation.executeQuery(sql,new String[]{brg_id,item_id});
		try {
			if(rs.next()){
				brgMonitor.setMonitor_id(rs.getString("monitor_id"));
				brgMonitor.setChanelNum(rs.getString("ChanelNum"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return brgMonitor;
	}
    public static String getColumnNameByColumn(String column){
    	if("avg".equals(column)){
    	return "平均值";	
    	}else if("max".equals(column)){
    	return "最大值";
    	}else if("tantile_95".equals(column)){
    		return "0.95分位点值";
    	}else if("variance".equals(column)){
    		return "方差值";
    	}else if("tantile_5".equals(column)){
    		return "0.05分位点值";
    	}else{
    		return "";
    	}
    } 
	public Map<String,List>getRowList(String tableName,String rowName,String brg_id,String mode){
		Map<String,List> map=new HashMap<>();
		List<String>row=new ArrayList<>();
		List<String>line=new ArrayList<>();
		String sql="SELECT DISTINCT  "+rowName+" ,brg_startime FROM "+tableName+" where brg_id=? and brg_mode=? ORDER BY brg_startime";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs =dataOperation.executeQuery(sql,new String[]{brg_id,mode});
		try {
			while(rs.next()){
				row.add(rs.getString(1));
				line.add(rs.getString("brg_startime"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		map.put("y", row);
		map.put("x", line);
		dataOperation.close();
		return map;
	}

	
	public Map<String,List>getRowListJiGuangRaoDu(String tableName,String startTime,String endTime){
		Map<String,List> map=new HashMap<>();
		List<String>row=new ArrayList<>();
		List<String>line=new ArrayList<>();
		String sql="SELECT datatime,substring_index(data, 'm', 1) as data,substring_index(datatime, '-', 3) time FROM "+tableName+" where  substring_index(datatime, '-', 3) >= ? ";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance("/myDbConfig.properties").getConnection());
		ResultSet rs =dataOperation.executeQuery(sql,new String[]{startTime});
		try {
			while(rs.next()){
				row.add(rs.getString("data"));
				line.add(rs.getString("datatime"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		map.put("y", row);
		map.put("x", line);
		dataOperation.close();
		return map;
	}
	
	
	/*public List<String>getlineList(String tableName,String brg_id,String mode){
		List<String>list=new ArrayList<>();
		String data=null;
		String sql="SELECT DISTINCT  brg_startime FROM "+tableName+" where brg_id=? and brg_mode=?";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs =dataOperation.executeQuery(sql,new String[]{brg_id,mode});
		try {
			while(rs.next()){
				data=rs.getString(1);
				list.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}*/
	public List<String>getlineList1(String tableName,String brg_id,String mode,String sort){
		List<String>list=new ArrayList<>();
		String data=null;
		String sql="SELECT  time FROM "+tableName+" where monitor_id=? and mode=? and sort =? ORDER BY time";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs =dataOperation.executeQuery(sql,new String[]{brg_id,mode,sort});
		try {
			while(rs.next()){
				data=rs.getString(1);
				list.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			dataOperation.close();
		}
		return list;
	}
	public List<String>getRowListBySort(String tableName,String rowName,String monitor,String mode,String sort){
		List<String>list=new ArrayList<>();
		String data=null;
		String sql="SELECT "+rowName+" FROM "+tableName+" where monitor_id=?  and sort=? ORDER BY time";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs =dataOperation.executeQuery(sql,new String[]{monitor,sort});
		try {
			while(rs.next()){
				data=rs.getString(1);
				list.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			dataOperation.close();
		}
		return list;
	}
	public String getMonitor_id(String item_first,String item_second,String brg_id){
		
		String monitor_id=null;
		String sql="SELECT monitor_id FROM brg_monitor where brg_id=? and item_id=(select id from dic_brg_monitoring_item where item_first=? and item_second=?)";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs =dataOperation.executeQuery(sql,new String[]{brg_id,item_first,item_second});
		try {
			while(rs.next()){
				monitor_id=rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return monitor_id;
	}
	
	public List<BrgMonitor> selectBrgMonitor(String item_first,String item_second,String brg_id){
		List<BrgMonitor>list=new ArrayList<>();
		String sql="SELECT * FROM brg_monitor where brg_id=? and item_id=(select id from dic_brg_monitoring_item where item_first=? and item_second=?)";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs =dataOperation.executeQuery(sql,new String[]{brg_id,item_first,item_second});
		try {
			while(rs.next()){
				BrgMonitor bm=new BrgMonitor();
				bm.setMonitor_id(rs.getString("monitor_id"));
				bm.setBrg_id(rs.getString("brg_id"));
				bm.setChanelNum(rs.getString("chanelNum"));
				bm.setItem_id(rs.getInt("item_id"));
				bm.setPointsNo(rs.getString("pointsNo"));
				bm.setProjectImage(rs.getString("projectImage"));
				list.add(bm);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}
	
	public List<BrgMonitorCableforce>selectCableforce(String monitor_id,String mode,String time){
		List<BrgMonitorCableforce>list=new ArrayList<>();
		String sql="select * from brg_monitor_cableforce where monitor_id=? and mode=?  and time like ? order by time desc";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs =dataOperation.executeQuery(sql,new String[]{monitor_id,mode,"%"+time+"%"});
		try {
			while(rs.next()){
				BrgMonitorCableforce cableforce=new BrgMonitorCableforce();
				cableforce.setMode(rs.getString("mode"));
				cableforce.setTime(rs.getString("time"));
				cableforce.setAvg(rs.getString("avg"));
				cableforce.setType(rs.getString("type"));
				cableforce.setSort(rs.getInt("sort"));
				list.add(cableforce);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}
	
	public List<BrgMonitorStaticdisp>selectStaticdisp(String monitor_id,String mode,String time){
		List<BrgMonitorStaticdisp>list=new ArrayList<>();
		String sql="select * from brg_monitor_staticdisp where monitor_id=? and mode=?  and time like ? order by time desc";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs =dataOperation.executeQuery(sql,new String[]{monitor_id,mode,"%"+time+"%"});
		try {
			while(rs.next()){
				BrgMonitorStaticdisp staticdisp=new BrgMonitorStaticdisp();
				staticdisp.setMode(rs.getString("mode"));
				staticdisp.setTime(rs.getString("time"));
				staticdisp.setMax(rs.getString("max"));
				staticdisp.setMin(rs.getString("min"));
				staticdisp.setType(rs.getString("type"));
				staticdisp.setSort(rs.getInt("sort"));
				list.add(staticdisp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}
	public List<BrgMonitorCableforce>selectCableforce(String monitor_id,String mode,String startTime,String endTime){
		List<BrgMonitorCableforce>list=new ArrayList<>();
		String sql="select * from brg_monitor_cableforce where monitor_id=? and mode=?  and time > ? and time <? order by time desc";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs =dataOperation.executeQuery(sql,new String[]{monitor_id,mode,startTime,endTime});
		try {
			while(rs.next()){
				BrgMonitorCableforce cableforce=new BrgMonitorCableforce();
				cableforce.setMode(rs.getString("mode"));
				cableforce.setTime(rs.getString("time"));
				cableforce.setAvg(rs.getString("avg"));
				cableforce.setType(rs.getString("type"));
				cableforce.setSort(rs.getInt("sort"));
				list.add(cableforce);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}
	
	public List<BrgMonitorStaticdisp>selectStaticdisp(String monitor_id,String mode,String startTime,String endTime){
		List<BrgMonitorStaticdisp>list=new ArrayList<>();
		String sql="select * from brg_monitor_staticdisp where monitor_id=? and mode=?  and time > ? and time <? order by time desc";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs =dataOperation.executeQuery(sql,new String[]{monitor_id,mode,startTime,endTime});
		try {
			while(rs.next()){
				BrgMonitorStaticdisp staticdisp=new BrgMonitorStaticdisp();
				staticdisp.setMode(rs.getString("mode"));
				staticdisp.setTime(rs.getString("time"));
				staticdisp.setMax(rs.getString("max"));
				staticdisp.setMin(rs.getString("min"));
				staticdisp.setType(rs.getString("type"));
				staticdisp.setSort(rs.getInt("sort"));
				list.add(staticdisp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}
	
	public String getTimeMonitor(String tableName,String brg_id,String brgMode){
		String brgNo = BrgCardDao.getInstance().getBrgCardAdminIdData(brg_id).get(0).getBridge_no();
		List<String> strings =new ArrayList<>();
		String sql=" select time from "+tableName+" where monitor_id = ? and mode=? order by time desc LIMIT 1";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs =dataOperation.executeQuery(sql,new String[]{brgNo,brgMode});
		try {
			while(rs.next()){
				String string = rs.getString("time");
				strings.add(string);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		if(strings.size()>0){
			return strings.get(0);
		}else{
			return "";
		}
		
	}
	public List<BrgMonitorDynadisp>selectDynadisp(String monitor_id,String mode,String time){
		List<BrgMonitorDynadisp> list=new ArrayList<>();
		String sql="select * from brg_monitor_dynadisp where monitor_id=? and mode=?  and time like ? order by time desc";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs =dataOperation.executeQuery(sql,new String[]{monitor_id,mode,"%"+time+"%"});
		try {
			while(rs.next()){
				BrgMonitorDynadisp dynadisp=new BrgMonitorDynadisp();
				dynadisp.setMode(rs.getString("mode"));
				dynadisp.setTime(rs.getString("time"));
				dynadisp.setAvg(rs.getString("avg"));
				dynadisp.setMax(rs.getString("max"));
				dynadisp.setTantile_95(rs.getString("tantile_95"));
				dynadisp.setType(rs.getString("type"));
				dynadisp.setVariance(rs.getString("variance"));
				dynadisp.setSort(rs.getInt("sort"));
				list.add(dynadisp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}
	
	public List<BrgMonitorDynadisp>selectDynadisp(String monitor_id,String mode,String starttime,String endTime){
		List<BrgMonitorDynadisp> list=new ArrayList<>();
		String sql="select * from brg_monitor_dynadisp where monitor_id=? and mode=?  and time > ? and time < ? order by time desc";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs =dataOperation.executeQuery(sql,new String[]{monitor_id,mode,starttime,endTime});
		try {
			while(rs.next()){
				BrgMonitorDynadisp dynadisp=new BrgMonitorDynadisp();
				dynadisp.setMode(rs.getString("mode"));
				dynadisp.setTime(rs.getString("time"));
				dynadisp.setAvg(rs.getString("avg"));
				dynadisp.setMax(rs.getString("max"));
				dynadisp.setTantile_95(rs.getString("tantile_95"));
				dynadisp.setType(rs.getString("type"));
				dynadisp.setVariance(rs.getString("variance"));
				dynadisp.setSort(rs.getInt("sort"));
				list.add(dynadisp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}
	
	public List<BrgMonitorStrainc>selectStrainc(String monitor_id,String mode,String time){
		List<BrgMonitorStrainc>list=new ArrayList<>();
	
		String sql="select * from brg_monitor_strainc where monitor_id=? and mode=? and time like ? order by time desc";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		
		ResultSet rs =dataOperation.executeQuery(sql,new String[]{monitor_id,mode,"%"+time+"%"});
		try {
			while(rs.next()){
				BrgMonitorStrainc strainc=new BrgMonitorStrainc();
				strainc.setMode(rs.getString("mode"));
				strainc.setTime(rs.getString("time"));
				strainc.setMax(rs.getString("max"));
				strainc.setMin(rs.getString("min"));
				strainc.setTantile_5(rs.getString("tantile_5"));
				strainc.setTantile_95(rs.getString("tantile_95"));
				strainc.setType(rs.getString("type"));
				strainc.setAvg(rs.getString("avg"));
				strainc.setVariance(rs.getString("variance"));
				strainc.setSort(rs.getInt("sort"));
				list.add(strainc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}
	
	public List<BrgMonitorStrainc>selectStrainc(String monitor_id,String mode,String starttime,String endTime){
		List<BrgMonitorStrainc>list=new ArrayList<>();
	
		String sql="select * from brg_monitor_strainc where monitor_id=? and mode=? and time >? and time < ?  order by time desc";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		
		ResultSet rs =dataOperation.executeQuery(sql,new String[]{monitor_id,mode,starttime,endTime});
		try {
			while(rs.next()){
				BrgMonitorStrainc strainc=new BrgMonitorStrainc();
				strainc.setMode(rs.getString("mode"));
				strainc.setTime(rs.getString("time"));
				strainc.setMax(rs.getString("max"));
				strainc.setMin(rs.getString("min"));
				strainc.setTantile_5(rs.getString("tantile_5"));
				strainc.setTantile_95(rs.getString("tantile_95"));
				strainc.setType(rs.getString("type"));
				strainc.setAvg(rs.getString("avg"));
				strainc.setVariance(rs.getString("variance"));
				strainc.setSort(rs.getInt("sort"));
				list.add(strainc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}
	
	public List<BrgMonitorStrains>selectStrains(String monitor_id,String mode,String time){
		List<BrgMonitorStrains>list=new ArrayList<>();
		String sql="select * from brg_monitor_strains where monitor_id=? and mode=? and time like ? order by time desc";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs =dataOperation.executeQuery(sql,new String[]{monitor_id,mode,"%"+time+"%"});
		try {
			while(rs.next()){
				BrgMonitorStrains strains=new BrgMonitorStrains();
				strains.setMode(rs.getString("mode"));
				strains.setTime(rs.getString("time"));
				strains.setMax(rs.getString("max"));
				strains.setMin(rs.getString("min"));
				strains.setTantile_5(rs.getString("tantile_5"));
				strains.setTantile_95(rs.getString("tantile_95"));
				strains.setType(rs.getString("type"));
				strains.setAvg(rs.getString("avg"));
				strains.setVariance(rs.getString("variance"));
				strains.setSort(rs.getInt("sort"));
				list.add(strains);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}
	
	public List<BrgMonitorStrains>selectStrains(String monitor_id,String mode,String starttime,String endTime){
		List<BrgMonitorStrains>list=new ArrayList<>();
		String sql="select * from brg_monitor_strains where monitor_id=? and mode=? and time > ? and time<? order by time desc";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs =dataOperation.executeQuery(sql,new String[]{monitor_id,mode,starttime,endTime});
		try {
			while(rs.next()){
				BrgMonitorStrains strains=new BrgMonitorStrains();
				strains.setMode(rs.getString("mode"));
				strains.setTime(rs.getString("time"));
				strains.setMax(rs.getString("max"));
				strains.setMin(rs.getString("min"));
				strains.setTantile_5(rs.getString("tantile_5"));
				strains.setTantile_95(rs.getString("tantile_95"));
				strains.setType(rs.getString("type"));
				strains.setAvg(rs.getString("avg"));
				strains.setVariance(rs.getString("variance"));
				strains.setSort(rs.getInt("sort"));
				list.add(strains);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}
	
	public List<BrgMonitorTemp>selectTemp(String monitor_id,String mode,String time){
		List<BrgMonitorTemp>list=new ArrayList<>();
		String sql="select * from brg_monitor_temp where monitor_id=? and mode=? and time like ? order by time desc";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs =dataOperation.executeQuery(sql,new String[]{monitor_id,mode,"%"+time+"%"});
		try {
			while(rs.next()){
				BrgMonitorTemp temp=new BrgMonitorTemp();
				temp.setMode(rs.getString("mode"));
				temp.setTime(rs.getString("time"));
				temp.setAvg(rs.getString("avg"));
				temp.setType(rs.getString("type"));
				temp.setSort(rs.getInt("sort"));
				list.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}
	
	public List<BrgMonitorTemp>selectTemp(String monitor_id,String mode,String starttime,String endTime){
		List<BrgMonitorTemp>list=new ArrayList<>();
		String sql="select * from brg_monitor_temp where monitor_id=? and mode=? and time > ? and time < ? order by time desc";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs =dataOperation.executeQuery(sql,new String[]{monitor_id,mode,starttime,endTime});
		try {
			while(rs.next()){
				BrgMonitorTemp temp=new BrgMonitorTemp();
				temp.setMode(rs.getString("mode"));
				temp.setTime(rs.getString("time"));
				temp.setAvg(rs.getString("avg"));
				temp.setType(rs.getString("type"));
				temp.setSort(rs.getInt("sort"));
				list.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}
	
	public BrgMonitor getMonitor(String monitor_id){
		List<BrgMonitor> brgMonitors = new ArrayList<>();
		String sql="SELECT * FROM brg_monitor where monitor_id=?";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs =dataOperation.executeQuery(sql,new String[]{monitor_id});
		try {
			while(rs.next()){
			BrgMonitor brgMonitor = new BrgMonitor();
			brgMonitor.setBrg_id(rs.getString("brg_id"));
			brgMonitor.setChanelNum(rs.getString("ChanelNum"));
			brgMonitor.setItem_id(rs.getInt("item_id"));
			brgMonitor.setMonitor_id(rs.getString("monitor_id"));
			brgMonitor.setPointsNo(rs.getString("pointsNo"));
			brgMonitor.setProjectImage(rs.getString("projectImage"));
			brgMonitors.add(brgMonitor);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		if(brgMonitors.size()==1){
			return brgMonitors.get(0);
		}else{
			return null;
		}
	}
	
	public List<StaffNumber> selectByNo(String brg_no,String monitorType,String item_second){
		List<StaffNumber>list=new ArrayList<>();
		String sql="SELECT * FROM staff_number where brg_no=? and type=? and item_second=?";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs =dataOperation.executeQuery(sql,new String[]{brg_no,monitorType,item_second});
		try {
			while(rs.next()){
				StaffNumber sn=new StaffNumber();
				sn.setBrg_no(rs.getString("brg_no"));
				sn.setType(rs.getString("type"));
				sn.setPhone(rs.getString("phone"));
				sn.setName(rs.getString("name"));
				sn.setItem_second(rs.getString("item_second"));
				list.add(sn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}
	
	public StaffNumber selectPhoneNoData(String brg_no,String monitorType){
		List<StaffNumber> list=new ArrayList<>();
		String sql="SELECT * FROM staff_number where brg_no=? and type=? ";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs =dataOperation.executeQuery(sql,new String[]{brg_no,monitorType});
		try {
			while(rs.next()){
				StaffNumber sn=new StaffNumber();
				sn.setBrg_no(rs.getString("brg_no"));
				sn.setType(rs.getString("type"));
				sn.setPhone(rs.getString("phone"));
				sn.setName(rs.getString("name"));
				sn.setItem_second(rs.getString("item_second"));
				list.add(sn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public StaffNumber selectByItem(String brg_no,String monitorType,String item_second){
		StaffNumber phone=new StaffNumber();
		String sql="SELECT phone,name FROM staff_number where brg_no=? and type=? and item_second=?";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs =dataOperation.executeQuery(sql,new String[]{brg_no,monitorType,item_second});
		try {
			while(rs.next()){
				phone.setPhone(rs.getString("phone"));
				phone.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return phone;
	}
	
	public int insertStaffNumber(StaffNumber sn){
		int i=0;
		String sql="insert into staff_number(brg_no,type,phone,name,item_second) values(?,?,?,?,?)";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		i=dataOperation.executeUpdate(sql, new Object[]{sn.getBrg_no(),sn.getType(),sn.getPhone(),sn.getName(),sn.getItem_second()});
		dataOperation.close();
		return i;
	}
	
	public int updateStaffNumber(StaffNumber sn){
		int i=0;
		String sql="update staff_number set phone=?,name=? where brg_no=? and type=? and item_second=?";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		i=dataOperation.executeUpdate(sql, new Object[]{sn.getPhone(),sn.getName(),sn.getBrg_no(),sn.getType(),sn.getItem_second()});
		dataOperation.close();
		return i;
	}
	
	public List<BrgWeightLoadRatio>getInitBrgIdAndTime(){
		List<BrgWeightLoadRatio>list=new ArrayList<>();
		String sql="SELECT brg_id,brg_startime FROM brg_weight_load_radio ORDER BY brg_startime DESC LIMIT 1";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs =dataOperation.executeQuery(sql,new String[]{});
		try {
			while(rs.next()){
				BrgWeightLoadRatio bwlr=new BrgWeightLoadRatio();
				bwlr.setBridge_id(rs.getString("brg_id"));
				bwlr.setStart_time(rs.getString("brg_startime"));
				list.add(bwlr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}
	
	public String getBrgTime(String brgNo,String brgMode){
		String time=null;
		String	sql="SELECT brg_startime FROM brg_weight_load_radio where brg_id=? and brg_mode=? ORDER BY brg_startime desc LIMIT 1";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs =dataOperation.executeQuery(sql,new String[]{brgNo,brgMode});
		try {
			while(rs.next()){
				time=rs.getString("brg_startime");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return time;
	}
	
	public List<String> getItem_first(String brgId){
		List<String>list=new ArrayList<>();
		String sql="SELECT item_first FROM dic_brg_monitoring_item where id in(select item_id from brg_monitor where brg_id=?) GROUP BY item_first";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs =dataOperation.executeQuery(sql,new String[]{brgId});
		try {
			while(rs.next()){
				list.add(rs.getString("item_first"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}
	public List<String> getDayHours(String item_second,String brgNo,String dayTime){
		List<String>list=new ArrayList<>();
		String sql="";
		String mode="hour";
		if("车辆荷载".equals(item_second)){
			sql="SELECT DISTINCT brg_startime FROM brg_weight_load_radio where brg_id=? and brg_startime like ? and brg_mode =?";
		}else if("温度".equals(item_second)){
			sql="SELECT DISTINCT time FROM brg_monitor_temp where monitor_id=? and time like ? and mode=?";
		}else if("动位移".equals(item_second)){
			sql="SELECT DISTINCT time FROM brg_monitor_dynadisp where monitor_id=? and time like ? and mode=?";
		}else if("混凝土应变".equals(item_second)){
			sql="SELECT DISTINCT time FROM brg_monitor_strainc where monitor_id=? and time like ? and mode=?";
		}else if("钢应变".equals(item_second)){
			sql="SELECT DISTINCT time FROM brg_monitor_strains where monitor_id=? and time like ? and mode=?";
		}else if("索力".equals(item_second)){
			sql="SELECT DISTINCT time FROM brg_monitor_cableforce where monitor_id=? and time like ? and mode=?";
		}else if("静位移".equals(item_second)){
			sql="SELECT DISTINCT time FROM brg_monitor_staticdisp where monitor_id=? and time like ? and mode=?";
		}
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs =dataOperation.executeQuery(sql,new String[]{brgNo,dayTime,mode});
		try {
			while(rs.next()){
				list.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}

	
	public Map<String,Object> getBrgMsgById(String brg_id){
		Map map = new HashMap<String, Object>();
		String sql="";
		sql = " SELECT "
				+ "bcai.bridge_name brgName, "
				+ "bcst.bridge_len brgLen, "
			+ "bcai.span_build brgSpan, "
			+ "bcai.longitude brgLon, "
			+ "bcai.latitude brgLat,  "
			+ "aa.rate dataDayIntRate, "
			+ "bb.rate dataMonthIntRate, "
			+ "cc.rate dataYearIntRate, "
			+ "dd.totalSize totalSizeDay, "
			+ " ee.totalSize totalSizeYear "
		+ "FROM "
			+ "brg_card_admin_id bcai "
		+ "LEFT JOIN brg_card_struct_tech bcst ON bcai.bridge_id = bcst.bridge_id "
		+ "LEFT JOIN ( "
			+ "SELECT  "
			+ "	* "
			+ "FROM  "
				+ "(  "
					+ "SELECT  "
					+ "* "
						+ "FROM "
						+ "brg_health_dayrate  "
						+ "ORDER BY "
						+ "rq DESC  "
				+ ") a "
			+ "GROUP BY  "
				+ "brg_id  "
		+ ") aa ON aa.brg_id = bcai.bridge_id  "
		+ "LEFT JOIN (  "
			+ "SELECT  "
			+ "	*  "
			+ "FROM  "
				+ "(  "
				+ "	SELECT "
					+ "	*  "
						+ "FROM "
						+ "brg_health_monthrate  "
					+ "ORDER BY  "
					+ "	rq DESC  "
				+ ") b  "
			+ "GROUP BY  "
			+ "	brg_id  "
		+ ") bb ON bb.brg_id = bcai.bridge_id  "
		+ "LEFT JOIN (  "
			+ "SELECT  "
			+ "	*  "
			+ "FROM  "
			+ "	(  "
						+ "	SELECT  "
				+ "		*  "
					+ "FROM  "
					+ "	brg_health_currate  "
				+ "	ORDER BY  "
					+ "	rq DESC  "
				+ ") c  "
			+ "GROUP BY  "
				+ "brg_id  "
		+ ") cc ON cc.brg_id = bcai.bridge_id  "
		+" LEFT JOIN (\r\n" + 
		" SELECT\r\n" + 
		"		SUM(file_size) totalSize\r\n" + 
		"	FROM\r\n" + 
		"		brg_health_statistic\r\n" + 
		"	WHERE\r\n" + 
		"		bridge_id = ? and file_time LIKE \r\n" + 
		" CONCAT(SUBSTR((SELECT file_time FROM brg_health_statistic ORDER BY file_time desc LIMIT 1),1,10),\"%\")\r\n" + 
		" ) dd ON 1=1"+
		" LEFT JOIN (\r\n" + 
		" SELECT\r\n" + 
		"		SUM(file_size) totalSize\r\n" + 
		"	FROM\r\n" + 
		"		brg_health_statistic\r\n" + 
		"	WHERE\r\n" + 
		"		bridge_id = ? and file_time LIKE \r\n" + 
		" CONCAT(SUBSTR((SELECT file_time FROM brg_health_statistic ORDER BY file_time desc LIMIT 1),1,4),\"%\")\r\n" + 
		" ) ee ON 1=1 "		
		+ " where bcai.bridge_id = ? ";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs =dataOperation.executeQuery(sql,new String[]{brg_id,brg_id,brg_id});
		ResultSetMetaData rsmd = null;
		try {
			rsmd = rs.getMetaData();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			if(rs.next()){
				for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
					String columnName = rsmd.getColumnName(i).toLowerCase();
					map.put(columnName, rs.getString(i));
				}
				map.put("dataDayIntRate", rs.getString("dataDayIntRate"));
				map.put("dataMonthIntRate", rs.getString("dataMonthIntRate"));
				map.put("dataYearIntRate", rs.getString("dataYearIntRate"));
				map.put("totalSizeDay", rs.getString("totalSizeDay"));
				map.put("totalSizeYear", rs.getString("totalSizeYear"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return map;
	}
	
	public List<Map> getBrgMonitorMsgById(String brg_id){
		
		String sql="";
		sql = " SELECT\r\n" + 
				"	item_id,ChanelNum,pointsNo,bcai.bridge_no\r\n" + 
				" FROM\r\n" + 
				"	brg_monitor bm LEFT JOIN brg_card_admin_id bcai on  bcai.bridge_id = bm.brg_id \r\n" + 
				" WHERE\r\n" + 
				"	brg_id = ? ";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		MyDataOperation dataOperation1 = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs =dataOperation.executeQuery(sql,new String[]{brg_id});
		List<Map> mapMain = new ArrayList();
		try {
			while(rs.next()){
				Map map = new HashMap<String, Object>();
				String tableNameEnd = "";
				String item_id = rs.getString("item_id");
				String bridge_no = rs.getString("bridge_no");
				String brgMonType = "";
				switch (item_id) {
				case "8":
					tableNameEnd = "strainc";
					brgMonType ="混凝土应变";
					break;
				case "9":
					tableNameEnd = "strains";
					brgMonType ="钢应变";
					break;
				case "12":
					tableNameEnd = "angle";
					brgMonType ="倾角";
					break;
				case "13":
					tableNameEnd = "crack";
					brgMonType ="裂缝";
					break;
				case "4":
					tableNameEnd = "dynadisp ";
					brgMonType ="动位移";
					break;
				case "11":
					tableNameEnd = "staticdisp";
					brgMonType ="静位移";
					break;
				case "3":
					tableNameEnd = "temp";
					brgMonType ="温度";
					break;
				case "10":
					tableNameEnd = "calberforce ";
					brgMonType ="索力";
					break;
				default:
					break;
				}
				map.put("brgMonType", brgMonType);
				tableNameEnd = "brg_monitor_"+tableNameEnd;
				String ChannelNum = rs.getString("ChanelNum");
				if(null!=ChannelNum&&!"".equals(ChannelNum.trim())&&ChannelNum.contains(",")) {
					String[] nums = ChannelNum.split(",");
					Integer length = nums.length;
					String sql1 = " SELECT\r\n" + 
							"	*\r\n" + 
							" FROM\r\n" + 
							"	(\r\n" + 
							"		SELECT\r\n" + 
							"			*\r\n" + 
							"		FROM\r\n" + 
							"			brg_monitor_dynadisp\r\n" + 
							"		WHERE\r\n" + 
							"			monitor_id = ? \r\n" + 
							"		ORDER BY\r\n" + 
							"			time DESC\r\n" + 
							"		LIMIT ? \r\n" + 
							"	)a ORDER BY a.sort";
					
					ResultSet rs1 =dataOperation1.executeQuery(sql1,new Object[]{bridge_no,length});
					ResultSetMetaData rsmd = null;
					try {
						rsmd = rs1.getMetaData();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					int k = 0;
					List<Map> mapList = new ArrayList<Map>();
					while(rs1.next()){
						
						Map map1 = new HashMap();
						Integer count = rsmd.getColumnCount();
						for (int i = 1; i < count + 1; i++) {
							String columnName = rsmd.getColumnName(i).toLowerCase();
							map1.put(columnName, rs1.getString(i));
						}
						map1.put("chnelNum",nums[k]);
						k++;
						mapList.add(map1);
					}
					map.put("dataMonNums", mapList);
					mapMain.add(map);
				}
			   
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		dataOperation1.close();
		return mapMain;
	}
	
	public static void main(String[] args) {
		BrgMonitorDao brgMonitorDao = new BrgMonitorDao();
		Map map = brgMonitorDao.getBrgMsgById("6f78200f27d54b5394ba8200162cb917");
		
		List list = brgMonitorDao.getBrgMonitorMsgById("6f78200f27d54b5394ba8200162cb917");
		
		JSONObject json = new JSONObject();
		String str = JSONObject.toJSONString(map);
		System.out.println(str);
	}
	
}
