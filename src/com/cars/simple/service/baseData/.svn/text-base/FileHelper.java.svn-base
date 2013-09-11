package com.cars.simple.service.baseData;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.HashMap;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

/******************************************************************
 * 文件名称 : FileHelper.java
 * 作         者  :  fushangbin
 * 创建时间 : 
 * 文件描述 : 主要是对文件系统的一些操作，包括文件的读写操作,主要是完成下载中的保存下载文件的功能
 ******************************************************************/
public class FileHelper
{
	private static final String TAG = "==FileHelper==";

	/**
	 * 当出错的时候返回的字节数为-1
	 */
	public final static int ERROR = -1;

	/**
	 * 当没有写入任何数据的时候返回0 
	 */
	public final static int NONE = 0;

	/**
	 * 该变量是从sd卡读取文件时默认的字符缓冲区的大小
	 */
	private final static int MAX_LENTH = 1024;

	/**
	 * sd卡所在的区块位置
	 */
	private final static int SDCARD_SYSTEM = 4;

	/**
	 * 在程序第一次安装的时候就在sd卡的主目录下新建一个系统所需的目录 
	 * @return   创建目录是否成功
	 */
	public static boolean makeDir(){
		boolean isComplete = false;
		return isComplete;
	}

	/**
	 * 创建File文件
	 * @param fileName  文件名
	 * @return     生成的文件
	 */
	public static File createDownloadFile(String fileName){
		return createFile(/*FusionField.DOWNLOAD_PATH +*/ fileName);
	}

	/**
	 * 通过提供的文件名在默认路径下生成文件
	 * @param fileName     文件的名称
	 * @return 生成的文件
	 */
	private static File createFile(String fileName){
		File file = new File(fileName);
		if (!isFileExist(file)){
			try{
				file.createNewFile();
			}catch (IOException e){
				if(fileName.lastIndexOf("/") > -1){
					String fatherRoot = fileName.substring(0, fileName.lastIndexOf("/"));
					File filetemp = new File(fatherRoot);
					filetemp.mkdirs();
					try {
						file.createNewFile();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
		return file;
	}

	/**
	 * 将从下载管理那里获取来的数据流写入文件中
	 * @param ops 从下载管理那里获取来的io流
	 * @param fileName 需要存储的文件的路径和名称
	 * @return 总共存储成功的字节数
	 * @throws SDNotEnouchSpaceException 
	 * @throws SDUnavailableException 
	 */
	public static int writeFile(byte[] io, String fileName)
			throws SDUnavailableException, SDNotEnouchSpaceException
	{
		int length = NONE;
		if (io != null){
			RandomAccessFile file = null;
			try{
				file = new RandomAccessFile(createFile(fileName), "rw");
				file.seek(file.length());
				file.write(io);
			}catch (IOException e){
				Log.e(TAG, "writeFile.e=" + e.getMessage());
				checkSD(io);
				// 如果出现异常，返回的字节数为-1，表示出现了异常，没有写入成功
				return ERROR;
			}finally{
				try{
					file.close();
				}catch (Exception e){
				}
			}
			length = io.length;
		}
		return length;
	}

	/**
	 * 将从网络获取来的数据流写入文件中
	 * @param ops 从网络获取来的io流
	 * @param fileName 需要存储的文件的名称
	 * @return 总共存储成功的字节数
	 * @throws SDNotEnouchSpaceException 
	 * @throws SDUnavailableException 
	 */
	public static int writeFile(RandomAccessFile file, byte[] io)
			throws SDUnavailableException, SDNotEnouchSpaceException
	{
		int length = NONE;
		if (io != null){
			if (file != null){
				try{
					file.seek(file.length());
					file.write(io);
				}catch (IOException e){
					Log.e(TAG, "writeFile.e=" + e.getMessage());
					checkSD(io);
					// 如果出现异常，返回的字节数为-1，表示出现了异常，没有写入成功
					return ERROR;
				}
				length = io.length;
			}else{
				checkSD(io);
				// 如果出现异常，返回的字节数为-1，表示出现了异常，没有写入成功
				return ERROR;
			}
		}
		return length;
	}

	/**
	 * 从本地文件中读取文件信息
	 * @param fileName 文件的名称
	 * @return 文件中的信息
	 */
	public static String readFile(String fileName){
		RandomAccessFile file = null;
		byte[] buffer = new byte[MAX_LENTH];
		StringBuffer content = new StringBuffer();
		try{
			file = new RandomAccessFile(createFile(fileName), "rw");
			while (file.read(buffer) != -1){
				content.append(new String(buffer));
			}
		}catch (IOException e){
			Log.e(TAG, "readFile.e=" + e.getMessage());
		}
		finally{
			try{
				file.close();
			}catch (IOException e){
			}
		}
		return content.toString();
	}
	
	/****************************************************************************************
	 * 函数名称：readFilebytes
	 * 函数描述：读取文件的byte数组
	 * 输入参数：@param fileName
	 * 输入参数：@return
	 * 输出参数：
	 * 返回    值：byte[]
	 * 备         注：
	 ****************************************************************************************/
	public static byte[]readFilebytes(String fileName){
		if (!isFileExist(new File(fileName))){
			return null;
		}
		RandomAccessFile file = null;
		byte[] buffer = new byte[MAX_LENTH];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try{
			file = new RandomAccessFile(createFile(fileName), "rw");
			while (file.read(buffer) != -1){
				baos.write(buffer);
			}
		}catch (IOException e){
		}catch(Error e){
		}finally{
			if(null != file){
				try{
					file.close();
				}catch (IOException e){
				}
			}
		}
		return baos.toByteArray();
	}

	/**
	 * 是否存在此文件
	 * @param file 判断是否存在的文件
	 * @return  存在返回true，否则返回false
	 */
	public static boolean isFileExist(final File file){
		boolean isExist = false;
		// 在无SD卡时file会为空
		if (file == null){
			return false;
		}
		if (file.exists()){
			isExist = true;
		}else{
			isExist = false;
		}
		return isExist;
	}

	/**
	 * 删除路径指向的文件
	 * @param fileName   文件的名称
	 * @return    true删除成功，false删除失败
	 */
	private static boolean deleteFile(final String fileName){
		boolean isComplete = false;
		File file = new File(fileName);
		if (file.exists()){
			isComplete = file.delete();
		}else{
			isComplete = true;
		}
		return isComplete;
	}

	/**
	 * 本地文件大小
	 * @param fileName  文件的名称
	 * @return 返回文件的大小
	 */
	public static long getLocalFileSize(final String fileName){
		File file = createFile(fileName);
		long length = 0;
		if (isFileExist(file)){
			length = file.length();
		}
		return length;
	}

	/**
	 * 读取配置文件读取配置信息
	 * @param context 调用这个方法的activity
	 * @return   包含配置信息的hashmap键值对 
	 */
	public static HashMap<String, String> loadProperties(Context context,String filename){
		HashMap<String, String> properties = new HashMap<String, String>();
		InputStream is = null;
		FileInputStream fis = null;
		InputStreamReader rin = null;
		// 将配置文件放到res/raw/目录下，可以通过以下的方法获取
		//		is = context.getResources().openRawResource(R.raw.system);

		// 这是读取配置文件的第二种方法
		// 将配置文件放到assets目录下，可以通过以下的方法获取
		//		is = context.getAssets().open("system.properties");
		// 用来提取键值对的临时字符串
		StringBuffer tempStr = new StringBuffer();
		// 用来存放读取的每个字符
		int ch = 0;
		// 分隔符在字符串中的位置，分隔符为":"
		int index = 0;
		// 用来保存读取的配置文件一行的信息
		String line = null;
		File mfile = null;
		try
		{
			is = context.getAssets().open(filename);
			rin = new InputStreamReader(is, "UTF-8");
			while (ch != -1){
				tempStr.delete(0, tempStr.length());
				while ((ch = rin.read()) != -1){
					if (ch != '\n'){
						tempStr.append((char) ch);
					}else{
						break;
					}
				}
				line = tempStr.toString().trim();
				// 判断读出的那行数据是否有效,#开头的代表注释,如果是注释行那么跳过下面,继续上面操作
				if (line.length() == 0 || line.startsWith("#")){
					continue;
				}
				// 查找key与value的分隔符,：
				index = line.indexOf(":");
				// 如果没有跳过下面,继续上面操作
				if (index == -1){
					continue;
				}
				// 将key与value对应的值保存进hashtable
				properties.put(line.substring(0, index), line
						.substring(index + 1));
			}
		}catch (IOException e){
			Log.e(TAG, "loadProperties.e=" + e.getMessage());
		}finally{
			try{
				if (is != null){
					is.close();
				}
				if (fis != null){
					fis.close();
				}
				if(null != rin){
					rin.close();
				}
			}catch (IOException e){
				Log.e(TAG, "loadProperties.e=" + e.getMessage());
			}finally{
				is = null;
				fis = null;
				rin = null;
			}
		}
		return properties;
	}

	/**
	 * 对sdcard的检查，主要是检查sd是否可用，并且sd卡的存储空间是否充足
	 * @param io 写入sd卡的数据
	 * @throws SDUnavailableException 
	 * @throws SDNotEnouchSpaceException 
	 */
	public static void checkSD(byte[] io) throws SDUnavailableException,
			SDNotEnouchSpaceException{
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)){
			throw new SDUnavailableException(/*LogicFacade.getText("|:sd_Unavailable")*/"sd_Unavailable");
		}else{
			if (io.length >= getFreeSD()){
				//通知UI
				throw new SDNotEnouchSpaceException(/*LogicFacade.getText("|:sd_NotEnoughSpace")*/"sd_NotEnoughSpace");
			}
		}
	}

	/**
	 * 获取SD卡的剩余空间
	 * @return SD卡的剩余的字节数 
	 */
	public static long getFreeSD(){
		long nAvailableCount = 0l;
		try{
			StatFs stat = new StatFs(Environment.getExternalStorageDirectory()
					.getAbsolutePath());
			nAvailableCount = (long) (stat.getBlockSize() * ((long) stat
					.getAvailableBlocks() - SDCARD_SYSTEM));
		}catch (Exception e){
			Log.i(TAG, "getFreeSD.e=in FileHelper.java");
		}
		return nAvailableCount;
	}

}
