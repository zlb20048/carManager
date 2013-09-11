package com.cars.simple.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.cars.simple.R;

public class MaintenanceShopDetailActivity extends BaseActivity {

	/**
	 * TAG
	 */
	private final static String TAG = MaintenanceShopDetailActivity.class
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
	 * 推荐商户列表
	 */
	private ListView listview = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maintenance_spe_detail_activity);
		Bundle bundle = getIntent().getExtras();
		String title = bundle.getString("title");
		showTop(title, null);

		listview = (ListView) findViewById(R.id.list);

		initData((List<Map<String, Object>>) bundle.getSerializable("data"));

		adapter = new SimpleAdapter(this, listData,
				R.layout.maintenance_detail_item_1, new String[] { "NAME" },
				new int[] { R.id.title });
		listview.setAdapter(adapter);
	}

	/**
	 * 初始化数据
	 * 
	 * @param serializable
	 */
	private void initData(List<Map<String, Object>> serializable) {
		// TODO Auto-generated method stub
		Map<String, Object> dataMap = serializable.get(0);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("NAME", "名称：" + dataMap.get("NAME"));
		listData.add(map);
		// map = new HashMap<String, Object>();
		// map.put("NAME", "价格：" + dataMap.get("PRICE"));
		// listData.add(map);
		map = new HashMap<String, Object>();
		map.put("NAME", "电话：" + dataMap.get("TEL"));
		listData.add(map);
		map = new HashMap<String, Object>();
		map.put("NAME", "地址：" + dataMap.get("ADDRESS"));
		listData.add(map);
	}
}
