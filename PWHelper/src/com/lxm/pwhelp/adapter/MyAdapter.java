package com.lxm.pwhelp.adapter;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.lxm.pwhelp.R;

public class MyAdapter extends BaseExpandableListAdapter {

	private Activity activity;
	private Map<String, List<String>> map;
	private List<String> parent;
	
	public MyAdapter(Activity activity, List<String> parent, Map<String, List<String>> map){
		this.activity = activity;
		this.parent = parent;
		this.map = map;
	}

	// get item related data
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		String key = parent.get(groupPosition);
		return (map.get(key).get(childPosition));
	}

	// get item id
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		String key = this.parent.get(groupPosition);
		String info = map.get(key).get(childPosition);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.layout_children, null);
		}
		TextView tv = (TextView) convertView.findViewById(R.id.second_textview);
		tv.setText(info);
		tv.setBackgroundColor(Color.WHITE);
		return tv;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		String key = parent.get(groupPosition);
		int size = map.get(key).size();
		return size;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return parent.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return parent.size();
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
		tv.setText(this.parent.get(groupPosition));
		tv.setBackgroundColor(Color.WHITE);
		tv = getTextView(tv);
		return tv;
	}

	private TextView getTextView(TextView textView){
		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,120);
		textView.setLayoutParams(lp);
		textView.setPadding(96, 0, 0, 0);
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
