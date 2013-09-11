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
import android.widget.Button;
import android.widget.EditText;

import com.cars.simple.R;
import com.cars.simple.fusion.FusionCode;
import com.cars.simple.logic.RegisterRequest;
import com.cars.simple.util.ResponsePaseUtil;

/**
 * @author liuzixiang
 * 
 */
public class ResetPswActivity extends BaseActivity implements OnClickListener {

	private final static String TAG = ResetPswActivity.class.getSimpleName();

	/**
	 * 忘记密码按钮
	 */
	public Button register_btn = null;

	/**
	 * 重置密码
	 */
	public EditText resetPswEdit = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cars.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reset_psw_activity);

		showTop(getString(R.string.reset_psw_str), null);

		register_btn = (Button) findViewById(R.id.register_btn);
		resetPswEdit = (EditText) findViewById(R.id.resetPswEdit);
		register_btn.setOnClickListener(this);

	}

	private Handler handler = new Handler() {

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
					showDialog(getString(R.string.reset_psw_success_str));
				} else if (code == -3) {

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
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		String email = resetPswEdit.getText().toString();
		if (checkEmail(email)) {
			showNetDialog(R.string.tips_str, R.string.net_work_request_str);
			RegisterRequest request = new RegisterRequest();
			request.resetPassword(handler, email);
		}
	}

	/**
	 * 检测邮箱地址
	 * 
	 * @param email
	 *            邮箱地址
	 */
	private boolean checkEmail(String email) {
		if (!email.contains("@")) {
			showDialog(getString(R.string.reset_account_error_str));
			return false;
		}
		return true;
	}
}
