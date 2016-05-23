package com.hsh24.sync.oms.dao;

import com.hsh24.sync.api.oms.bo.BankAcct;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IBankAcctDao {

	/**
	 * 
	 * @param map
	 * @return
	 */
	int updateBankAcct(BankAcct bankAcct);

}
