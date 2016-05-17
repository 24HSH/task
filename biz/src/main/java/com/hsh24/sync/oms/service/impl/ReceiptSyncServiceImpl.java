package com.hsh24.sync.oms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.hsh24.sync.api.oms.IReceiptLogService;
import com.hsh24.sync.api.oms.IReceiptService;
import com.hsh24.sync.api.oms.IReceiptSyncService;
import com.hsh24.sync.api.oms.bo.Receipt;
import com.hsh24.sync.api.oms.bo.ReceiptDetail;
import com.hsh24.sync.api.oms.bo.ReceiptLog;
import com.hsh24.sync.framework.bo.BooleanResult;
import com.hsh24.sync.framework.util.HttpUtil;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ReceiptSyncServiceImpl implements IReceiptSyncService {

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
			Long id = receiptLog.getId();
			Long receiptId = receiptLog.getReceiptId();

			Receipt receipt = receiptService.getReceipt(receiptId);
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

			Map<String, String> map = new HashMap<String, String>();
			try {
				map.put("mrmReceive", JSON.toJSONString(receipt));
				System.out.println(HttpUtil.post(url, map));
			} catch (Exception e) {
				e.printStackTrace();
			}

			BooleanResult result = receiptLogService.finishReceiptLog(id, "sys");
			if (result.getResult()) {
				count++;
			}
		}

		return count;
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
