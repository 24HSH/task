package com.hsh24.sync.trade.service.impl;

import org.apache.commons.lang.StringUtils;

import com.hsh24.sync.api.trade.ITradeService;
import com.hsh24.sync.api.trade.bo.Trade;
import com.hsh24.sync.framework.bo.BooleanResult;
import com.hsh24.sync.framework.log.Logger4jCollection;
import com.hsh24.sync.framework.log.Logger4jExtend;
import com.hsh24.sync.framework.util.LogUtil;
import com.hsh24.sync.trade.dao.ITradeDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class TradeServiceImpl implements ITradeService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(TradeServiceImpl.class);

	private ITradeDao tradeDao;

	// >>>>>>>>>>以下是第三方交易平台<<<<<<<<<<

	@Override
	public Trade getTrade(String tradeNo) {
		if (StringUtils.isBlank(tradeNo)) {
			return null;
		}

		Trade trade = new Trade();
		trade.setTradeNo(tradeNo.trim());

		return getTrade(trade);
	}

	@Override
	public BooleanResult payTrade(String tradeNo, String payType, String payDate) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		Trade trade = new Trade();
		// 待发货
		trade.setType(ITradeService.TO_SEND);

		if (StringUtils.isBlank(tradeNo)) {
			result.setCode("交易订单不能为空");
			return result;
		}
		trade.setTradeNo(tradeNo.trim());

		if (StringUtils.isBlank(payType)) {
			result.setCode("支付类型不能为空");
			return result;
		}
		trade.setPayType(payType.trim());

		if (StringUtils.isBlank(payDate)) {
			result.setCode("支付时间不能为空");
			return result;
		}
		trade.setPayDate(payDate);

		trade.setModifyUser(payType);

		return updateTrade(trade);
	}

	/**
	 * 
	 * @param trade
	 * @return
	 */
	private Trade getTrade(Trade trade) {
		try {
			return tradeDao.getTrade(trade);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(trade), e);
		}

		return null;
	}

	private BooleanResult updateTrade(Trade trade) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		try {
			int c = tradeDao.updateTrade(trade);
			if (c == 1) {
				result.setResult(true);
			} else {
				result.setCode("更新交易失败");
			}
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(trade), e);
			result.setCode("更新交易表失败");
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
