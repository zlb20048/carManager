/**
 * 
 */
package com.cars.simple.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.cars.simple.R;
import com.cars.simple.fusion.FusionCode;
import com.cars.simple.logic.DefaultCarRequest;
import com.cars.simple.util.ResponsePaseUtil;

/**
 * @author liuzixiang
 * 
 */
public class AccountChooseCarActivity extends BaseActivity implements
		OnItemClickListener {

	private final static String TAG = AccountChooseCarActivity.class
			.getSimpleName();

	/**
	 * List
	 */
	private ListView listview = null;

	/**
	 * 数据
	 */
	private List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();

	/**
	 * adapter
	 */
	private SimpleAdapter adapter = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cars.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_choose_car_activity);

		showTop(getString(R.string.account_add_cost_str), null);

		getDefaultCar();

		listview = (ListView) findViewById(R.id.list);

		adapter = new SimpleAdapter(this, listData, R.layout.car_type_item,
				new String[] { "NAME" }, new int[] { R.id.item });
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);
		listview.setOnItemClickListener(this);

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
	 * 初始化车辆名称
	 * 
	 * @param list
	 */
	private void initCarData(List<Map<String, Object>> list) {
		for (Map<String, Object> map : list) {
			listData.add(map);
		}
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
		intent.setClass(this, AccountRecordActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("usercarid", listData.get(position).get("ID") + "");
		intent.putExtras(bundle);
		startActivity(intent);
	}
}
