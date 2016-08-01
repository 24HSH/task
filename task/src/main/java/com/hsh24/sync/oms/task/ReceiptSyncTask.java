package com.hsh24.sync.oms.task;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hsh24.sync.api.oms.IReceiptSyncService;

/**
 * 
 * @author JiakunXu
 * 
 */
@Component
public class ReceiptSyncTask {

	@Resource
	private IReceiptSyncService receiptSyncService;

	public void sync() {
		receiptSyncService.sync();
	}

}
