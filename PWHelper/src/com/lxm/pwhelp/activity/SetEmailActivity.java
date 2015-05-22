package com.lxm.pwhelp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.lxm.pwhelp.R;

public class SetEmailActivity extends Activity implements View.OnClickListener {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.set_email_layout);
		findViewById(R.id.SetEmail_Return).setOnClickListener(this);
	}
	

	@Override
	public void onClick(View item) {
		switch(item.getId()){
		case R.id.SetEmail_Return:
			Intent intent = new Intent(SetEmailActivity.this,SettingsActivity.class);
			setResult(1,intent);
			finish();
			break;
		}
	}

}