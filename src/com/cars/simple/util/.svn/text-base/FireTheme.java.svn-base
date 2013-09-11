package com.cars.simple.util;

import java.util.HashMap;

import android.util.Log;

import com.cars.simple.logic.MainManager;
import com.cars.simple.service.baseData.FileHelper;

public class FireTheme {

	protected HashMap<String, String> themeProperties;
	/*******************************************************************
	 * 函数名称	: FireTheme
	 * 函数描述	: 构造方法
	 * 输入参数	: 
	 * 返回值  	: N/A
	 * 备注		: 为了节省内存，该方法中直接使用相应的字符串，没有定义字符串常量
	 ******************************************************************/
	public FireTheme() {
		
	}
	/*******************************************************************
	 * 函数名称	: loadFile
	 * 函数描述	: 加载资源文件
	 * 输入参数	: @param themeFile   -----   theme属性配置文件url
	 * 输入参数	: @throws IOException 
	 * 输出参数	: void
	 * 返回值  	: void
	 * 备注		: N/A
	 ******************************************************************/
	public void loadFile(String themeFile){
		themeProperties = FileHelper.loadProperties(MainManager.currentActivity,themeFile);
	}
	
	/*******************************************************************
	 * 函数名称	: getStringProperty
	 * 函数描述	: 获取key对应的字符串属�性
	 * 输入参数	: key        ----  字体属性对应的键值
	 * 返回值  	: String     ----  获取到的字符串
	 * 备注		: N/A
	 ******************************************************************/
	public String getStringProperty(String key){
		String v = (String) themeProperties.get(key);
		if (v != null){
			return v.trim();
		}
		return null;
	}

	/*******************************************************************
	 * 函数名称	: getIntProperty
	 * 函数描述	: 获取key对应的int属性
	 * 输入参数	: key   ----  int属性对应的键值
	 * 返回值	    : int   ----  获取到的int属性
	 * 备注		: N/A
	 ******************************************************************/
	public int getIntProperty(String key){
		String v = themeProperties.get(key);
		if (v == null){
			Log.i("theme NULL", "get theme NULL where key=" + key);
			return 0;
		}
		return Integer.parseInt(v);
	}
	/*******************************************************************
	 * 函数名称	: getIntProperty
	 * 函数描述	: 获取key对应的int属性
	 * 输入参数	: key   ----  int属性对应的键值
	 * 输入参数	: defaultValue   ----  默认值
	 * 返回值	    : int   ----  获取到的int属性
	 * 备注		: N/A
	 ******************************************************************/
	public int getIntProperty(String key,int defaultValue){
		String v = themeProperties.get(key);
		if (v == null){
			Log.i("theme NULL", "get theme NULL where key=" + key);
			return defaultValue;
		}
		return Integer.parseInt(v);
	}

	/*******************************************************************
	 * 函数名称	: getBooleanProperty
	 * 函数描述	: 获取key对应的boolean属�性
	 * 输入参数	: key        ----  对应的键值
	 * 返回值	    : boolean    ----  获取到的boolean属�性
	 * 备注		: N/A
	 ******************************************************************/
	public boolean getBooleanProperty(String key){
		String v = themeProperties.get(key);
		return Boolean.parseBoolean(v);
	}
}
