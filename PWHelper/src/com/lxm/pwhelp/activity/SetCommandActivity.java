package com.lxm.pwhelp.activity;

import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.lxm.pwhelp.R;
import com.lxm.pwhelp.bean.PWSetting;
import com.lxm.pwhelp.custom.EmailDialog;
import com.lxm.pwhelp.dao.PWSettingDao;
import com.lxm.pwhelp.utils.Tools;

/**
 * enable and disable password command
 * @author listener
 * @version 2015-5-31 19:16:29
 */
public class SetCommandActivity extends Activity implements View.OnClickListener {
	
	private PWSettingDao pwSettingDao;
	private TextView set_command_note;
	private Button enable_command_btn,disable_command_btn;
	private List<PWSetting> setting;
	private boolean isOn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.set_command_layout);
		findViewById(R.id.SetCommand_Return).setOnClickListener(this);
		findViewById(R.id.enable_command_btn).setOnClickListener(this);
		findViewById(R.id.disable_command_btn).setOnClickListener(this);
		initView();
	}
	
	private void initView(){
		pwSettingDao = new PWSettingDao(this);
		set_command_note = (TextView) findViewById(R.id.set_command_note);
		enable_command_btn = (Button) findViewById(R.id.enable_command_btn);
		disable_command_btn = (Button) findViewById(R.id.disable_command_btn);
		setting = pwSettingDao.getSettingByName("pw_command");
		if(setting!=null&&setting.size()>0){
			isOn=true;
			//set_command_note.setText("您当前的密码口令是:"+setting.get(0).getSetting_value());
			set_command_note.setGravity(Gravity.CENTER);
			enable_command_btn.setText("修改口令");
			disable_command_btn.setText("关闭口令");
			disable_command_btn.setVisibility(View.VISIBLE);
		}else{
			enable_command_btn.setText("开通口令");
		}
		set_command_note.setText("密码口令是你每次查看密码时需要输入的一个口令，开通密码口令将保证你的密码助手在访客模式下也同样能保证你的密码安全！");
	}

	@Override
	public void onClick(View item) {
		switch(item.getId()){
		case R.id.SetCommand_Return:
			Intent intent = new Intent(SetCommandActivity.this,SettingsActivity.class);
			setResult(RESULT_OK, intent);
			finish();
			break;
		case R.id.enable_command_btn:
			if(isOn){
				final EmailDialog dialog = new EmailDialog(SetCommandActivity.this,"修改你的密码查看口令");
				final EditText editText = (EditText) dialog.getEditText();
				final EditText editText1 = (EditText) dialog.getEditText1();
				editText1.setVisibility(View.VISIBLE);
				editText.setHint("请输入旧口令");
				editText1.setHint("请输入新口令");
				editText.setInputType(InputType.TYPE_CLASS_NUMBER);
				editText1.setInputType(InputType.TYPE_CLASS_NUMBER);
				InputFilter[] filters = {new LengthFilter(4)};
				editText.setFilters(filters);
				editText1.setFilters(filters);
				dialog.setOnPositiveListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						String oldCommand = editText.getText().toString();
						String newCommand = editText1.getText().toString();
						if(oldCommand==null||oldCommand.trim().length()!=4){
							Tools.showWarningDialog(SetCommandActivity.this,"警告","请输入四位有效数字的口令！");
							editText.requestFocus();
						} else if (newCommand==null||newCommand.trim().length()!=4) {
							Tools.showWarningDialog(SetCommandActivity.this,"警告","请输入四位有效数字的口令！");
							editText1.requestFocus();
						} else if (!setting.get(0).getSetting_value().equals(oldCommand)) {
							Tools.showWarningDialog(SetCommandActivity.this,"警告","旧口令输入有误，请重试！");
							editText.requestFocus();
						} else {
							PWSetting pwSetting = new PWSetting();
							pwSetting.setSetting_id(setting.get(0).getSetting_id());
							pwSetting.setSetting_name("pw_command");
							pwSetting.setSetting_value(newCommand);
							CreateOrUpdateStatus status = pwSettingDao.createOrUpdate(pwSetting);
							if(status.isUpdated()){
								Tools.showSucessDialog(SetCommandActivity.this, "修改成功", "密码查看口令修改成功,返回！", listener);
								dialog.dismiss();
							}
						}
					}
				});
				dialog.setOnNegativeListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				dialog.show();
			} else {
				final EmailDialog dialog = new EmailDialog(SetCommandActivity.this,"设置您的密码查看口令");
				final EditText editText = (EditText) dialog.getEditText();
				editText.setHint("输入密码查看口令");
				editText.setInputType(InputType.TYPE_CLASS_NUMBER);
				InputFilter[] filters = {new LengthFilter(4)};
				editText.setFilters(filters);
				dialog.setOnPositiveListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						String command = editText.getText().toString();
						if(command==null||command.trim().length()!=4){
							Tools.showWarningDialog(SetCommandActivity.this,"警告","请输入四位有效数字的口令！");
						}else{
							PWSetting pwSetting = new PWSetting();
							pwSetting.setSetting_name("pw_command");
							pwSetting.setSetting_value(command);
							CreateOrUpdateStatus status = pwSettingDao.createOrUpdate(pwSetting);
							if(status.isCreated()){
								Tools.showSucessDialog(SetCommandActivity.this, "开启成功", "密码查看口令开启成功,返回！", listener);
								dialog.dismiss();
							}
						}
					}
				});
				dialog.setOnNegativeListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				dialog.show();
			}
			break;
		case R.id.disable_command_btn:
			if(isOn){
				final EmailDialog dialog = new EmailDialog(SetCommandActivity.this,"验证口令");
				final EditText editText = (EditText) dialog.getEditText();
				editText.setHint("请输入口令");
				editText.setInputType(InputType.TYPE_CLASS_NUMBER);
				InputFilter[] filters = {new LengthFilter(4)};
				editText.setFilters(filters);
				dialog.setOnPositiveListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String command = editText.getText().toString();
						if(command==null||command.trim().length()!=4){
							Tools.showWarningDialog(SetCommandActivity.this,"警告","请输入四位有效数字的口令！");
						}else if (!setting.get(0).getSetting_value().equals(command)) {
							Tools.showWarningDialog(SetCommandActivity.this,"警告","口令输入有误，请重试！");
							editText.requestFocus();
						} else {
							PWSetting pwSetting = setting.get(0);
							int status = pwSettingDao.delete(pwSetting);
							if(status>0){
								Tools.showSucessDialog(SetCommandActivity.this, "关闭成功", "密码查看口令关闭成功,返回！", listener);
								dialog.dismiss();
							}
						}
					}
				});
				dialog.setOnNegativeListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				dialog.show();
			}
			break;
		}
	}
	
	private android.content.DialogInterface.OnClickListener listener = new android.content.DialogInterface.OnClickListener() {
		@Override
		public void onClick(
				DialogInterface dialog,
				int which) {
			Intent intent = new Intent(SetCommandActivity.this, SettingsActivity.class);
			setResult(RESULT_OK, intent);
			finish();
			dialog.dismiss();
		}
	};
	
}