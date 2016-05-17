package com.hsh24.sync.api.oms;

import com.hsh24.sync.api.oms.bo.Trade;
import com.hsh24.sync.framework.bo.BooleanResult;

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

	/**
	 * 
	 * @return
	 */
	BooleanResult sync2Send();

}
