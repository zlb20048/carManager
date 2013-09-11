/**
 * 
 */
package com.cars.simple.logic;

import android.os.Handler;

import com.cars.simple.fusion.Variable;
import com.cars.simple.service.request.Request;

/**
 * @author liuzixiang
 * 
 */
public class UpdateRequest
{

    /**
     * 检测版本更新
     * 
     * @param handler 回调方法
     * @param currentVersion 当前的版本信息
     */
    public void checkUpdate(Handler handler, int currentVersion)
    {
        // http://localhost:8080/cars/checkVersion.jspx?type=0
        Request request = new Request();
        String url = Variable.SERVER_SOFT_URL + "/checkVersion.jspx?type=" + currentVersion;
        request.setHandler(handler);
        request.sendGetRequest(url);
    }

    /**
     * 访问次数
     */
    public void visitorCount(Handler handler)
    {
        // http://cgj.jlonline.cn/visitCount.jspx?username=11231131
        Request request = new Request();
        String url = Variable.SERVER_SOFT_URL + "/visitCount.jspx?username=" + Variable.DEVICE_ID;
        request.setHandler(handler);
        request.sendGetRequest(url);
    }
}
