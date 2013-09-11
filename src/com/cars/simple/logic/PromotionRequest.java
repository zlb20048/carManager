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
public class PromotionRequest {

	/**
	 * 获取优惠活动列表
	 * 
	 * @param handler
	 *            回调方法
	 * @param parentcolumn
	 *            父类的id
	 * @param page
	 *            当前页码
	 */
	public void getPromotionList(Handler handler, int parentcolumn, int page) {
		// http://localhost:8080/cars/promotionsTypeList.jspx?parentcolumn=0&pagesize=10&page=1
		Request request = new Request();
		String url = Variable.SERVER_SOFT_URL
				+ "/promotionsTypeList.jspx?parentcolumn=" + parentcolumn
				+ "&pagesize=" + FusionCode.pageSize + "&page=" + page;
		request.setHandler(handler);
		request.sendGetRequest(url);
	}

	/**
	 * 获取促销优惠信息
	 * 
	 * @param handler
	 *            Handler
	 * @param keyword
	 *            搜索关键字
	 * @param brandid
	 *            id
	 * @param page
	 *            当前页
	 */
	public void getPromotion(Handler handler, String keyword, int brandid,
			int page) {
		// http://localhost:8080/cars/promotionsList.jspx?keyword=&brandid=1&pagesize=10&page=1
		Request request = new Request();
		String url = Variable.SERVER_SOFT_URL + "/promotionsList.jspx?keyword="
				+ keyword + "&pagesize=" + FusionCode.pageSize + "&page="
				+ page + "&brandid=" + brandid;
		request.setHandler(handler);
		request.sendGetRequest(url);
	}

}
