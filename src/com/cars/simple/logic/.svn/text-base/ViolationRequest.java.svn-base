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
public class ViolationRequest {

	/**
	 * 获取图片验证码
	 * 
	 * @param handler
	 *            Handler
	 */
	public void getVidataImage(Handler handler) {
		// http://www.njjg.gov.cn/bgcx/www/njjg/zxcx/123.jsp?0.35878042992156634
		Request request = new Request();
		String url = "http://www.njjg.gov.cn/bgcx/www/njjg/zxcx/123.jsp";
		request.setHandler(handler);
		request.sendGetByteRequest(url);
	}

	/**
	 * 获取车信息
	 * 
	 * @param handler
	 *            Handler
	 * @param fdjh
	 *            发动机号
	 * @param hphm
	 *            HZ032
	 */
	public void queryData(Handler handler, String fdjh,
			String hphm) {
		// http://www.njjg.gov.cn/bgcx/www/njjg/zxcx/bgcx2011.jsp
		// yzmflag=1&chexing=02&begindate=2010-7-18&keywords=%E8%8B%8FA&keywords2=HZ102&enddate=2012-7-18&fdjh=D37533&rand=2105&newsearch=1&imageButton.x=34&imageButton.y=8

		Request request = new Request();
		String url = Variable.SERVER_SOFT_URL + "/mycarsviolateinfo.jspx?hphm="
				+ hphm + "&fdjh=" + fdjh;
		request.setHandler(handler);
		request.sendGetRequest(url);
	}
}
