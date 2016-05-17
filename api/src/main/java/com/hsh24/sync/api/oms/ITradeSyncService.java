package com.hsh24.sync.api.oms;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface ITradeSyncService {

	/**
	 * 同步交易订单.
	 */
	int sync();

	/**
	 * 同步交易订单发货状态.
	 */
	int sync2Send();

}
