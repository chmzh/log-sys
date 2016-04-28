package com.cmz.logsys.domain;

public class DataMsg {
	private DataHeader headers;
	private String body;
	public DataHeader getHeaders() {
		return headers;
	}
	public void setHeaders(DataHeader headers) {
		this.headers = headers;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
}
