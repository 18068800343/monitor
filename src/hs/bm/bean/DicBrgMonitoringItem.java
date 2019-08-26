package hs.bm.bean;

public class DicBrgMonitoringItem {
	public String id;
	public String item_first;
	public String item_second;
	private String tableName;
	
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getItem_first() {
		return item_first;
	}

	public void setItem_first(String item_first) {
		this.item_first = item_first;
	}

	public String getItem_second() {
		return item_second;
	}

	public void setItem_second(String item_second) {
		this.item_second = item_second;
	}
}
