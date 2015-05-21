package com.lxm.pwhelp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.lxm.pwhelp.R;
import com.lxm.pwhelp.dao.PWItemDao;

public class DetailActivity extends Activity implements View.OnClickListener {
	
	private PWItemDao itemDao;

	@Override
	protected void onCreate(Bundle  savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.detail_layout);
		findViewById(R.id.Detail_Return).setOnClickListener(this);
		init(this);
	}
	
	private void init(Context content){
		itemDao = new PWItemDao(content);
	}

	@Override
	public void onClick(View item) {
		switch(item.getId()){
		case R.id.Detail_Return:
			Intent intent = new Intent(DetailActivity.this, MainActivity.class);
			setResult(1, intent);
			finish();
			break;
		}
	}
}
