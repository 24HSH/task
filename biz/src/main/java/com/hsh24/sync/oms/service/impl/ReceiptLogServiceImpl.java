package com.hsh24.sync.oms.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hsh24.sync.api.oms.IReceiptLogService;
import com.hsh24.sync.api.oms.bo.ReceiptLog;
import com.hsh24.sync.framework.bo.BooleanResult;
import com.hsh24.sync.framework.log.Logger4jCollection;
import com.hsh24.sync.framework.log.Logger4jExtend;
import com.hsh24.sync.framework.util.LogUtil;
import com.hsh24.sync.oms.dao.IReceiptLogDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ReceiptLogServiceImpl implements IReceiptLogService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(ReceiptLogServiceImpl.class);

	private IReceiptLogDao receiptLogDao;

	@Override
	public List<ReceiptLog> getReceiptLogList() {
		try {
			return receiptLogDao.getReceiptLogList();
		} catch (Exception e) {
			logger.error(e);
		}

		return null;
	}

	@Override
	public BooleanResult finishReceiptLog(Long id, String modifyUser) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (id == null) {
			result.setCode("收货日志信息不能为空。");
			return result;
		}

		if (StringUtils.isEmpty(modifyUser)) {
			result.setCode("操作人信息不能为空。");
			return result;
		}

		ReceiptLog receiptLog = new ReceiptLog();
		receiptLog.setId(id);
		receiptLog.setModifyUser(modifyUser);

		try {
			int c = receiptLogDao.finishReceiptLog(receiptLog);
			if (c == 1) {
				result.setResult(true);
			} else {
				result.setCode("更新收货日志失败。");
			}
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(receiptLog), e);
			result.setCode("更新收货日志表失败。");
		}

		return result;
	}

	public IReceiptLogDao getReceiptLogDao() {
		return receiptLogDao;
	}

	public void setReceiptLogDao(IReceiptLogDao receiptLogDao) {
		this.receiptLogDao = receiptLogDao;
	}

}
