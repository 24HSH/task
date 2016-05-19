package com.hsh24.sync.api.oms.bo;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ReceiptDetail implements Serializable {

	private static final long serialVersionUID = -6067606067147286331L;

	@JSONField(serialize = false)
	private Long detailId;

	@JSONField(serialize = false)
	private Long receiptId;

	@JSONField(serialize = false)
	private Long orderId;

	/**
	 * 收货数量.
	 */
	@JSONField(name = "goodsNum")
	private int quantity;

	@JSONField(name = "createBy")
	private String modifyUser;

	// >>>>>>>>>>以下是辅助属性<<<<<<<<<<

	@JSONField(name = "orgId")
	private Long shopId;

	@JSONField(serialize = false)
	private Long itemId;

	@JSONField(name = "goodsId")
	private String itemCode;

	@JSONField(serialize = false)
	private String itemName;

	@JSONField(serialize = false)
	private String propertiesName;

	private String goodsSpecCd = "";

	private String remark = "";

	@JSONField(serialize = false)
	private String barCode;

	public Long getDetailId() {
		return detailId;
	}

	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}

	public Long getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(Long receiptId) {
		this.receiptId = receiptId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getPropertiesName() {
		return propertiesName;
	}

	public void setPropertiesName(String propertiesName) {
		this.propertiesName = propertiesName;
	}

	public String getGoodsSpecCd() {
		return goodsSpecCd;
	}

	public void setGoodsSpecCd(String goodsSpecCd) {
		this.goodsSpecCd = goodsSpecCd;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

}
