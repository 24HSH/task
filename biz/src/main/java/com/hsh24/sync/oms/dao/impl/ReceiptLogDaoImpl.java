package com.hsh24.sync.oms.dao.impl;

import java.util.List;

import com.hsh24.sync.api.oms.bo.ReceiptLog;
import com.hsh24.sync.framework.dao.impl.BaseDaoImpl;
import com.hsh24.sync.oms.dao.IReceiptLogDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ReceiptLogDaoImpl extends BaseDaoImpl implements IReceiptLogDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<ReceiptLog> getReceiptLogList() {
		return getSqlMapClientTemplate().queryForList("oms.receipt.log.getReceiptLogList");
	}

	@Override
	public int finishReceiptLog(ReceiptLog receiptLog) {
		return getSqlMapClientTemplate().update("oms.receipt.log.finishReceiptLog", receiptLog);
	}

	@Override
	public int recordReceiptLog(ReceiptLog receiptLog) {
		return getSqlMapClientTemplate().update("oms.receipt.log.recordReceiptLog", receiptLog);
	}

}
