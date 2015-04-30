package com.hp.lxm.rssnews;

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
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
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

import com.hp.lxm.rssnews.domain.RssFeed;
import com.hp.lxm.rssnews.domain.RssItem;
import com.hp.lxm.rssnews.util.RssFeed_SAXParser;
import com.hp.lxm.rssnews.util.downloadImageTask;
import com.hp.lxm.rssnews.view.CornerListView;

public class SecondActivity extends Activity implements OnItemClickListener {

	public static String RSS_URL = "http://www.ifanr.com/feed";

	public final String tag = "RSSReader";
	private RssFeed feed = null;

	private downloadImageTask task;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main1);
		if (isWifi(this))
			new Thread(runnable).start();
		else
			Toast.makeText(SecondActivity.this, "No network connection!",
					Toast.LENGTH_SHORT).show();

	}

	private void showListView() {

		CornerListView itemList = (CornerListView) this.findViewById(R.id.list);
		if (feed == null) {
			setTitle("无效的RSS源");
		} else {
			SimpleAdapter simpleAdapter = new SimpleAdapter(this,
					feed.getAllItems(), android.R.layout.simple_list_item_2,
					new String[] { RssItem.TITLE, RssItem.PUBDATE }, new int[] {
							android.R.id.text1, android.R.id.text2 });
			itemList.setAdapter(simpleAdapter);
			itemList.setOnItemClickListener(this);
			itemList.setSelection(0);
			showPanel(0);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		System.out.println("ItemId:" + item.getItemId());
		if (item.getItemId() == R.id.action_settings) {
			showCustomDia();
		}
		return true;
	}

	private void showCustomDia() {
		AlertDialog.Builder customDia = new AlertDialog.Builder(
				SecondActivity.this);
		final View viewDia = LayoutInflater.from(SecondActivity.this).inflate(
				R.layout.custom_dialog, null);
		customDia.setTitle("NewFeed");
		customDia.setView(viewDia);
		customDia.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						EditText diaInput = (EditText) viewDia
								.findViewById(R.id.txt_cusDiaInput);
						showClickMessage(diaInput.getText().toString());
						RSS_URL = diaInput.getText().toString();
						new Thread(runnable).start();
					}
				});
		customDia.setNegativeButton("取消",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		customDia.create().show();
	}

	private void showClickMessage(String message) {
		Toast.makeText(SecondActivity.this, "Rss源: " + message,
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long id) {
		showPanel(position);
	}

	private void showPanel(int position) {
		TextView title = (TextView) this.findViewById(R.id.title);
		TextView description = (TextView) this.findViewById(R.id.description);
		TextView link = (TextView) this.findViewById(R.id.link);
		TextView pubdate = (TextView) this.findViewById(R.id.pubdate);
		ImageView imageView = (ImageView) this.findViewById(R.id.image);
		title.setText(feed.getItem(position).getTitle());
		description.setText(Html.fromHtml(feed.getItem(position)
				.getDescription()));
		description.setAutoLinkMask(Linkify.ALL);
		description.setMovementMethod(LinkMovementMethod.getInstance());
		link.setText(Html.fromHtml("<a href='"
				+ feed.getItem(position).getLink() + "'>"
				+ feed.getItem(position).getLink() + "</a>"));
		link.setAutoLinkMask(Linkify.ALL);
		link.setMovementMethod(LinkMovementMethod.getInstance());
		pubdate.setText(feed.getItem(position).getPubdate());
		imageView = (ImageView) this.findViewById(R.id.image);
		String url = feed.getItem(position).getImage();
		task = new downloadImageTask();
		task.execute(url, imageView);
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

	/**
	 * get the feed from source
	 */
	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			try {
				feed = new RssFeed_SAXParser().getFeed(RSS_URL);
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
	/**
	 * show the content
	 */
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				showListView();
				break;
			}
		};
	};

}