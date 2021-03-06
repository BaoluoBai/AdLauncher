package com.eightmile.adlauncher.activity;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import com.alibaba.fastjson.JSONObject;
import com.eightmile.adlauncher.R;
import com.eightmile.adlauncher.db.AdLauncherDB;
import com.eightmile.adlauncher.manager.FirmwareManager;
import com.eightmile.adlauncher.model.HeartBeat;
import com.eightmile.adlauncher.service.WebSocketService;
import com.eightmile.adlauncher.util.Config;
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
	String ip = Config.get("domain");
	String path = Config.get("url.getLayout");
	String mac = Config.get("mac");
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
    	String latest_url = "http://"+ip+"/index.php?m=Api&c=PUpdate&a=getSelf&mid="+Config.get("mac")+"&type=1";
    	LogUtil.i(TAG, latest_url);
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
        queryNeedUpdate();
    }
    
    
    
    @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unbindService(connection);
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
    	
//    	LogUtil.i(TAG, "执行加载布局");
    	HttpUtil.sendOkHttpRequest(address, new Callback() {
			
			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				// TODO Auto-generated method stub
				LogUtil.d(TAG, "服务器相应的回调");
				if("lastest_api".equals(type)){
					
				}else if("init_api".equals(type)){
					
				}else if("layout_api".equals(type)){
					LogUtil.i("TAG", "layout_api");
					Utility.handleLayoutResponse(adLauncherDB, arg1);
				}else if("applist_api".equals(type)){
					
				}else if("adlist_api".equals(type)){
					
				}else if("instantmessage_api".equals(type)){
					
				}else if("switchs_api".equals(type)){
					
				}else if("volume_api".equals(type)){
					
				}else if("emergency_api".equals(type)){
					
				}
			}
			
			@Override
			public void onFailure(Call arg0, IOException arg1) {
				// TODO Auto-generated method stub
				
			}
		});
    }
    
    public void queryNeedUpdate(){
    	String address = "http://"+Config.get("domain")+"/index.php?m=Api&c=PUpdate&a=getSelf&mid="+Config.get("mac")+"&type=1";
    	LogUtil.i(TAG, "address:"+address);
    	HttpUtil.sendOkHttpRequest(address, new Callback() {
			
			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				// TODO Auto-generated method stub
				String body = arg1.body().string();
				LogUtil.i(TAG, "body:"+body);
				boolean isNeed = Utility.handleVersionResponse(body);
				if(isNeed){
					LogUtil.d(TAG, "需要安装最新版本");
					
					JSONObject jsonObject = JSONObject.parseObject(body);
					String url = jsonObject.getJSONObject("data").getString("local");
					String currentVersion = jsonObject.getJSONObject("data").getString("code");
					LogUtil.i(TAG, "url :"+url+"..."+"version :"+currentVersion);
					FirmwareManager fm = new FirmwareManager();
					fm.updateApp(url, Integer.parseInt(currentVersion));
				}else{
					LogUtil.d(TAG, "不需要安装最新版本，加载布局");
					String layout_url = "http://"+ip+path+"&mid="+mac;
					queryFromServer(layout_url, "layout_api");
				}
			}
			
			@Override
			public void onFailure(Call arg0, IOException arg1) {
				// TODO Auto-generated method stub
				String layout_url = "http://"+ip+path+"&mid="+mac;
				queryFromServer(layout_url, "layout_api");
			}
		});
    }
}
