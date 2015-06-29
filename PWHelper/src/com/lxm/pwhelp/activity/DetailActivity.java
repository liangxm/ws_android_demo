package com.lxm.pwhelp.activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lxm.pwhelp.R;
import com.lxm.pwhelp.bean.Item;
import com.lxm.pwhelp.utils.Conver;
import com.lxm.pwhelp.utils.GroupType;

public class DetailActivity extends Activity implements View.OnClickListener {

	private RelativeLayout detail_line1, detail_line2, detail_line3,
			detail_line4, detail_line5, detail_line6, detail_line7,
			detail_line8, detail_line9, detail_line10;
	
	private TextView detail_line2_label1,detail_line4_label1,detail_line5_label1,detail_line6_label1,
			detail_line7_label1,detail_line8_label1,detail_line9_label1,
			detail_line10_label1;
	private TextView detail_line4_label2,detail_line5_label2,detail_line6_label2,
			detail_line7_label2,detail_line8_label2,detail_line9_label2,
			detail_line10_label2;
	private TextView line1_label2, line2_label2, line3_label2, detail_title;
	private String default_str, bank_str, web_str, weibo_str, qq_str,
			email_str, alipay_str, note_str;
	private ImageView item_logo_big;
	private ClipboardManager cmb;
	private Button button;
	private TextView title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.detail_layout);

		cmb = (ClipboardManager) this
				.getSystemService(Context.CLIPBOARD_SERVICE);
		title=(TextView) findViewById(R.id.title);
		title.setText(this.getResources().getString(R.string.detail_title));
		findViewById(R.id.Return).setOnClickListener(this);
		findViewById(R.id.detail_line1_label3).setOnClickListener(this);
		findViewById(R.id.detail_line2_label3).setOnClickListener(this);
		findViewById(R.id.detail_line3_label3).setOnClickListener(this);
		button=(Button)findViewById(R.id.open_app);
		button.setOnClickListener(this);
		button.setPadding(Conver.dip2px(this, 5),
				Conver.dip2px(this, 5), Conver.dip2px(this, 5),
				Conver.dip2px(this, 5));
		detail_line2_label1 = (TextView) findViewById(R.id.detail_line2_label1);
		detail_line1 = (RelativeLayout) findViewById(R.id.detail_line1);
		detail_line2 = (RelativeLayout) findViewById(R.id.detail_line2);
		detail_line3 = (RelativeLayout) findViewById(R.id.detail_line3);
		
		line1_label2 = (TextView) findViewById(R.id.detail_line1_label2);
		line2_label2 = (TextView) findViewById(R.id.detail_line2_label2);
		line3_label2 = (TextView) findViewById(R.id.detail_line3_label2);
		detail_title = (TextView) findViewById(R.id.detail_title);
		Bundle bundle = this.getIntent().getExtras();
		Item item = (Item) bundle.getSerializable("item");
		line1_label2.setText(item.getItem_type());
		line2_label2.setText(item.getItem_username());
		line3_label2.setText(item.getItem_password());

		line1_label2.setPadding(Conver.dip2px(this, 20), 0, 0, 0);
		line2_label2.setPadding(Conver.dip2px(this, 20), 0, 0, 0);
		line3_label2.setPadding(Conver.dip2px(this, 20), 0, 0, 0);
		detail_line1.setPadding(Conver.dip2px(this, 20),
				Conver.dip2px(this, 5), Conver.dip2px(this, 20),
				Conver.dip2px(this, 5));
		detail_line2.setPadding(Conver.dip2px(this, 20),
				Conver.dip2px(this, 5), Conver.dip2px(this, 20),
				Conver.dip2px(this, 5));
		detail_line3.setPadding(Conver.dip2px(this, 20),
				Conver.dip2px(this, 5), Conver.dip2px(this, 20),
				Conver.dip2px(this, 5));
		detail_title.setVisibility(View.GONE);

		item_logo_big = (ImageView) findViewById(R.id.item_logo_big);
		item_logo_big.setMaxHeight(Conver.dip2px(this, 162));
		item_logo_big.setMaxWidth(Conver.dip2px(this, 162));
		initGroupStr(item);
		appendExternal(item);
	}

	private void initGroupStr(Item item) {
		default_str = GroupType.Type_Default.getType();
		bank_str = GroupType.Type_Bank.getType();
		web_str = GroupType.Type_Web.getType();
		weibo_str = GroupType.Type_WeiBo.getType();
		qq_str = GroupType.Type_QQ.getType();
		email_str = GroupType.Type_Email.getType();
		alipay_str = GroupType.Type_Alipay.getType();
		note_str = GroupType.Type_Note.getType();
		String item_type = item.getItem_type();
		if (default_str.equals(item_type))
			item_logo_big.setImageResource(R.drawable.default_item_icon_big);
		else if (bank_str.equals(item_type))
			item_logo_big.setImageResource(R.drawable.bank_item_icon_big);
		else if (web_str.equals(item_type))
			item_logo_big.setImageResource(R.drawable.www_item_icon_big);
		else if (weibo_str.equals(item_type))
			item_logo_big.setImageResource(R.drawable.sina_item_icon_big);
		else if (qq_str.equals(item_type))
			item_logo_big.setImageResource(R.drawable.qq_item_icon_big);
		else if (email_str.equals(item_type))
			item_logo_big.setImageResource(R.drawable.email_item_icon_big);
		else if (alipay_str.equals(item_type))
			item_logo_big.setImageResource(R.drawable.alipay_item_icon_big);
		else if (note_str.equals(item_type)){
			detail_line2_label1.setText("标题");
			detail_line3.setVisibility(View.GONE);
			item_logo_big.setImageResource(R.drawable.note_item_icon_big);
		}else
			item_logo_big.setImageResource(R.drawable.default_item_icon_big);
	}
	
	private void appendExternal(Item item){
		if(item.getItem_name()!=null&&item.getItem_name().trim().length()>0){
			detail_line4 = (RelativeLayout) findViewById(R.id.detail_line4);
			detail_line4.setVisibility(View.VISIBLE);
			detail_line4_label1 = (TextView) findViewById(R.id.detail_line4_label1);
			detail_line4_label2 = (TextView) findViewById(R.id.detail_line4_label2);
			detail_line4_label1.setText("昵称");
			detail_line4_label2.setText(item.getItem_name());
			detail_line4.setPadding(Conver.dip2px(this, 20),
					Conver.dip2px(this, 5), Conver.dip2px(this, 20),
					Conver.dip2px(this, 5));
			detail_line4_label2.setPadding(Conver.dip2px(this, 20),
					Conver.dip2px(this, 5), Conver.dip2px(this, 20),
					Conver.dip2px(this, 5));
		}
		if(item.getItem_subtype()==0||item.getItem_subtype()==1){
			detail_line5 = (RelativeLayout) findViewById(R.id.detail_line5);
			detail_line5.setVisibility(View.VISIBLE);
			detail_line5_label1 = (TextView) findViewById(R.id.detail_line5_label1);
			detail_line5_label2 = (TextView) findViewById(R.id.detail_line5_label2);
			detail_line5_label1.setText("子类型");
			int subtype = item.getItem_subtype();
			if(subtype==0){
				detail_line5_label2.setText("储蓄卡");
			}else if(subtype==1){
				detail_line5_label2.setText("信用卡");
			}
			detail_line5.setPadding(Conver.dip2px(this, 20),
					Conver.dip2px(this, 5), Conver.dip2px(this, 20),
					Conver.dip2px(this, 5));
			detail_line5_label2.setPadding(Conver.dip2px(this, 20),
					Conver.dip2px(this, 5), Conver.dip2px(this, 20),
					Conver.dip2px(this, 5));
		}
		if(item.getItem_url()!=null&&item.getItem_url().trim().length()>0){
			detail_line6 = (RelativeLayout) findViewById(R.id.detail_line6);
			detail_line6.setVisibility(View.VISIBLE);
			detail_line6_label1 = (TextView) findViewById(R.id.detail_line6_label1);
			detail_line6_label2 = (TextView) findViewById(R.id.detail_line6_label2);
			detail_line6_label1.setText("URL");
			detail_line6_label2.setText(item.getItem_url());
			detail_line6.setPadding(Conver.dip2px(this, 20),
					Conver.dip2px(this, 5), Conver.dip2px(this, 20),
					Conver.dip2px(this, 5));
			detail_line6_label2.setPadding(Conver.dip2px(this, 20),
					Conver.dip2px(this, 5), Conver.dip2px(this, 20),
					Conver.dip2px(this, 5));
		}
		if(item.getItem_comment()!=null&&item.getItem_comment().trim().length()>0){
			detail_line7 = (RelativeLayout) findViewById(R.id.detail_line7);
			detail_line7.setVisibility(View.VISIBLE);
			detail_line7_label1 = (TextView) findViewById(R.id.detail_line7_label1);
			detail_line7_label2 = (TextView) findViewById(R.id.detail_line7_label2);
			if(item.getItem_type().equals("私密记事"))
				detail_line7_label1.setText("正文");
			else
				detail_line7_label1.setText("备注");
			detail_line7_label2.setText(item.getItem_comment());
			detail_line7.setPadding(Conver.dip2px(this, 20),
					Conver.dip2px(this, 5), Conver.dip2px(this, 20),
					Conver.dip2px(this, 5));
			detail_line7_label2.setPadding(Conver.dip2px(this, 20),
					Conver.dip2px(this, 5), Conver.dip2px(this, 20),
					Conver.dip2px(this, 5));
		}
		if(item.getQuestion1()!=null&&item.getQuestion1().trim().length()>0){
			detail_line8 = (RelativeLayout) findViewById(R.id.detail_line8);
			detail_line8.setVisibility(View.VISIBLE);
			detail_line8_label1 = (TextView) findViewById(R.id.detail_line8_label1);
			detail_line8_label2 = (TextView) findViewById(R.id.detail_line8_label2);
			detail_line8_label1.setText("密保问题1");
			detail_line8_label2.setText(item.getQuestion1());
			detail_line8.setPadding(Conver.dip2px(this, 20),
					Conver.dip2px(this, 5), Conver.dip2px(this, 20),
					Conver.dip2px(this, 5));
			detail_line8_label2.setPadding(Conver.dip2px(this, 20),
					Conver.dip2px(this, 5), Conver.dip2px(this, 20),
					Conver.dip2px(this, 5));
		}
		if(item.getQuestion2()!=null&&item.getQuestion2().trim().length()>0){
			detail_line9 = (RelativeLayout) findViewById(R.id.detail_line9);
			detail_line9.setVisibility(View.VISIBLE);
			detail_line9_label1 = (TextView) findViewById(R.id.detail_line9_label1);
			detail_line9_label2 = (TextView) findViewById(R.id.detail_line9_label2);
			detail_line9_label1.setText("密保问题2");
			detail_line9_label2.setText(item.getQuestion2());
			detail_line9.setPadding(Conver.dip2px(this, 20),
					Conver.dip2px(this, 5), Conver.dip2px(this, 20),
					Conver.dip2px(this, 5));
			detail_line9_label2.setPadding(Conver.dip2px(this, 20),
					Conver.dip2px(this, 5), Conver.dip2px(this, 20),
					Conver.dip2px(this, 5));
		}
		if(item.getCreated()!=null&&item.getCreated().trim().length()>0&&(!item.getCreated().equals("null"))){
			detail_line10 = (RelativeLayout) findViewById(R.id.detail_line10);
			detail_line10.setVisibility(View.VISIBLE);
			detail_line10_label1 = (TextView) findViewById(R.id.detail_line10_label1);
			detail_line10_label2 = (TextView) findViewById(R.id.detail_line10_label2);
			detail_line10_label1.setText("添加时间");
			detail_line10_label2.setText(item.getCreated());
			detail_line10.setPadding(Conver.dip2px(this, 20),
					Conver.dip2px(this, 5), Conver.dip2px(this, 20),
					Conver.dip2px(this, 5));
			detail_line10_label2.setPadding(Conver.dip2px(this, 20),
					Conver.dip2px(this, 5), Conver.dip2px(this, 20),
					Conver.dip2px(this, 5));
		}
	}

	@Override
	public void onClick(View item) {
		switch (item.getId()) {
		case R.id.Return:
			Intent intent = new Intent(this, MainActivity.class);
			setResult(RESULT_OK, intent);
			finish();
			break;
		case R.id.detail_line1_label3:
			cmb.setPrimaryClip(ClipData.newPlainText("item_type", line1_label2
					.getText().toString()));
			button.setTextColor(Color.rgb(78, 78, 78));
			Toast.makeText(getApplicationContext(), "内容已经被复制到剪切板！",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.detail_line2_label3:
			cmb.setPrimaryClip(ClipData.newPlainText("item_username",
					line2_label2.getText().toString()));
			button.setTextColor(Color.rgb(78, 78, 78));
			Toast.makeText(getApplicationContext(), "内容已经被复制到剪切板！",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.detail_line3_label3:
			cmb.setPrimaryClip(ClipData.newPlainText("item_password",
					line3_label2.getText().toString()));
			button.setTextColor(Color.rgb(78, 78, 78));
			Toast.makeText(getApplicationContext(), "内容已经被复制到剪切板！",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.open_app:
			Intent intent1 = new Intent(this,AppListActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("item_type", line1_label2.getText().toString());
			intent1.putExtras(bundle);
			startActivityForResult(intent1,1);
			finish();
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)
				&& (event.getAction() == KeyEvent.ACTION_DOWN)) {
			Intent intent = new Intent(DetailActivity.this, MainActivity.class);
			setResult(RESULT_OK, intent);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
