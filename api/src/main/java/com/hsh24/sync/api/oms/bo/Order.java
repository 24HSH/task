package com.hsh24.sync.api.oms.bo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 订单.
 * 
 * @author JiakunXu
 * 
 */
public class Order implements Serializable {

	private static final long serialVersionUID = -1206444713292456901L;

	private Long orderId;

	/**
	 * 交易ID.
	 */
	private Long tradeId;

	/**
	 * 商品 ID.
	 */
	@JSONField(name = "goodsId")
	private Long itemId;

	/**
	 * 商品名称.
	 */
	private String itemName;

	private Long skuId;

	/**
	 * sku所对应的销售属性的中文名字串，格式如：Pid1:vid1:pid_name1:vid_name1;Pid2:vid2:pid_name2:vid_name2.
	 */
	private String propertiesName;

	/**
	 * 购买该sku商品的数量.
	 */
	private int quantity;

	/**
	 * 购买价格.
	 */
	private BigDecimal price;

	/**
	 * 操作人ID.
	 */
	private String modifyUser;

	// >>>>>>>>>>以下是辅助属性<<<<<<<<<<

	/**
	 * 供应商id 创建时权限控制.
	 */
	private Long supId;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getTradeId() {
		return tradeId;
	}

	public void setTradeId(Long tradeId) {
		this.tradeId = tradeId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public String getPropertiesName() {
		return propertiesName;
	}

	public void setPropertiesName(String propertiesName) {
		this.propertiesName = propertiesName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * 某商品总价格.
	 * 
	 * @return
	 */
	public BigDecimal getTotal() {
		if (this.price != null) {
			return this.price.multiply(new BigDecimal(this.quantity));
		}

		return BigDecimal.ZERO;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Long getSupId() {
		return supId;
	}

	public void setSupId(Long supId) {
		this.supId = supId;
	}

}
