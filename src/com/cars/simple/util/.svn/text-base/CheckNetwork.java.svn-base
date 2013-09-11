package com.cars.simple.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckNetwork {

	/****************************************************************************************
	 * 函数名称：isNetworkAvailable
	 * 函数描述：检测网络是否可用
	 * 输入参数：@param mActivity
	 * 输入参数：@return
	 * 输出参数：
	 * 返回    值：boolean
	 * 备         注：
	 ****************************************************************************************/
	public static boolean isNetworkAvailable(Activity mActivity) {
		Context context = mActivity.getApplicationContext();
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
