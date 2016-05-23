package com.hsh24.sync.api.oms;

import com.hsh24.sync.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IBankAcctService {

	/**
	 * 
	 * @param tradeId
	 * @param modifyUser
	 * @return
	 */
	BooleanResult updateBankAcct(Long tradeId, String modifyUser);

}
