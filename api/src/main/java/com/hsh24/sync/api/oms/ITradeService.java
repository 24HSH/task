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
	 * 根据 trade_log 取消订单成功.
	 * 
	 * @param id
	 * @param modifyUser
	 * @return
	 */
	BooleanResult cancelTrade(Long id, String modifyUser);

	/**
	 * 
	 * @return
	 */
	BooleanResult sync2Send();

}
