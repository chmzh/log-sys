package com.cmz.logsys;

import java.io.IOException;

import com.cmz.logsys.util.FileUtil;

public class FileSender implements Sender {

	public String send(String logType, String body) {
		try {
			FileUtil.append(logType, body);
			return "{\"code\":0,\"msg\":\"成功\"}";
		} catch (IOException e) {
			return "{\"code\":-1,\"msg\":\""+e.getLocalizedMessage()+"\"}";
		}
		
	}
	
}
