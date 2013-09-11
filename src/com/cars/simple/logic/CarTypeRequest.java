/**
 * 
 */
package com.cars.simple.logic;

import android.os.Handler;

import com.cars.simple.fusion.Variable;
import com.cars.simple.mode.CarMessageInfo;
import com.cars.simple.service.request.Request;

/**
 * @author liuzixiang
 * 
 */
public class CarTypeRequest {

	/**
	 * 获取到车品牌
	 * 
	 * @param handler
	 *            回调函数
	 * @param currentPage
	 *            当前页
	 * @param pageSize
	 *            页面大小
	 */
	public void getCarType(Handler handler, int currentPage, int pageSize) {
		// http://localhost:8080/cars/getcarsbrandlist.jspx?pagesize=10&page=1
		Request request = new Request();
		String url = Variable.SERVER_SOFT_URL
				+ "/getcarsbrandlist.jspx?pagesize=" + pageSize + "&page="
				+ currentPage;
		request.setHandler(handler);
		request.sendGetRequest(url);
	}

	/**
	 * 获取车系列
	 * 
	 * @param handler
	 *            回调函数
	 * @param currentPage
	 *            当前页
	 * @param pageSize
	 *            页面大小
	 * @param id
	 *            id
	 * @param keyword
	 *            搜索关键字
	 */
	public void getCarXType(Handler handler, int currentPage, int pageSize,
			int id, String keyword) {
		// http://localhost:8080/cars/getcarsserieslist.jspx?carsbrandid=15&pagesize=5&page=1&keyword=M
		Request request = new Request();
		String url = Variable.SERVER_SOFT_URL
				+ "/getcarsserieslist.jspx?pagesize=" + pageSize + "&page="
				+ currentPage + "&carsbrandid=" + id + "&keyword=" + keyword;
		request.setHandler(handler);
		request.sendGetRequest(url);
	}

	/**
	 * 获取车类别
	 * 
	 * @param handler
	 *            回调函数
	 * @param currentPage
	 *            当前页
	 * @param pageSize
	 *            页面大小
	 * @param id
	 *            id
	 * @param keyword
	 *            搜索关键字
	 */
	public void getCarMType(Handler handler, int id, int pageSize,
			int currentPage, String keyword) {
		// http://localhost:8080/cars/getcarsmodellist.jspx?carsseriesid=1&pagesize=10&page=1&keyword=2
		Request request = new Request();
		String url = Variable.SERVER_SOFT_URL
				+ "/getcarsmodellist.jspx?pagesize=" + pageSize + "&page="
				+ currentPage + "&carsseriesid=" + id + "&keyword=" + keyword;
		request.setHandler(handler);
		request.sendGetRequest(url);
	}

	/**
	 * 保存车辆信息
	 * 
	 * @param handler
	 *            回调方法
	 * @param info
	 *            信息
	 */
	public void saveCarMessage(Handler handler, CarMessageInfo info) {
		// http://localhost:8080/cars/addmycars.jspx?modelid=1&seriesid=1&brandid=1&carnum=hz102&carname=nickname&enginenumber=11&framenumber=22&audittime=20121212&insurancetime=20120101
		Request request = new Request();
		String url = Variable.SERVER_SOFT_URL + "/addmycars.jspx?modelid="
				+ info.modelid + "&seriesid=" + info.seriesid + "&brandid="
				+ info.brandid + "&carnum=" + info.carnum + "&carname="
				+ info.carname + "&enginenumber=" + info.enginenumber
				+ "&framenumber=" + info.framenumber + "&audittime="
				+ info.audittime + "&insurancetime=" + info.insurancetime + "&othercar="+info.shengbao;
		if (info.userid != 0) {
			url = url + "&usercarid=" + info.userid;
		}
		request.setHandler(handler);
		request.sendGetRequest(url);
	}

	/**
	 * 获取车辆信息
	 * 
	 * @param handler
	 *            Handler
	 * @param id
	 *            id
	 */
	public void getCarMessage(Handler handler, int id) {
		// http://localhost:8080/cars/getmycarsinfo.jspx?id=10
		Request request = new Request();
		String url = Variable.SERVER_SOFT_URL + "/getmycarsinfo.jspx?id=" + id;
		request.setHandler(handler);
		request.sendGetRequest(url);
	}
}
