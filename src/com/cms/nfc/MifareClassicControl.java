package com.cms.nfc;

import java.io.IOException;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;

import com.cms.security.DESEncryptControl;
import com.cms.util.ByteConvert;
import com.cms.util.CardMapLayer;
import com.cms.util.LogUtil;

public class MifareClassicControl {
	private Tag tagFromIntent;
	private MifareClassic classic;
	private CardMapLayer cardMap;

	public static final byte[] KEY = "1234567890123456".getBytes();
	private boolean auth = false;
	public static final int READ_MFGID = 0;
	public static final int BLOCK0 = 24;
	public static final int BLOCK1 = 25;
	public static final int BLOCK2 = 26;
	public static final byte[] CMS_SECTOR_KEYA_F2 = new byte[] { (byte) 0x20,
			(byte) 0x12, (byte) 0x03, (byte) 'C', (byte) 'M', (byte) 'S' };

	public MifareClassicControl(Intent intent) {
		tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		// nfc解析代码，主要是解析MifareClassic格式
		if (tagFromIntent != null) {
			for (String tech : tagFromIntent.getTechList()) {
				LogUtil.d(tech);
			}
			classic = MifareClassic.get(tagFromIntent);
		}
	}

	// 尝试去获得每个sector的认证，只有认证通过才能访问
	public boolean authenticateSectorWithKeyA(int sectorIndex, byte[] authKey) {
		try {
			auth = classic.authenticateSectorWithKeyA(sectorIndex, authKey);
			LogUtil.d("auth=" + auth);
		} catch (IOException e) {
			LogUtil.d("auth " + sectorIndex + " fail");
			return false;
		}
		return auth;
	}

	public CardMapLayer readCMSCardAll() {
		if (connect()) {
			boolean b = authenticateSectorWithKeyA(6, CMS_SECTOR_KEYA_F2);
			if (b) {
				int bCount;
				int bIndex;
				// 得到sector中的4个block
				bCount = classic.getBlockCountInSector(6);
				bIndex = classic.sectorToBlock(6);

				LogUtil.d("total blocks=" + bCount);
				LogUtil.d("first block=" + bIndex);

				byte[] data = null;
				cardMap = new CardMapLayer();

				for (int i = 0; i < bCount; i++) {
					try {
						data = classic.readBlock(bIndex);
						LogUtil.d("data  data  " + data);
					} catch (IOException e) {
						LogUtil.d("read block fail");
					}
					if(null!=data){
						data = DESEncryptControl.Decrypt(KEY, data);
						LogUtil.d("data  " + data);
						switch (bIndex) {
						case BLOCK0:
							String cardNumber = "";
							String high = String.valueOf(ByteConvert.byte2int4(
									data, 0));
							String low = String.valueOf(ByteConvert.byte2int4(data,
									4));
	
							if (high.length() == 9) {
								cardNumber = high + low;
							} else {
								cardNumber = addPreZeroes(high, 8)
										+ addPreZeroes(low, 8);
							}
							cardMap.setCardNumber(cardNumber);
							cardMap.setCardID(new String(data, 8, 8));
	
							LogUtil.d("Block 0 data: CardNumber="
									+ cardMap.getCardNumber());
							LogUtil.d("Block 0 data: CardID=" + cardMap.getCardID());
	
							break;
	
						case BLOCK1:
							byte[] moneyValue = new byte[4];
							byte[] points = new byte[4];
							byte[] password = new byte[4];
							byte[] fullCardType = new byte[4];
	
							System.arraycopy(data, 0, moneyValue, 0, 4);
							System.arraycopy(data, 4, points, 0, 4);
							System.arraycopy(data, 8, password, 0, 4);
							System.arraycopy(data, 12, fullCardType, 0, 4);
	
							cardMap.setMoneyValue(ByteConvert.byte2int4(moneyValue));
							cardMap.setPoints(ByteConvert.byte2int4(points));
							cardMap.setPassword(new String(password));
							cardMap.setCardType(ByteConvert.byte2int4(fullCardType));
	
							LogUtil.d("Block 1 data: MoneyValue="
									+ cardMap.getMoneyValue());
							LogUtil.d("Block 1 data: Points=" + cardMap.getPoints());
							LogUtil.d("Block 1 data: Password="
									+ cardMap.getPassword());
							LogUtil.d("Block 1 data: CardType="
									+ cardMap.getCardType());
	
							break;
	
						case BLOCK2:
							byte[] initDate = new byte[4];
							byte[] activeDate = new byte[4];
							byte[] expireDate = new byte[4];
							byte[] initCode = new byte[4];
	
							System.arraycopy(data, 0, initDate, 0, 4);
							System.arraycopy(data, 4, activeDate, 0, 4);
							System.arraycopy(data, 8, expireDate, 0, 4);
							System.arraycopy(data, 12, initCode, 0, 4);
	
							cardMap.setInitDate(""
									+ ByteConvert.byte2int4(initDate));
							cardMap.setActiveDate(""
									+ ByteConvert.byte2int4(activeDate));
							cardMap.setExpireDate(""
									+ ByteConvert.byte2int4(expireDate));
							cardMap.setInitCode(ByteConvert.byte2long4(initCode, 0,
									true));
	
							LogUtil.d("Block 2 data: InitDate="
									+ cardMap.getInitDate());
							LogUtil.d("Block 2 data: ActiveDate="
									+ cardMap.getActiveDate());
							LogUtil.d("Block 2 data: ExpireDate="
									+ cardMap.getExpireDate());
							LogUtil.d("Block 2 data: InitCode="
									+ cardMap.getInitCode());
	
							break;
	
						}
						bIndex++;
					} else {
						LogUtil.d("data是空?");
					}
				}
			} else {
				LogUtil.d("Sector auth fail");
			}
		}
		close();
		LogUtil.d("read all card data done");
		return cardMap;
	}

	public String readMFGID() {
		String mfg = null;
		if (connect()) {
			boolean b = authenticateSectorWithKeyA(0, MifareClassic.KEY_DEFAULT);
			int bCount;
			int bIndex;
			if (b) {
				bCount = classic.getBlockCountInSector(0);
				bIndex = classic.sectorToBlock(0);
				LogUtil.d("total blocks=" + bCount);
				LogUtil.d("first block=" + bIndex);
				byte[] data = null;
				try {
					data = classic.readBlock(bIndex);
				} catch (Exception e) {
					LogUtil.d("mfc read block 0 fail");
				}
				if (data != null) {
					byte[] mfgid = new byte[4];
					System.arraycopy(data, 0, mfgid, 0, 4);
					mfg = String.valueOf(ByteConvert
							.byte2long4(mfgid, 0, false));
					LogUtil.d("mfgid=" + mfg);
				}
			} else {
				LogUtil.d("auth fail");
			}
		}
		close();
		return mfg;
	}

	// 得到cardmember
	public String readCardNumberID() {
		String high = null;
		String low = null;
		if (connect()) {
			// 读取第六个Sector的数�?
			boolean b = authenticateSectorWithKeyA(6, CMS_SECTOR_KEYA_F2);
			int bIndex;
			if (b) {
				// 读取第一个Block的数�?
				bIndex = classic.sectorToBlock(6);
				LogUtil.d("first block=" + bIndex);
				byte[] data = null;
				try {
					data = classic.readBlock(bIndex);
					data = DESEncryptControl.Decrypt(KEY, data);
					high = String.valueOf(ByteConvert.byte2int4(data, 0));
					low = String.valueOf(ByteConvert.byte2int4(data, 4));
				} catch (Exception e) {
					LogUtil.d("mfc read block 0 fail");
				}
			} else {
				LogUtil.d("auth fail");
			}
		}
		close();
		return high + low;
	}

	public boolean connect() {
		try {
			if (classic != null) {
				classic.connect();
			}
		} catch (IOException e) {
			LogUtil.d("nfc connect fail");
			return false;
		}

		return true;
	}

	public boolean close() {
		try {
			classic.close();
		} catch (IOException e) {
			LogUtil.d("nfc close fail");
			return false;
		}

		return true;
	}

	public String addPreZeroes(String pAmount, int length) {
		int amtLength = pAmount.length();

		if (amtLength < length) {
			StringBuffer sb = new StringBuffer(length);
			int addZero = length - amtLength;
			for (int i = 0; i < addZero; i++) {
				sb.append('0');
			}
			sb.append(pAmount);
			return sb.toString();
		} else {
			return pAmount;
		}
	}

}
