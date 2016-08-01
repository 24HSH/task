package com.hsh24.sync.pay.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.hsh24.sync.api.cache.IMemcachedCacheService;
import com.hsh24.sync.api.pay.IPayService;
import com.hsh24.sync.api.trade.ITradeService;
import com.hsh24.sync.api.trade.bo.Trade;
import com.hsh24.sync.api.wxpay.IWxpayService;
import com.hsh24.sync.framework.bo.BooleanResult;
import com.hsh24.sync.framework.exception.ServiceException;
import com.wideka.weixin.api.pay.bo.WxNotify;
import com.wideka.weixin.framework.util.XmlUtil;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class PayServiceImpl implements IPayService {

	@Resource
	private TransactionTemplate transactionTemplate;

	@Resource
	private IMemcachedCacheService memcachedCacheService;

	@Resource
	private IWxpayService wxpayService;

	@Resource
	private ITradeService tradeService;

	// >>>>>>>>>>以下是第三方交易平台<<<<<<<<<<

	@Override
	public BooleanResult notify(String wxNotify) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);
		result.setCode(IWxpayService.RETURN_CODE_FAIL);

		if (StringUtils.isBlank(wxNotify)) {
			return result;
		}

		final WxNotify notify = (WxNotify) XmlUtil.parse(wxNotify, new WxNotify());

		// 1. 判断回调信息
		BooleanResult res = wxpayService.validateWxNotify(notify);
		if (!res.getResult()) {
			return result;
		}

		// 锁定订单
		String key = notify.getOutTradeNo();

		try {
			memcachedCacheService.add(IMemcachedCacheService.CACHE_KEY_TRADE_NO + key, key,
				IMemcachedCacheService.CACHE_KEY_TRADE_NO_DEFAULT_EXP);
		} catch (ServiceException e) {
			return result;
		}

		// 2. 判断订单状态
		final Trade trade = tradeService.getTrade(notify.getOutTradeNo());

		if (trade == null) {
			return result;
		}

		// 已付款.
		if (!ITradeService.TO_PAY.equals(trade.getType())) {
			result.setResult(true);
			result.setCode(IWxpayService.RETURN_CODE_SUCCESS);
			return result;
		}

		res = transactionTemplate.execute(new TransactionCallback<BooleanResult>() {
			public BooleanResult doInTransaction(TransactionStatus ts) {
				// 3. 修改交易状态 topay -> tosend.
				BooleanResult result =
					tradeService.payTrade(trade.getTradeNo(), IPayService.PAY_TYPE_WXPAY, notify.getTimeEnd());
				if (!result.getResult()) {
					ts.setRollbackOnly();

					return result;
				}

				// 4. 记录回调信息.
				result = wxpayService.createWxNotify(notify);
				if (!result.getResult()) {
					ts.setRollbackOnly();

					return result;
				}

				return result;
			}
		});

		if (res.getResult()) {
			result.setResult(true);
			result.setCode(IWxpayService.RETURN_CODE_SUCCESS);
		}

		return result;
	}

}
