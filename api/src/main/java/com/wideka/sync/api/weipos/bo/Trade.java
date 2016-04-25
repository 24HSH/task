package com.wideka.sync.api.weipos.bo;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @author JiakunXu
 * 
 */
public class Trade implements Serializable {

	private static final long serialVersionUID = 1204325406388811125L;

	/**
	 * 收银订单号.
	 */
	@JSONField(name = "cashier_trade_no")
	private String cashierTradeNo;

	/**
	 * 第三方订单流水号，不可空，由数字和英文字母组成，长度不超过30.
	 */
	@JSONField(name = "out_trade_no")
	private String outTradeNo;

	/**
	 * 商品名称或标题，不可空.
	 */
	@JSONField(name = "body")
	private String body;

	/**
	 * 订单金额，单位：分，不可空.
	 */
	@JSONField(name = "total_fee")
	private int totalFee;

	/**
	 * 异步通知地址，可空.
	 */
	@JSONField(name = "notify_url")
	private String notifyUrl;

	/**
	 * 备注信息，可空，订单信息原样返回，可空.
	 */
	@JSONField(name = "attach")
	private String attach;

	public String getCashierTradeNo() {
		return cashierTradeNo;
	}

	public void setCashierTradeNo(String cashierTradeNo) {
		this.cashierTradeNo = cashierTradeNo;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

}
