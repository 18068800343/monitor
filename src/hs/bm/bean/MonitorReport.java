package hs.bm.bean;

/** 
* @ClassName: MonitorReport 
* @Description: 实时监测报告
* @author zhaohui
* @date 2018年12月4日 上午11:39:33 
*  
*/
public class MonitorReport {

	private String rId;
	private String rNo;
	private String brgName;
	private String brgNo;
	private String reportName;
	private String reportUrl;
	private String reportStatus;
	private String reportTime;
	public String getrId() {
		return rId;
	}
	public void setrId(String rId) {
		this.rId = rId;
	}
	public String getrNo() {
		return rNo;
	}
	public void setrNo(String rNo) {
		this.rNo = rNo;
	}
	public String getBrgName() {
		return brgName;
	}
	public void setBrgName(String brgName) {
		this.brgName = brgName;
	}
	public String getBrgNo() {
		return brgNo;
	}
	public void setBrgNo(String brgNo) {
		this.brgNo = brgNo;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getReportUrl() {
		return reportUrl;
	}
	public void setReportUrl(String reportUrl) {
		this.reportUrl = reportUrl;
	}
	public String getReportStatus() {
		return reportStatus;
	}
	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}
	public String getReportTime() {
		return reportTime;
	}
	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}
	
	
}
