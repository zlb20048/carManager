/**
 * 
 */
package com.cars.simple.activity;

import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
public class RegisterActivity extends BaseActivity implements OnClickListener {

	private final static String TAG = RegisterActivity.class.getSimpleName();

	/**
	 * 邮箱帐号
	 */
	private EditText accountEdit = null;

	/**
	 * 用户昵称
	 */
	private EditText nickEdit = null;

	/**
	 * 密码
	 */
	private EditText pswEdit = null;

	/**
	 * 确认密码
	 */
	private EditText repswEdit = null;

	/**
	 * 提交按钮
	 */
	private Button submit = null;

	/**
	 * 帐号
	 */
	private String account;

	/**
	 * 昵称
	 */
	private String nickname;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 确认密码
	 */
	private String repassword;

	/**
	 * 回调数据
	 */
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
					showSelfDialog(getString(R.string.register_success));
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
	 * 显示弹出Dialog提示框
	 * 
	 * @param message
	 *            提示信息
	 */
	protected void showSelfDialog(String message) {
		AlertDialog dialog = new AlertDialog.Builder(this).create();
		dialog.setTitle(getString(R.string.tip_str));
		dialog.setMessage(message);
		dialog.setButton(getString(R.string.sure_str),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				});
		dialog.show();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cars.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_activity);

		showTop(getString(R.string.register_title_str), null);

		initView();
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		accountEdit = (EditText) findViewById(R.id.accoutEdit);
		nickEdit = (EditText) findViewById(R.id.nicknameEdit);
		pswEdit = (EditText) findViewById(R.id.passwordEdit);
		repswEdit = (EditText) findViewById(R.id.repasswordEdit);

		submit = (Button) findViewById(R.id.register_btn);
		submit.setOnClickListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		if (checkEdit()) {
			register();
		}
	}

	/**
	 * 发送注册请求
	 */
	private void register() {
		showNetDialog(R.string.tips_str, R.string.net_work_request_str);
		RegisterRequest request = new RegisterRequest();
		request.register(account, nickname, password, handler);
	}

	/**
	 * 检测各个部件
	 * 
	 * @return
	 */
	private boolean checkEdit() {
		boolean isCheckAlready = false;
		account = accountEdit.getText().toString();
		// 验证帐号是否正确
		if (!isEmail(account)) {
			showDialog(getString(R.string.register_check_account));
			return isCheckAlready;
		}

		nickname = nickEdit.getText().toString();
		// 验证昵称
		if (null == nickname || "".equals(nickname)) {
			showDialog(getString(R.string.register_check_nick));
			return isCheckAlready;
		}

		password = pswEdit.getText().toString();
		// 验证密码长度
		if (null == password || password.length() < 6) {
			showDialog(getString(R.string.login_password_not_null));
			return isCheckAlready;
		}

		repassword = repswEdit.getText().toString();
		// 验证确认密码长度
		if (null == repassword || repassword.length() < 6) {
			showDialog(getString(R.string.login_password_not_null));
			return isCheckAlready;
		}

		// 验证两次输入的密码是否一致
		if (!password.equals(repassword)) {
			showDialog(getString(R.string.register_check_p_to_rep));
			return isCheckAlready;
		}

		isCheckAlready = true;

		return isCheckAlready;
	}

	/**
	 * 验证是否为邮箱用户
	 * 
	 * @param email
	 *            邮箱地址
	 * @return
	 */
	private boolean isEmail(String email) {
		boolean isEmail = false;
		if (null != email) {
			isEmail = email.contains("@");
		}
		return isEmail;
	}
}
