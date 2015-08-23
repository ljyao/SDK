package org.weixvn.util;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;

/**
 * 网络辅助类
 * 
 * @author weixvn
 */
public class NetworkHelper {

	public static WifiManager.WifiLock acquireWifiLock(Context paramContext,
			String paramString) {
		WifiManager.WifiLock localWifiLock = ((WifiManager) paramContext
				.getSystemService("wifi")).createWifiLock(paramString);
		localWifiLock.acquire();
		return localWifiLock;
	}

	private static NetworkInfo getNetworkInfo(Intent paramIntent) {
		return (NetworkInfo) paramIntent.getParcelableExtra("networkInfo");
	}

	public static boolean isActiveNetWorkWifi(Context paramContext) {
		ConnectivityManager localConnectivityManager = (ConnectivityManager) paramContext
				.getSystemService("connectivity");
		if (localConnectivityManager != null) {
			NetworkInfo localNetworkInfo = localConnectivityManager
					.getActiveNetworkInfo();
			if ((localNetworkInfo == null)
					|| (localNetworkInfo.getType() != ConnectivityManager.TYPE_WIFI))
				return false;
		}
		return true;
	}

	public static boolean isActiveNetworkMobile(Context paramContext) {
		ConnectivityManager localConnectivityManager = (ConnectivityManager) paramContext
				.getSystemService("connectivity");
		if (localConnectivityManager != null) {
			NetworkInfo localNetworkInfo = localConnectivityManager
					.getActiveNetworkInfo();
			if ((localNetworkInfo == null)
					|| (localNetworkInfo.getType() != ConnectivityManager.TYPE_MOBILE))
				return false;
		}
		return true;
	}

	public static boolean isNetworkAvailable(Context paramContext) {
		ConnectivityManager localConnectivityManager = (ConnectivityManager) paramContext
				.getSystemService("connectivity");
		int k;
		if (localConnectivityManager != null) {
			NetworkInfo[] arrayOfNetworkInfo = localConnectivityManager
					.getAllNetworkInfo();
			if (arrayOfNetworkInfo != null) {
				int j = arrayOfNetworkInfo.length;
				for (k = 0; k < j; k++) {
					if (arrayOfNetworkInfo[k].getState() == NetworkInfo.State.CONNECTED)
						return true;
				}
			}
		}

		return false;
	}

	public static boolean isNetworkMobile(Intent paramIntent) {
		NetworkInfo localNetworkInfo = getNetworkInfo(paramIntent);
		if (localNetworkInfo != null
				&& localNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
			return true;
		}
		return false;
	}

	public static boolean isNetworkWifi(Intent paramIntent) {
		NetworkInfo localNetworkInfo = getNetworkInfo(paramIntent);
		if (localNetworkInfo != null
				&& localNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		}
		return false;
	}

	public static Boolean network(Context activity) {
		ConnectivityManager conMan = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState();
		State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState();
		if (mobile.toString().equals("DISCONNECTED")
				&& wifi.toString().equals("DISCONNECTED")) {
			return false;
		} else {
			return true;
		}
	}
}
