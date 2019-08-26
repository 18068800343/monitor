package cn.org.hsxx.dao;


import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import cn.org.hsxx.bean.BrgCardAdminId;
import cn.org.hsxx.util.MyDataOperation;
import cn.org.hsxx.util.MyDataSource;
import cn.org.hsxx.util.Nullchange;
import hs.bm.bean.DicBrgCardDomain;


public class BrgCardDao
{
	private static BrgCardDao brgCardDao;

	public static BrgCardDao getInstance()
	{
		if (brgCardDao == null)
		{
			brgCardDao = new BrgCardDao();
		}
		return brgCardDao;
	}

	public BrgCardAdminId getBrgCardAdminIdData(String bridge_no)
	{
		ArrayList<BrgCardAdminId> brgCardAdminId = new ArrayList<BrgCardAdminId>();
		String sql = " SELECT * FROM brg_card_admin_id WHERE bridge_no=? ";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs = dataOperation.executeQuery(sql, new String[]{ bridge_no });
		try
		{
			while (rs.next())
			{
				BrgCardAdminId entity = new BrgCardAdminId();
				entity.setBridge_id(Nullchange.NulltoString(rs.getString("bridge_id")));
				entity.setBridge_no(Nullchange.NulltoString(rs.getString("bridge_no")));
				entity.setBridge_name(Nullchange.NulltoString(rs.getString("bridge_name")));
				entity.setCharge_man(Nullchange.NulltoString(rs.getString("charge_man")));
				entity.setFill_date(Nullchange.NulltoString(rs.getString("fill_date")));
				entity.setFill_man(Nullchange.NulltoString(rs.getString("fill_man")));
				entity.setZone_id(Nullchange.NulltoString(rs.getString("zone_id")));
				brgCardAdminId.add(entity);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		dataOperation.close();
		if(brgCardAdminId.size()==1){
			return brgCardAdminId.get(0);
		}else{
			return null;
		}
		
	}
	
	public String getChanelNum(String bridge_id,String item)
	{
		List<String> chanelNums = new ArrayList<>();
 		ArrayList<BrgCardAdminId> brgCardAdminId = new ArrayList<BrgCardAdminId>();
		String sql = " SELECT * FROM brg_monitor WHERE brg_id=? and item_id =?";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs = dataOperation.executeQuery(sql, new String[]{ bridge_id,item});
		try
		{
			while (rs.next())
			{
				String chanelNum = rs.getString("ChanelNum");
				chanelNums.add(chanelNum);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		dataOperation.close();
		if(chanelNums.size()>0){
			return chanelNums.get(0);
		}else{
			return "";
		}
	}
	
	
	public ArrayList<BrgCardAdminId> getBrigedName()
	{
		ArrayList<BrgCardAdminId> list = new ArrayList<BrgCardAdminId>();
		String sql = "select bca.bridge_id,bca.bridge_name from brg_card_admin_id bca";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection(), false);
		String[] arr = new String[]
		{};
		ResultSet rs = dataOperation.executeQuery(sql, arr);
		try
		{
			while (rs.next())
			{
				BrgCardAdminId dbcd = new BrgCardAdminId();
				dbcd.setBridge_name(rs.getString("bridge_name"));
				dbcd.setBridge_id(rs.getString("bridge_name"));
				list.add(dbcd);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}
	
}
