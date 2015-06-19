package com.lxm.pwhelp.db;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteDatabase.CursorFactory;
import net.sqlcipher.database.SQLiteOpenHelper;
import android.content.Context;
/**
 *  database helper for sqlcipher
 * @author lianxiao
 *
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

	public static final String CREATE_TABLE_ITEM = "create table pw_item("
			+ "item_id integer PRIMARY KEY AUTOINCREMENT, "
			+ "item_name varchar(50), "
			+ "item_username varchar(50), "
			+ "item_password varchar(50),"
			+ "item_type varchar(30), "
			+ "item_subtype varchar(30), "
			+ "item_url varchar(200), "
			+ "item_command text, "
			+ "item_question1 varchar(200), "
			+ "item_question2 varchar(200),"
			+ "modified date, "
			+ "created date,"
			+ "deleted blob"
			+ ");";
	private static final String CREATE_TABLE_GROUP = "create table pw_group("
			+ "group_id integer PRIMARY KEY AUTOINCREMENT, "
			+ "group_name varchar(30), "
			+ "group_level varchar(20), "
			+ "created date, "
			+ "deleted blob"
			+ ");";
	private static final String CREATE_TABLE_SETTING = "create table pw_setting("
			+ "setting_id integer PRIMARY KEY AUTOINCREMENT, "
			+ "setting_name varchar(20), "
			+ "setting_value varchar(50), "
			+ "created date, "
			+ "deleted blob"
			+ ");";

	public MyDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_ITEM);
		db.execSQL(CREATE_TABLE_GROUP);
		db.execSQL(CREATE_TABLE_SETTING);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	}

}