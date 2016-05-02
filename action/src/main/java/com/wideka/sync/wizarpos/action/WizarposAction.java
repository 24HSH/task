package com.wideka.sync.wizarpos.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

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
		Map<String, String> map = new HashMap<String, String>();

		if ("/merchantdef/payInfo".equals(api)) {
			str = "{\"mid\": \"" + mid + "\", \"payType\": \"1\"}";
		} else if ("/wechart/token".equals(api)) {
			str = "{\"mid\": \"" + mid + "\", \"cardNo\": \"00000026\"}";
		} else if ("/order/sync".equals(api)) {
			Order order = new Order();
			order.setOpenId("oC_nujm8qOZUVpaUko3hs-RDbONo");
			order.setMid(mid);
			order.setStatus("3");
			order.setDispatchType("0");
			order.setAmount(BigDecimal.TEN);
			order.setPayStatus("0");
			order.setAddressId("01af83de-bd8f-4667-89e4-0f8481924c4c");

			List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOperateType("I");
			orderDetail.setProductId("0bd1d2c3-7347-4a7c-8263-8fc978792c89");
			orderDetail.setAmount(BigDecimal.TEN);
			orderDetail.setPrice(BigDecimal.ONE);
			orderDetail.setQty(10);
			orderDetailList.add(orderDetail);

			order.setOrderDetailList(orderDetailList);

			str = JSON.toJSONString(order);
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
			order.setProductId("0bd1d2c3-7347-4a7c-8263-8fc978792c89");
			order.setMid(mid);
			order.setDispatchType("1");
			order.setPrint(true);
			order.setUrl("http://wwww.wideka.com");

			str = JSON.toJSONString(order);
		} else if ("/wxshop/tran/log".equals(api)) {
			str = "{\"mid\": \"" + mid + "\", \"pageNo\": \"1\"}";
		} else if ("/merchantdef/info".equals(api)) {
			str = "{\"mid\": \"" + mid + "\"}";
		} else if ("/thirdSyncService/doAction".equals(api)) {
			str = "{\"mid\": \"" + mid + "\", \"operationType\": \"1\", \"categoryId\": \"\"}";
		} else if ("/thirdNofity/syncThirdNotifyDef".equals(api)) {
			str = "{\"mid\": \"" + mid + "\", \"notifySet\": \"1\", \"notifyUrl\": \"http://wx.wideka.com\"}";
		} else if ("/wxshop/isEmpty".equals(api)) {
			str = "{\"mid\": \"" + mid + "\", \"cardId\": \"d2c503ba-2f0e-4876-8bc2-cb145f697f90\"}";
		} else if ("/wechart/wxshop/shakesearch".equals(api)) {
			str = "{\"mid\": \"" + mid + "\"}";
		} else if ("/wechart/wxshop/userinfo".equals(api)) {
			str = "{\"mid\": \"" + mid + "\"}";
		} else if ("/product/category".equals(api)) {
			str = "{\"mid\": \"" + mid + "\"}";
		} else if ("/wxshop/wxCardBind".equals(api)) {
			str = "{\"orderId\": \"03100105100000001201504100013\", \"totalFee\": \"100\"}";
		} else if ("/wxshop/shopRemind".equals(api)) {
			str =
				"{\"mid\": \"" + mid
					+ "\", \"orderId\": \"03100105100000001201504210069\", \"sendMessage\": \"微店结帐\", \"type\": \"1\"}";
		} else if ("/thirdSyncService/editMidStatus".equals(api)) {
			str = "{\"mid\": \"" + mid + "\", \"merchantStatus\": \"0\"}";
		} else if ("/thirdSyncService/syncThirdServiceDef".equals(api)) {
			str =
				"{\"mid\": \"" + mid
					+ "\", \"notifySet\": \"1\", \"serviceUrl\": \"http://wx.wideka.com\", \"urlType\": \"3\"}";
		} else if ("/product/merchandise".equals(api)) {
			str = "{\"mid\": \"" + mid + "\", \"productId\": \"0888bb66-0723-42bd-ae8e-abc3357c3040\"}";
		} else if ("/wxshop/weixintemplateinfo".equals(api)) {
			str = "{\"mid\": \"" + mid + "\", \"no\": \"TM00244\"}";
		} else if ("/getProxyMerchantsByMid".equals(api)) {
			str = "{\"mid\": \"" + mid + "\", \"lastUpdateTime\": \"\"}";
		} else if ("/catering/message/remind".equals(api)) {
			str = "{\"mid\": \"" + mid + "\", \"reOrderId\": \"\", \"sendMessage\": \"预订\"}";
		} else if ("/catering/service/call".equals(api)) {
			str =
				"{\"mid\": \"" + mid
					+ "\", \"tableId\": \"14e11ae4-8b12-11e4-8732-00163e00298f\", \"sendMessage\": \"加水\"}";
		} else if ("/ServicesIndustry/syncStuffData".equals(api)) {
			str = "{\"mid\": \"" + mid + "\"}";
		} else if ("/wxshop/wxPayUpdate".equals(api)) {
			str = "{\"orderId\": \"1000000\", \"totalFee\": \"100\", \"transactionId\": \"1000000\"}";
		} else if ("/wxshop/remind".equals(api)) {
			str =
				"{\"mid\": \"" + mid
					+ "\", \"cardNo\": \"14e11ae4-8b12-11e4-8732-00163e00298f\", \"sendMessage\": \"发送内容\"}";
		} else if ("/wxshop/order/query".equals(api)) {
			str = "{\"mid\": \"" + mid + "\", \"orderId\": \"1458f9d2-85c4-4916-a2eb-9805e83673c4\"}";
		}

		if (StringUtils.isNotBlank(str)) {
			try {
				this.setResourceResult(HttpUtil.post(url, str));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (!map.isEmpty()) {
			try {
				this.setResourceResult(HttpUtil.post(url, map));
			} catch (Exception e) {
				e.printStackTrace();
			}
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
