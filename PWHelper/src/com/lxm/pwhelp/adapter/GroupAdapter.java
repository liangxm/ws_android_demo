package com.lxm.pwhelp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxm.pwhelp.R;
import com.lxm.pwhelp.activity.MainActivity;
import com.lxm.pwhelp.bean.PWItem;
import com.lxm.pwhelp.custom.ToggleButton;
import com.lxm.pwhelp.utils.Conver;
import com.lxm.pwhelp.utils.GroupType;

public class GroupAdapter extends BaseExpandableListAdapter {

	private MainActivity activity;
	
	private String default_str,bank_str,web_str,weibo_str,qq_str,email_str,alipay_str;
	
	public GroupAdapter(MainActivity activity){
		this.activity = activity;
		initGroupStr();
	}
	
	private void initGroupStr(){
		default_str = GroupType.Type_Default.getType();
		bank_str = GroupType.Type_Bank.getType();
		web_str = GroupType.Type_Web.getType();
		weibo_str = GroupType.Type_WeiBo.getType();
		qq_str = GroupType.Type_QQ.getType();
		email_str = GroupType.Type_Email.getType();
		alipay_str = GroupType.Type_Alipay.getType();
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
		PWItem info = (PWItem) getChild(groupPosition,childPosition);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.layout_children, null);
			new ViewHolderChild(convertView);
		}
		final ViewHolderChild holder = (ViewHolderChild) convertView.getTag();
		String item_type = info.getItem_type();
		if(default_str.equals(item_type))
			holder.item_logo.setImageResource(R.drawable.default_item_icon);
		else if(bank_str.equals(item_type))
			holder.item_logo.setImageResource(R.drawable.bank_item_icon);
		else if(web_str.equals(item_type))
			holder.item_logo.setImageResource(R.drawable.www_item_icon);
		else if(weibo_str.equals(item_type))
			holder.item_logo.setImageResource(R.drawable.sina_item_icon);
		else if(qq_str.equals(item_type))
			holder.item_logo.setImageResource(R.drawable.qq_item_icon);
		else if(email_str.equals(item_type))
			holder.item_logo.setImageResource(R.drawable.email_item_icon);
		else if(alipay_str.equals(item_type))
			holder.item_logo.setImageResource(R.drawable.alipay_item_icon);
		else
			holder.item_logo.setImageResource(R.drawable.default_item_icon);
		holder.item_type.setText(item_type);
		holder.item_username.setText(info.getItem_username());
		holder.item_password.setText(info.getItem_password());
		holder.item_type.setBackgroundColor(Color.WHITE);
		return convertView;
	}
	
	class ViewHolderChild{
		ImageView item_logo;
		TextView item_type;
		TextView item_username;
		TextView item_password;
		ToggleButton mTogBtn;
		
		public ViewHolderChild(View view){
			item_logo = (ImageView) view.findViewById(R.id.item_logo);
			item_type = (TextView) view.findViewById(R.id.one_textview);
			item_username = (TextView) view.findViewById(R.id.two_textview);
			item_password = (TextView) view.findViewById(R.id.three_textview);
			view.setTag(this);
		}
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
