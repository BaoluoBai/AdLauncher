package com.eightmile.adlauncher.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import android.widget.Toast;

import com.eightmile.adlauncher.activity.AdApplication;
import com.eightmile.adlauncher.db.AdLauncherDB;
import com.eightmile.adlauncher.model.LayoutInfo;

public class Utility {
	
	/**
	 * 用于处理从服务器返回的布局数据	
	 * step1: 获取本地数据库存储的布局，如果为空，则直接加载从服务器请求到的布局，并插入到数据库,如果不为空则进入step2;
	 * step2: 判断本地数据库存储的布局数据与从服务器请求到的布局数据是否一样，如果一样，则直接加载本地布局，如果不一样则进入step3;
	 * step3: 加载从服务器返回的布局数据，并覆盖原先数据库中的布局数据
	 * @param adLauncherDB 数据库操作实例
	 * @param response 需要处理的布局数据
	 * @return boolean
	 */
	public static boolean handleLayoutResponse(AdLauncherDB adLauncherDB, String response){
		if(!TextUtils.isEmpty(response)){
			JSONObject jsonObject;
			String status = "";
			try {
				jsonObject = new JSONObject(response);
				status = jsonObject.getString("status");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(status.equals("1")){
				List<LayoutInfo> list = new ArrayList<LayoutInfo>();
				list = adLauncherDB.loadLayout();
				if(list.size() == 0){
					//直接加载从服务器请求的数据，并将数据插入到数据库
					adLauncherDB.saveLayout(response);
					return true;
				}else{
					if(list.get(0).getContent().equals(response)){
						//加载本地布局
						
						return true;
					}else{
						//加载从服务器请求的布局，并覆盖原数据库中的数据
						
						return true;
					}
				}
			}else {
				Toast.makeText(AdApplication.getContext(), "服务器错误,加载本地布局", Toast.LENGTH_SHORT).show();
				return false;
			}
			
		}
		return false;
	}
}
