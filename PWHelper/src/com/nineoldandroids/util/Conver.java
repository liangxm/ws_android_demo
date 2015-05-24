package com.nineoldandroids.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Conver {

	// 把日期转为字符串
	public static String ConverToString(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
		return df.format(date);
	}

	// 把字符串转为日期
	public static Date ConverToDate(String strDate) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
		return df.parse(strDate);
	}

}
