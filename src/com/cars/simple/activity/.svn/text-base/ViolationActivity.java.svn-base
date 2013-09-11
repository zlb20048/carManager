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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.cars.simple.R;
import com.cars.simple.fusion.FusionCode;
import com.cars.simple.fusion.Variable;
import com.cars.simple.logic.CacheOpt;
import com.cars.simple.logic.DefaultCarRequest;
import com.cars.simple.logic.ViolationRequest;
import com.cars.simple.util.ResponsePaseUtil;
import com.cars.simple.util.Util;

/**
 * @author liuzixiang
 * 
 */
public class ViolationActivity extends BaseActivity implements
		OnItemSelectedListener, OnClickListener {

	/**
	 * 标识
	 */
	private final static String TAG = ViolationActivity.class.getSimpleName();

	/**
	 * 查询的城市
	 */
	private Spinner spinner = null;

	/**
	 * 车辆
	 */
	private Spinner spinnerCar = null;

	/**
	 * 车牌1
	 */
	private Spinner spinnerCar1 = null;

	/**
	 * 车牌2
	 */
	private Spinner spinnerCar2 = null;

	/**
	 * 车牌号
	 */
	private EditText car_num_edit = null;

	/**
	 * 发动机编号
	 */
	private EditText spinner_enginer_edit = null;

	/**
	 * 验证码
	 */
	private EditText validateEdit = null;

	/**
	 * adapter
	 */
	private ArrayAdapter<String> adapter = null;

	/**
	 * adapter1
	 */
	private ArrayAdapter<String> cityCodeAdapter = null;

	/**
	 * adapter2
	 */
	private ArrayAdapter<String> cityAdapter = null;

	/**
	 * 验证码
	 */
	private ImageView validataImg = null;

	/**
	 * 提交按钮
	 */
	private Button submit = null;

	/**
	 * 车型的列表
	 */
	private String[] carArray = null;

	/**
	 * 车辆提交的id
	 */
	private int[] carIdArray = null;

	/**
	 * 城市简码
	 */
	private String[] cityArray = null;

	/**
	 * 城市名称
	 */
	private String[] citynumArray = null;

	/**
	 * 数据
	 */
	private List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();

	/**
	 * 总金额
	 */
	private int total = 0;

	private String key = null;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cars.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.violation_activity);

		showTop(getString(R.string.violation_car_error_title), null);

		initview();

		initdata();

		getDefaultCar();

		// getValidateImg();
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
					db.update(key,(String)object,ViolationActivity.this);
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
	 * 回调方法
	 */
	private Handler defaultHandler = new Handler() {

		@SuppressWarnings("unchecked")
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
					List<Map<String, Object>> list = (List<Map<String, Object>>) map
							.get("objlist");

					String carno = (String) objMap.get("CARNO");

					changeCarNo(carno);

					if (null != list && !list.isEmpty()) {
						initCarData(list);
						CacheOpt db = new CacheOpt();
						db.save(key,(String)object,ViolationActivity.this);
					}
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
	 * 初始化车辆名称
	 * 
	 * @param list
	 */
	private void initCarData(List<Map<String, Object>> list) {
		listData = list;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("NAME", getString(R.string.violation_car_other_str));
		map.put("CARNO", "");
		map.put("ENGINENUMBER", "");
		listData.add(map);
		int size = listData.size();
		carArray = new String[size];
		int selection = 0;
		for (int i = 0; i < size; i++) {
			Map<String, Object> m = listData.get(i);
			String name = (String) m.get("NAME");
			if (name.equals(Variable.Session.USERCARNAME))
			{
			    selection = i;
			}
			carArray[i] = name;
		}
		adapter = new ArrayAdapter<String>(this, R.layout.myspinner_item,
				carArray);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 设置下拉样式
		spinnerCar.setAdapter(adapter);
		spinnerCar.setSelection(selection);
		spinnerCar.setOnItemSelectedListener(this);
	}

	/**
	 * 改变默认选中的
	 */
	private void changeCarNo(String carono) {
		car_num_edit.setText("");
		if (null != carono && carono.length() > 2) {
			car_num_edit.setText(carono.substring(2));
			carono = carono.substring(1, 2);
			for (int i = 0; i < citynumArray.length; i++) {
				if (carono.equals(citynumArray[i])) {
					spinnerCar2.setSelection(i);
				}
			}
		}
	}

	/**
	 * 更改发动机号
	 * 
	 * @param valueOf
	 *            发动机号
	 */
	private void changeEngineer(String valueOf) {
		spinner_enginer_edit.setText(valueOf);
	}

	/**
	 * 图形验证码的Handler
	 */
	private Handler validateImageHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			closeNetDialog();
			int what = msg.what;
			Object object = msg.obj;
			switch (what) {
			case FusionCode.RETURN_GETBYTES:
				// 获取到图片验证码
				byte[] imageData = (byte[]) object;
				Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0,
						imageData.length);
				validataImg.setImageBitmap(bitmap);
				break;
			default:
				break;
			}
		}

	};

	private String car_city;

	private String car_city_num;

	/**
	 * 获取到图形验证码
	 */
	private void getValidateImg() {
		ViolationRequest request = new ViolationRequest();
		request.getVidataImage(validateImageHandler);
	}

	/**
	 * 获取默认的用户的车辆
	 */
	public void getDefaultCar() {
		key =  Variable.Session.USER_ID+","+Variable.SERVER_SOFT_URL + "/userinfo.jspx";
		CacheOpt db = new CacheOpt();
		String str =db.getValue(key,this);//读取数据库		
		if(str!=null) {
			Map<String, Object> map = ResponsePaseUtil.getInstance()
					.parseResponse((String) str);
			int code = (Integer) map.get("code");
			if (code > 0) {
				Map<String, Object> objMap = (Map<String, Object>) map
						.get("obj");
				List<Map<String, Object>> list = (List<Map<String, Object>>) map
						.get("objlist");

				String carno = (String) objMap.get("CARNO");

				changeCarNo(carno);

				if (null != list && !list.isEmpty()) {
					initCarData(list);
				}
			} else if (code == -3) {
				skipLogin();
			} else {
				showDialog((String) map.get("msg"));
			}
			DefaultCarRequest request = new DefaultCarRequest();
			request.getDefaultCarMessage(dbinfoHandler);
		}else{
			showNetDialog(R.string.tips_str, R.string.net_work_request_str);
			DefaultCarRequest request = new DefaultCarRequest();
			request.getDefaultCarMessage(defaultHandler);
		}
	}

	/**
	 * 初始化数据
	 */
	private void initdata() {
		cityArray = getResources().getStringArray(R.array.cityarray);
		citynumArray = getResources().getStringArray(R.array.citynumarray);
		// initAdapter();
		initCarNumAdapter();
		initCarNumCodeAdapter();
		submit.setOnClickListener(this);
	}

	/**
	 * 初始化界面view
	 */
	private void initview() {
		spinner = (Spinner) findViewById(R.id.spinner);
		spinnerCar = (Spinner) findViewById(R.id.spinnerCar);
		spinnerCar1 = (Spinner) findViewById(R.id.spinnerCar1);
		spinnerCar2 = (Spinner) findViewById(R.id.spinnerCar2);
		car_num_edit = (EditText) findViewById(R.id.car_num_edit);
		spinner_enginer_edit = (EditText) findViewById(R.id.spinner_enginer_edit);
		validateEdit = (EditText) findViewById(R.id.validateEdit);
		validataImg = (ImageView) findViewById(R.id.validataImg);
		validataImg.setOnClickListener(this);
		submit = (Button) findViewById(R.id.submit);
	}

	/**
	 * 初始化
	 */
	private void initAdapter() {
		adapter = new ArrayAdapter<String>(this, R.layout.myspinner_item);// 设置样式
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 设置下拉样式
		adapter.add(getString(R.string.violation_car_city_str));
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
	}

	/**
	 * 初始化省的adapter
	 */
	private void initCarNumCodeAdapter() {
		cityCodeAdapter = new ArrayAdapter<String>(this,
				R.layout.myspinner_item, citynumArray);// 设置样式
		cityCodeAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 设置下拉样式
		spinnerCar2.setAdapter(cityCodeAdapter);
		spinnerCar2.setOnItemSelectedListener(this);
	}

	/**
	 * 初始化省的adapter
	 */
	private void initCarNumAdapter() {
		cityAdapter = new ArrayAdapter<String>(this, R.layout.myspinner_item,
				cityArray);// 设置样式
		cityAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 设置下拉样式
		spinnerCar1.setAdapter(cityAdapter);
		spinnerCar1.setOnItemSelectedListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemSelectedListener#onItemSelected(android
	 * .widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		switch (arg0.getId()) {
		case R.id.spinnerCar:
			changeCarNo(String.valueOf(listData.get(arg2).get("CARNO")));
			changeEngineer(String.valueOf(listData.get(arg2)
					.get("ENGINENUMBER")));
			break;
		case R.id.spinnerCar1:
			car_city = cityArray[arg2];
			break;
		case R.id.spinnerCar2:
			car_city_num = citynumArray[arg2];
			break;
		default:
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemSelectedListener#onNothingSelected(android
	 * .widget.AdapterView)
	 */
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.validataImg:
			getValidateImg();
			break;
		case R.id.submit:
			getData();
			break;
		}
	}

	/**
	 * Handler
	 */
	private Handler queryHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			closeNetDialog();
			int what = msg.what;
			Object object = msg.obj;
			switch (what) {
			case FusionCode.RETURN_GETBYTES:
				byte[] data = (byte[]) object;
				String str = new String(data);
				if (null != str && !"".equals(str)) {

					if (str.contains(getString(R.string.validate_check_str))) {
						showDialog(getString(R.string.validate_check_error));
					} else {
						List<Map<String, Object>> list = getArrayList(str);
						if (null != list) {
							skipActivity(list);
						} else {
							showDialog(getString(R.string.no_data));
						}
					}

				}
				break;
			case FusionCode.RETURN_JSONOBJECT:
				Map<String, Object> map = ResponsePaseUtil.getInstance()
						.parseResponse((String) object);
				int code = (Integer) map.get("code");
				if (code == 0) {
					Map<String, Object> objMap = (Map<String, Object>) map
							.get("obj");
					Object objcount = objMap.get("wfcount");
					if (null != objcount) {
						if (Integer.parseInt(objcount.toString()) > 0) {
							JSONObject objJson = (JSONObject) objMap.get("wf");
							HashMap<String, Object> tempmap = new HashMap<String, Object>();
							Iterator<?> iter = objJson.keys();
							while (iter.hasNext()) {
								String key = (String) iter.next();
								Object value;
								try {
									value = objJson.get(key);
									if (value instanceof JSONArray) {
										ArrayList<Map<String, Object>> ll = new ArrayList<Map<String, Object>>();
										JSONArray baseListArray = (JSONArray) value;
										for (int i = 0; i < baseListArray
												.length(); i++) {
											Map<String, Object> mm = new HashMap<String, Object>();
											JSONObject objlist = baseListArray
													.getJSONObject(i);
											Iterator<?> iter1 = objlist.keys();
											while (iter1.hasNext()) {
												String kk = (String) iter1
														.next();
												Object o2 = objlist.get(kk);
												if (kk.equals("wzsj")) {
													o2 = Util.formatTime(String
															.valueOf(o2));
												}
												mm.put(kk, o2);
											}
											ll.add(mm);
										}
										skipActivity(ll);
										// tempmap.put(key, ll);
									} else {
										tempmap.put(key, value);
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
						}
					}
				} else if (code == -3) {
					skipLogin();
				} else {
					showDialog((String) map.get("desc"));
				}
				break;
			case FusionCode.NETWORK_ERROR:
			case FusionCode.NETWORK_TIMEOUT_ERROR:
				showDialog(getString(R.string.net_error));
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 获取到
	 * 
	 * @param list
	 *            List
	 *            {wzsj=2011-03-18 15:12:33, wzyy=机动车不按交通信号灯规定通行的, wzdd=虎踞北路, jkbh=null, hpzl=02, hphm=苏A061V1}
	
	 */
	private void skipActivity(List<Map<String, Object>> list) {
		if(list!=null &&!list.isEmpty()){
			for(int i=0;i<list.size();i++){
				Map<String, Object> m = list.get(i);
				if("null".equals(m.get("wzsj")+"")){
					m.put("wzsj", "");
				}
				if("null".equals(m.get("wzyy")+"")){
					m.put("wzyy", "");
				}
				if("null".equals(m.get("wzdd")+"")){
					m.put("wzdd", "");			
				}	
				if("null".equals(m.get("jkbh")+"")){
					m.put("jkbh", "");			
				}
				if("null".equals(m.get("hpzl")+"")){
					m.put("hpzl", "");			
				}
				if("null".equals(m.get("hphm")+"")){
					m.put("hphm", "");			
				}
			}
		}
		Intent intent = new Intent();
		intent.setClass(this, ViolationDetailActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("list", (Serializable) list);
		bundle.putString("carno", car_city + car_city_num
				+ car_num_edit.getText().toString());
		bundle.putString("total", total + "");
		intent.putExtras(bundle);
		startActivity(intent);
	}

	/**
	 * 获取数值
	 * 
	 * @param result
	 *            得到的数值
	 * @return
	 */
	public List<Map<String, Object>> getArrayList(String result) {
		List l = new ArrayList();
		int startIndex = result.indexOf("zyts");
		int endIndex = result.indexOf("end_tr");

		if (startIndex != -1 && endIndex != -1) {

			result = result.substring(startIndex, endIndex);

			String sps[] = result.split("</tr>");
			for (int i = 0; i < sps.length - 1; i++) {
				Map m = new HashMap();
				String sp = sps[i];
				if (sp != null) {
					String sps1[] = sp.split("</td>");
					for (int j = 0; j < sps1.length; j++) {
						String sp1 = sps1[j];
						if (sp1 != null) {
							switch (j) {
							case 0:
								m.put("id", sp1.substring(sp1
										.indexOf("first_td bg1") + 14));
								break;
							case 1:
								m.put("number",
										sp1.substring(sp1.indexOf("bg2") + 5));
								break;
							case 2:
								m.put("times",
										sp1.substring(sp1.indexOf("bg1") + 5));
								break;
							case 3:
								m.put("address",
										sp1.substring(sp1.indexOf("bg2") + 5));
								break;
							case 4:
								String res = sp1
										.substring(sp1.indexOf("bg1") + 5);
								if (res != null) {
									m.put("res",
											res.substring(0, res.indexOf("<")));
									String price = (String) res.subSequence(
											res.indexOf("额") + 1,
											res.lastIndexOf("元"));
									total += Integer.valueOf(price);
									m.put("price", price);
								}
								break;
							default:
							}
						}
					}
					l.add(m);
				}
			}
			return l;
		}
		return null;
	}

	/**
	 * 解析html
	 * 
	 * @param data
	 *            数据
	 */
	private void parseHtml(String data) {

		// int maxLogSize = 1000;
		// for (int i = 0; i <= data.length() / maxLogSize; i++) {
		// int start = i * maxLogSize;
		// int end = (i + 1) * maxLogSize;
		// end = end > data.length() ? data.length() : end;
		// Log.e(TAG, data.substring(start, end));
		// }

		int startIndex = 0;
		int endIndex = 0;
		Log.e(TAG, "startIndex = " + startIndex + " endIndex = " + endIndex);
		startIndex = data.indexOf("innerHTML");
		endIndex = data.indexOf("end_tr");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Log.e(TAG, "startIndex = " + startIndex + " endIndex = " + endIndex);
		if (startIndex != -1 && endIndex != -1) {
			String newData = data.substring(startIndex, endIndex);
			if (newData.contains("</tr>")) {
				String[] strs = newData.split("</tr>");
				for (String str : strs) {
					if (str.contains("</td>")) {
						String[] endData = str.split("</td>");
						Map<String, Object> map = new HashMap<String, Object>();
						int length = endData.length;
						for (int i = 0; i < length; i++) {
							String d = endData[i];
							startIndex = d.indexOf(">");
							if (startIndex != -1) {
								String userData = d.substring(startIndex + 1);
								Log.e(TAG, "data = " + userData + " i = " + i);
								if (i == 0) {
									startIndex = userData.indexOf(">");
									if (startIndex != -1) {
										userData = userData
												.substring(startIndex + 1);
									}
								} else if (i == 4) {

								} else if (i == 5) {

								}
								Log.i(TAG, "data = " + userData + " i = " + i);
								map.put("" + i, userData);
							}
						}
					}
				}

			}
		}
	}

	/**
	 * 查询数据
	 */
	private void getData() {
		showNetDialog(R.string.tips_str, R.string.net_work_request_str);
		ViolationRequest request = new ViolationRequest();

		String fdjh = spinner_enginer_edit.getText().toString();
		String keywords2 = car_num_edit.getText().toString();

		request.queryData(queryHandler, fdjh, keywords2);
	}
}
