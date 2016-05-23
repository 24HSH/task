package com.hsh24.sync.oms.dao.impl;

import com.hsh24.sync.api.oms.bo.BankAcct;
import com.hsh24.sync.framework.dao.impl.BaseDaoImpl;
import com.hsh24.sync.oms.dao.IBankAcctDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class BankAcctDaoImpl extends BaseDaoImpl implements IBankAcctDao {

	@Override
	public int updateBankAcct(BankAcct bankAcct) {
		return getSqlMapClientTemplate().update("oms.bankAcct.updateBankAcct", bankAcct);
	}

}
