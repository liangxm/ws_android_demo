/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package com.lxm.pwhelp.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.ListAdapter;

import com.lxm.pwhelp.R;

public class LazyAdapter implements ListAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;

	public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		final HashMap<String, String> song = data.get(position);
		if (convertView == null){
			convertView = inflater.inflate(R.layout.list_row, null);
		}
		TextView title = (TextView) convertView.findViewById(R.id.item_type); // 账户类型
		TextView artist = (TextView) convertView.findViewById(R.id.item_username); // username
		final TextView duration = (TextView) convertView.findViewById(R.id.item_password); // password
		ToggleButton mTogBtn = (ToggleButton) convertView.findViewById(R.id.mTogBtn);
		title.setText(song.get("item_type"));
		artist.setText("账号：" + song.get("item_username"));
		if(!mTogBtn.isChecked())
			duration.setText("密码："+"*********************");
		mTogBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked){
					duration.setText("密码：" + song.get("item_password"));
				}else{
					//未选中
					duration.setText("密码："+"*********************");
				}
			}
		});
		/*convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(activity, DetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("item_type", song.get("item_type").toString());
				bundle.putString("item_username", song.get("item_username").toString());
				bundle.putString("item_password", song.get("item_password").toString());
				intent.putExtras(bundle);
				activity.startActivityForResult(intent, 1);
			}
		});*/
        return convertView;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}
}
