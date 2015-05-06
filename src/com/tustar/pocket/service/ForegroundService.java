package com.tustar.pocket.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.tustar.pocket.R;
import com.tustar.pocket.activity.MainActivity;
import com.tustar.pocket.utils.Logger;

public class ForegroundService extends Service {

	private static final String TAG = ForegroundService.class.getSimpleName();
	private Context context;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		Logger.i(TAG, "onCreate ::");
		super.onCreate();
		context = this;

		Notification notification = new Notification(R.drawable.ic_launcher,
				"Notification comes", System.currentTimeMillis());
		Intent intent = new Intent(context, MainActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				intent, 0);
		notification.setLatestEventInfo(context, "This is title",
				"This is content", contentIntent);
		startForeground(1, notification);
	}
}
