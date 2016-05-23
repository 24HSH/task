package com.hsh24.sync.api.oms;

import com.hsh24.sync.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface ICashflowService {

	/**
	 * 
	 * @param tradeId
	 * @param modifyUser
	 * @return
	 */
	BooleanResult createCashflow(Long tradeId, String modifyUser);

}
