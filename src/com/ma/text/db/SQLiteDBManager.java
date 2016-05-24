/*
 * Copyright (C) 2013  ethan.qiu@sosino.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ma.text.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;

import com.ma.text.common.Config;
import com.ma.text.compoment.log.LogUtil;
import com.ma.text.db.entity.DBHashMap;
import com.ma.text.db.entity.DBMapArrayList;
import com.ma.text.db.entity.DBMasterEntity;
import com.ma.text.db.entity.NVArrayList;
import com.ma.text.db.exception.DBException;
import com.ma.text.db.exception.DBNotOpenException;
import com.ma.text.db.util.DBAnnoUtils;
import com.ma.text.db.util.SqlBuilderFactory;
import com.ma.text.db.util.sql.SqlBuilder;

/**
 * @Description: 数据库管理类，通过此类进行数据库的操作
 * @author: ethan.qiu@sosino.com
 * @date: 2013-6-21
 */
public class SQLiteDBManager {
	// public final static String DATABASE_PATH =
	// "data/data/com.sosino.community/databases";

	// 数据库默认设置
	public final static String DB_NAME = Config.DB_NAME;
	private final static int DB_VERSION = 1;// 默认数据库版本
	// 当前SQL指令
	private String queryStr = "";
	// 错误信息
	private String error = "";
	// 当前查询Cursor
	private Cursor queryCursor = null;
	// 是否已经连接数据库
	private Boolean isConnect = false;
	// 执行oepn打开数据库时，保存返回的数据库对象
	private SQLiteDatabase mSQLiteDatabase = null;
	private DBHelper mDBHelper = null;
	private DBUpdateListener mDbUpdateListener;

	private String ErrorDataBase = "DataBase Not Open!";

	public SQLiteDBManager(Context context) {
		DBParams params = new DBParams();
		this.mDBHelper = new DBHelper(context, params.getDbName(), null,
				params.getDbVersion());
	}

	/**
	 * 构造函数
	 * 
	 * @param context
	 *            上下文
	 * @param params
	 *            数据参数信息
	 */
	public SQLiteDBManager(Context context, DBParams params) {
		this.mDBHelper = new DBHelper(context, params.getDbName(), null,
				params.getDbVersion());
	}

	/**
	 * 设置升级的的监听器
	 * 
	 * @param dbUpdateListener
	 */
	public void setOnDbUpdateListener(DBUpdateListener dbUpdateListener) {
		this.mDbUpdateListener = dbUpdateListener;
		if (mDbUpdateListener != null) {
			mDBHelper.setOndbUpdateListener(mDbUpdateListener);
		}
	}

	/**
	 * 打开数据库如果是 isWrite为true,则磁盘满时抛出错误
	 * 
	 * @param isWrite
	 * @return
	 */
	public SQLiteDatabase openDatabase(DBUpdateListener dbUpdateListener,
			Boolean isWrite) {

		if (isWrite) {
			mSQLiteDatabase = openWritable(mDbUpdateListener);
		} else {
			mSQLiteDatabase = openReadable(mDbUpdateListener);
		}
		return mSQLiteDatabase;

	}

	/**
	 * 以读写方式打开数据库，一旦数据库的磁盘空间满了，数据库就不能以只能读而不能写抛出错误。
	 * 
	 * @param dbUpdateListener
	 * @return
	 */
	public SQLiteDatabase openWritable(DBUpdateListener dbUpdateListener) {
		if (dbUpdateListener != null) {
			this.mDbUpdateListener = dbUpdateListener;
		}
		if (mDbUpdateListener != null) {
			mDBHelper.setOndbUpdateListener(mDbUpdateListener);
		}
		try {
			mSQLiteDatabase = mDBHelper.getWritableDatabase();
			isConnect = true;
			// 注销数据库连接配置信息
			// 暂时不写
		} catch (Exception e) {
			isConnect = false;
		}

		return mSQLiteDatabase;
	}

	public void beginTransaction() {
		mSQLiteDatabase.beginTransaction();
	}

	public void setTransactionSuccessful() {
		mSQLiteDatabase.setTransactionSuccessful();
	}

	public void endTransaction() {
		mSQLiteDatabase.endTransaction();
	}

	/**
	 * 测试 SQLiteDatabase是否可用
	 * 
	 * @return
	 */
	public Boolean testSQLiteDatabase() {
		if (isConnect) {
			if (mSQLiteDatabase.isOpen()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 以读写方式打开数据库，如果数据库的磁盘空间满了，就会打开失败，当打开失败后会继续尝试以只读方式打开数据库。如果该问题成功解决，
	 * 则只读数据库对象就会关闭，然后返回一个可读写的数据库对象。
	 * 
	 * @param dbUpdateListener
	 * @return
	 */
	public SQLiteDatabase openReadable(DBUpdateListener dbUpdateListener) {
		if (dbUpdateListener != null) {
			this.mDbUpdateListener = dbUpdateListener;
		}
		if (mDbUpdateListener != null) {
			mDBHelper.setOndbUpdateListener(mDbUpdateListener);
		}
		try {
			mSQLiteDatabase = mDBHelper.getReadableDatabase();
			isConnect = true;
			// 注销数据库连接配置信息
			// 暂时不写
		} catch (Exception e) {
			isConnect = false;
		}

		return mSQLiteDatabase;
	}

	/**
	 * 执行查询，主要是SELECT, SHOW 等指令 返回数据集
	 * 
	 * @param sql
	 *            sql语句
	 * @param selectionArgs
	 * @return
	 */
	public ArrayList<DBHashMap<String>> query(String sql, String[] selectionArgs) {
		LogUtil.i(SQLiteDBManager.this, sql);
		if (testSQLiteDatabase()) {
			if (sql != null && !sql.equalsIgnoreCase("")) {
				this.queryStr = sql;
			}
			free();
			this.queryCursor = mSQLiteDatabase.rawQuery(sql, selectionArgs);
			if (queryCursor != null) {
				return getQueryCursorData();
			} else {
				LogUtil.e(SQLiteDBManager.this, "[Excute SQL Error]" + sql);
			}
		} else {
			LogUtil.e(SQLiteDBManager.this, ErrorDataBase);
		}
		return null;
	}

	/**
	 * 执行查询，主要是SELECT指令 返回数据集
	 * 
	 * @param clazz
	 * @param where
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> query(Class<?> clazz, String where, String groupBy,
			String having, String orderBy, String limit, String offset) {
		if (testSQLiteDatabase()) {
			List<T> list = null;
			SqlBuilder getSqlBuilder = SqlBuilderFactory.getInstance()
					.getSqlBuilder(SqlBuilderFactory.SELECT);
			getSqlBuilder.setClazz(clazz);
			getSqlBuilder.setCondition(false, where, groupBy, having, orderBy,
					limit, offset);
			try {
				String sqlString = getSqlBuilder.getSqlStatement();
				LogUtil.i(SQLiteDBManager.this, "excute " + sqlString);
				free();
				this.queryCursor = mSQLiteDatabase.rawQuery(sqlString, null);
				list = (List<T>) DBAnnoUtils.getListEntity(clazz,
						this.queryCursor);
			} catch (IllegalArgumentException e) {

				LogUtil.e(SQLiteDBManager.this, e.getMessage());
				e.printStackTrace();

			} catch (DBException e) {

				LogUtil.e(SQLiteDBManager.this, e.getMessage());
				e.printStackTrace();
			} catch (IllegalAccessException e) {

				LogUtil.e(SQLiteDBManager.this, e.getMessage());
				e.printStackTrace();
			} catch (SQLiteException e) {
				LogUtil.e(SQLiteDBManager.this, e.getMessage());
				e.printStackTrace();
			}
			return list;
		} else {
			return null;
		}

	}

	/**
	 * 查询记录
	 * 
	 * @param table
	 *            表名
	 * @param columns
	 *            需要查询的列
	 * @param selection
	 *            格式化的作为 SQL WHERE子句(不含WHERE本身)。 传递null返回给定表的所有行。
	 * @param selectionArgs
	 * @param groupBy
	 *            groupBy语句
	 * @param having
	 *            having语句
	 * @param orderBy
	 *            orderBy语句
	 * @return
	 */
	public ArrayList<DBHashMap<String>> query(String table, String[] columns,
			String selection, String[] selectionArgs, String groupBy,
			String having, String orderBy) {
		if (testSQLiteDatabase()) {
			this.queryCursor = mSQLiteDatabase.query(table, columns, selection,
					selectionArgs, groupBy, having, orderBy);
			if (queryCursor != null) {
				return getQueryCursorData();
			} else {
				LogUtil.e(SQLiteDBManager.this, "[Query Table Error]" + table);
			}
		} else {
			LogUtil.e(SQLiteDBManager.this, ErrorDataBase);
		}
		return null;
	}

	/**
	 * 查询记录
	 * 
	 * @param distinct
	 *            限制重复，如过为true则限制,false则不用管
	 * @param table
	 *            表名
	 * @param columns
	 *            需要查询的列
	 * @param selection
	 *            格式化的作为 SQL WHERE子句(不含WHERE本身)。 传递null返回给定表的所有行。
	 * @param selectionArgs
	 * @param groupBy
	 *            groupBy语句
	 * @param having
	 *            having语句
	 * @param orderBy
	 *            orderBy语句
	 * @param limit
	 *            limit语句
	 * @return
	 */
	public ArrayList<DBHashMap<String>> query(String table, boolean distinct,
			String[] columns, String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy, String limit) {
		if (testSQLiteDatabase()) {
			free();
			this.queryCursor = mSQLiteDatabase.query(distinct, table, columns,
					selection, selectionArgs, groupBy, having, orderBy, limit);
			if (queryCursor != null) {
				return getQueryCursorData();
			} else {
				LogUtil.e(SQLiteDBManager.this, "[Query Table Error]" + table);
			}
		} else {
			LogUtil.e(SQLiteDBManager.this, ErrorDataBase);
		}
		return null;
	}

	/**
	 * 查询记录
	 * 
	 * @param table
	 *            表名
	 * @param columns
	 *            需要查询的列
	 * @param selection
	 *            格式化的作为 SQL WHERE子句(不含WHERE本身)。 传递null返回给定表的所有行。
	 * @param selectionArgs
	 * @param groupBy
	 *            groupBy语句
	 * @param having
	 *            having语句
	 * @param orderBy
	 *            orderBy语句
	 * @param limit
	 *            limit语句
	 * @return
	 */
	public ArrayList<DBHashMap<String>> query(String table, String[] columns,
			String selection, String[] selectionArgs, String groupBy,
			String having, String orderBy, String limit) {

		if (testSQLiteDatabase()) {
			free();
			this.queryCursor = mSQLiteDatabase.query(table, columns, selection,
					selectionArgs, groupBy, having, orderBy, limit);
			if (queryCursor != null) {
				return getQueryCursorData();
			} else {
				LogUtil.e(SQLiteDBManager.this, "[Query Table Error]" + table);
			}
		} else {
			LogUtil.e(SQLiteDBManager.this, ErrorDataBase);
		}
		return null;
	}

	/**
	 * 查询记录
	 * 
	 * @param cursorFactory
	 * @param distinct
	 *            限制重复，如过为true则限制,false则不用管
	 * @param table
	 *            表名
	 * @param columns
	 *            需要查询的列
	 * @param selection
	 *            格式化的作为 SQL WHERE子句(不含WHERE本身)。 传递null返回给定表的所有行。
	 * @param selectionArgs
	 * @param groupBy
	 *            groupBy语句
	 * @param having
	 *            having语句
	 * @param orderBy
	 *            orderBy语句
	 * @param limit
	 *            limit语句
	 * @return
	 */
	public ArrayList<DBHashMap<String>> queryWithFactory(
			CursorFactory cursorFactory, boolean distinct, String table,
			String[] columns, String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy, String limit) {
		if (testSQLiteDatabase()) {
			free();
			this.queryCursor = mSQLiteDatabase.queryWithFactory(cursorFactory,
					distinct, table, columns, selection, selectionArgs,
					groupBy, having, orderBy, limit);
			if (queryCursor != null) {
				return getQueryCursorData();
			} else {
				LogUtil.e(SQLiteDBManager.this, "[Query Table Error]" + table);
			}
		} else {
			LogUtil.e(SQLiteDBManager.this, ErrorDataBase);
		}
		return null;

	}

	/**
	 * INSERT, UPDATE 以及DELETE
	 * 
	 * @param sql
	 *            语句
	 * @param bindArgs
	 * @throws DBNotOpenException
	 */
	public void execute(String sql, String[] bindArgs)
			throws DBNotOpenException {
		LogUtil.i(SQLiteDBManager.this, "Ready to excute SQL[" + sql + "]");
		if (testSQLiteDatabase()) {
			if (sql != null && !sql.equalsIgnoreCase("")) {
				this.queryStr = sql;
				if (bindArgs != null) {
					mSQLiteDatabase.execSQL(sql, bindArgs);
				} else {
					mSQLiteDatabase.execSQL(sql);
				}

			}

		} else {
			throw new DBNotOpenException(ErrorDataBase);
		}

	}

	/**
	 * 插入更新：存在则更新（except primarykey）,否则插入
	 * 
	 * @param entity
	 * @return
	 */
	public boolean InsertUpdate(Object entity) {
		Boolean isSuccess = false;
		SqlBuilder getSqlBuilder = SqlBuilderFactory.getInstance()
				.getSqlBuilder(SqlBuilderFactory.INSERT_UPDATE);
		getSqlBuilder.setEntity(entity);
		try {
			String sql = getSqlBuilder.getSqlStatement();
			LogUtil.i(SQLiteDBManager.this, "Ready to excute SQL[" + sql + "]");
			if (testSQLiteDatabase()) {
				if (sql != null && !sql.equalsIgnoreCase("")) {
					mSQLiteDatabase.execSQL(sql);
				}

			}
		} catch (IllegalArgumentException | IllegalAccessException
				| DBException e) {
			e.printStackTrace();
		}
		return isSuccess;
	}

	/**
	 * 插入更新：存在则更新（except primarykey）,否则插入
	 * 
	 * @param entity
	 * @return
	 */
	public boolean InsertUpdate(Object list, boolean isList) {
		ArrayList<Object> l = (ArrayList<Object>) list;
		int size = l.size();
		Boolean isSuccess = false;
		StringBuffer s = new StringBuffer("insert list result :{");
		for (int i = 0; i < size; i++) {
			SqlBuilder getSqlBuilder = SqlBuilderFactory.getInstance()
					.getSqlBuilder(SqlBuilderFactory.INSERT_UPDATE);
			getSqlBuilder.setEntity(l.get(i));
			isSuccess = execute(getSqlBuilder);
			s.append(i + ":" + isSuccess + " ; ");
		}
		s.append(" }");
		LogUtil.d(SQLiteDBManager.this, s.toString());

		return isSuccess;
	}

	/**
	 * 执行INSERT, UPDATE 以及DELETE操作
	 * 
	 * @param getSqlBuilder
	 *            Sql语句构建器
	 * @return
	 */
	public Boolean execute(SqlBuilder getSqlBuilder) {
		Boolean isSuccess = false;
		String sqlString;
		try {
			sqlString = getSqlBuilder.getSqlStatement();

			execute(sqlString, null);
			isSuccess = true;
		} catch (IllegalArgumentException e) {

			isSuccess = false;
			e.printStackTrace();

		} catch (DBException e) {

			isSuccess = false;
			e.printStackTrace();
		} catch (IllegalAccessException e) {

			isSuccess = false;
			e.printStackTrace();
		} catch (DBNotOpenException e) {

			e.printStackTrace();
			isSuccess = false;
		} catch (SQLiteException e) {

			e.printStackTrace();
			isSuccess = false;
		}
		return isSuccess;
	}

	/**
	 * 获得所有的查询数据集中的数据
	 * 
	 * @return
	 */
	public DBMapArrayList<String> getQueryCursorData() {
		DBMapArrayList<String> arrayList = null;
		if (queryCursor != null) {
			try {
				arrayList = new DBMapArrayList<String>();
				queryCursor.moveToFirst();
				while (queryCursor.moveToNext()) {
					arrayList.add(DBAnnoUtils.getRowData(queryCursor));
				}
			} catch (Exception e) {
				e.printStackTrace();
				LogUtil.e(SQLiteDBManager.this, "当前数据集获取失败！");
			}
		} else {
			LogUtil.e(SQLiteDBManager.this, "当前数据集不存在！");
		}
		return arrayList;
	}

	/**
	 * 取得数据库的表信息
	 * 
	 * @return
	 */
	public ArrayList<DBMasterEntity> getTables() {
		ArrayList<DBMasterEntity> tadbMasterArrayList = new ArrayList<DBMasterEntity>();
		String sql = "select * from sqlite_master where type='table' order by name";
		LogUtil.i(SQLiteDBManager.this, sql);
		if (testSQLiteDatabase()) {
			if (sql != null && !sql.equalsIgnoreCase("")) {
				this.queryStr = sql;
				free();
				queryCursor = mSQLiteDatabase
						.rawQuery(
								"select * from sqlite_master where type='table' order by name",
								null);

				if (queryCursor != null) {
					while (queryCursor.moveToNext()) {
						if (queryCursor != null
								&& queryCursor.getColumnCount() > 0) {
							DBMasterEntity tadbMasterEntity = new DBMasterEntity();
							tadbMasterEntity.setType(queryCursor.getString(0));
							tadbMasterEntity.setName(queryCursor.getString(1));
							tadbMasterEntity.setTbl_name(queryCursor
									.getString(2));
							tadbMasterEntity.setRootpage(queryCursor.getInt(3));
							tadbMasterEntity.setSql(queryCursor.getString(4));
							tadbMasterArrayList.add(tadbMasterEntity);
						}
					}
				} else {
					LogUtil.e(SQLiteDBManager.this, ErrorDataBase);
				}
			}
		} else {
			LogUtil.e(SQLiteDBManager.this, ErrorDataBase);
		}
		return tadbMasterArrayList;
	}

	/**
	 * 判断是否存在某个表,为true则存在，否则不存在
	 * 
	 * @param clazz
	 * @return true则存在，否则不存在
	 */
	public boolean hasTable(Class<?> clazz) {
		String tableName = DBAnnoUtils.getTableName(clazz);
		boolean b = hasTable(tableName);
		LogUtil.e(SQLiteDBManager.this, clazz.getSimpleName()
				+ ".hasTable()-->" + b);
		return b;
	}

	/**
	 * 判断是否存在某个表,为true则存在，否则不存在
	 * 
	 * @param tableName
	 *            需要判断的表名
	 * @return true则存在，否则不存在
	 */
	public boolean hasTable(String tableName) {
		if (tableName != null && !tableName.equalsIgnoreCase("")) {
			if (testSQLiteDatabase()) {
				tableName = tableName.trim();
				String sql = "select count(*) as c from Sqlite_master  where type ='table' and name ='"
						+ tableName + "' ";
				if (sql != null && !sql.equalsIgnoreCase("")) {
					this.queryStr = sql;
				}
				free();
				queryCursor = mSQLiteDatabase.rawQuery(sql, null);
				if (queryCursor.moveToNext()) {
					int count = queryCursor.getInt(0);
					if (count > 0)// 有
					{
						return true;
					}
				}
			} else {
				LogUtil.e(SQLiteDBManager.this, ErrorDataBase);
			}
		} else {
			LogUtil.e(SQLiteDBManager.this, "判断数据表名不能为空！");
		}
		return false;
	}

	/**
	 * 创建表
	 * 
	 * @param clazz
	 * @return 为true创建成功，为false创建失败
	 */
	public Boolean creatTable(Class<?> clazz) {
		Boolean isSuccess = false;
		if (testSQLiteDatabase()) {
			try {
				String sqlString = DBAnnoUtils.creatTableSql(clazz);
				execute(sqlString, null);
				isSuccess = true;
			} catch (DBException e) {
				isSuccess = false;
				e.printStackTrace();
				LogUtil.e(SQLiteDBManager.this, e.getMessage());
			} catch (DBNotOpenException e) {
				isSuccess = false;
				e.printStackTrace();
				LogUtil.e(SQLiteDBManager.this, e.getMessage());
			}
		} else {
			LogUtil.e(SQLiteDBManager.this, ErrorDataBase);
			return false;
		}

		LogUtil.e(SQLiteDBManager.this, clazz.getSimpleName()
				+ ".creatTable()-->" + isSuccess);

		return isSuccess;
	}

	public Boolean dropTable(Class<?> clazz) {
		String tableName = DBAnnoUtils.getTableName(clazz);
		boolean b = dropTable(tableName);
		LogUtil.e(SQLiteDBManager.this, clazz.getSimpleName()
				+ ".dropTable()-->" + b);
		return b;
	}

	/**
	 * 删除表
	 * 
	 * @param tableName
	 * @return 为true创建成功，为false创建失败
	 */
	public Boolean dropTable(String tableName) {
		Boolean isSuccess = false;
		if (tableName != null && !tableName.equalsIgnoreCase("")) {
			if (testSQLiteDatabase()) {
				try {
					String sqlString = "DROP TABLE " + tableName;
					execute(sqlString, null);
					isSuccess = true;
				} catch (Exception e) {

					isSuccess = false;
					e.printStackTrace();
					LogUtil.e(SQLiteDBManager.this, e.getMessage());
				}
			} else {
				LogUtil.e(SQLiteDBManager.this, ErrorDataBase);
				return false;
			}
		} else {
			LogUtil.e(SQLiteDBManager.this, "删除数据表名不能为空！");
		}
		return isSuccess;
	}

	public boolean checkColumnExists(String tableName, String columnName) {
		boolean result = false;
		Cursor cursor = null;

		try {
			cursor = mSQLiteDatabase
					.rawQuery(
							"select * from sqlite_master where name = ? and sql like ?",
							new String[] { tableName, "%" + columnName + "%" });
			result = null != cursor && cursor.moveToFirst();
		} catch (Exception e) {
			LogUtil.e(SQLiteDBManager.this,
					"checkColumnExists..." + e.getMessage());
		} finally {
			if (null != cursor && !cursor.isClosed()) {
				cursor.close();
			}
		}

		return result;
	}

	/**
	 * 更新表用于对实体修改时，改变表 暂时不写
	 * 
	 * @param tableName
	 * @return
	 */
	public Boolean alterTable(String tableName, String colName) {
		Boolean isSuccess = false;
		if (tableName != null && !tableName.equalsIgnoreCase("")) {
			if (testSQLiteDatabase()) {
				try {
					String sqlString = "ALTER TABLE " + tableName
							+ " ADD COLUMN '" + colName + "'";
					execute(sqlString, null);
					isSuccess = true;
				} catch (Exception e) {

					isSuccess = false;
					e.printStackTrace();
					LogUtil.e(SQLiteDBManager.this, e.getMessage());
				}
			} else {
				LogUtil.e(SQLiteDBManager.this, ErrorDataBase);
				return false;
			}
		} else {
			LogUtil.e(SQLiteDBManager.this, "删除数据表名不能为空！");
		}
		return isSuccess;
	}

	/**
	 * 数据库错误信息 并显示当前的SQL语句
	 * 
	 * @return
	 */
	public String error() {
		if (this.queryStr != null && !queryStr.equalsIgnoreCase("")) {
			error = error + "\n [ SQL语句 ] : " + queryStr;
		}
		LogUtil.e(SQLiteDBManager.this, error);
		return error;
	}

	/**
	 * 插入记录
	 * 
	 * @param entity
	 *            插入的实体
	 * @return
	 */
	public Boolean insert(Object entity) {
		return insert(entity, null);
	}

	/**
	 * 插入记录
	 * 
	 * @param table
	 *            需要插入到的表
	 * @param nullColumnHack
	 *            不允许为空的行
	 * @param values
	 *            插入的值
	 * @return
	 */
	public Boolean insert(String table, String nullColumnHack,
			ContentValues values) {
		if (testSQLiteDatabase()) {
			return mSQLiteDatabase.insert(table, nullColumnHack, values) > 0;
		} else {
			LogUtil.e(SQLiteDBManager.this, ErrorDataBase);
			return false;
		}
	}

	/**
	 * 插入记录
	 * 
	 * @param table
	 *            需要插入到的表
	 * @param nullColumnHack
	 *            不允许为空的行
	 * @param values
	 *            插入的值
	 * @return
	 */
	public Boolean insertOrThrow(String table, String nullColumnHack,
			ContentValues values) {
		if (testSQLiteDatabase()) {
			return mSQLiteDatabase.insertOrThrow(table, nullColumnHack, values) > 0;
		} else {
			LogUtil.e(SQLiteDBManager.this, ErrorDataBase);
			return false;
		}
	}

	/**
	 * 插入记录
	 * 
	 * @param entity
	 *            传入数据实体
	 * @param updateFields
	 *            插入到的字段,可设置为空
	 * @return 返回true执行成功，否则执行失败
	 */
	public Boolean insert(Object entity, NVArrayList updateFields) {
		SqlBuilder getSqlBuilder = SqlBuilderFactory.getInstance()
				.getSqlBuilder(SqlBuilderFactory.INSERT);
		getSqlBuilder.setEntity(entity);
		getSqlBuilder.setUpdateFields(updateFields);
		return execute(getSqlBuilder);
	}

	public boolean insert(Object list, boolean isList) {
		boolean result = false;
		ArrayList<Object> l = (ArrayList<Object>) list;
		int size = l.size();
		StringBuffer s = new StringBuffer("insert list result :{");
		for (int i = 0; i < size; i++) {
			SqlBuilder getSqlBuilder = SqlBuilderFactory.getInstance()
					.getSqlBuilder(SqlBuilderFactory.INSERT);
			getSqlBuilder.setEntity(l.get(i));
			result = execute(getSqlBuilder);
			s.append(i + ":" + result + " ; ");
		}
		s.append(" }");
		LogUtil.d(SQLiteDBManager.this, s.toString());
		return result;
	}

	/**
	 * 删除记录
	 * 
	 * @param table
	 *            被删除的表名
	 * @param whereClause
	 *            设置的WHERE子句时，删除指定的数据 ,如果null会删除所有的行。
	 * @param whereArgs
	 * 
	 * @return 返回true执行成功，否则执行失败
	 */
	public Boolean delete(String table, String whereClause, String[] whereArgs) {
		if (testSQLiteDatabase()) {
			return mSQLiteDatabase.delete(table, whereClause, whereArgs) > 0;

		} else {
			LogUtil.e(SQLiteDBManager.this, ErrorDataBase);
			return false;
		}
	}

	/**
	 * 删除记录
	 * 
	 * @param clazz
	 * @param where
	 *            where语句
	 * @return 返回true执行成功，否则执行失败
	 */
	public Boolean delete(Class<?> clazz, String where) {
		if (testSQLiteDatabase()) {
			SqlBuilder getSqlBuilder = SqlBuilderFactory.getInstance()
					.getSqlBuilder(SqlBuilderFactory.DELETE);
			getSqlBuilder.setClazz(clazz);
			getSqlBuilder.setCondition(false, where, null, null, null, null,
					null);
			return execute(getSqlBuilder);
		} else {
			return false;
		}

	}

	/**
	 * 删除记录
	 * 
	 * @param entity
	 * @return 返回true执行成功，否则执行失败
	 */
	public Boolean delete(Object entity) {
		if (testSQLiteDatabase()) {
			SqlBuilder getSqlBuilder = SqlBuilderFactory.getInstance()
					.getSqlBuilder(SqlBuilderFactory.DELETE);
			getSqlBuilder.setEntity(entity);
			return execute(getSqlBuilder);
		} else {
			return false;
		}

	}

	/**
	 * 更新记录
	 * 
	 * @param table
	 *            表名字
	 * @param values
	 * @param whereClause
	 * @param whereArgs
	 * @return 返回true执行成功，否则执行失败
	 */
	public Boolean update(String table, ContentValues values,
			String whereClause, String[] whereArgs) {
		if (testSQLiteDatabase()) {
			return mSQLiteDatabase
					.update(table, values, whereClause, whereArgs) > 0;
		} else {
			LogUtil.e(SQLiteDBManager.this, ErrorDataBase);
			return false;
		}
	}

	/**
	 * 更新记录 这种更新方式只有才主键不是自增的情况下可用
	 * 
	 * @param entity
	 *            更新的数据
	 * @return 返回true执行成功，否则执行失败
	 */
	public Boolean update(Object entity) {
		return update(entity, null);
	}

	/**
	 * 更新记录
	 * 
	 * @param entity
	 *            更新的数据
	 * @param where
	 *            where语句
	 * @return
	 */
	public Boolean update(Object entity, String where) {
		if (testSQLiteDatabase()) {
			SqlBuilder getSqlBuilder = SqlBuilderFactory.getInstance()
					.getSqlBuilder(SqlBuilderFactory.UPDATE);
			getSqlBuilder.setEntity(entity);
			getSqlBuilder.setCondition(false, where, null, null, null, null,
					null);
			return execute(getSqlBuilder);
		} else {
			return false;
		}

	}

	/**
	 * 获取最近一次查询的sql语句
	 * 
	 * @return sql 语句
	 */
	public String getLastSql() {
		return queryStr;
	}

	/**
	 * 获得当前查询数据集合
	 * 
	 * @return
	 */
	public Cursor getQueryCursor() {
		return queryCursor;
	}

	/**
	 * 关闭数据库
	 */
	public void close() {
		mSQLiteDatabase.close();
	}

	/**
	 * 释放查询结果
	 */
	public void free() {
		if (queryCursor != null) {
			try {
				this.queryCursor.close();
			} catch (Exception e) {
			}
		}

	}

	/**
	 * 数据库配置参数
	 */
	public static class DBParams {
		private String dbName = DB_NAME;
		private int dbVersion = DB_VERSION;

		public DBParams() {
		}

		public DBParams(String dbName) {
			this.dbName = dbName;
		}

		public DBParams(String dbName, int dbVersion) {
			this.dbName = dbName;
			this.dbVersion = dbVersion;
		}

		public String getDbName() {
			return dbName;
		}

		public void setDbName(String dbName) {
			this.dbName = dbName;
		}

		public int getDbVersion() {
			return dbVersion;
		}

		public void setDbVersion(int dbVersion) {
			this.dbVersion = dbVersion;
		}
	}

	/**
	 * Interface 数据库升级回调
	 */
	public interface DBUpdateListener {
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
	}
}
