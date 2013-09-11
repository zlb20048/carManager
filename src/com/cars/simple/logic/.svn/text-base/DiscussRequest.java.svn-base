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
public class DiscussRequest {
	/**
	 * 设置用户信息
	 * 
	 * @param handler
	 *            当前的回调方法
	 * @param key
	 *            关键字
	 * @param type
	 *            类型
	 */
	public void getBussinessId(Handler handler, String key, int type) {
		// http://localhost:8080/cars//businesses/selectbusinesseslist.jspx?key=1&type=-2

		Request request = new Request();
		String url = Variable.SERVER_SOFT_URL
				+ "/businesses/selectbusinesseslist.jspx?key=" + key + "&type="
				+ type;
		request.setHandler(handler);
		request.sendGetRequest(url);
	}

	/**
	 * 点评商家
	 * 
	 * @param handler
	 *            handler
	 * @param bussinessid
	 *            商家id
	 * @param content
	 *            内容
	 * @param price
	 *            价格
	 * @param milieu
	 *            环境
	 * @param service
	 *            服务
	 */
	public void discussBussiness(Handler handler, String bussinessid,
			String content, String price, String milieu, String service, String name) {
		// http://localhost:8080/cars/businesses/addDiscuss.jspx?businessesid=6
		// &content=good&pricescore=3&milieuscore=4&servicescore=5

		Request request = new Request();
		String url = Variable.SERVER_SOFT_URL
				+ "/businesses/addDiscuss.jspx?businessesid=" + bussinessid
				+ "&content=" + content + "&pricescore=" + price
				+ "&milieuscore=" + milieu + "&servicescore=" + service + "&name=" + name;
		request.setHandler(handler);
		request.sendGetRequest(url);
	}
}
