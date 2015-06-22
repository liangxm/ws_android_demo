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
	
	private static final String DATABASE_NAME = "pwhelper.db";
	private static final int DATABASE_VERSION = 4;

	public static final String CREATE_TABLE_ITEM = "create table pw_item("
			+ "item_id integer PRIMARY KEY AUTOINCREMENT, "
			+ "item_name varchar(50), "
			+ "item_username varchar(50), "
			+ "item_password varchar(50),"
			+ "item_type varchar(30), "
			+ "item_subtype varchar(30), "
			+ "item_url varchar(200), "
			+ "item_comment text, "
			+ "question1 varchar(200), "
			+ "question2 varchar(200), "
			+ "modified varchar(20), "
			+ "created varchar(20), "
			+ "deleted int"
			+ ");";
	private static final String CREATE_TABLE_GROUP = "create table pw_group("
			+ "group_id integer PRIMARY KEY AUTOINCREMENT, "
			+ "group_name varchar(30), "
			+ "group_level varchar(20), "
			+ "created varchar(20), "
			+ "deleted int(1)"
			+ ");";
	private static final String CREATE_TABLE_SETTING = "create table pw_setting("
			+ "setting_id integer PRIMARY KEY AUTOINCREMENT, "
			+ "setting_name varchar(20), "
			+ "setting_value varchar(50), "
			+ "created varchar(20), "
			+ "deleted int(1)"
			+ ");";
	
	private MyDatabaseHelper(Context context) {
		this(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public MyDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	
	/**
	 * get Helper single instance
	 * @param context
	 * @return instance
	 */
	public static MyDatabaseHelper getHelper(Context context){
		return new MyDatabaseHelper(context);
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