package com.cmz.logsys;

import java.io.File;

import com.cmz.logsys.constant.GlobalConstant;
import com.cmz.logsys.util.FileUtil;
import com.google.common.io.Files;

public class ScanTask implements Runnable {

	public void run() {
		scan();
	}
	
	private void scan(){
		String dir1 = GlobalConstant.logDir+File.separatorChar;
		File dir = new File(dir1);
		if(dir.isDirectory()){
			String[] subDirs = dir.list();
			for(String name : subDirs){
				String logName = dir1+name;
				File logType = new File(logName);
				String[] logs = logType.list();
				if(logs!=null && logs.length>0){
					for(String log:logs){
						String logfile = Files.getNameWithoutExtension(log);
						if(!FileUtil.COM_TMP_FILES.contains(logfile)){
							FileUtil.COM_TMP_FILES.add(logfile);
						}
					}
					
				}
			}
		}
	}

}
