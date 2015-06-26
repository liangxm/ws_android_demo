package com.lxm.pwhelp.dao;

import java.util.ArrayList;
import java.util.List;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import com.lxm.pwhelp.bean.Setting;
import com.lxm.pwhelp.db.MyDatabaseHelper;
import com.lxm.pwhelp.utils.Settings;
import android.content.Context;
/**
 * setting db dao
 * @author Listener
 * @version 2015-6-21 11:37:29
 */
public class PWSettingDao {

	private Context context;
	private SQLiteDatabase db;
	

	public PWSettingDao(Context context) {
		this.context=context;
	}

	/**
	 * add new setting record
	 * @param setting
	 */
	public void addSetting(Setting setting) {
		db = MyDatabaseHelper.getHelper(context).getWritableDatabase(Settings.SECRET_KEY);
		db.execSQL(
				"insert into pw_setting(setting_name,setting_value,created,deleted) values(?,?,?,?)",
				new Object[] { setting.getSetting_name(),
						setting.getSetting_value(), setting.getCreated(),
						setting.getDeleted() });
		db.close();
	}

	/**
	 * update the old setting record
	 * @param setting
	 */
	public void updateSetting(Setting setting) {
		db = MyDatabaseHelper.getHelper(context).getWritableDatabase(Settings.SECRET_KEY);
		db.execSQL(
				"update pw_setting set setting_name=?,setting_value=?,deleted=?",
				new Object[] { setting.getSetting_name(),
						setting.getSetting_value(), setting.getDeleted() });
		db.close();
	}

	/**
	 * query setting record by id
	 * @param id
	 * @return
	 */
	public Setting querySetting(int id) {
		db = MyDatabaseHelper.getHelper(context).getReadableDatabase(Settings.SECRET_KEY);
		Setting setting = null;
		Cursor cursor = db.rawQuery(
				"select * from pw_setting where deleted=? and item_id=?",
				new String[] {String.valueOf(0), String.valueOf(id) });
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				int setting_id = cursor.getInt(cursor
						.getColumnIndex("setting_id"));
				String setting_name = cursor.getString(cursor
						.getColumnIndex("setting_name"));
				String setting_value = cursor.getString(cursor
						.getColumnIndex("setting_value"));
				String created = cursor.getString(cursor
						.getColumnIndex("created"));
				int deleted = cursor.getInt(cursor.getColumnIndex("deleted"));
				setting = new Setting();
				setting.setSetting_id(setting_id);
				setting.setSetting_name(setting_name);
				setting.setSetting_value(setting_value);
				setting.setCreated(created);
				setting.setDeleted(deleted);
			}
		}
		cursor.close();
		db.close();
		return setting;
	}

	/**
	 * query all setting record
	 * @return
	 */
	public List<Setting> querySettingAll() {
		db = MyDatabaseHelper.getHelper(context).getReadableDatabase(Settings.SECRET_KEY);
		List<Setting> list = new ArrayList<Setting>();
		Cursor cursor = db.rawQuery("select * from pw_setting where deleted=?",
				new String[]{String.valueOf(0)});
		if(cursor!=null){
			while (cursor.moveToNext()) {
				int setting_id = cursor.getInt(cursor.getColumnIndex("setting_id"));
				String setting_name = cursor.getString(cursor
						.getColumnIndex("setting_name"));
				String setting_value = cursor.getString(cursor
						.getColumnIndex("setting_value"));
				String created = cursor.getString(cursor.getColumnIndex("created"));
				int deleted = cursor.getInt(cursor.getColumnIndex("deleted"));
				Setting setting = new Setting();
				setting.setSetting_id(setting_id);
				setting.setSetting_name(setting_name);
				setting.setSetting_value(setting_value);
				setting.setCreated(created);
				setting.setDeleted(deleted);
				list.add(setting);
			}
		}
		cursor.close();
		db.close();
		return list;
	}

	/**
	 * delete the setting record by setting_id
	 * @param setting
	 */
	public void deleteSetting(Setting setting) {
		db = MyDatabaseHelper.getHelper(context).getWritableDatabase(Settings.SECRET_KEY);
		db.execSQL("delete from pw_setting where setting_id=?",
				new String[] { String.valueOf(setting.getSetting_id()) });
		db.close();
	}

	/**
	 * query setting record by name
	 * @param name
	 * @return
	 */
	public Setting querySettingByName(String name) {
		db = MyDatabaseHelper.getHelper(context).getReadableDatabase(Settings.SECRET_KEY);
		Setting setting = null;
		Cursor cursor = db.rawQuery(
				"select * from pw_setting where deleted=? and setting_name=?",
				new String[] { String.valueOf(0),name });
		if(cursor!=null){
			if (cursor.moveToFirst()) {
				int setting_id = cursor.getInt(cursor.getColumnIndex("setting_id"));
				String setting_name = cursor.getString(cursor
						.getColumnIndex("setting_name"));
				String setting_value = cursor.getString(cursor
						.getColumnIndex("setting_value"));
				String created = cursor.getString(cursor.getColumnIndex("created"));
				int deleted = cursor.getInt(cursor.getColumnIndex("deleted"));
				setting = new Setting();
				setting.setSetting_id(setting_id);
				setting.setSetting_name(setting_name);
				setting.setSetting_value(setting_value);
				setting.setCreated(created);
				setting.setDeleted(deleted);
			}
		}
		cursor.close();
		db.close();
		return setting;
	}
	
	/**
	 * empty whole setting table
	 */
	public void emptyTable() {
		db = MyDatabaseHelper.getHelper(context).getWritableDatabase(Settings.SECRET_KEY);
		db.execSQL("delete from pw_setting");
		db.execSQL("update sqlite_sequence SET seq = ? where name = ?",new String[]{String.valueOf(0),"pw_setting"});
		db.close();
	}
}
