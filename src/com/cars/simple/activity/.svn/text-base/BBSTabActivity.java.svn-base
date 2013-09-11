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
public class BBSTabActivity extends BaseActivity {

	/**
	 * 管理TAB类
	 */
	private LocalActivityManager mLocalActivityManager;

	/**
	 * tab项
	 */
	private TabHost tabs;

	/**
	 * TAG_1
	 */
	private final static String TAG_1 = "TAG_1";

	/**
	 * TAG_2
	 */
	private final static String TAG_2 = "TAG_2";

	/**
	 * TAG_3
	 */
	private final static String TAG_3 = "TAG_3";

	/**
	 * TAG_4
	 */
	private final static String TAG_4 = "TAG_4";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cars.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_tab_activity);
		showTop(getString(R.string.magazine_title_str), null);
		mLocalActivityManager = new LocalActivityManager(this, false);
		tabs = (TabHost) findViewById(R.id.tabhost);
		mLocalActivityManager.dispatchCreate(savedInstanceState);
		tabs.setup(mLocalActivityManager);
		RelativeLayout articleTab = (RelativeLayout) LayoutInflater.from(this)
				.inflate(R.layout.message_tab, null);
		TextView image_button = (TextView) articleTab.findViewById(R.id.tab);
		image_button.setBackgroundResource(R.drawable.magazine_1_selector);
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putInt("type", 104);
		intent.putExtras(bundle);
		intent.setClass(this, MagazineActivity.class);
		tabs.addTab(tabs.newTabSpec(TAG_1).setIndicator(articleTab)
				.setContent(intent));
		articleTab = (RelativeLayout) LayoutInflater.from(this).inflate(
				R.layout.message_tab, null);
		image_button = (TextView) articleTab.findViewById(R.id.tab);
		image_button.setBackgroundResource(R.drawable.magazine_2_selector);
		intent = new Intent();
		bundle = new Bundle();
		bundle.putInt("type", 105);
		intent.putExtras(bundle);
		intent.setClass(this, MagazineActivity.class);
		tabs.addTab(tabs.newTabSpec(TAG_2).setIndicator(articleTab)
				.setContent(intent));
		articleTab = (RelativeLayout) LayoutInflater.from(this).inflate(
				R.layout.message_tab, null);
		image_button = (TextView) articleTab.findViewById(R.id.tab);
		image_button.setBackgroundResource(R.drawable.magazine_3_selector);
		intent = new Intent();
		bundle = new Bundle();
		bundle.putInt("type", 106);
		intent.putExtras(bundle);
		intent.setClass(this, MagazineActivity.class);
		tabs.addTab(tabs.newTabSpec(TAG_3).setIndicator(articleTab)
				.setContent(intent));
		articleTab = (RelativeLayout) LayoutInflater.from(this).inflate(
				R.layout.message_tab, null);
		image_button = (TextView) articleTab.findViewById(R.id.tab);
		image_button.setBackgroundResource(R.drawable.magazine_4_selector);
		intent = new Intent();
		bundle = new Bundle();
		bundle.putInt("type", 107);
		intent.putExtras(bundle);
		intent.setClass(this, MagazineActivity.class);
		tabs.addTab(tabs.newTabSpec(TAG_4).setIndicator(articleTab)
				.setContent(intent));
	}
}
