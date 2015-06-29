package com.lxm.pwhelp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.lxm.pwhelp.R;
import com.lxm.pwhelp.utils.Conver;
import com.lxm.pwhelp.utils.Settings;
/**
 * introduction about this app
 * @author Listener
 * @version 2015-6-16 13:28:59
 */
public class AboutUsActivity extends Activity implements View.OnClickListener {
	
	private TextView textview_introduction;
	private TextView title;
	private String introduction = "<p>版本号："+Settings.VERSION+"<br/>"+
		"<h2>关于作者</h2>"+
		"作者：梁小满 (Listener)<br/>"+
		"Email: pwhelper@163.com<br/>"+
		"个人主页：<a href=\"http://my.csdn.net/lxm1247983646\">http://my.csdn.net/lxm1247983646</a><br/><br/>"+
		"<h2>鸣谢</h2>"+
		"感谢<font color='red'>Selina</font>同学在设计方面的大力支持和无私奉献。<br/>"+
		"感谢<font color='red'>Joyce</font>同学在用户体验和界面方面给予的客观建议。<br/>"+
		"感谢<font color='red'>Cherry</font>同学积极参与测试，让潜在bug及时修复。<br/>"+
		"Copyright @2015 Listener All Rights<br/>"+
		"Reserved</p>";

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.about_us_layout);
		findViewById(R.id.Return).setOnClickListener(this);
		title = (TextView) findViewById(R.id.title);
		textview_introduction = (TextView) findViewById(R.id.about_introduction);
		textview_introduction.setMovementMethod(LinkMovementMethod.getInstance());
		textview_introduction.setText(Html.fromHtml(introduction));
		textview_introduction.setPadding(Conver.dip2px(this, 10), 0, 0, 0);
		title.setText(this.getResources().getString(R.string.about_title));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.Return:
			Intent intent = new Intent(this, SettingsActivity.class);
			setResult(RESULT_OK, intent);
			finish();
			break;
		}
	}
}
