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
package com.ma.text.db.entity;


/**
 * @Description: 数据库主键的字段
 * @author: ethan.qiu@sosino.com
 * @date: 2013-6-25
 */
public class PKPropertyEntity extends PropertyEntity
{

	public PKPropertyEntity()
	{
	}

	public PKPropertyEntity(String name, Class<?> type, Object defaultValue,
			boolean primaryKey, boolean isAllowNull, boolean autoIncrement,
			String columnName)
	{
		super(name, type, defaultValue, primaryKey, isAllowNull, autoIncrement,
				columnName);
	}

}
