package com.tustar.pocket.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.tustar.pocket.common.CommonDefine.PrefKey;

/**
 * Created by Tu on 4/8/15.
 */
public class DeviceUtils {

	private static final String TAG = DeviceUtils.class.getSimpleName();

	private DeviceUtils() {

	}

	/**
	 * Get the mobile phone imei. eg.A0000049022A23
	 * 
	 * @param context
	 * @return
	 */
	public static String getIMEI(Context context) {

		if (context == null) {
			Logger.w(TAG, "getIMEI :: context is null");
			return "0000" + System.currentTimeMillis();
		}

		String deviceId = "";

		// Get device id from sharedPreferences.
		deviceId = PrefUtils.getString(context, PrefKey.PREF_KEY_IMEI, "");

		if (!TextUtils.isEmpty(deviceId)) {
			return deviceId;
			// return "A0000049022A23";
		}

		// The device is simulator.
		if ((Build.MODEL.equals("sdk")) || (Build.MODEL.equals("google_sdk"))) {

			deviceId = "Simulator_" + System.currentTimeMillis();

			// Save device id to sharedPreferences
			PrefUtils.putString(context, PrefKey.PREF_KEY_IMEI, deviceId
					+ "_wshop");
			return deviceId;
		}

		try {
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			deviceId = tm.getDeviceId();
			Logger.d(TAG, "getIMEI :: deviceId = " + deviceId);
		} catch (Exception e) {
			Logger.e(TAG, "getIMEI :: Error = " + e.getMessage());
			e.printStackTrace();
		}

		// The device id is "000...000", so reset device id.
		try {
			long tmp = Long.parseLong(deviceId);
			if (tmp == 0) {
				deviceId = "0000" + System.currentTimeMillis();
			}
		} catch (Exception e) {
			Logger.e(TAG, "getIMEI :: Error = " + e.getMessage());
			e.printStackTrace();
		}

		// The device id is empty or null, so use
		if (TextUtils.isEmpty(deviceId) || deviceId.equals("null")) {
			deviceId = "0000" + System.currentTimeMillis();
		}

		// Save device id to sharedPreferences
		PrefUtils
				.putString(context, PrefKey.PREF_KEY_IMEI, deviceId + "_wshop");

		return deviceId + "_wshop";
		// return "A0000049022A23";
	}

	/**
	 * Get meta-data value by key
	 * 
	 * @param context
	 * @param metaKey
	 * @return
	 */
	public static String getMetaValue(Context context, String metaKey) {

		if (context == null || metaKey == null) {
			return null;
		}

		Bundle metaData = null;
		String metaValue = "";
		try {
			ApplicationInfo ai = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);
			if (null != ai) {
				metaData = ai.metaData;
			}
			if (null != metaData) {
				metaValue = metaData.getString(metaKey);
				if (null == metaValue) {
					metaValue = String.valueOf(metaData.getInt(metaKey));
				}
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		return metaValue;
	}

	/**
	 * Get device dpi(eg. hdpi, xhdpi, xxdpi)
	 * 
	 * @param activity
	 * @return default is hdpi
	 */
	public static String getDeviceDpi(Activity activity) {
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay()
				.getMetrics(mDisplayMetrics);
		int dpi = mDisplayMetrics.densityDpi;
		if (dpi > 200 && dpi < 280) {
			return "hdpi";
		} else if (dpi >= 280 && dpi < 400) {
			return "xhdpi";
		} else if (dpi >= 400) {
			return "xxhdpi";
		}
		return "hdpi";
	}
}
