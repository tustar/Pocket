package com.tustar.pocket.service;

import android.app.IntentService;
import android.content.Intent;

import com.tustar.pocket.utils.Logger;

public class PocketIntentService extends IntentService {

	private static final String TAG = PocketIntentService.class.getSimpleName();

	public PocketIntentService() {
		super(TAG);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Logger.i(TAG, "onHandleIntent :: intent = " + intent);
		Logger.d(TAG, "Thread id is = " + Thread.currentThread());
	}

	@Override
	public void onDestroy() {
		Logger.i(TAG, "onDestroy ::");
		super.onDestroy();
	}
}
