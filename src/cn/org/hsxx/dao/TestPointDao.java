package cn.org.hsxx.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.org.hsxx.bean.BasicDataJclx;
import cn.org.hsxx.bean.TestPoint;
import cn.org.hsxx.util.MyDataOperation;
import cn.org.hsxx.util.MyDataSource;
import cn.org.hsxx.util.Nullchange;
import hs.bm.bean.SysOrgInfo;

public class TestPointDao {
	private static TestPointDao testPointDao;

	public static TestPointDao getInstance()
	{
		if (testPointDao == null)
		{
			testPointDao = new TestPointDao();
		}
		return testPointDao;
	}
	
	public List<TestPoint> getTestPointData(String bridge_id,String cd_f_type_id){
		ArrayList<TestPoint> testPoints = new ArrayList<TestPoint>();
		String sql = "select tp.id,tp.modelID,tp.cd_code,tp.td_code,tp.cd_span_no,bdj.`id` as bdjid,tp.yj_status,bdj.`name`,tp.fz,tp.cd_describe,tp.cypl,tp.sbxh,tp.if_bad,tp.if_jihuo,tp.fz2,tp.xmd,tp.zysc from test_point tp LEFT JOIN basic_data_jclx bdj ON bdj.id = tp.cd_type_id WHERE tp.bridge_id = ?";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs = dataOperation.executeQuery(sql, new String[]{ bridge_id});
		if (!cd_f_type_id.equals("-1")) {
			sql += " and tp.cd_type_id = ?";
			rs = dataOperation.executeQuery(sql, new String[]{ bridge_id,cd_f_type_id });
		}
		try
		{
			while (rs.next())
			{
				TestPoint tp = new TestPoint();
				tp.setId(Nullchange.NulltoString(rs.getString("id")));
				tp.setModelID(Nullchange.NulltoString(rs.getString("modelID")));
				tp.setCd_code(Nullchange.NulltoString(rs.getString("cd_code")));
				tp.setTd_code(Nullchange.NulltoString(rs.getString("td_code")));
				tp.setCd_span_no(Nullchange.NulltoString(rs.getString("cd_span_no")));
				tp.setBase_dataName(Nullchange.NulltoString(rs.getString("name")));
				tp.setFz(Nullchange.NulltoString(rs.getString("fz")));
				tp.setCd_describe(Nullchange.NulltoString(rs.getString("cd_describe")));
				tp.setCypl(Nullchange.NulltoString(rs.getString("cypl")));
				tp.setSbxh(Nullchange.NulltoString(rs.getString("sbxh")));
				tp.setIf_bad(Nullchange.NulltoString(rs.getString("if_bad")));
				tp.setIf_jihuo(Nullchange.NulltoString(rs.getString("if_jihuo")));
				tp.setCd_type_id(Nullchange.NulltoString(rs.getString("bdjid")));
				tp.setYjStatus(Nullchange.NulltoString(rs.getString("yj_status")));
				tp.setFz2(Nullchange.NulltoString(rs.getString("fz2")));
				tp.setXmd(Nullchange.NulltoString(rs.getString("xmd")));
				tp.setZysc(Nullchange.NulltoString(rs.getString("zysc")));
				testPoints.add(tp);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		dataOperation.close();
		return testPoints;
	}
	
	public Map<String, List<String>> getTPData(String bridge_id){
		ArrayList<TestPoint> testPoints = new ArrayList<TestPoint>();
		ArrayList<String> modeIds = new ArrayList<String>();
		ArrayList<String> yjStatus = new ArrayList<String>();
		String sql = "select tp.modelID,tp.yj_status from test_point tp  WHERE tp.bridge_id = ?";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs = dataOperation.executeQuery(sql, new String[]{ bridge_id});
		Map<String, List<String>> map = new HashMap<>();
		try
		{
			while (rs.next())
			{
				String modelID = Nullchange.NulltoString(rs.getString("modelID"));
				String yjStatu = Nullchange.NulltoString(rs.getString("yj_status"));
				if(null!=modelID&&!"".equals(modelID)&&null!=yjStatu&&!"".equals(yjStatu)){
					
				}
				modeIds.add(modelID);
				yjStatus.add(yjStatu);
			}
			map.put("curSensorIDS", modeIds);
			map.put("curSensorStates", yjStatus);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		dataOperation.close();
		return map;
	}
	
	public int saveTestPoint(TestPoint tp){
		BasicDataJclx bdj = tp.getBdj();
		int i=0;
		String countSql = "select id from test_point";
		String jcflSql = "select basic_data_id from basic_data_jclx where id = ?";
		List<Integer> list = new ArrayList<>();
		List<Integer> listJclx = new ArrayList<>();
		String sql="INSERT INTO test_point(id,cd_code,td_code,cd_span_no,s_id,fz,cd_describe,cypl,sbxh,cd_type_id,cd_f_type_id,bridge_id,r_id,if_jihuo,fz2,xmd,zysc) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet countRs = dataOperation.executeQuery(countSql, null);
		ResultSet jcflRs = dataOperation.executeQuery(jcflSql, new String[]{ bdj.getId() });
		try {
			while (countRs.next()) {
				try {
					list.add(countRs.getInt("id"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			while (jcflRs.next()) {
				listJclx.add(jcflRs.getInt("basic_data_id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		i = dataOperation.executeUpdate(sql, new Object[]{
			Collections.max(list)+1,
			tp.getCd_code(),
			tp.getTd_code(),
			tp.getCd_span_no(),
			tp.getS_id(),
			tp.getFz(),
			tp.getCd_describe(),
			tp.getCypl(),
			tp.getSbxh(),
			bdj.getId(),
			listJclx.get(0),
			tp.getBridge_id(),
			tp.getR_id(),
			tp.getIf_jihuo(),
			tp.getFz2(),
			tp.getXmd(),
			tp.getZysc()
		});
		dataOperation.close();
		return i;
	}
	
	public int updateBasicData(String id,String jihuo){
		String sql = "update test_point set if_jihuo = ? where id = ?";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		int i = dataOperation.executeUpdate(sql, new Object[]{jihuo,id});
		dataOperation.close();
		return i;
	}
	public int updateYjStatus(String id,String status){
		String sql = "update test_point set yj_status = ? where id = ?";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		int i = dataOperation.executeUpdate(sql, new Object[]{status,id});
		dataOperation.close();
		return i;
	}
	public int insertTestPoint(TestPoint tp){
		String sql="INSERT INTO test_point(id,cd_code,td_code,cd_span_no,s_id,fz,cd_describe,cypl,sbxh,cd_type_id,cd_f_type_id,bridge_id,r_id,if_jihuo,modelID) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		
		int i = dataOperation.executeUpdate(sql, new Object[]{
			tp.getId(),
			tp.getCd_code(),
			tp.getTd_code(),
			tp.getCd_span_no(),
			tp.getS_id(),
			tp.getFz(),
			tp.getCd_describe(),
			tp.getCypl(),
			tp.getSbxh(),
			tp.getCd_type_id(),
			tp.getCd_f_type_id(),
			tp.getBridge_id(),
			tp.getR_id(),
			tp.getIf_jihuo(),
			tp.getModelID()
		});
		dataOperation.close();
		return i;
	}
	
	public int deleteTestPoint(String id){
		String sql = "delete from test_point where id = ?";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		int i = dataOperation.executeUpdate(sql, new Object[]{id});
		dataOperation.close();
		return i;
	}
	
	
	public int updateTestPoint(TestPoint tp){
		String sql = "UPDATE test_point SET cd_code=?,td_code=?,cd_span_no=?,cd_type_id=?,fz=?,cd_describe=?,cypl=?,sbxh=?,yj_status=?,fz2=?,xmd=?,zysc=? WHERE id=?";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		int i = dataOperation.executeUpdate(sql, new Object[]{tp.getCd_code(),tp.getTd_code(),tp.getCd_span_no(),tp.getCd_type_id(),tp.getFz(),tp.getCd_describe(),tp.getCypl(),tp.getSbxh(),tp.getYjStatus(),tp.getFz2(),tp.getXmd(),tp.getZysc(),tp.getId()});
		dataOperation.close();
		return i;
	}
	
	public String getTPYcCd(String bridge_id){
		int i=0;
		String sql = "select tp.modelID,tp.yj_status from test_point tp  WHERE tp.bridge_id = ? and tp.yj_status != '0'";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs = dataOperation.executeQuery(sql, new String[]{ bridge_id});
		Map<String, List<String>> map = new HashMap<>();
		try
		{
			while (rs.next())
			{
				++i;	
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		dataOperation.close();
		return i+"";
	}
}
