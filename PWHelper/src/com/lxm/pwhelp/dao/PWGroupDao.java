package com.lxm.pwhelp.dao;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.lxm.pwhelp.bean.PWGroup;
import com.lxm.pwhelp.db.DatabaseHelper;

public class PWGroupDao {

	private Context context;
	private Dao<PWGroup, Integer> groupDaoOpe;
	private DatabaseHelper helper;
	
	public PWGroupDao(Context context){
		this.context = context;
		try {
			helper = DatabaseHelper.getHelper(context);
			groupDaoOpe = helper.getDao(PWGroup.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * add a new group
	 * @param group
	 */
	public CreateOrUpdateStatus createOrUpdate(PWGroup group){
		CreateOrUpdateStatus code=null;
		try {
			code = groupDaoOpe.createOrUpdate(group);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return code;
	}
	
	/**
	 * get a group by id
	 * @param id
	 * @return
	 */
	public PWGroup getGroupByID(int id){
		PWGroup group = null;
		try {
			group = groupDaoOpe.queryForId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return group;
	}
	
	/**
	 * get all of groups which is available 
	 * @return
	 */
	public List<PWGroup> getAvailableGroup(){
		List<PWGroup> groups = null;
		try {
			groups = groupDaoOpe.queryBuilder().where().eq("deleted", false).query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return groups;
	}
	
	/**
	 * get all of groups
	 * @return
	 */
	public List<PWGroup> getGroupAll(){
		List<PWGroup> groups = null;
		try {
			groups = groupDaoOpe.queryBuilder().query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return groups;
	}
	
	public int deleteAll(){
		int code = -1;
		try {
			code = groupDaoOpe.delete(getGroupAll());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return code;
	}
}
