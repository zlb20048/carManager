package com.cars.simple.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/******************************************************************
 * 文件名称 : Util.java 作 者 : fushangbin 创建时间 : 文件描述 : 通用工具类
 ******************************************************************/
public class Util
{
    private final static String TAG = "Util";

    /**
     * "3gwap"字符串常量
     */
    private static final String wapStr = "3gwap";

    /**
     * 保存APN信息的变量
     */
    private static String apn;

    /**
     * 对话框
     */
    public static Dialog dialog = null;

    /**
     * gzip 解压的缓冲区域大小
     */
    public static final int BUFFER = 1024;

    /**
     * 判断apn是否3Gwap方式
     * 
     * @param context 浏览器对象上下文
     * @return 是否为3gwap代理(true:apn是3Gwap,false:不是)
     */
    public static boolean is3Gwap(Context context)
    {
        if (null == apn)
        {
            getAPN(context);
        }
        if (null != apn && apn.equals(wapStr))
        {
            return true;
        }
        return false;
    }

    /**
     * 通过context取得ConnectivityManager中的NetworkInfo里关于apn的联网信息
     * 
     * @param context 浏览器对象上下文
     * @return 代理模式
     */
    private static String getAPN(Context context)
    {
        if (apn == null)
        {
            // 通过context得到ConnectivityManager连接管理
            ConnectivityManager manager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // 通过ConnectivityManager得到NetworkInfo网络信息
            NetworkInfo info = manager.getActiveNetworkInfo();
            // 获取NetworkInfo中的apn信息
            apn = info.getExtraInfo();
        }
        return apn;
    }

    /**
     * 返回当前时间
     * 
     * @return "yyyy-MM-dd HH:mm:ss"格式的时间字符串
     */
    public static String getTime()
    {
        // 使用默认时区和语言环境获得一个日历
        Calendar cale = Calendar.getInstance();
        // 将Calendar类型转换成Date类型
        Date tasktime = cale.getTime();
        // 设置日期输出的格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 格式化输出
        return df.format(tasktime);
    }

    /**
     * 获取时间
     * 
     * @return day
     */
    public static String getDay()
    {
        // 使用默认时区和语言环境获得一个日历
        Calendar cale = Calendar.getInstance();
        // 将Calendar类型转换成Date类型
        Date tasktime = cale.getTime();
        // 设置日期输出的格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        // 格式化输出
        return df.format(tasktime);
    }

    /**
     * 解压zip格式的压缩包
     * 
     * @param inPath 输入路径
     * @param outPath 输出路径
     * @return 解压成功或失败标志
     */
    public static Boolean openZip(String inPath, String outPath)
    {
        String unzipfile = inPath; // 解压缩的文件名
        try
        {
            ZipInputStream zin = new ZipInputStream(new FileInputStream(unzipfile));
            ZipEntry entry;
            // 创建文件夹
            while ((entry = zin.getNextEntry()) != null)
            {
                if (entry.isDirectory())
                {
                    File directory = new File(outPath, entry.getName());
                    if (!directory.exists())
                    {
                        if (!directory.mkdirs())
                        {
                            System.exit(0);
                        }
                    }
                    zin.closeEntry();
                }
                else
                {
                    File myFile = new File(entry.getName());
                    FileOutputStream fout = new FileOutputStream(outPath + myFile.getPath());
                    DataOutputStream dout = new DataOutputStream(fout);
                    byte[] b = new byte[1024];
                    int len = 0;
                    while ((len = zin.read(b)) != -1)
                    {
                        dout.write(b, 0, len);
                    }
                    dout.close();
                    fout.close();
                    zin.closeEntry();
                }
            }
            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将字符串str按子字符串separatorChars 分割成数组
     * 
     * @param str 要拆分的字符串
     * @param separatorChars 用来拆分的分割字符
     * @return 拆分后的字符串
     */
    public static String[] split2(String str, String separatorChars)
    {
        return splitWorker(str, separatorChars, -1, false);
    }

    /**
     * 拆分字符串
     * 
     * @param str 要拆分的字符串
     * @param separatorChars 用来拆分的分割字符
     * @param max 要拆分字符串的最大长度
     * @param preserveAllTokens
     * @return 拆分后的字符串
     */
    private static String[] splitWorker(String str, String separatorChars, int max, boolean preserveAllTokens)
    {
        if (str == null)
        {
            return null;
        }
        int len = str.length();
        if (len == 0)
        {
            return new String[] {"" };
        }
        Vector<String> vector = new Vector<String>();
        int sizePlus1 = 1;
        int i = 0;
        int start = 0;
        boolean match = false;
        boolean lastMatch = false;
        if (separatorChars == null)
        {
            while (i < len)
            {
                if (str.charAt(i) == '\r' || str.charAt(i) == '\n' || str.charAt(i) == '\t')
                {
                    if (match || preserveAllTokens)
                    {
                        lastMatch = true;
                        if (sizePlus1++ == max)
                        {
                            i = len;
                            lastMatch = false;
                        }
                        vector.addElement(str.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                }
                else
                {
                    lastMatch = false;
                    match = true;
                    i++;
                }
            }
        }
        else if (separatorChars.length() == 1)
        {
            char sep = separatorChars.charAt(0);
            while (i < len)
            {
                if (str.charAt(i) == sep)
                {
                    if (match || preserveAllTokens)
                    {
                        lastMatch = true;
                        if (sizePlus1++ == max)
                        {
                            i = len;
                            lastMatch = false;
                        }
                        vector.addElement(str.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                }
                else
                {
                    lastMatch = false;
                    match = true;
                    i++;
                }
            }
        }
        else
        {
            while (i < len)
            {
                int id = i + separatorChars.length() < len ? i + separatorChars.length() : len;
                if (separatorChars.indexOf(str.charAt(i)) >= 0 && separatorChars.equals(str.substring(i, id)))
                {
                    if (match || preserveAllTokens)
                    {
                        lastMatch = true;
                        if (sizePlus1++ == max)
                        {
                            i = len;
                            lastMatch = false;
                        }
                        vector.addElement(str.substring(start, i));
                        match = false;
                    }
                    i += separatorChars.length();
                    start = i;
                }
                else
                {
                    lastMatch = false;
                    match = true;
                    i++;
                }
            }
        }
        if (match || preserveAllTokens && lastMatch)
        {
            vector.addElement(str.substring(start, i));
        }
        String[] ret = new String[vector.size()];
        vector.copyInto(ret);
        return ret;
    }

    /**
     * Drawable对象转化Bitmap对象
     * 
     * @param drawable Drawable对象
     * @return Bitmap对象
     */
    public static Bitmap drawableToBitmap(Drawable drawable)
    {
        Bitmap bitmap =
            Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        // canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * Bitmap对象转化byte数组
     * 
     * @param bitmap Bitmap对象
     * @return byte数组
     */
    public static byte[] bitmapToBytes(Bitmap bitmap)
    {
        if (null == bitmap)
        {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * Byte数组转化为Bitmap对象
     * 
     * @param data byte数组
     * @return Bitmap对象
     */
    public static Bitmap bytesToBimap(byte[] data)
    {
        if (data.length != 0)
        {
            return BitmapFactory.decodeByteArray(data, 0, data.length);
        }
        else
        {
            return null;
        }
    }

    /**************************************************************
     * 函数名称:gbk2utf8 函数描述:将gbk 转码为utf-8 输入参数：@param chenese 输入参数：@return 输出参数：byte[] 备 注：
     *************************************************************/
    public static byte[] gbk2utf8(String chenese)
    {
        char c[] = chenese.toCharArray();
        byte[] fullByte = new byte[3 * c.length];
        for (int i = 0; i < c.length; i++)
        {
            int m = (int) c[i];
            String word = Integer.toBinaryString(m);
            StringBuffer sb = new StringBuffer();
            int len = 16 - word.length();
            for (int j = 0; j < len; j++)
            {
                sb.append("0");
            }
            sb.append(word);
            sb.insert(0, "1110");
            sb.insert(8, "10");
            sb.insert(16, "10");
            String s1 = sb.substring(0, 8);
            String s2 = sb.substring(8, 16);
            String s3 = sb.substring(16);
            byte b0 = Integer.valueOf(s1, 2).byteValue();
            byte b1 = Integer.valueOf(s2, 2).byteValue();
            byte b2 = Integer.valueOf(s3, 2).byteValue();
            byte[] bf = new byte[3];
            bf[0] = b0;
            fullByte[i * 3] = bf[0];
            bf[1] = b1;
            fullByte[i * 3 + 1] = bf[1];
            bf[2] = b2;
            fullByte[i * 3 + 2] = bf[2];
        }
        return fullByte;
    }

    /**************************************************************
     * 函数名称:utf8Togb2312 函数描述:将UTF-8编码转为GBK 编码 输入参数：@param str 输入参数：@return 输出参数：String 备 注：
     *************************************************************/
    public static String utf8Togb2312(String str)
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++)
        {
            char c = str.charAt(i);
            switch (c)
            {
                case '+':
                    sb.append(' ');
                    break;
                case '%':
                    try
                    {
                        sb.append((char) Integer.parseInt(str.substring(i + 1, i + 3), 16));
                    }
                    catch (NumberFormatException e)
                    {
                        throw new IllegalArgumentException();
                    }
                    i += 2;
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }
        // Undo conversion to external encoding
        String result = sb.toString();
        String res = null;
        try
        {
            byte[] inputBytes = result.getBytes("8859_1");
            res = new String(inputBytes, "UTF-8");
        }
        catch (Exception e)
        {
        }
        return res;
    }

    public static boolean isValidUtf8(byte[] b, int aMaxCount)
    {
        int lLen = b.length, lCharCount = 0;
        for (int i = 0; i < lLen && lCharCount < aMaxCount; ++lCharCount)
        {
            byte lByte = b[i++];// to fast operation, ++ now, ready for the
            // following for(;;)
            if (lByte >= 0)
                continue;// >=0 is normal ascii
            if (lByte < (byte) 0xc0 || lByte > (byte) 0xfd)
                return false;
            int lCount =
                lByte > (byte) 0xfc ? 5 : lByte > (byte) 0xf8 ? 4 : lByte > (byte) 0xf0 ? 3
                    : lByte > (byte) 0xe0 ? 2 : 1;
            if (i + lCount > lLen)
                return false;
            for (int j = 0; j < lCount; ++j, ++i)
                if (b[i] >= (byte) 0xc0)
                    return false;
        }
        return true;
    }

    /**
     * 转化图片的大小
     */
    public static Bitmap changeBitmapSize(Bitmap bitmap, int width, int height)
    {
        try
        {
            int h = bitmap.getHeight();
            int w = bitmap.getWidth();
            float scaleWidth = (float) width / w;
            float scaleHeight = (float) height / h;
            float minScale = Math.min(scaleWidth, scaleHeight);
            Matrix matrix = new Matrix();
            matrix.postScale(minScale, minScale);
            Bitmap temp /* = bitmap.copy(Config.ARGB_8888, true) */;
            temp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
            Canvas canvasTemp = new Canvas(temp);
            Paint p = new Paint();
            canvasTemp.drawBitmap(temp, 0, 0, p);
            return temp;
        }
        catch (Error e)
        {
            return null;
        }
    }

    public static Bitmap imageZoom(Bitmap bitmap)
    {
        // 图片允许最大空间 单位：KB
        double maxSize = 400.00;
        // 将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        byte[] b = baos.toByteArray();
        // 将字节换成KB
        double mid = b.length / 1024;
        // 判断bitmap占用空间是否大于允许最大空间 如果大于则压缩 小于则不压缩
        if (mid > maxSize)
        {
            // 获取bitmap大小 是允许最大大小的多少倍
            double i = mid / maxSize;
            // 开始压缩 此处用到平方根 将宽带和高度压缩掉对应的平方根倍 （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
            bitmap = zoomImage(bitmap, bitmap.getWidth() / Math.sqrt(i), bitmap.getHeight() / Math.sqrt(i));
        }
        return bitmap;
    }

    public static Bitmap compressImage(Bitmap image)
    {
        int bitmapSize = 100;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > bitmapSize)
        { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            Log.v(TAG, "length = " + baos.toByteArray().length);
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        Log.v(TAG, "length = " + baos.toByteArray().length);
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }
    
    public static Bitmap comp(Bitmap image) {  
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();         
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);  
        if( baos.toByteArray().length / 1024 > 1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出    
            baos.reset();//重置baos即清空baos  
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中  
        }  
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());  
        BitmapFactory.Options newOpts = new BitmapFactory.Options();  
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了  
        newOpts.inJustDecodeBounds = true;  
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);  
        newOpts.inJustDecodeBounds = false;  
        int w = newOpts.outWidth;  
        int h = newOpts.outHeight;  
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为  
        float hh = 800f;//这里设置高度为800f  
        float ww = 480f;//这里设置宽度为480f  
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可  
        int be = 1;//be=1表示不缩放  
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放  
            be = (int) (newOpts.outWidth / ww);  
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放  
            be = (int) (newOpts.outHeight / hh);  
        }  
        if (be <= 0)  
            be = 1;  
        newOpts.inSampleSize = be;//设置缩放比例  
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了  
        isBm = new ByteArrayInputStream(baos.toByteArray());  
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);  
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩  
    }  
    
    /***
     * 图片的缩放方法
     * 
     * @param bgimage ：源图片资源
     * @param newWidth ：缩放后宽度
     * @param newHeight ：缩放后高度
     * @return
     */
    public static Bitmap zoomImage(Bitmap bgimage, double newWidth, double newHeight)
    {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width, (int) height, matrix, true);
        return bitmap;
    }

    /**
     * 将"请选择"字符串转换为对应的十进制Unicode码"&#35831;&#36873;&#25321;"
     */
    public static String ConvertToUnicode(String testStr)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < testStr.length(); i++)
        {
            char s1 = testStr.charAt(i);
            int i1 = (int) s1;
            sb.append(String.format("&#%1$d;", i1));
        }
        return sb.toString();
    }

    /**
     * 将Unicode码"&#35831;&#36873;&#25321; 转换成 "请选择"
     * 
     * @param str
     * @return
     */
    public static String UnicodeToConvert(String str)
    {
        str = str.replaceAll("&amp;", "&");
        StringBuffer sb = new StringBuffer();
        if (str != null && str.length() > 0)
        {
            int i = 0;
            int j = 0;
            String chStr;
            char ch;
            while (i < str.length())
            {
                if (str.charAt(i) == '&' && (i + 1) < str.length() && str.charAt(i + 1) == '#')
                {
                    j = str.indexOf(';', i);
                    if (j > i)
                    {
                        chStr = str.substring(i + 2, j);
                        ch = (char) Integer.parseInt(chStr);
                        sb.append(ch);
                        i = j + 1;
                        continue;
                    }
                }
                sb.append(str.charAt(i++));
            }
        }
        Log.i("UnicodeToConvert", "sb=" + sb.toString());
        return sb.toString();
    }

    /****************************************************************************************
     * 函数名称：isUpperCase 函数描述：判断是否全是大字字母 输入参数：@param str 输入参数：@return 输出参数： 返回 值：boolean 备 注：
     ****************************************************************************************/
    public static boolean isUpperCase(String str)
    {
        boolean isUpperCase = true;
        for (int j = 0; j < str.length(); j++)
        {
            char ch = str.charAt(j);
            if (ch < 'A' || ch > 'Z')
            {
                if (ch >= '0' && ch <= '9')
                {
                    continue;
                }
                isUpperCase = false;
                break;
            }
        }
        return isUpperCase;
    }

    /****************************************************************************************
     * 函数名称：getUrlPageName 函数描述：获取url地址的文件名称 输入参数：@param url 输入参数：@return 输出参数： 返回 值：String 备 注：
     ****************************************************************************************/
    public static String getUrlPageName(String url)
    {
        int l = url.lastIndexOf('/') + 1;
        if (url.indexOf('.', l + 1) > -1)
        {
            return url.substring(l, url.indexOf('.', l + 1));
        }
        return url.substring(l);
    }

    /****************************************************************************************
     * 函数名称：gzipdecompress 函数描述：gzip数据的解压 输入参数：@param data 输入参数：@return 输入参数：@throws Exception 输出参数： 返回 值：byte[] 备 注：
     ****************************************************************************************/
    public static byte[] gzipdecompress(byte[] data) throws Exception
    {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 解压缩
        gzipdecompress(bais, baos);
        data = baos.toByteArray();
        baos.flush();
        baos.close();
        bais.close();
        return data;
    }

    /****************************************************************************************
     * 函数名称：gzipdecompress 函数描述：数据解压缩 输入参数：@param is 输入参数：@param os 输入参数：@throws Exception 输出参数： 返回 值：void 备 注：
     ****************************************************************************************/
    private static void gzipdecompress(InputStream is, OutputStream os) throws Exception
    {
        GZIPInputStream gis = new GZIPInputStream(is);
        int count;
        byte data[] = new byte[BUFFER];
        while ((count = gis.read(data, 0, BUFFER)) != -1)
        {
            os.write(data, 0, count);
        }
        gis.close();
    }

    /****************************************************************************************
     * 函数名称：getDate 函数描述：格式化系统时间 输入参数：@param date 输入参数：@return 输出参数： 返回 值：String 备 注：
     ****************************************************************************************/
    public static String getDate(String date)
    {
        String SysDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat(date);
        Date date1 = new Date();
        SysDate = sdf.format(date1);
        return SysDate;
    }

    /**
     * 格式化时间
     * 
     * @param time 时间
     * @return 返回标注格式时间
     */
    public static String formatTime(String time)
    {
        // 201207181114
        String formatTime = "";
        int length = time.length();
        if (length == 8)
        {
            formatTime = time.substring(0, 4) + "-" + time.substring(4, 6) + "-" + time.substring(6, 8);
        }
        else if (length == 12)
        {
            formatTime =
                time.substring(0, 4) + "-" + time.substring(4, 6) + "-" + time.substring(6, 8) + " "
                    + time.substring(8, 10) + ":" + time.substring(10);
        }
        else if (length == 14)
        {
            formatTime =
                time.substring(0, 4) + "-" + time.substring(4, 6) + "-" + time.substring(6, 8) + " "
                    + time.substring(8, 10) + ":" + time.substring(10, 12) + ":" + time.substring(12);
        }

        return formatTime;
    }

    public static String getMonth()
    {
        Calendar cale = Calendar.getInstance();
        // 将Calendar类型转换成Date类型
        Date tasktime = cale.getTime();
        tasktime.setYear(tasktime.getYear() - 1);
        // 设置日期输出的格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM");
        // 格式化输出
        return df.format(tasktime);
    }

    /**
     * 获取开始时间
     * 
     * @return
     */
    public static String getBeginDateNew()
    {
        // 使用默认时区和语言环境获得一个日历
        Calendar cale = Calendar.getInstance();
        // 将Calendar类型转换成Date类型
        Date tasktime = cale.getTime();
        tasktime.setYear(tasktime.getYear() - 1);
        // 设置日期输出的格式
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        // 格式化输出
        return df.format(tasktime);
    }

    /**
     * 获取三个月
     * 
     * @return
     */
    public static String getDelayMonth(int delayMonth)
    {
        // 使用默认时区和语言环境获得一个日历
        Calendar cale = Calendar.getInstance();
        // 将Calendar类型转换成Date类型
        Date tasktime = cale.getTime();
        tasktime.setMonth(tasktime.getMonth() - delayMonth);
        // 设置日期输出的格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        // 格式化输出
        return df.format(tasktime);
    }

    /**
     * 获取年
     * 
     * @return
     */
    public static String getDelayYear(int delayYear)
    {
        // 使用默认时区和语言环境获得一个日历
        Calendar cale = Calendar.getInstance();
        // 将Calendar类型转换成Date类型
        Date tasktime = cale.getTime();
        tasktime.setYear(tasktime.getYear() - delayYear);
        // 设置日期输出的格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        // 格式化输出
        return df.format(tasktime);
    }

    /**
     * 获取开始时间
     * 
     * @return
     */
    public static String getEndDateNew()
    {
        // 使用默认时区和语言环境获得一个日历
        Calendar cale = Calendar.getInstance();
        // 将Calendar类型转换成Date类型
        Date tasktime = cale.getTime();
        tasktime.setYear(tasktime.getYear());
        // 设置日期输出的格式
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        // 格式化输出
        return df.format(tasktime);
    }

    /**
     * 获取到当前的日期
     * 
     * @param delayMonth 延迟的时间
     * @return 得到的时间
     */
    public static String getDayOfMonth(int delayMonth)
    {
        // 使用默认时区和语言环境获得一个日历
        Calendar cale = Calendar.getInstance();
        // 将Calendar类型转换成Date类型
        Date tasktime = cale.getTime();
        tasktime.setMonth(tasktime.getMonth() - delayMonth);
        // 设置日期输出的格式
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        // 格式化输出
        return df.format(tasktime);
    }

    /**
     * 获取开始时间
     * 
     * @return
     */
    public static String getBeginDate()
    {
        // 使用默认时区和语言环境获得一个日历
        Calendar cale = Calendar.getInstance();
        // 将Calendar类型转换成Date类型
        Date tasktime = cale.getTime();
        tasktime.setYear(tasktime.getYear() - 2);
        // 设置日期输出的格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        // 格式化输出
        String time = df.format(tasktime);

        // String month = time.substring(5, 7);
        //
        // int m = Integer.valueOf(month);
        //
        // String realTime = "";
        //
        // if (m < 10) {
        // String temp1 = time.substring(0, 5);
        // String temp2 = time.substring(6, 8);
        // realTime = temp1 + temp2;
        // }
        //
        // String day = time.substring(8, 10);
        // int d = Integer.valueOf(day);
        //
        // if (d < 10) {
        // String temp3 = time.substring(9);
        // realTime = realTime + temp3;
        // }
        //
        // if (!"".equals(realTime)) {
        // time = realTime;
        // }
        //
        // System.out.println("time = " + time);

        return time;
    }

    /**
     * 获取结束时间
     * 
     * @return
     */
    /**
     * @return
     */
    public static String getEndDate()
    {
        // 使用默认时区和语言环境获得一个日历
        Calendar cale = Calendar.getInstance();
        // 将Calendar类型转换成Date类型
        Date tasktime = cale.getTime();
        // 设置日期输出的格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        // 格式化输出
        String time = df.format(tasktime);

        // String month = time.substring(5, 7);
        //
        // int m = Integer.valueOf(month);
        //
        // String realTime = "";
        //
        // if (m < 10) {
        // String temp1 = time.substring(0, 5);
        // String temp2 = time.substring(6, 8);
        // realTime = temp1 + temp2;
        // }
        //
        // String day = time.substring(8, 10);
        // int d = Integer.valueOf(day);
        //
        // if (d < 10) {
        // String temp3 = time.substring(9);
        // realTime = realTime + temp3;
        // }
        //
        // if (!"".equals(realTime)) {
        // }
        return time;
    }
}
