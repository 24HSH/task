package com.hsh24.sync.api.oms.bo;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @author JiakunXu
 * 
 */
public class Receipt implements Serializable {

	private static final long serialVersionUID = 371464744766432881L;

	@JSONField(serialize = false)
	private Long receiptId;

	@JSONField(name = "receiveCd")
	private String receiptNo;

	@JSONField(name = "orgId")
	private Long shopId;

	@JSONField(serialize = false)
	private Long tradeId;

	@JSONField(name = "receiveDt")
	private String createDate;

	@JSONField(name = "createBy")
	private String createUser;

	@JSONField(name = "seller")
	private String modifyUser;

	// >>>>>>>>>>以下是辅助属性<<<<<<<<<<

	@JSONField(name = "supplierId")
	private Long supId;

	@JSONField(name = "purchaseCd")
	private String tradeNo;

	@JSONField(name = "interReceiveGoodsList")
	private List<ReceiptDetail> receiptDetailList;

	private String supplierOrderCd = "";

	private String warehouse;

	private String remark = "";

	public Long getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(Long receiptId) {
		this.receiptId = receiptId;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long getTradeId() {
		return tradeId;
	}

	public void setTradeId(Long tradeId) {
		this.tradeId = tradeId;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
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

	public Long getSupId() {
		return supId;
	}

	public void setSupId(Long supId) {
		this.supId = supId;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public List<ReceiptDetail> getReceiptDetailList() {
		return receiptDetailList;
	}

	public void setReceiptDetailList(List<ReceiptDetail> receiptDetailList) {
		this.receiptDetailList = receiptDetailList;
	}

	public String getSupplierOrderCd() {
		return supplierOrderCd;
	}

	public void setSupplierOrderCd(String supplierOrderCd) {
		this.supplierOrderCd = supplierOrderCd;
	}

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
