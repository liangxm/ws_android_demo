package com.cms.util;

public class CommonUtil {
	

	public static String removeIndex(String temp) {
		temp = temp.substring(temp.indexOf(".") + 1);
		return temp;
	}

	public static String trimZeroes(String pNumberString) {

		if (pNumberString.equals(""))
			return "000";
		else {
			if (pNumberString.length() > 1)
				while (pNumberString.charAt(0) == '0'
						&& pNumberString.length() > 1)
					pNumberString = pNumberString.substring(1);
		}
		if (pNumberString.length() == 0)
			return "000";
		else
			return pNumberString;
	}

	public static String trimToDecimal(String pValue) {
		return toDecimal(trimZeroes(pValue));

	}

	public static int stringToInt(String value) {
		return Integer.parseInt(value);
	}

	public static byte[] stringToIntByte4(String value) {
		return ByteConvert.int2byte4(Integer.parseInt(value));
	}

	public static byte[] stringToByte2(String value) {
		return ByteConvert.int2byte2(Integer.parseInt(value));
	}

	public static int byte4ToInt(byte[] value) {
		String stringValue = "" + ByteConvert.byte2int4(value);

		return Integer.parseInt(stringValue);
	}

	public static String addPreZeroes(String pAmount, int length) {
		int amtLength = pAmount.length();

		if (amtLength < length) {
			StringBuffer sb = new StringBuffer(length);
			int addZero = length - amtLength;
			for (int i = 0; i < addZero; i++) {
				sb.append('0');
			}
			sb.append(pAmount);
			return sb.toString();
		}
		else {
			return pAmount;
		}
	}

	public static String addPreSpaces(int amount, int charSpace) {
		return addPreSpaces(String.valueOf(amount), charSpace);
	}

	public static String addPreSpaces(String pAmount, int charSpace) {

		if (pAmount.indexOf("-") > 0)
			charSpace = charSpace - 1;
		int amtLength = pAmount.length();
		if (amtLength < charSpace) {
			int addZero = charSpace - amtLength;
			for (int i = 0; i < addZero; i++) {
				pAmount = " " + pAmount;
			}

		}
		return pAmount;

	}

	public static String addStringAsNumber(String sValueA, String sValueB) {
		int iValueA = Integer.parseInt(sValueA);
		int iValueB = Integer.parseInt(sValueB);
		int iTotal = iValueA + iValueB;
		return "" + iTotal;

	}

	public static String subtractStringAsNumber(String sValueA, String sValueB) {
		int iValueA = Integer.parseInt(sValueA);
		int iValueB = Integer.parseInt(sValueB);
		int iTotal = iValueA - iValueB;
		return "" + iTotal;

	}

	public static boolean isNegative(String value) {
		if (value.indexOf("-") >= 0) {
			return true;
		}
		else
			return false;

	}

	public static String cutDecimals(String value) {
		if (value.length() > 2)
			return value.substring(0, value.length() - 2);
		else
			return "0";

	}

	public static int cutDecimalsToInt(int value) {
		return Integer.parseInt(cutDecimals("" + value));

	}

	public static int calculatePointsCost(int purchaseAmount) {
		try {
			int pointCost = purchaseAmount * 100;
			return cutDecimalsToInt(pointCost);
		}
		catch (Exception e) {
			return 0;
		}
	}

	public static String cutDecimals(int value) {
		return cutDecimals("" + value);

	}

	public static String toDecimal(int value) {
		int jiaoFen = value % 100;
		if (jiaoFen < 10) {
			return String.valueOf(value / 100) + ".0" + String.valueOf(jiaoFen);
		}
		else {
			return String.valueOf(value / 100) + "." + String.valueOf(jiaoFen);
		}
	}

	public static String toDecimal(String pString) {
		try {
			if (pString == null) {
				return "null";
			}
			if (pString.equals("0")) {
				return "0.00";
			}

			boolean isNegative = isNegative(pString);
			if (isNegative) {
				pString = removeChar(pString, '-');
			}
			if (pString.length() < 3) {
				int zeroLength = 3 - pString.length();
				for (int i = 0; i < zeroLength; i++) {
					pString = "0" + pString;
				}
			}
			pString = pString.substring(0, pString.length() - 2) + "."
					+ pString.substring(pString.length() - 2);
			if (isNegative) {

				return "-" + pString;
			}
			else
				return pString;

		}
		catch (Exception e) {
			return "null";
		}
	}

	public static String formatDate(String date) {
		try {
			return date.substring(0, 4) + "/" + date.substring(4, 6) + "/"
					+ date.substring(6);
		}
		catch (Exception e) {
			return "null";
		}
	}

	public static String formatTime(String time) {
		try {
			if (time == null)
				return "null";

			else

				return time.substring(0, 2) + ":" + time.substring(2, 4) + ":"
						+ time.substring(4);
		}
		catch (Exception e) {
			return "null";
		}
	}

	public static String formatHHMMTime(String time) {
		try {
			if (time == null)
				return "null";
			else
				return time.substring(0, 2) + ":" + time.substring(2, 4);
		}
		catch (Exception e) {
			return "null";
		}
	}

	public static String addCenterSpace(String pValue) {
		try {
			int space = (32 - pValue.length()) / 2;
			for (int i = 0; i < space; i++) {

				pValue = " " + pValue;
			}
			return pValue;
		}
		catch (Exception e) {
			return "null";
		}
	}

	// TODO needn't to copy always.
	public static byte[] buildByteData(byte[][] pAuditData) {
		int totalLength = 0;
		for (int i = 0; i < pAuditData.length; i++) {
			totalLength += pAuditData[i].length;
		}
		byte[] finalAuditData = new byte[totalLength];
		int currentPosition = 0;
		for (int i = 0; i < pAuditData.length; i++) {

			System.arraycopy(pAuditData[i], 0, finalAuditData, currentPosition,
					pAuditData[i].length);
			currentPosition += pAuditData[i].length;
		}
		return finalAuditData;
	}

	public static String getFileName(String serialNumber, String date,
			String time) {

		return serialNumber + date + time + ".csv";

	}

	public static String removeChar(String s, char c) {
		String r = "";
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) != c)
				r += s.charAt(i);
		}
		return r;
	}

	public static String formatToIP(String pString) {
		if (pString.length() == 12)
			return pString.substring(0, 3) + "." + pString.substring(3, 6)
					+ "." + pString.substring(6, 9) + "."
					+ pString.substring(9);
		else
			return "000.000.000.000";

	}

	public static String[] addMissingElements(String[] arrayData, int dataSize) {
		String[] completeArray = new String[dataSize];
		System.arraycopy(arrayData, 0, completeArray, 0, arrayData.length);
		// int missingElements = dataSize - arrayData.length;
		for (int i = arrayData.length; i < dataSize; i++)
			completeArray[i] = "0";
		return completeArray;
	}

	public static String maskData(String data, String mask, int length) {
		try {
			data = data.substring(length, data.length());
			String maskString = "";
			for (int i = 1; i < length; i++) {
				maskString = maskString + mask;
			}

			return maskString + data;
		}
		catch (Exception e) {
			return "null";
		}
	}

	public static String getSpacedString(int pValue1, int pValue2, int pValue3) {
		String value1 = addPreSpaces(toDecimal(pValue1), 7);
		String value2 = addPreSpaces(toDecimal(pValue2), 8);
		String value3 = addPreSpaces(toDecimal(pValue3), 8);
		return value1 + "" + value2 + "" + value3;
	}

	public static String getSpacedString(String pValue1, String pValue2,
			String pValue3) {
		String value1 = pValue1;
		String value2 = pValue2;
		String value3 = pValue3;

		value1 = addPreSpaces(toDecimal(value1), 7);
		value2 = addPreSpaces(toDecimal(value2), 8);
		value3 = addPreSpaces(toDecimal(value3), 8);
		return value1 + "" + value2 + "" + value3;
	}

	public static String getSpacedStringPoints(int pValue1, int pValue2,
			int pValue3) {
		String value1 = addPreSpaces(pValue1, 7);
		String value2 = addPreSpaces(pValue2, 8);
		String value3 = addPreSpaces(pValue3, 8);
		return value1 + "" + value2 + "" + value3;
	}

	//	public String removeDot(String value){
	//        if(value.indexOf('.') >0)
	//		    return value.substring(0,value.indexOf('.'))+ value.substring(value.indexOf('.')+1);
	//        else 
	//        	return value;
	//	}
	public static int countLetters(String dataString, char searchChar) {

		int counter = 0;

		for (int i = 0; i < dataString.length(); i++) {

			if (dataString.charAt(i) == searchChar)
				counter++;
		}

		return counter;
	}

	public static String addPostChars(String data, int stringLength) {
		if (data.length() < stringLength) {
			int spaceAmount = stringLength - data.length();
			for (int i = 0; i < spaceAmount; i++) {
				data = data + " ";
			}
		}
		return data;

	}

	public static String addEmptyZero(String data, int stringLength) {
		if (data.length() < stringLength) {
			int spaceAmount = stringLength - data.length();
			for (int i = 0; i < spaceAmount; i++) {
				data = data + "0";
			}
		}
		return data;

	}

	public static int toInt(String s) {
		return Integer.parseInt(s);
	}

}
