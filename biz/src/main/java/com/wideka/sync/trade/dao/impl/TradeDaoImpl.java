package com.wideka.sync.trade.dao.impl;

import com.wideka.sync.api.trade.bo.Trade;
import com.wideka.sync.framework.dao.impl.BaseDaoImpl;
import com.wideka.sync.trade.dao.ITradeDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class TradeDaoImpl extends BaseDaoImpl implements ITradeDao {

	@Override
	public Trade getTrade(Trade trade) {
		return (Trade) getSqlMapClientTemplate().queryForObject("trade.getTrade", trade);
	}

	@Override
	public int updateTrade(Trade trade) {
		return getSqlMapClientTemplate().update("trade.updateTrade", trade);
	}

}
