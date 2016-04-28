package com.cmz.logsys;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.cmz.logsys.constant.GlobalConstant;
import com.cmz.logsys.domain.DataHeader;
import com.cmz.logsys.domain.DataMsg;
import com.cmz.logsys.domain.Resp;
import com.cmz.logsys.util.DataUtil;
import com.cmz.logsys.util.FileUtil;
import com.cmz.logsys.util.HttpUtil;
import com.google.gson.Gson;

public class SendTask implements Runnable {
	private final static ExecutorService SERVICE = Executors.newSingleThreadExecutor();
	public void run() {
		String fileName = FileUtil.COM_TMP_FILES.poll();
		if (fileName == null) {
			return;
		}

		for (String logType : GlobalConstant.logTypes) {
			String fileName1 = logType + File.separatorChar + fileName;
			File file = FileUtil.getFile(fileName1);
			if (!file.exists())
				return;
			SubSendTask subTask = new SubSendTask(logType, fileName,fileName1,file);
			SERVICE.execute(subTask);
		}

	}

	class SubSendTask implements Runnable {
		private String logType;
		private String fileName;
		private String fileName1;
		private File file;

		public SubSendTask(String logType, String fileName,String fileName1,File file) {
			this.logType = logType;
			this.fileName = fileName;
			this.fileName1 = fileName1;
			this.file = file;
		}

		public void run() {
			List<String> lines;
			HttpUtil httpUtil = new HttpUtil();
			try {
				lines = FileUtil.read(fileName1);

				StringBuilder builder = new StringBuilder();
				int incr = 0;
				int linecount = lines.size();
				for (String line : lines) {
					builder.append(line);
					incr += 1;
					if (linecount != incr) {
						builder.append("\r\n");
					}
				}

				DataHeader headers = DataUtil.genHeader(file.lastModified(), logType);
				DataMsg dataMsg = DataUtil.genMsg(headers, builder.toString());
				Gson gson = new Gson();
				String json = gson.toJson(dataMsg);

				// System.out.println(json);
				// httpUtil = new HttpUtil();
				String string = httpUtil.postJson(logType,fileName1, json);

				// System.out.println(fileName1+string);
				Resp resp = gson.fromJson(string, Resp.class);
				if (resp.getCode() == 0) {
					FileUtil.delete(fileName1);
				} else {
					FileUtil.COM_TMP_FILES.add(fileName);
				}
			} catch (IOException e) {
				FileUtil.COM_TMP_FILES.add(fileName);
				try {
					FileUtil.writeErrLog("打开文件失败,文件名：["+fileName+"]==异常信息==【"+e.getLocalizedMessage()+"】");
				} catch (IOException e1) {
					//e1.printStackTrace();
				}
			} finally {
				httpUtil.close();
			}

		}

	}

	/*
	 * public static void main(String[] args) { try { List<String> lines =
	 * FileUtil.read(
	 * "/Users/chenmingzhou/Documents/workspace18/logsys/data_logs/userreginfo/2016041817270"
	 * ); StringBuilder builder = new StringBuilder(); int incr = 0; int
	 * linecount = lines.size(); for(String line:lines){ builder.append(line);
	 * incr += 1; if(linecount!=incr){ builder.append("\r\n"); } }
	 * 
	 * System.out.println(builder.toString()); } catch (IOException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); }finally{
	 * 
	 * } }
	 */
}
