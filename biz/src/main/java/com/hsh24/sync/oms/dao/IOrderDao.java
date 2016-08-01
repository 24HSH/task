package com.hsh24.sync.oms.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hsh24.sync.api.oms.bo.Order;

/**
 * 
 * @author JiakunXu
 * 
 */
@Repository("omsOrderDao")
public interface IOrderDao {

	/**
	 * 
	 * @param order
	 * @return
	 */
	List<Order> getOrderList(Order order);

}
