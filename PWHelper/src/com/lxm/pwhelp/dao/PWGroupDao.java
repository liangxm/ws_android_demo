package com.lxm.pwhelp.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sqlcipher.Cursor;
import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.lxm.pwhelp.bean.Group;
import com.lxm.pwhelp.bean.PWGroup;
import com.lxm.pwhelp.utils.LogUtil;

//import com.lxm.pwhelp.db.DatabaseHelper;

public class PWGroupDao extends BaseDao {

	private Context context;
	private Dao<PWGroup, Integer> groupDaoOpe;

	// private DatabaseHelper helper;

	public PWGroupDao(Context context) {
		super(context);
		// this.context = context;
		// try {
		// helper = DatabaseHelper.getHelper(context);
		// groupDaoOpe = helper.getDao(PWGroup.class);
		// } catch (SQLException e) {
		// LogUtil.e(context.getClass().getName(), e.getMessage());
		// }
	}

	public void addGroup(Group group) {
		db.execSQL(
				"insert into pw_group(group_name,group_level,created,deleted) values(?,?,?,?)",
				new Object[] { group.getGroup_name(), group.getGroup_level(),
						group.getCreated(), group.getDeleted() });
	}

	public void updateGroup(Group group) {
		db.execSQL("update pw_group set group_name=?,group_level=?,delete=?",
				new Object[] { group.getGroup_name(), group.getGroup_level(),
						group.getDeleted() });
	}

	public Group queryGroup(int id) {
		Cursor cursor = db.rawQuery("select * from pw_group where deleted=0 and group_id=?",
				new String[] { String.valueOf(id) });
		while (cursor.moveToFirst()) {
			int group_id = cursor.getInt(cursor.getColumnIndex("group_id"));
			String group_name = cursor.getString(cursor
					.getColumnIndex("group_name"));
			String group_level = cursor.getString(cursor
					.getColumnIndex("group_level"));
			String created = cursor.getString(cursor.getColumnIndex("created"));
			int deleted = cursor.getInt(cursor.getColumnIndex("deleted"));
			return new Group(group_id, group_name, group_level, created,
					deleted);
		}
		cursor.close();
		return null;
	}
	
	public List<Group> queryGroupAll(){
		List<Group> list = new ArrayList<Group>();
		Cursor cursor = db.rawQuery("select * from pw_group where deleted=0",null);
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
		cursor.close();
		return list;
	}
	
	public void emptyTable(){
		db.execSQL("delete from pw_group");
		db.execSQL("update sqlite_sequence SET seq = 0 where name ='pw_group'");
	}

	/**
	 * add a new group
	 * 
	 * @param group
	 */
	public CreateOrUpdateStatus createOrUpdate(PWGroup group) {
		CreateOrUpdateStatus code = null;
		try {
			code = groupDaoOpe.createOrUpdate(group);
		} catch (SQLException e) {
			LogUtil.e(context.getClass().getName(), e.getMessage());
		}
		return code;
	}

	/**
	 * get a group by id
	 * 
	 * @param id
	 * @return
	 */
	public PWGroup getGroupByID(int id) {
		PWGroup group = null;
		try {
			group = groupDaoOpe.queryForId(id);
		} catch (SQLException e) {
			LogUtil.e(context.getClass().getName(), e.getMessage());
		}
		return group;
	}

	/**
	 * get all of groups which is available
	 * 
	 * @return
	 */
	public List<PWGroup> getAvailableGroup() {
		List<PWGroup> groups = null;
		try {
			groups = groupDaoOpe.queryBuilder().where().eq("deleted", false)
					.query();
		} catch (SQLException e) {
			LogUtil.e(context.getClass().getName(), e.getMessage());
		}
		return groups;
	}

	/**
	 * get all of groups
	 * 
	 * @return
	 */
	public List<PWGroup> getGroupAll() {
		List<PWGroup> groups = null;
		try {
			groups = groupDaoOpe.queryBuilder().query();
		} catch (SQLException e) {
			LogUtil.e(context.getClass().getName(), e.getMessage());
		}
		return groups;
	}

	public int deleteAll() {
		int code = -1;
		try {
			groupDaoOpe.queryRaw("delete from pw_group");
			groupDaoOpe
					.queryRaw("update sqlite_sequence SET seq = 0 where name ='pw_group'");
			// code = groupDaoOpe.delete(getGroupAll());
		} catch (SQLException e) {
			LogUtil.e(context.getClass().getName(), e.getMessage());
		}
		return code;
	}
}
