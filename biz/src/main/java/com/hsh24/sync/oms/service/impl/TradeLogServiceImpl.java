package com.hsh24.sync.oms.service.impl;

import java.util.List;

import com.hsh24.sync.api.oms.ITradeLogService;
import com.hsh24.sync.api.oms.bo.TradeLog;
import com.hsh24.sync.framework.bo.BooleanResult;
import com.hsh24.sync.framework.log.Logger4jCollection;
import com.hsh24.sync.framework.log.Logger4jExtend;
import com.hsh24.sync.oms.dao.ITradeLogDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class TradeLogServiceImpl implements ITradeLogService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(TradeLogServiceImpl.class);

	private ITradeLogDao tradeLogDao;

	@Override
	public List<TradeLog> getTradeLogList() {
		try {
			return tradeLogDao.getTradeLogList();
		} catch (Exception e) {
			logger.error(e);
		}

		return null;
	}

	@Override
	public BooleanResult finishTradeLog(Long id, String modifyUser) {
		
		
		// TODO Auto-generated method stub
		return null;
	}

}
