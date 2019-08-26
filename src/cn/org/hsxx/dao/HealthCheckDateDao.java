
package cn.org.hsxx.dao;

import cn.org.hsxx.bean.LoadRadio;
import cn.org.hsxx.util.MyDataOperation;
import cn.org.hsxx.util.MyDataSource;

public class HealthCheckDateDao {
	private static HealthCheckDateDao lDao;
	
	public static HealthCheckDateDao getInstance(){
		if(lDao==null){
			lDao=new HealthCheckDateDao();
		}
		return lDao;
	}

	public int addStaticdisp(String[] arr){
		int i=0;
		String sql=" insert into brg_monitor_staticdisp values(?,?,?,?,?,?,?)";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		i = dataOperation.executeUpdate(sql,arr);
		dataOperation.close();
		return i;
	}
	public int addDynadisp(String[] arr){
		int i=0;
		String sql=" insert into brg_monitor_dynadisp values(?,?,?,?,?,?,?,?,?)";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		i = dataOperation.executeUpdate(sql,arr);
		dataOperation.close();
		return i;
	}
	public int addStrainc(String[] arr){
		int i=0;
		String sql=" insert into brg_monitor_strainc values(?,?,?,?,?,?,?,?,?,?,?)";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		i = dataOperation.executeUpdate(sql,arr);
		dataOperation.close();
		return i;
	}
	public int addStrains(String[] arr){
		int i=0;
		String sql=" insert into brg_monitor_strains values(?,?,?,?,?,?,?,?,?,?,?)";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		i = dataOperation.executeUpdate(sql,arr);
		dataOperation.close();
		return i;
	}
	public int addTemp(String[] arr){
		int i=0;
		String sql=" insert into brg_monitor_temp values(?,?,?,?,?,?)";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		i = dataOperation.executeUpdate(sql,arr);
		dataOperation.close();
		return i;
	}
	public int addCableforce(String[] arr){
		int i=0;
		String sql=" insert into brg_monitor_cableforce values(?,?,?,?,?,?)";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		i = dataOperation.executeUpdate(sql,arr);
		dataOperation.close();
		return i;
	}
}
