/**
 * Generated by smali2java 1.0.0.558
 * Copyright (C) 2013 Hensence.com
 */

package com.lxm.pwhelp.activity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
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
import com.lxm.pwhelp.R;
import com.lxm.pwhelp.adapter.MyAdapter;
import com.lxm.pwhelp.adapter.PWItemAdapter;
import com.lxm.pwhelp.bean.PWGroup;
import com.lxm.pwhelp.bean.PWItem;
import com.lxm.pwhelp.bean.PWSetting;
import com.lxm.pwhelp.custom.CircleImageView;
import com.lxm.pwhelp.custom.EmailDialog;
import com.lxm.pwhelp.dao.PWGroupDao;
import com.lxm.pwhelp.dao.PWItemDao;
import com.lxm.pwhelp.dao.PWSettingDao;
import com.lxm.pwhelp.utils.Conver;
import com.lxm.pwhelp.utils.SharedPreferencesUtils;
import com.lxm.pwhelp.utils.Tools;
import com.lxm.pwhelp.view.NoScrollViewPager;

public class MainActivity extends Activity implements View.OnClickListener {

	private PWItemAdapter adapter;
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
		head_icon.setOnClickListener(this);
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
    	map = new HashMap<String, List<PWItem>>();
    	if(groups.size()==0){
	    	for(String groupStr:groupData){
				groupDao.createOrUpdate(new PWGroup(groupStr,"0",false));
				List<PWItem> list = new ArrayList<PWItem>();
				List<PWItem> itemGroups = itemDao.getPWItemByType(groupStr);
				parent.add(groupStr+"("+itemGroups.size()+")");
				for(PWItem item:itemGroups){
					list.add(item);
				}
				map.put(groupStr+"("+itemGroups.size()+")", list);
			}
    	}else{
    		for(PWGroup group:groups){
    			List<PWItem> list = new ArrayList<PWItem>();
				List<PWItem> itemGroups = itemDao.getPWItemByType(group.getGroup_name());
				parent.add(group.getGroup_name()+"("+itemGroups.size()+")");
				for(PWItem item:itemGroups){
					list.add(item);
				}
				map.put(group.getGroup_name()+"("+itemGroups.size()+")", list);
    		}
    	}
    	
    	//initialize the item data
    	List<PWItem> items = itemDao.getPWItemAll();
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
		mainlistview = (ExpandableListView) tab02.findViewById(R.id.explistview);

		backupitem = (RelativeLayout) tab04.findViewById(R.id.cloud);
		recovery = (RelativeLayout) tab04.findViewById(R.id.recovery);
		settings = (RelativeLayout) tab04.findViewById(R.id.settings);
		head_icon = (CircleImageView) tab04.findViewById(R.id.head_icon);
		head_icon.setMaxWidth(Conver.dip2px(this, 250));
		head_icon.setMaxHeight(Conver.dip2px(this, 250));
		shodow_head.setPadding(Conver.dip2px(this, 15), Conver.dip2px(this, 15), Conver.dip2px(this, 15), Conver.dip2px(this, 15));
		top_header = (CircleImageView) findViewById(R.id.top_header);
		
		add_group = (ImageView) findViewById(R.id.add_group);
		noitem = (LinearLayout) tab01.findViewById(R.id.noitem);
		no_add_item = (Button) tab01.findViewById(R.id.no_add_item);
		lv_list = (SwipeMenuListView) tab01.findViewById(R.id.list1);
		searchbox = (EditText) tab01.findViewById(R.id.searchbox);
		searchbox.addTextChangedListener(textWatcher);
		itemList = new ArrayList<PWItem>();
		// 设置单个分组展开
		initData();
		mainlistview.setAdapter(new MyAdapter(this));
		mainlistview.setOnChildClickListener(new OnChildClickListener(){
			@Override
			public boolean onChildClick(ExpandableListView parentlist, View view,
					int groupPosition, int childPosition, long id) {
				String key = parent.get(groupPosition);
				PWItem item = map.get(key).get(childPosition);
				Intent intent = new Intent(MainActivity.this, DetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("item", item);
				intent.putExtras(bundle);
				MainActivity.this.startActivityForResult(intent, 1);
				return true;
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
					adapter.notifyDataSetChanged();
					break;
				case 1:
					// delete
					delete(item);
					itemList.remove(item);
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
				PWItem item = itemList.get(position);
				Intent intent = new Intent(MainActivity.this, DetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("item", item);
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
			top_header.setVisibility(View.VISIBLE);
			mViewPager.setCurrentItem(0);
			resetImg();
			label1.setTextColor(Color.rgb(115, 215, 107));
			mWeiXinImg.setImageResource(R.drawable.home_selected);
			title.setText("首页");
			return;
		}
		case R.id.id_tab_address: {
			add_group.setVisibility(View.VISIBLE);
			top_header.setVisibility(View.VISIBLE);
			mViewPager.setCurrentItem(1);
			resetImg();
			label2.setTextColor(Color.rgb(115, 215, 107));
			mAddressImg.setImageResource(R.drawable.pass_selected);
			title.setText("密码分组");
			return;
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
		case R.id.head_icon: {
			showDialog();
			//Intent intent = new Intent(MainActivity.this, SettingHeaderActivity.class);
			//startActivityForResult(intent, 1);
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
	
	private Handler handler = new Handler();
	private Runnable eChanged = new Runnable() {
		@Override
		public void run() {
			String data = searchbox.getText().toString();
			itemList.clear();
			if (data != null && !"".equals(data)) 
				newItemList = itemDao.getPWItemByName(data);
			else
				newItemList = itemDao.getPWItemAll();
			for(PWItem item:newItemList){
				itemList.add(item);
			}
			adapter.notifyDataSetChanged();
		}
	};
	
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
	
	private void delete(PWItem item){
		item.setDeleted(true);
		itemDao.update(item);
	}
	
	private void edit(PWItem item){
		Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("item", item);
		intent.putExtras(bundle);
		startActivityForResult(intent, 1);
	}

	// 回调方法，从第二个页面回来的时候会执行这个方法
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {
			return;
		} else {
			switch (requestCode){
			case IMAGE_REQUEST_CODE:
				resizeImage(data.getData());
				break;
			case CAMERA_REQUEST_CODE:
				if (Tools.hasSdcard()){
					resizeImage(getImageUri());
				} else {
					Toast.makeText(MainActivity.this, "未找到存储卡，无法存储照片!", Toast.LENGTH_LONG).show();
				}
				break;
			case RESIZE_REQUEST_CODE:
				if (data != null) {
					showResizeImage(data);
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
		if (items.size() != itemList.size()) {
			switchTheNoItem(items);
			itemList.clear();
			for (PWItem item : items) {
				itemList.add(item);
			}
			List<PWGroup> groups = groupDao.getGroupAll();
			parent = new ArrayList<String>();
			map = new HashMap<String, List<PWItem>>();
			for(PWGroup group:groups){
				List<PWItem> list = new ArrayList<PWItem>();
				List<PWItem> itemGroups = itemDao.getPWItemByType(group.getGroup_name());
				parent.add(group.getGroup_name()+"("+itemGroups.size()+")");
				for(PWItem item:itemGroups){
					list.add(item);
				}
				map.put(group.getGroup_name()+"("+itemGroups.size()+")", list);
			}
		}
		
		//头像更新
		String imageUri = String.valueOf(SharedPreferencesUtils.getParam(MainActivity.this, SharedPreferencesUtils.PHOTO_PATH, new String()));
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
	
    /** 
     * 显示选择对话框 
     */  
    private void showDialog() {  
        new AlertDialog.Builder(this)  
                .setTitle("设置头像")  
                .setItems(new String[] {"选择本地图片", "拍照"}, new DialogInterface.OnClickListener() {  
                    @Override  
                    public void onClick(DialogInterface dialog, int which) {  
                        switch (which) {  
                        case 0:  
                            Intent intentFromGallery = new Intent();  
                            intentFromGallery.setType("image/*"); // 设置文件类型  
                            intentFromGallery  
                                    .setAction(Intent.ACTION_GET_CONTENT);  
                            startActivityForResult(intentFromGallery,  
                                    IMAGE_REQUEST_CODE);  
                            break;  
                        case 1:  
                            Intent intentFromCapture = new Intent(  
                                    MediaStore.ACTION_IMAGE_CAPTURE);  
                            // 判断存储卡是否可以用，可用进行存储  
                            if (Tools.hasSdcard()) {  
                            	intentFromCapture.putExtra(
										MediaStore.EXTRA_OUTPUT,
										Uri.fromFile(new File(Environment
												.getExternalStorageDirectory(),
												IMAGE_FILE_NAME)));
                            }  
  
                            startActivityForResult(intentFromCapture,CAMERA_REQUEST_CODE);  
                            break;  
                        }  
                    }  
                })  
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {  
  
                    @Override  
                    public void onClick(DialogInterface dialog, int which) {  
                        dialog.dismiss();  
                    }  
                }).show();  
  
    }
    
	private void resizeImage(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 180);
		intent.putExtra("outputY", 180);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, RESIZE_REQUEST_CODE);
	}

	private void showResizeImage(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			savePic(photo);
			Drawable drawable = new BitmapDrawable(this.getResources(),photo);
			head_icon.setImageDrawable(drawable);
		}
	}
	
	private void savePic(Bitmap photo){
    	long l2 = System.currentTimeMillis();
	    String fileName = l2 + ".jpg";
        String tempImgPath = getCacheDir().getAbsolutePath() + "/sysfiles/temp/" + fileName;
        String dir = getDir(tempImgPath);
		File dirFile = new File(dir);
		dirFile.mkdirs();
		if (!dirFile.exists()) {
			Toast.makeText(MainActivity.this, "无法创建SD卡目录,图片无法保存", Toast.LENGTH_LONG).show();
		}
		try {
        	BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tempImgPath));
        	photo.compress(Bitmap.CompressFormat.JPEG, 75, bos);// (0 - 100)压缩文件  
        	SharedPreferencesUtils.setParam(MainActivity.this, SharedPreferencesUtils.PHOTO_PATH, tempImgPath);
        } catch (Exception e) {
			e.printStackTrace();
        }  
    }
	
	public  String getDir(String filePath) {
		int lastSlastPos = filePath.lastIndexOf('/');
		return filePath.substring(0, lastSlastPos);
	}

	private Uri getImageUri() {
		return Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
				IMAGE_FILE_NAME));
	}
	
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESIZE_REQUEST_CODE = 2;
	private static final String IMAGE_FILE_NAME = "header.jpg";
}
