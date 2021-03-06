package com.hsh24.sync.oms.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hsh24.sync.api.oms.IShopService;
import com.hsh24.sync.api.oms.bo.Shop;
import com.hsh24.sync.framework.log.Logger4jCollection;
import com.hsh24.sync.framework.log.Logger4jExtend;
import com.hsh24.sync.framework.util.LogUtil;
import com.hsh24.sync.oms.dao.IShopDao;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class ShopServiceImpl implements IShopService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(ShopServiceImpl.class);

	@Resource
	private IShopDao shopDao;

	@Override
	public Shop getShop(Long shopId) {
		if (shopId == null) {
			return null;
		}

		Shop shop = new Shop();
		shop.setShopId(shopId);

		try {
			return shopDao.getShop(shop);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(shop), e);
		}

		return null;
	}

}
