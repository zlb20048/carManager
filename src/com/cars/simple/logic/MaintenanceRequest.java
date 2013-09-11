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
public class MaintenanceRequest {

	/**
	 * 获取列表
	 * 
	 * @param handler
	 *            Handler
	 * @param modelId
	 *            id
	 * @param page
	 *            当前页
	 */
	public void getMaintenance(Handler handler, String modelId, int page) {
		// http://221.130.48.67:18080/cars/getCarRepairList.jspx?modelId=10019&pagesize=100&page=1
		Request request = new Request();
		String url = Variable.SERVER_SOFT_URL
				+ "/getCarRepairList.jspx?carid=" + modelId + "&pagesize="
				+ FusionCode.pageSize + "&page=" + page;
		request.setHandler(handler);
		request.sendGetRequest(url);
	}

	/**
	 * 获取推荐商户列表
	 * 
	 * @param handler
	 *            Handler
	 * @param repairId
	 *            id
	 * @param page
	 *            当前页
	 */
	public void getSugess(Handler handler, String repairId, int page) {
		// http://221.130.48.67:18080/cars/getCarRepairBusList.jspx?repairId=1&pagesize=100&page=1
		Request request = new Request();
		String url = Variable.SERVER_SOFT_URL
				+ "/getCarRepairBusList.jspx?repairId=" + repairId
				+ "&pagesize=" + FusionCode.pageSize + "&page=" + page;
		request.setHandler(handler);
		request.sendGetRequest(url);
	}

	/**
	 * 获取特别商户
	 * 
	 * @param handler
	 *            Handler
	 * @param modleId
	 *            id
	 * @param page
	 *            当前页
	 */
	public void getSpe(Handler handler, String modleId, int page) {
		// http://221.130.48.67:18080/cars/getCarBusList.jspx?modelId=10019&pagesize=100&page=1
		Request request = new Request();
		String url = Variable.SERVER_SOFT_URL + "/getCarBusList.jspx?carid="
				+ modleId + "&pagesize=" + FusionCode.pageSize + "&page="
				+ page;
		request.setHandler(handler);
		request.sendGetRequest(url);
	}

}
