package com.eightmile.adlauncher.activity;

import java.util.Timer;
import java.util.TimerTask;

import com.eightmile.adlauncher.R;
import com.eightmile.adlauncher.db.AdLauncherDB;
import com.eightmile.adlauncher.model.HeartBeat;
import com.eightmile.adlauncher.service.WebSocketService;
import com.eightmile.adlauncher.util.HttpCallbackListener;
import com.eightmile.adlauncher.util.HttpUtil;
import com.eightmile.adlauncher.util.LogUtil;
import com.eightmile.adlauncher.util.Utility;
import com.google.gson.Gson;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;


public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";
	Gson gson = new Gson();
	HeartBeat heart = new HeartBeat("pang");
	private WebSocketService.WebSocketClientBinder webSocketClientBinder;
	private AdLauncherDB adLauncherDB;
	
	private ServiceConnection connection = new ServiceConnection(){

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			webSocketClientBinder = (WebSocketService.WebSocketClientBinder) service;
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adLauncherDB = AdLauncherDB.getInstance(this);
        Intent intent = new Intent(this, WebSocketService.class);
        boolean isBind = bindService(intent, connection, BIND_AUTO_CREATE);
        if(isBind == true){
        	TimerTask sendpang=new TimerTask() {
    			@Override
    			public void run() {
    				sendHeartBeat();
    			}
    		};
    		Timer time =new Timer();
    		time.schedule(sendpang, 10*1000, 20*1000);
        }
    }
    
    /**
     * 向服务器发送心跳包
     */
    public void sendHeartBeat(){
    	String pang = gson.toJson(heart);
    	if(webSocketClientBinder == null){
    		Toast.makeText(this, "服务未能绑定", Toast.LENGTH_SHORT).show();
    	}else{
    		webSocketClientBinder.sendMsg(pang);
    	}
    }
    
    /**
     * 从服务器查询数据，包括布局、版本、APP、广告列表、及时消息、定时开关机等
     * @param address 请求服务器的地址
     * @param type	请求的类型
     */
    private void queryFromServer(final String address, final String type){
    	HttpUtil.getHttpRequest(address, new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response) {
				// TODO Auto-generated method stub
				if("lastest_api".equals(type)){
					
				}else if("init_api".equals(type)){
					
				}else if("layout_api".equals(type)){
					Utility.handleLayoutResponse(adLauncherDB, response);
				}else if("applist_api".equals(type)){
					
				}else if("adlist_api".equals(type)){
					
				}else if("instantmessage_api".equals(type)){
					
				}else if("switchs_api".equals(type)){
					
				}else if("volume_api".equals(type)){
					
				}else if("emergency_api".equals(type)){
					
				}
			}
			
			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				LogUtil.e(TAG, "网络请求错误");
			}
		});
    }
}
