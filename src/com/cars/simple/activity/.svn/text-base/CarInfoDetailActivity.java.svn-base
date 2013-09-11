/**
 * 
 */
package com.cars.simple.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.cars.simple.R;
import com.cars.simple.fusion.FusionCode;
import com.cars.simple.fusion.Variable;
import com.cars.simple.logic.CacheOpt;
import com.cars.simple.logic.CarInfoRequeset;
import com.cars.simple.logic.CarTypeRequest;
import com.cars.simple.util.ResponsePaseUtil;
import com.cars.simple.util.Util;

/**
 * @author liuzixiang
 * 
 */
public class CarInfoDetailActivity extends BaseActivity implements
		OnClickListener {

	/**
	 * 标识
	 */
	private final static String TAG = CarInfoDetailActivity.class
			.getSimpleName();

	/**
	 * Listview
	 */
	private ListView listview = null;

	/**
	 * 数据
	 */
	private HashMap<String, Object> map = null;

	/**
	 * 数据
	 */
	private List<Map<String, Object>> listData = null;

	/**
	 * adapter
	 */
	private SimpleAdapter adapter = null;

	/**
	 * 数据
	 */
	private Bundle bundle = null;

	/**
	 * 编辑按钮
	 */
	private Button edit_btn = null;

	/**
	 * 删除按钮
	 */
	private Button delete_btn = null;

	private String key = null;
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cars.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		bundle = getIntent().getExtras();
		map = ((HashMap<String, Object>) bundle.getSerializable("data"));
		setContentView(R.layout.car_info_detail_activity);
		listview = (ListView) findViewById(R.id.list);
		showTitle();

		initView();
		initData(map);

	}

	/**
	 * 初始化布局文件
	 */
	private void initView() {
		// TODO Auto-generated method stub
		edit_btn = (Button) findViewById(R.id.edit_btn);
		delete_btn = (Button) findViewById(R.id.delete_btn);

		edit_btn.setOnClickListener(this);
		delete_btn.setOnClickListener(this);
	}

	/**
	 * 获取数据
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
				if (code > 0) {
					initData((Map<String, Object>) map.get("obj"));
					CacheOpt db = new CacheOpt();
					db.save(key,(String)object,CarInfoDetailActivity.this);//同步更新数据库
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

	};

	
	/**
	 * 更新数据库数据
	 */
	public Handler updatedbhandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int what = msg.what;
			Object object = msg.obj;
			switch (what) {
			case FusionCode.RETURN_JSONOBJECT:
				Map<String, Object> map = ResponsePaseUtil.getInstance()
						.parseResponse((String) object);
				int code = (Integer) map.get("code");
				if (code > 0) {
					CacheOpt db = new CacheOpt();
					db.update(key,(String)object,CarInfoDetailActivity.this);
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

	};
	/**
	 * 获取数据
	 */
	private void getCarMessage(int id) {
		showNetDialog(R.string.tips_str, R.string.net_work_request_str);
		
		key = Variable.Session.USER_ID+","+Variable.SERVER_SOFT_URL + "/getmycarsinfo.jspx?id=" + id;
		CacheOpt db = new CacheOpt();
		String str =db.getValue(key,this);//读取数据库		
		if(str!=null) {
			Map<String, Object> map = ResponsePaseUtil.getInstance()
					.parseResponse((String) str);
			int code = (Integer) map.get("code");
			if (code > 0) {
				initData((Map<String, Object>) map.get("obj"));
			} else if (code == -3) {
				skipLogin();
			} else {
				showDialog((String) map.get("msg"));
			}
			//异步请求更新数据库
			CarTypeRequest request = new CarTypeRequest();
			request.getCarMessage(updatedbhandler, id);
		}
		else{	
			//获取网络数据
			CarTypeRequest request = new CarTypeRequest();
			request.getCarMessage(handler, id);
		}
	}

	/**
	 * 初始化数据
	 */
	private void initData(Map<String, Object> map) {
		listData = new ArrayList<Map<String, Object>>();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("title", getString(R.string.car_info_name_str));
		m.put("value", map.get("NAME"));
		listData.add(m);
		m = new HashMap<String, Object>();
		m.put("title", getString(R.string.car_info_type_str));
		m.put("value", map.get("CARSMODELNAME"));
		listData.add(m);
		m = new HashMap<String, Object>();
		m.put("title", getString(R.string.car_info_num_str));
		m.put("value", map.get("CARNO"));
		listData.add(m);
		m = new HashMap<String, Object>();
		m.put("title", getString(R.string.car_info_engenerr_str));
		m.put("value", map.get("ENGINENUMBER"));
		listData.add(m);
		// m = new HashMap<String, Object>();
		// m.put("title", getString(R.string.car_info_top_str));
		// m.put("value", map.get("FRAMENUMBER"));
		// listData.add(m);
		m = new HashMap<String, Object>();
		m.put("title", getString(R.string.car_info_reg_str));
		m.put("value", Util.formatTime(String.valueOf(map.get("AUDITTIME"))));
		listData.add(m);
		m = new HashMap<String, Object>();
		m.put("title", getString(R.string.car_info_end_str));
		m.put("value",
				Util.formatTime(String.valueOf(map.get("INSURANCETIME"))));
		listData.add(m);
		adapter = new SimpleAdapter(this, listData,
				R.layout.car_info_detail_item,
				new String[] { "title", "value" }, new int[] { R.id.name,
						R.id.text });
		listview.setAdapter(adapter);
	}

	/**
	 * 显示头部
	 */
	private void showTitle() {
		Button backBtn = (Button) findViewById(R.id.backBtn);
		backBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		TextView top_title = (TextView) findViewById(R.id.top_title);
		top_title.setText(R.string.car_info_title_str);
		
		Button addBtn = (Button) findViewById(R.id.addBtn);
		addBtn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(CarInfoDetailActivity.this,
						CarInfoUpdateActivity.class);
				startActivity(intent);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onActivityResult(int, int,
	 * android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {
			if (null != data && data.getExtras() != null) {
				getCarMessage(data.getExtras().getInt("id"));
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.edit_btn:
			Intent intent = new Intent();
			intent.setClass(CarInfoDetailActivity.this,
					CarInfoUpdateActivity.class);
			intent.putExtras(bundle);
			startActivityForResult(intent, 1);
			break;
		case R.id.delete_btn:
			// 调用删除接口
			showDialog1(getString(R.string.delete_tip_str));
			break;
		}
	}

	/**
	 * 显示弹出Dialog提示框
	 * 
	 * @param message
	 *            提示信息
	 */
	protected void showDialog1(String message) {
		AlertDialog dialog = new AlertDialog.Builder(this).create();
		dialog.setTitle(getString(R.string.tip_str));
		dialog.setMessage(message);
		dialog.setButton(getString(R.string.sure_str),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						deleCarInfo(Integer.parseInt(map.get("ID").toString()));
					}
				});
		dialog.setButton2(getString(R.string.cancle_str),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				});
		dialog.show();
	}
	
	/**
	 * 删除车辆信息
	 * 
	 * @param id
	 *            id
	 */
	private void deleCarInfo(int id) {
		showNetDialog(R.string.tips_str, R.string.net_work_request_str);
		CarInfoRequeset request = new CarInfoRequeset();
		request.deleCarInfo(deleHandler, id);
	}
	
	/**
	 * 数据信息
	 */
	private Handler deleHandler = new Handler() {

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
					CarInfoActivity.isNeedRefreash = true;
					showDialogfinish(getString(R.string.car_info_dele_success_str));
				} else if (code == -3) {
					skipLogin();
				} else {
					showDialog((String) map.get("msg"));
				}
				adapter.notifyDataSetChanged();
				break;
			case FusionCode.NETWORK_ERROR:
			case FusionCode.NETWORK_TIMEOUT_ERROR:
				showDialog(getString(R.string.net_error));
				break;
			}
		}
	};
}
