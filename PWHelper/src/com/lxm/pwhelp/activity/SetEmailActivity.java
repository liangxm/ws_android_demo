package com.lxm.pwhelp.activity;

import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.lxm.pwhelp.R;
import com.lxm.pwhelp.bean.PWSetting;
import com.lxm.pwhelp.custom.EmailDialog;
import com.lxm.pwhelp.dao.PWSettingDao;
import com.lxm.pwhelp.utils.Tools;

public class SetEmailActivity extends Activity implements View.OnClickListener {

	private PWSettingDao pwSettingDao;
	private TextView set_email_note;
	private Button set_email_btn;
	private List<PWSetting> setting;
	private boolean isOn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.set_email_layout);
		findViewById(R.id.SetEmail_Return).setOnClickListener(this);
		findViewById(R.id.set_email_btn).setOnClickListener(this);
		initView();
	}

	private void initView() {
		pwSettingDao = new PWSettingDao(this);
		set_email_note = (TextView) findViewById(R.id.set_email_note);
		set_email_btn = (Button) findViewById(R.id.set_email_btn);
		setting = pwSettingDao.getSettingByName("email_address");
		if (setting != null && setting.size() > 0) {
			isOn = true;
			set_email_note.setText("您当前的数据备份邮箱地址是:"
					+ setting.get(0).getSetting_value());
			set_email_note.setGravity(Gravity.CENTER);
			set_email_btn.setText("修改备份邮箱");
		} else {
			set_email_note
					.setText("为了确保您的密码绝对安全，密码助手被设计成一款纯客户端App。您的任何数据将不被上传到服务器， 为了确保您的密码不被丢失，您可以设置您的email地址，以便备份您的密码！");
			set_email_btn.setText("设置备份邮箱地址");
		}
	}

	@Override
	public void onClick(View item) {
		switch (item.getId()) {
		case R.id.SetEmail_Return:
			Intent intent = new Intent(SetEmailActivity.this,
					SettingsActivity.class);
			setResult(RESULT_OK, intent);
			finish();
			break;
		case R.id.set_email_btn:
			String title = null;
			if (setting != null && setting.size() > 0) {
				title = "修改你的邮箱地址";
			} else {
				title = "请输入邮箱地址";
			}

			final EmailDialog dialog = new EmailDialog(SetEmailActivity.this,
					title);
			dialog.setOnPositiveListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String email_address = dialog.getEditText().getText()
							.toString();
					if (!Tools.isEmail(email_address)) {
						Tools.showWarningDialog(SetEmailActivity.this, "警告",
								"请输入有效的邮箱地址！");
					} else {
						PWSetting pwSetting = new PWSetting();
						if (isOn) {
							pwSetting.setSetting_id(setting.get(0)
									.getSetting_id());
						}
						pwSetting.setSetting_name("email_address");
						pwSetting.setSetting_value(email_address);
						CreateOrUpdateStatus status = pwSettingDao
								.createOrUpdate(pwSetting);
						if (status.isCreated() || status.isUpdated()) {
							Tools.showSucessDialog(SetEmailActivity.this,
									"绑定成功", "邮箱地址绑定成功,返回！", listener);
							dialog.dismiss();
						}
					}
				}
			});
			dialog.setOnNegativeListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			dialog.show();
			break;
		}
	}

	private android.content.DialogInterface.OnClickListener listener = new android.content.DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			Intent intent = new Intent(SetEmailActivity.this,
					SettingsActivity.class);
			setResult(RESULT_OK, intent);
			finish();
			dialog.dismiss();
		}
	};

}