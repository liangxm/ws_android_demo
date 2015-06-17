package com.lxm.pwhelp.dao;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.lxm.pwhelp.bean.PWSetting;
import com.lxm.pwhelp.db.DatabaseHelper;
import com.lxm.pwhelp.utils.LogUtil;

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
			LogUtil.e(context.getClass().getName(), e.getMessage());
		}
	}
	
	public CreateOrUpdateStatus createOrUpdate(PWSetting setting){
		CreateOrUpdateStatus code = null;
		try {
			code = settingDaoOpe.createOrUpdate(setting);
		} catch (SQLException e) {
			LogUtil.e(context.getClass().getName(), e.getMessage());
		}
		return code;
	}
	
	public int update(PWSetting setting){
		int code = -1;
		try {
			code = settingDaoOpe.update(setting);
		} catch (SQLException e) {
			LogUtil.e(context.getClass().getName(), e.getMessage());
		}
		return code;
	}
	
	public int delete(PWSetting setting){
		int code = -1;
		try {
			code = settingDaoOpe.delete(setting);
		} catch (SQLException e){
			LogUtil.e(context.getClass().getName(), e.getMessage());
		}
		return code;
	}
	
	public List<PWSetting> getSettingAll(){
		List<PWSetting> settings = null;
		try {
			settings = settingDaoOpe.queryBuilder().query();
		} catch (SQLException e){
			LogUtil.e(context.getClass().getName(), e.getMessage());
		}
		return settings;
	}
	
	public List<PWSetting> getSettingByName(String name){
		List<PWSetting> setting = null;
		try {
			setting = settingDaoOpe.queryBuilder().where().eq("setting_name", name).query();
		} catch (SQLException e){
			LogUtil.e(context.getClass().getName(), e.getMessage());
		}
		return setting;
	}
	
	/**
	 * delete all data
	 * @return
	 */
	public int deleteAll(){
		int code = -1;
		try {
//			code = settingDaoOpe.delete(getSettingAll());
			settingDaoOpe.queryRaw("delete from pw_setting");
			settingDaoOpe.queryRaw("update sqlite_sequence SET seq = 0 where name ='pw_setting'");
		} catch (SQLException e) {
			LogUtil.e(context.getClass().getName(), e.getMessage());
		}
		return code;
		
	}
}
