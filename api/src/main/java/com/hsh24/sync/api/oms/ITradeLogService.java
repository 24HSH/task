package com.hsh24.sync.api.oms;

import java.util.List;

import com.hsh24.sync.api.oms.bo.TradeLog;
import com.hsh24.sync.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface ITradeLogService {

	/**
	 * 获取 tradeLog 数据 (10条).
	 * 
	 * @return
	 */
	List<TradeLog> getTradeLogList();

	/**
	 * 完成 tradeLog 数据处理.
	 * 
	 * @param id
	 * @param modifyUser
	 * @return
	 */
	BooleanResult finishTradeLog(Long id, String modifyUser);

	/**
	 * 记录
	 * @param id
	 * @param error
	 * @param modifyUser
	 * @return
	 */
	BooleanResult recordTradeLog(Long id, String error, String modifyUser);

}
