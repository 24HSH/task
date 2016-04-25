package com.wideka.sync.weipos.action;

import java.util.Map;
import java.util.TreeMap;

import com.alibaba.fastjson.JSON;
import com.wideka.sync.api.weipos.bo.Data;
import com.wideka.sync.api.weipos.bo.MsgContent;
import com.wideka.sync.api.weipos.bo.CustOrder;
import com.wideka.sync.api.weipos.bo.PrintContent;
import com.wideka.sync.api.weipos.bo.Result;
import com.wideka.sync.api.weipos.bo.Trade;
import com.wideka.sync.framework.action.BaseAction;
import com.wideka.sync.framework.util.UUIDUtil;
import com.wideka.weixin.framework.util.EncryptUtil;
import com.wideka.weixin.framework.util.HttpUtil;

/**
 * 
 * @author JiakunXu
 * 
 */
public class WeiposAction extends BaseAction {

	private static final long serialVersionUID = -9176828111428175603L;

	/**
	 * 加密签名，signature结合了开发者填写的token参数和请求中所有参数。
	 */
	private String signature;

	/**
	 * 时间戳.
	 */
	private String timestamp;

	/**
	 * 随机数.
	 */
	private String nonce;

	/**
	 * Echo.
	 */
	private String event;

	/**
	 * 随机字符串.
	 */
	private String echo_str;

	private String service;

	public String callback() {

		Result result = new Result();
		result.setStatus(0);
		result.setInfo("success");

		Data data = new Data();
		data.setEchoStr(echo_str);

		result.setData(data);

		this.setResourceResult(JSON.toJSONString(result));

		return RESOURCE_RESULT;
	}

	/**
	 * 
	 * @return
	 */
	public String weiposNotify() {
		@SuppressWarnings("unchecked")
		Map<Object, Object> map = this.getServletRequest().getParameterMap();

		System.out.println("==============================");
		for (Map.Entry<Object, Object> m : map.entrySet()) {
			String[] a = ((String[]) m.getValue());
			for (String b : a) {
				System.out.println(m.getKey() + "=" + b);
			}
		}
		System.out.println("==============================");

		this.setResourceResult("success");
		return RESOURCE_RESULT;
	}

	public String accesstoken() {
		try {
			String result =
				HttpUtil
					.get("http://open.wangpos.com/wopengateway/api/accesstoken/get?appid=57198b9391b9b24b3bf49e0a&secret=SLPZONKL");
			this.setResourceResult(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return RESOURCE_RESULT;
	}

	public String api() throws Exception {
		String access_token = "571e0ace91b9b24b3bf4a10a";
		String timestamp = "135678976098";
		String nonce = "01234567";
		String mcode = "162201";
		String token = "21vMPTNxPYUQPPeO52wPu6DbHdpt";
		String device_en = "cce90238";
		String biz_code = "101460";

		String url = "http://open.wangpos.com/wopengateway/api/entry";

		Map<String, String> map = new TreeMap<String, String>();
		map.put("access_token", access_token);
		map.put("timestamp", timestamp);
		map.put("nonce", nonce);
		map.put("mcode", mcode);
		map.put("service", service);
		map.put("device_en", device_en);

		String sign = null;

		if ("base.device.get_status".equals(service) || "base.store.info".equals(service)) {
			StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, String> m : map.entrySet()) {
				if (sb.length() > 0) {
					sb.append("&");
				}
				sb.append(m.getKey()).append("=").append(m.getValue());
			}
			sb.append("&token=").append(token);

			sign = EncryptUtil.encryptSHA(sb.toString()).toUpperCase();

			map.put("signature", sign);
		} else if ("base.msg.app_msg".equals(service)) {
			map.put("biz_code", biz_code);
			String d = "{'a':1,'b':2}";
			map.put("data", d);

			StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, String> m : map.entrySet()) {
				if (sb.length() > 0) {
					sb.append("&");
				}
				sb.append(m.getKey()).append("=").append(m.getValue());
			}
			sb.append("&token=").append(token);

			sign = EncryptUtil.encryptSHA(sb.toString()).toUpperCase();

			map.put("signature", sign);
		} else if ("base.msg.notify_msg".equals(service)) {
			map.put("biz_code", biz_code);
			String d = "/";
			map.put("data", d);

			StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, String> m : map.entrySet()) {
				if (sb.length() > 0) {
					sb.append("&");
				}
				sb.append(m.getKey()).append("=").append(m.getValue());
			}
			sb.append("&token=").append(token);

			sign = EncryptUtil.encryptSHA(sb.toString()).toUpperCase();

			map.put("signature", sign);
		} else if ("order.msg.push".equals(service)) {
			CustOrder order = new CustOrder();
			order.setTemplate_code("MSG100006");
			order.setOutOrderNo("052600004");

			MsgContent msgContent = new MsgContent();
			msgContent.setTitle("好社惠商城订单");
			msgContent.setVoice("您有未处理订单");
			msgContent.setDesc("未处理好社惠商城订单");
			msgContent.setDescDetail("订单编号：1234567890\n订单时间：2015-5-26 12:30");

			order.setMsgContent(msgContent);
			order.setShowContent("<html><body>" + "<div>点菜单</div>" + "<div>分店名称：好社惠下沙1号亭</div>"
				+ "<div>订单编号：1234567890</div>" + "<div>下单时间：2015-05-22 08:30</div>" + "</body></html>");

			order.setPrintMode(2);
			PrintContent[] printContent = new PrintContent[11];
			printContent[0] = new PrintContent("点菜单\n", "CENTER", "BOLD");
			printContent[1] = new PrintContent("分店名称：好社惠下沙1号亭\n");
			printContent[2] = new PrintContent("订单编号：1234567890\n");
			printContent[3] = new PrintContent("下单时间：2015-05-22 08:30\n");
			printContent[4] = new PrintContent(" 小计    数量    名称\n");
			printContent[5] = new PrintContent("￥12.0   *2    青椒炒肉\n");
			printContent[6] = new PrintContent("￥10.0   *1    麻婆豆腐\n");
			printContent[7] = new PrintContent("￥14.0   *2    土豆丝肉泥\n");
			printContent[8] = new PrintContent("合计：62元\n");
			printContent[9] = new PrintContent("折扣：满40减2\n");
			printContent[10] = new PrintContent("折扣后合计60元\n\n\n\n\n");

			order.setPrintContent(printContent);

			int[] command = { 1 };
			order.setCommand(command);

			String d = JSON.toJSONString(order);

			map.put("data", d);

			StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, String> m : map.entrySet()) {
				if (sb.length() > 0) {
					sb.append("&");
				}
				sb.append(m.getKey()).append("=").append(m.getValue());
			}
			sb.append("&token=").append(token);

			sign = EncryptUtil.encryptSHA(sb.toString()).toUpperCase();

			map.put("signature", sign);
		} else if ("cashier.api.order".equals(service)) {
			Trade trade = new Trade();
			trade.setOutTradeNo(UUIDUtil.generate());
			trade.setBody("我是内容");
			trade.setTotalFee(100);
			trade.setNotifyUrl("http://wx.wideka.com/sync/weipos/notify.htm");
			trade.setAttach("我是附件");

			String d = JSON.toJSONString(trade);

			map.put("data", d);

			StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, String> m : map.entrySet()) {
				if (sb.length() > 0) {
					sb.append("&");
				}
				sb.append(m.getKey()).append("=").append(m.getValue());
			}
			sb.append("&token=").append(token);

			sign = EncryptUtil.encryptSHA(sb.toString()).toUpperCase();

			map.put("signature", sign);
		} else if ("cashier.api.refund".equals(service)) {
			Trade trade = new Trade();
			trade.setCashierTradeNo("10001622012016042500000037");

			String d = JSON.toJSONString(trade);

			map.put("data", d);

			StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, String> m : map.entrySet()) {
				if (sb.length() > 0) {
					sb.append("&");
				}
				sb.append(m.getKey()).append("=").append(m.getValue());
			}
			sb.append("&token=").append(token);

			sign = EncryptUtil.encryptSHA(sb.toString()).toUpperCase();

			map.put("signature", sign);
		}

		try {
			this.setResourceResult(HttpUtil.post(url, map));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return RESOURCE_RESULT;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getEcho_str() {
		return echo_str;
	}

	public void setEcho_str(String echo_str) {
		this.echo_str = echo_str;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

}
