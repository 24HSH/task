package com.hsh24.sync.api.oms.bo;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ReceiptLog {

	private Long id;

	private Long receiptId;

	private String type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(Long receiptId) {
		this.receiptId = receiptId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
