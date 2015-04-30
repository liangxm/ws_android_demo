package com.lxm.listviewdemo;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CustomizedListView extends Activity {
	// 所有的静态变量
	private static final String URL = "http://api.androidhive.info/music/music.xml";// xml目的地址,打开地址看一下
	// XML 节点
	private static final String KEY_SONG = "song"; // parent node
	private static final String KEY_ID = "id";
	protected static final String KEY_TITLE = "title";
	protected static final String KEY_ARTIST = "artist";
	protected static final String KEY_DURATION = "duration";
	protected static final String KEY_THUMB_URL = "thumb_url";

	private ListView list;
	private LazyAdapter adapter;
	private ArrayList<HashMap<String, String>> songsList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new Thread(runnable).start();
	}

	private void mainProcess() {
		list = (ListView) findViewById(R.id.list);
		adapter = new LazyAdapter(this, songsList);
		list.setAdapter(adapter);

		// 为单一列表行添加单击事件
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 这里可以自由发挥，比如播放一首歌曲等等
			}
		});
	}

	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			songsList = new ArrayList<HashMap<String, String>>();

			XMLParser parser = new XMLParser();
			String xml = parser.getXmlFromUrl(URL); // 从网络获取xml
			Document doc = parser.getDomElement(xml); // 获取 DOM 节点

			NodeList nl = doc.getElementsByTagName(KEY_SONG);
			// 循环遍历所有的歌节点 <song>
			for (int i = 0; i < nl.getLength(); i++) {
				// 新建一个 HashMap
				HashMap<String, String> map = new HashMap<String, String>();
				Element e = (Element) nl.item(i);
				// 每个子节点添加到HashMap关键= >值
				map.put(KEY_ID, parser.getValue(e, KEY_ID));
				map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
				map.put(KEY_ARTIST, parser.getValue(e, KEY_ARTIST));
				map.put(KEY_DURATION, parser.getValue(e, KEY_DURATION));
				map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));

				// HashList添加到数组列表
				songsList.add(map);
			}
			Message message = new Message(); 
            message.what = 1; 
            mHandler.sendMessage(message);
		}
	};

	// 定义Handler对象
	private Handler mHandler = new Handler() {
		@Override
		// 当有消息发送出来的时候就执行Handler的这个方法
		public void handleMessage(Message msg) {
			switch (msg.what) { 
            case 1: 
            	mainProcess();
                break; 
            } 
		}
	};

}