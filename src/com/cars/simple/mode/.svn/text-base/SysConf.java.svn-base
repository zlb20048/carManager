package com.cars.simple.mode;

import android.content.ContentValues;

import com.cars.simple.service.persistence.IPersistProxy;


/**
 * 系统配置表
 * @author shifeng
 *
 */
public final class SysConf implements Domain {
	
	private String key = null;
	
	private String value = null;
	
	private int scUsable = 0;

	public int getScUsable() {
		return scUsable;
	}

	public void setScUsable(int scUsable) {
		this.scUsable = scUsable;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	

	public final String KEY = "sc_key";
	private final String VALUE = "sc_value";
	private final String USABLE = "sc_usable";
	private final String TABLE_NAME = "t_sys_conf";
	
	public long save(IPersistProxy persist) {
		ContentValues args = new ContentValues();
		args.put(KEY, key);
		args.put(VALUE, value);
		args.put(USABLE, scUsable);
		return persist.insert(TABLE_NAME, null, args);
	}

	public int edit(IPersistProxy persist) {
		ContentValues args = new ContentValues();
		args.put(VALUE, value);
		args.put(USABLE, scUsable);
		return  persist.update(TABLE_NAME,args,KEY+"=?",new String[]{key});
//		Object[] param = new Object[2];
//		param[0] = value;
//		param[1] = "version";
//		persist.execute("update t_sys_conf set value = ? where key = ?", param);
//		return 1;
	}

	public int remove(IPersistProxy persist) {
		return persist.delete(TABLE_NAME, KEY+"=?", new String[]{key});
		//return persist.delete(TABLE_NAME, KEY+"="+key, null);
	}



}
