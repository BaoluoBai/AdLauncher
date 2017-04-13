package com.eightmile.adlauncher.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class HttpUtil {
	
	/**
	 * 向服务器请求数据
	 * @param address url地址
	 * @param listener
	 * @author zc
	 */
	public static void getHttpRequest(final String address, 
			final HttpCallbackListener listener){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpURLConnection connection_get = null;
				try {
					URL url = new URL(address);
					connection_get = (HttpURLConnection) url.openConnection();
					connection_get.setRequestMethod("GET");
					connection_get.setConnectTimeout(8000);
					connection_get.setReadTimeout(8000);
					InputStream in = connection_get.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder response = new StringBuilder();
					String line;
					while((line = reader.readLine())!= null){
						response.append(line);
					}
					if(listener != null){
						listener.onFinish(response.toString());
					}
				} catch (Exception e) {
					// TODO: handle exception
					if(listener != null){
						listener.onError(e);
					}
				} finally{
					if(listener != null){
						connection_get.disconnect();
					}
				}
			}
		}).start();
	}
	
	/**
	 * 向服务器发送数据
	 * @param address url地址
	 * @param data	需要发送的数据，为json字符串格式
	 * @param listener 回调函数
	 * @author zc
	 */
	public static void postHttpRequest(final String address,final String data,
			final HttpCallbackListener listener){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpURLConnection connection_post = null;
				try {
					URL url = new URL(address);
					connection_post = (HttpURLConnection) url.openConnection();
					connection_post.setRequestMethod("POST");
					connection_post.setConnectTimeout(8000);
					connection_post.setReadTimeout(8000);
					//是否允许输入输出  
					connection_post.setDoInput(true);  
					connection_post.setDoOutput(true);
					//设置请求头里面的数据，以下设置用于解决http请求code415的问题  
					connection_post.setRequestProperty("Content-Type",  
		                    "application/json");  
		            //链接地址  
					connection_post.connect();
					OutputStreamWriter writer = new OutputStreamWriter(connection_post.getOutputStream());  
		            //发送参数  
		            writer.write(data);
		            writer.flush();
		            BufferedReader reader = new BufferedReader(new InputStreamReader(    
		            		connection_post.getInputStream()));
		            StringBuilder response = new StringBuilder();
					String line;
					while((line = reader.readLine())!= null){
						response.append(line);
					}
					if(listener != null){
						listener.onFinish(response.toString());
					}
				} catch (Exception e) {
					// TODO: handle exception
					if(listener != null){
						listener.onError(e);
					}
				} finally {
					connection_post.disconnect();
				}
			}
		}).start();
	}
	
	
}
