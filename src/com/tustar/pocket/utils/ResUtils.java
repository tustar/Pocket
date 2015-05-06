package com.tustar.pocket.utils;

import java.lang.reflect.Field;

import android.content.Context;


@SuppressWarnings("rawtypes")
public class ResUtils {

	private static final String TAG = ResUtils.class.getName();

	private static ResUtils res = null;
	private static String packageName = null;

	private static Class anim = null;
	private static Class array = null;
	private static Class attr = null;
	private static Class color = null;
	private static Class dimen = null;
	private static Class drawable = null;
	private static Class id = null;
	private static Class layout = null;
	private static Class raw = null;
	private static Class string = null;
	private static Class style = null;
	private static Class styleable = null;

	private ResUtils(String paramString) {

		// anim => 1
		try {
			anim = Class.forName(paramString + ".R$anim");
		} catch (ClassNotFoundException localClassNotFoundException1) {
			Logger.d(TAG, localClassNotFoundException1.getMessage());
		}
		// array => 2
		try {
			array = Class.forName(paramString + ".R$array");
		} catch (ClassNotFoundException localClassNotFoundException2) {
			Logger.d(TAG, localClassNotFoundException2.getMessage());
		}
		// attr => 3
		try {
			attr = Class.forName(paramString + ".R$attr");
		} catch (ClassNotFoundException localClassNotFoundException3) {
			Logger.d(TAG, localClassNotFoundException3.getMessage());
		}
		// color => 4
		try {
			color = Class.forName(paramString + ".R$color");
		} catch (ClassNotFoundException localClassNotFoundException4) {
			Logger.d(TAG, localClassNotFoundException4.getMessage());
		}
		// dimen => 5
		try {
			dimen = Class.forName(paramString + ".R$dimen");
		} catch (ClassNotFoundException localClassNotFoundException5) {
			Logger.d(TAG, localClassNotFoundException5.getMessage());
		}
		// drawable => 6
		try {
			drawable = Class.forName(paramString + ".R$drawable");
		} catch (ClassNotFoundException localClassNotFoundException6) {
			Logger.d(TAG, localClassNotFoundException6.getMessage());
		}
		// id => 7
		try {
			id = Class.forName(paramString + ".R$id");
		} catch (ClassNotFoundException localClassNotFoundException7) {
			Logger.d(TAG, localClassNotFoundException7.getMessage());
		}
		// layout => 8
		try {
			layout = Class.forName(paramString + ".R$layout");
		} catch (ClassNotFoundException localClassNotFoundException8) {
			Logger.d(TAG, localClassNotFoundException8.getMessage());
		}
		// raw => 9
		try {
			raw = Class.forName(paramString + ".R$raw");
		} catch (ClassNotFoundException localClassNotFoundException9) {
			Logger.d(TAG, localClassNotFoundException9.getMessage());
		}
		// string => 10
		try {
			string = Class.forName(paramString + ".R$string");
		} catch (ClassNotFoundException localClassNotFoundException10) {
			Logger.d(TAG, localClassNotFoundException10.getMessage());
		}
		// style => 11
		try {
			style = Class.forName(paramString + ".R$style");
		} catch (ClassNotFoundException localClassNotFoundException11) {
			Logger.d(TAG, localClassNotFoundException11.getMessage());
		}
		// styleable => 12
		try {
			styleable = Class.forName(paramString + ".R$styleable");
		} catch (ClassNotFoundException localClassNotFoundException12) {
			Logger.d(TAG, localClassNotFoundException12.getMessage());
		}
	}

	public static synchronized ResUtils getInstance(Context paramContext) {

		if (res == null) {
			packageName = packageName != null ? packageName : paramContext
					.getPackageName();
			res = new ResUtils(packageName);
		}

		return res;
	}

	public static void setPackageName(String paramString) {
		packageName = paramString;
	}


	// ////////////////////
	// anim => 1
	/**
	 * @param paramString
	 * @return
	 */
	public int getRAnim(String paramString) {
		return getResId(anim, paramString);
	}

	// array => 2
	/**
	 * @param paramString
	 * @return
	 */
	public int getRArray(String paramString) {
		return getResId(array, paramString);
	}

	// attr => 3
	/**
	 * @param paramString
	 * @return
	 */
	public int getRAttr(String paramString) {
		return getResId(attr, paramString);
	}

	// color => 4
	/**
	 * @param paramString
	 * @return
	 */
	public int getRColor(String paramString) {
		return getResId(color, paramString);
	}

	// dimen => 5
	/**
	 * @param paramString
	 * @return
	 */
	public int getRDimen(String paramString) {
		return getResId(dimen, paramString);
	}

	// drawable => 6
	/**
	 * @param paramString
	 * @return
	 */
	public int getRDrawable(String paramString) {
		return getResId(drawable, paramString);
	}

	// id => 7
	/**
	 * @param paramString
	 * @return
	 */
	public int getRId(String paramString) {
		return getResId(id, paramString);
	}

	// layout => 8
	/**
	 * @param paramString
	 * @return
	 */
	public int getRLayout(String paramString) {
		return getResId(layout, paramString);
	}

	// raw => 9
	/**
	 * @param paramString
	 * @return
	 */
	public int getRRaw(String paramString) {
		return getResId(raw, paramString);
	}

	// string => 10
	/**
	 * @param paramString
	 * @return
	 */
	public int getRString(String paramString) {
		return getResId(string, paramString);
	}

	// style => 11
	/**
	 * @param paramString
	 * @return
	 */
	public int getRStyle(String paramString) {
		return getResId(style, paramString);
	}

	// styleable => 12
	/**
	 * @param paramString
	 * @return
	 */
	public int getRStyleable(String paramString) {
		return getResId(styleable, paramString);
	}

	// styleable array => 13
	/**
	 * @param paramString
	 * @return
	 */
	public int[] getRStyleableArray(String paramString) {

		Class paramClass = styleable;
		if (paramClass == null) {
			Logger.d(TAG, "getRes(null," + paramString + ")");
			throw new IllegalArgumentException(
					"ResClass is not initialized. Please make sure you have added neccessary resources. Also make sure you have "
							+ packageName
							+ ".R$* configured in obfuscation. field="
							+ paramString);
		}

		try {
			Field localField = paramClass.getField(paramString);
			return (int[]) localField.get(paramString);
		} catch (Exception localException) {
			Logger.d(TAG, "getRes(" + paramClass.getName() + ", " + paramString
					+ ")");
			Logger.d(
					TAG,
					"Error getting resource. Make sure you have copied all resources (res/) from SDK to your project. ");

			Logger.d(TAG, localException.getMessage());
		}

		return null;
	}

	private int getResId(Class<?> paramClass, String paramString) {
		if (paramClass == null) {
			Logger.d(TAG, "getRes(null," + paramString + ")");
			throw new IllegalArgumentException(
					"ResClass is not initialized. Please make sure you have added neccessary resources. Also make sure you have "
							+ packageName
							+ ".R$* configured in obfuscation. field="
							+ paramString);
		}

		try {
			Field localField = paramClass.getField(paramString);
			return localField.getInt(paramString);
		} catch (Exception localException) {
			Logger.d(TAG, "getRes(" + paramClass.getName() + ", " + paramString
					+ ")");
			Logger.d(
					TAG,
					"Error getting resource. Make sure you have copied all resources (res/) from SDK to your project. ");

			Logger.d(TAG, localException.getMessage());
		}
		return -1;
	}
}