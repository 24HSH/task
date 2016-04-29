package com.wideka.sync.wizarpos.action;

import java.util.HashMap;
import java.util.Map;

import com.wideka.sync.framework.action.BaseAction;
import com.wideka.sync.framework.util.HttpUtil;

/**
 * 
 * @author JiakunXu
 * 
 */
public class WizarposAction extends BaseAction {

	private static final long serialVersionUID = -322645893755696525L;

	private String api;

	public String api() {
		String mid = "100100210000302";

		String url = "http://cashier2.wizarpos.com/wizarposOpen-server/v1_0" + api;
		String str = null;

		if ("/merchantdef/payInfo".equals(api)) {
			str = "{\"mid\": \"" + mid + "\", \"payType\": \"1\"}";
		} else if ("/wechart/token".equals(api)) {
			str = "{\"mid\": \"" + mid + "\", \"cardNo\": \"00000026\"}";
		} else if ("/order/sync".equals(api)) {

		} else if ("/muslim/bind".equals(api)) {
			str = "{\"mid\": \"" + mid + "\", \"sn\": \"WP14451000002474\"}";
		} else if ("/wechart/wxshop/userinfo".equals(api)) {
			str = "{\"mid\": \"" + mid + "\", \"openId\": \"oAab_tj8kpkcXmINJAZIcxzzyvWs\"}";
		} else if ("/szws/getMerchantDefByMid".equals(api)) {
			str = "{\"mid\": \"" + mid + "\"}";
		}

		try {
			this.setResourceResult(HttpUtil.post(url, str));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return RESOURCE_RESULT;

	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

}
