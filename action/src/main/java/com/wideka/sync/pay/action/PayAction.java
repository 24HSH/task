package com.wideka.sync.pay.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.wideka.sync.api.pay.IPayService;
import com.wideka.sync.framework.action.BaseAction;
import com.wideka.sync.framework.bo.BooleanResult;
import com.wideka.sync.framework.log.Logger4jCollection;
import com.wideka.sync.framework.log.Logger4jExtend;

/**
 * 
 * @author JiakunXu
 * 
 */
public class PayAction extends BaseAction {

	private static final long serialVersionUID = 3200362561478926494L;

	private Logger4jExtend logger = Logger4jCollection.getLogger(PayAction.class);

	private IPayService payService;

	/**
	 * 回调.
	 * 
	 * @return
	 */
	public String wxNotify() {
		StringBuilder fileContent = new StringBuilder();

		InputStreamReader in = null;
		BufferedReader reader = null;
		try {
			in = new InputStreamReader(this.getServletRequest().getInputStream(), "UTF-8");
			reader = new BufferedReader(in);

			String tempStr = reader.readLine();
			while (tempStr != null) {
				fileContent.append(tempStr);
				tempStr = reader.readLine();
			}
		} catch (Exception e) {
			logger.error(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}

		BooleanResult result = payService.notify(fileContent.toString());
		this.setResourceResult(result.getCode());

		return RESOURCE_RESULT;
	}

	public IPayService getPayService() {
		return payService;
	}

	public void setPayService(IPayService payService) {
		this.payService = payService;
	}

}
