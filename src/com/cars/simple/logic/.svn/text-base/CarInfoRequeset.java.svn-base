/**
 * 
 */
package com.cars.simple.logic;

import android.os.Handler;

import com.cars.simple.fusion.FusionCode;
import com.cars.simple.fusion.Variable;
import com.cars.simple.service.request.Request;

/**
 * @author liuzixiang
 * 
 */
public class CarInfoRequeset {

	/**
	 * 获取到车辆信息并展示
	 * 
	 * @param handler
	 *            Handler
	 * @param page
	 *            当前页
	 */
	public void getCarInfo(Handler handler, int page) {
		// http://localhost:8080/cars/getmycarslist.jspx?pagesize=10&page=1
		Request request = new Request();
		String url = Variable.SERVER_SOFT_URL + "/getmycarslist.jspx?pagesize="
				+ FusionCode.pageSize + "&page=" + page;
		request.setHandler(handler);
		request.sendGetRequest(url);
	}

	/**
	 * 删除用户信息
	 * 
	 * @param handler
	 *            Handler
	 * @param id
	 *            id
	 */
	public void deleCarInfo(Handler handler, int id) {
		// localhost:8080/cars/delmycars.jspx?usercarid=1
		Request request = new Request();
		String url = Variable.SERVER_SOFT_URL + "/delmycars.jspx?usercarid="
				+ id;
		request.setHandler(handler);
		request.sendGetRequest(url);
	}
}
