package com.lxm.pwhelp.dao;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.lxm.pwhelp.bean.PWSetting;
import com.lxm.pwhelp.db.DatabaseHelper;

public class PWSettingDao {

	private Context context;
	private Dao<PWSetting, Integer> settingDaoOpe;
	private DatabaseHelper helper;
	
	public PWSettingDao(Context context) {
		this.context = context;
		try {
			helper = DatabaseHelper.getHelper(context);
			settingDaoOpe = helper.getDao(PWSetting.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public CreateOrUpdateStatus createOrUpdate(PWSetting setting){
		CreateOrUpdateStatus code = null;
		try {
			code = settingDaoOpe.createOrUpdate(setting);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return code;
	}
	
	public int update(PWSetting setting){
		int code = -1;
		try {
			code = settingDaoOpe.update(setting);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return code;
	}
	
	public List<PWSetting> getSettingByName(String name){
		List<PWSetting> setting = null;
		try {
			setting = settingDaoOpe.queryBuilder().where().eq("setting_name", name).query();
		} catch (SQLException e){
			e.printStackTrace();
		}
		return setting;
	}
}
