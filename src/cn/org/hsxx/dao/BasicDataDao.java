package cn.org.hsxx.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.org.hsxx.bean.BasicData;
import cn.org.hsxx.bean.BasicDataJclx;
import cn.org.hsxx.bean.BasicDataStandard;
import cn.org.hsxx.util.Nullchange;
import hs.bm.dao.MyDataOperation;
import hs.bm.dao.MyDataSource;

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
	
	public List<BasicDataJclx> selectAllBasicjclx(){
		List<BasicDataJclx> list=new ArrayList<>();
		String sql = " SELECT bdj.id,bdj.name,bdj.basic_data_id,bd.`id` as bdid,bd.`name` as bdname FROM basic_data_jclx bdj LEFT JOIN basic_data bd ON bd.id = bdj.basic_data_id order by bdj.id asc";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs = dataOperation.executeQuery(sql, null);
		try {
			while (rs.next()){
				BasicDataJclx entity = new BasicDataJclx();
				BasicData bd = new BasicData();
				entity.setId(rs.getString("id"));
				entity.setName(rs.getString("name"));
				entity.setBasic_data_id(rs.getString("basic_data_id"));
				bd.setBasicid(rs.getString("bdid"));
				bd.setName(rs.getString("bdname"));
				entity.setBd(bd);
				entity.setBasic_data_name(rs.getString("bdname"));
				list.add(entity);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	} 
	
	public List<BasicDataJclx> selectAllBasicjclx(String bridge_id){
		List<BasicDataJclx> list=new ArrayList<>();
		String sql = " select DISTINCT bridge_id,cd_type_id,bdj.id,bdj.name from test_point tp LEFT JOIN basic_data_jclx bdj ON bdj.id=tp.cd_type_id where bridge_id = ? order by bdj.id asc";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs = dataOperation.executeQuery(sql, new String[]{bridge_id});
		try {
			while (rs.next()){
				BasicDataJclx entity = new BasicDataJclx();
				entity.setId(rs.getString("id"));
				entity.setName(rs.getString("name"));
				list.add(entity);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	} 
	
	public int saveBasicData(String name,String type){
		String countSql = "select id from basic_data";
		List<Integer> list = new ArrayList<>();
		String sql = "INSERT INTO basic_data(id,name,type) value(?,?,?)";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet countRs = dataOperation.executeQuery(countSql, null);
		try {
			while (countRs.next()) {
				list.add(countRs.getInt("id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int i = dataOperation.executeUpdate(sql, new Object[]{Collections.max(list)+1,name,type});
		dataOperation.close();
		return i;
	}
	
	public int saveBasicDataJclx(String name){
		String countSql = "select id from basic_data_jclx";
		List<Integer> list = new ArrayList<>();
		String sql = "INSERT INTO basic_data_jclx(id,name) value(?,?)";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet countRs = dataOperation.executeQuery(countSql, null);
		try {
			while (countRs.next()) {
				list.add(countRs.getInt("id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int i = dataOperation.executeUpdate(sql, new Object[]{Collections.max(list)+1,name});
		dataOperation.close();
		return i;
	}
	
	public int updateBasicDataJclx(String id,String name){
		String sql = "UPDATE basic_data_jclx SET `name`= ? WHERE id = ?";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		int i = dataOperation.executeUpdate(sql, new Object[]{name,id});
		dataOperation.close();
		return i;
	}
	
	public int updateBasicData(String id,String name){
		String sql = "UPDATE basic_data SET `name`= ? WHERE id = ?";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		int i = dataOperation.executeUpdate(sql, new Object[]{name,id});
		dataOperation.close();
		return i;
	}
	
	public int deleteBasicDataJclx(String id){
		String sql = "DELETE FROM basic_data_jclx WHERE id = ?";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		int i = dataOperation.executeUpdate(sql, new Object[]{id});
		dataOperation.close();
		return i;
	}
	
	public int deleteBasicData(String id){
		String sql = "DELETE FROM basic_data WHERE id = ?";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		int i = dataOperation.executeUpdate(sql, new Object[]{id});
		dataOperation.close();
		return i;
	}
	
	public List<BasicData> selectBsicData(String type){
		ArrayList<BasicData> basicData = new ArrayList<BasicData>();
		String sql = "select * from basic_data where type = ?";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs = dataOperation.executeQuery(sql, new String[]{ type});
		try
		{
			while (rs.next())
			{
				BasicData entity = new BasicData();
				entity.setBasicid(Nullchange.NulltoString(rs.getString("id")));
				entity.setName(Nullchange.NulltoString(rs.getString("name")));
				entity.setType(Nullchange.NulltoString(rs.getString("type")));
				entity.setMode(Nullchange.NulltoString(rs.getString("mode")));
				basicData.add(entity);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		dataOperation.close();
		return basicData;
	}
	
	public List<BasicData> selectAllBasic(String bridge_id){
		ArrayList<BasicData> basicData = new ArrayList<BasicData>();
		String sql = "select DISTINCT bridge_id,cd_f_type_id,bd.id,bd.name,bd.type from test_point tp LEFT JOIN basic_data bd ON bd.id=tp.cd_f_type_id where bridge_id = ?";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs = dataOperation.executeQuery(sql, new String[]{ bridge_id});
		try
		{
			while (rs.next())
			{
				BasicData entity = new BasicData();
				entity.setBasicid(rs.getString("id"));
				entity.setName(rs.getString("name"));
				entity.setType(rs.getString("type"));
				basicData.add(entity);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		dataOperation.close();
		return basicData;
	}
	
	
	public List<BasicData> selectAllBasic(){
		ArrayList<BasicData> basicData = new ArrayList<BasicData>();
		String sql = "select * from basic_data";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs = dataOperation.executeQuery(sql, new String[]{ });
		try
		{
			while (rs.next())
			{
				BasicData entity = new BasicData();
				entity.setBasicid(rs.getString("id"));
				entity.setName(rs.getString("name"));
				entity.setType(rs.getString("type"));
				basicData.add(entity);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		dataOperation.close();
		return basicData;
	}
	
	
	public List<BasicData> selectAllBasicId(String id){
		ArrayList<BasicData> basicData = new ArrayList<BasicData>();
		String sql = "select * from basic_data where id=?";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs = dataOperation.executeQuery(sql, new String[]{id});
		try
		{
			while (rs.next())
			{
				BasicData entity = new BasicData();
				entity.setBasicid(rs.getString("id"));
				entity.setName(rs.getString("name"));
				entity.setType(rs.getString("type"));
				entity.setMode(rs.getString("mode"));
				basicData.add(entity);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		dataOperation.close();
		return basicData;
	}
	
	
	public List<BasicDataStandard> selectBasicStandard(String bridge_id){
		ArrayList<BasicDataStandard> basicData = new ArrayList<BasicDataStandard>();
		String sql = "SELECT bds.id,bd.`name`,bds.standard,bds.basic_data_id FROM `basic_data_standard` bds LEFT JOIN basic_data bd ON bd.id = bds.basic_data_id WHERE bd.type = 1 and bds.bridge_id = ? ORDER BY id asc";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs = dataOperation.executeQuery(sql, new String[]{bridge_id});
		try
		{
			while (rs.next())
			{
				BasicDataStandard entity = new BasicDataStandard();
				entity.setId(rs.getString("id"));
				entity.setBasicId(rs.getString("basic_data_id"));
				entity.setBasicName(rs.getString("name"));
				entity.setStandard(rs.getString("standard"));
				basicData.add(entity);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		dataOperation.close();
		return basicData;
	}
	
	public int saveBasicDataStandard(String bridge_id,String basic_data_id,String standard){
		String countSql = "select id from basic_data_standard";
		List<Integer> list = new ArrayList<>();
		String sql = "INSERT INTO basic_data_standard(id,basic_data_id,bridge_id,standard) value(?,?,?,?)";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet countRs = dataOperation.executeQuery(countSql, null);
		try {
			while (countRs.next()) {
				list.add(countRs.getInt("id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int i = dataOperation.executeUpdate(sql, new Object[]{Collections.max(list)+1,basic_data_id,bridge_id,standard});
		dataOperation.close();
		return i;
	}
	
	public int updateBasicDataStandard(String id,String standard,String basic_data_id){
		String sql = "UPDATE basic_data_standard SET standard = ?,basic_data_id = ? WHERE id = ?";
//		String sqlwzl = "SELECT basic_data_id FROM basic_data_standard WHERE id = ?";
//		String sqljcfl = "UPDATE basic_data SET name = ? WHERE id = ?";
//		List<Integer> list = new ArrayList<>();
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
//		ResultSet countRs = dataOperation.executeQuery(sqlwzl, new String[]{ id});
//		try {
//			while (countRs.next()) {
//				list.add(countRs.getInt("id"));
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		int wzlNum = dataOperation.executeUpdate(sql, new Object[]{standard,basic_data_id,id});
//		int jcflNum = dataOperation.executeUpdate(sqljcfl, new Object[]{name,list.get(0)});
		dataOperation.close();
		return wzlNum;
	}
	
	public int deleteBasicDataStandard(String id){
		String sql = "DELETE FROM basic_data_standard WHERE id = ?";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		int i = dataOperation.executeUpdate(sql, new Object[]{id});
		dataOperation.close();
		return i;
	}
}
