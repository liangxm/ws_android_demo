package org.reno;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareUltralight;
import android.os.Bundle;
import android.widget.Toast;

import com.cms.nfc.MifareUltralightTagTester;
/**
 * 
 */
public class ForegroundDispatch extends Activity {
	
	private NfcAdapter mAdapter;
	private IntentFilter[] intentFiltersArray;
	private String[][] techListsArray;
	private PendingIntent pendingIntent;
	private int type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// 得到跳转到该Activity的Intent对象
        Intent intent = getIntent();
        type = intent.getIntExtra("org.reno.cardType", -1);
		// 获取默认的NFC控制器
		mAdapter = NfcAdapter.getDefaultAdapter(this);
		if (mAdapter == null) {
			finish();
			return;
		}
		if (!mAdapter.isEnabled()) {
			finish();
			return;
		}
		
		pendingIntent = PendingIntent.getActivity(
			    this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		
		IntentFilter techIntent = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        try {
        	techIntent.addDataType("*/*");    /* Handles all MIME based dispatches.
                                           You should specify only the ones that you need. */
        }
        catch (MalformedMimeTypeException e) {
            throw new RuntimeException("fail", e);
        }
        intentFiltersArray = new IntentFilter[] {
        		techIntent,
        };
        techListsArray = new String[][] { new String[] { MifareUltralight.class.getName() } };
	}
	
	@Override
	public void onPause() {
		super.onPause();
		mAdapter.disableForegroundDispatch(this);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mAdapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techListsArray);
	}
	
	@Override
	public void onNewIntent(Intent intent) {
		//Toast.makeText(this, "onNewIntent"+getIntent().getAction(), Toast.LENGTH_SHORT).show();
		//Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		//do something with tagFromIntent
		if ("org.reno.second".equals(getIntent().getAction())) {
			//处理该intent
			processIntent(intent);
		}
	}
	
	//handle the tag
	private void processIntent(Intent intent) {
		MifareUltralightTagTester mut = new MifareUltralightTagTester(this,intent);
		String pageData="";
		if(mut.isNFCAvailable())
			pageData=mut.readTag(26);
		Toast.makeText(this, "读卡成功！type:["+type+"]", Toast.LENGTH_SHORT).show();
	}
}
