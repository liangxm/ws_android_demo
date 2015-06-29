package com.lxm.pwhelp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.lxm.pwhelp.R;
import com.lxm.pwhelp.bean.Item;
import com.lxm.pwhelp.dao.PWItemDao;
import com.lxm.pwhelp.utils.Tools;

public class AddNoteActivity extends Activity implements View.OnClickListener {
	
	private TextView title;
	private EditText text_title;
	private EditText text_content;
	
	private PWItemDao itemDao;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_note_layout);
		title=(TextView)findViewById(R.id.title);
		text_title=(EditText)findViewById(R.id.edit_title);
		text_content=(EditText)findViewById(R.id.edit_content);
		title.setText(this.getResources().getString(R.string.add_note_title));
		findViewById(R.id.Return).setOnClickListener(this);
		findViewById(R.id.Dlg_Submit).setOnClickListener(this);
		itemDao = new PWItemDao(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.Return:
			Intent intent = new Intent(this, MainActivity.class);
			setResult(RESULT_OK,intent);
			finish();
			break;
		case R.id.Dlg_Submit:
			String title = text_title.getText().toString();
			String content = text_content.getText().toString();
			if(Tools.emptyStr(title)){
				new AlertDialog.Builder(this).setTitle("警告")
				.setMessage("标题不能为空！").setPositiveButton("确定", null)
				.show();
			}else if(Tools.emptyStr(content)){
				new AlertDialog.Builder(this).setTitle("警告")
				.setMessage("正文不能为空！").setPositiveButton("确定", null)
				.show();
			}else{
				Item item = new Item();
				item.setItem_username(title);
				item.setItem_comment(content);
				item.setItem_subtype(100);
				item.setItem_type("私密记事");
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
										Intent intent = new Intent(AddNoteActivity.this, MainActivity.class);
										setResult(RESULT_OK, intent);
										finish();
										dialog.dismiss();
									}
						}).show();
			}
			break;
		}
	}
	
}