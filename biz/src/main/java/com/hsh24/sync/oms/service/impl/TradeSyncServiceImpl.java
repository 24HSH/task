package com.hsh24.sync.oms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSON;
import com.hsh24.sync.api.oms.IOrderService;
import com.hsh24.sync.api.oms.IShopService;
import com.hsh24.sync.api.oms.ITradeLogService;
import com.hsh24.sync.api.oms.ITradeService;
import com.hsh24.sync.api.oms.ITradeSyncService;
import com.hsh24.sync.api.oms.bo.Order;
import com.hsh24.sync.api.oms.bo.Result;
import com.hsh24.sync.api.oms.bo.Shop;
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
@Service("omsTradeSyncService")
public class TradeSyncServiceImpl implements ITradeSyncService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(TradeSyncServiceImpl.class);

	@Resource
	private TransactionTemplate transactionTemplate;

	@Resource(name = "omsTradeLogService")
	private ITradeLogService tradeLogService;

	@Resource(name = "omsTradeService")
	private ITradeService tradeService;

	@Resource(name = "omsOrderService")
	private IOrderService orderService;

	@Resource
	private IShopService shopService;

	@Value("${oms.trade.url}")
	private String url;

	@Override
	public int sync() {
		List<TradeLog> tradeLogList = tradeLogService.getTradeLogList();

		if (tradeLogList == null || tradeLogList.size() == 0) {
			return 0;

		}

		int count = 0;

		for (TradeLog tradeLog : tradeLogList) {
			if (!"tosend".equals(tradeLog.getType()) && !"tocancel".equals(tradeLog.getType())) {
				continue;
			}

			final Long id = tradeLog.getId();
			final Long tradeId = tradeLog.getTradeId();

			final Trade trade = tradeService.getTrade(tradeId);
			if (trade == null) {
				continue;
			}

			Long shopId = trade.getShopId();

			Shop shop = shopService.getShop(shopId);
			if (shop == null) {
				continue;
			}

			// 发货信息
			trade.setRemark(shop.getShopName() + "|" + trade.getCreateUser() + "|" + shop.getAddress());

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

						result.setResult(false);

						try {
							Map<String, String> map = new HashMap<String, String>();
							map.put("purchaseOrder", JSON.toJSONString(trade));

							String code = HttpUtil.post(url, map);
							if (StringUtils.isBlank(code)) {
								ts.setRollbackOnly();

								return result;
							}

							Result res = JSON.parseObject(code, Result.class);

							if (!"success".equals(res.getInfo())) {
								ts.setRollbackOnly();

								return result;
							}
						} catch (Exception e) {
							logger.error(e);

							ts.setRollbackOnly();

							result.setCode(e.getMessage());
							return result;
						}

						result.setResult(true);
						return result;
					}
				});

				if (result.getResult()) {
					count++;
				} else {
					record(id, result.getCode());
				}
			} else if ("tocancel".equals(tradeLog.getType())) {
				BooleanResult result = transactionTemplate.execute(new TransactionCallback<BooleanResult>() {
					public BooleanResult doInTransaction(TransactionStatus ts) {
						BooleanResult result = tradeLogService.finishTradeLog(id, "sys");
						if (!result.getResult()) {
							ts.setRollbackOnly();

							return result;
						}

						result.setResult(false);

						try {
							Map<String, String> map = new HashMap<String, String>();
							map.put(
								"purchaseOrder",
								"{\"actionType\":\"delete\",\"purOrderCds\":[{\"interPurchaseCd\":\""
									+ trade.getTradeNo() + "\"}]}");

							String code = HttpUtil.post(url, map);
							if (StringUtils.isBlank(code)) {
								ts.setRollbackOnly();

								result.setCode("返回空.");
								return result;
							}

							Result res = JSON.parseObject(code, Result.class);

							if (!"success".equals(res.getInfo())) {
								ts.setRollbackOnly();

								result.setCode(code);
								return result;
							}
						} catch (Exception e) {
							logger.error(e);

							ts.setRollbackOnly();

							result.setCode(e.getMessage());
							return result;
						}

						// 1. 取消成功
						result = tradeService.cancelTrade(tradeId, "sys");
						if (!result.getResult()) {
							ts.setRollbackOnly();

							return result;
						}

						return result;
					}
				});

				if (result.getResult()) {
					count++;
				} else {
					record(id, result.getCode());
				}
			}
		}

		return count;
	}

	private void record(Long id, String e) {
		tradeLogService.recordTradeLog(id, e, "sys");
	}

	@Override
	public int sync2Send() {
		BooleanResult result = tradeService.sync2Send();
		if (result.getResult()) {
			return Integer.parseInt(result.getCode());
		}

		return 0;
	}

}
