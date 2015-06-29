package com.lxm.pwhelp.activity;

import java.util.List;

import net.sqlcipher.database.SQLiteDatabase;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.lxm.pwhelp.R;
import com.lxm.pwhelp.bean.Group;
import com.lxm.pwhelp.bean.Item;
import com.lxm.pwhelp.dao.PWGroupDao;
import com.lxm.pwhelp.dao.PWItemDao;
import com.lxm.pwhelp.utils.GroupType;
import com.lxm.pwhelp.utils.Tools;

public class AddItemActivity extends Activity implements View.OnClickListener {

	private EditText name;
	private EditText username;
	private EditText password;
	private EditText url;
	private EditText comment;
	private EditText question1;
	private EditText question2;
	private Spinner type;
	private RadioGroup subtype;
	
	private TextView title;
	
	private LinearLayout line_type,line_name,line_username,line_password,line_url,line_comment,line_question1,line_question2,line_banktype;
	

	private ArrayAdapter<String> adapter;
	private String typeStr;
	
	private PWItemDao itemDao;
	private PWGroupDao groupDao;
	private SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_layout);
		title=(TextView)findViewById(R.id.title);
		title.setText(this.getResources().getText(R.string.add_title));
		findViewById(R.id.Return).setOnClickListener(this);
		findViewById(R.id.Dlg_Submit).setOnClickListener(this);
		init();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.Return:
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

			if (Tools.emptyStr(usernameStr))
				new AlertDialog.Builder(this).setTitle("警告")
						.setMessage("用户名不能为空！").setPositiveButton("确定", null)
						.show();
			else if (Tools.emptyStr(passwordStr))
				new AlertDialog.Builder(this).setTitle("警告")
						.setMessage("密码不能为空！").setPositiveButton("确定", null)
						.show();
			else {
				Item item = new Item();
				item.setItem_name(nameStr);
				item.setItem_username(usernameStr);
				item.setItem_password(passwordStr);
				item.setItem_type(typeStr == null ? "" : typeStr);
				if(line_banktype.getVisibility()!=View.VISIBLE){
					item.setItem_subtype(100);
				}else{
					switch(subtype.getCheckedRadioButtonId()){
					case R.id.radioCash:
						item.setItem_subtype(0);
						break;
					case R.id.radioCredit:
						item.setItem_subtype(1);
						break;
					}
				}
				item.setItem_url(urlStr);
				item.setItem_comment(commentStr);
				item.setQuestion1(question1Str);
				item.setQuestion2(question2Str);
				item.setCreated(Tools.getToday());
				itemDao.addItem(item);
				new AlertDialog.Builder(this)
						.setTitle("添加成功")
						.setMessage("密码项添加成功,返回！")
						.setCancelable(false)
						.setPositiveButton(
								"确定",
								new android.content.DialogInterface.OnClickListener() {
									@Override
									public void onClick(
											DialogInterface dialog,
											int which) {
										Intent intent = new Intent(AddItemActivity.this, MainActivity.class);
										setResult(RESULT_OK, intent);
										finish();
										dialog.dismiss();
									}
						}).show();
			}
			break;
		}
	}

	private void init() {
		groupDao = new PWGroupDao(this);
		itemDao = new PWItemDao(this);
		name = (EditText) findViewById(R.id.edit_name);
		username = (EditText) findViewById(R.id.edit_username);
		password = (EditText) findViewById(R.id.edit_password);
		url = (EditText) findViewById(R.id.edit_url);
		comment = (EditText) findViewById(R.id.edit_comment);
		question1 = (EditText) findViewById(R.id.edit_question1);
		question2 = (EditText) findViewById(R.id.edit_question2);
		type = (Spinner) findViewById(R.id.edit_type);
		subtype = (RadioGroup) findViewById(R.id.radioGroup);
		
		line_type = (LinearLayout) findViewById(R.id.item1);
		line_name = (LinearLayout) findViewById(R.id.item2);
		line_username = (LinearLayout) findViewById(R.id.item3);
		line_password = (LinearLayout) findViewById(R.id.item4);
		line_url = (LinearLayout) findViewById(R.id.item5);
		line_comment = (LinearLayout) findViewById(R.id.item6);
		line_question1 = (LinearLayout) findViewById(R.id.item7);
		line_question2 = (LinearLayout) findViewById(R.id.item8);
		line_banktype = (LinearLayout) findViewById(R.id.item9);
		
		List<Group> pwGroups = groupDao.queryGroupAll();
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
			if(GroupType.Type_Default.getType().equals(typeStr)){
				line_type.setVisibility(View.VISIBLE);
				line_username.setVisibility(View.VISIBLE);
				line_password.setVisibility(View.VISIBLE);
				line_comment.setVisibility(View.VISIBLE);
				line_name.setVisibility(View.GONE);
				line_url.setVisibility(View.GONE);
				line_question1.setVisibility(View.GONE);
				line_question2.setVisibility(View.GONE);
				line_banktype.setVisibility(View.GONE);
			}else if(GroupType.Type_Bank.getType().equals(typeStr)){
				username.setInputType(InputType.TYPE_CLASS_NUMBER);
				line_type.setVisibility(View.VISIBLE);
				line_username.setVisibility(View.VISIBLE);
				line_password.setVisibility(View.VISIBLE);
				line_comment.setVisibility(View.VISIBLE);
				line_name.setVisibility(View.GONE);
				line_url.setVisibility(View.GONE);
				line_question1.setVisibility(View.GONE);
				line_question2.setVisibility(View.GONE);
				line_banktype.setVisibility(View.VISIBLE);
			}else if(GroupType.Type_Web.getType().equals(typeStr)){
				line_type.setVisibility(View.VISIBLE);
				line_username.setVisibility(View.VISIBLE);
				line_password.setVisibility(View.VISIBLE);
				line_url.setVisibility(View.VISIBLE);
				line_comment.setVisibility(View.VISIBLE);
				line_name.setVisibility(View.GONE);
				line_question1.setVisibility(View.GONE);
				line_question2.setVisibility(View.GONE);
				line_banktype.setVisibility(View.GONE);
			}else if(GroupType.Type_WeiBo.getType().equals(typeStr)){
				line_type.setVisibility(View.VISIBLE);
				line_username.setVisibility(View.VISIBLE);
				line_password.setVisibility(View.VISIBLE);
				line_comment.setVisibility(View.VISIBLE);
				line_url.setVisibility(View.VISIBLE);
				line_name.setVisibility(View.GONE);
				line_question1.setVisibility(View.GONE);
				line_question2.setVisibility(View.GONE);
				line_banktype.setVisibility(View.GONE);
			}else if(GroupType.Type_QQ.getType().equals(typeStr)){
				username.setInputType(InputType.TYPE_CLASS_NUMBER);
				line_type.setVisibility(View.VISIBLE);
				line_name.setVisibility(View.VISIBLE);
				line_username.setVisibility(View.VISIBLE);
				line_password.setVisibility(View.VISIBLE);
				line_comment.setVisibility(View.VISIBLE);
				line_question1.setVisibility(View.VISIBLE);
				line_question2.setVisibility(View.VISIBLE);
				line_url.setVisibility(View.GONE);
				line_banktype.setVisibility(View.GONE);
			}else if(GroupType.Type_Email.getType().equals(typeStr)){
				line_type.setVisibility(View.VISIBLE);
				line_username.setVisibility(View.VISIBLE);
				line_password.setVisibility(View.VISIBLE);
				line_comment.setVisibility(View.VISIBLE);
				line_url.setVisibility(View.VISIBLE);
				line_name.setVisibility(View.VISIBLE);
				line_question1.setVisibility(View.GONE);
				line_question2.setVisibility(View.GONE);
				line_banktype.setVisibility(View.GONE);
			}else{
				line_type.setVisibility(View.VISIBLE);
				line_username.setVisibility(View.VISIBLE);
				line_password.setVisibility(View.VISIBLE);
				line_comment.setVisibility(View.VISIBLE);
				line_name.setVisibility(View.GONE);
				line_url.setVisibility(View.GONE);
				line_question1.setVisibility(View.GONE);
				line_question2.setVisibility(View.GONE);
				line_banktype.setVisibility(View.GONE);
			}
			
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}
	
	@Override
	protected void onDestroy() {
		if(db!=null){
			db.close();
		}
		super.onDestroy();
	}
}