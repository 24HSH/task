package com.hsh24.sync.oms.service.impl;

import java.util.List;

import com.hsh24.sync.api.oms.IOrderService;
import com.hsh24.sync.api.oms.ITradeLogService;
import com.hsh24.sync.api.oms.ITradeService;
import com.hsh24.sync.api.oms.ITradeSyncService;
import com.hsh24.sync.api.oms.bo.Order;
import com.hsh24.sync.api.oms.bo.Trade;
import com.hsh24.sync.api.oms.bo.TradeLog;

/**
 * 
 * @author JiakunXu
 * 
 */
public class TradeSyncServiceImpl implements ITradeSyncService {

	private ITradeLogService tradeLogService;

	private ITradeService tradeService;

	private IOrderService orderService;

	@Override
	public void sync() {
		List<TradeLog> tradeLogList = tradeLogService.getTradeLogList();

		if (tradeLogList != null && tradeLogList.size() > 0) {
			for (TradeLog tradeLog : tradeLogList) {
				if (!"tosend".equals(tradeLog.getType()) && !"cancel".equals(tradeLog.getType())) {
					continue;
				}

				Long tradeId = tradeLog.getTradeId();

				Trade trade = tradeService.getTrade(tradeId);
				if (trade == null) {
					continue;
				}

				List<Order> orderList = orderService.getOrderList(tradeId);
				if (orderList == null || orderList.size() == 0) {
					continue;
				}

				trade.setOrderList(orderList);

			}
		}
	}

	@Override
	public void syncTrade2Send() {
		// TODO Auto-generated method stub

	}

	public ITradeLogService getTradeLogService() {
		return tradeLogService;
	}

	public void setTradeLogService(ITradeLogService tradeLogService) {
		this.tradeLogService = tradeLogService;
	}

	public ITradeService getTradeService() {
		return tradeService;
	}

	public void setTradeService(ITradeService tradeService) {
		this.tradeService = tradeService;
	}

	public IOrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(IOrderService orderService) {
		this.orderService = orderService;
	}

}
