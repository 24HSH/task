package com.hsh24.sync.api.wizarpos.bo;

import java.io.Serializable;

/**
 * 
 * @author JiakunXu
 * 
 */
public class Payment implements Serializable {

	private static final long serialVersionUID = 1534081558827213435L;

	/**
	 * 支付形式(0刷卡1现金 2会员卡 3 微信支付 4 支付宝支 付),必须项.
	 */
	private String payType;

	/**
	 * 支付金额,以元为单位,保留两位小数,必须项.
	 */
	private String amount;

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

}
