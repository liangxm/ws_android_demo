package com.lxm.pwhelp.utils;
/**
 * group type enum
 * @author lxm
 * @version 2015-6-6 11:57:28
 */
public enum GroupType {

	Type_Default("默认分组"), Type_Bank("银行卡密码"), Type_Web("网站密码"), Type_WeiBo(
			"微博密码"), Type_QQ("QQ密码"), Type_Email("邮箱密码"), Type_Alipay("支付宝密码");

	private final String type;

	private GroupType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
}
