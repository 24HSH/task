package com.hsh24.sync.api.wangpos.bo;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @author JiakunXu
 * 
 */
public class StdOrder extends Order {

	private static final long serialVersionUID = 8102644501291322273L;

	/**
	 * 标准打印内容，这是对原始内容进行Base64编码（GBK）后的内容.
	 */
	@JSONField(name = "print_content")
	private String printContent;

	public String getPrintContent() {
		return printContent;
	}

	public void setPrintContent(String printContent) {
		this.printContent = printContent;
	}

}
