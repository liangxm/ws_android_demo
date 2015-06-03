package com.lxm.pwhelp.activity;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.lxm.pwhelp.R;
import com.lxm.pwhelp.bean.PWGroup;
import com.lxm.pwhelp.bean.PWItem;
import com.lxm.pwhelp.dao.PWGroupDao;
import com.lxm.pwhelp.dao.PWItemDao;

public class AddItemActivity extends Activity implements View.OnClickListener {

	private PWItemDao itemDao;
	private EditText name;
	private EditText username;
	private EditText password;
	private EditText url;
	private EditText comment;
	private EditText question1;
	private EditText question2;
	private Spinner type;
	private RadioGroup subtype;
	private String defaultStr,bankStr,bbsStr,weiboStr,qqStr,emailStr;
	
	private LinearLayout item1,item2,item3,item4,item5,item6,item7,item8,item9;
	
	private Resources resources;

	private ArrayAdapter<String> adapter;
	private String typeStr;
	
	private PWGroupDao groupDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_layout);
		findViewById(R.id.Dlg_Return).setOnClickListener(this);
		findViewById(R.id.Dlg_Submit).setOnClickListener(this);
		init(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.Dlg_Return:
			Intent intent = new Intent(this, MainActivity.class);
			setResult(RESULT_OK, intent);
			finish();
			break;
		case R.id.Dlg_Submit:
			String nameStr = name.getText().toString();
			String usernameStr = username.getText().toString();
			String passwordStr = password.getText().toString();
			String urlStr = url.getText().toString();
			String commentStr = comment.getText().toString();
			String question1Str = question1.getText().toString();
			String question2Str = question2.getText().toString();

			if (emptyStr(usernameStr))
				new AlertDialog.Builder(this).setTitle("警告")
						.setMessage("用户名不能为空！").setPositiveButton("确定", null)
						.show();
			else if (emptyStr(passwordStr))
				new AlertDialog.Builder(this).setTitle("警告")
						.setMessage("密码不能为空！").setPositiveButton("确定", null)
						.show();
			else {
				PWItem item = new PWItem();
				item.setItem_name(nameStr);
				item.setItem_username(usernameStr);
				item.setItem_password(passwordStr);
				item.setItem_type(typeStr == null ? "" : typeStr);
				switch(subtype.getCheckedRadioButtonId()){
				case R.id.radioCash:
					item.setItem_subtype(0);
					break;
				case R.id.radioCredit:
					item.setItem_subtype(1);
					break;
				}
				item.setItem_url(urlStr);
				item.setItem_comment(commentStr);
				item.setQuestion1(question1Str);
				item.setQuestion2(question2Str);
				CreateOrUpdateStatus status = itemDao.createOrUpdate(item);
				if (status.isCreated()) {
					new AlertDialog.Builder(this)
							.setTitle("添加成功")
							.setMessage("密码项添加成功,返回！")
							.setPositiveButton(
									"确定",
									new android.content.DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											Intent intent = new Intent(AddItemActivity.this, MainActivity.class);
											AddItemActivity.this.setResult(RESULT_OK, intent);
											AddItemActivity.this.finish();
											dialog.dismiss();
										}
							}).show();
				}
			}
			break;
		}
	}

	private boolean emptyStr(String str) {
		if (str != null && str.trim().length() > 0)
			return false;
		else
			return true;
	}

	private void init(Context context) {
		groupDao = new PWGroupDao(this);
		resources = this.getResources();
		itemDao = new PWItemDao(context);
		name = (EditText) findViewById(R.id.edit_name);
		username = (EditText) findViewById(R.id.edit_username);
		password = (EditText) findViewById(R.id.edit_password);
		url = (EditText) findViewById(R.id.edit_url);
		comment = (EditText) findViewById(R.id.edit_comment);
		question1 = (EditText) findViewById(R.id.edit_question1);
		question2 = (EditText) findViewById(R.id.edit_question2);
		type = (Spinner) findViewById(R.id.edit_type);
		subtype = (RadioGroup) findViewById(R.id.radioGroup);
		
		item1 = (LinearLayout) findViewById(R.id.item1);
		item2 = (LinearLayout) findViewById(R.id.item2);
		item3 = (LinearLayout) findViewById(R.id.item3);
		item4 = (LinearLayout) findViewById(R.id.item4);
		item5 = (LinearLayout) findViewById(R.id.item5);
		item6 = (LinearLayout) findViewById(R.id.item6);
		item7 = (LinearLayout) findViewById(R.id.item7);
		item8 = (LinearLayout) findViewById(R.id.item8);
		item9 = (LinearLayout) findViewById(R.id.item9);
		
		// 类型分组名称
		defaultStr = resources.getString(R.string.group_default);
		bankStr = resources.getString(R.string.group_bank);
		bbsStr = resources.getString(R.string.group_bbs);
		weiboStr = resources.getString(R.string.group_weibo);
		qqStr = resources.getString(R.string.group_qq);
		emailStr = resources.getString(R.string.group_email);
		
		List<PWGroup> pwGroups = groupDao.getAvailableGroup();
		String[] spinnerArr=new String[pwGroups.size()];
		for(int i=0;i<pwGroups.size();i++){
			spinnerArr[i] = pwGroups.get(i).getGroup_name();
		}
		
		// 将可选内容与ArrayAdapter连接起来
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, spinnerArr);
		// 设置下拉列表的风格
		//adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		type.setAdapter(adapter);
		// 添加事件Spinner事件监听
		type.setOnItemSelectedListener(new SpinnerSelectedListener());
		// 设置默认值
		type.setVisibility(View.VISIBLE);
	}

	// 使用数组形式操作
	class SpinnerSelectedListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			typeStr = adapter.getItem(arg2);
			if(defaultStr.equals(typeStr)){
				item1.setVisibility(View.VISIBLE);
				item3.setVisibility(View.VISIBLE);
				item4.setVisibility(View.VISIBLE);
				item6.setVisibility(View.VISIBLE);
				item2.setVisibility(View.GONE);
				item5.setVisibility(View.GONE);
				item7.setVisibility(View.GONE);
				item8.setVisibility(View.GONE);
				item9.setVisibility(View.GONE);
			}else if(bankStr.equals(typeStr)){
				item1.setVisibility(View.VISIBLE);
				item3.setVisibility(View.VISIBLE);
				item4.setVisibility(View.VISIBLE);
				item6.setVisibility(View.VISIBLE);
				item2.setVisibility(View.GONE);
				item5.setVisibility(View.GONE);
				item7.setVisibility(View.GONE);
				item8.setVisibility(View.GONE);
				item9.setVisibility(View.VISIBLE);
			}else if(bbsStr.equals(typeStr)){
				item1.setVisibility(View.VISIBLE);
				item3.setVisibility(View.VISIBLE);
				item4.setVisibility(View.VISIBLE);
				item5.setVisibility(View.VISIBLE);
				item6.setVisibility(View.VISIBLE);
				item2.setVisibility(View.GONE);
				item7.setVisibility(View.GONE);
				item8.setVisibility(View.GONE);
				item9.setVisibility(View.GONE);
			}else if(weiboStr.equals(typeStr)){
				item1.setVisibility(View.VISIBLE);
				item3.setVisibility(View.VISIBLE);
				item4.setVisibility(View.VISIBLE);
				item6.setVisibility(View.VISIBLE);
				item5.setVisibility(View.VISIBLE);
				item2.setVisibility(View.GONE);
				item7.setVisibility(View.GONE);
				item8.setVisibility(View.GONE);
				item9.setVisibility(View.GONE);
			}else if(qqStr.equals(typeStr)){
				item1.setVisibility(View.VISIBLE);
				item2.setVisibility(View.VISIBLE);
				item3.setVisibility(View.VISIBLE);
				item4.setVisibility(View.VISIBLE);
				item6.setVisibility(View.VISIBLE);
				item7.setVisibility(View.VISIBLE);
				item8.setVisibility(View.VISIBLE);
				item5.setVisibility(View.GONE);
				item9.setVisibility(View.GONE);
			}else if(emailStr.equals(typeStr)){
				item1.setVisibility(View.VISIBLE);
				item3.setVisibility(View.VISIBLE);
				item4.setVisibility(View.VISIBLE);
				item6.setVisibility(View.VISIBLE);
				item5.setVisibility(View.VISIBLE);
				item2.setVisibility(View.VISIBLE);
				item7.setVisibility(View.GONE);
				item8.setVisibility(View.GONE);
				item9.setVisibility(View.GONE);
			}else{
				item1.setVisibility(View.VISIBLE);
				item3.setVisibility(View.VISIBLE);
				item4.setVisibility(View.VISIBLE);
				item6.setVisibility(View.VISIBLE);
				item2.setVisibility(View.GONE);
				item5.setVisibility(View.GONE);
				item7.setVisibility(View.GONE);
				item8.setVisibility(View.GONE);
				item9.setVisibility(View.GONE);
			}
			
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}
}