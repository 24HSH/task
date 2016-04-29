package com.wideka.sync.api.wangpos.bo;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @author JiakunXu
 * 
 */
public class CustOrder extends Order {

	private static final long serialVersionUID = 5566836043490878815L;
	/**
	 * 旺POS标签打印内容.
	 */
	@JSONField(name = "print_content")
	private PrintContent[] printContent;

	public PrintContent[] getPrintContent() {
		return printContent;
	}

	public void setPrintContent(PrintContent[] printContent) {
		this.printContent = printContent;
	}

}
