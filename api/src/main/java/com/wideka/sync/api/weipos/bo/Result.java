package com.wideka.sync.api.weipos.bo;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @author JiakunXu
 * 
 */
public class Result implements Serializable {

	private static final long serialVersionUID = 5298150005183000384L;

	@JSONField(name = "status")
	private int status;

	@JSONField(name = "info")
	private String info;

	@JSONField(name = "data")
	private Data data;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

}
