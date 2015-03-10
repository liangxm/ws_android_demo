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
	// ���еľ�̬����
	private static final String URL = "http://api.androidhive.info/music/music.xml";// xmlĿ�ĵ�ַ,�򿪵�ַ��һ��
	// XML �ڵ�
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

		// Ϊ��һ�б�����ӵ����¼�
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// ����������ɷ��ӣ����粥��һ�׸����ȵ�
			}
		});
	}

	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			songsList = new ArrayList<HashMap<String, String>>();

			XMLParser parser = new XMLParser();
			String xml = parser.getXmlFromUrl(URL); // �������ȡxml
			Document doc = parser.getDomElement(xml); // ��ȡ DOM �ڵ�

			NodeList nl = doc.getElementsByTagName(KEY_SONG);
			// ѭ���������еĸ�ڵ� <song>
			for (int i = 0; i < nl.getLength(); i++) {
				// �½�һ�� HashMap
				HashMap<String, String> map = new HashMap<String, String>();
				Element e = (Element) nl.item(i);
				// ÿ���ӽڵ���ӵ�HashMap�ؼ�= >ֵ
				map.put(KEY_ID, parser.getValue(e, KEY_ID));
				map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
				map.put(KEY_ARTIST, parser.getValue(e, KEY_ARTIST));
				map.put(KEY_DURATION, parser.getValue(e, KEY_DURATION));
				map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));

				// HashList��ӵ������б�
				songsList.add(map);
			}
			Message message = new Message(); 
            message.what = 1; 
            mHandler.sendMessage(message);
		}
	};

	// ����Handler����
	private Handler mHandler = new Handler() {
		@Override
		// ������Ϣ���ͳ�����ʱ���ִ��Handler���������
		public void handleMessage(Message msg) {
			switch (msg.what) { 
            case 1: 
            	mainProcess();
                break; 
            } 
		}
	};

}