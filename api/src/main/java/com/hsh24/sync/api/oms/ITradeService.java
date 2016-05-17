package com.hsh24.sync.api.oms;

import com.hsh24.sync.api.oms.bo.Trade;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface ITradeService {

	/**
	 * 
	 * @param tradeId
	 * @return
	 */
	Trade getTrade(Long tradeId);

}
