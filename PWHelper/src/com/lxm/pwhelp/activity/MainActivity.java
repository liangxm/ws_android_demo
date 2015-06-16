/**
 * Password helper main activity
 * Copyright (C) 2015 Listener
 */

package com.lxm.pwhelp.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.lxm.pwhelp.R;
import com.lxm.pwhelp.adapter.GroupAdapter;
import com.lxm.pwhelp.adapter.PWItemAdapter;
import com.lxm.pwhelp.bean.PWGroup;
import com.lxm.pwhelp.bean.PWItem;
import com.lxm.pwhelp.bean.PWSetting;
import com.lxm.pwhelp.custom.CircleImageView;
import com.lxm.pwhelp.custom.EmailDialog;
import com.lxm.pwhelp.custom.ToggleButton;
import com.lxm.pwhelp.dao.PWGroupDao;
import com.lxm.pwhelp.dao.PWItemDao;
import com.lxm.pwhelp.dao.PWSettingDao;
import com.lxm.pwhelp.email.MailSenderInfo;
import com.lxm.pwhelp.email.SimpleMailSender;
import com.lxm.pwhelp.utils.Conver;
import com.lxm.pwhelp.utils.DesUtils;
import com.lxm.pwhelp.utils.FileUtil;
import com.lxm.pwhelp.utils.SharedPreferencesUtils;
import com.lxm.pwhelp.utils.Tools;
import com.lxm.pwhelp.view.NoScrollViewPager;

public class MainActivity extends Activity implements View.OnClickListener {

	private PWItemAdapter adapter;
	private GroupAdapter groupAdapter;
	private TextView label1;
	private TextView label2;
	private TextView label3;
	private TextView label4;
	
	private SwipeMenuListView lv_list;
	
	private ImageButton mAddressImg;
	private ImageButton mFrdImg;

	private PagerAdapter mPagerAdapter;
	private ExpandableListView mainlistview;
	public List<String> parent;
	public Map<String, List<PWItem>> map;

	private ImageButton mSettingImg;
	private LinearLayout mTabAddress;
	private LinearLayout mTabFrd;
	private LinearLayout mTabSetting;
	private LinearLayout mTabWeiXin;

	private LinearLayout additem;
	private LinearLayout shodow_head;
	private LinearLayout profiles;
	private RelativeLayout backupitem;
	private RelativeLayout recovery;
	private RelativeLayout settings;
	private CircleImageView head_icon;
	private CircleImageView top_header;
	
	private EditText searchbox;
	
	private LinearLayout noitem;

	private NoScrollViewPager mViewPager;
	private List<View> mViews;
	private ImageButton mWeiXinImg;
	private List<PWItem> itemList;
	private List<PWItem> newItemList;
	private TextView title;
	private TextView nicknameView;

	private ImageView add_group;
	
	private Button no_add_item;

	private PWItemDao pwItemDao;
	private PWGroupDao pwGroupDao;
	private PWSettingDao pwSettingDao;
	
	private ProgressDialog progressDialog;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		initView();
		initViewPage();
		initEvent();
	}
	
	private void initEvent() {
		mTabWeiXin.setOnClickListener(this);
		mTabAddress.setOnClickListener(this);
		mTabFrd.setOnClickListener(this);
		mTabSetting.setOnClickListener(this);
		additem.setOnClickListener(this);
		backupitem.setOnClickListener(this);
		recovery.setOnClickListener(this);
		profiles.setOnClickListener(this);
		settings.setOnClickListener(this);
		add_group.setOnClickListener(this);
		no_add_item.setOnClickListener(this);
	}

	private void initView() {
		pwItemDao = new PWItemDao(this);
		pwGroupDao = new PWGroupDao(this);
		pwSettingDao = new PWSettingDao(this);
		mViews = new ArrayList<View>();
		mViewPager = (NoScrollViewPager) findViewById(R.id.id_viewpage);
		mTabWeiXin = (LinearLayout) findViewById(R.id.id_tab_weixin);
		mTabAddress = (LinearLayout) findViewById(R.id.id_tab_address);
		mTabFrd = (LinearLayout) findViewById(R.id.id_tab_frd);
		mTabSetting = (LinearLayout) findViewById(R.id.id_tab_settings);
		mWeiXinImg = (ImageButton) findViewById(R.id.id_tab_weixin_img);
		mAddressImg = (ImageButton) findViewById(R.id.id_tab_address_img);
		mFrdImg = (ImageButton) findViewById(R.id.id_tab_frd_img);
		mSettingImg = (ImageButton) findViewById(R.id.id_tab_settings_img);
		label1 = (TextView) findViewById(R.id.label1);
		label2 = (TextView) findViewById(R.id.label2);
		label3 = (TextView) findViewById(R.id.label3);
		label4 = (TextView) findViewById(R.id.label4);
		title = (TextView) findViewById(R.id.title);
		add_group = (ImageView) findViewById(R.id.add_group);
		add_group.setVisibility(View.GONE);
	}
	// 初始化数据
    public void initData() {
    	//initialize the group data
    	List<PWGroup> groups = pwGroupDao.getAvailableGroup();
    	String[] groupData = new String[]{
    			Tools.getResources(this, R.string.group_default),
    			Tools.getResources(this, R.string.group_bank),
    			Tools.getResources(this, R.string.group_web),
    			Tools.getResources(this, R.string.group_weibo),
    			Tools.getResources(this, R.string.group_qq),
    			Tools.getResources(this, R.string.group_email),
    			Tools.getResources(this, R.string.group_alipay)
    	};
    	parent = new ArrayList<String>();
    	map = new HashMap<String, List<PWItem>>();
    	if(groups.size()==0){
    		//initialize the group data
	    	for(String groupStr:groupData){
				pwGroupDao.createOrUpdate(new PWGroup(groupStr,"0",false));
				List<PWItem> list = new ArrayList<PWItem>();
				List<PWItem> itemGroups = pwItemDao.getPWItemByType(groupStr);
				parent.add(groupStr+"("+itemGroups.size()+")");
				for(PWItem item:itemGroups){
					list.add(item);
				}
				map.put(groupStr+"("+itemGroups.size()+")", list);
			}
	    	//initialize the settings
	    	PWSetting pwSetting1 = new PWSetting();
	    	pwSetting1.setSetting_name("pw_command");
	    	pwSetting1.setSetting_value("8888");
	    	pwSettingDao.createOrUpdate(pwSetting1);
	    	
	    	PWSetting pwSetting2 = new PWSetting();
	    	pwSetting2.setSetting_name("pw_nickname");
	    	pwSetting2.setSetting_value("Selina");
	    	pwSettingDao.createOrUpdate(pwSetting2);
    	}else{
    		for(PWGroup group:groups){
    			List<PWItem> list = new ArrayList<PWItem>();
				List<PWItem> itemGroups = pwItemDao.getPWItemByType(group.getGroup_name());
				parent.add(group.getGroup_name()+"("+itemGroups.size()+")");
				for(PWItem item:itemGroups){
					list.add(item);
				}
				map.put(group.getGroup_name()+"("+itemGroups.size()+")", list);
    		}
    	}
    	
    	//initialize the item data
    	List<PWItem> items = pwItemDao.getAvailablePWItem();
		switchTheNoItem(items);
		for (PWItem item : items) {
			itemList.add(item);
		}
    }

	private void initViewPage() {
		// 初妈化四个布局
		LayoutInflater mLayoutInflater = LayoutInflater.from(this);
		View tab01 = mLayoutInflater.inflate(R.layout.tab01, null);
		View tab02 = mLayoutInflater.inflate(R.layout.tab02, null);
		View tab03 = mLayoutInflater.inflate(R.layout.tab03, null);
		View tab04 = mLayoutInflater.inflate(R.layout.tab04, null);

		additem = (LinearLayout) tab03.findViewById(R.id.additem);
		shodow_head = (LinearLayout) tab04.findViewById(R.id.shodow_head);
		profiles = (LinearLayout) tab04.findViewById(R.id.profiles);
		mainlistview = (ExpandableListView) tab02.findViewById(R.id.explistview);

		backupitem = (RelativeLayout) tab04.findViewById(R.id.cloud);
		recovery = (RelativeLayout) tab04.findViewById(R.id.recovery);
		settings = (RelativeLayout) tab04.findViewById(R.id.settings);
		head_icon = (CircleImageView) tab04.findViewById(R.id.head_icon);
		head_icon.setMaxWidth(Conver.dip2px(this, 250));
		head_icon.setMaxHeight(Conver.dip2px(this, 250));
		shodow_head.setPadding(Conver.dip2px(this, 5), Conver.dip2px(this, 5), Conver.dip2px(this, 5), Conver.dip2px(this, 5));
		top_header = (CircleImageView) findViewById(R.id.top_header);
		
		add_group = (ImageView) findViewById(R.id.add_group);
		noitem = (LinearLayout) tab01.findViewById(R.id.noitem);
		no_add_item = (Button) tab01.findViewById(R.id.no_add_item);
		lv_list = (SwipeMenuListView) tab01.findViewById(R.id.list1);
		searchbox = (EditText) tab01.findViewById(R.id.searchbox);
		nicknameView = (TextView) tab04.findViewById(R.id.name);
		searchbox.addTextChangedListener(textWatcher);
		itemList = new ArrayList<PWItem>();
		// 设置单个分组展开
		initData();
		groupAdapter=new GroupAdapter(this, parent, map);
		mainlistview.setAdapter(groupAdapter);
		mainlistview.setOnChildClickListener(new OnChildClickListener(){
			@Override
			public boolean onChildClick(ExpandableListView parentlist, View view,
					int groupPosition, int childPosition, long id) {
				ToggleButton toggleButton = (ToggleButton)view.findViewById(R.id.mTogBtn);
				if(!toggleButton.isSelected()){
					Tools.showToast(MainActivity.this, "打开开关，触击该项以查看详情！");
					return false;
				}else{
					String key = parent.get(groupPosition);
					PWItem item = map.get(key).get(childPosition);
					Intent intent = new Intent(MainActivity.this, DetailActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("item", item);
					intent.putExtras(bundle);
					startActivityForResult(intent,VIEW_ITEM_CODE);
					return true;
				}
			}
		});
		adapter = new PWItemAdapter(MainActivity.this, itemList);
		lv_list.setAdapter(adapter);
		
		// step 1. create a MenuCreator
		SwipeMenuCreator creator = new SwipeMenuCreator(){
			@Override
			public void create(SwipeMenu menu) {
				// create "open" item
				SwipeMenuItem openItem = new SwipeMenuItem(
						getApplicationContext());
				// set item background
				openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
						0xCE)));
				// set item width
				openItem.setWidth(Conver.dip2px(getApplicationContext(),90));
				// set item title
				openItem.setTitle("Edit");
				// set item title fontsize
				openItem.setTitleSize(18);
				// set item title font color
				openItem.setTitleColor(Color.WHITE);
				// add to menu
				menu.addMenuItem(openItem);

				// create "delete" item
				SwipeMenuItem deleteItem = new SwipeMenuItem(
						getApplicationContext());
				// set item background
				deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
						0x3F, 0x25)));
				// set item width
				deleteItem.setWidth(Conver.dip2px(getApplicationContext(),90));
				// set a icon
				deleteItem.setIcon(R.drawable.ic_delete);
				// add to menu
				menu.addMenuItem(deleteItem);
			}
		};
		
		//set creator
		lv_list.setMenuCreator(creator);
		
		// step 2. listener item click event
		lv_list.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public void onMenuItemClick(int position, SwipeMenu menu, int index) {
				PWItem item = itemList.get(position);
				switch (index) {
				case 0:
					// edit
					edit(item);
					break;
				case 1:
					AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
					builder.setMessage("确定要删除吗？");
					builder.setTitle("提示");
					builder.setPositiveButton("确认",new ClickListener(item,"delete"));
					builder.setNegativeButton("取消",new ClickListener(item,"destory"));
					builder.show();
					break;
				default:
					break;
				}
			}
			
		});
		
		lv_list.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long arg3) {
				ToggleButton toggleButton = (ToggleButton)view.findViewById(R.id.mTogBtn);
				if(!toggleButton.isSelected()){
					Tools.showToast(MainActivity.this, "打开开关，触击该项以查看详情！");
				}else{
					PWItem item = itemList.get(position);
					Intent intent = new Intent(MainActivity.this, DetailActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("item", item);
					intent.putExtras(bundle);
					startActivityForResult(intent,VIEW_ITEM_CODE);
				}
			}
		});
		
		mViews.add(tab01);
		mViews.add(tab02);
		mViews.add(tab03);
		mViews.add(tab04);

		// 适配器初始化并设置
		mPagerAdapter = new PagerAdapter() {
			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				container.removeView(mViews.get(position));
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				View view = mViews.get(position);
				container.addView(view);
				return view;
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return mViews.size();
			}
		};

		mViewPager.setAdapter(mPagerAdapter);
	}

	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.id_tab_weixin: {
			add_group.setVisibility(View.GONE);
			top_header.setVisibility(View.VISIBLE);
			mViewPager.setCurrentItem(0);
			resetImg();
			label1.setTextColor(Color.rgb(115, 215, 107));
			mWeiXinImg.setImageResource(R.drawable.home_selected);
			title.setText("首页");
			break;
		}
		case R.id.id_tab_address: {
			mainlistview.requestFocusFromTouch();
			add_group.setVisibility(View.VISIBLE);
			top_header.setVisibility(View.VISIBLE);
			mViewPager.setCurrentItem(1);
			resetImg();
			label2.setTextColor(Color.rgb(115, 215, 107));
			mAddressImg.setImageResource(R.drawable.pass_selected);
			title.setText("密码分组");
			break;
		}
		case R.id.id_tab_frd: {
			add_group.setVisibility(View.GONE);
			top_header.setVisibility(View.VISIBLE);
			mViewPager.setCurrentItem(2);
			resetImg();
			label3.setTextColor(Color.rgb(115, 215, 107));
			mFrdImg.setImageResource(R.drawable.manage_selected);
			title.setText("密码管理");
			break;
		}
		case R.id.id_tab_settings: {
			add_group.setVisibility(View.GONE);
			top_header.setVisibility(View.GONE);
			mViewPager.setCurrentItem(3);
			resetImg();
			label4.setTextColor(Color.rgb(115, 215, 107));
			mSettingImg.setImageResource(R.drawable.person_selected);
			title.setText("个人中心");
			break;
		}
		case R.id.additem: {
			Intent intent = new Intent(this, AddItemActivity.class);
			startActivityForResult(intent, ADD_ITEM_CODE);
			break;
		}
		case R.id.cloud: {
			if(!Tools.isWiFiConnected(this)){
				Tools.showToast(this, "由于此功能需要将您的备份数据发送到您的邮箱，请先开启WiFi后使用此功能！");
			}else{
				dialogEmail();
			}
			break;
		}
		case R.id.profiles: {
			Intent intent = new Intent(this, PersonalActivity.class);
			startActivityForResult(intent,PROFILE_SETTING_CODE);
			break;
		}
		case R.id.recovery: {
			showFileChooser();
			break;
		}
		case R.id.add_group: {
			Intent intent = new Intent(this, AddGroupActivity.class);
			startActivityForResult(intent, ADD_GROUP_CODE);
			break;
		}
		case R.id.settings: {
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivityForResult(intent,SYSY_SETTING_CODE);
			break;
		}
		case R.id.no_add_item: {
			Intent intent = new Intent(this, AddItemActivity.class);
			startActivityForResult(intent,FIRST_ADD_CODE);
			break;
		}
		}
	}
	
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
	
	// use of the handler update the ui
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg){
			switch (msg.arg1) {
			case 1:
				updateListView();
				break;
			case 2:
				Tools.showToast(MainActivity.this, msg.obj.toString());
				if(progressDialog.isShowing())
					progressDialog.dismiss();
				if(msg.arg2==0){
					updateListView();
				}
				break;
			case 3:
				String data = searchbox.getText().toString();
				itemList.clear();
				if (data != null && !"".equals(data)) 
					newItemList = pwItemDao.getPWItemByName(data);
				else
					newItemList = pwItemDao.getAvailablePWItem();
				for(PWItem item:newItemList){
					itemList.add(item);
				}
				adapter.notifyDataSetChanged();
				break;
			}
		}
	};
	
	// main list view update
	private void updateListView(){
		List<PWItem> items = pwItemDao.getAvailablePWItem();
		switchTheNoItem(items);
		itemList.clear();
		for (PWItem item : items) {
			itemList.add(item);
			//reverse the list so that newer item is up
			Collections.reverse(itemList);
		}
		adapter.notifyDataSetChanged();
		
		List<PWGroup> groups = pwGroupDao.getAvailableGroup();
		parent.clear();
		map.clear();
		for(PWGroup group:groups){
			List<PWItem> list = new ArrayList<PWItem>();
			List<PWItem> itemGroups = pwItemDao.getPWItemByType(group.getGroup_name());
			parent.add(group.getGroup_name()+"("+itemGroups.size()+")");
			for(PWItem item:itemGroups){
				list.add(item);
			}
			map.put(group.getGroup_name()+"("+itemGroups.size()+")", list);
		}
		groupAdapter.notifyDataSetChanged();
	}
	
	//search box update
	private Runnable eChanged = new Runnable() {
		@Override
		public void run() {
			Message message = Message.obtain();
			message.arg1=3;
			handler.sendMessage(message);
		}
	};
	
	private Runnable listViewChanged = new Runnable() {
		@Override
		public void run() {
			Message message = Message.obtain();
			message.arg1=1;
			handler.sendMessage(message);
		}
	};
	private class SendEmail implements Runnable {
		private String email;
		public SendEmail(String email){
			this.email=email;
		}
		@Override
		public void run() {
			Message message = Message.obtain();
			message.arg1=2;
			try {
				if(!FileUtil.createFile("pwhelper.pw")){
					message.obj = "文件创建失败！";
				}else{
					//write data to file
					DesUtils des = new DesUtils();
					FileUtil.saveFileSdcard("pwhelper.pw", des.encrypt(buildDataString()),false);
					
					//send email with attachment
					MailSenderInfo mailInfo = new MailSenderInfo();   
				    mailInfo.setMailServerHost(getString(R.string.email_host));   
				    mailInfo.setMailServerPort(getString(R.string.email_port));   
				    mailInfo.setValidate(true);   
				    mailInfo.setUserName(getString(R.string.email_user));   
				    mailInfo.setPassword(getString(R.string.email_password));//您的邮箱密码   
				    mailInfo.setFromAddress(getString(R.string.email_user));   
				    mailInfo.setToAddress(email);   
				    mailInfo.setAttachFileNames(new String[]{Environment.getExternalStorageDirectory()
							+ File.separator + "pwhelper.pw"});
				    mailInfo.setSubject(MainActivity.this.getResources().getString(R.string.email_subject));   
				    mailInfo.setContent(MainActivity.this.getResources().getString(R.string.email_content));   
				    //这个类主要来发送邮件  
				    SimpleMailSender sms = new SimpleMailSender();  
				    if(sms.sendTextMail(mailInfo)){
				    	message.obj="数据备份成功！";
				    }
				    //sms.sendHtmlMail(mailInfo);//发送html格式
				}
			} catch (NotFoundException e) {
				message.obj="文件没有发现，请在应用管理界面为此App开启文件读写权限！";
			} catch (FileNotFoundException e) {
				message.obj="文件没有发现，请在应用管理界面为此App开启文件读写权限！";
			} catch (UnsupportedEncodingException e) {
				message.obj="数据流编码失败，请尝试重装密码助手！";
			} catch (IOException e) {
				message.obj="文件写入或创建失败，请检查您的SD卡是否正常！";
			} catch (MessagingException e) {
				message.obj="邮件发送失败，请检查您的备份邮箱地址后重试！";
			} catch (Exception e) {
				e.printStackTrace();
				message.obj="未知错误，已发送错误报告给作者！"+e.getMessage();
			}
			handler.sendMessage(message);
		}
		
		private String getString(int id){
			return MainActivity.this.getResources().getString(id);
		}
		private String buildDataString(){
			//data loading
			String wrapLine = "\n";
			StringBuffer settingBuffer = new StringBuffer();
			StringBuffer groupBuffer = new StringBuffer();
			StringBuffer itemBuffer = new StringBuffer();
			
			List<PWSetting> pwSettingList = pwSettingDao.getSettingAll();
			for(PWSetting setting:pwSettingList){
				settingBuffer.append("setting|").append(setting.toString()).append(wrapLine);
			}
			
			List<PWGroup> pwGroupList = pwGroupDao.getGroupAll();
			for(PWGroup group:pwGroupList){
				groupBuffer.append("group|").append(group.toString()).append(wrapLine);
			}
			
			List<PWItem> pwItemList = pwItemDao.getPWItemAll();
			for(PWItem item:pwItemList){
				itemBuffer.append("item|").append(item.toString()).append(wrapLine);
			}
			return settingBuffer.toString()+groupBuffer.toString()+itemBuffer.toString();
		}
	}
	
	private class DataRecovery implements Runnable {
		private String path;
		public DataRecovery(String path) {
			this.path = path;
		}
		@Override
		public void run() {
			Message message = Message.obtain();
			message.arg1=2;
			if(!path.endsWith(".pw")){
				message.obj="文件格式错误，请选择正确的备份文件";
			}else{
				try {
					String content = FileUtil.loadFileFromSdcard(MainActivity.this, path);
					DesUtils des = new DesUtils();
					String wrapLine = "\n";
					//content = content.substring(0, content.length());
					String decryptContent = des.decrypt(content);
					String[] wholeData = decryptContent.split(wrapLine);
					//clear the database
					pwItemDao.deleteAll();
					pwGroupDao.deleteAll();
					pwSettingDao.deleteAll();
					//load the data
					for(String line:wholeData){
						String[] row = line.split("[|]");
						if(BR_TAG_ITEM.equals(row[0])){
							PWItem item = new PWItem();
							item.setItem_id(Integer.parseInt(row[1]));
							item.setItem_name(row[2]);
							item.setItem_username(row[3]);
							item.setItem_password(row[4]);
							item.setItem_type(row[5]);
							item.setItem_subtype(Integer.parseInt(row[6]));
							item.setItem_url(row[7]);
							item.setItem_comment(row[8]);
							item.setQuestion1(row[9]);
							item.setQuestion2(row[10]);
							item.setModified(row[11]);
							item.setCreated(row[12]);
							item.setDeleted(Boolean.valueOf(row[13]));
							pwItemDao.createOrUpdate(item);
						}else if(BR_TAG_GROUP.equals(row[0])){
							PWGroup group = new PWGroup();
							group.setGroup_id(Integer.parseInt(row[1]));
							group.setGroup_name(row[2]);
							group.setGroup_level(row[3]);
							group.setCreated(row[4]);
							group.setDeleted(Boolean.valueOf(row[5]));
							pwGroupDao.createOrUpdate(group);
						}else if(BR_TAG_SETTING.equals(row[0])){
							PWSetting setting = new PWSetting();
							setting.setSetting_id(Integer.parseInt(row[1]));
							setting.setSetting_name(row[2]);
							setting.setSetting_value(row[3]);
							setting.setCreated(row[4]);
							setting.setDeleted(Boolean.valueOf(row[5]));
							pwSettingDao.createOrUpdate(setting);
						}
					}
					message.obj="数据恢复成功！";
					message.arg2=0;
				} catch (FileNotFoundException e1) {
					message.obj="文件没有读到！"+path;
				} catch (IOException e2) {
					message.obj="文件读取失败！"+path;
				} catch (Exception e) {
					e.printStackTrace();
					message.obj="未知错误："+e.getMessage();
				}
				handler.sendMessage(message);
			}
		}
	}
	
	private class ClickListener implements android.content.DialogInterface.OnClickListener {
		private PWItem item;
		private String type;
		private String email;
		public ClickListener(PWItem item,String type){
			this.item = item;
			this.type = type;
		}
		public ClickListener(String email,String type){
			this.email = email;
			this.type = type;
		}
		@Override
		public void onClick(DialogInterface dialog, int which) {
			if("delete".equals(type)){
				delete(dialog);
			}else if("destory".equals(type)){
				dialog.dismiss();
			}else if("email".equals(type)){
				progressDialog = ProgressDialog.show(MainActivity.this, "数据备份", "正在尝试将您的数据发送到备份邮箱，请稍后...",true, true);
				// android can not access network in main thread 
				new Thread(new SendEmail(email)).start();
			}
		}
		private void delete(DialogInterface dialog){
			item.setDeleted(true);
			CreateOrUpdateStatus status = pwItemDao.createOrUpdate(item);
			if(status.isUpdated()){
				itemList.remove(item);
				adapter.notifyDataSetChanged();
				dialog.dismiss();
				handler.post(listViewChanged);
			}
		}
	}
	
	private void resetImg() {
		label1.setTextColor(Color.rgb(88, 88, 88));
		label2.setTextColor(Color.rgb(88, 88, 88));
		label3.setTextColor(Color.rgb(88, 88, 88));
		label4.setTextColor(Color.rgb(88, 88, 88));
		mWeiXinImg.setImageResource(R.drawable.home_noselected);
		mAddressImg.setImageResource(R.drawable.pass_noselected);
		mFrdImg.setImageResource(R.drawable.manage_noselected);
		mSettingImg.setImageResource(R.drawable.person_noselected);
	}
	
	/**
	 * modify the item
	 * @param item
	 */
	private void edit(PWItem item){
		Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("item", item);
		intent.putExtras(bundle);
		startActivityForResult(intent, EDIT_ITEM_CODE);
	}

	// 回调方法，从第二个页面回来的时候会执行这个方法
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {
			return;
		} else {
			switch (requestCode){
			case EDIT_ITEM_CODE:
			case ADD_GROUP_CODE:
			case ADD_ITEM_CODE:
			case FIRST_ADD_CODE:
				handler.post(listViewChanged);
				break;
			case FILE_SELECT_CODE:
				Uri fileUri = data.getData();
				String path = fileUri.getPath();
				if(!path.endsWith(".pw")){
					Tools.showToast(this, "文件选择错误,请选择正确的备份文件！");
				}else{
					progressDialog = ProgressDialog.show(MainActivity.this, "数据恢复", "正在尝试恢复您的数据，请稍后...",true, true);
					handler.post(new DataRecovery(path));
				}
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)
				&& (event.getAction() == KeyEvent.ACTION_DOWN)) {
			dialogExit();
		}
		return super.onKeyDown(keyCode, event);
	}

	/** 调用文件选择软件来选择文件 **/
	private void showFileChooser() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("*/*");
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		try {
			startActivityForResult(Intent.createChooser(intent, "请选择您的备份文件"),
					FILE_SELECT_CODE);
		} catch (android.content.ActivityNotFoundException ex) {
			// Potentially direct the user to the Market with a Dialog
			Toast.makeText(this, "请安装文件管理器", Toast.LENGTH_SHORT).show();
		}
	}

	// 弹窗
	private void dialogEmail() {
		List<PWSetting> setting = pwSettingDao.getSettingByName("email_address");
		if(setting!=null&&setting.size()>0){
			dialogBackup(setting.get(0).getSetting_value());
		}else{
			final EmailDialog dialog = new EmailDialog(MainActivity.this,"请输入邮箱地址");
			final EditText editText = (EditText) dialog.getEditText();
			editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
			dialog.setOnPositiveListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String email_address = editText.getText().toString();
					if (!Tools.isEmail(email_address)) {
						Tools.showWarningDialog(MainActivity.this, "警告",
								"请输入有效的邮箱地址！");
					}else{
						PWSetting pwSetting = new PWSetting();
						pwSetting.setSetting_name("email_address");
						pwSetting.setSetting_value(email_address);
						CreateOrUpdateStatus status = pwSettingDao
								.createOrUpdate(pwSetting);
						if(status.isCreated()){
							dialogBackup(email_address);
						}
					}
					dialog.dismiss();
				}
			});
			dialog.setOnNegativeListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			dialog.show();
		}
	}

	/**
	 * dialog for exit
	 */
	protected void dialogExit() {
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setMessage("确认退出吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认",
		new android.content.DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
				System.exit(0);
			}
		});

		builder.setNegativeButton("取消",
		new android.content.DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
	
	public void dialogBackup(final String email){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("开始备份数据到"+email);
		builder.setTitle("数据备份");
		builder.setPositiveButton("确认",new ClickListener(email,"email"));
		builder.setNegativeButton("取消",new ClickListener(email,"destory"));
		builder.show();
	}

	@Override
	protected void onResume() {
		//头像更新
		String imageUri = String.valueOf(SharedPreferencesUtils.getParam(MainActivity.this, SharedPreferencesUtils.PHOTO_PATH, new String()));
		String nickname = String.valueOf(SharedPreferencesUtils.getParam(MainActivity.this, SharedPreferencesUtils.NICK_NAME, new String()));
		File file = new File(imageUri);
		Uri uri = null;
		if(imageUri!=null&&file.exists()){
			uri = Uri.fromFile(file);
			top_header.setImageURI(uri);
			head_icon.setImageURI(uri);
		}else{
			top_header.setImageResource(R.drawable.head_icon);
			head_icon.setImageResource(R.drawable.head_icon);
		}
		if(nickname!=null&&nickname.trim().length()>0){
			nicknameView.setText(nickname);
		}
		super.onResume();
	}
	
	/**
	 * display the no item note and button
	 * @param items
	 */
	private void switchTheNoItem(List<PWItem> items){
		if(items.size()==0){
			lv_list.setVisibility(View.GONE);
			noitem.setVisibility(View.VISIBLE);
		}else{
			noitem.setVisibility(View.GONE);
			lv_list.setVisibility(View.VISIBLE);
		}
	}

	@Override
	protected void onDestroy() {
		if(progressDialog.isShowing())
			progressDialog.dismiss();
		super.onDestroy();
	}
	
	//requestCode
	private static final int EDIT_ITEM_CODE = 3;
	private static final int ADD_GROUP_CODE = 4;
	private static final int ADD_ITEM_CODE = 5;
	private static final int VIEW_ITEM_CODE = 6;
	private static final int FILE_SELECT_CODE = 7;
	private static final int SYSY_SETTING_CODE = 8;
	private static final int FIRST_ADD_CODE = 9;
	private static final int PROFILE_SETTING_CODE = 10;
	
	//backup and recovery tag
	private static final String BR_TAG_ITEM = "item";
	private static final String BR_TAG_GROUP = "group";
	private static final String BR_TAG_SETTING = "setting";
}
