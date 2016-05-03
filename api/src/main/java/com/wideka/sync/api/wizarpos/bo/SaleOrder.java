package com.wideka.sync.api.wizarpos.bo;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @author JiakunXu
 * 
 */
public class SaleOrder implements Serializable {

	private static final long serialVersionUID = -5067734804018270130L;

	/**
	 * 慧商户号,必须项.
	 */
	private String mid;

	/**
	 * 订单 ID,订单识别标识,新订单提交不必填写,订单修改 必须填写.
	 */
	private String orderId;

	/**
	 * 订单类型(1 堂吃 2 快餐 3 外卖),必须项.
	 */
	private String orderType;

	/**
	 * 配送方式 1 货到付款 2 微信支付送货上门 3 微信支付 到店提货 4 会员卡支付送货上门 5 会员卡支付到店提货.
	 */
	private String dispatchType;

	/**
	 * 餐台 ID 的集合.
	 */
	private String[] tableId;

	/**
	 * 订单状态(0 开单 1 结账 2 撤单).
	 */
	private String status;

	/**
	 * 支付状态(0 未支付 1 已支付).
	 */
	private String payStatus;

	/**
	 * 应付金额,以元为单位,保留两位小数.
	 */
	private String amount;

	/**
	 * 外卖地址,长度 40 位以内.
	 */
	private String takeoutAddr;

	/**
	 * 外卖电话.
	 */
	private String takeoutTel;

	/**
	 * true 推送消息到 pos,false(默认值)不推送.
	 */
	private Boolean print;

	@JSONField(name = "orderDetail")
	private List<OrderDetail> orderDetailList;

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getDispatchType() {
		return dispatchType;
	}

	public void setDispatchType(String dispatchType) {
		this.dispatchType = dispatchType;
	}

	public String[] getTableId() {
		return tableId;
	}

	public void setTableId(String[] tableId) {
		this.tableId = tableId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTakeoutAddr() {
		return takeoutAddr;
	}

	public void setTakeoutAddr(String takeoutAddr) {
		this.takeoutAddr = takeoutAddr;
	}

	public String getTakeoutTel() {
		return takeoutTel;
	}

	public void setTakeoutTel(String takeoutTel) {
		this.takeoutTel = takeoutTel;
	}

	public Boolean getPrint() {
		return print;
	}

	public void setPrint(Boolean print) {
		this.print = print;
	}

	public List<OrderDetail> getOrderDetailList() {
		return orderDetailList;
	}

	public void setOrderDetailList(List<OrderDetail> orderDetailList) {
		this.orderDetailList = orderDetailList;
	}

}
