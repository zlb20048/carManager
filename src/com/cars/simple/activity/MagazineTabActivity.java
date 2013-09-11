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
public class MagazineTabActivity extends BaseActivity {

	private final static String TAG = MagazineTabActivity.class.getSimpleName();

	/**
	 * 管理TAB类
	 */
	private LocalActivityManager mLocalActivityManager;

	/**
	 * tab项
	 */
	private TabHost tabs;

	/**
	 * 标签1
	 */
	private final static String TAG_1 = "tag_1";

	/**
	 * 标签2
	 */
	private final static String TAG_2 = "tag_2";

	/**
	 * 标签3
	 */
	private final static String TAG_3 = "tag_3";

	/**
	 * 标签4
	 */
	private final static String TAG_4 = "tag_4";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cars.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_tab_activity);
		showTop(getString(R.string.cars_home_5_str), null);
		mLocalActivityManager = new LocalActivityManager(this, false);
		tabs = (TabHost) findViewById(R.id.tabhost);
		mLocalActivityManager.dispatchCreate(savedInstanceState);
		tabs.setup(mLocalActivityManager);
		RelativeLayout articleTab = (RelativeLayout) LayoutInflater.from(this)
				.inflate(R.layout.message_tab, null);
		TextView image_button = (TextView) articleTab.findViewById(R.id.tab);
		image_button.setBackgroundResource(R.drawable.tab_left_selector);
		image_button.setText(getString(R.string.magazine_tab_1_title_str));
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putInt("type", 104);
		bundle.putString("title", getString(R.string.magazine_tab_1_title_str));
		intent.putExtras(bundle);
		intent.setClass(this, MagazineActivity.class);
		tabs.addTab(tabs.newTabSpec(TAG_1).setIndicator(articleTab)
				.setContent(intent));
		articleTab = (RelativeLayout) LayoutInflater.from(this).inflate(
				R.layout.message_tab, null);
		image_button = (TextView) articleTab.findViewById(R.id.tab);
		image_button.setBackgroundResource(R.drawable.tab_middle_selector);
		intent = new Intent();
		bundle = new Bundle();
		bundle.putInt("type", 105);
		bundle.putString("title", getString(R.string.magazine_tab_2_title_str));
		image_button.setText(getString(R.string.magazine_tab_2_title_str));
		intent.putExtras(bundle);
		intent.setClass(this, MagazineActivity.class);
		tabs.addTab(tabs.newTabSpec(TAG_2).setIndicator(articleTab)
				.setContent(intent));
		articleTab = (RelativeLayout) LayoutInflater.from(this).inflate(
				R.layout.message_tab, null);
		image_button = (TextView) articleTab.findViewById(R.id.tab);
		image_button.setBackgroundResource(R.drawable.tab_middle_selector);
		intent = new Intent();
		bundle = new Bundle();
		bundle.putInt("type", 106);
		bundle.putString("title", getString(R.string.magazine_tab_3_title_str));
		image_button.setText(getString(R.string.magazine_tab_3_title_str));
		intent.putExtras(bundle);
		intent.setClass(this, MagazineActivity.class);
		tabs.addTab(tabs.newTabSpec(TAG_3).setIndicator(articleTab)
				.setContent(intent));
		articleTab = (RelativeLayout) LayoutInflater.from(this).inflate(
				R.layout.message_tab, null);
		image_button = (TextView) articleTab.findViewById(R.id.tab);
		image_button.setBackgroundResource(R.drawable.tab_right_selector);
		intent = new Intent();
		bundle = new Bundle();
		bundle.putInt("type", 107);
		bundle.putString("title", getString(R.string.magazine_tab_4_title_str));
		image_button.setText(getString(R.string.magazine_tab_4_title_str));
		intent.putExtras(bundle);
		intent.setClass(this, MagazineActivity.class);
		tabs.addTab(tabs.newTabSpec(TAG_4).setIndicator(articleTab)
				.setContent(intent));
	}
}
