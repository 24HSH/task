package com.hsh24.sync.oms.dao;

import org.springframework.stereotype.Repository;

import com.hsh24.sync.api.oms.bo.Trade;

/**
 * 
 * @author JiakunXu
 * 
 */
@Repository("omsTradeDao")
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
	int cancelTrade(Trade trade);

	/**
	 * 
	 * @return
	 */
	int sync2Send();

}
