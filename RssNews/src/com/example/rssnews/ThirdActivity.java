package com.example.rssnews;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.example.rssnews.domain.RssFeed;
import com.example.rssnews.util.RssFeed_SAXParser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class ThirdActivity extends Activity {
	
	private ProgressDialog dialog;
	private RssFeed feed;
	
	public static String RSS_URL = "http://www.ifanr.com/feed";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置一个progressdialog的弹窗
		dialog = ProgressDialog.show(this, null, "程序正在加载，请稍候...", true, false);
		// 启动一个处理loading业务的线程
		if (isWifi(this)){
			new LoadingThread(this).start();
//			new Thread(runnable).start();
		}else{
			Toast.makeText(ThirdActivity.this, "No network connection!",
					Toast.LENGTH_SHORT).show();
		}
	}

	private class LoadingThread extends Thread {
		private ThirdActivity activity;

		public LoadingThread(ThirdActivity act) {
			Log.d(">>>>>LoadingThread", "构造方法");
			activity = act;
		}

		public void run() {
			Log.d(">>>>>LoadingThread", "传递message");
			// *********************
			// 处理业务
			// *********************
			try {
				feed = new RssFeed_SAXParser().getFeed(RSS_URL);
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(feed!=null){
				Message message = new Message();
				message.what = 1;
				activity.mHandler.sendMessage(message);
			}else{
				Toast.makeText(ThirdActivity.this, "Not found the RSS source!",
						Toast.LENGTH_SHORT).show();
			}
			// 发送消息
		}
	}
	
	/**
	 * make true current connect service is wifi
	 * 
	 * @param mContext
	 * @return
	 */
	private boolean isWifi(Context mContext) {
		ConnectivityManager connectivityManager = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null
				&& activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		}
		return false;
	}

	// 处理跳转到主Activity
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Log.d(">>>>>Mhandler", "开始handleMessage");
				Intent mIntent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putSerializable("feed", feed);
				mIntent.setClass(ThirdActivity.this, SecondActivity.class);
				mIntent.putExtra("bundle", bundle);
				startActivityForResult(mIntent, 0);
				ThirdActivity.this.finish();
				Log.d(">>>>>Mhandler", "LoadActivity关闭");
				if (dialog.isShowing())
					dialog.dismiss();
				break;
			}
		}
	};
}
