package com.hsh24.sync.api.oms;

import java.util.List;

import com.hsh24.sync.api.oms.bo.ReceiptLog;
import com.hsh24.sync.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IReceiptLogService {

	/**
	 * 
	 * @return
	 */
	List<ReceiptLog> getReceiptLogList();

	/**
	 * 
	 * @param id
	 * @param modifyUser
	 * @return
	 */
	BooleanResult finishReceiptLog(Long id, String modifyUser);

	/**
	 * 
	 * @param id
	 * @param error
	 * @param modifyUser
	 * @return
	 */
	BooleanResult recordReceiptLog(Long id, String error, String modifyUser);

}
