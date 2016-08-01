package com.hsh24.sync.api.oms.bo;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.ibatis.type.Alias;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 订单.
 * 
 * @author JiakunXu
 * 
 */
@Alias("omsOrder")
public class Order implements Serializable {

	private static final long serialVersionUID = -1206444713292456901L;

	@JSONField(serialize = false)
	private Long orderId;

	/**
	 * 交易ID.
	 */
	@JSONField(serialize = false)
	private Long tradeId;

	/**
	 * 商品 ID.
	 */
	@JSONField(serialize = false)
	private Long itemId;

	@JSONField(name = "goodsId")
	private String itemCode;

	/**
	 * 商品名称.
	 */
	@JSONField(serialize = false)
	private String itemName;

	@JSONField(serialize = false)
	private Long skuId;

	/**
	 * sku所对应的销售属性的中文名字串，格式如：Pid1:vid1:pid_name1:vid_name1;Pid2:vid2:pid_name2:vid_name2.
	 */
	@JSONField(serialize = false)
	private String propertiesName;

	/**
	 * 购买该sku商品的数量.
	 */
	@JSONField(name = "goodsNum")
	private int quantity;

	/**
	 * 购买价格.
	 */
	@JSONField(name = "goodsFactPrice")
	private BigDecimal price;

	/**
	 * 操作人ID.
	 */
	@JSONField(name = "createBy")
	private String createUser;

	// >>>>>>>>>>以下是辅助属性<<<<<<<<<<

	@JSONField(name = "orgId")
	private Long shopId;

	private String goodsSpecCd = "";

	private String remark = "";

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

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
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

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getGoodsSpecCd() {
		return goodsSpecCd;
	}

	public void setGoodsSpecCd(String goodsSpecCd) {
		this.goodsSpecCd = goodsSpecCd;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
