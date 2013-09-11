/**
 * 
 */
package com.cars.simple.activity;

import java.util.Map;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cars.simple.R;
import com.cars.simple.fusion.FusionCode;
import com.cars.simple.logic.MessaageRequeset;
import com.cars.simple.util.ResponsePaseUtil;
import com.cars.simple.util.Util;

/**
 * @author liuzixiang
 * 
 */
public class MessageDetailActivity extends BaseActivity {

	/**
	 * 标记
	 */
	private final static String TAG = MessageDetailActivity.class
			.getSimpleName();

	/**
	 * 标题
	 */
	private TextView titleTextView = null;

	/**
	 * 时间
	 */
	private TextView timeTextView = null;

	/**
	 * 内容
	 */
	private TextView contentTextView = null;

	/**
	 * 删除按钮
	 */
	private Button delete_btn = null;

	/**
	 * id
	 */
	private String id = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cars.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_detail_activity);

		showTop(getString(R.string.message_tip_str), null);

		Bundle bundle = getIntent().getExtras();

		String title = bundle.getString("title");
		String content = bundle.getString("content");
		String time = bundle.getString("time");
		id = bundle.getString("id");
		titleTextView = (TextView) findViewById(R.id.titleText);
		contentTextView = (TextView) findViewById(R.id.contentText);
		timeTextView = (TextView) findViewById(R.id.timeText);
		delete_btn = (Button) findViewById(R.id.delAllBtn);
		
		delete_btn.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				deleMessage();
			}
		});
		
		titleTextView.setText(title);
		contentTextView.setText(content);
		timeTextView.setText(Util.formatTime(time));

	}

	/**
	 * 删除
	 */
	private Handler deleteHandler = new Handler() {

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
					showDialogfinish(getString(R.string.delete_success_str));
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
	 * 删除信息
	 */
	private void deleMessage() {
		showNetDialog(R.string.tips_str, R.string.net_work_request_str);
		MessaageRequeset request = new MessaageRequeset();
		request.deleMessage(deleteHandler, id);
	}
}
