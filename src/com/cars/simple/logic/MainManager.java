package com.cars.simple.logic;

import java.io.IOException;
import java.util.HashMap;
import java.util.Timer;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;

import com.cars.simple.activity.BaseActivity;
import com.cars.simple.fusion.Variable;
import com.cars.simple.service.baseData.RawTemplate;
import com.cars.simple.service.http.cookie.CookieManager;

/************************************************************************************************
 * 文件名称: MainManager.java 文件描述: 程序的主逻辑控制类 作 者： fushangbin 创建时间: 2011-11-11下午1:52:31 备 注:
 ************************************************************************************************/
public class MainManager
{

    // private final String TAG = "==MainManager==";

    private static MainManager instance = null;

    /**
     * 是否有sd卡
     */
    public static boolean sdCardIsExist = false;

    /**
     * 全局唯一的定时器
     */
    public static Timer timer = new Timer();

    /**
     * 保存当前的Activity
     */
    public static BaseActivity currentActivity;

    private MainManager()
    {
    }

    public static MainManager getInstance()
    {
        if (instance == null)
        {
            instance = new MainManager();
        }
        return instance;
    }

    /****************************************************************************************
     * 函数名称：init 函数描述：初始化 输入参数：@param activity 输出参数： 返回 值：void 备 注：
     ****************************************************************************************/
    public static void init(Activity activity)
    {
        initSystemParams(activity);
        getDeviceId(activity);
        new RawTemplate(activity).initFile();
        try
        {
            HashMap<String, String> map = (HashMap<String, String>) new RawTemplate(activity).readFileUrl();
            Variable.SERVER_SOFT_URL = map.get("SERVER_SOFT_URL");
            Variable.SERVER_IMAGE_URL = map.get("SERVER_IMAGE_URL");
            Variable.SERVER_MEMBER_URL = map.get("SERVER_MEMBER_URL");
            Variable.SERVER_ACCOUNT_IMAGE_URL = map.get("SERVER_ACCOUNT_IMAGE_URL");
            // new RawTemplate(activity).initFile();
            // IPersistProxy persist = new PersistProxy(activity);
            // boolean isInitVersion = false;
            // SysConfProcessor confProcessor = new SysConfProcessor();
            // String value = confProcessor.getValue("version", activity);
            // if (value != null && !value.equals("")) {
            // Variable.SYS_VERSION = Integer.parseInt(value);
            // } else {
            // isInitVersion = true;
            // }
            // String soft = confProcessor.getValue("soft", activity);
            // if (null != soft && !soft.equals("")) {
            // Variable.SOFT = Integer.parseInt(soft);
            // }
            //
            // if (isInitVersion) {
            // SysConf sysConf = new SysConf();
            // sysConf.setKey("version");
            // sysConf.setValue(Variable.SYS_VERSION + "");
            // sysConf.save(persist);
            // }
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            {
                sdCardIsExist = true;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void getDeviceId(Activity context)
    {

        TelephonyManager telephonyManager =
            (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        /*
         * getDeviceId() function Returns the unique device ID. for example,the IMEI for GSM and the MEID or ESN for
         * CDMA phones.
         */
        Variable.DEVICE_ID = telephonyManager.getDeviceId();
    }

    /****************************************************************************************
     * 函数名称：initSystemParams 函数描述：初始化系统参数 输入参数：@param activity 输出参数： 返回 值：void 备 注：
     ****************************************************************************************/
    public static void initSystemParams(Activity activity)
    {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        Variable.Size.SCREEN_WIDTH = screenWidth;
        Variable.Size.SCREEN_HEIGHT = screenHeight;
        Variable.Size.density = dm.density;
    }

    /****************************************************************************************
     * 函数名称：getAppVersionName 函数描述：返回当前程序版本名 输入参数：@param context 输入参数：@return 输出参数： 返回 值：String 备 注：
     ****************************************************************************************/
    public static String getAppVersionName(Context context)
    {
        String versionName = "";
        try
        {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0)
            {
                return "";
            }
        }
        catch (Exception e)
        {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    /**
     * 获取当前的版本信息
     * 
     * @param context 上下文
     * @return 当前的版本号
     */
    public static int getAppVersion(Context context)
    {
        int currentVersion = 0;
        PackageManager pm = context.getPackageManager();
        try
        {
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            currentVersion = pi.versionCode;
        }
        catch (NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return currentVersion;
    }

    /****************************************************************************************
     * 函数名称：releaseUserInfo 函数描述：释放用户信息，在注销和退出程序时调用 输入参数： 输出参数： 返回 值：void 备 注：
     ****************************************************************************************/
    public void releaseUserInfo()
    {
        Variable.Session.USER_ID = null;
        Variable.Session.USER_NAME = null;
        Variable.Session.USER_BRAND = null;
        Variable.Session.CITY_NAME = null;
        Variable.Session.USER_BALANCE = null;
        Variable.Session.IS_LOGIN = false;
        Variable.Session.isJfLogin = false;
        Variable.Session.isJfValid = false;
        CookieManager.getInstance().clearCookie();
    }

}
