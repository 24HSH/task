package com.wideka.sync.weipos.action;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.wideka.sync.api.weipos.bo.Data;
import com.wideka.sync.api.weipos.bo.MsgContent;
import com.wideka.sync.api.weipos.bo.Order;
import com.wideka.sync.api.weipos.bo.PrintContent;
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
		String access_token = "571b78564deaae42b12779d5";
		String timestamp = "135678976098";
		String nonce = "01234567";
		String mcode = "162201";
		String token = "21vMPTNxPYUQPPeO52wPu6DbHdpt";
		String device_en = "cce90238";

		String url = "http://open.wangpos.com/wopengateway/api/entry";

		Map<String, String> map = new HashMap<String, String>();
		map.put("access_token", access_token);
		map.put("timestamp", timestamp);
		map.put("nonce", nonce);
		map.put("mcode", mcode);
		map.put("service", service);
		map.put("device_en", device_en);

		String sign = null;

		if ("base.device.get_status".equals(service) || "base.store.info".equals(service)) {
			String a =
				"access_token=" + access_token + "&device_en=" + device_en + "&mcode=" + mcode + "&nonce=" + nonce
					+ "&service=" + service + "&timestamp=" + timestamp + "&token=" + token;

			sign = EncryptUtil.encryptSHA(a).toUpperCase();

			map.put("signature", sign);
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

			// order.setPrintMode(1);
			// order.setPrintContent("y82yzbXY1rc6IM37vqlTT0hPCsGqz7W157uwOiAxODY4NHh4eHh4eAogIMGqz7XIyzog1cXI/QoK\nz8K1pcqxvOQ6IDIwMTUtMDUtMjEgMTA6Mjg6MzYKy82yzcqxvOQ6ILDr0KHKsdLUxNoKoaqhqqGq\noaqhqqGqoaqhqqGqoaqhqqGqoaqhqqGqoaoKINChvMYgICAgyv3BvyAgICAgw/uzxgogNy4wMCAg\nICAgMbfdICAgICDO97rsysG4x8Lrt7kKIDguMDAgICAgIDG33SAgICAgz+O4ycjiy7+4x8Lrt7kK\nMTAuMDAgICAgIDG33SAgICAgwunAscWjyOK4x8Lrt7kKoaqhqqGqoaqhqqGqoaqhqqGqoaqhqqGq\noaqhqqGqoaoKtqm1pb3wtu46IDI1LjAw1KoK08W73civICA6IC0yLjAw1KoKyrW8ytanuLY6IDIz\nLjAw1Ko=");

			order.setPrintMode(2);
			PrintContent[] printContents = new PrintContent[12];
			printContents[0] = new PrintContent("点菜单", "CENTER", "BOLD");
			printContents[1] = new PrintContent("分店名称：东北饺子馆");
			printContents[2] = new PrintContent("桌号：12号桌");
			printContents[3] = new PrintContent("订单编号：1234567890");
			printContents[4] = new PrintContent("下单时间：2015-05-22 08:30");
			printContents[5] = new PrintContent("小计            数量         名称");
			printContents[6] = new PrintContent("￥12.0   *2    青椒炒肉");
			printContents[7] = new PrintContent("￥10.0   *1    麻婆豆腐");
			printContents[8] = new PrintContent("￥14.0   *2    土豆丝肉泥");
			printContents[9] = new PrintContent("合计：62元");
			printContents[10] = new PrintContent("折扣：满40减2");
			printContents[11] = new PrintContent("折扣后合计60元");

			order.setPrintContents(printContents);

			int[] command = { 1, 2 };
			order.setCommand(command);

			String d = JSON.toJSONString(order);

			String a =
				"access_token=" + access_token + "&data=" + d + "&device_en=" + device_en + "&mcode=" + mcode
					+ "&nonce=" + nonce + "&service=" + service + "&timestamp=" + timestamp + "&token=" + token;

			sign = EncryptUtil.encryptSHA(a).toUpperCase();

			map.put("data", d);
			map.put("signature", sign);
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

			String d = JSON.toJSONString(data);

			String a =
				"access_token=" + access_token + "&data=" + d + "&device_en=" + device_en + "&mcode=" + mcode
					+ "&nonce=" + nonce + "&service=" + service + "&timestamp=" + timestamp + "&token=" + token;

			sign = EncryptUtil.encryptSHA(a).toUpperCase();

			map.put("data", d);
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
