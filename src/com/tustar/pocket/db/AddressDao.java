package com.tustar.pocket.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tustar.pocket.db.DbCommon.Column;
import com.tustar.pocket.db.DbCommon.Table;
import com.tustar.pocket.model.Address;
import com.tustar.pocket.utils.Logger;

public class AddressDao {

	private static final String TAG = AddressDao.class.getSimpleName();
	private static AddressDao self;
	private Context context;

	private AddressDao(Context context) {
		this.context = context.getApplicationContext();
	}

	public static AddressDao getDao(Context context) {
		if (null == self) {
			self = new AddressDao(context.getApplicationContext());
		}
		return self;
	}

	public List<Address> queryProvinces() {

		SQLiteDatabase db = SqliteUtils.getInstance(context).getRDb();
		try {
			List<Address> provinces = new ArrayList<Address>();
			String sql = "select * from " + Table.ADDRESS + " where "
					+ Column.PARENT_ID + " = '3583' and " + Column.TYPE
					+ " = 2";
			Logger.d(TAG, "queryProvinces :: sql = " + sql);
			Cursor cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				Address address = new Address();
				address.setId(cursor.getString(cursor.getColumnIndex(Column.ID)));
				address.setName(cursor.getString(cursor
						.getColumnIndex(Column.NAME)));
				address.setParent_id(cursor.getString(cursor
						.getColumnIndex(Column.PARENT_ID)));
				address.setType(cursor.getInt(cursor
						.getColumnIndex(Column.TYPE)));
				Logger.i(TAG, "Address:" + address);
				provinces.add(address);
			}
			cursor.close();
			return provinces;
		} catch (Exception e) {
			Logger.e(TAG, "queryProvinces ::" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.close();
			}
		}
		return null;
	}
}
