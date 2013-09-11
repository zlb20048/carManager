/**
 * 
 */
package com.cars.simple.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.cars.simple.R;
import com.cars.simple.fusion.FusionCode;
import com.cars.simple.fusion.Variable;
import com.cars.simple.logic.AccountRequest;
import com.cars.simple.logic.CacheOpt;
import com.cars.simple.logic.DefaultCarRequest;
import com.cars.simple.mode.ViewHolder;
import com.cars.simple.service.download.GetServerImageProcessor;
import com.cars.simple.service.download.ImageObj;
import com.cars.simple.util.ResponsePaseUtil;
import com.cars.simple.util.Util;
import com.cars.simple.widget.DateSelectDialog;

/**
 * @author liuzixiang
 * 
 */
public class AccountWaterActivity extends BaseActivity implements
		OnItemSelectedListener, OnItemClickListener, OnScrollListener,
		OnClickListener {

	private final static String TAG = AccountWaterActivity.class
			.getSimpleName();

	/**
	 * 车辆
	 */
	private Spinner spinner_1 = null;

	/**
	 * 选择类型
	 */
	private Spinner spinner_2 = null;

	/**
	 * 时间
	 */
	private Spinner spinner_3 = null;

	/**
	 * 类别
	 */
	private Button button_1 = null;

	/**
	 * 时间
	 */
	private Button button_2 = null;

	/**
	 * 车辆切换
	 */
	private Button button_3 = null;

	/**
	 * 历史
	 */
	private Button button_4 = null;

	/**
	 * 车型的列表
	 */
	private String[] carArray = null;

	/**
	 * 车型Id的列表
	 */
	private String[] carIdArray = null;

	/**
	 * 车辆
	 */
	private ArrayAdapter<String> carAdapter = null;

	/**
	 * 类别
	 */
	private ArrayAdapter<String> typeAdapter = null;

	/**
	 * 年
	 */
	private ArrayAdapter<String> dateAdapter = null;

	/**
	 * 车辆信息
	 */
	private List<Map<String, Object>> carList = null;

	/**
	 * 类型
	 */
	private String[] typeArray = null;

	/**
	 * 类型id
	 */
	private String[] typeIdArray = null;

	/**
	 * 年份
	 */
	private String[] dateArray = null;

	/**
	 * 列表数据源
	 */
	private List<Map<String, Object>> listdata = new ArrayList<Map<String, Object>>();

	/**
	 * 列表
	 */
	private ListView listview = null;

	/**
	 * adapter
	 */
	private MyAdapter adapter = null;

	/**
	 * 车辆ID
	 */
	private String usercarid = "-2";

	/**
	 * 流水帐类型
	 */
	private String type = "-2";

	/**
	 * 查询时间区域
	 */
	private int time = 0;

	/**
	 * 起始时间
	 */
	private String starttime;

	/**
	 * 结束时间
	 */
	private String endtime;

	/**
	 * 第一次加载
	 */
	private boolean is_spinner_1 = true;

	/**
	 * 第一次加载
	 */
	private boolean is_spinner_2 = true;

	/**
	 * 第一次加载
	 */
	private boolean is_spinner_3 = true;

	/**
	 * Button
	 */
	private Button total = null;

	/**
	 * 删除后需要显示的
	 */
	private int deletePosion = -1;
	
	private String key1 = null;
	private String key2 = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cars.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_water_activity);

		showTopTitle(getString(R.string.account_water_str),
				getString(R.string.account_total_str));

		initview();

		initData();

		getDefaultCar();

		adapter = new MyAdapter(this);

		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);
		listview.setOnScrollListener(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		getDefaultCar();
		Log.i(TAG, "onStart...");
	}
	@Override
	protected void onResume() {
		super.onResume();
		getDefaultCar();
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
						AccountWaterActivity.this);
				builder.setTitle(getString(R.string.check_str));
				builder.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// 跳转到添加话费界面
						Intent intent = new Intent();
						intent.setClass(AccountWaterActivity.this,
								AccountTotalActivity.class);
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
				intent.setClass(AccountWaterActivity.this,
						AccountRecordActivity.class);
				startActivity(intent);
				//finish();
			}
		});
	}

	
	private Handler dbhandler = new Handler() {

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
					db.update(key2,(String)object,AccountWaterActivity.this);
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
				totalPage = (Integer) map.get("allpage");
				if (code > 0) {
					initListData((List<Map<String, Object>>) map.get("objlist"));
					CacheOpt db = new CacheOpt();
					db.save(key2,(String)object,AccountWaterActivity.this);
				} else if (code == -3) {
					skipLogin();
				} else {
					showDialog((String) map.get("msg"));
				}
				if (totalPage > page) {
					footerButton.setVisibility(View.VISIBLE);
					footerProgressBarLayout.setVisibility(View.GONE);
				} else {
					footerButton.setVisibility(View.GONE);
					footerProgressBarLayout.setVisibility(View.GONE);
				}
				total.setText(getString(R.string.account_all_str)
						+ map.get("all"));
				adapter.notifyDataSetChanged();
				break;
			case FusionCode.NETWORK_ERROR:
			case FusionCode.NETWORK_TIMEOUT_ERROR:
				showDialog(getString(R.string.net_error));
				break;
			}
		}
	};

	/**
	 * 处理图片的Handler
	 */
	private Handler imageHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int what = msg.what;
			switch (what) {
			case FusionCode.DOWNLOAD_IMAGE_FINISH:
				ImageObj obj = (ImageObj) msg.obj;
				byte[] image = obj.getImg();
				obj.getMap().put("bitmap",
						BitmapFactory.decodeByteArray(image, 0, image.length));
				adapter.notifyDataSetChanged();
				break;
			default:
				break;
			}
		}

	};

	/**
	 * 初始化列表界面
	 * 
	 * @param list
	 *            数据
	 */
	private void initListData(List<Map<String, Object>> list) {
		for (Map<String, Object> map : list) {
			Bitmap bitmap = GetServerImageProcessor.getInstance()
					.getPromotionList(this, map, imageHandler);
			map.put("bitmap", bitmap);
			String pricetime = map.get("PRICETIME") + "";
			map.put("PRICETIME", Util.formatTime(pricetime));
			String allprice = map.get("ALLPRICE") + "";
			map.put("ALLPRICE", allprice);
			Object obj = map.get("ACCOUNTTYPE");
			if (null != obj && !"".equals(obj)) {
				int type = (Integer) obj;
				if(type>3)
					type --;
				System.out.println("typetypetype=="+type);
				if (type == -2) {
					map.put("type", typeArray[0]);
				} else {
					map.put("type", typeArray[type]);
				}
			} else {
				map.put("type", typeArray[0]);
			}
			listdata.add(map);
		}
		count = listdata.size();
		if (null != adapter)
		{
			adapter.notifyDataSetChanged();
		}
	}

	/**
	 * 获取流水帐号信息
	 */
	private void getWaterAccountRecord(boolean isClearData) {
		key2 =  Variable.Session.USER_ID+","+Variable.SERVER_SOFT_URL
				+ "/message/getaccount.jspx?usercarid=" + usercarid + "&type="
				+ type + "&starttime=" + starttime + "&endtime=" + endtime
				+ "&pagesize=" + FusionCode.pageSize + "&page=" + page;

		CacheOpt db = new CacheOpt();
		String str =db.getValue(key2,this);//读取数据库	
		if (isClearData) {
			listdata.clear();
		}
		if(str!=null) {
			Map<String, Object> map = ResponsePaseUtil.getInstance()
					.parseResponse((String) str);
			int code = (Integer) map.get("code");
			totalPage = (Integer) map.get("allpage");
			if (code > 0) {
				initListData((List<Map<String, Object>>) map.get("objlist"));
			} else if (code == -3) {
				skipLogin();
			} else {
				showDialog((String) map.get("msg"));
			}
			if (totalPage > page) {
				footerButton.setVisibility(View.VISIBLE);
				footerProgressBarLayout.setVisibility(View.GONE);
			} else {
				footerButton.setVisibility(View.GONE);
				footerProgressBarLayout.setVisibility(View.GONE);
			}
			total.setText(getString(R.string.account_all_str)
					+ map.get("all"));
			//adapter.notifyDataSetChanged();
			
			starttime = starttime.replace("-", "");
			endtime = endtime.replace("-", "");
			AccountRequest request = new AccountRequest();
			request.queryAccountWaterRecord(dbhandler, type, usercarid, starttime,
					endtime, page);
		}else{	
			showNetDialog(R.string.tips_str, R.string.net_work_request_str);
			starttime = starttime.replace("-", "");
			endtime = endtime.replace("-", "");
			AccountRequest request = new AccountRequest();
			request.queryAccountWaterRecord(handler, type, usercarid, starttime,
					endtime, page);
		}
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		starttime = Util.getBeginDateNew();
		endtime = Util.getEndDateNew();

		initTypeAdapter();
		initDateAdapter();
	}

	/**
	 * 初始化年
	 */
	private void initDateAdapter() {
		dateArray = getResources().getStringArray(R.array.datearray);
		dateAdapter = new ArrayAdapter<String>(this, R.layout.myspinner_item,
				dateArray);
		dateAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 设置下拉样式
		spinner_3.setAdapter(dateAdapter);
		spinner_3.setOnItemSelectedListener(this);
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
		spinner_2.setAdapter(typeAdapter);
		spinner_2.setOnItemSelectedListener(this);
	}

	/**
	 * 初始化view
	 */
	private void initview() {
		spinner_1 = (Spinner) findViewById(R.id.spinner_1);
		spinner_2 = (Spinner) findViewById(R.id.spinner_2);
		spinner_3 = (Spinner) findViewById(R.id.spinner_3);
		total = (Button) findViewById(R.id.total);
		listview = (ListView) findViewById(R.id.list);

		button_1 = (Button) findViewById(R.id.nodeadd1_btn);
		button_2 = (Button) findViewById(R.id.nodeadd2_btn);
		button_3 = (Button) findViewById(R.id.nodeadd3_btn);
		button_4 = (Button) findViewById(R.id.nodeadd4_btn);

		button_1.setOnClickListener(this);
		button_2.setOnClickListener(this);
		button_3.setOnClickListener(this);
		button_4.setOnClickListener(this);

		showMore(listview);
	}

	
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
					db.update(key1,(String)object,AccountWaterActivity.this);
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
					Map<String, Object> objMap = (Map<String, Object>) map
							.get("obj");
					List<Map<String, Object>> list = (List<Map<String, Object>>) map
							.get("objlist");

					if (null != list && !list.isEmpty()) {
						initCarAdapter(list);
						CacheOpt db = new CacheOpt();
						db.save(key1,(String)object,AccountWaterActivity.this);
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
	 * 获取默认的用户的车辆
	 */
	public void getDefaultCar() {
		key1 =  Variable.Session.USER_ID+","+Variable.SERVER_SOFT_URL + "/userinfo.jspx";;
		CacheOpt db = new CacheOpt();
		String str =db.getValue(key1,this);//读取数据库		
		if(str!=null) {
			Map<String, Object> map = ResponsePaseUtil.getInstance()
					.parseResponse((String) str);
			int code = (Integer) map.get("code");
			if (code > 0) {
				Map<String, Object> objMap = (Map<String, Object>) map
						.get("obj");
				List<Map<String, Object>> list = (List<Map<String, Object>>) map
						.get("objlist");

				if (null != list && !list.isEmpty()) {
					initCarAdapter(list);
				}
			} else if (code == -3) {
				skipLogin();
			} else {
				showDialog((String) map.get("msg"));
			}
			DefaultCarRequest request = new DefaultCarRequest();
			request.getDefaultCarMessage(dbinfoHandler);
		}else{
			DefaultCarRequest request = new DefaultCarRequest();
			request.getDefaultCarMessage(defaultHandler);
		}
	}

	/**
	 * 初始化
	 */
	private void initCarAdapter(List<Map<String, Object>> list) {
		carList = list;
		int size = list.size();
		carArray = new String[size];
		carIdArray = new String[size];
		carAdapter = new ArrayAdapter<String>(this, R.layout.myspinner_item);
		carAdapter.add(getString(R.string.account_car_choose_str));
		for (int i = 0; i < size; i++) {
			carAdapter.add((String) list.get(i).get("NAME"));
			carArray[i] = (String) list.get(i).get("NAME");
			carIdArray[i] = String.valueOf(list.get(i).get("ID"));
		}
		usercarid = carIdArray[0];
		carAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 设置下拉样式
		spinner_1.setAdapter(carAdapter);
		spinner_1.setOnItemSelectedListener(this);
		getWaterAccountRecord(true);
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

		page = 1;

		switch (parent.getId()) {
		case R.id.spinner_1:
			is_spinner_1 = false;
			if (position > 0) {
				usercarid = carList.get(position - 1).get("ID") + "";
			} else {
				usercarid = "-2";
			}
			break;
		case R.id.spinner_2:
			is_spinner_2 = false;
			type = typeIdArray[position];
			break;
		case R.id.spinner_3:
			is_spinner_3 = false;
			time = 1;
			break;
		default:
			break;
		}
		if (!(is_spinner_1 || is_spinner_2 || is_spinner_3)) {
			getWaterAccountRecord(true);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget
	 * .AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent();
		Map<String, Object> map = listdata.get(position);
		Bundle bundle = new Bundle();
		bundle.putSerializable("data", (Serializable) map);
		bundle.putBoolean("isUpdate", true);
		intent.putExtras(bundle);
		intent.setClass(AccountWaterActivity.this, AccountRecordActivity.class);
		startActivity(intent);
		//finish();
	}

	/**
	 * 展示更多
	 */
	private void showMore(ListView listview) {
		// Listview的一些东西
		LayoutInflater inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.list_foot_layout, null);
		footerButton = (Button) view.findViewById(R.id.button);
		footerProgressBarLayout = (LinearLayout) view
				.findViewById(R.id.linearlayout);
		footerProgressBarLayout.setVisibility(View.GONE);
		// adapter = new MyAdapter(this);
		footerButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (lastItem == adapter.getCount()
						&& scrollState == OnScrollListener.SCROLL_STATE_IDLE
						&& lastItem != 0) {
					page++;
					footerButton.setVisibility(View.GONE);
					footerProgressBarLayout.setVisibility(View.VISIBLE);
					// 发送请求
					getWaterAccountRecord(false);
				}
			}
		});
		listview.addFooterView(view);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		this.visibleItemCount = visibleItemCount;
		lastItem = firstVisibleItem + visibleItemCount - 1;

		if (page == totalPage) {
			listview.removeFooterView(view);
		}
	}

	/**
	 * 删除
	 */
	private Handler deleteHandler = new Handler() {

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
				if (code > 0) {
					if (deletePosion > -1) {
						listdata.remove(deletePosion);
						showDialog(getString(R.string.account_delete_success_str));
					}
				} else if (code == -3) {
					skipLogin();
				} else {
					deletePosion = -1;
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

	/**
	 * 删除流水账
	 * 
	 * @param id
	 *            accountid
	 */
	private void deleteWaterAccount(String id) {
		showNetDialog(R.string.tips_str, R.string.net_work_request_str);
		AccountRequest request = new AccountRequest();
		request.deleteWaterAccount(deleteHandler, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AbsListView.OnScrollListener#onScrollStateChanged(android
	 * .widget.AbsListView, int)
	 */
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		this.scrollState = scrollState;
	}

	public class MyAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		public MyAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return listdata.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return listdata.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(
						R.layout.account_water_list_item, null);

				// adapter = new SimpleAdapter(this, listdata,
				// R.layout.account_water_list_item, new String[] { "ALLPRICE",
				// "PRICETIME", "type" }, new int[] { R.id.item_1,
				// R.id.item_2, R.id.item_3 });

				holder.title = (TextView) convertView.findViewById(R.id.item_1);
				holder.text = (TextView) convertView.findViewById(R.id.item_2);
				holder.time = (TextView) convertView.findViewById(R.id.item_3);
				holder.type = (TextView) convertView.findViewById(R.id.item_4);
				holder.btn_1 = (Button) convertView
						.findViewById(R.id.update_btn);
				holder.btn_2 = (Button) convertView
						.findViewById(R.id.delete_btn);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.title.setText((String) listdata.get(position)
					.get("ALLPRICE"));
			holder.text.setText((String) listdata.get(position)
					.get("PRICETIME"));
			holder.type.setText((String) listdata.get(position).get("CARNAME"));
			holder.time.setText((String) listdata.get(position).get("type"));
			holder.btn_1.setOnClickListener(new Button.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					Map<String, Object> map = listdata.get(position);
					Bundle bundle = new Bundle();
					bundle.putSerializable("data", (Serializable) map);
					bundle.putBoolean("isUpdate", true);
					intent.putExtras(bundle);
					intent.setClass(AccountWaterActivity.this,
							AccountRecordActivity.class);
					startActivity(intent);
					//finish();
				}
			});
			holder.btn_2.setOnClickListener(new Button.OnClickListener() {

				@Override
				public void onClick(View v) {
					// 调用删除接口
					deletePosion = position;
					deleteWaterAccount(listdata.get(position).get("ACCOUNTID")
							+ "");
				}
			});

			return convertView;
		}
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.nodeadd1_btn:
			showType();
			break;
		case R.id.nodeadd2_btn:
			showTimeDialog();
			break;
		case R.id.nodeadd3_btn:
			showCarDialog();
			break;
		case R.id.nodeadd4_btn:
			showHositoryDialog();
			break;
		default:
			break;
		}
	}

	/**
	 * 显示历史记录
	 */
	private void showHositoryDialog() {
		// int size = 10;
		// String[] items = new String[size];
		// for (int i = 0; i < size; i++) {
		// items[i] = Util.getDelayYear(i);
		// }
		//
		// AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// builder.setTitle(getString(R.string.account_choose_date_str));
		// builder.setItems(items, new DialogInterface.OnClickListener() {
		//
		// @Override
		// public void onClick(DialogInterface arg0, int arg1) {
		// starttime = Util.getDelayYear(arg1);
		// }
		// });
		// builder.create().show();
		// int year2;
		// int month3;
		// int day4;
		// year2 = 2010;
		// month3 = 0;
		// day4 = 1;
		//
		// final DatePickerDialog datePickerDialog = new DatePickerDialog(
		// AccountWaterActivity.this,
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
		// getWaterAccountRecord(true);
		// }
		// }, year2, month3, day4);
		// datePickerDialog.setTitle("请选择");
		// datePickerDialog.show();
		final DateSelectDialog dialog = new DateSelectDialog(this);
		dialog.show();
		dialog.getSureBtn().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
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
				getWaterAccountRecord(true);
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
				getWaterAccountRecord(true);
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
				getWaterAccountRecord(true);
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
				getWaterAccountRecord(true);
			}
		});
		builder.create().show();
	}

}
