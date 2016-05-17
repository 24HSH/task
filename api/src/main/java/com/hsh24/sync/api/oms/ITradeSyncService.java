package com.hsh24.sync.api.oms;

public interface ITradeSyncService {

	/**
	 * 同步交易订单.
	 */
	void sync();

	/**
	 * 同步交易订单发货状态.
	 */
	void sync2Send();

}
