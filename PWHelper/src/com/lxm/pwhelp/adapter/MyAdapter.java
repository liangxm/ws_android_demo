package com.lxm.pwhelp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.lxm.pwhelp.R;
import com.lxm.pwhelp.activity.MainActivity;
import com.lxm.pwhelp.bean.SimpleData;
import com.nineoldandroids.util.Conver;

public class MyAdapter extends BaseExpandableListAdapter {

	private MainActivity activity;
	
	public MyAdapter(MainActivity activity){
		this.activity = activity;
	}

	// get item related data
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		String key = activity.parent.get(groupPosition);
		return (activity.map.get(key).get(childPosition));
	}

	// get item id
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		String key = activity.parent.get(groupPosition);
		SimpleData info = activity.map.get(key).get(childPosition);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.layout_children, null);
		}
		TextView tv1 = (TextView) convertView.findViewById(R.id.one_textview);
		TextView tv2 = (TextView) convertView.findViewById(R.id.two_textview);
		TextView tv3 = (TextView) convertView.findViewById(R.id.three_textview);
		tv1.setText(info.getItem_type());
		tv2.setText(info.getItem_username());
		tv3.setText(info.getItem_password());
		tv1.setBackgroundColor(Color.WHITE);
		
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		String key = activity.parent.get(groupPosition);
		int size = activity.map.get(key).size();
		return size;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return activity.parent.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return activity.parent.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.layout_parent, null);
		}
		TextView tv = (TextView) convertView.findViewById(R.id.parent_textview);
		tv.setText(activity.parent.get(groupPosition));
		tv.setBackgroundColor(Color.WHITE);
		tv = getTextView(tv);
		return tv;
	}

	private TextView getTextView(TextView textView){
		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,Conver.dip2px(activity, 40));
		textView.setLayoutParams(lp);
		textView.setPadding(Conver.dip2px(activity, 40), 0, 0, 0);
		textView.setTextSize(16);
		return textView;
	}
	
	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
