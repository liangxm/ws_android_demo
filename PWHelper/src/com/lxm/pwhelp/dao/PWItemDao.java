package com.lxm.pwhelp.dao;

import java.util.ArrayList;
import java.util.List;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import android.content.Context;

import com.lxm.pwhelp.bean.Item;
import com.lxm.pwhelp.db.MyDatabaseHelper;
import com.lxm.pwhelp.utils.Settings;
/**
 *  item dao 
 * @author Listener
 * @version 2015-6-21 11:30:57
 */
public class PWItemDao {

	private Context context;
	private SQLiteDatabase db;

	public PWItemDao(Context context) {
		this.context = context;
	}

	/**
	 * add new item record
	 * @param item
	 */
	public void addItem(Item item) {
		db = MyDatabaseHelper.getHelper(context).getWritableDatabase(
				Settings.SECRET_KEY);
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
		db.close();
	}

	/**
	 * update the old item record
	 * @param item
	 */
	public void updateItem(Item item) {
		db = MyDatabaseHelper.getHelper(context).getWritableDatabase(
				Settings.SECRET_KEY);
		db.execSQL("update pw_item set item_name=?,item_username=?,"
				+ "item_password=?,item_type=?,item_subtype=?,"
				+ "item_url=?,item_comment=?,question1=?,"
				+ "question2=?,modified=?,deleted=? where item_id=?",
				new String[] { item.getItem_name(), item.getItem_username(),
						item.getItem_password(), item.getItem_type(), String.valueOf(item.getItem_subtype()),
						item.getItem_url(), item.getItem_comment(), item.getQuestion1(),
						item.getQuestion2(), item.getModified(), String.valueOf(item.getDeleted()), String.valueOf(item.getItem_id())});
		db.close();
	}

	/**
	 * query item by id
	 * @param id
	 * @return
	 */
	public Item queryItem(int id) {
		db = MyDatabaseHelper.getHelper(context).getReadableDatabase(
				Settings.SECRET_KEY);
		Item item = null;
		Cursor cursor = db.rawQuery(
				"select * from pw_item where deleted=0 and item_id=?",
				new String[] { String.valueOf(id) });
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				int item_id = cursor.getInt(cursor.getColumnIndex("item_id"));
				String item_name = cursor.getString(cursor
						.getColumnIndex("item_name"));
				String item_username = cursor.getString(cursor
						.getColumnIndex("item_username"));
				String item_password = cursor.getString(cursor
						.getColumnIndex("item_password"));
				String item_type = cursor.getString(cursor
						.getColumnIndex("item_type"));
				int item_subtype = cursor.getInt(cursor
						.getColumnIndex("item_subtype"));
				String item_url = cursor.getString(cursor
						.getColumnIndex("item_url"));
				String item_comment = cursor.getString(cursor
						.getColumnIndex("item_comment"));
				String question1 = cursor.getString(cursor
						.getColumnIndex("question1"));
				String question2 = cursor.getString(cursor
						.getColumnIndex("question2"));
				String modified = cursor.getString(cursor
						.getColumnIndex("modified"));
				String created = cursor.getString(cursor
						.getColumnIndex("created"));
				int deleted = cursor.getInt(cursor.getColumnIndex("deleted"));
				item = new Item();
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
			}
			cursor.close();
			db.close();
		}
		return item;
	}

	/**
	 * query all item record
	 * @return
	 */
	public List<Item> queryItemAll() {
		db = MyDatabaseHelper.getHelper(context).getReadableDatabase(
				Settings.SECRET_KEY);
		List<Item> list = new ArrayList<Item>();
		Cursor cursor = db.rawQuery("select * from pw_item where deleted=?",
				new String[] { "0" });
		if (cursor != null) {
			while (cursor.moveToNext()) {
				int item_id = cursor.getInt(cursor.getColumnIndex("item_id"));
				String item_name = cursor.getString(cursor
						.getColumnIndex("item_name"));
				String item_username = cursor.getString(cursor
						.getColumnIndex("item_username"));
				String item_password = cursor.getString(cursor
						.getColumnIndex("item_password"));
				String item_type = cursor.getString(cursor
						.getColumnIndex("item_type"));
				int item_subtype = cursor.getInt(cursor
						.getColumnIndex("item_subtype"));
				String item_url = cursor.getString(cursor
						.getColumnIndex("item_url"));
				String item_comment = cursor.getString(cursor
						.getColumnIndex("item_comment"));
				String question1 = cursor.getString(cursor
						.getColumnIndex("question1"));
				String question2 = cursor.getString(cursor
						.getColumnIndex("question2"));
				String modified = cursor.getString(cursor
						.getColumnIndex("modified"));
				String created = cursor.getString(cursor
						.getColumnIndex("created"));
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
				list.add(item);
			}
		}
		cursor.close();
		db.close();
		return list;
	}

	/**
	 * query the item record by name
	 * @param filterKey
	 * @return
	 */
	public List<Item> getItemByName(String filterKey) {
		db = MyDatabaseHelper.getHelper(context).getReadableDatabase(
				Settings.SECRET_KEY);
		List<Item> items = new ArrayList<Item>();
		Cursor cursor = db.rawQuery(
				"select * from pw_item where deleted=0 and item_username like ?",
				new String[] { "%" + filterKey + "%" });
		if (cursor != null) {
			while (cursor.moveToNext()) {
				int item_id = cursor.getInt(cursor.getColumnIndex("item_id"));
				String item_name = cursor.getString(cursor
						.getColumnIndex("item_name"));
				String item_username = cursor.getString(cursor
						.getColumnIndex("item_username"));
				String item_password = cursor.getString(cursor
						.getColumnIndex("item_password"));
				String item_type = cursor.getString(cursor
						.getColumnIndex("item_type"));
				int item_subtype = cursor.getInt(cursor
						.getColumnIndex("item_subtype"));
				String item_url = cursor.getString(cursor
						.getColumnIndex("item_url"));
				String item_comment = cursor.getString(cursor
						.getColumnIndex("item_comment"));
				String question1 = cursor.getString(cursor
						.getColumnIndex("question1"));
				String question2 = cursor.getString(cursor
						.getColumnIndex("question2"));
				String modified = cursor.getString(cursor
						.getColumnIndex("modified"));
				String created = cursor.getString(cursor
						.getColumnIndex("created"));
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
				items.add(item);
			}
		}
		cursor.close();
		db.close();
		return items;
	}

	/**
	 * query the item record by type
	 * @param type
	 * @return
	 */
	public List<Item> getItemByType(String type) {
		SQLiteDatabase db = MyDatabaseHelper.getHelper(context)
				.getReadableDatabase(Settings.SECRET_KEY);
		List<Item> items = new ArrayList<Item>();
		Cursor cursor = db.rawQuery(
				"select * from pw_item where deleted=? and item_type=?",
				new String[] { "0", type });
		if (cursor != null) {
			while (cursor.moveToNext()) {
				int item_id = cursor.getInt(cursor.getColumnIndex("item_id"));
				String item_name = cursor.getString(cursor
						.getColumnIndex("item_name"));
				String item_username = cursor.getString(cursor
						.getColumnIndex("item_username"));
				String item_password = cursor.getString(cursor
						.getColumnIndex("item_password"));
				String item_type = cursor.getString(cursor
						.getColumnIndex("item_type"));
				int item_subtype = cursor.getInt(cursor
						.getColumnIndex("item_subtype"));
				String item_url = cursor.getString(cursor
						.getColumnIndex("item_url"));
				String item_comment = cursor.getString(cursor
						.getColumnIndex("item_comment"));
				String question1 = cursor.getString(cursor
						.getColumnIndex("question1"));
				String question2 = cursor.getString(cursor
						.getColumnIndex("question2"));
				String modified = cursor.getString(cursor
						.getColumnIndex("modified"));
				String created = cursor.getString(cursor
						.getColumnIndex("created"));
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
				items.add(item);
			}
		}
		cursor.close();
		db.close();
		return items;
	}
	
	/**
	 * query the item record by name
	 * @param filterKey
	 * @return
	 */
	public Item getMaxIdItem() {
		db = MyDatabaseHelper.getHelper(context).getReadableDatabase(
				Settings.SECRET_KEY);
		Item item = null;
		Cursor cursor = db.rawQuery("SELECT * FROM pw_item ORDER BY item_id desc", null);
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				int item_id = cursor.getInt(cursor.getColumnIndex("item_id"));
				String item_name = cursor.getString(cursor
						.getColumnIndex("item_name"));
				String item_username = cursor.getString(cursor
						.getColumnIndex("item_username"));
				String item_password = cursor.getString(cursor
						.getColumnIndex("item_password"));
				String item_type = cursor.getString(cursor
						.getColumnIndex("item_type"));
				int item_subtype = cursor.getInt(cursor
						.getColumnIndex("item_subtype"));
				String item_url = cursor.getString(cursor
						.getColumnIndex("item_url"));
				String item_comment = cursor.getString(cursor
						.getColumnIndex("item_comment"));
				String question1 = cursor.getString(cursor
						.getColumnIndex("question1"));
				String question2 = cursor.getString(cursor
						.getColumnIndex("question2"));
				String modified = cursor.getString(cursor
						.getColumnIndex("modified"));
				String created = cursor.getString(cursor
						.getColumnIndex("created"));
				int deleted = cursor.getInt(cursor.getColumnIndex("deleted"));
				item = new Item();
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
			}
		}
		cursor.close();
		db.close();
		return item;
	}
	
	/**
	 * empty whole item table
	 */
	public void emptyTable() {
		db = MyDatabaseHelper.getHelper(context).getWritableDatabase(
				Settings.SECRET_KEY);
		db.execSQL("delete from pw_item");
		db.execSQL("update sqlite_sequence SET seq = ? where name = ?",
				new String[] { String.valueOf(0), "pw_item" });
		db.close();
	}
}
