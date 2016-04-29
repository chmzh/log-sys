package com.cmz.logsys.constant;

public class GlobalConstant {
	public static String gameId = "";  //游戏ID
	public static String key = "";    //加密KEY
	public static String logDir = "";  //日志的绝对路径
	public static String errLogDir = "";  //错误信息的绝对路径
	public static int rollTime = 30;    
	
	public static int delay = 5;       
	public static int period = 60;      
	
	public static int retryThreads = 3;
	
	public static String host = "cloud.dovogame.com";
	public static int port = 8080;
	
	public static boolean debug = false;
	
	public static int connections = 1;
	
	public final static String userreginfo = "userreginfo";
	public final static String userlogininfo = "userlogininfo";
	public final static String serverselinfo = "serverselinfo";
	public final static String rolecreateinfo = "rolecreateinfo";
	public final static String rolelogininfo = "rolelogininfo";
	public final static String rolelvupinfo = "rolelvupinfo";
	public final static String rolecancelinfo = "rolecancelinfo";
	public final static String payrequestinfo = "payrequestinfo";
	public final static String paysucinfo = "paysucinfo";
	public final static String rewardinfo = "rewardinfo";
	public final static String consumeinfo = "consumeinfo";
	public final static String missioninfo = "missioninfo";
	public final static String userlogoutinfo = "userlogoutinfo";
	
	
	public final static String[] logTypes = new String[]{userreginfo,
			userlogininfo,
			serverselinfo,
			rolecreateinfo,
			rolelogininfo,
			rolelvupinfo,
			rolecancelinfo,
			payrequestinfo,
			paysucinfo,
			rewardinfo,
			consumeinfo,
			missioninfo,
			userlogoutinfo};
}
