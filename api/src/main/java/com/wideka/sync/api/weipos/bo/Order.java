package com.wideka.sync.api.weipos.bo;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @author JiakunXu
 * 
 */
public class Order implements Serializable {

	private static final long serialVersionUID = 7766350659055873347L;

	/**
	 * 常量，标识此消息类型.
	 */
	@JSONField(name = "template_code")
	private String template_code;

	/**
	 * 外部订单号,由调用方自定义.
	 */
	@JSONField(name = "out_order_no")
	private String outOrderNo;

	/**
	 * 消息内容结构体，显示在旺POS设备的标题栏.
	 */
	@JSONField(name = "msg_content")
	private MsgContent msgContent;

	/**
	 * 显示内容，HTML片段.
	 */
	@JSONField(name = "show_content")
	private String showContent;

	/**
	 * 打印模式：1-标准打印， 2-旺POS标签打印.
	 */
	@JSONField(name = "print_mode")
	private int printMode;

	/**
	 * 指令集合， 1-接单打印， 2-拒绝接单.
	 */
	@JSONField(name = "command")
	private int[] command;

	public String getTemplate_code() {
		return template_code;
	}

	public void setTemplate_code(String template_code) {
		this.template_code = template_code;
	}

	public String getOutOrderNo() {
		return outOrderNo;
	}

	public void setOutOrderNo(String outOrderNo) {
		this.outOrderNo = outOrderNo;
	}

	public MsgContent getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(MsgContent msgContent) {
		this.msgContent = msgContent;
	}

	public String getShowContent() {
		return showContent;
	}

	public void setShowContent(String showContent) {
		this.showContent = showContent;
	}

	public int getPrintMode() {
		return printMode;
	}

	public void setPrintMode(int printMode) {
		this.printMode = printMode;
	}

	public int[] getCommand() {
		return command;
	}

	public void setCommand(int[] command) {
		this.command = command;
	}
}
