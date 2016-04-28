package com.cmz.logsys.domain;

public class Message {
	private int code;
	private String msg;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public Message(int code,String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public Message(int code) {
		this.code = 0;
	}
}
