/**
 * 
 */
package com.cars.simple.logic;

import android.os.Handler;

import com.cars.simple.fusion.Variable;
import com.cars.simple.service.request.Request;

/**
 * @author liuzixiang
 * 
 */
public class DefaultCarRequest {

	/**
	 * 获取用户默认车辆信息信息
	 * 
	 * @param handler
	 */
	public void getDefaultCarMessage(Handler handler) {
		// http://localhost:8080/cars/userinfo.jspx
		Request request = new Request();
		String url = Variable.SERVER_SOFT_URL + "/userinfo.jspx";
		request.setHandler(handler);
		request.sendGetRequest(url);
	}

	/**
	 * 设置用户信息
	 * 
	 * @param handler
	 *            当前的回调方法
	 * @param userCarId
	 *            用户的车辆id
	 * @param city
	 *            设置的城市
	 */
	public void saveDefaultCarMessage(Handler handler, String userCarId,
			String city) {
		// http://localhost:8080/cars/setuserinfo.jspx?usercarid=23&city=sz
		Request request = new Request();
		String url = Variable.SERVER_SOFT_URL + "/setuserinfo.jspx?usercarid="
				+ userCarId + "&city=" + city;
		request.setHandler(handler);
		request.sendGetRequest(url);
	}
}
