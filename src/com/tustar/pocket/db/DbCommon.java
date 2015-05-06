package com.tustar.pocket.db;

import android.R.string;

public interface DbCommon {

	public static final int DB_VERSION = 1;
	public static final String DB_NAME = "pocket.db";

	public interface Table {
		public final String ADDRESS = "address";
	}

	public interface Column {
		public final String ID = "id";
		public final String NAME = "name";
		public final String PARENT_ID = "parent_id";
		public final String TYPE = "type";
		public final String ZIP = "zip";
		public final String CASH_ON_DELIVERY = "cash_on_delivery";
	}

	public static final String CREATE_ADDRESS = "";
}
