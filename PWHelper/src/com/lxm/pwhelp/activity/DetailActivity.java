package com.lxm.pwhelp.activity;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.lxm.pwhelp.R;

public class DetailActivity extends Activity implements View.OnClickListener {
	
	private TextView line1_label2,line2_label2,line3_label2;
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
		
		line1_label2 = (TextView) findViewById(R.id.detail_line1_label2);
		line2_label2 = (TextView) findViewById(R.id.detail_line2_label2);
		line3_label2 = (TextView) findViewById(R.id.detail_line3_label2);
		Bundle bundle = this.getIntent().getExtras();
		line1_label2.setText(bundle.getString("item_type"));
		line2_label2.setText(bundle.getString("item_username"));
		line3_label2.setText(bundle.getString("item_password"));
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
}
