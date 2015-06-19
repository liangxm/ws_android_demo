package com.lxm.pwhelp.bean;

import java.io.Serializable;

public class Setting implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int setting_id;
	private String setting_name;
	private String setting_value;
	private String created;
	private boolean deleted;

	public Setting() {}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(setting_id+"|");
		sb.append(setting_name+"|");
		sb.append(setting_value+"|");
		sb.append(created+"|");
		sb.append(deleted);
		return sb.toString();
	}

	public int getSetting_id() {
		return setting_id;
	}

	public void setSetting_id(int setting_id) {
		this.setting_id = setting_id;
	}

	public String getSetting_name() {
		return setting_name;
	}

	public void setSetting_name(String setting_name) {
		this.setting_name = setting_name;
	}

	public String getSetting_value() {
		return setting_value;
	}

	public void setSetting_value(String setting_value) {
		this.setting_value = setting_value;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

}
