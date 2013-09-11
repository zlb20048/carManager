/**
 * 
 */
package com.cars.simple.activity;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import com.cars.simple.R;
import com.cars.simple.fusion.Variable;
import com.cars.simple.util.Util;

/**
 * @author liuzixiang
 * 
 */
public class WebViewActivity extends BaseActivity {

	/**
	 * TAG
	 */
	private final static String TAG = WebViewActivity.class.getSimpleName();

	/**
	 * Webview
	 */
	private WebView webview = null;

	/**
	 * 标题
	 */
	private TextView titleTextview = null;

	/**
	 * 时间
	 */
	private TextView timeTextview = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cars.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview_activity);

		webview = (WebView) findViewById(R.id.webview);

		titleTextview = (TextView) findViewById(R.id.webTitle);
		timeTextview = (TextView) findViewById(R.id.des);

		Bundle bundle = getIntent().getExtras();

		String title = bundle.getString("title");
		String time = bundle.getString("time");

		String topTitle = bundle.getString("topTitle");

		titleTextview.setText(title);
		timeTextview.setText(Util.formatTime(time));

		showTop(topTitle, null);

		String content = bundle.getString("content");

		webview.loadDataWithBaseURL(Variable.SERVER_IMAGE_URL, content,
				"text/html", "utf-8", null);
	}
}
