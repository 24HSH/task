package com.hsh24.sync.oms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hsh24.sync.api.oms.IOrderService;
import com.hsh24.sync.api.oms.bo.Order;
import com.hsh24.sync.framework.log.Logger4jCollection;
import com.hsh24.sync.framework.log.Logger4jExtend;
import com.hsh24.sync.oms.dao.IOrderDao;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service("omsOrderService")
public class OrderServiceImpl implements IOrderService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(OrderServiceImpl.class);

	@Resource(name = "omsOrderDao")
	private IOrderDao orderDao;

	@Override
	public List<Order> getOrderList(Long tradeId) {
		if (tradeId == null) {
			return null;
		}

		Order order = new Order();
		order.setTradeId(tradeId);

		try {
			return orderDao.getOrderList(order);
		} catch (Exception e) {
			logger.error(e);
		}

		return null;
	}

}
