package com.cars.simple.service.baseData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.cars.simple.service.persistence.IPersistProxy;
import com.cars.simple.service.persistence.impl.PersistProxy;
import com.cars.simple.util.Sqlite3TableColumn;
import com.cars.simple.util.Sqlite3TableColumnType;

/**
 * 
 * @author shifeng
 *
 */
public class SysConfProcessor {
	private final String KEY = "key";
	private final String VALUE = "val";

	private final String sql = "select key,val from cache where key = ?";

	public String getValue(String key, Context context) {
		String scValue = null;
		IPersistProxy persist = new PersistProxy(context);
		List<Sqlite3TableColumn> list = new ArrayList<Sqlite3TableColumn>();
		Sqlite3TableColumn stc = new Sqlite3TableColumn();
		stc.setColumnName(KEY);
		stc.setColumnType(Sqlite3TableColumnType.SQLITE3_TEXT);
		list.add(stc);
		Sqlite3TableColumn stc1 = new Sqlite3TableColumn();
		stc1.setColumnName(VALUE);
		stc1.setColumnType(Sqlite3TableColumnType.SQLITE3_TEXT);
		list.add(stc1);
		List<Map<String, Object>> reList = persist.query(sql,
				new String[] { key }, list);
		if (reList != null && !reList.isEmpty()) {
			Map<String, Object> map = reList.get(0);
			if (map != null && !map.isEmpty()) {
				// String scKey = (String)map.get(KEY);
				scValue = (String) map.get(VALUE);
			}
		}
		return scValue;
	}
	
	/**
	 * 用于检测数据库db 是否存在
	 * @param context
	 * @return
	 */
	public boolean checkDataBase(Context context){
		boolean initDataBase = true;
		String sql = "select val from cache";
		IPersistProxy persist = new PersistProxy(context);
		List<Sqlite3TableColumn> list = new ArrayList<Sqlite3TableColumn>();
		Sqlite3TableColumn stc = new Sqlite3TableColumn();
		stc.setColumnName("val");
		stc.setColumnType(Sqlite3TableColumnType.SQLITE3_TEXT);
		list.add(stc);
		try{
			persist.query(sql,new String[] { "key" }, list);
		}
		catch(Exception e){
			initDataBase = false;
		}
		return initDataBase;
	}

}
