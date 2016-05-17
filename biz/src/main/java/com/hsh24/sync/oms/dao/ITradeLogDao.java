package com.hsh24.sync.oms.dao;

import java.util.List;

import com.hsh24.sync.api.oms.bo.TradeLog;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface ITradeLogDao {

	/**
	 * 分页.
	 * 
	 * @return
	 */
	List<TradeLog> getTradeLogList();

	/**
	 * 
	 * @param tradeLog
	 * @return
	 */
	int finishTradeLog(TradeLog tradeLog);

}
