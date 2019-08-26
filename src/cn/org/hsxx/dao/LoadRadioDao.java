
package cn.org.hsxx.dao;

import cn.org.hsxx.bean.LoadRadio;
import cn.org.hsxx.util.MyDataOperation;
import cn.org.hsxx.util.MyDataSource;

public class LoadRadioDao {
	private static LoadRadioDao lDao;
	
	public static LoadRadioDao getInstance(){
		if(lDao==null){
			lDao=new LoadRadioDao();
		}
		return lDao;
	}

	public int addLoadRadio(LoadRadio lr){
		int i=0;
		String sql="insert into brg_weight_load_radio(brg_id,brg_mode,brg_startime,load_radio1,load_radio1,load_radio1,load_radio2,load_radio3,load_radio4,load_radio5,load_radio6,load_radio7,load_radio8,load_radio9,load_radio10,load_radio11,load_radio12,load_radio13,load_radio14,load_radio15,load_radio16) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		i = dataOperation.executeUpdate(sql, new String[]{
				lr.getBrg_id(),
				lr.getBrg_mode(),
				lr.getBrg_startime(),
				lr.getLoad_radio1(),
				lr.getLoad_radio2(),
				lr.getLoad_radio3(),
				lr.getLoad_radio4(),
				lr.getLoad_radio5(),
				lr.getLoad_radio6(),
				lr.getLoad_radio7(),
				lr.getLoad_radio8(),
				lr.getLoad_radio9(),
				lr.getLoad_radio10(),
				lr.getLoad_radio11(),
				lr.getLoad_radio12(),
				lr.getLoad_radio13(),
				lr.getLoad_radio14(),
				lr.getLoad_radio15(),
				lr.getLoad_radio16()
		});
		dataOperation.close();
		return i;
	}
}
