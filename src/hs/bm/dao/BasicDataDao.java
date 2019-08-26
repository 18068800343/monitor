package hs.bm.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hs.bm.bean.BasicData;
import hs.bm.bean.TestPoint;
import hs.bm.vo.BrgSpanForCardVO;

public class BasicDataDao {
	private static BasicDataDao basicDataDao;

	public static BasicDataDao getInstance()
	{
		if (basicDataDao == null)
		{
			basicDataDao = new BasicDataDao();
		}
		return basicDataDao;
	}
	public List<BasicData> getBasicDataByType(String type){
		List<BasicData> list = new ArrayList<BasicData>();
		String sql = " select * from basic_data where type = ?";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs = dataOperation.executeQuery(sql, new String[]{ type });
		try
		{
			while (rs.next())
			{
				BasicData entity = new BasicData();
				entity.setId(rs.getString("id"));
				entity.setName(rs.getString("name"));
				entity.setType(rs.getString("type"));
				entity.setMode(rs.getString("mode"));
				list.add(entity);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}
	
	public List<TestPoint> getTdCodeByBrgId(String brgId){
		List<TestPoint> list = new ArrayList<TestPoint>();
		String sql = " select * from test_point where bridge_id = ?";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs = dataOperation.executeQuery(sql, new String[]{ brgId });
		try
		{
			while (rs.next())
			{
				TestPoint entity = new TestPoint();
				entity.setCdCode(rs.getString("cd_code"));
				entity.setTdCode(rs.getString("td_code"));
				entity.setCdTypeId(rs.getString("cd_type_id"));
				entity.setCdFTypeId(rs.getString("cd_f_type_id"));
				entity.setId(rs.getString("id"));
				list.add(entity);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}
	
	public String getCdFTypeIdByName(String cd_type){
		String sql = " select * from basic_data_jclx where name = ?";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs = dataOperation.executeQuery(sql, new String[]{ cd_type });
		String id = "";
		try
		{
			while (rs.next())
			{
			 id=rs.getString("id");
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		dataOperation.close();
		return id;
	}
}
