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
	 * @param updateTradeLog
	 * @return
	 */
	BooleanResult finishTradeLog(Long id, String modifyUser);

}
