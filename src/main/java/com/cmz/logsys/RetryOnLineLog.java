package com.cmz.logsys;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.codec.Charsets;

import com.cmz.logsys.constant.GlobalConstant;
import com.cmz.logsys.domain.LogInfo;
import com.cmz.logsys.util.FileUtil;
import com.cmz.logsys.util.HttpUtil;
import com.google.common.io.Files;

public class RetryOnLineLog implements Runnable {

	final static ExecutorService SERVICE = Executors.newFixedThreadPool(GlobalConstant.retryThreads);

	public void run() {
		if(GlobalConstant.debug){
			System.out.println("剩余日志数:"+FileUtil.ON_LINE_RETRY_FILES.size());
		}
		String logInfo = null;
		while ((logInfo = FileUtil.ON_LINE_RETRY_FILES.poll()) != null) {
			
			int index = logInfo.indexOf("/");
			String logType = logInfo.substring(0, index);
			
			String  fileName= logInfo.substring(index+1);
			String fileName1 = logType + File.separatorChar + fileName;

			File file = FileUtil.getFile(fileName1);
			if (!file.exists())
				return;
			RetryOnLineSubLog subTask = new RetryOnLineSubLog(logType, fileName1, file);
			RetryOnLineLog.SERVICE.execute(subTask);
		}

	}

	class RetryOnLineSubLog implements Runnable {
		private String logType;
		private String fileName;
		private File file;

		public RetryOnLineSubLog(String logType, String fileName, File file) {
			this.logType = logType;
			this.fileName = fileName;
			this.file = file;
		}

		public void run() {
			String line;
			HttpUtil httpUtil = new HttpUtil();
			try {
				if (file.exists()) {
					line = Files.readFirstLine(file, Charsets.UTF_8);

					String mString = httpUtil.postJson(logType, fileName, line);
					if (GlobalConstant.debug) {
						System.out.println(logType + "==>" + fileName + "==>" + mString);
					}
				}

				// System.out.println(msg);
			} catch (IOException e) {
				try {
					FileUtil.writeErrLog("打开文件失败,文件名：[" + fileName + "]==异常信息==【" + e.getLocalizedMessage() + "】");
				} catch (IOException e1) {
					// e1.printStackTrace();
				}

			} finally {

			}
		}
	}

}
