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
public class LoginRequest {

	/**
	 * 登录请求
	 * 
	 * @param username
	 *            名称
	 * @param password
	 *            密码
	 */
	public void login(String username, String password, Handler handler) {
		// http://localhost:8080/cars/login.jspx?username=zhuyujie&password=111111
		Request request = new Request();
		String url = Variable.SERVER_SOFT_URL + "/login.jspx?username="
				+ username + "&password=" + password;
		request.setHandler(handler);
		request.sendGetRequest(url);
		// Request request = new Request();
		// String url = "http://www.jswxcs.cn/1.txt";
		// request.setHandler(handler);
		// request.sendGetRequest(url);
	}

}
