package com.hsh24.sync.api.trade;

import com.hsh24.sync.api.trade.bo.Trade;
import com.hsh24.sync.framework.bo.BooleanResult;

/**
 * 交易接口.
 * 
 * @author JiakunXu
 * 
 */
public interface ITradeService {

	String CREATE = "create";

	/**
	 * 临时.
	 */
	String CHECK = "check";

	/**
	 * 待付款.
	 */
	String TO_PAY = "topay";

	/**
	 * 已付款.
	 */
	String TO_SEND = "tosend";

	/**
	 * 已发货.
	 */
	String SEND = "send";

	/**
	 * 已签收.
	 */
	String SIGN = "sign";

	String FEEDBACK = "feedback";

	String FEEDBACKED = "feedbacked";

	/**
	 * 申请退款.
	 */
	String TO_REFUND = "torefund";

	/**
	 * 已退款.
	 */
	String REFUND = "refund";

	String CANCEL = "cancel";

	// >>>>>>>>>>以下是第三方交易平台<<<<<<<<<<

	/**
	 * 
	 * @param tradeNo
	 * @return
	 */
	Trade getTrade(String tradeNo);

	/**
	 * 
	 * @param tradeNo
	 * @param payType
	 * @param payDate
	 * @return
	 */
	BooleanResult payTrade(String tradeNo, String payType, String payDate);

}
