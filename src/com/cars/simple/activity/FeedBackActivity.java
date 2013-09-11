/**
 * 
 */
package com.cars.simple.activity;

import java.util.Map;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;

import com.cars.simple.R;
import com.cars.simple.fusion.FusionCode;
import com.cars.simple.logic.FeedBackRequest;
import com.cars.simple.util.ResponsePaseUtil;

/**
 * @author liuzixiang
 * 
 */
public class FeedBackActivity extends BaseActivity implements OnClickListener {

	private final static String TAG = FeedBackActivity.class.getSimpleName();

	/**
	 * 意见反馈文本
	 */
	private EditText feedbackEdit = null;

	/**
	 * 意见反馈提交按钮
	 */
	private ImageButton submit_btn = null;

	/**
	 * 意见反馈内容
	 */
	private String content;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			closeNetDialog();
			int what = msg.what;
			Object object = msg.obj;
			switch (what) {
			case FusionCode.RETURN_JSONOBJECT:
				// JSONObject json = (JSONObject) object;
				// if (null != json) {
				// try {
				// int code = json.getInt("code");
				// String tips = json.getString("msg");
				// if (code > 0) {
				// showDialog(R.string.feed_back_submit_success_str);
				// } else {
				// showDialog(tips);
				// }
				// } catch (JSONException e) {
				// e.printStackTrace();
				// }
				// }
				Map<String, Object> map = ResponsePaseUtil.getInstance()
						.parseResponse((String) object);
				int code = (Integer) map.get("code");
				if (code > 0) {
					showDialogfinish(getString(R.string.feed_back_submit_success_str));
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cars.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feed_back_activity);
		showTop(getString(R.string.feed_back_title_str), null);

		feedbackEdit = (EditText) findViewById(R.id.feedbackEdit);

		submit_btn = (ImageButton) findViewById(R.id.submit_btn);

		submit_btn.setOnClickListener(this);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		if (checkContent()) {
			feedback();
		}
	}

	/**
	 * 发送意见反馈
	 */
	private void feedback() {
		showNetDialog(R.string.tips_str, R.string.net_work_request_str);
		FeedBackRequest request = new FeedBackRequest();
		request.feedBack(content, handler);
	}

	private boolean checkContent() {
		content = feedbackEdit.getText().toString();
		if (null == content || "".equals(content.trim())) {
			showDialog(getString(R.string.feed_back_check_content_str));
			return false;
		}
		return true;
	}

}
