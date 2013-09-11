/**
 * 
 */
package com.cars.simple.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.cars.simple.R;
import com.cars.simple.fusion.FusionCode;
import com.cars.simple.logic.AccountRequest;
import com.cars.simple.mode.ViewHolder;
import com.cars.simple.util.ResponsePaseUtil;

/**
 * @author liuzixiang
 * 
 */
public class AccountDetailActivity extends BaseActivity implements
		OnClickListener {

	/**
	 * 标记
	 */
	private final static String TAG = AccountDetailActivity.class
			.getSimpleName();

	/**
	 * 列表
	 */
	private ListView listview = null;

	/**
	 * adapter
	 */
	private MyAdapter adapter = null;

	/**
	 * 数据
	 */
	private List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();

	/**
	 * 选中的选项
	 */
	private Map<Integer, Object> selectMap = new HashMap<Integer, Object>();

	/**
	 * 提交按钮
	 */
	private Button submit_btn = null;

	/**
	 * 明细列表
	 */
	private List<Map<String, Object>> baselist = new ArrayList<Map<String, Object>>();

	/**
	 * 类别Handler
	 */
	private Handler typeHandler = new Handler() {

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
					listData = (List<Map<String, Object>>) map.get("objlist");
					for (Map<String, Object> m : baselist) {
						for (int i = 0; i < listData.size(); i++) {
							Map<String, Object> mm = listData.get(i);
							if (mm.get("ID").equals(m.get("BASEINFOID"))) {
								listData.get(i).put("text", m.get("VALUE"));
								selectMap.put(i, listData.get(i));
							}
						}
					}
					adapter.notifyDataSetChanged();
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
	 * @see com.cars.simple.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_detail_activity);

		showTop(getString(R.string.account_detail_title_str), null);

		submit_btn = (Button) findViewById(R.id.submit);

		submit_btn.setOnClickListener(this);

		listview = (ListView) findViewById(R.id.list);

		adapter = new MyAdapter(this);
		listview.setAdapter(adapter);

		Bundle bundle = getIntent().getExtras();

		String type = bundle.getString("type");

		baselist = (List<Map<String, Object>>) bundle
				.getSerializable("baselist");

		getAccountBaseInfo(type);

	}

	/**
	 * 获取Account基本信息
	 * 
	 * @param string
	 *            类别
	 */
	private void getAccountBaseInfo(String string) {
		showNetDialog(R.string.tips_str, R.string.net_work_request_str);
		AccountRequest request = new AccountRequest();
		request.getAccountBaseInfo(typeHandler, string);
	}

	public class MyAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		public MyAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return listData.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return listData.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder holder = null;
			// if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.account_detail_list_item,
					null);
			holder.edittext = (EditText) convertView
					.findViewById(R.id.edit_text);
			holder.edittext.setTag(position);
			holder.title = (TextView) convertView.findViewById(R.id.text_item);
			holder.checkbox = (CheckBox) convertView
					.findViewById(R.id.check_box);

			holder.edittext.addTextChangedListener(new TextWatcher() {

				@Override
				public void afterTextChanged(Editable s) {

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {

				}

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					// TODO Auto-generated method stub
					listData.get(position).put("text", s);
				}

			});

			convertView.setTag(holder);
			// } else {
			// holder = (ViewHolder) convertView.getTag();
			// }

			holder.title.setText((String) listData.get(position).get("NAME"));

			holder.checkbox.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					if (selectMap.get(position) != null) {
						selectMap.remove(position);
					} else {
						selectMap.put(position, listData.get(position));
					}
				}
			});

			Object obj = listData.get(position).get("text");

			if (null != obj) {
				holder.edittext.setText(obj.toString());
			}

			if (selectMap.get(position) != null) {
				holder.checkbox.setChecked(true);
			} else {
				holder.checkbox.setChecked(false);
			}
			return convertView;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		List<Map<String, Object>> backData = new ArrayList<Map<String, Object>>();
		Iterator iterator = selectMap.keySet().iterator();
		while (iterator.hasNext()) {
			Integer key = (Integer) iterator.next();
			Log.v(TAG, "key = " + key);
			Map<String, Object> map = listData.get(key);
			Log.v(TAG, "money = " + map.get("text"));
			backData.add(map);
		}
		bundle.putSerializable("data", (Serializable) backData);
		intent.putExtras(bundle);
		setResult(1, intent);
		finish();
	}
}
