package com.hp.lxm.rssnews;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class DialogActivity extends Activity {
	
	private int delayMillis = 8000;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layoout_dialog);
		
		new Handler().postDelayed(new Runnable(){
			@Override
			public void run() {
				DialogActivity.this.finish();
				Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.setClass(DialogActivity.this, SecondActivity.class);
				startActivityForResult(intent, 0);
			}
			
		}, delayMillis);
	}
}