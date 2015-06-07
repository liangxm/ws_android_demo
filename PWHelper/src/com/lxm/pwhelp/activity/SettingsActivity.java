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
	
	private static final int SET_EMAIL_CODE = 1;
	private static final int SET_COMMAND_CODE = 2;
	private static final int SET_LOCK_CODE = 3;
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
			break;
		case R.id.reset_lockpattern:
			Intent intent4 = new Intent(this,UnlockGesturePasswordActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("action", "reset");
			intent4.putExtras(bundle);
			startActivityForResult(intent4,SET_LOCK_CODE);
			finish();
			break;
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode){
			case SET_EMAIL_CODE:
				List<PWSetting> settingList1 = pwSettingDao.getSettingByName("email_address");
				if(settingList1!=null&&settingList1.size()>0){
					emailState.setText("已绑定");
				}else{
					emailState.setText("未绑定");
				}
				break;
			case SET_COMMAND_CODE:
				List<PWSetting> settingList2 = pwSettingDao.getSettingByName("pw_command");
				if(settingList2!=null&&settingList2.size()>0){
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
}