/**
 * 
 */
package com.cars.simple.activity;

import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cars.simple.R;
import com.cars.simple.fusion.FusionCode;
import com.cars.simple.fusion.Variable;
import com.cars.simple.logic.MessaageRequeset;
import com.cars.simple.util.ResponsePaseUtil;

/**
 * @author liuzixiang
 * 
 */
public class HomeAcitivity extends BaseActivity implements OnClickListener {

	/**
	 * 当前的标识
	 */
	private final static String TAG = HomeAcitivity.class.getSimpleName();

	/**
	 * 主界面按钮
	 */
	private LinearLayout image_btn_1 = null;

	/**
	 * 主界面按钮
	 */
	private LinearLayout image_btn_2 = null;

	/**
	 * 主界面按钮
	 */
	private LinearLayout image_btn_3 = null;

	/**
	 * 主界面按钮
	 */
	private LinearLayout image_btn_4 = null;

	/**
	 * 主界面按钮
	 */
	private LinearLayout image_btn_5 = null;

	/**
	 * 主界面按钮
	 */
	private LinearLayout image_btn_6 = null;

	/**
	 * 主界面按钮
	 */
	private LinearLayout image_btn_7 = null;

	/**
	 * 主界面按钮
	 */
	private LinearLayout image_btn_8 = null;

	/**
	 * 主界面按钮
	 */
	private RelativeLayout image_btn_9 = null;

	/**
	 * 设置按钮
	 */
	private ImageButton set_btn = null;

	/**
	 * 车辆
	 */
	private Button car_btn = null;

	/**
	 * 是否是第一次进入改界面
	 */
	private static Boolean IS_FRIST = true;
	
	/**
	 * 未读信息数量
	 */
	private TextView number_id = null;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cars.activity.EcmcActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG, "oncreate...");
		setContentView(R.layout.home_acitivity);
		initview();

		if (IS_FRIST) {
			IS_FRIST = false;
			checkUpdate(false);
		}

	}

	/**
	 * 初始化控件
	 */
	private void initview() {
		image_btn_1 = (LinearLayout) findViewById(R.id.index_1);
		image_btn_2 = (LinearLayout) findViewById(R.id.index_2);
		image_btn_3 = (LinearLayout) findViewById(R.id.index_3);
		image_btn_4 = (LinearLayout) findViewById(R.id.index_4);
		image_btn_5 = (LinearLayout) findViewById(R.id.index_5);
		image_btn_6 = (LinearLayout) findViewById(R.id.index_6);
		image_btn_7 = (LinearLayout) findViewById(R.id.index_7);
		image_btn_8 = (LinearLayout) findViewById(R.id.index_8);
		image_btn_9 = (RelativeLayout) findViewById(R.id.index_9);

		number_id = (TextView)findViewById(R.id.num_id);
		
		set_btn = (ImageButton) findViewById(R.id.set_btn);

		car_btn = (Button) findViewById(R.id.car_btn);

		image_btn_1.setOnClickListener(this);
		image_btn_2.setOnClickListener(this);
		image_btn_3.setOnClickListener(this);
		image_btn_4.setOnClickListener(this);
		image_btn_5.setOnClickListener(this);
		image_btn_6.setOnClickListener(this);
		image_btn_7.setOnClickListener(this);
		image_btn_8.setOnClickListener(this);
		image_btn_9.setOnClickListener(this);

		set_btn.setOnClickListener(this);
		car_btn.setOnClickListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cars.simple.activity.BaseActivity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();
		if (Variable.Session.USERCARNAME != null
				&& !Variable.Session.USERCARNAME.equals("")) {
			car_btn.setVisibility(View.VISIBLE);
			// car_btn.setText(Variable.Session.USERCARNAME);
		}
		getReadType(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.index_1:
			Intent intent = new Intent();
			intent.setClass(this, SettingAcitivity.class);
			startActivity(intent);
			break;
		case R.id.car_btn:
			intent = new Intent();
			intent.setClass(this, CarInfoActivity.class);
			startActivity(intent);
			break;
		case R.id.index_2:
			intent = new Intent();
			intent.setClass(this, AccountTotalActivity.class);
			startActivity(intent);
			break;
		case R.id.index_3:
			intent = new Intent();
			intent.setClass(this, OilActivity.class);
			startActivity(intent);
			break;
		case R.id.index_4:
			// 跳转到违章查询界面
			intent = new Intent();
			intent.setClass(this, ViolationActivity.class);
			startActivity(intent);
			break;
		case R.id.index_5:
			intent = new Intent();
			intent.setClass(this, MagazineTabActivity.class);
			startActivity(intent);
			break;
		case R.id.index_6:
			intent = new Intent();
			intent.setClass(this, MaintenanceActivity.class);
			startActivity(intent);
			break;
		case R.id.index_7:
			intent = new Intent();
			intent.setClass(this, PromotionActivity.class);
			startActivity(intent);
			break;
		case R.id.index_8:
			gowebView();
			break;
		case R.id.index_9:
			intent = new Intent();
			Bundle bundle = new Bundle();
			// 查看全部
			bundle.putInt("type", 2);
			intent.putExtras(bundle);
			intent.setClass(this, MessageReadActivity.class);
			startActivity(intent);
			break;
		case R.id.set_btn:
			// 跳转到设置界面
			intent = new Intent();
			intent.setClass(this, AccountRecordActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	/**
	 * 跳转到webview界面
	 */
	private void gowebView() {
		Intent intent = new Intent();
		intent.setClass(this, MemberActivity.class);
		startActivity(intent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				exit();
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 获取未阅读的
	 * 
	 * @param type
	 *            0未读 1已读
	 */
	private void getReadType(int type) {
//		showNetDialog(R.string.tips_str, R.string.net_work_request_str);
		MessaageRequeset request = new MessaageRequeset();
		request.getMessage(handler, type, FusionCode.pageSize, page);
	}

	/**
	 * 回调数据
	 */
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

				totalPage = (Integer) map.get("allpage");
				if (code > 0) {
					int size = ((List<Map<String, Object>>) map.get("objlist"))
							.size();
					number_id.setVisibility(View.VISIBLE);
					number_id.setText(size + "");
				} else {
					number_id.setVisibility(View.GONE);
				}
				break;
			case FusionCode.NETWORK_ERROR:
			case FusionCode.NETWORK_TIMEOUT_ERROR:
				break;
			}
		}
	};

}
