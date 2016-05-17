package com.hsh24.sync.oms.task;

import com.hsh24.sync.api.oms.ITradeSyncService;

/**
 * 
 * @author JiakunXu
 * 
 */
public class TradeSyncTask {

	private ITradeSyncService tradeSyncService;

	public void sync() {
		tradeSyncService.sync();
	}

	public void sync2Send() {
		tradeSyncService.sync2Send();
	}

	public ITradeSyncService getTradeSyncService() {
		return tradeSyncService;
	}

	public void setTradeSyncService(ITradeSyncService tradeSyncService) {
		this.tradeSyncService = tradeSyncService;
	}

}
