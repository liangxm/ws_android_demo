/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package com.lxm.pwhelp.db;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.lxm.pwhelp.bean.PWItem;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.sql.SQLException;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.lxm.pwhelp.utils.LogUtil;
import android.util.Log;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	
	private static final String DATABASE_NAME = "sqlite-pwhelper.db";
	private static final int DATABASE_VERSION = 4;
    private Dao<PWItem, Integer> PWItemDao;
    
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, PWItem.class);
            LogUtil.d("DatabaseHelper.onCreate() ok");
            return;
        } catch(SQLException e) {
            LogUtil.e("Can\'t create database");
            e.printStackTrace();
        }
    }
    
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, PWItem.class, true);
            onCreate(database, connectionSource);
            return;
        } catch(SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can\'t drop databases");
            e.printStackTrace();
        }
    }
    
    public Dao<PWItem, Integer> getPWItemDao() throws SQLException {
        if(PWItemDao == null) {
            PWItemDao = getDao(PWItem.class);
        }
        return PWItemDao;
    }
    
    public void close() {
        super.close();
        PWItemDao = null;
    }
}