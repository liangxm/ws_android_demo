package com.lxm.pwhelp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.view.Gravity;
import android.widget.Toast;

/**
 * 
 * @author listener (xiaoman.test@gmail.com) Create at 2012-8-17 ï¿½ï¿½ï¿½ï¿½10:14:40
 */
public class Tools {
	/**
	 * ï¿½ï¿½ï¿½ï¿½Ç·ï¿½ï¿½ï¿½ï¿½SDCard
	 * 
	 * @return
	 */
	public static boolean hasSdcard() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * show warning dialog
	 * 
	 * @param title
	 * @param message
	 */
	public static void showWarningDialog(Context context, String title,
			String message) {
		new AlertDialog.Builder(context).setTitle(title).setMessage(message)
				.setPositiveButton("È·¶¨", null).show();
	}

	/**
	 * show sucess dialog
	 * 
	 * @param context
	 * @param title
	 * @param message
	 * @param listener
	 */
	public static void showSucessDialog(Context context, String title,
			String message, OnClickListener listener) {
		new AlertDialog.Builder(context).setTitle(title).setMessage(message)
				.setPositiveButton("È·¶¨", listener).show();
	}

	/**
	 * show info dialog
	 * 
	 * @param context
	 * @param title
	 * @param message
	 */
	public static void showErrorDialog(Context context, String title,
			String message) {
		new AlertDialog.Builder(context).setTitle(title).setMessage(message)
				.setPositiveButton("È·¶¨", null).show();
	}

	/**
	 * show custom toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showToast(Context context, CharSequence message) {
		Toast mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		mToast.setGravity(Gravity.CENTER, 0, 0);
		mToast.show();
	}

	/**
	 * is email string check
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * is wifi Available
	 */
	public static boolean isWiFiConnected(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifiNetworkInfo = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifiNetworkInfo.isConnected()) {
			return true;
		}
		return false;
	}
	
	/**
	 * get string resources
	 */
	public static String getResources(Context context,int resId){
		return context.getResources().getString(resId);
	}
	
	public static String getToday(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
		return sdf.format(new Date());
	}
}
