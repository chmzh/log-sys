package com.cmz.logsys;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.cmz.logsys.constant.GlobalConstant;
import com.cmz.logsys.loginfo.ConsumeInfo;
import com.cmz.logsys.loginfo.MissionInfo;
import com.cmz.logsys.loginfo.PayRequestInfo;
import com.cmz.logsys.loginfo.PaySucInfo;
import com.cmz.logsys.loginfo.RewardInfo;
import com.cmz.logsys.loginfo.RoleCancelInfo;
import com.cmz.logsys.loginfo.RoleCreateInfo;
import com.cmz.logsys.loginfo.RoleLoginInfo;
import com.cmz.logsys.loginfo.RoleLvUpInfo;
import com.cmz.logsys.loginfo.ServerSelInfo;
import com.cmz.logsys.loginfo.UserLoginInfo;
import com.cmz.logsys.loginfo.UserLogoutInfo;
import com.cmz.logsys.loginfo.UserRegInfo;
import com.cmz.logsys.util.DataUtil;
import com.cmz.logsys.util.FileUtil;

public class LogClient  {
	
	public static volatile Boolean inited = false;

	private final static ScheduledExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();
	
	private final static Sender SENDER = new NetSender();
	
	
	/**
	 * 游戏ID 由数据中心提供
	 * @param gameId
	 */
	public static void setGameId(String gameId){
		GlobalConstant.gameId = gameId;
	}
	
	/**
	 * 日志目录，游戏填写，绝对路径
	 * @param logDir
	 */
	public static void setLogDir(String logDir){
		GlobalConstant.logDir = logDir;
	}
	
	public static void setConnections(int connections){
		GlobalConstant.connections = connections;
	}

	public static void setRetryThreads(int retryThreads){
		if(retryThreads>=1){
			GlobalConstant.retryThreads = retryThreads;
		}
	}
	
	public static void setDebug(boolean flag){
		GlobalConstant.debug = flag;
	}
	
//	public static void setErrLogDir(String errLogDir){
//		GlobalConstant.errLogDir = errLogDir;
//	}
	
	/**
	 * 加密KEY，由数据中心提供
	 * @param key
	 */
	public static void setKey(String key){
		GlobalConstant.key = key;
	}
	
	public static void setRollTime(int rollTime){
		//GlobalConstant.rollTime = rollTime;
	}
	
	public static void setDelay(int delay){
		//GlobalConstant.delay = delay;
	}
	
	public static void setPeriod(int period){
		//GlobalConstant.period = period;
	}
	
	public static void start() throws IOException,Exception{
		if(GlobalConstant.gameId.equals("")){
			throw new Exception("请填写GameId");
		}
		if(GlobalConstant.key.equals("")){
			throw new Exception("请填写key");
		}
		if(GlobalConstant.logDir.equals("")){
			throw new Exception("请填写日志目录");
		}
		if(!GlobalConstant.logDir.equals("")){
			FileUtil.mkdir(GlobalConstant.logDir);
		}
		
		ReTryOfflineLog task = new ReTryOfflineLog();
		Thread thread = new Thread(task);
		thread.start();
		
		RetryOnLineLog onLineLogTask = new RetryOnLineLog();
		EXECUTOR_SERVICE.scheduleAtFixedRate(onLineLogTask, GlobalConstant.delay, GlobalConstant.period, TimeUnit.SECONDS);
		
		
//		ScanTask scanTask = new ScanTask();
//		Thread scanThread = new Thread(scanTask);
//		scanThread.start();

		//SendTask sendTask = new SendTask();
		//EXECUTOR_SERVICE.scheduleAtFixedRate(sendTask, GlobalConstant.delay, GlobalConstant.period, TimeUnit.SECONDS);

		

		inited = true;
	}
//	
//	public static void stop(){
//		EXECUTOR_SERVICE.shutdown();
//		inited = false;
//	}
//	
//	public static void restart(){
//		EXECUTOR_SERVICE.scheduleAtFixedRate(new SendTask(), 10, 5, TimeUnit.SECONDS);
//		inited = true;
//	}
	
	/**
	 * 1 用户注册信息
	 * @param userType
	 * @param gender
	 * @param age
	 * @param gameServer
	 * @param serverId
	 * @param areaId
	 * @param playerId
	 * @param roleName
	 * @param userName
	 * @param col1
	 * @param col2
	 * @param col3
	 * @param ts
	 * @throws IOException 
	 * @throws CException 
	 */
	public static void sendUserRegInfo(String userType,String gender,int age,String gameServer,String serverId,String areaId,String playerId,String roleName,String userName,String col1,String col2,String col3,int ts) throws IOException, CException{
		isInited();
		validateTs(ts);
		String body = DataUtil.genBody(userType,gender,age,gameServer,serverId,areaId,playerId,roleName,userName,col1,col2,col3,ts);
		//FileUtil.append(GlobalConstant.userreginfo,body);
		SENDER.send(GlobalConstant.userreginfo, body);
	}
	
	public static void sendUserRegInfo(UserRegInfo info) throws IOException, CException{
		sendUserRegInfo(info.getUserType(), info.getGender(), info.getAge(), info.getGameServer(), info.getServerId(), info.getAreaId(), info.getPlayerId(), info.getRoleName(), info.getUserName(), info.getCol1(), info.getCol2(), info.getCol3(), info.getTs());
	}
	
	/**
	 * 2 用户登陆
	 * @param gameServer
	 * @param serverId
	 * @param areaId
	 * @param playerId
	 * @param roleName
	 * @param userName
		 * @param col1
	 * @param col2
	 * @param col3
	 * @param ts
	 * @throws CException 
	 * @throws IOException 
	 */
	public static void sendUserLoginInfo(String gameServer,String serverId,String areaId,String playerId,String roleName,String userName,String col1,String col2,String col3,int ts) throws CException, IOException{
		isInited();
		validateTs(ts);
		String body = DataUtil.genBody(gameServer,serverId,areaId,playerId,roleName,userName,col1,col2,col3,ts);
		//FileUtil.append(GlobalConstant.userlogininfo,body);
		SENDER.send(GlobalConstant.userlogininfo, body);
	}
	
	public static void sendUserLoginInfo(UserLoginInfo info) throws CException, IOException{
		sendUserLoginInfo(info.getGameServer(), info.getServerId(), info.getAreaId(), info.getPlayerId(), info.getRoleName(), info.getUserName(), info.getCol1(), info.getCol2(), info.getCol3(), info.getTs());
	}
	
	/**
	 * 3 选择服务器
	 * @param gameServer
	 * @param serverId
	 * @param areaId
	 * @param playerId
	 * @param roleName
	 * @param userName
	 	 * @param col1
	 * @param col2
	 * @param col3
	 * @param ts
	 * @throws CException 
	 * @throws IOException 
	 */
	public static void sendSelServerInfo(String gameServer,String serverId,String areaId,String playerId,String roleName,String userName,String col1,String col2,String col3,int ts) throws CException, IOException{
		isInited();
		validateTs(ts);
		String body = DataUtil.genBody(gameServer,serverId,areaId,playerId,roleName,userName,col1,col2,col3,ts);
			//FileUtil.append(GlobalConstant.serverselinfo,body);
			SENDER.send(GlobalConstant.serverselinfo, body);
			
	}
	public static void sendSelServerInfo(ServerSelInfo info) throws CException, IOException{
		sendSelServerInfo(info.getGameServer(), info.getServerId(), info.getAreaId(), info.getPlayerId(), info.getRoleName(), info.getUserName(), info.getCol1(), info.getCol2(), info.getCol3(), info.getTs());
			
	}
	/**
	 * 4 角色创建信息
	 * @param serverId
	 * @param roleType
	 * @param roleGender
	 * @param roleAge
	 * @param roleLevel
	 * @param playerId
	 * @param roleName
	 * @param userName
	 	 * @param col1
	 * @param col2
	 * @param col3
	 * @param ts
	 * @throws IOException 
	 * @throws CException 
	 */
	public static void sendRolecCreateInfo(String serverId,String roleType,String roleGender,int roleAge,int roleLevel,String playerId,String roleName,String userName,String col1,String col2,String col3,int ts) throws IOException, CException{
		isInited();
		validateTs(ts);
		String body = DataUtil.genBody(serverId,roleType,roleGender,roleAge,roleLevel,playerId,roleName,userName,col1,col2,col3,ts);

			//FileUtil.append(GlobalConstant.rolecreateinfo,body);
			SENDER.send(GlobalConstant.rolecreateinfo, body);
			
	}
	
	public static void sendRolecCreateInfo(RoleCreateInfo info) throws IOException, CException{
		sendRolecCreateInfo(info.getServerId(), info.getRoleType(), info.getRoleGender(), info.getRoleAge(), info.getRoleLevel(), info.getPlayerId(), info.getRoleName(), info.getUserName(), info.getCol1(), info.getCol2(), info.getCol3(), info.getTs());
			
	}
	
	/**
	 * 5 角色登陆信息
	 * @param serverId
	 * @param roleLevel
	 * @param playerId
	 * @param roleName
	 * @param userName
	 	 * @param col1
	 * @param col2
	 * @param col3
	 * @param ts
	 * @throws IOException 
	 * @throws CException 
	 */
	public static void sendRoleLoginInfo(String serverId,int roleLevel,String playerId,String roleName,String userName,String col1,String col2,String col3,int ts) throws IOException, CException{
		isInited();
		validateTs(ts);
		String body = DataUtil.genBody(serverId,roleLevel,playerId,roleName,userName,col1,col2,col3,ts);

			//FileUtil.append(GlobalConstant.rolelogininfo,body);
		SENDER.send(GlobalConstant.rolelogininfo, body);
		
	}
	public static void sendRoleLoginInfo(RoleLoginInfo info) throws IOException, CException{
		sendRoleLoginInfo(info.getServerId(), info.getRoleLevel(), info.getPlayerId(), info.getRoleName(), info.getUserName(), info.getCol1(), info.getCol2(), info.getCol3(), info.getTs());
		
	}
	/**
	 * 6 角色升级信息
	 * @param serverId
	 * @param oldlevel
	 * @param newlevel
	 * @param sucess
	 * @param params
	 * @param playerId
	 * @param roleName
	 * @param userName
	 	 * @param col1
	 * @param col2
	 * @param col3
	 * @param ts
	 * @throws CException 
	 * @throws IOException 
	 */
	public static void sendRoleLvupInfo(String serverId,int oldlevel,int newlevel,int sucess,String params,String playerId,String roleName,String userName,String col1,String col2,String col3,int ts) throws CException, IOException{
		isInited();
		validateTs(ts);
		String body = DataUtil.genBody(serverId,oldlevel,newlevel,sucess,params,playerId,roleName,userName,col1,col2,col3,ts);

			//FileUtil.append(GlobalConstant.rolelvupinfo,body);
		SENDER.send(GlobalConstant.rolelvupinfo, body);
	}
	
	public static void sendRoleLvupInfo(RoleLvUpInfo info) throws CException, IOException{
		sendRoleLvupInfo(info.getServerId(), info.getOldlevel(), info.getNewlevel(), info.getSucess(), info.getParams(), info.getPlayerId(), info.getRoleName(), info.getUserName(), info.getCol1(), info.getCol2(), info.getCol3(), info.getTs());
	}
	
	/**
	 * 7 角色注销信息
	 * @param serverId
	 * @param roleLevel
	 * @param playerId
	 * @param roleName
	 * @param userName
	 	 * @param col1
	 * @param col2
	 * @param col3
	 * @param ts
	 * @throws IOException 
	 * @throws CException 
	 */
	public static void sendRoleCancelInfo(String serverId,int roleLevel,String playerId,String roleName,String userName,String col1,String col2,String col3,int ts) throws IOException, CException{
		isInited();
		validateTs(ts);
		String body = DataUtil.genBody(serverId,roleLevel,playerId,roleName,userName,col1,col2,col3,ts);

			//FileUtil.append(GlobalConstant.rolecancelinfo,body);
			SENDER.send(GlobalConstant.rolecancelinfo, body);
	}
	
	public static void sendRoleCancelInfo(RoleCancelInfo info) throws IOException, CException{
		sendRoleCancelInfo(info.getServerId(), info.getRoleLevel(), info.getPlayerId(), info.getRoleName(), info.getUserName(), info.getCol1(), info.getCol2(), info.getCol3(), info.getTs());
	}
	/**
	 * 8 充值请求统计信息
	 * @param serverId
	 * @param orderId
	 * @param roleLevel
	 * @param IngotAmount
	 * @param IngotTotal
	 * @param iapId
	 * @param currencyAmount
	 * @param currencyType
	 * @param virtualCurrencyAmount
	 * @param paymentType
	 * @param payPlatform
	 * @param playerId
	 * @param roleName
	 * @param userName
	 	 * @param col1
	 * @param col2
	 * @param col3
	 * @param ts
	 * @throws CException 
	 * @throws IOException 
	 */
	public static void sendPayRequestInfo(String serverId,String orderId,int roleLevel,int IngotAmount,int IngotTotal,String iapId,double currencyAmount,String currencyType,double virtualCurrencyAmount,String paymentType,String payPlatform,String playerId,String roleName,String userName,String col1,String col2,String col3,int ts) throws CException, IOException{
		isInited();
		validateTs(ts);
		String body = DataUtil.genBody(serverId,orderId,roleLevel,IngotAmount,IngotTotal,iapId,currencyAmount,currencyType,virtualCurrencyAmount,paymentType,payPlatform,playerId,roleName,userName,col1,col2,col3,ts);

			//FileUtil.append(GlobalConstant.payrequestinfo,body);
			SENDER.send(GlobalConstant.payrequestinfo, body);
	}
	
	public static void sendPayRequestInfo(PayRequestInfo info) throws CException, IOException{
		sendPayRequestInfo(info.getServerId(), info.getOrderId(), info.getRoleLevel(), info.getIngotAmount(), info.getIngotTotal(), info.getIapId(), info.getCurrencyAmount(), info.getCurrencyType(), info.getVirtualCurrencyAmount(), info.getPaymentType(), info.getPayPlatform(), info.getPlayerId(), info.getRoleName(), info.getUserName(), info.getCol1(), info.getCol2(), info.getCol3(), info.getTs());
	}
	/**
	 * 9 充值成功统计信息
	 * @param serverId
	 * @param orderId
	 * @param roleLevel
	 * @param IngotAmount
	 * @param IngotTotal
	 * @param iapId
	 * @param currencyAmount
	 * @param currencyType
	 * @param virtualCurrencyAmount
	 * @param paymentType
	 * @param payPlatform
	 * @param playerId
	 * @param roleName
	 * @param userName
	 	 * @param col1
	 * @param col2
	 * @param col3
	 * @param ts
	 * @throws CException 
	 * @throws IOException 
	 */
	public static void sendPaySucInfo(String serverId,String orderId,int roleLevel,int IngotAmount,int IngotTotal,String iapId,double currencyAmount,String currencyType,double virtualCurrencyAmount,String paymentType,String payPlatform,String playerId,String roleName,String userName,String col1,String col2,String col3,int ts) throws CException, IOException{
		isInited();
		validateTs(ts);
		String body = DataUtil.genBody(serverId,orderId,roleLevel,IngotAmount,IngotTotal,iapId,currencyAmount,currencyType,virtualCurrencyAmount,paymentType,payPlatform,playerId,roleName,userName,col1,col2,col3,ts);
		
			//FileUtil.append(GlobalConstant.paysucinfo,body);
			SENDER.send(GlobalConstant.paysucinfo, body);
	}
	
	public static void sendPaySucInfo(PaySucInfo info) throws CException, IOException{
		sendPaySucInfo(info.getServerId(), info.getOrderId(), info.getRoleLevel(), info.getIngotAmount(), info.getIngotTotal(), info.getIapId(), info.getCurrencyAmount(), info.getCurrencyType(), info.getVirtualCurrencyAmount(), info.getPaymentType(), info.getPayPlatform(), info.getPlayerId(), info.getRoleName(), info.getUserName(), info.getCol1(), info.getCol2(), info.getCol3(), info.getTs());
	}
	
	/**
	 * 10 跟踪获赠元宝和虚拟货币信息
	 * @param serverId
	 * @param IngotAmount
	 * @param IngotTotal
	 * @param virtualCurrencyAmount
	 * @param reason
	 * @param playerId
	 * @param roleName
	 * @param userName
	 	 * @param col1
	 * @param col2
	 * @param col3
	 * @param ts
	 * @throws CException 
	 * @throws IOException 
	 */
	public static void sendRewardInfo(String serverId,int IngotAmount,int IngotTotal,double virtualCurrencyAmount,String reason,String playerId,String roleName,String userName,String col1,String col2,String col3,int ts) throws CException, IOException{
		isInited();
		validateTs(ts);
		String body = DataUtil.genBody(serverId,IngotAmount,IngotTotal,virtualCurrencyAmount,reason,playerId,roleName,userName,col1,col2,col3,ts);
		
			//FileUtil.append(GlobalConstant.rewardinfo,body);
			SENDER.send(GlobalConstant.rewardinfo, body);
	}
	
	public static void sendRewardInfo(RewardInfo info) throws CException, IOException{
		sendRewardInfo(info.getServerId(), info.getIngotAmount(), info.getIngotTotal(), info.getVirtualCurrencyAmount(),info.getReason() , info.getPlayerId(), info.getRoleName(), info.getUserName(), info.getCol1(), info.getCol2(), info.getCol3(), info.getTs());
	}
	
	/**
	 * 11 跟踪游戏消费点统计信息
	 * @param serverId
	 * @param item
	 * @param number
	 * @param IngotTotal
	 * @param IngotPrice
	 * @param virtualCurrencyPrice
	 * @param missionId
	 * @param roleLevel
	 * @param playerId
	 * @param roleName
	 * @param userName
	 	 * @param col1
	 * @param col2
	 * @param col3
	 * @param ts
	 * @throws CException 
	 * @throws IOException 
	 */
	public static void sendConsumeInfo(String serverId,String item,int number,int IngotTotal,int IngotPrice,double virtualCurrencyPrice,String missionId,int roleLevel,String playerId,String roleName,String userName,String col1,String col2,String col3,int ts) throws CException, IOException{
		isInited();
		validateTs(ts);
		
		String body = DataUtil.genBody(serverId,item,number,IngotTotal,IngotPrice,virtualCurrencyPrice,missionId,roleLevel,playerId,roleName,userName,col1,col2,col3,ts);
		
			//FileUtil.append(GlobalConstant.consumeinfo,body);
		SENDER.send(GlobalConstant.consumeinfo, body);
	}
	
	public static void sendConsumeInfo(ConsumeInfo info) throws CException, IOException{
		sendConsumeInfo(info.getServerId(), info.getItem(), info.getNumber(), info.getIngotTotal(), info.getIngotPrice(), info.getVirtualCurrencyPrice(), info.getMissionId(), info.getRoleLevel(), info.getPlayerId(), info.getRoleName(), info.getUserName(), info.getCol1(), info.getCol2(), info.getCol3(), info.getTs());
	}
	
	/**
	 * 12 missioninfo
	 * @param serverId
	 * @param missionId
	 * @param mainTaskId
	 * @param subTaskId
	 * @param type
	 * @param status
	 * @param cause
	 * @param playerId
	 * @param roleName
	 * @param userName
	 * @param playerStatus
	 * @param Ingot
	 * @param virtualCurrency
	 * @param scenceId
	 * @param roleLevel
	 	 * @param col1
	 * @param col2
	 * @param col3
	 * @param ts
	 * @throws CException 
	 * @throws IOException 
	 */
	public static void sendMissionInfo(String serverId,String missionId,String mainTaskId,String subTaskId,int type,int status,String cause,String playerId,String roleName,String userName,String playerStatus,int Ingot,double virtualCurrency,String scenceId,int roleLevel,String col1,String col2,String col3,int ts) throws CException, IOException{
		isInited();
		validateTs(ts);
		String body = DataUtil.genBody(serverId,missionId,mainTaskId,subTaskId,type,status,cause,playerId,roleName,userName,playerStatus,Ingot,virtualCurrency,scenceId,roleLevel,col1,col2,col3,ts);
		
			//FileUtil.append(GlobalConstant.missioninfo,body);
			SENDER.send(GlobalConstant.missioninfo, body);
	}
	
	public static void sendMissionInfo(MissionInfo info) throws CException, IOException{
		sendMissionInfo(info.getServerId(), info.getMissionId(), info.getMainTaskId(), info.getSubTaskId(), info.getType(), info.getStatus(), info.getCause(), info.getPlayerId(), info.getRoleName(), info.getUserName(), info.getPlayerStatus(), info.getIngot(), info.getVirtualCurrency(), info.getScenceId(), info.getRoleLevel(), info.getCol1(), info.getCol2(), info.getCol3(), info.getTs());
	}
	
	/**
	 * 13 用户退出登陆
	 * @param serverId
	 * @param roleLevel
	 * @param playerId
	 * @param roleName
	 * @param userName
	 	 * @param col1
	 * @param col2
	 * @param col3
	 * @param ts
	 * @throws CException 
	 * @throws IOException 
	 */
	public static void sendUserLogoutInfo(String serverId,int roleLevel,String playerId,String roleName,String userName,String col1,String col2,String col3,int ts) throws CException, IOException{
		isInited();
		validateTs(ts);
		String body = DataUtil.genBody(serverId,roleLevel,playerId,roleName,userName,col1,col2,col3,ts);
		
			//FileUtil.append(GlobalConstant.userlogoutinfo,body);
			SENDER.send(GlobalConstant.userlogoutinfo, body);
	}
	
	
	public static void sendUserLogoutInfo(UserLogoutInfo info) throws IOException, CException{
		sendUserLogoutInfo(info.getServerId(), info.getRoleLevel(), info.getPlayerId(), info.getRoleName(), info.getUserName(), info.getCol1(), info.getCol2(), info.getCol3(), info.getTs());
	}
	
	public static void isInited() throws CException{
		if(inited==false){
			throw new CException("请初始化SDK!");
		}
	}
	public static void validateTs(int ts) throws CException{
		if(ts <1000000000 || ts>1990000000){
			throw new CException("请填写10位时间戳！ts");
		}
	}
}
