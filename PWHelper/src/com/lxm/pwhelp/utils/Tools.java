package com.lxm.pwhelp.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.os.Environment;
import android.view.Gravity;
import android.widget.Toast;
/**
 * 
 * @author listener (xiaoman.test@gmail.com)
 * Create at 2012-8-17 ï¿½ï¿½ï¿½ï¿½10:14:40
 */
public class Tools {
	/**
	 * ï¿½ï¿½ï¿½ï¿½Ç·ï¿½ï¿½ï¿½ï¿½SDCard
	 * @return
	 */
	public static boolean hasSdcard(){
		String state = Environment.getExternalStorageState();
		if(state.equals(Environment.MEDIA_MOUNTED)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * show warning dialog
	 * @param title
	 * @param message
	 */
	public static void showWarningDialog(Context context,String title,String message){
		new AlertDialog.Builder(context).setTitle(title)
		.setMessage(message).setPositiveButton("È·¶¨", null)
		.show();
	}
	
	/**
	 * show sucess dialog
	 * @param context
	 * @param title
	 * @param message
	 * @param listener
	 */
	public static void showSucessDialog(Context context,String title,String message,OnClickListener listener){
		new AlertDialog.Builder(context)
		.setTitle(title)
		.setMessage(message)
		.setPositiveButton("È·¶¨",listener).show();
	}
	
	/**
	 * show info dialog
	 * @param context
	 * @param title
	 * @param message
	 */
	public static void showErrorDialog(Context context,String title,String message){
		new AlertDialog.Builder(context).setTitle(title)
		.setMessage(message).setPositiveButton("È·¶¨", null)
		.show();
	}
	
	public static void showToast(Context context,CharSequence message) {
		Toast mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		mToast.setGravity(Gravity.CENTER, 0, 0);
		mToast.show();
	}
}
