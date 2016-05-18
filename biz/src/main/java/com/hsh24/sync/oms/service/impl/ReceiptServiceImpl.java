package com.hsh24.sync.oms.service.impl;

import java.util.List;

import com.hsh24.sync.api.oms.IReceiptService;
import com.hsh24.sync.api.oms.bo.Receipt;
import com.hsh24.sync.api.oms.bo.ReceiptDetail;
import com.hsh24.sync.framework.log.Logger4jCollection;
import com.hsh24.sync.framework.log.Logger4jExtend;
import com.hsh24.sync.oms.dao.IReceiptDao;
import com.hsh24.sync.oms.dao.IReceiptDetailDao;
import com.wideka.weixin.framework.util.LogUtil;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ReceiptServiceImpl implements IReceiptService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(ReceiptServiceImpl.class);

	private IReceiptDao receiptDao;

	private IReceiptDetailDao receiptDetailDao;

	@Override
	public Receipt getReceipt(Long receiptId) {
		if (receiptId == null) {
			return null;
		}

		Receipt receipt = new Receipt();
		receipt.setReceiptId(receiptId);

		try {
			return receiptDao.getReceipt(receipt);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(receipt), e);
		}

		return null;
	}

	@Override
	public List<ReceiptDetail> getReceiptDetailList(Long receiptId) {
		if (receiptId == null) {
			return null;
		}

		ReceiptDetail receiptDetail = new ReceiptDetail();
		receiptDetail.setReceiptId(receiptId);

		try {
			return receiptDetailDao.getReceiptDetailList(receiptDetail);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(receiptDetail), e);
		}

		return null;
	}

	@Override
	public String getWarehouse(Long shopId) {
		if (shopId == null) {
			return null;
		}

		try {
			return receiptDao.getWarehouse(shopId);
		} catch (Exception e) {
			logger.error(e);
		}

		return null;
	}

	public IReceiptDao getReceiptDao() {
		return receiptDao;
	}

	public void setReceiptDao(IReceiptDao receiptDao) {
		this.receiptDao = receiptDao;
	}

	public IReceiptDetailDao getReceiptDetailDao() {
		return receiptDetailDao;
	}

	public void setReceiptDetailDao(IReceiptDetailDao receiptDetailDao) {
		this.receiptDetailDao = receiptDetailDao;
	}

}
