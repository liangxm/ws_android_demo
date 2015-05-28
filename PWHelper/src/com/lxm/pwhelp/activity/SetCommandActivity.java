package com.lxm.pwhelp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;

import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.lxm.pwhelp.R;
import com.lxm.pwhelp.bean.PWSetting;
import com.lxm.pwhelp.custom.EmailDialog;
import com.lxm.pwhelp.dao.PWSettingDao;

public class SetCommandActivity extends Activity implements View.OnClickListener {
	
	private PWSettingDao pwSettingDao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.set_command_layout);
		pwSettingDao = new PWSettingDao(this);
		findViewById(R.id.SetCommand_Return).setOnClickListener(this);
		findViewById(R.id.set_command_btn).setOnClickListener(this);
	}
	

	@Override
	public void onClick(View item) {
		switch(item.getId()){
		case R.id.SetCommand_Return:
			Intent intent = new Intent(SetCommandActivity.this,SettingsActivity.class);
			setResult(1,intent);
			finish();
			break;
		case R.id.set_command_btn:
			final EmailDialog dialog = new EmailDialog(SetCommandActivity.this,"请输入四位数字口令");
			final EditText editText = (EditText) dialog.getEditText();
			editText.setHint("请设置您的密码查看口令");
			editText.setInputType(InputType.TYPE_CLASS_NUMBER);
			editText.setEms(4);
			dialog.setOnPositiveListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// dosomething youself
					dialog.cancel();
				}
			});
			dialog.setOnNegativeListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					PWSetting pwSetting = new PWSetting();
					pwSetting.setSetting_name("pw_command");
					pwSetting.setSetting_value(dialog.getEditText().toString());
					CreateOrUpdateStatus status = pwSettingDao.createOrUpdate(pwSetting);
					if(status.isCreated()){
						showDialog();
					}
				}
			});
			dialog.show();
			break;
		}
	}
	
	public void showDialog(){
		new AlertDialog.Builder(this)
		.setTitle("开启成功")
		.setMessage("密码查看口令开启成功,返回！")
		.setPositiveButton(
				"确定",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(
							DialogInterface dialog,
							int which) {
						Intent intent = new Intent(SetCommandActivity.this, SettingsActivity.class);
						setResult(1, intent);
						finish();
					}
		}).show();
	}

}