package com.cars.simple.service.download;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import android.content.Context;
import android.os.Handler;

/**
 * 下载图片
 * 
 * @author shifeng
 * 
 */
public class ImageTask extends Task<ImageObj> {

	private final static String TAG = ImageTask.class.getSimpleName();

	private String url = null;
	private String name = null;

	private Handler handler = null;

	private Map<String, Object> map = null;

	public ImageTask(Context activity, TaskListener listener, ImageObj image) {
		super(activity, listener);
		this.url = image.getUrl();
		this.name = image.getName();
		this.handler = image.getHandler();
		this.map = image.getMap();
	}

	@Override
	public ImageObj process() throws Exception {
		ImageObj obj = new ImageObj();
		try {
			// HttpRsp rsp = new MyConnection().getWithSession(new HttpReq(url,
			// new byte[] {}));
			// if (rsp != null && rsp.getStatusCode() > 0) {
			// obj.setImg(rsp.getMsg());
			// obj.setName(name);
			// }
			obj.setImg(getImage(url));
			obj.setName(name);
			obj.setHandler(handler);
			obj.setMap(map);
		} catch (Exception e) {
		}
		return obj;
	}

	// 声明称为静态变量有助于调用
	public byte[] getImage(String path) throws Exception {
		URL url = new URL(path);
		// 记住使用的是HttpURLConnection类
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		// 如果运行超过5秒会自动失效 这是android规定
		conn.setConnectTimeout(5 * 1000);
		InputStream inStream = conn.getInputStream();
		// 调用readStream方法
		return readStream(inStream);
	}

	public byte[] readStream(InputStream inStream) throws Exception {
		// 把数据读取存放到内存中去
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		return outSteam.toByteArray();
	}

}
