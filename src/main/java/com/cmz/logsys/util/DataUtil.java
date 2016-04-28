package com.cmz.logsys.util;

import com.cmz.logsys.constant.GlobalConstant;
import com.cmz.logsys.domain.DataHeader;
import com.cmz.logsys.domain.DataMsg;

public class DataUtil {
	/**
	 * 生成body
	 * @param objects
	 * @return
	 */
	public static String genBody(Object...objects){
		StringBuilder builder = new StringBuilder();
		int count = objects.length;
		for(int i=0;i<count;i++){
			Object object = objects[i];
			
			if(object==null){
				object = "";
			}
			Class<?> class1 = object.getClass();
			if(class1.getSimpleName().equals("String")){
				String v = (String)object;
				v = v.replaceAll(",", "，").replaceAll("\r\n", "	");
				builder.append(v);
			}else{
				builder.append(object);
			}
			
			if(i!=count-1){
				builder.append(",");
			}
		}
		//System.out.println(builder.toString());
		return builder.toString();
	}
	/**
	 * 生成header
	 * @param logType
	 * @return
	 */
	public static DataHeader genHeader(long timestamp,String logType){
		DataHeader headers = new DataHeader();
		headers.setGameId(GlobalConstant.gameId);
		int ts = (int)(timestamp/1000);
		headers.setTimestamp(ts);
		headers.setLogType(logType);
		String sign = MD5Util.getMD5(GlobalConstant.gameId.toLowerCase()+logType+ts+GlobalConstant.key);
		headers.setSign(sign);
		return headers;
	}
	/**
	 * 消息
	 * @param header
	 * @param body
	 * @return
	 */
	public static DataMsg genMsg(DataHeader header,String body){
		DataMsg msg = new DataMsg();
		msg.setHeaders(header);
		msg.setBody(body);
		return msg;
	}
}
