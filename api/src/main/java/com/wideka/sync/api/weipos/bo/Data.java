package com.wideka.sync.api.weipos.bo;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @author JiakunXu
 * 
 */
public class Data implements Serializable {

	private static final long serialVersionUID = 5289028241505002304L;

	@JSONField(name = "echo_str")
	private String echoStr;

	/**
	 * 旺POS设备EN号.
	 */
	@JSONField(name = "device_en")
	private String deviceEn;

	@JSONField(name = "data")
	private Trade trade;

	public String getEchoStr() {
		return echoStr;
	}

	public void setEchoStr(String echoStr) {
		this.echoStr = echoStr;
	}

	public String getDeviceEn() {
		return deviceEn;
	}

	public void setDeviceEn(String deviceEn) {
		this.deviceEn = deviceEn;
	}

	public Trade getTrade() {
		return trade;
	}

	public void setTrade(Trade trade) {
		this.trade = trade;
	}

}
