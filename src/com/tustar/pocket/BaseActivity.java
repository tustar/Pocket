package com.tustar.pocket;

import android.app.Activity;
import android.os.Bundle;

import com.tustar.pocket.utils.Logger;

public class BaseActivity extends Activity {

	private static final String TAG = BaseActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Logger.i(TAG, getClass().getSimpleName());
		super.onCreate(savedInstanceState);
		ActivityCollector.addActivity(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivityCollector.removeActivity(this);
	}
}
