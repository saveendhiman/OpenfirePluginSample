package com.example.xmpp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Saveen Dhiman on 02-03-17
 * AppSettings user for save user details for future login
 */


public class AppSettings {

	public static String getPassword(Context paramContext) {
		return getPrefs(paramContext).getString("password", null);
	}

	public static void setPassword(Context paramContext, String paramString) {
		getPrefs(paramContext).edit().putString("password", paramString)
				.commit();
	}

	private static SharedPreferences getPrefs(Context paramContext) {
		return PreferenceManager.getDefaultSharedPreferences(paramContext);
	}

	public static String getUser(Context context) {
		return getPrefs(context).getString("user", null);
	}

	public static void setUser(Context context, String paramString) {
		getPrefs(context).edit().putString("user", paramString).commit();
	}

	public static String getUserName(Context context) {
		return getPrefs(context).getString("username", null);
	}

	public static void setUserName(Context context, String paramString) {
		getPrefs(context).edit().putString("username", paramString).commit();
	}

	public static long getGroupLastRead(Context context, String chatItem) {
		return getPrefs(context).getLong("grouplastread_" + chatItem, 0);
	}

	public static void setGroupLastRead(Context context, String chatItem,
			long lastread) {
		getPrefs(context).edit().putLong("grouplastread_" + chatItem, lastread)
				.commit();
	}

	public static String getUserHash(Context context) {
		return getPrefs(context).getString("userhash", null);
	}

	public static void setUserHash(Context context, String hash) {
		getPrefs(context).edit().putString("userhash", hash).commit();
	}

	public static String getSaveMessage(Context context, String user) {
		return getPrefs(context).getString("savemessage_" + user, "");
	}

	public static void setSaveMessage(Context context, String user,
			String message) {
		getPrefs(context).edit().putString("savemessage_" + user, message)
				.commit();
	}
}
