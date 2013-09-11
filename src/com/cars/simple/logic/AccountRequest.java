/**
 * 
 */
package com.cars.simple.logic;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import android.os.Handler;
import android.util.Log;

import com.cars.simple.fusion.FusionCode;
import com.cars.simple.fusion.Variable;
import com.cars.simple.mode.AccountInfo;
import com.cars.simple.service.request.Request;

/**
 * @author liuzixiang
 * 
 */
public class AccountRequest {

	/**
	 * 查询流水账记录
	 * 
	 * @param handler
	 *            Handler
	 * @param type
	 *            类型
	 * @param usercarid
	 *            id
	 * @param beginDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 */
	public void queryAccountWaterRecord(Handler handler, String type,
			String usercarid, String beginDate, String endDate, int page) {
		// http://localhost:8080/cars/message/getaccount.jspx?usercarid=22&type=1&starttime=20120721&endtime=20120724
		Request request = new Request();
		String url = Variable.SERVER_SOFT_URL
				+ "/message/getaccount.jspx?usercarid=" + usercarid + "&type="
				+ type + "&starttime=" + beginDate + "&endtime=" + endDate
				+ "&pagesize=" + FusionCode.pageSize + "&page=" + page;
		request.setHandler(handler);
		request.sendGetRequest(url);
	}

	/**
	 * 获取统计信息
	 * 
	 * @param handler
	 *            Handler
	 * @param type
	 *            类型
	 * @param userid
	 *            用户id
	 * @param starttime
	 *            开始时间
	 * @param endtime
	 *            结束时间
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 * @param selecttype
	 *            1,周 2,月
	 * @param cartype
	 *            1,自己 2,同车型
	 * @param random
	 *            1,不随机 2,随机其他车友
	 */
	public void queryAccountRecord(Handler handler, String type, String userid,
			String starttime, String endtime, int width, int height,
			String selecttype, String cartype, String random) {
		// http://localhost:8080/cars/message/getCosts.jspx?usercarid=22&type=-2&starttime=20120421&endtime=20120727&width=480&height=200&selecttype=2&cartype=1&random=1

		Request request = new Request();
		String url = Variable.SERVER_SOFT_URL
				+ "/message/getCosts.jspx?usercarid=" + userid + "&type="
				+ type + "&starttime=" + starttime + "&endtime=" + endtime
				+ "&width=" + width + "&height=" + height + "&selecttype="
				+ selecttype + "&cartype=" + cartype + "&random=" + random;
		request.setHandler(handler);
		request.sendGetRequest(url);
	}

	/**
	 * 获取统计信息
	 * 
	 * @param handler
	 *            Handler
	 * @param type
	 *            类型
	 * @param userid
	 *            用户id
	 * @param starttime
	 *            开始时间
	 * @param endtime
	 *            结束时间
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 * @param selecttype
	 *            1,周 2,月
	 * @param cartype
	 *            1,自己 2,同车型
	 * @param random
	 *            1,不随机 2,随机其他车友
	 */
	public void queryOilRecord(Handler handler, String userid,
			String starttime, String endtime, int width, int height) {
		// http://localhost:8080/cars/message/getOilCosts.jspx?usercarid=22&starttime=20120421&endtime=20120727&width=480&height=200

		Request request = new Request();
		String url = Variable.SERVER_SOFT_URL
				+ "/message/getOilCosts.jspx?usercarid=" + userid
				+ "&starttime=" + starttime + "&endtime=" + endtime + "&width="
				+ width + "&height=" + height;
		request.setHandler(handler);
		request.sendGetRequest(url);
	}

	/**
	 * 获取Account的图片
	 * 
	 * @param handler
	 *            Handler Handler
	 * @param name
	 *            请求的图片名称
	 */
	public void getAccountPic(Handler handler, String name) {
		Request request = new Request();
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyyMMddHHmmss");
		String dateString = formatter.format(currentTime);
		String url = Variable.SERVER_ACCOUNT_IMAGE_URL + name + "?"
				+ dateString;
		Log.v("url = ", "url = " + url);
		request.setHandler(handler);
		request.sendGetByteRequest(url);
	}

	/**
	 * 保存花费
	 * 
	 * @param handler
	 *            Handler
	 * @param info
	 *            保存的信息
	 */
	public void saveAccount(Handler handler, AccountInfo info) {
		// http://localhost:8080/cars/message/saveaccount.jspx?
		// oil=1&startmileage=15767&endmileage=15890&allmileage=200&laborcosts=50
		// &fittingcosts=230&unitprice=7.08&numbers=30&allprice=212.4&pricetype=1
		// &pricetime=20120723&address=China%20Nanjing&remark=testremak&usercarid=22
		// &accounttype=1&accountbase=1|100,2|128,3|136,4|12,5|89,6|67,7|90,8|123

		Request request = new Request();
		String url;
		try {
			url = Variable.SERVER_SOFT_URL
					+ "/message/saveaccount.jspx?oil=" + info.oil
					+ "&startmileage=" + info.startmileage + "&endmileage="
					+ info.endmileage + "&allmileage=" + info.allmileage
					+ "&laborcosts=" + info.laborcosts + "&fittingcosts="
					+ info.fittingcosts + "&unitprice=" + info.unitprice
					+ "&numbers=" + info.numbers + "&allprice=" + info.allprice
					+ "&pricetype=" + info.pricetype + "&pricetime="
					+ info.pricetime + "&address=" + URLEncoder.encode(info.address, "UTF-8") + "&remark="
					+ info.remark + "&usercarid=" + info.usercarid
					+ "&accounttype=" + info.accounttype + "&accountbase="
					+ info.accountbase;
			request.setHandler(handler);
			request.sendGetRequest(url);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 更新数据
	 * 
	 * @param handler
	 *            Handler
	 * @param info
	 *            info
	 */
	public void updateAccount(Handler handler, AccountInfo info) {
		// http://localhost:8080/cars/message/editaccount.jspx?
		// accountid=46&oil=11&startmileage=157671&endmileage=158901
		// &allmileage=2001&laborcosts=501&fittingcosts=2301&unitprice=7.081
		// &numbers=301&allprice=212.41&pricetype=2&pricetime=201207231
		// &address=China%20Nanjing1&remark=testremak1&usercarid=22&accounttype=11
		// &accountbase=21|11|1001,22|22|1281,23|33|1361,24|44|121

		Request request = new Request();
		String url = Variable.SERVER_SOFT_URL
				+ "/message/editaccount.jspx?accountid=" + info.accountid
				+ "&oil=" + info.oil + "&startmileage=" + info.startmileage
				+ "&endmileage=" + info.endmileage + "&allmileage="
				+ info.allmileage + "&laborcosts=" + info.laborcosts
				+ "&fittingcosts=" + info.fittingcosts + "&unitprice="
				+ info.unitprice + "&numbers=" + info.numbers + "&allprice="
				+ info.allprice + "&pricetype=" + info.pricetype
				+ "&pricetime=" + info.pricetime + "&address=" + info.address
				+ "&remark=" + info.remark + "&usercarid=" + info.usercarid
				+ "&accounttype=" + info.accounttype + "&accountbase="
				+ info.accountbase;
		request.setHandler(handler);
		// request.sendGetByteRequest(url);
		request.sendGetRequest(url);
	}

	/**
	 * 获取信息
	 * 
	 * @param handler
	 *            Handler
	 * @param type
	 *            类别 1，保养2，保险 3,规费
	 */
	public void getAccountBaseInfo(Handler handler, String type) {
		// http://localhost:8080/cars/message/getaccountbaseinfo.jspx?type=2
		Request request = new Request();
		String url = Variable.SERVER_SOFT_URL
				+ "/message/getaccountbaseinfo.jspx?type=" + type;
		request.setHandler(handler);
		request.sendGetRequest(url);
	}

	/**
	 * 删除流水帐
	 * 
	 * @param handler
	 *            Handler
	 * @param accountId
	 *            id
	 */
	public void deleteWaterAccount(Handler handler, String accountId) {
		// http://localhost:8080/cars/message/delaccount.jspx?accountid=37
		Request request = new Request();
		String url = Variable.SERVER_SOFT_URL
				+ "/message/delaccount.jspx?accountid=" + accountId;
		request.setHandler(handler);
		request.sendGetRequest(url);
	}

	/**
	 * 获取单条数据
	 * 
	 * @param handler
	 *            Handler
	 * @param accountId
	 *            id
	 */
	public void getSingleAccount(Handler handler, String accountId) {
		// http://localhost:8080/cars/message/getaccountinfo.jspx?accounted=8
		Request request = new Request();
		String url = Variable.SERVER_SOFT_URL
				+ "/message/getaccountinfo.jspx?accountid=" + accountId;
		request.setHandler(handler);
		request.sendGetRequest(url);
	}

	/**
	 * 获取单条数据
	 * 
	 * @param handler
	 *            Handler
	 * @param accountId
	 *            id
	 */
	public void getLastDistance(Handler handler) {
		
		Request request = new Request();
		String url = Variable.SERVER_SOFT_URL + "/getLast.jspx?usercarid="
				+ Variable.Session.USERCARID;
		request.setHandler(handler);
		request.sendGetRequest(url);
	}

}
