package com.eightmile.adlauncher.activity;

import android.app.Application;
import android.content.Context;

public class AdApplication extends Application {

	private static Context context;
	
	public static Context getContext(){
    	return context;
    }

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		context = getApplicationContext();
	}
	
	
}
