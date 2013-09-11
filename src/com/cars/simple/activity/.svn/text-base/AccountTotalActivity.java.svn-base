/**
 * 
 */
package com.cars.simple.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.cars.simple.R;
import com.cars.simple.fusion.FusionCode;
import com.cars.simple.fusion.Variable;
import com.cars.simple.logic.AccountRequest;
import com.cars.simple.logic.CacheOpt;
import com.cars.simple.logic.DefaultCarRequest;
import com.cars.simple.util.ResponsePaseUtil;
import com.cars.simple.util.Util;
import com.cars.simple.widget.DateSelectDialog;

/**
 * @author liuzixiang
 * 
 */
public class AccountTotalActivity extends BaseActivity implements
		OnItemSelectedListener, OnClickListener, OnCheckedChangeListener {

	/**
	 * TAG
	 */
	private final static String TAG = AccountTotalActivity.class
			.getSimpleName();

	/**
	 * ImageView
	 */
	private ImageView imageview = null;

	/**
	 * 饼状图
	 */
	private ImageView image_radiu = null;

	/**
	 * 车辆
	 */
	private Spinner spinner1 = null;

	/**
	 * 类型
	 */
	private Spinner spinner2 = null;

	/**
	 * 开始时间
	 */
	private EditText spinner3 = null;

	/**
	 * 结束时间
	 */
	private EditText spinner4 = null;

	/**
	 * 数据
	 */
	private List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();

	/**
	 * 车型的列表
	 */
	private String[] carArray = null;

	/**
	 * 车型Id的列表
	 */
	private String[] carIdArray = null;

	/**
	 * 类型
	 */
	private String[] typeArray = null;

	/**
	 * 类型id
	 */
	private String[] typeIdArray = null;

	/**
	 * adapter
	 */
	private ArrayAdapter<String> adapter = null;

	/**
	 * 柱状图
	 */
	private CheckBox bar_btn = null;

	/**
	 * 线性图
	 */
	private CheckBox line_btn = null;

	/**
	 * 按周
	 */
	private RadioGroup rg = null;

	/**
	 * 相同车辆消费
	 */
	private Button same_car_1 = null;

	/**
	 * 不同车辆消费
	 */
	private Button other_car_1 = null;

	/**
	 * 查询
	 */
	private Button submit = null;

	/**
	 * 花销类别
	 */
	private Button btn_1;

	/**
	 * 时间
	 */
	private Button btn_2;

	/**
	 * 同车型费用
	 */
	private Button btn_3;

	/**
	 * 其他车型费用
	 */
	private Button btn_4;

	/**
	 * 更多
	 */
	private Button btn_5;

	/**
	 * 类别
	 */
	private ArrayAdapter<String> typeAdapter = null;

	/**
	 * 需要请求的图片的名车
	 */
	private String pic_name = "_barchart.jpg";

	/**
	 * 类型
	 */
	private String type = "-2";

	/**
	 * 车辆的id
	 */
	private String usercarid = "";

	/**
	 * 开始时间
	 */
	private String starttime = "-2";

	/**
	 * 结束时间
	 */
	private String endtime = "-2";

	/**
	 * 1、周 2、月
	 */
	private String selecttype = "1";

	/**
	 * 1,自己 2,同车型
	 */
	private String cartype = "1";

	/**
	 * 1,不随机 2,随机其他车友
	 */
	private String random = "";

	private String key1 = null;
	private String key2 = null;
	private String key3 = null;

	/**
	 * accountHandler
	 */
	private Handler accountHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			closeNetDialog();
			int what = msg.what;
			Object object = msg.obj;
			switch (what) {
			case FusionCode.RETURN_GETBYTES:
				byte[] data = (byte[]) object;
				Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,
						data.length);
				Drawable d = new BitmapDrawable(bitmap);
				imageview.setBackgroundDrawable(d);
				break;
			default:
				break;
			}
		}
	};

	/**
	 * accountHandler
	 */
	private Handler imageHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			closeNetDialog();
			int what = msg.what;
			Object object = msg.obj;
			switch (what) {
			case FusionCode.RETURN_GETBYTES:
				byte[] data = (byte[]) object;
				Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,
						data.length);
				Drawable d = new BitmapDrawable(bitmap);
				image_radiu.setBackgroundDrawable(d);
				break;
			default:
				break;
			}
		}
	};

	/**
	 * Handler
	 */
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			closeNetDialog();
			int what = msg.what;
			Object object = msg.obj;
			switch (what) {
			case FusionCode.RETURN_JSONOBJECT:
				Map<String, Object> map = ResponsePaseUtil.getInstance()
						.parseResponse((String) object);
				int code = (Integer) map.get("code");
				String filename = (String) map.get("msg");
				if (code > 0) {
					getPic(filename);
					getPic2(filename);
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
					db.update(key1, (String) object, AccountTotalActivity.this);
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
					List<Map<String, Object>> list = (List<Map<String, Object>>) map
							.get("objlist");

					if (null != list && !list.isEmpty()) {
						initCarData(list);
						CacheOpt db = new CacheOpt();
						db.save(key1, (String) object,
								AccountTotalActivity.this);
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
	 * 初始化车辆名称
	 * 
	 * @param list
	 */
	private void initCarData(List<Map<String, Object>> list) {
		listData = list;
		int size = list.size();
		carArray = new String[size];
		carIdArray = new String[size];
		for (int i = 0; i < size; i++) {
			Map<String, Object> m = list.get(i);
			String name = (String) m.get("NAME");
			carArray[i] = name;
			carIdArray[i] = String.valueOf(m.get("ID"));
		}
		usercarid = carIdArray[0];
		adapter = new ArrayAdapter<String>(this, R.layout.myspinner_item,
				carArray);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 设置下拉样式
		spinner1.setAdapter(adapter);
		spinner1.setOnItemSelectedListener(this);
		getCost();
	}

	/**
	 * 类型
	 */
	private void initTypeAdapter() {
		typeArray = getResources().getStringArray(R.array.typearray);
		typeIdArray = getResources().getStringArray(R.array.typeidarray);
		typeAdapter = new ArrayAdapter<String>(this, R.layout.myspinner_item,
				typeArray);
		typeAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 设置下拉样式
		spinner2.setAdapter(typeAdapter);
		spinner2.setOnItemSelectedListener(this);
	}

	/**
	 * 获取默认的用户的车辆
	 */
	public void getDefaultCar() {
		key1 = Variable.Session.USER_ID + "," + Variable.SERVER_SOFT_URL
				+ "/userinfo.jspx";
		;
		CacheOpt db = new CacheOpt();
		String str = db.getValue(key1, this);// 读取数据库
		if (str != null) {
			Map<String, Object> map = ResponsePaseUtil.getInstance()
					.parseResponse((String) str);
			int code = (Integer) map.get("code");
			if (code > 0) {
				List<Map<String, Object>> list = (List<Map<String, Object>>) map
						.get("objlist");

				if (null != list && !list.isEmpty()) {
					initCarData(list);
				}
			} else if (code == -3) {
				skipLogin();
			} else {
				showDialog((String) map.get("msg"));
			}
			DefaultCarRequest request = new DefaultCarRequest();
			request.getDefaultCarMessage(dbinfoHandler);
		} else {
			DefaultCarRequest request = new DefaultCarRequest();
			request.getDefaultCarMessage(defaultHandler);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cars.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_total_activity);
		showTopTitle(getString(R.string.account_total_str),
				getString(R.string.account_water_str));
		initview();

		initdata();

	}

	/**
	 * 展示头部
	 * 
	 * @param titleName
	 *            头部名称
	 * @param nextTitleName
	 *            需要跳转
	 */
	private void showTopTitle(String titleName, final String nextTitleName) {
		Button backBtn = (Button) findViewById(R.id.backBtn);
		TextView topTips = (TextView) findViewById(R.id.top_title);
		if (null == titleName) {
			titleName = getString(R.string.app_name);
		}

		topTips.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showTopDialog();
			}

			private void showTopDialog() {
				String[] items = new String[] { nextTitleName };
				AlertDialog.Builder builder = new AlertDialog.Builder(
						AccountTotalActivity.this);
				builder.setTitle(getString(R.string.check_str));
				builder.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// 跳转到添加话费界面
						Intent intent = new Intent();
						intent.setClass(AccountTotalActivity.this,
								AccountWaterActivity.class);
						startActivity(intent);
						finish();
					}
				});
				builder.create().show();
			}
		});
		topTips.setText(titleName);

		backBtn.setText(getString(R.string.back_btn_str));
		backBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		ImageButton image_btn = (ImageButton) findViewById(R.id.set_btn);
		image_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(AccountTotalActivity.this,
						AccountRecordActivity.class);
				startActivity(intent);
			}
		});
	}

	/**
	 * 初始化数据
	 */
	private void initdata() {
		getDefaultCar();
		initTypeAdapter();
		getCost();
	}

	/**
	 * 获取图片
	 */
	private void getPic(String filename) {
		showNetDialog(R.string.tips_str, R.string.net_work_request_str);
		AccountRequest request = new AccountRequest();
		request.getAccountPic(accountHandler, filename+"barchart.jpg");
	}

	/**
	 * 获取图片
	 */
	private void getPic2(String filename) {
		showNetDialog(R.string.tips_str, R.string.net_work_request_str);
		AccountRequest request = new AccountRequest();
		request.getAccountPic(imageHandler,filename+"piechart.jpg");
	}

	/**
	 * 查看图
	 */
	private void getCost() {
		showNetDialog(R.string.tips_str, R.string.net_work_request_str);
		AccountRequest request = new AccountRequest();
		starttime = starttime.replace("-", "");
		endtime = endtime.replace("-", "");
		request.queryAccountRecord(handler, type, usercarid, starttime,
				endtime, 400, 300, selecttype, cartype, random);
		cartype = "1";
		random = "1";
	}

	/**
	 * 初始化布局
	 */
	private void initview() {
		imageview = (ImageView) findViewById(R.id.image_user);
		image_radiu = (ImageView) findViewById(R.id.image_radiu);
		spinner1 = (Spinner) findViewById(R.id.spinner_1);
		spinner2 = (Spinner) findViewById(R.id.spinner_2);
		spinner3 = (EditText) findViewById(R.id.spinner_3);
		spinner4 = (EditText) findViewById(R.id.spinner_4);
		bar_btn = (CheckBox) findViewById(R.id.bar_btn);
		line_btn = (CheckBox) findViewById(R.id.line_btn);
		same_car_1 = (Button) findViewById(R.id.same_car_1);
		other_car_1 = (Button) findViewById(R.id.other_car_1);
		submit = (Button) findViewById(R.id.submit);

		btn_1 = (Button) findViewById(R.id.nodeadd1_btn);
		btn_2 = (Button) findViewById(R.id.nodeadd2_btn);
		btn_3 = (Button) findViewById(R.id.nodeadd3_btn);
		btn_4 = (Button) findViewById(R.id.nodeadd4_btn);
		btn_5 = (Button) findViewById(R.id.nodeadd5_btn);

		btn_1.setOnClickListener(this);
		btn_2.setOnClickListener(this);
		btn_3.setOnClickListener(this);
		btn_4.setOnClickListener(this);
		btn_5.setOnClickListener(this);

		starttime = Util.getDelayMonth(3);
		endtime = Util.getDay();
		spinner3.setText(starttime);
		spinner4.setText(endtime);

		rg = (RadioGroup) findViewById(R.id.radioG);

		rg.setOnCheckedChangeListener(this);

		bar_btn.setOnClickListener(this);
		line_btn.setOnClickListener(this);
		same_car_1.setOnClickListener(this);
		other_car_1.setOnClickListener(this);
		submit.setOnClickListener(this);

		spinner3.setOnClickListener(this);
		spinner4.setOnClickListener(this);
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
		case R.id.spinner_1:
			if (usercarid.equals("")) {
				usercarid = listData.get(position).get("ID") + "";
				getCost();
			} else {
				usercarid = listData.get(position).get("ID") + "";
			}
			break;
		case R.id.spinner_2:
			type = typeIdArray[position];
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
		// TODO Auto-generated method stub

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
			year2 = canledar.get(Calendar.YEAR);
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
						editText.setText(timeName);
						switch (editText.getId()) {
						case R.id.spinner_3:
							starttime = year + month + day;
							break;
						case R.id.spinner_4:
							endtime = year + month + day;
							break;
						}
					}
				}, year2, month3, day4);
		datePickerDialog.setTitle("请选择");
		datePickerDialog.show();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		image_radiu.setVisibility(View.VISIBLE);
		switch (v.getId()) {
		case R.id.spinner_3:
			showTimeView(spinner3);
			break;
		case R.id.spinner_4:
			showTimeView(spinner4);
			break;
		case R.id.bar_btn:
			pic_name = "_barchart.jpg";
			line_btn.setChecked(false);
			break;
		case R.id.line_btn:
			pic_name = "_linechart.jpg";
			bar_btn.setChecked(false);
			break;
		case R.id.same_car_1:
		case R.id.nodeadd3_btn:
			image_radiu.setVisibility(View.GONE);
			cartype = "2";
			random = "1";
			getCost();
			break;
		case R.id.other_car_1:
		case R.id.nodeadd4_btn:
			image_radiu.setVisibility(View.GONE);
			cartype = "1";
			random = "2";
			getCost();
			break;
		case R.id.submit:
			image_radiu.setVisibility(View.VISIBLE);
			cartype = "1";
			random = "1";
			getCost();
			break;
		case R.id.nodeadd1_btn:
			showType();
			break;
		case R.id.nodeadd2_btn:
			showTimeDialog();
			break;
		case R.id.nodeadd5_btn:
			showMoreDialog();
			break;
		default:
			break;
		}
	}

	/**
	 * 展示更多界面
	 */
	private void showMoreDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.more_str));
		builder.setItems(getResources().getStringArray(R.array.more_array),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						switch (arg1) {
						case 0:
							showCarDialog();
							break;
						case 1:
							// showLineDialog();
//							if (pic_name.equals("_barchart.jpg")) {
//								pic_name = "_linechart.jpg";
//							} else {
//								pic_name = "_barchart.jpg";
//							}
//							getCost();
						    showHositoryDialog();
							break;
						case 2:
//							showHositoryDialog();
							break;
						}
					}
				});
		builder.create().show();
	}

	/**
	 * 显示历史记录
	 */
	private void showHositoryDialog() {
		// int year2;
		// int month3;
		// int day4;
		// year2 = 2010;
		// month3 = 0;
		// day4 = 1;
		//
		// final DatePickerDialog datePickerDialog = new DatePickerDialog(
		// AccountTotalActivity.this,
		// new DatePickerDialog.OnDateSetListener() {
		// @Override
		// public void onDateSet(DatePicker view, int year1,
		// int monthOfYear1, int dayOfMonth1) {
		// String year = year1 + "";
		// String month = (monthOfYear1 + 2) + "";
		// String day = dayOfMonth1 + "";
		// if (day.length() == 1) {
		// day = "0" + day;
		// }
		// if (month.length() == 1) {
		// month = "0" + month;
		// }
		// endtime = year + month + day;
		//
		// month = (monthOfYear1 + 1) + "";
		// if (month.length() == 1) {
		// month = "0" + month;
		// }
		// starttime = year + month + day;
		// getCost();
		// }
		// }, year2, month3, day4);
		// datePickerDialog.setTitle("请选择");
		// datePickerDialog.show();
		final DateSelectDialog dialog = new DateSelectDialog(this);
		dialog.show();
		dialog.getSureBtn().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int selectYear = dialog.getYear();
				int selectMonth = dialog.getMonth();
				StringBuffer st = new StringBuffer();
				starttime = st.append(selectYear).append(selectMonth).append("00")
						.toString();
				st = new StringBuffer();
				if (selectMonth == 12)
				{
				    endtime = st.append(selectYear + 1).append(01).append("00")
                    .toString();
				}
				else
				{
				    endtime = st.append(selectYear).append(selectMonth + 1).append("00")
				        .toString();
				}
//				endtime = "-2";
				dialog.dismiss();
				getCost();
			}
		});

		dialog.getCancelBtn().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}

	private void showCarDialog() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.violation_car_choose_str));
		builder.setItems(carArray, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				usercarid = carIdArray[arg1];
				getCost();
			}
		});
		builder.create().show();

	}

	private void showType() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.account_choose_date_str));
		builder.setItems(typeArray, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				type = typeIdArray[arg1];
				getCost();
			}
		});
		builder.create().show();
	}

	/**
	 * 选择时间Dialog
	 */
	private void showTimeDialog() {
		String[] items = getResources().getStringArray(R.array.time_array);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.account_choose_date_str));
		builder.setItems(items, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				switch (arg1) {
				case 0:
					starttime = Util.getDelayMonth(1);
					break;
				case 1:
					starttime = Util.getDelayMonth(3);
					break;
				case 2:
					starttime = Util.getDelayMonth(6);
					break;
				case 3:
					starttime = Util.getDelayMonth(12);
					break;
				default:
					break;
				}
				endtime = Util.getEndDate();
				getCost();
			}
		});
		builder.create().show();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.RadioGroup.OnCheckedChangeListener#onCheckedChanged(android
	 * .widget.RadioGroup, int)
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.radio_week:
			selecttype = "1";
			break;
		case R.id.radio_month:
			selecttype = "2";
			break;
		}
	}
}
