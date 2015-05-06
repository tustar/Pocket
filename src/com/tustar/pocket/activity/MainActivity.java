package com.tustar.pocket.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.tustar.pocket.BaseActivity;
import com.tustar.pocket.R;
import com.tustar.pocket.utils.Logger;

public class MainActivity extends BaseActivity {

	private static final String TAG = MainActivity.class.getSimpleName();
	private Context context;

	private View view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;

		/**
		 * 在onCreate方法中获取View的高度和宽度（Hack 13)
		 */
		view = findViewById(R.id.main_my_view);
		view.post(new Runnable() {

			@Override
			public void run() {
				Logger.d(TAG, "View has width = " + view.getWidth()
						+ ", height = " + view.getHeight());
			}
		});
	}
}
