package com.hsh24.sync.trade.dao;

import com.hsh24.sync.api.trade.bo.Trade;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface ITradeDao {

	/**
	 * 
	 * @param trade
	 * @return
	 */
	Trade getTrade(Trade trade);

	/**
	 * 
	 * @param trade
	 * @return
	 */
	int updateTrade(Trade trade);

}
