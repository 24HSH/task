package com.hsh24.sync.api.oms.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 交易.
 * 
 * @author JiakunXu
 * 
 */
public class Trade implements Serializable {

	private static final long serialVersionUID = -1761794880999676421L;

	/**
	 * 交易ID.
	 */
	private Long tradeId;

	/**
	 * 店铺ID.
	 */
	private Long shopId;

	/**
	 * 供应商ID.
	 */
	private Long supId;

	/**
	 * 交易价格（不含折扣）.
	 */
	private BigDecimal tradePrice;

	/**
	 * 备注，店小儿分类交易.
	 */
	private String remark;

	/**
	 * tosend: 待发货; send: 已发货; sign: 标记签收; cancel: 已关闭; feedback: 维权订单; feedbacked: 已处理维权订单.
	 */
	private String type;

	/**
	 * 交易订单号.
	 */
	@JSONField(name = "purchaseCd")
	private String tradeNo;

	/**
	 * 下单时间.
	 */
	@JSONField(name = "purchaseDt")
	private String createDate;

	/**
	 * 买家付款时间.
	 */
	private String payDate;

	/**
	 * 操作人ID.
	 */
	private String modifyUser;

	// >>>>>>>>>>以下是辅助属性<<<<<<<<<<

	/**
	 * 订单行项目.
	 */
	private List<Order> orderList;

	public Long getTradeId() {
		return tradeId;
	}

	public void setTradeId(Long tradeId) {
		this.tradeId = tradeId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long getSupId() {
		return supId;
	}

	public void setSupId(Long supId) {
		this.supId = supId;
	}

	public BigDecimal getTradePrice() {
		return tradePrice;
	}

	public void setTradePrice(BigDecimal tradePrice) {
		this.tradePrice = tradePrice;
	}

	/**
	 * 实付金额 tradePrice + (change).
	 * 
	 * @return
	 */
	public BigDecimal getPrice() {
		if (this.tradePrice != null) {
			return this.tradePrice;
		}

		return BigDecimal.ZERO;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public List<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}

}
