package com.lxm.pwhelp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.lxm.pwhelp.R;
import com.lxm.pwhelp.bean.PWSetting;
import com.lxm.pwhelp.custom.EmailDialog;
import com.lxm.pwhelp.dao.PWSettingDao;

public class SetEmailActivity extends Activity implements View.OnClickListener {
	
	private PWSettingDao pwSettingDao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.set_email_layout);
		pwSettingDao = new PWSettingDao(this);
		findViewById(R.id.SetEmail_Return).setOnClickListener(this);
		findViewById(R.id.set_email_btn).setOnClickListener(this);
	}
	

	@Override
	public void onClick(View item) {
		switch(item.getId()){
		case R.id.SetEmail_Return:
			Intent intent = new Intent(SetEmailActivity.this,SettingsActivity.class);
			setResult(1,intent);
			finish();
			break;
		case R.id.set_email_btn:
			final EmailDialog dialog = new EmailDialog(SetEmailActivity.this,"请输入邮箱地址");
			// final EditText editText = (EditText) dialog.getEditText();
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
					pwSetting.setSetting_name("email_address");
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
		.setTitle("绑定成功")
		.setMessage("邮箱地址绑定成功,返回！")
		.setPositiveButton(
				"确定",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(
							DialogInterface dialog,
							int which) {
						Intent intent = new Intent(SetEmailActivity.this, SettingsActivity.class);
						setResult(1, intent);
						finish();
					}
		}).show();
	}

}