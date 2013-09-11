/**
 * 
 */
package com.cars.simple.activity;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.cars.simple.R;
import com.cars.simple.fusion.FusionCode;
import com.cars.simple.fusion.Variable;
import com.cars.simple.logic.AccountRequest;
import com.cars.simple.logic.CacheOpt;
import com.cars.simple.logic.DefaultCarRequest;
import com.cars.simple.mode.AccountInfo;
import com.cars.simple.service.baseData.RawTemplate;
import com.cars.simple.util.ResponsePaseUtil;
import com.cars.simple.util.Util;

/**
 * @author liuzixiang
 * 
 */
public class AccountRecordActivity extends BaseActivity implements OnClickListener , OnItemSelectedListener ,
    OnFocusChangeListener
{

    private final static String TAG = AccountRecordActivity.class.getSimpleName();

    /**
     * 提交按钮
     */
    private Button submit = null;

    private Button delete = null;

    /**
     * 类型
     */
    private Spinner type_spinner = null;

    /**
     * 油价
     */
    private EditText unitprice_edit = null;

    /**
     * 油品
     */
    private Spinner oil_spinner = null;

    /**
     * 加油量
     */
    private EditText numbers_edit = null;

    /**
     * 起始
     */
    private EditText startmileage_edit = null;

    /**
     * 结束
     */
    private EditText endmileage_edit = null;

    /**
     * 人工费
     */
    private EditText laborcosts_edit = null;

    /**
     * 配件费
     */
    private EditText fittingcosts_edit = null;

    /**
     * 总价
     */
    private EditText allprice_edit = null;

    /**
     * 总里程
     */
    private EditText allmileage_edit = null;

    /**
     * 摘要
     */
    private EditText remark_edit = null;

    /**
     * 地点
     */
    private EditText address_edit = null;

    /**
     * 日期
     */
    private EditText pricetime_spinner = null;

    /**
     * 规费名称
     */
    private Spinner accounttype_spinner = null;

    /**
     * 美容Spinner
     */
    private Spinner wash_spinner = null;

    /**
     * 明细
     */
    private Button real_messsage_btn = null;

    /**
     * 车辆
     */
    private Spinner spinner1 = null;

    /**
     * layout
     */
    private LinearLayout layout_1 = null;

    /**
     * layout
     */
    private LinearLayout layout_2 = null;

    /**
     * layout
     */
    private LinearLayout layout_3 = null;

    /**
     * layout
     */
    private LinearLayout layout_4 = null;

    /**
     * layout
     */
    private LinearLayout layout_5 = null;

    /**
     * layout
     */
    private LinearLayout layout_6 = null;

    /**
     * layout
     */
    private LinearLayout layout_7 = null;

    /**
     * layout
     */
    private LinearLayout layout_8 = null;

    /**
     * layout
     */
    private LinearLayout layout_9 = null;

    /**
     * layout
     */
    private LinearLayout layout_10 = null;

    /**
     * layout
     */
    private LinearLayout layout_11 = null;

    /**
     * layout
     */
    private LinearLayout layout_12 = null;

    /**
     * layout
     */
    private LinearLayout layout_13 = null;

    /**
     * layout
     */
    private LinearLayout layout_14 = null;

    /**
     * layout
     */
    private LinearLayout layout_15 = null;

    /**
     * layout
     */
    private LinearLayout layout_16 = null;

    /**
     * 照相界面
     */
    private LinearLayout photo_layout = null;

    /**
     * 调用相机
     */
    private ImageButton photo_btn = null;

    /**
     * 照相图片
     */
    private ImageView accountImage = null;

    /**
     * 类型
     */
    private String[] typearray = null;

    /**
     * AccountBase
     */
    private String[] accountBaseArray = null;

    /**
     * id
     */
    private String[] accountBaseIdArray = null;

    /**
     * AccountBase
     */
    private String[] washBaseArray = null;

    /**
     * id
     */
    private String[] washBaseIdArray = null;

    /**
     * 类型id
     */
    private String[] typeidarray = null;

    /**
     * 油品
     */
    private String[] oilarray = null;

    /**
     * 油品类别id
     */
    private String[] oilidarray = null;

    /**
     * 车辆信息
     */
    private List<Map<String, Object>> carList = null;

    /**
     * 车辆数据
     */
    ArrayAdapter<String> carAdapter = null;

    /**
     * 类别
     */
    private ArrayAdapter<String> typeAdapter = null;

    /**
     * 类别
     */
    private ArrayAdapter<String> oilAdapter = null;

    /**
     * 类别
     */
    private ArrayAdapter<String> washAdapter = null;

    /**
     * 类别
     */
    private ArrayAdapter<String> accountBaseAdapter = null;

    /**
     * 获取车类型
     */
    private String usercarid = "";

    /**
     * 类别
     */
    private String accounttype = "";

    /**
     * 油品类型
     */
    private String oilType = "";

    /**
     * 规费类型
     */
    private String accountBaseType = "";

    /**
     * 时间
     */
    private String starttime = "";

    /**
     * 美容的id
     */
    private String washId = "";

    /**
     * 数据
     */
    private Map<String, Object> dataMap = null;

    /**
     * 开始的id
     */
    private String accounttypeid = "";

    /**
     * accountid
     */
    private int accountid = 0;

    /**
     * 是否是更新
     */
    private boolean isUpdate = false;

    /**
     * 明细
     */
    private String detail_str = "1";

    /**
     * 标记
     */
    private boolean[] flags = null;

    /**
     * 列表
     */
    private String[] items = null;

    /**
     * 传递的id
     */
    private String[] itemsid = null;

    /**
     * 总花费
     */
    private double totalMoney;

    /**
     * 返回的数据
     */
    private List<Map<String, Object>> backData = new ArrayList<Map<String, Object>>();

    /**
     * 明细详情
     */
    private List<Map<String, Object>> baselist = new ArrayList<Map<String, Object>>();

    /**
     * 得到的图片
     */
    private Bitmap camerabmp = null;

    /**
     * 图片
     */
    private String imageFilePath = "";
    private String key1 = "";

    /*
     * (non-Javadoc)
     * @see com.cars.activity.BaseActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_record_activity);
        delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(this);
        Bundle bundle = getIntent().getExtras();
        if (null != bundle)
        {
            dataMap = (Map<String, Object>) bundle.getSerializable("data");

            if (null != dataMap && !dataMap.isEmpty())
            {
                accounttypeid = (Integer) dataMap.get("ACCOUNTTYPE") + "";
                accountid = (Integer) dataMap.get("ACCOUNTID");
                isUpdate = bundle.getBoolean("isUpdate");
                usercarid = dataMap.get("USERCARID") + "";
                oilType = dataMap.get("OIL") + "";
            }
            else
            {
                usercarid = bundle.getString("usercarid");
                delete.setVisibility(View.GONE);
            }
        }
        else
        {
            delete.setVisibility(View.GONE);
        }

        showTop(getString(R.string.account_add_cost_str), null);

        initview();
        initdata();

        getData();

        // getLastDistance();
    }

    private Handler distanceHandler = new Handler()
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
                        Map<String, Object> objMap = (Map<String, Object>) map.get("obj");
                        if (null == dataMap || dataMap.isEmpty())
                        {
                            Variable.Session.LAST = objMap.get("last") + "";
                            startmileage_edit.setText(Variable.Session.LAST);
                        }
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

    private void getLastDistance()
    {
        AccountRequest request = new AccountRequest();
        request.getLastDistance(distanceHandler);
    }

    /**
     * 获取数据
     */
    private Handler singleHandler = new Handler()
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
                        Map<String, Object> objMap = (Map<String, Object>) map.get("obj");

                        if (objMap.containsKey("baselist"))
                        {
                            baselist = (List<Map<String, Object>>) objMap.get("baselist");
                        }
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

    /**
     * 获取单条数据
     */
    private void getData()
    {
        if (accountid != 0)
        {
            showNetDialog(R.string.tips_str, R.string.net_work_request_str);
            AccountRequest request = new AccountRequest();
            request.getSingleAccount(singleHandler, accountid + "");
        }
    }

    /**
     * 初始化数据
     */
    private void initdata()
    {
        typearray = getResources().getStringArray(R.array.typearray1);
        typeidarray = getResources().getStringArray(R.array.typeidarray1);

        oilarray = getResources().getStringArray(R.array.oilarray);
        oilidarray = getResources().getStringArray(R.array.oilidarray);

        initTypeAdapter();

        initOilAdapter();

        // if (usercarid.equals("")) {
        getDefaultCar();
        // }
    }

    /**
     * 获取默认的用户的车辆
     */
    public void getDefaultCar()
    {
        key1 = Variable.Session.USER_ID + "," + Variable.SERVER_SOFT_URL + "/userinfo.jspx";
        ;
        CacheOpt db = new CacheOpt();
        String str = db.getValue(key1, this);// 读取数据库
        if (str != null)
        {
            Map<String, Object> map = ResponsePaseUtil.getInstance().parseResponse((String) str);
            int code = (Integer) map.get("code");
            if (code > 0)
            {
                Map<String, Object> objMap = (Map<String, Object>) map.get("obj");
                List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("objlist");

                if (null != list && !list.isEmpty())
                {
                    initCarAdapter(list);
                }
            }
            else if (code == -3)
            {
                skipLogin();
            }
            else
            {
                showDialog((String) map.get("msg"));
            }
            DefaultCarRequest request = new DefaultCarRequest();
            request.getDefaultCarMessage(dbinfoHandler);
        }
        else
        {
            showNetDialog(R.string.tips_str, R.string.net_work_request_str);
            DefaultCarRequest request = new DefaultCarRequest();
            request.getDefaultCarMessage(defaultHandler);
        }

    }

    private Handler dbinfoHandler = new Handler()
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
                        CacheOpt db = new CacheOpt();
                        db.update(key1, (String) object, AccountRecordActivity.this);
                    }
                    else if (code == -3)
                    {
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
    private Handler defaultHandler = new Handler()
    {

        @SuppressWarnings("unchecked")
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
                        Map<String, Object> objMap = (Map<String, Object>) map.get("obj");
                        List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("objlist");

                        if (null != list && !list.isEmpty())
                        {
                            initCarAdapter(list);
                            CacheOpt db = new CacheOpt();
                            db.save(key1, (String) object, AccountRecordActivity.this);
                        }
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

    /**
     * 初始化
     */
    private void initCarAdapter(List<Map<String, Object>> list)
    {
        carList = list;
        int size = list.size();
        int selection = 0;
        carAdapter = new ArrayAdapter<String>(this, R.layout.myspinner_item);
        // carAdapter.add(getString(R.string.account_car_choose_str));
        for (int i = 0; i < size; i++)
        {
            String name = (String) list.get(i).get("NAME");
            carAdapter.add(name);
            if (name.equals(usercarid))
            {
                selection = i;
            }
        }
        carAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 设置下拉样式
        spinner1.setAdapter(carAdapter);
        spinner1.setSelection(selection);
        spinner1.setOnItemSelectedListener(this);
    }

    /**
     * 头部操作，点击操作的时候，返回前一个页面
     * 
     * @param titleStr 顶部的名称
     * @param backStr 按钮的名称
     */
    protected void showTop(String titleStr, String backStr)
    {
        Button backBtn = (Button) findViewById(R.id.backBtn);
        TextView topTips = (TextView) findViewById(R.id.top_title);
        if (null == titleStr)
        {
            titleStr = getString(R.string.app_name);
        }
        topTips.setText(titleStr);

        if (backStr == null)
        {
            backStr = getString(R.string.back_btn_str);
        }
        backBtn.setText(backStr);
        backBtn.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        spinner1 = (Spinner) findViewById(R.id.top_spinner);
    }

    /**
     * 展示时间控件
     * 
     * @param time 显示时间
     * @param editText 文本框
     */
    public void showTimeView(final EditText editText)
    {
        int year2;
        int month3;
        int day4;

        String time = editText.getText().toString();
        if (null != time && !"".equals(time))
        {
            year2 = Integer.parseInt(time.substring(0, 4));
            month3 = (Integer.parseInt(time.substring(5, 7))) - 1;
            day4 = Integer.parseInt(time.substring(8, 10));
        }
        else
        {
            Calendar canledar = Calendar.getInstance();
            year2 = canledar.get(Calendar.YEAR);
            month3 = canledar.get(Calendar.MONTH);
            day4 = canledar.get(Calendar.DAY_OF_MONTH);
        }

        final DatePickerDialog datePickerDialog =
            new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener()
            {
                @Override
                public void onDateSet(DatePicker view, int year1, int monthOfYear1, int dayOfMonth1)
                {
                    String year = year1 + "";
                    String month = (monthOfYear1 + 1) + "";
                    String day = dayOfMonth1 + "";
                    if (day.length() == 1)
                    {
                        day = "0" + day;
                    }
                    if (month.length() == 1)
                    {
                        month = "0" + month;
                    }
                    String timeName = year + "-" + month + "-" + day;
                    editText.setText(timeName);
                    switch (editText.getId())
                    {
                        case R.id.pricetime_spinner:
                            starttime = year + month + day;
                            break;
                    }
                }
            }, year2, month3, day4);
        datePickerDialog.setTitle("请选择");
        datePickerDialog.show();
    }

    /**
     * 初始化油品
     */
    private void initOilAdapter()
    {
        oilAdapter = new ArrayAdapter<String>(this, R.layout.myspinner_item, oilarray);
        int selectid = 0;
        for (int i = 0; i < oilidarray.length; i++)
        {
            if (oilType.equals(oilidarray[i]))
            {
                selectid = i;
            }
        }

        oilAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 设置下拉样式
        oil_spinner.setAdapter(oilAdapter);
        oil_spinner.setSelection(selectid);
        oil_spinner.setOnItemSelectedListener(this);
    }

    /**
     * 类型
     */
    private void initTypeAdapter()
    {
        int selectid = 0;
        for (int i = 0; i < typeidarray.length; i++)
        {
            if (typeidarray[i].equals(accounttypeid))
            {
                selectid = i;
            }
        }

        typeAdapter = new ArrayAdapter<String>(this, R.layout.myspinner_item, typearray);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 设置下拉样式
        type_spinner.setAdapter(typeAdapter);
        type_spinner.setSelection(selectid);
        type_spinner.setOnItemSelectedListener(this);
    }

    /**
     * 初始化view
     */
    private void initview()
    {
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);

        type_spinner = (Spinner) findViewById(R.id.type_spinner);
        oil_spinner = (Spinner) findViewById(R.id.oil_spinner);
        accounttype_spinner = (Spinner) findViewById(R.id.accounttype_spinner);

        wash_spinner = (Spinner) findViewById(R.id.wash_spinner);

        unitprice_edit = (EditText) findViewById(R.id.unitprice_edit);

        unitprice_edit.setOnFocusChangeListener(this);

        numbers_edit = (EditText) findViewById(R.id.numbers_edit);

        numbers_edit.setOnFocusChangeListener(this);

        startmileage_edit = (EditText) findViewById(R.id.startmileage_edit);
        endmileage_edit = (EditText) findViewById(R.id.endmileage_edit);
        laborcosts_edit = (EditText) findViewById(R.id.laborcosts_edit);
        fittingcosts_edit = (EditText) findViewById(R.id.fittingcosts_edit);
        allprice_edit = (EditText) findViewById(R.id.allprice_edit);

        allprice_edit.setOnFocusChangeListener(this);

        allmileage_edit = (EditText) findViewById(R.id.allmileage_edit);
        remark_edit = (EditText) findViewById(R.id.remark_edit);
        address_edit = (EditText) findViewById(R.id.address_edit);
        pricetime_spinner = (EditText) findViewById(R.id.pricetime_spinner);
        real_messsage_btn = (Button) findViewById(R.id.real_messsage_btn);

        photo_btn = (ImageButton) findViewById(R.id.photo_btn);
        photo_btn.setOnClickListener(this);

        accountImage = (ImageView) findViewById(R.id.accountImage);
        Bitmap bitmap = BitmapFactory.decodeFile(FusionCode.ECMC_FILE_PATH + "account_" + accountid + ".png");
        if (null != bitmap)
        {
            accountImage.setImageBitmap(bitmap);
        }
        else
        {
            accountImage.setVisibility(View.GONE);
        }
        accountImage.setOnClickListener(this);

        real_messsage_btn.setOnClickListener(this);
        pricetime_spinner.setOnClickListener(this);

        pricetime_spinner.setText(Util.getDay());

        startmileage_edit.setText(Variable.Session.LAST);

        if (null != dataMap && !dataMap.isEmpty())
        {
            unitprice_edit.setText(dataMap.get("UNITPRICE") + "");
            numbers_edit.setText(dataMap.get("NUMBERS") + "");
            startmileage_edit.setText(dataMap.get("STARTMILEAGE") + "");
            endmileage_edit.setText(dataMap.get("ENDMILEAGE") + "");
            laborcosts_edit.setText(dataMap.get("LABORCOSTS") + "");
            fittingcosts_edit.setText(dataMap.get("FITTINGCOSTS") + "");
            allprice_edit.setText(dataMap.get("ALLPRICE") + "");
            allmileage_edit.setText(dataMap.get("ALLMILEAGE") + "");
            remark_edit.setText(dataMap.get("REMARK") + "");
            address_edit.setText(dataMap.get("ADDRESS") + "");

            String stime = "";
            if (dataMap.get("PRICETIME") != null)
            {
                stime = (String) dataMap.get("PRICETIME");
                if (stime.length() > 10)
                {
                    stime = stime.substring(0, 11);
                }
            }
            pricetime_spinner.setText(stime);
        }

        layout_1 = (LinearLayout) findViewById(R.id.layout_1);
        layout_2 = (LinearLayout) findViewById(R.id.layout_2);
        layout_3 = (LinearLayout) findViewById(R.id.layout_3);
        layout_4 = (LinearLayout) findViewById(R.id.layout_4);
        layout_5 = (LinearLayout) findViewById(R.id.layout_5);
        layout_6 = (LinearLayout) findViewById(R.id.layout_6);
        layout_7 = (LinearLayout) findViewById(R.id.layout_7);
        layout_8 = (LinearLayout) findViewById(R.id.layout_8);
        layout_9 = (LinearLayout) findViewById(R.id.layout_9);
        layout_10 = (LinearLayout) findViewById(R.id.layout_10);
        layout_11 = (LinearLayout) findViewById(R.id.layout_11);
        layout_12 = (LinearLayout) findViewById(R.id.layout_12);
        layout_13 = (LinearLayout) findViewById(R.id.layout_13);
        layout_14 = (LinearLayout) findViewById(R.id.layout_14);
        layout_15 = (LinearLayout) findViewById(R.id.layout_15);
        layout_16 = (LinearLayout) findViewById(R.id.layout_16);
        photo_layout = (LinearLayout) findViewById(R.id.photo_layout);
    }

    /*
     * (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.submit:
                // 提交
                if (accounttype.equals("2"))
                {
                    accountBaseType = "";
                    String id = washId;
                    accountBaseType = id + "|" + allprice_edit.getText().toString();
                }
                if (isUpdate)
                {
                    updateAccount();
                }
                else
                {
                    saveAccount();
                }
                break;
            case R.id.delete:
                new AlertDialog.Builder(AccountRecordActivity.this)
                /* 弹出窗口的最上头文字 */
                .setTitle("删除流水账")
                /* 设置弹出窗口的图式 */
                // .setIcon(R.drawable.icon)
                    /* 设置弹出窗口的信息 */
                    .setMessage("确定删除吗？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialoginterface, int i)
                        {
                            showNetDialog(R.string.tips_str, R.string.net_work_request_str);
                            AccountRequest request = new AccountRequest();
                            request.deleteWaterAccount(deletehandler, accountid + "");
                        }
                    })
                    /* 设置弹出窗口的返回事件 */
                    .setNegativeButton("取消", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialoginterface, int i)
                        {
                        }
                    })
                    .show();
                break;
            case R.id.pricetime_spinner:
                showTimeView(pricetime_spinner);
                break;
            case R.id.real_messsage_btn:
                // 请求网络，得到网络数据，获取明细数据
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("type", detail_str);
                bundle.putSerializable("baselist", (Serializable) baselist);
                intent.putExtras(bundle);
                intent.setClass(this, AccountDetailActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.photo_btn:
                // 调用系统所用的相机界面
                getCamera();
                break;
            case R.id.accountImage:
                showBigImage();
                break;
            default:
                break;
        }
    }

    private Handler deletehandler = new Handler()
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

                        String starttime1 = Util.getBeginDateNew();
                        String endtime1 = Util.getEndDateNew();

                        showNetDialog(R.string.tips_str, R.string.net_work_request_str);
                        AccountRequest request = new AccountRequest();
                        request.queryAccountWaterRecord(waterhandler,
                            "-2",
                            usercarid,
                            starttime1,
                            endtime1,
                            1);

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

    private Handler waterhandler = new Handler()
    {

        @Override
        public void handleMessage(Message msg)
        {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            closeNetDialog();
            int what = msg.what;
            Object object = msg.obj;
            switch (what)
            {
                case FusionCode.RETURN_JSONOBJECT:
                    String starttime1 = Util.getBeginDateNew();
                    String endtime1 = Util.getEndDateNew();
                    String key2 =
                        Variable.Session.USER_ID + "," + Variable.SERVER_SOFT_URL
                            + "/message/getaccount.jspx?usercarid=" + usercarid + "&type=-2" + "&starttime="
                            + starttime1 + "&endtime=" + endtime1 + "&pagesize=" + FusionCode.pageSize
                            + "&page=1";

                    System.out.println("delkey2=" + key2);
                    Map<String, Object> map = ResponsePaseUtil.getInstance().parseResponse((String) object);
                    int code = (Integer) map.get("code");
                    if (code > 0)
                    {

                        CacheOpt db = new CacheOpt();
                        db.update(key2, (String) object, AccountRecordActivity.this);
                        Intent intent = new Intent();
                        intent.setClass(AccountRecordActivity.this, AccountWaterActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    break;
                case FusionCode.NETWORK_ERROR:
                case FusionCode.NETWORK_TIMEOUT_ERROR:
                    showDialog(getString(R.string.net_error));
                    break;
            }
        }
    };

    protected PopupWindow mPopupWindow;

    /**
     * 展示大图
     */
    private void showBigImage()
    {
        LayoutInflater mLayoutInflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View image_pop_view = mLayoutInflater.inflate(R.layout.image_layout, null);

        ImageView imageView = (ImageView) image_pop_view.findViewById(R.id.image);

        imageView.setImageBitmap(camerabmp);

        mPopupWindow = new PopupWindow(image_pop_view, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);

        mPopupWindow.showAtLocation(image_pop_view, Gravity.CENTER, 0, 0);
    }

    /**
     * 调用摄像头
     */
    private void getCamera()
    {
        new AlertDialog.Builder(this).setTitle(getString(R.string.account_photo_str))
            .setItems(R.array.photo_arry, new DialogInterface.OnClickListener()
            {

                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    switch (which)
                    {
                        case 0:
                            imageFilePath =
                                Environment.getExternalStorageDirectory().getAbsolutePath()
                                    + "/mypicture.jpg";
                            File imageFile = new File(imageFilePath);

                            Uri imageFileUri = Uri.fromFile(imageFile);

                            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            i.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageFileUri);
                            startActivityForResult(i, 2);
                            break;
                        case 1:
                            Intent intent = new Intent();
                            intent.setData(Uri.parse("content://media/internal/images/media"));
                            intent.setAction(Intent.ACTION_PICK);
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 3);
                            break;
                    }
                }
            })
            .create()
            .show();
    }

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (null != data)
        {
            if (requestCode == 1)
            {
                Bundle bundle = data.getExtras();
                backData = (List<Map<String, Object>>) bundle.getSerializable("data");
                totalMoney = 0.0;
                for (Map<String, Object> map : backData)
                {
                    String id = map.get("ID") + "";
                    Object obj = map.get("text");
                    accountBaseType = accountBaseType + id;
                    if (null != obj)
                    {
                        String money = obj.toString();
                        totalMoney += Double.parseDouble(money);
                        accountBaseType = accountBaseType + "|" + money + ",";
                    }
                    else
                    {
                        accountBaseType = accountBaseType + "|" + "0,";
                    }
                }
                int lastIndex = accountBaseType.lastIndexOf(",");
                if (lastIndex != -1)
                {
                    accountBaseType = accountBaseType.substring(0, lastIndex);
                }

                Log.v(TAG, "total = " + totalMoney);
                // allprice_edit.setText("" + totalMoney);
            }
            else if (requestCode == 2)
            {
                camerabmp = Util.comp(BitmapFactory.decodeFile(imageFilePath, getOption()));
                // camerabmp = BitmapFactory.decodeFile(imageFilePath);
                accountImage.setVisibility(View.VISIBLE);
                accountImage.setImageBitmap(Util.comp(camerabmp));
            }
            else if (requestCode == 3)
            {
                Uri uri = data.getData();
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                cursor.moveToFirst();
                try
                {
                    InputStream is = new FileInputStream(cursor.getString(1));
                    camerabmp = Util.comp(BitmapFactory.decodeStream(is, null, getOption()));
                    accountImage.setVisibility(View.VISIBLE);
                    accountImage.setImageBitmap(camerabmp);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public BitmapFactory.Options getOption()
    {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;// 这里设置高度为800f
        float ww = 480f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww)
        {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        }
        else if (w < h && h > hh)
        {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        return newOpts;
    }

    public static final int UPLOAD_DATA = 10000;

    /**
     * 保存图片
     * 
     * @param bitmap 需要保存的图片
     * @param name 名称
     */
    private void saveBitmap(final Bitmap bitmap, final String name)
    {
        showNetDialog(R.string.tips_str, R.string.net_work_request_str);
        byte[] data = Util.bitmapToBytes(bitmap);
        RawTemplate rt = new RawTemplate(AccountRecordActivity.this);
        try
        {
            // data = Util.bitmapToBytes(Util.comp(bitmap));
            Log.v(TAG, "name = " + name);
            rt.saveToDefault(data, name);

            Message msg = new Message();
            msg.what = UPLOAD_DATA;
            DataInfo info = new DataInfo();
            info.name = name;
            info.data = data;
            msg.obj = info;
            handler.sendMessage(msg);
            // 上传图片到服务器 zhuyujie
            // uploadFile(name, data);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private class DataInfo
    {
        public String name = "";

        public byte[] data = null;
    }

    private Handler handler = new Handler()
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
                        if (isUpdate)
                        {
                            if (null != camerabmp)
                            {
                                saveBitmap(camerabmp, "account_" + accountid + ".png");
                            }
                        }
                        else
                        {
                            if (null != camerabmp)
                            {
                                saveBitmap(camerabmp, "account_" + map.get("id") + ".png");
                            }
                            Intent intent = new Intent();
                            intent.setClass(AccountRecordActivity.this, DiscussActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("type", accounttype);
                            startActivity(intent);
                        }
                        finish();
                    }
                    else if (code == -3)
                    {

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
                case UPLOAD_DATA:
                    DataInfo info = (DataInfo) object;
                    uploadFile(info.name, info.data);
                    break;
            }
        }

    };

    /**
     * 保存
     */
    private void saveAccount()
    {
        AccountInfo info = new AccountInfo();
        info.accountbase = accountBaseType;
        info.accounttype = accounttype;
        info.address = address_edit.getText().toString();
        info.allmileage = allmileage_edit.getText().toString();
        info.allprice = allprice_edit.getText().toString();
        info.endmileage = endmileage_edit.getText().toString();
        info.fittingcosts = fittingcosts_edit.getText().toString();
        info.laborcosts = laborcosts_edit.getText().toString();
        info.numbers = numbers_edit.getText().toString();
        info.oil = oilType;
        info.pricetime = starttime;
        info.pricetype = "1";
        info.remark = remark_edit.getText().toString();
        info.startmileage = startmileage_edit.getText().toString();
        info.unitprice = unitprice_edit.getText().toString();
        info.usercarid = usercarid;

        if (checkRecord(info))
        {
            showNetDialog(R.string.tips_str, R.string.net_work_request_str);
            AccountRequest request = new AccountRequest();
            request.saveAccount(handler, info);
        }
    }

    /**
     * 检测数据
     * 
     * @param info
     */
    private boolean checkRecord(AccountInfo info)
    {
        String startmileage = info.startmileage;
        String endmileage = info.endmileage;
        if (null != startmileage && !"".equals(startmileage) && null != endmileage && !"".equals(endmileage))
        {
            int start = Integer.parseInt(startmileage);
            int end = Integer.parseInt(endmileage);
            if (start > end)
            {
                showDialog(getString(R.string.account_check_start_end_str));
                return false;
            }
        }
        return true;
    }

    /**
     * update
     */
    private void updateAccount()
    {
        AccountInfo info = new AccountInfo();
        info.accountid = accountid + "";
        info.accountbase = accountBaseType;
        info.usercarid = usercarid;
        info.accounttype = accounttype;
        info.address = address_edit.getText().toString();
        info.allmileage = allmileage_edit.getText().toString();
        info.allprice = allprice_edit.getText().toString();
        info.endmileage = endmileage_edit.getText().toString();
        info.fittingcosts = fittingcosts_edit.getText().toString();
        info.laborcosts = laborcosts_edit.getText().toString();
        info.numbers = numbers_edit.getText().toString();
        info.oil = oilType;
        info.pricetime = starttime;
        info.pricetype = "1";
        info.remark = remark_edit.getText().toString();
        info.startmileage = startmileage_edit.getText().toString();
        info.unitprice = unitprice_edit.getText().toString();
        if (checkRecord(info))
        {
            showNetDialog(R.string.tips_str, R.string.net_work_request_str);
            AccountRequest request = new AccountRequest();
            request.updateAccount(handler, info);
        }
    }

    /**
     * 隐藏所有的控件
     */
    private void hideAll()
    {
        layout_2.setVisibility(View.GONE);
        layout_3.setVisibility(View.GONE);
        layout_4.setVisibility(View.GONE);
        layout_5.setVisibility(View.GONE);
        layout_6.setVisibility(View.GONE);
        layout_7.setVisibility(View.GONE);
        layout_8.setVisibility(View.GONE);
        layout_9.setVisibility(View.GONE);
        layout_10.setVisibility(View.GONE);
        layout_11.setVisibility(View.GONE);
        layout_12.setVisibility(View.GONE);
        layout_13.setVisibility(View.GONE);
        layout_14.setVisibility(View.GONE);
        layout_15.setVisibility(View.GONE);
        layout_16.setVisibility(View.GONE);
        real_messsage_btn.setVisibility(View.GONE);
        wash_spinner.setVisibility(View.GONE);
        photo_layout.setVisibility(View.GONE);
    }

    /**
     * 显示Type1
     */
    private void showType1()
    {
        layout_2.setVisibility(View.VISIBLE);
        layout_3.setVisibility(View.VISIBLE);
        layout_4.setVisibility(View.VISIBLE);
        layout_5.setVisibility(View.VISIBLE);
        layout_6.setVisibility(View.VISIBLE);
        layout_7.setVisibility(View.VISIBLE);
        layout_14.setVisibility(View.VISIBLE);
    }

    /**
     * 显示Type2
     */
    private void showType2()
    {
        layout_2.setVisibility(View.VISIBLE);
        layout_12.setVisibility(View.VISIBLE);
        layout_14.setVisibility(View.VISIBLE);
        wash_spinner.setVisibility(View.VISIBLE);
    }

    /**
     * 显示Type2
     */
    private void showType3()
    {
        layout_2.setVisibility(View.VISIBLE);
        layout_14.setVisibility(View.VISIBLE);
    }

    /**
     * 显示Type3
     */
    private void showType4()
    {
        layout_2.setVisibility(View.VISIBLE);
        layout_8.setVisibility(View.VISIBLE);
        layout_9.setVisibility(View.VISIBLE);
        photo_layout.setVisibility(View.VISIBLE);
        layout_14.setVisibility(View.VISIBLE);
    }

    /**
     * 显示Type1
     */
    private void showType5()
    {
        layout_2.setVisibility(View.VISIBLE);
        layout_11.setVisibility(View.VISIBLE);
        real_messsage_btn.setVisibility(View.VISIBLE);
        photo_layout.setVisibility(View.VISIBLE);
        layout_14.setVisibility(View.VISIBLE);
    }

    /**
     * 显示Type5
     */
    private void showType6()
    {
        layout_2.setVisibility(View.VISIBLE);
        layout_14.setVisibility(View.VISIBLE);
        real_messsage_btn.setVisibility(View.VISIBLE);
    }

    /**
     * 显示Type1
     */
    private void showType7()
    {
        layout_2.setVisibility(View.VISIBLE);
        layout_13.setVisibility(View.VISIBLE);
        layout_14.setVisibility(View.VISIBLE);
    }

    /**
     * 显示Type1
     */
    private void showType8()
    {
        layout_2.setVisibility(View.VISIBLE);
        layout_13.setVisibility(View.VISIBLE);
        layout_14.setVisibility(View.VISIBLE);
    }

    /**
     * 显示Type1
     */
    private void showType9()
    {
        layout_2.setVisibility(View.VISIBLE);
        layout_13.setVisibility(View.VISIBLE);
        layout_14.setVisibility(View.VISIBLE);
    }

    /**
     * 显示Type1
     */
    private void showType10()
    {
        layout_2.setVisibility(View.VISIBLE);
        layout_12.setVisibility(View.VISIBLE);
        layout_14.setVisibility(View.VISIBLE);
    }

    /**
     * 显示Type11
     */
    private void showType11()
    {
        layout_2.setVisibility(View.VISIBLE);
        layout_15.setVisibility(View.VISIBLE);
        layout_14.setVisibility(View.VISIBLE);
    }

    /**
     * 显示Type1
     */
    private void showType12()
    {
        layout_2.setVisibility(View.VISIBLE);
        layout_12.setVisibility(View.VISIBLE);
        layout_14.setVisibility(View.VISIBLE);
    }

    /*
     * (non-Javadoc)
     * @see android.widget.AdapterView.OnItemSelectedListener#onItemSelected(android .widget.AdapterView,
     * android.view.View, int, long)
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        switch (parent.getId())
        {
            case R.id.type_spinner:
                accounttype = typeidarray[position] + "";
                if (position == 9)
                {
                    // 发送规费请求
                    detail_str = "3";
                    getAccountBaseInfo(detail_str);
                }
                else if (position == 3)
                {
                    detail_str = "1";
                }
                else if (position == 4)
                {
                    detail_str = "2";
                }
                else if (position == 1)
                {
                    detail_str = "4";
                    getAccountBaseInfo(detail_str);
                }
                hideAll();
                switchShow(position);
                break;
            case R.id.oil_spinner:
                oilType = oilidarray[position];
                break;
            case R.id.accounttype_spinner:
                accountBaseType = accountBaseIdArray[position];
                break;
            case R.id.wash_spinner:
                washId = washBaseIdArray[position] + "";
                break;
            case R.id.top_spinner:
                usercarid = carList.get(position).get("ID") + "";
                break;
            default:
                break;
        }
    }

    /**
     * 类别Handler
     */
    private Handler typeHandler = new Handler()
    {

        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            int what = msg.what;
            Object object = msg.obj;
            switch (what)
            {
                case FusionCode.RETURN_JSONOBJECT:
                    Map<String, Object> map = ResponsePaseUtil.getInstance().parseResponse((String) object);
                    int code = (Integer) map.get("code");
                    if (code > 0)
                    {
                        if (detail_str.equals("3"))
                        {
                            initAccountBase((List<Map<String, Object>>) map.get("objlist"));
                        }
                        else if (detail_str.equals("4"))
                        {
                            initWashAdapter((List<Map<String, Object>>) map.get("objlist"));
                        }
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

    /**
     * 类别Handler
     */
    private Handler typeHandler1 = new Handler()
    {

        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            int what = msg.what;
            Object object = msg.obj;
            switch (what)
            {
                case FusionCode.RETURN_JSONOBJECT:
                    Map<String, Object> map = ResponsePaseUtil.getInstance().parseResponse((String) object);
                    int code = (Integer) map.get("code");
                    if (code > 0)
                    {
                        List<Map<String, Object>> l = (List<Map<String, Object>>) map.get("objlist");

                        showDetail(l);
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

    /**
     * 初始化AccountBaseInfo
     * 
     * @param list 返回的类别
     */
    private void initAccountBase(List<Map<String, Object>> list)
    {
        int size = list.size();
        accountBaseArray = new String[size];
        accountBaseIdArray = new String[size];
        for (int i = 0; i < size; i++)
        {
            Map<String, Object> map = list.get(i);
            accountBaseArray[i] = map.get("NAME") + "";
            accountBaseIdArray[i] = map.get("ID") + "";
        }

        accountBaseAdapter = new ArrayAdapter<String>(this, R.layout.myspinner_item, accountBaseArray);
        accountBaseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 设置下拉样式
        accounttype_spinner.setAdapter(accountBaseAdapter);
        accounttype_spinner.setOnItemSelectedListener(this);
    }

    /**
     * 增加保养
     */
    private void initWashAdapter(List<Map<String, Object>> list)
    {

        int size = list.size();
        washBaseArray = new String[size];
        washBaseIdArray = new String[size];
        int selectid = 0;
        for (int i = 0; i < size; i++)
        {
            Map<String, Object> map = list.get(i);
            if (null != baselist && !baselist.isEmpty() && baselist.get(0).get("ID").equals(map.get("ID")))
            {
                selectid = i;
            }

            washBaseArray[i] = map.get("NAME") + "";
            washBaseIdArray[i] = map.get("ID") + "";
        }

        washAdapter = new ArrayAdapter<String>(this, R.layout.myspinner_item, washBaseArray);
        washAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 设置下拉样式
        wash_spinner.setAdapter(washAdapter);
        wash_spinner.setSelection(selectid);
        wash_spinner.setOnItemSelectedListener(this);

        // washAdapter = new ArrayAdapter<String>(this,
        // R.layout.myspinner_item);
        // washAdapter.add(getString(R.string.account_wash_str));
        // washAdapter
        // .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//
        // 设置下拉样式
        // wash_spinner.setAdapter(washAdapter);
        // wash_spinner.setOnItemSelectedListener(this);
    }

    /**
     * 展示列表
     * 
     * @param l 数据
     */
    protected void showDetail(List<Map<String, Object>> l)
    {
        AlertDialog dialog = null;
        Builder builder = new Builder(this);
        int size = l.size();
        items = new String[size];
        itemsid = new String[size];
        flags = new boolean[size];
        for (int i = 0; i < size; i++)
        {
            Map<String, Object> map = l.get(i);
            items[i] = (String) map.get("NAME");
            itemsid[i] = map.get("ID") + "";
            flags[i] = false;
        }

        builder.setMultiChoiceItems(items, flags, new DialogInterface.OnMultiChoiceClickListener()
        {

            public void onClick(DialogInterface dialog, int which, boolean isChecked)
            {
                flags[which] = isChecked;
                String result = "";
                for (int i = 0; i < flags.length; i++)
                {
                    if (flags[i])
                    {
                        result = result + itemsid[i] + "|";
                    }
                }
            }

        });

        // 添加一个确定按钮
        builder.setPositiveButton(getString(R.string.sure_str), new DialogInterface.OnClickListener()
        {

            public void onClick(DialogInterface dialog, int which)
            {

            }

        });
        dialog = builder.create();
        dialog.show();
    }

    /**
     * 获取Account基本信息
     * 
     * @param string 类别
     */
    private void getAccountBaseInfo(String string)
    {
        AccountRequest request = new AccountRequest();
        request.getAccountBaseInfo(typeHandler, string);
    }

    /**
     * 显示当前需要显示的
     */
    private void switchShow(int pos)
    {
        switch (pos)
        {
            case 0:
                showType1();
                break;
            case 1:
                showType2();
                break;
            case 2:
                // showType3();
                showType4();
                break;
            case 3:
                showType5();
                break;
            case 4:
                showType6();
                break;
            case 5:
                showType7();
                break;
            case 6:
                showType8();
                break;
            case 7:
                showType9();
                break;
            case 8:
                showType10();
                break;
            case 9:
                showType11();
                break;
            case 10:
                showType12();
                break;
            case 11:
                break;
        }
    }

    /*
     * (non-Javadoc)
     * @see android.widget.AdapterView.OnItemSelectedListener#onNothingSelected(android .widget.AdapterView)
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (event.getAction() == KeyEvent.ACTION_DOWN)
        {
            if (keyCode == KeyEvent.KEYCODE_BACK)
            {
                if (null != mPopupWindow && mPopupWindow.isShowing())
                {
                    mPopupWindow.dismiss();
                    return true;
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /*
     * (non-Javadoc)
     * @see android.view.View.OnFocusChangeListener#onFocusChange(android.view.View, boolean)
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus)
    {
        String allprice = allprice_edit.getText().toString();
        String unitprice = unitprice_edit.getText().toString();
        String number = numbers_edit.getText().toString();
        Log.v(TAG, "allprice = " + allprice + " unitprice = " + unitprice + " number = " + number);
        switch (v.getId())
        {
            case R.id.allprice_edit:
                if (!allprice.equals(""))
                {
                    if (!unitprice.equals("") && !unitprice.equals("0"))
                    {
                        numbers_edit.setText(formartNum(Double.parseDouble(allprice)
                            / Double.parseDouble(unitprice))
                            + "");
                    }
                    else if (!number.equals("") && !number.equals("0"))
                    {
                        unitprice_edit.setText(formartNum(Double.parseDouble(allprice)
                            / Double.parseDouble(number))
                            + "");
                    }
                }
                break;
            case R.id.unitprice_edit:
                if (!unitprice.equals("") && !unitprice.equals("0"))
                {
                    if (!allprice.equals(""))
                    {
                        numbers_edit.setText(formartNum(Double.parseDouble(allprice)
                            / Double.parseDouble(unitprice))
                            + "");
                    }
                    else if (!number.equals(""))
                    {
                        allprice_edit.setText(formartNum(Double.parseDouble(number)
                            * Double.parseDouble(unitprice))
                            + "");
                    }
                }
                break;
            case R.id.numbers_edit:
                // if (!numbers_edit.getText().toString().equals("")) {
                // if (!unitprice_edit.getText().toString().equals("")) {
                // allprice_edit.setText(formartNum(Double
                // .parseDouble(numbers_edit.getText().toString())
                // * Double.parseDouble(unitprice_edit.getText()
                // .toString()))
                // + "");
                // } else if (!allprice_edit.getText().toString().equals("")) {
                // unitprice_edit.setText(formartNum(Double
                // .parseDouble(allprice_edit.getText().toString())
                // / Double.parseDouble(numbers_edit.getText()
                // .toString()))
                // + "");
                // }
                // }
                break;
        }
    }

    private void uploadFile(String name, byte[] data)
    {
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        try
        {
            String actionUrl = Variable.SERVER_SOFT_URL + "/servlet/UploadImg";
            // String actionUrl = "http://192.168.8.105:8080/cars/servlet/UploadImg";
            URL url = new URL(actionUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            /* 允许Input、Output，不使用Cache */
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            /* 设置传送的method=POST */
            con.setRequestMethod("POST");
            /* setRequestProperty */
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");
            con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            /* 设置DataOutputStream */
            DataOutputStream ds = new DataOutputStream(con.getOutputStream());
            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; " + "name=\"file1\";filename=\"" + name + "\""
                + end);
            ds.writeBytes(end);
            /* 取得文件的FileInputStream */

            InputStream fStream = new ByteArrayInputStream(data);
            // FileInputStream fStream =new FileInputStream(byte[]);
            /* 设置每次写入1024bytes */
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = -1;
            /* 从文件读取数据至缓冲区 */
            while ((length = fStream.read(buffer)) != -1)
            {
                /* 将资料写入DataOutputStream中 */
                ds.write(buffer, 0, length);
            }
            ds.writeBytes(end);
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
            /* close streams */
            fStream.close();
            ds.flush();

            /* 取得Response内容 */
            InputStream is = con.getInputStream();
            int ch;
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1)
            {
                b.append((char) ch);
            }
            /* 将Response显示于Dialog */
            closeNetDialog();
            showDialog("上传成功");
            /* 关闭DataOutputStream */
            ds.close();
        }
        catch (Exception e)
        {
            closeNetDialog();
            showDialog("照片上传失败");
        }
    }
}
