package com.hsh24.sync.oms.service.impl;

import com.hsh24.sync.api.oms.ITradeService;
import com.hsh24.sync.api.oms.bo.Trade;
import com.hsh24.sync.framework.log.Logger4jCollection;
import com.hsh24.sync.framework.log.Logger4jExtend;
import com.hsh24.sync.framework.util.LogUtil;
import com.hsh24.sync.oms.dao.ITradeDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class TradeServiceImpl implements ITradeService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(TradeServiceImpl.class);

	private ITradeDao tradeDao;

	@Override
	public Trade getTrade(Long tradeId) {
		if (tradeId == null) {
			return null;
		}

		Trade trade = new Trade();
		trade.setTradeId(tradeId);

		try {
			return tradeDao.getTrade(trade);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(trade), e);
		}

		return null;
	}

	public ITradeDao getTradeDao() {
		return tradeDao;
	}

	public void setTradeDao(ITradeDao tradeDao) {
		this.tradeDao = tradeDao;
	}

}
