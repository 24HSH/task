package com.hsh24.sync.oms.dao;

import com.hsh24.sync.api.oms.bo.Cashflow;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface ICashflowDao {

	/**
	 * 
	 * @param map
	 * @return
	 */
	Long createCashflow(Cashflow cashflow);

}
