package cn.org.hsxx.bean;

public class TestPoint {
	private String id;
	/***测点编号*/
	private String cd_code;
	/***通道编号*/
	private String td_code;
	/***测点位置跨号*/
	private String cd_span_no;
	
	private String s_id;
	/***阈值*/
	private String fz;
	/***测点描述*/
	private String cd_describe;
	/***采样频率*/
	private String cypl;
	/***设备型号*/
	private String sbxh;
	
	private String if_bad;
	
	private BasicDataJclx bdj;
	
	/***检测点类型*/
	private String base_dataName;
	private String cd_type_id;
	
	/***检测点分类*/
	private String cd_f_type_id;
	
	private String bridge_id;
	
	private String r_id;
	
	private String if_jihuo;
	
	private String modelID;
	
	private String yjStatus;
	
	private String fz2;/***阈值2*/
	private String xmd;/***线密度*/
	private String zysc;/***自由索长*/
	private String csz;//初始值

	
	
	public String getCsz() {
		return csz;
	}

	public void setCsz(String csz) {
		this.csz = csz;
	}

	public String getYjStatus() {
		return yjStatus;
	}

	public String getFz2() {
		return fz2;
	}

	public void setFz2(String fz2) {
		this.fz2 = fz2;
	}

	public String getXmd() {
		return xmd;
	}

	public void setXmd(String xmd) {
		this.xmd = xmd;
	}

	public String getZysc() {
		return zysc;
	}

	public void setZysc(String zysc) {
		this.zysc = zysc;
	}

	public void setYjStatus(String yjStatus) {
		this.yjStatus = yjStatus;
	}

	public String getModelID() {
		return modelID;
	}

	public void setModelID(String modelID) {
		this.modelID = modelID;
	}

	public String getIf_jihuo() {
		return if_jihuo;
	}

	public void setIf_jihuo(String if_jihuo) {
		this.if_jihuo = if_jihuo;
	}

	public String getCd_code() {
		return cd_code;
	}

	public void setCd_code(String cd_code) {
		this.cd_code = cd_code;
	}

	public String getTd_code() {
		return td_code;
	}

	public void setTd_code(String td_code) {
		this.td_code = td_code;
	}

	public String getCd_span_no() {
		return cd_span_no;
	}

	public void setCd_span_no(String cd_span_no) {
		this.cd_span_no = cd_span_no;
	}

	public String getS_id() {
		return s_id;
	}

	public void setS_id(String s_id) {
		this.s_id = s_id;
	}

	public String getFz() {
		return fz;
	}

	public void setFz(String fz) {
		this.fz = fz;
	}

	public String getCd_describe() {
		return cd_describe;
	}

	public void setCd_describe(String cd_describe) {
		this.cd_describe = cd_describe;
	}

	public String getCypl() {
		return cypl;
	}

	public void setCypl(String cypl) {
		this.cypl = cypl;
	}

	public String getSbxh() {
		return sbxh;
	}

	public void setSbxh(String sbxh) {
		this.sbxh = sbxh;
	}

	public BasicDataJclx getBdj() {
		return bdj;
	}

	public void setBdj(BasicDataJclx bdj) {
		this.bdj = bdj;
	}

	public String getBase_dataName() {
		return base_dataName;
	}

	public void setBase_dataName(String base_dataName) {
		this.base_dataName = base_dataName;
	}

	public String getCd_f_type_id() {
		return cd_f_type_id;
	}

	public void setCd_f_type_id(String cd_f_type_id) {
		this.cd_f_type_id = cd_f_type_id;
	}

	public String getIf_bad() {
		return if_bad;
	}

	public void setIf_bad(String if_bad) {
		this.if_bad = if_bad;
	}

	public String getR_id() {
		return r_id;
	}

	public void setR_id(String r_id) {
		this.r_id = r_id;
	}

	public String getBridge_id() {
		return bridge_id;
	}

	public void setBridge_id(String bridge_id) {
		this.bridge_id = bridge_id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCd_type_id() {
		return cd_type_id;
	}

	public void setCd_type_id(String cd_type_id) {
		this.cd_type_id = cd_type_id;
	}
}
