package com.lxm.pwhelp.dao;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
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
	public int add(PWItem item){
		int code = -1;
		try {
			code = itemDaoOpe.create(item);
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
			items=itemDaoOpe.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return items;
	}
}
