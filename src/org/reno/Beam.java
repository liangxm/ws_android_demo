package org.reno;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.nfc.NfcAdapter;
import android.nfc.tech.MifareClassic;
import android.os.Bundle;
import android.widget.TextView;

import com.cms.nfc.MifareUltralightTagTester;
import com.cms.util.CardMapLayer;

public class Beam extends Activity {
	private NfcAdapter nfcAdapter;
	private TextView promt;
	private PendingIntent mPendingIntent;
	private IntentFilter[] mFilters;
	private String[][] mTechLists;
	private CardMapLayer cardMapLayer;
	private String mfgID;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		promt = (TextView) findViewById(R.id.promt);
		// 获取默认的NFC控制器
		nfcAdapter = NfcAdapter.getDefaultAdapter(this);
		if (nfcAdapter == null) {
			promt.setText("设备不支持NFC！");
			finish();
			return;
		}
		if (!nfcAdapter.isEnabled()) {
			promt.setText("请在系统设置中先启用NFC功能！");
			finish();
			return;
		}
		
		//if (isNFCAvailable()) {
		mPendingIntent = PendingIntent.getActivity(this, 0,new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		IntentFilter techIntent = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
		try {
			techIntent.addDataType("*/*");
		} catch (MalformedMimeTypeException e) {
			throw new RuntimeException("fail", e);
		}
		mFilters = new IntentFilter[] { techIntent };
		// 程序可处理的tech类型
		mTechLists = new String[][] { new String[] { MifareClassic.class.getName() } };
		//}
	}

	@Override
	protected void onResume() {
		System.out.println("OnResume");
		super.onResume();
		nfcAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters, mTechLists); 
		//得到是否检测到ACTION_TECH_DISCOVERED触发
		if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction())) {
			//处理该intent
			System.out.println("OnResume=IF");
			processIntent(getIntent());
		}
	}
	//字符序列转换为16进制字符串
	private String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("0x");
		if (src == null || src.length <= 0) {
			return null;
		}
		char[] buffer = new char[2];
		for (int i = 0; i < src.length; i++) {
			buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
			buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
			System.out.println(buffer);
			stringBuilder.append(buffer);
		}
		return stringBuilder.toString();
	}

	/**
	 * Parses the NDEF Message from the intent and prints to the TextView
	 */
	private void processIntent(Intent intent) {
		MifareUltralightTagTester mut = new MifareUltralightTagTester(this,intent);
		String pageData="";
		if(mut.isNFCAvailable())
			pageData=mut.readTag(26);
		promt.setText("pageData:["+pageData+"]");
		//NfcAControl nfc = new NfcAControl(this,null, intent);
		//if (nfc.isNFCAvailable()) 
		//	mfgID = nfc.readMFGID();
		//promt.setText("mfgID:"+mfgID);
		
		/*System.out.println("processIntent");
		MifareClassicControl aControl=new MifareClassicControl(intent);
		if(null!=aControl.readCardNumberID() && !"".equals(aControl.readCardNumberID())){
			cardMapLayer=aControl.readCMSCardAll();
			mfgID=aControl.readMFGID();
			LogUtil.d("cardValue  "+cardMapLayer);
			LogUtil.d("mfgID  "+mfgID);
			promt.setText(aControl.readCardNumberID());
		}else{
			promt.setText("此卡为空卡！");
		}
		//取出封装在intent中的TAG
		//Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		System.out.println("Tag:"+tagFromIntent.toString());
		for (String tech : tagFromIntent.getTechList()) {
			System.out.println(tech);
		}
		boolean auth = false;
		//读取TAG
		MifareClassic mfc = MifareClassic.get(tagFromIntent);
		try {
			String metaInfo = "";
			//Enable I/O operations to the tag from this TagTechnology object.
			mfc.connect();
			int type = mfc.getType();//获取TAG的类型
			int sectorCount = mfc.getSectorCount();//获取TAG中包含的扇区数
			String typeS = "";
			System.out.println("type:"+type);
			switch (type) {
			case MifareClassic.TYPE_CLASSIC:
				typeS = "TYPE_CLASSIC";
				break;
			case MifareClassic.TYPE_PLUS:
				typeS = "TYPE_PLUS";
				break;
			case MifareClassic.TYPE_PRO:
				typeS = "TYPE_PRO";
				break;
			case MifareClassic.TYPE_UNKNOWN:
				typeS = "TYPE_UNKNOWN";
				break;
			}
			metaInfo += "卡片类型：" + typeS + "\n共" + sectorCount + "个扇区\n共"
					+ mfc.getBlockCount() + "个块\n存储空间: " + mfc.getSize() + "B\n";
			for (int j = 0; j < sectorCount; j++) {
				//Authenticate a sector with key A.
				auth = mfc.authenticateSectorWithKeyA(j,
						MifareClassic.KEY_DEFAULT);
				int bCount;
				int bIndex;
				if (auth) {
					metaInfo += "Sector " + j + ":验证成功\n";
					// 读取扇区中的块
					bCount = mfc.getBlockCountInSector(j);
					bIndex = mfc.sectorToBlock(j);
					for (int i = 0; i < bCount; i++) {
						byte[] data = mfc.readBlock(bIndex);
						metaInfo += "Block " + bIndex + " : "
								+ bytesToHexString(data) + "\n";
						bIndex++;
					}
				} else {
					metaInfo += "Sector " + j + ":验证失败\n";
				}
			}
			promt.setText(metaInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
}
