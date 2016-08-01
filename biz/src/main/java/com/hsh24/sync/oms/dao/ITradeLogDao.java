package com.hsh24.sync.oms.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hsh24.sync.api.oms.bo.TradeLog;

/**
 * 
 * @author JiakunXu
 * 
 */
@Repository("omsTradeLogDao")
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

	/**
	 * 
	 * @param tradeLog
	 * @return
	 */
	int recordTradeLog(TradeLog tradeLog);

}
