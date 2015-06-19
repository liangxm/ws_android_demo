package com.lxm.pwhelp.bean;

import java.io.Serializable;

public class Group implements Serializable {

	private static final long serialVersionUID = 1L;

	private int group_id;
	private String group_name;
	private String group_level;
	private String created;
	private int deleted;

	public Group() {
	}

	public Group(int group_id, String group_name, String group_level, String created,
			int deleted) {
		super();
		this.group_id = group_id;
		this.group_name = group_name;
		this.group_level = group_level;
		this.created = created;
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(group_id + "|");
		sb.append(group_name + "|");
		sb.append(group_level + "|");
		sb.append(created + "|");
		sb.append(deleted);
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

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

}
