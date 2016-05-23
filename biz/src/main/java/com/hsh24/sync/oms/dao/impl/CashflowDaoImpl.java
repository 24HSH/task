package com.hsh24.sync.oms.dao.impl;

import com.hsh24.sync.api.oms.bo.Cashflow;
import com.hsh24.sync.framework.dao.impl.BaseDaoImpl;
import com.hsh24.sync.oms.dao.ICashflowDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class CashflowDaoImpl extends BaseDaoImpl implements ICashflowDao {

	@Override
	public Long createCashflow(Cashflow cashflow) {
		return (Long) getSqlMapClientTemplate().insert("oms.cashflow.createCashflow", cashflow);
	}

}
