package com.cars.simple.activity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cars.simple.R;
import com.cars.simple.fusion.FusionCode;
import com.cars.simple.fusion.Variable;
import com.cars.simple.logic.CacheOpt;
import com.cars.simple.logic.Installer;
import com.cars.simple.logic.MainManager;
import com.cars.simple.logic.UpdateRequest;
import com.cars.simple.mode.ActivityRebackInfo;
import com.cars.simple.service.http.cookie.CookieManager;
import com.cars.simple.service.request.Request;
import com.cars.simple.util.ProgressDialogUtil;
import com.cars.simple.util.ResponsePaseUtil;

/************************************************************************************************
 * 文件名称: EcmcActivity.java 文件描述: Activity 的基类
 ************************************************************************************************/
public abstract class BaseActivity extends Activity {

	private final String TAG = "==EcmcActivity==";

	/**
	 * 展示正在进行网络请求
	 */
	private ProgressDialogUtil pdu = null;

	/**
	 * 页面内http请求的haspmap
	 */
	protected HashMap<String, Request> requestList = new HashMap<String, Request>();

	/**
	 * 当不需要回调的时候调用
	 */
	protected Handler defaultHandler = new Handler();

	/**
	 * sroll状态
	 */
	protected int scrollState;

	/**
	 * 数量
	 */
	protected int count = 0;

	/**
	 * 最后的Item
	 */
	protected int lastItem;

	/**
	 * 可见的count
	 */
	protected int visibleItemCount;

	/**
	 * 点击的按钮
	 */
	protected Button footerButton;

	/**
	 * 显示正在读取
	 */
	protected LinearLayout footerProgressBarLayout;

	/**
	 * 底部界面
	 */
	protected View view;

	/**
	 * 当前页
	 */
	protected int page = 1;

	/**
	 * 总页数
	 */
	protected int totalPage = 0;

	/**
	 * 是否已经跳转
	 */
	protected boolean isSkip = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		// 隐藏虚拟键盘
		getWindow()
				.setSoftInputMode(
						WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
								| WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED);
		Log.i(TAG, "onCreate " + this.getLocalClassName());
		IntentFilter filter = new IntentFilter();
		filter.addAction(FusionCode.ACTION_FINISH_SELF);
		registerReceiver(mFinishReceiver, filter);
	}

	@Override
	protected void onDestroy() {
		Log.i(TAG, "onDestroy " + this.getLocalClassName());
		super.onDestroy();
		unregisterReceiver(mFinishReceiver);
	}

	@Override
	protected void onStart() {
		Log.i(TAG, "onstart " + this.getLocalClassName());
		super.onStart();
	}

	@Override
	public void finish() {
		Log.i(TAG, "finish " + this.getLocalClassName());
		super.finish();
	}

	/**
	 * 头部操作，点击操作的时候，返回前一个页面
	 * 
	 * @param titleStr
	 *            顶部的名称
	 * @param backStr
	 *            按钮的名称
	 */
	protected void showTop(String titleStr, String backStr) {
		Button backBtn = (Button) findViewById(R.id.backBtn);
		TextView topTips = (TextView) findViewById(R.id.top_title);
		if (null == titleStr) {
			titleStr = getString(R.string.app_name);
		}
		topTips.setText(titleStr);

		if (backStr == null) {
			backStr = getString(R.string.back_btn_str);
		}
		backBtn.setText(backStr);
		backBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	/*
	 * add()方法的四个参数，依次是： 1、组别，如果不分组的话就写Menu.NONE,
	 * 2、Id，这个很重要，Android根据这个Id来确定不同的菜单 3、顺序，那个菜单现在在前面由这个参数的大小决定 4、文本，菜单的显示文本
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, Menu.FIRST + 1, 1,
				this.getString(R.string.cars_menu_1_str));
		menu.add(Menu.NONE, Menu.FIRST + 2, 2,
				this.getString(R.string.cars_menu_2_str));
		menu.add(Menu.NONE, Menu.FIRST + 3, 3,
				this.getString(R.string.cars_menu_3_str));
		menu.add(Menu.NONE, Menu.FIRST + 4, 4,
				this.getString(R.string.cars_menu_4_str));
		menu.add(Menu.NONE, Menu.FIRST + 5, 5,
				this.getString(R.string.cars_menu_5_str));
		menu.add(Menu.NONE, Menu.FIRST + 6, 6,
				this.getString(R.string.cars_menu_6_str));
		menu.add(Menu.NONE, Menu.FIRST + 7, 7,
				this.getString(R.string.cars_menu_7_str));
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case Menu.FIRST + 1:
			Intent intent = new Intent();
			intent.setClass(this, LoginActivity.class);
			startActivity(intent);
			break;
		case Menu.FIRST + 2:
			intent = new Intent();
			intent.setClass(this, SettingAcitivity.class);
			startActivity(intent);
			break;
		case Menu.FIRST + 3:
			checkUpdate(true);
			break;
		case Menu.FIRST + 4:
			intent = new Intent();
			intent.setClass(this, FeedBackActivity.class);
			startActivity(intent);
			break;
		case Menu.FIRST + 5:
			// 好友分享功能
			intent = new Intent(Intent.ACTION_SEND);
			intent.setType("text/plain");
			intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
			intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_str));
			startActivity(Intent.createChooser(intent, getTitle()));
			break;
		case Menu.FIRST + 6:
			// 退出
			exitApp();
			break;
		case Menu.FIRST + 7:
			intent = new Intent();
			intent.setClass(this, AboutActivity.class);
			startActivity(intent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 退出程序
	 */
	protected void exitApp() {
		AlertDialog.Builder builder = getBuilder();
		if (builder == null) {
			return;
		}
		builder.setMessage(R.string.sure_exit);
		builder.setTitle(R.string.tip_str);
		builder.setPositiveButton(R.string.sure_str,
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						exit();
					}
				});
		builder.setNegativeButton(R.string.cancle_str,
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}

	/**
	 * 回调方法
	 */
	private Handler handler = new Handler() {

		@SuppressWarnings("unchecked")
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
					String updateMsg = (String) objMap.get("androidSeason");
					String updateUrl = (String) objMap.get("androidurl");
					String version = (String) objMap.get("androidversion");
					if (Integer.parseInt(version) > MainManager
							.getAppVersion(BaseActivity.this)) {
						showUpdateDialog(updateMsg, updateUrl);
					}
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
	};

	/**
	 * 显示弹出Dialog提示框
	 * 
	 * @param message
	 *            提示信息
	 */
	protected void showUpdateDialog(String message, final String url) {
		AlertDialog dialog = new AlertDialog.Builder(this).create();
		dialog.setTitle(getString(R.string.tip_str));
		dialog.setMessage(message);
		dialog.setButton(getString(R.string.sure_str),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 调用下载
						new Installer(BaseActivity.this).install(url);
					}
				});
		dialog.setButton2(getString(R.string.cancle_str),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		dialog.show();
	}

	/**
	 * 检测版本更新
	 */
	protected void checkUpdate(boolean isShowNetDialog) {
		if (isShowNetDialog) {
			showNetDialog(R.string.tips_str, R.string.check_update_str);
		}
		UpdateRequest request = new UpdateRequest();
		request.checkUpdate(handler, 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onOptionsMenuClosed(android.view.Menu)
	 */
	@Override
	public void onOptionsMenuClosed(Menu menu) {
		super.onOptionsMenuClosed(menu);
	}

	/**
	 * 显示弹出Dialog提示框
	 * 
	 * @param message
	 *            提示信息
	 */
	protected void showDialog(String message) {
	    if (!this.isFinishing())
	    {
	        AlertDialog dialog = new AlertDialog.Builder(this).create();
	        dialog.setTitle(getString(R.string.tip_str));
	        dialog.setMessage(message);
	        dialog.setButton(getString(R.string.sure_str),
	            new DialogInterface.OnClickListener() {
	            
	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	            }
	        });
	        dialog.show();
	    }
	}


	/**
	 * 显示弹出Dialog提示框
	 * 
	 * @param message
	 *            提示信息
	 */
	protected void showDialogfinish(String message) {
		AlertDialog dialog = new AlertDialog.Builder(this).create();
		dialog.setTitle(getString(R.string.tip_str));
		dialog.setMessage(message);
		dialog.setButton(getString(R.string.sure_str),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				});
		dialog.show();
	}

	/**
	 * 展示正在请求网络
	 */
	protected void showNetDialog(int title, int res) {
	    closeNetDialog();
	    pdu = new ProgressDialogUtil(this, title, res, true);
		pdu.showProgress();
	}

	/**
	 * 关闭网络请求弹出框
	 */
	protected void closeNetDialog() {
		if (null != pdu) {
			pdu.closeProgress();
			pdu = null;
		}
	}

	/**
	 * 退出程序的广播器
	 */
	private BroadcastReceiver mFinishReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (FusionCode.ACTION_FINISH_SELF.equals(intent.getAction())) {
				Log.e(TAG, "I am " + getLocalClassName()
						+ ",now finishing myself...");
				finish();
				Thread thread = new Thread() {
					public void run() {
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
						}
						System.exit(0);
					}
				};
				thread.start();
			}
		}
	};

	/****************************************************************************************
	 * 函数名称：tip 函数描述：弹出系统的tip 消息 输入参数：@param failMessage 输出参数： 返回 值：void 备 注：
	 ****************************************************************************************/
	protected void tip(String failMessage) {
		Toast.makeText(getApplicationContext(), "" + failMessage,
				Toast.LENGTH_LONG).show();
	}

	/****************************************************************************************
	 * 函数名称：tip 函数描述：弹出系统的tip 消息 输入参数：@param res 输出参数： 返回 值：void 备 注：
	 ****************************************************************************************/
	protected void tip(int res) {
		Toast.makeText(getApplicationContext(), res, Toast.LENGTH_LONG).show();
	}

	/****************************************************************************************
	 * 函数名称：dip2px 函数描述：dip 换算为px像素值 输入参数：@param context 输入参数：@param dipValue
	 * 输入参数：@return 输出参数： 返回 值：int 备 注：
	 ****************************************************************************************/
	protected int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/****************************************************************************************
	 * 函数名称：px2dip 函数描述：px像素值换算为dip值 输入参数：@param context 输入参数：@param pxValue
	 * 输入参数：@return 输出参数： 返回 值：int 备 注：
	 ****************************************************************************************/
	protected int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	protected AlertDialog.Builder getBuilder() {
		return new AlertDialog.Builder(this);
	}

	/**
	 * 退出程序的入口
	 */
	public void exit() {
		// 重置标记位，并清空Cookie
		resetSessionData();
		CookieManager.getInstance().removeAllCookie();
		int apiLevel = -1;
		try {
			apiLevel = Integer.parseInt(android.os.Build.VERSION.SDK);
		} catch (NumberFormatException e) {
		}
		// android2.2以下版本
		if (apiLevel > 2 && apiLevel < 8) {
			final String name = getPackageName();
			ActivityManager activityManager = (ActivityManager) this
					.getSystemService(Context.ACTIVITY_SERVICE);
			activityManager.restartPackage(name);
		} else {
			getApplicationContext().sendBroadcast(
					new Intent(FusionCode.ACTION_FINISH_SELF));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// android.os.Process.killProcess(android.os.Process.myPid());
		}
	}

	/**
	 * 启动app 应用的入口
	 * 
	 * @param packName
	 */
	public void startAPP(String packName) {
		try {
			PackageManager packageManager = this.getPackageManager();
			Intent intent = new Intent();
			intent = packageManager.getLaunchIntentForPackage(packName);
			startActivity(intent);
		} catch (Exception e) {
			Log.e("startAPP", "e=" + e.getMessage());
		}
	}

	/****************************************************************************************
	 * 函数名称：addRequest 函数描述：绑定页面的http请求对象，方便取消操作 输入参数：@param time 输入参数：@param
	 * request 输出参数： 返回 值：void 备 注：
	 ****************************************************************************************/
	public synchronized void addRequest(int time, Request request) {
		requestList.put("" + time, request);
	}

	/****************************************************************************************
	 * 函数名称：removeRequest 函数描述：接触页面的http请求 输入参数：@param time 输出参数： 返回 值：void 备 注：
	 ****************************************************************************************/
	public synchronized void removeRequest(int time) {
		requestList.remove("" + time);
	}

	/****************************************************************************************
	 * 函数名称：cancel 函数描述：当用户取消操作后页面cancle 操作，请不要删除 输入参数： 输出参数： 返回 值：void 备 注：
	 ****************************************************************************************/
	protected void cancel() {

	}

	/**
	 * 跳转到Login界面
	 * 
	 * @param backIntent
	 */
	protected void skipLogin() {
		if (!isSkip) {
			isSkip = true;
			Intent intent = new Intent();
			intent.setClass(this, LoginActivity.class);
			ActivityRebackInfo rebackInfo = new ActivityRebackInfo();
			Activity parentClass = getParent();
			if (parentClass != null) {
				rebackInfo.setBackClass(parentClass.getClass());
			} else {
				rebackInfo.setBackClass(getClass());
			}
			rebackInfo.setBackClassBundle(getIntent().getExtras());
			Variable.Session.classStack.push(rebackInfo);
			startActivity(intent);
			finish();
		}
	}

	/**
	 * 重置保存的数据
	 */
	protected void resetSessionData() {
		CookieManager.getInstance().clearCookie();
		Variable.Session.USER_NAME = "";
		Variable.Session.EMAIL = "";
		Variable.Session.USER_ID = "";
		Variable.Session.PK_ID = "";
		Variable.Session.IS_LOGIN = false;
	}

	/**
	 * 格式话数字
	 * 
	 * @param l
	 *            传入的数字
	 * @return
	 */
	protected BigDecimal formartNum(Double l) {
		return new BigDecimal(l).setScale(1, BigDecimal.ROUND_HALF_UP);
	}

	
	
	
}
