package com.ma.text.vo.db;

import com.ma.text.base.BaseEntity;
import com.ma.text.db.annotation.Column;
import com.ma.text.db.annotation.PrimaryKey;
import com.ma.text.db.annotation.TableName;

@SuppressWarnings("serial")
@TableName(name = "table_type")
public class TypeVo extends BaseEntity {
	
	/** 主键ID */
	@PrimaryKey(name = "_type_id", autoIncrement = true)
	private int type_id;

	@Column(name = "_type_title")
	private String title;
	@Column(name = "_createtime")
	private long creatTime;

	public int getType_id() {
		return type_id;
	}

	public void setType_id(int type_id) {
		this.type_id = type_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(long creatTime) {
		this.creatTime = creatTime;
	}

}