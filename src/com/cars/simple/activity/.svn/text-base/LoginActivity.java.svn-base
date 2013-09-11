/**
 * 
 */
package com.cars.simple.activity;

import java.util.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.cars.simple.R;
import com.cars.simple.fusion.FusionCode;
import com.cars.simple.fusion.Variable;
import com.cars.simple.logic.LoginRequest;
import com.cars.simple.mode.ActivityRebackInfo;
import com.cars.simple.util.ResponsePaseUtil;

/**
 * @author liuzixiang
 * 
 */
public class LoginActivity extends BaseActivity implements OnClickListener {

	/**
	 * 标记
	 */
	private final static String TAG = LoginActivity.class.getSimpleName();

	/**
	 * 登录提交按钮
	 */
	private Button submit_btn = null;

	/**
	 * 注册按钮
	 */
	private ImageButton regestor_btn = null;

	/**
	 * 忘记密码按钮
	 */
	private ImageButton psw_btn = null;

	/**
	 * 帐号
	 */
	private EditText accoutEdit = null;

	/**
	 * 密码
	 */
	private EditText passwordEdit = null;

	/**
	 * 帐号
	 */
	private String account;

	/**
	 * 密码
	 */
	private String password;

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
					Map<String, Object> objMap = (Map<String, Object>) map
							.get("obj");
					Variable.Session.USER_NAME = (String) objMap
							.get("USERNAME");
					Variable.Session.MODE_ID = String.valueOf(objMap
							.get("CARSMODELID"));
					Variable.Session.EMAIL = objMap.get("EMAIL") + "";
					Variable.Session.USER_ID = (String) objMap.get("USER_ID");
					Variable.Session.PK_ID = objMap.get("PK_ID") + "";
					Variable.Session.USERCARID = (String) objMap
							.get("USERCARID");
					Variable.Session.LAST = (String) objMap
                            .get("LAST");
					Variable.Session.CITY = (String) objMap.get("CITY");
					Variable.Session.USERCARNAME = (String) objMap
							.get("USERCARNAME");
					Variable.Session.IS_LOGIN = true;

					// 登录成功后，记住用户名和密码
					SharedPreferences.Editor spe = getSharedPreferences(
							Variable.LOGIN_DATA, Context.MODE_PRIVATE).edit();

					spe.putString(Variable.LOGIN_NAME, account);
					spe.putString(Variable.PWD_NAME, password);
					spe.commit();

					if (Variable.Session.classStack.isEmpty()) {
						// 跳转到主界面
						Intent intent = new Intent();
						intent.setClass(LoginActivity.this, HomeAcitivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						startActivity(intent);
						finish();
					} else {
						ActivityRebackInfo info = Variable.Session.classStack
								.pop();
						Intent intent = new Intent();
						intent.setClass(LoginActivity.this, info.getBackClass());
						Bundle bundle = info.getBackClassBundle();
						if (null != bundle) {
							intent.putExtras(info.getBackClassBundle());
						}
						startActivity(intent);
						finish();
					}

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
						if (Variable.Session.classStack.isEmpty()) {

							// 跳转到主界面
							Intent intent = new Intent();
							intent.setClass(LoginActivity.this,
									HomeAcitivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
							startActivity(intent);
							finish();
						} else {
							ActivityRebackInfo info = Variable.Session.classStack
									.pop();
							Intent intent = new Intent();
							intent.setClass(LoginActivity.this,
									info.getBackClass());
							Bundle bundle = info.getBackClassBundle();
							if (null != bundle) {
								intent.putExtras(info.getBackClassBundle());
							}
							startActivity(intent);
							finish();
						}
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
		setContentView(R.layout.login_acitivity);

		showTop(getString(R.string.setting_title),
				getString(R.string.login_user_login));

		submit_btn = (Button) findViewById(R.id.submit);
		regestor_btn = (ImageButton) findViewById(R.id.regestor_btn);
		psw_btn = (ImageButton) findViewById(R.id.password_btn);

		SharedPreferences sp = getSharedPreferences(Variable.LOGIN_DATA,
				Context.MODE_PRIVATE);
		String account = sp.getString(Variable.LOGIN_NAME, "");
		String password = sp.getString(Variable.PWD_NAME, "");

		accoutEdit = (EditText) findViewById(R.id.accoutEdit);
		passwordEdit = (EditText) findViewById(R.id.passwordEdit);

		accoutEdit.setText(account);
		passwordEdit.setText(password);

		submit_btn.setOnClickListener(this);
		regestor_btn.setOnClickListener(this);
		psw_btn.setOnClickListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.submit:
			if (loginCheck()) {
				login();
			}
			break;
		case R.id.regestor_btn:
			// 跳转到注册界面
			Intent intent = new Intent();
			intent.setClass(this, RegisterActivity.class);
			startActivity(intent);
			break;
		case R.id.password_btn:
			// 跳转到忘记密码界面
			intent = new Intent();
			intent.setClass(this, ResetPswActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	/**
	 * 登录
	 */
	private void login() {
		showNetDialog(R.string.tips_str, R.string.net_work_request_str);
		LoginRequest request = new LoginRequest();
		request.login(account, password, handler);
	}

	/**
	 * 登录检测
	 */
	private boolean loginCheck() {
		account = accoutEdit.getText().toString();
		password = passwordEdit.getText().toString();

		if (account == null || account.trim().equals("")) {
			showDialog(getString(R.string.login_account_not_null));
			return false;
		}

		if (password == null || password.trim().length() < 6) {
			showDialog(getString(R.string.login_password_not_null));
			return false;
		}

		return true;
	}
}
