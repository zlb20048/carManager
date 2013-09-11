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
public class FeedBackRequest {

	/**
	 * 发送意见反馈
	 * 
	 * @param content
	 *            意见反馈内容
	 * @param handler
	 *            回调方法
	 */
	public void feedBack(String content, Handler handler) {
		// http://localhost:8080/cars/feedbak.jspx?content=hellogay!
		Request request = new Request();
		String url = Variable.SERVER_SOFT_URL + "/feedbak.jspx?content="
				+ content;
		request.setHandler(handler);
		request.sendGetRequest(url);
	}
}
