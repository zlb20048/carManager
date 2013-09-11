/**
 * 
 */
package com.cars.simple.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.cars.simple.R;

import com.cars.simple.fusion.FusionCode;
import com.cars.simple.fusion.Variable;
import com.cars.simple.logic.CacheOpt;
import com.cars.simple.logic.PromotionRequest;
import com.cars.simple.mode.ViewHolder;
import com.cars.simple.service.baseData.RawTemplate;
import com.cars.simple.service.download.GetServerImageProcessor;
import com.cars.simple.service.download.ImageObj;
import com.cars.simple.util.ResponsePaseUtil;
import com.cars.simple.util.Util;

/**
 * @author liuzixiang
 * 
 */
public class PromotionListActivity extends BaseActivity implements
		OnItemClickListener {

	/**
	 * TAG
	 */
	private final static String TAG = PromotionListActivity.class
			.getSimpleName();

	/**
	 * 列表项
	 */
	private ListView listview = null;

	/**
	 * Adapter
	 */
	private SimpleAdapter adapter = null;

	/**
	 * 数据
	 */
	private List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();

	/**
	 * 当前页
	 */
	private int page = 1;

	/**
	 * id
	 */
	private int id = 0;

	private String key = null;
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cars.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.promotion_list_activity);

		Bundle bundle = getIntent().getExtras();

		id = bundle.getInt("id");
		String title = bundle.getString("title");

		showTop(title, null);

		listview = (ListView) findViewById(R.id.list);

		adapter = new SimpleAdapter(this, listData, R.layout.promotion_item,
				new String[] { "bitmap", "TITLE", "DES" }, new int[] {
						R.id.image, R.id.title, R.id.des });
		adapter.setViewBinder(new ViewHolder());
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(this);

		getPromotionData();
	}

	/**
	 * Handler
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
					initData((List<Map<String, Object>>) map.get("objlist"));
					CacheOpt db = new CacheOpt();
					db.save(key,(String)object,PromotionListActivity.this);
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
					db.update(key,(String)object,PromotionListActivity.this);
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
	 * 处理图片的Handler
	 */
	private Handler imageHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int what = msg.what;
			switch (what) {
			case FusionCode.DOWNLOAD_IMAGE_FINISH:
				ImageObj obj = (ImageObj) msg.obj;
				byte[] image = obj.getImg();
				Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
				obj.getMap().put("bitmap", bitmap);
				adapter.notifyDataSetChanged();
				break;
			default:
				break;
			}
		}

	};

	/**
	 * 初始化数据
	 * 
	 * @param list
	 *            List<Map<String, Object>>
	 */
	private void initData(List<Map<String, Object>> list) {
		for (Map<String, Object> map : list) {
			Bitmap bitmap = GetServerImageProcessor.getInstance()
					.getPromotionList(this, map, imageHandler);
			map.put("bitmap", bitmap);
			listData.add(map);
		}
		adapter.notifyDataSetChanged();
	}

	/**
	 * 发送网络请求，得到数据
	 */
	private void getPromotionData() {
		key =  Variable.Session.USER_ID+","+Variable.SERVER_SOFT_URL + "/promotionsList.jspx?keyword="
				+ "" + "&pagesize=" + FusionCode.pageSize + "&page="
				+ page + "&brandid=" + id;
		CacheOpt db = new CacheOpt();
		String str =db.getValue(key,this);//读取数据库		
		if(str!=null) {
			Map<String, Object> map = ResponsePaseUtil.getInstance()
					.parseResponse((String) str);
			int code = (Integer) map.get("code");
			if (code > 0) {
				initData((List<Map<String, Object>>) map.get("objlist"));
			} else if (code == -3) {
				skipLogin();
			} else {
				showDialog((String) map.get("msg"));
			}
			PromotionRequest request = new PromotionRequest();
			request.getPromotion(dbinfoHandler, "", id, page);
		}else{
			showNetDialog(R.string.tips_str, R.string.net_work_request_str);
			PromotionRequest request = new PromotionRequest();
			request.getPromotion(handler, "", id, page);
		}
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
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		Map<String, Object> map = listData.get(position);
		bundle.putString("title", (String) map.get("TITLE"));
		bundle.putString("topTitle", getString(R.string.promotion_title_str));
		bundle.putString("content", (String) map.get("CONTENT"));
		bundle.putString("time", String.valueOf(map.get("PUBTIME")));
		intent.setClass(this, WebViewActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}
}
