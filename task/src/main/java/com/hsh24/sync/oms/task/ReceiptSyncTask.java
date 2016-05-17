package com.hsh24.sync.oms.task;

import com.hsh24.sync.api.oms.IReceiptSyncService;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ReceiptSyncTask {

	private IReceiptSyncService receiptSyncService;

	public void sync() {
		receiptSyncService.sync();
	}

	public IReceiptSyncService getReceiptSyncService() {
		return receiptSyncService;
	}

	public void setReceiptSyncService(IReceiptSyncService receiptSyncService) {
		this.receiptSyncService = receiptSyncService;
	}

}
