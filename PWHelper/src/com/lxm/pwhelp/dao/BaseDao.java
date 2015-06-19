package com.lxm.pwhelp.dao;

import net.sqlcipher.database.SQLiteDatabase;
import android.content.Context;

import com.lxm.pwhelp.db.MyDatabaseHelper;
/**
 * base dao for sqlcipher
 * @author lianxiao
 *
 */
public class BaseDao {
	
	private static final String DATABASE_NAME = "pwhelper.db";
	private static final String SECRET_KEY = "pwhelper_lxm_2015";
	private static final int DATABASE_VERSION = 4;
	protected MyDatabaseHelper dbHelper;
	protected SQLiteDatabase db;

	public BaseDao(Context context) {
		dbHelper = new MyDatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
		db = dbHelper.getWritableDatabase(SECRET_KEY);
	}
}
