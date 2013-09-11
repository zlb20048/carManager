package com.cars.simple.util;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cars.simple.fusion.FusionCode;


/**
 * 数据库基础操作类
 * @author shifeng
 *
 */
public final class Sqlite3Template extends SQLiteOpenHelper {
	
	public Sqlite3Template(Context context) {
		 super(context,FusionCode.DB_NAME, null, FusionCode.DB_VERSION);  
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//db.execSQL("CREATE TABLE IF NOT EXISTS cache (key TEXT PRIMARY KEY,val TEXT );");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//db.execSQL("DROP TABLE IF EXISTS cache");
		onCreate(db);
	}

}
