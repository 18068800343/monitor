
package cn.org.hsxx.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.org.hsxx.bean.BrgHealthStatistic;
import cn.org.hsxx.util.MyDataOperation;
import cn.org.hsxx.util.MyDataSource;

public class BrgHealthStatisticDao {

	private static BrgHealthStatisticDao brgdao;
	public static BrgHealthStatisticDao getInstance(){
		if(brgdao==null){
			brgdao=new BrgHealthStatisticDao();
		}
		return brgdao;
	}
	
	public int addBrgHealthStatistic(BrgHealthStatistic bhs){
		int i=0;
		String sql="insert into brg_weight_statistic(r_id,bridge_id,start_time,end_time,data_file,file_size,file_time) values(?,?,?,?,?,?,?)";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		i = dataOperation.executeUpdate(sql, new Object[]{
			bhs.getR_id(),
			bhs.getBridge_id(),
			bhs.getStart_time(),
			bhs.getEnd_time(),
			bhs.getData_file(),
			bhs.getFile_size(),
			bhs.getFile_time()
		});
		dataOperation.close();
		return i;
	}
	
	public String getBrgName(String brgId){
		String brgName=null;
		String sql="SELECT bridge_name FROM brg_card_admin_id where bridge_no=?";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs = dataOperation.executeQuery(sql, new Object[]{brgId});
		try {
			while(rs.next()){
				brgName=rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return brgName;
	}
	
	public int addBugMessage(String brgId,String megTime,String lane){
		int i=0;
		String type="数据异常";
		String item_second="1";
		String sql="insert into wait_bug_message(brg_id,time,lane,type,item_second) values(?,?,?,?,?)";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		i=dataOperation.executeUpdate(sql, new Object[]{brgId,megTime,lane,type,item_second});
		dataOperation.close();
		return i;
	}
	
	public int addBugMessageMonitor(String brgId,String megTime,String lane,String type,String item_second){
		int i=0;
		String sql="insert into wait_bug_message(brg_id,time,lane,type,item_second) values(?,?,?,?,?)";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		i=dataOperation.executeUpdate(sql, new Object[]{brgId,megTime,lane,type,item_second});
		dataOperation.close();
		return i;
	}
	
	public List<String> selectBugMessageLine(String brgId,String megTime){
		List<String> list=new ArrayList<>();

        String line="";
		String type="数据异常";
		String item_second="1";
		String sql="select lane from wait_bug_message where brg_id=? and time=? and item_second=? and type=?";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs = dataOperation.executeQuery(sql, new Object[]{brgId,megTime,item_second,type});
		try {
			while(rs.next()){
				line=rs.getString(1);
				list.add(line);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}
	
	public boolean selectIfSend(String brgId,String megTime,String lane ,String type,String item_second){
		boolean flag =false;
		String sql="select * from wait_bug_message where brg_id=? and time=?  and type = ? and item_second= ?";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs = dataOperation.executeQuery(sql, new Object[]{brgId,megTime,type,item_second});
		try {
			while(rs.next()){
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return flag;
	}
	
	public List<String> selectBrgLastTime(String brgId){
		List<String> list=new ArrayList<>();
		String time="";
		String type="数据异常";
		String item_second="1";
		String sql="SELECT time FROM wait_bug_message where brg_id=? and type =? and item_second =? ORDER BY time desc limit 1";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs = dataOperation.executeQuery(sql, new Object[]{brgId,type,item_second});
		try {
			while(rs.next()){
				time=rs.getString(1);
				list.add(time);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}
	
	public List<String> selectBrgLastTimeXN(String brgId,String type,String item_second){
		List<String> list=new ArrayList<>();
		String time="";
		String sql="SELECT time FROM wait_bug_message where brg_id=? and type =? and item_second =? ORDER BY time desc limit 1";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs = dataOperation.executeQuery(sql, new Object[]{brgId,type,item_second});
		try {
			while(rs.next()){
				time=rs.getString(1);
				list.add(time);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}
}
