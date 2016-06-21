package com.hsh24.sync.api.wxpay;

import com.hsh24.sync.framework.bo.BooleanResult;
import com.hsh24.sync.framework.exception.ServiceException;
import com.wideka.weixin.api.pay.bo.Refund;
import com.wideka.weixin.api.pay.bo.WxNotify;

/**
 * 微信支付.
 * 
 * @author JiakunXu
 * 
 */
public interface IWxpayService {

	String ERROR_MESSAGE = "微信支付接口调用失败";

	String RETURN_CODE_SUCCESS = "<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>";

	String RETURN_CODE_FAIL = "<xml><return_code><![CDATA[FAIL]]></return_code></xml>";

	/**
	 * 支付通知.
	 * 
	 * @param wxNotify
	 * @return
	 */
	BooleanResult validateWxNotify(WxNotify wxNotify);

	/**
	 * 
	 * @param wxNotify
	 * @return
	 */
	BooleanResult createWxNotify(WxNotify wxNotify);

}
