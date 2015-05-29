/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package com.lxm.pwhelp.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.lxm.pwhelp.R;
import com.nineoldandroids.util.Conver;

public class LazyAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;

	public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
		activity = a;
		data = d;
	}

	public int getCount() {
		return data.size();
	}

	public HashMap<String, String> getItem(int position) {
		return data.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null){
			convertView = View.inflate(activity.getApplicationContext(),R.layout.list_row, null);
			new ViewHolder(convertView);
		}
		
		final ViewHolder holder = (ViewHolder) convertView.getTag();
		final String password = getItem(position).get("item_password");
		holder.item_type.setText(getItem(position).get("item_type"));
		holder.item_username.setText("账号：" + getItem(position).get("item_username"));
		holder.item_password.setText("密码："+"*********************");
		
		if(!holder.mTogBtn.isChecked())
			holder.item_password.setText("密码："+"*********************");
		holder.mTogBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked){
					holder.item_password.setText("密码：" + password);
				}else{
					holder.item_password.setText("密码："+"*********************");
				}
			}
		});
        return convertView;
	}
	class ViewHolder{
		TextView item_type;
		TextView item_username;
		TextView item_password;
		ToggleButton mTogBtn;
		
		public ViewHolder(View view){
			item_type = (TextView) view.findViewById(R.id.item_type);
			item_username = (TextView) view.findViewById(R.id.item_username);
			item_password = (TextView) view.findViewById(R.id.item_password);
			mTogBtn = (ToggleButton) view.findViewById(R.id.mTogBtn);
			view.setTag(this);
		}
	}
}
