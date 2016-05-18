package com.hsh24.sync.oms.dao;

import com.hsh24.sync.api.oms.bo.Receipt;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IReceiptDao {

	/**
	 * 
	 * @param receipt
	 * @return
	 */
	Receipt getReceipt(Receipt receipt);

	/**
	 * 
	 * @param shopId
	 * @return
	 */
	String getWarehouse(Long shopId);

}
