package com.lxm.pwhelp.activity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.lxm.pwhelp.R;
import com.lxm.pwhelp.utils.SharedPreferencesUtils;

public class SettingHeaderActivity extends Activity implements OnClickListener {

	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESIZE_REQUEST_CODE = 2;

	private static final String IMAGE_FILE_NAME = "header.jpg";

	private ImageView mImageHeader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.header_image_layout);
		setupViews();
	}

	private void setupViews() {
		mImageHeader = (ImageView) findViewById(R.id.image_header);
		final Button selectBtn1 = (Button) findViewById(R.id.btn_selectimage);
		final Button selectBtn2 = (Button) findViewById(R.id.btn_takephoto);
		selectBtn1.setOnClickListener(this);
		selectBtn2.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_selectimage:
			Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
			galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
			galleryIntent.setType("image/*");
			startActivityForResult(galleryIntent, IMAGE_REQUEST_CODE);
			break;
		case R.id.btn_takephoto:
			if (isSdcardExisting()) {
				Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
				cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, getImageUri());
				cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
				startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
			} else {
				Toast.makeText(view.getContext(), "请插入sd卡", Toast.LENGTH_LONG);
			}
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {
			return;
		} else {
			switch (requestCode){
			case IMAGE_REQUEST_CODE:
				resizeImage(data.getData());
				break;
			case CAMERA_REQUEST_CODE:
				if (isSdcardExisting()){
					resizeImage(getImageUri());
				} else {
					Toast.makeText(SettingHeaderActivity.this, "未找到存储卡，无法存储照片!", Toast.LENGTH_LONG);
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

	private boolean isSdcardExisting() {
		final String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	private void resizeImage(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, RESIZE_REQUEST_CODE);
	}

	private void showResizeImage(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			savePic(photo);
			Drawable drawable = new BitmapDrawable(photo);
			mImageHeader.setImageDrawable(drawable);
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
			Toast.makeText(SettingHeaderActivity.this, "无法创建SD卡目录,图片无法保存", Toast.LENGTH_LONG).show();
		}
		try {
        	BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tempImgPath));
        	photo.compress(Bitmap.CompressFormat.JPEG, 75, bos);// (0 - 100)压缩文件  
        	SharedPreferencesUtils.setParam(SettingHeaderActivity.this, SharedPreferencesUtils.PHOTO_PATH, tempImgPath);
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

}
