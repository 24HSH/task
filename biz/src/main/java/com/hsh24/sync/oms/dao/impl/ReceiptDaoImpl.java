package com.hsh24.sync.oms.dao.impl;

import com.hsh24.sync.api.oms.bo.Receipt;
import com.hsh24.sync.framework.dao.impl.BaseDaoImpl;
import com.hsh24.sync.oms.dao.IReceiptDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ReceiptDaoImpl extends BaseDaoImpl implements IReceiptDao {

	@Override
	public Receipt getReceipt(Receipt receipt) {
		return (Receipt) getSqlMapClientTemplate().queryForObject("oms.receipt.getReceipt", receipt);
	}

}
