package com.hsh24.sync.api.oms.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 交易.
 * 
 * @author JiakunXu
 * 
 */
@Alias("omsTrade")
public class Trade implements Serializable {

	private static final long serialVersionUID = -1761794880999676421L;

	private String actionType;

	/**
	 * 交易ID.
	 */
	@JSONField(serialize = false)
	private Long tradeId;

	/**
	 * 店铺ID.
	 */
	@JSONField(name = "orgId")
	private Long shopId;

	/**
	 * 供应商ID.
	 */
	@JSONField(name = "supplier")
	private Long supId;

	/**
	 * 交易价格（不含折扣）.
	 */
	@JSONField(name = "purchaseMoney")
	private BigDecimal tradePrice;

	private String remark = "";

	/**
	 * tosend: 待发货; send: 已发货; sign: 标记签收; cancel: 已关闭; feedback: 维权订单; feedbacked: 已处理维权订单.
	 */
	@JSONField(serialize = false)
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

	@JSONField(name = "preArriveDt")
	private String sendDate;

	@JSONField(name = "createBy")
	private String createUser;

	@JSONField(name = "purchaser")
	private String modifyUser;

	// >>>>>>>>>>以下是辅助属性<<<<<<<<<<

	/**
	 * 订单行项目.
	 */
	@JSONField(name = "purchaseOrderGoodsList")
	private List<Order> orderList;

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

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

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
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
