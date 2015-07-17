package com.lxm.pwhelp.adapter;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxm.pwhelp.R;
import com.lxm.pwhelp.bean.Item;
import com.lxm.pwhelp.bean.Setting;
import com.lxm.pwhelp.custom.ToggleButton;
import com.lxm.pwhelp.custom.ToggleButton.OnToggleChanged;
import com.lxm.pwhelp.dao.PWSettingDao;
import com.lxm.pwhelp.utils.GroupType;
import com.lxm.pwhelp.utils.Tools;
/**
 * pwItem list adapter
 * @author listener
 * @version 2015-5-31 11:55:47
 */
public class PWItemAdapter extends BaseAdapter {

	private Activity activity;
	private List<Item> data;
	private String command;
	private PWSettingDao pwSettingDao;
	
	private String default_str,bank_str,web_str,weibo_str,qq_str,email_str,alipay_str, note_str;

	public PWItemAdapter(Activity activity, List<Item> data,PWSettingDao pwSettingDao) {
		this.activity = activity;
		this.data = data;
		this.pwSettingDao=pwSettingDao;
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
		note_str = GroupType.Type_Note.getType();
	}

	public int getCount() {
		return data.size();
	}

	public Item getItem(int position) {
		return data.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder mViewHolder;
		if (null == convertView){
			convertView = View.inflate(activity.getApplicationContext(),R.layout.list_row, null);
			mViewHolder = new ViewHolder();
			mViewHolder.item_logo = (ImageView) convertView.findViewById(R.id.item_logo);
			mViewHolder.item_type = (TextView) convertView.findViewById(R.id.item_type);
			mViewHolder.item_username = (TextView) convertView.findViewById(R.id.item_username);
			mViewHolder.item_password = (TextView) convertView.findViewById(R.id.item_password);
			mViewHolder.mTogBtn = (ToggleButton) convertView.findViewById(R.id.mTogBtn);
			convertView.setTag(mViewHolder);
		}else{
			mViewHolder = (ViewHolder) convertView.getTag();
		}
		
		String password = getItem(position).getItem_password();
		String item_type = getItem(position).getItem_type();
		String appendType = "";
		boolean isnote=false;
		if(default_str.equals(item_type))
			mViewHolder.item_logo.setImageResource(R.drawable.default_item_icon);
		else if(bank_str.equals(item_type)){
			mViewHolder.item_logo.setImageResource(R.drawable.bank_item_icon);
			if(getItem(position).getItem_subtype()==0){
				appendType="(储蓄卡)";
			}else if(getItem(position).getItem_subtype()==1){
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
		else if(note_str.equals(item_type)){
			mViewHolder.item_logo.setImageResource(R.drawable.note_item_icon);
			isnote=true;
		}else
			mViewHolder.item_logo.setImageResource(R.drawable.default_item_icon);
			
		mViewHolder.item_type.setText(getItem(position).getItem_type()+appendType);
		mViewHolder.item_username.setText(isnote?"标题："+getItem(position).getItem_username():"账号：" + getItem(position).getItem_username());
		
		if(!mViewHolder.mTogBtn.isSelected()){
			mViewHolder.item_password.setText(isnote?"":"密码："+"*********************");
		}
		ToggleButton button = mViewHolder.mTogBtn;
		button.setOnToggleChanged(new ToggleChangeListener(isnote,button,mViewHolder.item_password,password));
        return convertView;
	}
	
	private class ToggleChangeListener implements OnToggleChanged {
		
		private ToggleButton button;
		private TextView item_password;
		private String password;
		private boolean isNote;
		
		public ToggleChangeListener(boolean isNote,ToggleButton button,TextView item_password,String password){
			this.isNote = isNote;
			this.button = button;
			this.item_password = item_password;
			this.password = password;
		}
		@Override
		public void onToggle(boolean on) {
			if(on){
				Setting commands = pwSettingDao.querySettingByName("pw_command");
				if(commands!=null){
					command = commands.getSetting_value();
					showCustomDialog(button,item_password,password,isNote);
				}else{
					item_password.setText(isNote?"":"密码：" + password);
				}
			}else{
				item_password.setText(isNote?"":"密码："+"*********************");
			}
		}
	}
	
	protected void showCustomDialog(final ToggleButton mTogBtn,final TextView item_password, final String password, final boolean isNote) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle("请输入口令");
        dialog.setIcon(android.R.drawable.ic_dialog_info);
        final EditText editText = new EditText(activity);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
		InputFilter[] filters = {new LengthFilter(4)};
		editText.setFilters(filters);
		editText.setHint("默认口令是8888");
		dialog.setView(editText);
        dialog.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
        	@Override
			public void onClick(DialogInterface dialog, int which) {
            	if(command.equals(editText.getText().toString())){
					item_password.setText(isNote?"":"密码：" + password);
					dialog.dismiss();
				}else{
					Tools.showWarningDialog(activity,"口令错误","口令错误，请重试！");
					mTogBtn.setToggleOff();
					dialog.dismiss();
				}
            }
        });
        dialog.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mTogBtn.setToggleOff();
				dialog.dismiss();
			}
		});
        dialog.show();
    }
	
	private class ViewHolder{
		ImageView item_logo;
		TextView item_type;
		TextView item_username;
		TextView item_password;
		ToggleButton mTogBtn;
	}
}
