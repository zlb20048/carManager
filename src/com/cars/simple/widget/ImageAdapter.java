/*
 * Copyright (C) 2011 Patrik �kerfeldt
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cars.simple.widget;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.cars.simple.R;
import com.cars.simple.activity.WebViewActivity;

public class ImageAdapter extends BaseAdapter {

	private final static String TAG = ImageAdapter.class.getSimpleName();

	private Context mContext;
	private LayoutInflater mInflater;
	private static final int[] ids = { R.drawable.magazine07 };

	private List<Map<String, Object>> mDataList = null;

	public ImageAdapter(Context context) {
		mContext = context;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public ImageAdapter(Context context, List<Map<String, Object>> dataList) {
		mContext = context;
		mDataList = dataList;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void setDataList(List<Map<String, Object>> dataList) {
		mDataList = dataList;
	}

	/**
	 * 获取到当前的数值
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getDataList() {
		return mDataList;
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE; // 返回很大的值使得getView中的position不断增大来实现循环
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.image_item, null);
		}
		if (mDataList != null && !mDataList.isEmpty()) {
			final int length = mDataList.size();
			Map<String, Object> map = mDataList.get(position % length);
			((ImageView) convertView.findViewById(R.id.imgView))
					.setImageBitmap((Bitmap) map.get("bitmap"));
			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// 跳转到业务页面
					Map<String, Object> map = mDataList.get(position % length);
					Intent intent = new Intent();
					Bundle bundle = new Bundle();
					bundle.putString("title", (String) map.get("TITLE"));
					bundle.putString("content", (String) map.get("CONTENT"));
					bundle.putString("time", String.valueOf(map.get("PUBTIME")));
					intent.putExtras(bundle);
					intent.setClass(mContext, WebViewActivity.class);
					mContext.startActivity(intent);
				}
			});
		}
		return convertView;
	}
}
