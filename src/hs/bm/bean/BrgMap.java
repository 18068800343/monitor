package hs.bm.bean;

import java.util.List;
import java.util.Map;

public class BrgMap {

	private String bridge_id;
	private String bridge_name;
	private String yj_status;
	private String no_data;
	private String normal;
	private String longitude;
	private String latitude;
	private Map<String, String> modeStatus;
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public Map<String, String> getModeStatus() {
		return modeStatus;
	}
	public void setModeStatus(Map<String, String> modeStatus) {
		this.modeStatus = modeStatus;
	}
	public String getBridge_id() {
		return bridge_id;
	}
	public void setBridge_id(String bridge_id) {
		this.bridge_id = bridge_id;
	}
	public String getBridge_name() {
		return bridge_name;
	}
	public void setBridge_name(String bridge_name) {
		this.bridge_name = bridge_name;
	}
	public String getYj_status() {
		return yj_status;
	}
	public void setYj_status(String yj_status) {
		this.yj_status = yj_status;
	}
	public String getNo_data() {
		return no_data;
	}
	public void setNo_data(String no_data) {
		this.no_data = no_data;
	}
	public String getNormal() {
		return normal;
	}
	public void setNormal(String normal) {
		this.normal = normal;
	}
}

