package com.hsh24.sync.oms.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.hsh24.sync.api.oms.IBankAcctService;
import com.hsh24.sync.api.oms.ICashflowService;
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
@Service("omsTradeService")
public class TradeServiceImpl implements ITradeService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(TradeServiceImpl.class);

	@Resource
	private TransactionTemplate transactionTemplate;

	@Resource
	private ICashflowService cashflowService;

	@Resource
	private IBankAcctService bankAcctService;

	@Resource(name = "omsTradeDao")
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
	public BooleanResult cancelTrade(final Long tradeId, final String modifyUser) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (tradeId == null) {
			result.setCode("交易信息不能为空");
			return result;
		}

		if (StringUtils.isEmpty(modifyUser)) {
			result.setCode("操作人信息不能为空");
			return result;
		}

		BooleanResult res = transactionTemplate.execute(new TransactionCallback<BooleanResult>() {
			public BooleanResult doInTransaction(TransactionStatus ts) {
				BooleanResult result = new BooleanResult();
				result.setResult(false);

				Trade trade = new Trade();
				trade.setTradeId(tradeId);
				trade.setModifyUser(modifyUser);

				try {
					int c = tradeDao.cancelTrade(trade);
					if (c != 1) {
						ts.setRollbackOnly();

						result.setCode("更新交易失败");
						return result;
					}
				} catch (Exception e) {
					logger.error(LogUtil.parserBean(trade), e);
					ts.setRollbackOnly();

					result.setCode("更新交易表失败");
					return result;
				}

				result = cashflowService.createCashflow(tradeId, modifyUser);
				if (!result.getResult()) {
					ts.setRollbackOnly();

					return result;
				}

				result = bankAcctService.updateBankAcct(tradeId, modifyUser);
				if (!result.getResult()) {
					ts.setRollbackOnly();

					return result;
				}

				return result;
			}
		});

		return res;
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
			result.setCode("同步交易表失败");
		}

		return result;
	}

}
