package com.hsh24.sync.oms.dao.impl;

import java.util.List;

import com.hsh24.sync.api.oms.bo.ReceiptDetail;
import com.hsh24.sync.framework.dao.impl.BaseDaoImpl;
import com.hsh24.sync.oms.dao.IReceiptDetailDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ReceiptDetailDaoImpl extends BaseDaoImpl implements IReceiptDetailDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<ReceiptDetail> getReceiptDetailList(ReceiptDetail receiptDetail) {
		return (List<ReceiptDetail>) getSqlMapClientTemplate().queryForList("oms.receipt.detail.getReceiptDetailList",
			receiptDetail);
	}

}
