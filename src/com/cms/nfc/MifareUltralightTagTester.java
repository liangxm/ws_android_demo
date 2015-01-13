package com.cms.nfc;
import java.io.IOException;
import java.nio.charset.Charset;

import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareUltralight;
import android.util.Log;

import com.cms.security.DESEncryptControl;
import com.cms.util.ByteConvert;
import com.cms.util.CommonUtil;
 
public class MifareUltralightTagTester{
 
    private static final String TAG = MifareUltralightTagTester.class.getSimpleName();
    public static final byte[] KEY = "1234567890123456".getBytes();
    
    private NfcAdapter nfcAdapter;
    private Context context;
    private Tag tagFromIntent;
    
    public MifareUltralightTagTester(Context context, Intent intent){
    	this.context = context;
    	nfcAdapter = NfcAdapter.getDefaultAdapter(context);
		tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
    }
 
    public void writeTag(Tag tag,String tagText){
        MifareUltralight ultralight =MifareUltralight.get(tag);
        try{
            ultralight.connect();
            ultralight.writePage(4,"abcd".getBytes(Charset.forName("US-ASCII")));
            ultralight.writePage(5,"efgh".getBytes(Charset.forName("US-ASCII")));
            ultralight.writePage(6,"ijkl".getBytes(Charset.forName("US-ASCII")));
            ultralight.writePage(7,"mnop".getBytes(Charset.forName("US-ASCII")));
        }catch(IOException e){
            Log.e(TAG,"IOException while closing MifareUltralight...", e);
        }finally{
            try{
                ultralight.close();
            }catch(IOException e){
                Log.e(TAG,"IOException while closing MifareUltralight...", e);
            }
        }
    }
 
    public String readTag(int pageNo){
        MifareUltralight mifare =MifareUltralight.get(tagFromIntent);
        try{
            mifare.connect();
            byte[] payload = mifare.readPages(pageNo);
            
            payload = DESEncryptControl.Decrypt(KEY, payload);
            
            String page1 = String.valueOf(ByteConvert.byte2int4(payload, 0));
			String page2 = String.valueOf(ByteConvert.byte2int4(payload, 4));
            String page3 = String.valueOf(ByteConvert.byte2int4(payload, 8));
            String page4 = String.valueOf(ByteConvert.byte2int4(payload, 12));
            return "page1:"+page1+" page2:"+page2+" page3:"+page3+" page4:"+page4;
            //return new String(payload);
        }catch(IOException e){
            Log.e(TAG,"IOException while writing MifareUltralight message...", e);
        }finally{
            if(mifare !=null){
               try{
                   mifare.close();
               }
               catch(IOException e){
                   Log.e(TAG,"Error closing tag...", e);
               }
            }
        }
        return null;
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
}