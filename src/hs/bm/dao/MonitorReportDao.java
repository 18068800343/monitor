package hs.bm.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hs.bm.bean.MonitorReport;

public class MonitorReportDao {

	private static MonitorReportDao dao;
	
	public static MonitorReportDao getInstanse(){
		if(dao==null){
			dao=new MonitorReportDao();
		}
		return dao;
	}
	
	public List<MonitorReport> selectMonitorReport(String manage){
		List<MonitorReport>list=new ArrayList<MonitorReport>();
		String sql="select * from monitor_report mr left join brg_card_admin_id bca on mr.brg_no = bca.bridge_no where bca.manage_id like ?";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		ResultSet rs =dataOperation.executeQuery(sql, new String[]{manage});
		try {
			while(rs.next()){
				MonitorReport mr=new MonitorReport();
				mr.setrId(rs.getString("r_id"));
				mr.setrNo(rs.getString("r_no"));
				mr.setBrgName(rs.getString("brg_name"));
				mr.setBrgNo(rs.getString("brg_no"));
				mr.setReportName(rs.getString("report_name"));
				mr.setReportUrl(rs.getString("report_url"));
				mr.setReportStatus(rs.getString("report_status"));
				mr.setReportTime(rs.getString("report_time"));
				list.add(mr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataOperation.close();
		return list;
	}
	
	public int addMonitorReport(String rId,String rNo,String brgName,String brgNo){
		int i=0;
		String sql="insert into monitor_report(r_id,r_no,brg_name,brg_no,report_status,report_time) values(?,?,?,?,?,now())";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		i=dataOperation.executeUpdate(sql, new Object[]{rId,rNo,brgName,brgNo,"0"});
		dataOperation.close();
		return i;
	}
	
	public int updateMonitorReport(String rId,String reportName,String reportUrl){
		int i=0;
		String sql="update monitor_report set report_name=?,report_url=?,report_status=? where r_id=?";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		i=dataOperation.executeUpdate(sql, new Object[]{reportName,reportUrl,"1",rId});
		dataOperation.close();
		return i;
	}
	
	public int deleteMonitorReport(String id){
		int i=0;
		String sql="delete from monitor_report where r_id=?";
		MyDataOperation dataOperation = new MyDataOperation(MyDataSource.getInstance().getConnection());
		i=dataOperation.executeUpdate(sql, new Object[]{id});
		dataOperation.close();
		return i;
	}
}
