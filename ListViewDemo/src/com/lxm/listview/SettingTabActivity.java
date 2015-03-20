package com.lxm.listview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

import com.lxm.listview.view.CornerListView;

public class SettingTabActivity extends Activity {

	private CornerListView cornerListView = null;

	private List<Map<String, String>> listData = null;
	private SimpleAdapter adapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_tab_setting);

		cornerListView = (CornerListView) findViewById(R.id.setting_list);
		setListData();

		adapter = new SimpleAdapter(getApplicationContext(), listData,
				R.layout.main_tab_setting_list_item, new String[] { "text" },
				new int[] { R.id.setting_list_item_text });
		cornerListView.setAdapter(adapter);
	}

	/**
	 * 设置列表数据
	 */
	private void setListData() {
		listData = new ArrayList<Map<String, String>>();

		Map<String, String> map = new HashMap<String, String>();
		map.put("text", "图库更新");
		listData.add(map);

		map = new HashMap<String, String>();
		map.put("text", "收藏图片");
		listData.add(map);

		map = new HashMap<String, String>();
		map.put("text", "下载目录");
		listData.add(map);
	}
}