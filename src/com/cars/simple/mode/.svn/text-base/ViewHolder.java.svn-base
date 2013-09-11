/**
 * 
 */
package com.cars.simple.mode;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;

/**
 * @author liuzixiang
 * 
 */
public final class ViewHolder implements ViewBinder {
	public CheckBox checkbox;
	public CheckBox readCheckBox;
	public TextView time;
	public TextView title;
	public TextView text;
	public TextView type;
	public ImageView image;
	public Button btn_1;
	public Button btn_2;
	public EditText edittext;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.SimpleAdapter.ViewBinder#setViewValue(android.view.View,
	 * java.lang.Object, java.lang.String)
	 */
	@Override
	public boolean setViewValue(View view, Object data,
			String textRepresentation) {
		if ((view instanceof ImageView) & (data instanceof Bitmap)) {
			ImageView iv = (ImageView) view;
			Bitmap bm = (Bitmap) data;
			iv.setImageBitmap(bm);
			return true;
		} else if ((view instanceof EditText) & (data instanceof String)) {
			EditText edit = (EditText) view;
			edit.setText((String) data);
			return true;
		}
		return false;
	}
}