package com.hsh24.sync.wxpay.dao;

import com.wideka.weixin.api.pay.bo.WxNotify;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IWxpayDao {

	/**
	 * 
	 * @param wxNotify
	 * @return
	 */
	int createWxNotify(WxNotify wxNotify);

}
