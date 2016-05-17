package com.hsh24.sync.oms.dao.impl;

import java.util.List;

import com.hsh24.sync.api.oms.bo.Order;
import com.hsh24.sync.framework.dao.impl.BaseDaoImpl;
import com.hsh24.sync.oms.dao.IOrderDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class OrderDaoImpl extends BaseDaoImpl implements IOrderDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getOrderList(Order order) {
		return getSqlMapClientTemplate().queryForList("oms.trade.order.getOrderList", order);
	}

}
