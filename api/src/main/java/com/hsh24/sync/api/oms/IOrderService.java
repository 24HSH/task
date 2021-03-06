package com.hsh24.sync.api.oms;

import java.util.List;

import com.hsh24.sync.api.oms.bo.Order;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IOrderService {

	/**
	 * 
	 * @param tradeId
	 * @return
	 */
	List<Order> getOrderList(Long tradeId);

}
