/**
 * 
 */
package com.cars.simple.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.cars.simple.R;
import com.cars.simple.fusion.Variable;
import com.cars.simple.util.Util;

/**
 * @author zhuyujie
 * 
 */
public class MemberActivity extends BaseActivity
{

    /**
     * TAG
     */
    private final static String TAG = MemberActivity.class.getSimpleName();

    /**
     * Webview
     */
    private WebView webview = null;

    /*
     * (non-Javadoc)
     * @see com.cars.activity.BaseActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_activity);

        showTop(getString(R.string.cars_home_8_str), null);
        
        if (!Variable.Session.IS_LOGIN)
        {
            skipLogin();
            return;
        }
       
        
        webview = (WebView) findViewById(R.id.webview);
        String url =
            Variable.SERVER_MEMBER_URL + "getHuiYuanHuodongList.jspx?usercarid=" + Variable.Session.USERCARID
                + "&userid=" + Variable.Session.USER_ID;

        Log.v(TAG, "url = " + url);
        webview.getSettings().setJavaScriptEnabled(true);  
		webview.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
			}
		});
        webview.loadUrl(url);  
        
        if(Variable.Session.USERCARID == null || "".equals(Variable.Session.USERCARID)){
        	showNoCarDialog("您还没有添加车辆，请先添加车辆！");
        }
        //webview.loadDataWithBaseURL(url, null, "text/html", "utf-8", null);
    }
    
    @Override  
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {  
            //goBack()返回WebView的上一层页面  
        	webview.goBack();  
            return true;  
        }  else{
        	finish();
        }
        return false;  
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
					intent.setClass(MemberActivity.this,
							CarInfoUpdateActivity.class);
					startActivity(intent);
	            }
	        });
	        dialog.show();
	    }
	}
}
