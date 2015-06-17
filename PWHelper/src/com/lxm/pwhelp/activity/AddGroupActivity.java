package com.lxm.pwhelp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.lxm.pwhelp.R;
import com.lxm.pwhelp.bean.PWGroup;
import com.lxm.pwhelp.dao.PWGroupDao;
import com.lxm.pwhelp.utils.Tools;
/**
 * Add group activity
 * @author lianxiao
 * @version 2015-5-29 14:10:45
 */
public class AddGroupActivity extends Activity implements View.OnClickListener {

	private EditText new_group;
	private PWGroupDao groupDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_group_layout);
		findViewById(R.id.group_cancel).setOnClickListener(this);
		findViewById(R.id.group_save).setOnClickListener(this);
		new_group = (EditText) findViewById(R.id.new_group);
		groupDao = new PWGroupDao(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.group_cancel:
			Intent intent = new Intent(AddGroupActivity.this, MainActivity.class);
			setResult(RESULT_OK, intent);
			finish();
			break;
		case R.id.group_save:
			String group_name = new_group.getText().toString();
			if(group_name.trim().length()==0){
				Tools.showErrorDialog(this, "警告", "请输入有效的分组名称！");
			}else{
				CreateOrUpdateStatus status = groupDao.createOrUpdate(new PWGroup(group_name,"0",Tools.getToday(),false));
				if(status.isCreated()){
					showDialog("添加成功","添加新分组成功！返回！");
				}else{
					showDialog("添加失败","添加新分组失败！返回！");
				}
			}
			break;
		}
	}
	
	private void showDialog(String title,String message){
		new AlertDialog.Builder(this)
		.setTitle(title)
		.setMessage(message)
		.setPositiveButton(
				"确定",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(
							DialogInterface dialog,
							int which) {
						dialog.dismiss();
						Intent intent = new Intent(AddGroupActivity.this, MainActivity.class);
						setResult(RESULT_OK, intent);
						finish();
					}
		}).show();
	}

}