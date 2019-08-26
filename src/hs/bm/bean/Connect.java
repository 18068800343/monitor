package hs.bm.bean;

import java.util.ArrayList;
import java.util.List;

public class Connect {
	
	private String id;
	private String name;
	private String type;
	private String bgco;
	private String name_no;
	private String longitude;
	private String latitude;
	private String bridge_name;
	private String bridge_id;
	private String brg_type;
	private String cd_count;
	private String count;
	private String day_count;
	private String month_count;
	private Integer sort;
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getDay_count() {
		return day_count;
	}

	public void setDay_count(String day_count) {
		this.day_count = day_count;
	}

	public String getMonth_count() {
		return month_count;
	}

	public void setMonth_count(String month_count) {
		this.month_count = month_count;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getCd_count() {
		return cd_count;
	}

	public void setCd_count(String cd_count) {
		this.cd_count = cd_count;
	}

	public String getBrg_type() {
		return brg_type;
	}

	public void setBrg_type(String brg_type) {
		this.brg_type = brg_type;
	}

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

	public String getBridge_name() {
		return bridge_name;
	}

	public void setBridge_name(String bridge_name) {
		this.bridge_name = bridge_name;
	}

	public String getBridge_id() {
		return bridge_id;
	}

	public void setBridge_id(String bridge_id) {
		this.bridge_id = bridge_id;
	}

	private List<Connect> children;
	
	public Connect(){
		this.children = new ArrayList<Connect>();
		
	}

	
	
	public String getName_no() {
		return name_no;
	}



	public void setName_no(String name_no) {
		this.name_no = name_no;
	}



	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBgco() {
		return bgco;
	}

	public void setBgco(String bgco) {
		this.bgco = bgco;
	}

	public List<Connect> getChildren() {
		return children;
	}

	public void setChildren(List<Connect> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "Connect [id=" + id + ", name=" + name + ", type=" + type + ", bgco=" + bgco + ", name_no=" + name_no
				+ ", longitude=" + longitude + ", latitude=" + latitude + ", bridge_name=" + bridge_name
				+ ", bridge_id=" + bridge_id + ", brg_type=" + brg_type + ", cd_count=" + cd_count + ", count=" + count
				+ ", day_count=" + day_count + ", month_count=" + month_count + ", sort=" + sort + ", children="
				+ children + "]";
	}
	
	
	

}
