package com.cmz.logsys.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

import javax.security.auth.login.LoginContext;

import org.apache.commons.codec.Charsets;

import com.cmz.logsys.constant.GlobalConstant;
import com.cmz.logsys.domain.LogInfo;
import com.google.common.io.Files;

/**
 * @author mingzhou.chen 28458942@qq.com
 * @since 2015年12月25日 下午4:55:49
 */
public final class FileUtil {

	private static volatile String tmpcur = "";
	private static Object lock = new Object();
	
	private static Object lock1 = new Object();
	
	
	/**
	 * 已完成的日志文件,即可发送到日志系统
	 */
	public final static ConcurrentLinkedQueue<String> COM_TMP_FILES = new ConcurrentLinkedQueue<String>();

	public final static ConcurrentLinkedQueue<String> ON_LINE_RETRY_FILES = new ConcurrentLinkedQueue<String>();

	public final static ConcurrentMap<String,ConcurrentLinkedQueue<String>> OFF_LINE_RETRY_FILES = new ConcurrentHashMap<String,ConcurrentLinkedQueue<String>>();
	
	/**
	 * 每一分产生一个文件
	 * 
	 * @param content
	 * @throws IOException
	 */
	public static void append(String logType, String content) throws IOException {
		String dir = GlobalConstant.logDir + File.separatorChar + logType + File.separatorChar;
		mkdir(dir);
		String fileName = GlobalConstant.logDir + File.separatorChar +getFileName(logType+File.separatorChar);
		final File newFile = new File(fileName);
		if (!newFile.exists()) {
			newFile.createNewFile();
		}
		Files.append(content + "\r\n", newFile, Charsets.UTF_8);
	}

	public static void mkdir(String dirs) throws IOException {
		File file = new File(dirs);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public static void getRetryFiles(){
		
		String parentDir = GlobalConstant.logDir+File.separatorChar;
		String logTypeDir = "";
		for(String logType:GlobalConstant.logTypes){
			logTypeDir = parentDir+logType + File.separatorChar;
			File file = new File(logTypeDir);
			if(!file.exists()){
				continue;
			}

			String[] dateDirs = file.list();
			if(null!=dateDirs){


				ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();
				OFF_LINE_RETRY_FILES.putIfAbsent(logType, queue);
				
				for(String dateDir:dateDirs){
					File file1 = new File(logTypeDir+dateDir);
					if(!file1.exists() || !file1.isDirectory()){
						continue;
					}
					
					
					String[] logFiles = file1.list();
					if(null!=logFiles){
						for(String logFile:logFiles){
							OFF_LINE_RETRY_FILES.get(logType).add(dateDir+File.separatorChar+logFile);
						}
					}
				}
				
			}
		}
	}
	
	
	public static void writeRetryFile(String logType,String content) throws IOException{
		String dateDir = getRetryDirName();
		String dir = GlobalConstant.logDir + File.separatorChar + logType+File.separatorChar+dateDir;
		mkdir(dir);
		long nanoTime = System.nanoTime();
		String fileName = "event_"+nanoTime+".log";
		final File newFile = new File(dir +fileName);
		String name = dateDir+fileName;
//		if (!newFile.exists()) {
//			
//			newFile.createNewFile();
//			
//		}
		ON_LINE_RETRY_FILES.add(logType+File.separatorChar+name);
		Files.write(content, newFile, Charsets.UTF_8);
		if(GlobalConstant.debug){
			System.out.println(logType+",发送日志失败，重新添加到队列:["+name+"],剩余日志数:"+ON_LINE_RETRY_FILES.size());
		}
		
		
	}
	
	public static void writeErrLog(String content) throws IOException{
		String dir = GlobalConstant.logDir + File.separatorChar;
		mkdir(dir);
		String errFile = toTime(System.currentTimeMillis(),"yyyy-MM-dd");
		String fileName = GlobalConstant.logDir + File.separatorChar +"err_"+errFile+".log";
		final File newFile = new File(fileName);
		if (!newFile.exists()) {
			newFile.createNewFile();
		}
		Files.append(content + "\r\n", newFile, Charsets.UTF_8);
	}
	
	public static String getRetryDirName(){
		long millis = System.currentTimeMillis();
		
		String dateFileName = toTime(millis, "yyyy-MM-dd");
		String dir = dateFileName + File.separatorChar;
		return dir;
	}
	
	public static String getFileName(String dir) {
		long millis = System.currentTimeMillis();
		String cur = toTime(millis);
		String retFileName = "";

		if (!tmpcur.equals(cur)) {
			if(!tmpcur.equals("")){
				synchronized (lock) {
					if(!COM_TMP_FILES.contains(tmpcur)){
						COM_TMP_FILES.add(tmpcur);
					}
				}
			}
			tmpcur = cur;
			
		}
		retFileName = dir + tmpcur + ".log";
		return retFileName;
	}

	public static List<String> read(String fileName) throws IOException{
		File file = getFile(fileName);
		return Files.readLines(file, Charsets.UTF_8);
	}
	
	public static File getFile(String fileName){
		fileName = GlobalConstant.logDir + File.separatorChar + fileName;
		if(!fileName.endsWith(".log")){
			fileName = fileName+".log";
		}
		File file = new File(fileName);
		return file;
	}
	
	public static void delete(String fileName){
		
		fileName = GlobalConstant.logDir + File.separatorChar + fileName;
		if(!fileName.endsWith(".log")){
			fileName = fileName+".log";
		}
		
		File file = new File(fileName);
		if(file.exists()){
			file.delete();
		}
	}
	
	
	public static void move(String fromName,String toName) throws IOException{
		if(!fromName.equals("") && !toName.equals("")){
			File from = new File(fromName);
			File to = new File(toName);
			if (from.exists()) {
				to.setLastModified(from.lastModified());
				Files.move(from, to);
			}
		}
	}
	
	/**
	 * 将微秒以指定格式输出为本地时间字符串
	 * 
	 * @param millis
	 * @param format
	 *            yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String toTime(long millis, String format) {
		Date date = new Date(millis);
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}

	public static String toTime(long millis) {
		int ss = Integer.valueOf(toTime(millis, "s"));
		int iss = ss / GlobalConstant.rollTime; // 10秒产生一个新文件
		String time = toTime(millis, "yyyyMMddHHmm") + iss;
		return time;
	}

}
