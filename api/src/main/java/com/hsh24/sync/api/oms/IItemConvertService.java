package com.hsh24.sync.api.oms;

import java.util.List;

import com.hsh24.sync.api.oms.bo.ItemConvert;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IItemConvertService {

	/**
	 * 
	 * @param barCode
	 * @return
	 */
	List<ItemConvert> getItemConvert(String barCode);

}
