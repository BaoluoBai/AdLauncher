package com.eightmile.adlauncher.manager;

import java.io.File;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import com.eightmile.adlauncher.activity.AdApplication;
import com.eightmile.adlauncher.util.Config;
import com.eightmile.adlauncher.util.DownloadListener;
import com.eightmile.adlauncher.util.DownloadUtil;
import com.eightmile.adlauncher.util.HttpUtil;
import com.eightmile.adlauncher.util.LogUtil;
import com.eightmile.adlauncher.util.Tools;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class FirmwareManager {
	public static final String TAG = "FirmwareManager";
	String apkPath;
	String apkVersionFile = "/version.txt";
	Boolean check_from_server = false;
	int reconnect_count = 0;
	int old_version;
	
	/**
	 * 从本地获取保存的版本号，如果该文件不存在则创建，写入原始版本号0;
	 * @return 本地保存的版本号
	 */
	public int oldVersion(){
		File file = new File(Config.get("layoutPath") + apkVersionFile);
		if (!file.exists()) {
			try {
				file.createNewFile();
				Tools.writeStrToFile("0", apkVersionFile, Config.get("layoutPath"), false);
				old_version = 0;
			} catch (IOException e) {
				LogUtil.e(TAG, "创建文件失败");
			}
		}else{
			// 获取现version
			int version_data;
			try {
				String s = Tools.getStrFromFile(apkVersionFile, Config.get("layoutPath"));
				LogUtil.d(TAG, s+" version");
				version_data = Integer.parseInt(Tools.getStrFromFile(apkVersionFile, Config.get("layoutPath")));
				old_version = version_data;
			} catch (JSONException e1) {
				LogUtil.e(TAG, "获取版本号失败");
			}
		}
		return old_version;
	}
	
	
	public void updateApp(final String address, final int version){
		String url = "http:"+Config.get("domain") + address;
		LogUtil.i(TAG, url);
		String saveDir = Config.get("layoutPath");
		DownloadUtil.get().download(url, saveDir, new DownloadListener() {
			
			@Override
			public void onDownloading(int progress) {
				// TODO Auto-generated method stub
				LogUtil.i(TAG, "downloading new version app...");
			}
			
			@Override
			public void onDownloadSuccess() {
				// TODO Auto-generated method stub
				String apkName = Tools.getNameFromUrl(address);
				apkPath = Config.get("layoutPath")+"/"+apkName;
				installApp();
				Tools.writeStrToFile(version+"", apkVersionFile, Config.get("layoutPath"), false);
			}
			
			@Override
			public void onDownloadFailed() {
				// TODO Auto-generated method stub
				LogUtil.e(TAG, "download new version app error");
			}
		});
	}
	
	
	private void installApp() {
		LogUtil.i(TAG,"install new version APP");
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(apkPath)),
				"application/vnd.android.package-archive");
		AdApplication.currentActivity().startActivity(intent);
		
	}
}
