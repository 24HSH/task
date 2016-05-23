package com.hsh24.sync.api.oms.bo;

/**
 * 
 * @author JiakunXu
 * 
 */
public class Cashflow {

	private Long cashflowId;

	private Long tradeId;

	private String modifyUser;

	public Long getCashflowId() {
		return cashflowId;
	}

	public void setCashflowId(Long cashflowId) {
		this.cashflowId = cashflowId;
	}

	public Long getTradeId() {
		return tradeId;
	}

	public void setTradeId(Long tradeId) {
		this.tradeId = tradeId;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

}
