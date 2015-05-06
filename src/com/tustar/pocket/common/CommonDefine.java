package com.tustar.pocket.common;

public interface CommonDefine {

	/**
	 * SharePreferences
	 * 
	 * @author tustar
	 * 
	 */
	public interface PrefKey {
		public static final String APP_PREF_FILE = "pocket";
		public static final String PREF_KEY_IMEI = "imei";
	}

	/**
	 * Meta-data name
	 * 
	 * @author tustar
	 * 
	 */
	public interface MetaDataName {
		public static final String META_NAME_UMENG_CHANNEL = "UMENG_CHANNEL";
	}

	/**
	 * Intent key
	 * 
	 * @author tustar
	 * 
	 */
	public interface IntentKey {
		public static final String INTENT_KEY_PUSH_MSG = "push_msg";
	}

	/**
	 * Intent action
	 * 
	 * @author tustar
	 * 
	 */
	public interface IntentAction {
		public static final String INTENT_ACTION_SHOW_GETUI_PUSH = "show_getui_push";
	}

	/**
	 * Umeng key
	 * 
	 * @author tustar
	 * 
	 */
	public interface UmengEvent {
		public static final String UMENG_KEY_CLICK_CHAT = "click_chat";
		public static final String UMENG_KEY_CLICK_IMAGE = "click_image";
		public static final String UMENG_KEY_CLICK_EXIT_OWNER = "click_exit_owner";
	}

	/**
	 * Request code
	 * 
	 * @author tustar
	 * 
	 */
	public interface RequestCode {
		public static final int MainActivity = 0x00001;
	}
}
