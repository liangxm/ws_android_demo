package com.lxm.pwhelp.dao;

import java.util.ArrayList;
import java.util.List;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import android.content.Context;

import com.lxm.pwhelp.bean.Group;
import com.lxm.pwhelp.db.MyDatabaseHelper;
import com.lxm.pwhelp.utils.Settings;

/**
 * group db dao
 * @author Listener
 * @version 2015-6-21 11:32:09
 */
public class PWGroupDao{

	private Context context;
	private SQLiteDatabase db;

	public PWGroupDao(Context context) {
		this.context = context;
	}

	/**
	 * add new group record
	 * @param group
	 */
	public void addGroup(Group group) {
		db = MyDatabaseHelper.getHelper(context).getWritableDatabase(Settings.SECRET_KEY);
		db.execSQL(
				"insert into pw_group(group_name,group_level,created,deleted) values(?,?,?,?)",
				new Object[] { group.getGroup_name(), group.getGroup_level(),
						group.getCreated(), group.getDeleted() });
		db.close();
	}

	/**
	 * update the old group
	 * @param group
	 */
	public void updateGroup(Group group) {
		db = MyDatabaseHelper.getHelper(context).getWritableDatabase(Settings.SECRET_KEY);
		db.execSQL("update pw_group set group_name=?,group_level=?,delete=?",
				new Object[] { group.getGroup_name(), group.getGroup_level(),
						group.getDeleted() });
		db.close();
	}

	/**
	 * query group record by id
	 * @param id
	 * @return
	 */
	public Group queryGroup(int id) {
		db = MyDatabaseHelper.getHelper(context).getReadableDatabase(Settings.SECRET_KEY);
		Group group = null;
		Cursor cursor = db.rawQuery("select * from pw_group where deleted=? and group_id=?",
				new String[] {String.valueOf(0), String.valueOf(id) });
		if(cursor!=null){
			if (cursor.moveToFirst()) {
				int group_id = cursor.getInt(cursor.getColumnIndex("group_id"));
				String group_name = cursor.getString(cursor
						.getColumnIndex("group_name"));
				String group_level = cursor.getString(cursor
						.getColumnIndex("group_level"));
				String created = cursor.getString(cursor.getColumnIndex("created"));
				int deleted = cursor.getInt(cursor.getColumnIndex("deleted"));
				group = new Group();
				group.setGroup_id(group_id);
				group.setGroup_name(group_name);
				group.setGroup_level(group_level);
				group.setCreated(created);
				group.setDeleted(deleted);
			}
		}
		cursor.close();
		db.close();
		return group;
	}
	
	/**
	 * query all group record
	 * @return
	 */
	public List<Group> queryGroupAll(){
		db = MyDatabaseHelper.getHelper(context).getReadableDatabase(Settings.SECRET_KEY);
		List<Group> list = new ArrayList<Group>();
		Cursor cursor = db.rawQuery("select * from pw_group where deleted=?", new String[]{String.valueOf(0)});
		if(cursor!=null){
			while (cursor.moveToNext()) {
				int group_id = cursor.getInt(cursor.getColumnIndex("group_id"));
				String group_name = cursor.getString(cursor
						.getColumnIndex("group_name"));
				String group_level = cursor.getString(cursor
						.getColumnIndex("group_level"));
				String created = cursor.getString(cursor.getColumnIndex("created"));
				int deleted = cursor.getInt(cursor.getColumnIndex("deleted"));
				list.add(new Group(group_id, group_name, group_level, created,
						deleted));
			}
		}
		cursor.close();
		db.close();
		return list;
	}
	
	/**
	 * empty the whole group table
	 */
	public void emptyTable(){
		db = MyDatabaseHelper.getHelper(context).getWritableDatabase(Settings.SECRET_KEY);
		db.execSQL("delete from pw_group");
		db.execSQL("update sqlite_sequence SET seq = ? where name = ?",new String[]{String.valueOf(0),"pw_group"});
		db.close();
	}
}
