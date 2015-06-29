package com.lxm.pwhelp.activity;

import net.sqlcipher.database.SQLiteDatabase;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxm.pwhelp.R;
import com.lxm.pwhelp.bean.Item;
import com.lxm.pwhelp.bean.Setting;
import com.lxm.pwhelp.dao.PWItemDao;
import com.lxm.pwhelp.dao.PWSettingDao;
import com.lxm.pwhelp.utils.Tools;

public class PasswordBackActivity extends Activity implements View.OnClickListener {
	
	private TextView title;
	private RelativeLayout command_back,acount_back;
	private PWSettingDao pwSettingDao;
	private PWItemDao pwItemDao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.password_back_layout);
		SQLiteDatabase.loadLibs(this);
		init();
	}
	
	private void init(){
		title=(TextView)findViewById(R.id.title);
		command_back=(RelativeLayout)findViewById(R.id.command_back);
		acount_back=(RelativeLayout)findViewById(R.id.acount_back);
		title.setText(this.getResources().getString(R.string.password_back_title));
		findViewById(R.id.Return).setOnClickListener(this);
		command_back.setOnClickListener(this);
		acount_back.setOnClickListener(this);
		pwSettingDao=new PWSettingDao(this);
		pwItemDao=new PWItemDao(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.Return:
			Intent intent = new Intent(this, UnlockGesturePasswordActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.command_back:
			Setting setting = pwSettingDao.querySettingByName("pw_command");
			final EditText commandStr = new EditText(this);
			final String command = setting.getSetting_value();
			final Intent intent1 = new Intent(this,GuideGesturePasswordActivity.class);
			commandStr.setInputType(InputType.TYPE_CLASS_NUMBER);
			InputFilter[] filters = {new LengthFilter(4)};
			commandStr.setFilters(filters);
			commandStr.setHint("默认口令是8888");
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("请输入口令");
			builder.setIcon(android.R.drawable.ic_dialog_info);
			builder.setView(commandStr);
			builder.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(command.equals(commandStr.getText().toString())){
						dialog.dismiss();
						startActivity(intent1);
						PasswordBackActivity.this.finish();
					}else{
						Tools.showWarningDialog(PasswordBackActivity.this,"口令错误","口令错误，请重试！");
						dialog.dismiss();
					}
				}
			});
			builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			builder.setCancelable(false);
			builder.show();
			break;
		case R.id.acount_back:
			Item item = pwItemDao.getMaxIdItem();
			final Intent intent2 = new Intent(this,GuideGesturePasswordActivity.class);
			if(item==null){
				startActivity(intent2);
				break;
			}
			final EditText acountStr = new EditText(this);
			final String passwrd = item.getItem_password();
			AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
			builder1.setTitle("请输入账户名为："+item.getItem_username()+" 的密码！");
			builder1.setIcon(android.R.drawable.ic_dialog_info);
			builder1.setView(acountStr);
			builder1.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(passwrd.equals(acountStr.getText().toString())){
						dialog.dismiss();
						startActivity(intent2);
						PasswordBackActivity.this.finish();
					}else{
						Tools.showWarningDialog(PasswordBackActivity.this,"密码错误","密码错误，请重试！");
						dialog.dismiss();
					}
				}
			});
			builder1.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			builder1.setCancelable(false);
			builder1.show();
			break;
		}
	}
	
}
