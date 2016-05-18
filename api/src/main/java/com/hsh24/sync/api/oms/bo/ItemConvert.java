package com.hsh24.sync.api.oms.bo;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ItemConvert {

	private String barCode;

	private Long itemId;

	private int quantity;

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
