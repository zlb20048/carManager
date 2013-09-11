/**
 * 
 */
package com.cars.simple.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.cars.simple.R;
import com.cars.simple.fusion.FusionCode;
import com.cars.simple.fusion.Variable;
import com.cars.simple.logic.CacheOpt;
import com.cars.simple.logic.DefaultCarRequest;
import com.cars.simple.util.ResponsePaseUtil;

/**
 * @author liuzixiang
 * 
 */
/**
 * @author Administrator
 * 
 */
public class SettingAcitivity extends BaseActivity implements OnItemClickListener , OnClickListener
{

    /**
     * 标识
     */
    private final static String TAG = SettingAcitivity.class.getSimpleName();

    /**
     * 设置列表
     */
    private ListView listview = null;

    /**
     * 设置界面的适配器
     */
    private SimpleAdapter adapter = null;

    /**
     * 用户名
     */
    private TextView user_name = null;

    /**
     * 注销
     */
    private Button reset = null;

    /**
     * 更改密码
     */
    private Button resetPwd = null;

    /**
     * 登陆界面
     */
    private LinearLayout loginLayout = null;

    /**
     * 城市选择
     */
    private RelativeLayout cityLayout = null;

    /**
     * adapter数据
     */
    private List<Map<String, Object>> dataItem = null;

    /**
     * 省份简码
     */
    private String[] provinceCodeArray = null;

    /**
     * 省份名称
     */
    private String[] provincenameArray = null;
    
    /**
     * 城市简码
     */
    private String[] cityCodeArray = null;

    /**
     * 城市名称
     */
    private String[] citynameArray = null;

    
    private Spinner provinceSpinner;
	private Spinner citySpinner;
	int defcityidindex = 0;//Spinner默认下标
    /*
     * (non-Javadoc)
     * @see com.cars.activity.BaseActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_acitivity);
        listview = (ListView) findViewById(R.id.listview);
        provinceSpinner = (Spinner) this.findViewById(R.id.province);
        citySpinner = (Spinner) this.findViewById(R.id.scity);
        initview();

        showTop(getString(R.string.cars_home_1_str), null);

        // 初始化列表数据
        initdata();
    }

    private void initview()
    {
        user_name = (TextView) findViewById(R.id.user_name);
        reset = (Button) findViewById(R.id.reset);
        resetPwd = (Button) findViewById(R.id.resetPwd);
        loginLayout = (LinearLayout) findViewById(R.id.login_layout);

        cityLayout = (RelativeLayout) findViewById(R.id.citylayout);

       // city = (TextView) findViewById(R.id.city);

        cityLayout.setOnClickListener(this);
        reset.setOnClickListener(this);
        resetPwd.setOnClickListener(this);

    }

    /**
     * 初始化界面数据
     */
    private void initdata()
    {   	
    	int provinceindex = 0;
    	//已经设置的城市编码
    	String defcityid = Variable.Session.CITY;
    	if(defcityid ==null)
    		defcityid = "3";
    	//获取设置的城市所在的省份编号
    	CacheOpt db = new CacheOpt();
    	String provinceId = db.getProvinceId(defcityid, this);
    	
    	//获取省份   	
    	List<Map<String, Object>> p = db.getProvince(this);
    	
    	if(p!=null && !p.isEmpty()){
    		provinceCodeArray = new String[p.size()];
        	provincenameArray = new String[p.size()];
	    	for(int i=0;i<p.size();i++){
	    		Map<String, Object> m = p.get(i);
	    		String id = (String)m.get("id");
	    		String name = (String)m.get("name");
	    		provinceCodeArray[i] = id;
	    		provincenameArray[i] = name ;
	    		if(provinceId.equals(provinceCodeArray[i])){
	    			provinceindex = i;
	    		}
	    	}
    	}
    	ArrayAdapter<String> provincAdapter = new ArrayAdapter<String>(this,
    			android.R.layout.simple_spinner_item, provincenameArray);
    	// 设置下拉列表的风格
    	provincAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	// 将数据绑定到Spinner视图上
    	provinceSpinner.setAdapter(provincAdapter);
    	provinceSpinner.setSelection(provinceindex);
    	provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				
				Spinner spinner = (Spinner) parent;
				String pro = (String) spinner.getItemAtPosition(position);
				String pid = provinceCodeArray[position];
				CacheOpt db = new CacheOpt();
				List<Map<String, Object>> c = db.getCity(pid,SettingAcitivity.this);
				if(c!=null && !c.isEmpty()){
					cityCodeArray = new String[c.size()+1];
					citynameArray = new String[c.size()+1];
					cityCodeArray[0] = "";
		    		citynameArray[0] = "请选择" ;
			    	for(int i=0;i<c.size();i++){
			    		Map<String, Object> m = c.get(i);
			    		String cid = (String)m.get("id");
			    		String name = (String)m.get("name");
			    		cityCodeArray[i+1] = cid;
			    		citynameArray[i+1] = name ;
			    		if((Variable.Session.CITY).equals(cityCodeArray[i+1])){
			    			defcityidindex = i+1;
			    		}
			    	}
		    	}
				ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(SettingAcitivity.this,
		    			android.R.layout.simple_spinner_item, citynameArray);
		    	// 设置下拉列表的风格
				cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    	// 将数据绑定到Spinner视图上
				citySpinner.setAdapter(cityAdapter);
				citySpinner.setSelection(defcityidindex);
				citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
						if(defcityidindex != position){
							 Variable.Session.CITY = cityCodeArray[position];
				             saveSetData(Variable.Session.CITY);
						}
					}
					
					@Override
					public void onNothingSelected(AdapterView<?> parent) {

					}
				});
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}

		});
    	
       

        dataItem = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        if (!Variable.Session.IS_LOGIN)
        {
            map.put("title", getString(R.string.setting_1_str));
            map.put("image", R.drawable.set_01);
            dataItem.add(map);
            loginLayout.setVisibility(View.GONE);
        }
        else
        {
            map.put("title", getString(R.string.setting_7_str));
            loginLayout.setVisibility(View.VISIBLE);
            user_name.setText(Variable.Session.USER_ID);
        }
        // map = new HashMap<String, Object>();
        // map.put("title", getString(R.string.setting_2_str));
        // map.put("image", R.drawable.set_02);
        // dataItem.add(map);
        // map = new HashMap<String, Object>();
        // map.put("title", getString(R.string.setting_3_str));
        // map.put("image", R.drawable.set_03);
        // dataItem.add(map);
        map = new HashMap<String, Object>();
        map.put("title", getString(R.string.setting_4_str));
        map.put("image", R.drawable.set_04);
        dataItem.add(map);
        map = new HashMap<String, Object>();
        map.put("title", getString(R.string.setting_5_str));
        map.put("image", R.drawable.set_05);
        dataItem.add(map);
        map = new HashMap<String, Object>();
        map.put("title", getString(R.string.setting_6_str));
        map.put("image", R.drawable.set_06);
        dataItem.add(map);
        map = new HashMap<String, Object>();
        map.put("title", getString(R.string.cars_menu_7_str));
        map.put("image", R.drawable.set_07);
        dataItem.add(map);
        // map = new HashMap<String, Object>();
        // map.put("title", getString(R.string.setting_8_str));
        // map.put("image", R.drawable.set_06);
        // dataItem.add(map);
        adapter =
            new SimpleAdapter(this,
                dataItem,
                R.layout.single_list_item,
                new String[] {"image", "title" },
                new int[] {R.id.ItemImage, R.id.ItemTitle });
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
    }

    /*
     * (non-Javadoc)
     * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget .AdapterView, android.view.View,
     * int, long)
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        if (Variable.Session.IS_LOGIN)
        {
            switch (position)
            {
                case 0:
                    // Intent intent = new Intent();
                    // intent.setClass(this, CarMessageActivity.class);
                    // startActivity(intent);
                    // 跳转到意见反馈界面
                    Intent intent = new Intent();
                    intent.setClass(this, FeedBackActivity.class);
                    startActivity(intent);
                    break;
                case 1:
                    // 检测版本更新
                    // checkUpdate(true);
                    intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
                    intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_str));
                    startActivity(Intent.createChooser(intent, getTitle()));
                    break;
                case 2:
                    intent = new Intent();
                    intent.setClass(this, HelpActivity.class);
                    startActivity(intent);
                    // 好友分享功能
                    break;
                case 3:
                    intent = new Intent();
                    intent.setClass(this, AboutActivity.class);
                    startActivity(intent);
                    break;
                case 4:
                    break;
                case 5:
                    // 跳转到忘记密码界面
                    // intent = new Intent();
                    // intent.setClass(this, ChangePwdActivity.class);
                    // startActivity(intent);
                    break;
                default:
                    break;
            }
        }
        else
        {
            switch (position)
            {
                case 0:
                    Intent intent = new Intent();
                    if (!Variable.Session.IS_LOGIN)
                    {
                        intent.setClass(this, LoginActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        // 执行注销操作
                        showDialog1(getString(R.string.login_reset_str));
                    }
                    break;
                case 1:
                    // intent = new Intent();
                    // intent.setClass(this, CarMessageActivity.class);
                    // startActivity(intent);
                    // 跳转到意见反馈界面
                    intent = new Intent();
                    intent.setClass(this, FeedBackActivity.class);
                    startActivity(intent);
                    break;
                case 2:
                    // 检测版本更新
                    // checkUpdate(true);
                    // 好友分享功能
                    intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
                    intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_str));
                    startActivity(Intent.createChooser(intent, getTitle()));
                    break;
                case 3:
                    intent = new Intent();
                    intent.setClass(this, HelpActivity.class);
                    startActivity(intent);
                    break;
                case 4:
                    intent = new Intent();
                    intent.setClass(this, AboutActivity.class);
                    startActivity(intent);
                    break;
                case 5:
                    // 跳转到使用帮助界面
                    break;
                case 6:
                    // // 跳转到忘记密码界面
                    // intent = new Intent();
                    // intent.setClass(this, ChangePwdActivity.class);
                    // startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 显示弹出Dialog提示框
     * 
     * @param message 提示信息
     */
    protected void showDialog1(String message)
    {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle(getString(R.string.tip_str));
        dialog.setMessage(message);
        dialog.setButton(getString(R.string.sure_str), new DialogInterface.OnClickListener()
        {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                resetSessionData();
                initdata();
            }
        });
        dialog.show();
    }

    //
    // /**
    // * 检测版本更新
    // */
    // private void checkUpdate() {
    // showNetDialog(R.string.tips_str, R.string.check_update_str);
    // UpdateRequest request = new UpdateRequest();
    // request.checkUpdate(handler, MainManager.getAppVersion(this));
    // }

    @Override
    public void onClick(View arg0)
    {
        switch (arg0.getId())
        {
            case R.id.reset:
                Intent intent = new Intent();
                if (!Variable.Session.IS_LOGIN)
                {
                    intent.setClass(this, LoginActivity.class);
                    startActivity(intent);
                }
                else
                {
                    // 执行注销操作
                    showDialog1(getString(R.string.login_reset_str));
                }
                break;
            case R.id.resetPwd:
                // 跳转到忘记密码界面
                intent = new Intent();
                intent.setClass(this, ChangePwdActivity.class);
                startActivity(intent);
                break;
            case R.id.citylayout:
                //showCityDialog();
                break;
        }
    }

    

    /**
     * 保存设置的城市和车辆信息
     */
    private void saveSetData(String city)
    {
        showNetDialog(R.string.tips_str, R.string.save_city_car_success_str);
        DefaultCarRequest request = new DefaultCarRequest();
        request.saveDefaultCarMessage(saveDataHandler, Variable.Session.USERCARID, city);
    }

    /**
     * 保存数据Handler
     */
    private Handler saveDataHandler = new Handler()
    {

        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            closeNetDialog();
            int what = msg.what;
            Object object = msg.obj;
            switch (what)
            {
                case FusionCode.RETURN_JSONOBJECT:
                    Map<String, Object> map = ResponsePaseUtil.getInstance().parseResponse((String) object);
                    int code = (Integer) map.get("code");
                    if (code > 0)
                    {
                        showDialog(getString(R.string.save_city_car_success_str));
                    }
                    else if (code == -3)
                    {
                        skipLogin();
                    }
                    else
                    {
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
}
