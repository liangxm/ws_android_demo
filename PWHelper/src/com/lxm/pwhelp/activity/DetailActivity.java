package com.lxm.pwhelp.activity;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lxm.pwhelp.R;
import com.nineoldandroids.util.Conver;

public class DetailActivity extends Activity implements View.OnClickListener {
	
	private RelativeLayout detail_line1,detail_line2,detail_line3;
	private TextView line1_label2,line2_label2,line3_label2,detail_title;
	private ClipboardManager cmb;
	
	@Override
	protected void onCreate(Bundle  savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.detail_layout);
		
		cmb = (ClipboardManager)this.getSystemService(Context.CLIPBOARD_SERVICE);
		
		findViewById(R.id.Detail_Return).setOnClickListener(this);
		findViewById(R.id.detail_line1_label3).setOnClickListener(this);
		findViewById(R.id.detail_line2_label3).setOnClickListener(this);
		findViewById(R.id.detail_line3_label3).setOnClickListener(this);
		
		detail_line1 = (RelativeLayout) findViewById(R.id.detail_line1);
		detail_line2 = (RelativeLayout) findViewById(R.id.detail_line2);
		detail_line3 = (RelativeLayout) findViewById(R.id.detail_line3);
		
		line1_label2 = (TextView) findViewById(R.id.detail_line1_label2);
		line2_label2 = (TextView) findViewById(R.id.detail_line2_label2);
		line3_label2 = (TextView) findViewById(R.id.detail_line3_label2);
		detail_title = (TextView) findViewById(R.id.detail_title);
		Bundle bundle = this.getIntent().getExtras();
		line1_label2.setText(bundle.getString("item_type"));
		line2_label2.setText(bundle.getString("item_username"));
		line3_label2.setText(bundle.getString("item_password"));
		
		line1_label2.setPadding(Conver.dip2px(this, 20), 0, 0, 0);
		line2_label2.setPadding(Conver.dip2px(this, 20), 0, 0, 0);
		line3_label2.setPadding(Conver.dip2px(this, 20), 0, 0, 0);
		detail_line1.setPadding(Conver.dip2px(this, 20), 0, Conver.dip2px(this, 20), 0);
		detail_line2.setPadding(Conver.dip2px(this, 20), 0, Conver.dip2px(this, 20), 0);
		detail_line3.setPadding(Conver.dip2px(this, 20), 0, Conver.dip2px(this, 20), 0);
	}
	
	@Override
	public void onClick(View item) {
		switch(item.getId()){
		case R.id.Detail_Return:
			Intent intent = new Intent(DetailActivity.this, MainActivity.class);
			setResult(1, intent);
			finish();
			break;
		case R.id.detail_line1_label3:
			cmb.setText(line1_label2.getText().toString());
			Toast.makeText(getApplicationContext(), "内容已经被复制到剪切板！",
				     Toast.LENGTH_SHORT).show();
			break;
		case R.id.detail_line2_label3:
			cmb.setText(line2_label2.getText().toString());
			Toast.makeText(getApplicationContext(), "内容已经被复制到剪切板！",
				     Toast.LENGTH_SHORT).show();
			break;
		case R.id.detail_line3_label3:
			cmb.setText(line3_label2.getText().toString());
			Toast.makeText(getApplicationContext(), "内容已经被复制到剪切板！",
				     Toast.LENGTH_SHORT).show();
			break;
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)
				&& (event.getAction() == KeyEvent.ACTION_DOWN)) {
			Intent intent = new Intent(DetailActivity.this, MainActivity.class);
			setResult(1, intent);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
