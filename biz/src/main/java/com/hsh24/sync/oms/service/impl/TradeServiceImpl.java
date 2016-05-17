package com.hsh24.sync.oms.service.impl;

import com.hsh24.sync.api.oms.ITradeService;
import com.hsh24.sync.api.oms.bo.Trade;
import com.hsh24.sync.framework.bo.BooleanResult;
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

	@Override
	public BooleanResult sync2Send() {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		try {
			int c = tradeDao.sync2Send();

			result.setCode(String.valueOf(c));
			result.setResult(true);
		} catch (Exception e) {
			logger.error(e);
			result.setCode("同步交易表失败。");
		}

		return result;
	}

	public ITradeDao getTradeDao() {
		return tradeDao;
	}

	public void setTradeDao(ITradeDao tradeDao) {
		this.tradeDao = tradeDao;
	}

}
