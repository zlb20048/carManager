/**
 * 
 */
package com.cars.simple.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.cars.simple.service.download.GetServerImageProcessor;
import com.cars.simple.service.download.ImageObj;
import com.cars.simple.util.ResponsePaseUtil;
import com.cars.simple.widget.CircleFlowIndicator;
import com.cars.simple.widget.ImageAdapter;
import com.cars.simple.widget.ViewFlow;

/**
 * @author liuzixiang
 * 
 */
public class PromotionActivity extends BaseActivity implements
		OnItemClickListener {

	/**
	 * TAG
	 */
	private final static String TAG = PromotionActivity.class.getSimpleName();

	/**
	 * ListView
	 */
	private ListView listview = null;

	/**
	 * adapter
	 */
	private SimpleAdapter adapter = null;

	/**
	 * 广告位
	 */
	private ImageAdapter imageAdapter = null;

	/**
	 * 数据
	 */
	private List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();

	/**
	 * Banner区域
	 */
	private List<Map<String, Object>> bannerListData = new ArrayList<Map<String, Object>>();

	/**
	 * 头部
	 */
	private ViewFlow viewFlow;

	/**
	 * tupian
	 */
	private Bitmap[] bitmaps;

	/**
	 * 当前页
	 */
	private int page = 1;

	
	private String key = null;
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cars.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.promotion_activity);

		showTop(getString(R.string.promotion_title_str), null);

		listview = (ListView) findViewById(R.id.list);

		adapter = new SimpleAdapter(this, listData,
				R.layout.promotion_list_item, new String[] { "bitmap",
						"PARTNAME" }, new int[] { R.id.image, R.id.text });
		adapter.setViewBinder(new ViewHolder());
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);

		getPromotionTypeList();

		getBannerList();
	}

	/**
	 * 获取Banner图片
	 */
	private void getBannerList() {
		PromotionRequest request = new PromotionRequest();
		request.getPromotion(bannerHandler, "", 0, page);
	}

	/**
	 * 展示上面的Banner
	 */
	private void showBanner() {
		viewFlow = (ViewFlow) findViewById(R.id.viewflow);

		if (null != bannerListData && !bannerListData.isEmpty()) {
			int length = bannerListData.size();
			bitmaps = new Bitmap[length];

			for (Map<String, Object> map : bannerListData) {
				Bitmap bitmap = GetServerImageProcessor.getInstance()
						.getPromotionBanner(this, map, imageHandler);
				map.put("bitmap", bitmap);
			}
			imageAdapter = new ImageAdapter(this, bannerListData);
			viewFlow.setAdapter(imageAdapter);
			viewFlow.setmSideBuffer(bannerListData.size()); // 实际图片张数，
		} else {
			viewFlow.setAdapter(new ImageAdapter(this));
			viewFlow.setmSideBuffer(1); // 实际图片张数， 我的ImageAdapter实际图片张数为3
		}

		CircleFlowIndicator indic = (CircleFlowIndicator) findViewById(R.id.viewflowindic);
		viewFlow.setFlowIndicator(indic);
		viewFlow.setTimeSpan(10 * 1000);
		viewFlow.setSelection(1 * 1000); // 设置初始位置
		viewFlow.startAutoFlowTimer(); // 启动自动播放
	}

	/**
	 * 首页图片
	 */
	private Handler bannerHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int what = msg.what;
			Object object = msg.obj;
			switch (what) {
			case FusionCode.RETURN_JSONOBJECT:
				Map<String, Object> map = ResponsePaseUtil.getInstance()
						.parseResponse((String) object);
				int code = (Integer) map.get("code");
				if (code > 0) {
					bannerListData = (List<Map<String, Object>>) map
							.get("objlist");
					showBanner();
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
	 * 获取列表
	 */
	private Handler promotionListHandler = new Handler() {

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
					db.save(key,(String)object,PromotionActivity.this);
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
					db.update(key,(String)object,PromotionActivity.this);
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
				obj.getMap().put("bitmap",
						BitmapFactory.decodeByteArray(image, 0, image.length));
				adapter.notifyDataSetChanged();
				new Thread() {
					public void run() {
						if (null != imageAdapter) {
							imageAdapter.notifyDataSetChanged();
						}
					}
				}.start();
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
	 */
	private void initData(List<Map<String, Object>> list) {
		for (Map<String, Object> map : list) {
			Bitmap bitmap = GetServerImageProcessor.getInstance()
					.getPromotionType(this, map, imageHandler);
			map.put("bitmap", bitmap);
			listData.add(map);
		}
		adapter.notifyDataSetChanged();
	}

	/**
	 * 获取列表
	 */
	private void getPromotionTypeList() {
		key =  Variable.Session.USER_ID+","+Variable.SERVER_SOFT_URL
				+ "/promotionsTypeList.jspx?parentcolumn=0&pagesize=" + FusionCode.pageSize + "&page=" + page;
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
			request.getPromotionList(dbinfoHandler, 0, page);
		}else{
			showNetDialog(R.string.tips_str, R.string.net_work_request_str);
			PromotionRequest request = new PromotionRequest();
			request.getPromotionList(promotionListHandler, 0, page);
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
		bundle.putInt("id", (Integer) listData.get(position).get("PARTID"));
		bundle.putString("title",
				(String) listData.get(position).get("PARTNAME"));
		intent.setClass(this, PromotionListActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}
}
