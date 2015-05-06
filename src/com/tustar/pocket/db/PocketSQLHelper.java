package com.tustar.pocket.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.tustar.pocket.utils.Logger;

public class PocketSQLHelper extends SQLiteOpenHelper {

	public static final String TAG = PocketSQLHelper.class.getSimpleName();
	private static PocketSQLHelper helper;

	public static PocketSQLHelper getHelper(Context context) {
		if (helper == null) {
			helper = new PocketSQLHelper(context.getApplicationContext(), null,
					DbCommon.DB_VERSION);
		}
		return helper;
	}

	public PocketSQLHelper(Context context, CursorFactory factory, int version) {
		super(context, DbCommon.DB_NAME, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Logger.d(TAG, "onCreate :: db = " + db);
		db.execSQL(DbCommon.CREATE_ADDRESS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Logger.d(TAG, "onUpgrade :: db = " + db + ", oldVersion = "
				+ oldVersion + ", newVersion = " + newVersion);
		switch (oldVersion) {
		case 1:
			db.execSQL(DbCommon.CREATE_ADDRESS);
		case 2:
			break;

		default:
			break;
		}
	}
}