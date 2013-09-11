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
public class RegisterRequest {

	/**
	 * 注册
	 * 
	 * @param account
	 *            帐号
	 * @param nickname
	 *            昵称
	 * @param password
	 *            密码
	 */
	public void register(String account, String nickname, String password,
			Handler handler) {
		Request request = new Request();
		// /registered.jspx?userid=zyjaz&password=111111&username=aabbcc&email=zhuyj@mail.xwtec.cn
		String url = Variable.SERVER_SOFT_URL + "/registered.jspx?userid="
				+ account + "&password=" + password + "&username=" + nickname
				+ "&email=" + account;
		request.setHandler(handler);
		request.sendGetRequest(url);
	}

	/**
	 * 找回密码
	 * 
	 * @param handler
	 *            Handler
	 * @param email
	 *            email
	 */
	public void resetPassword(Handler handler, String email) {
		// http://localhost:8080/cars/forgetpwd.jspx?email=zyjaz@yahoo.com.cn
		Request request = new Request();
		String url = Variable.SERVER_SOFT_URL + "/forgetpwd.jspx?email="
				+ email;
		request.setHandler(handler);
		request.sendGetRequest(url);
	}

	/**
	 * 改变密码
	 * 
	 * @param handler
	 *            Handler
	 * @param oldpassword
	 *            原始密码
	 * @param newpassword
	 *            新密码
	 */
	public void changePassword(Handler handler, String oldpassword,
			String newpassword) {
		// http://localhost:8080/cars/changepwd.jspx?password=111111
		Request request = new Request();
		String url = Variable.SERVER_SOFT_URL + "/changepwd.jspx?password="
				+ newpassword + "&oldpassword=" + oldpassword;
		request.setHandler(handler);
		request.sendGetRequest(url);
	}
}
