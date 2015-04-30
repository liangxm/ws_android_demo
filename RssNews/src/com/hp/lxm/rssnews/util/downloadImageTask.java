package com.hp.lxm.rssnews.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class downloadImageTask extends AsyncTask<Object, Integer, Boolean> {

	private ImageView imageView;
	private Bitmap mDownloadImage;

	@Override
	protected Boolean doInBackground(Object... params) {
		System.out.println("[downloadImageTask->]doInBackground " + params[0]);
		mDownloadImage = getHttpBitmap(params[0].toString());
		imageView = (ImageView) params[1];
		return true;
	}

	// 下载完成回调
	@Override
	protected void onPostExecute(Boolean result) {
		imageView.setImageBitmap(mDownloadImage);
		System.out.println("result = " + result);
		super.onPostExecute(result);
	}

	// 更新进度回调
	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
	}

	/**
	 * 从服务器取图片 http://bbs.3gstdy.com
	 * 
	 * @param url
	 * @return
	 */
	private Bitmap getHttpBitmap(String url) {
		URL myFileUrl = null;
		Bitmap bitmap = null;
		try {
			// Log.d(TAG, url);
			System.out.println("image-url:" + url);
			myFileUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			conn.setConnectTimeout(0);
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}
}