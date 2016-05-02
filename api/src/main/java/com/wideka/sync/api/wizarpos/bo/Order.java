package com.wideka.sync.api.wizarpos.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @author JiakunXu
 * 
 */
public class Order implements Serializable {

	private static final long serialVersionUID = 3556906450869793455L;

	/**
	 * 订单编号(原始订单修改操作用).
	 */
	private String orderId;

	/**
	 * 微信号.
	 */
	private String openId;

	/**
	 * 购物车编号(购物车结算用).
	 */
	private String cartId;

	/**
	 * 商品编号(立即购买用).
	 */
	private String productId;

	/**
	 * 会员卡编号.
	 */
	private String cardId;

	/**
	 * 慧商户号.
	 */
	private String mid;

	/**
	 * 微信订单标志,1:一月内所有订单 2:三月内所有订单 3: 全部订单.
	 */
	private String status;

	@JSONField(name = "orderDetail")
	private List<OrderDetail> orderDetailList;

	/**
	 * 订单备注.
	 */
	private String remark;

	/**
	 * 配送方式 1 货到付款 2 微信支付送货上门 3 微信支付到店 货 4 会员卡支付送货上门 5 会员卡支付到店 货.
	 */
	private String dispatchType;

	/**
	 * 付款金额.
	 */
	private BigDecimal amount;

	/**
	 * 支付状态(0 未支付 1 已支付).
	 */
	private String payStatus;

	/**
	 * 收货地址编号.
	 */
	private String addressId;

	/**
	 * pos 机是否要打印小票 true 打印,false 不打印.
	 */
	private Boolean print;

	/**
	 * 微信消息模板跳转链接.
	 */
	private String url;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getCartId() {
		return cartId;
	}

	public void setCartId(String cartId) {
		this.cartId = cartId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<OrderDetail> getOrderDetailList() {
		return orderDetailList;
	}

	public void setOrderDetailList(List<OrderDetail> orderDetailList) {
		this.orderDetailList = orderDetailList;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDispatchType() {
		return dispatchType;
	}

	public void setDispatchType(String dispatchType) {
		this.dispatchType = dispatchType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public Boolean isPrint() {
		return print;
	}

	public void setPrint(Boolean print) {
		this.print = print;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
