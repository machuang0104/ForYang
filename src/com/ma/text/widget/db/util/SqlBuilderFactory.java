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
package com.ma.text.widget.db.util;

import com.ma.text.widget.db.util.sql.DeleteSqlBuilder;
import com.ma.text.widget.db.util.sql.InsertSqlBuilder;
import com.ma.text.widget.db.util.sql.QuerySqlBuilder;
import com.ma.text.widget.db.util.sql.ReplaceIntoSqlBuilder;
import com.ma.text.widget.db.util.sql.SqlBuilder;
import com.ma.text.widget.db.util.sql.UpdateSqlBuilder;

/**
 * @Description: Sql构建器工厂,生成sql语句构建器
 * @author: ethan.qiu@sosino.com
 * @date: 2013-6-21
 */
public class SqlBuilderFactory {
	private static SqlBuilderFactory instance = null;
	/**
	 * 调用getSqlBuilder(int operate)返回插入sql语句构建器传入的参数
	 */
	public static final int INSERT = 0;
	/**
	 * 调用getSqlBuilder(int operate)返回查询sql语句构建器传入的参数
	 */
	public static final int SELECT = 1;
	/**
	 * 调用getSqlBuilder(int operate)返回删除sql语句构建器传入的参数
	 */
	public static final int DELETE = 2;
	/**
	 * 调用getSqlBuilder(int operate)返回更新sql语句构建器传入的参数
	 */
	public static final int UPDATE = 3;
	/**
	 * 调用getSqlBuilder(int operate)返回更新sql语句构建器传入的参数
	 */
	public static final int INSERT_UPDATE = 4;

	public static SqlBuilderFactory getInstance() {
		if (instance == null) {
			instance = new SqlBuilderFactory();
		}
		return instance;
	}

	/**
	 * 获得sql构建器
	 * 
	 * @param operate
	 * @return 构建器
	 */
	public synchronized SqlBuilder getSqlBuilder(int operate) {
		SqlBuilder sqlBuilder = null;
		switch (operate) {
		case INSERT:
			sqlBuilder = new InsertSqlBuilder();
			break;
		case SELECT:
			sqlBuilder = new QuerySqlBuilder();
			break;
		case DELETE:
			sqlBuilder = new DeleteSqlBuilder();
			break;
		case UPDATE:
			sqlBuilder = new UpdateSqlBuilder();
			break;
		case INSERT_UPDATE:
			sqlBuilder = new ReplaceIntoSqlBuilder();
			break;
		default:
			break;
		}
		return sqlBuilder;
	}
}
