package com.cars.simple.activity;

import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cars.simple.R;
import com.cars.simple.fusion.FusionCode;
import com.cars.simple.fusion.Variable;
import com.cars.simple.logic.AccountRequest;
import com.cars.simple.logic.CacheOpt;
import com.cars.simple.logic.LoginRequest;
import com.cars.simple.logic.MainManager;
import com.cars.simple.logic.UpdateRequest;
import com.cars.simple.service.baseData.SysConfProcessor;
import com.cars.simple.util.ResponsePaseUtil;
import com.cars.simple.util.Util;

/************************************************************************************************
 * 文件名称: Main.java 文件描述: 欢迎页面 作 者： fushangbin 创建时间: 2011-11-10下午4:43:54 备 注:
 ************************************************************************************************/
public class WelcomeActivity extends BaseActivity implements Runnable {

	private final static String TAG = "WelcomeActivity";

	/**
	 * Dialog是否正在显示
	 */
	boolean isDialogShowing = false;

	private TextView carName = null;

	private Button showNumber = null;

	private RelativeLayout flash_layout = null;

	private TextView version_text = null;
	
	private Handler mainHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case FusionCode.HOME_PAGE:
				Intent intent = new Intent();
				intent.setClass(WelcomeActivity.this, HomeAcitivity.class);
				startActivity(intent);
				finish();
				break;
			case FusionCode.LOADING_FINISH:
				mainHandler.sendEmptyMessage(FusionCode.HOME_PAGE);
				break;
			case FusionCode.NETWORK_ERROR:
			case FusionCode.NETWORK_TIMEOUT_ERROR:
				mainHandler.sendEmptyMessage(FusionCode.HOME_PAGE);
				break;
			}
		}
	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_activity);
//		CacheOpt db = new CacheOpt();
//		long flag = db.save("key","val",this);
//		Log.i("==flag==", "flag=" + flag);
//		String str =db.getValue("1",this);
//		Log.i("==str==", "str=" + str);
		initview();
		
	}

	/**
	 * 初始化view
	 */
	private void initview() {
		carName = (TextView) findViewById(R.id.carName);
		showNumber = (Button) findViewById(R.id.showNumber);
		flash_layout = (RelativeLayout) findViewById(R.id.flash_layout);
		version_text = (TextView)findViewById(R.id.version_text);
		version_text.setText("version:" + MainManager.getAppVersionName(this));
	}

	@Override
	protected void onStart() {
		super.onStart();
		isDialogShowing = false;
		new Thread(this).start();
	}

	/****************************************************************************************
	 * 函数名称：initNetworkInfo 函数描述：初始化网络连接方式 输入参数： 输出参数： 返回 值：void 备 注：
	 ****************************************************************************************/
	private void initNetworkInfo() {
		ConnectivityManager mag = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		// 此处输出当前可用网络
		NetworkInfo info = mag.getActiveNetworkInfo();
		if (info == null || info.getTypeName() == null) {
			return;
		}
		if (info.getTypeName().equalsIgnoreCase("WIFI")) {
		} else {
			if (info.getExtraInfo().equals("uninet")) {
			}
			if (info.getExtraInfo().equals("uniwap")) {
			}
			if (info.getExtraInfo().equals("3gwap")) {
			}
			if (info.getExtraInfo().equals("3gnet")) {
			}
			if (info.getExtraInfo().equals("cmwap")) {
				// Variable.net_proxy = true;
			}
			if (info.getExtraInfo().equals("cmnet")) {
			}
			if (info.getExtraInfo().equals("ctwap")) {
			}
			if (info.getExtraInfo().equals("ctnet")) {
			}
		}
		Log.i("==Main==", "FusionField.net_proxy=" + Variable.net_proxy);
	}

	@Override
	public void run() {
		initNetworkInfo();
		MainManager.init(this);
		Variable.Size.STATUS_HEIGHT = dip2px(this, 25);
		Variable.Size.CONTENT_HEIGHT = Variable.Size.SCREEN_HEIGHT
				- Variable.Size.STATUS_HEIGHT;
		switch (Variable.Size.SCREEN_WIDTH) {
		case 240:
			Variable.Size.SCREEN_SIZE = FusionCode.SCREEN_L;
			break;
		case 320:
			Variable.Size.SCREEN_SIZE = FusionCode.SCREEN_M;
			break;
		case 480:
			if (Variable.Size.SCREEN_HEIGHT == 800) {
				Variable.Size.SCREEN_SIZE = FusionCode.SCREEN_H;
			} else {
				Variable.Size.SCREEN_SIZE = FusionCode.SCREEN_HH;
			}
			break;
		case 640:
			Variable.Size.SCREEN_SIZE = FusionCode.SCREEN_HHH;
			break;
		}
		sendVistor();
		login();
	}

	private Handler handler1 = new Handler();
	
	public void sendVistor()
	{
	    UpdateRequest request = new UpdateRequest();
	    request.visitorCount(handler1);
	}
	
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			closeNetDialog();
			int what = msg.what;
			Object object = msg.obj;
			switch (what) {
			case FusionCode.RETURN_JSONOBJECT:
				Map<String, Object> map = ResponsePaseUtil.getInstance()
						.parseResponse((String) object);
				int code = (Integer) map.get("code");
				if (code > 0) {
					Map<String, Object> objMap = (Map<String, Object>) map
							.get("obj");
					Variable.Session.USER_NAME = (String) objMap
							.get("USERNAME");
					Variable.Session.MODE_ID = String.valueOf(objMap
							.get("CARSMODELID"));
					Variable.Session.EMAIL = objMap.get("EMAIL") + "";
					Variable.Session.USER_ID = (String) objMap.get("USER_ID");
					Variable.Session.USERCARID = (String) objMap
							.get("USERCARID");
					Variable.Session.LAST = (String) objMap
                    .get("LAST");
					Variable.Session.CITY = (String) objMap.get("CITY");
					Log.v(TAG, "Variable.Session.CITY = "
							+ Variable.Session.CITY);
					Variable.Session.USERCARNAME = (String) objMap
							.get("USERCARNAME");
					Variable.Session.PK_ID = objMap.get("PK_ID") + "";
					Variable.Session.IS_LOGIN = true;
					getCost();
				} else {
					mainHandler.sendEmptyMessageDelayed(FusionCode.HOME_PAGE,
							3 * 1000);
				}
				break;
			case FusionCode.NETWORK_ERROR:
			case FusionCode.NETWORK_TIMEOUT_ERROR:
				// showDialog(getString(R.string.net_error));
				mainHandler.sendEmptyMessage(FusionCode.HOME_PAGE);
				break;
			}
		}
	};

	/**
	 * Handler
	 */
	private Handler oilhandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			mainHandler.sendEmptyMessageDelayed(FusionCode.HOME_PAGE, 3 * 1000);
			closeNetDialog();
			int what = msg.what;
			Object object = msg.obj;
			switch (what) {
			case FusionCode.RETURN_JSONOBJECT:
				Map<String, Object> map = ResponsePaseUtil.getInstance()
						.parseResponse((String) object);
				int code = (Integer) map.get("code");
				if (code > 0) {
					Map<String, Object> m1 = (Map<String, Object>) map
							.get("alltimesCosts");
					if (null != m1 && !m1.isEmpty()) {
						String oilavg = (String) m1.get("oilavg");
						flash_layout.setVisibility(View.VISIBLE);
						Object no = m1.get("no");
						int nu = 0;
						if (null != no) {
							nu = (Integer) no;
						}

						int allN = 0;
						Object all = m1.get("all");
						if (null != all) {
							allN = (Integer) all;
						}
						int current_num = allN - nu;
						carName.setText(Variable.Session.USERCARNAME);
						showNumber.setText(String.valueOf(current_num));
					}
				} else if (code == -3) {
					skipLogin();
				} else {
					showDialog((String) map.get("msg"));
				}
				break;
			case FusionCode.NETWORK_ERROR:
			case FusionCode.NETWORK_TIMEOUT_ERROR:
				showDialog(getString(R.string.net_error));
				break;
			}
		}

		private String getStart(String start) {
			if (start.equals("1")) {
				return "A";
			} else if (start.equals("2")) {
				return "B";
			} else if (start.equals("3")) {
				return "C";
			} else if (start.equals("4")) {
				return "D";
			}
			return "E";
		}

	};

	/**
	 * 查看图
	 */
	private void getCost() {
		AccountRequest request = new AccountRequest();
		request.queryOilRecord(oilhandler, Variable.Session.USERCARID,
				Util.getDayOfMonth(1), Util.getDayOfMonth(0), 400, 300);
	}

	/**
	 * 登录
	 */
	private void login() {
		SharedPreferences sp = getSharedPreferences(Variable.LOGIN_DATA,
				Context.MODE_PRIVATE);
		String account = sp.getString(Variable.LOGIN_NAME, "");
		String password = sp.getString(Variable.PWD_NAME, "");
		if (!"".equals(account) && !"".equals(password)) {
			LoginRequest request = new LoginRequest();
			request.login(account, password, handler);
		} else {
			mainHandler.sendEmptyMessageDelayed(FusionCode.HOME_PAGE, 3 * 1000);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}
}