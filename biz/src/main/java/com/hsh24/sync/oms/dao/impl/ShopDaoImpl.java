package com.hsh24.sync.oms.dao.impl;

import com.hsh24.sync.api.oms.bo.Shop;
import com.hsh24.sync.framework.dao.impl.BaseDaoImpl;
import com.hsh24.sync.oms.dao.IShopDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ShopDaoImpl extends BaseDaoImpl implements IShopDao {

	@Override
	public Shop getShop(Shop shop) {
		return (Shop) getSqlMapClientTemplate().queryForObject("oms.shop.getShop", shop);
	}

}
