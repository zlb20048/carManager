/**
 * 
 */
package com.cars.simple.activity;

import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.cars.simple.R;
import com.cars.simple.fusion.FusionCode;
import com.cars.simple.fusion.Variable;
import com.cars.simple.logic.DefaultCarRequest;
import com.cars.simple.util.ResponsePaseUtil;

/**
 * @author liuzixiang
 * 
 */
public class CarMessageActivity extends BaseActivity implements
		OnItemSelectedListener, OnClickListener {

	/**
	 * 标识
	 */
	private final static String TAG = CarMessageActivity.class.getSimpleName();

	/**
	 * 车辆信息Spinner
	 */
	private Spinner carSpinner = null;

	/**
	 * 城市Spinner
	 */
	private Spinner citySpinner = null;
	
	/**
	 * 城市名称
	 */
	private ArrayAdapter<String> citynameAdapter = null;

	/**
	 * 默认车辆
	 */
	private ArrayAdapter<String> carAdapter = null;

	/**
	 * 车型的列表
	 */
	private String[] carArray = null;

	/**
	 * 车辆提交的id
	 */
	private int[] carIdArray = null;

	/**
	 * 城市简码
	 */
	private String[] cityCodeArray = null;

	/**
	 * 城市名称
	 */
	private String[] citynameArray = null;

	/**
	 * 选中城市的下标
	 */
	private int cityPos = 0;

	/**
	 * 选中车辆的下标
	 */
	private int carPos = 0;

	/**
	 * 提交按钮
	 */
	private ImageButton submit = null;

	/**
	 * 回调方法
	 */
	private Handler defaultHandler = new Handler() {

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
					List<Map<String, Object>> list = (List<Map<String, Object>>) map
							.get("objlist");

					String city = (String) objMap.get("CITY");

					changeSelection(city);

					if (null != list && !list.isEmpty()) {
						initCarData(list);
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
	};

	/**
	 * 保存数据Handler
	 */
	private Handler saveDataHandler = new Handler() {

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
					Variable.Session.USERCARNAME = citynameArray[carPos];
					showDialog(getString(R.string.save_city_car_success_str));
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
	 * 改变默认选中的
	 */
	private void changeSelection(String city) {
		for (int i = 0; i < cityCodeArray.length; i++) {
			if (city.equals(cityCodeArray[i])) {
				citySpinner.setSelection(i);
			}
		}
	}

	private void initCarData(List<Map<String, Object>> list) {
		int size = list.size();
		carArray = new String[size];
		carIdArray = new int[size];
		for (int i = 0; i < size; i++) {
			Map<String, Object> m = list.get(i);
			String carno = (String) m.get("NAME");
			carArray[i] = carno;
			carIdArray[i] = (Integer) m.get("ID");
		}
		carAdapter = new ArrayAdapter<String>(this, R.layout.myspinner_item,
				carArray);
		carAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 设置下拉样式
		carSpinner.setAdapter(carAdapter);
		carSpinner.setOnItemSelectedListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cars.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.car_message_activity);
		showTop(getString(R.string.car_message_title_str), null);

		initView();

		initdata();

		citynameAdapter = new ArrayAdapter<String>(this,
				R.layout.myspinner_item, citynameArray);
		citynameAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 设置下拉样式

		citySpinner.setAdapter(citynameAdapter);
		citySpinner.setOnItemSelectedListener(this);

		getDefaultCar();

	}

	/**
	 * 初始化数据
	 */
	private void initdata() {
		// TODO Auto-generated method stub
		cityCodeArray = getResources().getStringArray(R.array.citycodearray);
		citynameArray = getResources().getStringArray(R.array.citynamearray);
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		carSpinner = (Spinner) findViewById(R.id.carSpinner);
		citySpinner = (Spinner) findViewById(R.id.citySpinner);

		submit = (ImageButton) findViewById(R.id.submit);
		submit.setOnClickListener(this);
	}

	/**
	 * 获取默认的用户的车辆
	 */
	public void getDefaultCar() {
		showNetDialog(R.string.tips_str, R.string.net_work_request_str);
		DefaultCarRequest request = new DefaultCarRequest();
		request.getDefaultCarMessage(defaultHandler);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemSelectedListener#onItemSelected(android
	 * .widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		switch (parent.getId()) {
		case R.id.citySpinner:
			cityPos = position;
			break;
		case R.id.carSpinner:
			carPos = position;
			break;
		default:
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemSelectedListener#onNothingSelected(android
	 * .widget.AdapterView)
	 */
	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		saveSetData();
	}

	/**
	 * 保存设置的城市和车辆信息
	 */
	private void saveSetData() {
		showNetDialog(R.string.tips_str, R.string.save_city_car_success_str);
		DefaultCarRequest request = new DefaultCarRequest();
		request.saveDefaultCarMessage(saveDataHandler, carIdArray[carPos] + "",
				cityCodeArray[cityPos]);
	}
}
