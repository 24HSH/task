package com.hsh24.sync.api.oms;

import com.hsh24.sync.api.oms.bo.Shop;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IShopService {

	/**
	 * 
	 * @param shopId
	 * @return
	 */
	Shop getShop(Long shopId);

}
