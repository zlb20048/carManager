/**
 * 
 */
package com.cars.simple.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cars.simple.R;
import com.cars.simple.fusion.FusionCode;
import com.cars.simple.fusion.Variable;
import com.cars.simple.logic.CacheOpt;
import com.cars.simple.logic.MessaageRequeset;
import com.cars.simple.mode.ViewHolder;
import com.cars.simple.util.ResponsePaseUtil;

/**
 * @author liuzixiang
 * 
 */
public class MessageReadActivity extends BaseActivity implements
		OnClickListener, OnItemClickListener, OnScrollListener {

	/**
	 * TAG
	 */
	private final static String TAG = MessageReadActivity.class.getSimpleName();

	/**
	 * 当前的view
	 */
	private View view = null;

	/**
	 * 分页的按钮
	 */
	private Button footerButton = null;

	/**
	 * 正在加载转圈
	 */
	private LinearLayout footerProgressBarLayout = null;

	/**
	 * g滚动条状态
	 */
	private int scrollState;

	/**
	 * 数量
	 */
	private int count = 0;

	/**
	 * 最后的一个item
	 */
	private int lastItem;

	/**
	 * 可显示的数量
	 */
	private int visibleItemCount;

	/**
	 * 自定义Adapter
	 */
	private MyAdapter adapter = null;

	/**
	 * 获取当前页的数据大小
	 */
	private int currentPage = 1;

	/**
	 * 未读
	 */
	private int type = 0;

	/**
	 * listview 数据
	 */
	private List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();

	/**
	 * 选中的选项
	 */
	private Map<Integer, Object> selectMap = new HashMap<Integer, Object>();

	/**
	 * ListView
	 */
	private ListView listview = null;

	/**
	 * 全选按钮
	 */
	private Button selectAllImage = null;

	/**
	 * 取消全选
	 */
	private Button cancleAllImage = null;

	/**
	 * 删除
	 */
	private Button delAllImage = null;
	
	private String key = null;
	
	private Handler dbinfoHandler = new Handler() {

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
					CacheOpt db = new CacheOpt();
					db.update(key,(String)object,MessageReadActivity.this);
				} else if (code == -3) {
					skipLogin();
				} 
				break;
			case FusionCode.NETWORK_ERROR:
			case FusionCode.NETWORK_TIMEOUT_ERROR:
				
				break;
			}
		}
	};
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

				totalPage = (Integer) map.get("allpage");
				if (code > 0) {
					initData((List<Map<String, Object>>) map.get("objlist"));
					CacheOpt db = new CacheOpt();
					db.save(key,(String)object,MessageReadActivity.this);
				} else if (code == -3) {
					skipLogin();
				} else {
					showDialog((String) map.get("msg"));
				}

				if (totalPage > page) {
					footerButton.setVisibility(View.VISIBLE);
					footerProgressBarLayout.setVisibility(View.GONE);
				} else {
					footerButton.setVisibility(View.GONE);
					footerProgressBarLayout.setVisibility(View.GONE);
				}
				adapter.notifyDataSetChanged();
				break;
			case FusionCode.NETWORK_ERROR:
			case FusionCode.NETWORK_TIMEOUT_ERROR:
				showDialog(getString(R.string.net_error));
				break;
			}
		}
	};

	/**
	 * 展示更多
	 */
	private void showMore(ListView listview) {
		// Listview的一些东西
		LayoutInflater inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.list_foot_layout, null);
		footerButton = (Button) view.findViewById(R.id.button);
		footerProgressBarLayout = (LinearLayout) view
				.findViewById(R.id.linearlayout);
		footerProgressBarLayout.setVisibility(View.GONE);
		// adapter = new MyAdapter(this);
		footerButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (lastItem == adapter.getCount()
						&& scrollState == OnScrollListener.SCROLL_STATE_IDLE
						&& lastItem != 0) {
					page++;
					footerButton.setVisibility(View.GONE);
					footerProgressBarLayout.setVisibility(View.VISIBLE);
					// 发送请求
					getReadType(type);
				}
			}
		});
		listview.addFooterView(view);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cars.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_activity);

		Bundle bundle = getIntent().getExtras();
		type = bundle.getInt("type");

		showTop(getString(R.string.cars_home_9_str), null);

		listview = (ListView) findViewById(R.id.list);
		showMore(listview);
		adapter = new MyAdapter(this);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);
		listview.setOnScrollListener(this);

		selectAllImage = (Button) findViewById(R.id.selectAllBtn);
		cancleAllImage = (Button) findViewById(R.id.cancleAllBtn);
		delAllImage = (Button) findViewById(R.id.delAllBtn);

		selectAllImage.setOnClickListener(this);
		cancleAllImage.setOnClickListener(this);
		delAllImage.setOnClickListener(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		getReadType(type);
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
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.message_list_item,
						null);
				holder.time = (TextView) convertView.findViewById(R.id.time);
				holder.title = (TextView) convertView.findViewById(R.id.title);
				holder.text = (TextView) convertView.findViewById(R.id.content);
				holder.checkbox = (CheckBox) convertView
						.findViewById(R.id.checkBox);
				holder.readCheckBox = (CheckBox) convertView
						.findViewById(R.id.readStatusImage);
				holder.checkbox.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						if (selectMap.get(position) != null) {
							selectMap.remove(position);
						} else {
							selectMap.put(position, listData.get(position));
						}
					}
				});
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.title.setText((String) listData.get(position).get("TITLE"));
			holder.text.setText((String) listData.get(position).get(
					"MESSAGETEXT"));

			if ("1".equals(listData.get(position).get("READFLAG").toString())) {
				holder.readCheckBox.setChecked(true);
			} else {
				holder.readCheckBox.setChecked(false);
			}

			if (selectMap.get(position) != null) {
				holder.checkbox.setChecked(true);
			} else {
				holder.checkbox.setChecked(false);
			}

			return convertView;
		}
	}

	/**
	 * 获取未阅读的
	 * 
	 * @param type
	 *            0未读 1已读
	 */
	private void getReadType(int type) {
		key =  Variable.Session.USER_ID+","+Variable.SERVER_SOFT_URL
				+ "/message/getmessagelist.jspx?readtype=" + type
				+ "&pagesize=" + FusionCode.pageSize + "&page=" + page;
		CacheOpt db = new CacheOpt();
		String str =db.getValue(key,this);//读取数据库		
		if(str!=null) {
			Map<String, Object> map = ResponsePaseUtil.getInstance()
					.parseResponse((String) str);
			int code = (Integer) map.get("code");

			totalPage = (Integer) map.get("allpage");
			if (code > 0) {
				initData((List<Map<String, Object>>) map.get("objlist"));
			} else if (code == -3) {
				skipLogin();
			} else {
				showDialog((String) map.get("msg"));
			}

			if (totalPage > page) {
				footerButton.setVisibility(View.VISIBLE);
				footerProgressBarLayout.setVisibility(View.GONE);
			} else {
				footerButton.setVisibility(View.GONE);
				footerProgressBarLayout.setVisibility(View.GONE);
			}
			adapter.notifyDataSetChanged();
			
			MessaageRequeset request = new MessaageRequeset();
			request.getMessage(dbinfoHandler, type, FusionCode.pageSize, page);
		}else{
			showNetDialog(R.string.tips_str, R.string.net_work_request_str);
			MessaageRequeset request = new MessaageRequeset();
			request.getMessage(handler, type, FusionCode.pageSize, page);
		}
	}

	/**
	 * 初始化数据
	 */
	private void initData(List<Map<String, Object>> list) {
		for (Map<String, Object> map : list) {
			listData.add(map);
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		listData.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.selectAllBtn:
			setAllSelect();
			break;
		case R.id.delAllBtn:
			deleMessage();
			break;
		case R.id.cancleAllBtn:
			cancleAllSelect();
			break;
		default:
			break;
		}
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
					showDialog(getString(R.string.delete_success_str));
					adapter.notifyDataSetChanged();
				} else if (code == -3) {
					skipLogin();
				} else {
					showDialog((String) map.get("msg"));
				}
				adapter.notifyDataSetChanged();
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
		StringBuffer sb = new StringBuffer();
		for (Object o : selectMap.keySet()) {
			Map<String, Object> map = (Map<String, Object>) selectMap.get(o);
			sb.append(map.get("ID")).append(",");
			listData.remove(map);
		}
		selectMap.clear();
		if (sb.length() != 0) {
			showNetDialog(R.string.tips_str, R.string.net_work_request_str);
			MessaageRequeset request = new MessaageRequeset();
			request.deleMessage(deleteHandler, sb.toString());
		} else {
			showDialog(getString(R.string.message_del_tip));
		}

	}

	/**
	 * 设置所有的选项均选中
	 */
	private void setAllSelect() {
		for (int i = 0; i < listData.size(); i++) {
			selectMap.put(i, listData.get(i));
		}
		adapter.notifyDataSetChanged();
	}

	/**
	 * 取消所有
	 */
	private void cancleAllSelect() {
		selectMap.clear();
		adapter.notifyDataSetChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget
	 * .AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// 设置已读
		MessaageRequeset request = new MessaageRequeset();
		request.setReadAlreay(defaultHandler, (Integer) listData.get(position)
				.get("ID"));
		listData.get(position).put("READFLAG", "1");

		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString("title", listData.get(position).get("TITLE")
				.toString());
		bundle.putString("content", listData.get(position).get("MESSAGETEXT")
				.toString());
		bundle.putString("time", listData.get(position).get("SENDTIME")
				.toString());
		bundle.putString("id", listData.get(position).get("ID").toString());
		intent.putExtras(bundle);
		intent.setClass(this, MessageDetailActivity.class);
		startActivity(intent);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		this.visibleItemCount = visibleItemCount;
		lastItem = firstVisibleItem + visibleItemCount - 1;

		if (page == totalPage) {
			listview.removeFooterView(view);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AbsListView.OnScrollListener#onScrollStateChanged(android
	 * .widget.AbsListView, int)
	 */
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		this.scrollState = scrollState;
	}

}
