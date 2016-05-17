package com.hsh24.sync.api.oms;

import java.util.List;

import com.hsh24.sync.api.oms.bo.Order;
import com.hsh24.sync.api.oms.bo.Trade;
import com.hsh24.sync.framework.bo.BooleanResult;

public interface ITradeToSendSyncService {
	
	/**
	 * 获得trade 表t osend数据
	 * @param trade
	 * @return
	 */
	List<Trade> getToSendList(Trade trade);
	
	/**
	 * 获得trade tosend  订单明细orderlist
	 * @param Order
	 * @return
	 */
	List<Order> getToSendDetailList(Order order);
	
	
	/**
	 * 更新中间表tradelog.
	 * 
	 * @param updateTradeLog
	 * @return
	 */
	BooleanResult updateTradeLog(Trade trade);
	
	
	/**
	 * 获取 tradelog表 tosend  U数据
	 * 
	 * @param getToSendInTradeLog
	 * @return
	 */
	List<Trade> getToSendInTradeLog(Trade trade);
}
