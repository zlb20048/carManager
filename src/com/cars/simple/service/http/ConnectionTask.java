package com.cars.simple.service.http;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpStatus;
import org.apache.http.protocol.HTTP;

import android.os.Handler;
import android.util.Log;

import com.cars.simple.fusion.FusionCode;
import com.cars.simple.fusion.Variable;
import com.cars.simple.logic.MainManager;
import com.cars.simple.service.baseData.FileHelper;
import com.cars.simple.service.baseData.SDNotEnouchSpaceException;
import com.cars.simple.service.baseData.SDUnavailableException;
import com.cars.simple.service.http.cookie.CookieManager;
import com.cars.simple.service.threadpool.TaskObject;
import com.cars.simple.util.JSONArray;
import com.cars.simple.util.JSONException;
import com.cars.simple.util.JSONObject;
import com.cars.simple.util.Util;

/************************************************************************************************
 * 文件名称: ConnectionTask.java 文件描述: http 连接请求任务类 作 者： fushangbin 创建时间:
 * 2011-11-10下午4:39:40 备 注:
 ************************************************************************************************/
public class ConnectionTask implements TaskObject {

	/**
	 * 代理地址
	 */
	public static final String proxyURL = "http://10.0.0.172:80";

	/**
	 * 代理端口
	 */
	public static final int HOST_PORT = 80;

	/**
	 * 普通的json消息连接请求
	 */
	public static final int CONNECT_TYPE_JSON = 0;

	/**
	 * 业务下载连接任务
	 */
	public static final int CONNECT_TYPE_DOWNLOAD = 1;

	/**
	 * 下载图片任务
	 */
	public static final int CONNECT_TYPE_BITMAP = 2;

	/**
	 * 处理返回xml信息
	 */
	public static final int CONNECT_TYPE_XML = 3;

	/**
	 * 处理返回STRING
	 */
	public static final int CONNECT_TYPE_BYTES = 4;

	/**
	 * post请求定义
	 */
	public static final int POST = 0;

	/**
	 * get请求定义
	 */
	public static final int GET = 1;

	/**
	 * 日志名
	 */
	private static final String TAG = "--- ConnectionTask ---";

	/**
	 * 数据缓冲区的大小
	 */
	private static final int DATA_BUFFER_LEN = 512;

	/**
	 * 是否已经超时
	 */
	private boolean isTimeOut = false;

	/**
	 * 是否已经被取消的标志
	 */
	private boolean canceled = false;

	/**
	 * 是否已被暂停的标志
	 */
	private boolean paused = false;

	/**
	 * 请求类型，默认为POST
	 */
	private int requestType = POST;

	/**
	 * 超时定时器
	 */
	private Timer timer;

	/**
	 * 超时定时器任务
	 */
	private TimerTask timerTask;

	/**
	 * 超时时间
	 */
	private int timeout = 30 * 1000;

	/**
	 * 事件处理句柄
	 */
	private Handler handler;

	/**
	 * 区分联网请求JSON的标志码
	 */
	private int fusionCode;

	/**
	 * post请求消息体
	 */
	private byte[] dataBuf = null;

	/**
	 * url地址
	 */
	private String httpUrl;

	public String oldUrl;

	/**
	 * 返回码
	 */
	private int responseCode;

	/**
	 * 联网回调接口
	 */
	private IHttpListener httpListener;

	/**
	 * 网络状态回调接口
	 */
	private IStatusListener statusListener;

	/**
	 * http连接对象
	 */
	private HttpURLConnection conn;

	/**
	 * 输入流
	 */
	private InputStream is = null;

	/**
	 * 当前的连接类型 json请求、下载应用、下载图片
	 */
	private int connectType = CONNECT_TYPE_JSON;

	/**
	 * 存放请求头信息的Hashtable
	 */
	private Hashtable<String, String> sendHead = null;

	/**
	 * 断点
	 */
	private long breakpoint = 0L;

	/**
	 * 当为下载任务时，标志任务下载是否完成
	 */
	private boolean isDownloadSuccess = false;

	/**
	 * 文件对象
	 */
	private RandomAccessFile file;

	/**
	 * SD卡回调同步锁
	 */
	private Object sdSyn = new Object();

	/**
	 * 文件上传的文件数组
	 */
	public String[] files = null;

	/**
	 * http 请求的content-type ，登录接口与检测版本升级的接口不一样
	 */
	public String contentType = FusionCode.HTTP_CONTENT_TYPE_FORM;

	/**
	 * 记录任务的时间戳
	 */
	private int taskTimeStamp;

	/**
	 * 默认构造方法
	 */
	public ConnectionTask() {
	}

	/**
	 * 连接商城服务器构造方法
	 * 
	 * @param handler
	 *            JSON或bitmap回调接口
	 * @param httpUrl
	 *            连接地址
	 */
	public ConnectionTask(IStatusListener statusListener, int timeout,
			Handler handler) {
		this.statusListener = statusListener;
		this.timeout = timeout;
		this.handler = handler;
		this.connectType = CONNECT_TYPE_JSON;
	}

	/**
	 * 下载构造方法
	 * 
	 * @param httpListener
	 *            联网回调接口
	 * @param httpUrl
	 *            连接地址
	 */
	public ConnectionTask(IHttpListener httpListener, String httpUrl,
			int timeout) {
		this.httpListener = httpListener;
		this.httpUrl = httpUrl;
		this.timeout = timeout;
		this.connectType = CONNECT_TYPE_DOWNLOAD;
		requestType = GET;
	}

	public ConnectionTask(IStatusListener statusListener, int timeout,
			Handler handler, int netType) {
		this.statusListener = statusListener;
		this.timeout = timeout;
		this.handler = handler;
		this.connectType = netType;
	}

	/**
	 * 请求图片构造方法
	 * 
	 * @param handler
	 *            JSON或bitmap回调接口
	 * @param httpUrl
	 *            连接地址
	 */
	public ConnectionTask(Handler handler, String httpUrl) {
		this.handler = handler;
		this.httpUrl = httpUrl;
		this.requestType = GET;
		this.connectType = CONNECT_TYPE_BITMAP;
	}

	/**
	 * 设置断点值
	 */
	public void setBreakPoint(long breakpoint) {
		this.breakpoint = breakpoint;
	}

	/**
	 * 断点续传时获取继续下载时的断点信息
	 * 
	 * @return 已经下载的数据量
	 */
	public long getBreakPoint() {
		return breakpoint;
	}

	/**
	 * 设置断点
	 */
	public void setBreakPointHeader() {
		if (sendHead == null) {
			sendHead = new Hashtable<String, String>();
		}
		StringBuffer breakPointStr = new StringBuffer("bytes=");
		breakPointStr.append(breakpoint);
		breakPointStr.append("-");

		sendHead.put("RANGE", breakPointStr.toString());
	}

	/**
	 * 取消连接任务
	 */
	public void cancelConnect() {
		canceled = true;
	}

	/**
	 * 判断用户是否已删除下载任务
	 * 
	 * @return 是否删除标志
	 */
	public boolean isCanceled() {
		return canceled;
	}

	/**
	 * 暂停连接任务
	 */
	public void pausedConnect() {
		paused = true;
	}

	/**
	 * 判断用户是否已暂停下载
	 * 
	 * @return 是否暂停下载标志
	 */
	public boolean isPaused() {
		return paused;
	}

	/**
	 * 运行联网任务
	 */
	public void runTask() {
		try {
			// 通知任务开始下载
			if (connectType == CONNECT_TYPE_DOWNLOAD) {
				isDownloadSuccess = false;
				startDownloadCallback();
			}
			// 执行网络连接操作（发送HTTP请求，并处理网络返回数据）
			connetionProcess();
			// 如果下载成功，回调到上层处理
			if (connectType == CONNECT_TYPE_DOWNLOAD && isDownloadSuccess) {
				successDownloadCallback();
			}
		} catch (SecurityException se) {
			hanlderException(se);
		} catch (InterruptedIOException e) {
			// 在connetionProcess()方法执行中或读写流中或打开连接时，打断会抛此异常
			if (canceled) {
				canceledCallback();
			} else if (paused) {
				pausedCallback();
			} else if (isTimeOut) {
				// do nothing
			} else {
				hanlderException(e);
			}
		} catch (InterruptedException e) {
			// 暂停取消时的打断异常
			if (canceled) {
				canceledCallback();
			} else if (paused) {
				pausedCallback();
			} else if (isTimeOut) {
				// do nothing
			} else {
				hanlderException(e);
			}
		} catch (SocketException e) {
			// 无网络时会抛出该异常
			hanlderException(e);
		} catch (UnsupportedEncodingException e) {
			setConnError(responseCode, e.getMessage());
		} catch (JSONException e) {
			setConnError(responseCode, e.getMessage());
		} catch (IOException e) {
			// 服务器响应异常会抛出该异常
			hanlderException(e);
		} catch (Exception e) {
			// 其他异常
			hanlderException(e);
		} catch (Error e) {
			// 错误处理
			if (connectType == CONNECT_TYPE_DOWNLOAD) {
				if (!isTimeOut) {
					setError(responseCode, e.toString());
				}
			} else {
				setConnError(responseCode, e.toString());
			}
		} finally {
			clearNet();
		}
	}

	/**
	 * 异常处理
	 * 
	 * @param exception
	 *            异常对象
	 */
	private void hanlderException(Exception exception) {
		if (connectType == CONNECT_TYPE_DOWNLOAD) {
			setError(responseCode, exception.toString());
		} else {
			setConnError(responseCode, exception.toString());
		}
	}

	/**
	 * 实现了联网写读功能
	 * 
	 * @throws Exception
	 *             异常类
	 * @throws Error
	 *             错误类
	 */
	private void connetionProcess() throws Exception, Error {
		try {
			// 构建连接
			Log.i(TAG, "http.url=" + httpUrl);
			String tempUrl = sysEncode(httpUrl);
			int contentBeginIdx = 0;
			if (Variable.net_proxy) {
				contentBeginIdx = httpUrl.indexOf('/', 7);
				tempUrl = proxyURL + httpUrl.substring(contentBeginIdx);
			}
			Log.i(TAG, "tempUrl=" + tempUrl);
			URL url = new URL(tempUrl);
			Log.i(TAG, "http.url=" + httpUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(timeout);
			HttpURLConnection.setFollowRedirects(true);
			// 不使用Cache
			conn.setUseCaches(false);
			// 设置请求类型
			if (requestType == POST) {
				conn.setRequestMethod("POST");
			} else {
				conn.setRequestMethod("GET");
			}
			if (sendHead != null && sendHead.containsKey("RANGE")) {
				conn.addRequestProperty("RANGE", (String) sendHead.get("RANGE"));
			}
			conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
			conn.setRequestProperty("X-Online-Host", ""
					+ getHostDomain(httpUrl));
			conn.addRequestProperty("User-Agent", Variable.UA);
			conn.setRequestProperty("Accept-Encoding", "gzip");
			conn.setRequestProperty("source", "2");
			String cookie = CookieManager.getInstance().getCookie(httpUrl);
			if (null != cookie) {
				Log.i("==addcookie==", "addcookie=" + cookie);
				conn.addRequestProperty("cookie", cookie);
			}
			// 以内容实体方式发送请求参数
			if (requestType == POST && dataBuf != null) {
				// 发送POST请求必须设置允许输出
				conn.setDoOutput(true);
				conn.setDoInput(true);
				// 维持长连接
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("Charset", "UTF-8");
				conn.setRequestProperty("Content-Length",String.valueOf(dataBuf.length));
				conn.setRequestProperty("Content-Type", contentType);
				conn.setRequestProperty("source", "2");
				// 开始写入数据
				DataOutputStream outStream = new DataOutputStream(
						conn.getOutputStream());
				outStream.write(dataBuf);
				outStream.flush();
				outStream.close();
				if (canceled || paused || isTimeOut) {
					throw new InterruptedException();
				}
			}
			responseCode = conn.getResponseCode();
			Log.i(TAG, "responseCode=" + responseCode);
			// 请求状态
			switch (responseCode) {
			case HttpStatus.SC_OK:
				break;
			case HttpStatus.SC_PARTIAL_CONTENT:
				String contentType = conn.getHeaderField("Content-type");
				if (contentType != null
						&& (contentType.startsWith("text/vnd.wap.wml") || contentType
								.startsWith("application/vnd.wap.wmlc"))) {
					conn.disconnect();
					conn = null;
				}
				break;
			default:
				throw new IOException("Connection response status not OK:"
						+ responseCode);
			}
			if (canceled || paused || isTimeOut) {
				throw new InterruptedException();
			}
			for (int j = 1; conn.getHeaderFieldKey(j) != null; j++) {
				if (conn.getHeaderFieldKey(j).toLowerCase()
						.equals("set-cookie")) {
					Log.i(TAG, "setcookie=" + conn.getHeaderField(j));
					CookieManager.getInstance().setCookie(httpUrl,
							conn.getHeaderField(j));
				}
			}
			// 读取数据
			readData();
		} finally {
			// 关闭连接
			clearNet();
		}
	}

	/**
	 * 读网络数据
	 * 
	 * @throws IOException
	 *             IO异常类
	 * @throws InterruptedException
	 *             中断异常类
	 */
	private void readData() throws Exception, IOException,
			InterruptedException, UnsupportedEncodingException, JSONException {
		long fileLenght = (long) conn.getContentLength();
		// 获取网络数据输入流
		is = conn.getInputStream();
		switch (connectType) {
		// 请求图片
		case CONNECT_TYPE_BITMAP:
			Log.i(TAG, "return image");
			readBitmapData();
			break;
		// 下载请求
		case CONNECT_TYPE_DOWNLOAD:
			readDownloadData(fileLenght);
			break;
		// JSON请求
		case CONNECT_TYPE_JSON:
			readJsonData(fileLenght);
			break;
		// 返回xml信息
		case CONNECT_TYPE_XML:
			readInstallSuccessXML();
			break;
		// 直接返回byte[]
		case CONNECT_TYPE_BYTES:
			readGetData(fileLenght);
			break;
		default:
			break;
		}
	}

	/**
	 * 读取图片数据
	 */
	private void readBitmapData() throws IOException, InterruptedException {
		try {
			// 存储已经下载的长度
			long getLen = 0;
			// 存储每次从网络层读取到的数据
			// 临时数据缓冲区
			int buffLen = 0;
			byte[] buff = new byte[DATA_BUFFER_LEN];
			byte[] tempBuff = null;
			Thread.sleep(2000);
			// 创建文件
			createFile();
			while ((buffLen = is.read(buff)) != -1) {
				// 网络连接被暂停或取消，需要抛出中断异常，并关闭写文件线程
				if (canceled || paused || isTimeOut) {
					throw new InterruptedException();
				}
				// 如果没有读到数据就不需要写文件了
				if (buffLen <= 0) {
					continue;
				}
				// sd卡存在时将下载的数据写入文件
				if (MainManager.sdCardIsExist) {
					// 将读到的数据写入文件，写文件成功后通知上层回调下载进度
					tempBuff = new byte[buffLen];
					System.arraycopy(buff, 0, tempBuff, 0, buffLen);
					int len = FileHelper.writeFile(file, tempBuff);
					if (len == FileHelper.ERROR) {
						throw new InterruptedException();
					} else {
						getLen += len;
						// 设置下载量（百分比）
						// setDataLength(breakpoint + getLen, breakpoint +
						// dataLen);
					}
				}
				// sd卡不存在直接抛出异常
				else {
					throw new InterruptedException();
				}
			}
		} catch (IOException e) {
			throw new IOException();
		} catch (SDUnavailableException e) {
			throw new InterruptedException(e.getMessage());
		} catch (SDNotEnouchSpaceException e) {
			throw new InterruptedException(e.getMessage());
		}
		handler.sendMessage(handler.obtainMessage(FusionCode.RETURN_BITMAP,
				fusionCode, getTimeStamp(), null));
	}

	/**
	 * 读取JSON数据
	 * 
	 * @param dataLen
	 *            数据长度
	 * @throws IOException
	 *             抛出IO异常供调用者捕捉
	 * @throws InterruptedException
	 *             抛出中断异常供调用者捕捉
	 */
	private void readJsonData(long dataLen) throws Exception,
			InterruptedException, UnsupportedEncodingException, JSONException {
		ByteArrayOutputStream bos = null;
		try {
			bos = new ByteArrayOutputStream();
			int ch = 0;
			byte[] data = null;
			byte[] d = new byte[DATA_BUFFER_LEN];
			while ((ch = is.read(d)) != -1) {
				if (canceled) {
					data = bos.toByteArray();
					bos.close();
					throw new InterruptedException();
				}
				bos.write(d, 0, ch);

				if (canceled) {
					data = bos.toByteArray();
					bos.close();
					throw new InterruptedException();
				}
			}
			data = bos.toByteArray();
			if ("gzip".equals(conn.getHeaderField("Content-Encoding"))) {
				Log.i(TAG,
						"Content-Encoding="
								+ conn.getHeaderField("Content-Encoding"));
				data = Util.gzipdecompress(data);
			}
			JSONObject json = null;
			JSONArray array = null;
			// 把缓存以UTF_8编码格式转换成字符串
			String str = null;
			// 是否有[]
			boolean hasArrayTag = true;
			String charset = "utf-8";
			boolean isGBK = false;
			if (data.length > 3) {
				if (data[0] == (byte) 0xFF && data[1] == (byte) 0xFE) {// UTF-16LE
					isGBK = false;
					charset = "UTF-16LE";
				} else if (data[0] == (byte) 0xFE && data[1] == (byte) 0xFF) {// UTF-16BE
					isGBK = false;
					charset = "UTF-16BE";
				}
				// else if(data[0] == (byte)0xEF && data[1] == (byte)0xBB &&
				// data[2] == (byte)0xBF)
				else if (data[0] == -17 && data[1] == -69 && data[2] == -65) {// 判断是否为UTF－8
																				// 编码
					isGBK = false;
					charset = "UTF-8";
				}
			}
			Log.i(TAG, "charset=" + charset);
			if (isGBK) {
				str = new String(data, "GB2312");
				str = new String(Util.gbk2utf8(str), HTTP.UTF_8);
			} else {
				str = new String(data, charset);
			}
			Log.e(TAG, "str = " + str);

			int maxLogSize = 1000;
			for (int i = 0; i <= str.length() / maxLogSize; i++) {
				int start = i * maxLogSize;
				int end = (i + 1) * maxLogSize;
				end = end > str.length() ? str.length() : end;
				Log.v(TAG + "原始...", str.substring(start, end));
			}

			// str = URLDecoder.decode(str, "utf-8");
			if (str.startsWith("ok")) {
				str = str.substring(2);
			}
			Log.i("json", "return:" + str);
			// 把字符串转换成JSONObject格式
			// 避免有些服务器返回JSON格式数据不正确
			// Android平台对格式要求一定要带'[]'，否则可能解析异常
			// if (!str.startsWith("[")) {
			// hasArrayTag = false;
			// str = "[" + str + "]";
			// }
			// if (hasArrayTag) {
			// // 有数组就传递数组
			// array = new JSONArray(str);
			// handler.sendMessage(handler.obtainMessage(
			// FusionCode.RETURN_JSONOBJECT, fusionCode,
			// getTimeStamp(), array));
			// } else {
			// // 没有就构造 并且取数组首位JSONObject
			// json = new JSONArray(str).optJSONObject(0);
			// handler.sendMessage(handler.obtainMessage(
			// FusionCode.RETURN_JSONOBJECT, fusionCode,
			// getTimeStamp(), json));
			// }

			handler.sendMessage(handler.obtainMessage(
					FusionCode.RETURN_JSONOBJECT, fusionCode, getTimeStamp(),
					str));
		} finally {
			if (bos != null) {
				bos.close();
				bos = null;
			}
		}
	}

	/****************************************************************************************
	 * 函数名称：readGetData 函数描述：读取byte数组格式的网络数据 输入参数：@param dataLen 输入参数：@throws
	 * Exception 输入参数：@throws InterruptedException 输入参数：@throws
	 * UnsupportedEncodingException 输入参数：@throws JSONException 输出参数： 返回 值：void 备
	 * 注：
	 ****************************************************************************************/
	private void readGetData(long dataLen) throws Exception,
			InterruptedException, UnsupportedEncodingException, JSONException {
		ByteArrayOutputStream bos = null;
		try {
			bos = new ByteArrayOutputStream();
			int ch = 0;
			byte[] data = null;
			byte[] d = new byte[DATA_BUFFER_LEN];
			while ((ch = is.read(d)) != -1) {
				if (canceled) {
					data = bos.toByteArray();
					bos.close();
					throw new InterruptedException();
				}
				bos.write(d, 0, ch);
				if (canceled) {
					data = bos.toByteArray();
					bos.close();
					throw new InterruptedException();
				}
			}
			data = bos.toByteArray();
			String str = new String(data);
			int maxLogSize = 1000;
			for (int i = 0; i <= str.length() / maxLogSize; i++) {
				int start = i * maxLogSize;
				int end = (i + 1) * maxLogSize;
				end = end > str.length() ? str.length() : end;
				Log.v(TAG + "原始...", str.substring(start, end));
			}

			if ("gzip".equals(conn.getHeaderField("Content-Encoding"))) {
				Log.i(TAG,
						"Content-Encoding="
								+ conn.getHeaderField("Content-Encoding"));
				data = Util.gzipdecompress(data);
			}
			handler.sendMessage(handler.obtainMessage(
					FusionCode.RETURN_GETBYTES, fusionCode, getTimeStamp(),
					data));
		} finally {
			if (bos != null) {
				bos.close();
				bos = null;
			}
		}
	}

	/**
	 * 读取返回安装成功的xml信息；
	 */
	private void readInstallSuccessXML() throws Exception {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] d = new byte[1024];
			int ch = 0;
			// int bytesread = 0;
			while ((ch = is.read(d)) != -1) {
				bos.write(d, 0, ch);
				// bytesread += ch;
			}
			byte[] data = bos.toByteArray();
			if ("gzip".equals(conn.getHeaderField("Content-Encoding"))) {
				Log.i(TAG,
						"Content-Encoding="
								+ conn.getHeaderField("Content-Encoding"));
				data = Util.gzipdecompress(data);
			}
			bos.close();
			bos = null;
			if (handler != null) {
				handler.sendMessage(handler
						.obtainMessage(FusionCode.RETURN_XML, fusionCode,
								getTimeStamp(), data));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取下载业务的业务包数据
	 * 
	 * @param dataLen
	 *            数据长度
	 * @throws IOException
	 *             抛出IO异常供调用者捕捉
	 * @throws InterruptedException
	 *             抛出中断异常供调用者捕捉
	 */
	private void readDownloadData(long dataLen) throws IOException,
			InterruptedException {
		// 超时时间未到，连接成功停止超时任务
		stopTimeoutTimer();
		long contentLen = 0L;
		// 从返回头中获取返回长度
		String contentLength = conn.getHeaderField("Content-Length");
		if (contentLength != null && contentLength.length() > 0) {
			try {
				contentLen = Long.parseLong(contentLength.trim());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			String contentRange = conn.getHeaderField("content-range");
			if (null != contentRange) {
				contentLen = Long.parseLong(Util.split2(contentRange, "/")[1])
						- breakpoint;
			}
		}
		try {
			// 从流中获取返回长度
			long isLen = (long) (is.available());
			// 取3种返回长度的最大值作为真正的返回长度
			dataLen = dataLen > contentLen ? dataLen : contentLen;
			dataLen = dataLen > isLen ? dataLen : isLen;
			setDataLength(breakpoint, dataLen + breakpoint);
			// 存储已经下载的长度
			long getLen = 0;
			// 存储每次从网络层读取到的数据
			// 临时数据缓冲区
			int buffLen = 0;
			byte[] buff = new byte[DATA_BUFFER_LEN];
			byte[] tempBuff = null;
			Thread.sleep(2000);
			// 创建文件
			createFile();
			while ((buffLen = is.read(buff)) != -1) {
				// 网络连接被暂停或取消，需要抛出中断异常，并关闭写文件线程
				if (canceled || paused || isTimeOut) {
					throw new InterruptedException();
				}
				// 如果没有读到数据就不需要写文件了
				if (buffLen <= 0) {
					continue;
				}
				// sd卡存在时将下载的数据写入文件
				if (MainManager.sdCardIsExist) {
					// 将读到的数据写入文件，写文件成功后通知上层回调下载进度
					tempBuff = new byte[buffLen];
					System.arraycopy(buff, 0, tempBuff, 0, buffLen);
					int len = FileHelper.writeFile(file, tempBuff);
					if (len == FileHelper.ERROR) {
						throw new InterruptedException();
					} else {
						getLen += len;
						// 设置下载量（百分比）
						setDataLength(breakpoint + getLen, breakpoint + dataLen);
					}
				}// sd卡不存在直接抛出异常
				else {
					throw new InterruptedException();
				}
				// 网络连接被暂停或取消，需要抛出中断异常，并关闭写文件线程
				if (canceled || paused || isTimeOut) {
					throw new InterruptedException();
				}
			}
			// Thread.sleep(2000);
			// 保证下载完了文本进度显示100%
			if (dataLen != getLen) {
				dataLen = getLen;
				setDataLength(breakpoint + getLen, breakpoint + dataLen);
			}
		} catch (IOException e) {
			throw new IOException();
		} catch (SDUnavailableException e) {
			throw new InterruptedException(e.getMessage());
		} catch (SDNotEnouchSpaceException e) {
			throw new InterruptedException(e.getMessage());
		}
		// 网络连接被暂停或取消，需要抛出中断异常，并关闭写文件线程
		if (paused || canceled || isTimeOut) {
			throw new InterruptedException();
		}
		// 标志下载完成
		isDownloadSuccess = true;
	}

	/**
	 * 关闭连接
	 */
	private void clearNet() {
		synchronized (sdSyn) {
			try {
				if (is != null) {
					is.close();
				}
				if (conn != null) {
					conn.disconnect();
				}
				if (file != null) {
					file.close();
				}
			} catch (Exception e) {
			} finally {
				file = null;
				is = null;
				conn = null;
			}
		}
	}

	/**
	 * 无网络错误处理
	 * 
	 * @param responseCode
	 *            状态码
	 * @param exception
	 *            异常信息
	 */
	private void setConnError(int responseCode, String exception) {
		// 异常信息打印，正常情况下不会打出该日志
		if (statusListener != null) {
			statusListener.onConnError(responseCode, exception);
		}
	}

	/**
	 * 超时处理
	 * 
	 * @param responseCode
	 *            状态码
	 * @param exception
	 *            异常信息
	 */
	private void setTimeOut(int responseCode, String exception) {
		// 异常信息打印，正常情况下不会打出该日志
		if (statusListener != null) {
			statusListener.onTimeOut(responseCode, exception);
		}
	}

	/**
	 * 设置错误回调
	 */
	private void setError(int responseCode, String exception) {
		// 异常信息打印，正常情况下不会打出该日志
		if (httpListener != null) {
			httpListener.onError(responseCode, exception);
		}
	}

	/**
	 * 设置取消回调
	 */
	private void canceledCallback() {
		if (httpListener != null) {
			httpListener.canceledCallback();
		}
	}

	/**
	 * 设暂停回调
	 */
	private void pausedCallback() {
		if (httpListener != null) {
			httpListener.pausedCallback();
		}
	}

	/**
	 * 设置下载回调
	 */
	private void startDownloadCallback() {
		if (httpListener != null) {
			httpListener.startDownloadCallback();
		}
	}

	/**
	 * 下载完成回调通知接口
	 */
	private void successDownloadCallback() {
		if (httpListener != null) {
			httpListener.successDownloadCallback();
		}
	}

	/**
	 * 设置联网回调接口
	 * 
	 * @param httpListener
	 *            联网回调接口
	 */
	public void setHttpListener(IHttpListener httpListener) {
		this.httpListener = httpListener;
	}

	/**
	 * 回调网络接收的及时数据长度
	 * 
	 * @param getLength
	 *            已下载文件大小
	 * @param totalLength
	 *            文件的总大小
	 */
	public void setDataLength(long getLength, long totalLength) {
		if (httpListener != null) {
			httpListener.onProgressChanged(getLength, totalLength);
		}
	}

	/**
	 * 设置回调接口
	 * 
	 * @param handler
	 *            回调句柄
	 * @param fusionCode
	 *            区分联网请求JSON的标志码
	 */
	public void setHandler(Handler handler, int fusionCode) {
		this.handler = handler;
		this.fusionCode = fusionCode;
	}

	/**
	 * 设置请求头信息
	 * 
	 * @param dataBuf
	 *            请求头参数
	 */
	public void setDataBuf(byte[] dataBuf) {
		this.dataBuf = dataBuf;
	}

	/**
	 * 设置请求的url
	 * 
	 * @param httpUrl
	 *            连接地址
	 */
	public void setHttpUrl(String httpUrl) {
		this.httpUrl = httpUrl;
	}

	/**
	 * 设置请求类型
	 * 
	 * @param requestType
	 *            请求类型
	 */
	public void setRequestType(int requestType) {
		this.requestType = requestType;
	}

	/**
	 * 任务取消的回调接口方法
	 */
	public void onCancelTask() {
		Log.i(TAG, "onCancelTask");
		canceled = true;
	}

	/**
	 * 获取网络连接任务对象请求的url
	 * 
	 * @return 请求的url
	 */
	public String getHttpUrl() {
		return httpUrl;
	}

	/**
	 * SD 卡监听接口
	 * 
	 * @param arg
	 *            系统通知的action此类需要监听sd的插拔
	 */
	public void update(Object arg) {
		clearNet();
	}

	/**
	 * 设置定时器对象
	 * 
	 * @param timer
	 */
	public void setTimer(Timer timer) {
		if (timer != null) {
			this.timer = timer;
		}
	}

	/**
	 * 任务请求响应回调接口
	 * 
	 * @param code
	 *            响应通知码
	 */
	public void onTaskResponse(int code) {
		switch (code) {
		case TaskObject.RESPONSE_TIMEOUT_RUNNING:
			if (connectType == CONNECT_TYPE_JSON) {
				setTimeOut(responseCode, "TIMEOUT");
			} else {
				setError(responseCode, "TIMEOUT");
			}
			isTimeOut = true;
			clearNet();
			break;
		default:
			break;
		}
	}

	/**
	 * 设置任务的超时定时器任务对象
	 * 
	 * @param timeoutTask
	 *            定时器任务对象
	 */
	public void setTimeoutTask(TimerTask timeoutTask) {
		this.timerTask = timeoutTask;
	}

	public void setConnectType(int type) {
		connectType = type;
	}

	/**
	 * 启动超时定时器
	 */
	public void startTimeoutTimer() {
		if (timer != null) {
			timer.schedule(timerTask, timeout);
		}
	}

	/**
	 * 停止超时定时器
	 */
	public void stopTimeoutTimer() {
		if (timerTask != null) {
			timerTask.cancel();
		}
	}

	/**
	 * 创建下载文件目录
	 */
	private void createFile() {
		File tempFile = null;
		tempFile = FileHelper.createDownloadFile(oldUrl);
		try {
			file = new RandomAccessFile(tempFile, "rw");
		} catch (IOException e) {
		}
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

	@Override
	public void setTimeStamp(int time) {
		Log.i(TAG, "setTimeStamp=" + time);
		taskTimeStamp = time;
	}

	@Override
	public int getTimeStamp() {
		return taskTimeStamp;
	}

	/**
	 * 调用方法
	 * 
	 * @param url
	 *            url
	 * @return encode之后的数据
	 */
	private String sysEncode(String url) {
		String result = "";
		if (url != null) {
			if (url.indexOf("?") != -1) {
				String parameter = url.substring(url.indexOf("?") + 1);// 参数部分
				String urlhrad = url.substring(0, url.indexOf("?"));
				result = urlhrad + "?";
				if (parameter != null) {
					String canshu[] = parameter.split("&");
					for (int i = 0; i < canshu.length; i++) {
						if (canshu[i] != null) {
							String _canshu[] = canshu[i].split("=");
							if (_canshu != null && _canshu.length > 1) {
								if (_canshu[1] != null) {
									String rparam;
									try {
										rparam = _canshu[0]
												+ "="
												+ java.net.URLEncoder.encode(
														_canshu[1], "GBK");
										if (i + 1 == canshu.length) {
											result += rparam;
										} else {
											result += rparam + "&";
										}
									} catch (UnsupportedEncodingException e) {
										e.printStackTrace();
									}
								}
							} else {
								if (i + 1 == canshu.length) {
									result += _canshu[0] + "=";
								} else {
									result += _canshu[0] + "=&";
								}

							}
						}
					}
				}
			} else {
				result = url;
			}
		}
		return result;
	}
}
