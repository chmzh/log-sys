package com.cmz.logsys;

import com.cmz.logsys.domain.DataHeader;
import com.cmz.logsys.domain.DataMsg;
import com.cmz.logsys.util.DataUtil;
import com.cmz.logsys.util.HttpUtil;
import com.google.gson.Gson;

public class NetSender implements Sender {
	
	
	public String send(String logType, String body) {
		
		return send0(logType, body);
				
	}
	
	private String send0(String logType,String body){
		DataHeader headers = DataUtil.genHeader(System.currentTimeMillis(), logType);
		DataMsg dataMsg = DataUtil.genMsg(headers, body);
		Gson gson = new Gson();
		String json = gson.toJson(dataMsg);
		HttpUtil httpUtil = new HttpUtil();
		String mString = httpUtil.postJson(logType,"", json);	
		return mString;
	}
	
	class SubTask implements Runnable{

		private String logType;
		private String body;
		
		public SubTask(String logType,String body) {
			this.logType = logType;
			this.body = body;
		}
		
		public void run() {
			send0(logType, body);
		}
		
		
		
	}

}
