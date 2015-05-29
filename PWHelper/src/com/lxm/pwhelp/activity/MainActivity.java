/**
 * Generated by smali2java 1.0.0.558
 * Copyright (C) 2013 Hensence.com
 */

package com.lxm.pwhelp.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
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
import com.lxm.pwhelp.R;
import com.lxm.pwhelp.adapter.LazyAdapter;
import com.lxm.pwhelp.adapter.MyAdapter;
import com.lxm.pwhelp.bean.PWGroup;
import com.lxm.pwhelp.bean.PWItem;
import com.lxm.pwhelp.bean.PWSetting;
import com.lxm.pwhelp.bean.SimpleData;
import com.lxm.pwhelp.custom.EmailDialog;
import com.lxm.pwhelp.dao.PWGroupDao;
import com.lxm.pwhelp.dao.PWItemDao;
import com.lxm.pwhelp.dao.PWSettingDao;
import com.lxm.pwhelp.view.NoScrollViewPager;
import com.nineoldandroids.util.Conver;

public class MainActivity extends Activity implements View.OnClickListener {

	private LazyAdapter adapter;
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
	public Map<String, List<SimpleData>> map;

	private ImageButton mSettingImg;
	private LinearLayout mTabAddress;
	private LinearLayout mTabFrd;
	private LinearLayout mTabSetting;
	private LinearLayout mTabWeiXin;

	private LinearLayout additem;
	private RelativeLayout backupitem;
	private RelativeLayout recovery;
	private RelativeLayout settings;
	private ImageView head_icon;
	
	private LinearLayout noitem;

	private NoScrollViewPager mViewPager;
	private List<View> mViews;
	private ImageButton mWeiXinImg;
	private ArrayList<HashMap<String, String>> songsList;
	private TextView title;

	private ImageView add_group;
	
	private Button no_add_item;

	private PWItemDao itemDao;
	private PWGroupDao groupDao;
	private PWSettingDao pwSettingDao;

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
		settings.setOnClickListener(this);
		add_group.setOnClickListener(this);
		no_add_item.setOnClickListener(this);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			/**
			 * ViewPage左右滑动
			 */
			public void onPageSelected(int arg0) {
				int currentItem = mViewPager.getCurrentItem();
				switch (currentItem) {
				case 0: {
					label1.setTextColor(Color.rgb(115, 215, 107));
					mWeiXinImg.setImageResource(R.drawable.home_noselected);
					title.setText("首页");
					return;
				}
				case 1: {
					label2.setTextColor(Color.rgb(115, 215, 107));
					mAddressImg.setImageResource(R.drawable.pass_selected);
					title.setText("密码分组");
					return;
				}
				case 2: {
					label3.setTextColor(Color.rgb(115, 215, 107));
					mFrdImg.setImageResource(R.drawable.manage_selected);
					title.setText("密码管理");
					return;
				}
				case 3: {
					label4.setTextColor(Color.rgb(115, 215, 107));
					mSettingImg.setImageResource(R.drawable.person_selected);
					title.setText("个人中心");
					break;
				}
				}
			}

			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	private void initView() {
		itemDao = new PWItemDao(this);
		groupDao = new PWGroupDao(this);
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
    	List<PWGroup> groups = groupDao.getGroupAll();
    	String[] groupData = new String[]{"默认分组","银行卡密码","论坛密码","微博密码","QQ密码","邮箱密码"};
    	parent = new ArrayList<String>();
    	map = new HashMap<String, List<SimpleData>>();
    	if(groups.size()==0){
	    	for(String groupStr:groupData){
				groupDao.add(new PWGroup(groupStr,"0",false));
				List<SimpleData> list = new ArrayList<SimpleData>();
				List<PWItem> itemGroups = itemDao.getPWItemByType(groupStr);
				parent.add(groupStr+"("+itemGroups.size()+")");
				for(PWItem item:itemGroups){
					list.add(new SimpleData(item.getItem_type(),item.getItem_username(),item.getItem_password()));
				}
				map.put(groupStr+"("+itemGroups.size()+")", list);
			}
    	}else{
    		for(PWGroup group:groups){
    			List<SimpleData> list = new ArrayList<SimpleData>();
				List<PWItem> itemGroups = itemDao.getPWItemByType(group.getGroup_name());
				parent.add(group.getGroup_name()+"("+itemGroups.size()+")");
				for(PWItem item:itemGroups){
					list.add(new SimpleData(item.getItem_type(),item.getItem_username(),item.getItem_password()));
				}
				map.put(group.getGroup_name()+"("+itemGroups.size()+")", list);
    		}
    	}
    	
    	//initialize the item data
    	List<PWItem> items = itemDao.getPWItemAll();
		switchTheNoItem(items);
		for (PWItem item : items) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("item_id", String.valueOf(item.getItem_id()));
			map.put("item_name", item.getItem_name());
			map.put("item_username", item.getItem_username());
			map.put("item_password", item.getItem_password());
			map.put("item_type", item.getItem_type());
			map.put("item_subtype", String.valueOf(item.getItem_subtype()));
			map.put("item_url", item.getItem_url());
			map.put("item_comment", item.getItem_comment());
			map.put("question1", item.getQuestion1());
			map.put("question2", item.getQuestion2());
			map.put("modified", item.getModified());
			songsList.add(map);
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
		//explistview = (PinnedHeaderExpandableListView) tab02
		//		.findViewById(R.id.explistview);
		mainlistview = (ExpandableListView) tab02.findViewById(R.id.explistview);

		backupitem = (RelativeLayout) tab04.findViewById(R.id.cloud);
		recovery = (RelativeLayout) tab04.findViewById(R.id.recovery);
		settings = (RelativeLayout) tab04.findViewById(R.id.settings);
		head_icon = (ImageView) tab04.findViewById(R.id.head_icon);
		head_icon.setMaxWidth(Conver.dip2px(this, 250));
		head_icon.setMaxHeight(Conver.dip2px(this, 250));
		head_icon.setPadding(Conver.dip2px(this, 15), Conver.dip2px(this, 15), Conver.dip2px(this, 15), Conver.dip2px(this, 15));
		
		add_group = (ImageView) findViewById(R.id.add_group);
		noitem = (LinearLayout) tab01.findViewById(R.id.noitem);
		no_add_item = (Button) tab01.findViewById(R.id.no_add_item);
		lv_list = (SwipeMenuListView) tab01.findViewById(R.id.list1);
		songsList = new ArrayList<HashMap<String, String>>();

		// 设置单个分组展开
		initData();
		mainlistview.setAdapter(new MyAdapter(this));
		adapter = new LazyAdapter(this, songsList);
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
				HashMap<String, String> item = songsList.get(position);
				switch (index) {
				case 0:
					// edit
					edit(item);
					adapter.notifyDataSetChanged();
					break;
				case 1:
					// delete
					delete(item);
					songsList.remove(item);
					adapter.notifyDataSetChanged();
					break;
				default:
					break;
				}
			}
			
		});
		
		//lv_list.setCloseInterpolator(new BounceInterpolator());
		lv_list.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long arg3) {
				HashMap<String, String> song = songsList.get(position);
				Intent intent = new Intent(MainActivity.this, DetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("item_id", song.get("item_id"));
				bundle.putString("item_name", song.get("item_id"));
				bundle.putString("item_type", song.get("item_type"));
				bundle.putString("item_username", song.get("item_username"));
				bundle.putString("item_password", song.get("item_password"));
				bundle.putString("item_subtype", song.get("item_subtype"));
				bundle.putString("item_url", song.get("item_url"));
				bundle.putString("item_comment", song.get("item_comment"));
				bundle.putString("question1", song.get("question1"));
				bundle.putString("question2", song.get("question2"));
				bundle.putString("modified", song.get("modified"));
				bundle.putString("created", song.get("created"));
				intent.putExtras(bundle);
				MainActivity.this.startActivityForResult(intent, 1);
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
			mViewPager.setCurrentItem(0);
			resetImg();
			label1.setTextColor(Color.rgb(115, 215, 107));
			mWeiXinImg.setImageResource(R.drawable.home_selected);
			title.setText("首页");
			return;
		}
		case R.id.id_tab_address: {
			add_group.setVisibility(View.VISIBLE);
			mViewPager.setCurrentItem(1);
			resetImg();
			label2.setTextColor(Color.rgb(115, 215, 107));
			mAddressImg.setImageResource(R.drawable.pass_selected);
			title.setText("密码分组");
			return;
		}
		case R.id.id_tab_frd: {
			add_group.setVisibility(View.GONE);
			mViewPager.setCurrentItem(2);
			resetImg();
			label3.setTextColor(Color.rgb(115, 215, 107));
			mFrdImg.setImageResource(R.drawable.manage_selected);
			title.setText("密码管理");
			break;
		}
		case R.id.id_tab_settings: {
			add_group.setVisibility(View.GONE);
			mViewPager.setCurrentItem(3);
			resetImg();
			label4.setTextColor(Color.rgb(115, 215, 107));
			mSettingImg.setImageResource(R.drawable.person_selected);
			title.setText("个人中心");
			break;
		}
		case R.id.additem: {
			// DialogAddItem();
			Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
			// 打开新的Activity
			startActivityForResult(intent, 1);
			break;
		}
		case R.id.cloud: {
			dialogEmail();
			break;
		}
		case R.id.recovery: {
			showFileChooser();
			break;
		}
		case R.id.add_group: {
			Intent intent = new Intent(MainActivity.this, AddGroupActivity.class);
			startActivityForResult(intent, 1);
			break;
		}
		case R.id.settings: {
			Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
			startActivityForResult(intent, 1);
			break;
		}
		case R.id.no_add_item: {
			Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
			startActivityForResult(intent, 1);
			break;
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
	

	
	private void delete(HashMap<String, String> itemMap){
		PWItem item = new PWItem();
		item.setItem_id(Integer.parseInt(itemMap.get("item_id")));
		item.setItem_name(itemMap.get("item_name"));
		item.setItem_username(itemMap.get("item_username"));
		item.setItem_password(itemMap.get("item_password"));
		item.setItem_type(itemMap.get("item_type"));
		item.setItem_subtype(Integer.parseInt(itemMap.get("item_subtype")));
		item.setItem_url(itemMap.get("item_url"));
		item.setItem_comment(itemMap.get("item_comment"));
		item.setQuestion1(itemMap.get("question1"));
		item.setQuestion2(itemMap.get("question2"));
		item.setModified(Conver.ConverToString(new Date()));
		item.setCreated(itemMap.get("created"));
		item.setDeleted(true);
		itemDao.update(item);
	}
	
	private void edit(HashMap<String, String> itemMap){
		Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("item_id", itemMap.get("item_id"));
		bundle.putString("item_name", itemMap.get("item_id"));
		bundle.putString("item_type", itemMap.get("item_type"));
		bundle.putString("item_username", itemMap.get("item_username"));
		bundle.putString("item_password", itemMap.get("item_password"));
		bundle.putString("item_subtype", itemMap.get("item_subtype"));
		bundle.putString("item_url", itemMap.get("item_url"));
		bundle.putString("item_comment", itemMap.get("item_comment"));
		bundle.putString("question1", itemMap.get("question1"));
		bundle.putString("question2", itemMap.get("question2"));
		bundle.putString("modified", itemMap.get("modified"));
		bundle.putString("created", itemMap.get("created"));
		intent.putExtras(bundle);
		startActivityForResult(intent, 1);
	}

	// 回调方法，从第二个页面回来的时候会执行这个方法
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == Activity.RESULT_OK) {
			// Get the Uri of the selected file
			/*
			 * Uri uri = data.getData(); String url; try { url =
			 * FileUtils.getPath(this, uri); Log.i("ht", "url" + url); String
			 * fileName = url.substring(url.lastIndexOf("/") + 1); Intent intent
			 * = new Intent(this, UploadServices.class);
			 * intent.putExtra("fileName", fileName); intent.putExtra("url",
			 * url); intent.putExtra("type ", ""); intent.putExtra("fuid", "");
			 * intent.putExtra("type", "");
			 * 
			 * startService(intent);
			 * 
			 * } catch (URISyntaxException e) { // TODO Auto-generated catch
			 * block e.printStackTrace(); }
			 */
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
			startActivityForResult(Intent.createChooser(intent, "请选择一个要上传的文件"),
					0);
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
			// final EditText editText = (EditText) dialog.getEditText();
			dialog.setOnPositiveListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// dosomething youself
					dialog.dismiss();
				}
			});
			dialog.setOnNegativeListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// String email = editText.getText().toString();
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
	
	public void dialogBackup(String email){
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setMessage("开始备份数据到"+email);
		builder.setTitle("数据备份");
		builder.setPositiveButton("确认",
		new android.content.DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
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

	@Override
	protected void onResume() {
		List<PWItem> items = itemDao.getPWItemAll();
		if (items.size() != songsList.size()) {
			switchTheNoItem(items);
			songsList.clear();
			for (PWItem item : items) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("item_type", item.getItem_type());
				map.put("item_username", item.getItem_username());
				map.put("item_password", item.getItem_password());
				map.put("item_subtype", String.valueOf(item.getItem_subtype()));
				map.put("item_url", item.getItem_url());
				map.put("item_comment", item.getItem_comment());
				map.put("question1", item.getQuestion1());
				map.put("question2", item.getQuestion2());
				map.put("modified", item.getModified());
				songsList.add(map);
			}
			List<PWGroup> groups = groupDao.getGroupAll();
			parent = new ArrayList<String>();
			map = new HashMap<String, List<SimpleData>>();
			for(PWGroup group:groups){
				List<SimpleData> list = new ArrayList<SimpleData>();
				List<PWItem> itemGroups = itemDao.getPWItemByType(group.getGroup_name());
				parent.add(group.getGroup_name()+"("+itemGroups.size()+")");
				for(PWItem item:itemGroups){
					list.add(new SimpleData(item.getItem_type(),item.getItem_username(),item.getItem_password()));
				}
				map.put(group.getGroup_name()+"("+itemGroups.size()+")", list);
			}
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
		super.onDestroy();
	}

}
