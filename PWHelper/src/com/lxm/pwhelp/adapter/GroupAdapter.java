package com.lxm.pwhelp.adapter;

import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxm.pwhelp.R;
import com.lxm.pwhelp.activity.MainActivity;
import com.lxm.pwhelp.bean.Item;
import com.lxm.pwhelp.bean.Setting;
import com.lxm.pwhelp.custom.ToggleButton;
import com.lxm.pwhelp.custom.ToggleButton.OnToggleChanged;
import com.lxm.pwhelp.dao.PWSettingDao;
import com.lxm.pwhelp.utils.Conver;
import com.lxm.pwhelp.utils.GroupType;
import com.lxm.pwhelp.utils.Tools;

public class GroupAdapter extends BaseExpandableListAdapter {

	private MainActivity activity;
	private List<String> parent;
	private Map<String, List<Item>> map;
	private String command;
	private PWSettingDao pwSettingDao;
	
	private String default_str,bank_str,web_str,weibo_str,qq_str,email_str,alipay_str;
	
	public GroupAdapter(MainActivity activity, List<String> parent, Map<String, List<Item>> map,PWSettingDao pwSettingDao){
		this.activity = activity;
		this.parent = parent;
		this.map = map;
		this.pwSettingDao = pwSettingDao;
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
		Item item = (Item) getChild(groupPosition, childPosition);
		final ViewHolderChild mViewHolder;
		if (null == convertView){
			LayoutInflater inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.layout_children, null);
			mViewHolder = new ViewHolderChild();
			mViewHolder.item_logo = (ImageView) convertView.findViewById(R.id.item_logo);
			mViewHolder.item_type = (TextView) convertView.findViewById(R.id.item_type);
			mViewHolder.item_username = (TextView) convertView.findViewById(R.id.item_username);
			mViewHolder.item_password = (TextView) convertView.findViewById(R.id.item_password);
			mViewHolder.mTogBtn = (ToggleButton) convertView.findViewById(R.id.mTogBtn);
			convertView.setTag(mViewHolder);
		}else{
			mViewHolder = (ViewHolderChild) convertView.getTag();
		}
		
		final String password = item.getItem_password();
		String item_type = item.getItem_type();
		String appendType = "";
		if(default_str.equals(item_type))
			mViewHolder.item_logo.setImageResource(R.drawable.default_item_icon);
		else if(bank_str.equals(item_type)){
			mViewHolder.item_logo.setImageResource(R.drawable.bank_item_icon);
			if(item.getItem_subtype()==0){
				appendType="(储蓄卡)";
			}else if(item.getItem_subtype()==1){
				appendType="(信用卡)";
			}
		}else if(web_str.equals(item_type))
			mViewHolder.item_logo.setImageResource(R.drawable.www_item_icon);
		else if(weibo_str.equals(item_type))
			mViewHolder.item_logo.setImageResource(R.drawable.sina_item_icon);
		else if(qq_str.equals(item_type))
			mViewHolder.item_logo.setImageResource(R.drawable.qq_item_icon);
		else if(email_str.equals(item_type))
			mViewHolder.item_logo.setImageResource(R.drawable.email_item_icon);
		else if(alipay_str.equals(item_type))
			mViewHolder.item_logo.setImageResource(R.drawable.alipay_item_icon);
		else
			mViewHolder.item_logo.setImageResource(R.drawable.default_item_icon);
			
		mViewHolder.item_type.setText(item.getItem_type()+appendType);
		mViewHolder.item_username.setText("账号：" + item.getItem_username());
		
		if(!mViewHolder.mTogBtn.isSelected()){
			mViewHolder.item_password.setText("密码："+"*********************");
		}
		mViewHolder.mTogBtn.setOnToggleChanged(new OnToggleChanged(){
			@Override
			public void onToggle(boolean on) {
				if(on){
					//List<PWSetting> commands = pwSettingDao.getSettingByName("pw_command");
					Setting commands = pwSettingDao.querySettingByName("pw_command");
					if(commands!=null){
						command = commands.getSetting_value();
						final EditText commandStr = new EditText(activity);
						commandStr.setInputType(InputType.TYPE_CLASS_NUMBER);
						InputFilter[] filters = {new LengthFilter(4)};
						commandStr.setFilters(filters);
						commandStr.setHint("默认口令是8888");
						AlertDialog.Builder builder = new AlertDialog.Builder(activity);
						builder.setTitle("请输入口令");
						builder.setIcon(android.R.drawable.ic_dialog_info);
						builder.setView(commandStr);
						builder.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								if(command.equals(commandStr.getText().toString())){
									mViewHolder.item_password.setText("密码：" + password);
									dialog.dismiss();
								}else{
									Tools.showWarningDialog(activity,"口令错误","口令错误，请重试！");
									mViewHolder.mTogBtn.setToggleOff();
									dialog.dismiss();
								}
							}
						});
						builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										mViewHolder.mTogBtn.setToggleOff();
										dialog.dismiss();
									}
						});
						builder.setCancelable(false);
						builder.show();
					}else{
						mViewHolder.item_password.setText("密码：" + password);
					}
				}else{
					mViewHolder.item_password.setText("密码："+"*********************");
				}
			}
		});
        return convertView;
	}
	
	class ViewHolderChild{
		ImageView item_logo;
		TextView item_type;
		TextView item_username;
		TextView item_password;
		ToggleButton mTogBtn;
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
			View convertView, ViewGroup viewGroup) {
		if (null == convertView) {
			LayoutInflater inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.layout_parent, null);
		}
		TextView tv = (TextView) convertView.findViewById(R.id.parent_textview);
		tv.setText(parent.get(groupPosition));
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
