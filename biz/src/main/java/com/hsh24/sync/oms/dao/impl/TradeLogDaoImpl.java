package com.hsh24.sync.oms.dao.impl;

import java.util.List;

import com.hsh24.sync.api.oms.bo.TradeLog;
import com.hsh24.sync.framework.dao.impl.BaseDaoImpl;
import com.hsh24.sync.oms.dao.ITradeLogDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class TradeLogDaoImpl extends BaseDaoImpl implements ITradeLogDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<TradeLog> getTradeLogList() {
		return getSqlMapClientTemplate().queryForList("oms.trade.log.getTradeLogList");
	}

}
