package com.hsh24.sync.oms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSON;
import com.hsh24.sync.api.oms.IItemConvertService;
import com.hsh24.sync.api.oms.IReceiptLogService;
import com.hsh24.sync.api.oms.IReceiptService;
import com.hsh24.sync.api.oms.IReceiptSyncService;
import com.hsh24.sync.api.oms.bo.ItemConvert;
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
@Service
public class ReceiptSyncServiceImpl implements IReceiptSyncService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(ReceiptSyncServiceImpl.class);

	@Resource
	private TransactionTemplate transactionTemplate;

	@Resource
	private IReceiptLogService receiptLogService;

	@Resource
	private IReceiptService receiptService;

	@Resource
	private IItemConvertService itemConvertService;

	@Value("${oms.receipt.url}")
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

			receipt.setWarehouse(receiptService.getWarehouse(shopId));

			List<ReceiptDetail> receiptDetailList = receiptService.getReceiptDetailList(receiptId);
			if (receiptDetailList == null || receiptDetailList.size() == 0) {
				continue;
			}

			List<ReceiptDetail> detailList = new ArrayList<ReceiptDetail>();

			for (ReceiptDetail receiptDetail : receiptDetailList) {
				receiptDetail.setShopId(shopId);

				List<ItemConvert> itemConvertList = itemConvertService.getItemConvert(receiptDetail.getBarCode());

				// 存在商品转换
				if (itemConvertList == null || itemConvertList.size() == 0) {
					detailList.add(receiptDetail);
				} else {
					for (ItemConvert itemConvert : itemConvertList) {
						ReceiptDetail detail = new ReceiptDetail();
						detail.setItemId(itemConvert.getItemId());
						detail.setItemCode(itemConvert.getItemCode());
						detail.setQuantity(receiptDetail.getQuantity() * itemConvert.getQuantity());
						detail.setShopId(receiptDetail.getShopId());
						detail.setModifyUser(receiptDetail.getModifyUser());

						detailList.add(detail);
					}
				}
			}

			receipt.setReceiptDetailList(detailList);

			BooleanResult result = transactionTemplate.execute(new TransactionCallback<BooleanResult>() {
				public BooleanResult doInTransaction(TransactionStatus ts) {
					BooleanResult result = receiptLogService.finishReceiptLog(id, "sys");
					if (!result.getResult()) {
						ts.setRollbackOnly();

						return result;
					}

					result.setResult(false);

					try {
						Map<String, String> map = new HashMap<String, String>();
						map.put("mrmReceive", JSON.toJSONString(receipt));

						String code = HttpUtil.post(url, map);
						if (StringUtils.isBlank(code)) {
							ts.setRollbackOnly();

							result.setCode("返回空.");
							return result;
						}

						Result res = JSON.parseObject(code, Result.class);

						if (!"success".equals(res.getInfo())) {
							ts.setRollbackOnly();

							result.setCode(code);
							return result;
						}
					} catch (Exception e) {
						logger.error(e);

						ts.setRollbackOnly();

						result.setCode(e.getMessage());
						return result;
					}

					result.setResult(true);
					return result;
				}
			});

			if (result.getResult()) {
				count++;
			} else {
				record(id, result.getCode());
			}
		}

		return count;
	}

	private void record(Long id, String e) {
		receiptLogService.recordReceiptLog(id, e, "sys");
	}

}
