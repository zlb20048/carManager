package com.cars.simple.fusion;

import com.cars.simple.mode.ActivityRebackInfo;
import com.cars.simple.util.Stack;

/**
 * 系统可变常量
 * 
 * @author shifeng
 * 
 */
public final class Variable {

	public static int SYS_VERSION = 2;

	public static String SERVER_SOFT_URL = "http://cgj.jtonline.cn";
	/**
	 * 图片的Url
	 */
	public static String SERVER_IMAGE_URL = "http://cgj.jtonline.cn/carsmanager";
	
	/**
	 * 活动地址
	 */
	public static String SERVER_MEMBER_URL = "http://cgj.jtonline.cn/carsmanager";

	/**
	 * 统计请求的地址
	 */
	public static String SERVER_ACCOUNT_IMAGE_URL = "http://cgj.jtonline.cn/costsimages/";

	/**
	 * 当月流量url
	 */
	public static String SERVER_FLOW_URL = "";

	/**
	 * 已开服务url
	 */
	public static String SERVER_OPENSEVICE_URL = "";

	/**
	 * 套餐使用情况url
	 */
	public static String SERVER_PACKAGE_URL = "";

	/**
	 * 业务办理url
	 */
	public static String SERVER_OPERATE_URL = "";

	/**
	 * 话费充值url
	 */
	public static String SERVER_RECHARGE_URL = "";

	/**
	 * 热门精彩url
	 */
	public static String SERVER_RECOMMENDED_URL = "";

	/**
	 * 促销优惠url
	 */
	public static String SERVER_PROMOTIONS_URL = "";

	/**
	 * 个人信息url
	 */
	public static String SERVER_PERSONINFO_URL = "";

	/**
	 * jquery.js文件的url
	 */
	public static String JQUERY_JS_URL = "";

	/**
	 * 检测版本升级的url
	 */
	public static String SERVER_CHECKUPDATE_URL = "";

	/**
	 * 意见反馈url
	 */
	public static String SERVER_FEEDBACK_URL = "";

	/**
	 * 搜索页面的url
	 */
	public static String SERVER_SEARCH_URL = "";

	/**
	 * 清单页面url
	 */
	public static String SERVER_INVENTORY_URL = "";

	/**
	 * 修改密码url
	 */
	public static String SERVER_CHANGEPASS_URL = "";

	public static boolean net_proxy = false;

	public static String LOGIN_DATA = "car_login";

	public static String LOGIN_NAME = "login_name";

	public static String PWD_NAME = "login_psw";

	public static String DEVICE_ID = "";
	
	public final static class Size {
		public static int SCREEN_WIDTH = 0;
		public static int SCREEN_HEIGHT = 0;
		public static int CONTENT_HEIGHT = 0;
		public static int STATUS_HEIGHT = 0;
		public static int SCREEN_SIZE = 1;
		public static float density;
	}

	/**
	 * http 超时时间
	 */
	public static int HTTP_TIMEOUT = 1000 * 30;

	public static int SOFT = 1;

	public static String UA = "Mozilla/5.0 (Windows NT 5.1; rv:5.0) Gecko/20100101 Firefox/5.0";

	public final static class Session {

		public static boolean IS_DOWNLOADING = false;

		public static boolean IS_LOGIN = false;

		public static String USER_ID = null;

		public static String USERCARID = "-2";

		public static String CITY = null;
		
		public static String USERCARNAME = null;

		public static String PK_ID = null;

		public static String EMAIL = null;

		public static String USER_NAME = null;

		public static String MODE_ID = null;
		
		public static String USER_BRAND = "";

		public static String CITY_NAME = null;

		public static String USER_BALANCE = null;

		public static String USER_SCORE = null;

		public static String USER_MPOINT = null;
		
		/**
		 * 最后一次的距离
		 */
		public static String LAST = "";
		
		public static boolean isJfLogin = false;
		public static boolean isJfValid = false;

		public static String BRAND_STR = "";
		public static String IS_WEIHU = "";
		public static boolean IS_UPDATE = false;

		public final static Stack<ActivityRebackInfo> classStack = new Stack<ActivityRebackInfo>();

		/**
		 * 是否是强制更新
		 */
		public static boolean IS_FOURCE_UPDATE = false;

	}

}
