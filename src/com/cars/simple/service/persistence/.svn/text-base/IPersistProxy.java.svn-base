package com.cars.simple.service.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.cars.simple.util.Sqlite3TableColumn;

/**
 * sqlite3 数据库持久化类
 * @author shifeng
 *
 */
public interface IPersistProxy {
	
	public void execute(String sql);
	
    public void execute(String sql,Object[] bindArgs);
    
    public ArrayList<Map<String,Object>> query(String sql,String[] selectionArgs,List<Sqlite3TableColumn> columns);
    
    public long insert(String table,String nullColumnHack,ContentValues values);
	
	public int update(String table,ContentValues values,String whereClause,String[] whereArgs);
	
	public int delete(String table,String whereClause,String[] whereArgs);
	
	public void close(SQLiteDatabase db )  ;
}
