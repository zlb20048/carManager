package com.cars.simple.service.download;

import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import com.cars.simple.R;
import com.cars.simple.fusion.FusionCode;
import com.cars.simple.fusion.Variable;

/**
 * 
 * @author shifeng
 * 
 */
public class GetServerImageProcessor {

	private static GetServerImageProcessor instance = null;

	private GetServerImageProcessor() {
	}

	public static synchronized GetServerImageProcessor getInstance() {
		if (instance == null) {
			instance = new GetServerImageProcessor();
		}
		return instance;
	}

	/**
	 * 获取图片
	 * 
	 * @param context
	 *            context
	 * @param map
	 *            Map
	 * @param handler
	 *            Handler
	 * @return
	 */
	public Bitmap getMaganizeImage(Context context, Map<String, Object> map,
			Handler handler) {
		String url = (String) map.get("ARTICLE_IMAGE");
		String id = map.get("ARTICLEID") + "";
		Bitmap bitmap = BitmapFactory.decodeFile(FusionCode.ECMC_FILE_PATH
				+ "maganize_" + id + ".png");
		if (null == bitmap) {
			bitmap = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.magazine12);
			if (url != null) {
				url = Variable.SERVER_IMAGE_URL + url;
				new ImageDownload(context).getImg(url, "maganize_" + id
						+ ".png", map, handler);
			}
		}
		return bitmap;
	}

	/**
	 * 获取图片
	 * 
	 * @param context
	 *            context
	 * @param map
	 *            Map
	 * @param handler
	 *            Handler
	 * @return
	 */
	public Bitmap getPromotionType(Context context, Map<String, Object> map,
			Handler handler) {
		Object url = map.get("PART_IMAGE");
		String id = map.get("PARTID") + "";
		Bitmap bitmap = BitmapFactory.decodeFile(FusionCode.ECMC_FILE_PATH
				+ "promotion_type_" + id + ".png");
		if (null == bitmap) {
			bitmap = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.discount05);
			if (url != null) {
				url = Variable.SERVER_IMAGE_URL + url;
				new ImageDownload(context).getImg(String.valueOf(url),
						"promotion_type_" + id + ".png", map, handler);
			}
		}
		return bitmap;
	}

	/**
	 * 获取图片
	 * 
	 * @param context
	 *            context
	 * @param map
	 *            Map
	 * @param handler
	 *            Handler
	 * @return
	 */
	public Bitmap getPromotionList(Context context, Map<String, Object> map,
			Handler handler) {
		Object url = map.get("ARTICLE_IMAGE");
		String id = map.get("ARTICLEID") + "";
		Bitmap bitmap = BitmapFactory.decodeFile(FusionCode.ECMC_FILE_PATH
				+ "promotion_list_" + id + ".png");
		if (null == bitmap) {
			bitmap = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.discount05);
			if (url != null) {
				url = Variable.SERVER_IMAGE_URL + url;
				new ImageDownload(context).getImg(String.valueOf(url),
						"promotion_list_" + id + ".png", map, handler);
			}
		}
		return bitmap;
	}

	/**
	 * 获取图片
	 * 
	 * @param context
	 *            context
	 * @param map
	 *            Map
	 * @param handler
	 *            Handler
	 * @return
	 */
	public Bitmap getPromotionBanner(Context context, Map<String, Object> map,
			Handler handler) {
		Object url = map.get("ARTICLE_IMAGE_MOBIL");
		String id = map.get("ARTICLEID") + "";
		Bitmap bitmap = BitmapFactory.decodeFile(FusionCode.ECMC_FILE_PATH
				+ "promotion_banner_" + id + ".png");
		if (null == bitmap) {
			bitmap = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.discount06);
			if (url != null) {
				url = Variable.SERVER_IMAGE_URL + url;
				new ImageDownload(context).getImg(String.valueOf(url),
						"promotion_banner_" + id + ".png", map, handler);
			}
		}
		return bitmap;
	}

	/**
	 * 获取图片
	 * 
	 * @param context
	 *            context
	 * @param map
	 *            Map
	 * @param handler
	 *            Handler
	 * @return
	 */
	public Bitmap getMaganizeAdvImage(Context context, Map<String, Object> map,
			Handler handler) {
		String url = (String) map.get("ARTICLE_IMAGE");
		String id = map.get("ARTICLEID") + "";
		Bitmap bitmap = BitmapFactory.decodeFile(FusionCode.ECMC_FILE_PATH
				+ "maganize_" + id + ".png");
		if (null == bitmap) {
			bitmap = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.magazine13);
			if (url != null) {
				url = Variable.SERVER_IMAGE_URL + url;
				new ImageDownload(context).getImg(url, "maganize_" + id
						+ ".png", map, handler);
			}
		}
		return bitmap;
	}
}
