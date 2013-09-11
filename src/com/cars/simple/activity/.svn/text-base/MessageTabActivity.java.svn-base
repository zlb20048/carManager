/**
 * 
 */
package com.cars.simple.activity;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.cars.simple.R;

/**
 * @author liuzixiang
 * 
 */
public class MessageTabActivity extends BaseActivity {

	private final static String TAG = MessageTabActivity.class.getSimpleName();

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
		setContentView(R.layout.message_tab_activity);
		showTop(getString(R.string.message_tip_str), null);
		mLocalActivityManager = new LocalActivityManager(this, false);
		tabs = (TabHost) findViewById(R.id.tabhost);
		mLocalActivityManager.dispatchCreate(savedInstanceState);
		tabs.setup(mLocalActivityManager);
		RelativeLayout articleTab = (RelativeLayout) LayoutInflater.from(this)
				.inflate(R.layout.message_left_tab, null);
		TextView textTitle = (TextView) articleTab.findViewById(R.id.tab);
		textTitle.setText(getString(R.string.message_not_read_str));
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putInt("type", 0);
		intent.putExtras(bundle);
		intent.setClass(this, MessageReadActivity.class);
		tabs.addTab(tabs.newTabSpec(NOT_READ_TAG).setIndicator(articleTab)
				.setContent(intent));
		articleTab = (RelativeLayout) LayoutInflater.from(this).inflate(
				R.layout.message_right_tab, null);
		textTitle = (TextView) articleTab.findViewById(R.id.tab);
		textTitle.setText(getString(R.string.message_read_str));
		intent = new Intent();
		bundle = new Bundle();
		bundle.putInt("type", 1);
		intent.putExtras(bundle);
		intent.setClass(this, MessageReadActivity.class);
		tabs.addTab(tabs.newTabSpec(READ_TAG).setIndicator(articleTab)
				.setContent(intent));

	}
}
