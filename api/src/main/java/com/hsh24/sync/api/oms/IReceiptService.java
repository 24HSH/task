package com.hsh24.sync.api.oms;

import java.util.List;

import com.hsh24.sync.api.oms.bo.Receipt;
import com.hsh24.sync.api.oms.bo.ReceiptDetail;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IReceiptService {

	/**
	 * 
	 * @param receiptId
	 * @return
	 */
	Receipt getReceipt(Long receiptId);

	/**
	 * 
	 * @param receiptId
	 * @return
	 */
	List<ReceiptDetail> getReceiptDetailList(Long receiptId);

	/**
	 * 
	 * @param shopId
	 * @return
	 */
	String getWarehouse(Long shopId);

}
