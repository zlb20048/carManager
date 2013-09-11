package com.cars.simple.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;

import com.cars.simple.service.persistence.IPersistProxy;
import com.cars.simple.service.persistence.impl.PersistProxy;
import com.cars.simple.util.Sqlite3TableColumn;
import com.cars.simple.util.Sqlite3TableColumnType;

public class CacheOpt {
	private final String KEY = "key";
	private final String VALUE = "val";
	
	public long save(String key,String val,Context context) {
		IPersistProxy persist = new PersistProxy(context);
		ContentValues args = new ContentValues();
		args.put(KEY, key);
		args.put(VALUE, val);
		return persist.insert("cache", null, args);
		
	}
	
	public long update(String key,String val,Context context) {
		IPersistProxy persist = new PersistProxy(context);
		ContentValues args = new ContentValues();
		args.put(VALUE, val);
		return persist.update("cache", args, "key=?", new String[]{key});
	}

	public String getValue(String key, Context context) {
		String sql = "select key,val from cache where key = ?";
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
	
	public List<Map<String, Object>> getProvince(Context context) {
		String sql = "select id,name from s_province where (1=1 or 1=?)";
		IPersistProxy persist = new PersistProxy(context);
		List<Sqlite3TableColumn> list = new ArrayList<Sqlite3TableColumn>();
		Sqlite3TableColumn stc = new Sqlite3TableColumn();
		stc.setColumnName("id");
		stc.setColumnType(Sqlite3TableColumnType.SQLITE3_TEXT);
		list.add(stc);
		Sqlite3TableColumn stc1 = new Sqlite3TableColumn();
		stc1.setColumnName("name");
		stc1.setColumnType(Sqlite3TableColumnType.SQLITE3_TEXT);
		list.add(stc1);
		List<Map<String, Object>> reList = persist.query(sql,
				new String[] {"1"}, list);
		return reList;
	}
	
	public List<Map<String, Object>> getCity(String pid,Context context) {
		String sql = "select id,name from s_city where pid = ?";
		IPersistProxy persist = new PersistProxy(context);
		List<Sqlite3TableColumn> list = new ArrayList<Sqlite3TableColumn>();
		Sqlite3TableColumn stc = new Sqlite3TableColumn();
		stc.setColumnName("id");
		stc.setColumnType(Sqlite3TableColumnType.SQLITE3_TEXT);
		list.add(stc);
		Sqlite3TableColumn stc1 = new Sqlite3TableColumn();
		stc1.setColumnName("name");
		stc1.setColumnType(Sqlite3TableColumnType.SQLITE3_TEXT);
		list.add(stc1);
		List<Map<String, Object>> reList = persist.query(sql,
				new String[] {pid}, list);
		return reList;
	}
	
	public String getProvinceId(String cityid, Context context) {
		String sql = "select p.id as id from s_province p,s_city c where p.id = c.pid and c.id = ?";
		String scValue = null;
		IPersistProxy persist = new PersistProxy(context);
		List<Sqlite3TableColumn> list = new ArrayList<Sqlite3TableColumn>();
		Sqlite3TableColumn stc = new Sqlite3TableColumn();
		stc.setColumnName("id");
		stc.setColumnType(Sqlite3TableColumnType.SQLITE3_TEXT);
		list.add(stc);
		List<Map<String, Object>> reList = persist.query(sql,
				new String[] { cityid }, list);
		if (reList != null && !reList.isEmpty()) {
			Map<String, Object> map = reList.get(0);
			if (map != null && !map.isEmpty()) {
				// String scKey = (String)map.get(KEY);
				scValue = (String) map.get("id");
			}
		}
		return scValue;
	}
}
