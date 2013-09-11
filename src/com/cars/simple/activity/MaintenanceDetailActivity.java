package com.cars.simple.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
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
import android.widget.TextView;

import com.cars.simple.R;
import com.cars.simple.fusion.FusionCode;
import com.cars.simple.logic.MaintenanceRequest;
import com.cars.simple.util.ResponsePaseUtil;

public class MaintenanceDetailActivity extends BaseActivity implements
		OnItemClickListener {

	/**
	 * TAG
	 */
	private final static String TAG = MaintenanceDetailActivity.class
			.getSimpleName();

	/**
	 * 数据
	 */
	private List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();

	/**
	 * Adapter
	 */
	private SimpleAdapter adapter = null;

	/**
	 * id
	 */
	private int repair_id = 0;

	/**
	 * 列表信息
	 */
	private TextView titleTextView = null;

	/**
	 * 价格
	 */
	private TextView priceTextView = null;

	/**
	 * 列表
	 */
	private ListView childList = null;

	/**
	 * 推荐商户列表
	 */
	private ListView listview = null;

	/**
	 * 列表详细信息
	 */
	private SimpleAdapter childAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maintenance_detail_activity);
		showTop(getString(R.string.sus_shop_str), null);

		Bundle bundle = getIntent().getExtras();
		repair_id = bundle.getInt("repair_id");

		String title = bundle.getString("title");
		String price = bundle.getString("price");

		List<Map<String, Object>> list = (List<Map<String, Object>>) bundle
				.get("childList");

		titleTextView = (TextView) findViewById(R.id.title);
		priceTextView = (TextView) findViewById(R.id.price);

		titleTextView.setText(title);
		priceTextView.setText(price);

		childList = (ListView) findViewById(R.id.childList);
		listview = (ListView) findViewById(R.id.list);

		childAdapter = new SimpleAdapter(this, list,
				R.layout.maintenance_detail_item_2, new String[] { "NAME",
						"PRICE" }, new int[] { R.id.title, R.id.price });
		childList.setAdapter(childAdapter);

		adapter = new SimpleAdapter(this, listData,
				R.layout.maintenance_detail_item_2, new String[] { "NAME",
						"PRICE" }, new int[] { R.id.title, R.id.price });
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);

		getData();
	}

	/**
	 * 取得数据
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
				if (code > 0) {
					initData((List<Map<String, Object>>) map.get("objlist"));
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
	 * 初始化数据
	 * 
	 * @param list
	 */
	private void initData(List<Map<String, Object>> list) {
		for (Map<String, Object> map : list) {
			listData.add(map);
		}
		// setData(listData);
		adapter.notifyDataSetChanged();
	}

	private void setData(List<Map<String, Object>> list) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("NAME", "1111");
		map.put("ADDRESS", "22222");
		map.put("TEL", "33333");
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("NAME", "1111");
		map.put("ADDRESS", "22222");
		map.put("TEL", "33333");
		list.add(map);
	}

	/**
	 * 初始化数据
	 */
	private void getData() {
		MaintenanceRequest request = new MaintenanceRequest();
		request.getSugess(handler, repair_id + "", page);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent();
		intent.setClass(this, MaintenanceShopDetailActivity.class);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list.add(listData.get(arg2));
		Bundle bundle = new Bundle();
		bundle.putSerializable("data", (Serializable) list);
		bundle.putString("title", getString(R.string.sugesst_shop_str));
		intent.putExtras(bundle);
		startActivity(intent);
	}
}
