package com.cars.simple.service.persistence.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cars.simple.service.persistence.IPersistProxy;
import com.cars.simple.util.Sqlite3TableColumn;
import com.cars.simple.util.Sqlite3TableColumnType;
import com.cars.simple.util.Sqlite3Template;

/**
 * 数据库持久化实现类
 * @author shifeng
 *
 */
public class PersistProxy implements IPersistProxy {
	
//	private static IPersistProxy persist = null;
	
	private Sqlite3Template sqlite3Template = null;
	
	public PersistProxy(Context context) {
		sqlite3Template = new Sqlite3Template(context);
	};
	
//	public static synchronized IPersistProxy getInstance(Context context) {
//		if (persist == null) {
//			persist = new PersistProxy(context);
//		}
//		return persist;
//	}

	public void execute(String sql,Object[] bindArgs) {
		 SQLiteDatabase db = sqlite3Template.getWritableDatabase();
		 if (db.isOpen() && !db.isReadOnly()) {
			 db.execSQL(sql, bindArgs);
			 db.close();
		 }
	}
	
	public void execute(String sql) {
		 SQLiteDatabase db = sqlite3Template.getWritableDatabase();
		 if (db.isOpen() && !db.isReadOnly()) {
			 db.execSQL(sql);
			 db.close();
		 }
		
	}
	
	public ArrayList<Map<String,Object>> query(String sql,String[] selectionArgs,List<Sqlite3TableColumn> columns) {
		if (columns == null) {
			return null;
		}
		SQLiteDatabase db = sqlite3Template.getWritableDatabase();
		Cursor cursor = db.rawQuery(sql, selectionArgs);
		ArrayList<Map<String,Object>> list = null;
		if (cursor != null) {
			list = new ArrayList<Map<String,Object>>();
			while (cursor.moveToNext()) {
				Map<String,Object> map = new HashMap<String,Object>();
				for (Sqlite3TableColumn c:columns) {
					if (c != null && c.getColumnName() != null && c.getColumnType() != null) {
						if (c.getColumnType() == Sqlite3TableColumnType.SQLITE3_INT) {
							 int temInt = cursor.getInt(cursor.getColumnIndex(c.getColumnName()));  
							 map.put(c.getColumnName(), temInt);
						} else if (c.getColumnType() == Sqlite3TableColumnType.SQLITE3_TEXT) {
							 String temStr = cursor.getString(cursor.getColumnIndex(c.getColumnName()));  
							 map.put(c.getColumnName(), temStr);
						} else if (c.getColumnType() == Sqlite3TableColumnType.SQLITE3_FLOAT) {
							 float temStr = cursor.getFloat(cursor.getColumnIndex(c.getColumnName()));  
							 map.put(c.getColumnName(), temStr);
						} else if (c.getColumnType() == Sqlite3TableColumnType.SQLITE3_BLOB) {
							map.put(c.getColumnName(), cursor.getBlob(cursor.getColumnIndex(c.getColumnName())));
						} else {
							continue;
						}
					}
				}
				list.add(map);
			}
			cursor.close();
		}
		db.close();
		return list;
	}

	public long insert(String table,String nullColumnHack,ContentValues values) {
		SQLiteDatabase db = sqlite3Template.getWritableDatabase();
		long res = db.insert(table, nullColumnHack, values);
		db.close();
		return res;
	}
	
	public int update(String table,ContentValues values,String whereClause,String[] whereArgs) {
		SQLiteDatabase db = sqlite3Template.getWritableDatabase();
		int res = db.update(table, values, whereClause, whereArgs);
		db.close();
		return res;
	}
	
	public int delete(String table,String whereClause,String[] whereArgs) {
		SQLiteDatabase db = sqlite3Template.getWritableDatabase();
		int res = db.delete(table, whereClause, whereArgs);
		db.close();
		return res;
	}
	
	public void close(SQLiteDatabase db ) {
		if (db != null) {
			db.close();
		}
	}

}
