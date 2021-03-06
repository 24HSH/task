package com.hsh24.sync.oms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hsh24.sync.api.oms.ITradeLogService;
import com.hsh24.sync.api.oms.bo.TradeLog;
import com.hsh24.sync.framework.bo.BooleanResult;
import com.hsh24.sync.framework.log.Logger4jCollection;
import com.hsh24.sync.framework.log.Logger4jExtend;
import com.hsh24.sync.framework.util.LogUtil;
import com.hsh24.sync.oms.dao.ITradeLogDao;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service("omsTradeLogService")
public class TradeLogServiceImpl implements ITradeLogService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(TradeLogServiceImpl.class);

	@Resource(name = "omsTradeLogDao")
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
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (id == null) {
			result.setCode("交易日志信息不能为空");
			return result;
		}

		if (StringUtils.isEmpty(modifyUser)) {
			result.setCode("操作人信息不能为空");
			return result;
		}

		TradeLog tradeLog = new TradeLog();
		tradeLog.setId(id);
		tradeLog.setModifyUser(modifyUser);

		try {
			int c = tradeLogDao.finishTradeLog(tradeLog);
			if (c == 1) {
				result.setResult(true);
			} else {
				result.setCode("更新交易日志失败");
			}
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(tradeLog), e);
			result.setCode("更新交易日志表失败");
		}

		return result;
	}

	@Override
	public BooleanResult recordTradeLog(Long id, String e, String modifyUser) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (id == null) {
			result.setCode("交易日志信息不能为空");
			return result;
		}

		if (StringUtils.isBlank(e)) {
			result.setCode("日志信息不能为空");
			return result;
		}

		if (StringUtils.isEmpty(modifyUser)) {
			result.setCode("操作人信息不能为空");
			return result;
		}

		TradeLog tradeLog = new TradeLog();
		tradeLog.setId(id);
		tradeLog.setE(e);
		tradeLog.setModifyUser(modifyUser);

		try {
			int c = tradeLogDao.recordTradeLog(tradeLog);
			if (c == 1) {
				result.setResult(true);
			} else {
				result.setCode("更新交易日志失败");
			}
		} catch (Exception ex) {
			logger.error(LogUtil.parserBean(tradeLog), ex);
			result.setCode("更新交易日志表失败");
		}

		return result;
	}

}
