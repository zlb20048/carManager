/**
 * 
 */
package com.cars.simple.activity;

import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.cars.simple.R;

/**
 * @author liuzixiang
 * 
 */
public class ViolationDetailActivity extends BaseActivity implements
		OnItemSelectedListener {

	/**
	 * 标记
	 */
	private final static String TAG = ViolationDetailActivity.class
			.getSimpleName();

	/**
	 * 下拉选择
	 */
	private Spinner spinner = null;

	/**
	 * 车牌号
	 */
	private TextView textCarNumTextView = null;

	/**
	 * 好牌种类
	 */
	private TextView textCarNumTypeTextView = null;

	/**
	 * 汽车颜色
	 */
	private TextView textCarColorTextView = null;

	/**
	 * 有效期
	 */
	private TextView textCarTimeTextView = null;

	/**
	 * 总罚款金额
	 */
	private TextView textAllMoneyTextView = null;

	/**
	 * 总扣分
	 */
	private TextView textAllMatchTextView = null;
	/**
	 * ListView
	 */
	private ListView listview = null;

	/**
	 * adapter
	 */
	private ArrayAdapter<String> adapter = null;

	/**
	 * 数据
	 */
	private List<Map<String, Object>> listData = null;

	/**
	 * 列表Adapter
	 */
	private SimpleAdapter listAdapter = null;

	/**
	 * 车牌号码
	 */
	private String carno = null;

	/**
	 * total
	 */
	private String total = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cars.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.violation_detail_activity);

		showTop(getString(R.string.violation_car_error_title), null);

		Bundle bundle = getIntent().getExtras();

		listData = (List<Map<String, Object>>) bundle.getSerializable("list");

		carno = bundle.getString("carno");
		total = bundle.getString("total");
		initview();

		initdata();

	}

	/**
	 * 初始化数据
	 */
	private void initdata() {
		initAdapter();
		initListAdapter();
		textCarNumTextView.setText(getString(R.string.violation_car_num_str)
				+ carno);
		textAllMoneyTextView
				.setText(getString(R.string.violation_car_total_str) + total);
	}

	/**
	 * 初始化Listview Adapter
	 */
	private void initListAdapter() {
		listAdapter = new SimpleAdapter(this, listData,
				R.layout.violation_detail_list_item, new String[] { "wzdd",
						"wzyy", "wzsj" }, new int[] { R.id.item_2, R.id.item_3,
						R.id.item_1 });
		listview.setAdapter(listAdapter);
	}

	/**
	 * 初始化界面
	 */
	private void initview() {
		spinner = (Spinner) findViewById(R.id.spinner);
		textCarNumTextView = (TextView) findViewById(R.id.textCarNumTextView);
		textCarNumTypeTextView = (TextView) findViewById(R.id.textCarNumTypeTextView);
		textCarColorTextView = (TextView) findViewById(R.id.textCarColorTextView);
		textCarTimeTextView = (TextView) findViewById(R.id.textCarTimeTextView);
		textAllMoneyTextView = (TextView) findViewById(R.id.textAllMoneyTextView);
		textAllMatchTextView = (TextView) findViewById(R.id.textAllMatchTextView);
		listview = (ListView) findViewById(R.id.list);
	}

	/**
	 * 初始化
	 */
	private void initAdapter() {
		adapter = new ArrayAdapter<String>(this, R.layout.myspinner_item);// 设置样式
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 设置下拉样式
		adapter.add(getString(R.string.violation_car_city_str));
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
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
}
