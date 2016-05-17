package com.hsh24.sync.oms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.hsh24.sync.api.oms.IOrderService;
import com.hsh24.sync.api.oms.ITradeLogService;
import com.hsh24.sync.api.oms.ITradeService;
import com.hsh24.sync.api.oms.ITradeSyncService;
import com.hsh24.sync.api.oms.bo.Order;
import com.hsh24.sync.api.oms.bo.Trade;
import com.hsh24.sync.api.oms.bo.TradeLog;
import com.hsh24.sync.framework.bo.BooleanResult;
import com.wideka.weixin.framework.util.HttpUtil;

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
	public int sync() {
		List<TradeLog> tradeLogList = tradeLogService.getTradeLogList();

		if (tradeLogList == null || tradeLogList.size() == 0) {
			return 0;

		}

		int count = 0;

		for (TradeLog tradeLog : tradeLogList) {
			if (!"tosend".equals(tradeLog.getType()) && !"cancel".equals(tradeLog.getType())) {
				continue;
			}

			Long id = tradeLog.getId();
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

			if ("tosend".equals(tradeLog.getType())) {
				trade.setActionType("add");

				Map<String, String> map = new HashMap<String, String>();
				try {
					map.put("purchaseOrder", JSON.toJSONString(trade));
					System.out.println(HttpUtil.post(
						"http://192.168.1.40:8080/ec-erp/interPurOrderAction.do/method=POST", map));
				} catch (Exception e) {
					e.printStackTrace();
				}

				BooleanResult result = tradeLogService.finishTradeLog(id, "sys");
				if (result.getResult()) {
					count++;
				}
			} else if ("cancel".equals(tradeLog.getType())) {
				Map<String, String> map = new HashMap<String, String>();
				try {
					map.put("purchaseOrder", "{\"actionType\":\"delete\",\"purOrderCds\":[{\"interPurchaseCd\":\""
						+ trade.getTradeNo() + "\"}]}");
					System.out.println(HttpUtil.post(
						"http://192.168.1.40:8080/ec-erp/interPurOrderAction.do/method=POST", map));
				} catch (Exception e) {
					e.printStackTrace();
				}

				BooleanResult result = tradeLogService.finishTradeLog(id, "sys");
				if (result.getResult()) {
					count++;
				}
			}
		}

		return count;
	}

	@Override
	public int sync2Send() {
		BooleanResult result = tradeService.sync2Send();
		if (result.getResult()) {
			return Integer.parseInt(result.getCode());
		}

		return 0;
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
