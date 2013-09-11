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
import com.cars.simple.logic.RegisterRequest;
import com.cars.simple.util.ResponsePaseUtil;

/**
 * @author liuzixiang
 * 
 */
public class ChangePwdActivity extends BaseActivity implements OnClickListener {

	/**
	 * TAG
	 */
	private final static String TAG = ChangePwdActivity.class.getSimpleName();

	/**
	 * 原始密码
	 */
	private EditText old_password_edit_text = null;

	/**
	 * 新密码
	 */
	private EditText new_password_edit_text = null;

	/**
	 * 确认新密码
	 */
	private EditText sure_new_password_edit_text = null;

	/**
	 * 提交按钮
	 */
	private ImageButton submit_btn = null;

	/**
	 * 原始密码
	 */
	private String oldpassword = "";

	/**
	 * 新密码
	 */
	private String newpassword = "";

	/**
	 * 确认新密码
	 */
	private String surepassword = "";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cars.simple.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_pwd_activity);
		showTop(getString(R.string.change_pwd_title_str), null); 

		old_password_edit_text = (EditText) findViewById(R.id.oldpasswordEdit);
		new_password_edit_text = (EditText) findViewById(R.id.newpasswordEdit);
		sure_new_password_edit_text = (EditText) findViewById(R.id.surepasswordEdit);
		submit_btn = (ImageButton) findViewById(R.id.submit);
		submit_btn.setOnClickListener(this);
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
					showDialogfinish(getString(R.string.change_pwd_sucess_str));
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
		oldpassword = old_password_edit_text.getText().toString();
		newpassword = new_password_edit_text.getText().toString();
		surepassword = sure_new_password_edit_text.getText().toString();
		if (checkData()) {
			showNetDialog(R.string.tips_str, R.string.net_work_request_str);
			RegisterRequest request = new RegisterRequest();
			request.changePassword(handler, oldpassword, newpassword);
		}
	}

	/**
	 * 检测数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		if ("".equals(oldpassword)) {
			showDialog(getString(R.string.change_pwd_check_old_str));
			return false;
		}
		if ("".equals(newpassword) || newpassword.length() < 8) {
			showDialog(getString(R.string.change_pwd_check_new_str));
			return false;
		}

		if ("".equals(surepassword) || surepassword.length() < 8) {
			showDialog(getString(R.string.change_pwd_check_sure_str));
			return false;
		}

		if (!surepassword.equals(newpassword)) {
			showDialog(getString(R.string.change_pwd_check_sure_and_new_str));
			return false;
		}

		return true;
	}
}
