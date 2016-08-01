package com.hsh24.sync.oms.task;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hsh24.sync.api.oms.ITradeSyncService;

/**
 * 
 * @author JiakunXu
 * 
 */
@Component
public class TradeSyncTask {

	@Resource(name = "omsTradeSyncService")
	private ITradeSyncService tradeSyncService;

	public void sync() {
		tradeSyncService.sync();
	}

	public void sync2Send() {
		tradeSyncService.sync2Send();
	}

}
