/**
 * Generated by smali2java 1.0.0.558
 * Copyright (C) 2013 Hensence.com
 */

package com.lxm.pwhelp.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "pw_item")
public class PWItem {

	@DatabaseField(generatedId = true)
	private int item_id;
	@DatabaseField
	private String item_name;
	@DatabaseField
	private String item_username;
	@DatabaseField
	private String item_password;
	@DatabaseField
	private String item_type;
	@DatabaseField
	private String item_url;
	@DatabaseField
	private String item_comment;
	@DatabaseField
	private String question1;
	@DatabaseField
	private String question2;
	@DatabaseField
	private String modified;
	@DatabaseField
	private String created;
	@DatabaseField
	private boolean deleted;

	public PWItem() {
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(item_id + ";");
		sb.append(item_name + ";");
		sb.append(item_type + ";");
		sb.append(item_username + ";");
		sb.append(item_password + ";");
		return sb.toString();
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public int getItem_id() {
		return item_id;
	}

	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getItem_password() {
		return item_password;
	}

	public void setItem_password(String item_password) {
		this.item_password = item_password;
	}

	public String getItem_type() {
		return item_type;
	}

	public void setItem_type(String item_type) {
		this.item_type = item_type;
	}

	public String getItem_url() {
		return item_url;
	}

	public void setItem_url(String item_url) {
		this.item_url = item_url;
	}

	public String getItem_comment() {
		return item_comment;
	}

	public void setItem_comment(String item_comment) {
		this.item_comment = item_comment;
	}

	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}

	public String getQuestion1() {
		return question1;
	}

	public void setQuestion1(String question1) {
		this.question1 = question1;
	}

	public String getQuestion2() {
		return question2;
	}

	public void setQuestion2(String question2) {
		this.question2 = question2;
	}

	public String getItem_username() {
		return item_username;
	}

	public void setItem_username(String item_username) {
		this.item_username = item_username;
	}

}
