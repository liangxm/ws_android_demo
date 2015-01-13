package com.cms.projectsettings;

import android.os.Environment;

public class ProjectSettings {
	public static String PATH=Environment.getExternalStorageDirectory().getPath()+"/sycr/image/";
	//数据库连接
	public static final int SOCKET_CONNECTION_TIMEOUT=1000*5;//请求时间
	public static final int WAIT_DATA_TIMEOUT=1000*5;//等候时间
	
	
	//sycr api
	public static final String CMSACCOUNT="lxm";
	public static final String CMSACCOUNTPWD="lxm";
	
	//socket 请求返回码
	public static final int SUCCESSED = 1;//请求成功
	public static final int DOWN_PHOTO_SUCCESSED = 2;//请求成功
	public static final int VOUCHER_EXPIRED = 100;
	public static final int VOUCHER_USED = 101;
	public static final int CARD_BLOCKED = 105;//卡片被锁住
	public static final int CARD_ERASED = 106;//卡片已经被格式化
	public static final int CARD_NOT_REGISTERED = 107;//卡片没有注册
	public static final int VOUCHER_INACTIVE = 108;
	public static final int CARD_INACTIVE = 115;//卡片没有被激活
	public static final int ALREADY_RENT_BOAT = 116;
	public static final int BOAT_NOT_AVAILABLE = 117;
	public static final int DONT_RENT_BOAT = 118;
	public static final int CARD_NOT_FOUND = 900;//卡片没找到
	public static final int VOUCHER_NOT_FOUND = 901;
	public static final int POS_NOT_FOUND = 904;//设备没有注册
	public static final int SYSTEM_ERROR = 1000;//数据操作失败
	public static final int PUSH_PIC = 1001;
	public static final int UNKNOWN_ERROR = 10000;//未知错误
	public static final int TAG_PICS = 10001;//返回类型为PICS（写图片到本地）
	public static final int CONNECTION_TIMED_OUT=10002;//连接超时
	public static final int DATA_STREAM_READ_ERROR=10003;//数据库读取错误
	public static final String Version="1.0.1.6";
}
