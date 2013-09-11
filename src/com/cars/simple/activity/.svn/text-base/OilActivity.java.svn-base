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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.cars.simple.R;
import com.cars.simple.fusion.FusionCode;
import com.cars.simple.fusion.Variable;
import com.cars.simple.logic.AccountRequest;
import com.cars.simple.logic.DefaultCarRequest;
import com.cars.simple.util.ResponsePaseUtil;
import com.cars.simple.util.Util;
import com.cars.simple.widget.DateSelectDialog;

/**
 * @author liuzixiang
 * 
 */
public class OilActivity extends BaseActivity implements OnClickListener,
		OnItemSelectedListener {

	private final static String TAG = OilActivity.class.getSimpleName();

	/**
	 * ImageView
	 */
	private ImageView imageview = null;

	/**
	 * 车辆
	 */
	private Spinner spinner1 = null;

	/**
	 * 开始时间
	 */
	private EditText spinner3 = null;

	/**
	 * 结束时间
	 */
	private EditText spinner4 = null;

	/**
	 * 时间按钮
	 */
	private Button time_btn = null;

	/**
	 * 最近油耗按钮
	 */
	private Button re_btn = null;

	/**
	 * 平均油耗按钮
	 */
	private Button avg_btn = null;

	/**
	 * 历史
	 */
	private Button hositoy_btn = null;

	/**
	 * 柱状图
	 */
	private Button zzt_btn = null;

	/**
	 * 折线图
	 */
	private Button zxt_btn = null;

	/**
	 * 最近油耗
	 */
	private TextView zyyh_text = null;

	/**
	 * 平均油耗
	 */
	private TextView pyyh_text = null;

	/**
	 * 数据
	 */
	private List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();

	/**
	 * 车型的列表
	 */
	private String[] carArray = null;

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
	 * 查询
	 */
	private Button submit = null;

	/**
	 * 类别
	 */
	private ArrayAdapter<String> typeAdapter = null;

	/**
	 * 需要请求的图片的名车
	 */
	private String pic_name = "_oilbarchart.jpg";

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

	private TextView contentText = null;

	private TextView smallText = null;

	private TextView contentText_1 = null;

	private TextView smallText_1 = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cars.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.oil_total_activity);

		showTop(getString(R.string.oil_cost_title_str), null);

		initview();

		initdata();

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
		spinner1 = (Spinner) findViewById(R.id.top_spinner);
	}

	/**
	 * 初始化数据
	 */
	private void initdata() {
		getDefaultCar();
	}

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
		int selection = 0;
		carArray = new String[size];
		for (int i = 0; i < size; i++) {
			Map<String, Object> m = list.get(i);
			String name = (String) m.get("NAME");
			carArray[i] = name;
			
			if (name.equals(Variable.Session.USERCARNAME))
			{
			    selection = i;
			}
		}
		adapter = new ArrayAdapter<String>(this, R.layout.myspinner_item,
				carArray);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 设置下拉样式
		spinner1.setAdapter(adapter);
		spinner1.setSelection(selection);
		spinner1.setOnItemSelectedListener(this);
	}

	/**
	 * 获取默认的用户的车辆
	 */
	public void getDefaultCar() {
		showNetDialog(R.string.tips_str, R.string.net_work_request_str);
		DefaultCarRequest request = new DefaultCarRequest();
		request.getDefaultCarMessage(defaultHandler);
	}

	/**
	 * 查看图
	 */
	private void getCost() {
		showNetDialog(R.string.tips_str, R.string.net_work_request_str);
		AccountRequest request = new AccountRequest();
		starttime = starttime.replace("-", "");
		endtime = endtime.replace("-", "");
		request.queryOilRecord(handler, usercarid, starttime, endtime, 400, 300);
	}

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
				String  filename = (String) map.get("msg");
				if (code > 0) {
					Map<String, Object> m = (Map<String, Object>) map
							.get("monthCost");
					zyyh_text.setText("");
					pyyh_text.setText("");
					if (null != m && !m.isEmpty()) {
						String oilavg = (String) m.get("oilavg");
						contentText.setText(oilavg);
						Object no = m.get("no");
						int nu = 0;
						if (null != no) {
							nu = (Integer) no;
						}
						zyyh_text.setText("最近油耗：" + oilavg + " 节油排名：" + nu);
					}
					Map<String, Object> m1 = (Map<String, Object>) map
							.get("alltimesCosts");
					if (null != m1 && !m1.isEmpty()) {
						String oilavg = (String) m1.get("oilavg");
						Object no = m1.get("no");
						int nu = 0;
						if (null != no) {
							nu = (Integer) no;
						}
						contentText_1.setText(oilavg);
						pyyh_text.setText("平均油耗：" + oilavg + " 节油排名：" + nu);
					}

					getPic(filename);
					
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
	 * 获取图片
	 */
	private void getPic(String filename) {
	    showNetDialog(R.string.tips_str, R.string.net_work_request_str);
		AccountRequest request = new AccountRequest();
		request.getAccountPic(accountHandler, filename);
	}

	int i =0;
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

	/**
	 * 
	 */
	private void initview() {
		imageview = (ImageView) findViewById(R.id.image_user);
		// spinner1 = (Spinner) findViewById(R.id.spinner_1);
		spinner3 = (EditText) findViewById(R.id.spinner_3);
		spinner4 = (EditText) findViewById(R.id.spinner_4);

		time_btn = (Button) findViewById(R.id.time_btn);
		re_btn = (Button) findViewById(R.id.re_btn);
		avg_btn = (Button) findViewById(R.id.avg_btn);
		hositoy_btn = (Button) findViewById(R.id.hostory_btn);
		zzt_btn = (Button) findViewById(R.id.zzt_btn);
		zxt_btn = (Button) findViewById(R.id.zxt_btn);
		zyyh_text = (TextView) findViewById(R.id.zjyh_text);
		pyyh_text = (TextView) findViewById(R.id.pyyh_text);

		time_btn.setOnClickListener(this);
		re_btn.setOnClickListener(this);
		avg_btn.setOnClickListener(this);
		hositoy_btn.setOnClickListener(this);
		zzt_btn.setOnClickListener(this);
		zxt_btn.setOnClickListener(this);

		starttime = Util.getDelayMonth(3);
		endtime = Util.getDay();

		spinner3.setText(starttime);
		spinner4.setText(endtime);

		bar_btn = (CheckBox) findViewById(R.id.bar_btn);
		line_btn = (CheckBox) findViewById(R.id.line_btn);
		submit = (Button) findViewById(R.id.submit);

		smallText = (TextView) findViewById(R.id.smallText);
		contentText = (TextView) findViewById(R.id.contentText);

		smallText_1 = (TextView) findViewById(R.id.smallText_1);
		contentText_1 = (TextView) findViewById(R.id.contentText_1);

		bar_btn.setOnClickListener(this);
		line_btn.setOnClickListener(this);
		submit.setOnClickListener(this);

		spinner3.setOnClickListener(this);
		spinner4.setOnClickListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.spinner_3:
			showTimeView(spinner3);
			break;
		case R.id.spinner_4:
			showTimeView(spinner4);
			break;
		case R.id.bar_btn:
			pic_name = "_oilbarchart.jpg";
			line_btn.setChecked(false);
			break;
		case R.id.line_btn:
			pic_name = "_oillinechart.jpg";
			bar_btn.setChecked(false);
			break;
		case R.id.submit:
			getCost();
			break;
		case R.id.time_btn:
			showTimeDialog();
			break;
		case R.id.re_btn:
			showYh(1);
			break;
		case R.id.avg_btn:
			showYh(2);
			break;
		case R.id.hostory_btn:
			showHositoryDialog();
			break;
		case R.id.zzt_btn:
			pic_name = "_oilbarchart.jpg";
			break;
		case R.id.zxt_btn:
			pic_name = "_oillinechart.jpg";
			break;
		default:
			break;
		}
		getCost();
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
		// OilActivity.this, new DatePickerDialog.OnDateSetListener() {
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

	/**
	 * @param i
	 */
	private void showYh(int i) {
		if (i == 1) {
			if (zyyh_text.getVisibility() == View.VISIBLE) {
				zyyh_text.setVisibility(View.GONE);
			} else {
				zyyh_text.setVisibility(View.INVISIBLE);
			}
		} else if (i == 2) {
			if (pyyh_text.getVisibility() == View.VISIBLE) {
				pyyh_text.setVisibility(View.GONE);
			} else {
				pyyh_text.setVisibility(View.VISIBLE);
			}
		}
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
	 * android.widget.AdapterView.OnItemSelectedListener#onItemSelected(android
	 * .widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		switch (parent.getId()) {
		case R.id.top_spinner:
			usercarid = listData.get(position).get("ID") + "";
			getCost();
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
}
