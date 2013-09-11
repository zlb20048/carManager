package com.cars.simple.logic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;

import com.cars.simple.R;
import com.cars.simple.activity.HomeAcitivity;
import com.cars.simple.fusion.Variable;

public class Installer {

	private final static String TAG = "Installer";

	public Context context;

	private File myTempFile;

	/**
	 * 通知管理器
	 */
	private NotificationManager manager;

	/**
	 * 当前的Notification的下标
	 */
	private int currentNotificationIndex = 0;
	// 当前进度条里的进度值
	public static int progress = 0;
	private RemoteViews view = null;
	private Notification notification = new Notification();
	private Intent intent = null;
	private PendingIntent pIntent = null;// 更新显示

	public Installer(Context context) {
		this.context = context;
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (progress < 100) {
				view.setProgressBar(R.id.pb, 100, progress, false);
				view.setTextViewText(R.id.tv, "下载" + progress + "%");// 关键部分，如果你不重新更新通知，进度条是不会更新的
				// notification.contentView = view;
				// notification.contentIntent = pIntent;
				manager.notify(currentNotificationIndex, notification);
			} else {
				cancelNotification();
			}
			super.handleMessage(msg);
		}
	};

	public void install(final String url) {
		manager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		view = new RemoteViews(context.getPackageName(),
				R.layout.download_notify);
		intent = new Intent(context, HomeAcitivity.class);
		pIntent = PendingIntent.getService(context, 0, intent, 0);
		// 通知的图标必须设置(其他属性为可选设置),否则通知无法显示
		notification.icon = android.R.drawable.stat_sys_download;
		view.setImageViewResource(R.id.image,
				android.R.drawable.stat_sys_download);// 起一个线程用来更新progress
		notification.contentView = view;
		notification.contentIntent = pIntent;
		manager.notify(currentNotificationIndex, notification);

		new Thread() {
			public void run() {
				String tempPath = null;
				try {
					if (Environment.getExternalStorageState().equals(
							Environment.MEDIA_MOUNTED)) {
						Message msg = new Message();
						handler.sendMessage(msg);
						File sdCardDir = Environment
								.getExternalStorageDirectory();
						myTempFile = new File(sdCardDir, "ecmc.apk");
						if (myTempFile.exists()) {
							myTempFile.delete();
						}
						tempPath = myTempFile.getAbsolutePath();
						// 提示页面正在下载，需要更改
						// context.postMsg("1");
						String tempUrl = url;
						// int contentBeginIdx = 0;
						// if (Variable.NETWORK.equals("cmwap")) {
						// contentBeginIdx = url.indexOf('/', 7);
						// tempUrl = "http://10.0.0.172:80"
						// + url.substring(contentBeginIdx);
						// }
						URL myUrl = new URL(tempUrl);
						URLConnection conn = null;
						conn = (HttpURLConnection) myUrl.openConnection();
						conn.setRequestProperty("X-Online-Host", ""
								+ getHostDomain(url));
						Log.i("conn = ", "conn...");
						conn.connect();

						Log.i("connect = ", "connect...");

						InputStream is = conn.getInputStream();
						String contentLength = conn
								.getHeaderField("Content-Length");
						Log.i("####", "contentLength=" + contentLength);
						long length = Long.parseLong(contentLength);
						String command = "chmod 777 " + tempPath;
						Log.i("is = ", "is...");
						Runtime runtime = Runtime.getRuntime();
						runtime.exec(command);
						Log.i("is = ", "is...");
						if (!myTempFile.exists()) {
							myTempFile.createNewFile();
						}
						FileOutputStream fos = new FileOutputStream(tempPath);
						byte[] buf = new byte[1024 * 5];
						long temp = 0;
						do {
							Variable.Session.IS_DOWNLOADING = true;
							int numread = is.read(buf);
							temp += numread;
							if (numread <= 0) {
								break;
							}
							progress = (int) (temp * 100 / length);
							fos.write(buf, 0, numread);
							fos.flush();
							Thread.sleep(10);
						} while (true);
						is.close();
						fos.close();
						Variable.Session.IS_DOWNLOADING = false;
						Log.i("downloading", "downloading Finished...");

						// delete 资源
						// new RawTemplate(context).deleteAppRaw();

						Log.i("deleteAppRaw", "deleteAppRaw...");

						progress = 100;
						msg = new Message();
						handler.sendMessage(msg);

						Log.i(TAG, "installing...");

						Intent intent = new Intent();
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						intent.setAction(android.content.Intent.ACTION_VIEW);
						String type = "application/vnd.android.package-archive";
						intent.setDataAndType(Uri.fromFile(myTempFile), type);
						context.startActivity(intent);
						// context.finish();
						// android.os.Process.killProcess(android.os.Process
						// .myPid());
					}
				} catch (Exception e) {
					Log.e(this.toString(), "" + e.getMessage());
				}
			}
		}.start();
		new Thread() {
			public void run() {
				while (progress <= 100) {
					Message msg = new Message();
					handler.sendMessage(msg);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				Message msg = new Message();
				handler.sendMessage(msg);
			}
		}.start();
	}

	/**
	 * 删除显示的Notification
	 */
	private void cancelNotification() {
		manager.cancel(currentNotificationIndex);
	}

	/**
	 * 获取url 的主机域名
	 * 
	 * @param url
	 * @return
	 */
	private String getHostDomain(String url) {
		if (url.indexOf('/', 7) == -1) {
			return url.substring(0);
		}
		return url.substring(7, url.indexOf('/', 7));
	}
}
