/**
 * 
 */
package com.cars.simple.activity;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.cars.simple.R;

/**
 * @author liuzixiang
 * 
 */
public class AccountTabActivity extends BaseActivity {

	private final static String TAG = AccountTabActivity.class.getSimpleName();

	/**
	 * 管理TAB类
	 */
	private LocalActivityManager mLocalActivityManager;

	/**
	 * tab项
	 */
	private TabHost tabs;

	/**
	 * 没有阅读标签
	 */
	private final static String NOT_READ_TAG = "not_read";

	/**
	 * 已读标记
	 */
	private final static String READ_TAG = "read_tag";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cars.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_tab_activity);

		showTitle();

		mLocalActivityManager = new LocalActivityManager(this, false);
		tabs = (TabHost) findViewById(R.id.tabhost);
		mLocalActivityManager.dispatchCreate(savedInstanceState);
		tabs.setup(mLocalActivityManager);
		RelativeLayout articleTab = (RelativeLayout) LayoutInflater.from(this)
				.inflate(R.layout.message_left_tab, null);
		TextView textTitle = (TextView) articleTab.findViewById(R.id.tab);
		textTitle.setText(getString(R.string.account_total_str));
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putInt("type", 0);
		intent.putExtras(bundle);
		intent.setClass(this, AccountTotalActivity.class);
		tabs.addTab(tabs.newTabSpec(NOT_READ_TAG).setIndicator(articleTab)
				.setContent(intent));
		articleTab = (RelativeLayout) LayoutInflater.from(this).inflate(
				R.layout.message_right_tab, null);
		textTitle = (TextView) articleTab.findViewById(R.id.tab);
		textTitle.setText(getString(R.string.account_water_str));
		intent = new Intent();
		bundle = new Bundle();
		bundle.putInt("type", 1);
		intent.putExtras(bundle);
		intent.setClass(this, AccountWaterActivity.class);
		tabs.addTab(tabs.newTabSpec(READ_TAG).setIndicator(articleTab)
				.setContent(intent));

	}

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
		addBtn.setText(R.string.account_edit_str);
		addBtn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(AccountTabActivity.this,
						AccountRecordActivity.class);
				startActivity(intent);
			}
		});
		TextView top_title = (TextView) findViewById(R.id.top_title);
		top_title.setText(R.string.cars_home_2_str);
	}
}
