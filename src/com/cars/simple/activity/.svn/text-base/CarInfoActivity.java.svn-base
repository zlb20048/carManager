/**
 * 
 */
package com.cars.simple.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cars.simple.R;
import com.cars.simple.fusion.FusionCode;
import com.cars.simple.fusion.Variable;
import com.cars.simple.logic.CacheOpt;
import com.cars.simple.logic.CarInfoRequeset;
import com.cars.simple.logic.CarTypeRequest;
import com.cars.simple.logic.DefaultCarRequest;
import com.cars.simple.mode.ViewHolder;
import com.cars.simple.util.ResponsePaseUtil;

/**
 * @author liuzixiang
 * 
 */
public class CarInfoActivity extends BaseActivity implements
		OnItemClickListener {

	/**
	 * 标识
	 */
	private final static String TAG = CarInfoActivity.class.getSimpleName();

	/**
	 * 列表项
	 */
	private ListView listview = null;

	/**
	 * 查询得到的数据
	 */
	private List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();

	/**
	 * 初始化页数
	 */
	private int currentpage = 1;

	/**
	 * 当前删除的id
	 */
	private int deleposition = -9999;

	/**
	 * 当前的数据
	 */
	private MyAdapter adapter = null;

	/**
	 * 返回按钮
	 */
	private Button backBtn = null;

	/**
	 * 添加按钮
	 */
	private Button addBtn = null;

	/**
	 * 是否需要刷新
	 */
	public static boolean isNeedRefreash = false;

	private String key = null;
	
	/**
	 * 数据信息
	 */
	private Handler infoHandler = new Handler() {

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
					initData((List<Map<String, Object>>) map.get("objlist"));
					CacheOpt db = new CacheOpt();
					db.save(key,(String)object,CarInfoActivity.this);
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
		Button addBtn = (Button) findViewById(R.id.addBtn);
		addBtn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(CarInfoActivity.this,
						CarInfoUpdateActivity.class);
				startActivity(intent);
			}
		});
		TextView top_title = (TextView) findViewById(R.id.top_title);
		top_title.setText(R.string.car_info_title_str);
	}

	/**
	 * 初始化数据
	 * 
	 * @param data
	 *            得到的数据
	 */
	private void initData(List<Map<String, Object>> data) {
		listData.clear();
		for (Map<String, Object> map : data) {
			listData.add(map);
		}
		adapter = new MyAdapter(this);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cars.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.car_info_activity);
		showTitle();
		listview = (ListView) findViewById(R.id.list);

		getCarInfo();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cars.activity.BaseActivity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();
		if (isNeedRefreash) {
			isNeedRefreash = false;
			getCarInfo();
		}
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
					db.update(key,(String)object,CarInfoActivity.this);
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
	 * 获取当前的车辆信息
	 */
	private void getCarInfo() {		
		key =  Variable.Session.USER_ID+","+Variable.SERVER_SOFT_URL + "/getmycarslist.jspx?pagesize="
				+ FusionCode.pageSize + "&page=" + currentpage;
		CacheOpt db = new CacheOpt();
		String str =db.getValue(key,this);//读取数据库		
		if(str!=null) {
			Map<String, Object> map = ResponsePaseUtil.getInstance()
					.parseResponse((String) str);
			int code = (Integer) map.get("code");
			if (code > 0) {
				initData((List<Map<String, Object>>) map.get("objlist"));
			} else if (code == -3) {
				skipLogin();
			} else {
				showDialog((String) map.get("msg"));
			}
			//异步请求更新数据库
			CarInfoRequeset request = new CarInfoRequeset();
			request.getCarInfo(dbinfoHandler, currentpage);
		}
		else{
			showNetDialog(R.string.tips_str, R.string.net_work_request_str);
			//获取网络数据
			CarInfoRequeset request = new CarInfoRequeset();
			request.getCarInfo(infoHandler, currentpage);
		}
		
	}

	public class MyAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		public MyAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return listData.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return listData.get(position);
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
				convertView = mInflater.inflate(R.layout.car_info_list_item,
						null);
				holder.title = (TextView) convertView
						.findViewById(R.id.carName);
				holder.text = (TextView) convertView.findViewById(R.id.carNo);
				holder.image = (ImageView) convertView.findViewById(R.id.icon);
				holder.image
						.setOnClickListener(new ImageView.OnClickListener() {

							@Override
							public void onClick(View v) {
								// 设置选中的颜色
								Variable.Session.USERCARID = listData
										.get(position).get("ID").toString();
								Variable.Session.USERCARNAME = listData
										.get(position).get("NAME").toString();
								setDefaultCar(Integer
										.valueOf(Variable.Session.USERCARID));
								notifyDataSetChanged();
							}
						});
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			if (Variable.Session.USERCARID.equals(listData.get(position)
					.get("ID").toString())) {
				holder.image.setBackgroundResource(R.drawable.car_select_bg);
			} else {
				holder.image.setBackgroundResource(R.drawable.car_icon);
			}
			holder.title.setText((String) listData.get(position).get("NAME"));
			holder.text.setText((String) listData.get(position).get("CARNO"));
			return convertView;
		}
	}

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
					// showDialog(getString(R.string.save_city_car_success_str));
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

	private void setDefaultCar(int carId) {
		DefaultCarRequest request = new DefaultCarRequest();
		request.saveDefaultCarMessage(saveDataHandler, carId + "",
				Variable.Session.CITY);
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
		// 进行页面跳转
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		HashMap<String, Object> map = (HashMap<String, Object>) listData
				.get(position);
		bundle.putSerializable("data", map);
		intent.putExtras(bundle);
		intent.setClass(this, CarInfoDetailActivity.class);
		startActivity(intent);
	}

}
