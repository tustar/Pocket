package com.tustar.pocket.utils;

import android.util.Log;

import com.tustar.pocket.BuildConfig;

public class Logger {

	public static boolean ENABLE_LOG = BuildConfig.DEBUG;

	public static void v(String tag, String msg) {
		if (ENABLE_LOG) {
			try {
				Log.v(tag, msg);
			} catch (Exception e) {
			}
		}
	}

	public static void i(String tag, String msg) {
		if (ENABLE_LOG) {
			try {
				Log.i(tag, msg);
			} catch (Exception e) {
			}
		}
	}

	public static void i(String tag, String msg, Throwable tr) {
		if (ENABLE_LOG) {
			try {
				Log.i(tag, msg, tr);
			} catch (Exception e) {

			}
		}
	}

	public static void d(String tag, String msg) {
		if (ENABLE_LOG) {
			try {
				Log.d(tag, msg);
			} catch (Exception e) {

			}
		}
	}

	public static void d(String tag, String msg, Throwable tr) {
		if (ENABLE_LOG) {
			try {
				Log.d(tag, msg, tr);
			} catch (Exception e) {

			}
		}
	}

	public static void w(String tag, String msg) {
		if (ENABLE_LOG) {
			try {
				Log.w(tag, msg);
			} catch (Exception e) {

			}
		}
	}

	public static void w(String tag, String msg, Throwable tr) {
		if (ENABLE_LOG) {
			try {
				Log.w(tag, msg, tr);
			} catch (Exception e) {

			}
		}
	}

	public static void e(String tag, String msg) {
		if (ENABLE_LOG) {
			try {
				Log.e(tag, msg);
			} catch (Exception e) {

			}
		}
	}

	public static void e(String tag, String msg, Throwable tr) {
		if (ENABLE_LOG) {
			try {
				Log.e(tag, msg, tr);
			} catch (Exception e) {

			}
		}
	}
}
