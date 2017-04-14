package com.eightmile.adlauncher.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.eightmile.adlauncher.R;
import com.eightmile.adlauncher.activity.AdApplication;
import com.eightmile.adlauncher.model.DownloadBean;
import com.eightmile.adlauncher.util.Config;
import com.eightmile.adlauncher.util.DownloadListener;
import com.eightmile.adlauncher.util.DownloadUtil;
import com.eightmile.adlauncher.util.LogUtil;
import com.eightmile.adlauncher.util.Tools;

public class LayoutManager {
	public static final String TAG = "LayoutManager";
	
	private Activity activity;
	
	int uri_index = 0;
	
	Uri uri = null;
	
	ArrayList<Uri> url_list = new ArrayList<Uri>();
	
	private RelativeLayout out_layout;
	
	List<DownloadBean> downloadPicList;
	
	String filePath = Config.get("layoutPath");
	
	public LayoutManager(){
		this.activity = AdApplication.currentActivity();
		this.out_layout = (RelativeLayout) AdApplication.currentActivity().findViewById(R.id.outerFrame);
		this.downloadPicList = new ArrayList<DownloadBean>();
	}
	
	public void createLayout(String layout){
		activity = AdApplication.currentActivity();
		out_layout = (RelativeLayout) activity.findViewById(R.id.outerFrame);
		
		JSONObject jsonObject = JSONObject.parseObject(layout);
		JSONObject data = jsonObject.getJSONObject("data");
		DownloadBean download = new DownloadBean("out_background", data.getString("background"), "out");    
		downloadPicList.add(download);
		JSONArray modules = data.getJSONArray("modules");
		addModules(out_layout, modules);
		
	}
	
	public void addModules(RelativeLayout relativeLayout, JSONArray jsonArray){
		JSONObject module;
		for(int i = 0; i<jsonArray.size(); i++){
			try {
				module = (JSONObject) jsonArray.get(i);
				String mtype = module.getString("mtype");
				if(mtype.equals("app")){
					addAppView(relativeLayout, module);
				}
				if(mtype.equals("ad")){
					addAdView(relativeLayout, module);
				}
				if(mtype.equals("web")){
					addWebImage(relativeLayout, module);
				}
				if(mtype.equals("phone")){
//					phoneCtrl(Rlayout, module);
				}
				if(mtype.equals("rect")){
//					addRect(Rlayout, module);
				}
				if(mtype.equals("content")){
//					addContentView(Rlayout, module);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 加载用于显示应用程序的布局
	 * @param Rlayout 父布局文件
	 * @param object 应用程序布局数据
	 */
	private void addAppView(RelativeLayout Rlayout,JSONObject object){
    	Log.e("layout: ","add app module");
    	int height = 0;
    	int width = 0;
    	int marginleft = 0;
    	int margintop = 0;
    	height = object.getIntValue("height");
    	width = object.getIntValue("width");
    	marginleft = object.getIntValue("pleft");
    	margintop = object.getIntValue("ptop");
    	RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(width,height);
        rp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        rp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        rp.setMargins(marginleft, margintop, 0, 0);
        // 获取需要添加的布局  
        LinearLayout layout = 
        		(LinearLayout) View
        			.inflate(activity,R.layout.appview, null)
        			.findViewById(R.id.appview);
        // 将布局加入到当前布局中  
        Rlayout.addView(layout,rp);
    }
	
	
	/**
     * 动态插入视频区
     * @param object
     */
    private void addAdView(RelativeLayout Rlayout,JSONObject object){
		Log.e("layout: ","add ad module");
    	int height = 0;
    	int width = 0;
    	int marginleft = 0;
    	int margintop = 0;
    	try {
    		height = object.getIntValue("height");
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	try {
    		width = object.getIntValue("width");
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	try {
    		marginleft = object.getIntValue("pleft");
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	try {
    		margintop = object.getIntValue("ptop");
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(width,height);
        rp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        rp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        rp.setMargins(marginleft, margintop, 0, 0);
        // 获取需要添加的布局  
    	RelativeLayout layout = 
    			(RelativeLayout) View
    				.inflate(activity,R.layout.adview, null)
    				.findViewById(R.id.adview);
    	//将布局加入到当前布局中
        Rlayout.addView(layout,rp);
    }
    
    /**
     * 动态插入网页图片
     * @param object
     */
    private void addWebImage(final RelativeLayout Rlayout, JSONObject object){
    	Log.e("layout: ","add web module");
    	//解析JSONObject
    	final int width = object.getIntValue("width");
    	final int height = object.getIntValue("height");
    	final int pleft = object.getIntValue("pleft");
    	final int ptop = object.getIntValue("ptop");
    	final String url = object.getString("url");
    	String address_last = object.getString("background");
    	String savePath = Config.get("layoutPath");
    	String address = Config.get("domain")+address_last;
    	//获取文件名
    	final String fileName = Tools.getNameFromUrl(address_last);
    	LogUtil.i(TAG, "fileName" + fileName);
    	//判断文件是否存在
    	if(Tools.fileIsExist(savePath+"/"+fileName)){
    		ImageView imageView = new ImageView(activity);
			Drawable bg=Drawable.createFromPath(savePath+"/"+fileName);
			imageView.setId(uri_index);
	        imageView.setBackground(bg);
	        
			uri = Uri.parse(url);
			url_list.add(uri_index, uri);
	        imageView.setId(uri_index);
	        imageView.setBackground(bg);
	        RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(width,height);
	        rp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
	        rp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
	        rp.setMargins(pleft, ptop, 0, 0);
	        //按属性添加
	        Rlayout.addView(imageView,rp);
			uri_index++;
			OnClickListener openUrl = new OnClickListener() {
	            @Override  
	            public void onClick(View v) {
	            	Intent intent = null;
	            	Uri uri = url_list.get(v.getId());
	            	Log.e("layout:",v.getId()+"的uri: "+uri);
	                intent = new Intent();
	                intent.setData(uri).setAction("android.intent.action.VIEW");
	                activity.startActivity(intent);
	            }  
	        };
	        imageView.setOnClickListener(openUrl);
    	}else{
    		DownloadUtil.get().download(address, filePath, new DownloadListener() {
    			
    			@Override
    			public void onDownloading(int progress) {
    				// TODO Auto-generated method stub
    				
    			}
    			
    			@Override
    			public void onDownloadSuccess() {
    				// TODO Auto-generated method stub
    				ImageView imageView = new ImageView(activity);
    				Drawable bg=Drawable.createFromPath(filePath+fileName);
    				imageView.setId(uri_index);
    		        imageView.setBackground(bg);
    		        
    				uri = Uri.parse(url);
    				url_list.add(uri_index, uri);
    		        imageView.setId(uri_index);
    		        imageView.setBackground(bg);
    		        RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(width,height);
    		        rp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
    		        rp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
    		        rp.setMargins(pleft, ptop, 0, 0);
    		        //按属性添加
    		        Rlayout.addView(imageView,rp);
    				uri_index++;
    				OnClickListener openUrl = new OnClickListener() {
    		            @Override  
    		            public void onClick(View v) {
    		            	Intent intent = null;
    		            	Uri uri = url_list.get(v.getId());
    		            	Log.e("layout:",v.getId()+"的uri: "+uri);
    		                intent = new Intent();
    		                intent.setData(uri).setAction("android.intent.action.VIEW");
    		                activity.startActivity(intent);
    		            }  
    		        };
    		        imageView.setOnClickListener(openUrl);
    			}
    			
    			@Override
    			public void onDownloadFailed() {
    				// TODO Auto-generated method stub
    				LogUtil.e(TAG, "文件下载失败");
    			}
    		});
    	} 	       
        
    }
    	
}
