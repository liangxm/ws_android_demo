package com.lxm.pwhelp.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "pw_group")
public class PWGroup {

	@DatabaseField(generatedId = true)
	private int group_id;
	@DatabaseField
	private String group_name;
	@DatabaseField
	private String group_level;
	@DatabaseField
	private String created;
	@DatabaseField
	private boolean deleted;
	
	public PWGroup(){
	}
	
	public PWGroup(String group_name, String group_level,
			boolean deleted) {
		super();
		this.group_name = group_name;
		this.group_level = group_level;
		this.deleted = deleted;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(group_id+";");
		sb.append(group_name+";");
		return sb.toString();
	}

	public int getGroup_id() {
		return group_id;
	}

	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public String getGroup_level() {
		return group_level;
	}

	public void setGroup_level(String group_level) {
		this.group_level = group_level;
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

}
