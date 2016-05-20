package com.hsh24.sync.oms.dao.impl;

import com.hsh24.sync.api.oms.bo.Trade;
import com.hsh24.sync.framework.dao.impl.BaseDaoImpl;
import com.hsh24.sync.oms.dao.ITradeDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class TradeDaoImpl extends BaseDaoImpl implements ITradeDao {

	@Override
	public Trade getTrade(Trade trade) {
		return (Trade) getSqlMapClientTemplate().queryForObject("oms.trade.getTrade", trade);
	}

	@Override
	public int cancelTrade(Trade trade) {
		return getSqlMapClientTemplate().update("oms.trade.cancelTrade", trade);
	}

	@Override
	public int sync2Send() {
		return getSqlMapClientTemplate().update("oms.trade.sync2Send");
	}

}
