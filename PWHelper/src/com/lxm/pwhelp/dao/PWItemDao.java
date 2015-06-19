package com.lxm.pwhelp.dao;

import java.sql.SQLException;
import java.util.List;

import net.sqlcipher.Cursor;
import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.lxm.pwhelp.bean.Item;
import com.lxm.pwhelp.bean.PWItem;
import com.lxm.pwhelp.utils.LogUtil;

public class PWItemDao extends BaseDao {

	private Context context;
	private Dao<PWItem, Integer> itemDaoOpe;

	// private DatabaseHelper helper;

	public PWItemDao(Context context) {
		super(context);
		// this.context = context;
		// try{
		// helper = DatabaseHelper.getHelper(context);
		// itemDaoOpe = helper.getDao(PWItem.class);
		// } catch (SQLException e){
		// LogUtil.e(context.getClass().getName(), e.getMessage());
		// }
	}

	public void addItem(Item item) {
		db.execSQL(
				"insert into pw_item(" + "item_name, " + "item_username, "
						+ "item_password, " + "item_type, " + "item_subtype, "
						+ "item_url, " + "item_comment, " + "question1, "
						+ "question2, " + "modified, " + "created, "
						+ "deleted" + ") values(?,?,?,?,?,?,?,?,?,?,?,?)",
				new Object[] { item.getItem_name(), item.getItem_username(),
						item.getItem_password(), item.getItem_type(),
						item.getItem_subtype(), item.getItem_url(),
						item.getItem_comment(), item.getQuestion1(),
						item.getQuestion2(), item.getModified(),
						item.getCreated(), item.getDeleted() });
	}

	public void updateItem(Item item) {
		db.execSQL("update pw_item set " + "item_name=?," + "item_username=?,"
				+ "item_password=?," + "item_type=?," + "item_subtype=?,"
				+ "item_url=?," + "item_comment=?," + "question1=?,"
				+ "questoin2=?," + "modified=?," + "delete=?", new String[] {});
	}

	public Item queryGroup(int id) {
		Cursor cursor = db.rawQuery(
				"select * from pw_item where deleted=0 and item_id=?",
				new String[] { String.valueOf(id) });
		while (cursor.moveToFirst()) {
			int item_id = cursor.getInt(cursor.getColumnIndex("item_id"));
			String item_name = cursor.getString(cursor.getColumnIndex("item_name"));
			String item_username = cursor.getString(cursor.getColumnIndex("item_username"));
			String item_password = cursor.getString(cursor.getColumnIndex("item_password"));
			String item_type = cursor.getString(cursor.getColumnIndex("item_type"));
			int item_subtype = cursor.getInt(cursor.getColumnIndex("item_subtype"));
			String item_url = cursor.getString(cursor.getColumnIndex("item_url"));
			String item_comment = cursor.getString(cursor.getColumnIndex("item_comment"));
			String question1 = cursor.getString(cursor.getColumnIndex("question1"));
			String question2 = cursor.getString(cursor.getColumnIndex("question2"));
			String modified = cursor.getString(cursor.getColumnIndex("modified"));
			String created = cursor.getString(cursor.getColumnIndex("created"));
			int deleted = cursor.getInt(cursor.getColumnIndex("deleted"));
			Item item = new Item();
			item.setItem_id(item_id);
			item.setItem_name(item_name);
			item.setItem_username(item_username);
			item.setItem_password(item_password);
			item.setItem_type(item_type);
			item.setItem_subtype(item_subtype);
			item.setItem_url(item_url);
			item.setItem_comment(item_comment);
			item.setQuestion1(question1);
			item.setQuestion2(question2);
			item.setModified(modified);
			item.setCreated(created);
			item.setDeleted(deleted);
			return item;
		}
		cursor.close();
		return null;
	}

	/**
	 * add a new item
	 * 
	 * @param item
	 */
	public CreateOrUpdateStatus createOrUpdate(PWItem item) {
		CreateOrUpdateStatus code = null;
		try {
			code = itemDaoOpe.createOrUpdate(item);
		} catch (SQLException e) {
			LogUtil.e(context.getClass().getName(), e.getMessage());
		}
		return code;
	}

	/**
	 * delete a item
	 * 
	 * @param item
	 * @return
	 */
	public int delete(int item_id) {
		int code = -1;
		try {
			code = itemDaoOpe.deleteById(item_id);
		} catch (SQLException e) {
			LogUtil.e(context.getClass().getName(), e.getMessage());
		}
		return code;
	}

	public int update(PWItem item) {
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
	 * 
	 * @param id
	 * @return
	 */
	public PWItem getPWItemByID(int id) {
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
	 * 
	 * @return
	 */
	public List<PWItem> getAvailablePWItem() {
		List<PWItem> items = null;
		try {
			items = itemDaoOpe.queryBuilder().where().eq("deleted", false)
					.query();
		} catch (SQLException e) {
			LogUtil.e(context.getClass().getName(), e.getMessage());
		}
		return items;
	}

	/**
	 * get all of items
	 * 
	 * @return
	 */
	public List<PWItem> getPWItemAll() {
		List<PWItem> items = null;
		try {
			items = itemDaoOpe.queryBuilder().query();
		} catch (SQLException e) {
			LogUtil.e(context.getClass().getName(), e.getMessage());
		}
		return items;
	}

	public List<PWItem> getPWItemByType(String item_type) {
		List<PWItem> items = null;
		try {
			items = itemDaoOpe.queryBuilder().where()
					.eq("item_type", item_type).and().eq("deleted", false)
					.query();
		} catch (SQLException e) {
			LogUtil.e(context.getClass().getName(), e.getMessage());
		}
		return items;
	}

	public List<PWItem> getPWItemByName(String filterKey) {
		List<PWItem> items = null;
		try {
			items = itemDaoOpe.queryBuilder().where()
					.like("item_username", "%" + filterKey + "%").query();
		} catch (SQLException e) {
			LogUtil.e(context.getClass().getName(), e.getMessage());
		}
		return items;
	}

	public int deleteAll() {
		int code = -1;
		try {
			itemDaoOpe.queryRaw("delete from pw_item");
			itemDaoOpe
					.queryRaw("update sqlite_sequence SET seq = 0 where name ='pw_item'");
		} catch (SQLException e) {
			LogUtil.e(context.getClass().getName(), e.getMessage());
		}
		return code;
	}
}
