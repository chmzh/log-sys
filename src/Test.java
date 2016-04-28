

import java.io.IOException;

import com.cndw.logsys.CException;
import com.cndw.logsys.LogClient;
import com.cndw.logsys.domain.Message;

public class Test {
	public static void main(String[] args) {
		String id = "1";
		String logDir = "/Users/abc/logs";
		if(args!=null && args.length==2){
			id=args[0];
			logDir = args[1]+"/data_logs"+id;
		}
		
		LogClient.setGameId("");
		LogClient.setKey("");
		LogClient.setLogDir(logDir);
		LogClient.setTencent(false);
		LogClient.setRetryThreads(8);
		LogClient.setDebug(false);
		
		try {
			LogClient.start();
		} catch (Exception e) {

			e.printStackTrace();
		}
		for(int i=0;i<8;i++){
			new Thread(new Task()).start();
		}
		
	}
	
	static class Task implements Runnable{
		public void run() {
			
//			writeLog();
			
			while(true){
					writeLog();
				
			}
			
		}
		
		public void writeLog(){
			
			int ts = (int)(System.currentTimeMillis()/1000);
			try {
				LogClient.sendUserRegInfo(null, "", 1, "逗号测试,", "回车换行测试\r\n", "", "1001001", "角色名"+ts, "用户名"+ts,"v1","v2","v3", ts);
				LogClient.sendConsumeInfo("s1"+ts, "item", 1, 1, 1, 1.0, "消费", 1, "1", "角色名"+ts, "用户名"+ts,"v1","v2","v3", ts);
				LogClient.sendMissionInfo("s1"+ts, "1001","100","101", 1, 1, "", ts+"", "角色名"+ts, "用户名"+ts, "", 1, 1, "1", 1,"v1","v2","v3", ts);
				LogClient.sendPayRequestInfo("s1"+ts, ts+"", 1, 1, 1, "", 1, "USA", 1, "ALIPAY", "Apple", ts+"", "角色名"+ts, "用户名"+ts,"v1","v2","v3", ts);
				LogClient.sendPaySucInfo("s1"+ts, ts+"", 1, 1, 1, "", 1, "USA", 1, "ALIPAY", "Apple", ts+"", "角色名"+ts, "用户名"+ts,"v1","v2","v3", ts);
				LogClient.sendRewardInfo("s1"+ts, 1, 1, 1, "", "1", "角色名"+ts, "用户名"+ts,"v1","v2","v3", ts);
				LogClient.sendRoleCancelInfo("s1"+ts, 1, "1", "角色名"+ts, "用户名"+ts,"v1","v2","v3", ts);
				LogClient.sendRolecCreateInfo("s1"+ts, "", "", 1, 1, "", "角色名"+ts, "用户名"+ts,"v1","v2","v3", ts);
				LogClient.sendRoleLoginInfo("s1"+ts, 1, "1", "角色名"+ts, "用户名"+ts,"v1","v2","v3", ts);
				LogClient.sendRoleLvupInfo("s1"+ts, 1, 2, 1, "", "1", "角色名"+ts, "用户名"+ts,"v1","v2","v3", ts);
				LogClient.sendSelServerInfo("", "s1"+ts, "", "", "角色名"+ts, "用户名"+ts,"v1","v2","v3", ts);
				LogClient.sendUserLoginInfo(null, "s1"+ts, "", "1", "角色名"+ts, "用户名"+ts,"v1","v2","v3", ts);
				LogClient.sendUserLogoutInfo("s1"+ts, 1, "1","角色名"+ts, "用户名"+ts,"v1","v2","v3", ts);
			} catch (IOException | CException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 
			 
		}
	}
}
