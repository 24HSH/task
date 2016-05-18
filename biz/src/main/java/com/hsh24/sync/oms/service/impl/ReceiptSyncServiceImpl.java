package com.hsh24.sync.oms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSON;
import com.hsh24.sync.api.oms.IReceiptLogService;
import com.hsh24.sync.api.oms.IReceiptService;
import com.hsh24.sync.api.oms.IReceiptSyncService;
import com.hsh24.sync.api.oms.bo.Receipt;
import com.hsh24.sync.api.oms.bo.ReceiptDetail;
import com.hsh24.sync.api.oms.bo.ReceiptLog;
import com.hsh24.sync.api.oms.bo.Result;
import com.hsh24.sync.framework.bo.BooleanResult;
import com.hsh24.sync.framework.log.Logger4jCollection;
import com.hsh24.sync.framework.log.Logger4jExtend;
import com.hsh24.sync.framework.util.HttpUtil;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ReceiptSyncServiceImpl implements IReceiptSyncService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(ReceiptSyncServiceImpl.class);

	private TransactionTemplate transactionTemplate;

	private IReceiptLogService receiptLogService;

	private IReceiptService receiptService;

	private String url;

	@Override
	public int sync() {
		List<ReceiptLog> receiptLogList = receiptLogService.getReceiptLogList();

		if (receiptLogList == null || receiptLogList.size() == 0) {
			return 0;

		}

		int count = 0;

		for (ReceiptLog receiptLog : receiptLogList) {
			final Long id = receiptLog.getId();
			Long receiptId = receiptLog.getReceiptId();

			final Receipt receipt = receiptService.getReceipt(receiptId);
			if (receipt == null) {
				continue;
			}

			Long shopId = receipt.getShopId();

			List<ReceiptDetail> receiptDetailList = receiptService.getReceiptDetailList(receiptId);
			if (receiptDetailList == null || receiptDetailList.size() == 0) {
				continue;
			}

			for (ReceiptDetail receiptDetail : receiptDetailList) {
				receiptDetail.setShopId(shopId);
			}

			receipt.setReceiptDetailList(receiptDetailList);

			BooleanResult result = transactionTemplate.execute(new TransactionCallback<BooleanResult>() {
				public BooleanResult doInTransaction(TransactionStatus ts) {
					BooleanResult result = receiptLogService.finishReceiptLog(id, "sys");
					if (!result.getResult()) {
						ts.setRollbackOnly();

						return result;
					}

					try {
						Map<String, String> map = new HashMap<String, String>();
						map.put("mrmReceive", JSON.toJSONString(receipt));

						String code = HttpUtil.post(url, map);
						if (StringUtils.isBlank(code)) {
							ts.setRollbackOnly();

							return result;
						}

						Result res = JSON.parseObject(code, Result.class);

						if (!"success".equals(res.getInfo())) {
							ts.setRollbackOnly();

							return result;
						}
					} catch (Exception e) {
						logger.error(e);

						ts.setRollbackOnly();

						result.setCode(e.getMessage());
						result.setResult(false);
						return result;
					}

					return result;
				}
			});

			if (result.getResult()) {
				count++;
			}
		}

		return count;
	}

	public TransactionTemplate getTransactionTemplate() {
		return transactionTemplate;
	}

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public IReceiptLogService getReceiptLogService() {
		return receiptLogService;
	}

	public void setReceiptLogService(IReceiptLogService receiptLogService) {
		this.receiptLogService = receiptLogService;
	}

	public IReceiptService getReceiptService() {
		return receiptService;
	}

	public void setReceiptService(IReceiptService receiptService) {
		this.receiptService = receiptService;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
