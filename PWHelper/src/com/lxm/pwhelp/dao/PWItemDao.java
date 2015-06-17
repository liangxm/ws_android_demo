package com.lxm.pwhelp.dao;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.lxm.pwhelp.bean.PWItem;
import com.lxm.pwhelp.db.DatabaseHelper;
import com.lxm.pwhelp.utils.LogUtil;

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
			LogUtil.e(context.getClass().getName(), e.getMessage());
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
			LogUtil.e(context.getClass().getName(), e.getMessage());
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
			LogUtil.e(context.getClass().getName(), e.getMessage());
		}
		return code;
	}
	
	public int update(PWItem item){
		int code = -1;
		try {
			code = itemDaoOpe.update(item);
		} catch (SQLException e) {
			LogUtil.e(context.getClass().getName(), e.getMessage());
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
			LogUtil.e(context.getClass().getName(), e.getMessage());
		}
		return item;
	}
	
	/**
	 * get all of available Item
	 * @return
	 */
	public List<PWItem> getAvailablePWItem(){
		List<PWItem> items = null;
		try {
			items = itemDaoOpe.queryBuilder().where().eq("deleted", false).query();
		} catch (SQLException e) {
			LogUtil.e(context.getClass().getName(), e.getMessage());
		}
		return items;
	}
	
	/**
	 * get all of items
	 * @return
	 */
	public List<PWItem> getPWItemAll(){
		List<PWItem> items = null;
		try {
			items = itemDaoOpe.queryBuilder().query();
		} catch (SQLException e) {
			LogUtil.e(context.getClass().getName(), e.getMessage());
		}
		return items;
	}
	
	public List<PWItem> getPWItemByType(String item_type){
		List<PWItem> items = null;
		try {
			items = itemDaoOpe.queryBuilder().where().eq("item_type", item_type).and().eq("deleted", false).query();
		} catch (SQLException e) {
			LogUtil.e(context.getClass().getName(), e.getMessage());
		}
		return items;
	}
	
	public List<PWItem> getPWItemByName(String filterKey){
		List<PWItem> items = null;
		try {
			items = itemDaoOpe.queryBuilder().where().like("item_username", "%"+filterKey+"%").query();
		} catch (SQLException e) {
			LogUtil.e(context.getClass().getName(), e.getMessage());
		}
		return items;
	}
	
	public int deleteAll(){
		int code = -1;
		try {
	        itemDaoOpe.queryRaw("delete from pw_item");
	        itemDaoOpe.queryRaw("update sqlite_sequence SET seq = 0 where name ='pw_item'");
		} catch (SQLException e) {
			LogUtil.e(context.getClass().getName(), e.getMessage());
		}
		return code;
	}
}
