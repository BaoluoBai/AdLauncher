package com.eightmile.adlauncher.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.eightmile.adlauncher.model.DownloadBean;

public class LayoutManager {
	
	
	public static void createLayout(String layout){
		List<DownloadBean> downloadPicList = new ArrayList<DownloadBean>();
		JSONObject jsonObject = null;
		
		try {
			jsonObject = new JSONObject(layout);
			JSONObject data = jsonObject.getJSONObject("data");
			DownloadBean download = new DownloadBean("out_background", data.getString("background"), "out");    
			downloadPicList.add(download);
			JSONArray modules = data.getJSONArray("modules");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void addModules(JSONArray jsonArray){
		JSONObject module;
		for(int i = 0; i<jsonArray.length(); i++){
			try {
				module = (JSONObject) jsonArray.get(i);
				String mtype = module.getString("mtype");
				if(mtype.equals("app")){
//					addAppView(Rlayout, module);
				}
				if(mtype.equals("ad")){
//					addAdView(Rlayout, module);
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
}
