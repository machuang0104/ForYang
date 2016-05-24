package com.ma.text.db.client.dao;

import java.util.List;

import android.util.Log;

import com.ma.text.app.App;
import com.ma.text.db.SQLiteDBManager;
import com.ma.text.db.exception.DBNotOpenException;
import com.ma.text.vo.db.ContentVo;

/**
 * Dao Example 使用时不通过Dao直接操作，通过对应的Manager来操作数据库
 * 
 * @author machuang
 * 
 */
public final class ContentDao {

	private final String TAG = "ContentDao";

	public ContentDao() {
		createDatabase();
	}

	/**
	 * 创建表
	 */
	private synchronized void createDatabase() {
		SQLiteDBManager manager = App.getApp().getSQLiteDatabasePool()
				.getSQLiteDatabase();
		if (manager != null) {
			if (!manager.hasTable(ContentVo.class)) {
				manager.creatTable(ContentVo.class);
			}
		}
		App.getApp().getSQLiteDatabasePool().releaseSQLiteDatabase(manager);
	}

	/**
	 * 增
	 * 
	 * @param entity
	 * @return
	 */
	public synchronized boolean insert(ContentVo entity) {
		SQLiteDBManager manager = App.getApp().getSQLiteDatabasePool()
				.getSQLiteDatabase();
		boolean result = manager.insert(entity);
		App.getApp().getSQLiteDatabasePool().releaseSQLiteDatabase(manager);
		if (!result) {
			Log.e(TAG, "添加失败");
		}

		return result;
	}

	/**
	 * 删1
	 * 
	 * @param entity
	 * @return
	 */
	public synchronized boolean delete(ContentVo entity) {
		SQLiteDBManager manager = App.getApp().getSQLiteDatabasePool()
				.getSQLiteDatabase();
		boolean result = manager.delete(entity);
		App.getApp().getSQLiteDatabasePool().releaseSQLiteDatabase(manager);
		if (!result) {
			Log.e(TAG, "修改失败");
		}

		return result;
	}

	/**
	 * 删除2
	 * 
	 * @param entity
	 * @return
	 */
	public synchronized boolean delete(String where) {
		SQLiteDBManager manager = App.getApp().getSQLiteDatabasePool()
				.getSQLiteDatabase();
		boolean result = manager.delete(ContentVo.class, where);
		App.getApp().getSQLiteDatabasePool().releaseSQLiteDatabase(manager);
		if (!result) {
			Log.e(TAG, "修改失败");
		}

		return result;
	}

	public synchronized boolean deleteAll() {
		SQLiteDBManager manager = App.getApp().getSQLiteDatabasePool()
				.getSQLiteDatabase();
		boolean result = manager.dropTable(ContentVo.class);
		App.getApp().getSQLiteDatabasePool().releaseSQLiteDatabase(manager);
		if (!result) {
			Log.e(TAG, "delete table fail");
		}

		return result;
	}

	/**
	 * 改
	 * 
	 * @param entity
	 * @return
	 */
	public synchronized boolean update(ContentVo entity) {
		SQLiteDBManager manager = App.getApp().getSQLiteDatabasePool()
				.getSQLiteDatabase();
		boolean result = manager.update(entity);
		App.getApp().getSQLiteDatabasePool().releaseSQLiteDatabase(manager);
		if (!result) {
			Log.e(TAG, "修改失败");
		}

		return result;
	}

	/**
	 * 查1
	 * 
	 * @return
	 */

	public synchronized List<ContentVo> query(String where) {
		SQLiteDBManager manager = App.getApp().getSQLiteDatabasePool()
				.getSQLiteDatabase();
		List<ContentVo> list = manager.query(ContentVo.class, where, null,
				null, "_createtime desc", null, null);
		App.getApp().getSQLiteDatabasePool().releaseSQLiteDatabase(manager);

		return list;
	}

	public synchronized List<ContentVo> findById(int id) {
		SQLiteDBManager manager = App.getApp().getSQLiteDatabasePool()
				.getSQLiteDatabase();
		List<ContentVo> list = manager.query(ContentVo.class, "_id = " + id,
				null, null, "_updateTime desc", null, null);
		App.getApp().getSQLiteDatabasePool().releaseSQLiteDatabase(manager);

		return list;
	}

	public synchronized void insertUpdate(ContentVo entity) {
		SQLiteDBManager manager = App.getApp().getSQLiteDatabasePool()
				.getSQLiteDatabase();
		manager.InsertUpdate(entity);
		App.getApp().getSQLiteDatabasePool().releaseSQLiteDatabase(manager);
	}

	/**
	 * 执行sql
	 * 
	 * @param entity
	 * @return
	 */
	public synchronized void execute(String sql) {
		SQLiteDBManager manager = App.getApp().getSQLiteDatabasePool()
				.getSQLiteDatabase();
		try {
			manager.execute(sql, null);
		} catch (DBNotOpenException e) {
			e.printStackTrace();
		} finally {
			App.getApp().getSQLiteDatabasePool().releaseSQLiteDatabase(manager);
		}
	}
}