package com.eightmile.adlauncher.activity;

import java.util.Timer;
import java.util.TimerTask;

import com.eightmile.adlauncher.R;
import com.eightmile.adlauncher.model.HeartBeat;
import com.eightmile.adlauncher.service.WebSocketService;
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
//    		time.schedule(insertDeviceInfo,60*1000);
    		time.schedule(sendpang, 10*1000, 20*1000);
        }
    }
    
    public void sendHeartBeat(){
    	String pang = gson.toJson(heart);
    	if(webSocketClientBinder == null){
    		Toast.makeText(this, "服务未能绑定", Toast.LENGTH_SHORT).show();
    	}else{
    		webSocketClientBinder.sendMsg(pang);
    	}
    }
}
