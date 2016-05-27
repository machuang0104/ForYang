package com.ma.text.vo.db;

import com.ma.text.base.BaseEntity;
import com.ma.text.widget.db.annotation.Column;
import com.ma.text.widget.db.annotation.PrimaryKey;
import com.ma.text.widget.db.annotation.TableName;

@SuppressWarnings("serial")
@TableName(name = "table_test")
public class TestVo extends BaseEntity {
	/** 主键ID */
	@PrimaryKey(name = "_id", autoIncrement = false)
	private int id;

	@Column(name = "_message")
	private String message;

	@Column(name = "_updateTime")
	private long updateTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "TestUpdateVo [id=" + id + ", message=" + message
				+ ", updateTime=" + updateTime + "]";
	}

}