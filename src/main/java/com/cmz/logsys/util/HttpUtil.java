package com.cmz.logsys.util;


import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import com.cmz.logsys.constant.GlobalConstant;
import com.cmz.logsys.domain.DataMsg;
import com.cmz.logsys.domain.Message;
import com.google.gson.Gson;



public class HttpUtil {
	// 接口地址
		
		private CloseableHttpClient httpClient = null;
		private HttpPost method = null;
		private long startTime = 0L;
		private long endTime = 0L;
		private int status = 0;
		private String apiURL = "http://"+GlobalConstant.host+":"+GlobalConstant.port+"/json";


		
		/**
		 * 接口地址
		 * 
		 * @param url
		 */
		public HttpUtil() {

			httpClient = HttpClients.createDefault();
			
			method = new HttpPost(apiURL);
		}
		
		public void close(){
			try {
				httpClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/**
		 * 调用 API
		 * 
		 * @param parameters
		 * @return
		 */
		private String post(String logType,String fileName,String parameters) {
			
			//logger.info("parameters:" + parameters);
			String body = null;
			CloseableHttpResponse response = null;
			if (method != null & parameters != null
					&& !"".equals(parameters.trim())) {
				try {

					// 建立一个NameValuePair数组，用于存储欲传送的参数
					method.addHeader("Content-type",
							"application/json; charset=utf-8");
					method.setHeader("Accept", "application/json");
					method.setEntity(new StringEntity(parameters, Charset
							.forName("UTF-8")));
					startTime = System.currentTimeMillis();

					response = httpClient.execute(method);
					
					endTime = System.currentTimeMillis();
					int statusCode = response.getStatusLine().getStatusCode();
					if (statusCode != HttpStatus.SC_OK) {
						body = "{\"code\":-1,\"msg\":\"网络问题"+statusCode+"\"}";
						status = 1;
					}else{
						
						HttpEntity entity = response.getEntity();
						body = EntityUtils.toString(entity , "utf-8").trim();
					}

				} catch (ClientProtocolException e) {
					body = "{\"code\":-20,\"msg\":\""+e.getLocalizedMessage()+"\"}";
					// 网络错误
					status = 3;
						
				} catch (IOException e) {
					body = "{\"code\":-21,\"msg\":\""+e.getLocalizedMessage()+"\"}";
					// IO错误
					status = 4;
				}
				finally{
					method.abort();
					if(response!=null){
						try {
							response.close();
							httpClient.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					Gson gson = new Gson();
					Message msg = gson.fromJson(body, Message.class);
					if(status != 0 || msg.getCode()!=0){
						try {
							if(null!=fileName && !fileName.equals("")){
								FileUtil.ON_LINE_RETRY_FILES.add(fileName);
							}else{
								FileUtil.writeRetryFile(logType, parameters);
							}
							
							FileUtil.writeErrLog(FileUtil.toTime(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss")+":错误号:["+body+"],日志类型:["+logType+"],文件名：["+fileName+"]==内容==【"+parameters+"】");
						} catch (IOException e) {
							body = "{\"code\":-30,\"msg\":\""+e.getLocalizedMessage()+"\"}";
						}
						
					}else{
						if(null!=fileName && !fileName.equals("")){
							FileUtil.delete(fileName);
						}
					}
				}
			
			}
			
			return body;
		}

		public String postJson(String logType,String fileName,String json) {
			return post(logType,fileName,json);
		}



		/**
		 * 0.成功 1.执行方法失败 2.协议错误 3.网络错误
		 * 
		 * @return the status
		 */
		public int getStatus() {
			return status;
		}

		/**
		 * @param status
		 *            the status to set
		 */
		public void setStatus(int status) {
			this.status = status;
		}

		/**
		 * @return the startTime
		 */
		public long getStartTime() {
			return startTime;
		}

		/**
		 * @return the endTime
		 */
		public long getEndTime() {
			return endTime;
		}

}