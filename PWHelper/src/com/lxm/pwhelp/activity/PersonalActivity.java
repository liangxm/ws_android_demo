package com.lxm.pwhelp.activity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.lxm.pwhelp.R;
import com.lxm.pwhelp.bean.PWSetting;
import com.lxm.pwhelp.custom.EmailDialog;
import com.lxm.pwhelp.dao.PWSettingDao;
import com.lxm.pwhelp.utils.Conver;
import com.lxm.pwhelp.utils.SharedPreferencesUtils;
import com.lxm.pwhelp.utils.Tools;
/**
 * Personal information page
 * @author Listener
 * @version 2015-6-12 16:47:08
 */
public class PersonalActivity extends Activity implements View.OnClickListener {
	
	private ImageView touxiang_picture;
	private TextView nickname_value;
	private List<PWSetting> setting;
	private PWSettingDao pwSettingDao;
	private boolean isOn;
	
	private String nickname;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_user_info);
		findViewById(R.id.User_Info_Return).setOnClickListener(this);
		findViewById(R.id.touxiang_line).setOnClickListener(this);
		findViewById(R.id.nickname_line).setOnClickListener(this);
		initView();
	}
	
	private void initView(){
		pwSettingDao = new PWSettingDao(this);
		touxiang_picture = (ImageView) findViewById(R.id.touxiang_picture);
		nickname_value = (TextView) findViewById(R.id.nickname_value);
		touxiang_picture.setMaxWidth(Conver.dip2px(this, 250));
		touxiang_picture.setMaxHeight(Conver.dip2px(this, 250));
		touxiang_picture.setPadding(Conver.dip2px(this, 15), Conver.dip2px(this, 15), Conver.dip2px(this, 15), Conver.dip2px(this, 15));
		setting = pwSettingDao.getSettingByName("pw_nickname");
		if (setting != null && setting.size() > 0) {
			isOn=true;
			nickname_value.setText(setting.get(0).getSetting_value());
		}
		String imageUri = String.valueOf(SharedPreferencesUtils.getParam(this, SharedPreferencesUtils.PHOTO_PATH, new String()));
		File file = new File(imageUri);
		if(imageUri!=null&&file.exists()){
			Uri uri = Uri.fromFile(file);
			touxiang_picture.setImageURI(uri);
		}
	}

	@Override
	public void onClick(View item) {
		switch(item.getId()){
		case R.id.User_Info_Return:
			Intent intent1 = new Intent(this,MainActivity.class);
			setResult(RESULT_OK, intent1);
			finish();
			break;
		case R.id.touxiang_line:
			showDialog();
			break;
		case R.id.nickname_line:
			String title = null;
			if (setting != null && setting.size() > 0) {
				title = "修改你的昵称";
			} else {
				title = "请输入一个酷炫昵称";
			}

			final EmailDialog dialog = new EmailDialog(this,
					title);
			dialog.setOnPositiveListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					nickname = dialog.getEditText().getText()
							.toString();
					if(nickname!=null&&nickname.trim().length()>0){
						PWSetting pwSetting = new PWSetting();
						if (isOn) {
							pwSetting.setSetting_id(setting.get(0)
									.getSetting_id());
						}
						pwSetting.setSetting_name("pw_nickname");
						pwSetting.setSetting_value(nickname);
						CreateOrUpdateStatus status = pwSettingDao
								.createOrUpdate(pwSetting);
						if (status.isCreated() || status.isUpdated()) {
							Tools.showSucessDialog(PersonalActivity.this,
									"更新成功", "个人昵称成功,返回！", listener);
							SharedPreferencesUtils.setParam(PersonalActivity.this, SharedPreferencesUtils.NICK_NAME, nickname);
							dialog.dismiss();
						}
					}else{
						Tools.showToast(PersonalActivity.this, "请输入一个有效的昵称！");
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
			nickname_value.setText(nickname);
			dialog.dismiss();
		}
	};
	
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
					Toast.makeText(PersonalActivity.this, "未找到存储卡，无法存储照片!", Toast.LENGTH_LONG).show();
				}
				break;
			case RESIZE_REQUEST_CODE:
				if (data != null) {
					showResizeImage(data);
				}
				break;
			}
		}
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
		intent.putExtra("outputX", Conver.dip2px(this, 120));
		intent.putExtra("outputY", Conver.dip2px(this, 120));
		intent.putExtra("scale", true);
		intent.putExtra("return-data", true);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		startActivityForResult(intent, RESIZE_REQUEST_CODE);
	}

	private void showResizeImage(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			savePic(photo);
			Drawable drawable = new BitmapDrawable(this.getResources(),photo);
			touxiang_picture.setImageDrawable(drawable);
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
			Toast.makeText(PersonalActivity.this, "无法创建SD卡目录,图片无法保存", Toast.LENGTH_LONG).show();
		}
		try {
        	BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tempImgPath));
        	photo.compress(Bitmap.CompressFormat.JPEG, 75, bos);// (0 - 100)压缩文件  
        	SharedPreferencesUtils.setParam(PersonalActivity.this, SharedPreferencesUtils.PHOTO_PATH, tempImgPath);
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
    
    //requestCode
  	private static final int IMAGE_REQUEST_CODE = 0;
  	private static final int CAMERA_REQUEST_CODE = 1;
  	private static final int RESIZE_REQUEST_CODE = 2;
  	private static final String IMAGE_FILE_NAME = "header.jpg";
}