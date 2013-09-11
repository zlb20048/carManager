package com.cars.simple.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.cars.simple.R;
import com.cars.simple.fusion.FusionCode;
import com.cars.simple.fusion.Variable;
import com.cars.simple.logic.CacheOpt;
import com.cars.simple.logic.MaintenanceRequest;
import com.cars.simple.mode.ViewHolder;
import com.cars.simple.util.ResponsePaseUtil;

public class MaintenanceActivity extends BaseActivity implements
		OnItemClickListener {

	/**
	 * TAG
	 */
	private final static String TAG = MaintenanceActivity.class.getSimpleName();

	/**
	 * 数据
	 */
	private List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();

	/**
	 * Adapter
	 */
	private SimpleAdapter adapter = null;
	private String key = null;
	/**
	 * 列表
	 */
	private ListView expandableListView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maintenance_activity);
		showTopTitle(getString(R.string.maintenance_title_str),
				getString(R.string.machine_title_str));

		 if(Variable.Session.USERCARID == null || "".equals(Variable.Session.USERCARID)){
	        	showNoCarDialog("您还没有添加车辆，请先添加车辆！");
	        	return;
	        }
		 
		 
		expandableListView = (ListView) findViewById(R.id.expandableListView);
		adapter = new SimpleAdapter(this, listData, R.layout.maintenance_item,
				new String[] { "NAME", "PRICE" }, new int[] { R.id.title,
						R.id.price });
		adapter.setViewBinder(new ViewHolder());
		expandableListView.setAdapter(adapter);

		expandableListView.setOnItemClickListener(this);

		getData();
	}

	
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
					db.update(key,(String)object,MaintenanceActivity.this);
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
	 * 取得数据
	 */
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
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
					db.save(key,(String)object,MaintenanceActivity.this);
				} else if (code == -3) {
					skipLogin();
				} else {
					showDialog("亲，您的车型暂无数据，请在“意见反馈”中提醒我们，谢谢！");
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
	 * 初始化数据
	 * 
	 * @param list
	 */
	private void initData(List<Map<String, Object>> list) {
		for (Map<String, Object> map : list) {
			listData.add(map);
		}
		adapter.notifyDataSetChanged();
	}

	/**
	 * 初始化数据
	 */
	private void getData() {
		key =  Variable.Session.USER_ID+","+ Variable.SERVER_SOFT_URL
				+ "/getCarRepairList.jspx?carid=" + Variable.Session.USERCARID + "&pagesize="
				+ FusionCode.pageSize + "&page=" + page;
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
			
			MaintenanceRequest request = new MaintenanceRequest();
			request.getMaintenance(dbinfoHandler, Variable.Session.USERCARID, page);
		}else{
			showNetDialog(R.string.tips_str, R.string.net_work_request_str);
			MaintenanceRequest request = new MaintenanceRequest();
			request.getMaintenance(handler, Variable.Session.USERCARID, page);
		}
	}

	/**
	 * 展示头部
	 * 
	 * @param titleName
	 *            头部名称
	 * @param nextTitleName
	 *            需要跳转
	 */
	private void showTopTitle(String titleName, final String nextTitleName) {
		Button backBtn = (Button) findViewById(R.id.backBtn);
		TextView topTips = (TextView) findViewById(R.id.top_title);
		if (null == titleName) {
			titleName = getString(R.string.app_name);
		}

		topTips.setText(titleName);

		backBtn.setText(getString(R.string.back_btn_str));
		backBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		Button image_btn = (Button) findViewById(R.id.set_btn);
		image_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 跳转到特约商户界面
				Intent intent = new Intent();
				intent.setClass(MaintenanceActivity.this,
						MaintenanceSpeDetailActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent();
		intent.setClass(this, MaintenanceDetailActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt("repair_id", (Integer) listData.get(arg2)
				.get("REPAIR_ID"));
		bundle.putString("title", listData.get(arg2).get("NAME").toString());
		bundle.putString("price", listData.get(arg2).get("PRICE").toString());
		JSONArray childList = (JSONArray) listData.get(arg2).get("childList");

		bundle.putSerializable("childList",
				(Serializable) changeJSONArrayToList(childList));
		intent.putExtras(bundle);
		startActivity(intent);
	}

	/**
	 * 转换列表
	 * 
	 * @param childList
	 * @return
	 */
	private List<Map<String, Object>> changeJSONArrayToList(JSONArray childList) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			for (int i = 0; i < childList.length(); i++) {
				HashMap<String, Object> tempmap = new HashMap<String, Object>();
				JSONObject jsonObj1;
				jsonObj1 = childList.getJSONObject(i);
				Iterator<?> iter = jsonObj1.keys();
				while (iter.hasNext()) {
					String kk = (String) iter.next();
					Object o2 = jsonObj1.get(kk);
					tempmap.put(kk, o2);
				}
				list.add(tempmap);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	
	protected void showNoCarDialog(String message) {
	    if (!this.isFinishing())
	    {
	        AlertDialog dialog = new AlertDialog.Builder(this).create();
	        dialog.setTitle(getString(R.string.tip_str));
	        dialog.setMessage(message);
	        dialog.setButton(getString(R.string.sure_str),
	            new DialogInterface.OnClickListener() {
	            
	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	            	Intent intent = new Intent();
					intent.setClass(MaintenanceActivity.this,
							CarInfoUpdateActivity.class);
					startActivity(intent);
	            }
	        });
	        dialog.show();
	    }
	}
}
