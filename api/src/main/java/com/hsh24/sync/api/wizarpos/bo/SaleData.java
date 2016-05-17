package com.hsh24.sync.api.wizarpos.bo;

import java.io.Serializable;

/**
 * 
 * @author JiakunXu
 * 
 */
public class SaleData implements Serializable {

	private static final long serialVersionUID = 3416180370799628879L;

	private String tableName;

	private String lastReceivedTime;

	private String mid;

	private int count;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getLastReceivedTime() {
		return lastReceivedTime;
	}

	public void setLastReceivedTime(String lastReceivedTime) {
		this.lastReceivedTime = lastReceivedTime;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
