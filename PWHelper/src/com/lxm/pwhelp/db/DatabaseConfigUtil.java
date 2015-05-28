package com.lxm.pwhelp.db;

import java.io.IOException;

import java.sql.SQLException;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

public class DatabaseConfigUtil extends OrmLiteConfigUtil {

	public static void main(String[] args) throws IOException, SQLException {
		writeConfigFile("ormlite_config.txt");
	}
}
