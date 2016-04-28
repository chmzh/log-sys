package com.cmz.logsys;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.codec.Charsets;

import com.cmz.logsys.RetryOnLineLog.RetryOnLineSubLog;
import com.cmz.logsys.constant.GlobalConstant;
import com.cmz.logsys.util.FileUtil;
import com.cmz.logsys.util.HttpUtil;
import com.google.common.io.Files;

public class ReTryOfflineLog implements Runnable {
	
	private void retry(){
		FileUtil.getRetryFiles();
		for(Map.Entry<String, ConcurrentLinkedQueue<String>> entry : FileUtil.OFF_LINE_RETRY_FILES.entrySet()){
			ConcurrentLinkedQueue<String> queue = entry.getValue();
				String fileName = null;
				while((fileName = queue.poll())!=null){
					String logType = entry.getKey();
					String fileName1 = logType+File.separatorChar+fileName;

					File file = FileUtil.getFile(fileName1);
					if (!file.exists())
						return;
					PostTask subTask = new PostTask(file,logType, fileName1);
					RetryOnLineLog.SERVICE.execute(subTask);
				}
				

			
			
		}
	}
	
	class PostTask implements Runnable{
		private String logType;
		private String fileName;
		private File file;
		public PostTask(File file,String logType,String fileName) {
			this.file = file;
			this.fileName = fileName;
			this.logType = logType;
		}
		public void run() {
			String line;
			HttpUtil httpUtil = new HttpUtil();
			try {
				if(file.exists()){
					line = Files.readFirstLine(file, Charsets.UTF_8);
					
					String mString = httpUtil.postJson(logType, fileName, line);
					if(GlobalConstant.debug){
						System.out.println(logType+"==>"+fileName+"==>"+mString);
					}
				}

			} catch (IOException e) {
				try {
					FileUtil.writeErrLog("打开文件失败,文件名：[" +logType+File.separatorChar+ fileName + "]==异常信息==【" + e.getLocalizedMessage() + "】");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}finally{
				
			}
			
		}
		
	}

	public void run() {
		retry();
	}
}
