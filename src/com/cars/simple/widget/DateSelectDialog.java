package com.cars.simple.widget;

import java.util.Calendar;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;

import com.cars.simple.R;

public class DateSelectDialog extends Dialog {

	private Button sure_btn, cancle_btn = null;

	// Time changed flag
	private boolean timeChanged = false;

	//
	private boolean timeScrolled = false;

	/**
	 * 得到的年
	 */
	private int finalYear = 0;

	/**
	 * 得到的月
	 */
	private int finalMonth = 0;

	public DateSelectDialog(Context context) {
		super(context);
	}

	public DateSelectDialog(Context context, int style, String message,
			String url) {
		super(context, style);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.time_layout);
		setTitle("选择日期");
		WindowManager m = getWindow().getWindowManager();
		Display d = m.getDefaultDisplay();
		LayoutParams p = getWindow().getAttributes();
		p.width = (int) (d.getWidth() * 0.9);
		getWindow().setAttributes(p);
		sure_btn = (Button) findViewById(R.id.sure_btn);
		cancle_btn = (Button) findViewById(R.id.cancle_btn);
		initDateView();
	}

	private void initDateView() {
		Calendar c = Calendar.getInstance();
		int curyears = c.get(Calendar.YEAR);
		int curmonths = 1;

		final WheelView years = (WheelView) findViewById(R.id.hour);
		years.setAdapter(new NumericDescWheelAdapter(1970, curyears));
		years.setLabel("years");

		final WheelView months = (WheelView) findViewById(R.id.mins);
		months.setAdapter(new NumericWheelAdapter(1, 12));
		months.setLabel("months");
		months.setCyclic(true);

		// set current time
		years.setCurrentItem(curyears);
		months.setCurrentItem(curmonths);

		// add listeners
		addChangingListener(months, "month");
		addChangingListener(years, "year");

		OnWheelChangedListener wheelListener = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (!timeScrolled) {
					timeChanged = true;
					finalMonth = Integer.parseInt(months.getAdapter().getItem(months.getCurrentItem()));
					finalYear = Integer.parseInt(years.getAdapter().getItem(years.getCurrentItem()));
					timeChanged = false;
				}
			}
		};

		years.addChangingListener(wheelListener);
		months.addChangingListener(wheelListener);

		OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
			public void onScrollingStarted(WheelView wheel) {
				timeScrolled = true;
			}

			public void onScrollingFinished(WheelView wheel) {
				timeScrolled = false;
				timeChanged = true;
				finalMonth = Integer.parseInt(months.getAdapter().getItem(months.getCurrentItem()));
				finalYear = Integer.parseInt(years.getAdapter().getItem(years.getCurrentItem()));
				timeChanged = false;
			}
		};

		years.addScrollingListener(scrollListener);
		months.addScrollingListener(scrollListener);

	}

	/**
	 * Adds changing listener for wheel that updates the wheel label
	 * 
	 * @param wheel
	 *            the wheel
	 * @param label
	 *            the wheel label
	 */
	private void addChangingListener(final WheelView wheel, final String label) {
		wheel.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				wheel.setLabel(newValue != 1 ? label + "s" : label);
			}
		});
	}

	public Button getSureBtn() {
		return sure_btn;
	}

	public Button getCancelBtn() {
		return cancle_btn;
	}

	/**
	 * 获取用户选择的年月
	 * 
	 * @return
	 */
	public int getYear() {
		return finalYear;
	}

	public int getMonth() {
		return finalMonth;
	}
}
