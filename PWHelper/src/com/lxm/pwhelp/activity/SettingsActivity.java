package com.lxm.pwhelp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxm.pwhelp.R;

public class SettingsActivity extends Activity implements View.OnClickListener {
	
	private TextView emailState;
	private TextView commandState;
	
	private RelativeLayout setEmail;
	private RelativeLayout setCommand;
	
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
		setEmail.setOnClickListener(this);
		setCommand.setOnClickListener(this);
		emailState.setText("未绑定");
		commandState.setText("未开启");
	}

	@Override
	public void onClick(View item) {
		switch(item.getId()){
		case R.id.Settings_Return:
			Intent intent1 = new Intent(SettingsActivity.this,MainActivity.class);
			setResult(1,intent1);
			finish();
			break;
		case R.id.set_email:
			break;
		case R.id.set_command:
			Intent intent2 = new Intent(SettingsActivity.this, SetCommandActivity.class);
			startActivityForResult(intent2, 1);
			break;
		}
	}

}