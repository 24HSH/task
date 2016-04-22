package com.wideka.sync.weipos.action;

import com.alibaba.fastjson.JSON;
import com.wideka.sync.api.weipos.bo.Data;
import com.wideka.sync.api.weipos.bo.MsgContent;
import com.wideka.sync.api.weipos.bo.Order;
import com.wideka.sync.api.weipos.bo.Result;
import com.wideka.sync.api.weipos.bo.Trade;
import com.wideka.sync.framework.action.BaseAction;
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
		String access_token = "5719f7ee4deaae42b12778e0";
		String timestamp = "135678976098";
		String nonce = "01234567";
		String mcode = "162201";
		String token = "21vMPTNxPYUQPPeO52wPu6DbHdpt";
		String device_en = "cce90238";

		String url =
			"http://open.wangpos.com/wopengateway/api/entry?access_token=" + access_token + "&timestamp=" + timestamp
				+ "&nonce=" + nonce + "&mcode=" + mcode + "&service=" + service + "&device_en=" + device_en;

		String sign = null;

		if ("base.device.get_status".equals(service) || "base.store.info".equals(service)) {
			String a =
				"access_token=" + access_token + "&device_en=" + device_en + "&mcode=" + mcode + "&nonce=" + nonce
					+ "&service=" + service + "&timestamp=" + timestamp + "&token=" + token;

			sign = EncryptUtil.encryptSHA(a).toUpperCase();

			url += "&signature=" + sign;
		} else if ("order.msg.push".equals(service)) {
			Order order = new Order();
			order.setTemplate_code("MSG100006");
			order.setOutOrderNo("052600004");

			MsgContent msgContent = new MsgContent();
			msgContent.setTitle("美团外卖订单");
			msgContent.setVoice("您有未处理订单");
			msgContent.setDesc("未处理美团外卖订单");
			msgContent.setDescDetail("订单编号：123456789\n桌号：3号桌\n订单时间：2015-5-26 12:30");

			order.setMsgContent(msgContent);
			order.setShowContent("html code");
			order.setPrintMode(1);
			order
				.setPrintContent("y82yzbXY1rc6IM37vqlTT0hPCsGqz7W157uwOiAxODY4NHh4eHh4eAogIMGqz7XIyzog1cXI/QoK\nz8K1pcqxvOQ6IDIwMTUtMDUtMjEgMTA6Mjg6MzYKy82yzcqxvOQ6ILDr0KHKsdLUxNoKoaqhqqGq\noaqhqqGqoaqhqqGqoaqhqqGqoaqhqqGqoaoKINChvMYgICAgyv3BvyAgICAgw/uzxgogNy4wMCAg\nICAgMbfdICAgICDO97rsysG4x8Lrt7kKIDguMDAgICAgIDG33SAgICAgz+O4ycjiy7+4x8Lrt7kK\nMTAuMDAgICAgIDG33SAgICAgwunAscWjyOK4x8Lrt7kKoaqhqqGqoaqhqqGqoaqhqqGqoaqhqqGq\noaqhqqGqoaoKtqm1pb3wtu46IDI1LjAw1KoK08W73civICA6IC0yLjAw1KoKyrW8ytanuLY6IDIz\nLjAw1Ko=");
			int[] command = { 1, 2 };
			order.setCommand(command);

			String a =
				"access_token=" + access_token + "&data=" + JSON.toJSONString(order) + "&device_en=" + device_en
					+ "&mcode=" + mcode + "&nonce=" + nonce + "&service=" + service + "&timestamp=" + timestamp
					+ "&token=" + token;

			sign = EncryptUtil.encryptSHA(a).toUpperCase();

			url += "&signature=" + sign;
		} else if ("cashier.api.order".equals(service)) {
			Data data = new Data();
			data.setDeviceEn(device_en);

			Trade trade = new Trade();
			trade.setOutTradeNo("test0987654322");
			trade.setBody("test下单");
			trade.setTotalFee(1);
			trade.setNotifyUrl("http://wx.wideka.com/sync/weipos/notify.htm");
			trade.setAttach("attach备注");

			data.setTrade(trade);

			String a =
				"access_token=" + access_token + "&data=" + JSON.toJSONString(data) + "&device_en=" + device_en
					+ "&mcode=" + mcode + "&nonce=" + nonce + "&service=" + service + "&timestamp=" + timestamp
					+ "&token=" + token;

			sign = EncryptUtil.encryptSHA(a).toUpperCase();

			url += "&signature=" + sign;
		}

		this.setResourceResult(HttpUtil.get(url));
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
