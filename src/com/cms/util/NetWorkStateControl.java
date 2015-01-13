package com.cms.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetWorkStateControl {
	//判断所有的网络连接是否可用
	public static boolean isNetworkAvailable(Context context) {   
        ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);   
        if (connectivity == null) {   
            LogUtil.d("Unavailable");
            return false;   
        } else {   
            NetworkInfo[] info = connectivity.getAllNetworkInfo();   
            if (info != null) {   
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {   
                        LogUtil.d("Available");  
                        return true;   
                    }
                }
            }   
        }  
        return false;   
    }
	//判断网络连接是否连接
	public static boolean isNetworkConnected(Context context) {  
	     if (context != null) {  
	         ConnectivityManager mConnectivityManager = (ConnectivityManager) context  
	                 .getSystemService(Context.CONNECTIVITY_SERVICE);  
	         NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();  
	         if (mNetworkInfo != null) {  
	             return mNetworkInfo.isAvailable();  
	         }
	     }
	     return false;  
	 }
	//判断网络类型
	public static int getConnectedType(Context context) {  
	     if (context != null) {  
	         ConnectivityManager mConnectivityManager = (ConnectivityManager) context  
	                 .getSystemService(Context.CONNECTIVITY_SERVICE);  
	         NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();  
	         if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {  
	             return mNetworkInfo.getType();  
	         }  
	     }  
	     return -1;  
	 }
	//判断网络是否连接
	public static boolean isNetworkConnect(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
				if (mNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
					return mNetworkInfo.isAvailable();
				} else if (mNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
					return mNetworkInfo.isAvailable();
				}
			}
		}
		return false;
	}
	//判断移动网络是否连接
	public static boolean isMobileConnected(Context context) {  
	     if (context != null) {  
	         ConnectivityManager mConnectivityManager = (ConnectivityManager) context  
	                 .getSystemService(Context.CONNECTIVITY_SERVICE);  
	         NetworkInfo mMobileNetworkInfo = mConnectivityManager  
	                 .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);  
	         if (mMobileNetworkInfo != null) {  
	             return mMobileNetworkInfo.isAvailable();  
	         }  
	     }  
	     return false;  
	 }
	//判断wifi是否连接
	public static boolean isWifiConnected(Context context) {  
	     if (context != null) {  
	         ConnectivityManager mConnectivityManager = (ConnectivityManager) context  
	                 .getSystemService(Context.CONNECTIVITY_SERVICE);  
	         NetworkInfo mWiFiNetworkInfo = mConnectivityManager
	                 .getNetworkInfo(ConnectivityManager.TYPE_WIFI);  
	         if (mWiFiNetworkInfo != null) {  
	             return mWiFiNetworkInfo.isAvailable();  
	         }
	     }  
	     return false;
	 }
}
