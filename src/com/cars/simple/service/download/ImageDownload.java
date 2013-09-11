package com.cars.simple.service.download;

import java.io.IOException;
import java.util.Map;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.cars.simple.fusion.FusionCode;
import com.cars.simple.service.baseData.RawTemplate;

/**
 * 
 * @author shifeng
 * 
 */
public class ImageDownload implements TaskListener {

	private final static String TAG = ImageDownload.class.getSimpleName();

	private Context context;

	public ImageDownload(Context context) {
		this.context = context;
	}

	// protected ImageDownload() {
	// };

	public void getImg(String imgUrl, String name, Handler handler) {
		TaskExecutor executor = new TaskExecutor();
		ImageObj obj = new ImageObj();
		obj.setUrl(imgUrl);
		obj.setName(name);
		obj.setHandler(handler);
		Task<?> task = new ImageTask(context, this, obj);
		executor.execute(task);
	}

	public void getImg(String imgUrl, String name, Map<String, Object> map,
			Handler handler) {
		TaskExecutor executor = new TaskExecutor();
		ImageObj obj = new ImageObj();
		obj.setUrl(imgUrl);
		obj.setName(name);
		obj.setMap(map);
		obj.setHandler(handler);
		Task<?> task = new ImageTask(context, this, obj);
		executor.execute(task);
	}

	public void taskStarted(Task<?> task) {
		// Log.d(Constant.LOG_KEY+this.toString(), "ImageDownload taskStarted");
	}

	public void taskProgress(Task<?> task, long value, long max) {
		// Log.d(Constant.LOG_KEY+this.toString(),
		// "ImageDownload taskProgress");
	}

	public void taskCompleted(Task<?> task, Object obj) {
		if (obj != null) {
			ImageObj img = (ImageObj) obj;
			byte[] data = img.getImg();
			if (data != null) {
				String name = img.getName();
				if (name != null) {
					RawTemplate raw = new RawTemplate(context);
					try {
						raw.saveToDefault(data, name);
					} catch (IOException e) {
					}
					// 通知页面刷新图片
					Message msg = new Message();
					msg.what = FusionCode.DOWNLOAD_IMAGE_FINISH;
					msg.obj = img;
					Handler handler = img.getHandler();
					// 当Handler为空的时候不需要返回更新界面
					if (null != handler) {
						handler.sendMessage(msg);
					}
				}
			}
		}

	}

	public void taskFailed(Task<?> task, Throwable ex) {
		// Log.d(Constant.LOG_KEY+this.toString(), "ImageDownload taskFailed");
	}

}
