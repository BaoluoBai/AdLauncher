package com.eightmile.adlauncher.service;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import com.eightmile.adlauncher.model.HandShake;
import com.eightmile.adlauncher.util.Config;
import com.eightmile.adlauncher.util.LogUtil;
import com.google.gson.Gson;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class WebSocketService extends Service {
	private static final String TAG = "WebSocketService";
	private boolean isConnected = true;
	private URI uri = null;
	private WebSocketClient socketClient;
	private String mac = Config.get("mac");
	private WebSocketClientBinder mBinder = new WebSocketClientBinder();
	Gson gson = new Gson();

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mBinder;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		initWebSocket();
//		LogUtil.i(TAG, "service created");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		LogUtil.i(TAG, "service start command");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	/**
	 * 初始化webSocket,打开webSocket链接
	 */
	public void initWebSocket(){
		try {
			uri = new URI("ws://"+Config.get("domain")+":7373");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		socketClient = new WebSocketClient(uri) {
			HandShake handShake = new HandShake(mac, "login");
			String login = gson.toJson(handShake);
			@Override
			public void onOpen(ServerHandshake arg0) {
				// TODO Auto-generated method stub
				LogUtil.i(TAG, "WebSocket is open, handShake is "+ login);
				socketClient.send(login);
				isConnected = true;
			}
			
			@Override
			public void onMessage(String arg0) {
				// TODO Auto-generated method stub
				LogUtil.i(TAG, arg0);
			}
			
			@Override
			public void onError(Exception arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onClose(int arg0, String arg1, boolean arg2) {
				// TODO Auto-generated method stub
				
			}
		};
		socketClient.connect();
	}
	
	/**
	 * 该类内部提供了一个发送消息的方法
	 * @author zc
	 *
	 */
	public class WebSocketClientBinder extends Binder {  
        public WebSocketService getService() {  
            return WebSocketService.this;  
        }  
          
        public void sendMsg(String data){  
            if(socketClient.isOpen()){  
            	socketClient.send(data);  
            }
        }  
    }

}
