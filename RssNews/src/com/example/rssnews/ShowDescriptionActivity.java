package com.example.rssnews;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowDescriptionActivity extends Activity {
	
	private String url;
	private ImageView imageView;
	private Bitmap mDownloadImage;
	private downloadImageTask task;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_description);
        TextView title = (TextView) this.findViewById(R.id.title);
        TextView description = (TextView) this.findViewById(R.id.description);
        TextView link = (TextView) this.findViewById(R.id.link);
        TextView pubdate = (TextView) this.findViewById(R.id.pubdate);
        
        Intent intent = getIntent();
        Bundle bundle = null;
        if(intent!=null){
        	bundle = intent.getBundleExtra("android.intent.extra.rssItem");
        	if(bundle==null){
        		title.setText("不好意思程序出错啦");
        	}else{
        		title.setText(bundle.getString("title"));
        		description.setText(Html.fromHtml(bundle.getString("description")));
        		link.setText(Html.fromHtml("<a href='"+bundle.getString("link")+"'>"+bundle.getString("link")+"</a>"));
        		pubdate.setText(bundle.getString("pubdate"));
        	}
        }else{
        	title.setText("不好意思程序出错啦");
        }
        
        imageView = (ImageView) this.findViewById(R.id.image);
        url = bundle.getString("image");
        task = new downloadImageTask();
        task.execute(url);
    }
    
    /**
    * 从服务器取图片
    *http://bbs.3gstdy.com
    * @param url
    * @return
    */
    private Bitmap getHttpBitmap(String url) {
         URL myFileUrl = null;
         Bitmap bitmap = null;
         try {
              //Log.d(TAG, url);
        	  System.out.println("image-url:"+url);
              myFileUrl = new URL(url);
         } catch (MalformedURLException e) {
              e.printStackTrace();
         }
         try {
              HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
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
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    class downloadImageTask extends AsyncTask<String, Integer, Boolean> {
    	 
        @Override
        protected Boolean doInBackground(String... params) {
            System.out.println("[downloadImageTask->]doInBackground "
                    + params[0]);
            mDownloadImage = getHttpBitmap(params[0]);
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
 
    }

}
