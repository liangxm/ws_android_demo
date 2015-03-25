package com.example.rssnews;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rssnews.domain.RssFeed;
import com.example.rssnews.domain.RssItem;
import com.example.rssnews.util.RssFeed_SAXParser;
import com.example.rssnews.util.downloadImageTask;
import com.example.rssnews.view.CornerListView;

public class SecondActivity extends Activity implements OnItemClickListener {

	// �������ȡRSS��ַ
//		public static String RSS_URL = "http://news.qq.com/newsgn/rss_newsgn.xml";
		public static String RSS_URL = "http://www.ifanr.com/feed";

		public final String tag = "RSSReader";
		private RssFeed feed = null;
		
		private downloadImageTask task;

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main1);
			if(isWifi(this))
				new Thread(runnable).start();
			else
				Toast.makeText(SecondActivity.this, "No network connection!", Toast.LENGTH_SHORT).show();  
				
		}

		/*
		 * ��RSS���ݰ󶨵�ui���������ʾ
		 */
		private void showListView() {

			CornerListView itemList = (CornerListView) this.findViewById(R.id.list);
			if (feed == null) {
				setTitle("���ʵ�RSS��Ч");
				return;
			}
			SimpleAdapter simpleAdapter = new SimpleAdapter(this,
					feed.getAllItems(), android.R.layout.simple_list_item_2,
					new String[] { RssItem.TITLE, RssItem.PUBDATE }, new int[] {
							android.R.id.text1, android.R.id.text2 });
			itemList.setAdapter(simpleAdapter);
			itemList.setOnItemClickListener(this);
			itemList.setSelection(0);
			showPanel(0);
		}

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}
		
		@Override
	    public boolean onOptionsItemSelected(MenuItem item) {
			System.out.println("ItemId:"+item.getItemId());
	        if(item.getItemId() == R.id.action_settings){
	        	showCustomDia();
	        }
	        return true;
	    }
		
		/*�Զ���Ի���*/
		private void showCustomDia() {
			AlertDialog.Builder customDia=new AlertDialog.Builder(SecondActivity.this);
			final View viewDia=LayoutInflater.from(SecondActivity.this).inflate(R.layout.custom_dialog, null);
			customDia.setTitle("NewFeed");
			customDia.setView(viewDia);
			customDia.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					EditText diaInput=(EditText) viewDia.findViewById(R.id.txt_cusDiaInput);
					showClickMessage(diaInput.getText().toString());
					RSS_URL = diaInput.getText().toString();
					new Thread(runnable).start();
				}
			});
			customDia.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			customDia.create().show();
		}
		
		/*��ʾ���������*/  
	    private void showClickMessage(String message) {  
	        Toast.makeText(SecondActivity.this, "��ѡ�����: "+message, Toast.LENGTH_SHORT).show();  
	    }

		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
			showPanel(position);
			/*Intent intent = new Intent();
			intent.setClass(this, ShowDescriptionActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("title", feed.getItem(position).getTitle());
			bundle.putString("description",feed.getItem(position).getDescription());
			bundle.putString("link", feed.getItem(position).getLink());
			bundle.putString("pubdate", feed.getItem(position).getPubdate());
			bundle.putString("image", feed.getItem(position).getImage());
			// ��android.intent.extra.INTENT�����������ݲ���
			intent.putExtra("android.intent.extra.rssItem", bundle);
			startActivityForResult(intent, 0);*/
		}
		
		private void showPanel(int position){
			TextView title = (TextView) this.findViewById(R.id.title);
		    TextView description = (TextView) this.findViewById(R.id.description);
		    TextView link = (TextView) this.findViewById(R.id.link);
		    TextView pubdate = (TextView) this.findViewById(R.id.pubdate);
		    ImageView imageView = (ImageView) this.findViewById(R.id.image);
		    title.setText(feed.getItem(position).getTitle());
    		description.setText(Html.fromHtml(feed.getItem(position).getDescription()));
    		link.setText(Html.fromHtml("<a href='"+feed.getItem(position).getLink()+"'>"+feed.getItem(position).getLink()+"</a>"));
    		pubdate.setText(feed.getItem(position).getPubdate());
    		imageView = (ImageView) this.findViewById(R.id.image);
            String url = feed.getItem(position).getImage();
            task = new downloadImageTask();
            task.execute(url,imageView);
		}
		
		/**
		 * make true current connect service is wifi
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

		
		Runnable runnable = new Runnable(){
			@Override
			public void run() {
				try {
					feed = new RssFeed_SAXParser().getFeed(RSS_URL);
					//System.out.println(feed.getAllItems());
				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				} catch (SAXException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				Message message = new Message(); 
	            message.what = 1; 
	            mHandler.sendMessage(message);
				
			}
		};
		private Handler mHandler = new Handler(){ 
	        public void handleMessage(Message msg) { 
	            switch (msg.what) { 
	            case 1: 
	            	showListView();
	                break; 
	            } 
	        }; 
	    };

}
