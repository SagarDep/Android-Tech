package com.devilwwj.antbuild;

import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;

public class Utils {
	@SuppressLint("NewApi")
	public static boolean checkPermission(Context context, String permission) {
	    boolean result = false;
	    if (Build.VERSION.SDK_INT >= 23) {
	        if (context.checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
	            result = true;
	        }
	    } else {
	        PackageManager pm = context.getPackageManager();
	        if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
	            result = true;
	        }
	    }
	    return result;
	}

	public static String getDeviceInfo(Context context) {
	    try {
	        org.json.JSONObject json = new org.json.JSONObject();
	        android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
	                .getSystemService(Context.TELEPHONY_SERVICE);
	        String device_id = null;
	        if (checkPermission(context, permission.READ_PHONE_STATE)) {
	            device_id = tm.getDeviceId();
	        }
	        android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context
	                .getSystemService(Context.WIFI_SERVICE);
	        String mac = wifi.getConnectionInfo().getMacAddress();
	        json.put("mac", mac);
	        if (TextUtils.isEmpty(device_id)) {
	            device_id = mac;
	        }
	        if (TextUtils.isEmpty(device_id)) {
	            device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
	                    android.provider.Settings.Secure.ANDROID_ID);
	        }
	        json.put("device_id", device_id);
	        return json.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
}
