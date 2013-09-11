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
import com.cars.simple.logic.MagazineRequest;
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
public class MagazineActivity extends BaseActivity implements
		OnItemClickListener {

	/**
	 * 标记
	 */
	private final static String TAG = MagazineActivity.class.getSimpleName();

	/**
	 * listView
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
	 * 当前页
	 */
	private int currentPage = 1;

	/**
	 * 数据
	 */
	private List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();

	/**
	 * 数据
	 */
	private static List<Map<String, Object>> advlist = new ArrayList<Map<String, Object>>();

	/**
	 * 需要请求的id
	 */
	private int brandId = 0;

	/**
	 * 头部
	 */
	private ViewFlow viewFlow;

	/**
	 * tupian
	 */
	private Bitmap[] bitmaps;

	/**
	 * 标题
	 */
	private String title;
	
	private String key = null;

	/**
	 * 获取杂志列表
	 */
	private Handler magazineHandler = new Handler() {

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
					db.save(key,(String)object,MagazineActivity.this);
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
					db.update(key,(String)object,MagazineActivity.this);
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
	 * 获取杂志列表
	 */
	private Handler advHandler = new Handler() {

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
					advlist = (List<Map<String, Object>>) map.get("objlist");
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
	 *            数据
	 */
	private void initData(List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		for (Map<String, Object> map : list) {
			Bitmap bitmap = GetServerImageProcessor.getInstance()
					.getMaganizeImage(this, map, imageHandler);
			map.put("bitmap", bitmap);
			listData.add(map);
		}
		adapter.notifyDataSetChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cars.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.magazine_activity);

		Bundle bundle = getIntent().getExtras();

		brandId = bundle.getInt("type");
		title = bundle.getString("title");
		listview = (ListView) findViewById(R.id.list);
		adapter = new SimpleAdapter(this, listData,
				R.layout.magazine_list_item, new String[] { "bitmap", "TITLE",
						"DES" }, new int[] { R.id.image, R.id.title, R.id.des });
		adapter.setViewBinder(new ViewHolder());
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);
		getMagazineData();

		if (!advlist.isEmpty()) {
			showBanner();
		} else {
			getTitleData();
		}
	}

	/**
	 * 主界面的推荐的
	 */
	private void getTitleData() {
		MagazineRequest request = new MagazineRequest();
		// 此处最后需要更改
		request.getMagazine(advHandler, "", currentPage, 121);
	}

	/**
	 * 获取杂志数据
	 */
	private void getMagazineData() {
		key =  Variable.Session.USER_ID+","+Variable.SERVER_SOFT_URL + "/ColumnsList.jspx?keyword="
				+ "" + "&pagesize=" + FusionCode.pageSize + "&page="
				+ currentPage + "&brandid=" + brandId;
		CacheOpt db = new CacheOpt();
		String str =db.getValue(key,this);//读取数据库		
		if(str!=null) {
			Map<String, Object> map = ResponsePaseUtil.getInstance()
					.parseResponse(str);
			int code = (Integer) map.get("code");
			if (code > 0) {
				initData((List<Map<String, Object>>) map.get("objlist"));
			} else if (code == -3) {
				skipLogin();
			} else {
				showDialog((String) map.get("msg"));
			}	
			
			MagazineRequest request = new MagazineRequest();
			request.getMagazine(dbinfoHandler, "", currentPage, brandId);
		}else{
			showNetDialog(R.string.tips_str, R.string.net_work_request_str);
			MagazineRequest request = new MagazineRequest();
			request.getMagazine(magazineHandler, "", currentPage, brandId);
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
		// 跳转到详细界面
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		Map<String, Object> map = listData.get(position);
		bundle.putString("title", (String) map.get("title"));
		bundle.putString("topTitle", title);
		bundle.putString("content", (String) map.get("CONTENT"));
		bundle.putString("time", String.valueOf(map.get("PUBTIME")));
		intent.setClass(this, WebViewActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	/**
	 * 展示上面的Banner
	 */
	private void showBanner() {
		viewFlow = (ViewFlow) findViewById(R.id.viewflow);

		if (null != advlist && !advlist.isEmpty()) {
			int length = advlist.size();
			bitmaps = new Bitmap[length];

			for (Map<String, Object> map : advlist) {
				Bitmap bitmap = GetServerImageProcessor.getInstance()
						.getMaganizeAdvImage(this, map, imageHandler);
				map.put("bitmap", bitmap);
			}
			imageAdapter = new ImageAdapter(this, advlist);
			viewFlow.setAdapter(imageAdapter);
			viewFlow.setmSideBuffer(advlist.size()); // 实际图片张数，
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
}
