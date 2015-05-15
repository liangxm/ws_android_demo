package com.lxm.pwhelp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.lxm.pwhelp.R;
import com.lxm.pwhelp.bean.PWItem;
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

	private static final String[] m = { "默认分组", "网银密码", "论坛密码", "微博密码", "QQ密码",
			"邮箱密码" };
	private ArrayAdapter<String> adapter;
	private String typeStr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_layout);
		findViewById(R.id.Dlg_Return).setOnClickListener(this);
		findViewById(R.id.Dlg_Submit).setOnClickListener(this);
		init(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.Dlg_Return:
			Intent intent = new Intent(AddItemActivity.this, MainActivity.class);
			setResult(1, intent);
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

			if (emptyStr(nameStr))
				new AlertDialog.Builder(this).setTitle("警告")
						.setMessage("名称不能为空！").setPositiveButton("确定", null)
						.show();
			else if (emptyStr(usernameStr))
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
				item.setItem_url(urlStr);
				item.setItem_comment(commentStr);
				item.setQuestion1(question1Str);
				item.setQuestion2(question2Str);
				int code = itemDao.add(item);
				if (code > 0) {
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
											setResult(1, intent);
											finish();
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
		itemDao = new PWItemDao(context);
		name = (EditText) findViewById(R.id.edit_name);
		username = (EditText) findViewById(R.id.edit_username);
		password = (EditText) findViewById(R.id.edit_password);
		url = (EditText) findViewById(R.id.edit_url);
		comment = (EditText) findViewById(R.id.edit_comment);
		question1 = (EditText) findViewById(R.id.edit_question1);
		question2 = (EditText) findViewById(R.id.edit_question2);
		type = (Spinner) findViewById(R.id.edit_type);
		// 将可选内容与ArrayAdapter连接起来
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, m);
		// 设置下拉列表的风格
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}
}