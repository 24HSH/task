package com.hsh24.sync.oms.dao.impl;

import java.util.List;

import com.hsh24.sync.api.oms.bo.ItemConvert;
import com.hsh24.sync.framework.dao.impl.BaseDaoImpl;
import com.hsh24.sync.oms.dao.IItemConvertDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ItemConvertDaoImpl extends BaseDaoImpl implements IItemConvertDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemConvert> getItemConvert(ItemConvert itemConvert) {
		return (List<ItemConvert>) getSqlMapClientTemplate().queryForList("oms.item.convert.getItemConvert",
			itemConvert);
	}

}
