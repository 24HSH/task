package com.wideka.sync.api.weipos.bo;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 旺POS标签打印内容.
 * 
 * @author JiakunXu
 * 
 */
public class PrintContent implements Serializable {

	private static final long serialVersionUID = 3248716595134926830L;

	/**
	 * 本行内容.
	 */
	@JSONField(name = "content")
	private String content;

	/**
	 * 显示位置.
	 */
	@JSONField(name = "gravity")
	private String gravity;

	/**
	 * 字体设置.
	 */
	@JSONField(name = "font_style")
	private String fontStyle;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getGravity() {
		return gravity;
	}

	public void setGravity(String gravity) {
		this.gravity = gravity;
	}

	public String getFontStyle() {
		return fontStyle;
	}

	public void setFontStyle(String fontStyle) {
		this.fontStyle = fontStyle;
	}

}
