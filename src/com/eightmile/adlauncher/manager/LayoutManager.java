package com.eightmile.adlauncher.manager;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

import com.eightmile.adlauncher.R;
import com.eightmile.adlauncher.activity.AdApplication;
import com.eightmile.adlauncher.model.DownloadBean;

public class LayoutManager {
	
	private Activity activity;
	
	private RelativeLayout out_layout;
	
	List<DownloadBean> downloadPicList;
	
	public LayoutManager(){
		this.activity = AdApplication.currentActivity();
		this.out_layout = (RelativeLayout) AdApplication.currentActivity().findViewById(R.id.outerFrame);
		this.downloadPicList = new ArrayList<DownloadBean>();
	}
	
	public void createLayout(String layout){
		activity = AdApplication.currentActivity();
		out_layout = (RelativeLayout) activity.findViewById(R.id.outerFrame);
		
		JSONObject jsonObject = null;
		
		try {
			jsonObject = new JSONObject(layout);
			JSONObject data = jsonObject.getJSONObject("data");
			DownloadBean download = new DownloadBean("out_background", data.getString("background"), "out");    
			downloadPicList.add(download);
			JSONArray modules = data.getJSONArray("modules");
			addModules(out_layout, modules);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void addModules(RelativeLayout relativeLayout, JSONArray jsonArray){
		JSONObject module;
		for(int i = 0; i<jsonArray.length(); i++){
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
//					addWebImage(Rlayout, module);
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
    	try {
    		height = object.getInt("height");
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	try {
    		width = object.getInt("width");
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	try {
    		marginleft = object.getInt("pleft");
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	try {
    		margintop = object.getInt("ptop");
		} catch (JSONException e) {
			e.printStackTrace();
		}
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
    		height = object.getInt("height");
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	try {
    		width = object.getInt("width");
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	try {
    		marginleft = object.getInt("pleft");
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	try {
    		margintop = object.getInt("ptop");
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
    private void addWebImage(RelativeLayout Rlayout,JSONObject object){
    	Log.e("layout: ","add web module");
    	int height = 0;
    	int width = 0;
    	int marginleft = 0;
    	int margintop = 0;
    	String img = null;
    	String url = null;
    	try {
    		height = object.getInt("height");
    		width = object.getInt("width");
    		width = object.getInt("width");
    		marginleft = object.getInt("pleft");
    		margintop = object.getInt("ptop");
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	try {

		} catch (JSONException e) {
			e.printStackTrace();
		}
    	try {
    		
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	try {
    		
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	try {
    		img = filePath + object.getString("background");
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	try {
			url = object.getString("url");
			uri = Uri.parse(url);
			url_list.add(uri_index, uri);
		} catch (JSONException e) {
			e.printStackTrace();
		}
        ImageView imageView = new ImageView(activity);
        //设置图片
        Drawable bg=Drawable.createFromPath(img);
        imageView.setId(uri_index);
        imageView.setBackground(bg);
        //设置imageview属性
        RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(width,height);
        rp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        rp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        rp.setMargins(marginleft, margintop, 0, 0);
        //按属性添加
        Rlayout.addView(imageView,rp);
		uri_index++;
        
        //设置图片点击响应
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
	
}
