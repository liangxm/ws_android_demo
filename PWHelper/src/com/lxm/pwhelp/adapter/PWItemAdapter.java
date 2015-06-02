/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package com.lxm.pwhelp.adapter;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.lxm.pwhelp.R;
import com.lxm.pwhelp.bean.PWItem;
import com.lxm.pwhelp.bean.PWSetting;
import com.lxm.pwhelp.custom.ToggleButton;
import com.lxm.pwhelp.custom.ToggleButton.OnToggleChanged;
import com.lxm.pwhelp.dao.PWSettingDao;
import com.lxm.pwhelp.utils.Tools;
/**
 * pwItem list adapter
 * @author listener
 * @version 2015-5-31 11:55:47
 */
public class PWItemAdapter extends BaseAdapter {

	private Activity activity;
	private List<PWItem> data;
	private String command;
	private PWSettingDao pwSettingDao;

	public PWItemAdapter(Activity activity, List<PWItem> data) {
		this.activity = activity;
		this.data = data;
		pwSettingDao=new PWSettingDao(activity);
	}

	public int getCount() {
		return data.size();
	}

	public PWItem getItem(int position) {
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
		final String password = getItem(position).getItem_password();
		holder.item_type.setText(getItem(position).getItem_type());
		holder.item_username.setText("账号：" + getItem(position).getItem_username());
		
		if(!holder.mTogBtn.isSelected()){
			holder.item_password.setText("密码："+"*********************");
		}
		holder.mTogBtn.setOnToggleChanged(new OnToggleChanged(){
			@Override
			public void onToggle(boolean on) {
				if(on){
					List<PWSetting> commands = pwSettingDao.getSettingByName("pw_command");
					if(commands!=null&&commands.size()>0){
						command = commands.get(0).getSetting_value();
						final EditText commandStr = new EditText(activity);
						AlertDialog.Builder builder = new AlertDialog.Builder(activity);
						builder.setTitle("请输入口令");
						builder.setIcon(android.R.drawable.ic_dialog_info);
						builder.setView(commandStr);
						builder.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								if(command.equals(commandStr.getText().toString())){
									holder.item_password.setText("密码：" + password);
									dialog.dismiss();
								}else{
									Tools.showWarningDialog(activity,"口令错误","口令错误，请重试！");
									holder.mTogBtn.setToggleOff();
									dialog.dismiss();
								}
							}
						});
						builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										holder.mTogBtn.setToggleOff();
										dialog.dismiss();
									}
						});
						builder.setCancelable(false);
						builder.show();
					}else{
						holder.item_password.setText("密码：" + password);
					}
				}else{
					holder.item_password.setText("密码："+"*********************");
				}
			}
		});
		/*holder.mTogBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(final CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked){
					List<PWSetting> commands = pwSettingDao.getSettingByName("pw_command");
					if(commands!=null&&commands.size()>0){
						command = commands.get(0).getSetting_value();
						final EditText commandStr = new EditText(activity);
						AlertDialog.Builder builder = new AlertDialog.Builder(activity);
						builder.setTitle("请输入口令");
						builder.setIcon(android.R.drawable.ic_dialog_info);
						builder.setView(commandStr);
						builder.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								if(command.equals(commandStr.getText().toString())){
									holder.item_password.setText("密码：" + password);
									dialog.dismiss();
								}else{
									Tools.showWarningDialog(activity,"口令错误","口令错误，请重试！");
									buttonView.setChecked(false);
									dialog.dismiss();
									return;
								}
							}
						});
						builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										buttonView.setChecked(false);
										dialog.dismiss();
									}
						});
						builder.setCancelable(false);
						builder.show();
					}else{
						holder.item_password.setText("密码：" + password);
					}
				}else{
					holder.item_password.setText("密码："+"*********************");
				}
			}
		});*/
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