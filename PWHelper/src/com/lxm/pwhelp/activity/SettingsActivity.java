package com.lxm.pwhelp.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxm.pwhelp.R;
import com.lxm.pwhelp.bean.PWSetting;
import com.lxm.pwhelp.dao.PWSettingDao;

public class SettingsActivity extends Activity implements View.OnClickListener {
	
	private TextView emailState;
	private TextView commandState;
	
	private RelativeLayout setEmail;
	private RelativeLayout setCommand;
	private RelativeLayout resetPattern;
	
	private PWSettingDao pwSettingDao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.settings_layout);
		findViewById(R.id.Settings_Return).setOnClickListener(this);
		initView();
	}
	
	private void initView(){
		emailState = (TextView) findViewById(R.id.email_state);
		commandState = (TextView) findViewById(R.id.command_state);
		setEmail = (RelativeLayout) findViewById(R.id.set_email);
		setCommand = (RelativeLayout) findViewById(R.id.set_command);
		resetPattern = (RelativeLayout) findViewById(R.id.reset_lockpattern);
		setEmail.setOnClickListener(this);
		setCommand.setOnClickListener(this);
		resetPattern.setOnClickListener(this);
		pwSettingDao=new PWSettingDao(this);
		List<PWSetting> settingList1 = pwSettingDao.getSettingByName("email_address");
		if(settingList1!=null&&settingList1.size()>0){
			emailState.setText("已绑定");
		}else{
			emailState.setText("未绑定");
		}
		List<PWSetting> settingList2 = pwSettingDao.getSettingByName("pw_command");
		if(settingList2!=null&&settingList2.size()>0){
			commandState.setText("已开启");	
		}else{
			commandState.setText("未开启");
		}
	}

	@Override
	public void onClick(View item) {
		switch(item.getId()){
		case R.id.Settings_Return:
			Intent intent1 = new Intent(SettingsActivity.this,MainActivity.class);
			startActivity(intent1);
			break;
		case R.id.set_email:
			Intent intent2 = new Intent(SettingsActivity.this, SetEmailActivity.class);
			startActivity(intent2);
			break;
		case R.id.set_command:
			Intent intent3 = new Intent(SettingsActivity.this, SetCommandActivity.class);
			startActivity(intent3);
			break;
		case R.id.reset_lockpattern:
			Intent intent4 = new Intent(SettingsActivity.this,UnlockGesturePasswordActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("action", "reset");
			intent4.putExtras(bundle);
			startActivityForResult(intent4,1);
			finish();
			break;
		}
	}
	
	@Override
	protected void onResume() {
		List<PWSetting> settingList1 = pwSettingDao.getSettingByName("email_address");
		if(settingList1!=null&&settingList1.size()>0){
			emailState.setText("已绑定");
		}else{
			emailState.setText("未绑定");
		}
		List<PWSetting> settingList2 = pwSettingDao.getSettingByName("pw_command");
		if(settingList2!=null&&settingList2.size()>0){
			commandState.setText("已开启");	
		}else{
			commandState.setText("未开启");
		}
		super.onResume();
	}

}