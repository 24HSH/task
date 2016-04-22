package com.wideka.sync.api.weipos.bo;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 消息内容结构体，显示在旺POS设备的标题栏.
 * 
 * @author JiakunXu
 * 
 */
public class MsgContent implements Serializable {

	private static final long serialVersionUID = 5458842310478197559L;

	/**
	 * 消息标题.
	 */
	@JSONField(name = "title")
	private String title;

	/**
	 * 消息语音.
	 */
	@JSONField(name = "voice")
	private String voice;

	/**
	 * 消息描述.
	 */
	@JSONField(name = "desc")
	private String desc;

	/**
	 * 消息内容详情.
	 */
	@JSONField(name = "desc_detail")
	private String descDetail;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVoice() {
		return voice;
	}

	public void setVoice(String voice) {
		this.voice = voice;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDescDetail() {
		return descDetail;
	}

	public void setDescDetail(String descDetail) {
		this.descDetail = descDetail;
	}

}
