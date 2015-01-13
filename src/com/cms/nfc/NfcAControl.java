package com.cms.nfc;

import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcA;
import android.os.Handler;

import com.cms.util.ByteConvert;
import com.cms.util.LogUtil;

public class NfcAControl {


	NfcAdapter nfcAdapter;
	Context context;
	Handler handler;
	boolean auth = false;
	NfcA mfc;
	Tag tagFromIntent;

	public NfcAControl(Context context, Handler handler, Intent intent) {
		this.context = context;
		this.handler = handler;
		nfcAdapter = NfcAdapter.getDefaultAdapter(context);
		tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		
		for (String tech : tagFromIntent.getTechList()) {
			LogUtil.d("NfcAControl: tech" + tech);
		}
		
		mfc = NfcA.get(tagFromIntent);
		
	}
	
	public String readMFGID(){
		return String.valueOf(ByteConvert.byte2long4(tagFromIntent.getId(), 0, false));
	}
	
	public static boolean isEnable(Context context){
		NfcAdapter nfc = NfcAdapter.getDefaultAdapter(context);
		if(null==nfc){
			return false;
		}
		
		if(nfc.isEnabled()){
			return true;
		}else{
			return false;
		}
		
	}
	
	public boolean isNFCAvailable() {
		if (nfcAdapter == null) {
			return false;
		}

		if (!nfcAdapter.isEnabled()) {
			return false;
		}

		return true;
	}

	public boolean connect() {
		try {
			mfc.connect();
		} catch (IOException e) {
			LogUtil.d("NfcAControl: nfc connect fail");
			return false;
		}

		return true;
	}

	public boolean close() {
		try {
			mfc.close();
		} catch (IOException e) {
			LogUtil.d("NfcAControl: nfc close fail");
			return false;
		}

		return true;
	}

}
