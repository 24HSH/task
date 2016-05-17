package com.hsh24.sync.oms.dao;

import java.util.List;

import com.hsh24.sync.api.oms.bo.Order;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IOrderDao {

	/**
	 * 
	 * @param order
	 * @return
	 */
	List<Order> getOrderList(Order order);

}
