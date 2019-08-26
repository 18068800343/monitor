
package cn.org.hsxx.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.org.hsxx.util.MyDataOperation;
import cn.org.hsxx.util.MyDataSource;


public class BrgMonitorDao {
	private static BrgMonitorDao brgDao;
	
	public static BrgMonitorDao getIntance(){
		if(brgDao==null){
			brgDao=new BrgMonitorDao();
		}
		return brgDao;
	}
	
	
	public String selectByItem(String brg_no,String monitorType,String item_second){
		String phone=null;
		String sql="SELECT phone FROM staff_number where brg_no=? and type=? and item_second=?";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs =dataOperation.executeQuery(sql,new String[]{brg_no,monitorType,item_second});
		try {
			while(rs.next()){
				phone=rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return phone;
	}
	
}
