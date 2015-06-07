package com.lxm.pwhelp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class LoadingActivity extends Activity {

	private ProgressDialog dialog;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		dialog = ProgressDialog.show(this, null, "程序正在加载，请稍后...");
		new LoadingThread(this).start();
	}
	
	private class LoadingThread extends Thread {
		private LoadingActivity activity;
		
		public LoadingThread(LoadingActivity act) {
			this.activity = act;
		}
		
		public void run() {
			/**
			 * logic hander
			 */
			activity.mHandler.sendEmptyMessage(0);
		}
	}
	
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg){
			Intent mIntent = new Intent();
			LoadingActivity.this.finish();
			if(dialog.isShowing())
				dialog.dismiss();
		}
	};
}
