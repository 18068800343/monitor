package hs.bm.bean;

import java.util.List;

public class DataFull {

	private Integer id;
	private String brg_id;
	private String mode;
	private String mode_name;
	private String data_full;
	private String data_type;
	private String data_full_date;
	private List<String> brg_mode;
	
	public List<String> getBrg_mode() {
		return brg_mode;
	}
	public void setBrg_mode(List<String> brg_mode) {
		this.brg_mode = brg_mode;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBrg_id() {
		return brg_id;
	}
	public void setBrg_id(String brg_id) {
		this.brg_id = brg_id;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getMode_name() {
		return mode_name;
	}
	public void setMode_name(String mode_name) {
		this.mode_name = mode_name;
	}
	public String getData_full() {
		return data_full;
	}
	public void setData_full(String data_full) {
		this.data_full = data_full;
	}
	public String getData_type() {
		return data_type;
	}
	public void setData_type(String data_type) {
		this.data_type = data_type;
	}
	public String getData_full_date() {
		return data_full_date;
	}
	public void setData_full_date(String data_full_date) {
		this.data_full_date = data_full_date;
	}
}
