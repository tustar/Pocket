package com.tustar.pocket.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * SqliteUtils
 * 
 * @author tustar
 * 
 */
public class SqliteUtils {

	private static volatile SqliteUtils instance;

	private DbHelper dbHelper;
	private SQLiteDatabase wDb;
	private SQLiteDatabase rDb;

	private SqliteUtils(Context context) {

		dbHelper = DbHelper.getHelper(context);
		wDb = dbHelper.getWritableDatabase();
		rDb = dbHelper.getReadableDatabase();
	}

	public static SqliteUtils getInstance(Context context) {
		if (instance == null) {
			synchronized (SqliteUtils.class) {
				if (instance == null) {
					instance = new SqliteUtils(context);
				}
			}
		}
		return instance;
	}

	public SQLiteDatabase getWDb() {
		if (!wDb.isOpen()) {
			wDb = dbHelper.getWritableDatabase();
		}
		return wDb;
	}

	public SQLiteDatabase getRDb() {
		if (!rDb.isOpen()) {
			rDb = dbHelper.getReadableDatabase();
		}
		return rDb;
	}
}
