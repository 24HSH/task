package com.hsh24.sync.oms.service.impl;

import org.apache.commons.lang3.StringUtils;

import com.hsh24.sync.api.oms.ICashflowService;
import com.hsh24.sync.api.oms.bo.Cashflow;
import com.hsh24.sync.framework.bo.BooleanResult;
import com.hsh24.sync.framework.log.Logger4jCollection;
import com.hsh24.sync.framework.log.Logger4jExtend;
import com.hsh24.sync.oms.dao.ICashflowDao;
import com.wideka.weixin.framework.util.LogUtil;

/**
 * 
 * @author JiakunXu
 * 
 */
public class CashflowServiceImpl implements ICashflowService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(CashflowServiceImpl.class);

	private ICashflowDao cashflowDao;

	@Override
	public BooleanResult createCashflow(Long tradeId, String modifyUser) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (tradeId == null) {
			result.setCode("交易信息不能为空。");
			return result;
		}

		if (StringUtils.isEmpty(modifyUser)) {
			result.setCode("操作人信息不能为空。");
			return result;
		}

		Cashflow cashflow = new Cashflow();
		cashflow.setTradeId(tradeId);
		cashflow.setModifyUser(modifyUser);

		try {
			cashflowDao.createCashflow(cashflow);
			result.setResult(true);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(cashflow), e);

			result.setCode("添加资金流水信息失败，请稍后再试。");
		}

		return result;
	}

	public ICashflowDao getCashflowDao() {
		return cashflowDao;
	}

	public void setCashflowDao(ICashflowDao cashflowDao) {
		this.cashflowDao = cashflowDao;
	}

}
