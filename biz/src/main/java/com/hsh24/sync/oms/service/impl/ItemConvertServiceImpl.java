package com.hsh24.sync.oms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hsh24.sync.api.oms.IItemConvertService;
import com.hsh24.sync.api.oms.bo.ItemConvert;
import com.hsh24.sync.framework.log.Logger4jCollection;
import com.hsh24.sync.framework.log.Logger4jExtend;
import com.hsh24.sync.framework.util.LogUtil;
import com.hsh24.sync.oms.dao.IItemConvertDao;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class ItemConvertServiceImpl implements IItemConvertService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(ItemConvertServiceImpl.class);

	@Resource
	private IItemConvertDao itemConvertDao;

	@Override
	public List<ItemConvert> getItemConvert(String barCode) {
		if (StringUtils.isBlank(barCode)) {
			return null;
		}

		ItemConvert itemConvert = new ItemConvert();
		itemConvert.setBarCode(barCode);

		try {
			return itemConvertDao.getItemConvert(itemConvert);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(itemConvert), e);
		}

		return null;
	}

}
