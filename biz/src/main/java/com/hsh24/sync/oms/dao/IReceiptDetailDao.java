package com.hsh24.sync.oms.dao;

import java.util.List;

import com.hsh24.sync.api.oms.bo.ReceiptDetail;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IReceiptDetailDao {

	/**
	 * 
	 * @param receiptDetail
	 * @return
	 */
	List<ReceiptDetail> getReceiptDetailList(ReceiptDetail receiptDetail);

}
