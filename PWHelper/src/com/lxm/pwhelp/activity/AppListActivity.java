package com.lxm.pwhelp.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lxm.pwhelp.R;
/**
 * app list activity for user forward to special application
 * @author Listener
 * @version 2015-6-16 15:56:35
 */
public class AppListActivity extends Activity {
	private List<ApplicationInfo> mAppList;
	private List<ApplicationInfo> tempAppList;
	private AppAdapter mAdapter;
	private ListView mListView;
	private EditText searchApp;
	private Bundle bundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_list_layout);
		bundle = this.getIntent().getExtras();
		mAppList = getPackageManager().getInstalledApplications(0);
		tempAppList = new ArrayList<ApplicationInfo>();
		mListView = (ListView) findViewById(R.id.listView);
		searchApp = (EditText) findViewById(R.id.searchapp);
		searchApp.addTextChangedListener(textWatcher);
		mAdapter = new AppAdapter();
		mListView.setAdapter(mAdapter);
		// other setting
		mListView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long arg3) {
				ApplicationInfo item = mAppList.get(position);
				open(item);
			}
		});
	}
	
	/**
	 *  faster show application
	 * @param type
	 */
	private void fastShow(String type){
		if(type.contains("QQ")){
			for(ApplicationInfo info:mAppList){
				String applicationName = (String)getPackageManager().getApplicationLabel(info);
				if(applicationName.contains("QQ")){
					searchApp.setText("QQ");
					break;
				}
			}
		} else if(type.contains("支付宝")){
			for(ApplicationInfo info:mAppList){
				String applicationName = (String)getPackageManager().getApplicationLabel(info);
				if(applicationName.contains("支付宝")){
					searchApp.setText("支付宝");
					break;
				}
			}
		}
	}
	
	private void open(ApplicationInfo item) {
		// open app
		Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
		resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		resolveIntent.setPackage(item.packageName);
		List<ResolveInfo> resolveInfoList = getPackageManager()
				.queryIntentActivities(resolveIntent, 0);
		if (resolveInfoList != null && resolveInfoList.size() > 0) {
			ResolveInfo resolveInfo = resolveInfoList.get(0);
			String activityPackageName = resolveInfo.activityInfo.packageName;
			String className = resolveInfo.activityInfo.name;

			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);
			ComponentName componentName = new ComponentName(
					activityPackageName, className);

			intent.setComponent(componentName);
			startActivity(intent);
		}
	}
	
	@Override 
	public void onWindowFocusChanged(boolean hasFocus) { 
		String type = bundle.getString("item_type"); 
		if(type!=null&&type.length()>0){
			fastShow(type);
		}
	    super.onWindowFocusChanged(hasFocus); 
	}

	class AppAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return mAppList.size();
		}
		@Override
		public ApplicationInfo getItem(int position) {
			return mAppList.get(position);
		}
		@Override
		public long getItemId(int position) {
			return position;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(getApplicationContext(),
						R.layout.item_list_app, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			ApplicationInfo item = getItem(position);
			String applicationName = (String)getPackageManager().getApplicationLabel(item);
			holder.iv_icon.setImageDrawable(item.loadIcon(getPackageManager()));
			holder.tv_name.setText(applicationName);
			return convertView;
		}
		class ViewHolder {
			ImageView iv_icon;
			TextView tv_name;

			public ViewHolder(View view) {
				iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
				tv_name = (TextView) view.findViewById(R.id.tv_name);
				view.setTag(this);
			}
		}
	}
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg){
			switch (msg.arg1){
			case 1:
				String data = searchApp.getText().toString();
				if(data!=null&&!"".equals(data)){
					tempAppList.clear();
					for(ApplicationInfo info:mAppList){
						String applicationName = (String)getPackageManager().getApplicationLabel(info);
						if(applicationName.contains(data)){
							tempAppList.add(info);
						}
					}
					mAppList.clear();
					mAppList = tempAppList;
				}else{
					mAppList.clear();
					mAppList = getPackageManager().getInstalledApplications(0);
				}
				mAdapter.notifyDataSetChanged();
				break;
			}
		}
	};
	
	
	// main tab search box watcher
	private TextWatcher textWatcher = new TextWatcher() {
		@Override
		public void afterTextChanged(Editable s) {
			handler.post(eChanged);
		}
		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {}
		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
		}
	};
	
	//search box update
	private Runnable eChanged = new Runnable() {
		@Override
		public void run() {
			Message message = Message.obtain();
			message.arg1=1;
			handler.sendMessage(message);
		}
	};
}
