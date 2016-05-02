package com.wideka.sync.wizarpos.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.wideka.sync.api.wizarpos.bo.Order;
import com.wideka.sync.api.wizarpos.bo.OrderDetail;
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
		} else if ("/wxshop/pay".equals(api)) {
			str =
				"{\"mid\": \""
					+ mid
					+ "\", \"orderId\":\"100000000\", \"payAmount\": \"0.01\", \"print\": \"true\", \"url\": \"wx.wideka.com\"}";
		} else if ("/wxshop/market/order/submit".equals(api)) {
			Order order = new Order();
			order.setCardId(null);
			order.setMid(mid);

			List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setProductId("0bd1d2c3-7347-4a7c-8263-8fc978792c89");
			orderDetail.setAmount(BigDecimal.TEN);
			orderDetail.setPrice(BigDecimal.ONE);
			orderDetail.setQty(10);
			orderDetail.setRemark(null);
			orderDetailList.add(orderDetail);

			order.setOrderDetailList(orderDetailList);
			order.setRemark(null);
			order.setUrl("http://wwww.wideka.com");

			str = JSON.toJSONString(order);
		} else if ("/wxshop/order/submit".equals(api)) {
			Order order = new Order();
			order.setOrderId("100000000");
			order.setMid(mid);
			order.setDispatchType("1");
			order.setPrint(true);
			order.setUrl("http://wwww.wideka.com");

			str = JSON.toJSONString(order);
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
