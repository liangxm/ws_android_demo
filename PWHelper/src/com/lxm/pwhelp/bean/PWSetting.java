package com.lxm.pwhelp.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "pw_setting")
public class PWSetting {

	@DatabaseField(generatedId = true)
	private int setting_id;
	@DatabaseField(unique = true)
	private String setting_name;
	@DatabaseField
	private String setting_value;
	@DatabaseField
	private String created;
	@DatabaseField
	private Boolean deleted;

	public PWSetting() {
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("setting_id"+";");
		sb.append("setting_name"+";");
		sb.append("setting_value"+";");
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
