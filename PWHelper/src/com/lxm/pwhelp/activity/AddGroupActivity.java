package com.lxm.pwhelp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.lxm.pwhelp.R;
import com.lxm.pwhelp.dao.PWItemDao;

public class AddGroupActivity extends Activity implements View.OnClickListener {

	private PWItemDao itemDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_group_layout);
		//findViewById(R.id.Group_Return).setOnClickListener(this);
		//findViewById(R.id.Group_Submit).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		/*case R.id.Group_Return:
			Intent intent = new Intent(AddGroupActivity.this, MainActivity.class);
			setResult(1, intent);
			finish();
			break;*/
		/*case R.id.Group_Submit:
			break;*/
		}
	}

}