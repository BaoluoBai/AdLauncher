package com.eightmile.adlauncher.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class AdLauncherOpenHelper extends SQLiteOpenHelper {
	public static final String CREATE_VEDIO = "create table vedio(" +
			"id integer primary key autoincrement, " +
			"name text, " +
			"location text, " +
			"begintime integer, " +
			"endtime integer, " +
			"count integer, " +
			"priority integer, " +
			"aid text, " +
			"validity integer, " +
			"validity_start integer, " +
			"timelong text, " +
			"atype text, " +
			"url text, " +
			"md5 text)";
	
	public static final String CREATE_ADLOG = "create table adlog(" +
			"id integer primary key autoincrement," +
			"aid text, " +
			"name text, " +
			"timelong text, " +
			"created_at text)";
	
	public static final String CREATE_DOWNLOADLOG = "create table downloadlog(" +
			"id integer primary key autoincrement, " +
			"aid text, " +
			"name text, " +
			"type text, " +
			"created_at text, " +
			"status text)";
	
	public static final String CREATE_DEVICEINFO = "create table deviceinfo(" +
			"id integer primary key autoincrement, " +
			"created_at text, " +
			"switchs text, " +
			"volume text, " +
			"cpu text, " +
			"flash text, " +
			"disc text, " +
			"version text, " +
			"ip text)";

	public AdLauncherOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_VEDIO);
		db.execSQL(CREATE_ADLOG);
		db.execSQL(CREATE_DOWNLOADLOG);
		db.execSQL(CREATE_DEVICEINFO);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
