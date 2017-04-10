package com.eightmile.adlauncher.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public class AdLauncherDB {
	/**
	 * 数据库表名
	 */
	public static final String DB_NAME = "ad_launcher";
	
	/**
	 * 数据库版本号
	 */
	public static final int DB_VERSION = 1;
	
	private static AdLauncherDB adLauncherDB;
	
	private SQLiteDatabase db;
	
	/**
	 * 构造方法私有化
	 * @param context
	 * @author zc
	 */
	private AdLauncherDB(Context context){
		AdLauncherOpenHelper dbHelper = new AdLauncherOpenHelper(context, DB_NAME, null, DB_VERSION);
		db = dbHelper.getWritableDatabase();
	}
	
	/**
	 * 获取AdLauncherDB实例
	 * @param context
	 * @return AdLauncherDB
	 * @author zc
	 */
	public synchronized static AdLauncherDB getInstance(Context context){
		if(adLauncherDB == null){
			adLauncherDB = new AdLauncherDB(context);
		}
		return adLauncherDB;
	}
}
