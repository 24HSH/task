package com.hsh24.sync.oms.dao;

import java.util.List;

import com.hsh24.sync.api.oms.bo.ItemConvert;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IItemConvertDao {

	/**
	 * 
	 * @param itemConvert
	 * @return
	 */
	List<ItemConvert> getItemConvert(ItemConvert itemConvert);

}
