package com.wideka.sync.api.wizarpos.bo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @author JiakunXu
 * 
 */
public class OrderDetail implements Serializable {

	private static final long serialVersionUID = -5949473713198941714L;

	private String productId;

	private BigDecimal amount;

	private BigDecimal price;

	private int qty;

	private String remark;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
