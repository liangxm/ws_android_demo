package com.lxm.pwhelp.bean;

public class SimpleData {

	private String item_type;
	private String item_username;
	private String item_password;

	public SimpleData() {
	}

	public SimpleData(String item_type, String item_username,
			String item_password) {
		super();
		this.item_type = item_type;
		this.item_username = item_username;
		this.item_password = item_password;
	}

	public String getItem_type() {
		return item_type;
	}

	public void setItem_type(String item_type) {
		this.item_type = item_type;
	}

	public String getItem_username() {
		return item_username;
	}

	public void setItem_username(String item_username) {
		this.item_username = item_username;
	}

	public String getItem_password() {
		return item_password;
	}

	public void setItem_password(String item_password) {
		this.item_password = item_password;
	}

}
