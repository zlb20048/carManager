/**
 * 
 */
package com.cars.simple.activity;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.cars.simple.R;
import com.cars.simple.fusion.FusionCode;
import com.cars.simple.fusion.Variable;
import com.cars.simple.logic.CacheOpt;
import com.cars.simple.logic.CarInfoRequeset;
import com.cars.simple.logic.CarTypeRequest;
import com.cars.simple.mode.CarMessageInfo;
import com.cars.simple.util.ResponsePaseUtil;
import com.cars.simple.util.Util;

/**
 * @author liuzixiang
 * 
 */
public class CarInfoUpdateActivity extends BaseActivity implements
		OnItemSelectedListener, OnClickListener {

	/**
	 * 标记
	 */
	private final static String TAG = CarInfoUpdateActivity.class
			.getSimpleName();

	/**
	 * 车辆名称
	 */
	private EditText car_name_edit;

	/**
	 * 车辆类型
	 */
	private Spinner car_type_spinner;

	/**
	 * 车辆类型1
	 */
	private Spinner car_type_1_spinner;

	/**
	 * 车辆类型2
	 */
	private Spinner car_type_2_spinner;

	/**
	 * 车牌号
	 */
	private Spinner car_num_spinner;

	/**
	 * 车牌号1
	 */
	private Spinner car_num_1_spinner;

	/**
	 * 车牌号2
	 */
	private EditText car_num_2_edit;

	/**
	 * 汽车引擎
	 */
	private EditText car_engineer_edit;

	/**
	 * 车架号
	 */
	private EditText car_jia_edit;

	/**
	 * 登记日期
	 */
	private EditText car_load_time_edit;

	/**
	 * 保险日期
	 */
	private EditText car_save_time_edit;

	/**
	 * 登记日期layout
	 */
	private LinearLayout car_load_time_edit_layout;

	/**
	 * 保险日期Layout
	 */
	private LinearLayout car_save_time_edit_layout;

	/**
	 * 上报车型
	 */
	private EditText car_shenbao;
	/**
	 * 提交按钮
	 */
	private Button image_btn = null;

	/**
	 * 数据
	 */
	private Map<String, Object> dataMap = null;

	/**
	 * 当前页
	 */
	private int currentPage = 1;

	/**
	 * 分页大小
	 */
	private int pageSize = 10000;

	/**
	 * 类别类型
	 */
	private List<Map<String, Object>> typeList = null;

	/**
	 * 类别类型
	 */
	private List<Map<String, Object>> typeXList = null;

	/**
	 * 类别类型
	 */
	private List<Map<String, Object>> typeMList = null;

	/**
	 * 车品牌
	 */
	private ArrayAdapter<String> typeAdapter = null;

	/**
	 * 车系列
	 */
	private ArrayAdapter<String> xAdapter = null;

	/**
	 * 车型号
	 */
	private ArrayAdapter<String> mAdapter = null;

	/**
	 * 苏州
	 */
	private ArrayAdapter<String> cityAdapter = null;

	/**
	 * code
	 */
	private ArrayAdapter<String> cityCodeAdapter = null;

	/**
	 * 品牌
	 */
	private int brandid;

	/**
	 * 系列
	 */
	private int seriesid;

	/**
	 * 类别
	 */
	private int mid;

	/**
	 * 城市码
	 */
	private String car_city_num = null;

	/**
	 * 城市
	 */
	private String car_city = null;

	/**
	 * 车辆id
	 */
	private int userid = 0;

	/**
	 * 车牌
	 */
	private String carno = null;
	
	private String key1 = null;
	
	private String key2 = null;
	
	private String key3 = null;
	
    private String key4 = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cars.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.car_info_update_activity);

		Bundle bundle = getIntent().getExtras();

		if (null != bundle) {
			dataMap = (Map<String, Object>) bundle.getSerializable("data");
			userid = (Integer) dataMap.get("ID");
			brandid = (Integer) dataMap.get("BRANDID");
			seriesid = (Integer) dataMap.get("CARSSERIESID");
			mid = (Integer) dataMap.get("CARSMODELID");
			carno = (String) dataMap.get("CARNO");
		}

		showTop(getString(R.string.car_info_add_str), null);

		initview();

		initData();

		getCarType();
	}

	private Handler dbtypeHandler = new Handler() {

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
					CacheOpt db = new CacheOpt();
					db.update(key1,(String)object,CarInfoUpdateActivity.this);
				} else if (code == -3) {
					skipLogin();
				} 
				break;
			case FusionCode.NETWORK_ERROR:
			case FusionCode.NETWORK_TIMEOUT_ERROR:
				
				break;
			}
		}
	};
	
	/**
	 * 获取类别
	 */
	private Handler typeHandler = new Handler() {

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
					typeList = (List<Map<String, Object>>) map.get("objlist");
					initTypeAdapter();
					CacheOpt db = new CacheOpt();
					db.save(key1,(String)object,CarInfoUpdateActivity.this);
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

	
	
	private Handler dbtypeXHandler = new Handler() {

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
					CacheOpt db = new CacheOpt();
					db.update(key2,(String)object,CarInfoUpdateActivity.this);
				} else if (code == -3) {
					skipLogin();
				} 
				break;
			case FusionCode.NETWORK_ERROR:
			case FusionCode.NETWORK_TIMEOUT_ERROR:
				
				break;
			}
		}
	};
	/**
	 * 获取类别
	 */
	private Handler typeXHandler = new Handler() {

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
					typeXList = (List<Map<String, Object>>) map.get("objlist");
					initXAdapter();
					CacheOpt db = new CacheOpt();
					db.save(key2,(String)object,CarInfoUpdateActivity.this);
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

	
	private Handler dbtypeMHandler = new Handler() {

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
					CacheOpt db = new CacheOpt();
					db.update(key3,(String)object,CarInfoUpdateActivity.this);
				} else if (code == -3) {
					skipLogin();
				} 
				break;
			case FusionCode.NETWORK_ERROR:
			case FusionCode.NETWORK_TIMEOUT_ERROR:
				
				break;
			}
		}
	};
	/**
	 * 获取类别
	 */
	private Handler typeMHandler = new Handler() {

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
					typeMList = (List<Map<String, Object>>) map.get("objlist");
					initMAdapter();
					CacheOpt db = new CacheOpt();
					db.save(key3,(String)object,CarInfoUpdateActivity.this);
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

	private Handler dbinfoHandler = new Handler() {

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
					CacheOpt db = new CacheOpt();
					db.update(key4,(String)object,CarInfoUpdateActivity.this);
				} else if (code == -3) {
					skipLogin();
				} 
				break;
			case FusionCode.NETWORK_ERROR:
			case FusionCode.NETWORK_TIMEOUT_ERROR:
				
				break;
			}
		}
	};
	
	/**
	 * 保存数据
	 */
	private Handler saveHandler = new Handler() {

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
					//刷新 缓存数据库
					key4 =  Variable.Session.USER_ID+","+Variable.SERVER_SOFT_URL + "/getmycarslist.jspx?pagesize="
							+ FusionCode.pageSize + "&page=1";
					CarInfoRequeset request = new CarInfoRequeset();
					request.getCarInfo(dbinfoHandler, 1);
					
					showDialogfinish(getString(R.string.car_info_success));
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
						Intent intent = new Intent();
						Bundle bundle = new Bundle();
						bundle.putInt("id", userid);
						intent.putExtras(bundle);
						setResult(1, intent);
						finish();
					}
				});
		dialog.show();
	}

	/**
	 * 初始化品牌
	 */
	private void initTypeAdapter() {
		typeAdapter = new ArrayAdapter<String>(this, R.layout.myspinner_item);// 设置样式
		typeAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 设置下拉样式
		typeAdapter.add(getString(R.string.car_info_choose_type_str));
		int selectid = 0;
		int i = 0;
		for (Map<String, Object> map : typeList) {
			i++;
			if ((Integer) map.get("BRANDID") == brandid) {
				selectid = i;
			}
			typeAdapter.add((String) map.get("BRANDNAME"));
		}
		car_type_spinner.setAdapter(typeAdapter);
		car_type_spinner.setSelection(selectid);
		car_type_spinner.setOnItemSelectedListener(this);
	}

	/**
	 * 初始化系列
	 */
	private void initXAdapter() {
		xAdapter = new ArrayAdapter<String>(this, R.layout.myspinner_item);// 设置样式
		xAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 设置下拉样式
		xAdapter.add(getString(R.string.car_info_choose_x_str));
		int selectid = 0;
		int i = 0;
		for (Map<String, Object> map : typeXList) {
			i++;
			if ((Integer) map.get("SERIESID") == seriesid) {
				selectid = i;
			}
			xAdapter.add((String) map.get("SERIESNAME"));
		}
		car_type_1_spinner.setAdapter(xAdapter);
		car_type_1_spinner.setSelection(selectid);
		car_type_1_spinner.setOnItemSelectedListener(this);
	}

	/**
	 * 初始化系列
	 */
	private void initMAdapter() {
		mAdapter = new ArrayAdapter<String>(this, R.layout.myspinner_item);// 设置样式
		mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 设置下拉样式
		mAdapter.add(getString(R.string.car_info_choose_m_str));
		int selectid = 0;
		int i = 0;
		for (Map<String, Object> map : typeMList) {
			i++;
			if ((Integer) map.get("MODELID") == mid) {
				selectid = i;
			}
			mAdapter.add((String) map.get("MODELNAME"));
		}
		car_type_2_spinner.setAdapter(mAdapter);
		car_type_2_spinner.setSelection(selectid);
		car_type_2_spinner.setOnItemSelectedListener(this);
	}

	/**
	 * 初始化省的adapter
	 */
	private void initCarNumAdapter() {
		cityAdapter = new ArrayAdapter<String>(this, R.layout.myspinner_item,
				getResources().getStringArray(R.array.cityarray));// 设置样式
		cityAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 设置下拉样式
		car_num_spinner.setAdapter(cityAdapter);
		car_num_spinner.setOnItemSelectedListener(this);
	}

	/**
	 * 初始化省的adapter
	 */
	private void initCarNumCodeAdapter() {
		String[] carsnum = getResources().getStringArray(R.array.citynumarray);
		int selectid = 0;
		if (null != carno) {
			String tip = carno.substring(1, 2);
			int i = 0;
			for (String str : carsnum) {
				if (tip.equals(str)) {
					selectid = i;
				}
				i++;
			}
		}
		cityCodeAdapter = new ArrayAdapter<String>(this,
				R.layout.myspinner_item, carsnum);// 设置样式
		cityCodeAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 设置下拉样式
		car_num_1_spinner.setAdapter(cityCodeAdapter);
		car_num_1_spinner.setSelection(selectid);
		car_num_1_spinner.setOnItemSelectedListener(this);
	}

	/**
	 * 获取车类别
	 */
	public void getCarType() {
		key1 =  Variable.Session.USER_ID+","+Variable.SERVER_SOFT_URL
				+ "/getcarsbrandlist.jspx?pagesize=" + pageSize + "&page="
				+ currentPage;
		CacheOpt db = new CacheOpt();
		String str =db.getValue(key1,this);//读取数据库		
		if(str!=null) {
			Map<String, Object> map = ResponsePaseUtil.getInstance()
					.parseResponse((String) str);
			int code = (Integer) map.get("code");
			if (code > 0) {
				typeList = (List<Map<String, Object>>) map.get("objlist");
				initTypeAdapter();
			} else if (code == -3) {
				skipLogin();
			} else {
				showDialog((String) map.get("msg"));
			}
			CarTypeRequest request = new CarTypeRequest();
			request.getCarType(dbtypeHandler, currentPage, pageSize);
		}else{
			showNetDialog(R.string.tips_str, R.string.net_work_request_str);
			CarTypeRequest request = new CarTypeRequest();
			request.getCarType(typeHandler, currentPage, pageSize);
		}
	}

	/**
	 * 获取车系列
	 * 
	 * @param id
	 *            id
	 */
	public void getCarXType(int id) {
		key2 =  Variable.Session.USER_ID+","+Variable.SERVER_SOFT_URL
				+ "/getcarsserieslist.jspx?pagesize=" + pageSize + "&page="
				+ currentPage + "&carsbrandid=" + id + "&keyword=";
		CacheOpt db = new CacheOpt();
		String str =db.getValue(key2,this);//读取数据库		
		if(str!=null) {
			Map<String, Object> map = ResponsePaseUtil.getInstance()
					.parseResponse((String) str);
			int code = (Integer) map.get("code");
			if (code > 0) {
				typeXList = (List<Map<String, Object>>) map.get("objlist");
				initXAdapter();
			} else if (code == -3) {
				skipLogin();
			} else {
				showDialog((String) map.get("msg"));
			}
			CarTypeRequest request = new CarTypeRequest();
			request.getCarXType(dbtypeXHandler, currentPage, pageSize, id, "");
		}else{
			showNetDialog(R.string.tips_str, R.string.net_work_request_str);
			CarTypeRequest request = new CarTypeRequest();
			request.getCarXType(typeXHandler, currentPage, pageSize, id, "");
		}
	}

	/**
	 * 获取车类别
	 */
	public void getCarMType(int id) {
		key3 =  Variable.Session.USER_ID+","+Variable.SERVER_SOFT_URL
				+ "/getcarsmodellist.jspx?pagesize=" + pageSize + "&page="
				+ currentPage + "&carsseriesid=" + id + "&keyword=";
		CacheOpt db = new CacheOpt();
		String str =db.getValue(key3,this);//读取数据库		
		if(str!=null) {
			Map<String, Object> map = ResponsePaseUtil.getInstance()
					.parseResponse((String) str);
			int code = (Integer) map.get("code");
			if (code > 0) {
				typeMList = (List<Map<String, Object>>) map.get("objlist");
				initMAdapter();
			} else if (code == -3) {
				skipLogin();
			} else {
				showDialog((String) map.get("msg"));
			}
			CarTypeRequest request = new CarTypeRequest();
			request.getCarMType(dbtypeMHandler, id, pageSize, currentPage, "");
		}else{
			showNetDialog(R.string.tips_str, R.string.net_work_request_str);
			CarTypeRequest request = new CarTypeRequest();
			request.getCarMType(typeMHandler, id, pageSize, currentPage, "");
		}
	}

	/**
	 * 展示时间控件
	 * 
	 * @param time
	 *            显示时间
	 * @param editText
	 *            文本框
	 */
	public void showTimeView(final EditText editText) {
		int year2;
		int month3;
		int day4;

		String time = editText.getText().toString();
		if (null != time && !"".equals(time)) {
			year2 = Integer.parseInt(time.substring(0, 4));
			month3 = (Integer.parseInt(time.substring(5, 7))) - 1;
			day4 = Integer.parseInt(time.substring(8, 10));
		} else {
			Calendar canledar = Calendar.getInstance();
			year2 = 2010;
			month3 = canledar.get(Calendar.MONTH);
			day4 = canledar.get(Calendar.DAY_OF_MONTH);
		}

		final DatePickerDialog datePickerDialog = new DatePickerDialog(this,
				new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year1,
							int monthOfYear1, int dayOfMonth1) {
						String year = year1 + "";
						String month = (monthOfYear1 + 1) + "";
						String day = dayOfMonth1 + "";
						if (day.length() == 1) {
							day = "0" + day;
						}
						if (month.length() == 1) {
							month = "0" + month;
						}
						String timeName = year + "-" + month + "-" + day;
						if (editText.getId() == R.id.car_load_time_edit) {
							if (car_save_time_edit.getText().toString()
									.equals("")) {
								car_save_time_edit.setText(timeName);
							}
						}
						editText.setText(timeName);
					}
				}, year2, month3, day4);
		datePickerDialog.setTitle("请选择");
		datePickerDialog.show();
	}

	/**
	 * 初始化数据
	 * 
	 * @param id
	 *            id
	 */
	private void initData() {
		initCarNumAdapter();
		initCarNumCodeAdapter();
		if (null != dataMap) {
			car_name_edit.setText(String.valueOf(dataMap.get("NAME")));
			car_num_2_edit.setText(String.valueOf(carno.substring(2)));
			car_engineer_edit.setText(String.valueOf(dataMap
					.get("ENGINENUMBER")));
			car_jia_edit.setText(String.valueOf(dataMap.get("FRAMENUMBER")));
			car_load_time_edit.setText(Util.formatTime(String.valueOf(dataMap
					.get("AUDITTIME"))));
			car_save_time_edit.setText(Util.formatTime(String.valueOf(dataMap
					.get("INSURANCETIME"))));
		}
	}

	/**
	 * 初始化界面
	 */
	private void initview() {
		car_name_edit = (EditText) findViewById(R.id.car_name_edit);
		car_num_2_edit = (EditText) findViewById(R.id.car_num_2_edit);
		car_engineer_edit = (EditText) findViewById(R.id.car_engineer_edit);
		car_jia_edit = (EditText) findViewById(R.id.car_jia_edit);
		car_load_time_edit = (EditText) findViewById(R.id.car_load_time_edit);
		car_save_time_edit = (EditText) findViewById(R.id.car_save_time_edit);

		car_load_time_edit_layout = (LinearLayout) findViewById(R.id.car_load_time_edit_layout);
		car_save_time_edit_layout = (LinearLayout) findViewById(R.id.car_save_time_edit_layout);

		car_load_time_edit_layout.setOnClickListener(this);
		car_save_time_edit_layout.setOnClickListener(this);

		car_load_time_edit.setOnClickListener(this);
		car_save_time_edit.setOnClickListener(this);

		car_type_spinner = (Spinner) findViewById(R.id.car_type_spinner);
		car_type_1_spinner = (Spinner) findViewById(R.id.car_type_1_spinner);
		car_type_2_spinner = (Spinner) findViewById(R.id.car_type_2_spinner);

		car_num_spinner = (Spinner) findViewById(R.id.car_num_spinner);
		car_num_1_spinner = (Spinner) findViewById(R.id.car_num_1_spinner);

		car_shenbao = (EditText) findViewById(R.id.submit_edit);
		image_btn = (Button) findViewById(R.id.submit);
		image_btn.setOnClickListener(this);
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
		case R.id.car_type_spinner:
			if (position != 0) {
				brandid = (Integer) typeList.get(position - 1).get("BRANDID");
				getCarXType(brandid);
			}
			break;
		case R.id.car_type_1_spinner:
			if (position != 0) {
				seriesid = (Integer) typeXList.get(position - 1)
						.get("SERIESID");
				getCarMType(seriesid);
			}
			break;
		case R.id.car_type_2_spinner:
			if (position != 0) {
				mid = (Integer) typeMList.get(position - 1).get("MODELID");
			}
			break;
		case R.id.car_num_spinner:
			car_city = getResources().getStringArray(R.array.cityarray)[position];
			break;
		case R.id.car_num_1_spinner:
			car_city_num = getResources().getStringArray(R.array.citynumarray)[position];
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
		switch (v.getId()) {
		case R.id.car_save_time_edit:
		case R.id.car_save_time_edit_layout:
			showTimeView(car_save_time_edit);
			break;
		case R.id.car_load_time_edit:
		case R.id.car_load_time_edit_layout:
			showTimeView(car_load_time_edit);
			break;
		case R.id.submit:
			submitData();
			break;
		default:
			break;
		}
	}

	/**
	 * 提交数据
	 */
	private void submitData() {

		CarMessageInfo info = new CarMessageInfo();
		info.userid = userid;
		info.audittime = car_load_time_edit.getText().toString()
				.replace("-", "");
		info.brandid = brandid;
		info.carname = car_name_edit.getText().toString();
		info.carnum = car_city + car_city_num
				+ car_num_2_edit.getText().toString();
		info.enginenumber = car_engineer_edit.getText().toString();
		info.framenumber = car_jia_edit.getText().toString().replace("-", "");
		info.insurancetime = car_save_time_edit.getText().toString()
				.replace("-", "");
		info.modelid = mid;
		info.seriesid = seriesid;
		info.shengbao = car_shenbao.getText().toString();
		if (checkData(info)) {
			showNetDialog(R.string.tips_str, R.string.net_work_request_str);
			CarTypeRequest request = new CarTypeRequest();
			request.saveCarMessage(saveHandler, info);
		}
	}

	private boolean checkData(CarMessageInfo info) {
		if (null == info.carname || "".equals(info.carname)) {
			showDialog(getString(R.string.car_info_check_name_str));
			return false;
		}
		if (0 == info.brandid && 0 == info.modelid && 0 == info.seriesid) {
			showDialog(getString(R.string.car_info_check_x_str));
			return false;
		}
		if (info.carnum == null || info.carnum.equals("")
				|| info.carnum.length() < 5) {
			showDialog(getString(R.string.car_info_check_n_str));
			return false;
		}
		if (!info.enginenumber.equals("") && info.enginenumber.length() < 6) {
			showDialog(getString(R.string.car_info_check_eninerr_num));
			return false;
		}

		return true;
	}
}
