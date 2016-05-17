package com.hsh24.sync.oms.dao;

import java.util.List;

import com.hsh24.sync.api.oms.bo.ReceiptLog;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IReceiptLogDao {

	/**
	 * 分页.
	 * 
	 * @return
	 */
	List<ReceiptLog> getReceiptLogList();

	/**
	 * 
	 * @param receiptLog
	 * @return
	 */
	int finishReceiptLog(ReceiptLog receiptLog);

}
