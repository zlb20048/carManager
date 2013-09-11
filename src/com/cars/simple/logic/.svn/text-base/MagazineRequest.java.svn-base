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
public class MagazineRequest {

	/**
	 * 获取杂志列表
	 * 
	 * @param handler
	 *            Handler
	 * @param keyword
	 *            关键字
	 * @param page
	 *            当前页
	 * @param brandid
	 *            id
	 */
	public void getMagazine(Handler handler, String keyword, int page,
			int brandid) {
		// http://localhost:8080/cars/ColumnsList.jspx?keyword=&brandid=33&pagesize=10&page=1
		Request request = new Request();
		String url = Variable.SERVER_SOFT_URL + "/ColumnsList.jspx?keyword="
				+ keyword + "&pagesize=" + FusionCode.pageSize + "&page="
				+ page + "&brandid=" + brandid;
		request.setHandler(handler);
		request.sendGetRequest(url);
	}

}
