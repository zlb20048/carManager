package com.cars.simple.service.baseData;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.content.Context;
import android.util.Log;

import com.cars.simple.R;
import com.cars.simple.fusion.FusionCode;

/**
 * 文件读写类
 * 
 * @author shifeng
 * 
 */
public class RawTemplate {

	private Context context = null;

	public RawTemplate(Context context) {
		this.context = context;
	}

	/****************************************************************************************
	 * 函数名称：unwebviewDB 函数描述：解压webview.db 文件 输入参数：@throws Exception 输出参数： 返回
	 * 值：void 备 注：
	 ****************************************************************************************/
	private void unwebviewDB() throws Exception {
		// InputStream is = context.getResources().openRawResource(
		// R.raw.webviewcache);
		// ZipInputStream zis = new ZipInputStream(new BufferedInputStream(is));
		// ZipEntry entry;
		// while ((entry = zis.getNextEntry()) != null) {
		// int size;
		// byte[] buffer = new byte[128];
		// OutputStream fos = new FileOutputStream(FusionCode.ECMC_DB_PATH
		// + entry.getName());
		// BufferedOutputStream bos = new BufferedOutputStream(fos,
		// buffer.length);
		//
		// while ((size = zis.read(buffer, 0, buffer.length)) != -1) {
		// bos.write(buffer, 0, size);
		// }
		// bos.flush();
		// bos.close();
		// }
		// zis.close();
		// is.close();
	}

	/****************************************************************************************
	 * 函数名称：readFileUrl 函数描述：加载服务器url 输入参数：@return 输入参数：@throws IOException
	 * 输出参数： 返回 值：Map<String,String> 备 注：
	 ****************************************************************************************/
	public Map<String, String> readFileUrl() throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		InputStream in = context.getResources()
				.openRawResource(R.raw.serverurl);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line = null;
		while ((line = br.readLine()) != null) {
			if (line.startsWith("#")) {
				continue;
			}
			int len = line.indexOf("=");
			if (len != -1) {
				String[] t = new String[2];
				t[0] = line.substring(0, len);
				t[1] = line.substring(len + 1, line.length());
				map.put(t[0], t[1]);
			}
		}
		br.close();
		return map;
	}

	/****************************************************************************************
	 * 函数名称：initFile 函数描述：初始化文件 输入参数： 输出参数： 返回 值：void 备 注：
	 ****************************************************************************************/
	public void initFile() {
		try {
			if (unzipdb()) {
				unwebviewDB();
				// unjsfile();
			}
		} catch (Exception e) {
			Log.e(this.toString(), "" + e.getMessage());
		}
	}

	private boolean checkDataBase() {
		File dir = new File(FusionCode.ECMC_DB_PATH + "ecmc.db");
		return dir.exists();
	}

	public void deleteAppRaw() {
		File f = new File(FusionCode.ECMC_DB_PATH + "ecmc.db");
		if (f.exists()) {
			f.delete();
		}
	}

	public void deleteFile() {
		Log.v("=====deleteFile====", "..........");
		deleteAppRaw();
		deleteImage();
	}

	private void deleteImage() {
		File dir = new File(FusionCode.ECMC_FILE_PATH);
		File[] file = dir.listFiles();
		if (null == file) {
			return;
		}
		for (int i = 0; i < file.length; i++) {
			if (file[i].exists()) {
				file[i].delete();
			}
		}
	}

	/****************************************************************************************
	 * 函数名称：unzipdb 函数描述：解压db.zip 文件 输入参数：@return 输入参数：@throws Exception 输出参数：
	 * 返回 值：boolean 备 注：
	 ****************************************************************************************/
	private boolean unzipdb() throws Exception {
		File f = new File(FusionCode.ECMC_DB_PATH);
		if (!f.exists()) {
			f.mkdirs();
		}
		if (checkDataBase()) {
			return false;
		}
		Log.i("@@@@@@@@@@@@@@@", "init database");
		//InputStream is = null;
		 InputStream is =
		 context.getResources().openRawResource(R.raw.cars);
		ZipInputStream zis = new ZipInputStream(new BufferedInputStream(is));
		ZipEntry entry;
		while ((entry = zis.getNextEntry()) != null) {
			int size;
			byte[] buffer = new byte[128];
			OutputStream fos = new FileOutputStream(FusionCode.ECMC_DB_PATH
					+ entry.getName());
			BufferedOutputStream bos = new BufferedOutputStream(fos,
					buffer.length);
			while ((size = zis.read(buffer, 0, buffer.length)) != -1) {
				bos.write(buffer, 0, size);
			}
			bos.flush();
			bos.close();
		}
		zis.close();
		is.close();
		return true;
	}

	/*
	 * 写入文件到默认路径
	 */
	public void saveToDefault(byte[] buf, String imgName) throws IOException {
		FileOutputStream fos = context.openFileOutput(imgName,
				Context.MODE_PRIVATE);
		fos.write(buf);
		fos.flush();
		fos.close();
	}

	/****************************************************************************************
	 * 函数名称：readFromDefault 函数描述：读取默认路径下地文件 输入参数：@param imgName 输入参数：@return
	 * 输入参数：@throws IOException 输出参数： 返回 值：byte[] 备 注：
	 ****************************************************************************************/
	public byte[] readFromDefault(String imgName) throws IOException {
		FileInputStream is = context.openFileInput(imgName);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int ch = -1;
		byte[] buf = new byte[128];
		while ((ch = is.read(buf)) != -1) {
			baos.write(buf, 0, ch);
		}
		is.close();
		byte[] data = baos.toByteArray();
		baos.close();
		return data;
	}

	/****************************************************************************************
	 * 函数名称：appendAsHex 函数描述：将10进制数据转换成十六进制，不足位数时前面补0 输入参数：@param i 输入参数：@param
	 * ret 输出参数： 返回 值：void 备 注：
	 ****************************************************************************************/
	private static void appendAsHex(int i, StringBuffer ret) {
		String hex = Integer.toHexString(i);
		switch (hex.length()) {
		case 1:
			ret.append("0000000");
			break;
		case 2:
			ret.append("000000");
			break;
		case 3:
			ret.append("00000");
			break;
		case 4:
			ret.append("0000");
			break;
		case 5:
			ret.append("000");
			break;
		case 6:
			ret.append("00");
			break;
		case 7:
			ret.append("0");
			break;
		}
		ret.append(hex);
	}

}
