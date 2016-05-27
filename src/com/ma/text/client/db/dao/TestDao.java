package com.ma.text.client.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.ma.text.app.App;
import com.ma.text.vo.db.TestVo;
import com.ma.text.widget.db.SQLiteDBManager;
import com.ma.text.widget.db.exception.DBNotOpenException;

/**
 * Dao Example 使用时不通过Dao直接操作，通过对应的Manager来操作数据库
 * 
 * @author machuang
 * 
 */
public final class TestDao {

	private final String TAG = "BoxDao";

	public TestDao() {
		createDatabase();
	}

	/**
	 * 创建表
	 */
	private synchronized void createDatabase() {
		SQLiteDBManager manager = App.getApp().getSQLiteDatabasePool()
				.getSQLiteDatabase();
		if (manager != null) {
			if (!manager.hasTable(TestVo.class)) {
				manager.creatTable(TestVo.class);
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
	public synchronized boolean insert(TestVo entity) {
		SQLiteDBManager manager = App.getApp().getSQLiteDatabasePool()
				.getSQLiteDatabase();
		boolean result = manager.insert(entity);
		App.getApp().getSQLiteDatabasePool().releaseSQLiteDatabase(manager);
		if (!result) {
			Log.e(TAG, "添加失败");
		}

		return result;
	}

	public synchronized boolean insert(ArrayList<TestVo> list) {
		SQLiteDBManager manager = App.getApp().getSQLiteDatabasePool()
				.getSQLiteDatabase();
		boolean result = true;
		result = manager.insert(list, true);
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
	public synchronized boolean delete(TestVo entity) {
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
		boolean result = manager.delete(TestVo.class, where);
		App.getApp().getSQLiteDatabasePool().releaseSQLiteDatabase(manager);
		if (!result) {
			Log.e(TAG, "修改失败");
		}

		return result;
	}

	public synchronized boolean deleteAll() {
		SQLiteDBManager manager = App.getApp().getSQLiteDatabasePool()
				.getSQLiteDatabase();
		boolean result = manager.dropTable(TestVo.class);
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
	public synchronized boolean update(TestVo entity) {
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
	public synchronized List<TestVo> query(String where) {
		SQLiteDBManager manager = App.getApp().getSQLiteDatabasePool()
				.getSQLiteDatabase();
		List<TestVo> list = manager.query(TestVo.class, where,
				null, null, "_updateTime desc", null, null);
		App.getApp().getSQLiteDatabasePool().releaseSQLiteDatabase(manager);

		return list;
	}

	/**
	 * 查2
	 * 
	 * @param where
	 * @param orderby
	 * @return
	 */
	public synchronized List<TestVo> query(String where, String orderby) {
		SQLiteDBManager manager = App.getApp().getSQLiteDatabasePool()
				.getSQLiteDatabase();
		List<TestVo> list = manager.query(TestVo.class, where,
				null, null, orderby, null, null);
		App.getApp().getSQLiteDatabasePool().releaseSQLiteDatabase(manager);

		return list;
	}

	public synchronized void insertUpdate(TestVo entity) {
		SQLiteDBManager manager = App.getApp().getSQLiteDatabasePool()
				.getSQLiteDatabase();
		manager.InsertUpdate(entity);
		App.getApp().getSQLiteDatabasePool().releaseSQLiteDatabase(manager);
	}

	public synchronized void insertUpdate(ArrayList<TestVo> list) {
		SQLiteDBManager manager = App.getApp().getSQLiteDatabasePool()
				.getSQLiteDatabase();
		manager.InsertUpdate(list, true);
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