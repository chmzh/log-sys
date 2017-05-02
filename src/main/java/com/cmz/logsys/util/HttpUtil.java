package com.cmz.logsys.util;


import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.SocketConfig;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import com.cmz.logsys.constant.GlobalConstant;
import com.cmz.logsys.domain.Message;
import com.google.gson.Gson;



public class HttpUtil {
	// 接口地址
		private HttpPost method = null;
		private long startTime = 0L;
		private long endTime = 0L;
		private int status = 0;
		private String apiURL = "http://"+GlobalConstant.host+":"+GlobalConstant.port+"/json";


		private static PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		private static CloseableHttpClient httpClient = null;

		static{
			cm.setMaxTotal(2000);
			SocketConfig socketConfig = SocketConfig.custom().setSoKeepAlive(true).setSoTimeout(800).build();
			HttpHost host = new HttpHost(GlobalConstant.host, GlobalConstant.port);
			cm.setSocketConfig(host, socketConfig);
			
			httpClient = HttpClients.custom().setConnectionManager(cm).build();
		}
		/**
		 * 接口地址
		 * 
		 * @param url
		 */
		public HttpUtil() {

			//httpClient = HttpClients.createDefault();
			
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
					//method.abort();
					if(response!=null){
						try {
							response.close();
							//httpClient.close();
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

	
	   public static String httpPostForm(String url, Map<String,String> params) {
        String body = "";
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost method = new HttpPost(url);

        try {
            List<NameValuePair> pairList = new ArrayList<>();
            if (!params.isEmpty()) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    pairList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));

                }
            }

            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(pairList, "UTF-8");
            method.setEntity(uefEntity);
            //method.addHeader("Content-type","application/json; charset=utf-8");
            method.setHeader("Accept", "application/json; charset=utf-8");
            response = httpClient.execute(method);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                LogUtil.getConsole().error(url + "网络问题:" + statusCode);
            } else {
                HttpEntity entity = response.getEntity();
                body = EntityUtils.toString(entity, "utf-8").trim();
            }
        } catch (ClientProtocolException e) {
            LogUtil.getConsole().error(url, e);

        } catch (IOException e) {
            LogUtil.getConsole().error(url, e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    LogUtil.getConsole().error(url, e);
                }
            }
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    LogUtil.getConsole().error(url, e);
                }
            }
        }

        return body;
    }

    public static String httpGet(String url, Map<String, String> params) {
        String body = "";
        CloseableHttpResponse response = null;
        HttpContext context = HttpClientContext.create();
        try {
            List<NameValuePair> pairList = new ArrayList<>();
            if (Objects.nonNull(params)) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    pairList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));

                }
            }
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(pairList, "UTF-8");
            String str = EntityUtils.toString(uefEntity);
            HttpGet method = new HttpGet(url + "?" + str);
            method.setHeader("Accept", "application/json; charset=utf-8");
            response = httpClient.execute(method, context);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                LogUtil.getConsole().error(url + "网络问题:" + statusCode);
            } else {
                HttpEntity entity = response.getEntity();
                body = EntityUtils.toString(entity, "utf-8").trim();
            }
        } catch (ClientProtocolException e) {
            LogUtil.getConsole().error(url, e);

        } catch (IOException e) {
            LogUtil.getConsole().error(url, e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    LogUtil.getConsole().error(url, e);
                }
            }
        }

        return body;
    }

    public static String httpPostForm(String url, Map<String, String> params) {
        String body = "";
        CloseableHttpResponse response = null;
        HttpPost method = new HttpPost(url);
        HttpContext context = HttpClientContext.create();
        try {
            List<NameValuePair> pairList = new ArrayList<>();
            if (Objects.nonNull(params)) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    pairList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));

                }
            }

            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(pairList, "UTF-8");
            method.setEntity(uefEntity);
            method.setHeader("Content-Type", "application/x-www-form-urlencoded");
            method.setHeader("Accept", "application/json; charset=utf-8");
            response = httpClient.execute(method, context);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                LogUtil.getConsole().error(url + "网络问题:" + statusCode);
            } else {
                HttpEntity entity = response.getEntity();
                body = EntityUtils.toString(entity, "utf-8").trim();
            }
        } catch (ClientProtocolException e) {
            LogUtil.getConsole().error(url, e);

        } catch (IOException e) {
            LogUtil.getConsole().error(url, e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    LogUtil.getConsole().error(url, e);
                }
            }

        }

        return body;
    }
    public static String getBasicAuthorization(String username, String password) {
        String encodeKey = username + ":" + password;
        return "Basic " + String.valueOf(Base64.encode(encodeKey.getBytes()));
    }
    public static String httpPostJson(String url,String paramters,String userName,String password){
        if(StringUtils.isBlank(paramters)){
            throw new NullPointerException("paramtres 为空");
        }
        String body = "";
        CloseableHttpResponse response = null;
        HttpPost method = new HttpPost(url);
        HttpContext context = HttpClientContext.create();
        try {
            method.addHeader("Content-type", "application/json; charset=utf-8");
            method.setHeader("Accept", "application/json; charset=utf-8");
            if(StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password)){
                method.setHeader("Authorization",getBasicAuthorization(userName,password));
            }
            method.setEntity(new StringEntity(paramters, Charset.forName("UTF-8")));
            response = httpClient.execute(method, context);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                LogUtil.getConsole().error(url + "网络问题:" + statusCode);
            } else {
                HttpEntity entity = response.getEntity();
                body = EntityUtils.toString(entity, "utf-8").trim();
            }
        } catch (ClientProtocolException e) {
            LogUtil.getConsole().error(url, e);

        } catch (IOException e) {
            LogUtil.getConsole().error(url, e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    LogUtil.getConsole().error(url, e);
                }
            }
        }
        return body;
    }

    public static String httpPostJson(String url, String paramters) {
        return httpPostJson(url,paramters,null,null);
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
