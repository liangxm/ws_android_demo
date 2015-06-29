package com.lxm.pwhelp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxm.pwhelp.R;
import com.lxm.pwhelp.bean.Setting;
import com.lxm.pwhelp.dao.PWSettingDao;

public class SettingsActivity extends Activity implements View.OnClickListener {
	
	private TextView emailState;
	private TextView commandState;
	private TextView title;
	
	private RelativeLayout setEmail;
	private RelativeLayout setCommand;
	private RelativeLayout resetPattern;
	private RelativeLayout about_us;
	
	private PWSettingDao pwSettingDao;
	
	private static final int SET_EMAIL_CODE = 1;
	private static final int SET_COMMAND_CODE = 2;
	private static final int SET_LOCK_CODE = 3;
	private static final int ABOUT_US_CODE = 4;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.settings_layout);
		title=(TextView)findViewById(R.id.title);
		title.setText(this.getResources().getString(R.string.settings_title));
		findViewById(R.id.Return).setOnClickListener(this);
		initView();
	}
	
	private void initView(){
		emailState = (TextView) findViewById(R.id.email_state);
		commandState = (TextView) findViewById(R.id.command_state);
		setEmail = (RelativeLayout) findViewById(R.id.set_email);
		setCommand = (RelativeLayout) findViewById(R.id.set_command);
		resetPattern = (RelativeLayout) findViewById(R.id.reset_lockpattern);
		about_us = (RelativeLayout) findViewById(R.id.about_us);
		setEmail.setOnClickListener(this);
		setCommand.setOnClickListener(this);
		resetPattern.setOnClickListener(this);
		about_us.setOnClickListener(this);
		pwSettingDao=new PWSettingDao(this);
		//List<PWSetting> settingList1 = pwSettingDao.getSettingByName("email_address");
		Setting settingList1 = pwSettingDao.querySettingByName("email_address");
		if(settingList1!=null){
			emailState.setText("已绑定");
		}else{
			emailState.setText("未绑定");
		}
		//List<PWSetting> settingList2 = pwSettingDao.getSettingByName("pw_command");
		Setting settingList2 = pwSettingDao.querySettingByName("pw_command");
		if(settingList2!=null){
			commandState.setText("已开启");	
		}else{
			commandState.setText("未开启");
		}
	}

	@Override
	public void onClick(View item) {
		switch(item.getId()){
		case R.id.Return:
			Intent intent1 = new Intent(this,MainActivity.class);
			setResult(RESULT_OK, intent1);
			finish();
			break;
		case R.id.set_email:
			Intent intent2 = new Intent(this, SetEmailActivity.class);
			startActivityForResult(intent2,SET_EMAIL_CODE);
			finish();
			break;
		case R.id.set_command:
			Intent intent3 = new Intent(this, SetCommandActivity.class);
			startActivityForResult(intent3,SET_COMMAND_CODE);
			finish();
			break;
		case R.id.reset_lockpattern:
			Intent intent4 = new Intent(this,UnlockGesturePasswordActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("action", "reset");
			intent4.putExtras(bundle);
			startActivityForResult(intent4,SET_LOCK_CODE);
			finish();
			break;
		case R.id.about_us:
			Intent intent5 = new Intent(this,AboutUsActivity.class);
			startActivityForResult(intent5,ABOUT_US_CODE);
			finish();
			break;
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode){
			case SET_EMAIL_CODE:
				//List<PWSetting> settingList1 = pwSettingDao.getSettingByName("email_address");
				Setting settingList1 = pwSettingDao.querySettingByName("email_address");
				if(settingList1!=null){
					emailState.setText("已绑定");
				}else{
					emailState.setText("未绑定");
				}
				break;
			case SET_COMMAND_CODE:
				//List<PWSetting> settingList2 = pwSettingDao.getSettingByName("pw_command");
				Setting settingList2 = pwSettingDao.querySettingByName("pw_command");
				if(settingList2!=null){
					commandState.setText("已开启");	
				}else{
					commandState.setText("未开启");
				}
				break;
			case SET_LOCK_CODE:
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}