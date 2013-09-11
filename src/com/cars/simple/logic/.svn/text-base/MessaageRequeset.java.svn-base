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
public class MessaageRequeset {

	/**
	 * 获取消息
	 * 
	 * @param handler
	 *            Handler
	 * @param type
	 *            类型 0未读 1已读
	 * @param pageSize
	 *            分页大小
	 * @param currentPage
	 *            当前分页
	 * 
	 */
	public void getMessage(Handler handler, int type, int pageSize,
			int currentPage) {
		// http://localhost:8080/cars/message/getmessagelist.jspx?readtype=0&pagesize=10&page=1
		Request request = new Request();
		String url = Variable.SERVER_SOFT_URL
				+ "/message/getmessagelist.jspx?readtype=" + type
				+ "&pagesize=" + pageSize + "&page=" + currentPage;
		request.setHandler(handler);
		request.sendGetRequest(url);
	}

	/**
	 * 删除信息
	 * 
	 * @param handler
	 *            Handler
	 * @param id
	 *            要删除的信息Id
	 */
	public void deleMessage(Handler handler, String ids) {
		// http://localhost:8080/cars/message/deletemessage.jspx?id=31
		Request request = new Request();
		String url = Variable.SERVER_SOFT_URL
				+ "/message/deletemessage.jspx?id=" + ids;
		request.setHandler(handler);
		request.sendGetRequest(url);
	}

	/**
	 * 设置信息已读
	 * 
	 * @param handler
	 *            当前的Handler
	 * @param id
	 *            当前的信息ID
	 */
	public void setReadAlreay(Handler handler, int id) {
		// http://localhost:8080/cars/message/setReaded.jspx?id=31
		Request request = new Request();
		String url = Variable.SERVER_SOFT_URL + "/message/setReaded.jspx?id="
				+ id;
		request.setHandler(handler);
		request.sendGetRequest(url);
	}
}
