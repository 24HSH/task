package com.hsh24.sync.oms.service.impl;

import org.apache.commons.lang3.StringUtils;

import com.hsh24.sync.api.oms.IBankAcctService;
import com.hsh24.sync.api.oms.bo.BankAcct;
import com.hsh24.sync.framework.bo.BooleanResult;
import com.hsh24.sync.framework.log.Logger4jCollection;
import com.hsh24.sync.framework.log.Logger4jExtend;
import com.hsh24.sync.framework.util.LogUtil;
import com.hsh24.sync.oms.dao.IBankAcctDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class BankAcctServiceImpl implements IBankAcctService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(BankAcctServiceImpl.class);

	private IBankAcctDao bankAcctDao;

	@Override
	public BooleanResult updateBankAcct(Long tradeId, String modifyUser) {
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

		BankAcct bankAcct = new BankAcct();
		bankAcct.setTradeId(tradeId);
		bankAcct.setModifyUser(modifyUser);

		try {
			int c = bankAcctDao.updateBankAcct(bankAcct);
			if (c == 1) {
				result.setResult(true);
			} else {
				result.setCode("更新账户信息失败。");
			}
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(bankAcct), e);
			result.setCode("更新账户信息表失败。");
		}

		return result;
	}

	public IBankAcctDao getBankAcctDao() {
		return bankAcctDao;
	}

	public void setBankAcctDao(IBankAcctDao bankAcctDao) {
		this.bankAcctDao = bankAcctDao;
	}

}
