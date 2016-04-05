package com.wideka.sync.wxpay.service.impl;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import com.wideka.sync.api.wxpay.IWxpayService;
import com.wideka.sync.framework.bo.BooleanResult;
import com.wideka.sync.framework.log.Logger4jCollection;
import com.wideka.sync.framework.log.Logger4jExtend;
import com.wideka.sync.framework.util.EncryptUtil;
import com.wideka.sync.framework.util.LogUtil;
import com.wideka.sync.wxpay.dao.IWxpayDao;
import com.wideka.weixin.api.pay.IRefundService;
import com.wideka.weixin.api.pay.IUnifiedOrderService;
import com.wideka.weixin.api.pay.bo.WxNotify;

/**
 * 
 * @author JiakunXu
 * 
 */
public class WxpayServiceImpl implements IWxpayService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(WxpayServiceImpl.class);

	private IUnifiedOrderService unifiedOrderService;

	private IRefundService refundService;

	private IWxpayDao wxpayDao;

	private String appId;

	private String appSecret;

	private String mchId;

	private String notifyUrl;

	private String key;

	private String sslPath;

	@Override
	public BooleanResult validateWxNotify(WxNotify wxNotify) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);
		result.setCode(IWxpayService.RETURN_CODE_FAIL);

		if (wxNotify == null) {
			return result;
		}

		// 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
		if ("FAIL".equals(wxNotify.getReturnCode())) {
			return result;
		}

		if (validate(wxNotify)) {
			result.setResult(true);
			result.setCode(IWxpayService.RETURN_CODE_SUCCESS);
		}

		return result;
	}

	/**
	 * 
	 * @param wxNotify
	 * @return
	 */
	private boolean validate(WxNotify wxNotify) {
		StringBuilder sign = new StringBuilder();

		if (StringUtils.isNotBlank(wxNotify.getAppId())) {
			sign.append("&appid=").append(wxNotify.getAppId());
		}
		if (StringUtils.isNotBlank(wxNotify.getBankType())) {
			sign.append("&bank_type=").append(wxNotify.getBankType());
		}
		if (wxNotify.getCashFee() != null) {
			sign.append("&cash_fee=").append(wxNotify.getCashFee());
		}
		if (StringUtils.isNotBlank(wxNotify.getCashFeeType())) {
			sign.append("&cash_fee_type=").append(wxNotify.getCashFeeType());
		}
		if (wxNotify.getCouponCount() != null) {
			sign.append("&coupon_count=").append(wxNotify.getCouponCount());
		}
		if (wxNotify.getCouponFee() != null) {
			sign.append("&coupon_fee=").append(wxNotify.getCouponFee());
		}
		if (wxNotify.getCouponCount() != null && wxNotify.getCouponCount() > 0) {
			Integer[] couponFees = wxNotify.getCouponFees();
			for (int i = 0; i < wxNotify.getCouponCount(); i++) {
				sign.append("&coupon_fee_=" + i).append(couponFees[i]);
			}

			String[] couponIds = wxNotify.getCouponIds();
			for (int i = 0; i < wxNotify.getCouponCount(); i++) {
				sign.append("&coupon_id_=" + i).append(couponIds[i]);
			}
		}
		if (StringUtils.isNotBlank(wxNotify.getDeviceInfo())) {
			sign.append("&device_info=").append(wxNotify.getDeviceInfo());
		}
		if (StringUtils.isNotBlank(wxNotify.getErrCode())) {
			sign.append("&err_code=").append(wxNotify.getErrCode());
		}
		if (StringUtils.isNotBlank(wxNotify.getErrCodeDes())) {
			sign.append("&err_code_des=").append(wxNotify.getErrCodeDes());
		}
		if (StringUtils.isNotBlank(wxNotify.getFeeType())) {
			sign.append("&fee_type=").append(wxNotify.getFeeType());
		}
		if (StringUtils.isNotBlank(wxNotify.getIsSubscribe())) {
			sign.append("&is_subscribe=").append(wxNotify.getIsSubscribe());
		}
		if (StringUtils.isNotBlank(wxNotify.getMchId())) {
			sign.append("&mch_id=").append(wxNotify.getMchId());
		}
		if (StringUtils.isNotBlank(wxNotify.getNonceStr())) {
			sign.append("&nonce_str=").append(wxNotify.getNonceStr());
		}
		if (StringUtils.isNotBlank(wxNotify.getOpenId())) {
			sign.append("&openid=").append(wxNotify.getOpenId());
		}
		if (StringUtils.isNotBlank(wxNotify.getOutTradeNo())) {
			sign.append("&out_trade_no=").append(wxNotify.getOutTradeNo());
		}
		if (StringUtils.isNotBlank(wxNotify.getResultCode())) {
			sign.append("&result_code=").append(wxNotify.getResultCode());
		}
		if (StringUtils.isNotBlank(wxNotify.getReturnCode())) {
			sign.append("&return_code=").append(wxNotify.getReturnCode());
		}
		if (StringUtils.isNotBlank(wxNotify.getReturnMsg())) {
			sign.append("&return_msg=").append(wxNotify.getReturnMsg());
		}
		if (StringUtils.isNotBlank(wxNotify.getTimeEnd())) {
			sign.append("&time_end=").append(wxNotify.getTimeEnd());
		}
		if (wxNotify.getTotalFee() != null) {
			sign.append("&total_fee=").append(wxNotify.getTotalFee());
		}
		if (StringUtils.isNotBlank(wxNotify.getTradeType())) {
			sign.append("&trade_type=").append(wxNotify.getTradeType());
		}
		if (StringUtils.isNotBlank(wxNotify.getTransactionId())) {
			sign.append("&transaction_id=").append(wxNotify.getTransactionId());
		}

		sign.append("&key=").append(key);

		try {
			if (wxNotify.getSign().equals(EncryptUtil.encryptMD5(sign.substring(1).toString()).toUpperCase())) {
				return true;
			}
		} catch (IOException e) {
			logger.error(e);
		}

		return false;
	}

	@Override
	public BooleanResult createWxNotify(WxNotify wxNotify) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		try {
			wxpayDao.createWxNotify(wxNotify);
			result.setResult(true);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(wxNotify), e);
		}

		return result;
	}

	public IUnifiedOrderService getUnifiedOrderService() {
		return unifiedOrderService;
	}

	public void setUnifiedOrderService(IUnifiedOrderService unifiedOrderService) {
		this.unifiedOrderService = unifiedOrderService;
	}

	public IRefundService getRefundService() {
		return refundService;
	}

	public void setRefundService(IRefundService refundService) {
		this.refundService = refundService;
	}

	public IWxpayDao getWxpayDao() {
		return wxpayDao;
	}

	public void setWxpayDao(IWxpayDao wxpayDao) {
		this.wxpayDao = wxpayDao;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSslPath() {
		return sslPath;
	}

	public void setSslPath(String sslPath) {
		this.sslPath = sslPath;
	}

}
