/**
 * 
 */
package com.cars.simple.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * @author liuzixiang
 * 
 */
public class ResponsePaseUtil {

	private final static String TAG = ResponsePaseUtil.class.getSimpleName();

	private static ResponsePaseUtil instance;

	private ResponsePaseUtil() {

	}

	public static ResponsePaseUtil getInstance() {
		if (null == instance) {
			instance = new ResponsePaseUtil();
		}
		return instance;
	}

	/**
	 * 解析数据
	 * 
	 * @param response
	 *            得到的resonse数据
	 * @return
	 */
	public Map<String, Object> parseResponse(String response) {
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			if (response == null || response.equals("")) {
				return null;
			}
			JSONObject jsonObject = new JSONObject(response);
			String code = "-1";
			if (jsonObject.has("code")) {
				code = jsonObject.getString("code");
			}
			String msg = "";
			if (jsonObject.has("msg")) {
				msg = jsonObject.getString("msg");
			}
			int allpage = 0;
			if (jsonObject.has("allpage")) {
				allpage = jsonObject.getInt("allpage");
				map.put("allpage", allpage);
			}

			Object all = 0;
			if (jsonObject.has("all")) {
				all = jsonObject.get("all");
				map.put("all", all);
			}

			if (jsonObject.has("lastRanking")) {
				map.put("lastRanking", jsonObject.get("lastRanking"));
			}

	        if (jsonObject.has("id")) {
	            map.put("id", jsonObject.get("id"));
	         }
			
			if (jsonObject.has("lastAllRanking")) {
				map.put("lastAllRanking", jsonObject.get("lastAllRanking"));
			}
			
			if (jsonObject.has("desc")) {
			    map.put("desc",jsonObject.get("desc"));
			}

			int res = Integer.parseInt(code);

			map.put("code", res);
			map.put("msg", msg);

			if (jsonObject.has("obj")) {
				Object resultObj = jsonObject.get("obj");
				if (resultObj != null && !resultObj.equals("null")) {
					if (resultObj instanceof JSONArray) {
						JSONArray jsonArray = (JSONArray) resultObj;
						for (int i = 0; i < jsonArray.length(); i++) {
							HashMap<String, Object> tempmap = new HashMap<String, Object>();
							JSONObject jsonObj1 = jsonArray.getJSONObject(i);
							Iterator<?> iter = jsonObj1.keys();
							while (iter.hasNext()) {
								String kk = (String) iter.next();
								Object o2 = jsonObj1.get(kk);
								tempmap.put(kk, o2);
							}
							list.add(tempmap);
						}
						map.put("obj", list);
					} else if (resultObj instanceof JSONObject) {
						HashMap<String, Object> tempmap = new HashMap<String, Object>();
						JSONObject objJson = (JSONObject) resultObj;
						Iterator<?> iter = objJson.keys();
						while (iter.hasNext()) {
							String key = (String) iter.next();
							Object value = objJson.get(key);
							if (value instanceof JSONArray) {
								ArrayList<Map<String, Object>> ll = new ArrayList<Map<String, Object>>();
								JSONArray baseListArray = (JSONArray) value;
								for (int i = 0; i < baseListArray.length(); i++) {
									Map<String, Object> mm = new HashMap<String, Object>();
									JSONObject objlist = baseListArray
											.getJSONObject(i);
									Iterator<?> iter1 = objlist.keys();
									while (iter1.hasNext()) {
										String kk = (String) iter1.next();
										Object o2 = objlist.get(kk);
										mm.put(kk, o2);
									}
									ll.add(mm);
								}
								tempmap.put(key, ll);
							} else {
								tempmap.put(key, value);
							}
						}
						map.put("obj", tempmap);
					}
				}
			}
			if (jsonObject.has("objlist")) {
				Object resultObj = jsonObject.get("objlist");
				if (resultObj instanceof JSONArray) {
					JSONArray jsonArray = (JSONArray) resultObj;
					for (int i = 0; i < jsonArray.length(); i++) {
						HashMap<String, Object> tempmap = new HashMap<String, Object>();
						JSONObject jsonObj1 = jsonArray.getJSONObject(i);
						Iterator<?> iter = jsonObj1.keys();
						while (iter.hasNext()) {
							String kk = (String) iter.next();
							Object o2 = jsonObj1.get(kk);
							tempmap.put(kk, o2);
						}
						list.add(tempmap);
					}
					map.put("objlist", list);
				}
			}
			if (jsonObject.has("monthCost")) {
				Object resultObj = jsonObject.get("monthCost");
				if (resultObj instanceof JSONObject) {
					JSONObject jsonObj1 = (JSONObject) resultObj;
					Iterator<?> iter = jsonObj1.keys();
					Map<String, Object> tempmap = new HashMap<String, Object>();
					while (iter.hasNext()) {
						String kk = (String) iter.next();
						Object o2 = jsonObj1.get(kk);
						tempmap.put(kk, o2);
					}
					map.put("monthCost", tempmap);
				}
			}
			if (jsonObject.has("alltimesCosts")) {
				Object resultObj = jsonObject.get("alltimesCosts");
				if (resultObj instanceof JSONObject) {
					JSONObject jsonObj1 = (JSONObject) resultObj;
					Iterator<?> iter = jsonObj1.keys();
					Map<String, Object> tempmap = new HashMap<String, Object>();
					while (iter.hasNext()) {
						String kk = (String) iter.next();
						Object o2 = jsonObj1.get(kk);
						tempmap.put(kk, o2);
					}
					map.put("alltimesCosts", tempmap);
				}
			}

		} catch (JSONException e) {
			Log.e(TAG, e.getMessage());
		}
		return map;
	}
}
