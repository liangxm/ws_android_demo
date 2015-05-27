package com.lxm.pwhelp.dao;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.lxm.pwhelp.bean.PWItem;
import com.lxm.pwhelp.db.DatabaseHelper;

public class PWItemDao {

	private Context context;
	private Dao<PWItem, Integer> itemDaoOpe;
	private DatabaseHelper helper;
	
	public PWItemDao(Context context) {
		this.context = context;
		try{
			helper = DatabaseHelper.getHelper(context);
			itemDaoOpe = helper.getDao(PWItem.class);
		} catch (SQLException e){
			e.printStackTrace();
		} 
	}
	
	/**
	 * add a new item
	 * @param item
	 */
	public CreateOrUpdateStatus createOrUpdate(PWItem item){
		CreateOrUpdateStatus code=null;
		try {
			code = itemDaoOpe.createOrUpdate(item);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return code;
	}
	
	/**
	 * delete a item
	 * @param item
	 * @return
	 */
	public int delete(int item_id){
		int code = -1;
		try {
			code = itemDaoOpe.deleteById(item_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return code;
	}
	
	public int update(PWItem item){
		int code = -1;
		try {
			code = itemDaoOpe.update(item);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return code;
	}
	
	/**
	 * get a item by id
	 * @param id
	 * @return
	 */
	public PWItem getPWItemByID(int id){
		PWItem item = null;
		try {
			item = itemDaoOpe.queryForId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return item;
	}
	
	
	public List<PWItem> getPWItemAll(){
		List<PWItem> items = null;
		try {
			items = itemDaoOpe.queryBuilder().where().eq("deleted", false).query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return items;
	}
	
	public List<PWItem> getPWItemByType(String item_type){
		List<PWItem> items = null;
		try {
			items = itemDaoOpe.queryBuilder().where().eq("item_type", item_type).query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return items;
	}
}
