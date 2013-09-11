/**
 * 
 */
package com.cars.simple.activity;

import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.Spinner;

import com.cars.simple.R;
import com.cars.simple.fusion.FusionCode;
import com.cars.simple.logic.DiscussRequest;
import com.cars.simple.util.ResponsePaseUtil;

/**
 * @author liuzixiang
 * 
 */
public class DiscussActivity extends BaseActivity implements OnClickListener,
		OnItemSelectedListener, OnRatingBarChangeListener {

	/**
	 * 标记
	 */
	private final static String TAG = DiscussActivity.class.getSimpleName();

	/**
	 * Spinner
	 */
	private Spinner spinner = null;

	/**
	 * 自动填写功能
	 */
	private AutoCompleteTextView autoCompleteTextView = null;

	/**
	 * 价格
	 */
	private RatingBar priceRating = null;

	/**
	 * 服务
	 */
	private RatingBar serviceRating = null;

	/**
	 * 环境
	 */
	private RatingBar milieuRating = null;

	/**
	 * 内容
	 */
	private EditText content_edit = null;

	/**
	 * 提交按钮
	 */
	private Button submit_btn = null;

	/**
	 * 取消按钮
	 */
	private Button cancle_btn = null;

	/**
	 * 商户id
	 */
	private String bussinessid = "";

	/**
	 * 类别类型
	 */
	private List<Map<String, Object>> typeList = null;

	/**
	 * 商家类别
	 */
	private ArrayAdapter<String> adapter = null;

	/**
	 * 价格
	 */
	private String price = "";
	/**
	 * 环境
	 */
	private String milieu = "";
	/**
	 * 服务
	 */
	private String service = "";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cars.simple.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.discuss_activity);
		showTop(getString(R.string.discuss_submit_str), null);

		spinner = (Spinner) findViewById(R.id.spinner);

		autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteText);

		priceRating = (RatingBar) findViewById(R.id.ratingBar2);
		serviceRating = (RatingBar) findViewById(R.id.ratingBar3);
		milieuRating = (RatingBar) findViewById(R.id.ratingBar4);

		priceRating.setOnRatingBarChangeListener(this);
		serviceRating.setOnRatingBarChangeListener(this);
		milieuRating.setOnRatingBarChangeListener(this);

		content_edit = (EditText) findViewById(R.id.edit_talk);

		submit_btn = (Button) findViewById(R.id.submit_btn);
		cancle_btn = (Button) findViewById(R.id.cancel_btn);

		submit_btn.setOnClickListener(this);
		cancle_btn.setOnClickListener(this);

		getType();
	}

	/**
	 * 获取商家类别
	 */
	private void getType() {
		showNetDialog(R.string.tips_str, R.string.net_work_request_str);
		DiscussRequest request = new DiscussRequest();
		request.getBussinessId(typeXHandler, "", -2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.submit_btn:
			submit();
			break;
		case R.id.cancel_btn:
			finish();
			break;
		}
	}

	/**
	 * 获取类别
	 */
	private Handler typeXHandler = new Handler() {

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
					typeList = (List<Map<String, Object>>) map.get("objlist");
					initAdapter();
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
	 * 初始化系列
	 */
	private void initAdapter() {
		int size = typeList.size();
		String[] str = new String[size];
		adapter = new ArrayAdapter<String>(this, R.layout.myspinner_item);// 设置样式
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 设置下拉样式
		// for (Map<String, Object> map : typeList) {
		// adapter.add((String) map.get("NAME"));
		// }
		for (int i = 0; i < size; i++) {
			Map<String, Object> map = typeList.get(i);
			String name = (String) map.get("NAME");
			adapter.add(name);
			str[i] = name;
		}
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);

		ArrayAdapter<String> av = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, str);
		autoCompleteTextView.setAdapter(av);
	}

	/**
	 * 回调方法
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
					showDialog1(getString(R.string.discuss_submit_success_str));
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
	 * 显示弹出Dialog提示框
	 * 
	 * @param message
	 *            提示信息
	 */
	protected void showDialog1(String message) {
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

	/**
	 * 提交
	 */
	private void submit() {
		showNetDialog(R.string.tips_str, R.string.net_work_request_str);
		DiscussRequest request = new DiscussRequest();
		String content = content_edit.getText().toString();
		price = priceRating.getRating() + "";
		milieu = milieuRating.getRating() + "";
		service = serviceRating.getRating() + "";
		String name = autoCompleteTextView.getText().toString();
		request.discussBussiness(handler, bussinessid, content, price, milieu,
				service, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemSelectedListener#onItemSelected(android
	 * .widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		bussinessid = typeList.get(position).get("BUSINESSESID") + "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemSelectedListener#onNothingSelected(android
	 * .widget.AdapterView)
	 */
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.RatingBar.OnRatingBarChangeListener#onRatingChanged(android
	 * .widget.RatingBar, float, boolean)
	 */
	@Override
	public void onRatingChanged(RatingBar ratingBar, float rating,
			boolean fromUser) {
		switch (ratingBar.getId()) {
		case R.id.ratingBar2:
			price = rating + "";
			break;
		case R.id.ratingBar3:
			service = rating + "";
			break;
		case R.id.ratingBar4:
			milieu = rating + "";
			break;
		}
	}
}
