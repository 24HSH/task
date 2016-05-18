package com.hsh24.sync.oms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSON;
import com.hsh24.sync.api.oms.IOrderService;
import com.hsh24.sync.api.oms.ITradeLogService;
import com.hsh24.sync.api.oms.ITradeService;
import com.hsh24.sync.api.oms.ITradeSyncService;
import com.hsh24.sync.api.oms.bo.Order;
import com.hsh24.sync.api.oms.bo.Trade;
import com.hsh24.sync.api.oms.bo.TradeLog;
import com.hsh24.sync.framework.bo.BooleanResult;
import com.hsh24.sync.framework.log.Logger4jCollection;
import com.hsh24.sync.framework.log.Logger4jExtend;
import com.hsh24.sync.framework.util.HttpUtil;

/**
 * 
 * @author JiakunXu
 * 
 */
public class TradeSyncServiceImpl implements ITradeSyncService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(TradeSyncServiceImpl.class);

	private TransactionTemplate transactionTemplate;

	private ITradeLogService tradeLogService;

	private ITradeService tradeService;

	private IOrderService orderService;

	private String url;

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

			final Long id = tradeLog.getId();
			Long tradeId = tradeLog.getTradeId();

			final Trade trade = tradeService.getTrade(tradeId);
			if (trade == null) {
				continue;
			}

			Long shopId = trade.getShopId();

			List<Order> orderList = orderService.getOrderList(tradeId);
			if (orderList == null || orderList.size() == 0) {
				continue;
			}

			for (Order order : orderList) {
				order.setShopId(shopId);
			}

			trade.setOrderList(orderList);

			if ("tosend".equals(tradeLog.getType())) {
				trade.setActionType("add");

				BooleanResult result = transactionTemplate.execute(new TransactionCallback<BooleanResult>() {
					public BooleanResult doInTransaction(TransactionStatus ts) {
						BooleanResult result = tradeLogService.finishTradeLog(id, "sys");
						if (!result.getResult()) {
							ts.setRollbackOnly();

							return result;
						}

						try {
							Map<String, String> map = new HashMap<String, String>();
							map.put("purchaseOrder", JSON.toJSONString(trade));

							String code = HttpUtil.post(url, map);
							if (!"s".equals(code)) {
								ts.setRollbackOnly();

								return result;
							}
						} catch (Exception e) {
							logger.error(e);

							ts.setRollbackOnly();

							result.setCode(e.getMessage());
							result.setResult(false);
							return result;
						}

						return result;
					}
				});

				if (result.getResult()) {
					count++;
				}
			} else if ("cancel".equals(tradeLog.getType())) {
				BooleanResult result = transactionTemplate.execute(new TransactionCallback<BooleanResult>() {
					public BooleanResult doInTransaction(TransactionStatus ts) {
						BooleanResult result = tradeLogService.finishTradeLog(id, "sys");
						if (!result.getResult()) {
							ts.setRollbackOnly();

							return result;
						}

						try {
							Map<String, String> map = new HashMap<String, String>();
							map.put(
								"purchaseOrder",
								"{\"actionType\":\"delete\",\"purOrderCds\":[{\"interPurchaseCd\":\""
									+ trade.getTradeNo() + "\"}]}");

							String code = HttpUtil.post(url, map);
							if (!"s".equals(code)) {
								ts.setRollbackOnly();

								return result;
							}
						} catch (Exception e) {
							logger.error(e);

							ts.setRollbackOnly();

							result.setCode(e.getMessage());
							result.setResult(false);
							return result;
						}

						return result;
					}
				});

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

	public TransactionTemplate getTransactionTemplate() {
		return transactionTemplate;
	}

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
